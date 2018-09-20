/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper;

import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpUitsluitingNederlandsKiesrechtInhoud;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Persoon;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonUitsluitingNLKiesrechtHistorie;
import nl.moderniseringgba.migratie.synchronisatie.repository.DynamischeStamtabelRepository;
import nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper.strategie.AbstractPersoonHistorieMapperStrategie;
import nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper.strategie.BRPActieFactory;
import nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper.strategie.MapperUtil;

/**
 * Mapper waarmee een {@link BrpStapel<BrpUitsluitingNederlandsKiesrechtInhoud>} gemapt kan worden op een verzameling
 * van {@link PersoonUitsluitingNLKiesrechtHistorie} en vice versa.
 * 
 */
public final class PersoonUitsluitingNLKiesrechtMapper
        extends
        AbstractPersoonHistorieMapperStrategie<BrpUitsluitingNederlandsKiesrechtInhoud, PersoonUitsluitingNLKiesrechtHistorie> {

    /**
     * Maakt een PersoonUitsluitingNLKiesrechtMapper object.
     * 
     * @param dynamischeStamtabelRepository
     *            de repository die bevraging van de stamtabellen mogelijk maakt
     * @param brpActieFactory
     *            de factory die gebruikt wordt voor het mappen van BRP acties
     */
    public PersoonUitsluitingNLKiesrechtMapper(
            final DynamischeStamtabelRepository dynamischeStamtabelRepository,
            final BRPActieFactory brpActieFactory) {
        super(dynamischeStamtabelRepository, brpActieFactory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void voegHistorieToeAanEntiteit(
            final PersoonUitsluitingNLKiesrechtHistorie historie,
            final Persoon persoon) {
        persoon.addPersoonUitsluitingNLKiesrechtHistorie(historie);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void kopieerActueleGroepNaarEntiteit(
            final PersoonUitsluitingNLKiesrechtHistorie historie,
            final Persoon persoon) {
        persoon.setIndicatieUitsluitingNLKiesrecht(historie.getIndicatieUitsluitingNLKiesrecht());
        persoon.setDatumEindeUitsluitingNLKiesrecht(historie.getDatumEindeUitsluitingNLKiesrecht());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PersoonUitsluitingNLKiesrechtHistorie mapHistorischeGroep(
            final BrpUitsluitingNederlandsKiesrechtInhoud groepInhoud) {
        final PersoonUitsluitingNLKiesrechtHistorie result = new PersoonUitsluitingNLKiesrechtHistorie();

        result.setIndicatieUitsluitingNLKiesrecht(groepInhoud.getIndicatieUitsluitingNederlandsKiesrecht());
        result.setDatumEindeUitsluitingNLKiesrecht(MapperUtil.mapBrpDatumToBigDecimal(groepInhoud
                .getDatumEindeUitsluitingNederlandsKiesrecht()));
        return result;
    }
}
