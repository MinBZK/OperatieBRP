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
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVoornaamInhoud;
import org.springframework.stereotype.Component;

/**
 * Mapt een voornaam.
 */
@Component
public final class VoornaamMapper extends AbstractMapper<BrpVoornaamInhoud> {

    /**
     * Groep element.
     */
    public static final GroepElement GROEP_ELEMENT = ElementHelper.getGroepElement(Element.PERSOON_VOORNAAM_STANDAARD.getId());

    private static final AttribuutElement VOLGNUMMER_ELEMENT = ElementHelper.getAttribuutElement(Element.PERSOON_VOORNAAM_VOLGNUMMER.getId());
    private static final AttribuutElement NAAM_ELEMENT = ElementHelper.getAttribuutElement(Element.PERSOON_VOORNAAM_NAAM.getId());

    /**
     * Constructor.
     */
    public VoornaamMapper() {
        super(ElementHelper.getGroepElement(Element.PERSOON_VOORNAAM_IDENTITEIT.getId()),
                GROEP_ELEMENT,
                ElementHelper.getAttribuutElement(Element.PERSOON_VOORNAAM_DATUMAANVANGGELDIGHEID.getId()),
                ElementHelper.getAttribuutElement(Element.PERSOON_VOORNAAM_DATUMEINDEGELDIGHEID.getId()),
                ElementHelper.getAttribuutElement(Element.PERSOON_VOORNAAM_TIJDSTIPREGISTRATIE.getId()),
                ElementHelper.getAttribuutElement(Element.PERSOON_VOORNAAM_TIJDSTIPVERVAL.getId()));
    }

    @Override
    public BrpVoornaamInhoud mapInhoud(final MetaRecord identiteitRecord, final MetaRecord record, final OnderzoekMapper onderzoekMapper) {
        return new BrpVoornaamInhoud(
                BrpMetaAttribuutMapper.mapBrpString(
                        record.getAttribuut(NAAM_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), NAAM_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpInteger(
                        identiteitRecord.getAttribuut(VOLGNUMMER_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(identiteitRecord.getVoorkomensleutel(), VOLGNUMMER_ELEMENT, true)));
    }

}
