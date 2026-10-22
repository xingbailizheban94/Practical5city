package com.example.practicalfive;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText etMunicipality;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. 找到输入框
        etMunicipality = findViewById(R.id.etMunicipality);

        // 2. 监听键盘的“Go”键
        etMunicipality.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_GO) {

                String input = etMunicipality.getText().toString();

                // 3. 交给 TextProcessor 验证
                String validCity = TextProcessor.processMunicipality(input);

                if (validCity != null) {
                    // 验证成功，跳转到 Municipality 界面（注意这里改成了你建的名字 Municipality）
                    Intent intent = new Intent(MainActivity.this, Municipality.class);
                    intent.putExtra("CITY_NAME", validCity);
                    startActivity(intent);
                    etMunicipality.setText(""); // 清空输入框
                } else {
                    // 验证失败，弹出提示
                    Toast.makeText(MainActivity.this, "Invalid municipality. Please try again.", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
            return false;
        });
    }
}