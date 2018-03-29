/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.samengesteld;

import java.util.List;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.synchronisatie.dal.service.BrpDalService;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.Controle;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.lijst.LijstControle;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.lijst.LijstControleEenActueelAnummer;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.logging.ControleLogging;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.logging.ControleMelding;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl.PlControle;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl.PlControleAangebodenAdressenKomenVoorInGevondenAdressen;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl.PlControleDezelfdePersoon;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl.PlControleGevondenBlokkeringssituatieIsJuist;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl.PlControleGevondenDatumtijdstempelNieuwer;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl.PlControleGevondenVersienummerGroter;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl.PlControleHistorieAnummerGelijk;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl.PlControleVorigAnummerGelijk;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.verzoek.VerzoekControle;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.verzoek.VerzoekControleBerichtVanSoortLg01;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.zoeker.PlZoeker;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.zoeker.PlZoekerOpActueelAnummerEnNietFoutiefOpgeschortObvActueelAnummer;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.pl.PlService;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.context.VerwerkingsContext;
import org.springframework.stereotype.Component;

/**
 * <p>
 * Samengestelde controle 'Aangeboden persoonslijst is ouder'.
 * </p>
 * Een aangeboden persoonslijst is ouder en kan genegeerd worden als:
 * <ol>
 * <li>het bericht is van soort Lg01, en</li>
 * <li>er in de BRP één persoonslijst voorkomt dat niet foutief opgeschort is en een a-nummer heeft dat gelijk is aan
 * het a-nummer op de aangeboden persoonslijst (*), en</li>
 * <li>er een juiste blokkeringssituatie bestaat voor de gevonden persoonslijst (3.4), en</li>
 * <li>de gevonden persoonslijst in de BRP een gemeente van bijhouding heeft dat gelijk is aan de gemeente van
 * bijhouding op de aangeboden persoonslijst, en</li>
 * <li>de gevonden persoonslijst in de BRP een vorig a-nummer heeft dat gelijk is aan het vorig a-nummer op de
 * aangeboden persoonslijst of de gevonden persoonslijst in de BRP een historie van vorige a-nummers heeft die voorkomt
 * in de historie van vorige a-nummers op de aangeboden persoonslijst, en</li>
 * <li>de gevonden persoonslijst in de BRP een historie van a-nummers heeft dat gelijk is aan de historie van a-nummers
 * op de aangeboden persoonslijst, en</li>
 * <li>de persoon op de gevonden persoonslijst in de BRP dezelfde persoon is als de persoon op de aangeboden
 * persoonslijst, en</li>
 * <li>de gevonden persoonslijst in de BRP een versienummer heeft dat groter is dan het versienummer van de aangeboden
 * persoonslijst, en</li>
 * <li>de gevonden persoonslijst in de BRP een datumtijdstempel heeft dat nieuwer is dan de datumtijdstempel van de
 * aangebonden persoonslijst</li>
 * </ol>
 */
@Component(value = "controlePersoonslijstIsOuder")
public final class ControlePersoonslijstIsOuder implements Controle {

    private final VerzoekControle verzoekControleBerichtVanSoortLg01;
    private final PlZoeker plZoekerObvActueelAnummer;
    private final LijstControle lijstControleEen;
    private final PlControle plControleGevondenBlokkeringssituatieIsJuist;
    private final PlControle plControleAangebodenAdressenKomenVoorInGevondenAdressen;
    private final PlControle plControleVorigAnummerGelijk;
    private final PlControle plControleHistorieAnummerGelijk;
    private final PlControle plControleDezelfdePersoon;
    private final PlControle plControleGevondenVersienummerGroter;
    private final PlControle plControleGevondenDatumtijdstempelNieuwer;

    /**
     * Constructor voor deze implementatie van een {@link Controle}.
     * @param plService implementatie van de {@link PlService}
     * @param brpDalService implementatie van de {@link BrpDalService}
     */
    ControlePersoonslijstIsOuder(final PlService plService, final BrpDalService brpDalService) {
        this.verzoekControleBerichtVanSoortLg01 = new VerzoekControleBerichtVanSoortLg01();
        this.plZoekerObvActueelAnummer = new PlZoekerOpActueelAnummerEnNietFoutiefOpgeschortObvActueelAnummer(plService);
        this.lijstControleEen = new LijstControleEenActueelAnummer();
        this.plControleGevondenBlokkeringssituatieIsJuist = new PlControleGevondenBlokkeringssituatieIsJuist(brpDalService);
        this.plControleAangebodenAdressenKomenVoorInGevondenAdressen = new PlControleAangebodenAdressenKomenVoorInGevondenAdressen();
        this.plControleVorigAnummerGelijk = new PlControleVorigAnummerGelijk();
        this.plControleHistorieAnummerGelijk = new PlControleHistorieAnummerGelijk();
        this.plControleDezelfdePersoon = new PlControleDezelfdePersoon();
        this.plControleGevondenVersienummerGroter = new PlControleGevondenVersienummerGroter();
        this.plControleGevondenDatumtijdstempelNieuwer = new PlControleGevondenDatumtijdstempelNieuwer();
    }

    @Override
    public boolean controleer(final VerwerkingsContext context) {
        final ControleLogging logging = new ControleLogging(ControleMelding.CONTROLE_PL_IS_OUDER);
        final List<BrpPersoonslijst> dbPersoonslijsten = plZoekerObvActueelAnummer.zoek(context);
        boolean result = false;

        if (verzoekControleBerichtVanSoortLg01.controleer(context.getVerzoek()) && lijstControleEen.controleer(dbPersoonslijsten)) {
            final BrpPersoonslijst dbPersoonslijst = dbPersoonslijsten.get(0);

            result = plControleGevondenBlokkeringssituatieIsJuist.controleer(context, dbPersoonslijst);
            result = result && plControleVorigAnummerGelijk.controleer(context, dbPersoonslijst);
            result = result && plControleHistorieAnummerGelijk.controleer(context, dbPersoonslijst);
            result = result && plControleDezelfdePersoon.controleer(context, dbPersoonslijst);
            result = result && plControleAangebodenAdressenKomenVoorInGevondenAdressen.controleer(context, dbPersoonslijst);
            result = result && plControleGevondenVersienummerGroter.controleer(context, dbPersoonslijst);
            result = result && plControleGevondenDatumtijdstempelNieuwer.controleer(context, dbPersoonslijst);
        }

        logging.logResultaat(result, true);
        return result;
    }

}
