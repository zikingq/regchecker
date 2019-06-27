package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;
import com.jisucloud.clawler.regagent.util.OCRDecode;
import com.jisucloud.deepsearch.selenium.Ajax;
import com.jisucloud.deepsearch.selenium.AjaxListener;
import com.jisucloud.deepsearch.selenium.ChromeAjaxListenDriver;
import com.jisucloud.deepsearch.selenium.HeadlessUtil;

import lombok.extern.slf4j.Slf4j;

import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@UsePapaSpider
public class YinDuoWangSpider implements PapaSpider {

	private ChromeAjaxListenDriver chromeDriver;
	private boolean checkTel = false;
	private boolean vcodeSuc = false;//验证码是否正确

	@Override
	public String message() {
		return "91旺财是九一金融旗下互联网网络借贷信息中介平台,北京市互联网金融行业协会副会长单位,中国互联网金融协会会员理事单位,公司法人许泽玮先生现任北京市互联网金融协会。";
	}

	@Override
	public String platform() {
		return "91wangcai";
	}

	@Override
	public String home() {
		return "91wangcai.com";
	}

	@Override
	public String platformName() {
		return "91旺财";
	}

	@Override
	public String[] tags() {
		return new String[] {"P2P", "借贷"};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new YinDuoWangSpider().checkTelephone("13910252045"));
//		System.out.println(new YinDuoWangSpider().checkTelephone("18210538513"));
//	}
	
	private String getImgCode() {
		for (int i = 0 ; i < 3; i++) {
			try {
				WebElement img = chromeDriver.findElementByCssSelector("#valicodeImg");
				img.click();
				Thread.sleep(1000);
				byte[] body = chromeDriver.screenshot(img);
				return OCRDecode.decodeImageCode(body);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "";
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			chromeDriver = HeadlessUtil.getChromeDriver(true, null, null);
			chromeDriver.quicklyVisit("https://www.baidu.com/link?url=bZ3z2HWzTi0fDvJ7ddVPW9aBsLVKOgsqcs_TEBYcVsVtk_ztXwNsjsVAbqYEf4tb&wd=&eqid=cdc510d90004fb09000000025d088c68");
			chromeDriver.get("https://www.yinduowang.com/login");
			chromeDriver.addAjaxListener(new AjaxListener() {
				
				@Override
				public String matcherUrl() {
					return "https://www.yinduowang.com/public/login";
				}
				
				@Override
				public String[] blockUrl() {
					return null;
				}
				
				@Override
				public void ajax(Ajax ajax) throws Exception {
					vcodeSuc = true;
					checkTel = ajax.getResponse().contains("密码错误") || ajax.getResponse().contains("锁定");
				}

				@Override
				public String fixPostData() {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public String fixGetData() {
					// TODO Auto-generated method stub
					return null;
				}
			});
			Thread.sleep(2000);
			chromeDriver.findElementById("mobile").sendKeys(account);
			chromeDriver.findElementById("pwd").sendKeys("lvnqwnk12mcxn");
			for (int i = 0; i < 5; i++) {
				chromeDriver.reInject();
				chromeDriver.findElementByCssSelector("div[class='login-btn userLogin login-btnactive']").click();
				Thread.sleep(3000);
				if (vcodeSuc) {
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (chromeDriver != null) {
				chromeDriver.quit();
			}
		}
		return checkTel;
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