/**
 * 
 */
package GameState;

import java.util.ArrayList;


import Move.Move;
import Player.Player;

/**
 * @author Piyush
 *
 */
public class GameState {
	
	private Player p1;
	private Player p2;
	private Board board;
	
	public GameState(Player p1, Player p2, Board board)
	{
		this.p1 = p1;
		this.p2 = p2;
		this.board = board;
	}
	
	public Player getP1() 
	{
		return p1;
	}
	public void setP1(Player p1) {
		this.p1 = p1;
	}
	public Player getP2() {
		return p2;
	}
	public void setP2(Player p2) {
		this.p2 = p2;
	}
	public Board getBoard() {
		return board;
	}
	public void setBoard(Board board) {
		this.board = board;
	}
	
	private GameState parentNode;
	public GameState getParentNode() {
		return parentNode;
	}
	public void setParentNode(GameState parentNode) {
		this.parentNode = parentNode;
	}
	
	public boolean isTerminalState()
	{
		boolean flag = this.board.areAllSquaresOccupied();
		return flag;
	}
	
	public ArrayList<Move> getListOfFeasibleMoves(Player p)
	{
		//ArrayList<Move> temp = this.board.fetchListOfFeasibleMoves(this, p.getPlayerId());
		return this.board.fetchListOfFeasibleMoves(this, p.getPlayerId());
	}
	
	public GameState getCopy()
	{
		return new GameState(this.p1.getPlayerCopy(), this.p2.getPlayerCopy(), this.board.getCopy());
	}
	
	/*public GameState getCopy()
	{
		return this;
	}*/
	
	public GameState getGameStateAfterPlayerMoves(Player p, Move m)
	{	
		if(m.getMoveType().equalsIgnoreCase("c"))
		{
			return m.makeNextMove("c");
		}
		else if(m.getMoveType().equalsIgnoreCase("m"))
		{
			return m.makeNextMove("m");
		}
		return null;
	}
	
	public ArrayList<GameState> getListofChildSquares(Player p)
	{
		ArrayList<GameState> listOfChildren = new ArrayList<GameState>();
		for(Move m : this.getListOfFeasibleMoves(p))
		{
			if(m.getMoveType().equalsIgnoreCase("c"))
			{
				Move move = m;
				listOfChildren.add(move.makeNextMove("c"));
			}
			else if(m.getMoveType().equalsIgnoreCase("m"))
			{
				Move move = m;
				listOfChildren.add(move.makeNextMove("m"));
			}
		}
		return listOfChildren;
	}
	
	public Player getMinPlayer()
	{
		if(!p1.isMaxPlayer())
		{
			return p1;
		}
		else if(!p2.isMaxPlayer())
		{
			return p2;
		}
		return null;
	}
	
	public Player getMaxPlayer()
	{
		if(p1.isMaxPlayer())
		{
			return p1;
		}
		else if(p2.isMaxPlayer())
		{
			return p2;
		}
		return null;
	}
	
	private boolean max;
	
	public boolean isMax() {
		return max;
	}
	public void setMax(boolean max) {
		this.max = max;
	}
	
	public Player getPlayerByID(String id)
	{
		//Player p = null;
		if(this.p1.getPlayerId().equals(id))
		{
			return this.p1;
		}
		else /*if(this.p2.getPlayerId().equals(id))*/{
			return this.p2;
		}
		//return p;
	}


}
