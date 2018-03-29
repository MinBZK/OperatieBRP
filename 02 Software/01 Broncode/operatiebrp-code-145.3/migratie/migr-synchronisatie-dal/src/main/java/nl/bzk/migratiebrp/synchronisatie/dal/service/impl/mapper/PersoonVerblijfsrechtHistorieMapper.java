/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVerblijfsrechtHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.repositories.DynamischeStamtabelRepository;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVerblijfsrechtInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.AbstractPersoonHistorieMapperStrategie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BRPActieFactory;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.MapperUtil;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.OnderzoekMapper;

/**
 * Mapper waarmee een {@link nl.bzk.migratiebrp.conversie.model.brp.BrpStapel
 * <nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVerblijfsrechtInhoud>} gemapt kan worden op een verzameling van
 * {@link nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonVerblijfsrechtHistorie} en vice versa.
 */
public final class PersoonVerblijfsrechtHistorieMapper extends
        AbstractPersoonHistorieMapperStrategie<BrpVerblijfsrechtInhoud, PersoonVerblijfsrechtHistorie> {

    /**
     * Maakt een PersoonVerblijfsrechtHistorieMapper object.
     * @param dynamischeStamtabelRepository de repository die bevraging van de stamtabellen mogelijk maakt
     * @param brpActieFactory de factory die gebruikt wordt voor het mappen van BRP acties
     * @param onderzoekMapper de mapper voor onderzoeken
     */
    public PersoonVerblijfsrechtHistorieMapper(
            final DynamischeStamtabelRepository dynamischeStamtabelRepository,
            final BRPActieFactory brpActieFactory,
            final OnderzoekMapper onderzoekMapper) {
        super(dynamischeStamtabelRepository, brpActieFactory, onderzoekMapper);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void voegHistorieToeAanEntiteit(final PersoonVerblijfsrechtHistorie historie, final Persoon persoon) {
        persoon.addPersoonVerblijfsrechtHistorie(historie);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PersoonVerblijfsrechtHistorie mapHistorischeGroep(final BrpVerblijfsrechtInhoud groepInhoud, final Persoon persoon) {
        final PersoonVerblijfsrechtHistorie result;
        result =
                new PersoonVerblijfsrechtHistorie(
                        persoon,
                        getStamtabelMapping().findVerblijfsrechtByCode(groepInhoud.getAanduidingVerblijfsrechtCode()),
                        MapperUtil.mapBrpDatumToInteger(groepInhoud.getDatumAanvangVerblijfstitel()),
                        MapperUtil.mapBrpDatumToInteger(groepInhoud.getDatumMededelingVerblijfsrecht()));
        result.setDatumVoorzienEindeVerblijfsrecht(MapperUtil.mapBrpDatumToInteger(groepInhoud.getDatumVoorzienEindeVerblijfsrecht()));

        getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getAanduidingVerblijfsrechtCode(), Element.PERSOON_VERBLIJFSRECHT_AANDUIDINGCODE);
        getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getDatumMededelingVerblijfsrecht(), Element.PERSOON_VERBLIJFSRECHT_DATUMMEDEDELING);
        getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getDatumVoorzienEindeVerblijfsrecht(), Element.PERSOON_VERBLIJFSRECHT_DATUMVOORZIENEINDE);
        getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getDatumAanvangVerblijfstitel(), Element.PERSOON_VERBLIJFSRECHT_DATUMAANVANG);

        return result;
    }
}
