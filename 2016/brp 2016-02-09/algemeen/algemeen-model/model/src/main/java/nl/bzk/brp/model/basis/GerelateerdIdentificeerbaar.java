/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.basis;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.BetrokkenheidHisVolledigView;

/**
 * Interface die voor identificatiemogelijkheden zorgt voor de betrokken persoonviews bij een hoofdpersoonview. Zo wordt per soort betrokkenheid
 * in combinatie met de relatie duidelijk aangegeven welk element in de elementenum dit betreft. Aangezien het model genormaliseerd is, dient dit
 * in runtime te worden gedaan.
 *
 */
public interface GerelateerdIdentificeerbaar {

    /**
     * Geeft het gerelateerde object type.
     *
     * @return het gerelateerde object type als ElementEnum
     */
    ElementEnum getGerelateerdeObjectType();

    /**
     * Geeft de betrokkenheid van de betrokken persoon die dit voorkomen
     * bevat.
     *
     * @return de betrokkenheid view van de betrokken persoon
     */
    BetrokkenheidHisVolledigView getBetrokkenPersoonBetrokkenheidView();

    /**
     * Zet het gerelateerde object type.
     *
     * @param gerelateerdeObjectType het gerelateerde objecttype
     */
    void setGerelateerdeObjectType(final ElementEnum gerelateerdeObjectType);

    /**
     * Zet de betrokkenheid waarmee de link naar de relatie met hoofdpersoon wordt gelegd.
     * @param betrokkenPersoonBetrokkenheid the betrokkenheid van de betrokken persoon
     */
    void setBetrokkenPersoonBetrokkenheidView(BetrokkenheidHisVolledigView betrokkenPersoonBetrokkenheid);
}
