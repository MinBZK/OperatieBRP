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
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBijhoudingInhoud;
import org.springframework.stereotype.Component;

/**
 * Mapt de bijhouding.
 */
@Component
public final class PersoonBijhoudingMapper extends AbstractMapper<BrpBijhoudingInhoud> {

    /**
     * Groep element.
     */
    public static final GroepElement GROEP_ELEMENT = ElementHelper.getGroepElement(Element.PERSOON_BIJHOUDING.getId());
    /**
     * Datum aanvang geldigheid attribuut element.
     */
    public static final AttribuutElement
            DATUM_AANVANG_GELDIGHEID_ELEMENT =
            ElementHelper.getAttribuutElement(Element.PERSOON_BIJHOUDING_DATUMAANVANGGELDIGHEID.getId());

    private static final AttribuutElement PARTIJ_ELEMENT = ElementHelper
            .getAttribuutElement(Element.PERSOON_BIJHOUDING_PARTIJCODE.getId());
    private static final AttribuutElement BIJHOUDINGSAARD_ELEMENT =
            ElementHelper.getAttribuutElement(Element.PERSOON_BIJHOUDING_BIJHOUDINGSAARDCODE.getId());
    private static final AttribuutElement NADERE_BIJHOUDINGSAARD_ELEMENT =
            ElementHelper.getAttribuutElement(Element.PERSOON_BIJHOUDING_NADEREBIJHOUDINGSAARDCODE.getId());

    /**
     * Constructor.
     */
    public PersoonBijhoudingMapper() {
        super(ElementHelper.getGroepElement(Element.PERSOON_IDENTITEIT.getId()),
                GROEP_ELEMENT,
                DATUM_AANVANG_GELDIGHEID_ELEMENT,
                ElementHelper.getAttribuutElement(Element.PERSOON_BIJHOUDING_DATUMEINDEGELDIGHEID.getId()),
                ElementHelper.getAttribuutElement(Element.PERSOON_BIJHOUDING_TIJDSTIPREGISTRATIE.getId()),
                ElementHelper.getAttribuutElement(Element.PERSOON_BIJHOUDING_TIJDSTIPVERVAL.getId()));
    }

    /**
     * Map inhoud.
     * @param identiteitRecord identiteits record
     * @param record meta record
     * @param onderzoekMapper onderzoek mapper
     * @return de brpBijhoudingsaardInhoud.
     */
    @Override
    public BrpBijhoudingInhoud mapInhoud(final MetaRecord identiteitRecord, final MetaRecord record, final OnderzoekMapper onderzoekMapper) {
        return new BrpBijhoudingInhoud(
                BrpMetaAttribuutMapper.mapBrpPartijCode(
                        record.getAttribuut(PARTIJ_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), PARTIJ_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpBijhoudingsaardCode(
                        record.getAttribuut(BIJHOUDINGSAARD_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), BIJHOUDINGSAARD_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpNadereBijhoudingsaardCode(
                        record.getAttribuut(NADERE_BIJHOUDINGSAARD_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), NADERE_BIJHOUDINGSAARD_ELEMENT, true)));
    }

}
