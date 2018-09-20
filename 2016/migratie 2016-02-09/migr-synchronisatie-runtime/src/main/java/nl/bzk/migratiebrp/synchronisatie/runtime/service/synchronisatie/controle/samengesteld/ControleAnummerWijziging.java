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
 * Samengestelde controle 'A-nummer wijziging'.
 * <p/>
 * Er is sprake van een a-nummer wijziging door de gemeente van bijhouding als:
 * <ol>
 * <li>in de kop van het bericht oud a-nummer is gevuld, en</li>
 * <li>in de kop van het bericht oud a-nummer gelijk is aan vorig a-nummer op de aangeboden persoonslijst, en</li>
 * <li>er in de BRP één persoonslijst voorkomt dat niet foutief opgeschort is en een a-nummer heeft dat gelijk is aan
 * het vorig a-nummer op de aangeboden persoonslijst (*), en</li>
 * <li>er in de BRP geen persoonslijst voorkomt met een a-nummer of historisch a-nummer dat gelijk is aan het a-nummer
 * op de aangeboden persoonslijst (*), en</li>
 * <li>de gevonden persoonslijst in de BRP een gemeenten van bijhouding heeft dat gelijk is aan de gemeente van
 * bijhouding op de aangeboden persoonslijst, en</li>
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

    @Inject
    @Named(value = "verzoekControleOudAnummerIsGevuld")
    private VerzoekControle verzoekControleOudAnummerIsGevuld;

    @Inject
    @Named(value = "plZoekerOpAnummerEnNietFoutiefOpgeschortObvActueelVorigAnummer")
    private PlZoeker plZoekerOpAnummerEnNietFoutiefOpgeschortObvActueelVorigAnummer;
    @Inject
    @Named(value = "plZoekerOpAnummerObvActueelAnummer")
    private PlZoeker plZoekerOpAnummerObvActueelAnummer;

    @Inject
    @Named(value = "lijstControleEen")
    private LijstControle lijstControleEen;
    @Inject
    @Named(value = "lijstControleGeen")
    private LijstControle lijstControleGeen;

    @Inject
    @Named(value = "plControleBijhoudingsPartijGelijk")
    private PlControle plControleBijhoudingsPartijGelijk;
    @Inject
    @Named(value = "plControleDezelfdePersoon")
    private PlControle plControleDezelfdePersoon;
    @Inject
    @Named(value = "plControleGevondenVersienummerKleiner")
    private PlControle plControleGevondenVersienummerKleiner;
    @Inject
    @Named(value = "plControleGevondenDatumtijdstempelOuder")
    private PlControle plControleGevondenDatumtijdstempelOuder;

    @Override
    public boolean controleer(final VerwerkingsContext context) {
        final ControleLogging logging = new ControleLogging(ControleMelding.CONTROLE_ANUMMER_WIJZIGING);

        final List<BrpPersoonslijst> dbPersoonslijsten = plZoekerOpAnummerEnNietFoutiefOpgeschortObvActueelVorigAnummer.zoek(context);
        final List<BrpPersoonslijst> controlePersoonslijsten = plZoekerOpAnummerObvActueelAnummer.zoek(context);

        if (verzoekControleOudAnummerIsGevuld.controleer(context.getVerzoek())
            && lijstControleEen.controleer(dbPersoonslijsten)
            && lijstControleGeen.controleer(controlePersoonslijsten))
        {
            final BrpPersoonslijst dbPersoonslijst = dbPersoonslijsten.get(0);

            if (plControleBijhoudingsPartijGelijk.controleer(context, dbPersoonslijst)
                && plControleDezelfdePersoon.controleer(context, dbPersoonslijst)
                && plControleGevondenVersienummerKleiner.controleer(context, dbPersoonslijst)
                && plControleGevondenDatumtijdstempelOuder.controleer(context, dbPersoonslijst))
            {
                logging.logResultaat(true);
                return true;
            }
        }

        logging.logResultaat(false);
        return false;
    }
}
