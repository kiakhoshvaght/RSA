package kiakhoshvaght.rsa;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.input_et)
    protected EditText inputEt;
    @BindView(R.id.result_tv)
    protected TextView resultTv;

    private static final String TAG = MainActivity.class.getName();
    private RSA rsa = new RSA();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({
            R.id.encrypt_btn,
            R.id.decrypt_btn
    })
    @Override
    public void onClick(View view) {
        Log.i(TAG,"FUNCTION : onClick");
        switch (view.getId()){
            case R.id.encrypt_btn:
                byte[] ecrypted = rsa.encrypt(inputEt.getText().toString().getBytes());
                Log.i(TAG,"FUNCTION : onClick => Encrypt: " + bytesToString(ecrypted));
                resultTv.setText(bytesToString(ecrypted));
                break;
            case  R.id.decrypt_btn:
                Log.i(TAG,"FUNCTION : onClick => Decrypt");
                resultTv.setText(rsa.decrypt(inputEt.getText().toString().getBytes()).toString());
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
