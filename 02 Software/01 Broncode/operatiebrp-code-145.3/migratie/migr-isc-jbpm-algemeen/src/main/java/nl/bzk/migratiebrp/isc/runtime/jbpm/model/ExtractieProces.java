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
 * Proces extractie.
 */

public class ExtractieProces implements Serializable {
    private static final long serialVersionUID = 1L;

    // Veld nodig voor one-on-one PK
    private Long processInstanceId;
    private ProcessInstance processInstance;
    private String procesNaam;
    private String berichtType;
    private String kanaal;
    private String foutreden;
    private String anummer;
    private Date startdatum;
    private Date einddatum;
    private Date wachtStartdatum;
    private Date wachtEinddatum;

    /**
     * Geeft het proces instantie ID.
     * @return het proces instantie ID
     */
    public Long getProcessInstanceId() {
        return processInstanceId;
    }

    /**
     * Geeft proces instantie.
     * @return de proces instantie
     */
    public ProcessInstance getProcessInstance() {
        return processInstance;
    }

    /**
     * Zet de proces instantie.
     * @param processInstance De te zetten proces instantie
     */
    public void setProcessInstance(final ProcessInstance processInstance) {
        this.processInstance = processInstance;
    }

    /**
     * Geeft de procesnaam.
     * @return de procesnaam
     */
    public String getProcesNaam() {
        return procesNaam;
    }

    /**
     * Zet de procesnaam.
     * @param procesNaam De te zetten procesNaam
     */
    public void setProcesNaam(final String procesNaam) {
        this.procesNaam = procesNaam;
    }

    /**
     * Geeft het bericht type.
     * @return het bericht type
     */
    public String getBerichtType() {
        return berichtType;
    }

    /**
     * Zet het bericht type.
     * @param berichtType Het te zetten bericht type
     */
    public void setBerichtType(final String berichtType) {
        this.berichtType = berichtType;
    }

    /**
     * Geeft het kanaal.
     * @return het kanaal
     */
    public String getKanaal() {
        return kanaal;
    }

    /**
     * Zet het kanaal.
     * @param kanaal Het te zetten kanaal
     */
    public void setKanaal(final String kanaal) {
        this.kanaal = kanaal;
    }

    /**
     * Geeft de foutreden.
     * @return de foutreden
     */
    public String getFoutreden() {
        return foutreden;
    }

    /**
     * Zet de foutreden.
     * @param foutreden De te zetten foutreden
     */
    public void setFoutreden(final String foutreden) {
        this.foutreden = foutreden;
    }

    /**
     * Geeft het anummer.
     * @return het anummer
     */
    public String getAnummer() {
        return anummer;
    }

    /**
     * Zet het anummer.
     * @param anummer Het te zetten anummer
     */
    public void setAnummer(final String anummer) {
        this.anummer = anummer;
    }

    /**
     * Geeft de startdatum.
     * @return de startdatum
     */
    public Date getStartdatum() {
        return startdatum == null ? null : new Date(startdatum.getTime());
    }

    /**
     * Zet de startdatum.
     * @param startdatum De te zetten startdatum
     */
    public void setStartdatum(final Date startdatum) {
        this.startdatum = startdatum == null ? null : new Date(startdatum.getTime());
    }

    /**
     * Geeft de einddatum.
     * @return de einddatum
     */
    public Date getEinddatum() {
        return einddatum == null ? null : new Date(einddatum.getTime());
    }

    /**
     * Zet de einddatum.
     * @param einddatum De te zetten einddatum
     */
    public void setEinddatum(final Date einddatum) {
        this.einddatum = einddatum == null ? null : new Date(einddatum.getTime());
    }

    /**
     * Geeft de wacht startdatum.
     * @return de wacht startdatum
     */
    public Date getWachtStartdatum() {
        return wachtStartdatum == null ? null : new Date(wachtStartdatum.getTime());
    }

    /**
     * Zet de wacht startdatum.
     * @param wachtStartdatum De te zetten wacht startdatum
     */
    public void setWachtStartdatum(final Date wachtStartdatum) {
        this.wachtStartdatum = wachtStartdatum == null ? null : new Date(wachtStartdatum.getTime());
    }

    /**
     * Geeft de wacht einddatum.
     * @return de wacht einddatum
     */
    public Date getWachtEinddatum() {
        return wachtEinddatum == null ? null : new Date(wachtEinddatum.getTime());
    }

    /**
     * Zet de wacht einddatum.
     * @param wachtEinddatum De te zetten wacht einddatum
     */
    public void setWachtEinddatum(final Date wachtEinddatum) {
        this.wachtEinddatum = wachtEinddatum == null ? null : new Date(wachtEinddatum.getTime());
    }

}
