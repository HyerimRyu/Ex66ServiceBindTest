package kr.co.teada.ex66servicebindtest;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;

import java.io.IOException;

public class MusicServiceUsingBind extends Service {

    MediaPlayer mp;

    //MainActivity 쪽에 MusicServiceUsingBin 객체의 참조주소를 전달해 줄 직원객체 설계
    class MyBinder extends Binder{
        //MusicServiceUsingBin 객체의 참조주소를 리턴시켜주는 기능
        public MusicServiceUsingBind getServiceAddress(){
            return MusicServiceUsingBind.this;
        }

    }

    //bindService()를 실행해서 연결되었을 때 자동 호출되는 메소드
    @Override
    public IBinder onBind(Intent intent) {

        //Binder 객체 생성
        MyBinder myBinder=new MyBinder();
        return myBinder;

    }//end of onBind()

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }//end of onUnbind()

    @Override
    public void onDestroy() {
        super.onDestroy();
    }//end of onDestroy()


    //MainActivity 에서 제어할 메소드들
    public void playMusic(){
        if(mp==null){

            try {
                mp=new MediaPlayer();
                Uri uri=Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.maid_with_the_flaxen_hair);
                mp.setDataSource(this, uri);
                mp.prepare();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }//end of if

        //플레이 중이 아니면...
        if(!mp.isPlaying()) mp.start();

    }//end of playMusic()..

    public void pauseMusic(){
        if(mp!=null){
            mp.pause();
        }

    }

    public  void stopMusic(){
        if (mp !=null){
            mp.stop();
            mp.release();
            mp=null;
        }

    }

}//end of MusicServiceUsingBind class..
