/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.bericht.basis;

import nl.bzk.brp.model.attribuuttype.Naam;
import nl.bzk.brp.model.basis.AbstractObjectTypeBericht;
import nl.bzk.brp.model.objecttype.bericht.DocumentStandaardGroepBericht;
import nl.bzk.brp.model.objecttype.logisch.Document;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortDocument;

/**
 *
 * .
 *
 */
public abstract class AbstractDocumentBericht extends AbstractObjectTypeBericht implements Document {

    private Naam                          documentsoortNaam;
    private SoortDocument                 soort;
    private DocumentStandaardGroepBericht standaard;

    /**
     * {@inheritDoc}
     */
    @Override
    public SoortDocument getSoort() {
        return soort;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DocumentStandaardGroepBericht getStandaard() {
        return standaard;
    }

    /**
     * Zet Soort van Document.
     *
     * @param soort Soort.
     */
    public void setSoort(final SoortDocument soort) {
        this.soort = soort;
    }

    /**
     * Zet Standaard van Document.
     *
     * @param standaard Standaard.
     */
    public void setStandaard(final DocumentStandaardGroepBericht standaard) {
        this.standaard = standaard;
    }

    public Naam getDocumentsoortNaam() {
        return documentsoortNaam;
    }

    public void setDocumentsoortNaam(Naam documentsoortNaam) {
        this.documentsoortNaam = documentsoortNaam;
    }

}
