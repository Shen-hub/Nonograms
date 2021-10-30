package com.example.nonograms;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Color;
import android.util.Log;

public class Cell {
    Paint p = new Paint();
    int status, winStatus;
    float left, top, right, bottom;

    public Cell() {}
    public Cell(int status, int winStatus) {
        this.status = status; //0 - Белое поле, 1 - Чёрное поле, 2 - крестик
        this.winStatus = winStatus;
    }

    public void draw(Canvas c, float left, float top, float right, float bottom)
    {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        p.setStrokeWidth(5);
        p.setAntiAlias(true);
        if (status == 0)
        {
            p.setColor(Color.GRAY);
            p.setStyle(Paint.Style.STROKE);

        }
        else {
            p.setColor(Color.GRAY);
            p.setStyle(Paint.Style.STROKE);
            c.drawRect(left, top, right, bottom, p);
            p.setColor(Color.BLACK);
            p.setStyle(Paint.Style.FILL);
        }

        c.drawRect(left, top, right, bottom, p);
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void changeStatus(float touchX, float touchY, int rb)
    {
        //Log.e("MyTag", String.valueOf("status: " + rb));
        if (touchX > left && touchX < right && touchY > top && touchY < bottom)
        {
            Log.e("MyTag", String.valueOf("yes"));
            p.setStrokeWidth(5);
            p.setAntiAlias(true);
            if (status == 0 && rb == 1)
            {
                p.setColor(Color.GRAY);
                p.setStyle(Paint.Style.STROKE);
                setStatus(1);
            }
            else if (status == 0 && rb == 0)
            {
                p.setColor(Color.GRAY);
                p.setStyle(Paint.Style.STROKE);
                setStatus(0);
            }
            else if (status == 1 && rb == 0)
            {
                p.setColor(Color.BLACK);
                p.setStyle(Paint.Style.FILL_AND_STROKE);
                setStatus(0);
            }
            else if (status == 1 && rb == 1)
            {
                p.setColor(Color.GRAY);
                p.setStyle(Paint.Style.STROKE);
                setStatus(1);
            }
            Log.e("MyTag", String.valueOf("status: " + status));
            //Log.e("MyTag", String.valueOf(status));
        }
    }

    /*public boolean checkStatus()
    {

        if (status == winStatus)
            return true;
        else
            return false;
    }*/

}
