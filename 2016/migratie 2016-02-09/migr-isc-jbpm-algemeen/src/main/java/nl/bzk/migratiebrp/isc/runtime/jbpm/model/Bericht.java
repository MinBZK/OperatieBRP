/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.runtime.jbpm.model;

import java.io.Serializable;
import java.util.Date;

import org.jbpm.graph.exe.ProcessInstance;

/**
 * Bericht.
 */
@SuppressWarnings("checkstyle:designforextension")
public class Bericht implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Date tijdstip;
    private String kanaal;
    private Character richting;
    private String messageId;
    private String correlationId;
    private String bericht;
    private String naam;
    private ProcessInstance processInstance;
    private VirtueelProces virtueelProces;
    private String verzendendePartij;
    private String ontvangendePartij;
    private Long msSequenceNumber;
    private String actie;
    private Boolean indicatieGeteld;

    /**
     * Geeft het ID terug.
     *
     * @return het ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Zet het ID.
     *
     * @param id
     *            Het te zetten ID.
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Geeft het tijdstip terug.
     *
     * @return het tijdstip
     */
    public Date getTijdstip() {
        return tijdstip == null ? null : new Date(tijdstip.getTime());
    }

    /**
     * Zet het tijstip.
     *
     * @param tijdstip
     *            Het te zetten tijdstip.
     */
    public void setTijdstip(final Date tijdstip) {
        this.tijdstip = tijdstip == null ? null : new Date(tijdstip.getTime());
    }

    /**
     * Geeft het kanaal terug.
     *
     * @return het kanaal
     */
    public String getKanaal() {
        return kanaal;
    }

    /**
     * Zet het kanaal.
     *
     * @param kanaal
     *            Het te zetten kanaal.
     */
    public void setKanaal(final String kanaal) {
        this.kanaal = kanaal;
    }

    /**
     * Geeft de richting terug.
     *
     * @return de richting
     */
    public Character getRichting() {
        return richting;
    }

    /**
     * Zet de richting.
     *
     * @param richting
     *            De te zetten richting.
     */
    public void setRichting(final Character richting) {
        this.richting = richting;
    }

    /**
     * Geeft het messageID terug.
     *
     * @return het messageID
     */
    public String getMessageId() {
        return messageId;
    }

    /**
     * Zet het messageID.
     *
     * @param messageId
     *            Het te zetten messageID.
     */
    public void setMessageId(final String messageId) {
        this.messageId = messageId;
    }

    /**
     * Geeft het correlatieID terug.
     *
     * @return het correlatieID
     */
    public String getCorrelationId() {
        return correlationId;
    }

    /**
     * Zet het correlatieID.
     *
     * @param correlationId
     *            Het te zetten correlatieID.
     */
    public void setCorrelationId(final String correlationId) {
        this.correlationId = correlationId;
    }

    /**
     * Geeft het bericht terug.
     *
     * @return het bericht
     */
    public String getBericht() {
        return bericht;
    }

    /**
     * Zet het bericht.
     *
     * @param bericht
     *            Het te zetten bericht.
     */
    public void setBericht(final String bericht) {
        this.bericht = bericht;
    }

    /**
     * Geeft de naam terug.
     *
     * @return de naam
     */
    public String getNaam() {
        return naam;
    }

    /**
     * Zet de naam.
     *
     * @param naam
     *            De te zetten naam.
     */
    public void setNaam(final String naam) {
        this.naam = naam;
    }

    /**
     * Geeft de proces instantie terug.
     *
     * @return de proces instantie
     */
    public ProcessInstance getProcessInstance() {
        return processInstance;
    }

    /**
     * Zet de proces instantie.
     *
     * @param processInstance
     *            De te zetten proces instantie.
     */
    public void setProcessInstance(final ProcessInstance processInstance) {
        this.processInstance = processInstance;
    }

    /**
     * Geeft het virtueel proces terug.
     *
     * @return het virtueel proces
     */
    public VirtueelProces getVirtueelProces() {
        return virtueelProces;
    }

    /**
     * Zet het virtueel proces.
     *
     * @param virtueelProces
     *            Het te zetten virtueel proces.
     */
    public void setVirtueelProces(final VirtueelProces virtueelProces) {
        this.virtueelProces = virtueelProces;
    }

    /**
     * Geeft de verzendende partij terug.
     *
     * @return de verzenden partij
     */
    public String getVerzendendePartij() {
        return verzendendePartij;
    }

    /**
     * Zet de verzendende partij.
     *
     * @param verzendendePartij
     *            De te zetten verzendende partij.
     */
    public void setVerzendendePartij(final String verzendendePartij) {
        this.verzendendePartij = verzendendePartij;
    }

    /**
     * Geeft de ontvangende partij terug.
     *
     * @return de ontvangende partij
     */
    public String getOntvangendePartij() {
        return ontvangendePartij;
    }

    /**
     * Zet de ontvangende partij.
     *
     * @param ontvangendePartij
     *            De te zetten ontvangende partij.
     */
    public void setOntvangendePartij(final String ontvangendePartij) {
        this.ontvangendePartij = ontvangendePartij;
    }

    /**
     * Geeft het msSequenceNumber terug.
     *
     * @return het msSequenceNumber
     */
    public Long getMsSequenceNumber() {
        return msSequenceNumber;
    }

    /**
     * Zet het MsSequenceNumber.
     *
     * @param msSequenceNumber
     *            Het te zetten MsSequenceNumber.
     */
    public void setMsSequenceNumber(final Long msSequenceNumber) {
        this.msSequenceNumber = msSequenceNumber;
    }

    /**
     * Geeft de actie terug.
     *
     * @return de actie
     */
    public String getActie() {
        return actie;
    }

    /**
     * Zet de actie.
     *
     * @param actie
     *            De te zetten actie.
     */
    public void setActie(final String actie) {
        this.actie = actie;
    }

    /**
     * Geeft de indicatie dat het bericht is geteld terug.
     *
     * @return de indicatie dat het bericht is geteld
     */
    public Boolean getIndicatieGeteld() {
        return indicatieGeteld;
    }

    /**
     * Zet de indicatie dat het bericht is geteld.
     *
     * @param indicatieGeteld
     *            De te zetten indicatie dat het bericht is geteld.
     */
    public void setIndicatieGeteld(final Boolean indicatieGeteld) {
        this.indicatieGeteld = indicatieGeteld;
    }

}
