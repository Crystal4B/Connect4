import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends JFrame
{
	private JButton buttonGrid[][];
	private Color player;
	private boolean isSingle;

	public Window(boolean isSingle)
	{
		super("Connect4");

		buttonGrid = new JButton[7][7];
		player = Color.RED;
		this.isSingle = isSingle;

		JPanel gamePanel = new JPanel();
		gamePanel.setLayout(new GridLayout(7, 7));
		for (int row = 0; row < 7; row++)
		{
			for (int col = 0; col < 7; col++)
			{
				buttonGrid[row][col] = new JButton("");
				if (row == 0)
				{
					buttonGrid[row][col].setText("v");
					buttonGrid[row][col].setFont(new Font("Arial", Font.PLAIN, 45));
					buttonGrid[row][col].addActionListener(buttonListener);
					buttonGrid[row][col].setFocusPainted(false);
				}
				else
				{
					buttonGrid[row][col].setEnabled(false);
				}
				buttonGrid[row][col].setContentAreaFilled(false);
				buttonGrid[row][col].setOpaque(true);
				buttonGrid[row][col].setBackground(Color.WHITE);
				gamePanel.add(buttonGrid[row][col]);
			}
		}

		setSize(500, 500);
		setContentPane(gamePanel);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		setVisible(true);
	}

	/**
	 * Listener for executing moves as buttons are pressed
	 */
	ActionListener buttonListener = (ActionEvent e) ->
	{
		for (int i = 0; i < buttonGrid.length; i++)
		{
			if (buttonGrid[0][i].equals(e.getSource()))
			{
				for (int j = buttonGrid.length-1; j > 0; j--)
				{
					if (buttonGrid[j][i].getBackground().equals(Color.WHITE))
					{
						buttonGrid[j][i].setBackground(player);
						player = player.equals(Color.RED) ? Color.BLUE : Color.RED;
						EndState playerWon = isVictory(buttonGrid);
						if (playerWon != null)
						{
							endScreen();
						}

						if (isSingle && playerWon == null)
						{
							// ai move
							int bestMove[] = Minimax.computerMove(buttonGrid);
							buttonGrid[bestMove[0]][bestMove[1]].setBackground(player);

							EndState computerWon = isVictory(buttonGrid);
							if (computerWon != null)
							{
								endScreen();
							}
							player = player.equals(Color.RED) ? Color.BLUE : Color.RED;
						}
						break;
					}
				}
				break;
			}
		}
	};

	public void endScreen()
	{
		for (int i = 0; i < buttonGrid.length; i++)
		{
			buttonGrid[0][i].setEnabled(false);
		}
	}

	public static EndState isVictory(JButton board[][])
	{
		boolean freespace = false;
		for (int row = 1; row < board.length; row++)
		{
			for (int col = 0; col < board.length; col++)
			{
				Color color = board[row][col].getBackground();
				if (color.equals(Color.WHITE))
				{
					freespace = true;
					continue;
				}

				// Horizontal
				if (col + 3 < board.length &&
					board[row][col+1].getBackground().equals(color) &&
					board[row][col+2].getBackground().equals(color) &&
					board[row][col+3].getBackground().equals(color))
				{
					int points[][] = {
						{row, col},
						{row, col+1},
						{row, col+2},
						{row, col+3}
					};
					return new EndState(color, points);
				}

				if (row + 3 < board.length)
				{
					// Vertical
					if (board[row+1][col].getBackground().equals(color) &&
						board[row+2][col].getBackground().equals(color) &&
						board[row+3][col].getBackground().equals(color))
					{
						int points[][] = {
							{row, col},
							{row, col+1},
							{row, col+2},
							{row, col+3}
						};
						return new EndState(color, points);
					}

					// Diagnal right
					if (col + 3 < board.length &&
						board[row+1][col+1].getBackground().equals(color) &&
						board[row+2][col+2].getBackground().equals(color) &&
						board[row+3][col+3].getBackground().equals(color))
					{
						int points[][] = {
							{row, col},
							{row, col+1},
							{row, col+2},
							{row, col+3}
						};
						return new EndState(color, points);
					}

					// Diagnal left
					if (col -3 >= 0 &&
						board[row+1][col-1].getBackground().equals(color) &&
						board[row+2][col-2].getBackground().equals(color) &&
						board[row+3][col-3].getBackground().equals(color))
					{
						int points[][] = {
							{row, col},
							{row, col+1},
							{row, col+2},
							{row, col+3}
						};
						return new EndState(color, points);
					}
				}
			}
		}
		if (!freespace)
		{
			return new EndState(Color.WHITE); // tie
		}
		return null; // unresolved
	}
}