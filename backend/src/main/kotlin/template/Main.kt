@file:JvmName("Main")

package template

import io.helidon.common.LogConfig
import io.helidon.common.reactive.Single
import io.helidon.config.Config
import io.helidon.health.HealthSupport
import io.helidon.health.checks.HealthChecks
import io.helidon.media.jsonp.JsonpSupport
import io.helidon.metrics.MetricsSupport
import io.helidon.webserver.Routing
import io.helidon.webserver.WebServer

fun main() {
    startServer()
}

fun startServer(config: Config = Config.create()): Single<WebServer> {
    // load logging configuration
    LogConfig.configureRuntime()

    val webserver = webserver {
        config(config["server"])
        routing(createRouting(config))
        addMediaSupport(JsonpSupport.create())
    }.start()

    // Try to start the server. If successful, print some info and arrange to
    // print a message at shutdown. If unsuccessful, print the exception.
    webserver.thenAccept { ws: WebServer ->
        println("WEB server is UP: http://localhost:${ws.port()}")
        ws.whenShutdown().thenRun { println("WEB server is DOWN.") }
    }.exceptionallyAccept { error: Throwable ->
        println("Startup failed: " + error.message)
        error.printStackTrace(System.err)
    }

    return webserver
}

private fun createRouting(config: Config): Routing {
    val metrics = MetricsSupport.create()
    val greetService = GreetService(config)
    val health = HealthSupport.builder()
        .addLiveness(*HealthChecks.healthChecks()) // Adds a convenient set of checks
        .build()
    val staticService = serveStaticFiles()
    return routing {
        register(health) // Health at "/health"
        register(metrics) // Metrics at "/metrics"
        register("/greet", greetService)
        register("/", staticService)
    }
}
