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
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVerblijfsrechtInhoud;
import org.springframework.stereotype.Component;

/**
 * Mapt het verblijfsrecht.
 */
@Component
public final class PersoonVerblijfsrechtMapper extends AbstractMapper<BrpVerblijfsrechtInhoud> {

    /**
     * Groep element.
     */
    public static final GroepElement GROEP_ELEMENT = ElementHelper.getGroepElement(Element.PERSOON_VERBLIJFSRECHT.getId());

    private static final AttribuutElement AANDUIDING_ELEMENT =
            ElementHelper.getAttribuutElement(Element.PERSOON_VERBLIJFSRECHT_AANDUIDINGCODE.getId());
    private static final AttribuutElement DATUM_MEDEDELING_ELEMENT =
            ElementHelper.getAttribuutElement(Element.PERSOON_VERBLIJFSRECHT_DATUMMEDEDELING.getId());
    private static final AttribuutElement DATUM_AANVANG_ELEMENT =
            ElementHelper.getAttribuutElement(Element.PERSOON_VERBLIJFSRECHT_DATUMAANVANG.getId());
    private static final AttribuutElement DATUM_VOORZIEN_EINDE_ELEMENT =
            ElementHelper.getAttribuutElement(Element.PERSOON_VERBLIJFSRECHT_DATUMVOORZIENEINDE.getId());

    /**
     * Constructor.
     */
    public PersoonVerblijfsrechtMapper() {
        super(ElementHelper.getGroepElement(Element.PERSOON_IDENTITEIT.getId()),
                GROEP_ELEMENT,
                null,
                null,
                ElementHelper.getAttribuutElement(Element.PERSOON_VERBLIJFSRECHT_TIJDSTIPREGISTRATIE.getId()),
                ElementHelper.getAttribuutElement(Element.PERSOON_VERBLIJFSRECHT_TIJDSTIPVERVAL.getId()));
    }

    @Override
    public BrpVerblijfsrechtInhoud mapInhoud(final MetaRecord identiteitRecord, final MetaRecord record, final OnderzoekMapper onderzoekMapper) {
        return new BrpVerblijfsrechtInhoud(
                BrpMetaAttribuutMapper.mapBrpVerblijfsrechtCode(
                        record.getAttribuut(AANDUIDING_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), AANDUIDING_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpDatum(
                        record.getAttribuut(DATUM_MEDEDELING_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), DATUM_MEDEDELING_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpDatum(
                        record.getAttribuut(DATUM_VOORZIEN_EINDE_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), DATUM_VOORZIEN_EINDE_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpDatum(
                        record.getAttribuut(DATUM_AANVANG_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), DATUM_AANVANG_ELEMENT, true)));
    }
}
