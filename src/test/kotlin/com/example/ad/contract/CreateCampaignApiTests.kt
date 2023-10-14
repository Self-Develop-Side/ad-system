package com.example.ad.contract

import com.fasterxml.jackson.databind.ObjectMapper
import net.javacrumbs.jsonunit.assertj.assertThatJson
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EmptySource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.*
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
            requestURI(),
            request,
            String::class.java,
        )

        assertThat(response.statusCode.value()).isEqualTo(HttpStatus.BAD_REQUEST.value())
        assertThat(response.body).isEqualTo(expectedProblemDetail(ProblemDetails.forUnknownInput()))
    }

    @ParameterizedTest
    @EmptySource
    fun `캠페인 생성에 필요한 입력 값이 유효하지 않은 경우 400 에러가 발생한다`(value: String) {
        val request = CreateCampaignRequest(value, value, value, value)

        val response = client.postForEntity(
            requestURI(),
            request,
            String::class.java,
        )

        assertThat(response.statusCode.value()).isEqualTo(HttpStatus.BAD_REQUEST.value())
        assertThatJson(response.body!!) {
            node("detail").isEqualTo(ErrorMessage.NOT_VALID_INPUT.value)
            node("instance").isEqualTo(PATH)
            node("validationErrors").isArray.size().isEqualTo(7)
        }
    }

    private fun requestURI() = "http://localhost:$port$PATH"

    private fun expectedProblemDetail(problemDetail: ProblemDetail): String {
        val expected = objectMapper.writeValueAsString(
            problemDetail.apply {
                instance = URI(PATH)
            },
        )
        return expected
    }

    companion object {
        private const val PATH = "/api/v1/campaigns/contract"
    }
}
