package com.deusgmbh.xcusatio.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;

import com.thoughtworks.xstream.XStream;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

/**
 * 
 * @author Lars.Dittert@de.ibm.com
 *
 */
abstract public class StorageUnit<T> {
    private static final Logger LOGGER = Logger.getLogger(StorageUnit.class.getName());

    private ObservableList<T> units = FXCollections.observableArrayList();
    private XStream xstream;

    private Class<T> parameterType;

    public StorageUnit(Class<T> parameterType) {
        this.parameterType = parameterType;

        // TODO: remove this after testing is done
        this.reset();

        this.units.addListener(new ListChangeListener<T>() {
            @Override
            public void onChanged(javafx.collections.ListChangeListener.Change<? extends T> c) {
                System.out.println("Changed on " + c.toString());
            }
        });
    }

    /**
     * 
     * @returns all elements managed by this class
     */
    public ObservableList<T> get() {
        return this.units;
    }

    public T get(int id) {
        return this.units.get(id);
    }

    /**
     * Loads XML file from path if present and parses it into the units array
     * 
     * @return <b>this</b>
     * @throws IOException
     * @throws FileNotFoundException
     */
    @SuppressWarnings("unchecked")
    public StorageUnit<T> load() throws FileNotFoundException, IOException {

        BufferedReader reader = new BufferedReader(new FileReader(parameterType.getSimpleName() + ".xml"));
        String line = null;
        StringBuilder stringBuilder = new StringBuilder();
        String ls = System.getProperty("line.separator");

        try {
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(ls);
            }

            units = (ObservableList<T>) xstream.fromXML(stringBuilder.toString());
        } finally {
            reader.close();
        }
        LOGGER.info("XML is load from " + parameterType.getSimpleName() + ".xml");
        return this;
    }

    /**
     * This should persist the units array to path
     * 
     * @return <b>this</b>
     */
    public StorageUnit<T> persist() {
        xstream.alias(parameterType.getSimpleName(), parameterType);
        String dataXml = xstream.toXML(this.units);
        File file = new File(parameterType.getSimpleName() + ".xml");

        try (FileOutputStream fop = new FileOutputStream(file)) {

            // if file doesn't exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }
            byte[] contentInBytes = dataXml.getBytes();

            fop.write(contentInBytes);
            fop.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        LOGGER.info("XML is saved into " + parameterType.getSimpleName() + ".xml");
        return this;
    }

    public StorageUnit<T> add(T object) {
        units.add(object);
        return this;
    }

    public StorageUnit<T> remove(T object) {
        units.remove(object);
        return this;
    }

    public StorageUnit<T> edit(int excuseID, T editedObj) {
        units.set(excuseID, editedObj);
        return this;
    }

    /**
     * Replaces all the current content with the default values by using
     * {@link #addDefaultValues()}. After that this change needs to be persisted
     * manually by calling {@link #persist()}
     * 
     * @returns this
     */
    public StorageUnit<T> reset() {
        this.units = FXCollections.observableArrayList();
        this.addDefaultValues();
        return this;
    }

    /**
     * Adds all default elements to this unit
     * 
     * @returns this
     */
    abstract public StorageUnit<T> addDefaultValues();

}
