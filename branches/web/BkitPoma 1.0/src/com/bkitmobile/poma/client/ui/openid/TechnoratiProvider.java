package com.bkitmobile.poma.client.ui.openid;

public class TechnoratiProvider extends Provider {
	public TechnoratiProvider() {
		setLogo("images/openid/technorati.png");
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Technorati";
	}

	@Override
	public String getURL() {
		// TODO Auto-generated method stub
		return "http://technorati.com/people/technorati/username/";
	}

}
