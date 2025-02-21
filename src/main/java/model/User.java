package model;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.scheduling.config.Task;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


// Informar que é um entidade - O Hibernate irá gerenciar o CRUD
@Entity

// Definir o nome da tabela
@Table(name=User.TABLE_NAME)

public class User {

    public interface createUser{}

    public interface attUser{}

    // Definir o nome da tabela de usuários
    public static final String TABLE_NAME = "userTb";

    /*
    É interessante usar como classe, porque aceita nulo também
    @Id - informo que é um id
    @GeneratedValue(strategy = GenerationType.IDENTITY) - como se fosse o autoincrement do ID
    @Column(name = "id", unique = true) - vai fazer a mesma coisa do GeneratedValue pra garantir
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


    private List<Task> tasks = new ArrayList<>();


    public User(){

    }

    public User(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        User user = (User) object;
        return Objects.equals(id, user.id) && Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(tasks, user.tasks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, tasks);
    }
}
