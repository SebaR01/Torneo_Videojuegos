package com.torneo.api.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "Torneos", schema = "public")
public class Torneo
{
}
