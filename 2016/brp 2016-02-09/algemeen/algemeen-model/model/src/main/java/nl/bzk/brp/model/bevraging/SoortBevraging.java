/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bevraging;

import java.util.Arrays;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.bericht.ber.BerichtBericht;

/**
 * Vertaling van {@link SoortBericht} naar het type bevraging.
 */
public enum SoortBevraging {

    /**
     * Geef details persoon.
     */
    GEEF_DETAILS_PERSOON(SoortBericht.BHG_BVG_GEEF_DETAILS_PERSOON, SoortBericht.LVG_BVG_GEEF_DETAILS_PERSOON),
    /**
     * Zoek persoon.
     */
    ZOEK_PERSOON(SoortBericht.BHG_BVG_ZOEK_PERSOON),
    /**
     * Geef personen op adres met betrokkenheden.
     */
    GEEF_PERSONEN_OP_ADRES_MET_BETROKKENHEDEN(SoortBericht.BHG_BVG_GEEF_PERSONEN_OP_ADRES_MET_BETROKKENHEDEN),
    /**
     * Bepaal kandidaat vader.
     */
    BEPAAL_KANDIDAAT_VADER(SoortBericht.BHG_BVG_BEPAAL_KANDIDAAT_VADER),

    /**
     * Zoek persoon op adres.
     */
    ZOEK_PERSOON_OP_ADRES(),
    /**
     * Geef medebewoners.
     */
    GEEF_MEDEBEWONERS(SoortBericht.LVG_BVG_GEEF_MEDEBEWONERS);

    private SoortBericht[] soortenBericht;

    private SoortBevraging(final SoortBericht... soortenBericht) {
        this.soortenBericht = soortenBericht;
    }

    /**
     * Geef de soorten berichten bij deze soort bevraging.
     * @return de lijst met bericht soorten
     */
    public SoortBericht[] getSoortenBericht() {
        return Arrays.copyOf(soortenBericht, soortenBericht.length);
    }

    /**
     * Geeft de juiste soort op basis van een {@link nl.bzk.brp.model.bericht.ber.BerichtBericht}.
     *
     * @param bericht het bericht waarvoor de soortbevraging wordt gezocht
     * @return de soortbevraging, of {@code null} als er geen bevraging voor het bericht is
     */
    public static SoortBevraging bepaalOpBasisVanBericht(final BerichtBericht bericht) {
        SoortBevraging soortBevraging = null;
        for (SoortBevraging eenSoortBevraging : SoortBevraging.values()) {
            for (SoortBericht eenSoortBericht : eenSoortBevraging.getSoortenBericht()) {
                if (eenSoortBericht.equals(bericht.getSoort().getWaarde())) {
                    soortBevraging = eenSoortBevraging;
                    break;
                }
            }
        }
        return soortBevraging;
    }

}
