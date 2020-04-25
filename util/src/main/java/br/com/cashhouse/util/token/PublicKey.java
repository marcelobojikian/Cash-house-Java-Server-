package br.com.cashhouse.util.token;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import br.com.cashhouse.core.exception.InvalidOperationException;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
public class PublicKey {

	private Resource resource = new ClassPathResource("publicKey.txt");

	public String getPublicKeyAsString() {
		try {
			return IOUtils.toString(resource.getInputStream(), UTF_8);
		} catch (IOException e) {
			throw new InvalidOperationException("Can't read public key");
		}
	}

}
