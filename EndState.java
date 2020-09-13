import java.awt.Color;

public class EndState
{
	private Color state;
	private int points[][];

	public EndState(Color state)
	{
		this.state = state;
	}

	public EndState(Color state, int points[][])
	{
		this(state);
		this.points = points;
	}

	public Color getState()
	{
		return state;
	}

	public int[][] getPoints()
	{
		return points;
	}
}
