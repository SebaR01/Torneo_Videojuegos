package com.torneo.api.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "resultados", schema = "public")
@Data
public class Resultado
{
}
