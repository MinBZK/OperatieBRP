/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.voisc;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import nl.moderniseringgba.isc.esb.message.BerichtId;
import nl.moderniseringgba.isc.voisc.constants.MessagesCodes;
import nl.moderniseringgba.isc.voisc.constants.SpdConstants;
import nl.moderniseringgba.isc.voisc.constants.VoiscConstants;
import nl.moderniseringgba.isc.voisc.entities.Bericht;
import nl.moderniseringgba.isc.voisc.entities.LogboekRegel;
import nl.moderniseringgba.isc.voisc.entities.Mailbox;
import nl.moderniseringgba.isc.voisc.entities.StatusEnum;
import nl.moderniseringgba.isc.voisc.exceptions.EsbFoutException;
import nl.moderniseringgba.isc.voisc.exceptions.MailboxServerPasswordException;
import nl.moderniseringgba.isc.voisc.exceptions.SpdProtocolException;
import nl.moderniseringgba.isc.voisc.exceptions.SslPasswordException;
import nl.moderniseringgba.isc.voisc.exceptions.VoaException;
import nl.moderniseringgba.isc.voisc.jms.QueueService;
import nl.moderniseringgba.isc.voisc.mailbox.MailboxServerProxy;
import nl.moderniseringgba.isc.voisc.mailbox.VoiscDbProxy;
import nl.moderniseringgba.isc.voisc.utils.PasswordGenerator;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.jms.JmsException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the VOSPGservice interface.
 * <P>
 * The methods <tt>sendMessages()</tt> and <tt>receiveMessage()</tt> contains database reconnect functionality. This
 * means that whenever the database connection get lost (and a 'hot-swap' to a failover database is made), the method
 * retries a certain piece of code to execute. So that the data in memory can be persisted to the database. The piece of
 * code which is retried a couple of times contains database interactions, such as select/update/insert and/or delete
 * statements. <BR>
 * When the connection is lost an exception occurs. Because exceptions can be nested it's useful to retrieve the causing
 * exception, to determine the causing problem of the exception. There are right now twe different causing exceptions
 * which means that the connection is lost: <tt>java.sql.SQLException</tt> or <tt>java.net.ConnectException</tt>.
 * <P>
 * 
 */
public class VoaServiceImpl implements VoaService {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private static final int TWO_MONTHS_AGO = -60;
    private static final String CLOSING_PARENTHESIS = ")";

    @Inject
    private VoiscDbProxy voiscDbProxy;
    @Inject
    private MailboxServerProxy mailboxServerProxy;
    @Inject
    private QueueService queueService;

    /**
     * Change password of the mailboxserver.
     * 
     * @param newPW
     *            het nieuw in te stellen password
     * @throws VoaException
     *             Als het wijzigen van het wachtwoord niet lukt.
     */
    private void changeMBPassword(final Mailbox mailbox, final String newPW) throws VoaException {
        try {
            mailboxServerProxy.changePassword(mailbox, newPW);
            mailbox.setMailboxpwd(newPW);
            mailbox.setLaatsteWijzigingPwd(new Date());
            voiscDbProxy.saveMailbox(mailbox);
        } catch (final MailboxServerPasswordException mbe) {
            final Object[] parms = new Object[1];
            parms[0] = mbe.getMessage();
            throw new VoaException(MessagesCodes.ERRMSG_VOSPG_CHG_PW_ERROR, parms, mbe);
        }
    }

    /**
     * login to the mailbox.
     * 
     * @param mailbox
     *            The mailbox where we log on to
     * @param regel
     *            Logboekregel zodat evt. foutmeldingen gelogd kan worden
     * @throws VoaException
     *             ({@see MessagesCodes.ERRMSG_VOSPG_WRONG_MAILBOX_PW}, {@see MessagesCodes.ERRMSG_VOSPG_WRONG_SSL_PW},
     *             {@see MessagesCodes.ERRMSG_VOSPG_LOGON_ERROR})
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public final void login(final LogboekRegel regel, final Mailbox mailbox) throws VoaException {
        try {
            final boolean chgPwd = checkPasswordChange(mailbox);
            mailboxServerProxy.logOn(mailbox);
            if (chgPwd) {
                final String newPwd = PasswordGenerator.generate();
                changeMBPassword(mailbox, newPwd);
            }
        } catch (final MailboxServerPasswordException mbpwe) {
            final Object[] parms = new Object[] { mailbox.getMailboxnr() };
            final VoaException ve = new VoaException(MessagesCodes.ERRMSG_VOA_WRONG_MAILBOX_PW, parms, mbpwe);
            regel.setFoutmelding(ve.getMessage());
        } catch (final SslPasswordException ssle) {
            final VoaException ve = new VoaException(MessagesCodes.ERRMSG_VOA_WRONG_SSL_PW, null, ssle);
            regel.setFoutmelding(ve.getMessage());
        } catch (final SpdProtocolException spde) {
            final VoaException ve = new VoaException(MessagesCodes.ERRMSG_VOA_LOGON_ERROR, null, spde);
            regel.setFoutmelding(ve.getMessage());
        }
    }

    private boolean checkPasswordChange(final Mailbox mailbox) {
        final Calendar checkDate = Calendar.getInstance();
        checkDate.add(Calendar.DATE, TWO_MONTHS_AGO);
        final Date laatsteWijzigingPwdDt = mailbox.getLaatsteWijzigingPwd();
        final Calendar laatsteWijzigingPwdCal = Calendar.getInstance();
        laatsteWijzigingPwdCal.setTime(laatsteWijzigingPwdDt);

        return laatsteWijzigingPwdCal.before(checkDate);
    }

    /**
     * logout from mailbox and disconnect from the mailboxserver.
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public final void logout() {
        try {
            mailboxServerProxy.logOff();
        } catch (final SpdProtocolException spe) {
            // we log it, but no further action
            LOGGER.error("Error in logoff:", spe);
        }
    }

    /**
     * @param mailbox
     *            The mailbox where the messages are received from.
     * @param regel
     *            Logboekregel zodat het aantal ontvangen berichten vastgelegd kan worden
     * @throws VoaException
     *             thrown when either the listMessages contains errors or de database is not reachable
     * 
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public final void receiveAndStoreMessagesFromMailbox(final Mailbox mailbox, final LogboekRegel regel)
            throws VoaException {
        int nextSeqNr = 0;
        int msgReceivedOk = 0;
        int msgReceivedNok = 0;

        final int limitNumber = mailbox.getLimitNumber();
        final List<Integer> seqNummers = new ArrayList<Integer>(limitNumber);
        try {
            do {
                seqNummers.clear();
                nextSeqNr =
                        mailboxServerProxy.listMessages(nextSeqNr, seqNummers, limitNumber, SpdConstants.MSSTATUS,
                                SpdConstants.PRIORITY);

                for (final Integer seqNr : seqNummers) {
                    try {
                        final Bericht bericht = mailboxServerProxy.getMessage(seqNr);
                        if (bericht != null) {
                            bericht.setStatus(StatusEnum.MAILBOX_RECEIVED);
                            bericht.setRegistratieDt(new Date());
                            bepaalEsbMessageId(bericht);
                            bepaalEsbCorrelationId(bericht);
                            final Bericht berichtToSave = handleDeliveryOrStatusReport(bericht);
                            voiscDbProxy.saveBericht(berichtToSave, VoiscConstants.RECONNECT,
                                    VoiscConstants.INKOMEND_MB_BERICHT);
                            if (berichtToSave.isReport()) {
                                // Maak en sla een herhaal bericht.
                                createAndStoreHerhaalBericht(berichtToSave);
                            }
                            msgReceivedOk++;
                        } else {
                            msgReceivedNok++;
                        }
                    } catch (final SpdProtocolException spe) {
                        LOGGER.error("Fout in protocol bij het ontvangen van bericht(MSSequenceNr: " + seqNr
                                + CLOSING_PARENTHESIS, spe);
                        msgReceivedNok++;
                    } catch (final VoaException ve) {
                        msgReceivedNok++;
                        throw ve;
                    }
                }
            } while (nextSeqNr != 0);
        } catch (final SpdProtocolException spe) {
            throw new VoaException(MessagesCodes.ERRMSG_VOA_RECEIVE_ERROR, null, spe);
        } finally {
            regel.setAantalOntvangenOK(msgReceivedOk);
            regel.setAantalOntvangenNOK(msgReceivedNok);
        }
    }

    private void createAndStoreHerhaalBericht(final Bericht origBericht) throws VoaException {
        final Bericht herhaalBericht = origBericht.createHerhaalBericht();
        voiscDbProxy.saveBericht(herhaalBericht, VoiscConstants.RECONNECT, !VoiscConstants.INKOMEND_MB_BERICHT);
    }

    /**
     * Check of het een gewoon bericht is of een deliveryreport of statusreport.
     * 
     * @param bericht
     * @return
     */
    private Bericht handleDeliveryOrStatusReport(final Bericht bericht) {
        Bericht returnBericht = bericht;
        if (bericht.getNonDeliveryReason() != null) {
            final Bericht eerderBericht =
                    voiscDbProxy.getBerichtByDispatchSequenceNumber(bericht.getDispatchSequenceNumber());
            eerderBericht.setNonDeliveryReason(bericht.getNonDeliveryReason());
            eerderBericht.setReportDeliveryTime(new Date());
            returnBericht = eerderBericht;
            returnBericht.setReport(true);
        }

        if (bericht.getNonReceiptReason() != null) {
            final Bericht eerderBericht = voiscDbProxy.getBerichtByEref(bericht.getEref2());
            eerderBericht.setNonReceiptReason(bericht.getNonReceiptReason());
            eerderBericht.setReportDeliveryTime(new Date());
            returnBericht = eerderBericht;
            returnBericht.setReport(true);
        }
        return returnBericht;
    }

    /**
     * Bepaal of de combinatie van originator en bref reeds is ontvangen. Zo ja, dan betreft dit een door de GBA
     * gemeente verstuurd herhaalbericht en dient deze dezelfde EsbMessageId te krijgen zodat deze ook de herhaling kan
     * detecteren.
     * 
     * @param bericht
     *            bericht
     */
    private void bepaalEsbMessageId(final Bericht bericht) {
        final Bericht eerderBericht =
                voiscDbProxy.getBerichtByBrefAndOrginator(bericht.getBref(), bericht.getOriginator());

        if (eerderBericht == null) {
            bericht.setEsbMessageId(BerichtId.generateMessageId());
        } else {
            bericht.setEsbMessageId(eerderBericht.getEsbMessageId());
        }

    }

    /**
     * Zoek het voorgaande bericht en pak daarvan het esb correlatie id zodat het bericht door de ESB weer gecorreleerd
     * kan worden.
     * 
     * @param bericht
     *            Dat uiteindelijk naar de ESB gestuurd moet worden.
     */
    private void bepaalEsbCorrelationId(final Bericht bericht) {
        final String eref2 = bericht.getEref2();
        if (eref2 != null && !eref2.isEmpty()) {
            final Bericht eerderBericht = voiscDbProxy.getBerichtByEref(eref2);
            if (eerderBericht != null) {
                bericht.setEsbCorrelationId(eerderBericht.getEsbMessageId());
            }
        }
    }

    /**
     * Send all messages to the mailbox. All berichten in the database marked as to be send are sent tot the mailbox.
     * This method will will send one message and than update the status of the message in the database. If it is not
     * possible to update the status of the message, because of database failures (i.e. database connection is lost)
     * this method will retry a given number of times to update the message. But the message itself will be send only
     * once to the Mailbox Server. We assume we are logged in to the mailbox server. I.e. login() must be called before
     * summarize() is called (and logout() somewhere after)
     * 
     * @param regel
     *            Logboekregel zodat het aantal verzonden berichten vastgelegd kan worden
     * @param messagesToSend
     *            Lijst met berichten dat verstuurd moet worden
     * 
     * @throws VoaException
     *             Wordt gegooid als het te verzenden bericht niet bijgewerkt kan worden in de database
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public final void sendMessagesToMailbox(final LogboekRegel regel, final List<Bericht> messagesToSend)
            throws VoaException {
        int msgSendOk = 0;
        int msgSendNok = 0;
        try {
            for (final Bericht bericht : messagesToSend) {
                try {
                    // check of recipient bestaat.
                    checkRecipient(bericht);

                    // Eenmalig proberen op te slaan met status MESG_SENDING. Controle of de DB aanwezig is.
                    bericht.setStatus(StatusEnum.MESG_SENDING);
                    voiscDbProxy.saveBericht(bericht, !VoiscConstants.RECONNECT, !VoiscConstants.INKOMEND_MB_BERICHT);

                    // Stuur bericht en sla o.a. het dispatchnummer op wat we van de MBS terug kijgen.
                    mailboxServerProxy.putMessage(bericht);
                    bericht.setStatus(StatusEnum.MAILBOX_SENT);
                    voiscDbProxy.saveBericht(bericht, VoiscConstants.RECONNECT, !VoiscConstants.INKOMEND_MB_BERICHT);
                    msgSendOk++;
                } catch (final SpdProtocolException spe) {
                    final Long berichtId = bericht.getId();
                    LOGGER.error("Fout in protocol bij verzenden van bericht(ID: " + berichtId + CLOSING_PARENTHESIS,
                            spe);
                    msgSendNok++;
                    this.logSeqNr(bericht);
                } catch (final EsbFoutException efe) {
                    // stuur een error bericht naar de ESB (dus opslaan in de berichten tabel).
                    msgSendNok++;
                    voiscDbProxy.sendEsbErrorBericht(bericht, efe.getMessage());
                } catch (final VoaException ve) {
                    msgSendNok++;
                    throw ve;
                }
            }
        } finally {
            regel.setAantalVerzondenOK(msgSendOk);
            regel.setAantalVerzondenNOK(msgSendNok);
        }
    }

    /**
     * Check of de recipient bestaat. Zo niet dan wordt een EsbFoutException gegooid.
     * 
     * @param bericht
     *            Het bericht waar de recipient van gecontroleerd wordt.
     * @throws EsbFoutException
     *             Exception als de recipient niet bestaat.
     */
    private void checkRecipient(final Bericht bericht) throws EsbFoutException {
        final Mailbox mailbox = voiscDbProxy.getMailboxByNummer(bericht.getRecipient());
        if (mailbox == null) {
            throw new EsbFoutException("Recipient bestaat niet.");
        }
    }

    @Override
    public final void connectToMailboxServer() {
        mailboxServerProxy.connect();
    }

    @Override
    public final List<Bericht> getMessagesToSend(final Mailbox mailbox) {
        return voiscDbProxy.getBerichtToSendMBS(mailbox.getMailboxnr());
    }

    @Override
    public final List<Bericht> getMessagesToSendToQueue(final int limit) {
        return voiscDbProxy.getBerichtToSendQueue(limit);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public final void sendMessagesToEsb(final List<Bericht> messagesToSend) {

        for (final Bericht bericht : messagesToSend) {
            try {
                // stuur naar de queue
                queueService.sendMessage(bericht);
                bericht.setStatus(StatusEnum.QUEUE_SENT);
                voiscDbProxy.saveBericht(bericht, VoiscConstants.RECONNECT, !VoiscConstants.INKOMEND_MB_BERICHT);
            } catch (final JmsException je) {
                final Long berichtId = bericht.getId();
                LOGGER.error("Fout bij versturen van bericht (ID: " + berichtId + ") naar JMS-Queue", je);
            } catch (final VoaException ve) {
                final Long berichtId = bericht.getId();
                LOGGER.error("Fout bij opslaan van bericht (ID: " + berichtId + ") in de DB", ve);
            }
        }
    }

    /**
     * @param bericht
     *            het bericht waarvan het sequentie-nummer moet worden gelogd.
     */
    private void logSeqNr(final Bericht bericht) {
        final Integer mbSeqNr = bericht.getDispatchSequenceNumber();
        if (mbSeqNr != null) {
            LOGGER.error("bericht heeft Mailbox SequenceNumber: " + mbSeqNr.intValue());
        } else {
            LOGGER.error("Bericht heeft (nog) geen Mailbox SequenceNumber ");
        }
    }
}
