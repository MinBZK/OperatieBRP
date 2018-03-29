/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper;

import java.util.Set;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAdres;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAdresHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAanduidingBijHuisnummerCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpAangeverCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpCharacter;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpInteger;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpLandOfGebiedCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpRedenWijzigingVerblijfCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortAdresCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpAdresInhoud;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.mapper.strategie.BrpOnderzoekMapper;
import org.springframework.stereotype.Component;

/**
 * Map adres van het BRP database model naar het BRP conversie model.
 */
@Component
public final class BrpAdresMapper {

    private final BrpAdresInhoudMapper mapper;

    /**
     * Constructor.
     * @param mapper adres inhoud mapper
     */
    @Inject
    public BrpAdresMapper(final BrpAdresInhoudMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * Map het eerste adres.
     * @param persoonAdresSet de adressen
     * @param brpOnderzoekMapper De mapper voor onderzoeken
     * @return adres
     */
    public BrpStapel<BrpAdresInhoud> map(final Set<PersoonAdres> persoonAdresSet, final BrpOnderzoekMapper brpOnderzoekMapper) {
        if (persoonAdresSet == null || persoonAdresSet.isEmpty()) {
            return null;
        } else {
            return mapper.map(persoonAdresSet.iterator().next().getPersoonAdresHistorieSet(), brpOnderzoekMapper);
        }
    }

    /**
     * Inhoudelijke mapper.
     */
    @Component
    public static final class BrpAdresInhoudMapper extends AbstractBrpMapper<PersoonAdresHistorie, BrpAdresInhoud> {

        @Override
        protected BrpAdresInhoud mapInhoud(final PersoonAdresHistorie historie, final BrpOnderzoekMapper brpOnderzoekMapper) {
            final BrpSoortAdresCode soortAdresCode = BrpMapperUtil.mapBrpSoortAdresCode(historie.getSoortAdres(),
                    brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_ADRES_SOORTCODE, true));
            final BrpRedenWijzigingVerblijfCode redenWijzigingVerblijfCode = BrpMapperUtil.mapBrpRedenWijzigingVerblijfCode(historie.getRedenWijziging(),
                    brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_ADRES_REDENWIJZIGINGCODE, true));
            final BrpAangeverCode aangeverCode = BrpMapperUtil.mapBrpAangeverCode(historie.getAangeverAdreshouding(),
                    brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_ADRES_AANGEVERADRESHOUDINGCODE, true));
            final BrpDatum datumAanvangAdreshouding = BrpMapperUtil.mapDatum(historie.getDatumAanvangAdreshouding(),
                    brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_ADRES_DATUMAANVANGADRESHOUDING, true));
            final BrpString identificatiecodeAdresseerbaarObject = BrpString.wrap(historie.getIdentificatiecodeAdresseerbaarObject(),
                    brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_ADRES_IDENTIFICATIECODEADRESSEERBAAROBJECT, true));
            final BrpString identificatiecodeNummeraanduiding = BrpString.wrap(historie.getIdentificatiecodeNummeraanduiding(),
                    brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_ADRES_IDENTIFICATIECODENUMMERAANDUIDING, true));
            final BrpGemeenteCode gemeenteCode = BrpMapperUtil.mapBrpGemeenteCode(historie.getGemeente(),
                    brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_ADRES_GEMEENTECODE, true));
            final BrpString naamOpenbareRuimte = BrpString.wrap(historie.getNaamOpenbareRuimte(),
                    brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_ADRES_NAAMOPENBARERUIMTE, true));
            final BrpString afgekorteNaamOpenbareRuimte = BrpString.wrap(historie.getAfgekorteNaamOpenbareRuimte(),
                    brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_ADRES_AFGEKORTENAAMOPENBARERUIMTE, true));
            final BrpString gemeentedeel = BrpString.wrap(historie.getGemeentedeel(),
                    brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_ADRES_GEMEENTEDEEL, true));
            final BrpInteger huisnummer = BrpInteger.wrap(historie.getHuisnummer(),
                    brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_ADRES_HUISNUMMER, true));
            final BrpCharacter huisletter = BrpCharacter.wrap(historie.getHuisletter(),
                    brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_ADRES_HUISLETTER, true));
            final BrpString huisnummerToevoeging = BrpString.wrap(historie.getHuisnummertoevoeging(),
                    brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_ADRES_HUISNUMMERTOEVOEGING, true));
            final BrpString postcode = BrpString.wrap(historie.getPostcode(),
                    brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_ADRES_POSTCODE, true));
            final BrpString woonplaatsnaam = BrpString.wrap(historie.getWoonplaatsnaam(),
                    brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_ADRES_WOONPLAATSNAAM, true));
            final BrpAanduidingBijHuisnummerCode aandBijHuisnummer = BrpMapperUtil.mapBrpAanduidingBijHuisnummerCode(historie.getLocatietovAdres(),
                    brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_ADRES_LOCATIETENOPZICHTEVANADRES, true));
            final BrpString locatieOmschrijving = BrpString.wrap(historie.getLocatieOmschrijving(),
                    brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_ADRES_LOCATIEOMSCHRIJVING, true));
            final BrpString buitenlandsAdresRegel1 = BrpString.wrap(historie.getBuitenlandsAdresRegel1(),
                    brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_ADRES_BUITENLANDSADRESREGEL1, true));
            final BrpString buitenlandsAdresRegel2 = BrpString.wrap(historie.getBuitenlandsAdresRegel2(),
                    brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_ADRES_BUITENLANDSADRESREGEL2, true));
            final BrpString buitenlandsAdresRegel3 = BrpString.wrap(historie.getBuitenlandsAdresRegel3(),
                    brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_ADRES_BUITENLANDSADRESREGEL3, true));
            final BrpString buitenlandsAdresRegel4 = BrpString.wrap(historie.getBuitenlandsAdresRegel4(),
                    brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_ADRES_BUITENLANDSADRESREGEL4, true));
            final BrpString buitenlandsAdresRegel5 = BrpString.wrap(historie.getBuitenlandsAdresRegel5(),
                    brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_ADRES_BUITENLANDSADRESREGEL5, true));
            final BrpString buitenlandsAdresRegel6 = BrpString.wrap(historie.getBuitenlandsAdresRegel6(),
                    brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_ADRES_BUITENLANDSADRESREGEL6, true));
            final BrpLandOfGebiedCode landOfGebied = BrpMapperUtil.mapBrpLandOfGebiedCode(historie.getLandOfGebied(),
                    brpOnderzoekMapper.bepaalOnderzoek(historie, Element.PERSOON_ADRES_LANDGEBIEDCODE, true));

            return new BrpAdresInhoud(
                    soortAdresCode,
                    redenWijzigingVerblijfCode,
                    aangeverCode,
                    datumAanvangAdreshouding,
                    identificatiecodeAdresseerbaarObject,
                    identificatiecodeNummeraanduiding,
                    gemeenteCode,
                    naamOpenbareRuimte,
                    afgekorteNaamOpenbareRuimte,
                    gemeentedeel,
                    huisnummer,
                    huisletter,
                    huisnummerToevoeging,
                    postcode,
                    woonplaatsnaam,
                    aandBijHuisnummer,
                    locatieOmschrijving,
                    buitenlandsAdresRegel1,
                    buitenlandsAdresRegel2,
                    buitenlandsAdresRegel3,
                    buitenlandsAdresRegel4,
                    buitenlandsAdresRegel5,
                    buitenlandsAdresRegel6,
                    landOfGebied,
                    null);
        }
    }
}
