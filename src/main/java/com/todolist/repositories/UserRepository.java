package com.todolist.repositories;

import com.todolist.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{

}


/*
@Repository ->
    Interface que faz a comunicação entre a aplicação e o banco de dados.

    Extende interfaces do JPA que já possue diversos métodos prontos em SQL

    extends JpaRepository<User, Long>:
        <Entidade, tipo do ID da entidade>
 */