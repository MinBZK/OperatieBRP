/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb;

import java.util.List;

import nl.moderniseringgba.isc.jbpm.berichten.Bericht;
import nl.moderniseringgba.isc.jbpm.berichten.BerichtenDao;
import nl.moderniseringgba.isc.jbpm.berichten.Direction;
import nl.moderniseringgba.isc.jbpm.berichten.JbpmBerichtenDao;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.jboss.soa.esb.actions.ActionLifecycleException;
import org.jboss.soa.esb.actions.ActionPipelineProcessor;
import org.jboss.soa.esb.actions.ActionProcessingException;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Message;
import org.jboss.soa.esb.message.Properties;

/**
 * Esb service die verantwoordelijk is voor het opslaan van het bericht in de mig_berichten tabel.
 */
public final class BerichtAuditAction implements ActionPipelineProcessor {

    private static final Logger LOG = LoggerFactory.getLogger();

    private BerichtenDao berichtenDao = new JbpmBerichtenDao();

    private final ConfigTree configTree;

    private String kanaal;
    private Direction richting;

    /**
     * Constructor.
     * 
     * @param configTree
     *            configuratie
     */
    public BerichtAuditAction(final ConfigTree configTree) {
        this.configTree = configTree;
    }

    @Override
    public void initialise() throws ActionLifecycleException {
        kanaal = configTree.getAttribute("kanaal");
        try {
            richting = Direction.valueOf(configTree.getAttribute("richting"));
        } catch (final NullPointerException e) {
            richting = null;
        } catch (final IllegalArgumentException e) {
            richting = null;
        }
        if (kanaal == null || richting == null) {
            throw new ActionLifecycleException("Kanaal en/of richting niet (juist) geconfigureerd.");
        }
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
        LOG.debug("process: {}", message.getBody());

        final Properties properties = message.getProperties();
        final String messageId = (String) properties.getProperty(EsbConstants.PROPERTY_MESSAGE_ID);
        if (messageId == null || "".equals(messageId)) {
            LOG.warn("Bericht ontvangen zonder message-id!\n{}", message);
        }

        // Bij inkomende berichten voor het opslaan bepalen of dit message-id al bekend is binnen ons systeem.
        if (richting == Direction.INKOMEND) {
            final List<Bericht> bestaandeBerichten =
                    berichtenDao.findBerichtenByMessageId(messageId, kanaal, richting);
            properties.setProperty(EsbConstants.PROPERTY_INDICATIE_HERHALING,
                    Boolean.valueOf(!bestaandeBerichten.isEmpty()));
        }

        // Bericht opslaan
        final String correlatieId = (String) properties.getProperty(EsbConstants.PROPERTY_CORRELATIE_ID);
        final String bericht = (String) message.getBody().get();
        final Long berichtId = berichtenDao.saveBericht(kanaal, richting, messageId, correlatieId, bericht);
        properties.setProperty(EsbConstants.PROPERTY_BERICHT, berichtId);

        // Bij uitgaande berichten hebben we bij de format de 'extra' properties bewaard (bij inkomende berichten
        // updaten we deze na het parsen)
        if (richting == Direction.UITGAAND) {
            berichtenDao.updateNaam(berichtId, (String) properties.getProperty(EsbConstants.PROPERTY_NAAM));
            berichtenDao.updateGemeenten(berichtId,
                    (String) properties.getProperty(EsbConstants.PROPERTY_BRON_GEMEENTE),
                    (String) properties.getProperty(EsbConstants.PROPERTY_DOEL_GEMEENTE));
            berichtenDao
                    .updateHerhaling(berichtId, (Integer) properties.getProperty(EsbConstants.PROPERTY_HERHALING));
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
