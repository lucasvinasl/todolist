package com.todolist.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/*

    Essa classe serve para padronizar as respostas de erro da API.

    Ela encapsula detalhes do erro que será enviado ao cliente (front-end, app mobile, etc.)
    em formato JSON, ao invés de retornar mensagens internas e perigosas de exceção que o Spring/JVM
    retornaria por padrão.

    Essa classe vai ser responsável por tratar os erros ou exceptions que a API retorna.

    "Erro" não significa que é um problema na API, mas é o trace/resposta que ela repassa
    caso não consiga dar a resposta que foi solicitada.

    Por exemplo, se tentar cadastrar um cliente com mesmo usuario que já existe,
    vai retornar uma exception.

    É importante que essa exception que a API retorne seja tratada e não repassada tal qual,
    pois pode conter diversas informações do sistema.

    Além disso, a API pode repassar uma informação que não é condizente com a verdade da
    regra de negócio, por exemplo:
        Se tentar criar um usuário com o username que já existe,
        A API provavelmente vai retornar um erro 500 - lado servidor,
        mas não é, é um erro 400 - lado cliente, pois está tentando cadastrar um user
        que já existe.

 */
@Getter
@Setter
// Gera um construtor com os atributos final
@RequiredArgsConstructor
// Exclui campos nulos do JSON de resposta
// Se Strin stackTrace ou List erros forem nulos, não aparecem no JSON
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    // Vai retornar o StatusCode da requisição
    // A mensagem do erro que eu quero retornar
    // Retornar a trace;
    // Lista dos erros;
    private final int status;
    private final String message;
    private String stackTrace;
    private List<ValidationErro> erros;

    @Getter
    @Setter
    @RequiredArgsConstructor
    // Representa um erro em um campo específico
    // Aqui será detalhado os campos com erro que não passaram na validação:
    // Se eu for criar um usuario por exemplo, tenho que repassar usuario e senha, ambos os campos podem
    // gerar algum erro.
    private static class ValidationErro{
        private final String field;
        private final String message;
    }

    // Verifica se a lista de erros está vaiza.
    // Cria a lista de erros.
    // Adiciona um erro a partir da estrutura ValidationErro, ou seja,
    // Cada erro ira informar o campo do erro e uma mensagem referente ao erro.

    public void addValidationError(String field, String message){
        if(Objects.isNull(erros)){
            this.erros = new ArrayList<>();
        }
        this.erros.add(new ValidationErro( field, message));
    }


}
