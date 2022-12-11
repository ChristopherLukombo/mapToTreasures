package fr.carbon.maptotreasures.domain;

/**
 * The Class Treasure for managing Treasure.
 * 
 * @author christopher
 *
 */
public class Treasure {

	private String name;

	private int positionWidth;

	private int positionHeight;

	private int number;

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

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public void decrementNumber() {
		this.number--;
	}

	@Override
	public String toString() {
		return name + "(" + number + ")";
	}
}
