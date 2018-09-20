/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.brpnaarlo3;

import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpAttribuutConverteerder;
import nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3.attributen.BrpGroepConverteerder;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Converteerder voor conversie van BrpFamilierechtelijkeBetrekkingInhoud naar Lo3OuderInhoud.
 */
@Component
public final class OuderFamilierechtelijkeBetrekkingConverteerder extends BrpGroepConverteerder<BrpFamilierechtelijkeBetrekkingInhoud, Lo3OuderInhoud> {
    private static final Logger LOG = LoggerFactory.getLogger();

    @Inject
    private BrpAttribuutConverteerder converteerder;

    @Override
    protected Logger getLogger() {
        return LOG;
    }

    @Override
    public Lo3OuderInhoud maakNieuweInhoud() {
        return new Lo3OuderInhoud(null, null, null, null, null, null, null, null, null, null, null);
    }

    @Override
    public Lo3OuderInhoud vulInhoud(
        final Lo3OuderInhoud lo3Inhoud,
        final BrpFamilierechtelijkeBetrekkingInhoud brpInhoud,
        final BrpFamilierechtelijkeBetrekkingInhoud brpVorigeInhoud)
    {
        final Lo3OuderInhoud.Builder builder = new Lo3OuderInhoud.Builder(lo3Inhoud);

        if (brpInhoud == null) {
            builder.familierechtelijkeBetrekking(null);
        } else {
            builder.familierechtelijkeBetrekking(converteerder.converteerDatum(brpInhoud.getDatumFamilierechtelijkeBetrekking()));
        }

        return builder.build();
    }

}
