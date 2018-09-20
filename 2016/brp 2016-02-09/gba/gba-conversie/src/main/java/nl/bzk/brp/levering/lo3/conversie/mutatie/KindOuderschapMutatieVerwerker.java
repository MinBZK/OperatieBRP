/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import nl.bzk.brp.levering.lo3.conversie.brpnaarlo3.BrpFamilierechtelijkeBetrekkingInhoud;
import nl.bzk.brp.levering.lo3.conversie.brpnaarlo3.KindFamilierechtelijkeBetrekkingConverteerder;
import nl.bzk.brp.levering.lo3.conversie.brpnaarlo3.KindOuderschapMapper;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.ElementEnum;
import nl.bzk.brp.model.operationeel.kern.HisOuderOuderschapModel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3KindInhoud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Verwerkt mutaties in kind obv ouderschap.
 */
@Component
public final class KindOuderschapMutatieVerwerker
        extends AbstractMaterieelMutatieVerwerker<Lo3KindInhoud, BrpFamilierechtelijkeBetrekkingInhoud, HisOuderOuderschapModel>
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
    protected KindOuderschapMutatieVerwerker(final KindOuderschapMapper mapper, final KindFamilierechtelijkeBetrekkingConverteerder converteerder) {
        super(mapper, converteerder, ElementEnum.OUDER_OUDERSCHAP);
    }

}
