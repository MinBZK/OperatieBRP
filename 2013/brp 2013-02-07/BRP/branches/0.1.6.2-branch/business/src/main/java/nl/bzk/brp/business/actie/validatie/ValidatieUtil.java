/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.actie.validatie;

import java.util.Collection;
import java.util.List;

import nl.bzk.brp.model.basis.AbstractAttribuutType;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.SoortMelding;
import org.apache.commons.lang.StringUtils;

/** Utility class t.b.v. het uitvoeren van validaties. */
public final class ValidatieUtil {

    /** Constructor private gemaakt omdat dit een Utility class is. */
    private ValidatieUtil() {
    }

    /**
     * Controleert of de waarde null of leeg is. In geval het om een collection of String gaat wordt de
     * lengte ook meegenomen.
     *
     * @param meldingen Lijst waaraan de melding kan worden toegevoegd.
     * @param waarde De te controleren waarde.
     * @param veldNaam Naam van het veld dat gecontroleerd wordt, is nodig voor in de melding.
     * @return true voor niet lege waarde, false anders
     */
    public static boolean controleerVerplichtVeld(final List<Melding> meldingen,
        final Object waarde, final String veldNaam)
    {
        boolean retval = true;
        if (waarde == null) {
            voegMeldingVerplichtVeld(meldingen, veldNaam);
            retval = false;
        } else if (waarde instanceof Collection) {
            Collection collection = (Collection) waarde;
            if (collection.size() == 0) {
                voegMeldingVerplichtVeld(meldingen, veldNaam);
                retval = false;
            }
        } else if (waarde instanceof AbstractAttribuutType) {
            AbstractAttribuutType attr = (AbstractAttribuutType) waarde;
            if (attr.getWaarde() == null) {
                voegMeldingVerplichtVeld(meldingen, veldNaam);
                retval = false;
            } else if (attr.getWaarde() instanceof String
                       && StringUtils.isBlank((String) attr.getWaarde()))
            {
                voegMeldingVerplichtVeld(meldingen, veldNaam);
                retval = false;
            }
        } else if (waarde instanceof String
                && StringUtils.isBlank((String) waarde))
        {
            voegMeldingVerplichtVeld(meldingen, veldNaam);
                retval = false;
        }
        return retval;
    }

    /**
     * Voegt een melding toe voor een verplicht veld aan de lijst van meldingen.
     *
     * @param meldingen Lijst van meldingen.
     * @param veldNaam Naam van het veld.
     */
    public static void voegMeldingVerplichtVeld(final List<Melding> meldingen, final String veldNaam) {
        meldingen.add(new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.ALG0002,
            String.format("%s: %s", MeldingCode.ALG0002.getOmschrijving(), veldNaam)));
    }
}
