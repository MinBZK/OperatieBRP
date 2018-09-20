/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern.basis;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Aktenummer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DocumentIdentificatie;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DocumentOmschrijving;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.basis.AbstractGroepBericht;
import nl.bzk.brp.model.logisch.kern.DocumentStandaardGroep;
import nl.bzk.brp.model.logisch.kern.basis.DocumentStandaardGroepBasis;


/**
 * Vorm van historie: formeel. Motivatie: het objecttype wordt gebruikt voor de verantwoording van een specifieke BRP
 * actie. Denkbaar is dat twee verschillende BRP acties verwijzen naar hetzelfde Document; relevant is welke gegevens er
 * toen geregistreerd stonden bij het Document, vandaar dat formele historie relevant is. NB: dit onderdeel van het
 * model is nog in ontwikkeling. Denkbaar is dat de modellering anders wordt. RvdP 17 jan 2012.
 *
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.BerichtModelGenerator.
 * Metaregister versie: 1.6.0.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2013-01-15 12:43:16.
 * Gegenereerd op: Tue Jan 15 12:53:48 CET 2013.
 */
public abstract class AbstractDocumentStandaardGroepBericht extends AbstractGroepBericht implements
        DocumentStandaardGroepBasis
{

    private DocumentIdentificatie identificatie;
    private Aktenummer            aktenummer;
    private DocumentOmschrijving  omschrijving;
    private String                partijCode;
    private Partij                partij;

    /**
     * {@inheritDoc}
     */
    @Override
    public DocumentIdentificatie getIdentificatie() {
        return identificatie;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Aktenummer getAktenummer() {
        return aktenummer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DocumentOmschrijving getOmschrijving() {
        return omschrijving;
    }

    /**
     *
     *
     * @return
     */
    public String getPartijCode() {
        return partijCode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Partij getPartij() {
        return partij;
    }

    /**
     * Zet Identificatie van Standaard.
     *
     * @param identificatie Identificatie.
     */
    public void setIdentificatie(final DocumentIdentificatie identificatie) {
        this.identificatie = identificatie;
    }

    /**
     * Zet Aktenummer van Standaard.
     *
     * @param aktenummer Aktenummer.
     */
    public void setAktenummer(final Aktenummer aktenummer) {
        this.aktenummer = aktenummer;
    }

    /**
     * Zet Omschrijving van Standaard.
     *
     * @param omschrijving Omschrijving.
     */
    public void setOmschrijving(final DocumentOmschrijving omschrijving) {
        this.omschrijving = omschrijving;
    }

    /**
     *
     *
     * @param partijCode
     */
    public void setPartijCode(final String partijCode) {
        this.partijCode = partijCode;
    }

    /**
     * Zet Partij van Standaard.
     *
     * @param partij Partij.
     */
    public void setPartij(final Partij partij) {
        this.partij = partij;
    }

}
