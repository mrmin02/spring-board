package com.busyvacation.aboard.db.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.busyvacation.aboard.db.dto.Member;
import com.busyvacation.aboard.db.repository.MemberJpaRepository;

@Service
public class MemberDetailsServiceImpl implements UserDetailsService{
	
	@Autowired
	private MemberJpaRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		System.out.println("UserDetailsServiceImpl: " + username);
		Member entityUser = userRepository.findByMemberid(username);
		
		if(ObjectUtils.isEmpty(entityUser)) {
			throw new UsernameNotFoundException("Invalid username");
		}
		UserDetails user = 
				User
				.withUsername(entityUser.getMemberid())
					.password(entityUser.getPassword())
					.username(entityUser.getMemberid())
					.authorities(AuthorityUtils.createAuthorityList("USER"))
					.roles("USER")
				.build();
		return user;
	}

}
