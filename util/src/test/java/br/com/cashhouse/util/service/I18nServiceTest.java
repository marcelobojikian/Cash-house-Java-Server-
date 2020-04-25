package br.com.cashhouse.util.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.context.request.ServletWebRequest;

public class I18nServiceTest {

	private I18nService i18nService = new I18nService();

	@Mock
	private HttpServletRequest request;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);

		ServletWebRequest webRequest = Mockito.mock(ServletWebRequest.class);
		when(webRequest.getNativeRequest()).thenReturn(request);
	}

	@Test
	public void shouldGetLocaleDefault() {

		Locale result = i18nService.resolveLocale(request);
		assertEquals(Locale.getDefault(), result);

		when(request.getHeader("Accept-Language")).thenReturn("");
		result = i18nService.resolveLocale(request);
		assertEquals(Locale.getDefault(), result);

	}

	@Test
	public void shodldGetEnLocale() {
		when(request.getHeader("Accept-Language")).thenReturn("en-US,en;q=0.8");
		Locale result = i18nService.resolveLocale(request);
		assertEquals(new Locale("en"), result);
	}

	@Test
	public void shouldGetEsLocale() {
		when(request.getHeader("Accept-Language")).thenReturn("es-MX,en-US;q=0.7,en;q=0.3");
		Locale result = i18nService.resolveLocale(request);
		assertEquals(new Locale("es"), result);
	}

	@Test
	public void shouldGetPtLocale() {
		when(request.getHeader("Accept-Language")).thenReturn("pt-MX,en-US;q=0.7,en;q=0.3");
		Locale result = i18nService.resolveLocale(request);
		assertEquals(new Locale("pt"), result);
	}

}
