/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl;

import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.AbstractBrpAttribuutMetOnderzoek;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpBijhoudingInhoud;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.logging.ControleLogging;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.logging.ControleMelding;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.context.VerwerkingsContext;
import org.springframework.stereotype.Component;

/**
 * Controle dat bijhoudingspartij op aangeboden en gevonden persoonslijst gelijk is.
 */
@Component(value = "plControleBijhoudingsPartijGelijk")
public final class PlControleBijhoudingsPartijGelijk implements PlControle {

    @Override
    public boolean controleer(final VerwerkingsContext context, final BrpPersoonslijst dbPersoonslijst) {
        final BrpPersoonslijst brpPersoonslijst = context.getBrpPersoonslijst();
        final ControleLogging logging = new ControleLogging(ControleMelding.PL_CONTROLE_BIJHOUDINGSPARTIJ_GELIJK);

        // Aangeboden waarde
        final BrpPartijCode brpBijhoudingsPartij = getBijhoudingsPartij(brpPersoonslijst);
        logging.logAangebodenWaarden(PlControleHelper.geefAttribuutWaarde(brpBijhoudingsPartij));

        // Gevonden waarde
        final BrpPartijCode dbBijhoudingsPartij = getBijhoudingsPartij(dbPersoonslijst);
        logging.logGevondenWaarden(PlControleHelper.geefAttribuutWaarde(dbBijhoudingsPartij));

        // Resultaat
        final boolean result = AbstractBrpAttribuutMetOnderzoek.equalsWaarde(brpBijhoudingsPartij, dbBijhoudingsPartij);
        logging.logResultaat(result);

        return result;
    }

    private BrpPartijCode getBijhoudingsPartij(final BrpPersoonslijst persoonslijst) {
        final BrpStapel<BrpBijhoudingInhoud> stapel = persoonslijst.getBijhoudingStapel();
        if (stapel == null) {
            return null;
        }

        return stapel.getActueel().getInhoud().getBijhoudingspartijCode();
    }

}
