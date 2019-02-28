package kr.co.teada.ex66servicebindtest;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    MusicServiceUsingBind musicServiceUsingBind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //액티비티가 화면에 보여질 때 자동으로 실행되는 콜백메소드
    @Override
    protected void onResume() {
        super.onResume();

        //MusicService 와 연결(bind) 작업 수행!!
        if(musicServiceUsingBind==null){
            //musicServiceUsingBind 를 실행시킬 Intent 생성
            Intent intent=new Intent(this, MusicServiceUsingBind.class);
            startService(intent); //서비스 객체가 없으면 create 를 하고, 있다면 onStartCommand()만 다시 불러


            //세 번 째 파라미터 flags 값에 0 = flags 를 안 주겠다! :자동으로 서비스 객체를 생성하지 않고 생성 되어 있는 서비스 객체와 연결만 해
            //BIND_AUTO_CREATE : 연결할 서비스 객체 자동 생성, 기존 서비스와 다른 객체가 또 만들어 질 수도 있어
            bindService(intent, conn, 0);
        }
    }//end of onResume()

    //멤버변수 위치(메소드들 사이에 있어도 멤버변수 위치)
    //musicServiceUsingBind 와 연결된 연결통로를 관리하는 객체
    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            //bindService()로 서비스 객체와 연결을 시도하여 성공하였을 때, 자동실행
            //두 번째 파라미터 : 서비스 객체의 onBind() 메소드에서 리턴된 객체
            MusicServiceUsingBind.MyBinder myBinder=(MusicServiceUsingBind.MyBinder) binder;
            musicServiceUsingBind=myBinder.getServiceAddress();


        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    public void clickPlay(View view) {
        if(musicServiceUsingBind!=null) musicServiceUsingBind.playMusic();
    }

    public void clickPause(View view) {
        if(musicServiceUsingBind!=null) musicServiceUsingBind.pauseMusic();
    }

    public void clickStop(View view) {
        if(musicServiceUsingBind!=null){
            musicServiceUsingBind.stopMusic();
            //서비스 객체와 연결(bind) 종료
            unbindService(conn);
            musicServiceUsingBind=null;
        }

        Intent intent=new Intent(this, MusicServiceUsingBind.class);
        stopService(intent);

        finish();

    }
}
