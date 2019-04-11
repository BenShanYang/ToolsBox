package com.benshanyang.toolsbox;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.benshanyang.toolslibrary.callback.TextWatchListener;
import com.benshanyang.toolslibrary.constant.Border;
import com.benshanyang.toolslibrary.constant.PWDActionType;
import com.benshanyang.toolslibrary.utils.DensityUtils;
import com.benshanyang.toolslibrary.utils.Logger;
import com.benshanyang.toolslibrary.utils.ResUtils;
import com.benshanyang.toolslibrary.utils.TextUtils;
import com.benshanyang.toolslibrary.widget.BorderTextView;
import com.benshanyang.toolslibrary.widget.ClearEditText;
import com.benshanyang.toolslibrary.widget.CountDownButton;
import com.benshanyang.toolslibrary.widget.EditableTextView;
import com.benshanyang.toolslibrary.widget.PasswordEditText;
import com.benshanyang.toolslibrary.widget.RoundedButton;
import com.benshanyang.toolslibrary.widget.SearchBarView;
import com.benshanyang.toolslibrary.widget.SelectableTextView;
import com.benshanyang.toolslibrary.widget.SimpleClearEditText;
import com.benshanyang.toolslibrary.widget.TitleBarView;

import static android.util.TypedValue.COMPLEX_UNIT_PX;

/**
 * 类描述: 测试类 </br>
 * 时间: 2019/3/22 15:21
 *
 * @author YangKuan
 * @version
 * @since
 */
public class MainActivity extends AppCompatActivity {

    View.OnClickListener o = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(MainActivity.this, "搜索", Toast.LENGTH_SHORT).show();
        }
    };

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
        titleBarView.setTitleTextSize(DensityUtils.sp2px(MainActivity.this,18));
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
        pwdET.setPWDMaxLength(8);
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
        clearEditText.setMaxLength(8);
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

        final CountDownButton countDownButton = findViewById(R.id.countdownbutton);
        countDownButton.setNormalBackground(ResUtils.getDrawable(this,R.drawable.normal_bg));
        countDownButton.setTimingBackground(ResUtils.getDrawable(this,R.drawable.timing_bg));
        countDownButton.setNormalTextColor(0xFFFFFFFF);
        countDownButton.setTimingTextColor(0xFF666666);
        countDownButton.setDuration(60000);
        countDownButton.setInterval(1000);
        countDownButton.setStartText("发送验证码");
        countDownButton.setRestartText("重新发送");
        countDownButton.setUnit("s");
        countDownButton.setTimePrefix("剩下");
        countDownButton.setTextSize(28);
        countDownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownButton.startTiming();
                Logger.v("打印日志");
                Logger.i("打印日志");
                Logger.w("打印日志");
                Logger.d("打印日志");
                Logger.e("打印日志");
            }
        });

        SimpleClearEditText simpleClearEditText = findViewById(R.id.simpleclearedittext);
        simpleClearEditText.setClearIcon(R.drawable.icon_clear);
        simpleClearEditText.setTextSize(28);
        simpleClearEditText.setTextColor(0xFFFF0000);
        simpleClearEditText.setHint("请输入");
        //simpleClearEditText.setNormalBackground(getResources().getDrawable(R.drawable.normal_bottom_border_bg));
        //simpleClearEditText.setFocusedBackground(getResources().getDrawable(R.drawable.focused_bottom_border_bg));
        //simpleClearEditText.setBorderColor(0xFF000000);
        simpleClearEditText.setFocusedBorderColor(0xFFFF0000);
        simpleClearEditText.setNormalBorderColor(0xFF0000FF);
        simpleClearEditText.setBorderWidth(2);
        simpleClearEditText.isShowBorder(true);
        simpleClearEditText.setDigits("^[a-z0-9]+$");
        simpleClearEditText.setGravity(Gravity.LEFT);
        simpleClearEditText.setMaxLength(5);

        BorderTextView borderTextView1 = findViewById(R.id.bordertextview1);
        //borderTextView1.setBorderColor(0xFFD5D5D5);
        borderTextView1.setTopBorderLeftSpace(0);
        borderTextView1.setTopBorderRightSpace(0);
        borderTextView1.setBottomBorderLeftSpace(80);
        borderTextView1.setBottomBorderRightSpace(0);
        borderTextView1.setTopBorderWidth(2);
        borderTextView1.setBottomBorderWidth(2);
        borderTextView1.setTopBorderNormalColor(0xFFD5D5D5);
        borderTextView1.setTopBorderPressedColor(0xFFFF0000);
        borderTextView1.setBottomBorderNormalColor(0xFFD5D5D5);
        borderTextView1.setBottomBorderPressedColor(0xFFFF0000);
        borderTextView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"BorderTextView被点击了",Toast.LENGTH_SHORT).show();
            }
        });

        SelectableTextView selectableTextView = findViewById(R.id.select_textview);
        selectableTextView.setTitleIcon(R.drawable.ic_shopping,30);
        selectableTextView.setContentIcon(R.drawable.ic_shopping,R.drawable.ic_shopping,30);
        selectableTextView.setTitleContentSpace(40);
        selectableTextView.setTitleTextSize(36);
        selectableTextView.setContentTextSize(28);
        selectableTextView.setTitleTextColor(ResUtils.getColor(this,R.color.color_333333));
        selectableTextView.setContentTextColor(ResUtils.getColor(this,R.color.colorPrimaryDark));
        selectableTextView.setTitle("性别");
        selectableTextView.setContent("男阿斯蒂芬撒犯得上地方阿斯弗啊十分大师傅士大夫士大夫萨芬阿三发射点发射点发射点发撒沙发上士大夫萨芬随风倒士大夫士大夫阿斯蒂芬撒地方撒旦飞洒地方士大夫树都发");
        selectableTextView.setSingleLine(false);
        selectableTextView.setMaxLines(2);
        selectableTextView.setTopBorderLeftSpace(0);
        selectableTextView.setBottomBorderLeftSpace(0);
        selectableTextView.setTopBorderRightSpace(0);
        selectableTextView.setBottomBorderRightSpace(0);
        selectableTextView.setTopBorderWidth(2);
        selectableTextView.setBottomBorderWidth(2);
        selectableTextView.setTopBorderNormalColor(0xFFD5D5D5);
        selectableTextView.setTopBorderPressedColor(0xFFFF0000);
        selectableTextView.setBottomBorderNormalColor(0xFFD5D5D5);
        selectableTextView.setBottomBorderPressedColor(0xFFFF0000);
        selectableTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"SelectableTextView被点击了",Toast.LENGTH_SHORT).show();
            }
        });

        EditableTextView editableTextView = findViewById(R.id.edittextview);
        editableTextView.setEditable(true);
        editableTextView.setBackgroundColor(0xFFFFCCCC);
        editableTextView.setTopBorderColor(0xFFFF0000);
        editableTextView.setBottomBorderColor(0xFF0000FF);
        editableTextView.setTopBorderWidth(2);
        editableTextView.setBottomBorderWidth(2);
        editableTextView.setTopBorderLeftSpace(10);
        editableTextView.setTopBorderRightSpace(10);
        editableTextView.setBottomBorderLeftSpace(20);
        editableTextView.setBottomBorderRightSpace(20);
        editableTextView.setDigits("^[a-z0-9]+$");
        editableTextView.setMaxLength(20);
        editableTextView.setTitleContentSpace(40);
        editableTextView.setHint("请输入名称");
        editableTextView.setContentText("Hello World!");
        editableTextView.setContentTextColor(0xFF666666);
        editableTextView.setContentTextSize(28);
        editableTextView.setTextColorHint(0xFF999999);
        editableTextView.setTitleTextColor(0xFF333333);
        editableTextView.setTitleIcon(R.drawable.ic_shopping,20);
        editableTextView.setTitleTextSize(28);
        editableTextView.setTitleText("用户名称");

        SearchBarView searchBarView = findViewById(R.id.searchbarview);
        searchBarView.setBorderWidth(2);
        searchBarView.setNormalBackgroundColor(0xFFF1F1F1);
        searchBarView.setFocusedBackgroundColor(0xFFFFFF00);
        searchBarView.setNormalBorderColor(0xFFFFCCCC);
        searchBarView.setFocusedBorderColor(0xFFFF0000);
        searchBarView.setCornerRadius(40);
        searchBarView.setShowBorder(true);
        searchBarView.setHint("请输入要搜索的内容...");
        searchBarView.setTextColorHint(0xFF999999);
        searchBarView.setTextColor(0xFF333333);
        searchBarView.setTextSize(28);
        searchBarView.setShowActionButton(true);
        searchBarView.setActionIcon(R.drawable.ic_camera);
        searchBarView.setEditIcon(R.drawable.ic_search,20);
        searchBarView.setOnTextChangedListener(new SearchBarView.OnTextChangedListener() {
            @Override
            public void onChanged(Editable s, final EditText editText, ImageButton imageButton) {
                if(!TextUtils.isEmpty(s)){
                    imageButton.setImageResource(R.drawable.ic_clear);
                    imageButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            editText.setText("");
                        }
                    });
                }else{
                    imageButton.setImageResource(R.drawable.ic_camera);
                    imageButton.setOnClickListener(o);
                }
            }
        },o);

        findViewById(R.id.btn_toast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,ToastActivity.class));
            }
        });

        findViewById(R.id.btn_singleclick).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SingleClickActivity.class));
            }
        });
    }

}
