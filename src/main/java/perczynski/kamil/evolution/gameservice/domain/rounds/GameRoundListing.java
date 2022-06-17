package perczynski.kamil.evolution.gameservice.domain.rounds;

import lombok.Value;
import perczynski.kamil.evolution.gameservice.domain.bets.GameRound;

import java.util.List;

@Value
public class GameRoundListing {

    List<GameRound> rounds;

}
