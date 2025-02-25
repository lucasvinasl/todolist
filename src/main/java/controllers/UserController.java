package controllers;

import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import services.UserServices;
import javax.validation.Valid;
import java.net.URI;

// Identificando um Controller para arquitetura Rest:
// Recebe requisições HTTP, processa e retorna uma resposta
@RestController

// Criando o endpoint/rota do controller -> todos os endpoints vão ter esse prefixo.
@RequestMapping("/user")

// Valida os dados que recebe do front: Vai validar anotações como NotNull, NotEmpty, Size
// Essas anotações foram repassadas lá nos métodos da classe User
@Validated
public class UserController {

    // Injeta uma instância da classe UserServices
    @Autowired
    private UserServices userServices;


    //localhost:8080/user/id
    @GetMapping("/{id}")
    //Vai aplicar o verbo Get no metedo e buscar pelo id repassado
    public ResponseEntity<User> byId(@PathVariable Long id){
        User user = this.userServices.findUserById(id);
        return ResponseEntity.ok().body(user);
    }

    /*
    @GetMapping("/{id}"): Define que será um Get e recebe o id como parametro.
    ResponseEntity -> Entidade de Resposta HTTP, ela vai retornar se a minha
    requisição deu certo:
        Vai criar o usuário e retornar 201:
        return ResponseEntity.created(uri).build();

        Vai deletar o usuário e retornar 204 sem conteúdo:
        return ResponseEntity.noContent().build();

    userServices.findUserById(id) -> usa o metodo já do Services
     */

    @PostMapping
    @Validated(User.createUser.class)
    public ResponseEntity<Void> create(@Valid @RequestBody User user){
        this.userServices.createUser(user);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }


    @PutMapping("/{id}")
    @Validated(User.attUser.class)
    public ResponseEntity<Void> update(@Valid @RequestBody User user, @PathVariable Long id){
        user.setId(id);
        user = this.userServices.updateUser(user);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){

        this.userServices.deleteUser(id);
        return ResponseEntity.noContent().build();

    }








}
/*
    Controller:

    É a camada da aplicação / endpoint que o front se comunica com a aplicação.
    O controller comunica com o services
    O services comunica com o repository
 */
