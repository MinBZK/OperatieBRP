/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.jbpm.installer;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.apache.commons.io.IOUtils;
import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.file.def.FileDefinition;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.util.ClassLoaderUtil;

/**
 * Jbpm process installer.
 */
public final class JbpmProcessInstallerImpl implements JbpmProcessInstaller {

    private static final String PROCESSDEFINITION_XML = "processdefinition.xml";
    private static final String PROCESSDEFINITION_MD5 = "processdefinition.md5";
    private static final String PROCESSIMAGE_JPG = "processimage.jpg";
    private static final String FORMS_XML = "forms.xml";
    private static final String GPD_XML = "gpd.xml";

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final String[] PROCES_DEFINITION_LOCATIONS = { "/foutafhandeling/", "/uc201/", "/uc202/",
            "/uc301/", "/uc302/", "/uc306/", "/uc307/", "/uc308/", "/uc311/", };

    @Override
    public void deployJbpmProcesses() {
        for (final String processDefinitionLocation : PROCES_DEFINITION_LOCATIONS) {
            deployProcessDefinition(processDefinitionLocation);
        }
    }

    // CHECKSTYLE:OFF - Lengte
    private void deployProcessDefinition(final String processDefinitionLocation) {
        // CHECKSTYLE:ON
        final JbpmConfiguration jbpmConfiguration = JbpmConfiguration.getInstance();
        final JbpmContext jbpmContext = jbpmConfiguration.createJbpmContext();

        try {
            // each entry of dirs is of format [processdefintion.xml : processimage.jpg : gpd.xml ]
            LOG.info("Attempting to deploy process from " + processDefinitionLocation);
            final ProcessData processData = new ProcessData(processDefinitionLocation);

            // Parse process definition
            final ProcessDefinition processDefinition =
                    ProcessDefinition.parseXmlString(processData.getProcessDefinitionXml());

            // Check if process definition should be deployed
            final ProcessDefinition latestDeployedProcessDefinition =
                    jbpmContext.getGraphSession().findLatestProcessDefinition(processDefinition.getName());
            if (latestDeployedProcessDefinition != null
                    && latestDeployedProcessDefinition.getFileDefinition() != null) {
                final byte[] deployedHash =
                        latestDeployedProcessDefinition.getFileDefinition().getBytes(PROCESSDEFINITION_MD5);

                if (deployedHash != null && Arrays.equals(deployedHash, processData.getHash())) {
                    // Deployed version is equal
                    LOG.info("Deployed version (for " + processDefinitionLocation + ") is up to date.");
                    return;
                }
            }

            // Deploy
            jbpmContext.deployProcessDefinition(processDefinition);
            FileDefinition fd = processDefinition.getFileDefinition();
            if (fd == null) {
                fd = new FileDefinition();
                processDefinition.addDefinition(fd);
            }

            // Add definitionHash
            fd.addFile(PROCESSDEFINITION_MD5, processData.getHash());
            jbpmContext.getGraphSession().saveProcessDefinition(processDefinition);
            LOG.info("Added hash to procesdefinition");

            // Add processimage.jpg
            fd.addFile(PROCESSIMAGE_JPG, processData.getProcessImage());
            jbpmContext.getGraphSession().saveProcessDefinition(processDefinition);
            LOG.info("Added processimage.png to procesdefinition");

            // Add gpd.xml
            fd.addFile(GPD_XML, processData.getGpdXml());
            jbpmContext.getGraphSession().saveProcessDefinition(processDefinition);
            LOG.info("Added gpd.xml to procesdefinition");

            // Add forms.xml (optional)
            if (processData.getFormsXml() != null) {
                fd.addFile(FORMS_XML, processData.getFormsXml());
                jbpmContext.getGraphSession().saveProcessDefinition(processDefinition);
                LOG.info("Added forms.xml to procesdefinition");

                for (final Map.Entry<String, byte[]> form : processData.getForms().entrySet()) {
                    fd.addFile(form.getKey(), form.getValue());
                    LOG.info("Added form (" + form.getKey() + ") to procesdefinition");
                }
            }

            jbpmContext.getGraphSession().saveProcessDefinition(processDefinition);
            LOG.info("Successfully deployed process from " + processDefinitionLocation);
        } catch (final IOException e) {
            LOG.error("Failed to deploy process from " + processDefinitionLocation, e);
        } finally {
            jbpmContext.close();
        }
    }

    private static String readAsString(final String resource) throws IOException {
        final InputStream is = ClassLoaderUtil.getClassLoader().getResourceAsStream(resource);
        if (is == null) {
            return null;
        } else {
            return IOUtils.toString(is);
        }
    }

    private static byte[] readAsBytes(final String resource) throws IOException {
        final InputStream is = ClassLoaderUtil.getClassLoader().getResourceAsStream(resource);
        if (is == null) {
            return null;
        } else {
            return IOUtils.toByteArray(is);
        }
    }

    /**
     * Process data.
     */
    private static class ProcessData {
        private static final Pattern FORM_PATTERN = Pattern.compile("<form .*?form=\"(.*?)\".*?/>");

        private final String processDefinitionXml;
        private final byte[] processImage;
        private final byte[] gpdXml;
        private final String formsXml;
        private final byte[] hash;

        private final SortedMap<String, byte[]> forms = new TreeMap<String, byte[]>();

        /**
         * Constructor.
         * 
         * @param location
         *            locatie
         * @throws IOException
         *             bij lees fouten
         */
        public ProcessData(final String location) throws IOException {
            processDefinitionXml = readAsString(location + PROCESSDEFINITION_XML);
            processImage = readAsBytes(location + PROCESSIMAGE_JPG);
            gpdXml = readAsBytes(location + GPD_XML);
            formsXml = readAsString(location + FORMS_XML);

            if (formsXml != null) {
                final Matcher formMatcher = FORM_PATTERN.matcher(formsXml);

                while (formMatcher.find()) {
                    final String formName = formMatcher.group(1);
                    forms.put(formName, readAsBytes(location + formName));
                }
            }

            MessageDigest digester;
            try {
                digester = MessageDigest.getInstance("MD5");
            } catch (final NoSuchAlgorithmException e) {
                // Will not happen
                throw new RuntimeException(e);
            }
            digester.update(processDefinitionXml.getBytes());
            digester.update(processImage);
            digester.update(gpdXml);
            if (formsXml != null) {
                digester.update(formsXml.getBytes());
            }
            for (final byte[] form : forms.values()) {
                digester.update(form);
            }

            hash = digester.digest();
        }

        public String getProcessDefinitionXml() {
            return processDefinitionXml;
        }

        public byte[] getProcessImage() {
            return processImage;
        }

        public byte[] getGpdXml() {
            return gpdXml;
        }

        /**
         * @return forms.xml (null if non-existent)
         */
        public byte[] getFormsXml() {
            return formsXml == null ? null : formsXml.getBytes();
        }

        public byte[] getHash() {
            return hash;
        }

        public SortedMap<String, byte[]> getForms() {
            return forms;
        }

    }

}
