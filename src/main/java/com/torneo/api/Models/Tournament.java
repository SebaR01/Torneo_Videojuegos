package com.torneo.api.Models;

import ENUMS.GamesCategory;
import ENUMS.GamesState;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity //Anotación, proporcionada por JPA, que marca a la clase como una entidad, lo cual hace que sus atributos sean mapeables en la base de datos. Al ser una entidad, Spring ya toma a la clase como una tabla de la bdd y a sus atributos como los campos
@Table(name="torneos", schema="public") //Anotación, proporcionada por JPA, que me permite personalizar el nombre de la tabla en la base de datos. Si no pongo esta anotación, el nombre de la tabla será idéntico al nombre de la clase.
@Data//Anotación, proporcionada por Lombok, que proporciona de manera automática métodos básicos: getter, setter, builder, equals, compareTo, toString y hasDat.
public class Tournament
{
    @Id//Anotación, proporcionada por JPA, que marca al atributo de abajo como el Id. Es obligatorio esta anotación.
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Digo que es autoincremental
    private Long id;

    @NotNull(message = "name atribute is not a null camp. You must write something.")
    @Size(max = 100, message = "Size tournament name cannot have got more than one hundred chars.")
    private String name;

    @NotNull(message = "game atribute is not a null camp.")
    @Size(max = 50, message = "game name must have got 50 chars or less.")
    private String game;

    @Enumerated(EnumType.STRING) //La anotación @Enumerated, proporcionada por JPA, especifica la forma en la que la variable de tipo ENUM se mapeará en el mapa. En este caso, marqué que se guarde por el String (name).
    private GamesCategory gamesCategory;

    @Enumerated(EnumType.STRING)
    private GamesState gamesState;

    @ManyToOne //Un torneo tiene un solo organizador; pero un organizador, puede tener muchos torneos.
    @JoinColumn(name = "id")
    private User organizerId;

}
