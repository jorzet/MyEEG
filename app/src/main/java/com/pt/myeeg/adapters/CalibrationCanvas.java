package com.pt.myeeg.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import com.pt.myeeg.R;

/**
 * Created by Jorge Zepeda Tinoco on 24/08/17.
 * jorzet.94@gmail.com
 */

public class CalibrationCanvas extends android.support.v7.widget.AppCompatImageView{
    private Canvas canvas;

    public Paint p;
    public static final int ERROR_FROM_ELECTRODE = -0x01;
    public static final int ELECTRODE_NOT_ABLE =    0x00;
    public static final int ELECTRODE_RED =         0x01;
    public static final int ELECTRODE_ORANGE =      0x02;
    public static final int ELECTRODE_GREEN =       0x03;

    private static final int BATERY_NODE_0 =   0x01;
    private static final int BATERY_NODE_20 =  0x14;
    private static final int BATERY_NODE_30 =  0x1E;
    private static final int BATERY_NODE_50 =  0x32;
    private static final int BATERY_NODE_60 =  0x3C;
    private static final int BATERY_NODE_80 =  0x50;
    private static final int BATERY_NODE_90 =  0x5A;
    private static final int BATERY_NODE_100 = 0x64;

    private static final String FP1 = "FP1";
    private static final String G   = "G";
    private static final String FP2 = "FP2";
    private static final String F7  = "F7";
    private static final String F3  = "F3";
    private static final String FZ  = "FZ";
    private static final String F4  = "F4";
    private static final String F8  = "F8";
    private static final String A1  = "A1";
    private static final String T3  = "T3";
    private static final String C3  = "C3";
    private static final String CZ  = "CZ";
    private static final String C4  = "C4";
    private static final String T4  = "T4";
    private static final String A2  = "A2";
    private static final String T5  = "T5";
    private static final String P3  = "P3";
    private static final String PZ  = "PZ";
    private static final String P4  = "P4";
    private static final String T6  = "T6";
    private static final String O1  = "O1";
    private static final String O2  = "O2";

    public static String[] chanels = {FP1, G, FP2,
                                      F7, F3, FZ, F4, F8,
                                      A1, T3, C3, CZ, C4, T4, A2,
                                      T5, P3, PZ, P4, T6,
                                      O1, O2};
    //Sumar a cada porcentaje

    public static final double[][] percentageElectrode = {{0.3189047, 0.1522988}, // FP1
                                            {0.4415020, 0.1257586}, // G
                                            {0.5541029, 0.1522988}, // FP2

                                            {0.1793488, 0.2933463}, // F7
                                            {0.3073488, 0.3208463}, // F3
                                            {0.4395020, 0.3528463}, // FZ
                                            {0.5784243, 0.3158463}, // F4
                                            {0.7060243, 0.2903863}, // F8

                                            {-0.005865, 0.4790999}, // A1
                                            {0.1206650, 0.4758463}, // T3
                                            {0.2853488, 0.4758463}, // C3
                                            {0.4415020, 0.4758463}, // CZ
                                            {0.6034243, 0.4758463}, // C4
                                            {0.7560378, 0.4758463}, // T4
                                            {0.8965644, 0.4788463}, // A2

                                            {0.1759000, 0.6698366}, // T5
                                            {0.3043488, 0.6273366}, // P3
                                            {0.4415020, 0.5953366}, // PZ
                                            {0.5794543, 0.6273366}, // P4
                                            {0.7060243, 0.6643366}, // T6

                                            {0.3295047, 0.8059919}, // O1
                                            {0.5583429, 0.8058816}};// O2

                           // FP1 G FP2 F7 F3 FZ F4 F8 A1 T3 C3 CZ C4 T4 A2 T5 P3 PZ P4 T6 O1 O2
    //public int[] electrodes = {0, 2, 2, 3, 3, 1, 3, 2, 3, 3, 3, 3, 1, 3, 3, 3, 3, 2, 3, 3, 3, 3};
    //                         FP1 G FP2 F7 F3 FZ F4 F8 A1 T3 C3 CZ C4 T4 A2 T5 P3 PZ P4 T6 O1 O2
    public int[] electrodes = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};

    public float width;
    public float height;

    public static final int[] circles = {
            R.drawable.ic_red_circule,
            R.drawable.ic_orange_circle,
            R.drawable.ic_green_circle
    };

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.canvas = canvas;
        p=new Paint();
        p.setColor(Color.WHITE);

        Bitmap System1020 = BitmapFactory.decodeResource(getResources(), R.drawable.ic_10_20_system);

        Bitmap redCircle = BitmapFactory.decodeResource(getResources(), circles[0]);
        Bitmap orangeCircle = BitmapFactory.decodeResource(getResources(), circles[1]);
        Bitmap greenCircle = BitmapFactory.decodeResource(getResources(), circles[2]);

        canvas.drawBitmap(System1020, 0, 0, p);
        this.height =  System1020.getHeight();
        this.width = System1020.getWidth();



        for(int i=0; i<electrodes.length; i++) {
            if (electrodes[i] == ELECTRODE_RED || electrodes[i] == ERROR_FROM_ELECTRODE)
                canvas.drawBitmap(redCircle, (float)percentageElectrode[i][0]*width, (float)percentageElectrode[i][1]*height, p);
            else if(electrodes[i] == ELECTRODE_ORANGE)
                canvas.drawBitmap(orangeCircle, (float)percentageElectrode[i][0]*width, (float)percentageElectrode[i][1]*height, p);
            else if(electrodes[i] == ELECTRODE_GREEN)
                canvas.drawBitmap(greenCircle, (float)percentageElectrode[i][0]*width, (float)percentageElectrode[i][1]*height, p);

            canvas.drawText(chanels[i],(float)percentageElectrode[i][0]*width, (float)percentageElectrode[i][1]*height,p);
        }
    }

    public CalibrationCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Canvas getCanvas(){
        return  this.canvas;
    }

    public void setElectrodesAsociatedWithPatient(int[] electrodes){
        this.electrodes = electrodes;
    }
}
