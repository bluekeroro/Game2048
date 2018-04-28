package com.bluekeroro.android.game2048;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by BlueKeroro on 2018/4/28.
 */
public class LostDialog extends Dialog {
    private Button mButton;

    public LostDialog(Context context, int themeResId) {
        super(context, themeResId);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lost_dialog_view);
        mButton=(Button)findViewById(R.id.TryAgainButton);
        GradientDrawable myGrad=(GradientDrawable)mButton.getBackground();
        myGrad.setColor(0xff8f7a66);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameActivity.getGameActivity().mGameView.startGame();
                LostDialog.this.dismiss();
            }
        });
    }
}