package template

import io.helidon.webserver.Routing
import io.helidon.webserver.Service
import io.helidon.webserver.WebServer
import io.helidon.webserver.staticcontent.StaticContentSupport
import java.util.logging.Logger
import javax.json.JsonBuilderFactory
import javax.json.JsonObject
import javax.json.JsonObjectBuilder

inline fun webserver(init: WebServer.Builder.() -> Unit): WebServer = WebServer.builder().apply(init).build()

inline fun routing(init: Routing.Builder.() -> Unit): Routing = Routing.builder().apply(init).build()

inline fun JsonBuilderFactory.obj(init: JsonObjectBuilder.() -> Unit): JsonObject =
    createObjectBuilder().apply(init).build()

inline fun <reified T : Any> newLogger(): Logger = Logger.getLogger(T::class.java.name)

inline fun serveStaticFiles(
    resourceRoot: String = "/static",
    indexFile: String = "index.html",
    crossinline init: StaticContentSupport.ClassPathBuilder.() -> Unit = {}
): Service =
    StaticContentSupport.builder(resourceRoot)
        .apply(init)
        .welcomeFileName(indexFile)
        .build()
