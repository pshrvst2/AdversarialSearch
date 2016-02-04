/**
 * 
 */
package Animation;

import java.awt.Font;
import edu.princeton.cs.introcs.StdDraw;
import GameData.UnitSquare;
import GameState.Board;
import GameState.GameState;

/**
 * @author Piyush
 * @Info: This class used Std Draw library stdlib-package.jar for animation. 
 */
public class Animation {
	
	/*public static String _fileKeren = "C:/Projects/CS440/workspace2/AdversarialSearch/game_boards/Keren.txt";
	public static String _fileNarvik = "C:/Projects/CS440/workspace2/AdversarialSearch/game_boards/Narvik.txt";
	public static String _fileSevastopol = "C:/Projects/CS440/workspace2/AdversarialSearch/game_boards/Sevastopol.txt";
	public static String _fileSmolensk = "C:/Projects/CS440/workspace2/AdversarialSearch/game_boards/Smolensk.txt";
	public static String _fileWesterplatte = "C:/Projects/CS440/workspace2/AdversarialSearch/game_boards/Westerplatte.txt";
	*/
	private String _player1blue;
	private String _player2green;
	private GameState _gameState;
	private int _rows;
	private int _cols;
	private Font _font;
	
	public Animation(GameState gameState) 
	{
		this._gameState = gameState;
		this._player1blue = gameState.getP1().getPlayerId();
		this._player2green = gameState.getP2().getPlayerId();
		Board b = gameState.getBoard();
		this._rows = b.get_nbrOfRows();
		this._cols = b.get_nbrOfColumns();
		startDisplay();
		
	}

	public String get_bluePlayer() {
		return _player1blue;
	}

	public void set_bluePlayer(String _bluePlayer) {
		this._player1blue = _bluePlayer;
	}

	public String get_greenPlayer() {
		return _player2green;
	}

	public void set_greenPlayer(String _greenPlayer) {
		this._player2green = _greenPlayer;
	}

	public GameState getGameState() {
		return _gameState;
	}

	public void setGameState(GameState gameState) {
		this._gameState = gameState;
	}

	public int get_rows() {
		return _rows;
	}

	public void set_rows(int _rows) {
		this._rows = _rows;
	}

	public int get_cols() {
		return _cols;
	}

	public void set_cols(int _cols) {
		this._cols = _cols;
	}
	
	private void startDisplay()
	{
		int style = Font.BOLD;
		Font font = new Font ("Arial", style , 30);
		_font = font;
		int scale = 150;
		int size = 600;
		while(_cols*scale > size || _rows*scale > size)
		{
			scale = scale - 5;
			if(scale <= 0){
				scale = 1;
				break;
			}
		}
		StdDraw.setCanvasSize(_cols*scale, _rows*scale);
		StdDraw.setXscale(0, _cols);
        StdDraw.setYscale(0, _rows);
        StdDraw.setFont(font);
        drawInitialState(font);
		StdDraw.show(10);
	}
	
	public void drawInitialState(Font font)
	{
		// start with a blank board
		for(int i = 0; i < _rows; i++)
		{
			for(int col = 0; col < _cols; col++)
			{
				StdDraw.setPenColor(StdDraw.BLACK);
				StdDraw.setPenRadius();
				StdDraw.filledSquare(col+0.5, (_rows-1-i)+0.5, .45 );
			}
		}
		
		//draw everything.. players, scores, squares
		for(int row = 0; row < _rows; row++)
		{
			for(int col = 0; col < _cols; col++)
			{
				StdDraw.setPenColor(StdDraw.BLACK);
				StdDraw.setFont(font);
				int value = _gameState.getBoard().get_square().get(row).get(col).getAssetValue();
				StdDraw.text(col+0.5, (_rows-1-row)+0.5, ""+ value);
			}
		}
		
		// single space
		/*for(int i = 0; i < _rows; i++)
		{
			for(int j = 0; j < _cols; j++)
			{
				StdDraw.setFont(font);
				StdDraw.setPenColor(StdDraw.BLACK);
				UnitSquare us =  _gameState.getBoard().get_square().get(i).get(j);
				int value = us.getAssetValue();
				StdDraw.text(col+0.5, (_rows-1-row)+0.5, ""+ value);
			}
		}*/
	}
	
	public void displayCurrentGameState()
	{
		this.clearBoard();
		this.fetchAndDrawSquareAssetsAndScores();
		this.displayScores();
		StdDraw.show(5);
	}
	
	public void fetchAndDrawSquareAssetsAndScores()
	{
		for(int row = 0; row < _rows; row++)
		{
			for(int col = 0; col < _cols; col++)
			{
				StdDraw.setFont(_font);
				StdDraw.setPenColor(StdDraw.BLACK);
				int x = _gameState.getBoard().get_square().get(row).get(col).getAssetValue();
				StdDraw.text(col+0.5, (_rows-1-row)+0.5, ""+ x);
			}
		}
		
		// report player scores on screen
		StdDraw.setFont(_font);
		StdDraw.setPenColor(StdDraw.ORANGE);
		String p1Name = "" + _gameState.getP1().getPlayerId();
		String p2Name = "" + _gameState.getP2().getPlayerId();
		String p1Score = ""+ _gameState.getP1().getScore();
		String p2Score = ""+ _gameState.getP2().getScore();
		
		StdDraw.text(1, 6.1, "Player1 [" + p1Name + "] : " + p1Score);
		StdDraw.text(5, 6.1, "Player2 [" + p2Name + "] : " + p2Score);
	}
	
	private void displayScores()
	{
		StdDraw.setFont(_font);
		StdDraw.setPenColor(StdDraw.BLACK);
		String p1Name = "" + _gameState.getP1().getPlayerId();
		String p2Name = "" + _gameState.getP2().getPlayerId();
		String p1Score = ""+ _gameState.getP1().getScore();
		String p2Score = ""+ _gameState.getP1().getScore();
		
		StdDraw.text(1, 6.1, p1Name + " = " + p1Score);
		StdDraw.text(5, 6.1, p2Name + " = " + p2Score);
	}
	
	public void clearBoard()
	{
		StdDraw.clear();
		for(int i = 0; i < _rows; i++)
		{
			for(int j = 0; j < _cols; j++)
			{
				StdDraw.setPenRadius();
				StdDraw.setPenColor(StdDraw.YELLOW);
				StdDraw.filledSquare(j+0.5, (_rows-1-i)+0.5, .45 );
			}
		}
		
		for(int i = 0; i < _rows; i++)
		{
			for(int j = 0; j < _cols; j++)
			{
				UnitSquare unitSq = _gameState.getBoard().get_square().get(i).get(j);
				if(unitSq.isConquered())
				{
					if(unitSq.getOccupiedByPlayer().equals(_player1blue))
					{
						StdDraw.setPenRadius();
						StdDraw.setPenColor(StdDraw.BLUE);
						StdDraw.filledCircle(j+0.5, (_rows-1-i)+0.5, .45 );
					}
					else
					{
						StdDraw.setPenRadius();
						StdDraw.setPenColor(StdDraw.GREEN);
						StdDraw.filledCircle(j+0.5, (_rows-1-i)+0.5, .45 );
					}
				}
			}
		}
	}
	
	/*public static void main(String[] args) {
		Board board = new Board();
		Player player1=null;
		Player player2=null;
		File file = new File(_fileKeren);
		board.readFile(file);
		//_square = new ArrayList<ArrayList<UnitSquare>>();
		board.initState();
		board.printBoard();
		boolean alphaBetaUse = false;
		boolean miniMaxUse = false;
		int score = 0;
		player1 = new Player("P1", alphaBetaUse, miniMaxUse, score);
		player2 = new Player("P2", alphaBetaUse, miniMaxUse, score);
		GameState startingGameState = new GameState(player1, player2, board);
		Animation animation = new Animation(startingGameState);
	}*/
	
	

}
