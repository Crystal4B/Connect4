import java.awt.Color;

import javax.swing.JButton;

public class Minimax
{
	public static int[] computerMove(JButton board[][])
	{
		int bestScore = -Integer.MAX_VALUE;
		int bestMove[] = new int[2];
		for (int col = 0; col < board.length; col++)
		{
			for (int row = board.length-1; row > 0; row--)
			{
				if (board[row][col].getBackground().equals(Color.WHITE))
				{
					board[row][col].setBackground(Color.BLUE);
					int score = minimax(board, 0, false, -Integer.MAX_VALUE, Integer.MAX_VALUE);
					board[row][col].setBackground(Color.WHITE);
					if (score > bestScore)
					{
						bestScore = score;
						bestMove[0] = row;
						bestMove[1] = col;
					}
				}
			}
		}
		return bestMove;
	}

	private static int minimax(JButton board[][], int depth, boolean isMaximizing, int alpha, int beta)
	{
		EndState result = Window.isVictory(board);
		if (result != null)
		{
			if (result.getState().equals(Color.BLUE)) return 1; 
			else if (result.getState().equals(Color.RED)) return -1;
			else if (result.getState().equals(Color.WHITE)) return 0;
		}

		if (depth == 5)
		{
			return 0;
		}

		int bestScore = isMaximizing ? -Integer.MAX_VALUE : Integer.MAX_VALUE;
		for (int col = 0; col < board.length; col++)
		{
			for (int row = board.length-1; row > 0; row--)
			{
				if(board[row][col].getBackground().equals(Color.WHITE))
				{
					board[row][col].setBackground(isMaximizing ? Color.BLUE : Color.RED);
					if (isMaximizing)
					{
						int score = minimax(board, depth+1, false, alpha, beta);
						bestScore = Math.max(score, bestScore);
						alpha = Math.max(alpha, bestScore);
					}
					else
					{
						int score = minimax(board, depth+1, true, alpha, beta);
						bestScore = Math.min(score, bestScore);
						beta = Math.min(beta, bestScore);
					}
					board[row][col].setBackground(Color.WHITE);

					if (beta <= alpha)
					{
						break;
					}
				}
			}
		}
		return bestScore;
	}
}
