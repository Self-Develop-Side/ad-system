package com.example.ad.contract

import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UpdateCampaignApi {

    @PutMapping("/api/v1/campaigns/contract")
    fun updateCampaign(@RequestBody @Valid request: UpdateCampaignRequest): ResponseEntity<Any> {
        return ResponseEntity.ok().build()
    }
}
