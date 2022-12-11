package fr.carbon.maptotreasures.config;

import static fr.carbon.maptotreasures.config.Constants.ADVENTURER;
import static fr.carbon.maptotreasures.config.Constants.CARD;
import static fr.carbon.maptotreasures.config.Constants.DASH;
import static fr.carbon.maptotreasures.config.Constants.PLAIN;
import static fr.carbon.maptotreasures.config.Constants.MOUNTAIN;
import static fr.carbon.maptotreasures.config.Constants.TREASURE;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.carbon.maptotreasures.domain.Adventurer;
import fr.carbon.maptotreasures.domain.Mountain;
import fr.carbon.maptotreasures.domain.Treasure;
import fr.carbon.maptotreasures.exception.ConfigException;
import fr.carbon.maptotreasures.exception.InitException;

/**
 * The Class ReaderConfig for managing Config.
 *
 * @author christopher
 */
public class ReaderConfig {
	
	private static final Logger log = LoggerFactory.getLogger(ReaderConfig.class);

	private static final Path PATH = Paths.get("src","main","resources", "config.txt");
	
	
	/**
	 * Gets the card.
	 *
	 * @return the card
	 */
	public Object[][] getCard() {
		final List<String> lines = getLines();

		final Object[][] card = initializeCard(lines);
		
		if (null == card) {
			throw new InitException();
		}
		
		fillCardOfEmpty(card);

		for (String line : lines) {
			final String[] tokens = line.split(DASH);
			if (CARD.equals(tokens[0])) {
				continue;
			}

			addDataInCard(card, tokens);
		}

		return card;
	}
	
	private List<String> getLines() {
		List<String> lines = null;
		try {
			lines = Files.readAllLines(PATH);
		} catch (IOException e) {
			throw new ConfigException(e);
		}
		return lines;
	}

	private Object[][] initializeCard(final List<String> lines) {
		Object[][] card = null;
		for (String line : lines) {
			String[] tokens = line.split(DASH);

			if (CARD.equals(tokens[0])) {
				int width = Integer.parseInt(tokens[1]);
				int height = Integer.parseInt(tokens[2]);
				card = new Object[height][width];
				break;
			}
		}
		return card;
	}
	
	private void fillCardOfEmpty(final Object[][] card) {
		for (int i = 0; i < card.length; i++) {
			for (int j = 0; j < card[i].length; j++) {
				card[i][j] = PLAIN;
			}
		}
	}
	
	private void addDataInCard(Object[][] card, String[] tokens) {
		switch (tokens[0]) {
			case TREASURE -> {
				int width = Integer.parseInt(tokens[1]);
				int height = Integer.parseInt(tokens[2]);
				
				Treasure treasure = new Treasure();
				treasure.setName(tokens[0]);
				treasure.setPositionHeight(height);
				treasure.setPositionWidth(width);
				treasure.setNumber(Integer.parseInt(tokens[3]));
	
				card[height][width] = treasure;
			}
			case ADVENTURER -> {
				int width = Integer.parseInt(tokens[2]);
				int height = Integer.parseInt(tokens[3]);
				
				Adventurer adventurer = new Adventurer();
				adventurer.setName(tokens[0]);
				adventurer.setFirstName(tokens[1]);
				adventurer.setPositionHeight(height);
				adventurer.setPositionWidth(width);
				adventurer.setMoves(tokens[5].toCharArray());
	
				card[height][width] = adventurer;
			}
			case MOUNTAIN -> {
				int width = Integer.parseInt(tokens[1]);
				int height = Integer.parseInt(tokens[2]);
	
				Mountain mountain = new Mountain();
				mountain.setName(tokens[0]);
				mountain.setPositionHeight(height);
				mountain.setPositionWidth(width);
	
				card[height][width] = mountain;
			}
			default -> log.error("Data cannot be processed");
			
		}
	}
}
