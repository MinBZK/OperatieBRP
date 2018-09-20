/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.bepalers.impl;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.hisvolledig.kern.BetrokkenheidHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.ErkennerHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.ErkenningOngeborenVruchtHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.FamilierechtelijkeBetrekkingHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.GeregistreerdPartnerschapHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.HuwelijkHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.InstemmerHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.KindHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.NaamgeverHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.NaamskeuzeOngeborenVruchtHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.OuderHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PartnerHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.RelatieHisVolledig;
import org.springframework.stereotype.Component;

/**
 * Utility class die bepaalt welk type ElementEnum bij een bepaalde class hoort.
 */
@Component
public class ElementEnumBepaler {

    /**
     * Bepaal betrokken persoon object type.
     *
     * @param betrokkenheid the betrokkenheid
     * @return element enum
     */
    public final ElementEnum bepaalBetrokkenPersoonObjectType(final BetrokkenheidHisVolledig betrokkenheid) {
        ElementEnum betrokkenheidEnum = null;
        if (betrokkenheid instanceof OuderHisVolledig) {
            betrokkenheidEnum = ElementEnum.GERELATEERDEOUDER_PERSOON;
        } else if (betrokkenheid instanceof KindHisVolledig) {
            betrokkenheidEnum = ElementEnum.GERELATEERDEKIND_PERSOON;
        } else if (betrokkenheid instanceof ErkennerHisVolledig) {
            betrokkenheidEnum = ElementEnum.GERELATEERDEERKENNER_PERSOON;
        } else if (betrokkenheid instanceof InstemmerHisVolledig) {
            betrokkenheidEnum = ElementEnum.GERELATEERDEINSTEMMER_PERSOON;
        } else if (betrokkenheid instanceof NaamgeverHisVolledig) {
            betrokkenheidEnum = ElementEnum.GERELATEERDENAAMGEVER_PERSOON;
        } else if (betrokkenheid instanceof PartnerHisVolledig) {
            if (betrokkenheid.getRelatie() instanceof HuwelijkHisVolledig) {
                betrokkenheidEnum = ElementEnum.GERELATEERDEHUWELIJKSPARTNER_PERSOON;
            } else if (betrokkenheid.getRelatie() instanceof GeregistreerdPartnerschapHisVolledig) {
                betrokkenheidEnum = ElementEnum.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON;
            }
        }
        return betrokkenheidEnum;
    }

    /**
     * @param betrokkenheid BetrokkenheidHisVolledig
     * @return het elementEnum voor de gegeven betrokkenheid
     */
    public final ElementEnum bepaalBetrokkenBetrokkenheidObjectType(final BetrokkenheidHisVolledig betrokkenheid) {
        ElementEnum betrokkenheidEnum = null;
        if (betrokkenheid instanceof OuderHisVolledig) {
            betrokkenheidEnum = ElementEnum.GERELATEERDEOUDER;
        } else if (betrokkenheid instanceof KindHisVolledig) {
            betrokkenheidEnum = ElementEnum.GERELATEERDEKIND;
        } else if (betrokkenheid instanceof ErkennerHisVolledig) {
            betrokkenheidEnum = ElementEnum.GERELATEERDEERKENNER;
        } else if (betrokkenheid instanceof InstemmerHisVolledig) {
            betrokkenheidEnum = ElementEnum.GERELATEERDEINSTEMMER;
        } else if (betrokkenheid instanceof NaamgeverHisVolledig) {
            betrokkenheidEnum = ElementEnum.GERELATEERDENAAMGEVER;
        } else if (betrokkenheid instanceof PartnerHisVolledig) {
            if (betrokkenheid.getRelatie() instanceof HuwelijkHisVolledig) {
                betrokkenheidEnum = ElementEnum.GERELATEERDEHUWELIJKSPARTNER;
            } else if (betrokkenheid.getRelatie() instanceof GeregistreerdPartnerschapHisVolledig) {
                betrokkenheidEnum = ElementEnum.GERELATEERDEGEREGISTREERDEPARTNER;
            }
        }
        return betrokkenheidEnum;
    }

    /**
     * @param relatie RelatieHisVolledig
     * @return het elementEnum voor de gegeven relatie
     */
    public final ElementEnum bepaalRelatieObjectType(final RelatieHisVolledig relatie) {
        ElementEnum relatieEnum = null;
        if (relatie instanceof HuwelijkHisVolledig) {
            relatieEnum = ElementEnum.HUWELIJK;
        } else if (relatie instanceof GeregistreerdPartnerschapHisVolledig) {
            relatieEnum = ElementEnum.HUWELIJKGEREGISTREERDPARTNERSCHAP;
        } else if (relatie instanceof ErkenningOngeborenVruchtHisVolledig) {
            relatieEnum = ElementEnum.ERKENNINGONGEBORENVRUCHT;
        } else if (relatie instanceof NaamskeuzeOngeborenVruchtHisVolledig) {
            relatieEnum = ElementEnum.NAAMSKEUZEONGEBORENVRUCHT;
        } else if (relatie instanceof FamilierechtelijkeBetrekkingHisVolledig) {
            relatieEnum = ElementEnum.FAMILIERECHTELIJKEBETREKKING;
        }
        return relatieEnum;
    }
}
