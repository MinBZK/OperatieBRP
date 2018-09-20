/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper;

import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpOpschortingInhoud;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Persoon;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonOpschortingHistorie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.RedenOpschorting;
import nl.moderniseringgba.migratie.synchronisatie.repository.DynamischeStamtabelRepository;
import nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper.strategie.AbstractPersoonHistorieMapperStrategie;
import nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper.strategie.BRPActieFactory;

/**
 * Mapper waarmee een {@link BrpStapel<BrpOpschortingInhoud>} gemapt kan worden op een verzameling van
 * {@link PersoonOpschortingHistorie} en vice versa.
 */
public final class PersoonOpschortingMapper extends
        AbstractPersoonHistorieMapperStrategie<BrpOpschortingInhoud, PersoonOpschortingHistorie> {

    /**
     * Maakt een PersoonOpschortingMapper object.
     * 
     * @param dynamischeStamtabelRepository
     *            de repository die bevraging van de stamtabellen mogelijk maakt
     * @param brpActieFactory
     *            de factory die gebruikt wordt voor het mappen van BRP acties
     */
    public PersoonOpschortingMapper(
            final DynamischeStamtabelRepository dynamischeStamtabelRepository,
            final BRPActieFactory brpActieFactory) {
        super(dynamischeStamtabelRepository, brpActieFactory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void voegHistorieToeAanEntiteit(final PersoonOpschortingHistorie historie, final Persoon persoon) {
        persoon.addPersoonOpschortingHistorie(historie);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void kopieerActueleGroepNaarEntiteit(final PersoonOpschortingHistorie historie, final Persoon persoon) {
        persoon.setRedenOpschortingBijhouding(historie.getRedenOpschorting());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PersoonOpschortingHistorie mapHistorischeGroep(final BrpOpschortingInhoud groepInhoud) {
        final PersoonOpschortingHistorie result = new PersoonOpschortingHistorie();
        result.setRedenOpschorting(RedenOpschorting.parseCode(groepInhoud.getRedenOpschortingBijhoudingCode()
                .getCode()));
        return result;
    }
}
