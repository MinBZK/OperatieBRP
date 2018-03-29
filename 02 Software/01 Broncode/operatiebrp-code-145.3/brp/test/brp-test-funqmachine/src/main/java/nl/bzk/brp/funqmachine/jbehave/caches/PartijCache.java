/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.funqmachine.jbehave.caches;

/**
 * PartijCache.
 */
public interface PartijCache {
    /**
     * Geeft partijcode ahv partijnaam.
     * @param partijNaam partijNaam
     * @return partijCode
     */
    String geefPartijCode(final String partijNaam);

    /**
     * Geeft partijnaam ahv partijcode.
     * @param partijCode partijcode
     * @return partijnaam
     */
    String geefPartijNaam(final String partijCode);

    /**
     * Geeft partij-oin ahv partijnaam.
     * @param partijNaam partijnaam
     * @return partij-oin
     */
    String geefPartijOin(final String partijNaam) ;
}
