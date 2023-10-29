package com.example.ad.contract

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.*
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class UpdateCampaignApiTests {

    @LocalServerPort
    private var port: Int = 0

    @Autowired
    private lateinit var client: TestRestTemplate

    @Test
    fun `캠페인 수정에 필요한 입력 값이 NULL인 경우 400 에러가 발생한다`() {
        val headers = HttpHeaders().apply { contentType = MediaType.APPLICATION_JSON }
        val request: HttpEntity<String> = HttpEntity(null, headers)

        val response = client.exchange(
            requestURI(),
            HttpMethod.PUT,
            request,
            String::class.java,
        )

        assertThat(response.statusCode.value()).isEqualTo(HttpStatus.BAD_REQUEST.value())
    }

    private fun requestURI() = "http://localhost:$port$PATH"

    companion object {
        private const val PATH = "/api/v1/campaigns/contract"
    }
}
