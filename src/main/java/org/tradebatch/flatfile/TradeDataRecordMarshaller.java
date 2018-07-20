
package org.tradebatch.flatfile;

import org.easybatch.core.field.BeanFieldExtractor;
import org.easybatch.core.field.FieldExtractor;
import org.easybatch.core.marshaller.RecordMarshaller;
import org.easybatch.core.record.Record;
import org.easybatch.core.record.StringRecord;

import java.beans.IntrospectionException;
import java.util.Iterator;



public class TradeDataRecordMarshaller<P> implements RecordMarshaller<Record<P>, StringRecord> {

    /**
     * Default delimiter.
     */
    public static final String DEFAULT_DELIMITER = ",";

    /**
     * Default qualifier.
     */
    public static final String DEFAULT_QUALIFIER = "\"";

    private String delimiter;
    private String qualifier;
    private FieldExtractor<P> fieldExtractor;

    /**
     * Create a new {@link TradeDataRecordMarshaller}.
     *
     * @param type   of object to marshal
     * @param fields to marshal in order
     * @throws IntrospectionException If the object to marshal cannot be introspected
     */
    public TradeDataRecordMarshaller(final Class<P> type, final String... fields) throws IntrospectionException {
        this(type, fields, DEFAULT_DELIMITER, DEFAULT_QUALIFIER);
    }

    /**
     * Create a new {@link TradeDataRecordMarshaller}
     *
     * @param type      of object to marshal
     * @param fields    of fields to marshal in order
     * @param delimiter of fields
     * @throws IntrospectionException If the object to marshal cannot be introspected
     */
    public TradeDataRecordMarshaller(final Class<P> type, final String[] fields, final String delimiter) throws IntrospectionException {
        this(type, fields, delimiter, DEFAULT_QUALIFIER);
    }

    /**
     * Create a new {@link TradeDataRecordMarshaller}
     *
     * @param type      of object to marshal
     * @param fields    to marshal in order
     * @param delimiter of fields
     * @param qualifier of fields
     * @throws IntrospectionException If the object to marshal cannot be introspected
     */
    public TradeDataRecordMarshaller(final Class<P> type, final String[] fields, final String delimiter, final String qualifier) throws IntrospectionException {
        this(new BeanFieldExtractor<>(type, fields), delimiter, qualifier);
    }

    /**
     * Create a new {@link TradeDataRecordMarshaller}
     *
     * @param fieldExtractor extract fields
     * @param delimiter      of fields
     * @param qualifier      of fields
     * @throws IntrospectionException If the object to marshal cannot be introspected
     */
    public TradeDataRecordMarshaller(FieldExtractor<P> fieldExtractor, final String delimiter, final String qualifier) throws IntrospectionException {
        this.fieldExtractor = fieldExtractor;
        this.delimiter = delimiter;
        this.qualifier = qualifier;
    }

    @Override
    public StringRecord processRecord(final Record<P> record) throws Exception {
        Iterable<Object> values = fieldExtractor.extractFields(record.getPayload());
        StringBuilder stringBuilder = new StringBuilder();
        Iterator<?> iterator = values.iterator();
        while (iterator.hasNext()) {
            Object value = iterator.next();
            stringBuilder.append(qualifier);
            stringBuilder.append(value);
            stringBuilder.append(qualifier);
            if (iterator.hasNext()) {
                stringBuilder.append(delimiter);
            }
        }
        return new StringRecord(record.getHeader(), stringBuilder.toString());
    }
}
