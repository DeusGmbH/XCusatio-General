package com.deusgmbh.xcusatio.data;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.logging.Logger;

import com.thoughtworks.xstream.XStream;

public class StorageUnit<T> {
    private static final Logger LOGGER = Logger.getLogger(StorageUnit.class.getName());
    protected final String path;

    protected ArrayList<T> units = new ArrayList<T>();
    protected XStream xstream;

    public StorageUnit(String path) {
        this.path = path;
        this.load();
    }

    public StorageUnit() {
        this("");
    }

    public StorageUnit<T> load() {

        return this;
    }

    public StorageUnit<T> persist() {
        Class<T> typeClass = this.getGenericTypeClass();
        xstream.alias(typeClass.getSimpleName(), typeClass);
        return this;
    }

    @SuppressWarnings("unchecked")
    private Class<T> getGenericTypeClass() {
        try {
            String className = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]
                    .getTypeName();
            LOGGER.info("ParamterType of Storage Unit is " + className);
            Class<?> clazz = Class.forName(className);
            return (Class<T>) clazz;
        } catch (Exception e) {
            throw new IllegalStateException("Class is not parametrized with generic type!!! Please use extends <> ");
        }
    }

}
