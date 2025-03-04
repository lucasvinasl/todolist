package com.todolist.model;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity
@Table(name = Task.TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Task {

    public static final String TABLE_NAME = "taskTb";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    /*@ManyToOne:
    * Relação N:1 -> N tarefas estarão associadas 1 usuário, ou, 1 Usuário tem N Terefas
    */

    /* @JoinColumn(name = "user_id", nullable = false, updatable = false)->
        Especifica a chave estrangeira que liga a tabela Task à tabela User
        user_id será a coluna que armazenará essa chave

        nullable = false, updatable = false ->
            Não será nula e nem será alterada.

    */
    @Column(name = "description", length = 255, nullable = false)
    @NotNull
    @NotEmpty
    @Size(min = 1, max =  255)
    private String description;


}
