package com.example.ad.contract

import org.springframework.stereotype.Repository
import java.util.*

@Repository
class InMemoryCampaignRepository : CampaignRepository {
    override fun save(campaign: Campaign) {
        storage[campaign.id] = campaign
    }

    companion object {
        private val storage: MutableMap<UUID, Campaign> = mutableMapOf()
    }
}
