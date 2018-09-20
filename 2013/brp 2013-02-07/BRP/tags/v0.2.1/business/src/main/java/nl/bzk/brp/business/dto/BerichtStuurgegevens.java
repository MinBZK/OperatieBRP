/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.dto;

import nl.bzk.brp.model.attribuuttype.Ja;
import nl.bzk.brp.model.basis.Identificeerbaar;

/**
 * Model klasse t.b.v. de binding van de stuurgegevens die in elk bericht voorkomen.
 */
public class BerichtStuurgegevens implements Identificeerbaar {

    private String  organisatie;
    private String  applicatie;
    private String  referentienummer;
    private String  crossReferentienummer;
    private Ja prevalidatieBericht;
    private String  verzendendId;

    @Override
    public void setVerzendendId(final String verzId) {
        verzendendId = verzId;
    }

    @Override
    public String getVerzendendId() {
        return verzendendId;
    }

    /**
     * Default constructor.
     */
    public BerichtStuurgegevens() {
    }

    /**
     * Constructor voor het initialiseren van alle velden.
     *
     * @param organisatie Verzendende organisatie.
     * @param applicatie Verzendende applicatie.
     * @param referentienummer Referentienummer.
     * @param crossReferentienummer Crossreferentienummer.
     * @param prevalidatieBericht prevalidatieBericht.
     */
    public BerichtStuurgegevens(final String organisatie, final String applicatie, final String referentienummer,
            final String crossReferentienummer, final Ja prevalidatieBericht)
    {
        this.organisatie = organisatie;
        this.applicatie = applicatie;
        this.referentienummer = referentienummer;
        this.crossReferentienummer = crossReferentienummer;
        this.prevalidatieBericht = prevalidatieBericht;
    }

    public String getOrganisatie() {
        return organisatie;
    }

    public void setOrganisatie(final String organisatie) {
        this.organisatie = organisatie;
    }

    public String getApplicatie() {
        return applicatie;
    }

    public void setApplicatie(final String applicatie) {
        this.applicatie = applicatie;
    }

    public String getReferentienummer() {
        return referentienummer;
    }

    public void setReferentienummer(final String referentienummer) {
        this.referentienummer = referentienummer;
    }

    public String getCrossReferentienummer() {
        return crossReferentienummer;
    }

    public void setCrossReferentienummer(final String crossReferentienummer) {
        this.crossReferentienummer = crossReferentienummer;
    }

    public Ja getPrevalidatieBericht() {
        return prevalidatieBericht;
    }

    public void setPrevalidatieBericht(final Ja prevalidatieBericht) {
        this.prevalidatieBericht = prevalidatieBericht;
    }

    /**
     * Controlleer of het om een prevalidatie bericht gaat.
     *
     * @return true als het om een prevalidatie bericht gaat.
     */
    public boolean isPrevalidatieBericht() {
        boolean isPrevalidatieBericht;

        isPrevalidatieBericht = prevalidatieBericht != null;

        return isPrevalidatieBericht;
    }
}
