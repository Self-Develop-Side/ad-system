package com.example.ad

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class AppTests {

    @Test
    fun `CI 테스트`() {
        val a = 1
        assertThat(a).isEqualTo(1)
    }
}
