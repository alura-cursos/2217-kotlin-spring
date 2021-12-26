package br.com.alura.forum.controller

import br.com.alura.forum.config.JWTUtil
import br.com.alura.forum.model.Role
import br.com.alura.forum.model.UsuarioTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TopicoControllerTest {

    @Autowired
    private lateinit var webApplicationContext: WebApplicationContext

    @Autowired
    private lateinit var jwtUtil: JWTUtil

    private lateinit var mockMvc: MockMvc

    private var jwt: String? = null

    @BeforeEach
    fun setup() {
        jwt = generateToken()
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .apply<DefaultMockMvcBuilder?>(SecurityMockMvcConfigurers
                .springSecurity()).build()
    }

    companion object {
        private const val TOKEN = "%s"
        private const val URI = "/topicos"
        private const val URI_WITH_PARAM = URI.plus("/%s")
    }

    @Test
    fun `deve retornar codigo 400 quando chamar topicos sem autenticacao`() {
        mockMvc.get(URI).andExpect { status { is4xxClientError() } }
    }

    @Test
    fun `deve retornar codigo 200 quando chamar topicos e usuario estiver autenticado`() {
        mockMvc.get(URI) {
            headers { this.setBearerAuth(TOKEN.format(jwt)) }
        }.andExpect { status { isOk() } }
    }

    @Test
    fun `deve retornar codigo 200 quando chamar topicos por id e usuario estiver autenticado`() {
        mockMvc.get(URI_WITH_PARAM.format("1")) {
            headers { this.setBearerAuth(TOKEN.format(jwt)) }
        }.andExpect { status { isOk() } }
    }

    private fun generateToken(): String? {
        val authorities = mutableListOf(Role(1, "LEITURA_ESCRITA"))
        val usuario = UsuarioTest.buildToToken()

        return jwtUtil.generateToken(usuario.email, authorities)
    }
}