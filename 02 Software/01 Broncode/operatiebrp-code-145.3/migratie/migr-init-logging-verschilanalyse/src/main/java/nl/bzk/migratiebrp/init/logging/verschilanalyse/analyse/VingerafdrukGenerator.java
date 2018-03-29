/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.logging.verschilanalyse.analyse;

import java.util.List;
import nl.bzk.migratiebrp.init.logging.model.domein.entities.VerschilAnalyseRegel;
import org.apache.commons.lang3.StringUtils;

/**
 * Genereert een vingerafdruk ('fingerprint') op basis van de VerschilAnalyse. De vingerafdruk wordt
 * als volgt opgebouwd:
 * <ul>
 * <li>Cxx - waarbij xx het categorie nummer is</li>
 * <li>Sxx - waarbij xx het stapel nummer is (optioneel)</li>
 * <li>Vxx - waarbij xx het voorkomen nummer is (optioneel)</li>
 * <li>elementnummer (optioneel)</li>
 * <li>
 * <ul>
 * <li>A - nieuw (Added)</li>
 * <li>R - verwijderd (Removed)</li>
 * <li>M - aangepast (Modified)</li>
 * <li>NON_UNIQUE_MATCHED - Niet uniek gematched</li>
 * <li>NOT_ACTUAL - Actueel is niet als actueel teruggevonden</li>
 * </ul>
 * </li>
 * </ul>
 * <br>
 * Een voorbeeld van een vingerafdruk is:<br>
 * <code>C01S00V000110A0120A</code><br>
 * In dit voorbeeld zijn in Categorie 1 (C01), Stapel 0 (S00), Voorkomen 0 (V00) de elementnummers
 * 01.10 (0110A) en 01.20 (0120A) toegevoegd.<br>
 */
public final class VingerafdrukGenerator {
    private static final int DEFAULT_PADDING = 2;
    private static final String CAT_PREFIX = "C";
    private static final String STAPEL_PREFIX = "S";
    private static final String VK_PREFIX = "V";

    private VingerafdrukGenerator() {
    }

    /**
     * Genereert de vingerafdruk aan de hand van de meegegeven verschilAnalyse regels.
     * @param verschilAnalyse De verschilAnalyseRegels behorende bij een enkele gebeurtenis
     * @return de gegenereerde vingerafdruk
     */
    public static String maakVingerafdruk(final List<VerschilAnalyseRegel> verschilAnalyse) {
        if (verschilAnalyse.isEmpty()) {
            return null;
        }

        final StringBuilder vingerafdruk = new StringBuilder();
        appendHeader(vingerafdruk, verschilAnalyse.get(0));

        for (final VerschilAnalyseRegel regel : verschilAnalyse) {
            appendInhoud(vingerafdruk, regel);
        }

        return vingerafdruk.toString();
    }

    private static void appendHeader(final StringBuilder vingerafdruk, final VerschilAnalyseRegel regel) {
        // Categorie en Stapel zijn altijd ingevuld.
        vingerafdruk.append(CAT_PREFIX).append(zeroPadLengte(regel.getCategorie()));
        vingerafdruk.append(STAPEL_PREFIX).append(zeroPadLengte(regel.getStapel()));

        if (regel.getVoorkomen() != null) {
            vingerafdruk.append(VK_PREFIX).append(zeroPadLengte(regel.getVoorkomen()));
        }
    }

    private static void appendInhoud(final StringBuilder vingerafdruk, final VerschilAnalyseRegel regel) {
        if (regel.getElement() != null) {
            vingerafdruk.append(regel.getElement());
        }
        vingerafdruk.append(regel.getType().getCode());
    }

    /**
     * Voegt voorloopnullen toe, totdat waarde de opgegeven standaard lengte
     * {@link #DEFAULT_PADDING} heeft.
     * @param waarde een integer waarde waaraan voorloopnullen wordt toegevoegd
     * @return een {@link String} met daar in de waarde, aangevuld met voorloopnullen
     */
    private static String zeroPadLengte(final int waarde) {
        return StringUtils.leftPad(Integer.toString(waarde), DEFAULT_PADDING, "0");
    }
}
