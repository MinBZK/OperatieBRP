/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import javax.inject.Inject;
import nl.bzk.brp.levering.lo3.mapper.HuwelijkVerbintenisMapper;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpVerbintenisInhoud;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpAttribuutConverteerder;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpHuwelijkConverteerder.VerbintenisConverteerder;
import org.springframework.stereotype.Component;

/**
 * Verwerkt mutaties in huwelijk/verbintenis.
 */
@Component
public final class HuwelijkVerbintenisMutatieVerwerker extends AbstractRelatieMutatieVerwerker<BrpVerbintenisInhoud> {
    /**
     * Constructor.
     * @param mapper mapper
     * @param attribuutConverteerder attributen converteerder
     */
    @Inject
    protected HuwelijkVerbintenisMutatieVerwerker(final HuwelijkVerbintenisMapper mapper, final BrpAttribuutConverteerder attribuutConverteerder) {
        // Let op: gaat via AbstractRelatieMutatieVerwerker zodat filtering van ontbinding nav overlijden goed gaat
        super(mapper, new VerbintenisConverteerder(attribuutConverteerder), attribuutConverteerder, null, HuwelijkVerbintenisMapper.GROEP_ELEMENT, null);
    }
}
