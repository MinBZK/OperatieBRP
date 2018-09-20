/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import nl.bzk.brp.levering.lo3.mapper.Ouder1GezagMapper;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.operationeel.kern.HisOuderOuderlijkGezagModel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3GezagsverhoudingInhoud;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.BrpOuder1GezagInhoud;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpGezagsverhoudingConverteerder.GezagOuder1Converteerder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Verwerkt mutaties in ouder1/gezag.
 */
@Component
public final class Ouder1GezagMutatieVerwerker
        extends AbstractMaterieelMutatieVerwerker<Lo3GezagsverhoudingInhoud, BrpOuder1GezagInhoud, HisOuderOuderlijkGezagModel>
{
    /**
     * Constructor.
     *
     * @param mapper
     *            mapper
     * @param converteerder
     *            converteerder
     */
    @Autowired
    protected Ouder1GezagMutatieVerwerker(final Ouder1GezagMapper mapper, final GezagOuder1Converteerder converteerder) {
        super(mapper, converteerder, ElementEnum.GERELATEERDEOUDER_OUDERLIJKGEZAG);
    }

}
