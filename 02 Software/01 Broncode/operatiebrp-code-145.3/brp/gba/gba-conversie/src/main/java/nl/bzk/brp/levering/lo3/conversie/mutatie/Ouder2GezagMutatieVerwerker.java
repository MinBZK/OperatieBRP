/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.levering.lo3.mapper.AbstractOuderGezagMapper;
import nl.bzk.brp.levering.lo3.mapper.Ouder2GezagMapper;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3GezagsverhoudingInhoud;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpOuder2GezagInhoud;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpAttribuutConverteerder;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpGezagsverhoudingConverteerder.GezagOuder2Converteerder;
import org.springframework.stereotype.Component;

/**
 * Verwerkt mutaties in ouder2/gezag.
 */
@Component
public final class Ouder2GezagMutatieVerwerker extends AbstractMaterieelMutatieVerwerker<Lo3GezagsverhoudingInhoud, BrpOuder2GezagInhoud> {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    /**
     * Constructor.
     * @param mapper mapper
     * @param attribuutConverteerder attributen converteerder
     */
    @Inject
    protected Ouder2GezagMutatieVerwerker(final Ouder2GezagMapper mapper, final BrpAttribuutConverteerder attribuutConverteerder) {
        super(mapper, new GezagOuder2Converteerder(attribuutConverteerder), attribuutConverteerder, null, AbstractOuderGezagMapper.GROEP_ELEMENT, LOGGER);
    }
}
