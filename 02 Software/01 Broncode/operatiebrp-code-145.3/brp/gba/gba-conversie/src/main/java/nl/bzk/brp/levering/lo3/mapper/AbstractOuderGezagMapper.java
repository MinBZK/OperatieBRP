/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.element.GroepElement;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;
import org.springframework.stereotype.Component;

/**
 * Mapt een gezag voor ouder1.
 * @param <G> Conversie model groep inhoud type
 */
@Component
public abstract class AbstractOuderGezagMapper<G extends BrpGroepInhoud> extends AbstractMapper<G> {

    /**
     * Groep element.
     */
    public static final GroepElement GROEP_ELEMENT = ElementHelper.getGroepElement(Element.GERELATEERDEOUDER_OUDERLIJKGEZAG.getId());

    /**
     * Constructor.
     */
    public AbstractOuderGezagMapper() {
        super(ElementHelper.getGroepElement(Element.GERELATEERDEOUDER_IDENTITEIT.getId()),
                GROEP_ELEMENT,
                ElementHelper.getAttribuutElement(Element.GERELATEERDEOUDER_OUDERLIJKGEZAG_DATUMAANVANGGELDIGHEID.getId()),
                ElementHelper.getAttribuutElement(Element.GERELATEERDEOUDER_OUDERLIJKGEZAG_DATUMEINDEGELDIGHEID.getId()),
                ElementHelper.getAttribuutElement(Element.GERELATEERDEOUDER_OUDERLIJKGEZAG_TIJDSTIPREGISTRATIE.getId()),
                ElementHelper.getAttribuutElement(Element.GERELATEERDEOUDER_OUDERLIJKGEZAG_TIJDSTIPVERVAL.getId()));
    }

}
