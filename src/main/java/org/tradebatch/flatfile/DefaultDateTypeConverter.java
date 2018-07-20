package org.tradebatch.flatfile;

import static org.easybatch.core.util.Utils.checkArgument;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.easybatch.core.converter.TypeConverter;



public class DefaultDateTypeConverter implements TypeConverter<String, Date> {

    /**
     * The default date format.
     */
    public static final String DEFAULT_DATE_FORMAT = "yyyyMMdd";

    /**
     * The date format to use.
     */
    private String dateFormat;

    /**
     * Create a Date converter with the default format {@link org.easybatch.core.converter.DateTypeConverter#DEFAULT_DATE_FORMAT}
     */
    public DefaultDateTypeConverter() {
        this(DEFAULT_DATE_FORMAT);
    }

    /**
     * Create a Date converter with the specified date format.
     *
     * @param dateFormat the date format to use
     */
    public DefaultDateTypeConverter(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Date convert(final String value) {
        checkArgument(value != null, "Value to convert must not be null");
        checkArgument(!value.isEmpty(), "Value to convert must not be empty");
        try {
            return new SimpleDateFormat(dateFormat).parse(value);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Unable to convert value '" + value + "' to a Date object with format "
                    + dateFormat, e);
        }
    }


}
