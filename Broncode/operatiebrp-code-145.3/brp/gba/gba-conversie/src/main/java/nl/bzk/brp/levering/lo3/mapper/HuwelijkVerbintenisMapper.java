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
 * Mapt een verbintenis (van een huwelijk).
 */
@Component
public final class HuwelijkVerbintenisMapper extends AbstractVerbintenisMapper {

    /**
     * Identiteit groep element.
     */
    public static final GroepElement IDENTITEIT_GROEP_ELEMENT = ElementHelper.getGroepElement(Element.HUWELIJK_IDENTITEIT.getId());
    /**
     * Groep element.
     */
    public static final GroepElement GROEP_ELEMENT = ElementHelper.getGroepElement(Element.HUWELIJK_STANDAARD.getId());

    /**
     * Constructor.
     */
    public HuwelijkVerbintenisMapper() {
        super(IDENTITEIT_GROEP_ELEMENT,
                GROEP_ELEMENT,
                ElementHelper.getAttribuutElement(Element.HUWELIJK_TIJDSTIPREGISTRATIE.getId()),
                ElementHelper.getAttribuutElement(Element.HUWELIJK_TIJDSTIPVERVAL.getId()),
                ElementHelper.getAttribuutElement(Element.HUWELIJK_SOORTCODE.getId()));
    }
}
