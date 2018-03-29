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
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOverlijdenInhoud;
import org.springframework.stereotype.Component;

/**
 * Mapt het overlijden.
 */
@Component
public final class PersoonOverlijdenMapper extends AbstractMapper<BrpOverlijdenInhoud> {

    /**
     * Groep element.
     */
    public static final GroepElement GROEP_ELEMENT = ElementHelper.getGroepElement(Element.PERSOON_OVERLIJDEN.getId());

    private static final AttribuutElement DATUM_ELEMENT = ElementHelper.getAttribuutElement(Element.PERSOON_OVERLIJDEN_DATUM.getId());
    private static final AttribuutElement GEMEENTE_ELEMENT = ElementHelper
            .getAttribuutElement(Element.PERSOON_OVERLIJDEN_GEMEENTECODE.getId());
    private static final AttribuutElement WOONPLAATS_ELEMENT = ElementHelper
            .getAttribuutElement(Element.PERSOON_OVERLIJDEN_WOONPLAATSNAAM.getId());
    private static final AttribuutElement BUITENLANDSE_PLAATS_ELEMENT =
            ElementHelper.getAttribuutElement(Element.PERSOON_OVERLIJDEN_BUITENLANDSEPLAATS.getId());
    private static final AttribuutElement BUITENLANDSE_REGIO_ELEMENT =
            ElementHelper.getAttribuutElement(Element.PERSOON_OVERLIJDEN_BUITENLANDSEREGIO.getId());
    private static final AttribuutElement LAND_ELEMENT = ElementHelper
            .getAttribuutElement(Element.PERSOON_OVERLIJDEN_LANDGEBIEDCODE.getId());
    private static final AttribuutElement OMSCHRIJVING_LOCATIE_ELEMENT =
            ElementHelper.getAttribuutElement(Element.PERSOON_OVERLIJDEN_OMSCHRIJVINGLOCATIE.getId());

    /**
     * Constructor.
     */
    public PersoonOverlijdenMapper() {
        super(ElementHelper.getGroepElement(Element.PERSOON_IDENTITEIT.getId()),
                GROEP_ELEMENT,
                null,
                null,
                ElementHelper.getAttribuutElement(Element.PERSOON_OVERLIJDEN_TIJDSTIPREGISTRATIE.getId()),
                ElementHelper.getAttribuutElement(Element.PERSOON_OVERLIJDEN_TIJDSTIPVERVAL.getId()));
    }

    @Override
    public BrpOverlijdenInhoud mapInhoud(final MetaRecord identiteitRecord, final MetaRecord record, final OnderzoekMapper onderzoekMapper) {
        return new BrpOverlijdenInhoud(
                BrpMetaAttribuutMapper.mapBrpDatum(
                        record.getAttribuut(DATUM_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), DATUM_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpGemeenteCode(
                        record.getAttribuut(GEMEENTE_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), GEMEENTE_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpString(
                        record.getAttribuut(WOONPLAATS_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), WOONPLAATS_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpString(
                        record.getAttribuut(BUITENLANDSE_PLAATS_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), BUITENLANDSE_PLAATS_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpString(
                        record.getAttribuut(BUITENLANDSE_REGIO_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), BUITENLANDSE_REGIO_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpLandOfGebiedCode(
                        record.getAttribuut(LAND_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), LAND_ELEMENT, true)),
                BrpMetaAttribuutMapper.mapBrpString(
                        record.getAttribuut(OMSCHRIJVING_LOCATIE_ELEMENT),
                        onderzoekMapper.bepaalOnderzoek(record.getVoorkomensleutel(), OMSCHRIJVING_LOCATIE_ELEMENT, true)));
    }
}
