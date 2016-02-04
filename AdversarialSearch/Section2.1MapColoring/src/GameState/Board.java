/**
 * 
 */
package GameState;

//import GameData.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import GameData.UnitSquare;
import Move.Move;
import Player.Player;

/**
 * @author Piyush
 * @Info: Begins the game. Stores the asset values and the squares.
 */
public class Board {
	
	public static String _fileKeren = "C:/Projects/CS440/workspace2/WarGames/game_boards/Keren.txt";
	public static String _fileNarvik = "C:/Projects/CS440/workspace2/WarGames/game_boards/Narvik.txt";
	public static String _fileSevastopol = "C:/Projects/CS440/workspace2/WarGames/game_boards/Sevastopol.txt";
	public static String _fileSmolensk = "C:/Projects/CS440/workspace2/WarGames/game_boards/Smolensk.txt";
	public static String _fileWesterplatte = "C:/Projects/CS440/workspace2/WarGames/game_boards/Westerplatte.txt";
	
	private int _nbrOfRows;
	private int _nbrOfColumns;
	private ArrayList<ArrayList<Integer>> _squareValues;
	private ArrayList<ArrayList<UnitSquare>> _square;
	
	public ArrayList<ArrayList<UnitSquare>> get_square() {
		return _square;
	}

	public void set_square(ArrayList<ArrayList<UnitSquare>> _square) {
		this._square = _square;
	}

	public Board() 
	{
		
	}

	public Board(int _nbrOfRows, int _nbrOfColumns,
			ArrayList<ArrayList<Integer>> squareVal) {
		//super();
		this._nbrOfRows = _nbrOfRows;
		this._nbrOfColumns = _nbrOfColumns;
		this._squareValues = squareVal;
	}
	
/*	public static ArrayList<ArrayList<Integer>> get_square() {
		return _squareValues;
	}

	public void set_square(ArrayList<ArrayList<Integer>> _square) {
		Board._squareValues = _square;
	}
*/
	public int get_nbrOfRows() {
		return _nbrOfRows;
	}

	public void set_nbrOfRows(int _nbrOfRows) {
		this._nbrOfRows = _nbrOfRows;
	}

	public int get_nbrOfColumns() {
		return _nbrOfColumns;
	}

	public void set_nbrOfColumns(int _nbrOfColumns) {
		this._nbrOfColumns = _nbrOfColumns;
	}
	
	public ArrayList<Move> fetchListOfFeasibleMoves(GameState gameSt, String player)
	{
		// the logic here would be to scan all the squares and get the squares 
		// where the player can move
		ArrayList<Move> listOfFeasibleMoves = new ArrayList<Move>();
		
		for(int i = 0; i < _nbrOfRows; i++)
		{
			for(int j = 0; j < _nbrOfColumns; j++)
			{
				UnitSquare unitSquare = _square.get(i).get(j);
				
				if(!unitSquare.isConquered())
				{
					// if any of the squares are not conquered then its eligible
					// for commando para drop. So add it!
					Player p = gameSt.getPlayerByID(player);
					listOfFeasibleMoves.add(new Move(p, unitSquare, gameSt, "c"));
					
					//now check for Blitz
					// Get the list of its neighbors (which is maximum 4)
					for(UnitSquare neighboringSq : unitSquare.getNeighboringUnitSquares())
					{
						//for M1DeathBlitz, the neigboring square has to conquered by the opponent.
						if(neighboringSq.isConquered())
						{
							if(neighboringSq.getOccupiedByPlayer().equals(player))
							{
								//this is M1 death Blitz! Bingo!!
								listOfFeasibleMoves.add(new Move(p, unitSquare, gameSt, "m"));
								break;
							}
						}
					}
				}
			}
		}
		return listOfFeasibleMoves;
		
	}
	
	/*public Board getCopy()
	{
		Board board = new Board();
		board = this;
		return board;
	}*/
	
	public Board getCopy()
	{
		Board board = new Board();
		board.set_nbrOfColumns(this._nbrOfColumns);
		board.set_nbrOfRows(this._nbrOfRows);
		ArrayList<ArrayList<UnitSquare>> unitSquareList 
			= getSquareCopy();/*new ArrayList<ArrayList<UnitSquare>>();*/
			/*for(int row = 0; row < this._nbrOfRows; row++ )
			{
				unitSquareList.add(new ArrayList<UnitSquare>());
				for(int col = 0; col < this._nbrOfColumns; col++ )
				{
					UnitSquare usq = this._square.get(row).get(col).getCopy();
					unitSquareList.get(row).add(usq);
				}
			}*/

		board.set_square(unitSquareList);
		board.insertNeighborAfterCopy();
		/*for(ArrayList<UnitSquare> listOfSq : this._square)
		{
			for(UnitSquare usq : listOfSq)
			{
				usq.setNeighboringUnitSquares(findNeigboringSquares(usq));
			}
		}
		board.set_square(this._square);*/
		return board;
	}
	
	public void insertNeighborAfterCopy()
	{
		for(ArrayList<UnitSquare> listOfSquares : this._square)
		{
			for(UnitSquare usq : listOfSquares)
			{
				usq.setNeighboringUnitSquares(this.findNeigboringSquares(usq));
			}
		}
	}
	
	private ArrayList<ArrayList<UnitSquare>> getSquareCopy()
	{
		ArrayList<ArrayList<UnitSquare>> sqCopy = new ArrayList<ArrayList<UnitSquare>>();
		for(int row = 0; row < _nbrOfRows; row++ )
		{
			sqCopy.add(new ArrayList<UnitSquare>());
			for(int col = 0; col < _nbrOfColumns; col++ )
			{
				UnitSquare usqCopy = this._square.get(row).get(col).getCopy();
				sqCopy.get(row).add(usqCopy);
			}
		}
		return sqCopy;
	}

	public void readFile(File file)
	{
		try 
		{
			ArrayList<ArrayList<Integer>> squareValues =  new ArrayList<ArrayList<Integer>>();
			Scanner scanner = new Scanner(file);
			int count = 0;
			String line;
			while(scanner.hasNextLine())
			{
				line = scanner.nextLine();
				Scanner lineScanner = new Scanner(line);
				squareValues.add(new ArrayList<Integer>());
				while(lineScanner.hasNextInt())
				{
					squareValues.get(count).add(lineScanner.nextInt());
				}
				count++;
				lineScanner.close();
			}
			scanner.close();
		
			_nbrOfRows = squareValues.size();
			_nbrOfColumns = squareValues.get(0).size();
			_squareValues = squareValues;
		
		} 
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void printBoard()
	{
		for(ArrayList<UnitSquare> listOfUnitSquares : _square)
		{
			for(UnitSquare square : listOfUnitSquares)
			{
				System.out.print(square.getAssetValue() + "    ");
			}
			System.out.println();
		}
	}
	
	public void initState()
	{
		_square = new ArrayList<ArrayList<UnitSquare>>();
		ArrayList<ArrayList<Integer>> initialGridVals = _squareValues;
		for(int row = 0; row < _nbrOfRows; row++ )
		{
			_square.add(new ArrayList<UnitSquare>());
			for(int col = 0; col < _nbrOfColumns; col++ )
			{
				int val = initialGridVals.get(row).get(col);
				this._square.get(row).add(new UnitSquare(row, col, val));
			}
		}
		
		//each square should know its neighbor. This makes computation simpler at M1 death blitz
		/*for(ArrayList<UnitSquare> oneRowOfSquares : this._square)
		{
			for(UnitSquare square : oneRowOfSquares)
			{
				ArrayList<UnitSquare> temp = findNeigboringSquares(square);
				square.setNeighboringUnitSquares(temp);
			}
		}*/
		this.insertNeighborAfterCopy();
		System.out.println("x");
	}
	
	public boolean areAllSquaresOccupied()
	{
		boolean retflag = false;
		for(ArrayList<UnitSquare> unitSquareList : this._square)
		{
			for(UnitSquare unitSquare : unitSquareList)
			{
				if(!unitSquare.isConquered())
				{
					return retflag;
				}
			}
		}
		retflag = true;
		return retflag;
	}
	
	public ArrayList<UnitSquare> findNeigboringSquares(UnitSquare square)
	{
		ArrayList<UnitSquare> neighborList = new ArrayList<UnitSquare>();
		// checking the boundary conditions and the usual squares.
		if( square.getColumn() != 0 )
			neighborList.add(_square.get(square.getRow()).get(square.getColumn()-1));
		if( square.getColumn() != _nbrOfColumns-1 )
			neighborList.add(_square.get(square.getRow()).get(square.getColumn()+1));
		if( square.getRow() != 0 )
			neighborList.add(_square.get(square.getRow()-1).get(square.getColumn()));
		if( square.getRow() != _nbrOfRows-1 )
			neighborList.add(_square.get(square.getRow()+1).get(square.getColumn()));
		
		return neighborList;
	}

	/*public static void main(String[] args)
	{
		Board state = new Board();
		File file = new File(_fileKeren);
		//File file = new File(_fileNarvik);
		//File file = new File(_fileSevastopol);
		//File file = new File(_fileSmolensk);
		//File file = new File(_fileWesterplatte);
		state.readFile(file);
		
		state.initState();
		state.printBoard();
		
	}*/


}
