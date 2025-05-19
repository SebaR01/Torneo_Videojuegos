package com.torneo.api.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
//Anotación, proporcionada por JPA, que marca a la clase como una entidad, lo cual hace que sus atributos sean mapeables en la base de datos. Al ser una entidad, Spring ya toma a la clase como una tabla de la bdd y a sus atributos como los campos
@Table(name="tournaments", schema="public") //Anotación, proporcionada por JPA, que me permite personalizar el nombre de la tabla en la base de datos. Si no pongo esta anotación, el nombre de la tabla será idéntico al nombre de la clase.
@Data//Anotación, proporcionada por Lombok, que proporciona de manera automática métodos básicos: getter, setter, builder, equals, compareTo, toString y hasDat.
public class Team
{
    @Id//Anotación, proporcionada por JPA, que marca al atributo de abajo como el Id. Es obligatorio esta anotación.
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Digo que es autoincremental
    private Long id;

}
