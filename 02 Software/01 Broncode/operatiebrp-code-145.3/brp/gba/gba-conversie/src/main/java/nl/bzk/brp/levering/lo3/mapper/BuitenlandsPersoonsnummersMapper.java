/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.element.ObjectElement;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBuitenlandsPersoonsnummerInhoud;
import org.springframework.stereotype.Component;

/**
 * Mapt de buitenlandspersoonsnummers.
 */
@Component
public final class BuitenlandsPersoonsnummersMapper extends AbstractMultipleMapper<BrpBuitenlandsPersoonsnummerInhoud> {

    /**
     * Groep element.
     */
    public static final ObjectElement OBJECT_ELEMENT = ElementHelper.getObjectElement(Element.PERSOON_BUITENLANDSPERSOONSNUMMER.getId());

    private final BuitenlandsPersoonsnummerMapper buitenlandsPersoonsnummerMapper;

    /**
     * Constructor.
     * @param buitenlandsPersoonsnummerMapper buitenlands persoonsnummer mapper
     */
    @Inject
    public BuitenlandsPersoonsnummersMapper(final BuitenlandsPersoonsnummerMapper buitenlandsPersoonsnummerMapper) {
        super(OBJECT_ELEMENT);
        this.buitenlandsPersoonsnummerMapper = buitenlandsPersoonsnummerMapper;
    }

    @Override
    protected BuitenlandsPersoonsnummerMapper getSingleMapper() {
        return buitenlandsPersoonsnummerMapper;
    }
}
