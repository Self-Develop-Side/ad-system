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
class CreateCampaignApi(
    private val campaignRepository: CampaignRepository,
) {

    @PostMapping("/api/v1/campaigns/contract")
    fun create(@RequestBody @Valid request: CreateCampaignRequest): ResponseEntity<CampaignResponse> {
        val campaign = Campaign(
            clientId = ClientId(request.clientId),
            name = request.name,
            createdBy = request.createdBy,
            campaignType = CampaignType.findByCampaignType(request.campaignType),
        )
        campaignRepository.save(campaign)
        return ResponseEntity.ok()
            .body(
                CampaignResponse(
                    id = campaign.id.toString(),
                    clientId = campaign.clientId.value.toString(),
                    name = campaign.name,
                    createdBy = campaign.createdBy,
                    campaignType = campaign.campaignType.value,
                    status = campaign.status.toString(),
                ),
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

data class CampaignResponse(
    val id: String,
    val clientId: String,
    val name: String,
    val createdBy: String,
    val campaignType: String,
    val status: String,
)
