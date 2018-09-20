/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ByteaopslagAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ChecksumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VersienummerKleinAttribuut;
import nl.bzk.brp.model.basis.Groep;

/**
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.LogischModelGenerator")
public interface PersoonCacheStandaardGroepBasis extends Groep {

    /**
     * Retourneert Versienummer van Standaard.
     *
     * @return Versienummer.
     */
    VersienummerKleinAttribuut getVersienummer();

    /**
     * Retourneert Persoon historie volledig checksum van Standaard.
     *
     * @return Persoon historie volledig checksum.
     */
    ChecksumAttribuut getPersoonHistorieVolledigChecksum();

    /**
     * Retourneert Persoon historie volledig gegevens van Standaard.
     *
     * @return Persoon historie volledig gegevens.
     */
    ByteaopslagAttribuut getPersoonHistorieVolledigGegevens();

    /**
     * Retourneert Afnemerindicatie checksum van Standaard.
     *
     * @return Afnemerindicatie checksum.
     */
    ChecksumAttribuut getAfnemerindicatieChecksum();

    /**
     * Retourneert Afnemerindicatie gegevens van Standaard.
     *
     * @return Afnemerindicatie gegevens.
     */
    ByteaopslagAttribuut getAfnemerindicatieGegevens();

}
