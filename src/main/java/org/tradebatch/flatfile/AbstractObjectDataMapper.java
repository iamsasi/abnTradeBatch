package org.tradebatch.flatfile;

import org.easybatch.core.converter.TypeConverter;



public abstract class AbstractObjectDataMapper<T> {

	/**
     * The object mapper.
     */
    protected ObjectDataMapper<T> objectdataMapper;

    /**
     * Create an {@link AbstractRecordMapper}.
     *
     * @param recordClass the target type
     */
    public AbstractObjectDataMapper(Class<T> recordClass) {
        this.objectdataMapper = new ObjectDataMapper<>(recordClass);
    }

    /**
     * Register a custom type converter.
     *
     * @param typeConverter the type converter to user
     */
    public void registerTypeConverter(final TypeConverter<String, ?> typeConverter) {
        objectdataMapper.registerTypeConverter(typeConverter);
    }

}
