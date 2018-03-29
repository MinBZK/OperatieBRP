/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.attribuut;

import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;

/**
 * Een wrapper definitie voor BRP attributen met onderzoek.
 */
public interface BrpAttribuutMetOnderzoek extends BrpAttribuut {

    /**
     * Geef de waarde van waarde.
     * @return de waarde van het attribuut.
     */
    Object getWaarde();

    /**
     * True als het element inhoudelijk is gevuld. Onderzoek maakt geen deel uit van het element en wordt dus niet
     * meegenomen of het element gevuld is of niet.
     * @return true of de waarde van het element gevuld is ongeacht of onderzoek is gevuld.
     */
    boolean isInhoudelijkGevuld();

    /**
     * Geef de waarde van onderzoek.
     * @return Een onderzoek als dit element in onderzoek staat.
     */
    Lo3Onderzoek getOnderzoek();
}
