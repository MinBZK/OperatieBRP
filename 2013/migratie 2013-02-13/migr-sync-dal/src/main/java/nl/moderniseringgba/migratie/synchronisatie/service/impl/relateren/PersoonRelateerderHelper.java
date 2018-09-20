/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.service.impl.relateren;

import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Persoon;

/**
 * Deze interface definieert extra functionaliteit ter ondersteuning van de PersoonRelateerder.
 * 
 * @see PersoonRelateerder
 */
public interface PersoonRelateerderHelper {

    /**
     * Vergelijkt de betrokkenheden en relaties - voor huwelijk of geregistreerd partnerschap - van de geconverteerde
     * persoon met de bestaande persoon en merged de verschillen in de geconverteerde persoon. I.h.g.v. een match in de
     * combinatie betrokkenheid-relatie-betrokkenheid-persoon, dan wordt deze combinatie van de bestaande overgenomen in
     * de geconverteerde persoon. De bestaande persoon wordt ook gewijzigd in het geval dat een betrokkenheid van de
     * bestaande relatie gelijk is aan de huidige persoon. In dat geval gaat de betrokkenheid over naar deze persoon en
     * wordt de betrokkenheid verwijderd op de bestaande persoon.
     * 
     * @param geconverteerdePersoon
     *            de geconverteerde persoon
     * @param bestaandPersoon
     *            de bestaande (in de BRP) persoon waarmee vergeleken wordt, mag niet null zijn
     * @throws IllegalArgumentException
     *             als de bestaande persoon een ander a-nummer heeft dan de geconverteerde persoon of één van beide geen
     *             a-nummer heeft
     * @throws NullPointerException
     *             als geconverteerdePersoon of bestaandPersoon null is
     */
    void mergeRelatiesVoorHuwelijkOfGp(final Persoon geconverteerdePersoon, final Persoon bestaandPersoon);

    /**
     * Vergelijkt de betrokkenheden en relaties - voor ouder - van de geconverteerde persoon met de bestaande persoon en
     * merged de verschillen in de geconverteerde persoon. I.h.g.v. een match in de combinatie
     * betrokkenheid-relatie-betrokkenheid-persoon, dan wordt deze combinatie van de bestaande overgenomen in de
     * geconverteerde persoon. De bestaande persoon wordt ook gewijzigd in het geval dat een betrokkenheid van de
     * bestaande relatie gelijk is aan de huidige persoon. In dat geval gaat de betrokkenheid over naar deze persoon en
     * wordt de betrokkenheid verwijderd op de bestaande persoon.
     * 
     * @param geconverteerdePersoon
     *            de geconverteerde persoon
     * @param bestaandPersoon
     *            de bestaande (in de BRP) persoon waarmee vergeleken wordt, mag niet null zijn
     * @throws IllegalArgumentException
     *             als de bestaande persoon een ander a-nummer heeft dan de geconverteerde persoon of één van beide geen
     *             a-nummer heeft
     * @throws NullPointerException
     *             als geconverteerdePersoon of bestaandPersoon null is
     */
    void mergeRelatiesVoorOuder(Persoon geconverteerdePersoon, Persoon bestaandPersoon);

    /**
     * Vergelijkt de betrokkenheden en relaties - voor kind - van de geconverteerde persoon met de bestaande persoon en
     * merged de verschillen in de geconverteerde persoon. I.h.g.v. een match in de combinatie
     * betrokkenheid-relatie-betrokkenheid-persoon, dan wordt deze combinatie van de bestaande overgenomen in de
     * geconverteerde persoon. De bestaande persoon wordt ook gewijzigd in het geval dat een betrokkenheid van de
     * bestaande relatie gelijk is aan de huidige persoon. In dat geval gaat de betrokkenheid over naar deze persoon en
     * wordt de betrokkenheid verwijderd op de bestaande persoon.
     * 
     * @param geconverteerdePersoon
     *            de geconverteerde persoon
     * @param bestaandPersoon
     *            de bestaande (in de BRP) persoon waarmee vergeleken wordt, mag niet null zijn
     * @throws IllegalArgumentException
     *             als de bestaande persoon een ander a-nummer heeft dan de geconverteerde persoon of één van beide geen
     *             a-nummer heeft
     * @throws NullPointerException
     *             als geconverteerdePersoon of bestaandPersoon null is
     */
    void mergeRelatiesVoorKind(Persoon geconverteerdePersoon, Persoon bestaandPersoon);

    /**
     * Als de geconverteerde persoon ouder relaties heeft dan is het mogelijk dat er vanuit het kind (de gerelateerde)
     * meerdere kind relaties zijn ontstaan. Dit mag niet en deze moeten dus gemerged worden.
     * 
     * @param geconverteerdePersoon
     *            de (nieuwe) geconverteerde persoon
     */
    void mergeRelatiesVoorGerelateerdeKinderen(Persoon geconverteerdePersoon);
}
