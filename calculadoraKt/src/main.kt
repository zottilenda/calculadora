fun main() {
    // Exibe o título da calculadora
    println("=== CALCULADORA ===")

    // Solicita ao usuário que digite uma expressão matemática
    print("Digite a expressao: ")

    // Lê a entrada do usuário (ou string vazia se for nulo)
    val entrada = readLine() ?: ""

    try {
        // Remove espaços e avalia a expressão
        val resultado = avaliar(entrada.replace(" ", ""))

        // Mostra o resultado
        println("Resultado: $resultado")
    } catch (e: Exception) {
        // Caso ocorra erro (ex: expressão inválida)
        println("Erro na expressão!")
    }
}

// Função principal que inicia a avaliação da expressão
fun avaliar(expr: String): Double {
    // Começa resolvendo parênteses
    return resolverParenteses(expr)
}

// Resolve expressões com parênteses de dentro para fora
fun resolverParenteses(expr: String): Double {
    var expressao = expr

    // Enquanto ainda houver parênteses
    while (expressao.contains("(")) {
        // Encontra o primeiro ')' (fechamento)
        val fim = expressao.indexOf(")")

        // Encontra o '(' correspondente mais próximo antes dele
        val inicio = expressao.substring(0, fim).lastIndexOf("(")

        // Extrai o conteúdo dentro dos parênteses
        val dentro = expressao.substring(inicio + 1, fim)

        // Resolve essa parte sem parênteses
        val valor = resolverSimples(dentro)

        // Substitui "(expressão)" pelo valor calculado
        expressao = expressao.substring(0, inicio) + valor + expressao.substring(fim + 1)
    }

    // Quando não houver mais parênteses, resolve normalmente
    return resolverSimples(expressao)
}

// Resolve expressão sem parênteses, respeitando precedência de operadores
fun resolverSimples(expr: String): Double {
    val numeros = mutableListOf<Double>()   // Lista de números
    val operadores = mutableListOf<Char>()  // Lista de operadores

    var numeroAtual = ""

    // Percorre cada caractere da expressão
    for (c in expr) {
        if (c.isDigit() || c == '.') {
            // Monta o número (ex: "12.5")
            numeroAtual += c
        } else {
            // Quando encontra operador, salva o número atual
            numeros.add(numeroAtual.toDouble())

            // Salva o operador
            operadores.add(c)

            // Reseta o número atual
            numeroAtual = ""
        }
    }

    // Adiciona o último número
    numeros.add(numeroAtual.toDouble())

    // ===== PRIMEIRA PASSAGEM: * e / =====
    var i = 0
    while (i < operadores.size) {
        if (operadores[i] == '*' || operadores[i] == '/') {
            // Realiza a operação
            val res = if (operadores[i] == '*')
                numeros[i] * numeros[i + 1]
            else
                numeros[i] / numeros[i + 1]

            // Substitui os dois números pelo resultado
            numeros[i] = res

            // Remove o número usado
            numeros.removeAt(i + 1)

            // Remove o operador usado
            operadores.removeAt(i)
        } else {
            i++
        }
    }

    // ===== SEGUNDA PASSAGEM: + e - =====
    var resultado = numeros[0]

    for (j in operadores.indices) {
        resultado = when (operadores[j]) {
            '+' -> resultado + numeros[j + 1]
            '-' -> resultado - numeros[j + 1]
            else -> resultado
        }
    }

    // Retorna o resultado final
    return resultado
}