/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpStapel;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpOuderInhoud;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Betrokkenheid;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.BetrokkenheidOuderHistorie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.HistorieStatus;
import nl.moderniseringgba.migratie.synchronisatie.repository.DynamischeStamtabelRepository;
import nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper.strategie.AbstractHistorieMapperStrategie;
import nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper.strategie.BRPActieFactory;

/**
 * Mapt de BrpOuder stapel uit het migratie model op de BetrokkenheidOuder historie in het operationele BRP model.
 * 
 */
public final class BetrokkenheidOuderMapper extends
        AbstractHistorieMapperStrategie<BrpOuderInhoud, BetrokkenheidOuderHistorie, Betrokkenheid> {

    /**
     * Maakt een BetrokkenheidOuderMapper object.
     * 
     * @param dynamischeStamtabelRepository
     *            de repository die bevraging van de stamtabellen mogelijk maakt
     * @param brpActieFactory
     *            de factory die gebruikt wordt voor het mappen van BRP acties
     */
    public BetrokkenheidOuderMapper(
            final DynamischeStamtabelRepository dynamischeStamtabelRepository,
            final BRPActieFactory brpActieFactory) {
        super(dynamischeStamtabelRepository, brpActieFactory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void mapActueleGegevens(final BrpStapel<BrpOuderInhoud> brpStapel, final Betrokkenheid entiteit) {
        entiteit.setOuderStatusHistorie(HistorieStatus.bepaalHistorieStatus(brpStapel));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void
            voegHistorieToeAanEntiteit(final BetrokkenheidOuderHistorie historie, final Betrokkenheid entiteit) {
        entiteit.addBetrokkenheidOuderHistorie(historie);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void kopieerActueleGroepNaarEntiteit(
            final BetrokkenheidOuderHistorie historie,
            final Betrokkenheid entiteit) {
        entiteit.setIndicatieOuder(historie.getIndicatieOuder());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected BetrokkenheidOuderHistorie mapHistorischeGroep(final BrpOuderInhoud groepInhoud) {
        final BetrokkenheidOuderHistorie result = new BetrokkenheidOuderHistorie();
        result.setIndicatieOuder(groepInhoud.getHeeftIndicatie());
        return result;
    }
}
