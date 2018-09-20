/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.binding;

/**
 * Enumeratie die de soort van een {@link Melding} aangeeft.
 */
public enum SoortMelding {

    /** Info geeft aan dat een melding puur informatief is. */
    INFO,
    /** Een waarschuwing geeft aan dat er mogelijk een fout of aandachtspunt is, maar dat alles wel goed is gegaan. */
    WAARSCHUWING,
    /** Een overrulebare fout geeft aan dat er iets fout is gegaan, maar dat de fout overruled kan worden. */
    FOUT_OVERRULEBAAR,
    /** Een onoverrulebare fout geeft aan dat er iets fout is gegaan en dat deze fout niet kan worden overruled. */
    FOUT_ONOVERRULEBAAR;

    public String getNaam() {
        return this.name();
    }

}
