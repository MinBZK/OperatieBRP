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
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpDatum;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpString;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGeboorteInhoud;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpIdentificatienummersInhoud;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.logging.ControleLogging;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.logging.ControleMelding;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.context.VerwerkingsContext;

/**
 * Controle dezelfde persoon.
 */
public final class PlControleDezelfdePersoon implements PlControle {

    @Override
    public boolean controleer(final VerwerkingsContext context, final BrpPersoonslijst dbPersoonslijst) {
        final BrpPersoonslijst brpPersoonslijst = context.getBrpPersoonslijst();
        final ControleLogging logging = new ControleLogging(ControleMelding.PL_CONTROLE_DEZELFDE_PERSOON);

        final boolean result = controleerBsn(logging, brpPersoonslijst, dbPersoonslijst)
                && controleerGeboorteDatums(logging, brpPersoonslijst, dbPersoonslijst);

        logging.logResultaat(result);
        return result;
    }

    private boolean controleerBsn(final ControleLogging logging, final BrpPersoonslijst brpPersoonslijst, final BrpPersoonslijst dbPersoonslijst) {
        logging.addMelding("Controle op BSN.");

        final Set<String> aangebodenBsns = getBsns(brpPersoonslijst);
        final Set<String> dbBsns = getBsns(dbPersoonslijst);

        // Log waarden
        logging.logAangebodenWaarden(aangebodenBsns);
        logging.logGevondenWaarden(dbBsns);

        return aangebodenBsns.containsAll(dbBsns);
    }

    private Set<String> getBsns(final BrpPersoonslijst persoonslijst) {
        final BrpStapel<BrpIdentificatienummersInhoud> stapel = persoonslijst.getIdentificatienummerStapel();
        if (stapel == null) {
            return Collections.emptySet();
        }

        final SortedSet<String> bsns = new TreeSet<>();
        for (final BrpGroep<BrpIdentificatienummersInhoud> groep : stapel) {
            if (groep.getHistorie().isVervallen()) {
                continue;
            }
            final BrpIdentificatienummersInhoud inhoud = groep.getInhoud();
            final String bsn = BrpString.unwrap(inhoud.getBurgerservicenummer());
            bsns.add(bsn == null ? "" : bsn);
        }

        return bsns;
    }


    private boolean controleerGeboorteDatums(final ControleLogging logging, final BrpPersoonslijst brpPersoonslijst, final BrpPersoonslijst dbPersoonslijst) {
        logging.addMelding("Controle op geboortedatum.");

        // Aangeboden waarden
        final Set<Integer> brpGeboorteDatums = getGeboortedatums(brpPersoonslijst);
        logging.logAangebodenWaarden(brpGeboorteDatums);

        // Gevonden waarden
        final Set<Integer> dbGeboorteDatums = getGeboortedatums(dbPersoonslijst);
        logging.logGevondenWaarden(dbGeboorteDatums);

        // Resultaat
        return brpGeboorteDatums.containsAll(dbGeboorteDatums);
    }


    private Set<Integer> getGeboortedatums(final BrpPersoonslijst persoonslijst) {
        final BrpStapel<BrpGeboorteInhoud> stapel = persoonslijst.getGeboorteStapel();
        if (stapel == null) {
            return Collections.emptySet();
        }

        final SortedSet<Integer> geboorteDatums = new TreeSet<>();
        for (final BrpGroep<BrpGeboorteInhoud> groep : stapel) {
            final BrpGeboorteInhoud inhoud = groep.getInhoud();
            final Integer geboorteDatum = BrpDatum.unwrap(inhoud.getGeboortedatum());
            if (geboorteDatum != null && !geboorteDatums.contains(geboorteDatum)) {
                geboorteDatums.add(geboorteDatum);
            }
        }

        return geboorteDatums;
    }
}
