/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonInschrijvingHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.repositories.DynamischeStamtabelRepository;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLong;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpInschrijvingInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.AbstractPersoonHistorieMapperStrategie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BRPActieFactory;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.MapperUtil;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.OnderzoekMapper;

/**
 * Mapper waarmee een {@link nl.bzk.migratiebrp.conversie.model.brp.BrpStapel <BrpInschrijvingInhoud>} gemapt kan worden
 * op een verzameling van {@link PersoonInschrijvingHistorie} en vice versa.
 */
public final class PersoonInschrijvingMapper extends AbstractPersoonHistorieMapperStrategie<BrpInschrijvingInhoud, PersoonInschrijvingHistorie> {

    /**
     * Maakt een PersoonInschrijvingMapper object.
     * @param dynamischeStamtabelRepository de repository die bevraging van de stamtabellen mogelijk maakt
     * @param brpActieFactory de factory die gebruikt wordt voor het mappen van BRP acties
     * @param onderzoekMapper de mapper voor onderzoeken
     */
    public PersoonInschrijvingMapper(
            final DynamischeStamtabelRepository dynamischeStamtabelRepository,
            final BRPActieFactory brpActieFactory,
            final OnderzoekMapper onderzoekMapper) {
        super(dynamischeStamtabelRepository, brpActieFactory, onderzoekMapper);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void voegHistorieToeAanEntiteit(final PersoonInschrijvingHistorie historie, final Persoon persoon) {
        persoon.addPersoonInschrijvingHistorie(historie);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PersoonInschrijvingHistorie mapHistorischeGroep(final BrpInschrijvingInhoud groepInhoud, final Persoon persoon) {
        // Datuminschrijving en versienummer zijn verplicht
        final PersoonInschrijvingHistorie result =
                new PersoonInschrijvingHistorie(
                        persoon,
                        MapperUtil.mapBrpDatumToInteger(groepInhoud.getDatumInschrijving()),
                        BrpLong.unwrap(groepInhoud.getVersienummer()),
                        MapperUtil.mapBrpDatumTijdToTimestamp(groepInhoud.getDatumtijdstempel()));
        getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getDatumInschrijving(), Element.PERSOON_INSCHRIJVING_DATUM);
        getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getDatumtijdstempel(), Element.PERSOON_INSCHRIJVING_DATUMTIJDSTEMPEL);
        getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getVersienummer(), Element.PERSOON_INSCHRIJVING_VERSIENUMMER);

        return result;
    }
}
