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

import java.awt.image.BufferedImage;
import java.util.Arrays;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import ai.emot.api.EmotionOperations;
import ai.emot.core.EmotionProfile;

/**
 * 
 * @author Michael Lavelle
 *
 */
public class EmotionTemplate implements EmotionOperations {

	private RestTemplate restTemplate;
	private String apiBaseUrl;
	@SuppressWarnings("unused")
	private boolean isAuthorized;

	public EmotionTemplate(String apiBaseUrl, RestTemplate restTemplate,
			boolean isAuthorized) {
		this.restTemplate = restTemplate;
		this.isAuthorized = isAuthorized;
		this.apiBaseUrl = apiBaseUrl;
	}

	@Override
	public EmotionProfile getFaceImageEmotionProfile(BufferedImage image) {
	
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			headers.setContentType(MediaType.IMAGE_JPEG);
			HttpEntity<BufferedImage> entity = new HttpEntity<BufferedImage>(image, headers);

			
			ResponseEntity<EmotionProfile> response = restTemplate.exchange(apiBaseUrl + "/face/emotion", HttpMethod.POST, entity, EmotionProfile.class);
			return response.getBody();
			
	}
}
