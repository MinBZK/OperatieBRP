/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl;

import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.logging.ControleLogging;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.logging.ControleMelding;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.context.VerwerkingsContext;

/**
 * Controle dat de aangeboden persoonslijst een gevuld datum ingang blokkering PL bevat.
 */
public final class PlControleBevatDatumIngangBlokkering implements PlControle {

    @Override
    public boolean controleer(final VerwerkingsContext context, final BrpPersoonslijst dbPersoonslijst) {
        final Lo3Persoonslijst lo3Persoonslijst = context.getLo3Persoonslijst();
        final ControleLogging logging = new ControleLogging(ControleMelding.PL_CONTROLE_BEVAT_DATUM_INGANG_BLOKKERING);

        final String datumIngangBlokkering = bepaalDatumIngangBlokkering(lo3Persoonslijst);

        logging.logAangebodenWaarden(datumIngangBlokkering);

        final boolean resultaat = datumIngangBlokkering != null && !"".equals(datumIngangBlokkering);
        logging.logResultaat(resultaat);

        return resultaat;
    }

    private String bepaalDatumIngangBlokkering(final Lo3Persoonslijst lo3Persoonslijst) {
        if (lo3Persoonslijst != null
                && lo3Persoonslijst.getInschrijvingStapel() != null
                && lo3Persoonslijst.getInschrijvingStapel().getLo3ActueelVoorkomen().getInhoud() != null
                && lo3Persoonslijst.getInschrijvingStapel().getLo3ActueelVoorkomen().getInhoud().getDatumIngangBlokkering() != null) {
            return lo3Persoonslijst.getInschrijvingStapel().getLo3ActueelVoorkomen().getInhoud().getDatumIngangBlokkering().getWaarde();
        }
        return null;
    }
}
