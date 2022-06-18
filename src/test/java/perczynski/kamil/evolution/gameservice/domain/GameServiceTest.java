package perczynski.kamil.evolution.gameservice.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import perczynski.kamil.evolution.gameservice.domain.bets.Bet;
import perczynski.kamil.evolution.gameservice.domain.bets.BetMode;
import perczynski.kamil.evolution.gameservice.domain.bets.BetOperations;
import perczynski.kamil.evolution.gameservice.domain.bets.GameRound;
import perczynski.kamil.evolution.gameservice.domain.rounds.GameRoundMachine;
import perczynski.kamil.evolution.gameservice.domain.rounds.GameRoundRepository;
import perczynski.kamil.evolution.gameservice.domain.rounds.Win;
import perczynski.kamil.evolution.gameservice.libs.ErrorCodeException;
import perczynski.kamil.evolution.gameservice.libs.Money;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.mockito.Mockito.*;
import static perczynski.kamil.evolution.gameservice.domain.GameErrorCode.INVALID_BET_AMOUNT;
import static perczynski.kamil.evolution.gameservice.domain.PlayerFixtures.somePlayer;
import static perczynski.kamil.evolution.gameservice.domain.bets.BetFixtures.someBet;
import static perczynski.kamil.evolution.gameservice.libs.MockAnswers.withFirstParameter;
import static perczynski.kamil.evolution.gameservice.libs.Uuids.nextUuid;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    @InjectMocks
    private GameService gameService;

    @Mock
    private PlayerRepository playerRepository;
    @Mock
    private GameRoundRepository gameRoundRepository;
    @Mock
    private GameRoundMachine gameRoundMachine;
    @Mock
    private BetOperations betOperations;

    @Test
    void testWinMoney() {
        // given:
        final Player player = somePlayer()
                .playerId(nextUuid())
                .balance(new Money(10_00))
                .build();
        final Bet bet = someBet().build();
        final Win win = new Win(new Money(300), true);

        when(playerRepository.find(any())).thenReturn(player);
        when(gameRoundMachine.drawWin(any())).thenReturn(win);

        when(playerRepository.save(any())).thenAnswer(withFirstParameter());
        when(gameRoundRepository.save(any())).thenAnswer(withFirstParameter());

        // when:
        final GameRound gameRound = gameService.placeBet(player.getPlayerId(), bet);

        // then:
        assertThat(gameRound.getNextBalance()).isEqualTo(new Money(1300));
    }

    @Test
    void testPlaceInvalidBetAmount() {
        // given:
        final Player player = somePlayer()
                .playerId(nextUuid())
                .balance(new Money(10_00))
                .build();
        final Bet bet = someBet().stake(new Money(-10)).build();

        // when
        final ErrorCodeException exception = catchThrowableOfType(
                () -> gameService.placeBet(player.getPlayerId(), bet),
                ErrorCodeException.class
        );

        // then:
        assertThat(exception.getCode()).isEqualTo(INVALID_BET_AMOUNT);
    }

    @Test
    void testWinMoneyInFreeGame() {
        // given:
        final Player player = somePlayer()
                .playerId(nextUuid())
                .freeRoundAvailable(false)
                .build();

        final Bet bet = someBet()
                .mode(BetMode.PLAY_FOR_FREE)
                .build();
        final Win win = new Win(new Money(3000), true);

        when(playerRepository.find(any())).thenReturn(player);
        when(gameRoundMachine.drawWin(any())).thenReturn(win);
        when(gameRoundRepository.save(any())).thenAnswer(withFirstParameter());

        // when:
        final GameRound gameRound = gameService.placeBet(player.getPlayerId(), bet);

        // then:
        assertThat(player.getBalance()).isEqualTo(gameRound.getNextBalance());
        verify(playerRepository, never()).save(any());
    }

    @Test
    void testLooseBet() {
        // given:
        final Player player = somePlayer()
                .playerId(nextUuid())
                .freeRoundAvailable(true)
                .build();

        final Bet bet = someBet().build();
        final Win win = new Win(Money.ZERO, false);

        when(playerRepository.find(any())).thenReturn(player);
        when(gameRoundMachine.drawWin(any())).thenReturn(win);

        when(playerRepository.save(any())).thenAnswer(withFirstParameter());
        when(gameRoundRepository.save(any())).thenAnswer(withFirstParameter());

        // when:
        final GameRound gameRound = gameService.placeBet(player.getPlayerId(), bet);

        // then:
        final Player updatedPlayer = captureUpdatedPlayer(playerRepository);

        assertThat(gameRound.getNextBalance())
                .isEqualTo(player.getBalance())
                .isEqualTo(updatedPlayer.getBalance());

        assertThat(updatedPlayer.isFreeRoundAvailable()).isFalse();
    }

    private static Player captureUpdatedPlayer(PlayerRepository playerRepository) {
        final ArgumentCaptor<Player> captor = ArgumentCaptor.forClass(Player.class);
        verify(playerRepository).save(captor.capture());
        return captor.getValue();
    }

}