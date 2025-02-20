package model;


import jakarta.persistence.*;

// Informar que é um entidade - O Hibernate irá gerenciar o CRUD
@Entity

// Definir o nome da tabela
@Table(name=User.TABLE_NAME)

public class User {

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

    @Column(name = "username", length = 255, nullable = false, unique = true)
    private String username;

    @Column(name ="password", length = 100, nullable = false)
    private String password;
}
