package at.jpaeof.model.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.webobjects.foundation.NSData;

@Converter
public class NSDataConverter implements AttributeConverter<NSData, byte[]> {

	@Override
	public NSData convertToEntityAttribute(byte[] b) {
		if (b == null) {
			return null;
		}
		return new NSData(b);
	}

	@Override
	public byte[] convertToDatabaseColumn(NSData d) {
		if (d == null) {
			return null;
		}
		return d.bytes();
	}

}
