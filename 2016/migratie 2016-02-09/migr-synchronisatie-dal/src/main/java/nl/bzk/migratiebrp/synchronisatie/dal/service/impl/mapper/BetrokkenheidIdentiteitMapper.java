/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentiteitInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Betrokkenheid;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.BetrokkenheidHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.DynamischeStamtabelRepository;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.AbstractHistorieMapperStrategie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BRPActieFactory;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.OnderzoekMapper;

/**
 * Mapt de Identiteit stapel uit het migratie model op de Betrokkenheid historie in het operationele BRP model.
 *
 */
public final class BetrokkenheidIdentiteitMapper extends AbstractHistorieMapperStrategie<BrpIdentiteitInhoud, BetrokkenheidHistorie, Betrokkenheid> {

    /**
     * Maakt een BetrokkenheidOuderlijkGezagMapper object.
     *
     * @param dynamischeStamtabelRepository
     *            de repository die bevraging van de stamtabellen mogelijk maakt
     * @param brpActieFactory
     *            de factory die gebruikt wordt voor het mappen van BRP acties
     * @param onderzoekMapper
     *            de mapper voor onderzoeken
     */
    public BetrokkenheidIdentiteitMapper(
        final DynamischeStamtabelRepository dynamischeStamtabelRepository,
        final BRPActieFactory brpActieFactory,
        final OnderzoekMapper onderzoekMapper)
    {
        super(dynamischeStamtabelRepository, brpActieFactory, onderzoekMapper);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void mapActueleGegevens(final BrpStapel<BrpIdentiteitInhoud> brpStapel, final Betrokkenheid entiteit) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void voegHistorieToeAanEntiteit(final BetrokkenheidHistorie historie, final Betrokkenheid entiteit) {
        entiteit.addBetrokkenheidHistorie(historie);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void kopieerActueleGroepNaarEntiteit(final BetrokkenheidHistorie historie, final Betrokkenheid entiteit) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected BetrokkenheidHistorie mapHistorischeGroep(final BrpIdentiteitInhoud groepInhoud, final Betrokkenheid entiteit) {
        return new BetrokkenheidHistorie(entiteit);
    }

}
