package perczynski.kamil.evolution.gameservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import perczynski.kamil.evolution.gameservice.domain.PlayerRepository;
import perczynski.kamil.evolution.gameservice.domain.rounds.GameRoundMachineProperties;
import perczynski.kamil.evolution.gameservice.infra.persistence.InMemoryPlayerRepository;

@SpringBootApplication
@EnableConfigurationProperties(GameRoundMachineProperties.class)
public class GameServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GameServiceApplication.class, args);
    }

}
