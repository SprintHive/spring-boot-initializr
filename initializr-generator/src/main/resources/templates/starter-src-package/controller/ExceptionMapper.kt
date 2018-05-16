package {{packageName}}.controller

import brave.Tracer
import com.sprinthive.hello.controller.model.ErrorCode
import com.sprinthive.hello.controller.model.ErrorDetail
import com.sprinthive.hello.controller.model.ExceptionMessage
import com.sprinthive.hello.controller.model.RequestError
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.NoHandlerFoundException

@RestControllerAdvice
class ExceptionMapper {

    @Autowired
    lateinit var tracer : Tracer

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun validationError(exception: MethodArgumentNotValidException) : ExceptionMessage {
        val errors = exception.bindingResult.fieldErrors.asSequence()
                .map { ErrorDetail("Field '${it.field}' constraint violated: ${it.code}", ErrorCode.INVALID_PARAMETER.code) }
                .toList()
        return ExceptionMessage(RequestError(errors, HttpStatus.BAD_REQUEST.value(), traceId()))
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun invalidOperation(exception: NoHandlerFoundException) : ExceptionMessage {
        val errors = listOf(ErrorDetail("Invalid operation", ErrorCode.INVALID_OPERATION.code))
        return ExceptionMessage(RequestError(errors, HttpStatus.NOT_FOUND.value(), traceId()))
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun internalServerError(exception: RuntimeException) : ExceptionMessage {
        val errors = listOf(ErrorDetail("Internal server error", ErrorCode.INTERNAL_ERROR.code))
        return ExceptionMessage(RequestError(errors, HttpStatus.INTERNAL_SERVER_ERROR.value(), traceId()))
    }

    private fun traceId() : String? {
        return tracer.currentSpan()?.context()?.traceIdString()
    }
}
