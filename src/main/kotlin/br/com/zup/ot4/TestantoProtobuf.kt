package br.com.zup.ot4

import br.com.zup.edu.Cargo
import br.com.zup.edu.FuncionarioRequest
import java.io.FileInputStream
import java.io.FileOutputStream

fun main() {

    val request = FuncionarioRequest.newBuilder()
        .setNome("D Martins")
        .setCpf("000.000.000-00")
        .setSalario(5000.0)
        .setAtivo(true)
        .setCargo(Cargo.QA)
        .addEnderecos(FuncionarioRequest.Endereco.newBuilder()
                    .setLogradouro("Rua dos num sei o que")
                    .setCep("00000-000")
                    .setComplemento("Apt 1306")
                    .build()
        )
        .build()

    println(request)
    request.writeTo(FileOutputStream("funcionario-request.bin"))

    val request2 = FuncionarioRequest.newBuilder()
        .mergeFrom(FileInputStream("funcionario-request.bin"))

    request2.setCargo(Cargo.GERENTE).build()

    println(request2)
}