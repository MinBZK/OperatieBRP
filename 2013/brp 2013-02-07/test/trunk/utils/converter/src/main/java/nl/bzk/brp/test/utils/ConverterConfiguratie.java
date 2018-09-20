/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.test.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.io.IOUtils;

public class ConverterConfiguratie {

    private static final XPath XPATH = XPathFactory.newInstance().newXPath();

    private boolean debug;
    private List<File> inputFiles;
    private File templateFile;
    private Map<String, String> findReplaceValues;
    private Map<String, String> defaultBestaansrechtValues;
    private Map<String, String> defaultGeenBestaansrechtValues;
    private Map<String, String> structuurMapping;
    private Map<String, String> nieuwNaarOudMapping;
    private List<String> nieuweTags;
    private File padNaarOutputFolder;
    
    private ConverterConfiguratie(
            final boolean debug,
            final List<File> inputFiles,
            final File templateFile,
            final Map<String, String> findReplaceValues,
            final Map<String, String> defaultBestaansrechtValues,
            final Map<String, String> defaultGeenBestaansrechtValues,
            final Map<String, String> structuurMapping,
            final Map<String, String> nieuwNaarOudMapping,
            final List<String> nieuweTags,
            final File padNaarOutputFolder) {
        this.debug = debug;
        this.inputFiles = inputFiles;
        this.templateFile = templateFile;
        this.findReplaceValues = findReplaceValues;
        this.defaultBestaansrechtValues = defaultBestaansrechtValues;
        this.defaultGeenBestaansrechtValues = defaultGeenBestaansrechtValues;
        this.structuurMapping = structuurMapping;
        this.nieuwNaarOudMapping = nieuwNaarOudMapping;
        this.nieuweTags = nieuweTags;
        this.padNaarOutputFolder = padNaarOutputFolder;
    }

    public boolean isDebug() {
        return debug;
    }
    
    public List<File> getInputFiles() {
        return inputFiles;
    }

    public File getTemplateFile() {
        return templateFile;
    }

    public Map<String, String> getFindReplaceValues() {
        return findReplaceValues;
    }

    public Map<String, String> getDefaultBestaansrechtValues() {
        return defaultBestaansrechtValues;
    }    

    public Map<String, String> getDefaultGeenBestaansrechtValues() {
        return defaultGeenBestaansrechtValues;
    }

    public Map<String, String> getStructuurMapping() {
        return structuurMapping;
    }

    public Map<String, String> getNieuwNaarOudMapping() {
        return nieuwNaarOudMapping;
    }

    public List<String> getNieuweTags() {
        return nieuweTags;
    }

    public File getPadNaarOutputFolder() {
        return padNaarOutputFolder;
    }

    public static ConverterConfiguratie maakConfiguratieUitParameters(
            final boolean debug,
            final File padNaarInputFiles, final File padNaarTemplate,
            final File padNaarFindReplace, final File padNaarDefaultsBestaansrecht,
            final File padNaarDefaultsGeenBestaansrecht,
            final File padNaarStructuur, final File padNaarMapping,
            final File padNaarNieuw, final File padNaarOutputFolder) throws Exception
    {
        // De input files zijn alle bestanden in de meegegeven map met de extensie 'xml'.
        List<File> inputFiles = Arrays.asList(padNaarInputFiles.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(final File dir, final String name) {
                return name.toLowerCase().endsWith(".xml");
            }
        }));
        
        //TODO: Uitbreiding van de checks: kijken of de xpaths wel 'passen' op de oude en nieuwe xml.

        Map<String, String> findReplaceValues = maakPropertyMap(padNaarFindReplace, false, false);
        Map<String, String> defaultBestaansrechtValues = maakPropertyMap(padNaarDefaultsBestaansrecht, true, false);
        Map<String, String> defaultGeenBestaansrechtValues = maakPropertyMap(padNaarDefaultsGeenBestaansrecht, true, false);
        Map<String, String> structuurMapping = maakPropertyMap(padNaarStructuur, true, true);
        Map<String, String> nieuwNaarOudMapping = maakPropertyMap(padNaarMapping, true, true);
        List<String> nieuweTags = IOUtils.readLines(new FileInputStream(padNaarNieuw));
        
        return new ConverterConfiguratie(debug, inputFiles, padNaarTemplate, findReplaceValues,
                defaultBestaansrechtValues, defaultGeenBestaansrechtValues, structuurMapping,
                nieuwNaarOudMapping, nieuweTags, padNaarOutputFolder);
    }

    private static Map<String, String> maakPropertyMap(
            final File propertiesFile, final boolean keyIsXpath, final boolean valueIsXpath) throws Exception {
        // Pre-processing: zet escape character voor dubbele punten.
        String inhoud = IOUtils.toString(new FileInputStream(propertiesFile));
        inhoud = inhoud.replace(":", "\\:");
        Properties properties = new Properties();
        properties.load(IOUtils.toInputStream(inhoud));
        Map<String, String> map = new HashMap<String, String>();
        for (Entry<Object, Object> entry : properties.entrySet()) {
            // Compileer XPath als check, wordt niet zo opgeslagen.
            if (keyIsXpath) {
                XPATH.compile(entry.getKey().toString());
            }
            if (valueIsXpath) {
                XPATH.compile(entry.getValue().toString());
            }
            map.put(entry.getKey().toString(), entry.getValue().toString());
        }
        return map;
    }

}
