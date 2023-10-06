package com.example.ad.contract

import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ProblemDetail
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class CreateCampaignApi {

    @PostMapping("/api/v1/campaigns/contract")
    fun create(@RequestBody request: CreateCampaignRequest) {
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handle(e: HttpMessageNotReadableException): ResponseEntity<ProblemDetail> {
        return ResponseEntity
            .badRequest()
            .body(ProblemDetails.forNullInput())
    }

    companion object {
        const val PATH = "/api/v1/campaigns/contract"
    }
}

object ProblemDetails {
    fun forNullInput(): ProblemDetail {
        return ProblemDetail.forStatusAndDetail(
            HttpStatusCode.valueOf(HttpStatus.BAD_REQUEST.value()),
            ErrorMessage.NULL_INPUT.value
        )
    }
}

enum class ErrorMessage(val value: String) {
    NULL_INPUT("해당 입력 값은 NULL일 수 없습니다");
}

data class CreateCampaignRequest(
    val name: String,
)
