/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpStapel;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpOuderlijkGezagInhoud;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Betrokkenheid;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.BetrokkenheidOuderlijkGezagHistorie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.HistorieStatus;
import nl.moderniseringgba.migratie.synchronisatie.repository.DynamischeStamtabelRepository;
import nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper.strategie.AbstractHistorieMapperStrategie;
import nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper.strategie.BRPActieFactory;

/**
 * Mapt de BrpOuderlijkGezag stapel uit het migratie model op de BetrokkenheidOuderlijkGezag historie in het
 * operationele BRP model.
 * 
 */
public final class BetrokkenheidOuderlijkGezagMapper extends
        AbstractHistorieMapperStrategie<BrpOuderlijkGezagInhoud, BetrokkenheidOuderlijkGezagHistorie, Betrokkenheid> {

    /**
     * Maakt een BetrokkenheidOuderlijkGezagMapper object.
     * 
     * @param dynamischeStamtabelRepository
     *            de repository die bevraging van de stamtabellen mogelijk maakt
     * @param brpActieFactory
     *            de factory die gebruikt wordt voor het mappen van BRP acties
     */
    public BetrokkenheidOuderlijkGezagMapper(
            final DynamischeStamtabelRepository dynamischeStamtabelRepository,
            final BRPActieFactory brpActieFactory) {
        super(dynamischeStamtabelRepository, brpActieFactory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void
            mapActueleGegevens(final BrpStapel<BrpOuderlijkGezagInhoud> brpStapel, final Betrokkenheid entiteit) {
        entiteit.setOuderlijkGezagStatusHistorie(HistorieStatus.bepaalHistorieStatus(brpStapel));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void voegHistorieToeAanEntiteit(
            final BetrokkenheidOuderlijkGezagHistorie historie,
            final Betrokkenheid entiteit) {
        entiteit.addBetrokkenheidOuderlijkGezagHistorie(historie);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void kopieerActueleGroepNaarEntiteit(
            final BetrokkenheidOuderlijkGezagHistorie historie,
            final Betrokkenheid entiteit) {
        entiteit.setIndicatieOuderHeeftGezag(historie.getIndicatieOuderHeeftGezag());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected BetrokkenheidOuderlijkGezagHistorie mapHistorischeGroep(final BrpOuderlijkGezagInhoud groepInhoud) {
        final BetrokkenheidOuderlijkGezagHistorie result = new BetrokkenheidOuderlijkGezagHistorie();
        result.setIndicatieOuderHeeftGezag(groepInhoud.getOuderHeeftGezag());
        return result;
    }

}
