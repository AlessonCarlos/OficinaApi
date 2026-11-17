package com.projeto.oficina_api.cliente;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projeto.oficina_api.cliente.dto.ClienteRequest;
import com.projeto.oficina_api.cliente.dto.ClienteResponse;
import com.projeto.oficina_api.cliente.dto.ClienteUpdateRequest;
import com.projeto.oficina_api.exception.CadastroNaoEncontradoException;
import com.projeto.oficina_api.exception.CpfCadastradoException;



@Service 
public class ClienteService {

    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository){
        this.repository = repository;
    }

    /*Ciração dos metodos do CRUD */


    /*Aqui está o metodo cadastrar */
    @Transactional 
    public ClienteResponse cadastrar(ClienteRequest request){

        String cpf = somenteNumeros(request.cpf());

        if (repository.existsByCpf(cpf)){
            throw new CpfCadastradoException();
        }

        Cliente cliente = new Cliente(

            request.nome().trim(),
            cpf,
            somenteNumeros(request.telefone()),
            normalizarEmail(request.email())
            
        );

        return ClienteResponse.from(repository.save(cliente));
        
    }

    /*Aqui está o metodo Listar */
     @Transactional(readOnly = true)
    public List<ClienteResponse> listar(){
        return repository.findAll()
                .stream()
                .map(ClienteResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public ClienteResponse buscarPorId(Long id) {
        return ClienteResponse.from(buscarEntidade(id));
    }

    /*Aqui está o metodo atualizar */
     @Transactional
    public ClienteResponse atualizar(Long id, ClienteUpdateRequest request) {
        Cliente cliente = buscarEntidade(id);

        cliente.atualizar(
            request.nome().trim(),
            somenteNumeros(request.telefone()),
            normalizarEmail(request.email())
        );

        return ClienteResponse.from(cliente);
    }

    /*Aqui está o metodo deletar */
     @Transactional
    public void deletar(Long id) {
        Cliente cliente = buscarEntidade(id);
        repository.delete(cliente);
    }

    /*Outros metodos */

    private Cliente buscarEntidade(Long id) {
        return repository.findById(id)
                    .orElseThrow( () -> new CadastroNaoEncontradoException("Cliente não encontrado"));
    }

    private String somenteNumeros(String valor){
        return valor.replaceAll("\\D", "");
    }

    private String normalizarEmail(String email){
        if (email == null || email.isBlank()) {
            return null;
        }
        return email.trim().toLowerCase();
    }



}
