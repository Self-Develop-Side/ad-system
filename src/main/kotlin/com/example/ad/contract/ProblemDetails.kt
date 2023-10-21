package com.example.ad.contract

import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ProblemDetail

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
