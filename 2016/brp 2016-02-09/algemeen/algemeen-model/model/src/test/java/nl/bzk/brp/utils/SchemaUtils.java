/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.utils;

/**
 * Centraliseren waar we de schema's kunnen vinden; dit moet in de toekomst gelezen kunnen worden vanuit een bestand.
 */
public class SchemaUtils {

    /**
     * XSD_BIJHOUDING_BEVRAGING_BERICHTEN.
     */
    private static final String XSD_BIJHOUDING_BEVRAGING_BERICHTEN = "brp0200_bhgBevraging_Berichten.xsd";
    /**
     * XSD_LEVERING_BEVRAGING_BERICHTEN.
     */
    private static final String XSD_LEVERING_BEVRAGING_BERICHTEN   = "brp0200_lvgBevraging_Berichten.xsd";
    /**
     * XSD_HuwelijkGeregistreerdPartnerschap_BERICHTE.
     */
    private static final String XSD_HGP_BERICHTEN                  =
            "brp0200_bhgHuwelijkGeregistreerdPartnerschap_Berichten.xsd";
    /**
     * XSD_AFSTAMMING_BERICHTEN.
     */
    private static final String XSD_AFSTAMMING_BERICHTEN           = "brp0200_bhgAfstamming_Berichten.xsd";
    /**
     * XSD_VERBLIJF_ADRES_BERICHTEN.
     */
    private static final String XSD_VERBLIJF_ADRES_BERICHTEN       = "brp0200_bhgVerblijfAdres_Berichten.xsd";
    /**
     * XSD_BHG_FIATTERING_BERICHTEN.
     */
    private static final String XSD_BHG_FIATTERING_BERICHTEN       = "brp0200_bhgFiattering_Berichten.xsd";

    public String getXsdBijhoudingBevragingBerichten() {
        return XSD_BIJHOUDING_BEVRAGING_BERICHTEN;
    }

    public String getXsdLeveringBevragingBerichten() {
        return XSD_LEVERING_BEVRAGING_BERICHTEN;
    }

    public String getXsdHuwelijkPartnerschapBerichten() {
        return XSD_HGP_BERICHTEN;
    }

    public String getXsdAfstammingBerichten() {
        return XSD_AFSTAMMING_BERICHTEN;
    }

    public String getXsdVerblijfAdresBerichten() {
        return XSD_VERBLIJF_ADRES_BERICHTEN;
    }

    public String getXsdNotificatieBerichten() {
        return XSD_BHG_FIATTERING_BERICHTEN;
    }
}
