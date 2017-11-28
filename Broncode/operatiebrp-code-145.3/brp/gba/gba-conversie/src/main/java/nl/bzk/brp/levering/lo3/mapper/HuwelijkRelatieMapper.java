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
import org.springframework.stereotype.Component;

/**
 * Mapt een relatie (van een huwelijk).
 */
@Component
public final class HuwelijkRelatieMapper extends AbstractRelatieMapper {

    /**
     * Identiteit groep element.
     */
    public static final GroepElement IDENTITEIT_GROEP_ELEMENT = ElementHelper.getGroepElement(Element.HUWELIJK_IDENTITEIT.getId());
    /**
     * Groep element.
     */
    public static final GroepElement GROEP_ELEMENT = ElementHelper.getGroepElement(Element.HUWELIJK_STANDAARD.getId());
    /**
     * Reden einde attribuut element.
     */
    public static final AttribuutElement REDEN_EINDE_ELEMENT = ElementHelper.getAttribuutElement(Element.HUWELIJK_REDENEINDECODE.getId());

    /**
     * Constructor.
     */
    public HuwelijkRelatieMapper() {
        super(IDENTITEIT_GROEP_ELEMENT,
                GROEP_ELEMENT,
                ElementHelper.getAttribuutElement(Element.HUWELIJK_TIJDSTIPREGISTRATIE.getId()),
                ElementHelper.getAttribuutElement(Element.HUWELIJK_TIJDSTIPVERVAL.getId()),
                ElementHelper.getAttribuutElement(Element.HUWELIJK_DATUMAANVANG.getId()),
                ElementHelper.getAttribuutElement(Element.HUWELIJK_GEMEENTEAANVANGCODE.getId()),
                ElementHelper.getAttribuutElement(Element.HUWELIJK_WOONPLAATSNAAMAANVANG.getId()),
                ElementHelper.getAttribuutElement(Element.HUWELIJK_BUITENLANDSEPLAATSAANVANG.getId()),
                ElementHelper.getAttribuutElement(Element.HUWELIJK_BUITENLANDSEREGIOAANVANG.getId()),
                ElementHelper.getAttribuutElement(Element.HUWELIJK_LANDGEBIEDAANVANGCODE.getId()),
                ElementHelper.getAttribuutElement(Element.HUWELIJK_OMSCHRIJVINGLOCATIEAANVANG.getId()),
                REDEN_EINDE_ELEMENT,
                ElementHelper.getAttribuutElement(Element.HUWELIJK_DATUMEINDE.getId()),
                ElementHelper.getAttribuutElement(Element.HUWELIJK_GEMEENTEEINDECODE.getId()),
                ElementHelper.getAttribuutElement(Element.HUWELIJK_WOONPLAATSNAAMEINDE.getId()),
                ElementHelper.getAttribuutElement(Element.HUWELIJK_BUITENLANDSEPLAATSEINDE.getId()),
                ElementHelper.getAttribuutElement(Element.HUWELIJK_BUITENLANDSEREGIOEINDE.getId()),
                ElementHelper.getAttribuutElement(Element.HUWELIJK_LANDGEBIEDEINDECODE.getId()),
                ElementHelper.getAttribuutElement(Element.HUWELIJK_OMSCHRIJVINGLOCATIEEINDE.getId()));
    }
}
