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
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.verzoek.VerzoekControle;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.zoeker.PlZoeker;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.context.VerwerkingsContext;
import org.springframework.stereotype.Component;

/**
 * Samengestelde controle 'Aangeboden persoonslijst is gelijk'.
 * <p/>
 * Een aangeboden persoonslijst is gelijk aan de gevonden persoonslijst en kan genegeerd worden als:
 * <ol>
 * <li>is vastgesteld dat het versienummer en datumtijdstempel gelijk zijn</li>
 * <li>is vastgesteld dat de rest van de persoonslijst ook gelijk is</li>
 * </ol>
 * Het is uit performanceoverwegingen belangrijk dat de vergelijking van de gehele persoonslijst alleen gedaan wordt als
 * is vastgesteld dat het versienummer en datumtijdstempel gelijk zijn.
 */
@Component(value = "controlePersoonslijstIsGelijk")
public final class ControlePersoonslijstIsGelijk implements Controle {

    @Inject
    @Named(value = "verzoekControleOudAnummerIsNietGevuld")
    private VerzoekControle verzoekControleOudAnummerIsNietGevuld;

    @Inject
    @Named(value = "plZoekerOpAnummerEnNietFoutiefOpgeschortObvActueelAnummer")
    private PlZoeker plZoekerOpAnummerEnNietFoutiefOpgeschortObvActueelAnummer;

    @Inject
    @Named(value = "lijstControleEen")
    private LijstControle lijstControleEen;

    @Inject
    @Named(value = "plControleVersienummerGelijk")
    private PlControle plControleVersienummerGelijk;
    @Inject
    @Named(value = "plControleDatumtijdstempelGelijk")
    private PlControle plControleDatumtijdstempelGelijk;
    @Inject
    @Named(value = "plControleVolledigGelijk")
    private PlControle plControleVolledigGelijk;

    @Override
    public boolean controleer(final VerwerkingsContext context) {
        final ControleLogging logging = new ControleLogging(ControleMelding.CONTROLE_PL_IS_GELIJK);
        final List<BrpPersoonslijst> dbPersoonslijsten = plZoekerOpAnummerEnNietFoutiefOpgeschortObvActueelAnummer.zoek(context);

        if (verzoekControleOudAnummerIsNietGevuld.controleer(context.getVerzoek()) && lijstControleEen.controleer(dbPersoonslijsten)) {
            final BrpPersoonslijst dbPersoonslijst = dbPersoonslijsten.get(0);

            if (dbPersoonslijst != null
                && plControleVersienummerGelijk.controleer(context, dbPersoonslijst)
                && plControleDatumtijdstempelGelijk.controleer(context, dbPersoonslijst)
                && plControleVolledigGelijk.controleer(context, dbPersoonslijst))
            {
                logging.logResultaat(true);
                return true;
            }
        }

        logging.logResultaat(false);
        return false;
    }
}
