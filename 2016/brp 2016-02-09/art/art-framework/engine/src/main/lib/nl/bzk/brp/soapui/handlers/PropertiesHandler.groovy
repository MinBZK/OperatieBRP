package nl.bzk.brp.soapui.handlers

import com.eviware.soapui.impl.wsdl.MutableTestPropertyHolder
import com.eviware.soapui.model.TestPropertyHolder
import com.eviware.soapui.model.testsuite.TestProperty
import groovy.transform.CompileStatic

/**
 * Handler die omgaat met {@link Properties}.
 */
@CompileStatic
class PropertiesHandler {

    /**
     * Adds properties from path, file to target.
     * @param path
     * @param filename
     * @param target
     */
    static void addPropertiesTo(String path, String filename, TestPropertyHolder target) {
        File file = new File(path, filename)

        addPropertiesTo(file, target)
    }

    /**
     * Adds properties from file to target.
     *
     * @param file
     * @param target
     */
    static void addPropertiesTo(File file, TestPropertyHolder target) {
        Properties properties = loadProperties(file)

        setProperties(properties, target)
    }

    /**
     * Laadt een file in een {@link Properties} object.
     *
     * @param file het bestand
     * @return een {@link Properties} instantie
     */
    static Properties loadProperties(File file) {
        Properties properties = new Properties()
        file.withInputStream { InputStream stream -> properties.load(stream) }

        properties
    }

    /**
     * Laadt een .properties bestand in een {@link Properties} object.
     *
     * @param path de folder waarin het properties bestand staat
     * @param filename het properties bestand
     * @return een {@link Properties} instantie
     */
    static Properties loadProperties(String path, String filename) {
        path = makeDirWithEndingslash(path)
        File file = new File(path, filename)

        loadProperties(file)
    }

    /**
     * Zet properties op een object.
     *
     * @param props De te zetten properties
     * @param target het object waaraan ze worden toegevoegd
     */
    static void setProperties(Properties props, TestPropertyHolder target) {
        props.each { key, val ->
            target.setPropertyValue((String)key, (String)val)
        }
    }

    /**
     * Verwijdert alle properties van het target.
     *
     * @param target het object waarvan properties worden verwijdert
     */
    static void clearTestProperties(MutableTestPropertyHolder target) {
        target.propertyList.each { TestProperty prop ->
            target.removeProperty prop.name
        }
    }

    private static String makeDirWithEndingslash(String dir) {
        if (dir.substring(dir.length()) == '/') {
            return dir
        } else if (dir.substring(dir.length()) != '\\') {
            dir = dir + '/'
        }
        return dir
    }
}
