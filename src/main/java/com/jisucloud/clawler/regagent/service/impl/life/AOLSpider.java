package com.jisucloud.clawler.regagent.service.impl.life;

import com.deep077.spiderbase.selenium.mitm.ChromeAjaxHookDriver;
import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.i.PapaSpider;
import com.jisucloud.clawler.regagent.i.UsePapaSpider;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Set;

@Slf4j
//@UsePapaSpider 没有测试手机号
public class AOLSpider extends PapaSpider {

	private ChromeAjaxHookDriver chromeDriver;
	private boolean checkTel = false;

	@Override
	public String message() {
		return "美国在线（American Online），2000年至2009年期间是美国时代华纳的子公司，著名的因特网服务提供商。在2000年美国在线和时代华纳（Time Warner）宣布计划合并，2001年1月11日该交易被联邦贸易委员会（Federal Trade Commission）证实。合并及以后的运作的信息见时代华纳。";
	}

	@Override
	public String platform() {
		return "aol";
	}

	@Override
	public String home() {
		return "aol.com";
	}

	@Override
	public String platformName() {
		return "美国在线";
	}

	@Override
	public String[] tags() {
		return new String[] {"新闻" , "国外新闻"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("13910002005", "18210538513");
	}
	

	@Override
	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newInstanceWithGoogleProxy(true, true, CHROME_USER_AGENT);
			chromeDriver.get("https://login.aol.com/forgot?intl=us&lang=zh-cn&done=https%3A%2F%2Fwww.aol.com");
			smartSleep(2000);
			chromeDriver.findElementByLinkText("重新开始").click();
			smartSleep(2000);
			chromeDriver.findElementById("username").sendKeys("+86"+account);
			chromeDriver.findElementByCssSelector("button[type='submit']").click();
			smartSleep(3000);
//			String uiHeaderTitle = chromeDriver.findElementByCssSelector("h2[class='uiHeaderTitle']").getText();
//			return uiHeaderTitle.contains("重置密码");
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
