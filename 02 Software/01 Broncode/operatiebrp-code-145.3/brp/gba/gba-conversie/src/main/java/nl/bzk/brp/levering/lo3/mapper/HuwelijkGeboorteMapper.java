/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.element.GroepElement;
import org.springframework.stereotype.Component;

/**
 * Mapt de geboorte (van een huwelijk).
 */
@Component
public final class HuwelijkGeboorteMapper extends AbstractGeboorteMapper {

    /**
     * Groep element.
     */
    public static final GroepElement GROEP_ELEMENT = ElementHelper.getGroepElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE.getId());

    /**
     * Constructor.
     */
    public HuwelijkGeboorteMapper() {
        super(ElementHelper.getGroepElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_IDENTITEIT.getId()),
                GROEP_ELEMENT,
                ElementHelper.getAttribuutElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE_TIJDSTIPREGISTRATIE.getId()),
                ElementHelper.getAttribuutElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE_TIJDSTIPVERVAL.getId()),
                ElementHelper.getAttribuutElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE_DATUM.getId()),
                ElementHelper.getAttribuutElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE_GEMEENTECODE.getId()),
                ElementHelper.getAttribuutElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE_WOONPLAATSNAAM.getId()),
                ElementHelper.getAttribuutElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE_BUITENLANDSEPLAATS.getId()),
                ElementHelper.getAttribuutElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE_BUITENLANDSEREGIO.getId()),
                ElementHelper.getAttribuutElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE_LANDGEBIEDCODE.getId()),
                ElementHelper.getAttribuutElement(Element.GERELATEERDEHUWELIJKSPARTNER_PERSOON_GEBOORTE_OMSCHRIJVINGLOCATIE.getId()));
    }
}
