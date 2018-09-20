/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaat;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortRelatie;

/**
 * Element identificatie utility klasse, geeft mogelijkheden voor het verbeteren/detailleren van de elementidentificatie objecten, wanneer deze
 * bijvoorbeeld ambigu zijn doordat de groep op een supertype aanwezg is.
 */
public final class ElementIdentificatieUtil {

    private ElementIdentificatieUtil() {
        // private constructor
    }

    /**
     * Geeft de element enum voor de relatie standaard groep.
     *
     * @param soortRelatie soort relatie
     * @return element enum
     */
    public static ElementEnum geefElementEnumVoorRelatieStandaardGroep(final SoortRelatie soortRelatie) {
        ElementEnum elementEnum = null;
        switch (soortRelatie) {
            case HUWELIJK:
                elementEnum = ElementEnum.HUWELIJK_STANDAARD;
                break;
            case GEREGISTREERD_PARTNERSCHAP:
                elementEnum = ElementEnum.GEREGISTREERDPARTNERSCHAP_STANDAARD;
                break;
            case FAMILIERECHTELIJKE_BETREKKING:
                elementEnum = ElementEnum.FAMILIERECHTELIJKEBETREKKING_STANDAARD;
                break;
            case NAAMSKEUZE_ONGEBOREN_VRUCHT:
                elementEnum = ElementEnum.NAAMSKEUZEONGEBORENVRUCHT_STANDAARD;
                break;
            case ERKENNING_ONGEBOREN_VRUCHT:
                elementEnum = ElementEnum.ERKENNINGONGEBORENVRUCHT_STANDAARD;
                break;
            default:
                break;
        }
        return elementEnum;
    }

    /**
     * Geeft de element enum voor de relatie identiteit groep.
     *
     * @param soortRelatie soort relatie
     * @return element enum
     */
    public static ElementEnum geefElementEnumVoorRelatieIdentiteitGroep(final SoortRelatie soortRelatie) {
        ElementEnum elementEnum = null;
        switch (soortRelatie) {
            case HUWELIJK:
                elementEnum = ElementEnum.HUWELIJK_IDENTITEIT;
                break;
            case GEREGISTREERD_PARTNERSCHAP:
                elementEnum = ElementEnum.GEREGISTREERDPARTNERSCHAP_IDENTITEIT;
                break;
            case FAMILIERECHTELIJKE_BETREKKING:
                elementEnum = ElementEnum.FAMILIERECHTELIJKEBETREKKING_IDENTITEIT;
                break;
            case NAAMSKEUZE_ONGEBOREN_VRUCHT:
                elementEnum = ElementEnum.NAAMSKEUZEONGEBORENVRUCHT_IDENTITEIT;
                break;
            case ERKENNING_ONGEBOREN_VRUCHT:
                elementEnum = ElementEnum.ERKENNINGONGEBORENVRUCHT_IDENTITEIT;
                break;
            default:
                break;
        }
        return elementEnum;
    }
}
