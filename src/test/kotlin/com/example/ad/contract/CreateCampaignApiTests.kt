package com.example.ad.contract

import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import java.net.URI

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class CreateCampaignApiTests {

    @LocalServerPort
    private var port: Int = 0

    @Autowired
    private lateinit var client: TestRestTemplate

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun `캠페인 생성에 필요한 입력 값이 NULL인 경우 400 에러가 발생한다`() {
        val headers = HttpHeaders().apply { contentType = MediaType.APPLICATION_JSON }
        val request: HttpEntity<String> = HttpEntity(null, headers)

        val response = client.postForEntity(
            "http://localhost:${port}${CreateCampaignApi.PATH}",
            request,
            String::class.java
        )

        val errorResult = objectMapper.writeValueAsString(
            ProblemDetails.forNullInput().apply {
                instance = URI(CreateCampaignApi.PATH)
            }
        )
        assertThat(response.statusCode.value()).isEqualTo(HttpStatus.BAD_REQUEST.value())
        assertThat(response.body).isEqualTo(errorResult)
    }
}
