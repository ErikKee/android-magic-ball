package osu.magicball;

import android.animation.Animator;
import android.animation.FloatEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;


/*
*   Google Developer Group Study Jam @Oklahoma State University - September 2015
*   https://github.com/fnk0/MagicBall
* */
public class MainActivity extends AppCompatActivity implements ShakeSensorCallback, Animator.AnimatorListener{

    // This will hold a reference to the Android sensor that controls the sensors such as
    // Accelerometer, Gyroscope, etc..
    SensorManager mSensorManager;

    // This will hold a reference to the ShakeListener that we will be using to detect when a shake happens
    ShakeListener mListener;

    // We initialize a Random() object that will take care of giving us a random phrase
    Random mRandom = new Random();

    // Will hold the Array with strings that represents our answers
    String[] mAnswers;

    // The TextView from your XML layout that will display the answer
    TextView mAnswerTextView;

    ImageView mImageView;
    ObjectAnimator mObjectAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAnswerTextView = (TextView) findViewById(R.id.answer);
        mAnswers = getResources().getStringArray(R.array.magic_answers);
        mImageView = (ImageView) findViewById(R.id.imageBall);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mListener = new ShakeListener(this, mSensorManager);

        mObjectAnimator = ObjectAnimator.ofFloat(mImageView, "rotation", 360f);
        mObjectAnimator.setInterpolator(new LinearInterpolator());
        mObjectAnimator.setRepeatCount(1);
        mObjectAnimator.setRepeatMode(ObjectAnimator.RESTART);
        mObjectAnimator.addListener(this);
        mObjectAnimator.setEvaluator(new FloatEvaluator());
        mObjectAnimator.setDuration(1500);
    }

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        mAnswerTextView.setText(mAnswers[mRandom.nextInt(mAnswers.length)]);
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }

    @Override
    public void executeShakeAction(int shakeAction) {
        // This will be called anytime a shake action happen
        if(shakeAction == ShakeListener.SHAKE_HORIZONTAL){
            // Shake horizontal to get an answer
            String randomString = mAnswers[mRandom.nextInt(mAnswers.length)];
            mAnswerTextView.setText(randomString); // Print answer
        }
        else if(shakeAction == ShakeListener.SHAKE_VERTICAL){
            // Shake vertically to clear
            mAnswerTextView.setText(""); // Clear text
        }


    }
}
