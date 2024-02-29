package com.example.hw224a10357.Utility;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.example.hw224a10357.interfaces.StepCallback;

public class StepDetector {
    private SensorManager sensorManager;
    private Sensor sensor;
    private SensorEventListener sensorEventListener;

    private long timestamp = 0L;

    private StepCallback stepCallback;

    public StepDetector(Context context, StepCallback stepCallback) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.stepCallback = stepCallback;
        initEventListener();
    }

    private void initEventListener() {
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float x = event.values[0];
                float z = event.values[2];
                calculateStep(x, z);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {
                // pass
            }
        };
    }

    private void calculateStep(float x, float z) {
        if (System.currentTimeMillis() - timestamp > 500) {
            timestamp = System.currentTimeMillis();
            if (x > 1.0) {
                if (stepCallback != null)
                    stepCallback.stepLeft();
            } else if (x < -1.0) {
                if (stepCallback != null)
                    stepCallback.stepRight();
            }
            if (z > 2) {
                if (stepCallback != null)
                    stepCallback.changeSpeed((int) (1000 / z));
            }
        }
    }


    public void start() {
        sensorManager.registerListener(
                sensorEventListener,
                sensor,
                SensorManager.SENSOR_DELAY_NORMAL
        );
    }

    public void stop() {
        sensorManager.unregisterListener(
                sensorEventListener,
                sensor
        );
    }

}
