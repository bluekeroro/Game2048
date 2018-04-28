package com.bluekeroro.android.game2048;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by BlueKeroro on 2018/4/27.
 */
public class Card extends FrameLayout {
    private TextView label;
    private TextView mBackground;
    private int num;

    public Card(Context context) {
        super(context);
        label = new TextView(getContext());
        mBackground=new TextView(getContext());
        label.setTextSize(35);
        label.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        label.setGravity(Gravity.CENTER);
        label.setBackground(getResources().getDrawable(R.drawable.corner_view));
        mBackground.setBackground(getResources().getDrawable(R.drawable.corner_view));
        GradientDrawable myGrad=(GradientDrawable)label.getBackground();
        myGrad.setColor(Color.argb(89,238, 228, 218));
        LayoutParams lp = new LayoutParams(-1, -1);
        lp.setMargins(25,25,0,0);
        addView(mBackground, lp);
        addView(label, lp);
        setNum(0,false);
    }

    public int getNum() {
        return num;
    }
    private void showCard(TextView label){
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(label, "scaleX", 0.7f,1.1f,1).setDuration(90);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(label, "scaleY", 0.7f,1.1f,1).setDuration(90);
        AnimatorSet animatorSet=new AnimatorSet();
        animatorSet.play(animator1).with(animator2);
        animatorSet.start();
    }
    //平移不需要animate，突然出现才需要animate
    public void setNum(int num,boolean animate) {
        this.num = num;
        if(num<=0){
            label.setText("");
        }else{
            label.setText(String.valueOf(num));
            if(animate){
                showCard(label);
            }
        }
        GradientDrawable myGrad=(GradientDrawable)label.getBackground();
        switch(label.getText().toString()){
            case "":
                myGrad.setColor(Color.argb(89,238, 228, 218));
                break;
            case "2":
                label.setTextColor(0xff776e65);
                myGrad.setColor(0xffeee4da);
                break;
            case "4":
                label.setTextColor(0xff776e65);
                myGrad.setColor(0xffede0c8);
                break;
            case "8":
                label.setTextColor(0xfff9f6f2);
                myGrad.setColor(0xfff2b179);
                break;
            case "16":
                label.setTextColor(0xfff9f6f2);
                myGrad.setColor(0xfff59563);
                break;
            case "32":
                label.setTextColor(0xfff9f6f2);
                myGrad.setColor(0xfff67c5f);
                break;
            case "64":
                label.setTextColor(0xfff9f6f2);
                myGrad.setColor(0xfff65e3b);
                break;
            case "128":
                label.setTextColor(0xfff9f6f2);
                myGrad.setColor(0xffedcf72);
                break;
            case "256":
                label.setTextColor(0xfff9f6f2);
                myGrad.setColor(0xffedcc61);
                break;
            case "512":
                label.setTextColor(0xfff9f6f2);
                myGrad.setColor(0xffedc850);
                break;
            case "1024":
                label.setTextColor(0xfff9f6f2);
                myGrad.setColor(0xffedc53f);
                break;
            case "2048":
                label.setTextColor(0xfff9f6f2);
                myGrad.setColor(0xffedc22e);
                break;
        }
        /*switch(label.getText().toString()){
            case "":
                label.setBackgroundColor(Color.argb(89,238, 228, 218));
                break;
            case "2":
                label.setTextColor(0xff776e65);
                label.setBackgroundColor(0xffeee4da);
                break;
            case "4":
                label.setTextColor(0xff776e65);
                label.setBackgroundColor(0xffede0c8);
                break;
            case "8":
                label.setTextColor(0xfff9f6f2);
                label.setBackgroundColor(0xfff2b179);
                break;
            case "16":
                label.setTextColor(0xfff9f6f2);
                label.setBackgroundColor(0xfff59563);
                break;
            case "32":
                label.setTextColor(0xfff9f6f2);
                label.setBackgroundColor(0xfff67c5f);
                break;
            case "64":
                label.setTextColor(0xfff9f6f2);
                label.setBackgroundColor(0xfff65e3b);
                break;
            case "128":
                label.setTextColor(0xfff9f6f2);
                label.setBackgroundColor(0xffedcf72);
                break;
            case "256":
                label.setTextColor(0xfff9f6f2);
                label.setBackgroundColor(0xffedcc61);
                break;
            case "512":
                label.setTextColor(0xfff9f6f2);
                label.setBackgroundColor(0xffedc850);
                break;
            case "1024":
                label.setTextColor(0xfff9f6f2);
                label.setBackgroundColor(0xffedc53f);
                break;
            case "2048":
                label.setTextColor(0xfff9f6f2);
                label.setBackgroundColor(0xffedc22e);
                break;
        }*/
    }

    public boolean equals(Card obj) {
        return getNum()==obj.getNum();
    }
}
