package fr.carbon.maptotreasures.domain;

/**
 * The Class Mountain for managing Mountain.
 * 
 * @author christopher
 *
 */
public class Mountain {

	private String name;

	private int positionWidth;

	private int positionHeight;

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

	@Override
	public String toString() {
		return name;
	}
}
