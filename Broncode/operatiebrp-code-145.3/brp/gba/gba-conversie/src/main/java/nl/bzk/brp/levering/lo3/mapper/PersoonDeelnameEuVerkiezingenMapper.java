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
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpDeelnameEuVerkiezingenInhoud;
import org.springframework.stereotype.Component;

/**
 * Mapt de deelname EU verkiezingen.
 */
@Component
public final class PersoonDeelnameEuVerkiezingenMapper extends AbstractMapper<BrpDeelnameEuVerkiezingenInhoud> {

    /**
     * Groep element.
     */
    public static final GroepElement GROEP_ELEMENT = ElementHelper.getGroepElement(Element.PERSOON_DEELNAMEEUVERKIEZINGEN.getId());

    private static final AttribuutElement INDICATIE_DEELNAME_ELEMENT =
            ElementHelper.getAttribuutElement(Element.PERSOON_DEELNAMEEUVERKIEZINGEN_INDICATIEDEELNAME.getId());
    private static final AttribuutElement DATUM_AANLEIDING_AANPASSING_ELEMENT
            =
            ElementHelper.getAttribuutElement(Element.PERSOON_DEELNAMEEUVERKIEZINGEN_DATUMAANLEIDINGAANPASSING.getId());
    private static final AttribuutElement DATUM_VOORZIEN_EINDE_UITSLUITING_ELEMENT =
            ElementHelper.getAttribuutElement(Element.PERSOON_DEELNAMEEUVERKIEZINGEN_DATUMVOORZIENEINDEUITSLUITING.getId());

    /**
     * Constructor.
     */
    public PersoonDeelnameEuVerkiezingenMapper() {
        super(ElementHelper.getGroepElement(Element.PERSOON_IDENTITEIT.getId()),
                GROEP_ELEMENT,
                null,
                null,
                ElementHelper.getAttribuutElement(Element.PERSOON_DEELNAMEEUVERKIEZINGEN_TIJDSTIPREGISTRATIE.getId()),
                ElementHelper.getAttribuutElement(Element.PERSOON_DEELNAMEEUVERKIEZINGEN_TIJDSTIPVERVAL.getId()));
    }

    @Override
    public BrpDeelnameEuVerkiezingenInhoud mapInhoud(final MetaRecord identiteitRecord, final MetaRecord record, final OnderzoekMapper onderzoekMapper) {
        return new BrpDeelnameEuVerkiezingenInhoud(
                BrpMetaAttribuutMapper.mapBrpBooleanJaNee(
                        record.getAttribuut(INDICATIE_DEELNAME_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), INDICATIE_DEELNAME_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpDatum(
                        record.getAttribuut(DATUM_AANLEIDING_AANPASSING_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), DATUM_AANLEIDING_AANPASSING_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpDatum(
                        record.getAttribuut(DATUM_VOORZIEN_EINDE_UITSLUITING_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), DATUM_VOORZIEN_EINDE_UITSLUITING_ELEMENT, true)));
    }
}
