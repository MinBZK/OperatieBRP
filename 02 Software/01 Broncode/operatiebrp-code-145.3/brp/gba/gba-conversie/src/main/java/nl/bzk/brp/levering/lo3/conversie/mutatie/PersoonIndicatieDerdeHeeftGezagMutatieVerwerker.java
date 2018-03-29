/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.levering.lo3.mapper.PersoonIndicatieDerdeHeeftGezagMapper;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpDerdeHeeftGezagIndicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3GezagsverhoudingInhoud;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpAttribuutConverteerder;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpGezagsverhoudingConverteerder.DerdeHeeftGezagIndicatieConverteerder;
import org.springframework.stereotype.Component;

/**
 * Verwerkt mutaties in persoon/indicatie derde heeft gezag.
 */
@Component
public final class PersoonIndicatieDerdeHeeftGezagMutatieVerwerker
        extends AbstractMaterieelMutatieVerwerker<Lo3GezagsverhoudingInhoud, BrpDerdeHeeftGezagIndicatieInhoud> {
    private static final Logger LOGGER = LoggerFactory.getLogger();

    /**
     * Constructor.
     * @param mapper mapper
     * @param attribuutConverteerder attributen converteerder
     */
    @Inject
    protected PersoonIndicatieDerdeHeeftGezagMutatieVerwerker(final PersoonIndicatieDerdeHeeftGezagMapper mapper,
                                                              final BrpAttribuutConverteerder attribuutConverteerder) {
        super(mapper, new DerdeHeeftGezagIndicatieConverteerder(attribuutConverteerder), attribuutConverteerder, null,
                PersoonIndicatieDerdeHeeftGezagMapper.GROEP_ELEMENT, LOGGER);
    }

}
