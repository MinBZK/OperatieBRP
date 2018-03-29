/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen;

import com.google.common.collect.Maps;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBericht;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;

/**
 * Mappings van {@link SoortDienst soort dienst} naar inkomende en uitgaande {@link SoortBericht soort berichten}.
 */
public final class Mappings {

    private static final Map<SoortDienst, SoortBerichten> BEVRAGING_SOORT_DIENST_BERICHT_MAP = Maps.newEnumMap(SoortDienst.class);
    private static final Map<SoortDienst, SoortBerichten> STUF_SOORT_DIENST_BERICHT_MAP = Maps.newEnumMap(SoortDienst.class);
    private static final Map<SoortDienst, SoortBerichten> SYNCHRONISATIE_SOORT_DIENST_BERICHT_MAP = Maps.newEnumMap(SoortDienst.class);
    private static final Map<SoortDienst, SoortBerichten> AFNEMERINDICATIES_SOORT_DIENST_BERICHT_MAP = Maps.newEnumMap(SoortDienst.class);
    private static final Map<SoortDienst, SoortBerichten> SOORT_DIENST_BERICHT_MAP = Maps.newEnumMap(SoortDienst.class);

    static {
        BEVRAGING_SOORT_DIENST_BERICHT_MAP.put(SoortDienst.GEEF_DETAILS_PERSOON,
                new SoortBerichten(SoortBericht.LVG_BVG_GEEF_DETAILS_PERSOON, SoortBericht.LVG_BVG_GEEF_DETAILS_PERSOON_R));
        BEVRAGING_SOORT_DIENST_BERICHT_MAP
                .put(SoortDienst.ZOEK_PERSOON, new SoortBerichten(SoortBericht.LVG_BVG_ZOEK_PERSOON, SoortBericht.LVG_BVG_ZOEK_PERSOON_R));
        BEVRAGING_SOORT_DIENST_BERICHT_MAP.put(SoortDienst.ZOEK_PERSOON_OP_ADRESGEGEVENS,
                new SoortBerichten(SoortBericht.LVG_BVG_ZOEK_PERSOON_OP_ADRES, SoortBericht.LVG_BVG_ZOEK_PERSOON_OP_ADRES_R));
        BEVRAGING_SOORT_DIENST_BERICHT_MAP.put(SoortDienst.GEEF_MEDEBEWONERS_VAN_PERSOON,
                new SoortBerichten(SoortBericht.LVG_BVG_GEEF_MEDEBEWONERS, SoortBericht.LVG_BVG_GEEF_MEDEBEWONERS_R));

        SYNCHRONISATIE_SOORT_DIENST_BERICHT_MAP.put(SoortDienst.SYNCHRONISATIE_PERSOON,
                new SoortBerichten(SoortBericht.LVG_SYN_GEEF_SYNCHRONISATIE_PERSOON, SoortBericht.LVG_SYN_GEEF_SYNCHRONISATIE_PERSOON_R));
        SYNCHRONISATIE_SOORT_DIENST_BERICHT_MAP.put(SoortDienst.SYNCHRONISATIE_STAMGEGEVEN,
                new SoortBerichten(SoortBericht.LVG_SYN_GEEF_SYNCHRONISATIE_STAMGEGEVEN, SoortBericht.LVG_SYN_GEEF_SYNCHRONISATIE_STAMGEGEVEN_R));

        AFNEMERINDICATIES_SOORT_DIENST_BERICHT_MAP.put(SoortDienst.PLAATSING_AFNEMERINDICATIE,
                new SoortBerichten(SoortBericht.LVG_SYN_REGISTREER_AFNEMERINDICATIE, SoortBericht.LVG_SYN_REGISTREER_AFNEMERINDICATIE_R));
        AFNEMERINDICATIES_SOORT_DIENST_BERICHT_MAP.put(SoortDienst.VERWIJDERING_AFNEMERINDICATIE,
                new SoortBerichten(SoortBericht.LVG_SYN_REGISTREER_AFNEMERINDICATIE, SoortBericht.LVG_SYN_REGISTREER_AFNEMERINDICATIE_R));

        STUF_SOORT_DIENST_BERICHT_MAP.put(SoortDienst.GEEF_STUF_BG_BERICHT,
                new SoortBerichten(SoortBericht.STV_STV_GEEF_STUFBG_BERICHT, SoortBericht.STV_STV_GEEF_STUFBG_BERICHT_R));


        SOORT_DIENST_BERICHT_MAP.putAll(BEVRAGING_SOORT_DIENST_BERICHT_MAP);
        SOORT_DIENST_BERICHT_MAP.putAll(SYNCHRONISATIE_SOORT_DIENST_BERICHT_MAP);
        SOORT_DIENST_BERICHT_MAP.putAll(AFNEMERINDICATIES_SOORT_DIENST_BERICHT_MAP);
        SOORT_DIENST_BERICHT_MAP.putAll(STUF_SOORT_DIENST_BERICHT_MAP);
    }

    private Mappings() {

    }

    /**
     * Bepaal de {@link SoortDienst} op basis van het request element.
     * @param requestElement het request element
     * @return de {@link SoortDienst} als gevonden, anders null
     */
    public static Set<SoortDienst> soortDienst(final String requestElement) {
        final EnumSet<SoortDienst> soortDienstSet = EnumSet.noneOf(SoortDienst.class);
        for (final Map.Entry<SoortDienst, SoortBerichten> entries : SOORT_DIENST_BERICHT_MAP.entrySet()) {
            if (requestElement.equals(entries.getValue().getRequestElement())) {
                soortDienstSet.add(entries.getKey());
            }
        }
        return soortDienstSet;
    }

    /**
     * Retourneer de {@link SoortBerichten} van een {@link SoortDienst} binnen Bevraging.
     * @param soortDienst de {@link SoortDienst}
     * @return de {@link SoortBerichten}
     */
    public static SoortBerichten getBevragingSoortBerichten(final SoortDienst soortDienst) {
            return BEVRAGING_SOORT_DIENST_BERICHT_MAP.get(soortDienst);
    }

    /**
     * Retourneert of de {@link SoortDienst} een Bevraging dienst is.
     * @param soortDienst de {@link SoortDienst}
     * @return {@code true} als de {@link SoortDienst} een Bevraging dienst is, anders {@code false}
     */
    public static boolean isBevragingSoortDienst(final SoortDienst soortDienst) {
            return BEVRAGING_SOORT_DIENST_BERICHT_MAP.containsKey(soortDienst);
    }

    /**
     * Wrapper voor inkomend en uitgaand {@link SoortBericht}.
     */
    public static final class SoortBerichten {
        private SoortBericht inkomendBericht;
        private SoortBericht uitgaandBericht;

        private SoortBerichten(final SoortBericht inkomendBericht, final SoortBericht uitgaandBericht) {
            this.inkomendBericht = inkomendBericht;
            this.uitgaandBericht = uitgaandBericht;
        }

        public String getRequestElement() {
            return inkomendBericht.getIdentifier();
        }

        public SoortBericht getInkomendBericht() {
            return inkomendBericht;
        }

        public String getResponseElement() {
            return uitgaandBericht.getIdentifier();
        }

        public SoortBericht getUitgaandBericht() {
            return uitgaandBericht;
        }
    }
}
