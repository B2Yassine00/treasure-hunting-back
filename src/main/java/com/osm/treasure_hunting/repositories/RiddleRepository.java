package com.osm.treasure_hunting.repositories;

import com.osm.treasure_hunting.models.Riddle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RiddleRepository extends JpaRepository<Riddle, Long> {

}
