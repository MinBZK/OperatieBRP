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
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpOnderCurateleIndicatieInhoud;
import org.springframework.stereotype.Component;

/**
 * Mapt indicaties onder curatele.
 */
@Component
public final class PersoonIndicatiesOnderCurateleMapper extends AbstractMultipleMapper<BrpOnderCurateleIndicatieInhoud> {
    /**
     * Object element.
     */
    public static final ObjectElement OBJECT_ELEMENT = ElementHelper.getObjectElement(Element.PERSOON_INDICATIE_ONDERCURATELE.getId());

    private final PersoonIndicatieOnderCurateleMapper persoonIndicatieOnderCurateleMapper;

    /**
     * Constructor.
     * @param persoonIndicatieOnderCurateleMapper de mapper voor een enkele indicatie onder curatele
     */
    @Inject
    protected PersoonIndicatiesOnderCurateleMapper(final PersoonIndicatieOnderCurateleMapper persoonIndicatieOnderCurateleMapper) {
        super(OBJECT_ELEMENT);
        this.persoonIndicatieOnderCurateleMapper = persoonIndicatieOnderCurateleMapper;
    }

    @Override
    protected PersoonIndicatieOnderCurateleMapper getSingleMapper() {
        return persoonIndicatieOnderCurateleMapper;
    }
}
