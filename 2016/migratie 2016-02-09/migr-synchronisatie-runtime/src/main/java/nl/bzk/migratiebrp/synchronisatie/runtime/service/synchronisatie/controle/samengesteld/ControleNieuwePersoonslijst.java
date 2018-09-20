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
 * Samengestelde controle 'Nieuwe persoonslijst'.
 * <p/>
 * Er is sprake van het als nieuw opnemen van een persoonslijst in de BRP als (postconditie Toevoegen):
 * <ol>
 * <li>in de kop van het bericht oud a-nummer niet is gevuld, en</li>
 * <li>de aangeboden persoonslijst geen historie van a-nummers bevat of dat alle historische a-nummers gelijk zijn aan
 * het a-nummer, en</li>
 * <li>er in de BRP geen persoonslijst voorkomt dat een a-nummer of historisch a-nummer heeft dat gelijk is aan a-nummer
 * van de aangeboden persoonslijst (*)</li>
 * </ol>
 */
@Component(value = "controleNieuwePersoonslijst")
public final class ControleNieuwePersoonslijst implements Controle {

    @Inject
    @Named(value = "verzoekControleOudAnummerIsNietGevuld")
    private VerzoekControle verzoekControleOudAnummerIsNietGevuld;

    @Inject
    @Named(value = "plZoekerOpAnummerObvActueelAnummer")
    private PlZoeker plZoekerOpAnummerObvActueelAnummer;

    @Inject
    @Named(value = "lijstControleGeen")
    private LijstControle lijstControleGeen;

    @Inject
    @Named(value = "plControleAnummerHistorischGelijk")
    private PlControle plControleAnummerHistorischGelijk;

    @Override
    public boolean controleer(final VerwerkingsContext context) {
        final ControleLogging logging = new ControleLogging(ControleMelding.CONTROLE_NIEUWE_PL);
        final List<BrpPersoonslijst> controlePersoonslijsten = plZoekerOpAnummerObvActueelAnummer.zoek(context);

        if (verzoekControleOudAnummerIsNietGevuld.controleer(context.getVerzoek())
            && lijstControleGeen.controleer(controlePersoonslijsten)
            && plControleAnummerHistorischGelijk.controleer(context, null))
        {
            logging.logResultaat(true);
            return true;

        }

        logging.logResultaat(false);
        return false;
    }
}
