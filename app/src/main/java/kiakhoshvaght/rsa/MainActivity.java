package kiakhoshvaght.rsa;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.input_et)
    protected EditText inputEt;
    @BindView(R.id.result_tv)
    protected EditText resultTv;

    private static final String TAG = MainActivity.class.getName();
    private RSA rsa = new RSA();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @OnClick({
            R.id.encrypt_btn,
            R.id.decrypt_btn
    })
    @Override
    public void onClick(View view) {
        Log.i(TAG,"FUNCTION : onClick");
        switch (view.getId()){
            case R.id.encrypt_btn:
                String text = inputEt.getText().toString();
                Log.i(TAG,"FUNCTION : onClick => Encrypt => text: " + text);
                byte[] encrypted = rsa.encrypt(text.getBytes());
                Log.i(TAG, "FUNCTION : onClick => Encrypt => text in bytes: " + bytesToString(text.getBytes()));
                Log.i(TAG,"FUNCTION : onClick => Encrypt: " + bytesToString(encrypted));
                resultTv.setText(Base64.encodeToString(encrypted, Base64.DEFAULT));
                inputEt.setText(new String(rsa.decrypt(encrypted)));
                break;
            case  R.id.decrypt_btn:
                Log.i(TAG,"FUNCTION : onClick => Decrypt");
                Charset charset = StandardCharsets.US_ASCII;
                byte[] decrypted = rsa.decrypt(Base64.decode(inputEt.getText().toString(),Base64.DEFAULT));
                Log.i(TAG,"FUNCTION : onClick => Decrypt => Decrypted in bytes: " + decrypted.toString());
                resultTv.setText(new String(decrypted));
                break;
        }
    }

    private static String bytesToString(byte[] encrypted) {
        String test = "";
        for (byte b : encrypted) {
            test += Byte.toString(b);
        }
        return test;
    }

}
