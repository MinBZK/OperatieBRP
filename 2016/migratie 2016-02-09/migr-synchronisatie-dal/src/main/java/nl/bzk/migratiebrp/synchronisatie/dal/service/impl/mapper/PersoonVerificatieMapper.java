/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVerificatieInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Element;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonVerificatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonVerificatieHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.DynamischeStamtabelRepository;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.AbstractHistorieMapperStrategie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BRPActieFactory;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.MapperUtil;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.OnderzoekMapper;

/**
 * Mapper waarmee een {@link nl.bzk.migratiebrp.conversie.model.brp.BrpStapel <BrpVerificatieInhoud>} gemapt kan worden
 * op een verzameling van
 * {@link nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonVerificatieHistorie}.
 */
public final class PersoonVerificatieMapper extends
        AbstractHistorieMapperStrategie<BrpVerificatieInhoud, PersoonVerificatieHistorie, PersoonVerificatie>
{

    /**
     * Maakt een PersoonVerificatieMapper object.
     * 
     * @param dynamischeStamtabelRepository
     *            de repository die bevraging van de stamtabellen mogelijk maakt
     * @param brpActieFactory
     *            de factory die gebruikt wordt voor het mappen van BRP acties
     * @param onderzoekMapper
     *            de mapper voor onderzoeken
     */
    public PersoonVerificatieMapper(
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
    protected void mapActueleGegevens(final BrpStapel<BrpVerificatieInhoud> brpStapel, final PersoonVerificatie entiteit) {
        final BrpVerificatieInhoud actueleInhoud = brpStapel.getActueel().getInhoud();
        entiteit.setPartij(getStamtabelMapping().findPartijByCode(actueleInhoud.getPartij()));
        entiteit.setSoortVerificatie(BrpString.unwrap(actueleInhoud.getSoort()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void voegHistorieToeAanEntiteit(final PersoonVerificatieHistorie historie, final PersoonVerificatie persoonVerificatie) {
        persoonVerificatie.addPersoonVerificatieHistorie(historie);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void kopieerActueleGroepNaarEntiteit(final PersoonVerificatieHistorie historie, final PersoonVerificatie persoonVerificatie) {
        persoonVerificatie.setDatum(historie.getDatum());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PersoonVerificatieHistorie mapHistorischeGroep(final BrpVerificatieInhoud groepInhoud, final PersoonVerificatie entiteit) {
        final PersoonVerificatieHistorie result =
                new PersoonVerificatieHistorie(entiteit, MapperUtil.mapBrpDatumToInteger(groepInhoud.getDatum()));

        getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getDatum(), Element.PERSOON_VERIFICATIE_DATUM);

        getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getDatum(), Element.PERSOON_VERIFICATIE_DATUM);
        getOnderzoekMapper().mapOnderzoek(entiteit, groepInhoud.getSoort(), Element.PERSOON_VERIFICATIE_SOORT);
        getOnderzoekMapper().mapOnderzoek(entiteit, groepInhoud.getPartij(), Element.PERSOON_VERIFICATIE_PARTIJCODE);

        return result;
    }
}
