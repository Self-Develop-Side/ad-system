package com.example.ad.contract

object ValidationErrors {
    fun invalidCampaignType(): ValidationError {
        return ValidationError(
            "campaignType",
            "캠페인 타입이 올바르지 않습니다",
        )
    }

    fun invalidClientId(): ValidationError {
        return ValidationError(
            "clientId",
            "클라이언트 아이디가 올바르지 않습니다",
        )
    }
}
