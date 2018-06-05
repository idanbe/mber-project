package com.example.administrator.game_4_in_a_row;


import android.util.Log;

import java.util.Random;

//this class make find next move of computer
public class AiMove
{
    private final int Zero=0,ONE=1,TWO=2,Three=3,Four=4,Five=5,Six=6,Seven=7;
    private final String EMPTY ="E";
    private final String PLAYER1="R";
    private final String PLAYER2="Y";
    private String [][] cell_arr ;
    private int i,j;
    private Random randomGenerator;
    private int randomCol;
    private int check;
    private final int NotFind =-1;


    public AiMove()
    {
        cell_arr =new String[Six][Seven]; //array of board game
        clear_cell_Arr(); //clear board game
    }

    public int Ai_move() //computer move
    {
       if(cell_arr[Five][Three].equals(EMPTY))
       {
           return Three;
       }

        check=find_Ai_threeSequence();  //find three sequence coins of computer
        if(check!=NotFind)              // to complete 4 sequence and win
        {
            return check; //return next move
        }
        check=find_rival_threeSequence(); //find rival three sequence coins
        if(check!=NotFind)                //and block him for win
        {
            return check;
        }
        check=find_Ai_twoSequence();
        if(check !=NotFind)
        {
            return check;
        }
        check=find_rival_twoSequence();
        if(check !=NotFind)
        {
            return check;
        }
        check=find_Ai_OneCell();
        if(check!=NotFind)
        {
            return check;
        }
        return getRandomCol();

    }


    //Y== color of computer , E == empty cell
    //find three sequence YYYE/EYYY/YEYY...
    private int find_Ai_threeSequence()
    {
        check=find_Sequence(PLAYER1,Three);
        return check;
    }
    //find two sequence YYEE/EEYY/EYEY..
    private int find_Ai_twoSequence()
    {
        check=find_Sequence(PLAYER1,TWO);
        return check;
    }

    //find one cell: YEEE/EYEE/EEYE/EEEY
    private int find_Ai_OneCell()
    {   check=find_Sequence(PLAYER1,ONE);
        return check;
    }
    //R= COLOR OF PLAYER
    //find rival three sequence of R: RRRE/ERRR/RERR/RRER
    private int find_rival_threeSequence()
    {
        check=find_Sequence(PLAYER2,Three);
        return check;

    }
    //find two sequence of R:RREE/EERR/ERER/RERE
    private int find_rival_twoSequence()
    {
        check=find_Sequence(PLAYER2,TWO);
        return check;

    }

    //this method get player to search and sequence
    private int find_Sequence(String player,int seq)
    {
        for(i=Zero;i<Six;i++) {
            for (j = Zero; j < Seven; j++)
            {
                check=check_up(i, j, player,seq);
                if (check!=NotFind)
                {
                    return check ;
                }
                check=check_Down(i, j, player,seq);
                if (check!=NotFind)
                {
                    return check;
                }
                check=check_Right(i, j, player,seq);
                if (check!=NotFind) {
                    return check;
                }
                check=check_Left(i, j, player,seq);
                if (check!=NotFind) {
                    return check;
                }
                check=check_Diagonal_up_Right(i, j, player,seq);
                if (check!=NotFind) {
                    return check;
                }
                check=check_Diagonal_up_Left(i, j, player,seq);
                if (check!=NotFind) {
                    return check;
                }
                check=check_Diagonal_Down_Right(i, j, player,seq);
                if (check!=NotFind) {
                    return check;
                }
                check=check_Diagonal_Down_Left(i, j, player,seq);
                if ((check)!=NotFind) {
                    return check;
                }
            }
        }
        return NotFind;
    }



    //get a random col for insert coin
    private int getRandomCol()
    {
        randomGenerator = new Random();
        do
        {
            randomCol = randomGenerator.nextInt(Seven);
        }
        while (check_ifCol_full(randomCol));

        return randomCol;
    }




    //clear array of board
    private void clear_cell_Arr()
    {
        for (i =Zero; i < Six; i++)
        {
            for (j = Six; j < Seven; j++)
            {
                cell_arr[i][j]=EMPTY;
            }
        }
    }

    //set array of board
    public void setCell_arr(String[][] arr)
    {
        for(i=Zero;i<Six;i++)
        {
            for(j=Zero;j<Seven;j++)
            {
                cell_arr[i][j]=arr[i][j];
            }
        }
    }

    //check if col is full
    private boolean check_ifCol_full(int col)
    {
        int i=Five;
        while ((i>=Zero)&&(!cell_arr[i][col].equals(EMPTY)))//check if empty cell in col
        {
            i=i-ONE;
        }
        if(i==-ONE)//col is full
        {
            return true;
        }
        return false;  //col not full
    }



    private int check_up(int r,int c,String player,int seq)   // r=row , c=col
    {
        if(r>TWO)
        {
            if(seq==Three)
            {
                if((cell_arr[r][c].equals(player))&&(cell_arr[r-ONE][c].equals(player))
                        &&(cell_arr[r-TWO][c].equals(player))&&(cell_arr[r-Three][c].equals(EMPTY)))
                {
                    return c;
                }
            }
            if(seq==TWO)
            {
                if((cell_arr[r][c].equals(player))&&(cell_arr[r-ONE][c].equals(player))
                        &&(cell_arr[r-TWO][c].equals(EMPTY))&&(cell_arr[r-Three][c].equals(EMPTY)))
                {
                    return c;
                }
            }
            if(seq==ONE)
            {
                if((cell_arr[r][c].equals(player))&&(cell_arr[r-ONE][c].equals(EMPTY))
                        &&(cell_arr[r-TWO][c].equals(EMPTY))&&(cell_arr[r-Three][c].equals(EMPTY)))
                {
                    return c;
                }
            }
        }
        return NotFind;
    }


    private int check_Down(int r,int c,String player,int seq)   // r=row , c=col
    {
        if(seq==Three)
        {

        }
        if(seq==TWO)
        {

        }
        if(seq==ONE)
        {

        }

        return NotFind;
    }


    private int check_Right(int r,int c,String player,int seq)   // r=row , c=col
    {
        if(c<Four)
        {
            if(seq==Three)
            {
                if((cell_arr[r][c].equals(player))&&(cell_arr[r][c+ONE].equals(player))
                        &&(cell_arr[r][c+TWO].equals(player))&&(cell_arr[r][c+Three].equals(EMPTY)))
                {
                    if((r<Five)&&((cell_arr[r+ONE][c+Three].equals(EMPTY))))
                    {
                        return NotFind;
                    }
                    return c+Three ;
                }
                if((cell_arr[r][c].equals(player))&&(cell_arr[r][c+ONE].equals(player))
                        &&(cell_arr[r][c+TWO].equals(EMPTY))&&(cell_arr[r][c+Three].equals(player)))
                {
                    if((r<Five)&&((cell_arr[r+ONE][c+TWO].equals(EMPTY))))
                    {
                        return NotFind;
                    }
                    return c+TWO ;
                }
            }
            if(seq==TWO)
            {
                if((cell_arr[r][c].equals(player))&&(cell_arr[r][c+ONE].equals(player))
                        &&(cell_arr[r][c+TWO].equals(EMPTY))&&(cell_arr[r][c+Three].equals(EMPTY)))
                {
                    if((r<Five)&&((cell_arr[r+ONE][c+TWO].equals(EMPTY))))
                    {
                        return NotFind;
                    }
                    return c+TWO ;
                }
                if((cell_arr[r][c].equals(player))&&(cell_arr[r][c+ONE].equals(EMPTY))
                        &&(cell_arr[r][c+TWO].equals(player))&&(cell_arr[r][c+Three].equals(EMPTY)))
                {
                    if((r<Five)&&((cell_arr[r+ONE][c+ONE].equals(EMPTY))))
                    {
                        return NotFind;
                    }
                    return c+ONE ;
                }
            }
            if(seq==ONE)
            {
                if((cell_arr[r][c].equals(player))&&(cell_arr[r][c+ONE].equals(EMPTY))
                        &&(cell_arr[r][c+TWO].equals(EMPTY))&&(cell_arr[r][c+Three].equals(EMPTY)))
                {
                    if((r<Five)&&((cell_arr[r+ONE][c+ONE].equals(EMPTY))))
                    {
                        return NotFind;
                    }
                    return c+ONE ;
                }
            }
        }
        return NotFind;
    }


    private int check_Left(int r,int c,String player,int seq)   // r=row , c=col
    {
        if(c>TWO)
        {
                if (seq == Three)
                {
                    if ((cell_arr[r][c].equals(player)) && (cell_arr[r][c - ONE].equals(player))
                            && (cell_arr[r][c - TWO].equals(player)) && (cell_arr[r][c - Three].equals(EMPTY))) {
                        if ((r < Five) && ((cell_arr[r + ONE][c - Three].equals(EMPTY))))
                        {
                            return NotFind;
                        }
                        return c - Three;
                    }
                    if ((cell_arr[r][c].equals(player)) && (cell_arr[r][c - ONE].equals(player))
                            && (cell_arr[r][c - TWO].equals(EMPTY)) && (cell_arr[r][c - Three].equals(player))) {
                        if ((r < Five) && ((cell_arr[r + ONE][c - TWO].equals(EMPTY))))
                        {
                            return NotFind;
                        }
                        return c - TWO;
                    }
                    if (seq == TWO) {
                        if ((cell_arr[r][c].equals(player)) && (cell_arr[r][c - ONE].equals(player))
                                && (cell_arr[r][c - TWO].equals(EMPTY)) && (cell_arr[r][c - Three].equals(EMPTY))) {
                            if ((r < Five) && ((cell_arr[r + ONE][c - TWO].equals(EMPTY)))) {
                                return NotFind;
                            }
                            return c - TWO;
                        }
                        if ((cell_arr[r][c].equals(player)) && (cell_arr[r][c - ONE].equals(EMPTY))
                                && (cell_arr[r][c - TWO].equals(player)) && (cell_arr[r][c - Three].equals(EMPTY))) {
                            if ((r < Five) && ((cell_arr[r + ONE][c - ONE].equals(EMPTY)))) {
                                return NotFind;
                            }
                            return c - ONE;
                        }

                    }

                    if (seq == ONE) {
                        if ((cell_arr[r][c].equals(player)) && (cell_arr[r][c - ONE].equals(EMPTY))
                                && (cell_arr[r][c - TWO].equals(EMPTY)) && (cell_arr[r][c - Three].equals(EMPTY))) {
                            if ((r < Five) && ((cell_arr[r + ONE][c - ONE].equals(EMPTY)))) {
                                return NotFind;
                            }
                            return c - ONE;
                        }
                    }
                }
            }
        return NotFind;
    }

    private int check_Diagonal_up_Right(int r,int c,String player,int seq)   // r=row , c=col
    {
        if((r>TWO)&&(c<Four)) {
                if (seq == Three) {
                    if ((cell_arr[r][c].equals(player)) && (cell_arr[r - ONE][c + ONE].equals(player))
                            && (cell_arr[r - TWO][c + TWO].equals(player)) && (cell_arr[r - Three][c + Three].equals(EMPTY))) {
                        {
                           if(cell_arr[r-TWO][c+Three].equals(EMPTY))
                           {
                               return NotFind;
                           }
                            return c+ Three;
                        }
                    }
                    if ((cell_arr[r][c].equals(player)) && (cell_arr[r - ONE][c + ONE].equals(EMPTY))
                            && (cell_arr[r - TWO][c + TWO].equals(player)) && (cell_arr[r - Three][c + Three].equals(player))) {
                        {
                            if(cell_arr[r][c+ONE].equals(EMPTY))
                            {
                                return NotFind;
                            }
                            return c+ ONE;
                        }
                    }
                }
                if (seq == TWO)
                {
                    if ((cell_arr[r][c].equals(player)) && (cell_arr[r - ONE][c + ONE].equals(player))
                            && (cell_arr[r - TWO][c + TWO].equals(EMPTY)) && (cell_arr[r - Three][c + Three].equals(EMPTY)))

                        {
                            if(cell_arr[r-ONE][c+TWO].equals(EMPTY))
                            {
                                return NotFind;
                            }
                            return c + TWO;
                        }
                }
                if (seq == ONE)
                {
                    if ((cell_arr[r][c].equals(player)) && (cell_arr[r - ONE][c + ONE].equals(EMPTY))
                            && (cell_arr[r - TWO][c + TWO].equals(EMPTY)) && (cell_arr[r - Three][c + Three].equals(EMPTY))) {
                        if(cell_arr[r][c+ONE].equals(EMPTY))
                        {
                            return NotFind;
                        }
                        return c+ONE;
                        }
                    }
                }
        return NotFind;
    }

    private int check_Diagonal_up_Left(int r,int c,String player,int seq)   // r=row , c=col
    {
        if((r>TWO)&&(c>TWO))
        {
            if(seq==Three)
            {
                if((cell_arr[r][c].equals(player))&&(cell_arr[r-ONE][c-ONE].equals(player))
                        &&(cell_arr[r-TWO][c-TWO].equals(player))&&(cell_arr[r-Three][c-Three].equals(EMPTY)))
                {
                    if(cell_arr[r-TWO][c-Three].equals(EMPTY))
                    {
                        return NotFind;
                    }

                    return c-Three;
                }
                if((cell_arr[r][c].equals(player))&&(cell_arr[r-ONE][c-ONE].equals(EMPTY))
                        &&(cell_arr[r-TWO][c-TWO].equals(player))&&(cell_arr[r-Three][c-Three].equals(player)))
                {
                    if(cell_arr[r][c-ONE].equals(EMPTY))
                    {
                        return NotFind;
                    }
                    return c-ONE;
                }
            }
            if(seq==TWO)
            {
                if((cell_arr[r][c].equals(player))&&(cell_arr[r-ONE][c-ONE].equals(player))
                        &&(cell_arr[r-TWO][c-TWO].equals(EMPTY))&&(cell_arr[r-Three][c-Three].equals(EMPTY)))
                {
                    if(cell_arr[r-ONE][c-TWO].equals(EMPTY))
                    {
                        return NotFind;
                    }
                    return c-TWO;
                }
            }
            if(seq==ONE)
            {
                if((cell_arr[r][c].equals(player))&&(cell_arr[r-ONE][c-ONE].equals(EMPTY))
                        &&(cell_arr[r-TWO][c-TWO].equals(EMPTY))&&(cell_arr[r-Three][c-Three].equals(EMPTY)))
                {
                    if(cell_arr[r][c-ONE].equals(EMPTY))
                    {
                        return NotFind;
                    }
                    return c -ONE;
                }
            }
        }
        return NotFind;
    }


    private int check_Diagonal_Down_Right(int r,int c,String player,int seq)   // r=row , c=col
    {
        if((r<Three)&&(c<Four))
        {
            if (seq == Three)
            {
                    if ((cell_arr[r][c].equals(player)) && (cell_arr[r + ONE][c + ONE].equals(player))
                            && (cell_arr[r + TWO][c + TWO].equals(player)) && (cell_arr[r + Three][c + Three].equals(EMPTY))) {
                        if ((r < TWO) && (cell_arr[r + Four][c + Three].equals(EMPTY))) {
                            return NotFind;
                        }
                        return c + Three;
                    }
                if ((cell_arr[r][c].equals(player)) && (cell_arr[r + ONE][c + ONE].equals(player))
                        && (cell_arr[r + TWO][c + TWO].equals(EMPTY)) && (cell_arr[r + Three][c + Three].equals(player))) {

                    if (cell_arr[r + Three][c + TWO].equals(EMPTY)) {
                        return NotFind;
                    }
                    return c + TWO;
                }
            }
            if (seq == TWO) {
                    if ((cell_arr[r][c].equals(player)) && (cell_arr[r + ONE][c + ONE].equals(player))
                            && (cell_arr[r + TWO][c + TWO].equals(EMPTY)) && (cell_arr[r + Three][c + Three].equals(EMPTY))) {

                        if ((cell_arr[r + Three][c + TWO].equals(EMPTY)))
                        {
                            return NotFind;
                        }
                        return c + TWO;
                    }
            }
            if (seq == ONE) {

                    if ((cell_arr[r][c].equals(player)) && (cell_arr[r + ONE][c + ONE].equals(EMPTY))
                            && (cell_arr[r + TWO][c + TWO].equals(EMPTY)) && (cell_arr[r + Three][c + Three].equals(EMPTY)))
                    {
                        if ((cell_arr[r + TWO][c + ONE].equals(EMPTY)))
                        {
                            return NotFind;
                        }
                        return c + ONE;
                    }
                }
        }
        return NotFind;
    }

    private int check_Diagonal_Down_Left(int r,int c,String player,int seq)   // r=row , c=col
    {
        if((r<Three)&&(c>TWO))
        {
            if(seq==Three)
            {
                if((cell_arr[r][c].equals(player))&&(cell_arr[r+ONE][c-ONE].equals(player))
                        &&(cell_arr[r+TWO][c-TWO].equals(player))&&(cell_arr[r+Three][c-Three].equals(EMPTY)))
                {
                    if ((r < TWO) && (cell_arr[r + Four][c - Three].equals(EMPTY)))
                    {
                        return NotFind;
                    }
                    return c - Three;
                }
                if((cell_arr[r][c].equals(player))&&(cell_arr[r+ONE][c-ONE].equals(player))
                        &&(cell_arr[r+TWO][c-TWO].equals(EMPTY))&&(cell_arr[r+Three][c-Three].equals(player)))
                {
                    if ((cell_arr[r + Three][c - TWO].equals(EMPTY)))
                    {
                        return NotFind;
                    }
                    return c - TWO;
                }
            }
            if(seq==TWO)
            {
                if((cell_arr[r][c].equals(player))&&(cell_arr[r+ONE][c-ONE].equals(player))
                        &&(cell_arr[r+TWO][c-TWO].equals(EMPTY))&&(cell_arr[r+Three][c-Three].equals(EMPTY)))
                {
                    if ((cell_arr[r + Three][c - TWO].equals(EMPTY)))
                    {
                        return NotFind;
                    }
                    return c - TWO;
                }
            }
            if(seq==ONE)
            {
                if((cell_arr[r][c].equals(player))&&(cell_arr[r+ONE][c-ONE].equals(EMPTY))
                        &&(cell_arr[r+TWO][c-TWO].equals(EMPTY))&&(cell_arr[r+Three][c-Three].equals(EMPTY)))
                {
                    if ((cell_arr[r + TWO][c - ONE].equals(EMPTY)))
                    {
                        return NotFind;
                    }
                    return c - ONE;
                }
            }

        }
        return NotFind;
    }





}
