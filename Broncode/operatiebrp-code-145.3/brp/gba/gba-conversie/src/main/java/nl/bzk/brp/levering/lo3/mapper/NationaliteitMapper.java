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
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNationaliteitInhoud;
import org.springframework.stereotype.Component;

/**
 * Mapt een nationaliteit.
 */
@Component
public final class NationaliteitMapper extends AbstractMapper<BrpNationaliteitInhoud> {

    /**
     * Identiteit groep element.
     */
    public static final GroepElement IDENTITEIT_GROEP_ELEMENT = ElementHelper.getGroepElement(Element.PERSOON_NATIONALITEIT_IDENTITEIT.getId());
    /**
     * Groep element.
     */
    public static final GroepElement GROEP_ELEMENT = ElementHelper.getGroepElement(Element.PERSOON_NATIONALITEIT_STANDAARD.getId());

    /**
     * Nationaliteit code element.
     */
    public static final AttribuutElement NATIONALITEITCODE_ELEMENT = ElementHelper.getAttribuutElement(Element.PERSOON_NATIONALITEIT_NATIONALITEITCODE.getId());

    private static final AttribuutElement REDEN_VERKRIJGING_ELEMENT =
            ElementHelper.getAttribuutElement(Element.PERSOON_NATIONALITEIT_REDENVERKRIJGINGCODE.getId());
    private static final AttribuutElement REDEN_VERLIES_ELEMENT = ElementHelper.getAttribuutElement(Element.PERSOON_NATIONALITEIT_REDENVERLIESCODE.getId());
    private static final AttribuutElement INDICATIE_BIJHOUDING_BEEINDIGD_ELEMENT =
            ElementHelper.getAttribuutElement(Element.PERSOON_NATIONALITEIT_INDICATIEBIJHOUDINGBEEINDIGD.getId());
    private static final AttribuutElement MIGRATIE_DATUM_EINDE_BIJHOUDING_ELEMENT =
            ElementHelper.getAttribuutElement(Element.PERSOON_NATIONALITEIT_MIGRATIEDATUMEINDEBIJHOUDING.getId());
    private static final AttribuutElement MIGRATIE_REDEN_OPNAME_ELEMENT =
            ElementHelper.getAttribuutElement(Element.PERSOON_NATIONALITEIT_MIGRATIEREDENOPNAMENATIONALITEIT.getId());
    private static final AttribuutElement MIGRATIE_REDEN_BEEINDIGEN_ELEMENT =
            ElementHelper.getAttribuutElement(Element.PERSOON_NATIONALITEIT_MIGRATIEREDENBEEINDIGENNATIONALITEIT.getId());

    /**
     * Constructor.
     */
    public NationaliteitMapper() {
        super(IDENTITEIT_GROEP_ELEMENT, GROEP_ELEMENT, ElementHelper.getAttribuutElement(Element.PERSOON_NATIONALITEIT_DATUMAANVANGGELDIGHEID.getId()),
                ElementHelper.getAttribuutElement(Element.PERSOON_NATIONALITEIT_DATUMEINDEGELDIGHEID.getId()),
                ElementHelper.getAttribuutElement(Element.PERSOON_NATIONALITEIT_TIJDSTIPREGISTRATIE.getId()),
                ElementHelper.getAttribuutElement(Element.PERSOON_NATIONALITEIT_TIJDSTIPVERVAL.getId()));
    }

    @Override
    public BrpNationaliteitInhoud mapInhoud(final MetaRecord identiteitRecord, final MetaRecord record, final OnderzoekMapper onderzoekMapper) {
        return new BrpNationaliteitInhoud(
                BrpMetaAttribuutMapper.mapBrpNationaliteitCode(identiteitRecord.getAttribuut(NATIONALITEITCODE_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(identiteitRecord.getVoorkomensleutel(), NATIONALITEITCODE_ELEMENT, true, record.getVoorkomensleutel(),
                                GROEP_ELEMENT)),
                BrpMetaAttribuutMapper.mapBrpRedenVerkrijgingNederlandschapCode(record.getAttribuut(REDEN_VERKRIJGING_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), REDEN_VERKRIJGING_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpRedenVerliesNederlanderschapCode(record.getAttribuut(REDEN_VERLIES_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), REDEN_VERLIES_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpBooleanJa(record.getAttribuut(INDICATIE_BIJHOUDING_BEEINDIGD_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), INDICATIE_BIJHOUDING_BEEINDIGD_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpDatum(record.getAttribuut(MIGRATIE_DATUM_EINDE_BIJHOUDING_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), MIGRATIE_DATUM_EINDE_BIJHOUDING_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpString(record.getAttribuut(MIGRATIE_REDEN_OPNAME_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), MIGRATIE_REDEN_OPNAME_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpString(record.getAttribuut(MIGRATIE_REDEN_BEEINDIGEN_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), MIGRATIE_REDEN_BEEINDIGEN_ELEMENT, true)));
    }

    @Override
    protected Integer getStapelNummer(final MetaRecord identiteitRecord, final MetaRecord record) {
        return (int) identiteitRecord.getVoorkomensleutel();
    }
}
