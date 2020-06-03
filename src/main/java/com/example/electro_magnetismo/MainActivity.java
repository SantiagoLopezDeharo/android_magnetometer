package com.example.electro_magnetismo;
import androidx.appcompat.app.AppCompatActivity;
import android.app.PendingIntent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements SensorEventListener
{

    //Creating the TextView reference and Sensors
    private TextView medidor;
    private SensorManager SM;
    private DecimalFormat Df;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Declaring variables
        medidor = (TextView) findViewById(R.id.textView2);
        DecimalFormatSymbols simbolo  = new DecimalFormatSymbols(Locale.US);
        simbolo.setDecimalSeparator('.');
        //We create a decimal format to display the value correctly
        Df = new DecimalFormat("#.000", simbolo);
        SM = (SensorManager) getSystemService(SENSOR_SERVICE);
    }
    @Override
    public void onResume()
    {
        super.onResume();
        //starts to use the magnetometer
        SM.registerListener(this, SM.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SM.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //if the app closes it stops using the magnetometer
        SM.unregisterListener(this);
    }
    private PendingIntent pendingI;
    private final static String CHANNEL_ID = "NOTIFICATION";
    private final static int NOTIFICATION_ID = 0;
    @Override
    public void onSensorChanged(SensorEvent event)
    {
        //if the sensor is correctly map
        if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
        {
            //get the magnetometer values
            float mgx = event.values[0];
            float mgy = event.values[1];
            float mgz = event.values[2];

            //Get the value un Teslas
            double magnitude = Math.sqrt((mgx*mgx) + (mgy*mgy) + (mgz*mgz));
            float mgfinal = (float) magnitude;
            //Display de magnetic field in Teslas
            medidor.setText(Df.format(mgfinal) + "\u00b5Tesla");
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}