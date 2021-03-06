package com.jisucloud.clawler.regagent.service.impl.borrow;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.Response;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import java.util.Map;



@Slf4j
@PapaSpiderConfig(
		home = "lianzidai.com", 
		message = "连资贷是一家注重风险防控、致力诚信经营的互联网金融平台,创立于2014年9月。借助移动支付和大数据等先进互联网技术,满足借款人和出借人双方需求,实现多方共赢。", 
		platform = "lianzidai", 
		platformName = "连资贷", 
		tags = { "P2P", "借贷" }, 
		testTelephones = { "13910200045", "18212345678" })
public class LianZiDaiSpider extends PapaSpider {

	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.lianzidai.com/user/checkMobilePhone.html?mobilePhone=" + account;
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.lianzidai.com")
					.addHeader("Referer", "https://www.lianzidai.com/user/register.html")
					.addHeader("X-Requested-With", "XMLHttpRequest")
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			if (res.contains("false")) {
				return true;
			}
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
