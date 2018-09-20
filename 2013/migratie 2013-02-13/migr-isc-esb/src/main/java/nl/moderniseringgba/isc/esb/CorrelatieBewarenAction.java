/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb;

import nl.moderniseringgba.isc.jbpm.actionhandler.EsbNotifier;
import nl.moderniseringgba.isc.jbpm.berichten.BerichtenDao;
import nl.moderniseringgba.isc.jbpm.berichten.JbpmBerichtenDao;
import nl.moderniseringgba.isc.jbpm.correlatie.JbpmProcessCorrelatieStore;
import nl.moderniseringgba.isc.jbpm.correlatie.ProcessCorrelatieStore;
import nl.moderniseringgba.isc.jbpm.correlatie.ProcessData;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.jboss.soa.esb.actions.ActionLifecycleException;
import org.jboss.soa.esb.actions.ActionPipelineProcessor;
import org.jboss.soa.esb.actions.ActionProcessingException;
import org.jboss.soa.esb.addressing.PortReference;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;
import org.jboss.soa.esb.message.Properties;
import org.jboss.soa.esb.services.jbpm.Constants;

/**
 * Action die de process correlatie opslaat voor uitgaande berichten.
 */
public final class CorrelatieBewarenAction implements ActionPipelineProcessor {

    private static final Logger LOG = LoggerFactory.getLogger();

    private ProcessCorrelatieStore processCorrelatieStore = new JbpmProcessCorrelatieStore();
    private BerichtenDao berichtenDao = new JbpmBerichtenDao();

    /**
     * Constructor (verplicht voor Jboss ESB).
     * 
     * @param configTree
     *            configuratie
     */
    public CorrelatieBewarenAction(final ConfigTree configTree) {
        // Geen actie
    }

    public void setProcessCorrelatieStore(final ProcessCorrelatieStore processCorrelatieStore) {
        this.processCorrelatieStore = processCorrelatieStore;
    }

    public void setBerichtenDao(final BerichtenDao berichtenDao) {
        this.berichtenDao = berichtenDao;
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    @Override
    public Message process(final Message message) throws ActionProcessingException {
        LOG.debug("process: {}", message);
        final Properties properties = message.getProperties();

        // Save process correlations
        final String processInstanceId;
        if (message.getHeader().getCall().getReplyTo() != null) {
            final String messageId = (String) properties.getProperty(EsbConstants.PROPERTY_MESSAGE_ID);

            final PortReference portReference = message.getHeader().getCall().getReplyTo().getAddr();

            processInstanceId = portReference.getExtensionValue(Constants.PROCESS_INSTANCE_ID);
            final String tokenId = portReference.getExtensionValue(Constants.TOKEN_ID);
            final String nodeId = portReference.getExtensionValue(Constants.NODE_ID);
            final String counterName = Constants.PROCESS_NODE_VERSION_COUNTER + nodeId + '_' + tokenId;
            final String counterValue = portReference.getExtensionValue(counterName);

            final String bronGemeente = (String) properties.getProperty(EsbConstants.PROPERTY_BRON_GEMEENTE);
            final String doelGemeente = (String) properties.getProperty(EsbConstants.PROPERTY_DOEL_GEMEENTE);

            final ProcessData processData =
                    new ProcessData(Long.valueOf(processInstanceId), Long.valueOf(tokenId), Long.valueOf(nodeId),
                            counterName, Integer.valueOf(counterValue), bronGemeente, doelGemeente);
            LOG.info("Processdata voor bericht {}: {}", messageId, processData);

            processCorrelatieStore.bewaarProcessCorrelatie(messageId, processData);
        } else if (message.getBody().get(EsbNotifier.PROPERTY_PROCESS_INSTANCE_ID) != null) {
            processInstanceId = (String) message.getBody().get(EsbNotifier.PROPERTY_PROCESS_INSTANCE_ID);
        } else {
            LOG.info("Geen procesdata voor correlatie");
            processInstanceId = null;
        }

        if (processInstanceId != null) {
            final Long berichtId = (Long) properties.getProperty(EsbConstants.PROPERTY_BERICHT);
            berichtenDao.updateProcessInstance(berichtId, Long.valueOf(processInstanceId));
        }
        return message;
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    @Override
    public void initialise() throws ActionLifecycleException {
        // Geen actie
    }

    @Override
    public void destroy() throws ActionLifecycleException {
        // Geen actie
    }

    @Override
    public void processException(final Message arg0, final Throwable arg1) {
        // Geen actie
    }

    @Override
    public void processSuccess(final Message arg0) {
        // Geen actie
    }
}
