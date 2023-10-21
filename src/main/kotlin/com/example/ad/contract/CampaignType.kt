package com.example.ad.contract

enum class CampaignType(val value: String) {
    PAID("유료"),
    IN_HOUSE("내부"),
    ;

    companion object {
        fun findByCampaignType(value: String): CampaignType {
            return entries.find { it.value == value }
                ?: throw InvalidCampaignTypeException()
        }
    }
}
