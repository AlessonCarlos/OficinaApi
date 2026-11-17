package com.projeto.oficina_api.cliente.dto;

import java.time.LocalDateTime;

import com.projeto.oficina_api.cliente.Cliente;

public record ClienteResponse(

    Long id,
    String nome,
    String cpf,
    String telefone,
    String email,
    LocalDateTime dataCadastro
) {

    public static ClienteResponse from(Cliente cliente){
        return new ClienteResponse(
                cliente.getId(),
                cliente.getNome(),
                mascararCpf(cliente.getCpf()),
                cliente.gettelefone(),
                cliente.getEmail(),
                cliente.getDataCadastro()
     );

    }

    private static String mascararCpf(String cpf){
        return "***." + cpf.substring(3, 6) + "." + cpf.substring(6, 9) + "-**";
    }
    




}
