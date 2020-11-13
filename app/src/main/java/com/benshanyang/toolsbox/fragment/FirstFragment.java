package com.benshanyang.toolsbox.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.widget.EditText;
import android.widget.TextView;

import com.benshanyang.toolsbox.R;
import com.benshanyang.toolslibrary.widget.ExpandTextView;
import com.benshanyang.toolslibrary.widget.SimpleExpandTextView;

public class FirstFragment extends Fragment {

    AppCompatButton expandButton;
    ExpandTextView expandTextView;
    SimpleExpandTextView simpleExpandTextView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        expandButton = (AppCompatButton) view.findViewById(R.id.expand_button);
        simpleExpandTextView = (SimpleExpandTextView) view.findViewById(R.id.simple_expand_text_view);

        expandTextView = (ExpandTextView) view.findViewById(R.id.expand_text_view);
        expandTextView.setExpandTextStyle(0xFF0000FF, ExpandTextView.NORMAL_TEXTSIZE, false);
        expandTextView.setCloseTextStyle(0xFF0000FF, ExpandTextView.NORMAL_TEXTSIZE, false);
        expandTextView.setIconFontAssetsPath("expand_icon_font/ic_expand_iconfont.ttf");
        expandTextView.setExpandIcon("\uebc1", true);
        expandTextView.setCloseIcon("\uebc2", true);

        // 设置最大行数
        String content = "最新的版本是只支持AndroidX的。从XUI 1.0.5以后，是支持AndroidX的版本，1.0.5之前的版本是支持Support的版本。这里我建议使用最新版本（AndroidX版本），因为之前的版本可能存在一些兼容性的bug，并在后面的版本被逐一修复。如果你依然想使用Support版本的话，要么你就使用1.0.5之前的版本（存在一些低版本兼容性的bug），要么你就clone一下当前最新版本的源码，将其改为Support版本本地导入依赖使用。";
        expandTextView.setText(content);
        simpleExpandTextView.setText(content);

        simpleExpandTextView.addTextTooLong(new SimpleExpandTextView.TextTooLong() {
            @Override
            public void tooLong(boolean tooLong) {
                expandButton.setVisibility(tooLong ? View.VISIBLE : View.GONE);
            }
        });

        simpleExpandTextView.addExpandStateListener(new SimpleExpandTextView.ExpandStateListener() {
            @Override
            public void expandText(boolean expand) {
                expandButton.setText(expand ? "收起" : "展开");
            }
        });

        expandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (simpleExpandTextView.isExpand()) {
                    simpleExpandTextView.closeText();
                } else {
                    simpleExpandTextView.expandText();
                }
            }
        });

        ((TextView) view.findViewById(R.id.tv_iconfont)).setTypeface(Typeface.createFromAsset(getContext().getAssets(), "expand_icon_font/ic_expand_iconfont.ttf"));
        ((TextView) view.findViewById(R.id.tv_iconfont)).setText(
                "\n\\ue600 \ue600; " + "\\uebc0 \uebc0; " + "\\uebc1 \uebc1; " + "\\uebc2 \uebc2 \n\n" + "\\uebbe \uebbe; " + "\\uebbf \uebbf; " + "\\uebbd \uebbd; " + "\\uebbc \uebbc \n"
        );

        ((EditText) view.findViewById(R.id.edit_text)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                expandTextView.setText(s);
                simpleExpandTextView.setText(s);
            }
        });
    }
}