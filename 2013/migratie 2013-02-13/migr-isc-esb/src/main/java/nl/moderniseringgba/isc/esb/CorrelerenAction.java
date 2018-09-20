/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb;

import nl.moderniseringgba.isc.esb.invoker.EsbServiceInvoker;
import nl.moderniseringgba.isc.esb.invoker.ServiceInvoker;
import nl.moderniseringgba.isc.esb.message.Bericht;
import nl.moderniseringgba.isc.esb.message.brp.BrpBericht;
import nl.moderniseringgba.isc.esb.message.brp.BrpVerzoekBericht;
import nl.moderniseringgba.isc.esb.message.impl.OnbekendBericht;
import nl.moderniseringgba.isc.esb.message.impl.OngeldigBericht;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3Bericht;
import nl.moderniseringgba.isc.jbpm.berichten.BerichtenDao;
import nl.moderniseringgba.isc.jbpm.berichten.JbpmBerichtenDao;
import nl.moderniseringgba.isc.jbpm.correlatie.JbpmProcessCorrelatieStore;
import nl.moderniseringgba.isc.jbpm.correlatie.ProcessCorrelatieStore;
import nl.moderniseringgba.isc.jbpm.correlatie.ProcessData;
import nl.moderniseringgba.isc.jbpm.jsf.FoutafhandelingPaden;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.jboss.soa.esb.actions.ActionLifecycleException;
import org.jboss.soa.esb.actions.ActionPipelineProcessor;
import org.jboss.soa.esb.actions.ActionProcessingException;
import org.jboss.soa.esb.addressing.PortReference;
import org.jboss.soa.esb.helpers.ConfigTree;
import org.jboss.soa.esb.message.Body;
import org.jboss.soa.esb.message.Message;
import org.jboss.soa.esb.message.Properties;
import org.jboss.soa.esb.services.jbpm.Constants;

/**
 * Action die de proces correlatie bepaald van een binnengekomen bericht en de verwerking in JBPM start.
 */
// CHECKSTYLE:OFF - Fan-out complexity
public final class CorrelerenAction implements ActionPipelineProcessor {
    // CHECKSTYLE:ON

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final String BPM_SERVICE_CATEGORIE = "BPM";
    private static final String BPM_SIGNAL_SERVICE = "Signal";
    private static final String BPM_START_SERVICE = "Start";
    private static final String BPM_FOUT_SERVICE = "Fout";

    private static final String BODY_ATTRIBUTE_FUNCTIONELE_STAP = "functioneleStap";
    private static final String BODY_ATTRIBUTE_BRON_GEMEENTE = "bronGemeente";
    private static final String BODY_ATTRIBUTE_DOEL_GEMEENTE = "doelGemeente";
    private static final String BODY_ATTRIBUTE_FOUT = "fout";
    private static final String BODY_ATTRIBUTE_FOUTMELDING = "foutmelding";
    private static final String BODY_ATTRIBUTE_INDICATIE_BEHEERDER = "indicatieBeheerder";
    private static final String BODY_ATTRIBUTE_RESTART = "restart";
    private static final String BODY_ATTRIBUTE_FOUTPADEN = "foutafhandelingPaden";
    private static final String BODY_ATTRIBUTE_LO3_BERICHT = "lo3Bericht";
    private static final String BODY_ATTRIBUTE_BRP_BERICHT = "brpBericht";
    private static final String END_PAD = "end";

    private ProcessCorrelatieStore processCorrelatieStore = new JbpmProcessCorrelatieStore();
    private BerichtenDao berichtenDao = new JbpmBerichtenDao();

    private ServiceInvoker bpmSignalInvoker = new EsbServiceInvoker(BPM_SERVICE_CATEGORIE, BPM_SIGNAL_SERVICE);
    private ServiceInvoker bpmStartInvoker = new EsbServiceInvoker(BPM_SERVICE_CATEGORIE, BPM_START_SERVICE);
    private ServiceInvoker bpmFoutInvoker = new EsbServiceInvoker(BPM_SERVICE_CATEGORIE, BPM_FOUT_SERVICE);

    /**
     * Constructor (verplicht voor Jboss ESB).
     * 
     * @param configTree
     *            configuratie
     */
    public CorrelerenAction(final ConfigTree configTree) {
        // Geen actie
    }

    public void setBpmSignalInvoker(final ServiceInvoker bpmSignalInvoker) {
        this.bpmSignalInvoker = bpmSignalInvoker;
    }

    public void setBpmStartInvoker(final ServiceInvoker bpmStartInvoker) {
        this.bpmStartInvoker = bpmStartInvoker;
    }

    public void setBpmFoutInvoker(final ServiceInvoker bpmFoutInvoker) {
        this.bpmFoutInvoker = bpmFoutInvoker;
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

        final String correlatieId = (String) properties.getProperty(EsbConstants.PROPERTY_CORRELATIE_ID);

        // Correleer bericht
        final ProcessData processData;
        if (correlatieId != null) {
            processData = processCorrelatieStore.zoekProcessCorrelatie(correlatieId);
        } else {
            processData = null;
        }

        if (processData != null) {
            final Long berichtId = (Long) properties.getProperty(EsbConstants.PROPERTY_BERICHT);
            berichtenDao.updateProcessInstance(berichtId, processData.getProcessInstanceId());
        }

        // Vooralsnog negeren we alle herhalingen.
        final Boolean isHerhaling = (Boolean) properties.getProperty(EsbConstants.PROPERTY_INDICATIE_HERHALING);
        if (Boolean.TRUE.equals(isHerhaling)) {
            return message;
        }

        final Bericht bericht = (Bericht) message.getBody().get();
        if (correlatieId != null) {
            if (processData == null) {
                LOG.info("Geen correlatie gevonden");
                // Geen correlatie gevonden
                startFoutmeldingProces(message, "Correlatie id ontvangen maar niet gevonden in correlatie tabel.");
            } else {
                if (!controleerAdressering(processData, properties)) {
                    startFoutmeldingProces(message, "Addressering is incorrect.");
                } else {
                    // JBPM wil de correlatie in portreference extensions.
                    final PortReference portReference = new PortReference();

                    portReference.addExtension(Constants.PROCESS_INSTANCE_ID, processData.getProcessInstanceId()
                            .toString());
                    portReference.addExtension(Constants.TOKEN_ID, processData.getTokenId().toString());
                    portReference.addExtension(Constants.NODE_ID, processData.getNodeId().toString());
                    portReference
                            .addExtension(processData.getCounterName(), processData.getCounterValue().toString());

                    bpmSignalInvoker.deliverAsync(message, portReference.getAllExtensions());
                }

            }
        } else {
            final String cyclus = bericht.getStartCyclus();

            if (cyclus == null) {
                startFoutmeldingProces(message, "Ontvangen bericht mag geen proces starten.");
            } else {
                // JBPM wil de process definition naam als body attribute.
                message.getBody().add(Constants.PROCESS_DEFINITION_NAME, cyclus);
                bpmStartInvoker.deliverAsync(message);
            }
        }

        return message;
    }

    private boolean controleerAdressering(final ProcessData processData, final Properties properties) {
        boolean result = true;

        // Controleer doel gemeente van dit bericht gelijk aan bron gemeente van bericht waaraan
        // gecorreleerd wordt.
        if (processData.getBronGemeente() != null) {
            final String doelGemeente = (String) properties.getProperty(EsbConstants.PROPERTY_DOEL_GEMEENTE);
            if (!processData.getBronGemeente().equals(doelGemeente)) {
                result = false;
            }
        }

        // En ook voor bron gemeente
        if (processData.getDoelGemeente() != null) {
            final String bronGemeente = (String) properties.getProperty(EsbConstants.PROPERTY_BRON_GEMEENTE);
            if (!processData.getDoelGemeente().equals(bronGemeente)) {
                result = false;
            }
        }

        return result;
    }

    private void startFoutmeldingProces(final Message message, final String foutmelding) {
        final Body body = message.getBody();
        body.add(Constants.PROCESS_DEFINITION_NAME, "foutafhandeling");
        body.add(BODY_ATTRIBUTE_FUNCTIONELE_STAP, "esb.verwerken");
        body.add(BODY_ATTRIBUTE_FOUT, "esb.verwerken.fout");
        body.add(BODY_ATTRIBUTE_FOUTMELDING, foutmelding);
        body.add(BODY_ATTRIBUTE_INDICATIE_BEHEERDER, false);

        final Bericht bericht = (Bericht) body.get();
        if (bericht instanceof Lo3Bericht) {
            if (bericht instanceof OnbekendBericht || bericht instanceof OngeldigBericht) {
                body.add(BODY_ATTRIBUTE_LO3_BERICHT, bericht);
            } else {
                body.add(BODY_ATTRIBUTE_LO3_BERICHT,
                        new nl.moderniseringgba.isc.esb.message.lo3.impl.OnbekendBericht((Lo3Bericht) bericht));
            }
        }
        if (bericht instanceof BrpBericht) {
            if (bericht instanceof OngeldigBericht) {
                body.add(BODY_ATTRIBUTE_FOUTMELDING, ((OngeldigBericht) bericht).getMelding());
            }
            body.add(BODY_ATTRIBUTE_BRP_BERICHT, bericht);
        }

        message.getBody().add(BODY_ATTRIBUTE_RESTART, END_PAD);
        final FoutafhandelingPaden paden = new FoutafhandelingPaden();
        paden.put(END_PAD, "Afsluiten", bericht instanceof Lo3Bericht, false, bericht instanceof BrpVerzoekBericht);
        message.getBody().add(BODY_ATTRIBUTE_FOUTPADEN, paden);

        // Gemeente
        final Properties properties = message.getProperties();
        message.getBody().add(BODY_ATTRIBUTE_BRON_GEMEENTE,
                properties.getProperty(EsbConstants.PROPERTY_BRON_GEMEENTE));
        message.getBody().add(BODY_ATTRIBUTE_DOEL_GEMEENTE,
                properties.getProperty(EsbConstants.PROPERTY_DOEL_GEMEENTE));

        bpmFoutInvoker.deliverAsync(message);
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
