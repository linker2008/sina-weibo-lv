package weibo4android.androidexamples;

import java.util.List;

import weibo4android.Status;
import weibo4android.User;
import weibo4android.Weibo;
import weibo4android.WeiboException;
import weibo4android.androidexamples.R;
import weibo4android.http.RequestToken;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class AndroidExample extends Activity {
	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
    	System.setProperty("weibo4j.oauth.consumerSecret", Weibo.CONSUMER_SECRET);
    	System.out.println();
    	Button beginOuathBtn=  (Button) findViewById(R.id.Button01);
    	beginOuathBtn.setOnClickListener(new Button.OnClickListener()
        {

            public void onClick( View v )
            {
//            	test_1();
//            	test_2();
//            	test_3();
            }
        } );
    	
	}
	
	private void test_1(){
    	Weibo weibo = OAuthConstant.getInstance().getWeibo();
    	RequestToken requestToken;
		try {
			requestToken =weibo.getOAuthRequestToken("weibo4android://OAuthActivity");
			Uri uri = Uri.parse(requestToken.getAuthenticationURL()+ "&from=xweibo");
			OAuthConstant.getInstance().setRequestToken(requestToken);
			startActivity(new Intent(Intent.ACTION_VIEW, uri));
		} catch (WeiboException e) {
			e.printStackTrace();
		}
		
	}
	
	private void test_2(){
//		oauth_token=e0bcd11246be4e83b7e186958fd3551b&oauth_token_secret=fdd481f26139ddef57909a826a60695d&user_id=1849977442

		try {
			String args[] = {"e0bcd11246be4e83b7e186958fd3551b", "fdd481f26139ddef57909a826a60695d"};
			User user = getWeibo(true,args).verifyCredentials();
			System.out.println(user.toString());
		} catch (WeiboException e) {
			e.printStackTrace();
		}
	}
	
	private void test_3(){
		try {
			String args[] = {"e0bcd11246be4e83b7e186958fd3551b", "fdd481f26139ddef57909a826a60695d"};
//			String args[] = {"linkerlv@3g.sina.cn", "sinalvlinker"};
 	 		//获取前20条最新更新的公共微博消息
 			 List<Status> statuses = getWeibo(true,args).getFriendsTimeline();
 			for (Status status : statuses) {
 	            System.out.println(status.getUser().getName() + ":" +
 	                               status.getText());
 	        }
 		} catch (WeiboException e) {
 			e.printStackTrace();
 		}
	}
	
	private static Weibo getWeibo(boolean isOauth,String[] args) {
		Weibo weibo = new Weibo();
		if(isOauth) {//oauth验证方式 args[0]:访问的token；args[1]:访问的密匙
			weibo.setToken(args[0], args[1]);
		}else {//用户登录方式
   			weibo.setUserId(args[0]);//用户名/ID
   			weibo.setPassword(args[1]);//密码
		}
		return weibo;
	}
}