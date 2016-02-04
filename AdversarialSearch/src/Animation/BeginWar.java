/**
 * 
 */
package Animation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import GameState.Board;
import GameState.GameState;
import Player.Player;
import SearchStrategy.SearchStrategy;

/**
 * @author Piyush
 *
 */
public class BeginWar 
{

	public static String _fileKeren = "C:/Projects/CS440/workspace2/AdversarialSearch/game_boards/Keren.txt";
	public static String _fileNarvik = "C:/Projects/CS440/workspace2/AdversarialSearch/game_boards/Narvik.txt";
	public static String _fileSevastopol = "C:/Projects/CS440/workspace2/AdversarialSearch/game_boards/Sevastopol.txt";
	public static String _fileSmolensk = "C:/Projects/CS440/workspace2/AdversarialSearch/game_boards/Smolensk.txt";
	public static String _fileWesterplatte = "C:/Projects/CS440/workspace2/AdversarialSearch/game_boards/Westerplatte.txt";
	PrintWriter _p1TimeWriter;
	PrintWriter _p2TimeWriter;
	PrintWriter _p1nodePrintWirter;
	PrintWriter _p2nodePrintWirter;

	int _miniMaxDepth = 2;
	int _alphaBetaDepth = 2;
	int _sleep = 10;
	static String _folder = "C:/Projects/CS440/workspace2/AdversarialSearch/output/";
	String _board = "";


	public BeginWar()
	{

	}

	/*public GameState initGameVaribales()
	{
		File file = new File("C:/Projects/CS440/workspace/WarGames/givenBoards/Westerplatte.txt");
		_board = "Westerplatte";

		Board board = new Board();

		Player p1 = new Player("Player1", false, false, 0);
		Player p2 = new Player("Player2", false, false, 0);
		return new GameState(p1, p2, board);
	}*/
	public void arrangeFolder(boolean p1UseAlphaBeta, boolean p2UseAlphaBeta)
	{
		_folder = _folder + _board + "/";
		if(p1UseAlphaBeta)
		{
			_folder += "alphaBeta _against_";
		}
		else
		{
			_folder += "miniMax_against_";
		}
		if(p2UseAlphaBeta)
		{
			_folder += "alphaBeta/";
		}
		else
		{
			_folder += "miniMax/";
		}
	}

	public void warCommencement(GameState state, boolean p1AlphaBetaUse, boolean p2AlphaBetaUse) throws InterruptedException 
	{	
		try 
		{
			arrangeFolder(p1AlphaBetaUse, p2AlphaBetaUse);
			_p1TimeWriter = new PrintWriter(_folder + "p1time.txt");
			_p2TimeWriter = new PrintWriter(_folder + "p2time.txt");
			_p1nodePrintWirter = new PrintWriter(_folder + "p1_nodes.txt");
			_p2nodePrintWirter = new PrintWriter(_folder + "p2_nodes.txt");
			PrintWriter pw = new PrintWriter(_folder + "GameResults.txt");


			System.out.println("********Game Begins**************");
			Animation animation = new Animation(state);
			GameState gameChangeState = state;
			int chance = 0;
			long moveStartTime;
			long moveDuration;
			long begin = System.currentTimeMillis();
			while(!gameChangeState.isTerminalState())
			{
				moveStartTime = System.currentTimeMillis();
				if( chance%2 == 0)
				{
					gameChangeState = searchAndFetchBestState(gameChangeState, true, p1AlphaBetaUse);
					moveDuration = System.currentTimeMillis() - moveStartTime;
					_p1TimeWriter.println(moveDuration);
				}
				else
				{
					gameChangeState = searchAndFetchBestState(gameChangeState, false, p2AlphaBetaUse);
					moveDuration = System.currentTimeMillis() - moveStartTime;
					_p2TimeWriter.println(moveDuration);
				}
				animation.setGameState(gameChangeState);
				animation.displayCurrentGameState();
				Thread t = Thread.currentThread();
				t.sleep(_sleep);
				chance++;
			}
			System.out.println("\nP1 using alphabeta strategy = " + p1AlphaBetaUse + ", P2 using alphabeta strategy = " + p2AlphaBetaUse);
			System.out.println("MiniMax Depth = " + _miniMaxDepth + ", and alphabeta Depth = " + _alphaBetaDepth );
			pw.println("\t!!!Game Over!!!");
			pw.println("Player"+ gameChangeState.getP1().getPlayerId() + " scored: "+ gameChangeState.getP1().getScore() );
			pw.println("Player"+ gameChangeState.getP2().getPlayerId() + " scored: "+ + gameChangeState.getP2().getScore() );
			pw.println("Computation time " + (System.currentTimeMillis() - begin) );
			pw.close();

			_p1nodePrintWirter.close();
			_p2nodePrintWirter.close();
			_p1TimeWriter.close();
			_p2TimeWriter.close();

		} 
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private GameState searchAndFetchBestState(GameState gameState, boolean p1Moves, boolean abUse)
	{
		Player max;
		if(p1Moves)
		{
			max = gameState.getP1();
			gameState.getP1().setMaxPlayer(true);
			gameState.getP2().setMaxPlayer(false);
		}
		else
		{
			max = gameState.getP2();
			gameState.getP1().setMaxPlayer(false);
			gameState.getP2().setMaxPlayer(true);
		}

		ArrayList<GameState> listOfChildGameStates = gameState.getListofChildSquares(max);
		GameState mostOptimum = null;
		// assign Int.minvalue adn Int.maxval as symbols of + and - infinity.
		int currMaxOrMinBest = Integer.MIN_VALUE;
		int retSearchValue;

		int nbrOfExpancdedNodes = 0;
		for( GameState child : listOfChildGameStates )
		{
			SearchStrategy search = new SearchStrategy(child, _miniMaxDepth, _alphaBetaDepth, abUse);
			retSearchValue = search.startSearch();
			nbrOfExpancdedNodes += search.getNbrOfExpNodes();
			if(retSearchValue > currMaxOrMinBest)
			{
				mostOptimum = child;
				currMaxOrMinBest = retSearchValue;
			}
		}

		if(p1Moves)
		{
			_p1nodePrintWirter.println("# nodes expanded "+nbrOfExpancdedNodes);
		}
		else
		{
			_p2nodePrintWirter.println("# nodes expanded "+nbrOfExpancdedNodes);
		}
		return mostOptimum;
	}

	public GameState developGame(int i)
	{
		Board board = new Board();
		Player player1=null;
		Player player2=null;
		//File file = new File(_fileKeren);
		//_board = "Keren";
		File file = new File(_fileNarvik);
		_board = "Narvik";
		//File file = new File(_fileSevastopol);
		//_board = "Sevastopol";
		//File file = new File(_fileSmolensk);
		//_board = "Smolensk";
		//File file = new File(_fileWesterplatte);
		//_board = "Westerplatte";
		board.readFile(file);
		board.initState();
		board.printBoard();
		int score = 0;
		if(i == 0)
		{
			player1 = new Player("P1", false, score);
			player2 = new Player("P2", false, score);
		}
		else if(i == 1)
		{
			player1 = new Player("P1", false, score);
			player2 = new Player("P2", true, score);
		}
		else if(i == 2)
		{
			player1 = new Player("P1", true, score);
			player2 = new Player("P2", false, score);
		}
		else
		{	
			player1 = new Player("P1", true, score);
			player2 = new Player("P2", true, score);
		}
		GameState startingGameState = new GameState(player1, player2, board);
		return startingGameState;
	}

	public static void main(String[] args) 
	{

		BeginWar war = new BeginWar();
		try 
		{
			war.warCommencement(war.developGame(0), false, false);
			_folder = "C:/Projects/CS440/workspace2/AdversarialSearch/output/";
			war.warCommencement(war.developGame(1), false, true);
			_folder = "C:/Projects/CS440/workspace2/AdversarialSearch/output/";
			war.warCommencement(war.developGame(2), true, false);
			_folder = "C:/Projects/CS440/workspace2/AdversarialSearch/output/";
			war.warCommencement(war.developGame(3), true, true);
			_folder = "C:/Projects/CS440/workspace2/AdversarialSearch/output/";
		} 
		catch (InterruptedException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
