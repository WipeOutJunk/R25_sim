package com.example.r25_sim;


import android.annotation.TargetApi;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private SoundPool mSoundPool;
    private AssetManager mAssetManager;
    private int mCatSound, mDyatelSound, mCowSound, mSnakeSound, mPopugaiSound, mTarakanSound;
    private int mStreamID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            // Для устройств до Android 5
            createOldSoundPool();
        } else {
            // Для новых устройств
            createNewSoundPool();
        }

        mAssetManager = getAssets();

        // получим идентификаторы
        mCatSound = loadSound("cat.ogg");
        mDyatelSound = loadSound("dyatel.ogg");
        mCowSound = loadSound("cow.ogg");
        mSnakeSound = loadSound("snake.ogg");
        mPopugaiSound = loadSound("popugai.ogg");
        mTarakanSound = loadSound("tarakan.mp3");

        ImageButton cowImageButton = findViewById(R.id.image_cow);
        cowImageButton.setOnClickListener(onClickListener);

        ImageButton dyatelImageButton = findViewById(R.id.image_dyatel);
        dyatelImageButton.setOnClickListener(onClickListener);

        ImageButton catImageButton = findViewById(R.id.image_cat);
        catImageButton.setOnClickListener(onClickListener);

        ImageButton popugaiImageButton = findViewById(R.id.image_popugai);
        popugaiImageButton.setOnClickListener(onClickListener);

        ImageButton tarakanImageButton = findViewById(R.id.image_tarakan);
        tarakanImageButton.setOnClickListener(onClickListener);

        ImageButton snakeImageButton = findViewById(R.id.image_snake);
        snakeImageButton.setOnClickListener(onClickListener);


//        cowImageButton.setOnTouchListener(new View.OnTouchListener() {
//
//            public boolean onTouch(View v, MotionEvent event) {
//                int eventAction = event.getAction();
//                if (eventAction == MotionEvent.ACTION_UP) {
//                    // Отпускаем палец
//                    if (mStreamID > 0)
//                        mSoundPool.stop(mStreamID);
//                }
//                if (eventAction == MotionEvent.ACTION_DOWN) {
//                    // Нажимаем на кнопку
//                    mStreamID = playSound(mCowSound);
//                }
//                if (event.getAction() == MotionEvent.ACTION_CANCEL) {
//                    mSoundPool.stop(mStreamID);
//                }
//                return true;
//            }
//        });
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.image_cow) {
                playSound(mCowSound);
            } else if (v.getId() == R.id.image_dyatel) {
                playSound(mDyatelSound);
            } else if (v.getId() == R.id.image_cat) {
                playSound(mCatSound);
            } else if (v.getId() == R.id.image_tarakan) {
                playSound(mTarakanSound);
            } else if (v.getId() == R.id.image_snake) {
                playSound(mSnakeSound);
            } else if (v.getId() == R.id.image_popugai) {
                playSound(mPopugaiSound);
            }
        }
    };

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void createNewSoundPool() {
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        mSoundPool = new SoundPool.Builder()
                .setAudioAttributes(attributes)
                .build();
    }

    @SuppressWarnings("deprecation")
    private void createOldSoundPool() {
        mSoundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
    }

    private int playSound(int sound) {
        if (sound > 0) {
            mStreamID = mSoundPool.play(sound, 1, 1, 1, 0, 1);
        }
        return mStreamID;
    }

    private int loadSound(String fileName) {
        AssetFileDescriptor afd;
        try {
            afd = mAssetManager.openFd(fileName);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Не могу загрузить файл " + fileName,
                    Toast.LENGTH_SHORT).show();
            return -1;
        }
        return mSoundPool.load(afd, 1);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            // Для устройств до Android 5
            createOldSoundPool();
        } else {
            // Для новых устройств
            createNewSoundPool();
        }

        mAssetManager = getAssets();

        // получим идентификаторы
        mCatSound = loadSound("cat.ogg");
        mDyatelSound = loadSound("dyatel.ogg");
        mCowSound = loadSound("cow.ogg");
        mSnakeSound = loadSound("snake.ogg");
        mPopugaiSound = loadSound("popugai.ogg");
        mTarakanSound = loadSound("tarakan.mp3");

    }

    @Override
    protected void onPause() {
        super.onPause();
        mSoundPool.release();
        mSoundPool = null;
    }
}