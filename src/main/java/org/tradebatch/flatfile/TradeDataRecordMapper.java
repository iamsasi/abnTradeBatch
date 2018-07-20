package org.tradebatch.flatfile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.easybatch.core.mapper.RecordMapper;
import org.easybatch.core.record.GenericRecord;
import org.easybatch.core.record.Record;
import org.easybatch.core.record.StringRecord;
import org.tradebatch.flatfile.Field;

/**
 * 
 * @author Sasi Nataraja
 *
 * @param <P>
 */
public class TradeDataRecordMapper<P> extends AbstractObjectDataMapper<P> implements RecordMapper<StringRecord, Record<P>> {

    /**
     * Default whitespace trimming value.
     */
    public static final boolean DEFAULT_WHITESPACE_TRIMMING = false;

    private int[] fieldsLength;
    private int[] fieldsOffsets;
    private String[] fieldNames;
    private int recordExpectedLength;
    private boolean trimWhitespaces = DEFAULT_WHITESPACE_TRIMMING;

    /**
     * Create a new {@link TradeDataRecordMapper} instance.
     *
     * @param recordClass  the target domain object class
     * @param fieldsLength an array of fields length in the same order in the FLR flat file.
     * @param fieldNames   a String array representing fields name in the same order in the FLR flat file.
     */
    public TradeDataRecordMapper(Class<P> recordClass, int[] fieldsLength, String[] fieldNames) {
        super(recordClass);
        this.fieldsLength = fieldsLength.clone();
        this.fieldNames = fieldNames.clone();
        objectdataMapper = new ObjectDataMapper<>(recordClass);
        for (int fieldLength : fieldsLength) {
            recordExpectedLength += fieldLength;
        }
        fieldsOffsets = calculateOffsets(fieldsLength);
    }

    @Override
    public Record<P> processRecord(final StringRecord record) throws Exception {

        List<Field> fields = parseRecord(record);
        Map<String, String> fieldsContents = new HashMap<>();
        for (Field field : fields) {
            String fieldName = fieldNames[field.getIndex()];
            String fieldValue = field.getRawContent();
            if(fieldValue.trim().isEmpty() || fieldValue==null) {
            	fieldValue ="0";// temp fix @TODO: discuss possibilities
            }
            fieldsContents.put(fieldName, fieldValue);
        }
        return new GenericRecord<>(record.getHeader(), objectdataMapper.mapObject(fieldsContents));
    }

    protected List<Field> parseRecord(final StringRecord record) throws Exception {

        String payload = record.getPayload();
        int recordLength = payload.length();

        if (recordLength != recordExpectedLength) {
            throw new Exception("record length " + recordLength + " not equal to expected length of " + recordExpectedLength);
        }

        List<Field> fields = new ArrayList<>();
        for (int i = 0; i < fieldsLength.length; i++) {
            String token = payload.substring(fieldsOffsets[i], fieldsOffsets[i + 1]);
            token = trimWhitespaces(token);
            Field field = new Field(i, token);
            fields.add(field);
        }

        return fields;
    }


    // utility method to calculate field offsets used to extract fields from record.
    private int[] calculateOffsets(final int[] lengths) {
        int[] offsets = new int[lengths.length + 1];
        offsets[0] = 0;
        for (int i = 0; i < lengths.length; i++) {
            offsets[i + 1] = offsets[i] + lengths[i];
        }
        return offsets;
    }

    private String trimWhitespaces(final String token) {
        if (trimWhitespaces) {
            return token.trim();
        }
        return token;
    }

    /**
     * Trim white spaces when parsing the fixed length record.
     *
     * @param trimWhitespaces true if whitespaces should be trimmed
     */
    public void setTrimWhitespaces(final boolean trimWhitespaces) {
        this.trimWhitespaces = trimWhitespaces;
    }

}
