package com.projeto.oficina_api.exception;

public class CpfCadastradoException extends RuntimeException{

    public CpfCadastradoException() {
        super("Já existe um cliente cadastrado com esse CPF");
    }

}
