/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.brpnaarlo3;

import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3KindInhoud;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpGroepConverteerder;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Converteerder voor conversie van BrpFamilierechtelijkeBetrekkingInhoud naar Lo3KindInhoud.
 */
@Component
public final class KindFamilierechtelijkeBetrekkingConverteerder extends BrpGroepConverteerder<BrpFamilierechtelijkeBetrekkingInhoud, Lo3KindInhoud> {
    private static final Logger LOG = LoggerFactory.getLogger();

    @Override
    protected Logger getLogger() {
        return LOG;
    }

    @Override
    public Lo3KindInhoud maakNieuweInhoud() {
        return new Lo3KindInhoud(null, null, null, null, null, null, null, null, null);
    }

    @Override
    public Lo3KindInhoud vulInhoud(
        final Lo3KindInhoud lo3Inhoud,
        final BrpFamilierechtelijkeBetrekkingInhoud brpInhoud,
        final BrpFamilierechtelijkeBetrekkingInhoud brpVorigeInhoud)
    {
        return lo3Inhoud;
    }

}
