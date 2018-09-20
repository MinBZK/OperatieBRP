/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOverlijdenInhoud;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Element;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonOverlijdenHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.DynamischeStamtabelRepository;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.AbstractPersoonHistorieMapperStrategie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BRPActieFactory;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.MapperUtil;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.OnderzoekMapper;

/**
 * Mapper waarmee een {@link nl.bzk.migratiebrp.conversie.model.brp.BrpStapel <BrpOverlijdenInhoud>} gemapt kan worden
 * op een verzameling van {@link PersoonOverlijdenHistorie} en vice versa.
 */
public final class PersoonOverlijdenMapper extends AbstractPersoonHistorieMapperStrategie<BrpOverlijdenInhoud, PersoonOverlijdenHistorie> {

    /**
     * Maakt een PersoonOverlijdenMapper object.
     * 
     * @param dynamischeStamtabelRepository
     *            de repository die bevraging van de stamtabellen mogelijk maakt
     * @param brpActieFactory
     *            de factory die gebruikt wordt voor het mappen van BRP acties
     * @param onderzoekMapper
     *            de mapper voor onderzoeken
     */
    public PersoonOverlijdenMapper(
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
    protected void voegHistorieToeAanEntiteit(final PersoonOverlijdenHistorie historie, final Persoon persoon) {
        persoon.addPersoonOverlijdenHistorie(historie);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void kopieerActueleGroepNaarEntiteit(final PersoonOverlijdenHistorie historie, final Persoon persoon) {
        persoon.setWoonplaatsOverlijden(historie.getWoonplaatsnaamOverlijden());
        persoon.setLandOfGebiedOverlijden(historie.getLandOfGebied());
        persoon.setOmschrijvingLocatieOverlijden(historie.getOmschrijvingLocatieOverlijden());
        persoon.setDatumOverlijden(historie.getDatumOverlijden());
        persoon.setBuitenlandsePlaatsOverlijden(historie.getBuitenlandsePlaatsOverlijden());
        persoon.setBuitenlandseRegioOverlijden(historie.getBuitenlandseRegioOverlijden());
        persoon.setGemeenteOverlijden(historie.getGemeente());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PersoonOverlijdenHistorie mapHistorischeGroep(final BrpOverlijdenInhoud groepInhoud, final Persoon persoon) {
        final PersoonOverlijdenHistorie result =
                new PersoonOverlijdenHistorie(
                    persoon,
                    MapperUtil.mapBrpDatumToInteger(groepInhoud.getDatum()),
                    getStamtabelMapping().findLandOfGebiedByCode(groepInhoud.getLandOfGebiedCode(), SoortMeldingCode.PRE010));

        getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getDatum(), Element.PERSOON_OVERLIJDEN_DATUM);
        getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getLandOfGebiedCode(), Element.PERSOON_OVERLIJDEN_LANDGEBIEDCODE);

        result.setWoonplaatsnaamOverlijden(BrpString.unwrap(groepInhoud.getWoonplaatsnaamOverlijden()));
        getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getWoonplaatsnaamOverlijden(), Element.PERSOON_OVERLIJDEN_WOONPLAATSNAAM);
        result.setOmschrijvingLocatieOverlijden(BrpString.unwrap(groepInhoud.getOmschrijvingLocatie()));
        getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getOmschrijvingLocatie(), Element.PERSOON_OVERLIJDEN_OMSCHRIJVINGLOCATIE);
        result.setBuitenlandsePlaatsOverlijden(BrpString.unwrap(groepInhoud.getBuitenlandsePlaats()));
        getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getBuitenlandsePlaats(), Element.PERSOON_OVERLIJDEN_BUITENLANDSEPLAATS);
        result.setBuitenlandseRegioOverlijden(BrpString.unwrap(groepInhoud.getBuitenlandseRegio()));
        getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getBuitenlandseRegio(), Element.PERSOON_OVERLIJDEN_BUITENLANDSEREGIO);
        result.setGemeente(getStamtabelMapping().findGemeenteByCode(groepInhoud.getGemeenteCode(), SoortMeldingCode.PRE026));
        getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getGemeenteCode(), Element.PERSOON_OVERLIJDEN_GEMEENTECODE);
        return result;
    }
}
