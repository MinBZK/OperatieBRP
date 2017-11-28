/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.dataaccess.bevraging;

import java.util.EnumMap;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import nl.bzk.brp.domain.element.ObjectElement;

/**
 * JoinUtil.
 */
final class JoinUtil {

    private static final EnumMap<Element, SoortRelatie> RELATIE_MAP = new EnumMap<>(Element.class);
    private static final EnumMap<Element, SoortBetrokkenheid> GERELATEERDE_BETROKKENHEID_MAP = new EnumMap<>(Element.class);
    private static final EnumMap<Element, SoortBetrokkenheid> BETROKKENHEID_MAP = new EnumMap<>(Element.class);

    static {
        RELATIE_MAP.put(Element.HUWELIJK, SoortRelatie.HUWELIJK);
        RELATIE_MAP.put(Element.GEREGISTREERDPARTNERSCHAP, SoortRelatie.GEREGISTREERD_PARTNERSCHAP);
        RELATIE_MAP.put(Element.FAMILIERECHTELIJKEBETREKKING, SoortRelatie.FAMILIERECHTELIJKE_BETREKKING);

        GERELATEERDE_BETROKKENHEID_MAP.put(Element.GERELATEERDEKIND_PERSOON, SoortBetrokkenheid.KIND);
        GERELATEERDE_BETROKKENHEID_MAP.put(Element.GERELATEERDEKIND, SoortBetrokkenheid.KIND);
        GERELATEERDE_BETROKKENHEID_MAP.put(Element.GERELATEERDEOUDER_PERSOON, SoortBetrokkenheid.OUDER);
        GERELATEERDE_BETROKKENHEID_MAP.put(Element.GERELATEERDEOUDER, SoortBetrokkenheid.OUDER);
        GERELATEERDE_BETROKKENHEID_MAP.put(Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON, SoortBetrokkenheid.PARTNER);
        GERELATEERDE_BETROKKENHEID_MAP.put(Element.GERELATEERDEGEREGISTREERDEPARTNER, SoortBetrokkenheid.PARTNER);
        GERELATEERDE_BETROKKENHEID_MAP.put(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON, SoortBetrokkenheid.PARTNER);
        GERELATEERDE_BETROKKENHEID_MAP.put(Element.GERELATEERDEHUWELIJKSPARTNER, SoortBetrokkenheid.PARTNER);

        BETROKKENHEID_MAP.put(Element.PERSOON_KIND, SoortBetrokkenheid.KIND);
        BETROKKENHEID_MAP.put(Element.PERSOON_OUDER, SoortBetrokkenheid.OUDER);
        BETROKKENHEID_MAP.put(Element.PERSOON_PARTNER, SoortBetrokkenheid.PARTNER);
    }

    private JoinUtil() {
    }

    /**
     * @param objectElementVanAttribuut objectElementVanAttribuut
     * @return relatie soort
     */
    static SoortRelatie isRelatie(final ObjectElement objectElementVanAttribuut) {
        return RELATIE_MAP.get(objectElementVanAttribuut.getElement());
    }

    /**
     * @param objectElementVanAttribuut objectElementVanAttribuut
     * @return betrokkenheid soort
     */
    static SoortBetrokkenheid getGerelateerdeBetrokkenheid(final ObjectElement objectElementVanAttribuut) {
        return GERELATEERDE_BETROKKENHEID_MAP.get(objectElementVanAttribuut.getElement());
    }

    /**
     * @param objectElementVanAttribuut objectElementVanAttribuut
     * @return betrokkenheid soort
     */
    static SoortBetrokkenheid getBetrokkenheid(final ObjectElement objectElementVanAttribuut) {
        return BETROKKENHEID_MAP.get(objectElementVanAttribuut.getElement());
    }
}
