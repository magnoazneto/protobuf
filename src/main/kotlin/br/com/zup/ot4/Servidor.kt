package br.com.zup.ot4

import br.com.zup.edu.FuncionarioRequest
import br.com.zup.edu.FuncionarioResponse
import br.com.zup.edu.FuncionarioServiceGrpc
import com.google.protobuf.Timestamp
import io.grpc.ServerBuilder
import io.grpc.stub.StreamObserver
import java.time.LocalDateTime
import java.time.ZoneId

fun main() {

    val server = ServerBuilder
        .forPort(50051)
        .addService(FuncionarioEndpoint())
        .build()

    server.start()
    server.awaitTermination()
}

class FuncionarioEndpoint: FuncionarioServiceGrpc.FuncionarioServiceImplBase() {

    override fun cadastrar(
        request: FuncionarioRequest?,
        responseObserver: StreamObserver<FuncionarioResponse>?
    ) {

        println("Cadastro chamado para request: $request")

        var nome: String? = request?.nome
        if(!request?.hasField(FuncionarioRequest.getDescriptor().findFieldByName("nome"))!!){
            nome = "[???]"
        }

        val instant = LocalDateTime.now().atZone(ZoneId.of("UTC")).toInstant()
        val criadoEm = Timestamp.newBuilder()
            .setSeconds(instant.epochSecond)
            .setNanos(instant.nano)
            .build()

        val response = FuncionarioResponse.newBuilder()
            .setNome(nome)
            .setCriadoEm(criadoEm)
            .build()

        println("Response: $response")

        responseObserver?.run {
            onNext(response)
            onCompleted()
        }
    }
}