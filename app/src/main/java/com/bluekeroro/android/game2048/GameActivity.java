package com.bluekeroro.android.game2048;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class GameActivity extends AppCompatActivity {
    private TextView mScore;
    private int score=0;
    private Button mButton;
    public GameView mGameView;
    private static GameActivity sGameActivity=null;
    private TextView mRule;
    private boolean isGetWidth;
    private TextView mBestScore;
    private LinearLayout scoreBoard;
    private LinearLayout bestScoreBoard;
    private TextView mAddTextView;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        //getResources().getDrawable(R.drawable.corner_view).getConstantState().newDrawable();
        mAddTextView=(TextView)findViewById(R.id.addTextView);
        mScore=(TextView)findViewById(R.id.tvScore);
        mButton=(Button)findViewById(R.id.restartButton);
        GradientDrawable myGrad=(GradientDrawable)mButton.getBackground();
        myGrad.setColor(0xff8f7a66);
        mGameView=(GameView)findViewById(R.id.gameView);
        myGrad=(GradientDrawable)mGameView.getBackground();
        myGrad.setColor(0xffbbada0);
        scoreBoard=(LinearLayout)findViewById(R.id.scoreLinearLayout);
        myGrad=(GradientDrawable)scoreBoard.getBackground();
        myGrad.setColor(0xffbbada0);
        bestScoreBoard=(LinearLayout)findViewById(R.id.bestScoreLinearLayout);
        myGrad=(GradientDrawable)bestScoreBoard.getBackground();
        myGrad.setColor(0xffbbada0);
        isGetWidth=false;
        ViewTreeObserver observer=mGameView.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if(!isGetWidth){
                    int width=mGameView.getWidth();
                    width=(width-25)/4;
                    mGameView.addCards(width,width);
                    mGameView.startGame();
                    isGetWidth=true;
                }
            }
        });
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGameView.startGame();
            }
        });
        mRule=(TextView)findViewById(R.id.rule);
        mRule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(GameActivity.this)
                        .setTitle("HOW TO PLAY: ")
                        .setMessage("Use your finger to move the tiles. " +
                                "When two tiles with the same number touch, they merge into one!")
                        .show();
            }
        });
        mBestScore=(TextView)findViewById(R.id.bestScore);
        mBestScore.setText(BestScorePreferences.getBestScore(this));

    }

    public GameActivity() {
        sGameActivity = this;
    }

    public static GameActivity getGameActivity() {
        return sGameActivity;
    }
    public void clearScore(){
        score=0;
        showScore();
    }
    public void showScore(){
        mScore.setText(String.valueOf(score));
    }
    public void addScore(int add){
        score+=add;
        mAddTextView.setText("+"+add);
        showAddAnimate(mAddTextView);
        int bestScore=Integer.parseInt(BestScorePreferences.getBestScore(this));
        if(bestScore<score){
            bestScore=score;
            mBestScore.setText(String.valueOf(bestScore));
            BestScorePreferences.setBestScore(this,String.valueOf(bestScore));
        }
        showScore();
    }
    private void showAddAnimate(TextView mAddTextView){
        ObjectAnimator animator1 = ObjectAnimator
                .ofFloat(mAddTextView, "alpha", 1,0)
                .setDuration(1000);
        ObjectAnimator animator2 = ObjectAnimator
                .ofFloat(mAddTextView, "translationY", 0,-100)
                .setDuration(1000);
        AnimatorSet animatorSet=new AnimatorSet();
        animatorSet.play(animator1)
                .with(animator2);
        animatorSet.start();
    }
}
