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
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.lijst.LijstControleEenVorigAnummer;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.lijst.LijstControleGeenActueelAnummer;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.lijst.LijstControleGeenBurgerServicenummer;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.logging.ControleLogging;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.logging.ControleMelding;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl.PlControle;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl.PlControleActueelBsnGelijk;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl.PlControleBijhoudingsPartijGelijk;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl.PlControleBijhoudingsPartijGelijkVerzendendeGemeente;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl.PlControleDezelfdePersoon;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl.PlControleGevondenBlokkeringssituatieIsJuist;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl.PlControleGevondenDatumtijdstempelOuder;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl.PlControleGevondenVersienummerKleiner;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.zoeker.PlZoeker;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.zoeker.PlZoekerOpActueelAnummerEnNietFoutiefOpgeschortObvActueelVorigAnummer;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.zoeker
        .PlZoekerOpActueelEnHistorischAnummerEnNietFoutiefOpgeschortObvActueelAnummer;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.zoeker
        .PlZoekerOpActueelEnHistorischBurgerServicenummerEnNietFoutiefOpgeschortObvActueelBurgerServicenummer;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.pl.PlService;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.context.VerwerkingsContext;
import org.springframework.stereotype.Component;

/**
 * <p>
 * Samengestelde controle 'A-nummer wijziging'.
 * </p>
 * Er is sprake van een a-nummer wijziging door de gemeente van bijhouding als:
 * <ol>
 * <li>er in de BRP één persoonslijst voorkomt dat niet foutief opgeschort is en een a-nummer heeft dat gelijk is aan
 * het vorig a-nummer op de aangeboden persoonslijst (*), en</li>
 * <li>er een juiste blokkeringssituatie bestaat voor de gevonden persoonslijst (3.4), en</li>
 * <li>er in de BRP geen persoonslijst voorkomt met een a-nummer of historisch a-nummer dat gelijk is aan het a-nummer
 * op de aangeboden persoonslijst (*), en</li>
 * <li>de gevonden persoonslijst in de BRP een gemeenten van bijhouding heeft dat gelijk is aan de gemeente van
 * bijhouding op de aangeboden persoonslijst, en</li>
 * <li>de aangeboden persoonslijst een gemeente van bijhouding heeft dat gelijk is aan de verzendende partij van de
 * aangeboden persoonslijst, en</li>
 * <li>de persoon op de gevonden persoonslijst in de BRP dezelfde persoon is als de persoon op de aangeboden
 * persoonslijst, en</li>
 * <li>de gevonden persoonslijst in de BRP een versienummer heeft dat kleiner is dan het versienummer van de aangeboden
 * persoonslijst, en</li>
 * <li>de gevonden persoonslijst in de BRP een datumtijdstempel heeft dat ouder is dan de datumtijdstempel van de
 * aangebonden persoonslijst</li>
 * </ol>
 */
@Component(value = "controleAnummerWijziging")
public final class ControleAnummerWijziging implements Controle {

    private final PlZoeker plZoekerObvActueelVorigAnummer;
    private final PlZoeker plZoekerObvActueelAnummer;
    private final PlZoeker plZoekerObvActueelBsn;
    private final LijstControle lijstControleEen;
    private final LijstControle lijstControleGeen;
    private final LijstControle lijstControleGeenBsn;
    private final PlControle plControleGevondenBlokkeringssituatieIsJuist;
    private final PlControle plControleBijhoudingsPartijGelijk;
    private final PlControle plControleBijhoudingsPartijGelijkVerzendendeGemeente;
    private final PlControle plControleDezelfdePersoon;
    private final PlControle plControleActueelBsnGelijk;
    private final PlControle plControleGevondenVersienummerKleiner;
    private final PlControle plControleGevondenDatumtijdstempelOuder;

    /**
     * Constructor voor deze implementatie van {@link Controle}.
     * @param plService een implementatie van de {@link PlService}
     * @param brpDalService een implementatie van de {@link BrpDalService}
     */
    @Inject
    public ControleAnummerWijziging(final PlService plService, final BrpDalService brpDalService) {
        plZoekerObvActueelVorigAnummer = new PlZoekerOpActueelAnummerEnNietFoutiefOpgeschortObvActueelVorigAnummer(plService);
        plZoekerObvActueelAnummer = new PlZoekerOpActueelEnHistorischAnummerEnNietFoutiefOpgeschortObvActueelAnummer(plService);
        plZoekerObvActueelBsn = new PlZoekerOpActueelEnHistorischBurgerServicenummerEnNietFoutiefOpgeschortObvActueelBurgerServicenummer(plService);
        lijstControleEen = new LijstControleEenVorigAnummer();
        lijstControleGeen = new LijstControleGeenActueelAnummer();
        lijstControleGeenBsn = new LijstControleGeenBurgerServicenummer();
        plControleGevondenBlokkeringssituatieIsJuist = new PlControleGevondenBlokkeringssituatieIsJuist(brpDalService);
        plControleBijhoudingsPartijGelijk = new PlControleBijhoudingsPartijGelijk();
        plControleBijhoudingsPartijGelijkVerzendendeGemeente = new PlControleBijhoudingsPartijGelijkVerzendendeGemeente();
        plControleDezelfdePersoon = new PlControleDezelfdePersoon();
        plControleActueelBsnGelijk = new PlControleActueelBsnGelijk();
        plControleGevondenVersienummerKleiner = new PlControleGevondenVersienummerKleiner();
        plControleGevondenDatumtijdstempelOuder = new PlControleGevondenDatumtijdstempelOuder();
    }

    @Override
    public boolean controleer(final VerwerkingsContext context) {
        final ControleLogging logging = new ControleLogging(ControleMelding.CONTROLE_ANUMMER_WIJZIGING);

        final List<BrpPersoonslijst> dbPersoonslijsten = plZoekerObvActueelVorigAnummer.zoek(context);
        final List<BrpPersoonslijst> controlePersoonslijsten = plZoekerObvActueelAnummer.zoek(context);
        final List<BrpPersoonslijst> dbPersoonslijstenOpBsn = plZoekerObvActueelBsn.zoek(context);
        boolean result = false;

        // Controleer of de lijst uit 1 persoonslijst bestaat.
        if (lijstControleEen.controleer(dbPersoonslijsten)) {

            // Controleer de blokkeringsituatie van de gevonden persoonslijst.
            final BrpPersoonslijst dbPersoonslijst = dbPersoonslijsten.get(0);
            result = plControleGevondenBlokkeringssituatieIsJuist.controleer(context, dbPersoonslijst);

            // Controleer dat er geen persoonslijst voorkomt met het nieuwe a-nummer.
            result = result && lijstControleGeen.controleer(controlePersoonslijsten);

            // Basis controles zijn geslaagd, doorloop nu de overige controles.
            if (result) {
                result = plControleBijhoudingsPartijGelijk.controleer(context, dbPersoonslijst);
                result = result && plControleBijhoudingsPartijGelijkVerzendendeGemeente.controleer(context, dbPersoonslijst);
                result = result && plControleDezelfdePersoon.controleer(context, dbPersoonslijst);
                result = result && (  plControleActueelBsnGelijk.controleer(context, dbPersoonslijst)
                                   || lijstControleGeenBsn.controleer(dbPersoonslijstenOpBsn)
                                   );
                result = result && plControleGevondenVersienummerKleiner.controleer(context, dbPersoonslijst);
                result = result && plControleGevondenDatumtijdstempelOuder.controleer(context, dbPersoonslijst);
            }
        }

        logging.logResultaat(result, true);
        return result;
    }
}
