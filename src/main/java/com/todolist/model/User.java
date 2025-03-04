package com.todolist.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;



/*
 * Indica que esta classe representa uma entidade JPA/Hibernate,
 * ou seja, um objeto que será persistido no banco de dados.
 *
 */
@Entity

// Definir o nome da tabela a partir da constante TABLE_NAME
@Table(name=User.TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class User {

    /*
     * Essas interfaces são utilizadas como grupos de validação nas anotações @NotNull, @NotEmpty e @Size,
     * permitindo que certas restrições sejam aplicadas apenas em determinadas operações
     * (como criação ou atualização de usuário).
     * Isso permite que o username seja obrigatório apenas na criação do usuário
     * e não em atualizações.
     *
     * Isso permite que o password seja criado tanto na criação como na atualização de um user.
     */

    public interface createUser{}

    public interface attUser{}

    // Definir o nome da tabela de usuários
    public static final String TABLE_NAME = "userTb";

    /*
     * Definindo o primeiro atributo que será tratado como uma coluna na minha tabela
     * do Banco de Dados:
     *
     *
     *
     * @Id - Informa que será atribuído um ID à esse atributo:
     *      @Id define também que esse atributo será a chave primária da Entity
     *
     * @GeneratedValue(strategy = GenerationType.IDENTITY):
     *  Gera a Estratégia de Geração automática do ID e
     *  que o AUTO_INCREMENT de ID seja implementado
     *
     * @Column(name = "id", unique = true):
     * Esse atributo (Long id), será armazenado na Column id da tabela do banco e que devem ser únicos.
     *  É utilizado os class Wrapper: Manipula a variável como um objeto.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "username", length = 100, nullable = false, unique = true)
    @NotNull(groups = createUser.class)
    @NotEmpty(groups = createUser.class)
    @Size(groups = createUser.class, min = 2, max = 100)
    private String username;

    @JsonProperty(access =  JsonProperty.Access.WRITE_ONLY)
    @Column(name ="password", length = 100, nullable = false)
    @NotNull(groups = {createUser.class, attUser.class})
    @NotEmpty(groups = {createUser.class, attUser.class})
    @Size(groups = {createUser.class, attUser.class}, min = 8, max = 100)
    private String password;


    /*USERNAME:
     * @Column ->
     *      Para o usuário eu posso dizer que só aceita que seja unique visto que eu não posso ter
     *      usuários com mesmo username na minha base de dados(Sem valores duplicados).
     *      É uma regra de negócio.
     *
     * @NotNull ->
     *      Eu preciso que seja implementado a criação do USERNAME sempre que o usuário for criado.
     *      Ou seja, um contrato de implementação garantindo que não seja Nullo.
     *
     * @NotEmpty ->
     *      Reforça não pode ser em uma String em branco: "".
     * @Size ->
     *      Tamanho dos atributos.
     *
     *
     * @JsonProperty(access =  JsonProperty.Access.WRITE_ONLY):
     *      Garante que o PASSWORD não seja repassado como retorno do JSON e não vazar essa informação.
     *      Apenas metodo de escrita 'WRITE_ONLY'
     *
     */

    /* PASSWORD
     * @Column ->
     *      Mesma coisa do User, só que:
     *      Não há a necessidade de usar unique = true, visto que eu não tem essa validação de uma senha
     *      seja obrigatoriamente diferente da outra, os usuários não vão conflitar por conta de senha.
     *
     *
     * @NotNull e @NotEmpty com  groups = {createUser.class, attUser.class} ->
     *
     *      Garante que seja definida uma senha não nula e não vazia tanto na Criação como na Atualização do atributo.
     *      Uma regra de negócio padrão para senhas.
     *
     * @Size ->
     *      Tamanho da senha.
     *
     *
     * @JsonProperty(access =  JsonProperty.Access.WRITE_ONLY):     *
     *       Garante que o PASSWORD não seja repassado como retorno do JSON e não vazar essa informação.
     *       Apenas metodo de escrita 'WRITE_ONLY'
     */



    // A Lista de Atividades do Usuário
    @OneToMany(mappedBy = "user")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Task> taskList = new ArrayList<Task>();

    /*
        @OneToMany(mappedBy = "user") ->
            1 usuário pode ter Varias Tasks
            Quem mapeia/gerencia a classe Task é a variável User.
            O atributo "user_id" da tabela Task aponta para a tabela User através do atributo "user".
     */



/*
    *
    * Equals vai verificar objetos Users iguais quanto aos seus atributos
    * O hashCode vai atribuir um valor hash para cada objeto
    *
    *
    * O GPT Recomendou o seguinte:
    *
    * O equals() pode ser simplificado comparando apenas o id, já que id é único:
    * @Override
        public boolean equals(Object object) {
            if (this == object) return true;
            if (object == null || getClass() != object.getClass()) return false;
            User user = (User) object;
            return Objects.equals(id, user.id);
        }
    *Isso melhora a performance e evita problemas com listas
    * (tasks pode conter muitas referências, tornando a comparação mais lenta).
     */


}
