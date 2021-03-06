package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;

@PapaSpiderConfig(
		home = "renrendai.com", 
		message = "人人贷财富是值得信赖的专业p2p网络借贷、互联网金融平台,拥有AAA级信用认证,为用户提供多元化网络借贷与优质的互联网金融平台服务,更好的满足用户财富增值需求。", 
		platform = "renrendai", 
		platformName = "人人贷", 
		tags = {  "P2P", "消费分期", "借贷"  }, 
		testTelephones = { "15985268904", "18212345678" })
public class RenRenDaiSpider extends PapaSpider {

	public boolean checkTelephone(String account) {
		try {
			Request request = new Request.Builder().url("https://api.renrendai.com/n2/passport/index/doLogin")
					.post(createUrlEncodedForm(
							"version=2.0&clientVersion=50719&platform=ANDROID&channelCode=rrdweb&deviceInfo=%7B%22androidVersion%22%3A%2222%22%2C%22imei%22%3A%22865166021433753%22%2C%22imsi%22%3A%22460006926420389%22%2C%22manufacturer%22%3A%22oppo%22%2C%22mnc%22%3A%22%E4%B8%AD%E5%9B%BD%E7%A7%BB%E5%8A%A8%22%2C%22model%22%3A%22oppo%20r9%20plusm%20a%22%2C%22screenHeight%22%3A%22960%22%2C%22screenWidth%22%3A%22540%22%7D&model=oppo%20r9%20plusm%20a&sysVersion=22&appId=rrd&username="
									+ account
									+ "&password=XeTQ7aqqsL9%2BQ3dUFcT5%2BtTrFMjH%2BOJxI%2BbR%2FT9KVMwCRy8napU5cV%2FT1fhva%2BXEZ1yGSNLEdxPu%0AQ%2BKozWxV5dmshSJgq1QxIM8H58nWV%2FKVKg4s%2B1UHwG96H1Az2%2FgLEgDbynS87D2hYiMFjeGZiiWC%0AUKN3%2FdGrsm21rdQ17UE%3D%0A"))
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String body = response.body().string();
			return !body.contains("该帐号未注册");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean checkEmail(String account) {
		return false;
	}

	@Override
	public Map<String, String> getFields() {
		return null;
	}

}
