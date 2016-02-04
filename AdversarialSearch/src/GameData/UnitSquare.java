/**
 * 
 */
package GameData;

import java.util.ArrayList;

/**
 * @author Piyush
 *  This class contains the information of each square on board.
 *
 */
public class UnitSquare {
	
	private int row = 0;
	private int column = 0;
	//boolean isOccupied = false;
	private int assetValue = 0;
	private String occupiedByPlayer;
	private ArrayList<UnitSquare> NeighboringUnitSquares;
	private boolean isConquered;
	
	public UnitSquare() {
		//super();
		// TODO Auto-generated constructor stub
	}
	
	public UnitSquare(int row, int column) {
		//super();
		this.row = row;
		this.column = column;
	}

	public UnitSquare(int row, int column, int assetValue) {
		//super();
		this.row = row;
		this.column = column;
		this.assetValue = assetValue;
	}
	
	public UnitSquare getCopy()
	{
		UnitSquare usq = new UnitSquare(this.row, this.column, this.assetValue);
		usq.setConquered(this.isConquered);
		usq.setOccupiedByPlayer(this.occupiedByPlayer);
		return usq;
	}
	
	/*public UnitSquare getCopy(){
		UnitSquare usq = new UnitSquare(row, column, assetValue);
		usq = this;
		return usq;
	}*/

	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getColumn() {
		return column;
	}
	public void setColumn(int column) {
		this.column = column;
	}
	public int getAssetValue() {
		return assetValue;
	}
	public void setAssetValue(int assetValue) {
		this.assetValue = assetValue;
	}
	public String getOccupiedByPlayer() {
		return occupiedByPlayer;
	}
	public void setOccupiedByPlayer(String occupiedByPlayer) {
		this.occupiedByPlayer = occupiedByPlayer;
	}
	public ArrayList<UnitSquare> getNeighboringUnitSquares() {
		return NeighboringUnitSquares;
	}
	public void setNeighboringUnitSquares(ArrayList<UnitSquare> neighboringUnitSquares) {
		NeighboringUnitSquares = neighboringUnitSquares;
	}

	public boolean isConquered() {
		return isConquered;
	}

	public void setConquered(boolean isConquered) {
		this.isConquered = isConquered;
	}	

}
