package com.osm.treasure_hunting.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RiddleHistoryDTO {
    private String question;
    private boolean solved;
    private int action_to_be_solved;
}
