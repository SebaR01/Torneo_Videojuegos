package com.torneo.api.Models;

import com.torneo.api.enums.Role;
import jakarta.persistence.*;
import lombok.Data;

@Entity
//Anotación, proporcionada por JPA, que marca a la clase como una entidad, lo cual hace que sus atributos sean mapeables en la base de datos. Al ser una entidad, Spring ya toma a la clase como una tabla de la bdd y a sus atributos como los campos
@Table(name = "users")//Tu explicación es correcta, pero como ahora la clase User es real (con username, password, etc.), necesito que se guarde en una tabla propia, y no en torneos. Por eso uso @Table(name = 'users')
//@Table(name="torneos", schema="public") //Anotación, proporcionada por JPA, que me permite personalizar el nombre de la tabla en la base de datos. Si no pongo esta anotación, el nombre de la tabla será idéntico al nombre de la clase.
@Data

//Anotación, proporcionada por Lombok, que proporciona de manera automática métodos básicos: getter, setter, builder, equals, compareTo, toString y hasDat.
public class User
{
    @Id//Anotación, proporcionada por JPA, que marca al atributo de abajo como el Id. Es obligatorio esta anotación.
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Digo que es autoincremental
    private Long id;


    ///Completo la clase User agregando los campos necesarios para manejar la autenticación con Spring Security.
    ///  Necesitamos identificar a los usuarios, validar su contraseña y controlar qué acciones pueden hacer
    /// según su rol. Por eso agregamos username, password, email y role

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;
}


