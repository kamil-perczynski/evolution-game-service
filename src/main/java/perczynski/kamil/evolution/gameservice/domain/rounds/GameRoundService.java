package perczynski.kamil.evolution.gameservice.domain.rounds;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import perczynski.kamil.evolution.gameservice.domain.Player;
import perczynski.kamil.evolution.gameservice.domain.PlayerRepository;
import perczynski.kamil.evolution.gameservice.domain.bets.GameRound;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameRoundService {

    private final PlayerRepository playerRepository;
    private final GameRoundRepository gameRoundRepository;

    public GameRoundListing listGameRounds(String playerId) {
        log.info("Listing game rounds for player: {}", playerId);
        final Player player = playerRepository.find(playerId);

        final List<GameRound> rounds = gameRoundRepository.findByPlayerId(player.getPlayerId());
        return new GameRoundListing(rounds);
    }

    public GameRound readGameRound(String roundId) {
        log.info("Reading game round: {}", roundId);
        return gameRoundRepository.find(roundId);
    }
}
