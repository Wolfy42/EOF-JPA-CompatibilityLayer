package er.extensions.jpa.converter;

import java.util.Date;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.webobjects.foundation.NSTimestamp;

@Converter
public class NSTimestampConverter implements AttributeConverter<NSTimestamp, Date> {

	@Override
	public NSTimestamp convertToEntityAttribute(Date d) {
		if (d == null) {
			return null;
		}
		return new NSTimestamp(d);
	}

	@Override
	public Date convertToDatabaseColumn(NSTimestamp t) {
		return t;
	}

}
