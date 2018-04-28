package com.bluekeroro.android.game2048;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by BlueKeroro on 2018/4/28.
 */
public class WinDialog extends Dialog {
    private Button mButton;

    public WinDialog(Context context, int themeResId) {
        super(context, themeResId);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.win_dialog_view);
        mButton=(Button)findViewById(R.id.restartButton);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameActivity.getGameActivity().mGameView.startGame();
                WinDialog.this.dismiss();
            }
        });
    }
}
