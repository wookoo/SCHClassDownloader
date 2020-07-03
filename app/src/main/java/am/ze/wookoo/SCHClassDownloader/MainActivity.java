package am.ze.wookoo.SCHClassDownloader;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private WebView mwv;//Mobile Web View
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mwv=(WebView)findViewById(R.id.web);

        WebSettings mws=mwv.getSettings();//Mobile Web Setting
        mws.setJavaScriptEnabled(true);//자바스크립트 허용
        mws.setLoadWithOverviewMode(true);//컨텐츠가 웹뷰보다 클 경우 스크린 크기에 맞게 조정
        //mws.setDomStorageEnabled(true);

        mwv.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (url.equals("https://cyber.sch.ac.kr/ko/")){
                    view.loadUrl("javascript:document.querySelector(\"body > div:nth-child(2) > header > nav.navigation > button > i\").click()");
                    view.loadUrl("javascript:window.Android.printLogOut(document.querySelector(\"body > div:nth-child(4) > section > aside > div > button.logout\").textContent);");
                    view.loadUrl("javascript:document.querySelector(\"#auth > div > button\").click()");
                }
                //view.loadUrl("javascript:window.Android.getHtml(document.getElementsByTagName('html')[0].innerHTML);");
                //

                //로그인상태 확인하고. 로그인 안되있으면 로그인 시키기.
                //로그인이 되었으면 lms 로 진입 후 현재 강의 출력
                //현재 강의에서 강좌명 : 토큰명으로 저장
            }
        });

        Button b1 = findViewById(R.id.button);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mwv.loadUrl("javascript:$('#id').val('아이디')");
                mwv.loadUrl("javascript:$('#passw').val('비밀번호')");
                mwv.loadUrl("javascript:document.querySelector('#authParam > input.btn-type-skyblue').click()");

            }
        });
        mwv.addJavascriptInterface(new MyJavascriptInterface(),"Android");
        mwv.loadUrl("https://cyber.sch.ac.kr/ko/");
    }


}
class MyJavascriptInterface {

    public static String temp =null;

    @JavascriptInterface
    public void getHtml(String html) { //위 자바스크립트가 호출되면 여기로 html이 반환됨
        System.out.println(html);
    }
    @JavascriptInterface
    public void printLogOut(String data){
        if(data.equals("로그아웃")){
            MyJavascriptInterface.temp  = "ok";
        }
    }
}

