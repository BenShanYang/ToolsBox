package com.benshanyang.toolsbox;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.benshanyang.toolslibrary.callback.TextWatchListener;
import com.benshanyang.toolslibrary.constant.Border;
import com.benshanyang.toolslibrary.constant.PWDActionType;
import com.benshanyang.toolslibrary.utils.DensityUtils;
import com.benshanyang.toolslibrary.widget.ClearEditText;
import com.benshanyang.toolslibrary.widget.PasswordEditText;
import com.benshanyang.toolslibrary.widget.RoundedButton;
import com.benshanyang.toolslibrary.widget.TitleBarView;

/**
 * 类描述: 测试类 </br>
 * 时间: 2019/3/22 15:21
 *
 * @author YangKuan
 * @version
 * @since
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //标题栏
        TitleBarView titleBarView = findViewById(R.id.title_bar_view);
        titleBarView.setTitleBarHeight(DensityUtils.dp2px(this,50));
        titleBarView.setBackgroundColor(0xFFFFCCCC);
        titleBarView.setTitleBarBackgroundColor(Color.WHITE);
        titleBarView.setTitle("标题");
        titleBarView.setTitleTextColor(0xFF0000FF);
        titleBarView.setTitleTextSize(18);
        titleBarView.setBottomBorder(DensityUtils.dp2px(this,2),0xFF000000);
        titleBarView.setImmersionStateBar(true);//一定要在setBottomBorder()方法之后调用否则底部边线会被覆盖掉
        titleBarView.setButtonText("购物车");
        titleBarView.setButtonType(TitleBarView.Type.IMAGE_BUTTON);
        titleBarView.setImageButtonSrc(R.drawable.ic_shopping);
        titleBarView.setButtonDrawable(R.drawable.ic_shopping,0);
        titleBarView.setButtonTextColor(0xFFFFFF00);
        titleBarView.setButtonTextSize(10);

        titleBarView.setOnFinishListener(new TitleBarView.OnFinishListener() {
            @Override
            public void onFinish(View view) {
                Toast.makeText(MainActivity.this,"关闭当前页面",Toast.LENGTH_SHORT).show();
            }
        });

        titleBarView.setOnActionButtonClickListener(new TitleBarView.OnActionButtonClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"右侧按钮被点击了",Toast.LENGTH_SHORT).show();
            }
        });

        //密码输入框
        PasswordEditText pwdET = findViewById(R.id.pwdet);
        pwdET.setIconTextPaddingLeft(DensityUtils.dp2px(this,8));
        pwdET.setIconTextPaddingRight(DensityUtils.dp2px(this,5));
        pwdET.setIconPaddingLeft(DensityUtils.dp2px(this,8));
        pwdET.setActionButtonType(PWDActionType.PASSWORD_CLEAR);
        pwdET.setBottomBorder(DensityUtils.dp2px(this,1),0xFFFFCCCC , 0xFFD5D5D5);
        pwdET.setPWDDigits("^[a-zA-Z0-9_@#%\\*]+$");
        pwdET.setBorderVisibility(Border.VISIBLE);
        pwdET.setTextColor(0xFFFF0000);
        pwdET.setPWDHint("请输入密码");
        pwdET.setHintTextColor(0xFF999999);
        pwdET.setPWDEditTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    //获取焦点
                }else{
                    //失去焦点
                }
            }
        });
        pwdET.addTextWatchListener(new TextWatchListener() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ClearEditText clearEditText = findViewById(R.id.cet);
        clearEditText.setIcon(R.drawable.ic_shopping);
        clearEditText.setIconPaddingLeft(DensityUtils.dp2px(this,8));
        clearEditText.setIconTextPaddingLeft(DensityUtils.dp2px(this,8));
        clearEditText.setClearIconPaddingLeft(DensityUtils.dp2px(this,10));
        clearEditText.setClearIconPaddingRight(DensityUtils.dp2px(this,10));
        clearEditText.setTextColor(0xFF0000FF);
        clearEditText.setHintTextColor(0xFF999999);
        clearEditText.setHint("请输入内容");
        clearEditText.setSingleLine(true);
        clearEditText.setGravity(Gravity.CENTER_VERTICAL|Gravity.LEFT);
        clearEditText.setDigits("^[a-z0-9]+$");
        clearEditText.isShowBorder(true);
        clearEditText.setBottomBorder(2,getResources().getColor(R.color.colorAccent),getResources().getColor(R.color.colorPrimary));
        clearEditText.setClearEditTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    //获取焦点
                }else{
                    //失去焦点
                }
            }
        });
        clearEditText.addTextWatchListener(new TextWatchListener() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        RoundedButton btn = findViewById(R.id.btn);
        btn.setCornerRadius(60);
        btn.setBorderWidth(2);
        btn.setShowBorder(true);
        btn.setPressedBackgroundColor(0xFFFF9933);
        btn.setNormalBackgroundColor(0xFFF3F3F3);
        btn.setPressedBorderColor(0xFFFF0000);
        btn.setNormalBorderColor(0xFFD5D5D5);
        btn.setPressedTextColor(0xFFFFFFFF);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,RecyclerActivity.class));
            }
        });
    }

}
