package {{packageName}}.controller.model;

import java.time.Instant

data class ExceptionMessage(val error: RequestError)

data class RequestError(val errors: List<ErrorDetail>, val httpCode: Int, val traceId: String?, val timestamp: Instant = Instant.now())

data class ErrorDetail(val message: String, val code: Int)

