package com.osm.treasure_hunting.controllers;

import com.osm.treasure_hunting.DTO.*;
import com.osm.treasure_hunting.models.Game;
import com.osm.treasure_hunting.models.GameDifficulty;
import com.osm.treasure_hunting.models.Riddle;
import com.osm.treasure_hunting.models.User;
import com.osm.treasure_hunting.services.GameService;
import com.osm.treasure_hunting.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class GameController {

    private final GameService gameSessionService;

    private final UserService userService;

    @PostMapping("/start")
    public GameSessionDTO startGame(@RequestBody StartGameDTO startGameDTO) {
        User user = userService.getUser(startGameDTO.getUsername());
        Game game = gameSessionService.startGame(user, startGameDTO.getDifficulty());
        return GameSessionDTO.builder()
                .game_id(game.getId())
                .access_token(game.getId())
                .username(user.getUsername())
                .attempts_left(game.getAttemptsLeft())
                .difficulty(game.getDifficulty())
                .build();
    }

    @GetMapping("/{gameId}/riddle")
    public RiddleQuestionDTO getCurrentRiddle(@PathVariable Long gameId, @RequestHeader("Authorization") String accessToken) {
        Game session = gameSessionService.getGameSessionById(gameId);
        return RiddleQuestionDTO.builder().question(gameSessionService.getCurrentRiddle(session).getQuestion()).build();
    }

    @PostMapping("/{gameId}/answer")
    public AnswerValidationDTO submitAnswer(@PathVariable Long gameId, @RequestBody AnswerDTO answer,  @RequestHeader("Authorization") String accessToken) {
        Game session = gameSessionService.getGameSessionById(gameId);
        boolean solved = gameSessionService.validateAnswer(session, answer.getLatitude(), answer.getLongitude());
        return AnswerValidationDTO.builder().status(session.getStatus()).solved(solved).attempts_left(session.getAttemptsLeft()).build();

    }

    @GetMapping("/history")
    public ResponseEntity<List<GameHistoryDTO>> getGameHistory(@RequestParam String username) {
        User user = userService.getUser(username);
        return ResponseEntity.ok(gameSessionService.getGameHistory(user));
    }

    @DeleteMapping("/{gameId}")
    public DeletedDTO deleteGame(@PathVariable Long gameId) {
        gameSessionService.deleteGameSession(gameId);
        return DeletedDTO.builder().message("Game: "+ gameId+ " Deleted").build();
    }

    @PostMapping("/{gameId}/abandon")
    public DeletedDTO abandonGame(@PathVariable Long gameId) {
        gameSessionService.abandonGameSession(gameId);
        return DeletedDTO.builder().message("Game: "+gameId+" Abandoned").build();
    }
}


