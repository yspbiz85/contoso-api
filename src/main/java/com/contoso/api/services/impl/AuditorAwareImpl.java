package com.contoso.api.services.impl;

import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
public class AuditorAwareImpl implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication.getName().equals("anonymousUser")) {
			return Optional.of("contoso");
		} else {
			return Optional.ofNullable(Optional.ofNullable(SecurityContextHolder.getContext())
					.map(e -> e.getAuthentication()).map(Authentication::getName).orElse("contoso"));
		}

	}

}
