package com.example.ad.contract

import java.util.UUID

class Campaign(
        val id: UUID,
        val clientId: ClientId,
        val name: String,
        val createdBy: String,
        val campaignType: CampaignType,
        val status: CampaignStatus = CampaignStatus.SWITCHED_OFF,
) {
    constructor(
            clientId: ClientId,
            name: String,
            createdBy: String,
            campaignType: CampaignType,
            status: CampaignStatus = CampaignStatus.SWITCHED_OFF
    ) : this(UUID.fromString("invalid-uuid"), clientId, name, createdBy, campaignType, status)
}
