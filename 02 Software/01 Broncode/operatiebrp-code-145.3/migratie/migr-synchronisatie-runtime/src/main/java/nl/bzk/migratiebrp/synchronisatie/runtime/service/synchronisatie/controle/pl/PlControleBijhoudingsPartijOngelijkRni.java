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
@Component(value = "plControleBijhoudingsPartijOngelijkRni")
public final class PlControleBijhoudingsPartijOngelijkRni implements PlControle {

    private static final String BEGIN_MELDING_RNI = "(RNI=";
    private static final String PARTIJ_CODE_RNI = "199901";
    private static final String MELDING_GELIJK_RNI = BEGIN_MELDING_RNI + PARTIJ_CODE_RNI + " => gelijk aan RNI)";
    private static final String MELDING_ONGELIJK_RNI = BEGIN_MELDING_RNI + PARTIJ_CODE_RNI + " => ongelijk aan RNI)";

    @Override
    public boolean controleer(final VerwerkingsContext context, final BrpPersoonslijst dbPersoonslijst) {
        final ControleLogging logging = new ControleLogging(ControleMelding.PL_CONTROLE_BIJHOUDINGSPARTIJ_ONGELIJK_RNI);

        // Aangeboden waarde
        final BrpPartijCode rniBijhoudingsPartij = new BrpPartijCode(PARTIJ_CODE_RNI);

        final BrpStapel<BrpBijhoudingInhoud> stapel = context.getBrpPersoonslijst().getBijhoudingStapel();
        // Gevonden waarde
        if (stapel == null) {
            return true;
        }

        final BrpPartijCode brpBijhoudingsPartij = stapel.getActueel().getInhoud().getBijhoudingspartijCode();

        final boolean result = !AbstractBrpAttribuutMetOnderzoek.equalsWaarde(rniBijhoudingsPartij, brpBijhoudingsPartij);

        // Resultaat
        final String logMelding = result ? MELDING_ONGELIJK_RNI : MELDING_GELIJK_RNI;
        logging.logAangebodenWaarden(PlControleHelper.geefAttribuutWaarde(brpBijhoudingsPartij) + logMelding);
        logging.logResultaat(result);

        return result;
    }

}
