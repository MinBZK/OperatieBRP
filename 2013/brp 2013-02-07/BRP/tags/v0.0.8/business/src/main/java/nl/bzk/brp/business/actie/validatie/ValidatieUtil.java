/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.actie.validatie;

import java.util.List;

import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.SoortMelding;

/**
 * Utility class t.b.v. het uitvoeren van validaties.
 */
public final class ValidatieUtil {

    /**
     * Constructor private gemaakt omdat dit een Utility class is.
     */
    private ValidatieUtil() {
    }

    /**
     * Controleert of de waarde null of leeg is.
     *
     * @param meldingen Lijst waaraan de melding kan worden toegevoegd.
     * @param waarde De te controleren waarde.
     * @param veldNaam Naam van het veld dat gecontroleerd wordt, is nodig voor in de melding.
     */
    public static void controleerVerplichtVeld(final List<Melding> meldingen,
                                               final Object waarde, final String veldNaam)
    {
        if (waarde == null || (waarde instanceof String && isWaardeNullOfLeeg((String) waarde))) {
            meldingen.add(new Melding(SoortMelding.FOUT_OVERRULEBAAR, MeldingCode.ALG0002, "Veld: " + veldNaam));
        }
    }

    /**
     * Controleert of waarde null of een lege string is.
     *
     * @param waarde De te controleren waarde.
     * @return True indien waarde null of leeg is.
     */
    public static boolean isWaardeNullOfLeeg(final String waarde) {
        return waarde == null || "".equals(waarde.trim());
    }
}
