/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.samengesteld;

import java.util.List;
import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.synchronisatie.dal.service.BrpDalService;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.Controle;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.lijst.LijstControle;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.lijst.LijstControleEenActueelAnummer;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.lijst.LijstControleGeenBurgerServicenummer;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.logging.ControleLogging;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.logging.ControleMelding;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl.PlControle;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl.PlControleActueelBsnGelijk;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl.PlControleBijhoudingsPartijGelijk;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl.PlControleBijhoudingsPartijOngelijkVerzendendeGemeente;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl.PlControleDezelfdePersoon;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl.PlControleGevondenBlokkeringssituatieIsJuist;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl.PlControleGevondenDatumtijdstempelOuder;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl.PlControleGevondenPersoonOpgeschortRedenO;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl.PlControleGevondenVersienummerGelijkOfKleiner;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl.PlControleHistorieAnummerGelijk;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl.PlControleVorigAnummerGelijk;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.zoeker.PlZoeker;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.zoeker.PlZoekerOpActueelAnummerEnNietFoutiefOpgeschortObvActueelAnummer;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.zoeker
        .PlZoekerOpActueelEnHistorischBurgerServicenummerEnNietFoutiefOpgeschortObvActueelBurgerServicenummer;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.pl.PlService;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.context.VerwerkingsContext;
import org.springframework.stereotype.Component;

/**
 * Samengestelde controle 'Reguliere wijziging door de gemeente van bijhouding'.
 *
 * Er is sprake van een wijziging door verhuizing of gemeentelijke herindeling als:
 * <ol>
 * <li>er in de BRP één persoonslijst voorkomt dat niet foutief opgeschort is en een a-nummer heeft dat gelijk is aan
 * het a-nummer op de aangeboden persoonslijst (*), en</li>
 * <li>de gevonden persoonslijst is opgeschort met reden 'O'</li>
 * <li>er een juiste blokkeringssituatie bestaat voor de gevonden persoonslijst (3.4), en</li>
 * <li>de gevonden persoonslijst in de BRP een gemeente van bijhouding heeft dat gelijk is aan de gemeente van
 * bijhouding op de aangeboden persoonslijst, en</li>
 * <li>de aangeboden persoonslijst een gemeente van bijhouding heeft dat ongelijk is aan de verzendende partij van de
 * aangeboden persoonslijst, en</li>
 * <li>de gevonden persoonslijst in de BRP een vorig a-nummer heeft dat gelijk is aan het vorig a-nummer op de
 * aangeboden persoonslijst of de gevonden persoonslijst in de BRP een historie van vorige a-nummers heeft die voorkomt
 * in de historie van vorige a-nummers op de aangeboden persoonslijst, en</li>
 * <li>de gevonden persoonslijst in de BRP een historie van a-nummers heeft dat gelijk is aan de historie van a-nummers
 * op de aangeboden persoonslijst, en</li>
 * <li>de persoon op de gevonden persoonslijst in de BRP dezelfde persoon is als de persoon op de aangeboden
 * persoonslijst, en</li>
 * <li>de gevonden persoonslijst in de BRP een versienummer heeft dat gelijk of kleiner is dan het versienummer van de
 * aangeboden persoonslijst, en</li>
 * <li>de gevonden persoonslijst in de BRP een datumtijdstempel heeft dat ouder is dan de datumtijdstempel van de
 * aangebonden persoonslijst</li>
 * </ol>
 */
@Component(value = "controleWijzigingOverledenPersoon")
public final class ControleWijzigingOverledenPersoon implements Controle {

    private final PlZoeker plZoekerObvActueelAnummer;
    private final PlZoeker plZoekerObvActueelBsn;
    private final LijstControle lijstControleEen;
    private final LijstControle lijstControleGeen;
    private final PlControle plControleGevondenBlokkeringssituatieIsJuist;
    private final PlControle plControleBijhoudingsPartijGelijk;
    private final PlControle plControleBijhoudingsPartijOngelijkVerzendendeGemeente;
    private final PlControle plControleGevondenPersoonOpgeschortRedenO;
    private final PlControle plControleVorigAnummerGelijk;
    private final PlControle plControleHistorieAnummerGelijk;
    private final PlControle plControleDezelfdePersoon;
    private final PlControle plControleActueelBsnGelijk;
    private final PlControle plControleGevondenVersienummerGelijkOfKleiner;
    private final PlControle plControleGevondenDatumtijdstempelOuder;

    /**
     * Constructor voor deze implementatie van een {@link Controle}.
     * @param plService implementatie van de {@link PlService}
     * @param brpDalService implementatie van de {@link BrpDalService}
     */
    @Inject
    public ControleWijzigingOverledenPersoon(final PlService plService, final BrpDalService brpDalService) {
        this.plZoekerObvActueelAnummer = new PlZoekerOpActueelAnummerEnNietFoutiefOpgeschortObvActueelAnummer(plService);
        this.plZoekerObvActueelBsn = new PlZoekerOpActueelEnHistorischBurgerServicenummerEnNietFoutiefOpgeschortObvActueelBurgerServicenummer(plService);
        this.lijstControleEen = new LijstControleEenActueelAnummer();
        this.lijstControleGeen = new LijstControleGeenBurgerServicenummer();
        this.plControleGevondenBlokkeringssituatieIsJuist = new PlControleGevondenBlokkeringssituatieIsJuist(brpDalService);
        this.plControleBijhoudingsPartijGelijk = new PlControleBijhoudingsPartijGelijk();
        this.plControleBijhoudingsPartijOngelijkVerzendendeGemeente = new PlControleBijhoudingsPartijOngelijkVerzendendeGemeente();
        this.plControleGevondenPersoonOpgeschortRedenO = new PlControleGevondenPersoonOpgeschortRedenO();
        this.plControleVorigAnummerGelijk = new PlControleVorigAnummerGelijk();
        this.plControleHistorieAnummerGelijk = new PlControleHistorieAnummerGelijk();
        this.plControleDezelfdePersoon = new PlControleDezelfdePersoon();
        this.plControleActueelBsnGelijk = new PlControleActueelBsnGelijk();
        this.plControleGevondenVersienummerGelijkOfKleiner = new PlControleGevondenVersienummerGelijkOfKleiner();
        this.plControleGevondenDatumtijdstempelOuder = new PlControleGevondenDatumtijdstempelOuder();
    }

    @Override
    public boolean controleer(final VerwerkingsContext context) {
        final ControleLogging logging = new ControleLogging(ControleMelding.CONTROLE_WIJZIGING_OVERLEDEN_PERSOON);
        final List<BrpPersoonslijst> dbPersoonslijstenOpAnummer = plZoekerObvActueelAnummer.zoek(context);
        final List<BrpPersoonslijst> dbPersoonslijstenOpBsn = plZoekerObvActueelBsn.zoek(context);
        boolean result = false;

        if (lijstControleEen.controleer(dbPersoonslijstenOpAnummer)) {
            final BrpPersoonslijst dbPersoonslijst = dbPersoonslijstenOpAnummer.get(0);
            result = plControleGevondenBlokkeringssituatieIsJuist.controleer(context, dbPersoonslijst);
            result = result && plControleBijhoudingsPartijGelijk.controleer(context, dbPersoonslijst);
            result = result && plControleGevondenPersoonOpgeschortRedenO.controleer(context, dbPersoonslijst);
            result = result && plControleBijhoudingsPartijOngelijkVerzendendeGemeente.controleer(context, dbPersoonslijst);
            result = result && plControleVorigAnummerGelijk.controleer(context, dbPersoonslijst);
            result = result && plControleHistorieAnummerGelijk.controleer(context, dbPersoonslijst);
            result = result && plControleDezelfdePersoon.controleer(context, dbPersoonslijst);
            result = result && (  plControleActueelBsnGelijk.controleer(context, dbPersoonslijst)
                               || lijstControleGeen.controleer(dbPersoonslijstenOpBsn)
                               );
            result = result && plControleGevondenVersienummerGelijkOfKleiner.controleer(context, dbPersoonslijst);
            result = result && plControleGevondenDatumtijdstempelOuder.controleer(context, dbPersoonslijst);
        }

        logging.logResultaat(result, true);
        return result;
    }
}
