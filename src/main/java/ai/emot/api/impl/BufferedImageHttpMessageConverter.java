package ai.emot.api.impl;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

public class BufferedImageHttpMessageConverter implements HttpMessageConverter<BufferedImage> {

    public List<MediaType> getSupportedMediaTypes() {
        return Collections.singletonList(new MediaType("image", "jpeg"));
    }

	@Override
	public boolean canRead(Class<?> clazz, MediaType mediaType) {		// TODO Auto-generated method stub
        return BufferedImage.class.equals(clazz);
	}

	@Override
	public boolean canWrite(Class<?> clazz, MediaType arg1) {
        return BufferedImage.class.equals(clazz);
	}

	@Override
	public BufferedImage read(Class<? extends BufferedImage> arg0,
			HttpInputMessage inputMessage) throws IOException,
			HttpMessageNotReadableException {
        return ImageIO.read(inputMessage.getBody());
	}

	@Override
	public void write(BufferedImage image, MediaType arg1, HttpOutputMessage message)
			throws IOException, HttpMessageNotWritableException {
    	ImageIO.write(image, "jpg", message.getBody());
	}

}