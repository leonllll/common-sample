package com.leonlu.code.sample.http;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.core.MediaType;

import org.apache.wink.client.ClientConfig;
import org.apache.wink.client.ClientResponse;
import org.apache.wink.client.ClientRuntimeException;
import org.apache.wink.client.Resource;
import org.apache.wink.client.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 
	<dependency>
	    <groupId>org.apache.wink</groupId>
	    <artifactId>wink-client</artifactId>
	    <version>1.4</version>
	</dependency>
 * @author leon
 *
 */
public class WinkSample {
	public static final Logger logger = LoggerFactory.getLogger(WinkSample.class);
	
	private static String restRequest(String resourceURL, Map<String, Object> httpParams, String httpMethod) {
		ClientConfig config = new ClientConfig();
		RestClient restClient = new RestClient(config);
		logger.info("requestToServer resourceURL " + resourceURL + ", httpParams = " + httpParams);
		ClientResponse clientResponse;
		try {
			Resource resource = restClient
					.resource(resourceURL)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON);
			
			httpMethod = httpMethod.toUpperCase();
			if(httpMethod.equals("POST")) {
				clientResponse = resource.post(httpParams);
			} else if(httpMethod.equals("DELETE")) {
				clientResponse = resource.delete();
			} else if(httpMethod.equals("PUT")) {
				clientResponse = resource.put(httpParams);
			} else if(httpMethod.equals("GET")) {
				clientResponse = resource.get();
			} else {
				String message = "http method " + httpMethod + " can't be handled";
				logger.error(message);
				throw new RuntimeException(message);	
			}
		} catch (ClientRuntimeException e) {
			logger.debug(e.getMessage());
			throw new RuntimeException(e);	
		}
		logger.info("Response from " + resourceURL + ", Status: " + clientResponse.getStatusCode() + " " + clientResponse.getMessage());
		if (clientResponse.getStatusCode() == 200) {
			logger.debug("requestToServer resourceURL " + resourceURL + " successfully");
			return clientResponse.getEntity(String.class);
		} else {
			String message = "Incorrect response from " + resourceURL + 
					". status code: " + clientResponse.getStatusCode() +
					", message: " + clientResponse.getMessage() + 
					", content: " + clientResponse.getEntity(String.class);
					
			logger.error(message);
			throw new RuntimeException(message);	
		}
	}

	/**
	 * trust all certificates when making a request though HTTPS
	 * this function should be called before doing the request
	 * @throws RuntimeException
	 */
	public static void trustAllCertificates() throws RuntimeException {
		
		try {
			TrustManager[] trustManager = new TrustManager[] {
					new X509TrustManager() {
						@Override
						public java.security.cert.X509Certificate[] getAcceptedIssuers() {
							return null;
						}
						@Override
						public void checkClientTrusted(
							java.security.cert.X509Certificate[] certs, String authType) {
						}
						@Override
						public void checkServerTrusted(
							java.security.cert.X509Certificate[] certs, String authType) {
						}
					}
				};
			
			SSLContext sc = SSLContext.getInstance("TLS");//or SSL, it depends on the certificate
			sc.init(null, trustManager, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
			
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e.getMessage());
		} catch (KeyManagementException e) {
			throw new RuntimeException(e.getMessage());
		} 
	}
}
