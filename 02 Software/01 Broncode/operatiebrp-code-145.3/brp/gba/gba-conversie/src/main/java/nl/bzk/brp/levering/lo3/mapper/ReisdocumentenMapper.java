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
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpReisdocumentInhoud;
import org.springframework.stereotype.Component;

/**
 * Mapt de reisdocumenten.
 */
@Component
public final class ReisdocumentenMapper extends AbstractMultipleMapper<BrpReisdocumentInhoud> {

    /**
     * Groep element.
     */
    public static final ObjectElement OBJECT_ELEMENT = ElementHelper.getObjectElement(Element.PERSOON_REISDOCUMENT.getId());

    private final ReisdocumentMapper reisdocumentMapper;

    /**
     * Constructor.
     * @param reisdocumentMapper reisdocument mapper
     */
    @Inject
    protected ReisdocumentenMapper(final ReisdocumentMapper reisdocumentMapper) {
        super(OBJECT_ELEMENT);
        this.reisdocumentMapper = reisdocumentMapper;
    }

    @Override
    protected ReisdocumentMapper getSingleMapper() {
        return reisdocumentMapper;
    }
}
