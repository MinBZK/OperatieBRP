/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.samengesteld;

import java.util.List;
import javax.inject.Named;
import nl.bzk.algemeenbrp.dal.repositories.DynamischeStamtabelRepository;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.synchronisatie.dal.service.BrpDalService;
import nl.bzk.migratiebrp.synchronisatie.dal.service.SyncParameters;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.Controle;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.lijst.LijstControle;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.lijst.LijstControleEenActueelAnummer;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.logging.ControleLogging;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.logging.ControleMelding;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl.PlControle;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl.PlControleDatumtijdstempelGelijk;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl.PlControleGevondenBlokkeringssituatieIsJuist;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl.PlControleVersienummerGelijk;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl.PlControleVolledigGelijk;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.verzoek.VerzoekControle;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.verzoek.VerzoekControleBerichtVanSoortLg01;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.zoeker.PlZoeker;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.zoeker.PlZoekerOpActueelAnummerEnNietFoutiefOpgeschortObvActueelAnummer;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.pl.PlService;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.context.VerwerkingsContext;
import org.springframework.stereotype.Component;

/**
 * <p>
 * Samengestelde controle 'Aangeboden persoonslijst is gelijk'.
 * </p>
 * Een aangeboden persoonslijst is gelijk aan de gevonden persoonslijst en kan genegeerd worden als:
 * <ol>
 * <li>er een juiste blokkeringssituatie bestaat voor de gevonden persoonslijst (3.4), en</li>
 * <li>is vastgesteld dat het versienummer en datumtijdstempel gelijk zijn</li>
 * <li>is vastgesteld dat de rest van de persoonslijst ook gelijk is</li>
 * </ol>
 * Het is uit performanceoverwegingen belangrijk dat de vergelijking van de gehele persoonslijst alleen gedaan wordt als
 * is vastgesteld dat het versienummer en datumtijdstempel gelijk zijn.
 */
@Component(value = "controlePersoonslijstIsGelijk")
public final class ControlePersoonslijstIsGelijk implements Controle {

    private final VerzoekControle verzoekControleBerichtVanSoortLg01;
    private final PlZoeker plZoekerObvActueelAnummer;
    private final LijstControle lijstControleEen;
    private final PlControle plControleGevondenBlokkeringssituatieIsJuist;
    private final PlControle plControleVersienummerGelijk;
    private final PlControle plControleDatumtijdstempelGelijk;
    private final PlControle plControleVolledigGelijk;

    /**
     * Constructor voor deze implementatie van een {@link Controle}.
     * @param plService implementatie van de {@link PlService}
     * @param brpDalService implementatie van de {@link BrpDalService}
     * @param syncParameters de parameters waarmee de Synchronisatie service is opgestart
     * @param dynamischeStamtabelRepository de repository voor de dynamische stamtabellen
     */
    public ControlePersoonslijstIsGelijk(final PlService plService, final BrpDalService brpDalService,
                                         @Named("syncParameters") final SyncParameters syncParameters,
                                         final DynamischeStamtabelRepository dynamischeStamtabelRepository) {
        this.verzoekControleBerichtVanSoortLg01 = new VerzoekControleBerichtVanSoortLg01();
        this.plZoekerObvActueelAnummer = new PlZoekerOpActueelAnummerEnNietFoutiefOpgeschortObvActueelAnummer(plService);
        this.lijstControleEen = new LijstControleEenActueelAnummer();
        this.plControleGevondenBlokkeringssituatieIsJuist = new PlControleGevondenBlokkeringssituatieIsJuist(brpDalService);
        this.plControleVersienummerGelijk = new PlControleVersienummerGelijk();
        this.plControleDatumtijdstempelGelijk = new PlControleDatumtijdstempelGelijk();
        this.plControleVolledigGelijk = new PlControleVolledigGelijk(syncParameters, dynamischeStamtabelRepository);
    }

    @Override
    public boolean controleer(final VerwerkingsContext context) {
        final ControleLogging logging = new ControleLogging(ControleMelding.CONTROLE_PL_IS_GELIJK);
        final List<BrpPersoonslijst> dbPersoonslijsten = plZoekerObvActueelAnummer.zoek(context);

        boolean result = verzoekControleBerichtVanSoortLg01.controleer(context.getVerzoek());
        result = result && lijstControleEen.controleer(dbPersoonslijsten);

        if (result) {
            final BrpPersoonslijst dbPersoonslijst = dbPersoonslijsten.get(0);

            result = plControleGevondenBlokkeringssituatieIsJuist.controleer(context, dbPersoonslijst);
            result = result && plControleVersienummerGelijk.controleer(context, dbPersoonslijst);
            result = result && plControleDatumtijdstempelGelijk.controleer(context, dbPersoonslijst);
            result = result && plControleVolledigGelijk.controleer(context, dbPersoonslijst);
        }

        logging.logResultaat(result, true);
        return result;
    }
}
