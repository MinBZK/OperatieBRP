/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.samengesteld;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.Controle;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.lijst.LijstControle;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.logging.ControleLogging;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.logging.ControleMelding;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl.PlControle;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.zoeker.PlZoeker;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.context.VerwerkingsContext;
import org.springframework.stereotype.Component;

/**
 * Controle gezaghebbend synchronisatieantwoord door gemeente van bijhouding.
 */
@Component(value = "controleGezaghebbend")
public final class ControleGezaghebbend implements Controle {

    @Inject
    @Named(value = "plZoekerOpAnummerEnNietFoutiefOpgeschortObvActueelAnummer")
    private PlZoeker plZoekerOpAnummerEnNietFoutiefOpgeschortObvActueelAnummer;

    @Inject
    @Named(value = "lijstControleEen")
    private LijstControle lijstControleEen;

    @Inject
    @Named(value = "plControleBijhoudingsPartijGelijk")
    private PlControle plControleBijhoudingsPartijGelijk;
    @Inject
    @Named(value = "plControleGevondenVersienummerGelijkOfKleiner")
    private PlControle plControleGevondenVersienummerGelijkOfKleiner;
    @Inject
    @Named(value = "plControleGevondenDatumtijdstempelGelijkOfOuder")
    private PlControle plControleGevondenDatumtijdstempelGelijkOfOuder;

    @Override
    public boolean controleer(final VerwerkingsContext context) {
        final ControleLogging logging = new ControleLogging(ControleMelding.CONTROLE_GEZAGHEBBEND);
        final List<BrpPersoonslijst> dbPersoonslijsten = plZoekerOpAnummerEnNietFoutiefOpgeschortObvActueelAnummer.zoek(context);

        if (lijstControleEen.controleer(dbPersoonslijsten)) {
            final BrpPersoonslijst dbPersoonslijst = dbPersoonslijsten.get(0);

            if (dbPersoonslijst != null
                && plControleBijhoudingsPartijGelijk.controleer(context, dbPersoonslijst)
                && plControleGevondenVersienummerGelijkOfKleiner.controleer(context, dbPersoonslijst)
                && plControleGevondenDatumtijdstempelGelijkOfOuder.controleer(context, dbPersoonslijst))
            {
                logging.logResultaat(true);
                return true;
            }
        }

        logging.logResultaat(false);
        return false;
    }
}
