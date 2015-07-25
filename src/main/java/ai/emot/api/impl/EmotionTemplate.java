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

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import org.springframework.web.client.RestTemplate;

import ai.emot.api.EmotionOperations;
import ai.emot.core.BufferedImageFeaturesMapper;
import ai.emot.core.EmotionProfile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author Michael Lavelle
 *
 */
public class EmotionTemplate implements EmotionOperations {

	private RestTemplate restTemplate;
	private ObjectMapper objectMapper;
	private String apiBaseUrl;
	private int targetFaceImageWidth = 25;
	private int targetFaceImageHeight = 25;
	@SuppressWarnings("unused")
	private boolean isAuthorized;

	public EmotionTemplate(String apiBaseUrl, RestTemplate restTemplate,
			boolean isAuthorized) {
		this.restTemplate = new RestTemplate();
		this.objectMapper = new ObjectMapper();
		this.isAuthorized = isAuthorized;
		this.apiBaseUrl = apiBaseUrl;
	}

	@Override
	public EmotionProfile getFaceImageEmotionProfile(BufferedImage image) {
		String dataAsJson;
		try {

			BufferedImageFeaturesMapper bufferedImageFeaturesMapper = new BufferedImageFeaturesMapper(
					25, 25);
			dataAsJson = objectMapper
					.writeValueAsString(bufferedImageFeaturesMapper
							.toFeaturesVector(getScaledImage(image)));
			return restTemplate.postForObject(apiBaseUrl + "/face/emotion",
					dataAsJson, EmotionProfile.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return new EmotionProfile();
		}
	}

	private BufferedImage getScaledImage(BufferedImage image) {
		if (image.getWidth() == targetFaceImageWidth
				&& image.getHeight() == targetFaceImageHeight) {
			return image;
		} else {
			return toBufferedImage(image.getScaledInstance(
					targetFaceImageWidth, targetFaceImageHeight,
					Image.SCALE_SMOOTH));
		}
	}

	public static BufferedImage toBufferedImage(Image img) {
		if (img instanceof BufferedImage) {
			return (BufferedImage) img;
		}

		// Create a buffered image with transparency
		BufferedImage bimage = new BufferedImage(img.getWidth(null),
				img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

		// Draw the image on to the buffered image
		Graphics2D bGr = bimage.createGraphics();
		bGr.drawImage(img, 0, 0, null);
		bGr.dispose();

		// Return the buffered image
		return bimage;
	}

}
