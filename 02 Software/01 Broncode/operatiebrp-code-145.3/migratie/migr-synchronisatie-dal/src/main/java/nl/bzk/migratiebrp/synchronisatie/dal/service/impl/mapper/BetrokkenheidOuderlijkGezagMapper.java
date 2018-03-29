/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BetrokkenheidOuderlijkGezagHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.repositories.DynamischeStamtabelRepository;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOuderlijkGezagInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.AbstractHistorieMapperStrategie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BRPActieFactory;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.OnderzoekMapper;

/**
 * Mapt de BrpOuderlijkGezag stapel uit het migratie model op de BetrokkenheidOuderlijkGezag historie in het
 * operationele BRP model.
 */
public final class BetrokkenheidOuderlijkGezagMapper extends
        AbstractHistorieMapperStrategie<BrpOuderlijkGezagInhoud, BetrokkenheidOuderlijkGezagHistorie, Betrokkenheid> {

    /**
     * Maakt een BetrokkenheidOuderlijkGezagMapper object.
     * @param dynamischeStamtabelRepository de repository die bevraging van de stamtabellen mogelijk maakt
     * @param brpActieFactory de factory die gebruikt wordt voor het mappen van BRP acties
     * @param onderzoekMapper de mapper voor onderzoeken
     */
    public BetrokkenheidOuderlijkGezagMapper(
            final DynamischeStamtabelRepository dynamischeStamtabelRepository,
            final BRPActieFactory brpActieFactory,
            final OnderzoekMapper onderzoekMapper) {
        super(dynamischeStamtabelRepository, brpActieFactory, onderzoekMapper);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void voegHistorieToeAanEntiteit(final BetrokkenheidOuderlijkGezagHistorie historie, final Betrokkenheid entiteit) {
        entiteit.addBetrokkenheidOuderlijkGezagHistorie(historie);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected BetrokkenheidOuderlijkGezagHistorie mapHistorischeGroep(final BrpOuderlijkGezagInhoud groepInhoud, final Betrokkenheid entiteit) {
        final BetrokkenheidOuderlijkGezagHistorie result =
                new BetrokkenheidOuderlijkGezagHistorie(entiteit, BrpBoolean.unwrap(groepInhoud.getOuderHeeftGezag()));

        getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getOuderHeeftGezag(), Element.GERELATEERDEOUDER_OUDERLIJKGEZAG_INDICATIEOUDERHEEFTGEZAG);

        return result;
    }

}
