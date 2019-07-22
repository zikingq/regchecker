package com.jisucloud.clawler.regagent.service.impl.life;

import com.deep077.spiderbase.selenium.mitm.ChromeAjaxHookDriver;
import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.i.PapaSpider;
import com.jisucloud.clawler.regagent.i.UsePapaSpider;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Set;

@Slf4j
//@UsePapaSpider
public class YahooSpider extends PapaSpider {

	private ChromeAjaxHookDriver chromeDriver;
	private boolean checkTel = false;

	@Override
	public String message() {
		return "雅虎（英文名称：Yahoo！，NASDAQ：YHOO）是美国著名的互联网门户网站，也是20世纪末互联网奇迹的创造者之一。其服务包括搜索引擎、电邮、新闻等，业务遍及24个国家和地区，为全球超过5亿的独立用户提供多元化的网络服务。同时也是一家全球性的因特网通讯、商贸及媒体公司。";
	}

	@Override
	public String platform() {
		return "yahoo";
	}

	@Override
	public String home() {
		return "yahoo.com";
	}

	@Override
	public String platformName() {
		return "雅虎";
	}

	@Override
	public String[] tags() {
		return new String[] {"新闻" , "搜索引擎"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("13910002005", "18210538513");
	}
	
	@Override
	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newInstanceWithGoogleProxy(true, false, IOS_USER_AGENT);
			chromeDriver.get("https://login.yahoo.com/forgot?.intl=us&.lang=zh-CN&.src=fpctx&done=https%3A%2F%2Fwww.yahoo.com");
			smartSleep(1000);
			chromeDriver.findElementById("username").sendKeys("+86"+account);
			chromeDriver.findElementByCssSelector("button[name='verifyYid']").click();
			smartSleep(60000);
			if (chromeDriver.checkElement("#MemberNameError")) {
				return true;
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
