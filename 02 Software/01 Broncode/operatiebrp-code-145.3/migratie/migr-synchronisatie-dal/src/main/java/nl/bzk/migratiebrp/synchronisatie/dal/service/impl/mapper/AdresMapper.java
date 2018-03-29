/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAdres;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAdresHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdres;
import nl.bzk.algemeenbrp.dal.repositories.DynamischeStamtabelRepository;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpInteger;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpAdresInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.AbstractHistorieMapperStrategie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BRPActieFactory;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.MapperUtil;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.OnderzoekMapper;

/**
 * Deze mapper mapped BrpAdresInhoud uit het migratie model op de corresponderen BRP entiteiten uit het operationele
 * datamodel van de BRP.
 */
public final class AdresMapper extends AbstractHistorieMapperStrategie<BrpAdresInhoud, PersoonAdresHistorie, PersoonAdres> {

    /**
     * Maakt een AdresMapper object.
     * @param dynamischeStamtabelRepository de repository die bevraging van de stamtabellen mogelijk maakt
     * @param brpActieFactory de factory die gebruikt wordt voor het mappen van BRP acties
     * @param onderzoekMapper de mapper voor onderzoeken
     */
    public AdresMapper(
            final DynamischeStamtabelRepository dynamischeStamtabelRepository,
            final BRPActieFactory brpActieFactory,
            final OnderzoekMapper onderzoekMapper) {
        super(dynamischeStamtabelRepository, brpActieFactory, onderzoekMapper);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void voegHistorieToeAanEntiteit(final PersoonAdresHistorie historie, final PersoonAdres entiteit) {
        entiteit.addPersoonAdresHistorie(historie);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PersoonAdresHistorie mapHistorischeGroep(final BrpAdresInhoud groepInhoud, final PersoonAdres adres) {
        final PersoonAdresHistorie result =
                new PersoonAdresHistorie(
                        adres,
                        SoortAdres.parseCode(groepInhoud.getSoortAdresCode().getWaarde()),
                        getStamtabelMapping().findLandOfGebiedByCode(groepInhoud.getLandOfGebiedCode()),
                        getStamtabelMapping().findRedenWijzigingVerblijfByCode(groepInhoud.getRedenWijzigingAdresCode()));
        result.setAangeverAdreshouding(getStamtabelMapping().findAangeverByCode(groepInhoud.getAangeverAdreshoudingCode()));
        result.setIdentificatiecodeAdresseerbaarObject(BrpString.unwrap(groepInhoud.getIdentificatiecodeAdresseerbaarObject()));
        result.setBuitenlandsAdresRegel1(BrpString.unwrap(groepInhoud.getBuitenlandsAdresRegel1()));
        result.setBuitenlandsAdresRegel2(BrpString.unwrap(groepInhoud.getBuitenlandsAdresRegel2()));
        result.setBuitenlandsAdresRegel3(BrpString.unwrap(groepInhoud.getBuitenlandsAdresRegel3()));
        result.setBuitenlandsAdresRegel4(BrpString.unwrap(groepInhoud.getBuitenlandsAdresRegel4()));
        result.setBuitenlandsAdresRegel5(BrpString.unwrap(groepInhoud.getBuitenlandsAdresRegel5()));
        result.setBuitenlandsAdresRegel6(BrpString.unwrap(groepInhoud.getBuitenlandsAdresRegel6()));
        result.setDatumAanvangAdreshouding(MapperUtil.mapBrpDatumToInteger(groepInhoud.getDatumAanvangAdreshouding()));
        result.setGemeentedeel(BrpString.unwrap(groepInhoud.getGemeentedeel()));
        final Character huisletter = BrpCharacter.unwrap(groepInhoud.getHuisletter());
        if (huisletter != null) {
            result.setHuisletter(huisletter);
        }
        final Integer huisnummer = BrpInteger.unwrap(groepInhoud.getHuisnummer());
        if (huisnummer != null) {
            result.setHuisnummer(huisnummer);
        }
        result.setHuisnummertoevoeging(BrpString.unwrap(groepInhoud.getHuisnummertoevoeging()));
        result.setIdentificatiecodeNummeraanduiding(BrpString.unwrap(groepInhoud.getIdentificatiecodeNummeraanduiding()));
        result.setLocatieOmschrijving(BrpString.unwrap(groepInhoud.getLocatieOmschrijving()));
        if (groepInhoud.getLocatieTovAdres() != null) {
            result.setLocatietovAdres(groepInhoud.getLocatieTovAdres().getWaarde());
        }
        result.setNaamOpenbareRuimte(BrpString.unwrap(groepInhoud.getNaamOpenbareRuimte()));
        result.setAfgekorteNaamOpenbareRuimte(BrpString.unwrap(groepInhoud.getAfgekorteNaamOpenbareRuimte()));
        result.setGemeente(getStamtabelMapping().findGemeenteByCode(groepInhoud.getGemeenteCode()));
        result.setWoonplaatsnaam(BrpString.unwrap(groepInhoud.getWoonplaatsnaam()));
        result.setPostcode(BrpString.unwrap(groepInhoud.getPostcode()));

        // onderzoek
        mapOnderzoekOpHistorischeGroep(result, groepInhoud);

        return result;
    }

    private void mapOnderzoekOpHistorischeGroep(final PersoonAdresHistorie historieEntiteit, final BrpAdresInhoud groepInhoud) {
        getOnderzoekMapper().mapOnderzoek(historieEntiteit, groepInhoud.getSoortAdresCode(), Element.PERSOON_ADRES_SOORTCODE);
        getOnderzoekMapper().mapOnderzoek(historieEntiteit, groepInhoud.getLandOfGebiedCode(), Element.PERSOON_ADRES_LANDGEBIEDCODE);
        getOnderzoekMapper().mapOnderzoek(historieEntiteit, groepInhoud.getAangeverAdreshoudingCode(), Element.PERSOON_ADRES_AANGEVERADRESHOUDINGCODE);
        getOnderzoekMapper().mapOnderzoek(
                historieEntiteit,
                groepInhoud.getIdentificatiecodeAdresseerbaarObject(),
                Element.PERSOON_ADRES_IDENTIFICATIECODEADRESSEERBAAROBJECT);
        getOnderzoekMapper().mapOnderzoek(historieEntiteit, groepInhoud.getBuitenlandsAdresRegel1(), Element.PERSOON_ADRES_BUITENLANDSADRESREGEL1);
        getOnderzoekMapper().mapOnderzoek(historieEntiteit, groepInhoud.getBuitenlandsAdresRegel2(), Element.PERSOON_ADRES_BUITENLANDSADRESREGEL2);
        getOnderzoekMapper().mapOnderzoek(historieEntiteit, groepInhoud.getBuitenlandsAdresRegel3(), Element.PERSOON_ADRES_BUITENLANDSADRESREGEL3);
        getOnderzoekMapper().mapOnderzoek(historieEntiteit, groepInhoud.getBuitenlandsAdresRegel4(), Element.PERSOON_ADRES_BUITENLANDSADRESREGEL4);
        getOnderzoekMapper().mapOnderzoek(historieEntiteit, groepInhoud.getBuitenlandsAdresRegel5(), Element.PERSOON_ADRES_BUITENLANDSADRESREGEL5);
        getOnderzoekMapper().mapOnderzoek(historieEntiteit, groepInhoud.getBuitenlandsAdresRegel6(), Element.PERSOON_ADRES_BUITENLANDSADRESREGEL6);
        getOnderzoekMapper().mapOnderzoek(historieEntiteit, groepInhoud.getDatumAanvangAdreshouding(), Element.PERSOON_ADRES_DATUMAANVANGADRESHOUDING);
        getOnderzoekMapper().mapOnderzoek(historieEntiteit, groepInhoud.getGemeentedeel(), Element.PERSOON_ADRES_GEMEENTEDEEL);
        getOnderzoekMapper().mapOnderzoek(historieEntiteit, groepInhoud.getHuisletter(), Element.PERSOON_ADRES_HUISLETTER);
        getOnderzoekMapper().mapOnderzoek(historieEntiteit, groepInhoud.getHuisnummer(), Element.PERSOON_ADRES_HUISNUMMER);
        getOnderzoekMapper().mapOnderzoek(historieEntiteit, groepInhoud.getHuisnummertoevoeging(), Element.PERSOON_ADRES_HUISNUMMERTOEVOEGING);
        getOnderzoekMapper().mapOnderzoek(
                historieEntiteit,
                groepInhoud.getIdentificatiecodeNummeraanduiding(),
                Element.PERSOON_ADRES_IDENTIFICATIECODENUMMERAANDUIDING);
        getOnderzoekMapper().mapOnderzoek(historieEntiteit, groepInhoud.getLocatieOmschrijving(), Element.PERSOON_ADRES_LOCATIEOMSCHRIJVING);
        getOnderzoekMapper().mapOnderzoek(historieEntiteit, groepInhoud.getLocatieTovAdres(), Element.PERSOON_ADRES_LOCATIETENOPZICHTEVANADRES);
        getOnderzoekMapper().mapOnderzoek(historieEntiteit, groepInhoud.getNaamOpenbareRuimte(), Element.PERSOON_ADRES_NAAMOPENBARERUIMTE);
        getOnderzoekMapper().mapOnderzoek(
                historieEntiteit,
                groepInhoud.getAfgekorteNaamOpenbareRuimte(),
                Element.PERSOON_ADRES_AFGEKORTENAAMOPENBARERUIMTE);
        getOnderzoekMapper().mapOnderzoek(historieEntiteit, groepInhoud.getGemeenteCode(), Element.PERSOON_ADRES_GEMEENTECODE);
        getOnderzoekMapper().mapOnderzoek(historieEntiteit, groepInhoud.getWoonplaatsnaam(), Element.PERSOON_ADRES_WOONPLAATSNAAM);
        getOnderzoekMapper().mapOnderzoek(historieEntiteit, groepInhoud.getPostcode(), Element.PERSOON_ADRES_POSTCODE);
        getOnderzoekMapper().mapOnderzoek(historieEntiteit, groepInhoud.getRedenWijzigingAdresCode(), Element.PERSOON_ADRES_REDENWIJZIGINGCODE);
    }
}
