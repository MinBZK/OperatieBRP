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
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeslachtsnaamcomponentInhoud;
import org.springframework.stereotype.Component;

/**
 * Mapt een geslachtsnaamcomponent.
 */
@Component
public final class GeslachtsnaamcomponentMapper extends AbstractMapper<BrpGeslachtsnaamcomponentInhoud> {

    /**
     * Groep element.
     */
    public static final GroepElement GROEP_ELEMENT = ElementHelper.getGroepElement(Element.PERSOON_GESLACHTSNAAMCOMPONENT_STANDAARD.getId());
    private static final AttribuutElement VOORVOEGSEL_ELEMENT = ElementHelper.getAttribuutElement(Element.PERSOON_GESLACHTSNAAMCOMPONENT_VOORVOEGSEL.getId());
    private static final AttribuutElement SCHEIDINGSTEKEN_ELEMENT =
            ElementHelper.getAttribuutElement(Element.PERSOON_GESLACHTSNAAMCOMPONENT_SCHEIDINGSTEKEN.getId());
    private static final AttribuutElement STAM_ELEMENT = ElementHelper.getAttribuutElement(Element.PERSOON_GESLACHTSNAAMCOMPONENT_STAM.getId());
    private static final AttribuutElement PREDICAATCODE_ELEMENT =
            ElementHelper.getAttribuutElement(Element.PERSOON_GESLACHTSNAAMCOMPONENT_PREDICAATCODE.getId());
    private static final AttribuutElement ADELLIJKETITELCODE_ELEMENT =
            ElementHelper.getAttribuutElement(Element.PERSOON_GESLACHTSNAAMCOMPONENT_ADELLIJKETITELCODE.getId());
    private static final AttribuutElement VOLGNUMMER_ELEMENT = ElementHelper.getAttribuutElement(Element.PERSOON_GESLACHTSNAAMCOMPONENT_VOLGNUMMER.getId());

    /**
     * Constructor.
     */
    public GeslachtsnaamcomponentMapper() {
        super(ElementHelper.getGroepElement(Element.PERSOON_GESLACHTSNAAMCOMPONENT_IDENTITEIT.getId()),
                GROEP_ELEMENT,
                ElementHelper.getAttribuutElement(Element.PERSOON_GESLACHTSNAAMCOMPONENT_DATUMAANVANGGELDIGHEID.getId()),
                ElementHelper.getAttribuutElement(Element.PERSOON_GESLACHTSNAAMCOMPONENT_DATUMEINDEGELDIGHEID.getId()),
                ElementHelper.getAttribuutElement(Element.PERSOON_GESLACHTSNAAMCOMPONENT_TIJDSTIPREGISTRATIE.getId()),
                ElementHelper.getAttribuutElement(Element.PERSOON_GESLACHTSNAAMCOMPONENT_TIJDSTIPVERVAL.getId()));
    }

    @Override
    public BrpGeslachtsnaamcomponentInhoud mapInhoud(final MetaRecord identiteitRecord, final MetaRecord record, final OnderzoekMapper onderzoekMapper) {
        return new BrpGeslachtsnaamcomponentInhoud(
                BrpMetaAttribuutMapper.mapBrpString(
                        record.getAttribuut(VOORVOEGSEL_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), VOORVOEGSEL_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpCharacterScheidingsteken(
                        record.getAttribuut(SCHEIDINGSTEKEN_ELEMENT),
                        record.getAttribuut(VOORVOEGSEL_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), SCHEIDINGSTEKEN_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpString(
                        record.getAttribuut(STAM_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), STAM_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpPredicaatCode(
                        record.getAttribuut(PREDICAATCODE_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), PREDICAATCODE_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpAdellijkeTitelCode(
                        record.getAttribuut(ADELLIJKETITELCODE_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), ADELLIJKETITELCODE_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpInteger(identiteitRecord.getAttribuut(VOLGNUMMER_ELEMENT), null));
    }
}
