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
 * Proces fout.
 */
@SuppressWarnings("checkstyle:designforextension")
public class Fout implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Date tijdstip;
    private String proces;
    private ProcessInstance processInstance;
    private String procesInitGemeente;
    private String procesDoelGemeente;
    private String code;
    private String melding;
    private String resolutie;

    /**
     * Geeft het ID.
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
     *            Het te zetten ID
     */
    public void setId(final Long id) {
        this.id = id;
    }

    /**
     * Geeft het tijdstip.
     * 
     * @return het tijdstip
     */
    public Date getTijdstip() {
        return tijdstip == null ? null : new Date(tijdstip.getTime());
    }

    /**
     * Zet het tijdstip.
     * 
     * @param tijdstip
     *            Het te zetten tijdstip
     */
    public void setTijdstip(final Date tijdstip) {
        this.tijdstip = tijdstip == null ? null : new Date(tijdstip.getTime());
    }

    /**
     * Geeft het proces.
     * 
     * @return het proces
     */
    public String getProces() {
        return proces;
    }

    /**
     * Zet het proces.
     * 
     * @param proces
     *            Het te zetten proces
     */
    public void setProces(final String proces) {
        this.proces = proces;
    }

    /**
     * Geeft de proces instantie.
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
     *            De te zetten proces instantie
     */
    public void setProcessInstance(final ProcessInstance processInstance) {
        this.processInstance = processInstance;
    }

    /**
     * Geeft de initiator gemeente van het proces.
     * 
     * @return de initiator gemeente van het proces
     */
    public String getProcesInitGemeente() {
        return procesInitGemeente;
    }

    /**
     * Zet de initiator gemeente van het proces.
     * 
     * @param procesInitGemeente
     *            De te zetten initiator gemeente.
     */
    public void setProcesInitGemeente(final String procesInitGemeente) {
        this.procesInitGemeente = procesInitGemeente;
    }

    /**
     * Geeft de doelgemeente van het proces.
     * 
     * @return de doelgemeente van het proces
     */
    public String getProcesDoelGemeente() {
        return procesDoelGemeente;
    }

    /**
     * Zet de doelgemeente van het proces.
     * 
     * @param procesDoelGemeente
     *            De te zetten doelgemeente
     */
    public void setProcesDoelGemeente(final String procesDoelGemeente) {
        this.procesDoelGemeente = procesDoelGemeente;
    }

    /**
     * Geeft de foutcode.
     * 
     * @return de foutcode
     */
    public String getCode() {
        return code;
    }

    /**
     * Zet de foutcode.
     * 
     * @param code
     *            De te zetten foutcode
     */
    public void setCode(final String code) {
        this.code = code;
    }

    /**
     * Geeft de foutmelding.
     * 
     * @return de foutmelding
     */
    public String getMelding() {
        return melding;
    }

    /**
     * Zet de foutmelding.
     * 
     * @param melding
     *            De te zetten foutmelding
     */
    public void setMelding(final String melding) {
        this.melding = melding;
    }

    /**
     * Geeft de resolutie.
     * 
     * @return de resolutie
     */
    public String getResolutie() {
        return resolutie;
    }

    /**
     * Zet de resolutie.
     * 
     * @param resolutie
     *            De te zetten resolutie
     */
    public void setResolutie(final String resolutie) {
        this.resolutie = resolutie;
    }

}
