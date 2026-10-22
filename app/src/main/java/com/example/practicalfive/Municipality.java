package com.example.practicalfive;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Municipality extends AppCompatActivity {

    private TextView tvCityName;
    private TextView tvCityInfo;
    private ImageView ivCityImage;
    private Button btnVisitSite;
    private String currentCity = ""; // 记录当前是哪个城市

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_municipality);

        // 1. 找到界面上所有的控件
        tvCityName = findViewById(R.id.tvCityName);
        tvCityInfo = findViewById(R.id.tvCityInfo);
        ivCityImage = findViewById(R.id.ivCityImage);
        btnVisitSite = findViewById(R.id.btnVisitSite);

        // 2. 接收 MainActivity 传过来的城市名字
        Intent intent = getIntent();
        String receivedCity = intent.getStringExtra("CITY_NAME");

        // 3. 再次用 TextProcessor 验证（安全起见），如果无效就弹提示并关闭界面
        currentCity = TextProcessor.processMunicipality(receivedCity);
        if (currentCity == null) {
            Toast.makeText(this, "Invalid data received.", Toast.LENGTH_SHORT).show();
            finish(); // 关闭当前界面
            return;
        }

        // 4. 根据城市名字，设置对应的内容、图片和颜色
        tvCityName.setText(currentCity);

        // 从 arrays.xml 里面把 4 段简介取出来
        String[] histories = getResources().getStringArray(R.array.municipalities_array);

        // 默认背景色、文字色、图片、官网链接
        int themeColor = ContextCompat.getColor(this, R.color.municipality_beijing);
        int textColor = ContextCompat.getColor(this, R.color.color_textlight); // 默认白字
        int imageRes = R.drawable.beijing;
        String websiteUrl = "https://english.beijing.gov.cn/";
        int arrayIndex = 0; // 数组里北京的索引是 0

        // 用 switch 语句根据城市进行大变身！
        switch (currentCity) {
            case "Beijing":
                themeColor = ContextCompat.getColor(this, R.color.municipality_beijing);
                imageRes = R.drawable.beijing;
                websiteUrl = "https://english.beijing.gov.cn/";
                arrayIndex = 0;
                break;
            case "Shanghai":
                themeColor = ContextCompat.getColor(this, R.color.municipality_shanghai);
                imageRes = R.drawable.shanghai;
                websiteUrl = "https://english.shanghai.gov.cn/";
                arrayIndex = 1;
                break;
            case "Chongqing":
                themeColor = ContextCompat.getColor(this, R.color.municipality_chongqing);
                imageRes = R.drawable.chongqing;
                websiteUrl = "https://english.cq.gov.cn/";
                arrayIndex = 2;
                break;
            case "Tianjin":
                themeColor = ContextCompat.getColor(this, R.color.municipality_tianjin);
                imageRes = R.drawable.tianjin;
                websiteUrl = "https://en.tj.gov.cn/";
                arrayIndex = 3;
                break;
        }

        // 5. 把设置好的颜色、图片、文字应用到界面上
        tvCityName.setBackgroundColor(themeColor);
        tvCityName.setTextColor(textColor);

        // 按钮的背景变成主题色
        btnVisitSite.setBackgroundColor(themeColor);
        btnVisitSite.setTextColor(textColor);

        // 如果是浅色背景（比如天津的炭黑、重庆的橙色），文字可能需要白色更清晰，这里统一用白色
        tvCityInfo.setTextColor(themeColor); // 信息文字的颜色变成城市的主题色

        ivCityImage.setImageResource(imageRes);
        tvCityInfo.setText(histories[arrayIndex]);

        // 6. 给 Visit site 按钮设置点击事件（隐式 Intent）
        String finalWebsiteUrl = websiteUrl; // 给下面的内部类用
        btnVisitSite.setOnClickListener(v -> {
            try {
                // 创建一个隐式 Intent，打开浏览器
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(finalWebsiteUrl));
                startActivity(browserIntent);
            } catch (Exception e) {
                // 如果手机里没有浏览器，弹出提示
                Toast.makeText(Municipality.this, "No application found to open website", Toast.LENGTH_SHORT).show();
            }
        });
    }
}