package jackleeforce.aidlserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

/**
 * Created by lijiahua on 16/8/26.
 */
public class AIDLService extends Service {

    public class MyAidlImpl extends IMyAidlInterface.Stub
    {
        @Override
        public int add(int value1, int value2) throws RemoteException {
            return value1 + value2;
        }

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyAidlImpl();
    }
}
