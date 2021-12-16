package br.com.alura.forum.model

object TopicoTest {
    fun build() = Topico(
        id = 1,
        titulo = "Kotlin Basico",
        mensagem = "Aprendendo kotlin basico",
        curso = Curso(nome = "Kotlin Basico", categoria = "Programacao"),
        autor = Usuario(nome = "Joao", email = "jvc.martins", password = "123")
    )
}