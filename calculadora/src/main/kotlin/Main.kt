fun main() {
    println("=== CALCULADORA ===")
    print("Digite a expressao: ")
    val entrada = readLine() ?: ""

    if (entrada.replace(" ", "").length < 9) {
        println("Expressao muito curta! Digite pelo menos 9 caracteres.")
        return
    }

    // Separa os números e operadores
    val partes = entrada.trim().split(" ")

    var resultado = partes[0].toDouble()
    var i = 1

    while (i < partes.size) {
        val operador = partes[i]
        val numero   = partes[i + 1].toDouble()

        if (operador == "+") resultado += numero
        if (operador == "-") resultado -= numero
        if (operador == "*") resultado *= numero
        if (operador == "/") resultado /= numero

        i += 2
    }

    println("Resultado: $resultado")
}
 