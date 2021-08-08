package players;

import game.AbstractPlayer;
import game.BoardSquare;
import game.Move;
import game.OthelloGame;

import java.util.List;

/**
 * Created by manap on 1/16/2018.
 */
public class GreedyPlayer extends AbstractPlayer{
    public GreedyPlayer(int depth){
        super(depth);
    }
    @Override
    public BoardSquare play(int[][] tab) {
        int black_best=Integer.MIN_VALUE;
        int white_best=Integer.MAX_VALUE;
        Move m = new Move(tab, new BoardSquare(-1,-1));
        double s = 0;
        int [][] board = new int[tab.length][tab.length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j] = tab[i][j];
            }
        }
        Move chMove = minimax(board, 0,1, black_best, white_best, m, s).move;
        chMove.setBoard(tab);
        if(chMove!=null){
            System.out.println("chosen move col is: "+chMove.getBardPlace().getCol() + " row is: " + chMove.getBardPlace().getRow());
        }
        return chMove.getBardPlace();
    }

    private class MoveScore{
        public Move move;
        public double score;
        public MoveScore(Move move, double score){
            this.move = move;
            this.score = score;
        }
    }

    //our player is the one who chooses the max
    private MoveScore minimax(int[][] board, int depth, int max_depth, double black_best, double white_best, Move chosen_move, double chosen_score){
        MoveScore result = new MoveScore(chosen_move, chosen_score);
        OthelloGame game = new OthelloGame();
        List<Move> moves_list = game.getValidMoves(board, getBoardMark());
//        chosen_move = moves_list.get(0);
        if(depth == max_depth){
            chosen_score = eval(board,this);
        }
        else{
            if(moves_list==null)
                chosen_score = eval(board,this);
            else{
                for (int i = 0; i < moves_list.size(); i++) {
                    int [][] board2 = new int[board.length][board.length];
                    for (int j = 0; j < board.length; j++) {
                        for (int z = 0; z < board.length; z++) {
                            board2[j][z] = board[j][z];
                        }
                    }
                    int[][] new_board = game.do_move(board2, moves_list.get(i).getBardPlace(), this);
                    // they get values in the next line
                    result = minimax(new_board, depth+1, max_depth, black_best, white_best, chosen_move, chosen_score);
                    chosen_score = result.score;
                    if(result.move.getBardPlace().getRow() != -1 && result.move.getBardPlace().getCol() != -1)
                        chosen_move = result.move;
//                    for (int j = 0; j < new_board.length; j++) {
//                        for (int z = 0; z < new_board.length; z++) {
//                            board[j][z] = board2[j][z];
//                        }
//                    }
                    if(depth%2==0 && chosen_score > black_best){
                        if(chosen_score > white_best)
                            break;
                        else{
                            chosen_move = moves_list.get(i);
                            black_best = chosen_score;
                        }
                    }
                    if(depth%2==1 && chosen_score < white_best){
                        if(chosen_score < black_best)
                            break;
                        else{
                            chosen_move = moves_list.get(i);
                            white_best = chosen_score;
                        }
                    }
                    if(this.getBoardMark()==1){
                        result.score =  black_best;
//                        return result;
                    }
                    else if(this.getBoardMark()==-1){
                        result.score = white_best;
//                        return result;
                    }
                }
            }
        }
        result.score = chosen_score;
        result.move = chosen_move;
        return result;
    }

    public static double eval(int[][] othelloBoard, AbstractPlayer abstractPlayer) {

        /*
         * this part is for calculating the coin parity heuristic
         *
         * here we check the whole of the board and for each block if there is our coin then myCoinNum++
         * else if there is an opponent coin, oppCoinNum++
         */
        OthelloGame othelloGame = new OthelloGame();
//        OthelloBoard othelloBoard = new OthelloBoard(othelloGame);

        int myCoinNum = 0;
        int oppCoinNum = 0;

        for(int row = 0; row < othelloGame.size; row++){
            for(int col = 0; col < othelloGame.size; col++){

                if(othelloBoard[row][col] == abstractPlayer.getBoardMark()) {
                    myCoinNum++;
                }
                else if(othelloBoard[row][col] == abstractPlayer.getOpponentBoardMark()) {
                    oppCoinNum++;
                }
            }
        }
        //finally we have this formula to calculate the coin parity heuristic
        double coinParityHeuristic = 100 *((myCoinNum - oppCoinNum)/(myCoinNum + oppCoinNum));

        /*
         * here we declare the mobility heuristic
         */

        return coinParityHeuristic;
    }
}
