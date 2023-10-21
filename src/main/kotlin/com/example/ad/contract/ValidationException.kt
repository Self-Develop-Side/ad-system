package com.example.ad.contract

sealed class ValidationException(val validationError: ValidationError) : RuntimeException()
