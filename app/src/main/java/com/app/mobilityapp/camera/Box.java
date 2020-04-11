package com.app.mobilityapp.camera;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.view.View;

public class Box extends View {

    int w;
    int h;

    Box(Activity context, int width, int hieght) {
        super(context);
        w = width;
        h = hieght;
    }


    @Override
    protected void onDraw(Canvas canvas) { // Override the onDraw() Method
        super.onDraw(canvas);
//        int w = canvas.getWidth();
//        int h = canvas.getHeight()-350;
        int range = 350;
        //center
        int left = w / 2 - range;
        int right = w / 2 + range;
        int top = h / 2 - range;
        int btm = h / 2 + range;

//        RectF rect = new RectF(100, 100, w - 100, h - 100);
        RectF rect = new RectF(left, top, right, btm);
        float radius = 10.0f; // should be retrieved from resources and defined as dp

        int outerFillColor = 0xaa000000;
// first create an off-screen bitmap and its canvas
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas auxCanvas = new Canvas(bitmap);

// then fill the bitmap with the desired outside color
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(outerFillColor);
        paint.setStyle(Paint.Style.FILL);
        auxCanvas.drawPaint(paint);

// then punch a transparent hole in the shape of the rect
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        auxCanvas.drawRoundRect(rect, radius, radius, paint);

// then draw the white rect border (being sure to get rid of the xfer mode!)
        paint.setXfermode(null);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        auxCanvas.drawRoundRect(rect, radius, radius, paint);

// finally, draw the whole thing to the original canvas
        canvas.drawBitmap(bitmap, 0, 0, paint);

    }
}