package com.example.ad.contract

import jakarta.validation.Valid
import org.springframework.http.ProblemDetail
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
class CreateCampaignApi {

    @PostMapping("/api/v1/campaigns/contract")
    fun create(@RequestBody @Valid request: CreateCampaignRequest) {
        val campaign = Campaign(
            id = UUID.randomUUID(),
            clientId = ClientId(request.clientId),
            name = request.name,
            createdBy = request.createdBy,
            campaignType = CampaignType.findByCampaignType(request.campaignType),
        )
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

    @ExceptionHandler(ValidationException::class)
    fun handle(e: ValidationException): ResponseEntity<ProblemDetail> {
        return ResponseEntity
            .badRequest()
            .body(ProblemDetails.forNotValidInput(listOf(e.validationError)))
    }
}

class ClientId(value: String) {
    private val value: UUID

    init {
        this.value = this.runCatching { UUID.fromString(value) }
            .getOrElse { throw InvalidClientIdException() }
    }
}

class InvalidClientIdException : ValidationException(ValidationErrors.invalidClientId())
