/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper;

import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpEuropeseVerkiezingenInhoud;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Persoon;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonEUVerkiezingenHistorie;
import nl.moderniseringgba.migratie.synchronisatie.repository.DynamischeStamtabelRepository;
import nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper.strategie.AbstractPersoonHistorieMapperStrategie;
import nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper.strategie.BRPActieFactory;
import nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper.strategie.MapperUtil;

/**
 * Mapper waarmee een {@link BrpStapel<BrpEuropeseVerkiezingenInhoud>} gemapt kan worden op een verzameling van
 * {@link PersoonEUVerkiezingenHistorie} en vice versa.
 */
public final class PersoonEUVerkiezingenMapper extends
        AbstractPersoonHistorieMapperStrategie<BrpEuropeseVerkiezingenInhoud, PersoonEUVerkiezingenHistorie> {

    /**
     * Maakt een PersoonEUVerkiezingenMapper object.
     * 
     * @param dynamischeStamtabelRepository
     *            de repository die bevraging van de stamtabellen mogelijk maakt
     * @param brpActieFactory
     *            de factory die gebruikt wordt voor het mappen van BRP acties
     */
    public PersoonEUVerkiezingenMapper(
            final DynamischeStamtabelRepository dynamischeStamtabelRepository,
            final BRPActieFactory brpActieFactory) {
        super(dynamischeStamtabelRepository, brpActieFactory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void voegHistorieToeAanEntiteit(final PersoonEUVerkiezingenHistorie historie, final Persoon persoon) {
        persoon.addPersoonEUVerkiezingenHistorie(historie);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void
            kopieerActueleGroepNaarEntiteit(final PersoonEUVerkiezingenHistorie historie, final Persoon persoon) {
        persoon.setIndicatieDeelnameEUVerkiezingen(historie.getIndicatieDeelnameEUVerkiezingen());
        persoon.setDatumAanleidingAanpassingDeelnameEUVerkiezing(historie
                .getDatumAanleidingAanpassingDeelnameEUVerkiezing());
        persoon.setDatumEindeUitsluitingEUKiesrecht(historie.getDatumEindeUitsluitingEUKiesrecht());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PersoonEUVerkiezingenHistorie mapHistorischeGroep(final BrpEuropeseVerkiezingenInhoud groepInhoud) {
        final PersoonEUVerkiezingenHistorie result = new PersoonEUVerkiezingenHistorie();

        result.setIndicatieDeelnameEUVerkiezingen(groepInhoud.getDeelnameEuropeseVerkiezingen());
        result.setDatumAanleidingAanpassingDeelnameEUVerkiezing(MapperUtil.mapBrpDatumToBigDecimal(groepInhoud
                .getDatumAanleidingAanpassingDeelnameEuropeseVerkiezingen()));
        result.setDatumEindeUitsluitingEUKiesrecht(MapperUtil.mapBrpDatumToBigDecimal(groepInhoud
                .getDatumEindeUitsluitingEuropeesKiesrecht()));

        return result;
    }
}
