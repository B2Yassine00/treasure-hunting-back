package com.osm.treasure_hunting.DTO;

import com.osm.treasure_hunting.models.GameStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnswerValidationDTO {

    private boolean solved;
    private int attempts_left;
    private GameStatus status;
}
