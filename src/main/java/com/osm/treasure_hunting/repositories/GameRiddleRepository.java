package com.osm.treasure_hunting.repositories;

import com.osm.treasure_hunting.models.Game;
import com.osm.treasure_hunting.models.GameRiddle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRiddleRepository extends JpaRepository<GameRiddle, Long> {
    List<GameRiddle> findByGameAndSolvedFalse(Game game);

    List<GameRiddle> findByGame(Game gameSession);

}
