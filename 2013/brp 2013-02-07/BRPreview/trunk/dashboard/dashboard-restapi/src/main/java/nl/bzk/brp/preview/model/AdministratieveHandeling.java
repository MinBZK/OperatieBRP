/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.preview.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.core.style.ToStringCreator;


/**
 * De klasse AdministratieveHandeling.
 */
public class AdministratieveHandeling {

    /** De administratieve handeling id. */
    private Long          administratieveHandelingId;

    /** De soort administratieve handeling. */
    private String        soortAdministratieveHandeling;

    /** De soort administratieve handeling code. */
    private String        soortAdministratieveHandelingCode;

    /** De tijdstip ontlening. */
    private Date          tijdstipOntlening;

    /** De toelichting ontlening. */
    private String        toelichtingOntlening;

    /** De tijdstip registratie. */
    private Date          tijdstipRegistratie;

    /** De partij. */
    private String        partij;

    /** De bzm. */
    private String        bzm;

    /** De verwerkingswijze. */
    private String        verwerkingswijze;

    /** De meldingen. */
    private final List<Melding> meldingen = new ArrayList<Melding>();

    /**
     * Instantieert een lege administratieve handeling.
     */
    public AdministratieveHandeling() {

    }


    /**
     * Instantieert een administratieve handeling op basis van een andere administratieve handeling.
     *
     * @param administratieveHandeling de administratieve handeling
     */
    public AdministratieveHandeling(final AdministratieveHandeling administratieveHandeling) {
        administratieveHandelingId = administratieveHandeling.getAdministratieveHandelingId();
        soortAdministratieveHandeling = administratieveHandeling.getSoortAdministratieveHandeling();
        soortAdministratieveHandelingCode = administratieveHandeling.getSoortAdministratieveHandelingCode();
        tijdstipOntlening = administratieveHandeling.getTijdstipOntlening();
        toelichtingOntlening = administratieveHandeling.getToelichtingOntlening();
        tijdstipRegistratie = administratieveHandeling.getTijdstipRegistratie();
        partij = administratieveHandeling.getPartij();
        bzm = administratieveHandeling.getBzm();
        verwerkingswijze = administratieveHandeling.getVerwerkingswijze();
    }

    /**
     * Haalt een verwerkingswijze op.
     *
     * @return verwerkingswijze
     */
    public String getVerwerkingswijze() {
        return verwerkingswijze;
    }

    /**
     * Instellen van verwerkingswijze.
     *
     * @param verwerkingswijze de nieuwe verwerkingswijze
     */
    public void setVerwerkingswijze(final String verwerkingswijze) {
        this.verwerkingswijze = verwerkingswijze;
    }

    /**
     * Haalt een administratieve handeling id op.
     *
     * @return administratieve handeling id
     */
    public Long getAdministratieveHandelingId() {
        return administratieveHandelingId;
    }

    /**
     * Instellen van administratieve handeling id.
     *
     * @param administratieveHandelingId de nieuwe administratieve handeling id
     */
    public void setAdministratieveHandelingId(final Long administratieveHandelingId) {
        this.administratieveHandelingId = administratieveHandelingId;
    }

    /**
     * Haalt een soort administratieve handeling op.
     *
     * @return soort administratieve handeling
     */
    public String getSoortAdministratieveHandeling() {
        return soortAdministratieveHandeling;
    }

    /**
     * Instellen van soort administratieve handeling.
     *
     * @param soortAdministratieveHandeling de nieuwe soort administratieve handeling
     */
    public void setSoortAdministratieveHandeling(final String soortAdministratieveHandeling) {
        this.soortAdministratieveHandeling = soortAdministratieveHandeling;
    }

    /**
     * Haalt een soort administratieve handeling code op.
     *
     * @return soort administratieve handeling code
     */
    public String getSoortAdministratieveHandelingCode() {
        return soortAdministratieveHandelingCode;
    }

    /**
     * Instellen van soort administratieve handeling code.
     *
     * @param soortAdministratieveHandelingCode de nieuwe soort administratieve handeling code
     */
    public void setSoortAdministratieveHandelingCode(final String soortAdministratieveHandelingCode) {
        this.soortAdministratieveHandelingCode = soortAdministratieveHandelingCode;
    }

    /**
     * Haalt een tijdstip ontlening op.
     *
     * @return tijdstip ontlening
     */
    public Date getTijdstipOntlening() {
        return tijdstipOntlening;
    }

    /**
     * Instellen van tijdstip ontlening.
     *
     * @param tijdstipOntlening de nieuwe tijdstip ontlening
     */
    public void setTijdstipOntlening(final Date tijdstipOntlening) {
        this.tijdstipOntlening = tijdstipOntlening;
    }

    /**
     * Haalt een toelichting ontlening op.
     *
     * @return toelichting ontlening
     */
    public String getToelichtingOntlening() {
        return toelichtingOntlening;
    }

    /**
     * Instellen van toelichting ontlening.
     *
     * @param toelichtingOntlening de nieuwe toelichting ontlening
     */
    public void setToelichtingOntlening(final String toelichtingOntlening) {
        this.toelichtingOntlening = toelichtingOntlening;
    }

    /**
     * Haalt een tijdstip registratie op.
     *
     * @return tijdstip registratie
     */
    public Date getTijdstipRegistratie() {
        return tijdstipRegistratie;
    }

    /**
     * Instellen van tijdstip registratie.
     *
     * @param tijdstipRegistratie de nieuwe tijdstip registratie
     */
    public void setTijdstipRegistratie(final Date tijdstipRegistratie) {
        this.tijdstipRegistratie = tijdstipRegistratie;
    }

    /**
     * Haalt een partij op.
     *
     * @return partij
     */
    public String getPartij() {
        return partij;
    }

    /**
     * Instellen van partij.
     *
     * @param partij de nieuwe partij
     */
    public void setPartij(final String partij) {
        this.partij = partij;
    }

    /**
     * Instellen van bzm.
     *
     * @param bzm de nieuwe bzm
     */
    public void setBzm(final String bzm) {
        this.bzm = bzm;
    }

    /**
     * Haalt een bzm op.
     *
     * @return bzm
     */
    public String getBzm() {
        return bzm;
    }

    /**
     * Haalt een meldingen op.
     *
     * @return meldingen
     */
    public List<Melding> getMeldingen() {
        return meldingen;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringCreator(this).append("id", administratieveHandelingId)
                .append("soort", soortAdministratieveHandeling).append("partij", partij)
                .append("tsontlening", tijdstipOntlening).append("toelontlening", toelichtingOntlening)
                .append("tsreg", tijdstipRegistratie).toString();
    }

}
