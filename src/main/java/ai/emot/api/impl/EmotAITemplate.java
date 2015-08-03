/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ai.emot.api.impl;

import java.util.List;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.support.ClientHttpRequestFactorySelector;
import org.springframework.web.client.RestTemplate;

import ai.emot.api.EmotAI;
import ai.emot.api.EmotionOperations;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Michael Lavelle
 */
public class EmotAITemplate extends AbstractOAuth2ApiBinding implements EmotAI {

	private EmotionOperations emotionOperations;

	private ObjectMapper objectMapper;

	/**
	 * Create a new instance of EmotAI. This constructor creates the
	 * EmotAITemplate using a given access token and apiBaseUrl
	 * 
	 * @param qpiBaseUrl
	 * @param accessToken
	 */
	public EmotAITemplate(String qpiBaseUrl, String accessToken) {
		super(accessToken);
		initialize(qpiBaseUrl, accessToken);
	}

	@Override
	protected List<HttpMessageConverter<?>> getMessageConverters() {
		List<HttpMessageConverter<?>> messageConverters = super
				.getMessageConverters();
		messageConverters.add(new BufferedImageHttpMessageConverter());
		return messageConverters;
	}

	@Override
	protected void configureRestTemplate(RestTemplate restTemplate) {
	}

	private void initSubApis(String oauthApiBaseUrl, String accessToken) {

		emotionOperations = new EmotionTemplate(oauthApiBaseUrl,
				getRestTemplate(), isAuthorized());

	}
	
	

	@Override
	protected MappingJackson2HttpMessageConverter getJsonMessageConverter() {
		MappingJackson2HttpMessageConverter converter = super
				.getJsonMessageConverter();
		objectMapper = new ObjectMapper();
		converter.setObjectMapper(objectMapper);
		return converter;
	}

	// private helpers
	private void initialize(String apiBaseUrl, String accessToken) {
		// Wrap the request factory with a BufferingClientHttpRequestFactory so
		// that the error handler can do repeat reads on the response.getBody()
		super.setRequestFactory(ClientHttpRequestFactorySelector
				.bufferRequests(getRestTemplate().getRequestFactory()));
		initSubApis(apiBaseUrl, accessToken);

	}

	@Override
	public EmotionOperations emotionOperations() {
		return emotionOperations;
	}

}
