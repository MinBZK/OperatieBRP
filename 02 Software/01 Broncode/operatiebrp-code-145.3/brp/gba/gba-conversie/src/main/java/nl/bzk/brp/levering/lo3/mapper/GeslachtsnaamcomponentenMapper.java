/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.element.GroepElement;
import nl.bzk.brp.domain.element.ObjectElement;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeslachtsnaamcomponentInhoud;
import org.springframework.stereotype.Component;

/**
 * Mapt de geslachtsnaamcomponenten.
 */
@Component
public final class GeslachtsnaamcomponentenMapper extends AbstractMultipleMapper<BrpGeslachtsnaamcomponentInhoud> {

    /**
     * Groep element.
     */
    public static final GroepElement IDENTITEIT_ELEMENT = ElementHelper.getGroepElement(Element.PERSOON_ADRES_IDENTITEIT.getId());

    private static final ObjectElement OBJECT_ELEMENT = ElementHelper.getObjectElement(Element.PERSOON_GESLACHTSNAAMCOMPONENT.getId());


    private final GeslachtsnaamcomponentMapper geslachtsnaamcomponentMapper;

    /**
     * Constructor.
     * @param geslachtsnaamcomponentMapper geslachtsnaam component mapper
     */
    @Inject
    public GeslachtsnaamcomponentenMapper(final GeslachtsnaamcomponentMapper geslachtsnaamcomponentMapper) {
        super(OBJECT_ELEMENT);
        this.geslachtsnaamcomponentMapper = geslachtsnaamcomponentMapper;
    }

    @Override
    protected GeslachtsnaamcomponentMapper getSingleMapper() {
        return geslachtsnaamcomponentMapper;
    }
}
