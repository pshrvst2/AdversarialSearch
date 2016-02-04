/**
 * 
 */
package SearchStrategy;

import java.util.ArrayList;

import GameState.GameState;
import Move.Move;

/**
 * @author Piyush
 *
 */
public class SearchStrategy 
{
	private int mmDepth;
	private int abDepth;
	private GameState rootNode;
	private boolean useAlphaBeta;
	private int nbrOfExpandedNodes;

	public SearchStrategy(GameState rootNode, int mmDepth, int abDepth, boolean useAlphaBeta)
	{
		this.rootNode = rootNode;
		this.useAlphaBeta = useAlphaBeta;
		this.mmDepth = mmDepth;
		this.abDepth = abDepth;
		this.nbrOfExpandedNodes = 0;
	}
	
	public void setMiniMaxDepthLimit(int depth){
		this.mmDepth = depth;
	}
	public void setAlphaBetaDepthLimit(int depth){
		this.abDepth = depth;
	}

	public int getNbrOfExpNodes() {
		return nbrOfExpandedNodes;
	}
	
	public int evaluationFuntion(GameState gameState, boolean isNodeMax)
	{
		int maxScore = gameState.getMaxPlayer().getScore();
		int minScore = gameState.getMinPlayer().getScore();	
		int heuristic;
		int scoreDifference = maxScore - minScore;
		heuristic = scoreDifference;
		return heuristic;
		
	}
	
	public int startSearch()
	{
		this.nbrOfExpandedNodes = 0;
		int result; 
		if(this.useAlphaBeta)
		{
			result = alphaBetaSearch(this.rootNode, this.abDepth, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
		}
		else
		{
			// use minimax search strategy
			result = minimaxSearch(this.rootNode, this.mmDepth, true); 
		}
		return result;
	}
	
	public int minimaxSearch(GameState root, int depthLimit, boolean isNodeMax)
	{
		if( root.isTerminalState() || depthLimit == 0 )
		{
			// return the score if its the terminal child
			return evaluationFuntion(root, isNodeMax);
		}
		else
		{
			ArrayList<Move> listofAvailableMoves;
			Move move;
			GameState child;
			int childMiniMaxValue;
			
			if(isNodeMax)
			{
				int miniMaxValue = Integer.MIN_VALUE;
				listofAvailableMoves = root.getListOfFeasibleMoves(root.getMaxPlayer());
				for(int moveIndex = 0; moveIndex < listofAvailableMoves.size(); moveIndex++)
				{
					move = listofAvailableMoves.get(moveIndex);
					child = root.getGameStateAfterPlayerMoves(root.getMaxPlayer(), move);
					this.nbrOfExpandedNodes++;
					childMiniMaxValue = minimaxSearch(child, depthLimit-1, false);
					miniMaxValue = Math.max(miniMaxValue, childMiniMaxValue);
				}
				return miniMaxValue;
			}
			else
			{ 
				int miniMaxValue = Integer.MAX_VALUE;
				listofAvailableMoves = root.getListOfFeasibleMoves(root.getMinPlayer());
				for(int moveIndex = 0; moveIndex < listofAvailableMoves.size(); moveIndex++)
				{
					move = listofAvailableMoves.get(moveIndex);
					child = root.getGameStateAfterPlayerMoves(root.getMinPlayer(), move);
					this.nbrOfExpandedNodes++;
					childMiniMaxValue = minimaxSearch(child, depthLimit-1, true);
					miniMaxValue = Math.min(miniMaxValue, childMiniMaxValue);
				}
				return miniMaxValue;
			}
		}
	}
	
	public int alphaBetaSearch(GameState rootNode, int depthLimit, int alpha, int beta, boolean isMaxNode){
		if( rootNode.isTerminalState() || depthLimit == 0 )
		{
			return evaluationFuntion(rootNode, isMaxNode);
		}
		else
		{
			ArrayList<Move> MoveOptions;
			GameState childStateNode;
			if(isMaxNode)
			{
				MoveOptions = rootNode.getListOfFeasibleMoves(rootNode.getMaxPlayer());
				int miniMaxValue = alpha;
				int childValue;
				for(int moveIndex = 0; moveIndex < MoveOptions.size(); moveIndex++)
				{
					childStateNode = rootNode.getGameStateAfterPlayerMoves(rootNode.getMaxPlayer(), MoveOptions.get(moveIndex));
					this.nbrOfExpandedNodes++;
					childValue = alphaBetaSearch(childStateNode, depthLimit-1, miniMaxValue, beta, false);
					miniMaxValue = Math.max(miniMaxValue, childValue);
					alpha = Math.max(alpha, miniMaxValue);
					if(beta < alpha)
					{
						return alpha;
					}
				}
				return miniMaxValue;
			}
			else
			{ 
				MoveOptions = rootNode.getListOfFeasibleMoves(rootNode.getMinPlayer());
				int miniMaxValue = beta;
				int childValue;
				for(int moveIndex = 0; moveIndex < MoveOptions.size(); moveIndex++)
				{
					childStateNode = rootNode.getGameStateAfterPlayerMoves(rootNode.getMinPlayer(), MoveOptions.get(moveIndex));
					this.nbrOfExpandedNodes++;
					childValue = alphaBetaSearch(childStateNode, depthLimit-1, alpha, miniMaxValue, true);
					miniMaxValue = Math.min(miniMaxValue, childValue);
					beta = Math.min(beta,  miniMaxValue);
					if(beta < alpha)
					{
						return beta;
					}
				}
				return miniMaxValue;
			}
		}
	}
}
