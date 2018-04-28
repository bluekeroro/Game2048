package com.bluekeroro.android.game2048;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BlueKeroro on 2018/4/26.
 */
public class GameView extends GridLayout{
    private Card[][] cardsMap=new Card[4][4];
    private List<Point> emptyPoint=new ArrayList<Point>();
    public GameView(Context context) {
        super(context);
        initGameView();
    }
    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initGameView();
    }
    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initGameView();
    }
    private void initGameView() {
        setColumnCount(4);
        //setBackgroundColor(0xffbbada0);
        setOnTouchListener(new View.OnTouchListener(){
            private float startX=0,startY=0,offsetX=0,offsetY=0;
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        startX=event.getX();
                        startY=event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        offsetX=event.getX()-startX;
                        offsetY=event.getY()-startY;
                        if(Math.abs(offsetX)>Math.abs(offsetY)){
                            if(offsetX<-5){
                                swipeLeft();
                            }else if(offsetX>5){
                                swipeRight();
                            }
                        }else{
                            if(offsetY<-5){
                                swipeUp();
                            }else if(offsetY>5){
                                swipeDown();
                            }
                        }
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }
    public void addCards(int cardWidth,int cardHeight){
        Card mCard;
        //removeAllViews();
        for(int x=0;x<4;x++){
            for(int y=0;y<4;y++){
                mCard=new Card(getContext());
                mCard.setNum(0,false);
                cardsMap[x][y]=mCard;
                addView(mCard,cardWidth,cardHeight);
            }
        }
        //Toast.makeText(getContext(), "addCards"+cardWidth+"/"+cardHeight+"/count="+cardsMap.length, Toast.LENGTH_SHORT).show();
    }

    public void startGame(){
        if(GameActivity.getGameActivity()!=null){
            GameActivity.getGameActivity().clearScore();
        }
        for(int x=0;x<4;x++){
            for(int y=0;y<4;y++){
                cardsMap[x][y].setNum(0,false);
            }
        }
        addRandomNum();
        addRandomNum();
    }
    private void addRandomNum(){
        emptyPoint.clear();
        for(int x=0;x<4;x++){
            for(int y=0;y<4;y++){
                if(cardsMap[x][y].getNum()<=0){
                    emptyPoint.add(new Point(x,y));
                }
                //check win
                if(cardsMap[x][y].getNum()==2048){
                    /*Dialog winDialog=new AlertDialog.Builder(getContext())
                            .setMessage("You win!")
                            .setPositiveButton("Restart", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startGame();
                                }
                            }).show();
                    winDialog.setCanceledOnTouchOutside(false);*/
                    WinDialog dialog=new WinDialog(getContext(),R.style.translationTheme);
                    dialog.show();
                    dialog .setCanceledOnTouchOutside(false);
                }
            }
        }
        if(emptyPoint.size()>0){
            Point p=emptyPoint.remove((int)(Math.random()*emptyPoint.size()));
            cardsMap[p.x][p.y].setNum(Math.random()>0.1?2:4,true);
            if(emptyPoint.size()==0){
                checkComplete();
            }
        }
    }
    private void checkComplete(){
        for(int x=0;x<4;x++){
            for(int y=0;y<4;y++){
                if(x>1&&cardsMap[x][y].equals(cardsMap[x-1][y])){
                    return ;
                }
                if(x<3&&cardsMap[x][y].equals(cardsMap[x+1][y])){
                    return ;
                }
                if(y>1&&cardsMap[x][y].equals(cardsMap[x][y-1])){
                    return ;
                }
                if(y<3&&cardsMap[x][y].equals(cardsMap[x][y+1])){
                    return ;
                }
            }
        }
        LostDialog dialog=new LostDialog(getContext(),R.style.translationTheme);
        dialog.show();
        dialog .setCanceledOnTouchOutside(false);
        /*Dialog lostDialog=new AlertDialog.Builder(getContext())
                .setMessage("Game over!")
                .setPositiveButton("Try again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startGame();
                    }
                }).show();
        lostDialog.setCanceledOnTouchOutside(false);*/
    }
    private void swipeLeft(){
        boolean isMerge=false;
        for(int x=0;x<4;x++){
            for(int y=0;y<4;y++){
                for(int y1=y+1;y1<4;y1++){
                    if(cardsMap[x][y1].getNum()>0){
                        //===============================
                        /*FrameLayout parent=(FrameLayout)getParent();
                        Card temp=new Card(getContext());
                        temp.setNum(1024);
                        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(cardsMap[3][0].getWidth(),cardsMap[3][0].getWidth());
                        lp.setMargins(cardsMap[3][0].getLeft(),cardsMap[3][0].getTop(),cardsMap[3][0].getRight(),cardsMap[3][0].getBottom());
                        parent.addView(temp,lp);
                        ObjectAnimator moveAnimator = ObjectAnimator
                                .ofFloat(temp, "translationX", 0,cardsMap[3][3].getLeft()-cardsMap[3][0].getLeft())
                                .setDuration(3000);
                        moveAnimator.start();*/
                        //===================================
                       // Log.i("sad",x+"/"+y+"/"+(cardsMap[x][y1].getLeft()+25)+"/"+(cardsMap[x][y].getLeft()+25));
                        if(cardsMap[x][y].getNum()<=0){
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum(),false);
                            cardsMap[x][y1].setNum(0,false);
                            y--;
                            isMerge=true;
                        }else if(cardsMap[x][y].equals(cardsMap[x][y1])){
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum()*2,true);
                            cardsMap[x][y1].setNum(0,false);
                            GameActivity.getGameActivity().addScore(cardsMap[x][y].getNum());
                            isMerge=true;
                        }
                        break;
                    }
                }
            }
        }
        if(isMerge){
            addRandomNum();
            addRandomNum();
        }
    }
    private void swipeRight(){
        boolean isMerge=false;
        for(int x=0;x<4;x++){
            for(int y=3;y>=0;y--){
                for(int y1=y-1;y1>=0;y1--){
                    if(cardsMap[x][y1].getNum()>0){
                        if(cardsMap[x][y].getNum()<=0){
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum(),false);
                            cardsMap[x][y1].setNum(0,false);
                            y++;
                            isMerge=true;
                        }else if(cardsMap[x][y].equals(cardsMap[x][y1])){
                            cardsMap[x][y].setNum(cardsMap[x][y1].getNum()*2,true);
                            cardsMap[x][y1].setNum(0,false);
                            GameActivity.getGameActivity().addScore(cardsMap[x][y].getNum());
                            isMerge=true;
                        }
                        break;
                    }
                }
            }
        }
        if(isMerge){
            addRandomNum();
            addRandomNum();
        }
    }
    private void swipeUp(){
        boolean isMerge=false;
        for(int x=0;x<4;x++){
            for(int y=0;y<4;y++){
                for(int x1=x+1;x1<4;x1++){
                    if(cardsMap[x1][y].getNum()>0){
                        if(cardsMap[x][y].getNum()<=0){
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum(),false);
                            cardsMap[x1][y].setNum(0,false);
                            y--;
                            isMerge=true;
                        }else if(cardsMap[x][y].equals(cardsMap[x1][y])){
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum()*2,true);
                            cardsMap[x1][y].setNum(0,false);
                            GameActivity.getGameActivity().addScore(cardsMap[x][y].getNum());
                            isMerge=true;
                        }
                        break;
                    }
                }
            }
        }
        if(isMerge){
            addRandomNum();
            addRandomNum();
        }
    }
    private void swipeDown(){
        boolean isMerge=false;
        for(int x=3;x>=0;x--){
            for(int y=0;y<4;y++){
                for(int x1=x-1;x1>=0;x1--){
                    if(cardsMap[x1][y].getNum()>0){
                        if(cardsMap[x][y].getNum()<=0){
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum(),false);
                            cardsMap[x1][y].setNum(0,false);
                            y--;
                            isMerge=true;
                        }else if(cardsMap[x][y].equals(cardsMap[x1][y])){
                            cardsMap[x][y].setNum(cardsMap[x1][y].getNum()*2,true);
                            cardsMap[x1][y].setNum(0,false);
                            GameActivity.getGameActivity().addScore(cardsMap[x][y].getNum());
                            isMerge=true;
                        }
                        break;
                    }
                }
            }
        }
        if(isMerge){
            addRandomNum();
            addRandomNum();
        }
    }
}
