/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl;

import java.util.Collections;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import nl.bzk.migratiebrp.conversie.model.brp.BrpGroep;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpNummerverwijzingInhoud;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.logging.ControleLogging;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.logging.ControleMelding;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.context.VerwerkingsContext;

/**
 * Controleer of 'de gevonden persoonslijst in de BRP een <b>vorig a-nummer</b> heeft dat gelijk is aan het
 * <b>vorig-anummer</b> op de aangeboden persoonlijst of de gevonden persoonlijst een <b>historie van vorige
 * a-nummers</b> heeft die voorkomt in de <b>historie van vorige a-nummers</b> op de aangeboden persoonslijst'.
 *
 *
 * het actuele vorige a-nummer van de gevonden en de aanboden persoonslijsten overeenkomt.
 */
public final class PlControleVorigAnummerGelijk implements PlControle {

    @Override
    public boolean controleer(final VerwerkingsContext context, final BrpPersoonslijst dbPersoonslijst) {
        final BrpPersoonslijst brpPersoonslijst = context.getBrpPersoonslijst();
        final ControleLogging logging = new ControleLogging(ControleMelding.PL_CONTROLE_VORIG_ANUMMER_GELIJK);

        // Aangeboden waarden
        final String brpVorigAnummer = getVorigAnummer(brpPersoonslijst);
        logging.logAangebodenWaarden(brpVorigAnummer);

        // Gevonden waarden
        final String dbVorigAnummer = getVorigAnummer(dbPersoonslijst);
        logging.logGevondenWaarden(dbVorigAnummer);

        // Resultaat
        if (PlControleHelper.isGelijk(brpVorigAnummer, dbVorigAnummer)) {
            logging.logResultaat(true);
            return true;
        }

        logging.addMelding("Resultaat: false, ga door met controle op historie vorige a-nummers");

        // Aangeboden waarden
        final Set<String> brpVorigAnummers = getVorigAnummers(brpPersoonslijst);
        logging.logAangebodenWaarden(brpVorigAnummers);

        // Gevonden waarden
        final Set<String> dbVorigAnummers = getVorigAnummers(dbPersoonslijst);
        logging.logGevondenWaarden(dbVorigAnummers);

        // Resultaat
        final boolean result = brpVorigAnummers.containsAll(dbVorigAnummers);
        logging.logResultaat(result);

        return result;
    }

    private String getVorigAnummer(final BrpPersoonslijst persoonslijst) {
        final BrpStapel<BrpNummerverwijzingInhoud> stapel = persoonslijst.getNummerverwijzingStapel();

        if (stapel == null || stapel.getActueel() == null || stapel.getActueel().getInhoud() == null) {
            return null;
        }

        return BrpString.unwrap(stapel.getActueel().getInhoud().getVorigeAdministratienummer());
    }

    private Set<String> getVorigAnummers(final BrpPersoonslijst persoonslijst) {
        final BrpStapel<BrpNummerverwijzingInhoud> stapel = persoonslijst.getNummerverwijzingStapel();

        if (stapel == null) {
            return Collections.emptySet();
        }

        final SortedSet<String> vorigeAnummers = new TreeSet<>();

        for (final BrpGroep<BrpNummerverwijzingInhoud> groep : persoonslijst.getNummerverwijzingStapel()) {
            if (groep.getActieVerval() != null) {
                continue;
            }
            final BrpNummerverwijzingInhoud inhoud = groep.getInhoud();
            final String vorigeAdministratienummer = BrpString.unwrap(inhoud.getVorigeAdministratienummer());
            if (vorigeAdministratienummer != null && !vorigeAnummers.contains(vorigeAdministratienummer)) {
                vorigeAnummers.add(vorigeAdministratienummer);
            }
        }

        return vorigeAnummers;
    }

}
