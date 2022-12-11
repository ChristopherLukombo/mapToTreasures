package fr.carbon.maptotreasures.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class Adventurer for managing Adventurer.
 * 
 * @author christopher
 *
 */
public class Adventurer {

	private String name;
	
	private String firstName;

	private int positionWidth;

	private int positionHeight;

	private List<Treasure> treasures = new ArrayList<>();

	private char[] moves;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPositionWidth() {
		return positionWidth;
	}

	public void setPositionWidth(int positionWidth) {
		this.positionWidth = positionWidth;
	}

	public int getPositionHeight() {
		return positionHeight;
	}

	public void setPositionHeight(int positionHeight) {
		this.positionHeight = positionHeight;
	}

	public List<Treasure> getTreasures() {
		return treasures;
	}

	public void setTreasures(List<Treasure> treasures) {
		this.treasures = treasures;
	}

	public void addTreasure(Treasure treasure) {
		this.treasures.add(treasure);
	}

	public char[] getMoves() {
		return moves;
	}

	public void setMoves(char[] moves) {
		this.moves = moves;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Override
	public String toString() {
		return name + "(" + firstName + ")";
	}
}
