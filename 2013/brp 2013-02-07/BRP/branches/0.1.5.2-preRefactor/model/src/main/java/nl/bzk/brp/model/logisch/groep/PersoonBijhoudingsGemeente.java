/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.groep;

import nl.bzk.brp.model.gedeeld.Partij;
import nl.bzk.brp.model.logisch.AbstractIdentificerendeGroep;

/**
 * De logische groep BijhoudingsGemeente binnen Persoon.
 *
 */

public class PersoonBijhoudingsGemeente extends AbstractIdentificerendeGroep {
    private Partij gemeente;
    private Integer datumInschrijving;
    private Boolean indOnverwerktDocumentAanwezig;

    /**
     * @return the gemeente
     */
    public Partij getGemeente() {
        return gemeente;
    }
    /**
     * @param gemeente the gemeente to set
     */
    public void setGemeente(final Partij gemeente) {
        this.gemeente = gemeente;
    }
    /**
     * @return the datumInschrijving
     */
    public Integer getDatumInschrijving() {
        return datumInschrijving;
    }
    /**
     * @param datumInschrijving the datumInschrijving to set
     */
    public void setDatumInschrijving(final Integer datumInschrijving) {
        this.datumInschrijving = datumInschrijving;
    }
    /**
     * @return the indOnverwerktDocumentAanwezig
     */
    public Boolean getIndOnverwerktDocumentAanwezig() {
        return indOnverwerktDocumentAanwezig;
    }
    /**
     * @param indOnverwerktDocumentAanwezig the indOnverwerktDocumentAanwezig to set
     */
    public void setIndOnverwerktDocumentAanwezig(final Boolean indOnverwerktDocumentAanwezig) {
        this.indOnverwerktDocumentAanwezig = indOnverwerktDocumentAanwezig;
    }
}
