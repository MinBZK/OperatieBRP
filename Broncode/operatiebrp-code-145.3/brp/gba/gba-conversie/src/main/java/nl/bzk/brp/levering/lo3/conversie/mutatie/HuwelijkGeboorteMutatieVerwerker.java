/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.levering.lo3.mapper.HuwelijkGeboorteMapper;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3HuwelijkOfGpInhoud;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpAttribuutConverteerder;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpHuwelijkConverteerder.GeboorteConverteerder;
import org.springframework.stereotype.Component;

/**
 * Verwerkt mutaties in huwelijk.geboorte.
 */
@Component
public class HuwelijkGeboorteMutatieVerwerker extends AbstractFormeelMutatieVerwerker<Lo3HuwelijkOfGpInhoud, BrpGeboorteInhoud> {
    private static final Logger LOGGER = LoggerFactory.getLogger();

    /**
     * Constructor.
     * @param mapper mapper
     * @param attribuutConverteerder attributen converteerder
     */
    @Inject
    protected HuwelijkGeboorteMutatieVerwerker(final HuwelijkGeboorteMapper mapper, final BrpAttribuutConverteerder attribuutConverteerder) {
        super(mapper, new GeboorteConverteerder(attribuutConverteerder), attribuutConverteerder, null, HuwelijkGeboorteMapper.GROEP_ELEMENT, LOGGER);
    }
}
