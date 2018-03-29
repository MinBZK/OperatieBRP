/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Relatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;

import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlElementInterface;

/**
 * Deze interface definieert de operaties op een relatie element uit het bijhoudingsbericht.
 */
@XmlElementInterface(RelatieRegister.class)
public interface RelatieElement extends BmrObjecttype, BmrEntiteit<BijhoudingRelatie> {

    /**
     * Geef de waarde van relatie.
     * @return relatie
     */
    RelatieGroepElement getRelatieGroep();

    /**
     * Geef de waarde van betrokkenheden.
     * @return betrokkenheden
     */
    List<BetrokkenheidElement> getBetrokkenheden();

    /**
     * Maakt een {@link Relatie} entiteit met bijbehorende betrokkenheden o.b.v. het bijhoudingsbericht.
     * @param actie de verantwoording
     * @param datumAanvangGeldigheid de datum aanvang geldigheid die gebruikt wordt voor de materiele groepen van de pseudo persoon (als deze gemaakt moet
     * worden)
     * @return de nieuwe relatie entiteit
     */
    BijhoudingRelatie maakRelatieEntiteitEnBetrokkenen(BRPActie actie, int datumAanvangGeldigheid);

    /**
     * Bepaal alle hoofdpersonen uit de actie.
     * @return List {@link BijhoudingPersoon}
     */
    @Bedrijfsregel(Regel.R1287)
    List<BijhoudingPersoon> getHoofdPersonen();

    /**
     * Geef alle persoon elementen uit de actie.
     * @return List {@link PersoonElement}
     */
    List<PersoonElement> getPersoonElementen();

    /**
     * Geeft de bijbehorende relatie entiteit.
     * @return de relatie entiteit
     */
    BijhoudingRelatie getRelatieEntiteit();

    /**
     * Geeft het soort relatie terug.
     * @return het soort relatie voor dit element.
     */
    SoortRelatie getSoortRelatie();
}
