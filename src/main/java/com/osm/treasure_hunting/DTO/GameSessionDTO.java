package com.osm.treasure_hunting.DTO;

import com.osm.treasure_hunting.models.GameDifficulty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameSessionDTO {

    private Long game_id;
    private String username;
    private Long access_token;
    private int attempts_left;
    private GameDifficulty difficulty;
}
