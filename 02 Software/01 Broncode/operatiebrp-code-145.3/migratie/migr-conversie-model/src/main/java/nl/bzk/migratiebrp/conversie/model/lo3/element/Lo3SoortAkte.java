/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.element;

import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;

/**
 * Akten van toevalligegebeurtennis.
 */
public enum Lo3SoortAkte {
    /**
     * Erkenning na de geboorteaangifte.
     */
    AKTE_1C,
    /**
     * Ontkenning ouderschap (rechterlijke uitspraak).
     */
    AKTE_1E,
    /**
     * Notariele akte van erkenning.
     */
    AKTE_1J,
    /**
     * Vernietiging van een erkenning (rechterlijke uitspraak).
     */
    AKTE_1N,
    /**
     * Adoptie (rechterlijke uitspraak).
     */
    AKTE_1Q,
    /**
     * Verklaring ontkenning ouderschap ex artikel199, onder b, boek 1 BW.
     */
    AKTE_1U,
    /**
     * Gerechtelijke vaststelling van het ouderschap.
     */
    AKTE_1V,
    /**
     * Wijziging van de geslachtsnaam bij koninklijk besluit.
     */
    AKTE_1H,
    /**
     * Voornaamswijziging (rechterlijke uitspraak).
     */
    AKTE_1M,
    /**
     * Aktewijziging in verband met transseksualiteit.
     */
    AKTE_1S,
    /**
     * Overlijden.
     */
    AKTE_2A,
    /**
     * Lijkvinding.
     */
    AKTE_2G,
    /**
     * Huwelijk.
     */
    AKTE_3A,
    /**
     * Echtscheiding/huwelijksontbinding na scheiding van tafel en bed.
     */
    AKTE_3B,
    /**
     * Omzetting geregistreerd partnerschap in huwelijk.
     */
    AKTE_3H,
    /**
     * Geregistreerd partnerschap.
     */
    AKTE_5A,
    /**
     * Beeindiging geregistreerd partnerschap door overeenkomst of ontbinding.
     */
    AKTE_5B,
    /**
     * Omzetting huwelijk in geregistreerd partnerschap.
     */
    AKTE_5H;

    private static final int AKTENUMMER_LENGTE = 7;

    /**
     * Bepaal het soort akte voor een aktenummer.
     * @param aktenummer aktenummer
     * @return Soort akte, of null als het aktenummer ongeldig is, of de akte niet bekend is voor een Tb02
     */
    public static Lo3SoortAkte bepaalSoortAkteObvAktenummer(final String aktenummer) {
        try {
            final boolean ongeldigeAkte = aktenummer == null || aktenummer.length() != AKTENUMMER_LENGTE;
            final int tweedeKarakter = 2;
            return ongeldigeAkte ? null : valueOf(String.format("AKTE_%s%s", Character.toUpperCase(aktenummer.charAt(0)), aktenummer.charAt(tweedeKarakter)));
        } catch (final IllegalArgumentException e) {
            LoggerFactory.getLogger().debug("Fout tijdens bepalen soort akte", e);
            return null;
        }
    }
}
