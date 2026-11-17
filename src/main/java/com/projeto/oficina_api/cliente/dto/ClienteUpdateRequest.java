package com.projeto.oficina_api.cliente.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ClienteUpdateRequest(
    @NotBlank (message = "nome é Obrigatorio")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    String nome,

    @NotBlank(message = "Telefone é obrigatorio")
    @Pattern (regexp = "^\\(?\\d{2}\\)?\\s?\\d{4,5}-?\\d{4}$",
        message = "Telefone inválido. Exemplo: (81) 99999-9999")
    String telefone,

    @Email(message = "Email invalido")
    @Size(max = 120, message = "Email deve ter no maximo 120 caracteres")
    String email
) {


}
