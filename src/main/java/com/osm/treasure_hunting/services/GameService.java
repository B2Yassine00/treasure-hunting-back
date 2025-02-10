package com.osm.treasure_hunting.services;

import com.osm.treasure_hunting.DTO.GameHistoryDTO;
import com.osm.treasure_hunting.DTO.RiddleHistoryDTO;
import com.osm.treasure_hunting.models.*;
import com.osm.treasure_hunting.repositories.GameRepository;
import com.osm.treasure_hunting.repositories.GameRiddleRepository;
import com.osm.treasure_hunting.repositories.RiddleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameSessionRepository;

    private final GameRiddleRepository gameRiddleRepository;

    private final RiddleRepository riddleRepository;

    public void deleteGameSession(Long gameId) {
        gameSessionRepository.deleteById(gameId);
    }

    public void abandonGameSession(Long gameId) {
        Optional<Game> gameSessionOpt = gameSessionRepository.findById(gameId);
        gameSessionOpt.ifPresent(session -> {
            session.setStatus(GameStatus.FAILED);
            gameSessionRepository.save(session);
        });
    }

    public List<GameHistoryDTO> getGameHistory(User user) {
        List<Game> sessions = gameSessionRepository.findByUser(user);
        return sessions.stream().map(session -> new GameHistoryDTO(
                session.getId(),
                session.getStatus(),
                session.getAttemptsLeft(),
                gameRiddleRepository.findByGame(session).stream()
                        .map(riddle -> new RiddleHistoryDTO(
                                riddle.getRiddle().getQuestion(),
                                riddle.isSolved(),
                                riddle.getAction_to_be_solved()
                        ))
                        .collect(Collectors.toList())
        )).collect(Collectors.toList());
    }

    public Game getGameSessionById(Long gameId) {
        return gameSessionRepository.findById(gameId).orElseThrow(() -> new RuntimeException("Game session not found"));
    }
    public Game startGame(User user, GameDifficulty difficulty) {
        List<Riddle> selectedRiddles = getRandomRiddles(difficulty);
        Game session = Game.builder().user(user).difficulty(difficulty).status(GameStatus.IN_PROGRESS)
                .attemptsLeft(difficulty == GameDifficulty.EASY ? 10 :
                difficulty == GameDifficulty.MEDIUM ? 7 : 5)
                .build();

        Game game = gameSessionRepository.save(session);

        List<GameRiddle> gameRiddles = selectedRiddles.stream()
                .map(riddle -> GameRiddle.builder().game(game).riddle(riddle).solved(false).build())
                .toList();

        gameRiddleRepository.saveAll(gameRiddles);
        return session;
    }

    public Riddle getCurrentRiddle(Game session) {
        return gameRiddleRepository.findByGameAndSolvedFalse(session)
                .stream().findFirst()
                .map(GameRiddle::getRiddle)
                .orElse(null);
    }

    public boolean validateAnswer(Game session, double userLat, double userLng) {
        GameRiddle currentGameRiddle = gameRiddleRepository.findByGameAndSolvedFalse(session)
                .stream().findFirst()
                .orElse(null);

        if (currentGameRiddle == null) return false;

        Riddle currentRiddle = currentGameRiddle.getRiddle();
        double distance = calculateDistance(currentRiddle.getLatitude(), currentRiddle.getLongitude(), userLat, userLng);
        currentGameRiddle.setAction_to_be_solved(currentGameRiddle.getAction_to_be_solved()+1);
        if (distance < 0.1) { // Correct if within 100 meters
            currentGameRiddle.setSolved(true);
            gameRiddleRepository.save(currentGameRiddle);

            if (getCurrentRiddle(session) == null) {
                session.setStatus(GameStatus.COMPLETED);
            }

            gameSessionRepository.save(session);
            return true;
        }

        session.setAttemptsLeft(session.getAttemptsLeft() - 1);
        if (session.getAttemptsLeft() == 0) {
            session.setStatus(GameStatus.FAILED);
        }

        gameSessionRepository.save(session);
        return false;
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Radius of the earth in km
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

    private List<Riddle> getRandomRiddles(GameDifficulty difficulty) {
        int count = difficulty == GameDifficulty.EASY ? 3 :
                difficulty == GameDifficulty.MEDIUM ? 4 : 5;
        return riddleRepository.findAll(PageRequest.of(0, count)).toList();
    }
}
