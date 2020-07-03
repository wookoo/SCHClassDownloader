package am.ze.wookoo.SCHClassDownloader;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private WebView mwv;//Mobile Web View
    private EditText userID,userPW;
    private boolean clicked = false;
    private Button loginButton;
    private int task = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager cookie = CookieManager.getInstance();
            cookie.removeSessionCookies(new ValueCallback<Boolean>() {
                @Override
                public void onReceiveValue(Boolean aBoolean) {

                }
            });
            cookie.removeAllCookies(new ValueCallback<Boolean>() {
                @Override
                public void onReceiveValue(Boolean aBoolean) {
                    Log.d("cookie","쿠키초기화");
                }
            });
        }
        loginButton = (Button)findViewById(R.id.btnLogin);
        loginButton.setText("로딩중");
        loginButton.setClickable(false);

        mwv=(WebView)findViewById(R.id.web);
        userID = (EditText)findViewById(R.id.editID);
        userPW = (EditText)findViewById(R.id.editPW);

        WebSettings mws=mwv.getSettings();//Mobile Web Setting
        mws.setJavaScriptEnabled(true);//자바스크립트 허용
        mws.setLoadWithOverviewMode(true);//컨텐츠가 웹뷰보다 클 경우 스크린 크기에 맞게 조정
        //mws.setDomStorageEnabled(true);



        mwv.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if(task == 0){
                    view.loadUrl("javascript:document.querySelector(\"body > div:nth-child(2) > header > nav.navigation > button > i\").click()");
                    view.loadUrl("javascript:window.Android.printLogOut(document.querySelector(\"body > div:nth-child(4) > section > aside > div > button.logout\").textContent);");
                    view.loadUrl("javascript:document.querySelector(\"#auth > div > button\").click()");


                    task = 1;


                }
                else if(task == 1 && clicked){

                    if(url.matches("https://sso.sch.ac.kr/oa/.*")){
                        Log.d("오류","로그인실패");
                    }

                    else{
                        Log.d("성공","로그인성공");
                        task = 2;
                        view.loadUrl("https://lms.sch.ac.kr/");
                        loginButton.setText("로그인 성공");
                        loginButton.setClickable(false); //로그인 버튼 비활성화
                        //나중에 ~~님 환영합니다 로 수정 예정

                    }

                }
                else if (task == 1){
                    loginButton.setText("로그인"); //버튼 로그인 가능하게 설정
                    loginButton.setClickable(true);
                }
                else if (task == 2){
                    view.loadUrl("javascript:document.querySelector(\"#page-content > div > div > div > div > div.col-loginbox > div.col-login.col-login-default > div.loginform > div:nth-child(1) > a\").click()");

                    task = 3;
                }
                else if (task == 3){
                    view.loadUrl("javascript:window.Android.getHtml(document.getElementsByTagName('html')[0].innerHTML);");
                }



                //로그인상태 확인하고. 로그인 안되있으면 로그인 시키기.
                //로그인이 되었으면 lms 로 진입 후 현재 강의 출력
                //현재 강의에서 강좌명 : 토큰명으로 저장

            }

        });


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicked = true;
                mwv.loadUrl(String.format("javascript:$('#id').val('%s')", userID.getText()));
                mwv.loadUrl(String.format("javascript:$('#passw').val('%s')", userPW.getText()));
                //입력한 아이디 비번

                mwv.loadUrl("javascript:document.querySelector('#authParam > input.btn-type-skyblue').click()");

            }
        });
        mwv.addJavascriptInterface(new MyJavascriptInterface(),"Android");

        mwv.loadUrl("https://cyber.sch.ac.kr/ko/");
    }


}
class MyJavascriptInterface { //사용자 정의 스크립트

    public static String isLogin = "new";

    @JavascriptInterface
    public void getHtml(String html) { //위 자바스크립트가 호출되면 여기로 html이 반환됨


        Pattern p = Pattern.compile("https://lms.sch.ac.kr/course/view.php\\?id=\\d+"); //정규식 패턴, https://lms.sch.ac.kr/course/view.php?id=숫자 가 패턴임
        Matcher m = p.matcher(html); //받아온 html 소스에서 url 을 정규식으로 추출
        while(m.find()){
            System.out.println(m.group());
        }
    }
    @JavascriptInterface
    public void printLogOut(String data){
        if(data.equals("로그아웃")){
            MyJavascriptInterface.isLogin  = "ok";
        }
    }
}

