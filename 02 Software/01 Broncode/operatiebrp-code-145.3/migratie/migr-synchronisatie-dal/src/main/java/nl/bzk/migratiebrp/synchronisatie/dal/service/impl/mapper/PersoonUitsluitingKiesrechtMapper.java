/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonUitsluitingKiesrechtHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.repositories.DynamischeStamtabelRepository;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpUitsluitingKiesrechtInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.AbstractPersoonHistorieMapperStrategie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BRPActieFactory;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.MapperUtil;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.OnderzoekMapper;

/**
 * Mapper waarmee een {@link nl.bzk.migratiebrp.conversie.model.brp.BrpStapel <BrpUitsluitingKiesrechtInhoud>} gemapt
 * kan worden op een verzameling van
 * {@link nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonUitsluitingKiesrechtHistorie} en vice
 * versa.
 */
public final class PersoonUitsluitingKiesrechtMapper extends
        AbstractPersoonHistorieMapperStrategie<BrpUitsluitingKiesrechtInhoud, PersoonUitsluitingKiesrechtHistorie> {

    /**
     * Maakt een PersoonUitsluitingKiesrechtMapper object.
     * @param dynamischeStamtabelRepository de repository die bevraging van de stamtabellen mogelijk maakt
     * @param brpActieFactory de factory die gebruikt wordt voor het mappen van BRP acties
     * @param onderzoekMapper de mapper voor onderzoeken
     */
    public PersoonUitsluitingKiesrechtMapper(
            final DynamischeStamtabelRepository dynamischeStamtabelRepository,
            final BRPActieFactory brpActieFactory,
            final OnderzoekMapper onderzoekMapper) {
        super(dynamischeStamtabelRepository, brpActieFactory, onderzoekMapper);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void voegHistorieToeAanEntiteit(final PersoonUitsluitingKiesrechtHistorie historie, final Persoon persoon) {
        persoon.addPersoonUitsluitingKiesrechtHistorie(historie);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PersoonUitsluitingKiesrechtHistorie mapHistorischeGroep(final BrpUitsluitingKiesrechtInhoud groepInhoud, final Persoon persoon) {
        final PersoonUitsluitingKiesrechtHistorie result;
        result = new PersoonUitsluitingKiesrechtHistorie(persoon, BrpBoolean.unwrap(groepInhoud.getIndicatieUitsluitingKiesrecht()));

        result.setDatumVoorzienEindeUitsluitingKiesrecht(MapperUtil.mapBrpDatumToInteger(groepInhoud.getDatumVoorzienEindeUitsluitingKiesrecht()));

        // onderzoek
        getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getIndicatieUitsluitingKiesrecht(), Element.PERSOON_UITSLUITINGKIESRECHT_INDICATIE);
        getOnderzoekMapper().mapOnderzoek(
                result,
                groepInhoud.getDatumVoorzienEindeUitsluitingKiesrecht(),
                Element.PERSOON_UITSLUITINGKIESRECHT_DATUMVOORZIENEINDE);
        return result;
    }
}
