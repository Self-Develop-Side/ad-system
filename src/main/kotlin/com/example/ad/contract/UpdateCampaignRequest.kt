package com.example.ad.contract

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class UpdateCampaignRequest(
    @field:NotBlank
    @field:Size(min = 25, max = 50)
    val name: String,
    @field:NotBlank
    val campaignType: String,
)
