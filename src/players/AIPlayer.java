package players;

import game.*;

import java.util.List;

/**
 * Created by manap on 1/14/2018.
 */
public class AIPlayer extends AbstractPlayer{
    public AIPlayer(int depth){
        super(depth);
    }
//    int the_score;
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
                    if((moves_list.get(i).getBardPlace().getCol()==7) &&(moves_list.get(i).getBardPlace().getRow()==7)
                            || (moves_list.get(i).getBardPlace().getCol()==7) &&(moves_list.get(i).getBardPlace().getRow()==0)
                            || (moves_list.get(i).getBardPlace().getCol()==0) &&(moves_list.get(i).getBardPlace().getRow()==7)
                            || (moves_list.get(i).getBardPlace().getCol()==0) &&(moves_list.get(i).getBardPlace().getRow()==0)){
                        result.move = moves_list.get(i);
                        return result;
                    }
                    boolean flag = false;
                    for (int j = 0; j < moves_list.size(); j++) {
                        if(!((moves_list.get(j).getBardPlace().getCol()==7) &&(moves_list.get(j).getBardPlace().getRow()==6)
                                || (moves_list.get(j).getBardPlace().getCol()==6) &&(moves_list.get(j).getBardPlace().getRow()==7)
                                || (moves_list.get(j).getBardPlace().getCol()==0) &&(moves_list.get(j).getBardPlace().getRow()==6)
                                || (moves_list.get(j).getBardPlace().getCol()==1) &&(moves_list.get(j).getBardPlace().getRow()==7)
                                || (moves_list.get(j).getBardPlace().getCol()==1) &&(moves_list.get(j).getBardPlace().getRow()==0)
                                || (moves_list.get(j).getBardPlace().getCol()==0) &&(moves_list.get(j).getBardPlace().getRow()==1)
                                || (moves_list.get(j).getBardPlace().getCol()==6) &&(moves_list.get(j).getBardPlace().getRow()==0)
                                || (moves_list.get(j).getBardPlace().getCol()==7) &&(moves_list.get(j).getBardPlace().getRow()==1)
                                || (moves_list.get(i).getBardPlace().getCol()==1) &&(moves_list.get(i).getBardPlace().getRow()==1)
                                || (moves_list.get(i).getBardPlace().getCol()==6) &&(moves_list.get(i).getBardPlace().getRow()==6)
                                || (moves_list.get(i).getBardPlace().getCol()==6) &&(moves_list.get(i).getBardPlace().getRow()==1)
                                || (moves_list.get(i).getBardPlace().getCol()==1) &&(moves_list.get(i).getBardPlace().getRow()==6))){
                            flag = true;
                            continue;
                        }
                    }
                    if(((moves_list.get(i).getBardPlace().getCol()==7) &&(moves_list.get(i).getBardPlace().getRow()==6)
                            || (moves_list.get(i).getBardPlace().getCol()==6) &&(moves_list.get(i).getBardPlace().getRow()==7)
                            || (moves_list.get(i).getBardPlace().getCol()==0) &&(moves_list.get(i).getBardPlace().getRow()==6)
                            || (moves_list.get(i).getBardPlace().getCol()==1) &&(moves_list.get(i).getBardPlace().getRow()==7)
                            || (moves_list.get(i).getBardPlace().getCol()==1) &&(moves_list.get(i).getBardPlace().getRow()==0)
                            || (moves_list.get(i).getBardPlace().getCol()==0) &&(moves_list.get(i).getBardPlace().getRow()==1)
                            || (moves_list.get(i).getBardPlace().getCol()==6) &&(moves_list.get(i).getBardPlace().getRow()==0)
                            || (moves_list.get(i).getBardPlace().getCol()==7) &&(moves_list.get(i).getBardPlace().getRow()==1)
                            || (moves_list.get(i).getBardPlace().getCol()==1) &&(moves_list.get(i).getBardPlace().getRow()==1)
                            || (moves_list.get(i).getBardPlace().getCol()==6) &&(moves_list.get(i).getBardPlace().getRow()==6)
                            || (moves_list.get(i).getBardPlace().getCol()==6) &&(moves_list.get(i).getBardPlace().getRow()==1)
                            || (moves_list.get(i).getBardPlace().getCol()==1) &&(moves_list.get(i).getBardPlace().getRow()==6)) && flag){
//                        i++;
                        continue;
                    }
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

    public double eval(int[][] board, AbstractPlayer abstractPlayer) {



        int sizeI = board.length;
        double myCoinNum = 0;
        double oppCoinNum = 0;
        /*
            coin parity heuristic
        */
        double maxCornerHeuristic = 0;
        double minCornerHeuristic = 0;

        int boardWeight[][] = {
                {100, -10, 11, 6, 6, 11, -10, 100},
                {-10, -20, 1, 2, 2, 1, -20, -10},
                {10, 1, 5, 4, 4, 5, 1, 10},
                {6, 2, 4, 2, 2, 4, 2, 6},
                {6, 2, 4, 2, 2, 4, 2, 6},
                {10, 1, 5, 4, 4, 5, 1, 10},
                {-10, -20, 1, 2, 2, 1, -20, -10},
                {100, -10, 11, 6, 6, 11, -10, 100},
        };

        for(int row = 0; row < sizeI; row++){
            for(int col = 0; col < sizeI; col++){
                /* here we check the whole of board and for each block if there is our coin, myCoinNum++
                        * if there is an opponent coin, oppCoinNum++
                        */
                if(board[row][col] == abstractPlayer.getBoardMark()) {
                    maxCornerHeuristic += boardWeight[row][col];
                    myCoinNum ++;
                }
                else if(board[row][col] == abstractPlayer.getOpponentBoardMark())
                    minCornerHeuristic -= boardWeight[row][col];
                oppCoinNum++;
            }
        }

        /*
          finally we have this formula to calculate the coin parity heuristic
         */
        double coinParityHeuristic = 100 * (myCoinNum - oppCoinNum)/(myCoinNum + oppCoinNum);

        /*
            mobility check heursitic
        */


//        int mobilityCount = 0;
//        for (int i = 0 ; i < sizeI; i++){
//            for (int j = 0 ; j < sizeI; j++){
//                mobilityCount += abstractPlayer.getOpponentBoardMark();
//            }
//        }

        /*
        check corners
         */

        if (board[0][0] == abstractPlayer.getBoardMark() || board[0][7] == abstractPlayer.getBoardMark() || board[7][0] == abstractPlayer.getBoardMark() || board[7][7] == abstractPlayer.getBoardMark()){
            maxCornerHeuristic += 100;
        }
        else {
            minCornerHeuristic -= 100;
        }

        double cornerHeu = 100 * (maxCornerHeuristic - minCornerHeuristic)/(maxCornerHeuristic + minCornerHeuristic);

        return 25 * (int)coinParityHeuristic + 50 * (int)cornerHeu;
    }
}
