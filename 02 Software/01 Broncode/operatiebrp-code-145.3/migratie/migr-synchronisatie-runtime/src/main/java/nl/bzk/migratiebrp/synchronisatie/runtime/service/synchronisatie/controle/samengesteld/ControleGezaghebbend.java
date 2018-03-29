/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.samengesteld;

import java.util.List;
import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.Controle;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.lijst.LijstControle;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.lijst.LijstControleEenActueelAnummer;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.logging.ControleLogging;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.logging.ControleMelding;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl.PlControle;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl.PlControleBijhoudingsPartijGelijk;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl.PlControleGevondenDatumtijdstempelGelijkOfOuder;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl.PlControleGevondenVersienummerGelijkOfKleiner;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.zoeker.PlZoeker;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.zoeker.PlZoekerOpActueelAnummerEnNietFoutiefOpgeschortObvActueelAnummer;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.pl.PlService;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.context.VerwerkingsContext;
import org.springframework.stereotype.Component;

/**
 * Controle gezaghebbend synchronisatieantwoord door gemeente van bijhouding.
 */
@Component(value = "controleGezaghebbend")
public final class ControleGezaghebbend implements Controle {

    private final PlZoeker plZoekerObvActueelAnummer;
    private final LijstControle lijstControleEen;
    private final PlControle plControleBijhoudingsPartijGelijk;
    private final PlControle plControleGevondenVersienummerGelijkOfKleiner;
    private final PlControle plControleGevondenDatumtijdstempelGelijkOfOuder;

    /**
     * Constructor voor deze implementatie van een {@link Controle}.
     * @param plService implementatie van de {@link PlService}
     */
    @Inject
    public ControleGezaghebbend(final PlService plService) {
        this.plZoekerObvActueelAnummer = new PlZoekerOpActueelAnummerEnNietFoutiefOpgeschortObvActueelAnummer(plService);
        this.lijstControleEen = new LijstControleEenActueelAnummer();
        this.plControleBijhoudingsPartijGelijk = new PlControleBijhoudingsPartijGelijk();
        this.plControleGevondenVersienummerGelijkOfKleiner = new PlControleGevondenVersienummerGelijkOfKleiner();
        this.plControleGevondenDatumtijdstempelGelijkOfOuder = new PlControleGevondenDatumtijdstempelGelijkOfOuder();
    }

    @Override
    public boolean controleer(final VerwerkingsContext context) {
        final ControleLogging logging = new ControleLogging(ControleMelding.CONTROLE_GEZAGHEBBEND);
        final List<BrpPersoonslijst> dbPersoonslijsten = plZoekerObvActueelAnummer.zoek(context);

        if (lijstControleEen.controleer(dbPersoonslijsten)) {
            final BrpPersoonslijst dbPersoonslijst = dbPersoonslijsten.get(0);

            if (dbPersoonslijst != null
                    && plControleBijhoudingsPartijGelijk.controleer(context, dbPersoonslijst)
                    && plControleGevondenVersienummerGelijkOfKleiner.controleer(context, dbPersoonslijst)
                    && plControleGevondenDatumtijdstempelGelijkOfOuder.controleer(context, dbPersoonslijst)) {
                logging.logResultaat(true);
                return true;
            }
        }

        logging.logResultaat(false);
        return false;
    }
}
