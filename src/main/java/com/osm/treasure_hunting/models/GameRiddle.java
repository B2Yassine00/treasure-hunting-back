package com.osm.treasure_hunting.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameRiddle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @ManyToOne
    @JoinColumn(name = "riddle_id", nullable = false)
    private Riddle riddle;

    @Column(nullable = false, columnDefinition = "INTEGER DEFAULT 0")
    private int action_to_be_solved = 0;

    private boolean solved;
}

