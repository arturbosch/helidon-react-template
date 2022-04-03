package template

import io.helidon.config.Config
import io.helidon.webserver.Routing
import io.helidon.webserver.ServerRequest
import io.helidon.webserver.ServerResponse
import io.helidon.webserver.Service
import javax.json.Json

internal class GreetService constructor(config: Config) : Service {

    private val greeting: String = config["app.greeting"].asString().orElse("Ciao")

    override fun update(rules: Routing.Rules) {
        rules.get("/", { _: ServerRequest, response: ServerResponse ->
            val msg = String.format("%s %s!", greeting, "World")
            LOGGER.info("Greeting message is $msg")
            response.send(JSON.obj { add("message", msg) })
        })
    }

    companion object {
        private val LOGGER = newLogger<GreetService>()
        private val JSON = Json.createBuilderFactory(emptyMap<String, Any>())
    }
}
