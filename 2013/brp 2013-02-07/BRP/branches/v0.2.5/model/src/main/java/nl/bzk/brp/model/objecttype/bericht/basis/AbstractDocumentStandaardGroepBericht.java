/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.bericht.basis;

import nl.bzk.brp.model.attribuuttype.Aktenummer;
import nl.bzk.brp.model.attribuuttype.DocumentIdentificatie;
import nl.bzk.brp.model.attribuuttype.DocumentOmschrijving;
import nl.bzk.brp.model.attribuuttype.Gemeentecode;
import nl.bzk.brp.model.basis.AbstractGroepBericht;
import nl.bzk.brp.model.groep.logisch.DocumentStandaardGroep;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Partij;

/**
 *
 * .
 *
 */
public abstract class AbstractDocumentStandaardGroepBericht extends AbstractGroepBericht implements DocumentStandaardGroep {

    private DocumentIdentificatie identificatie;
    private Aktenummer aktenummer;
    private DocumentOmschrijving omschrijving;
    private Partij partij;
    private Gemeentecode partijCode;

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
     * {@inheritDoc}
     */
    @Override
    public Partij getPartij() {
        return partij;
    }

    /**
     * Zet Identificatie van Standaard.
     *
     * @param identificatie
     *            Identificatie.
     */
    public void setIdentificatie(final DocumentIdentificatie identificatie) {
        this.identificatie = identificatie;
    }

    /**
     * Zet Aktenummer van Standaard.
     *
     * @param aktenummer
     *            Aktenummer.
     */
    public void setAktenummer(final Aktenummer aktenummer) {
        this.aktenummer = aktenummer;
    }

    /**
     * Zet Omschrijving van Standaard.
     *
     * @param omschrijving
     *            Omschrijving.
     */
    public void setOmschrijving(final DocumentOmschrijving omschrijving) {
        this.omschrijving = omschrijving;
    }

    /**
     * Zet Partij van Standaard.
     *
     * @param partij
     *            Partij.
     */
    public void setPartij(final Partij partij) {
        this.partij = partij;
    }

    public Gemeentecode getPartijCode() {
        return partijCode;
    }

    public void setPartijCode(Gemeentecode partijCode) {
        this.partijCode = partijCode;
    }

}
