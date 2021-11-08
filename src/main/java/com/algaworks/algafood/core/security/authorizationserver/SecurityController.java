package com.algaworks.algafood.core.security.authorizationserver;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController {
	
	
	@GetMapping("/login")
	public String login() {
		return "pages/login";  // http://localhost:8080/oauth/authorize?response_type=code&client_id=foodanalytics&state=abc&redirect_uri=http://www.foodanalytics.local:8082
	}
	
	//WhitelabelApprovalEndpoint classe referencia do sprint
	
	@GetMapping("/oauth/confirm_access")
	public String approval() {
		return "pages/approval";
	}
	
}
