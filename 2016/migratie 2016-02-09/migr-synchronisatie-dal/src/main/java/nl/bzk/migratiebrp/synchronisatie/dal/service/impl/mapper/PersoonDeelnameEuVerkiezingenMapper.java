/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpBoolean;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpDeelnameEuVerkiezingenInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Element;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonDeelnameEuVerkiezingenHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.DynamischeStamtabelRepository;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.AbstractPersoonHistorieMapperStrategie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BRPActieFactory;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.MapperUtil;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.OnderzoekMapper;

/**
 * Mapper waarmee een {@link nl.bzk.migratiebrp.conversie.model.brp.BrpStapel <BrpDeelnameEuVerkiezingenInhoud>} gemapt
 * kan worden op een verzameling van
 * {@link nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonDeelnameEuVerkiezingenHistorie} en vice
 * versa.
 */
public final class PersoonDeelnameEuVerkiezingenMapper extends
        AbstractPersoonHistorieMapperStrategie<BrpDeelnameEuVerkiezingenInhoud, PersoonDeelnameEuVerkiezingenHistorie>
{

    /**
     * Maakt een PersoonDeelnameEuVerkiezingenMapper object.
     * 
     * @param dynamischeStamtabelRepository
     *            de repository die bevraging van de stamtabellen mogelijk maakt
     * @param brpActieFactory
     *            de factory die gebruikt wordt voor het mappen van BRP acties
     * @param onderzoekMapper
     *            de mapper voor onderzoeken
     */
    public PersoonDeelnameEuVerkiezingenMapper(
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
    protected void voegHistorieToeAanEntiteit(final PersoonDeelnameEuVerkiezingenHistorie historie, final Persoon persoon) {
        persoon.addPersoonDeelnameEuVerkiezingenHistorie(historie);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void kopieerActueleGroepNaarEntiteit(final PersoonDeelnameEuVerkiezingenHistorie historie, final Persoon persoon) {
        persoon.setIndicatieDeelnameEuVerkiezingen(historie.getIndicatieDeelnameEuVerkiezingen());
        persoon.setDatumAanleidingAanpassingDeelnameEuVerkiezingen(historie.getDatumAanleidingAanpassingDeelnameEuVerkiezingen());
        persoon.setDatumVoorzienEindeUitsluitingEuVerkiezingen(historie.getDatumVoorzienEindeUitsluitingEuVerkiezingen());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PersoonDeelnameEuVerkiezingenHistorie mapHistorischeGroep(final BrpDeelnameEuVerkiezingenInhoud groepInhoud, final Persoon persoon) {
        final PersoonDeelnameEuVerkiezingenHistorie result;
        result = new PersoonDeelnameEuVerkiezingenHistorie(persoon, BrpBoolean.unwrap(groepInhoud.getIndicatieDeelnameEuVerkiezingen()));

        final Integer datumAanleidingAanpassing = MapperUtil.mapBrpDatumToInteger(groepInhoud.getDatumAanleidingAanpassingDeelnameEuVerkiezingen());
        result.setDatumAanleidingAanpassingDeelnameEuVerkiezingen(datumAanleidingAanpassing);
        final Integer datumVoorzienEinde = MapperUtil.mapBrpDatumToInteger(groepInhoud.getDatumVoorzienEindeUitsluitingEuVerkiezingen());
        result.setDatumVoorzienEindeUitsluitingEuVerkiezingen(datumVoorzienEinde);

        // onderzoek
        getOnderzoekMapper().mapOnderzoek(
            result,
            groepInhoud.getIndicatieDeelnameEuVerkiezingen(),
            Element.PERSOON_DEELNAMEEUVERKIEZINGEN_INDICATIEDEELNAME);
        getOnderzoekMapper().mapOnderzoek(
            result,
            groepInhoud.getDatumAanleidingAanpassingDeelnameEuVerkiezingen(),
            Element.PERSOON_DEELNAMEEUVERKIEZINGEN_DATUMAANLEIDINGAANPASSING);
        getOnderzoekMapper().mapOnderzoek(
            result,
            groepInhoud.getDatumVoorzienEindeUitsluitingEuVerkiezingen(),
            Element.PERSOON_DEELNAMEEUVERKIEZINGEN_DATUMVOORZIENEINDEUITSLUITING);

        return result;
    }
}
