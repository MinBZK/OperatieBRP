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
 * Samengestelde controle 'Reguliere wijziging door de gemeente van bijhouding'.
 * <p/>
 * Er is sprake van een wijziging door verhuizing of gemeentelijke herindeling als:
 * <ol>
 * <li>in de kop van het bericht oud a-nummer niet is gevuld, en</li>
 * <li>er in de BRP één persoonslijst voorkomt dat niet foutief opgeschort is en een a-nummer heeft dat gelijk is aan
 * het a-nummer op de aangeboden persoonslijst (*), en</li>
 * <li>de gevonden persoonslijst in de BRP een gemeente van bijhouding heeft dat ongelijk is aan de gemeente van
 * bijhouding op de aangeboden persoonslijst, en</li>
 * <li>de gevonden persoonslijst in de BRP een vorig a-nummer heeft dat gelijk is aan het vorig a-nummer op de
 * aangeboden persoonslijst of de gevonden persoonslijst in de BRP een historie van vorige a-nummers heeft die voorkomt
 * in de historie van vorige a-nummers op de aangeboden persoonslijst, en</li>
 * <li>de gevonden persoonslijst in de BRP een historie van a-nummers heeft dat gelijk is aan de historie van a-nummers
 * op de aangeboden persoonslijst, en</li>
 * <li>de persoon op de gevonden persoonslijst in de BRP dezelfde persoon is als de persoon op de aangeboden
 * persoonslijst, en</li>
 * <li>de gevonden persoonslijst in de BRP een adres en een historie van adressen heeft dat voorkomt in de historie van
 * adressen op de aangeboden persoonslijst, en</li>
 * <li>de gevonden persoonslijst in de BRP een versienummer heeft dat gelijk of kleiner is dan het versienummer van de
 * aangeboden persoonslijst, en</li>
 * <li>de gevonden persoonslijst in de BRP een datumtijdstempel heeft dat ouder is dan de datumtijdstempel van de
 * aangebonden persoonslijst</li>
 * </ol>
 */
@Component(value = "controleReguliereVerhuizing")
public final class ControleReguliereVerhuizing implements Controle {

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
    @Named(value = "plControleBijhoudingsPartijOngelijk")
    private PlControle plControleBijhoudingsPartijOngelijk;
    @Inject
    @Named(value = "plControleVorigAnummerGelijk")
    private PlControle plControleVorigAnummerGelijk;
    @Inject
    @Named(value = "plControleHistorieAnummerGelijk")
    private PlControle plControleHistorieAnummerGelijk;
    @Inject
    @Named(value = "plControleDezelfdePersoon")
    private PlControle plControleDezelfdePersoon;
    @Inject
    @Named(value = "plControleGevondenAdressenKomenVoorInHistorieAangebodenAdressen")
    private PlControle plControleGevondenAdressenKomenVoorInHistorieAangebodenAdressen;
    @Inject
    @Named(value = "plControleGevondenVersienummerGelijkOfKleiner")
    private PlControle plControleGevondenVersienummerGelijkOfKleiner;
    @Inject
    @Named(value = "plControleGevondenDatumtijdstempelOuder")
    private PlControle plControleGevondenDatumtijdstempelOuder;

    @Override
    public boolean controleer(final VerwerkingsContext context) {
        final ControleLogging logging = new ControleLogging(ControleMelding.CONTROLE_REGULIERE_VERHUIZING);
        final List<BrpPersoonslijst> dbPersoonslijsten = plZoekerOpAnummerEnNietFoutiefOpgeschortObvActueelAnummer.zoek(context);

        if (verzoekControleOudAnummerIsNietGevuld.controleer(context.getVerzoek()) && lijstControleEen.controleer(dbPersoonslijsten)) {
            final BrpPersoonslijst dbPersoonslijst = dbPersoonslijsten.get(0);

            if (dbPersoonslijst != null
                && plControleBijhoudingsPartijOngelijk.controleer(context, dbPersoonslijst)
                && plControleVorigAnummerGelijk.controleer(context, dbPersoonslijst)
                && plControleHistorieAnummerGelijk.controleer(context, dbPersoonslijst)
                && plControleDezelfdePersoon.controleer(context, dbPersoonslijst)
                && plControleGevondenAdressenKomenVoorInHistorieAangebodenAdressen.controleer(context, dbPersoonslijst)
                && plControleGevondenVersienummerGelijkOfKleiner.controleer(context, dbPersoonslijst)
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
