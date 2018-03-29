/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import javax.inject.Inject;
import nl.bzk.brp.levering.lo3.mapper.GeregistreerdPartnerschapRelatieMapper;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpRelatieInhoud;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpAttribuutConverteerder;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpHuwelijkConverteerder;
import org.springframework.stereotype.Component;

/**
 * Verwerkt mutaties in geregistreerdpartnerschap/relatie.
 */
@Component
public final class GeregistreerdPartnerschapRelatieMutatieVerwerker extends AbstractRelatieMutatieVerwerker<BrpRelatieInhoud> {

    /**
     * Constructor.
     * @param mapper mapper
     * @param attribuutConverteerder attributen converteerder
     */
    @Inject
    protected GeregistreerdPartnerschapRelatieMutatieVerwerker(
            final GeregistreerdPartnerschapRelatieMapper mapper,
            final BrpAttribuutConverteerder attribuutConverteerder) {
        super(mapper,
                new BrpHuwelijkConverteerder.RelatieConverteerder(attribuutConverteerder),
                attribuutConverteerder,
                null,
                GeregistreerdPartnerschapRelatieMapper.GROEP_ELEMENT,
                GeregistreerdPartnerschapRelatieMapper.REDEN_EINDE_ELEMENT);
    }
}
