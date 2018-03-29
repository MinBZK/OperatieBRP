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
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNummerverwijzingInhoud;
import org.springframework.stereotype.Component;

/**
 * Mapt de nummerverwijzing.
 */
@Component
public final class PersoonNummerverwijzingMapper extends AbstractMapper<BrpNummerverwijzingInhoud> {

    /**
     * Groep element.
     */
    public static final GroepElement GROEP_ELEMENT = ElementHelper.getGroepElement(Element.PERSOON_NUMMERVERWIJZING.getId());

    private static final AttribuutElement VORIGE_ANR_ELEMENT =
            ElementHelper.getAttribuutElement(Element.PERSOON_NUMMERVERWIJZING_VORIGEADMINISTRATIENUMMER.getId());
    private static final AttribuutElement VOLGENDE_ANR_ELEMENT =
            ElementHelper.getAttribuutElement(Element.PERSOON_NUMMERVERWIJZING_VOLGENDEADMINISTRATIENUMMER.getId());
    private static final AttribuutElement VORIGE_BSN_ELEMENT =
            ElementHelper.getAttribuutElement(Element.PERSOON_NUMMERVERWIJZING_VORIGEBURGERSERVICENUMMER.getId());
    private static final AttribuutElement VOLGENDE_BSN_ELEMENT =
            ElementHelper.getAttribuutElement(Element.PERSOON_NUMMERVERWIJZING_VOLGENDEBURGERSERVICENUMMER.getId());

    /**
     * Constructor.
     */
    public PersoonNummerverwijzingMapper() {
        super(ElementHelper.getGroepElement(Element.PERSOON_IDENTITEIT.getId()),
                GROEP_ELEMENT,
                ElementHelper.getAttribuutElement(Element.PERSOON_NUMMERVERWIJZING_DATUMAANVANGGELDIGHEID.getId()),
                ElementHelper.getAttribuutElement(Element.PERSOON_NUMMERVERWIJZING_DATUMEINDEGELDIGHEID.getId()),
                ElementHelper.getAttribuutElement(Element.PERSOON_NUMMERVERWIJZING_TIJDSTIPREGISTRATIE.getId()),
                ElementHelper.getAttribuutElement(Element.PERSOON_NUMMERVERWIJZING_TIJDSTIPVERVAL.getId()));
    }

    @Override
    public BrpNummerverwijzingInhoud mapInhoud(final MetaRecord identiteitRecord, final MetaRecord record, final OnderzoekMapper onderzoekMapper) {
        return new BrpNummerverwijzingInhoud(
                BrpMetaAttribuutMapper.mapBrpString(
                        record.getAttribuut(VORIGE_ANR_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), VORIGE_ANR_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpString(
                        record.getAttribuut(VOLGENDE_ANR_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), VOLGENDE_ANR_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpString(
                        record.getAttribuut(VORIGE_BSN_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), VORIGE_BSN_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpString(
                        record.getAttribuut(VOLGENDE_BSN_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), VOLGENDE_BSN_ELEMENT, true)));
    }
}
