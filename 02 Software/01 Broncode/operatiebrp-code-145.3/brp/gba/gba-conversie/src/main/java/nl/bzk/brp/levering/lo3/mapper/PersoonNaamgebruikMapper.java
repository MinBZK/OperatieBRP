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
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNaamgebruikInhoud;
import org.springframework.stereotype.Component;

/**
 * Mapt het naamgebruik.
 */
@Component
public final class PersoonNaamgebruikMapper extends AbstractMapper<BrpNaamgebruikInhoud> {

    /**
     * Groep element.
     */
    public static final GroepElement GROEP_ELEMENT = ElementHelper.getGroepElement(Element.PERSOON_NAAMGEBRUIK.getId());

    private static final AttribuutElement NAAMGEBRUIK_ELEMENT = ElementHelper.getAttribuutElement(Element.PERSOON_NAAMGEBRUIK_CODE.getId());
    private static final AttribuutElement INDICATIE_AFGELEID_ELEMENT =
            ElementHelper.getAttribuutElement(Element.PERSOON_NAAMGEBRUIK_INDICATIEAFGELEID.getId());
    private static final AttribuutElement PREDICAAT_ELEMENT = ElementHelper.getAttribuutElement(Element.PERSOON_NAAMGEBRUIK_PREDICAATCODE.getId());
    private static final AttribuutElement ADELLIJKE_TITEL_ELEMENT =
            ElementHelper.getAttribuutElement(Element.PERSOON_NAAMGEBRUIK_ADELLIJKETITELCODE.getId());
    private static final AttribuutElement VOORNAMEN_ELEMENT = ElementHelper.getAttribuutElement(Element.PERSOON_NAAMGEBRUIK_VOORNAMEN.getId());
    private static final AttribuutElement VOORVOEGSEL_ELEMENT = ElementHelper.getAttribuutElement(Element.PERSOON_NAAMGEBRUIK_VOORVOEGSEL.getId());
    private static final AttribuutElement SCHEIDINGSTEKEN_ELEMENT = ElementHelper.getAttribuutElement(Element.PERSOON_NAAMGEBRUIK_SCHEIDINGSTEKEN.getId());
    private static final AttribuutElement STAM_ELEMENT = ElementHelper.getAttribuutElement(Element.PERSOON_NAAMGEBRUIK_GESLACHTSNAAMSTAM.getId());

    /**
     * Constructor.
     */
    public PersoonNaamgebruikMapper() {
        super(ElementHelper.getGroepElement(Element.PERSOON_IDENTITEIT.getId()),
                GROEP_ELEMENT,
                null,
                null,
                ElementHelper.getAttribuutElement(Element.PERSOON_NAAMGEBRUIK_TIJDSTIPREGISTRATIE.getId()),
                ElementHelper.getAttribuutElement(Element.PERSOON_NAAMGEBRUIK_TIJDSTIPVERVAL.getId()));
    }

    @Override
    public BrpNaamgebruikInhoud mapInhoud(final MetaRecord identiteitRecord, final MetaRecord record, final OnderzoekMapper onderzoekMapper) {
        return new BrpNaamgebruikInhoud(
                BrpMetaAttribuutMapper.mapBrpNaamgebruikCode(
                        record.getAttribuut(NAAMGEBRUIK_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), NAAMGEBRUIK_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpBooleanJaNee(
                        record.getAttribuut(INDICATIE_AFGELEID_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), INDICATIE_AFGELEID_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpPredicaatCode(
                        record.getAttribuut(PREDICAAT_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), PREDICAAT_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpAdellijkeTitelCode(
                        record.getAttribuut(ADELLIJKE_TITEL_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), ADELLIJKE_TITEL_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpString(
                        record.getAttribuut(VOORNAMEN_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), VOORNAMEN_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpString(
                        record.getAttribuut(VOORVOEGSEL_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), VOORVOEGSEL_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpCharacterScheidingsteken(
                        record.getAttribuut(SCHEIDINGSTEKEN_ELEMENT),
                        record.getAttribuut(VOORVOEGSEL_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), SCHEIDINGSTEKEN_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpNaamgebruikGeslachtsnaamstam(
                        record.getAttribuut(STAM_ELEMENT),
                        onderzoekMapper.bepaalOnderzoeken(record.getVoorkomensleutel(), STAM_ELEMENT, true)));

    }

}
