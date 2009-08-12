package com.bkitmobile.poma.client.ui.openid;

public class ClaimIDProvider extends Provider{

	public ClaimIDProvider() {
		setLogo("images/openid/claimid.png");
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "ClaimID";
	}

	@Override
	public String getURL() {
		// TODO Auto-generated method stub
		return "http://claimid.com/username";
	}

}
