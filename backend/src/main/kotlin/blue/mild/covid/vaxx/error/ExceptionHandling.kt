package blue.mild.covid.vaxx.error

import blue.mild.covid.vaxx.monitoring.CALL_ID
import blue.mild.covid.vaxx.security.auth.AuthorizationException
import blue.mild.covid.vaxx.security.auth.InsufficientRightsException
import blue.mild.covid.vaxx.utils.createLogger
import io.ktor.application.Application
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.StatusPages
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond
import org.slf4j.MDC

private val logger = createLogger("ExceptionHandler")

/**
 * Registers exception handling.
 */
fun Application.installExceptionHandling() {
    install(StatusPages) {
        exception<InsufficientRightsException> {
            call.respond(HttpStatusCode.Forbidden)
        }

        exception<AuthorizationException> {
            call.respond(HttpStatusCode.Unauthorized)
        }

        exception<EntityNotFoundException> { cause ->
            logger.warn { cause.message }
            call.errorResponse(HttpStatusCode.NotFound, "Not found.")
        }

        exception<ValidationException> { cause ->
            logger.warn { cause.message }
            call.errorResponse(HttpStatusCode.BadRequest, "Bad request. ${cause.message}")
        }

        exception<EmptyStringException> { cause ->
            logger.warn { cause.message }
            call.errorResponse(HttpStatusCode.BadRequest, "Bad request. ${cause.message}")
        }

        exception<Exception> { cause ->
            logger.error(cause) { "Exception occurred in the application: ${cause.message}" }
            call.errorResponse(
                HttpStatusCode.InternalServerError,
                "Server was unable to fulfill the request, please contact administrator with request ID: ${MDC.get(CALL_ID)}"
            )
        }
    }
}

suspend inline fun ApplicationCall.errorResponse(statusCode: HttpStatusCode, message: String?) {
    respond(status = statusCode, message = mapOf("message" to (message ?: "No details specified")))
}
