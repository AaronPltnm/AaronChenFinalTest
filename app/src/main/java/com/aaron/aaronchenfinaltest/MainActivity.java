package com.aaron.aaronchenfinaltest;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.QRCodeDetector;

public class MainActivity extends AppCompatActivity{
    Button button;
    ImageView imgview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        imgview = findViewById(R.id.imageView);

        if (!OpenCVLoader.initDebug()){
            Log.d("MainActicity", "OpenCV init fail");
        }else{
            Log.d("MainActicity", "OpenCV init sucess");
        }

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Bitmap bitmap = ((BitmapDrawable)imgview.getDrawable()).getBitmap();
                Mat mat = new Mat();
                Utils.bitmapToMat(bitmap, mat);

                QRCodeDetector qrCodeDetector = new QRCodeDetector();
                String result = qrCodeDetector.detectAndDecode(mat);
                Log.d("MainActicity", "onClick" + result);
                String[] lines = result.split("");
                for(int i = 0; i<lines.length;i++){
                    String line = lines[i];
                    String pointA = line.split("")[0];
                    String pointB = line.split("")[1];
                    String pointC = line.split("")[2];
                    String xA = pointA.split(",")[0];
                    String yA = pointA.split(",")[1];
                    String xB = pointA.split(",")[0];
                    String yB = pointA.split(",")[1];
                    Point point1 = new Point(Double.valueOf(xA), Double.valueOf(yA));
                    Point point2 = new Point(Double.valueOf(xB), Double.valueOf(yB));
                    drawLine(mat,point1,point2);
                }
                Utils.matToBitmap(mat,bitmap);
                imgview.setImageBitmap(bitmap);
            }
        });
    }
    void drawLine(Mat mat, Point point1, Point point2){
        Scalar color = new Scalar(255,0,0,255);
        Imgproc.line(mat,point1,point2,color,5);

    }
}
