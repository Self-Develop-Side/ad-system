package com.example.ad.contract

interface CampaignRepository {
    fun save(campaign: Campaign): Campaign
}
