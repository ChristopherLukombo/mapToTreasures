package maptotreasures;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.carbon.maptotreasures.domain.Game;

class GameTest {

	private Game game;

	@BeforeEach
	void setUp() {
		game = new Game();
	}

	@Test
	void should_start_successfully() {
		 assertDoesNotThrow(() -> game.start());
	}
}
