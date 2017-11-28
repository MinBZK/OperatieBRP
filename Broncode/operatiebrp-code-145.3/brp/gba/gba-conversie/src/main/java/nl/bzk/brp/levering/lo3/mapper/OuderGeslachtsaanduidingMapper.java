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
 * Mapt de geslachtsaanduiding (van een ouder).
 */
@Component
public final class OuderGeslachtsaanduidingMapper extends AbstractGeslachtsaanduidingMapper {

    /**
     * Groep element.
     */
    public static final GroepElement GROEP_ELEMENT = ElementHelper.getGroepElement(Element.GERELATEERDEOUDER_PERSOON_GESLACHTSAANDUIDING.getId());

    /**
     * Constructor.
     */
    public OuderGeslachtsaanduidingMapper() {
        super(ElementHelper.getGroepElement(Element.GERELATEERDEOUDER_PERSOON_IDENTITEIT.getId()),
                GROEP_ELEMENT,
                ElementHelper.getAttribuutElement(Element.GERELATEERDEOUDER_PERSOON_GESLACHTSAANDUIDING_DATUMAANVANGGELDIGHEID.getId()),
                ElementHelper.getAttribuutElement(Element.GERELATEERDEOUDER_PERSOON_GESLACHTSAANDUIDING_DATUMEINDEGELDIGHEID.getId()),
                ElementHelper.getAttribuutElement(Element.GERELATEERDEOUDER_PERSOON_GESLACHTSAANDUIDING_TIJDSTIPREGISTRATIE.getId()),
                ElementHelper.getAttribuutElement(Element.GERELATEERDEOUDER_PERSOON_GESLACHTSAANDUIDING_TIJDSTIPVERVAL.getId()),
                ElementHelper.getAttribuutElement(Element.GERELATEERDEOUDER_PERSOON_GESLACHTSAANDUIDING_CODE.getId()));
    }
}
