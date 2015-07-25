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

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Michael Lavelle
 *
 */
public class EmotionProfile implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Map<Emotion, Double> emotionRatings;

	public EmotionProfile(Map<Emotion, Double> emotionRatings) {
		this.emotionRatings = emotionRatings;
	}

	public EmotionProfile() {
		this.emotionRatings = new HashMap<Emotion, Double>();
	}

	public Map<Emotion, Double> getEmotionRatings() {
		return emotionRatings;
	}

	@Override
	public String toString() {
		return "EmotionProfile [emotionRatings=" + emotionRatings + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((emotionRatings == null) ? 0 : emotionRatings.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EmotionProfile other = (EmotionProfile) obj;
		if (emotionRatings == null) {
			if (other.emotionRatings != null)
				return false;
		} else if (!emotionRatings.equals(other.emotionRatings))
			return false;
		return true;
	}

}
