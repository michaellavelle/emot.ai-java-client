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
package ai.emot.core;

import java.awt.image.BufferedImage;

import org.ml4j.algorithms.FeaturesMapper;

/**
 * 
 * @author Michael Lavelle
 *
 */
public class BufferedImageFeaturesMapper implements
		FeaturesMapper<BufferedImage> {

	private int width;
	private int height;

	public BufferedImageFeaturesMapper(int width, int height) {
		this.width = width;
		this.height = height;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public int getFeatureCount() {
		return width * height;
	}

	@Override
	public double[] toFeaturesVector(BufferedImage image) {
		double[] data = new double[image.getWidth() * image.getHeight()];

		int ind = 0;
		for (int w = 0; w < image.getWidth(); w++) {
			for (int h = 0; h < image.getHeight(); h++) {

				int color = image.getRGB(w, h);

				// extract each color component
				int red = (color >>> 16) & 0xFF;
				int green = (color >>> 8) & 0xFF;
				int blue = (color >>> 0) & 0xFF;

				// calc luminance in range 0.0 to 1.0; using SRGB luminance
				// constants
				float luminance = (red * 0.2126f + green * 0.7152f + blue * 0.0722f) / 255;
				data[ind++] = luminance;
			}
		}

		return data;
	}

}
