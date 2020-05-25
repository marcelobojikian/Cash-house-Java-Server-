package br.com.cashhouse.util.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.cashhouse.core.model.Profile;
import br.com.cashhouse.core.repository.ProfileRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	ProfileRepository profileRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Profile> entity = profileRepository.findByUsername(username);
        return entity.orElseThrow(() -> new UsernameNotFoundException(username));
	}

}
