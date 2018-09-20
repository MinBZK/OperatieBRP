/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb;

import nl.moderniseringgba.isc.jbpm.berichten.BerichtenDao;
import nl.moderniseringgba.isc.jbpm.berichten.JbpmBerichtenDao;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.jboss.soa.esb.actions.ActionLifecycleException;
import org.jboss.soa.esb.actions.ActionPipelineProcessor;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;
import org.jboss.soa.esb.message.Properties;
import org.jboss.soa.esb.services.jbpm.Constants;
import org.jboss.soa.esb.services.jbpm.cmd.MessageHelper;

/**
 * Associeer een de gestarte proces instance met het binnenkomende bericht.
 */
public final class AssociateProcessInstanceAction implements ActionPipelineProcessor {

    private static final Logger LOG = LoggerFactory.getLogger();

    private BerichtenDao berichtenDao = new JbpmBerichtenDao();

    /**
     * Constructor (verplicht voor Jboss ESB).
     * 
     * @param configTree
     *            configuratie
     */
    public AssociateProcessInstanceAction(final ConfigTree configTree) {
        // Geen actie
    }

    @Override
    public void initialise() throws ActionLifecycleException {
        // Geen actie
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
    public Message process(final Message message) {
        final Properties properties = message.getProperties();
        final Long berichtId = (Long) properties.getProperty(EsbConstants.PROPERTY_BERICHT);

        final Long processInstanceId = MessageHelper.getLongValue(message, Constants.PROCESS_INSTANCE_ID);

        LOG.info("process(berichtId={}, processInstanceId={})", berichtId, processInstanceId);

        if (berichtId != null && processInstanceId != null) {
            berichtenDao.updateProcessInstance(berichtId, processInstanceId);
        }

        return message;
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

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
