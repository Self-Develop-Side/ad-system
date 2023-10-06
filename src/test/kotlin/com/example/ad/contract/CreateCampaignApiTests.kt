package com.example.ad.contract

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class CreateCampaignApiTests {

    @LocalServerPort
    private var port: Int = 0

    @Autowired
    private lateinit var client: TestRestTemplate

    @Test
    fun `캠페인 생성에 필요한 입력 값이 NULL인 경우 400 에러가 발생한다`() {
        val response = client.postForEntity(
            "http://localhost:$port/api/v1/campaigns/contract",
            null,
            String::class.java
        )

        assertThat(response.statusCode.value()).isEqualTo(HttpStatus.BAD_REQUEST.value())
    }
}
