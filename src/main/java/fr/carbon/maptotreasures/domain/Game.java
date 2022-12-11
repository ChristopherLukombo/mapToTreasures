package fr.carbon.maptotreasures.domain;

import static fr.carbon.maptotreasures.config.Constants.ADVANCE;
import static fr.carbon.maptotreasures.config.Constants.CARD;
import static fr.carbon.maptotreasures.config.Constants.DASH;
import static fr.carbon.maptotreasures.config.Constants.LEFT;
import static fr.carbon.maptotreasures.config.Constants.LINE_BREAK;
import static fr.carbon.maptotreasures.config.Constants.MOUNTAIN;
import static fr.carbon.maptotreasures.config.Constants.OUTPUT_TXT;
import static fr.carbon.maptotreasures.config.Constants.PLAIN;
import static fr.carbon.maptotreasures.config.Constants.RIGHT;
import static fr.carbon.maptotreasures.config.Constants.TREASURE;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.carbon.maptotreasures.config.ReaderConfig;

/**
 * The Class Game for managing Game.
 * 
 * @author christopher
 *
 */
public class Game {
	
	private static final Logger log = LoggerFactory.getLogger(Game.class);

	private final ReaderConfig readerConfig;
	
	private final Object[][] card;
	
	public Game() {
		this.readerConfig = new ReaderConfig();
		this.card = readerConfig.getCard();
	}
	
	/**
	 * Start the game.
	 */
	public void start() {
		display(card);

		final List<Adventurer> adventurers = getAllAdventurers(card);
		adventurers.forEach(this::produceMoves);

		writeResult(card);
	}

	private void produceMoves(Adventurer adventurer) {
		for (final char move: adventurer.getMoves()) {
			switch (move) {
				case ADVANCE -> {
					advance(adventurer, card);
					display(card);
					
				}
				case LEFT -> {
					left(adventurer, card);
					display(card);
				}
				case RIGHT -> {
					right(adventurer, card);
					display(card);
				}
				default -> log.warn("Error move: {}", move);
			}
		}
	}

	private void display(final Object[][] card) {
		System.out.println("----------------------------------------------------------");
		for (int i = 0; i < card.length; i++) {
			for (int j = 0; j < card[i].length; j++) {
				System.out.print("\t" + card[i][j] + "\t");
			}

			// By now we are done with only 1 row so
			// New line
			System.out.println();
		}
	}

	private void advance(Adventurer adventurer, Object[][] card) {
		if (detectImpactAdvance(adventurer, card)) {
			return;
		}

		if (card[adventurer.getPositionHeight() + 1][adventurer.getPositionWidth()] instanceof Mountain) {
			return;
		}
		
		if (card[adventurer.getPositionHeight() + 1][adventurer.getPositionWidth()] instanceof Treasure treasure && treasure.getNumber() > 0) {
			adventurer.addTreasure(treasure);
			treasure.decrementNumber();
		}
		
		handOverTheRemainingTreasures(adventurer, card);

		adventurer.setPositionHeight(adventurer.getPositionHeight() + 1);
		card[adventurer.getPositionHeight()][adventurer.getPositionWidth()] = adventurer;
		
		if ((card[adventurer.getPositionHeight() - 1][adventurer.getPositionWidth()] instanceof Treasure treasure && treasure.getNumber() == 0) ||
				(card[adventurer.getPositionHeight() - 1][adventurer.getPositionWidth()] instanceof Adventurer)) {
			card[adventurer.getPositionHeight() - 1][adventurer.getPositionWidth()] = PLAIN;
		}
	}

	private boolean detectImpactAdvance(Adventurer adventurer, Object[][] card) {
		return adventurer.getPositionHeight() + 1 > card[adventurer.getPositionHeight()].length;
	}
	
	private void handOverTheRemainingTreasures(Adventurer adventurer, Object[][] card) {
		if (adventurer.getTreasures().stream().anyMatch(treasure -> isSamePositionBefore(adventurer, treasure))) {
			adventurer.getTreasures().stream()
					.filter(treasure -> isSamePositionBefore(adventurer, treasure))
					.findFirst()
					.ifPresent(treasure -> card[treasure.getPositionHeight()][treasure.getPositionWidth()] = treasure);
		}
	}

	private boolean isSamePositionBefore(Adventurer adventurer, Treasure treasure) {
		return treasure.getNumber() > 0 && treasure.getPositionHeight() == adventurer.getPositionHeight()
				&& treasure.getPositionWidth() == adventurer.getPositionWidth();
	}

	private void right(Adventurer adventurer, Object[][] card) {
		if (detectImpactRight(adventurer, card)) {
			return;
		}

		if (card[adventurer.getPositionHeight()][adventurer.getPositionWidth() + 1] instanceof Mountain) {
			return;
		}
		
		if (card[adventurer.getPositionHeight()][adventurer.getPositionWidth() + 1] instanceof Treasure treasure && treasure.getNumber() > 0) {
			adventurer.addTreasure(treasure);
			treasure.decrementNumber();
		} 
		
		handOverTheRemainingTreasures(adventurer, card);

		adventurer.setPositionWidth(adventurer.getPositionWidth() + 1);
		card[adventurer.getPositionHeight()][adventurer.getPositionWidth()] = adventurer;
		
		if ((card[adventurer.getPositionHeight()][adventurer.getPositionWidth() - 1] instanceof Treasure treasure && treasure.getNumber() == 0) || 
				(card[adventurer.getPositionHeight()][adventurer.getPositionWidth() - 1] instanceof Adventurer)) {
			card[adventurer.getPositionHeight()][adventurer.getPositionWidth() - 1] = PLAIN;
		}
	}

	private boolean detectImpactRight(Adventurer adventurer, Object[][] card) {
		return adventurer.getPositionWidth() + 1 >= card[adventurer.getPositionWidth()].length;
	}

	private void left(Adventurer adventurer, Object[][] card) {
		if (detectImpactLeft(adventurer)) {
			return;
		}

		if (card[adventurer.getPositionHeight()][adventurer.getPositionWidth() - 1] instanceof Mountain) {
			return;
		}
		
		if (card[adventurer.getPositionHeight()][adventurer.getPositionWidth() - 1] instanceof Treasure treasure && treasure.getNumber() > 0) {
			adventurer.addTreasure(treasure);
			treasure.decrementNumber();
		} 
		
		handOverTheRemainingTreasures(adventurer, card);
		
		adventurer.setPositionWidth(adventurer.getPositionWidth() - 1);
		card[adventurer.getPositionHeight()][adventurer.getPositionWidth()] = adventurer;

		
		if ((card[adventurer.getPositionHeight()][adventurer.getPositionWidth() + 1] instanceof Treasure treasure && treasure.getNumber() == 0) ||
				(card[adventurer.getPositionHeight()][adventurer.getPositionWidth() + 1] instanceof Adventurer)) {
			card[adventurer.getPositionHeight()][adventurer.getPositionWidth() + 1] = PLAIN;
		}
	}

	private boolean detectImpactLeft(Adventurer adventurer) {
		return adventurer.getPositionWidth() - 1 < 0;
	}
	
	private void writeResult(final Object[][] card) {
		final List<Mountain> allMountains = getAllMountains(card);
		final List<Treasure> allTreasures = getAllTreasures(card);
		final List<Adventurer> allAventurers = getAllAdventurers(card);
		
		final String result = buildResult(card, allMountains, allTreasures, allAventurers);
		
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_TXT))) {
			writer.write(result);
		} catch (IOException e) {
			log.error("Error during writting result", e);
		}
	}

	private String buildResult(
			Object[][] card, 
			final List<Mountain> allMountains,
			final List<Treasure> allTreasures,
			final List<Adventurer> allAdventurers) {
		final StringBuilder resultbuilder = new StringBuilder();
		
		resultbuilder.append(CARD).append(DASH).append(card[0].length).append(DASH).append(card.length);
		resultbuilder.append(LINE_BREAK);
		
		allMountains.forEach(mountain -> {
			resultbuilder.append(MOUNTAIN).append(DASH).append(mountain.getPositionWidth()).append(DASH).append(mountain.getPositionHeight());
			resultbuilder.append(LINE_BREAK);
		});
		
		allTreasures.forEach(treasure -> {
			resultbuilder.append(TREASURE).append(DASH).append(treasure.getPositionWidth()).append(DASH).append(treasure.getPositionHeight()).append(DASH).append(treasure.getNumber());
			resultbuilder.append(LINE_BREAK);
		});
		
		allAdventurers.forEach(adventurer -> {
			resultbuilder.append(adventurer.getName()).append(DASH).append(adventurer.getFirstName()).append(DASH).append(adventurer.getPositionWidth()).append(DASH).append(adventurer.getPositionHeight()).append(DASH).append(adventurer.getTreasures().size());
			resultbuilder.append(LINE_BREAK);
		});
		
		return resultbuilder.toString();
	}
	
	private List<Mountain> getAllMountains(final Object[][] card) {
		final List<Mountain> mountains = new ArrayList<>();
		
		for (int i = 0; i < card.length; i++) {
			for (int j = 0; j < card[i].length; j++) {
				if (card[i][j] instanceof Mountain mountain) {
					mountains.add(mountain);
				}
			}
		}

		return mountains;
	}
	
	private List<Treasure> getAllTreasures(final Object[][] card) {
		final List<Treasure> treasures = new ArrayList<>();
		
		for (int i = 0; i < card.length; i++) {
			for (int j = 0; j < card[i].length; j++) {
				if (card[i][j] instanceof Treasure treasure) {
					treasures.add(treasure);
				}
			}
		}

		return treasures;
	}

	private List<Adventurer> getAllAdventurers(final Object[][] card) {
		final List<Adventurer> adventurers = new ArrayList<>();
		
		for (int i = 0; i < card.length; i++) {
			for (int j = 0; j < card[i].length; j++) {
				if (card[i][j] instanceof Adventurer adventurer) {
					adventurers.add(adventurer);
				}
			}
		}

		return adventurers;
	}
}
