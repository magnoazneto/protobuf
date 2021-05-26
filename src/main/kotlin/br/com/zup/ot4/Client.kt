package br.com.zup.ot4

import br.com.zup.edu.Cargo
import br.com.zup.edu.FuncionarioRequest
import br.com.zup.edu.FuncionarioResponse
import br.com.zup.edu.FuncionarioServiceGrpc
import io.grpc.ManagedChannelBuilder

fun main() {

    val channel = ManagedChannelBuilder
        .forAddress("localhost", 50051)
        .usePlaintext()
        .build()

    val client = FuncionarioServiceGrpc.newBlockingStub(channel)

    val request = FuncionarioRequest.newBuilder()
        .setNome("D Martins")
        .setCpf("000.000.000-00")
        .setIdade(25)
        .setSalario(5000.0)
        .setAtivo(true)
        .setCargo(Cargo.QA)
        .addEnderecos(
            FuncionarioRequest.Endereco.newBuilder()
            .setLogradouro("Rua dos num sei o que")
            .setCep("00000-000")
            .setComplemento("Apt 1306")
            .build()
        )
        .build()

    val response : FuncionarioResponse = client.cadastrar(request)
    println(response)
}