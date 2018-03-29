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
 * Mapt de samengestelde naam (van een persoon).
 */
@Component
public final class PersoonSamengesteldeNaamMapper extends AbstractSamengesteldeNaamMapper {

    /**
     * Groep element.
     */
    public static final GroepElement GROEP_ELEMENT = ElementHelper.getGroepElement(Element.PERSOON_SAMENGESTELDENAAM.getId());

    /**
     * Constructor.
     */
    public PersoonSamengesteldeNaamMapper() {
        super(ElementHelper.getGroepElement(Element.PERSOON_IDENTITEIT.getId()),
                GROEP_ELEMENT,
                ElementHelper.getAttribuutElement(Element.PERSOON_SAMENGESTELDENAAM_DATUMAANVANGGELDIGHEID.getId()),
                ElementHelper.getAttribuutElement(Element.PERSOON_SAMENGESTELDENAAM_DATUMEINDEGELDIGHEID.getId()),
                ElementHelper.getAttribuutElement(Element.PERSOON_SAMENGESTELDENAAM_TIJDSTIPREGISTRATIE.getId()),
                ElementHelper.getAttribuutElement(Element.PERSOON_SAMENGESTELDENAAM_TIJDSTIPVERVAL.getId()),
                ElementHelper.getAttribuutElement(Element.PERSOON_SAMENGESTELDENAAM_PREDICAATCODE.getId()),
                ElementHelper.getAttribuutElement(Element.PERSOON_SAMENGESTELDENAAM_VOORNAMEN.getId()),
                ElementHelper.getAttribuutElement(Element.PERSOON_SAMENGESTELDENAAM_VOORVOEGSEL.getId()),
                ElementHelper.getAttribuutElement(Element.PERSOON_SAMENGESTELDENAAM_SCHEIDINGSTEKEN.getId()),
                ElementHelper.getAttribuutElement(Element.PERSOON_SAMENGESTELDENAAM_ADELLIJKETITELCODE.getId()),
                ElementHelper.getAttribuutElement(Element.PERSOON_SAMENGESTELDENAAM_GESLACHTSNAAMSTAM.getId()),
                ElementHelper.getAttribuutElement(Element.PERSOON_SAMENGESTELDENAAM_INDICATIENAMENREEKS.getId()),
                ElementHelper.getAttribuutElement(Element.PERSOON_SAMENGESTELDENAAM_INDICATIEAFGELEID.getId()));
    }
}
