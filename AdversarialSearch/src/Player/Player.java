/**
 * 
 */
package Player;

/**
 * @author Piyush
 *
 */
public class Player {
	
	private String playerId;
	private boolean alphaBetaUse;
	//private boolean minMaxUse = false;
	private boolean maxPlayer;
	private int score;
	
	public Player() {
		// TODO Auto-generated constructor stub
	}
	
	/*public Player(String playerId, boolean alphaBetaUse, boolean minMaxUse, int score) 
	{
		this.playerId = playerId;
		this.alphaBetaUse = alphaBetaUse;
		//this.minMaxUse = minMaxUse;
		this.score = score;
	}*/
	
	public Player(String playerId, boolean alphaBetaUse, int score) 
	{
		this.playerId = playerId;
		this.alphaBetaUse = alphaBetaUse;
		this.score = score;
	}

	public String getPlayerId() {
		return playerId;
	}
	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}
	public boolean isAlphaBetaUse() {
		return alphaBetaUse;
	}
	public void setAlphaBetaUse(boolean alphaBetaUse) {
		this.alphaBetaUse = alphaBetaUse;
	}
	/*public boolean isMiniMaxUse() {
		return minMaxUse;
	}
	public void setMiniMaxUse(boolean miniMaxUse) {
		this.minMaxUse = miniMaxUse;
	}*/
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		if(score > 4)
		{
			System.out.println();
		}
		this.score = score;
	}
	
	public Player getPlayerCopy() 
	{
		Player p = new Player(this.playerId, this.alphaBetaUse, this.score);
		p.maxPlayer = this.maxPlayer;
		return p;	
	}
	
	/*public Player getPlayerCopy() 
	{
		Player p = new Player(playerId, alphaBetaUse, score);
		p = this;
		return p;	
	}*/
	

	public boolean isMaxPlayer() {
		return maxPlayer;
	}

	public void setMaxPlayer(boolean maxPlayer) {
		this.maxPlayer = maxPlayer;
	}

}
