package com.projeto.oficina_api.exception;

public class CadastroNaoEncontradoException extends RuntimeException{

    public CadastroNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

}
