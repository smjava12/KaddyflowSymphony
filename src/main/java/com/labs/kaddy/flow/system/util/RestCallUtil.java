/**
 * 
 */
package com.labs.kaddy.flow.system.util;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;

import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * This Class is used to return RestTemplate instance
 * 
 * @author Surjyakanta
 *
 */
@Configuration
public class RestCallUtil {

	/**
	 * This method is used to return RestTemplate instance
	 * 
	 * @return RestTemplate instance
	 * @throws KeyManagementException    KeyManagementException
	 * @throws NoSuchAlgorithmException  NoSuchAlgorithmException
	 * @throws KeyStoreException  		 KeyStoreException
	 *
	 */
	@Primary
	@Bean
	//@LoadBalanced
	public RestTemplate loadBalancedRestTemplate()
			throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {

		TrustStrategy acceptingTrustStrategy = new TrustStrategy() {

			@Override
			public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				return true;
			}
		};

		SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy)
				.build();
		SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
		
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
				.register("https", csf).build();
		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		connectionManager.setMaxTotal(50);
		connectionManager.setDefaultMaxPerRoute(30);
		
		CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connectionManager).build();
		
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		requestFactory.setConnectTimeout(120000);
		requestFactory.setReadTimeout(120000);
		requestFactory.setHttpClient(httpClient);
		return new RestTemplate(requestFactory);
		
	}
}
