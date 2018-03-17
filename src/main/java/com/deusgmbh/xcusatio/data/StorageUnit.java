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

    /**
     * Adds a unit to the units array
     * 
     * @param unit
     * @return <b>this</b>
     */
    public StorageUnit<T> add(T unit) {
        units.add(unit);
        return this;
    }

    /**
     * 
     * @param unit
     * @return <b>this</b>
     */
    public StorageUnit<T> remove(T unit) {
        // TODO: implement
        return this;
    }

    /**
     * 
     * @param id
     * @return element with id
     */
    public T get(int id) {
        return units.get(id);
    }

    /**
     * Loads XML file from path if present and parses it into the units array
     * 
     * @return <b>this</b>
     */
    public StorageUnit<T> load() {

        return this;
    }

    /**
     * This should persist the units array to path
     * 
     * @return <b>this</b>
     */
    public StorageUnit<T> persist() {
        Class<T> typeClass = this.getGenericTypeClass();
        xstream.alias(typeClass.getSimpleName(), typeClass);
        // not finished

        return this;
    }

    /**
     * 
     * @returns the class of the generic type of <b>this</b>
     */
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
