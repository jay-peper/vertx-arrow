import io.vertx.core.AbstractVerticle
import io.vertx.ext.web.Router

class MyVerticle : AbstractVerticle() {

    // Called when verticle is deployed
    override fun start() {
        // Create a Router
        val router = Router.router(vertx)

        // Mount the handler for all incoming requests at every path and HTTP method
        router.route().handler(MyController()::`do fun`)

        // Create the HTTP server
        vertx.createHttpServer()
            // Handle every request using the router
            .requestHandler(router)
            // Start listening
            .listen(8888)
            // Print the port
            .onSuccess { server ->
                println("HTTP server started on port " + server.actualPort())
            }
    }


}