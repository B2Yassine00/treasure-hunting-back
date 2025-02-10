package com.osm.treasure_hunting.repositories;

import com.osm.treasure_hunting.models.Game;
import com.osm.treasure_hunting.models.GameStatus;
import com.osm.treasure_hunting.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNullApi;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game,Long> {
    List<Game> findByUserAndStatus(User user, GameStatus status);

    List<Game> findByUser(User user);

}
