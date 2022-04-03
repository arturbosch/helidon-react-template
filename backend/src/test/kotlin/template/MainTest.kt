package template

import io.helidon.config.Config
import io.helidon.config.MapConfigSource
import io.helidon.media.jsonp.JsonpSupport
import io.helidon.webclient.WebClient
import io.helidon.webclient.WebClientResponse
import io.helidon.webserver.WebServer
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import java.util.concurrent.TimeUnit
import javax.json.JsonObject

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MainTest {

    private lateinit var webServer: WebServer
    private lateinit var webClient: WebClient

    @BeforeAll
    fun startTheServer() {
        val config: Config = Config.create(
            MapConfigSource.create(mapOf("server.port" to randomPort().toString(), "app.greeting" to "Hello"))
        )
        webServer = startServer(config).await()
        webClient = WebClient.builder()
            .baseUri("http://localhost:" + webServer.port())
            .addMediaSupport(JsonpSupport.create())
            .build()
    }

    @AfterAll
    fun stopServer() {
        webServer.shutdown()
            .toCompletableFuture()
            .get(10, TimeUnit.SECONDS)
    }

    @Test
    fun testHelloWorld() {
        val jsonObject: JsonObject = webClient.get()
            .path("/greet")
            .request(JsonObject::class.java)
            .await()
        Assertions.assertEquals("Hello World!", jsonObject.getString("message"))
        var response: WebClientResponse = webClient.get()
            .path("/health")
            .request()
            .await()
        Assertions.assertEquals(200, response.status().code())
        response = webClient.get()
            .path("/metrics")
            .request()
            .await()
        Assertions.assertEquals(200, response.status().code())
        response = webClient.get()
            .path("/")
            .request()
            .await()
        Assertions.assertEquals(200, response.status().code())
    }
}
