package com.torneo.api.repository;

import com.torneo.api.models.TeamXPlayer;
import jdk.dynalink.linker.LinkerServices;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TeamXPlayerRepository extends JpaRepository<TeamXPlayer, Long>
{
}
