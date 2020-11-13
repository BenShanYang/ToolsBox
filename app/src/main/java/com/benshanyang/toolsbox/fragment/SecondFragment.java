package com.benshanyang.toolsbox.fragment;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.benshanyang.toolsbox.R;
import com.benshanyang.toolslibrary.utils.ToastUtils;

import static android.graphics.Typeface.BOLD_ITALIC;

public class SecondFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.button_error_toast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.error(getContext(), R.string.error_message, Toast.LENGTH_SHORT, true).show();
            }
        });
        view.findViewById(R.id.button_success_toast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.success(getContext(), R.string.success_message, Toast.LENGTH_SHORT, true).show();
            }
        });
        view.findViewById(R.id.button_info_toast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.info(getContext(), R.string.info_message, Toast.LENGTH_SHORT, true).show();
            }
        });
        view.findViewById(R.id.button_warning_toast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.warning(getContext(), R.string.warning_message, Toast.LENGTH_SHORT, true).show();
            }
        });
        view.findViewById(R.id.button_normal_toast_wo_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.normal(getContext(), R.string.normal_message_without_icon).show();
            }
        });
        view.findViewById(R.id.button_normal_toast_w_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Drawable icon = getResources().getDrawable(R.drawable.ic_pets_white_24dp);
                ToastUtils.normal(getContext(), R.string.normal_message_with_icon, icon).show();
            }
        });
        view.findViewById(R.id.button_info_toast_with_formatting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.info(getContext(), getFormattedMessage()).show();
            }
        });
        view.findViewById(R.id.button_custom_config).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtils.Config.getInstance()
                        .setToastTypeface(Typeface.createFromAsset(getContext().getAssets(), "simkai.ttf"))
                        .allowQueue(false)
                        .apply();
                ToastUtils.custom(getContext(), R.string.custom_message, getResources().getDrawable(R.drawable.laptop512),
                        android.R.color.black, android.R.color.holo_green_light, Toast.LENGTH_SHORT, true, true).show();
                ToastUtils.Config.reset(); // Use this if you want to use the configuration above only once
            }
        });
    }

    private CharSequence getFormattedMessage() {
        final String prefix = "Formatted ";
        final String highlight = "bold italic";
        final String suffix = " text";
        SpannableStringBuilder ssb = new SpannableStringBuilder(prefix).append(highlight).append(suffix);
        int prefixLen = prefix.length();
        ssb.setSpan(new StyleSpan(BOLD_ITALIC),
                prefixLen, prefixLen + highlight.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ssb;
    }
}