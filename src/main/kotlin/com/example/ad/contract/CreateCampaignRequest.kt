package com.example.ad.contract

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

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
    val campaignType: String,
)
