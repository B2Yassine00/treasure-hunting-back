package com.osm.treasure_hunting.DTO;

import com.osm.treasure_hunting.models.GameDifficulty;
import lombok.*;

@Data
@RequiredArgsConstructor
public class StartGameDTO {

    private String username;
    private GameDifficulty difficulty;

}
