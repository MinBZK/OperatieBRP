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
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpMigratieInhoud;
import org.springframework.stereotype.Component;

/**
 * Mapt de migratie.
 */
@Component
public final class PersoonMigratieMapper extends AbstractMapper<BrpMigratieInhoud> {

    /**
     * Groep element.
     */
    public static final GroepElement GROEP_ELEMENT = ElementHelper.getGroepElement(Element.PERSOON_MIGRATIE.getId());

    private static final AttribuutElement SOORT_ELEMENT = ElementHelper
            .getAttribuutElement(Element.PERSOON_MIGRATIE_SOORTCODE.getId());
    private static final AttribuutElement REDEN_WIJZIGING_ELEMENT =
            ElementHelper.getAttribuutElement(Element.PERSOON_MIGRATIE_REDENWIJZIGINGCODE.getId());
    private static final AttribuutElement AANGEVER_ELEMENT = ElementHelper
            .getAttribuutElement(Element.PERSOON_MIGRATIE_AANGEVERCODE.getId());
    private static final AttribuutElement LAND_ELEMENT = ElementHelper
            .getAttribuutElement(Element.PERSOON_MIGRATIE_LANDGEBIEDCODE.getId());
    private static final AttribuutElement BUITENLANDSE_ADRES_REGEL1_ELEMENT =
            ElementHelper.getAttribuutElement(Element.PERSOON_MIGRATIE_BUITENLANDSADRESREGEL1.getId());
    private static final AttribuutElement BUITENLANDSE_ADRES_REGEL2_ELEMENT =
            ElementHelper.getAttribuutElement(Element.PERSOON_MIGRATIE_BUITENLANDSADRESREGEL2.getId());
    private static final AttribuutElement BUITENLANDSE_ADRES_REGEL3_ELEMENT =
            ElementHelper.getAttribuutElement(Element.PERSOON_MIGRATIE_BUITENLANDSADRESREGEL3.getId());
    private static final AttribuutElement BUITENLANDSE_ADRES_REGEL4_ELEMENT =
            ElementHelper.getAttribuutElement(Element.PERSOON_MIGRATIE_BUITENLANDSADRESREGEL4.getId());
    private static final AttribuutElement BUITENLANDSE_ADRES_REGEL5_ELEMENT =
            ElementHelper.getAttribuutElement(Element.PERSOON_MIGRATIE_BUITENLANDSADRESREGEL5.getId());
    private static final AttribuutElement BUITENLANDSE_ADRES_REGEL6_ELEMENT =
            ElementHelper.getAttribuutElement(Element.PERSOON_MIGRATIE_BUITENLANDSADRESREGEL6.getId());

    /**
     * Constructor.
     */
    public PersoonMigratieMapper() {
        super(ElementHelper.getGroepElement(Element.PERSOON_IDENTITEIT.getId()),
                GROEP_ELEMENT,
                ElementHelper.getAttribuutElement(Element.PERSOON_MIGRATIE_DATUMAANVANGGELDIGHEID.getId()),
                ElementHelper.getAttribuutElement(Element.PERSOON_MIGRATIE_DATUMEINDEGELDIGHEID.getId()),
                ElementHelper.getAttribuutElement(Element.PERSOON_MIGRATIE_TIJDSTIPREGISTRATIE.getId()),
                ElementHelper.getAttribuutElement(Element.PERSOON_MIGRATIE_TIJDSTIPVERVAL.getId()));
    }

    @Override
    public BrpMigratieInhoud mapInhoud(final MetaRecord identiteitRecord, final MetaRecord record, final OnderzoekMapper onderzoekMapper) {
        return new BrpMigratieInhoud(
                BrpMetaAttribuutMapper.mapBrpSoortMigratieCode(
                        record.getAttribuut(SOORT_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), SOORT_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpRedenWijzigingVerblijfCode(
                        record.getAttribuut(REDEN_WIJZIGING_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), REDEN_WIJZIGING_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpAangeverCode(
                        record.getAttribuut(AANGEVER_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), AANGEVER_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpLandOfGebiedCode(
                        record.getAttribuut(LAND_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), LAND_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpString(
                        record.getAttribuut(BUITENLANDSE_ADRES_REGEL1_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), BUITENLANDSE_ADRES_REGEL1_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpString(
                        record.getAttribuut(BUITENLANDSE_ADRES_REGEL2_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), BUITENLANDSE_ADRES_REGEL2_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpString(
                        record.getAttribuut(BUITENLANDSE_ADRES_REGEL3_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), BUITENLANDSE_ADRES_REGEL3_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpString(
                        record.getAttribuut(BUITENLANDSE_ADRES_REGEL4_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), BUITENLANDSE_ADRES_REGEL4_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpString(
                        record.getAttribuut(BUITENLANDSE_ADRES_REGEL5_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), BUITENLANDSE_ADRES_REGEL5_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpString(
                        record.getAttribuut(BUITENLANDSE_ADRES_REGEL6_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), BUITENLANDSE_ADRES_REGEL6_ELEMENT, true)));
    }
}
