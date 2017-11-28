/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.element.GroepElement;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpAdresInhoud;
import org.springframework.stereotype.Component;

/**
 * Mapt adressen.
 */
@Component
public final class AdresMapper extends AbstractMapper<BrpAdresInhoud> {

    /**
     * Identiteit groep element.
     */
    public static final GroepElement IDENTITEIT_GROEP_ELEMENT = ElementHelper.getGroepElement(Element.PERSOON_ADRES_IDENTITEIT.getId());
    /**
     * Groep element.
     */
    public static final GroepElement GROEP_ELEMENT = ElementHelper.getGroepElement(Element.PERSOON_ADRES_STANDAARD.getId());

    private static final AttribuutElement SOORT_ELEMENT = ElementHelper
            .getAttribuutElement(Element.PERSOON_ADRES_SOORTCODE.getId());
    private static final AttribuutElement REDEN_WIJZIGING_ELEMENT =
            ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_REDENWIJZIGINGCODE.getId());
    private static final AttribuutElement AANGEVER_ELEMENT =
            ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_AANGEVERADRESHOUDINGCODE.getId());
    private static final AttribuutElement DATUM_AANVANG_ADRESHOUDING_ELEMENT =
            ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_DATUMAANVANGADRESHOUDING.getId());
    private static final AttribuutElement ID_ADRESSEERBAAR_OBJECT_ELEMENT =
            ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_IDENTIFICATIECODEADRESSEERBAAROBJECT.getId());
    private static final AttribuutElement ID_NUMMERAANDUIDING_ELEMENT =
            ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_IDENTIFICATIECODENUMMERAANDUIDING.getId());
    private static final AttribuutElement GEMEENTE_ELEMENT = ElementHelper
            .getAttribuutElement(Element.PERSOON_ADRES_GEMEENTECODE.getId());
    private static final AttribuutElement NAAM_OPENBARE_RUIMTE_ELEMENT =
            ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_NAAMOPENBARERUIMTE.getId());
    private static final AttribuutElement AFGEKORTE_NAAM_OPENBARE_RUIMTE_ELEMENT =
            ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_AFGEKORTENAAMOPENBARERUIMTE.getId());
    private static final AttribuutElement GEMEENTEDEEL_ELEMENT = ElementHelper
            .getAttribuutElement(Element.PERSOON_ADRES_GEMEENTEDEEL.getId());
    private static final AttribuutElement HUISNUMMER_ELEMENT = ElementHelper
            .getAttribuutElement(Element.PERSOON_ADRES_HUISNUMMER.getId());
    private static final AttribuutElement HUISLETTER_ELEMENT = ElementHelper
            .getAttribuutElement(Element.PERSOON_ADRES_HUISLETTER.getId());
    private static final AttribuutElement HUISNUMMER_TOEVOEGING_ELEMENT =
            ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_HUISNUMMERTOEVOEGING.getId());
    private static final AttribuutElement POSTCODE_ELEMENT = ElementHelper
            .getAttribuutElement(Element.PERSOON_ADRES_POSTCODE.getId());
    private static final AttribuutElement WOONPLAATS_ELEMENT = ElementHelper
            .getAttribuutElement(Element.PERSOON_ADRES_WOONPLAATSNAAM.getId());
    private static final AttribuutElement LOCATIE_ELEMENT =
            ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_LOCATIETENOPZICHTEVANADRES.getId());
    private static final AttribuutElement LOCATIE_OMSCHRIJVING_ELEMENT =
            ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_LOCATIEOMSCHRIJVING.getId());
    private static final AttribuutElement BUITENLANDS_ADRES_REGEL1_ELEMENT =
            ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL1.getId());
    private static final AttribuutElement BUITENLANDS_ADRES_REGEL2_ELEMENT =
            ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL2.getId());
    private static final AttribuutElement BUITENLANDS_ADRES_REGEL3_ELEMENT =
            ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL3.getId());
    private static final AttribuutElement BUITENLANDS_ADRES_REGEL4_ELEMENT =
            ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL4.getId());
    private static final AttribuutElement BUITENLANDS_ADRES_REGEL5_ELEMENT =
            ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL5.getId());
    private static final AttribuutElement BUITENLANDS_ADRES_REGEL6_ELEMENT =
            ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_BUITENLANDSADRESREGEL6.getId());
    private static final AttribuutElement LAND_ELEMENT =
            ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_LANDGEBIEDCODE.getId());
    private static final AttribuutElement INDICATIE_PERSOON_AANGETROFFEN_OP_ADRES_ELEMENT =
            ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_INDICATIEPERSOONAANGETROFFENOPADRES.getId());

    /**
     * Constructor.
     */
    public AdresMapper() {
        super(IDENTITEIT_GROEP_ELEMENT,
                GROEP_ELEMENT,
                ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_DATUMAANVANGGELDIGHEID.getId()),
                ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_DATUMEINDEGELDIGHEID.getId()),
                ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_TIJDSTIPREGISTRATIE.getId()),
                ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_TIJDSTIPVERVAL.getId()));
    }

    @Override
    public BrpAdresInhoud mapInhoud(final MetaRecord identiteitRecord, final MetaRecord record, final OnderzoekMapper onderzoekMapper) {
        return new BrpAdresInhoud(
                BrpMetaAttribuutMapper.mapBrpSoortAdresCode(
                        record.getAttribuut(SOORT_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), SOORT_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpRedenWijzigingVerblijfCode(
                        record.getAttribuut(REDEN_WIJZIGING_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), REDEN_WIJZIGING_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpAangeverCode(
                        record.getAttribuut(AANGEVER_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), AANGEVER_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpDatum(
                        record.getAttribuut(DATUM_AANVANG_ADRESHOUDING_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), DATUM_AANVANG_ADRESHOUDING_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpString(
                        record.getAttribuut(ID_ADRESSEERBAAR_OBJECT_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), ID_ADRESSEERBAAR_OBJECT_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpString(
                        record.getAttribuut(ID_NUMMERAANDUIDING_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), ID_NUMMERAANDUIDING_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpGemeenteCode(
                        record.getAttribuut(GEMEENTE_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), GEMEENTE_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpString(
                        record.getAttribuut(NAAM_OPENBARE_RUIMTE_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), NAAM_OPENBARE_RUIMTE_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpString(
                        record.getAttribuut(AFGEKORTE_NAAM_OPENBARE_RUIMTE_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), AFGEKORTE_NAAM_OPENBARE_RUIMTE_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpString(
                        record.getAttribuut(GEMEENTEDEEL_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), GEMEENTEDEEL_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpInteger(
                        record.getAttribuut(HUISNUMMER_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), HUISNUMMER_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpCharacter(
                        record.getAttribuut(HUISLETTER_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), HUISLETTER_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpString(
                        record.getAttribuut(HUISNUMMER_TOEVOEGING_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), HUISNUMMER_TOEVOEGING_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpString(
                        record.getAttribuut(POSTCODE_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), POSTCODE_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpString(
                        record.getAttribuut(WOONPLAATS_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), WOONPLAATS_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpAanduidingBijHuisnummerCode(
                        record.getAttribuut(LOCATIE_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), LOCATIE_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpString(
                        record.getAttribuut(LOCATIE_OMSCHRIJVING_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), LOCATIE_OMSCHRIJVING_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpString(
                        record.getAttribuut(BUITENLANDS_ADRES_REGEL1_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), BUITENLANDS_ADRES_REGEL1_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpString(
                        record.getAttribuut(BUITENLANDS_ADRES_REGEL2_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), BUITENLANDS_ADRES_REGEL2_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpString(
                        record.getAttribuut(BUITENLANDS_ADRES_REGEL3_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), BUITENLANDS_ADRES_REGEL3_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpString(
                        record.getAttribuut(BUITENLANDS_ADRES_REGEL4_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), BUITENLANDS_ADRES_REGEL4_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpString(
                        record.getAttribuut(BUITENLANDS_ADRES_REGEL5_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), BUITENLANDS_ADRES_REGEL5_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpString(
                        record.getAttribuut(BUITENLANDS_ADRES_REGEL6_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), BUITENLANDS_ADRES_REGEL6_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpLandOfGebiedCode(
                        record.getAttribuut(LAND_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), LAND_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpBooleanNee(
                        record.getAttribuut(INDICATIE_PERSOON_AANGETROFFEN_OP_ADRES_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), INDICATIE_PERSOON_AANGETROFFEN_OP_ADRES_ELEMENT, true)));
    }
}
