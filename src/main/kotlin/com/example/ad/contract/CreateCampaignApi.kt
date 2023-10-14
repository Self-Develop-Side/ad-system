package com.example.ad.contract

import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ProblemDetail
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import kotlin.reflect.KClass

@RestController
class CreateCampaignApi {

    @PostMapping("/api/v1/campaigns/contract")
    fun create(@RequestBody @Valid request: CreateCampaignRequest) {
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handle(e: HttpMessageNotReadableException): ResponseEntity<ProblemDetail> {
        return ResponseEntity
            .badRequest()
            .body(ProblemDetails.forUnknownInput())
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handle(e: MethodArgumentNotValidException): ResponseEntity<ProblemDetail> {
        val validationErrors = e.bindingResult.fieldErrors.map {
            ValidationError(it.field, it.defaultMessage)
        }
        return ResponseEntity
            .badRequest()
            .body(ProblemDetails.forNotValidInput(validationErrors))
    }
}

object ProblemDetails {
    fun forUnknownInput(): ProblemDetail {
        return ProblemDetail.forStatusAndDetail(
            HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value()),
            ErrorMessage.UNKNOWN_INPUT.value,
        )
    }

    fun forNotValidInput(validationErrors: List<ValidationError>): ProblemDetail {
        val problemDetail = ProblemDetail.forStatusAndDetail(
            HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value()),
            ErrorMessage.NOT_VALID_INPUT.value,
        ).also {
            it.setProperty("validationErrors", validationErrors)
        }
        return problemDetail
    }
}

data class ValidationError(
    val field: String,
    val reason: String?,
)

enum class ErrorMessage(val value: String) {
    UNKNOWN_INPUT("처리 할 수 없는 입력 값입니다"),
    NOT_VALID_INPUT("입력 값이 유효하지 않습니다"),
}

enum class CampaignType(val value: String) {
    PAID("유료"),
    IN_HOUSE("내부"),
    ;

    fun isValid(value: String): Boolean = this.value == value
}

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [CampaignTypeValidator::class])
annotation class CampaignTypeConstraint(
    val message: String = "캠페인 타입이 유효하지 않습니다",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Any>> = [],
)

class CampaignTypeValidator : ConstraintValidator<CampaignTypeConstraint, String> {
    override fun isValid(value: String, context: ConstraintValidatorContext): Boolean =
        CampaignType.entries.any { it.isValid(value) }
}

data class CreateCampaignRequest(
    @field:NotBlank
    val clientId: String,
    @field:NotBlank
    @field:Size(min = 25, max = 50)
    val name: String,
    @field:NotBlank
    @field:Size(min = 5, max = 10)
    val createdBy: String,
    @field:NotBlank
    @field:CampaignTypeConstraint
    val campaignType: String,
)
