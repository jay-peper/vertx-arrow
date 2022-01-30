import io.vertx.core.Vertx
import io.vertx.core.http.HttpClient
import io.vertx.core.http.HttpClientResponse
import io.vertx.core.http.HttpMethod
import io.vertx.junit5.VertxExtension
import io.vertx.junit5.VertxTestContext
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.test.assertEquals


@ExtendWith(VertxExtension::class)
class MyVerticleTest {

    @BeforeEach
    fun deploy_verticle(vertx: Vertx, testContext: VertxTestContext) {
        vertx.deployVerticle(MyVerticle(), testContext.succeedingThenComplete())
    }

    @Test
    fun `with error`(vertx: Vertx, testContext: VertxTestContext) {
        val client: HttpClient = vertx.createHttpClient()
        client.request(HttpMethod.GET, 8888, "localhost", "/")
            .compose { req -> req.send().compose(HttpClientResponse::body) }
            .onComplete(testContext.succeeding { buffer ->
                testContext.verify {
                    assertEquals("""{"first":"Error","second":"name unknown"}""", buffer.toString())
                    testContext.completeNow()
                }
            })
    }

    @Test
    fun `with success`(vertx: Vertx, testContext: VertxTestContext) {
        val client: HttpClient = vertx.createHttpClient()
        client.request(HttpMethod.GET, 8888, "localhost", "/?name=Jay")
            .compose { req -> req.send().compose(HttpClientResponse::body) }
            .onComplete(testContext.succeeding { buffer ->
                testContext.verify {
                    assertEquals("""{"name":"Jay","message":"Hello Jay connected "}""", buffer.toString())
                    testContext.completeNow()
                }
            })
    }
}