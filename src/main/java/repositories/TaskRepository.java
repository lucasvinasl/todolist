package repositories;

import model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByUser_Id(Long id);

//    @Query(value = "SELECT t FROM Task t WHERE t.user.id = :id")
//    List<Task> findByUser_Id(@Param("id") Long id);

//    @Query(value = "SELECT * FROM task t WHERE t.user_id = :id", nativeQuery = true)
//    List<Task> findByUser_Id(@Param("id") Long id);

}

/*
    Query Derivada:
        JPA gera automaticamente a consulta SQL pelo metodo repassado:
            List<Task> findByUser_Id(Long id);
        Em JPQL seria:
            SELECT t FROM Task t WHERE t.user.id = :id
        Em SQL seria:
            SELECT * FROM task WHERE user_id = ?;

    Query JPQL:
        @Query(value = "SELECT t FROM Task t WHERE t.user.id = :id")
        List<Task> findByUser_Id(@Param("id") Long id);

        JPQL é uma linguagem baseada em Objetos e não em Tabelas.

        Task t instancia um objeto da entidade Task e busca tasks associadas ao id: t.user.id

        Em SQL seria:
            SELECT * FROM task WHERE user_id = ?;


    Query Nativa SQL:
        @Query(value = "SELECT * FROM task t WHERE t.user_id = :id", nativeQuery = true)
        List<Task> findByUser_Id(@Param("id") Long id);

        Query pura em SQL:
            Diferente do JPQL eu não referencio o Objeto/Entidade Task mas sim a tabela task.

            Em SQL:
            SELECT * FROM task WHERE user_id = ?;



     Qual usar?
        Se a consulta for simples → Use a Query Derivada (findByUser_Id).
        Se precisar de mais controle, mas ainda quiser JPA → Use JPQL (@Query sem nativeQuery).
        Se precisar de máxima performance e controle total → Use SQL Nativo (@Query com nativeQuery = true).


 */
