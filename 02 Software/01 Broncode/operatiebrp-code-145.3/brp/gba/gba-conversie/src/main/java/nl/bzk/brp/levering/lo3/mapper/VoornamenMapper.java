/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.mapper;

import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpVoornaamInhoud;
import org.springframework.stereotype.Component;

/**
 * Mapt de voornamen.
 */
@Component
public final class VoornamenMapper extends AbstractMultipleMapper<BrpVoornaamInhoud> {

    private final VoornaamMapper voornaamMapper;

    /**
     * Constructor.
     * @param voornaamMapper voornaam mapper
     */
    @Inject
    protected VoornamenMapper(final VoornaamMapper voornaamMapper) {
        super(ElementHelper.getObjectElement(Element.PERSOON_VOORNAAM.getId()));
        this.voornaamMapper = voornaamMapper;
    }

    @Override
    protected VoornaamMapper getSingleMapper() {
        return voornaamMapper;
    }
}
