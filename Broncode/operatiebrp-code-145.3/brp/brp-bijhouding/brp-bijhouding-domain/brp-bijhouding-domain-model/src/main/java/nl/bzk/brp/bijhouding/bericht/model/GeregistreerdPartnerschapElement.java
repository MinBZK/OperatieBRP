/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlElement;

import java.util.List;
import java.util.Map;

/**
 * Een geregistreerdPartnerschap uit het bijhoudingsbericht.
 */
//
@XmlElement("geregistreerdPartnerschap")
public final class GeregistreerdPartnerschapElement extends AbstractHuwelijkOfGpElement {

    /**
     * Maakt een BronElement object.
     *
     * @param basisAttribuutGroep de basis attribuutgroep, mag niet null zijn
     * @param relatieGroep        relatie, mag niet null zijn
     * @param betrokkenheden      betrokkenheden, mag niet null zijn
     */
    public GeregistreerdPartnerschapElement(
            final Map<String, String> basisAttribuutGroep,
            final RelatieGroepElement relatieGroep,
            final List<BetrokkenheidElement> betrokkenheden) {
        super(basisAttribuutGroep, betrokkenheden, relatieGroep);
    }

    @Override
    public SoortRelatie getSoortRelatie() {
        return SoortRelatie.GEREGISTREERD_PARTNERSCHAP;
    }
}
