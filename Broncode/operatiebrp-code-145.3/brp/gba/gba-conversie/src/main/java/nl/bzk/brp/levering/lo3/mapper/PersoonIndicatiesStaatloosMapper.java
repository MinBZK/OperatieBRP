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
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpStaatloosIndicatieInhoud;
import org.springframework.stereotype.Component;

/**
 * Mapt indicaties staatloos.
 */
@Component
public final class PersoonIndicatiesStaatloosMapper extends AbstractMultipleMapper<BrpStaatloosIndicatieInhoud> {
    /**
     * Object element.
     */
    public static final ObjectElement OBJECT_ELEMENT = ElementHelper.getObjectElement(Element.PERSOON_INDICATIE_STAATLOOS.getId());

    private final PersoonIndicatieStaatloosMapper singleMapper;

    /**
     * Constructor.
     * @param singleMapper de mapper voor een enkele indicatie staatloos
     */
    @Inject
    protected PersoonIndicatiesStaatloosMapper(final PersoonIndicatieStaatloosMapper singleMapper) {
        super(OBJECT_ELEMENT);
        this.singleMapper = singleMapper;
    }

    @Override
    protected PersoonIndicatieStaatloosMapper getSingleMapper() {
        return singleMapper;
    }
}
