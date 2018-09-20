/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.generator.java;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.Writer;
import java.util.Map;

import nl.bzk.brp.generator.GenerationPackageNames;
import nl.bzk.brp.generator.java.domein.GenerationReport;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stringtemplate.v4.AutoIndentWriter;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;
import org.stringtemplate.v4.STWriter;


public abstract class AbstractJavaGenerator<T extends Serializable> {

    protected boolean                verbose        = false;

    protected static final String    ID             = "ID";

    private static final Logger      logger         = LoggerFactory.getLogger(AbstractJavaGenerator.class);

    protected static final int       MAX_LINE_WIDTH = 120;
    protected ST                     contentTemplate;

    protected GenerationPackageNames basePackageName;

    private Map<String, Object>      properties;

    protected STGroup                group;

    protected String                 generatedSourcesPath;

    private boolean                  overwritable   = true;

    public AbstractJavaGenerator() {

    }

    /**
     * Zet de template name die van het template groep bestand (dus zonder extensie).
     * 
     * @param templateName the new template group name
     */
    public void setTemplateGroupName(final String templateName) {
        group = new STGroupFile("templates/" + templateName + ".stg");
    }

    public void setBasePackage(final GenerationPackageNames basePackage) {
        this.basePackageName = basePackage;
    }

    public void setGeneratedSourcesPath(final String outputPath) {
        logger.debug("Setting generation output path to " + outputPath);
        generatedSourcesPath = outputPath;
    }

    /**
     * @return the properties
     */
    public Map<String, Object> getProperties() {
        return properties;
    }

    /**
     * @param properties the properties to set
     */
    public void setProperties(final Map<String, Object> properties) {
        this.properties = properties;
    }

    public void clearProperties() {
        if (properties != null)
            this.properties.clear();
        else
            this.properties = null;
    }

    /**
     * Genereer alle objecten voor objecttype T.
     * 
     * @return het rapport met daarin de succesvol gegenereerde objecten en de foutmeldingen voor niet gegenereerde
     *         objecten
     */
    public abstract GenerationReport genereer();

    /**
     * Genereer een element voor objecttype T.
     * 
     * @param writer de writer die gebruikt wordt om de sourcecode mee te schrijven (kan dus file of console zijn)
     * @param report het rapport
     * @param element het object dat gegenereerd moet worden
     */
    public abstract void genereerElement(final Writer writer, final GenerationReport report, final T element);

    protected int write(final String sourceCode, final Writer writer) {
        int result;

        if (writer == null) {
            result = -1;
        } else {
            try {

                STWriter stWriter = new AutoIndentWriter(writer);
                stWriter.setLineWidth(MAX_LINE_WIDTH);
                result = contentTemplate.write(stWriter);

                if (verbose) {
                    logger.debug(sourceCode);
                }

            } catch (IOException e) {
                logger.error("Fout bij het wegschrijven van de gegenereerde code", e);
                result = -1;
            } finally {
                if (writer != null) {
                    try {
                        writer.close();
                    } catch (IOException e) {
                        logger.error("Fout bij het wegschrijven/afsluiten van de file van de gegenereerde code");
                    }
                }
            }
        }

        return result;
    }

    protected Writer creeerWriter(final String objectName) {
        Writer writer = null;
        String fileName = String.format("%s.java", objectName);
        fileName = fileName.replaceAll("ID", "Id");

        try {
            String targetGenerationPath =
                FilenameUtils.concat(generatedSourcesPath, basePackageName.getPackageAsPath());
            logger.info(FilenameUtils.concat(generatedSourcesPath, basePackageName.getPackageAsPath()));
            FileUtils.forceMkdir(new File(targetGenerationPath));

            File outputFile = new File(FilenameUtils.concat(targetGenerationPath, fileName));
            if ((outputFile.exists() && overwritable) || !outputFile.exists()) {
                writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile), "UTF-8"));
                logger.debug(">> " + fileName + " wegschrijven naar " + targetGenerationPath);
            }
        } catch (IOException e) {
            logger.error("Fout bij het creeeren van " + fileName, e);
            writer = null;

        }
        return writer;
    }

    /**
     * @return is de code overschrijfbaar, default true, voor usercode uit het Generation GAP pattern moet deze op false
     *         staan!
     */
    public boolean isOverwritable() {
        return overwritable;
    }

    /**
     * @param overwritable zet de overwritable optie uit voor het Generation GAP pattern.
     */
    public void setOverwritable(boolean overwritable) {
        this.overwritable = overwritable;
    }

}
