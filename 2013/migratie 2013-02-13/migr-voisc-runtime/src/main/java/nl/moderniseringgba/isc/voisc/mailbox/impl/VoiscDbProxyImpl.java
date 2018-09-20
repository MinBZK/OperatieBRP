/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.voisc.mailbox.impl;

import java.net.ConnectException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import nl.moderniseringgba.isc.voisc.constants.MessagesCodes;
import nl.moderniseringgba.isc.voisc.constants.VoiscConstants;
import nl.moderniseringgba.isc.voisc.entities.Bericht;
import nl.moderniseringgba.isc.voisc.entities.LogboekRegel;
import nl.moderniseringgba.isc.voisc.entities.Mailbox;
import nl.moderniseringgba.isc.voisc.entities.StatusEnum;
import nl.moderniseringgba.isc.voisc.exceptions.VoaException;
import nl.moderniseringgba.isc.voisc.mailbox.VoiscDbProxy;
import nl.moderniseringgba.isc.voisc.repository.MailboxRepository;
import nl.moderniseringgba.isc.voisc.repository.VoaRepository;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 */
@Component
public final class VoiscDbProxyImpl implements VoiscDbProxy {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private static final String CLOSING_PARENTHESIS = ")";
    private static final String ID_PREFIX = "ID: ";

    @Inject
    private MailboxRepository mailboxRepository;
    @Inject
    private VoaRepository voaRepository;

    private Integer databaseReconnects;
    private Integer emergencySleepTime;

    /**
     * @param bericht
     *            het bericht dat verstuurd of ontvangen wordt
     * @param doReconnectLoop
     *            true als er meerdere pogingen moeten worden gedaan als de DB niet bereikbaar is
     * @param inkomend
     *            true als het inkomend mailbox bericht betreft, false als het een uitgaand mailbox bericht is
     * @throws VoaException
     *             wordt gegooid als er een andere fout dan een SQLException of ConnectException optreedt
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveBericht(final Bericht bericht, final boolean doReconnectLoop, final boolean inkomend)
            throws VoaException {
        int currentNrReconnects = 0;
        Throwable exp = null;
        boolean reconnect = doReconnectLoop;
        do {
            try {
                voaRepository.save(bericht);
                exp = null;
                // CHECKSTYLE:OFF try to catch all db-exceptions
            } catch (final Throwable t) { // NOSONAR
                // CHECKSTYLE:ON
                exp = t;
                final StringBuilder mesg = new StringBuilder("Fout bij opslaan van bericht(");
                if (inkomend) {
                    mesg.append("MSSequenceNumber: ").append(bericht.getDispatchSequenceNumber());
                } else {
                    mesg.append(ID_PREFIX).append(bericht.getId());
                }
                mesg.append(CLOSING_PARENTHESIS);
                LOGGER.warn(mesg.toString(), t);
            }

            if (exp == null) {
                reconnect = false;
                if (currentNrReconnects > 0) {
                    logDbReconnectSuccesful(bericht, inkomend);
                }
            } else {
                if (!reconnect || currentNrReconnects == databaseReconnects) {
                    // Inkomende berichten, log de seq nummer en originator en recipient.
                    if (inkomend) {
                        LOGGER.error("Het volgende bericht kon niet ge-update worden in de database:\n"
                                + " Dispatch sequence number: " + bericht.getDispatchSequenceNumber());
                    }
                    throw new VoaException(MessagesCodes.ERRMSG_VOSPG_DB_CONNECTION_FAILED, null);
                }
                currentNrReconnects++;
                this.handleException(exp, currentNrReconnects);
            }
        } while (reconnect && currentNrReconnects <= databaseReconnects);
    }

    private void logDbReconnectSuccesful(final Bericht bericht, final boolean inkomend) {
        LOGGER.info("Connectie naar de database is succesvol hersteld.");
        final StringBuilder mesg = new StringBuilder("Bericht (");
        if (inkomend) {
            mesg.append("MSSequenceNr: ").append(bericht.getDispatchSequenceNumber());
        } else {
            mesg.append(ID_PREFIX).append(bericht.getId());
        }
        mesg.append(") succesvol opgeslagen");
        LOGGER.info(mesg.toString());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveLogboekRegel(final LogboekRegel regel) {
        regel.setEindDatumTijd(Calendar.getInstance().getTime());
        regel.setAantalOntvangen(regel.getAantalOntvangenNOK() + regel.getAantalOntvangenOK());
        regel.setAantalVerzonden(regel.getAantalVerzondenNOK() + regel.getAantalVerzondenOK());
        mailboxRepository.saveLogboekEntry(regel);
    }

    /**
     * Check the Throwable if it is an Exception which is caused by a lost connection to the database.<br>
     * When it is an SQLException or a ConnectException, sleep some time ... <br>
     * If it's an other exception throw a new VOSPGException with the causing Throwable. *
     * 
     * @param t1
     *            Throwable which will be checked on type
     * @param nrReconnects
     *            number of reconnect tries
     * @throws VoaException
     *             wordt gegeooid als er geen SqlException of ConnectException is gedetecteerd
     */
    private void handleException(final Throwable t1, final int nrReconnects) throws VoaException {
        final long emergencySleeptime = emergencySleepTime * 1000;

        // because the Exceptions are nested, get the innermost Exception, this is the initial
        // Exception
        Throwable t = t1;
        while (t.getCause() != null) {
            t = t.getCause();
        }
        // When the initial Exception is of type java.sql.SQLException or java.net.ConnectException
        // wait some time
        // and retry the failed db action
        if (t instanceof SQLException || t instanceof ConnectException) {
            LOGGER.warn("DB-connection is verbroken: ExceptionType= " + t.getClass());
            LOGGER.warn("getMessage(): " + t.getMessage());
            LOGGER.warn("DB-reconnect retry: " + nrReconnects + " ...");
            try {
                Thread.sleep(emergencySleeptime);
            } catch (final InterruptedException ire) {
                LOGGER.debug("InterruptedException ignored..");
            }
        } else {
            // unexpected exception
            LOGGER.error("Onverwachte fout opgetreden: ", t);
            throw new VoaException(MessagesCodes.ERRMSG_VOSPG_UNEXPECTED_EXCEPTION, null, t);
        }
    }

    /**
     * @param databaseReconnects
     *            the databaseReconnects to set
     */
    public void setDatabaseReconnects(final Integer databaseReconnects) {
        this.databaseReconnects = databaseReconnects;
    }

    public void setEmergencySleepTime(final Integer sleepTime) {
        emergencySleepTime = sleepTime;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveMailbox(final Mailbox mailbox) {
        mailboxRepository.save(mailbox);
    }

    @Override
    public Bericht getBerichtByDispatchSequenceNumber(final int dispatchSequenceNumber) {
        return voaRepository.getBerichtByDispatchSeqNr(dispatchSequenceNumber);
    }

    @Override
    public Bericht getBerichtByEref(final String eref) {
        return voaRepository.getBerichtByEref(eref);
    }

    @Override
    public Mailbox getMailboxByGemeentecode(final String gemeentecode) {
        return mailboxRepository.getMailboxByGemeentecode(gemeentecode);
    }

    @Override
    public Mailbox getMailboxByNummer(final String nummer) {
        return mailboxRepository.getMailboxByNummer(nummer);
    }

    @Override
    public List<Bericht> getBerichtToSendMBS(final String mailboxNr) {
        return voaRepository.getBerichtToSendMBS(mailboxNr);
    }

    @Override
    public List<Bericht> getBerichtToSendQueue(final int limit) {
        return voaRepository.getBerichtToSendQueue(limit);
    }

    /**
     * Error bericht opstellen en versturen naar de ESB.
     * 
     * @param bericht
     *            Bericht waarop een errorbericht als reply gestuurd wordt.
     * @param error
     *            De exception waarvan een errorbericht gemaakt moet worden.
     * @throws VoaException
     *             Wordt gegooid als er een onverwachte fout optreedt.
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void sendEsbErrorBericht(final Bericht bericht, final String error) throws VoaException {
        final Bericht errorBericht = new Bericht();
        errorBericht.setAanduidingInUit(Bericht.AANDUIDING_IN_UIT_IN);
        errorBericht.setStatus(StatusEnum.MAILBOX_RECEIVED);
        errorBericht.setBerichtInhoud(error);
        if (bericht != null) {
            errorBericht.setEsbCorrelationId(bericht.getEsbMessageId());
        }
        saveBericht(errorBericht, !VoiscConstants.RECONNECT, !VoiscConstants.INKOMEND_MB_BERICHT);
    }

    @Override
    public Bericht getBerichtByEsbMessageId(final String correlationId) {
        return voaRepository.getBerichtByEsbMessageId(correlationId);
    }

    @Override
    public Bericht getBerichtByBrefAndOrginator(final String bref, final String originator) {
        return voaRepository.getBerichtByBrefAndOrginator(bref, originator);
    }
}
