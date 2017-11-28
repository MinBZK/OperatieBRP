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
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpUitsluitingKiesrechtInhoud;
import org.springframework.stereotype.Component;

/**
 * Mapt de uitsluiting kiesrecht.
 */
@Component
public final class PersoonUitsluitingKiesrechtMapper extends AbstractMapper<BrpUitsluitingKiesrechtInhoud> {

    /**
     * Groep element.
     */
    public static final GroepElement GROEP_ELEMENT = ElementHelper.getGroepElement(Element.PERSOON_UITSLUITINGKIESRECHT.getId());

    private static final AttribuutElement INDICATIE_ELEMENT =
            ElementHelper.getAttribuutElement(Element.PERSOON_UITSLUITINGKIESRECHT_INDICATIE.getId());
    private static final AttribuutElement DATUM_VOORZIEN_EINDE_ELEMENT
            =
            ElementHelper.getAttribuutElement(Element.PERSOON_UITSLUITINGKIESRECHT_DATUMVOORZIENEINDE.getId());

    /**
     * Constructor.
     */
    public PersoonUitsluitingKiesrechtMapper() {
        super(ElementHelper.getGroepElement(Element.PERSOON_IDENTITEIT.getId()),
                GROEP_ELEMENT,
                null,
                null,
                ElementHelper.getAttribuutElement(Element.PERSOON_UITSLUITINGKIESRECHT_TIJDSTIPREGISTRATIE.getId()),
                ElementHelper.getAttribuutElement(Element.PERSOON_UITSLUITINGKIESRECHT_TIJDSTIPVERVAL.getId()));
    }

    @Override
    public BrpUitsluitingKiesrechtInhoud mapInhoud(final MetaRecord identiteitRecord, final MetaRecord record, final OnderzoekMapper onderzoekMapper) {
        return new BrpUitsluitingKiesrechtInhoud(
                BrpMetaAttribuutMapper.mapBrpBooleanJa(
                        record.getAttribuut(INDICATIE_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), INDICATIE_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpDatum(
                        record.getAttribuut(DATUM_VOORZIEN_EINDE_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), DATUM_VOORZIEN_EINDE_ELEMENT, true)));
    }
}
