package com.deusgmbh.xcusatio.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Logger;

import com.deusgmbh.xcusatio.data.tags.Tag;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.XStreamException;
import com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

/**
 * 
 * @author Lars.Dittert@de.ibm.com, Tobias.Schmidt@de.ibm.com
 *
 */
abstract public class DataManager<T> {
    private static final Logger LOGGER = Logger.getLogger(DataManager.class.getName());

    private ObservableList<T> units;

    private Class<T> parameterType;

    private static final String RESOURCES_PATH = "src/main/resources/";

    public DataManager(Class<T> parameterType) {
        this.parameterType = parameterType;
        this.units = FXCollections.observableArrayList();
        this.load();

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

    private String getFilePath() {
        return RESOURCES_PATH + parameterType.getSimpleName() + ".xml";
    }

    /**
     * Loads XML file from path if present and parses it into the units array
     * 
     * @return <b>this</b>
     */
    @SuppressWarnings("unchecked")
    public DataManager<T> load() {
        XStream xstream = this.getXStream();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(this.getFilePath()));

            String line = null;
            StringBuilder stringBuilder = new StringBuilder();
            String ls = System.getProperty("line.separator");

            try {
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                    stringBuilder.append(ls);
                }
            } finally {
                reader.close();

            }
            this.addAll((List<T>) xstream.fromXML(stringBuilder.toString()));
        } catch (XStreamException | IOException e) {
            this.reset();
            LOGGER.warning(
                    "Data could not be loaded from " + this.getFilePath() + " - Loaded default datasets instead!");
            LOGGER.warning("Data could not be loaded because: " + e.getMessage());
            return this;
        }
        LOGGER.info("XML is load from " + this.getFilePath());
        return this;
    }

    public DataManager<T> addAll(List<T> newUnits) {
        newUnits.forEach(unit -> units.add(unit));
        return this;
    }

    private XStream getXStream() {
        XStream xstream = new XStream();
        xstream.alias(parameterType.getSimpleName(), parameterType);
        xstream.alias(Tag.class.getSimpleName(), Tag.class);

        xstream.registerConverter(new AbstractSingleValueConverter() {

            public boolean canConvert(Class type) {
                return (type != null) && LocalDate.class.getPackage()
                        .equals(type.getPackage());
            }

            @Override
            public String toString(Object source) {
                return source.toString();
            }

            @Override
            public Object fromString(String str) {
                try {
                    return LocalDate.parse(str);
                } catch (Exception e) {
                    throw new RuntimeException("Error in date string: " + str);
                }
            }

        });

        return xstream;
    }

    /**
     * This should persist the units array to path
     * 
     * @return <b>this</b>
     */
    public DataManager<T> persist() {
        XStream xstream = this.getXStream();
        List<T> listToSave = new ArrayList<>();
        listToSave.addAll(this.units);
        String dataXml = xstream.toXML((List<T>) listToSave);
        File file = new File(this.getFilePath());

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
            throw new RuntimeException("could not save settings");
        }
        LOGGER.info("XML is saved into " + this.getFilePath());
        return this;
    }

    public DataManager<T> add(T object) {
        units.add(object);
        return this;
    }

    public DataManager<T> remove(T object) {
        units.remove(object);
        return this;
    }

    public DataManager<T> edit(int excuseID, T editedObj) {
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
    public DataManager<T> reset() {
        units.clear();
        this.addDefaultValues();
        return this;
    }

    /**
     * This method can be used to change an element managed by this class in a
     * way that is going to impact the observable list. This allows the editing
     * of objects that you don't know the id for.
     * 
     * @param object
     * @param action
     */
    public void applyFor(T object, Function<T, T> action) {
        for (int i = 0; i < this.units.size(); i++) {
            if (this.units.get(i)
                    .equals(object)) {
                this.units.set(i, action.apply(this.units.get(i)));
            }
        }
    }

    /**
     * Adds all default elements to this unit
     * 
     * @returns this
     */
    abstract public DataManager<T> addDefaultValues();

}
