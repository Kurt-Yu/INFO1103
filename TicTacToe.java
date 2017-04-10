import java.util.*;

public class TicTacToe{
	public static void main(String[] args){
		char[][] cells = new char[][]{{' ', ' ', ' '}, {' ', ' ', ' '}, {' ', ' ', ' '}};
		int count = 0;
		Scanner scan = new Scanner(System.in);

		while (true){
			int i = scan.nextInt();
			int j = scan.nextInt();
			cells[j][i] = (count % 2 == 0)? 'X' : 'O';   
			count++;

			if ((cells[0][0] == cells[0][1] && cells[0][1] == cells[0][2] && cells[0][0] != ' ')       //check row
				|| (cells[1][0] == cells[1][1] && cells[1][1] == cells[1][2] && cells[1][0] != ' ')    
				|| (cells[2][0] == cells[2][1] && cells[2][1] == cells[2][2] && cells[2][0] != ' ')    
				|| (cells[0][0] == cells[1][0] && cells[1][0] == cells[2][0] && cells[0][0] != ' ')    //check column
				|| (cells[0][1] == cells[1][1] && cells[1][1] == cells[2][1] && cells[0][1] != ' ')
				|| (cells[0][2] == cells[1][2] && cells[1][2] == cells[2][2] && cells[0][2] != ' ')
				|| (cells[0][0] == cells[1][1] && cells[1][1] == cells[2][2] && cells[0][0] != ' ')    //check diagonals
				|| (cells[0][2] == cells[1][1] && cells[1][1] == cells[2][0] && cells[0][2] != ' ')){
				if (count % 2 == 1) System.out.println("X wins!");
				else System.out.println("O wins!");
				break;
			}
			else if (cells[0][0] != ' ' && cells[0][1] != ' ' && cells[0][2] != ' ' && 
					cells[1][0] != ' '  && cells[1][1] != ' ' && cells[1][2] != ' ' && 
					cells[2][0] != ' ' && cells[2][1] != ' ' && cells[2][2] != ' '){
				System.out.println("Draw");
				break;
			}

			System.out.println();
			for (int m = 0; m < 3; m++){
				System.out.println(cells[m][0] + "|" + cells[m][1] + "|" + cells[m][2]);
				if (m != 2) System.out.println("-----");
			}
			System.out.println();
		}

		System.out.println();
		for (int m = 0; m < 3; m++){
			System.out.println(cells[m][0] + "|" + cells[m][1] + "|" + cells[m][2]);
			if (m != 2) System.out.println("-----");
		}
		System.out.println();
	}
}