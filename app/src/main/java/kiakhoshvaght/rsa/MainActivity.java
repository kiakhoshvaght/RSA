package kiakhoshvaght.rsa;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    @BindView(R.id.input_et)
    protected EditText inputEt;
    @BindView(R.id.result_tv)
    protected TextView resultTv;
    @BindView(R.id.p_tv)
    protected EditText pTv;
    @BindView(R.id.q_tv)
    protected EditText qTv;
    @BindView(R.id.d_tv)
    protected EditText dTv;
    @BindView(R.id.e_tv)
    protected EditText eTv;
    @BindView(R.id.n_tv)
    protected EditText nTv;

    private static final String TAG = MainActivity.class.getName();
    private RSA rsa = new RSA();
    boolean textChangedByButton = false;

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
                if(!eTv.getText().toString().equals("") && !dTv.getText().toString().equals("") && !pTv.getText().toString().equals("") && !qTv.getText().toString().equals("") && !nTv.getText().toString().equals("")){
                    Log.i(TAG,"FUNCTION : onClick => Encrypt => we have values");
                    rsa.e = BigInteger.valueOf(Integer.parseInt(eTv.getText().toString()));
                    rsa.d = BigInteger.valueOf(Integer.parseInt(dTv.getText().toString()));
                    rsa.p = BigInteger.valueOf(Integer.parseInt(pTv.getText().toString()));
                    rsa.q = BigInteger.valueOf(Integer.parseInt(qTv.getText().toString()));
                    rsa.calculateManual();
                }
                String text = inputEt.getText().toString();
                Log.i(TAG,"FUNCTION : onClick => Encrypt => text: " + text);
                byte[] encrypted = rsa.encrypt(text.getBytes());
                Log.i(TAG, "FUNCTION : onClick => Encrypt => text in bytes: " + bytesToString(text.getBytes()));
                Log.i(TAG,"FUNCTION : onClick => Encrypt: " + bytesToString(encrypted));
                textChangedByButton = true;
                resultTv.setText("Encrypted result: ");
                inputEt.setText(Base64.encodeToString(encrypted, Base64.DEFAULT));
                pTv.setText(rsa.p + "");
                qTv.setText(rsa.q + "");
                eTv.setText(rsa.e + "");
                dTv.setText(rsa.d + "");
                nTv.setText(rsa.N + "");
                break;
            case  R.id.decrypt_btn:
                if(!eTv.getText().toString().equals("") && !dTv.getText().toString().equals("") && !pTv.getText().toString().equals("") && !qTv.getText().toString().equals("") && !nTv.getText().toString().equals("")){
                    Log.i(TAG,"FUNCTION : onClick => Encrypt => we have values");
                    rsa.e = BigInteger.valueOf(Integer.parseInt(eTv.getText().toString()));
                    rsa.d = BigInteger.valueOf(Integer.parseInt(dTv.getText().toString()));
                    rsa.p = BigInteger.valueOf(Integer.parseInt(pTv.getText().toString()));
                    rsa.q = BigInteger.valueOf(Integer.parseInt(qTv.getText().toString()));
                    rsa.calculateManual();
                }
                Log.i(TAG,"FUNCTION : onClick => Decrypt");
                Charset charset = StandardCharsets.US_ASCII;
                byte[] decrypted = rsa.decrypt(Base64.decode(inputEt.getText().toString(),Base64.DEFAULT));
                Log.i(TAG,"FUNCTION : onClick => Decrypt => Decrypted in bytes: " + decrypted.toString());
                textChangedByButton = true;
                resultTv.setText("Decrypted result: ");
                inputEt.setText(new String(decrypted));
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

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        //do nothing
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        //do nothing
    }

    @Override
    public void afterTextChanged(Editable editable) {
        if(textChangedByButton)
            textChangedByButton = false;
        else
            resultTv.setText("");
    }
}
