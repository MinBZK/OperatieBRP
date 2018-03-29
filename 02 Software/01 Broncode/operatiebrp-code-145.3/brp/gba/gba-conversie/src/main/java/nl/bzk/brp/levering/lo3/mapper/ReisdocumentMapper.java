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
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpReisdocumentInhoud;
import org.springframework.stereotype.Component;

/**
 * Mapt een reisdocument.
 */
@Component
public final class ReisdocumentMapper extends AbstractMapper<BrpReisdocumentInhoud> {

    /**
     * Identiteit groep element.
     */
    public static final GroepElement IDENTITEIT_GROEP_ELEMENT = ElementHelper.getGroepElement(Element.PERSOON_REISDOCUMENT_IDENTITEIT.getId());
    /**
     * Groep element.
     */
    public static final GroepElement GROEP_ELEMENT = ElementHelper.getGroepElement(Element.PERSOON_REISDOCUMENT_STANDAARD.getId());
    /**
     * Datum einde document attribuut element.
     */
    public static final AttribuutElement DATUM_EINDE_DOCUMENT_ELEMENT =
            ElementHelper.getAttribuutElement(Element.PERSOON_REISDOCUMENT_DATUMEINDEDOCUMENT.getId());
    private static final AttribuutElement SOORT_ELEMENT = ElementHelper.getAttribuutElement(Element.PERSOON_REISDOCUMENT_SOORTCODE.getId());

    private static final AttribuutElement NUMMER_ELEMENT = ElementHelper.getAttribuutElement(Element.PERSOON_REISDOCUMENT_NUMMER.getId());
    private static final AttribuutElement DATUM_INGANG_DOCUMENT_ELEMENT =
            ElementHelper.getAttribuutElement(Element.PERSOON_REISDOCUMENT_DATUMINGANGDOCUMENT.getId());
    private static final AttribuutElement DATUM_UITGIFTE_ELEMENT = ElementHelper.getAttribuutElement(Element.PERSOON_REISDOCUMENT_DATUMUITGIFTE.getId());
    private static final AttribuutElement AUTORITEIT_VAN_AFGIFTE_ELEMENT =
            ElementHelper.getAttribuutElement(Element.PERSOON_REISDOCUMENT_AUTORITEITVANAFGIFTE.getId());

    private static final AttribuutElement DATUM_INHOUDING_VERMISSING_ELEMENT =
            ElementHelper.getAttribuutElement(Element.PERSOON_REISDOCUMENT_DATUMINHOUDINGVERMISSING.getId());
    private static final AttribuutElement AANDUIDING_INHOUDING_VERMISSING_ELEMENT =
            ElementHelper.getAttribuutElement(Element.PERSOON_REISDOCUMENT_AANDUIDINGINHOUDINGVERMISSINGCODE.getId());

    /**
     * Constructor.
     */
    public ReisdocumentMapper() {
        super(IDENTITEIT_GROEP_ELEMENT, GROEP_ELEMENT, null, null, ElementHelper.getAttribuutElement(Element.PERSOON_REISDOCUMENT_TIJDSTIPREGISTRATIE.getId()),
                ElementHelper.getAttribuutElement(Element.PERSOON_REISDOCUMENT_TIJDSTIPVERVAL.getId()));
    }

    @Override
    public BrpReisdocumentInhoud mapInhoud(final MetaRecord identiteitRecord, final MetaRecord record, final OnderzoekMapper onderzoekMapper) {
        return new BrpReisdocumentInhoud(
                BrpMetaAttribuutMapper.mapBrpSoortNederlandsReisdocumentCode(identiteitRecord.getAttribuut(SOORT_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(identiteitRecord.getVoorkomensleutel(), SOORT_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpString(record.getAttribuut(NUMMER_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), NUMMER_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpDatum(record.getAttribuut(DATUM_INGANG_DOCUMENT_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), DATUM_INGANG_DOCUMENT_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpDatum(record.getAttribuut(DATUM_UITGIFTE_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), DATUM_UITGIFTE_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpReisdocumentAutoriteitVanAfgifteCode(record.getAttribuut(AUTORITEIT_VAN_AFGIFTE_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), AUTORITEIT_VAN_AFGIFTE_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpDatum(record.getAttribuut(DATUM_EINDE_DOCUMENT_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), DATUM_EINDE_DOCUMENT_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpDatum(record.getAttribuut(DATUM_INHOUDING_VERMISSING_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), DATUM_INHOUDING_VERMISSING_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpAanduidingInhoudingOfVermissingReisdocumentCode(record.getAttribuut(AANDUIDING_INHOUDING_VERMISSING_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), AANDUIDING_INHOUDING_VERMISSING_ELEMENT, true)));
    }

    @Override
    protected Integer getStapelNummer(final MetaRecord identiteitRecord, final MetaRecord record) {
        return (int) identiteitRecord.getVoorkomensleutel();
    }
}
