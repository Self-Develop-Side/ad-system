package com.example.ad.contract

data class ValidationError(
    val field: String,
    val reason: String?,
)
