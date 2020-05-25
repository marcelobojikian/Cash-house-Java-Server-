package br.com.cashhouse.util.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.com.cashhouse.core.model.Profile;
import br.com.cashhouse.core.repository.ProfileRepository;

//@Component
public class DaoAuthenticationProvider implements AuthenticationProvider {
	
//	@Autowired
//	private PasswordEncoder passwordEncoder;
//	
//	@Autowired
//	ProfileRepository profileRepository;
// 
//    @Override
    public Authentication authenticate(Authentication authentication) {
//  
//        String name = authentication.getName();
//        String password = authentication.getCredentials().toString();
//        
//        String passwordEncoded = passwordEncoder.encode(password);
//
//        Optional<Profile> entity = profileRepository.findByUsername(name);
//        
//        if(entity.isPresent()) {
//        	Profile profile = entity.get();
//            
//            if (passwordEncoded.equals(profile.getPassword())) {
//            	Collection<? extends GrantedAuthority> authorities = profile.getAuthorities();
//                return new UsernamePasswordAuthenticationToken(profile, passwordEncoded, authorities);
//            }
//        	
//        }
//        
        return null;
    }
 
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
