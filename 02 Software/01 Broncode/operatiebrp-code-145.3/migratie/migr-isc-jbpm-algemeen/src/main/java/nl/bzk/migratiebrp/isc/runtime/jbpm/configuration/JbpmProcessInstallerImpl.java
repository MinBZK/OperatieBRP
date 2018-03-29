/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.runtime.jbpm.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import org.apache.commons.io.IOUtils;
import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.file.def.FileDefinition;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.jpdl.xml.JpdlXmlReader;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.InputSource;

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
    private final String[] procesDefinitionLocations;

    /**
     * Constructor.
     * @param procesDefinitionLocations process definition locaties
     */
    protected JbpmProcessInstallerImpl(final String... procesDefinitionLocations) {
        this.procesDefinitionLocations = procesDefinitionLocations;
    }

    @Override
    @Transactional(value = "iscTransactionManager", propagation = Propagation.REQUIRED)
    public void deployJbpmProcesses(final JbpmConfiguration jbpmConfiguration) {
        for (final String processDefinitionLocation : procesDefinitionLocations) {
            deployProcessDefinition(jbpmConfiguration, processDefinitionLocation);
        }
    }

    private void deployProcessDefinition(final JbpmConfiguration jbpmConfiguration, final String processDefinitionLocation) {
        final JbpmContext jbpmContext = jbpmConfiguration.createJbpmContext();

        try {
            // each entry of dirs is of format [processdefintion.xml : processimage.jpg : gpd.xml ]
            LOG.info("Attempting to deploy process from " + processDefinitionLocation);
            final ProcessData processData = new ProcessData(processDefinitionLocation);

            // Parse process definition
            final JpdlXmlReader jpdlReader =
                    new JpdlXmlReader(new InputSource(new StringReader(processData.getProcessDefinitionXml())), problem -> LOG.error(problem.toString()));
            final ProcessDefinition processDefinition = jpdlReader.readProcessDefinition();

            // Check if process definition should be deployed
            if (isDeLaatsteProcessDefinitionGedeployed(processDefinitionLocation, jbpmContext, processData, processDefinition)) {
                return;
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

            // Add processimage.jpg
            fd.addFile(PROCESSIMAGE_JPG, processData.getProcessImage());
            jbpmContext.getGraphSession().saveProcessDefinition(processDefinition);

            // Add gpd.xml
            fd.addFile(GPD_XML, processData.getGpdXml());
            jbpmContext.getGraphSession().saveProcessDefinition(processDefinition);
            LOG.debug("Added hash, processimage.png and gpd.xml to procesdefinition");

            // Add forms.xml (optional)
            if (processData.getFormsXml() != null) {
                fd.addFile(FORMS_XML, processData.getFormsXml());
                jbpmContext.getGraphSession().saveProcessDefinition(processDefinition);
                LOG.debug("Added forms.xml to procesdefinition");

                for (final Map.Entry<String, byte[]> form : processData.getForms().entrySet()) {
                    fd.addFile(form.getKey(), form.getValue());
                    LOG.debug("Added form (" + form.getKey() + ") to procesdefinition");
                }
            }

            jbpmContext.getGraphSession().saveProcessDefinition(processDefinition);
            LOG.info("Successfully deployed process from " + processDefinitionLocation);
        } catch (final IOException e) {
            LOG.error("Failed to deploy process from " + processDefinitionLocation, e);
        } catch (final Exception e /* Catch exception voor logging. Exception wrdt gerethrowed. */) {
            LOG.error("Unexpected failure during deploy process", e);
            throw e;
        } finally {
            jbpmContext.close();
        }
    }

    private boolean isDeLaatsteProcessDefinitionGedeployed(final String processDefinitionLocation, final JbpmContext jbpmContext, final ProcessData processData,
                                                           final ProcessDefinition processDefinition) {
        final ProcessDefinition latestDeployedProcessDefinition = jbpmContext.getGraphSession().findLatestProcessDefinition(processDefinition.getName());
        if (latestDeployedProcessDefinition != null && latestDeployedProcessDefinition.getFileDefinition() != null) {
            final byte[] deployedHash = latestDeployedProcessDefinition.getFileDefinition().getBytes(PROCESSDEFINITION_MD5);

            if (deployedHash != null && Arrays.equals(deployedHash, processData.getHash())) {
                // Deployed version is equal
                LOG.info("Deployed version (for " + processDefinitionLocation + ") is up to date.");
                return true;
            }
        }

        return false;
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

        private final SortedMap<String, byte[]> forms = new TreeMap<>();

        /**
         * Constructor.
         * @param location locatie
         * @throws IOException bij lees fouten
         */
        public ProcessData(final String location) throws IOException {
            processDefinitionXml = readAsString(location + PROCESSDEFINITION_XML);
            if (processDefinitionXml == null) {
                throw new IOException("Process definitie kan niet gelezen worden." + " Controleer of de JBPM classloader op context staat.");
            }
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

            final MessageDigest digester;
            try {
                digester = MessageDigest.getInstance("SHA-256");
            } catch (final NoSuchAlgorithmException e) {
                // Will not happen
                throw new IOException(e);
            }
            digester.update(processDefinitionXml.getBytes(StandardCharsets.UTF_8));
            digester.update(processImage);
            digester.update(gpdXml);
            if (formsXml != null) {
                digester.update(readAsBytes(formsXml));
            }
            for (final byte[] form : forms.values()) {
                digester.update(form);
            }

            hash = digester.digest();
        }

        private static String readAsString(final String resource) throws IOException {
            final InputStream is = JbpmProcessInstaller.class.getResourceAsStream(resource);
            if (is == null) {
                return null;
            } else {
                return IOUtils.toString(is, Charset.defaultCharset());
            }
        }

        private static byte[] readAsBytes(final String resource) throws IOException {
            final InputStream is = JbpmProcessInstaller.class.getResourceAsStream(resource);
            if (is == null) {
                return new byte[]{};
            } else {
                return IOUtils.toByteArray(is);
            }
        }

        /**
         * Geef de waarde van process definition xml.
         * @return process definition xml
         */
        public String getProcessDefinitionXml() {
            return processDefinitionXml;
        }

        /**
         * Geef de waarde van process image.
         * @return process image
         */
        public byte[] getProcessImage() {
            return processImage;
        }

        /**
         * Geef de waarde van gpd xml.
         * @return gpd xml
         */
        public byte[] getGpdXml() {
            return gpdXml;
        }

        /**
         * Geef de waarde van forms xml.
         * @return forms.xml (null if non-existent)
         */
        public byte[] getFormsXml() {
            return formsXml == null ? null : formsXml.getBytes(StandardCharsets.UTF_8);
        }

        /**
         * Geef de waarde van hash.
         * @return hash
         */
        public byte[] getHash() {
            return hash;
        }

        /**
         * Geef de waarde van forms.
         * @return forms
         */
        public SortedMap<String, byte[]> getForms() {
            return forms;
        }

    }

}
