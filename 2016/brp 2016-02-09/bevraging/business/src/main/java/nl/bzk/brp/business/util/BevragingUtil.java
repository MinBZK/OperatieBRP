/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.util;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Historievorm;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.HistorievormAttribuut;

/**
 *  Algemene utility klasse voor veelgebruikte functies voor Datum/Tijd en Historie.
 */
public final class BevragingUtil {

    /**
     * Utility class private constructor.
     */
    private BevragingUtil() {

    }

    /**
     * Geef het standaard formeel peilmoment voor bevraging (NU).
     * @return tijdstip nu
     */
    public static DatumTijdAttribuut getDefaultFormeelPeilmomentVoorBevraging() {
        return DatumTijdAttribuut.nu();
    }

    /**
     * Geef het standaard materieel peilmoment voor bevraging (VANDAAG).
     * @return datum vandaag
     */
    public static DatumAttribuut getDefaultMaterieelPeilmomentVoorBevraging() {
        return DatumAttribuut.vandaag();
    }

    /**
     * Geef de standaard historie vorm voor bevraging (GEEN).
     * @return geen historievorm
     */
    public static HistorievormAttribuut getDefaultHistorieVormVoorBevraging() {
        return new HistorievormAttribuut(Historievorm.GEEN);
    }
}
