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
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpPersoonskaartInhoud;
import org.springframework.stereotype.Component;

/**
 * Mapt de persoonskaart.
 */
@Component
public final class PersoonPersoonskaartMapper extends AbstractMapper<BrpPersoonskaartInhoud> {

    /**
     * Groep element.
     */
    public static final GroepElement GROEP_ELEMENT = ElementHelper.getGroepElement(Element.PERSOON_PERSOONSKAART.getId());

    private static final AttribuutElement INDICATIE_VOLLEDIG_GECONVERTEERD_ELEMENT =
            ElementHelper.getAttribuutElement(Element.PERSOON_PERSOONSKAART_INDICATIEVOLLEDIGGECONVERTEERD.getId());
    private static final AttribuutElement PARTIJCODE_ELEMENT = ElementHelper
            .getAttribuutElement(Element.PERSOON_PERSOONSKAART_PARTIJCODE.getId());

    /**
     * Constructor.
     */
    public PersoonPersoonskaartMapper() {
        super(ElementHelper.getGroepElement(Element.PERSOON_IDENTITEIT.getId()),
                GROEP_ELEMENT,
                null,
                null,
                ElementHelper.getAttribuutElement(Element.PERSOON_PERSOONSKAART_TIJDSTIPREGISTRATIE.getId()),
                ElementHelper.getAttribuutElement(Element.PERSOON_PERSOONSKAART_TIJDSTIPVERVAL.getId()));
    }

    @Override
    public BrpPersoonskaartInhoud mapInhoud(final MetaRecord identiteitRecord, final MetaRecord record, final OnderzoekMapper onderzoekMapper) {
        return new BrpPersoonskaartInhoud(
                BrpMetaAttribuutMapper.mapBrpPartijCode(
                        record.getAttribuut(PARTIJCODE_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), PARTIJCODE_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpBooleanJaNee(
                        record.getAttribuut(INDICATIE_VOLLEDIG_GECONVERTEERD_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), INDICATIE_VOLLEDIG_GECONVERTEERD_ELEMENT, true)));
    }
}
