package {{packageName}}.controller.model;

enum class ErrorCode(val code: Int) {
    INVALID_OPERATION(1000),
    INVALID_PARAMETER(1001),
    INTERNAL_ERROR(5000)
}
