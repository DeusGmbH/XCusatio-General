package com.deusgmbh.xcusatio.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.thoughtworks.xstream.XStream;

/**
 * 
 * @author Lars.Dittert@de.ibm.com
 *
 */
public class StorageUnit<T> {
    private static final Logger LOGGER = Logger.getLogger(StorageUnit.class.getName());

    private ArrayList<T> units = new ArrayList<T>();
    private XStream xstream;

    private Class<T> parameterType;

    public StorageUnit(Class<T> parameterType) {
        this.parameterType = parameterType;
    }

    /**
     * 
     * @returns all elements managed by this class
     */
    public List<T> get() {
        return this.units;
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

            units = (ArrayList<T>) xstream.fromXML(stringBuilder.toString());
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

    public StorageUnit<T> addUnitToList(T object) {
        units.add(object);
        return this;
    }

    public StorageUnit<T> removeUnitFromList(T object) {
        units.remove(object);
        return this;
    }

    public StorageUnit<T> editUnit(int excuseID, T editedObj) {
        units.set(excuseID, editedObj);
        return this;
    }
}
