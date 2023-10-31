package com.example.ad.contract

import org.springframework.stereotype.Repository
import java.util.*

@Repository
class InMemoryCampaignRepository : CampaignRepository {
    override fun save(campaign: Campaign): Campaign {
        val entity = Campaign(
                id = UUID.randomUUID(),
                clientId = campaign.clientId,
                name = campaign.name,
                createdBy = campaign.createdBy,
                campaignType = campaign.campaignType
        )
        storage[entity.id] = entity
        return entity
    }

    companion object {
        private val storage: MutableMap<UUID, Campaign> = mutableMapOf()
    }
}
