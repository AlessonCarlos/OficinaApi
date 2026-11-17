package com.projeto.oficina_api.cliente.dto;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ClienteRequest(

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    String nome,

    @NotBlank(message = "CPF é obrigatorio")
    @CPF(message = "CPF infavlido")
    String cpf,

    @NotBlank(message = "Telefone é obrigatorio")
    @Pattern (regexp = "^\\(?\\d{2}\\)?\\s?\\d{4,5}-?\\d{4}$",
                message = "Telefone inválido"
    )
    String telefone,

    @Email(message = "Emai invalido")
    @Size(max = 120, message = "Emai deve ter no maximo 120 caracteres")
    String email



    
) {

}
