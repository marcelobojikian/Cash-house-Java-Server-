package br.com.cashhouse.heroku.spring.token;

import java.security.KeyPair;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties("security.jwt")
public class JwtToken {

	@Value("key-store")
	private Resource keyStore;

	@Value("key-store-password")
	private String keyStorePassword;

	@Value("key-pair-alias")
	private String keyPairAlias;

	@Value("key-pair-password")
	private String keyPairPassword;

	@Autowired
	private DataSource dataSource;

	@Bean
	public TokenStore tokenStore() {
		return new JdbcTokenStore(dataSource);
	}

	public KeyPair getKeyPair() {
		KeyStoreKeyFactory factory = new KeyStoreKeyFactory(keyStore, keyStorePassword.toCharArray());
		return factory.getKeyPair(keyPairAlias);
	}

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		KeyPair keyPair = getKeyPair();
		converter.setKeyPair(keyPair);
		return converter;
	}

}
