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
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVerificatieInhoud;
import org.springframework.stereotype.Component;

/**
 * Mapt een verificatie.
 */
@Component
public final class VerificatieMapper extends AbstractMapper<BrpVerificatieInhoud> {

    /**
     * Identiteit groep element.
     */
    public static final GroepElement IDENTITEIT_GROEP_ELEMENT = ElementHelper.getGroepElement(Element.PERSOON_VERIFICATIE_IDENTITEIT.getId());
    /**
     * Groep element.
     */
    public static final GroepElement GROEP_ELEMENT = ElementHelper.getGroepElement(Element.PERSOON_VERIFICATIE_STANDAARD.getId());
    /**
     * Partij attribuut element.
     */
    public static final AttribuutElement
            PARTIJ_ELEMENT = ElementHelper.getAttribuutElement(Element.PERSOON_VERIFICATIE_PARTIJCODE.getId());

    private static final AttribuutElement SOORT_ELEMENT = ElementHelper.getAttribuutElement(Element.PERSOON_VERIFICATIE_SOORT.getId());
    private static final AttribuutElement DATUM_ELEMENT = ElementHelper.getAttribuutElement(Element.PERSOON_VERIFICATIE_DATUM.getId());

    /**
     * Constructor.
     */
    public VerificatieMapper() {
        super(IDENTITEIT_GROEP_ELEMENT,
                GROEP_ELEMENT,
                null,
                null,
                ElementHelper.getAttribuutElement(Element.PERSOON_VERIFICATIE_TIJDSTIPREGISTRATIE.getId()),
                ElementHelper.getAttribuutElement(Element.PERSOON_VERIFICATIE_TIJDSTIPVERVAL.getId()));
    }

    @Override
    public BrpVerificatieInhoud mapInhoud(final MetaRecord identiteitRecord, final MetaRecord record, final OnderzoekMapper onderzoekMapper) {
        final BrpPartijCode partijCode =
                BrpMetaAttribuutMapper.mapBrpPartijCode(
                        identiteitRecord.getAttribuut(PARTIJ_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(identiteitRecord.getVoorkomensleutel(), PARTIJ_ELEMENT, true));
        final BrpString soortVerificatie =
                BrpMetaAttribuutMapper.mapBrpString(
                        identiteitRecord.getAttribuut(SOORT_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(identiteitRecord.getVoorkomensleutel(), SOORT_ELEMENT, true));
        final BrpDatum datumVerificatie =
                BrpMetaAttribuutMapper.mapBrpDatum(
                        record.getAttribuut(DATUM_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), DATUM_ELEMENT, true));

        return new BrpVerificatieInhoud(partijCode, soortVerificatie, datumVerificatie);
    }
}
