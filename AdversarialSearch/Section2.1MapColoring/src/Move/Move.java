/**
 * 
 */
package Move;

import java.util.ArrayList;

import GameData.UnitSquare;
import GameState.GameState;
import Player.Player;

/**
 * @author Piyush
 *
 */
public class Move {

	private Player _currPlayer;
	private UnitSquare _unitsquare;
	private GameState _gameState;
	private ArrayList<UnitSquare> _listOfconquredSquares;
	// Move type can either by Commando Para Drop or M1DeathBlitz
	// c denotes commando, m denotes m1 death blitz
	private String _moveType;
	
	public Move()
	{
		//default constructor
	}

	public Move(Player currPlayer, UnitSquare unitsquare, GameState gameState, String moveType) 
	{
		this._currPlayer = currPlayer;
		this._unitsquare = unitsquare;
		this._gameState = gameState;
		this._moveType = moveType;
		
		if(moveType.equalsIgnoreCase("m"))
		{
			this._listOfconquredSquares = new ArrayList<UnitSquare>();
			this.populateListOfConqSquares();
		}
	}


	public Player getCurrPlayer() {
		return _currPlayer;
	}
	public void setCurrPlayer(Player currPlayer) {
		this._currPlayer = currPlayer;
	}
	public UnitSquare getUnitsquare() {
		return _unitsquare;
	}
	public void setUnitsquare(UnitSquare unitsquare) {
		this._unitsquare = unitsquare;
	}
	public GameState getGameState() {
		return _gameState;
	}
	public void setGameState(GameState gameState) {
		this._gameState = gameState;
	}

	public ArrayList<UnitSquare> getConqSquares() {
		return _listOfconquredSquares;
	}

	public void setConqSquares(ArrayList<UnitSquare> conqSquares) {
		this._listOfconquredSquares = conqSquares;
	}
	
	/*public void setConqSquares()
	{
		ArrayList<UnitSquare> currentSquareNeighbours = _unitsquare.getNeighboringUnitSquares();
		//find the squares which can be blitzed
		for( UnitSquare square : currentSquareNeighbours )
		{
			if(square.isConquered())
			{
				String currentOccupiedBy = square.getOccupiedByPlayer();
				// get the player id and check if its the opponent
				if(!currentOccupiedBy.equalsIgnoreCase(this._currPlayer.getPlayerId()))
				{
					this._listOfconquredSquares.add(square);
				}
			}
		}
	}*/
	
	public GameState makeNextMove(String moveType)
	{
		GameState newGameState = null;
		if(moveType.equalsIgnoreCase("c"))
		{
			newGameState = this._gameState.getCopy();
			UnitSquare usq = newGameState.getBoard().get_square().get(this._unitsquare.getRow()).get(this._unitsquare.getColumn());
			Player p = newGameState.getPlayerByID(this._currPlayer.getPlayerId());
			usq.setOccupiedByPlayer(p.getPlayerId());
			usq.setConquered(true);
			int newScoreForPlayer = p.getScore() + usq.getAssetValue();
			if(newScoreForPlayer > 72)
			{
				System.out.println();
			}
			p.setScore(newScoreForPlayer);
			return newGameState;
		}
		else if(moveType.equalsIgnoreCase("m"))
		{
			UnitSquare conqSquare;
			newGameState = this._gameState.getCopy();
			UnitSquare usq = newGameState.getBoard().get_square().get(this._unitsquare.getRow()).get(this._unitsquare.getColumn());
			Player p = newGameState.getPlayerByID(this._currPlayer.getPlayerId());
			usq.setOccupiedByPlayer(this._currPlayer.getPlayerId());
			usq.setConquered(true);
			int newScoreForPlayer = p.getScore() + usq.getAssetValue();
			p.setScore(newScoreForPlayer);
			
			// fixed issue. phew!
			// adjust all the scores
			Player p1;
			for(UnitSquare conqSq : this._listOfconquredSquares)
			{
				conqSquare = newGameState.getBoard().get_square().get(conqSq.getRow()).get(conqSq.getColumn());
				p1 = newGameState.getPlayerByID(conqSquare.getOccupiedByPlayer());
				p1.setScore(p1.getScore()- conqSquare.getAssetValue()); 
				conqSquare.setOccupiedByPlayer(this._currPlayer.getPlayerId());
				p.setScore(p.getScore()+ conqSquare.getAssetValue());
			}
			return newGameState;
		}
		return newGameState;
	}
	
	public boolean canMoveToNextSquare(GameState gameSt)
	{
		boolean flag = _unitsquare.isConquered();
		return !flag;
	}

	public String getMoveType() {
		return _moveType;
	}

	public void setMoveType(String moveType) {
		this._moveType = moveType;
	}
	
	private void populateListOfConqSquares()
	{
		ArrayList<UnitSquare> neighboringSq = this._unitsquare.getNeighboringUnitSquares();
		for( UnitSquare usq : neighboringSq )
		{
			if(usq.isConquered())
			{
				if(!usq.getOccupiedByPlayer().equals(this._currPlayer.getPlayerId())){
					//if the space is occupied by the other player, add it to conquered spaces
					this._listOfconquredSquares.add(usq);
				}
			}
		}
	}

}
