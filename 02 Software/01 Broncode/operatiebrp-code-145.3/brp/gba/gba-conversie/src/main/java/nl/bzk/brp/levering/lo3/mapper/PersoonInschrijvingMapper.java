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
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpInschrijvingInhoud;
import org.springframework.stereotype.Component;

/**
 * Mapt de inschrijving.
 */
@Component
public final class PersoonInschrijvingMapper extends AbstractMapper<BrpInschrijvingInhoud> {

    /**
     * Groep element.
     */
    public static final GroepElement GROEP_ELEMENT = ElementHelper.getGroepElement(Element.PERSOON_INSCHRIJVING.getId());

    private static final AttribuutElement DATUMTIJDSTEMPEL_ELEMENT =
            ElementHelper.getAttribuutElement(Element.PERSOON_INSCHRIJVING_DATUMTIJDSTEMPEL.getId());
    private static final AttribuutElement VERSIENUMMER_ELEMENT =
            ElementHelper.getAttribuutElement(Element.PERSOON_INSCHRIJVING_VERSIENUMMER.getId());
    private static final AttribuutElement DATUM_ELEMENT = ElementHelper.getAttribuutElement(Element.PERSOON_INSCHRIJVING_DATUM.getId());

    /**
     * Constructor.
     */
    public PersoonInschrijvingMapper() {
        super(ElementHelper.getGroepElement(Element.PERSOON_IDENTITEIT.getId()),
                GROEP_ELEMENT,
                null,
                null,
                ElementHelper.getAttribuutElement(Element.PERSOON_INSCHRIJVING_TIJDSTIPREGISTRATIE.getId()),
                ElementHelper.getAttribuutElement(Element.PERSOON_INSCHRIJVING_TIJDSTIPVERVAL.getId()));
    }

    @Override
    public BrpInschrijvingInhoud mapInhoud(final MetaRecord identiteitRecord, final MetaRecord record, final OnderzoekMapper onderzoekMapper) {
        return new BrpInschrijvingInhoud(
                BrpMetaAttribuutMapper.mapBrpDatum(
                        record.getAttribuut(DATUM_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), DATUM_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpLong(
                        record.getAttribuut(VERSIENUMMER_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), VERSIENUMMER_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpDatumTijd(
                        record.getAttribuut(DATUMTIJDSTEMPEL_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), DATUMTIJDSTEMPEL_ELEMENT, true)));
    }
}
