/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

import nl.bzk.brp.model.attribuuttype.Datum;

/**
 * Een object dat de interface GeldigheidsPeriode ondersteund, moet een datumaang, datumEinde kunnen terggeven
 * en een test ondersteunen of het object geldig is op een specifieke peilDatum.
 *
 */
public interface GeldigheidsPeriode {

    /**
     * Geeft de aanvangs datum.
     * @return de datum
     */
    Datum getDatumAanvang();

    /**
     * Geeft de einde datum.
     * @return de datum
     */
    Datum getDatumEinde();

    /**
     * Test of het object geldig is op een bepaald datum.
     * @param peilDatum de datum
     * @return true als geldig, false anders.
     */
    boolean isGeldigOp(final Datum peilDatum);

    /**
     * .
     * @param beginDatum .
     * @param eindDatum .
     * @return .
     */
    boolean isGeldigPeriode(final Datum beginDatum, final Datum eindDatum);
}
