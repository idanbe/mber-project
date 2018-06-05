package com.example.administrator.game_4_in_a_row;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import android.os.Vibrator;


//view for paint on board game
public class MyView extends View {

    // values
    private final int Zero=0,ONE=1,TWO=2,Three=3,Four=4,Five=5,Six=6,Seven=7;
    private Paint paint;
    private int Vibe_Time=50;
    private static final int ten=10;
    private Path path;
    private Random random;
    private float top, right, width, height;
    private static String [][] cell_arr;
    private static int [][] win_cell;
    private static boolean find_win;
    private static String Winner;
    private final String EMPTY ="E";
    private final String PLAYER1="R";
    private final String PLAYER2="Y";
    private float rx,ry,witdh_cell,height_cell;
    private Vibrator v;
    private static boolean vibe_flag;
    private int i,j;


    public MyView(Context context) {
        super(context);
        init(null, Zero);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs,Zero);
    }

    public MyView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }


    private void init(AttributeSet attrs, int defStyle) {

            // Load attributes
            paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(Color.RED);
            paint.setStrokeWidth(ten);
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            path = new Path();
            random = new Random();
            cell_arr = new String[Six][Seven];
            clear_cell_Arr();
            win_cell= new int[Four][TWO];
            v =(Vibrator)this.getContext().getSystemService(Context.VIBRATOR_SERVICE);

    }




    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        right = getPaddingRight();
        top = getPaddingTop();
        width = w - (getPaddingLeft() + getPaddingRight());
        height = h - (getPaddingTop() + getPaddingBottom());
        witdh_cell=(width/Seven);
        height_cell=(height/Six);
        rx=right; //x of first cell
        ry=top; //y of first cell
    }




    @Override    //Draw on board
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        for (i = Zero; i < Six; i++)
        {
                for (j = Zero; j < Seven; j++) {

                    if (!cell_arr[i][j].equals(EMPTY))//if cell not empty paint
                    {
                        if(cell_arr[i][j].equals(PLAYER1))
                        {
                            paint.setColor(Color.RED);
                        }
                        else // player 2
                        {
                            paint.setColor(Color.YELLOW);
                        }
                        rx=(witdh_cell*j); //x,y of cell to paint
                        ry=(height_cell*i);
                        canvas.drawRect(rx, ry, (rx + witdh_cell), (ry + height_cell), paint);
                        if(vibe_flag) {
                            v.vibrate(Vibe_Time); //vibration
                        }
                    }
                }
        }
        if(find_win) //mark 4 cells of win
        {
            for (i = Zero; i < Four; i++) {
                    rx = (witdh_cell * win_cell[i][ONE]); //x,y of cell to paint
                    ry = (height_cell * win_cell[i][Zero]);


                if(Winner.equals(PLAYER1))
                {
                    paint.setColor(Color.rgb(159, 0, 15));

                }
                else //player 2 win
                {
                    paint.setColor(Color.rgb(253, 208, 23 ));

                }//mark cells of win
                canvas.drawRect(rx, ry, (rx + witdh_cell), (ry + height_cell)-ten, paint);
            }

        }
    }



        //set winner
    public void set_winer(String win)
    {
        Winner=win;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // touch on view
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_CANCEL:
                break;

        }
        return false;
    }

    //treu==has a winner /false == game on no winner
    public void set_find_win(boolean b)
    {
        find_win=b;
    }

    //clear all board
    private void clear_cell_Arr()
    {
        for (i = Zero; i < Six; i++)
        {
            for (j =Zero; j < Seven; j++)
            {
                cell_arr[i][j]=EMPTY;
            }
        }
    }


    //get array of coins to paint on board
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

    //get 4 cells that come to win
    public void setwin_Cell(int[][] arr)
    {
        for (i=Zero;i<Four;i++)
        {
            for(j =Zero; j<TWO;j++)
            {
                win_cell[i][j]=arr[i][j];
            }
        }
        return;
    }

    //set vibration on/off
    public void setVibeFlag(Boolean b)
    {
        vibe_flag=b;
    }


}
