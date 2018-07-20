package org.tradebatch.flatfile;

import static java.lang.String.format;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.easybatch.core.converter.AtomicIntegerTypeConverter;
import org.easybatch.core.converter.AtomicLongTypeConverter;
import org.easybatch.core.converter.BigDecimalTypeConverter;
import org.easybatch.core.converter.BigIntegerTypeConverter;
import org.easybatch.core.converter.BooleanTypeConverter;
import org.easybatch.core.converter.ByteTypeConverter;
import org.easybatch.core.converter.CharacterTypeConverter;
import org.easybatch.core.converter.DoubleTypeConverter;
import org.easybatch.core.converter.FloatTypeConverter;
import org.easybatch.core.converter.GregorianCalendarTypeConverter;
import org.easybatch.core.converter.IntegerTypeConverter;
import org.easybatch.core.converter.LongTypeConverter;
import org.easybatch.core.converter.ShortTypeConverter;
import org.easybatch.core.converter.SqlDateTypeConverter;
import org.easybatch.core.converter.SqlTimeTypeConverter;
import org.easybatch.core.converter.SqlTimestampTypeConverter;
import org.easybatch.core.converter.StringTypeConverter;
import org.easybatch.core.converter.TypeConverter;
import org.easybatch.core.mapper.BeanIntrospectionException;
import org.easybatch.core.mapper.ObjectMapper;
import org.easybatch.core.mapper.TypeConverterRegistrationException;

public class ObjectDataMapper<T> {


    private static final Logger LOGGER = Logger.getLogger(ObjectMapper.class.getName());

    private Class<T> objectType;
    private Map<String, Method> setters;
    private Map<Class<?>, TypeConverter<String, ?>> typeConverters;

    /**
     * Create a new {@link ObjectMapper}.
     *
     * @param objectType the target object type
     */
    public ObjectDataMapper(final Class<T> objectType) {
        this.objectType = objectType;
        initializeTypeConverters();
        initializeSetters();
    }

    /**
     * Map values to fields of the target object type.
     *
     * @param values fields values
     * @return A populated instance of the target type.
     * @throws Exception if values cannot be mapped to target object fields
     */
    public T mapObject(final Map<String, String> values) throws Exception {

        T result = createInstance();

        // for each field
        for (Map.Entry<String, String> entry : values.entrySet()) {

            String field = entry.getKey();
            //get field raw value
            String value = values.get(field);

            Method setter = setters.get(field);
            if (setter == null) {
                LOGGER.log(Level.WARNING, "No public setter found for field {0}, this field will be set to null (if object type) or default value (if primitive type)", field);
                continue;
            }

            Class<?> type = setter.getParameterTypes()[0];
            TypeConverter<String, ?> typeConverter = typeConverters.get(type);
            if (typeConverter == null) {
                LOGGER.log(Level.WARNING,
                        "Type conversion not supported for type {0}, field {1} will be set to null (if object type) or default value (if primitive type)",
                        new Object[]{type, field});
                continue;
            }

            if (value == null) {
                LOGGER.log(Level.WARNING, "Attempting to convert null to type {0} for field {1}, this field will be set to null (if object type) or default value (if primitive type)", new Object[]{type, field});
                continue;
            }

            if (value.isEmpty()) {
                LOGGER.log(Level.FINE, "Attempting to convert an empty string to type {0} for field {1}, this field will be ignored", new Object[]{type, field});
                continue;
            }

            convertValue(result, field, value, setter, type, typeConverter);

        }

        return result;
    }

    private void initializeSetters() {
        setters = new HashMap<>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(objectType);
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            getSetters(propertyDescriptors);
        } catch (IntrospectionException e) {
            throw new BeanIntrospectionException("Unable to introspect target type " + objectType.getName(), e);
        }
    }

    private void getSetters(PropertyDescriptor[] propertyDescriptors) {
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            setters.put(propertyDescriptor.getName(), propertyDescriptor.getWriteMethod());
        }
        //exclude property "class"
        setters.remove("class");
    }

    private T createInstance() throws Exception {
        try {
            return objectType.newInstance();
        } catch (Exception e) {
            throw new Exception(format("Unable to create a new instance of target type %s", objectType.getName()), e);
        }
    }

    private void convertValue(Object result, String field, String value, Method setter, Class<?> type, TypeConverter<String, ?> typeConverter) throws Exception {
        try {
            Object typedValue = typeConverter.convert(value);
            setter.invoke(result, typedValue);
        } catch (Exception e) {
            throw new Exception(format("Unable to convert %s to type %s for field %s", value, type, field), e);
        }
    }

    private void initializeTypeConverters() {
        typeConverters = new HashMap<>();
        typeConverters.put(AtomicInteger.class, new AtomicIntegerTypeConverter());
        typeConverters.put(AtomicLong.class, new AtomicLongTypeConverter());
        typeConverters.put(BigDecimal.class, new BigDecimalTypeConverter());
        typeConverters.put(BigInteger.class, new BigIntegerTypeConverter());
        typeConverters.put(Boolean.class, new BooleanTypeConverter());
        typeConverters.put(Boolean.TYPE, new BooleanTypeConverter());
        typeConverters.put(Byte.class, new ByteTypeConverter());
        typeConverters.put(Byte.TYPE, new ByteTypeConverter());
        typeConverters.put(Character.class, new CharacterTypeConverter());
        typeConverters.put(Character.TYPE, new CharacterTypeConverter());
        typeConverters.put(Double.class, new DoubleTypeConverter());
        typeConverters.put(Double.TYPE, new DoubleTypeConverter());
        typeConverters.put(Float.class, new FloatTypeConverter());
        typeConverters.put(Float.TYPE, new FloatTypeConverter());
        typeConverters.put(Integer.class, new IntegerTypeConverter());
        typeConverters.put(Integer.TYPE, new IntegerTypeConverter());
        typeConverters.put(Long.class, new LongTypeConverter());
        typeConverters.put(Long.TYPE, new LongTypeConverter());
        typeConverters.put(Short.class, new ShortTypeConverter());
        typeConverters.put(Short.TYPE, new ShortTypeConverter());
        typeConverters.put(java.util.Date.class, new DefaultDateTypeConverter());
        typeConverters.put(java.util.Calendar.class, new GregorianCalendarTypeConverter());
        typeConverters.put(java.util.GregorianCalendar.class, new GregorianCalendarTypeConverter());
        typeConverters.put(java.sql.Date.class, new SqlDateTypeConverter());
        typeConverters.put(java.sql.Time.class, new SqlTimeTypeConverter());
        typeConverters.put(java.sql.Timestamp.class, new SqlTimestampTypeConverter());
        typeConverters.put(String.class, new StringTypeConverter());
    }

    public void registerTypeConverter(final TypeConverter<String, ?> typeConverter) {
        //retrieve the target class name of the converter
		Class<? extends TypeConverter> typeConverterClass = typeConverter.getClass();
        Type[] genericInterfaces = typeConverterClass.getGenericInterfaces();
        Type genericInterface = genericInterfaces[0];
        if (!(genericInterface instanceof ParameterizedType)) {
            LOGGER.log(Level.WARNING, "The type converter {0} should be a parametrized type", typeConverterClass.getName());
            return;
        }
        ParameterizedType parameterizedType = (ParameterizedType) genericInterface;
        Type type = parameterizedType.getActualTypeArguments()[1];

        // register the converter
        try {
			Class clazz = Class.forName(getClassName(type));
            typeConverters.put(clazz, typeConverter);
        } catch (ClassNotFoundException e) {
            throw new TypeConverterRegistrationException("Unable to register custom type converter " + typeConverterClass.getName(), e);
        }
    }

    private String getClassName(Type actualTypeArgument) {
        // FIXME : find a clean way for this
        return actualTypeArgument.toString().substring(6);
    }


}
