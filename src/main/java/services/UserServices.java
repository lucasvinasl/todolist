package services;

import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repositories.UserRepository;
import java.util.Optional;

@Service
public class UserServices {


    @Autowired
    private UserRepository userRepository;


    public User findUserById(Long id){
        Optional<User> user = this.userRepository.findById(id);
        return user.orElseThrow(() -> new RuntimeException(
                "Usuário não encontrado - ID: "+id + "Tipo: "+User.class.getName()
        ));
    }

    @Transactional
    public User createUser(User user){
        user.setId(null);
        user = this.userRepository.save(user);
        return user;
    }

    @Transactional
    public User updateUser(User user){
        User newUser = findUserById(user.getId());
        newUser.setPassword(user.getPassword());
        return this.userRepository.save(newUser);
    }


    public void deleteUser(Long id){
        findUserById(id);

        try{

            this.userRepository.delete(findUserById(id));

        }catch (Exception e){
            throw new RuntimeException("Não é Possível Excluir o Usuário. Há Tarefas Relacionadas.");
        }
    }

}

/*
    @Service:
        Indica que a classe é possui regras de negócio, ou seja,
        realiza operações e funções dentro da aplicação.


        O Service geralmente está entre a camada de Controle(Controllers)
        e a camada de de Dados (Repository).

   @Autowired
        Injeta automaticamente as depedências, ou seja,
        Um objeto irá receber as depedências de outro objeto.
        Nesse caso, eu repasso ao UserServices todos os métodos e construtores
        de UserRepository e TaskRepository, garantindo a persistÊncia dos métodos.

-------------------

    public User findUserById(Long id){
        Optional<User> user = this.userRepository.findById(id);
        return user.orElseThrow(() -> new RuntimeException(
                "Usuário não encontrado - ID: "+id + "Tipo: "+User.class.getName()
        ));
    }

    Retorna o usuário pelo seu Id.
    Esse metodo cria um objeto User que recebe o retorno o metodo findById criado em UserRepository;
    Opcional:
        Se o objeto existe, retorna através do findById.
        Se não existe, lança a exceção.

------------------

    @Transactional
    public User createUser(User user){
        user.setId(null);
        user = this.userRepository.save(user);

        return user;
    }

    @Transactional:
        Garante que a operação seja feita de forma única e caso ocorra algum erro,
        tudo possa ser revertido mantendo a integridade dos dados.

    Cria o metodo de Criar o Usuario.

    user.setId(null):
    reseta o id, caso exista, e não permita duplicidade.

    user = this.userRepository.save(user):
    chama o metodo da persistencia do userRepository para salvar o usuario


----------------------
    @Transactional
    public User updateUser(User user){
        User newUser = findUserById(user.getId());
        newUser.setPassword(user.getPassword());
        return this.userRepository.save(newUser);
    }


    Atualiza o usuário:

        User newUser = findUserById(user.getId()):
        Cria um novo usuário a partir do usuário existente que foi repassado.
        Se não existe, lança a exceção da própria classe.

        newUser.setPassword(user.getPassword()):
        esse novo usuário só vai conseguir atualizar a informação de senha.
        user.getPassword já foi implementado pelo JpaRepository

        return this.userRepository.save(newUser):
        Salva o novo usuario persistindo o userRepository.save
        Como o id em momento nenhum foi alterado, apenas a informação de senha é sobreescrita.



------------------------------

    public void deleteUser(Long id){
        findUserById(id);

        try{

            this.userRepository.delete(findUserById(id));

        }catch (Exception e){
            throw new RuntimeException("Não é Possível Excluir o Usuário. Há Tarefas Relacionadas.");
        }
    }

    findUserById(id);
    Localiza o usuário. Se não existir, lança a exceção da propria classe.

    persiste o metodo delete do JPA do userRepository.

    se acontecer alguma exceção, no caso desse usuario extiver vinculado a tarefas, joga a mensagem.

 */
