package jackleeforce.aidlclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import jackleeforce.aidlserver.IMyAidlInterface;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_a;
    private EditText et_b;
    private EditText et_result;
    private Button btn_calc;
    private IMyAidlInterface mService;
    private AddServiceConnect mServiceConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();

        connectService();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        releaseService();
    }

    private void initUI()
    {
        et_a = (EditText)findViewById(R.id.et_a);
        et_b = (EditText)findViewById(R.id.et_b);
        et_result = (EditText)findViewById(R.id.et_result);
        btn_calc = (Button)findViewById(R.id.calculate);

        btn_calc.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.calculate:
                calc();
                break;
            default:
                break;

        }
    }

    private void calc()
    {
        int a = Integer.parseInt(et_a.getText().toString());
        int b = Integer.parseInt(et_b.getText().toString());

        try
        {
            int result = mService.add(a, b);

            et_result.setText(String.valueOf(result));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    class AddServiceConnect implements ServiceConnection
    {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = IMyAidlInterface.Stub.asInterface(service);

            Toast.makeText(MainActivity.this,"onServiceConnected",Toast.LENGTH_LONG).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;

            Toast.makeText(MainActivity.this,"onServiceDisconnected",Toast.LENGTH_LONG).show();
        }
    }

    public void connectService()
    {
        mServiceConnect = new AddServiceConnect();
        Intent i = new Intent();

        i.setComponent(new ComponentName("jackleeforce.aidlserver","jackleeforce.aidlserver.AIDLService"));
        i.setPackage(getPackageName());

        boolean result = getApplicationContext().bindService(i,mServiceConnect, Context.BIND_AUTO_CREATE);
        if (!result)
        {
            Toast.makeText(MainActivity.this,"bindService failed",Toast.LENGTH_LONG).show();
        }
    }

    public void releaseService()
    {
        unbindService(mServiceConnect);
        mServiceConnect = null;
    }


}
