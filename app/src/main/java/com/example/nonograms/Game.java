package com.example.nonograms;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;

public class Game extends View {
    float width, height;
    int row, column, rb;
    ArrayList<String> menuHorizontalField = new ArrayList<String>();
    ArrayList<String> menuVerticalField = new ArrayList<String>();
    ArrayList<ArrayList<Cell>> cells = new ArrayList<ArrayList<Cell>>();
    String picture;
    Context context;
    Paint p;
    Boolean isWin = false;

    public Game(Context context) {
        super(context);
    }
    public Game(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

    }

    public void setField(String picture)
    {
        this.picture = picture;
        getField(this.picture);
        //startDateField(this.picture);
    }
    public void setRB(int rb)
    {
        this.rb = rb;
        invalidate();
        Log.e("MyTag", String.valueOf("Status" + rb));
    }

    //Определяем размеры игрового поля
    private void getField(String picture)
    {
        String buf[] = picture.split(" ");

        column = picture.indexOf(" "); //Определяем количество строк
        row = (picture.length() - column + 1) / column; //Определяем количество столбцов

        getCells(buf);
        getMenu(buf);

    }


    //charAt
    private void getCells(String[] picture)
    {
        int ibuf;
        //Заполняем ArrayList ячеек
        for (int i = 0; i < row; i++) {
            cells.add(new ArrayList<Cell>());
            //Log.e("MyTag", String.valueOf(i + "x"));
            //Log.e("MTag", String.valueOf(picture[i]));
            for (int j = 0; j < column; j++) {
                ibuf = picture[i].charAt(j) - '0'; //Переводим в int
                Cell c = new Cell(0, ibuf);
                cells.get(i).add(c);
                //Log.e("MTag", String.valueOf(ibuf + " "+ i + " " + j + " "+ cells.get(i).get(j).winStatus));
            }
        }
    }

    //Рассчитываем значения для меню
    private void getMenu(String[] picture)
    {
        int menuBufH, menuBufV;
        int iBufH, iBufV;
        String sBufH, sBufV;
        for (int i = 0; i < row; i++)
        {
            menuBufH = 0;
            menuBufV = 0;
            sBufH = "";
            sBufV = "";

            for (int j = 0; j < column; j++) {
                iBufH = picture[i].charAt(j) - '0';
                iBufV = picture[j].charAt(i) - '0';

                //Складываем единицы, если они стоят рядом
                //Горизонтальное меню
                if (iBufH == 1)
                {
                    menuBufH = menuBufH + 1;
                }
                if (iBufH == 0 && menuBufH != 0)
                {
                    sBufH = sBufH + menuBufH;
                    menuBufH = 0;
                }

                if (iBufH == 1 && j == row - 1)
                {
                    sBufH = sBufH + menuBufH;
                }

                //Вертикальное меню
                if (iBufV == 1)
                {
                    menuBufV = menuBufV + 1;
                }

                if (iBufV == 0 && menuBufV != 0)
                {
                    sBufV = sBufV + menuBufV;
                    menuBufV = 0;
                }

                if (iBufV == 1 && j == row - 1)
                {
                    sBufV = sBufV + menuBufV;
                }
            }
            menuHorizontalField.add(sBufH);
            menuVerticalField.add(sBufV);
        }

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int sizePixels = Math.min(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(sizePixels, sizePixels);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        width = canvas.getWidth();
        height = canvas.getHeight();

        drawLines(canvas);
        //Log.e("MyTag", String.valueOf(row));
        if (isWin)
            drawWin(canvas);
    }

    private void drawLines(Canvas canvas)
    {
        int buf;
        float textWidth;
        p = new Paint();
        p.setColor(Color.WHITE);
        p.setStyle(Paint.Style.FILL);
        canvas.drawRect(0, 0, width, width, p);

        //Прорисовка игрового поля
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                    cells.get(i).get(j).draw(canvas,  (i+1) * width / (row + 1), (j+1) * width / (column + 1),  (i+2)*width / (row + 1),(j+2)*width / (column + 1));;
            }
        }

        //Прорисовка левого меню

        for (int i = 0; i < column; i++) {
            buf = menuHorizontalField.get(i).length();
            p.setColor(Color.BLACK);
            p.setStyle(Paint.Style.STROKE);
            p.setStrokeWidth(8);
            p.setAntiAlias(true);
            //Log.e("MyTag", String.valueOf("l: " + buf));
            for (int k = 0; k < buf; k++) {
                canvas.drawRect(k*width/(row+1)/buf, (i + 1) * width / (column + 1), (k+1)*width/(row + 1)/buf,  (i + 2) * width / (column + 1), p);
                p.setTextSize(32.0f);
                p.setStrokeWidth(3);
                p.setColor(Color.BLACK);
                p.setStyle(Paint.Style.STROKE);
                textWidth = p.measureText(String.valueOf(menuHorizontalField.get(i).charAt(k)));
                canvas.drawText(String.valueOf(menuHorizontalField.get(i).charAt(k)), (float) ((k+0.5)*width/(row+1)/buf) - (textWidth / 2f), (float)((i + 1.5) * width / (column + 1)) + (textWidth / 2f), p);
                //Log.e("MyTag", String.valueOf("text: " + width /(row+1)/buf + " " + width /(column+1)/buf));
                p.setStrokeWidth(8);
            }
        }

        //Прорисовка верхнего меню
        for (int i = 0; i < row; i++) {
            buf = menuVerticalField.get(i).length();
            p.setColor(Color.BLACK);
            p.setStyle(Paint.Style.STROKE);
            p.setStrokeWidth(8);
            p.setAntiAlias(true);
            //Log.e("MyTag", String.valueOf("l: " + menuVerticalField.get(i)));
            for (int k = 0; k < buf; k++) {
                canvas.drawRect((i + 1) * width / (row + 1),
                        k*width/(column + 1)/buf,
                        (i + 2) * width /(row + 1),
                        (k+1)*width/(column + 1)/buf, p);
                p.setTextSize(32.0f);
                p.setStrokeWidth(4);
                p.setColor(Color.BLACK);
                p.setStyle(Paint.Style.STROKE);
                textWidth = p.measureText(String.valueOf(menuVerticalField.get(i).charAt(k)));
                //Log.e("MyTag", String.valueOf("text: " + menuVerticalField.get(i).charAt(k)));
                canvas.drawText(String.valueOf(menuVerticalField.get(i).charAt(k)),
                        (float) ((i+1.5)*width/(row+1)) - (textWidth / 2f),
                        (float)((k + 0.5) * width / (column + 1)/buf) + (textWidth / 2f), p);
                p.setStrokeWidth(8);
            }
        }
    }

    //Событие нажатия по ячейке
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < column; j++) {
                    cells.get(i).get(j).changeStatus(x, y, rb);
                    invalidate();
                }
            }
            win();
        }
        return super.onTouchEvent(event);
    }


    private void win()
    {

        int k = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                Log.e("MyTag", String.valueOf(i+ " " + j +" " + cells.get(i).get(j).winStatus + " "+ cells.get(i).get(j).status));
                //Костыли наше всё
                if (cells.get(i).get(j).status == cells.get(j).get(i).winStatus)
                    k++;
                else
                    break;
            }
        }
        Log.e("MyTag", String.valueOf("you win" + isWin + " " + "k " + k ));
        if (k == row * column)
        {
            isWin = true;
            Log.e("MyTag", String.valueOf("you win" + isWin + " " + "k " + k ));
            invalidate();
        }

    }

    private void drawWin(Canvas c)
    {
        float textWidth, textHeight;
        Rect mTextBoundRect = new Rect();
        String text = "Вы победили!";
        p.setColor(Color.BLACK);
        p.setStyle(Paint.Style.FILL);
        c.drawRect(0, 0 , width, height, p);

        p.setAntiAlias(true);
        p.setColor(Color.WHITE);
        p.setTextSize(50.0f);
        p.setStrokeWidth(5);
        p.setStyle(Paint.Style.FILL);
        p.getTextBounds(text, 0, text.length(), mTextBoundRect);
        textWidth = p.measureText(text);
        textHeight = mTextBoundRect.height();
        c.drawText(text, width / 2 - (textWidth / 2f),  height / 2 + (textHeight /2f), p);
    }


}
