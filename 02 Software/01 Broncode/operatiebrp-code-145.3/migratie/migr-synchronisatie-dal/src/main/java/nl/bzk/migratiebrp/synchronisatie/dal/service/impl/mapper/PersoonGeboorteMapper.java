/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeboorteHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.repositories.DynamischeStamtabelRepository;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.bzk.migratiebrp.conversie.model.melding.SoortMeldingCode;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.AbstractNietIngeschrevenPersoonHistorieMapperStrategie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BRPActieFactory;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.MapperUtil;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.OnderzoekMapper;

/**
 * Mapper waarmee een {@link nl.bzk.migratiebrp.conversie.model.brp.BrpStapel <BrpGeboorteInhoud>} gemapt kan worden op
 * een verzameling van {@link PersoonGeboorteHistorie} en vice versa.
 */
public final class PersoonGeboorteMapper extends AbstractNietIngeschrevenPersoonHistorieMapperStrategie<BrpGeboorteInhoud, PersoonGeboorteHistorie> {

    /**
     * Maakt een PersoonGeboorteMapper object.
     * @param dynamischeStamtabelRepository de repository die bevraging van de stamtabellen mogelijk maakt
     * @param brpActieFactory de factory die gebruikt wordt voor het mappen van BRP acties
     * @param onderzoekMapper de mapper voor onderzoeken
     */
    public PersoonGeboorteMapper(
            final DynamischeStamtabelRepository dynamischeStamtabelRepository,
            final BRPActieFactory brpActieFactory,
            final OnderzoekMapper onderzoekMapper) {
        super(dynamischeStamtabelRepository, brpActieFactory, onderzoekMapper);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void voegHistorieToeAanEntiteit(final PersoonGeboorteHistorie historie, final Persoon persoon) {
        persoon.addPersoonGeboorteHistorie(historie);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PersoonGeboorteHistorie mapHistorischeGroep(final BrpGeboorteInhoud groepInhoud, final Persoon persoon) {
        final PersoonGeboorteHistorie result =
                new PersoonGeboorteHistorie(
                        persoon,
                        MapperUtil.mapBrpDatumToInteger(groepInhoud.getGeboortedatum()),
                        getStamtabelMapping().findLandOfGebiedByCode(groepInhoud.getLandOfGebiedCode(), SoortMeldingCode.PRE008));

        result.setGemeente(getStamtabelMapping().findGemeenteByCode(groepInhoud.getGemeenteCode(), SoortMeldingCode.PRE025));
        result.setOmschrijvingGeboortelocatie(BrpString.unwrap(groepInhoud.getOmschrijvingGeboortelocatie()));
        result.setWoonplaatsnaamGeboorte(BrpString.unwrap(groepInhoud.getWoonplaatsnaamGeboorte()));
        result.setBuitenlandsePlaatsGeboorte(BrpString.unwrap(groepInhoud.getBuitenlandsePlaatsGeboorte()));
        result.setBuitenlandseRegioGeboorte(BrpString.unwrap(groepInhoud.getBuitenlandseRegioGeboorte()));

        // onderzoek
        mapOnderzoek(persoon, groepInhoud, result);

        return result;
    }

    @Override
    protected void mapOnderzoekPersoon(final BrpGeboorteInhoud groepInhoud, final PersoonGeboorteHistorie historie) {
        getOnderzoekMapper().mapOnderzoek(historie, groepInhoud.getGeboortedatum(), Element.PERSOON_GEBOORTE_DATUM);
        getOnderzoekMapper().mapOnderzoek(historie, groepInhoud.getLandOfGebiedCode(), Element.PERSOON_GEBOORTE_LANDGEBIEDCODE);
        getOnderzoekMapper().mapOnderzoek(historie, groepInhoud.getGemeenteCode(), Element.PERSOON_GEBOORTE_GEMEENTECODE);
        getOnderzoekMapper().mapOnderzoek(historie, groepInhoud.getOmschrijvingGeboortelocatie(), Element.PERSOON_GEBOORTE_OMSCHRIJVINGLOCATIE);
        getOnderzoekMapper().mapOnderzoek(historie, groepInhoud.getWoonplaatsnaamGeboorte(), Element.PERSOON_GEBOORTE_WOONPLAATSNAAM);
        getOnderzoekMapper().mapOnderzoek(historie, groepInhoud.getBuitenlandsePlaatsGeboorte(), Element.PERSOON_GEBOORTE_BUITENLANDSEPLAATS);
        getOnderzoekMapper().mapOnderzoek(historie, groepInhoud.getBuitenlandseRegioGeboorte(), Element.PERSOON_GEBOORTE_BUITENLANDSEREGIO);
    }

    @Override
    protected void mapOnderzoekKind(final BrpGeboorteInhoud groepInhoud, final PersoonGeboorteHistorie result) {
        getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getGeboortedatum(), Element.GERELATEERDEKIND_PERSOON_GEBOORTE_DATUM);
        getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getLandOfGebiedCode(), Element.GERELATEERDEKIND_PERSOON_GEBOORTE_LANDGEBIEDCODE);
        getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getGemeenteCode(), Element.GERELATEERDEKIND_PERSOON_GEBOORTE_GEMEENTECODE);
        getOnderzoekMapper().mapOnderzoek(
                result,
                groepInhoud.getOmschrijvingGeboortelocatie(),
                Element.GERELATEERDEKIND_PERSOON_GEBOORTE_OMSCHRIJVINGLOCATIE);
        getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getWoonplaatsnaamGeboorte(), Element.GERELATEERDEKIND_PERSOON_GEBOORTE_WOONPLAATSNAAM);
        getOnderzoekMapper().mapOnderzoek(
                result,
                groepInhoud.getBuitenlandsePlaatsGeboorte(),
                Element.GERELATEERDEKIND_PERSOON_GEBOORTE_BUITENLANDSEPLAATS);
        getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getBuitenlandseRegioGeboorte(), Element.GERELATEERDEKIND_PERSOON_GEBOORTE_BUITENLANDSEREGIO);
    }

    @Override
    protected void mapOnderzoekOuder(final BrpGeboorteInhoud groepInhoud, final PersoonGeboorteHistorie result) {
        getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getGeboortedatum(), Element.GERELATEERDEOUDER_PERSOON_GEBOORTE_DATUM);
        getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getLandOfGebiedCode(), Element.GERELATEERDEOUDER_PERSOON_GEBOORTE_LANDGEBIEDCODE);
        getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getGemeenteCode(), Element.GERELATEERDEOUDER_PERSOON_GEBOORTE_GEMEENTECODE);
        getOnderzoekMapper().mapOnderzoek(
                result,
                groepInhoud.getOmschrijvingGeboortelocatie(),
                Element.GERELATEERDEOUDER_PERSOON_GEBOORTE_OMSCHRIJVINGLOCATIE);
        getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getWoonplaatsnaamGeboorte(), Element.GERELATEERDEOUDER_PERSOON_GEBOORTE_WOONPLAATSNAAM);
        getOnderzoekMapper().mapOnderzoek(
                result,
                groepInhoud.getBuitenlandsePlaatsGeboorte(),
                Element.GERELATEERDEOUDER_PERSOON_GEBOORTE_BUITENLANDSEPLAATS);
        getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getBuitenlandseRegioGeboorte(), Element.GERELATEERDEOUDER_PERSOON_GEBOORTE_BUITENLANDSEREGIO);
    }

    @Override
    protected void mapOnderzoekHuwelijkspartner(final BrpGeboorteInhoud groepInhoud, final PersoonGeboorteHistorie result) {
        getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getGeboortedatum(), Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE_DATUM);
        getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getLandOfGebiedCode(), Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE_LANDGEBIEDCODE);
        getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getGemeenteCode(), Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE_GEMEENTECODE);
        getOnderzoekMapper().mapOnderzoek(
                result,
                groepInhoud.getOmschrijvingGeboortelocatie(),
                Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE_OMSCHRIJVINGLOCATIE);
        getOnderzoekMapper().mapOnderzoek(
                result,
                groepInhoud.getWoonplaatsnaamGeboorte(),
                Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE_WOONPLAATSNAAM);
        getOnderzoekMapper().mapOnderzoek(
                result,
                groepInhoud.getBuitenlandsePlaatsGeboorte(),
                Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE_BUITENLANDSEPLAATS);
        getOnderzoekMapper().mapOnderzoek(
                result,
                groepInhoud.getBuitenlandseRegioGeboorte(),
                Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE_BUITENLANDSEREGIO);
    }

    @Override
    protected void mapOnderzoekGeregistreerdPartner(final BrpGeboorteInhoud groepInhoud, final PersoonGeboorteHistorie result) {
        getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getGeboortedatum(), Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE_DATUM);
        getOnderzoekMapper().mapOnderzoek(
                result,
                groepInhoud.getLandOfGebiedCode(),
                Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE_LANDGEBIEDCODE);
        getOnderzoekMapper().mapOnderzoek(result, groepInhoud.getGemeenteCode(), Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE_GEMEENTECODE);
        getOnderzoekMapper().mapOnderzoek(
                result,
                groepInhoud.getOmschrijvingGeboortelocatie(),
                Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE_OMSCHRIJVINGLOCATIE);
        getOnderzoekMapper().mapOnderzoek(
                result,
                groepInhoud.getWoonplaatsnaamGeboorte(),
                Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE_WOONPLAATSNAAM);
        getOnderzoekMapper().mapOnderzoek(
                result,
                groepInhoud.getBuitenlandsePlaatsGeboorte(),
                Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE_BUITENLANDSEPLAATS);
        getOnderzoekMapper().mapOnderzoek(
                result,
                groepInhoud.getBuitenlandseRegioGeboorte(),
                Element.GERELATEERDEGEREGISTREERDEPARTNER_PERSOON_GEBOORTE_BUITENLANDSEREGIO);
    }
}
