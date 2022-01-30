import arrow.core.Invalid
import arrow.core.Valid
import arrow.core.Validated
import io.vertx.core.MultiMap
import io.vertx.ext.web.RoutingContext
import io.vertx.kotlin.core.json.json
import io.vertx.kotlin.core.json.obj

class MyController {
    fun `do fun`(context: RoutingContext){
//        val address = context.request().connection().remoteAddress().toString()
        // Get the query parameter "name"
        val queryParams = context.queryParams()
        val name = getName(queryParams)
        // Write a json response
        when (name) {
            is Valid ->
            context.json(
                json {
                    obj(
                        "name" to name.value,
                        "message" to "Hello ${name.value} connected "
                    )
                }
            )
            is Invalid ->
                context.json(
                    json {
                        "Error" to name.value
                    }
                )
        }
    }

    private fun getName(queryParams: MultiMap): Validated<String, String> {
        val nameParam = queryParams.get("name")
        return if (nameParam == null) {
            Invalid("name unknown")
        } else {
            Valid(nameParam)
        }
    }
}