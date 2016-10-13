package com.example.student.mariocrystalball;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class MainActivity extends AppCompatActivity {

    private TextView answerText;

    private SensorManager sensorManager;
    private Sensor accelerometer;

    private float acceleration;
    private float currentAcceleration;
    private float previousAcceleration;

    private ImageView img;

    private AnimationDrawable imgAnim;

    private final SensorEventListener sensorListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {

            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            previousAcceleration = currentAcceleration;
            currentAcceleration = (float)Math.sqrt(x * x + y * y + z * z);
            float delta = currentAcceleration - previousAcceleration;
            acceleration = acceleration * 0.9f + delta;

            //When it is shaken...
            if(acceleration > 20) {
                //Plays music
                MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.sound);
                mediaPlayer.start();

                //Displays a random answer
                String ans = Predictions.get().getPrediction();
                answerText.setText(ans);

                //Says that the device was shaken
                Toast toast = Toast.makeText(getApplication(), "Device has shaken", Toast.LENGTH_SHORT);
                toast.show();
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initializes variables used to measure whenever the device has been shaken
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        acceleration = 0.0f;
        currentAcceleration = SensorManager.GRAVITY_EARTH;
        previousAcceleration = SensorManager.GRAVITY_EARTH;

        //Used to make the answer given from the getPrediction function in the Prediction class show on screen.
        answerText = (TextView) findViewById(R.id.answerText);

        answerText.setText("Shake to get a prediction.");

        //Used to create the spinning image when the app loads.
        img = (ImageView) findViewById(R.id.spin_block);
        img.setImageResource(R.drawable.crystal_anim);

        imgAnim = (AnimationDrawable) img.getDrawable();

        imgAnim.start();

        //Sets custom font
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/customFont.ttf");
        answerText.setTypeface(font);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(sensorListener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(sensorListener);
    }
}
