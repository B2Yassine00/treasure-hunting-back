package com.osm.treasure_hunting.DTO;

import com.osm.treasure_hunting.models.GameStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameHistoryDTO {
    private Long gameId;
    private GameStatus status;
    private int attemptsLeft;
    private List<RiddleHistoryDTO> riddles;
}
