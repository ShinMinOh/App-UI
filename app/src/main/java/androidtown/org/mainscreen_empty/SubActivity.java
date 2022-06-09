package androidtown.org.mainscreen_empty;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class SubActivity extends AppCompatActivity {
    ImageView imageView;
    CameraSurfaceView surfaceView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        imageView = (ImageView)findViewById(R.id.imageView);
        surfaceView = (CameraSurfaceView)findViewById(R.id.surfaceView);
        Button button = (Button)findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 카메라 사진 캡쳐
                capture();
            }
        });
    }

    public void capture() {
        surfaceView.capture(new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                // bytearray 형식으로 전달
                // 이미지뷰로 보여주거나 파일로 저장
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 8;
                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);

                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                int newWidth = 200;
                int newHeight = 200;

                float scaleWidth = ((float) newWidth) / width;
                float scaleHeight = ((float) newHeight) / height;

                Matrix matrix = new Matrix();

                matrix.postScale(scaleWidth, scaleHeight);

                matrix.postRotate(90);



                Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0,0,width,height,matrix,true);
                BitmapDrawable bmd = new BitmapDrawable(resizedBitmap);

                imageView.setImageDrawable(new BitmapDrawable(resizedBitmap));//이미지뷰에 사진 보여주기
                camera.startPreview();
            }
        });
    }
}