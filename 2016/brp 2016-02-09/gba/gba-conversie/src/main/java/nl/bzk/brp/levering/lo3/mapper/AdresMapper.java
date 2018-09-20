/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.HisPersoonAdresModel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpAdresInhoud;
import org.springframework.stereotype.Component;

/**
 * Mapt adressen.
 */
@Component
public final class AdresMapper extends AbstractMaterieelMapper<PersoonHisVolledig, HisPersoonAdresModel, BrpAdresInhoud> {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    /**
     * Constructor.
     */
    public AdresMapper() {
        super(ElementEnum.PERSOON_ADRES_DATUMAANVANGGELDIGHEID,
              ElementEnum.PERSOON_ADRES_DATUMEINDEGELDIGHEID,
              ElementEnum.PERSOON_ADRES_TIJDSTIPREGISTRATIE,
              ElementEnum.PERSOON_ADRES_TIJDSTIPVERVAL);
    }

    @Override
    protected Iterable<HisPersoonAdresModel> getHistorieIterable(final PersoonHisVolledig persoonHisVolledig) {
        if (persoonHisVolledig.getAdressen().isEmpty()) {
            return null;
        }

        if (persoonHisVolledig.getAdressen().size() > 1) {
            LOGGER.warn("Er zijn meerdere adressen gevonden. Alleen het eerste adres wordt gemapt.");
        }

        return persoonHisVolledig.getAdressen().iterator().next().getPersoonAdresHistorie();
    }

    @Override
    public BrpAdresInhoud mapInhoud(final HisPersoonAdresModel historie, final OnderzoekMapper onderzoekMapper) {
        return new BrpAdresInhoud(
            BrpMapperUtil.mapBrpSoortAdresCode(
                historie.getSoort(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_ADRES_SOORTCODE, true)),
            BrpMapperUtil.mapBrpRedenWijzigingVerblijfCode(
                historie.getRedenWijziging(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_ADRES_REDENWIJZIGINGCODE, true)),
            BrpMapperUtil.mapBrpAangeverCode(
                historie.getAangeverAdreshouding(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_ADRES_AANGEVERADRESHOUDINGCODE, true)),
            BrpMapperUtil.mapBrpDatum(
                historie.getDatumAanvangAdreshouding(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_ADRES_DATUMAANVANGADRESHOUDING, true)),
            BrpMapperUtil.mapBrpString(
                historie.getIdentificatiecodeAdresseerbaarObject(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_ADRES_IDENTIFICATIECODEADRESSEERBAAROBJECT, true)),
            BrpMapperUtil.mapBrpString(
                historie.getIdentificatiecodeNummeraanduiding(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_ADRES_IDENTIFICATIECODENUMMERAANDUIDING, true)),
            BrpMapperUtil.mapBrpGemeenteCode(
                historie.getGemeente(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_ADRES_GEMEENTECODE, true)),
            BrpMapperUtil.mapBrpString(
                historie.getNaamOpenbareRuimte(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_ADRES_NAAMOPENBARERUIMTE, true)),
            BrpMapperUtil.mapBrpString(
                historie.getAfgekorteNaamOpenbareRuimte(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_ADRES_AFGEKORTENAAMOPENBARERUIMTE, true)),
            BrpMapperUtil.mapBrpString(
                historie.getGemeentedeel(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_ADRES_GEMEENTEDEEL, true)),
            BrpMapperUtil.mapBrpInteger(
                historie.getHuisnummer(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_ADRES_HUISNUMMER, true)),
            BrpMapperUtil.mapBrpCharacter(
                historie.getHuisletter(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_ADRES_HUISLETTER, true)),
            BrpMapperUtil.mapBrpString(
                historie.getHuisnummertoevoeging(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_ADRES_HUISNUMMERTOEVOEGING, true)),
            BrpMapperUtil.mapBrpString(
                historie.getPostcode(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_ADRES_POSTCODE, true)),
            BrpMapperUtil.mapBrpString(
                historie.getWoonplaatsnaam(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_ADRES_WOONPLAATSNAAM, true)),
            BrpMapperUtil.mapBrpAanduidingBijHuisnummerCode(
                historie.getLocatieTenOpzichteVanAdres(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_ADRES_LOCATIETENOPZICHTEVANADRES, true)),
            BrpMapperUtil.mapBrpString(
                historie.getLocatieomschrijving(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_ADRES_LOCATIEOMSCHRIJVING, true)),
            BrpMapperUtil.mapBrpString(
                historie.getBuitenlandsAdresRegel1(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_ADRES_BUITENLANDSADRESREGEL1, true)),
            BrpMapperUtil.mapBrpString(
                historie.getBuitenlandsAdresRegel2(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_ADRES_BUITENLANDSADRESREGEL2, true)),
            BrpMapperUtil.mapBrpString(
                historie.getBuitenlandsAdresRegel3(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_ADRES_BUITENLANDSADRESREGEL3, true)),
            BrpMapperUtil.mapBrpString(
                historie.getBuitenlandsAdresRegel4(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_ADRES_BUITENLANDSADRESREGEL4, true)),
            BrpMapperUtil.mapBrpString(
                historie.getBuitenlandsAdresRegel5(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_ADRES_BUITENLANDSADRESREGEL5, true)),
            BrpMapperUtil.mapBrpString(
                historie.getBuitenlandsAdresRegel6(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_ADRES_BUITENLANDSADRESREGEL6, true)),
            BrpMapperUtil.mapBrpLandOfGebiedCode(
                historie.getLandGebied(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_ADRES_LANDGEBIEDCODE, true)),
            BrpMapperUtil.mapBrpBoolean(
                historie.getIndicatiePersoonAangetroffenOpAdres(),
                onderzoekMapper.bepaalOnderzoek(historie.getID(), ElementEnum.PERSOON_ADRES_INDICATIEPERSOONAANGETROFFENOPADRES, true)));
    }
}
