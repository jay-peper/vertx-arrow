import io.vertx.core.Vertx

fun main() {
    Vertx.vertx().deployVerticle(MyVerticle())
}