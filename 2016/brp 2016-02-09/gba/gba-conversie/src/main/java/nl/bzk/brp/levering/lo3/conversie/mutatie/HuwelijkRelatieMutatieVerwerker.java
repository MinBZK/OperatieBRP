/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import nl.bzk.brp.levering.lo3.mapper.HuwelijkRelatieMapper;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpRelatieInhoud;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpHuwelijkConverteerder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Verwerkt mutaties in huwelijk/relatie.
 */
@Component
public final class HuwelijkRelatieMutatieVerwerker extends AbstractRelatieMutatieVerwerker<BrpRelatieInhoud> {

    /**
     * Constructor.
     *
     * @param mapper
     *            mapper
     * @param converteerder
     *            converteerder
     */
    @Autowired
    protected HuwelijkRelatieMutatieVerwerker(final HuwelijkRelatieMapper mapper, final BrpHuwelijkConverteerder.RelatieConverteerder converteerder) {
        super(mapper, converteerder, null, ElementEnum.HUWELIJK_STANDAARD);
    }

}
