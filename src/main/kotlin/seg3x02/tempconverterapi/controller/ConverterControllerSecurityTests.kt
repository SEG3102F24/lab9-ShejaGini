package seg3x02.tempconverterapi.controller

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class ConverterControllerSecurityTests {

    @Autowired private lateinit var mockMvc: MockMvc

    @Test
    fun `should allow access with valid credentials`() {
        mockMvc.perform(
                        get("/temperature-converter/celsius-fahrenheit/20")
                                .with(httpBasic("user1", "pass1"))
                )
                .andExpect(status().isOk)
    }

    @Test
    fun `should deny access with invalid credentials`() {
        mockMvc.perform(
                        get("/temperature-converter/celsius-fahrenheit/20")
                                .with(httpBasic("user1", "wrongpass"))
                )
                .andExpect(status().isUnauthorized)
    }

    @Test
    fun `should deny access without credentials`() {
        mockMvc.perform(get("/temperature-converter/celsius-fahrenheit/20"))
                .andExpect(status().isUnauthorized)
    }

    @Test
    fun `should allow access to second user with valid credentials`() {
        mockMvc.perform(
                        get("/temperature-converter/fahrenheit-celsius/68")
                                .with(httpBasic("user2", "pass2"))
                )
                .andExpect(status().isOk)
    }
}
