package com.kapil.covid19.authetication;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kapil.covid19.domain.User;
import com.kapil.covid19.services.UserDetailService;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {
	
	private UserDetailService userDetailService;

	public JwtUserDetailsServiceImpl(UserDetailService userDetailService) {
		super();
		this.userDetailService = userDetailService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
			
		User user = userDetailService.getByEmailAddress(username);
		UserDetails userDetails = new JwtUserDetails(user.getId(), user.getEmailAddress(), 
				user.getPassword(), "USER");
				
		return userDetails;
	}
}


