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
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.lijst.LijstControleGeenActueelAnummer;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.lijst.LijstControleGeenBurgerServicenummer;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.logging.ControleLogging;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.logging.ControleMelding;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl.PlControle;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl.PlControleAnummerHistorischGelijk;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl.PlControleBijhoudingsPartijGelijkVerzendendeGemeente;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.pl.PlControleNietOpgeschortMetCodeF;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.zoeker.PlZoeker;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.zoeker
        .PlZoekerOpActueelEnHistorischAnummerEnNietFoutiefOpgeschortObvActueelAnummer;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.zoeker
        .PlZoekerOpActueelEnHistorischBurgerServicenummerEnNietFoutiefOpgeschortObvActueelBurgerServicenummer;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.pl.PlService;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.context.VerwerkingsContext;
import org.springframework.stereotype.Component;

/**
 * <p>
 * Samengestelde controle 'Nieuwe persoonslijst'.
 * </p>
 * Er is sprake van het als nieuw opnemen van een persoonslijst in de BRP als (postconditie Toevoegen):
 * <ol>
 * <li>de aangeboden persoonslijst geen historie van a-nummers bevat of dat alle historische a-nummers gelijk zijn aan
 * het a-nummer, en</li>
 * <li>de aangeboden persoonslijst een gemeente van bijhouding heeft dat gelijk is aan de verzendende partij van de
 * aangeboden persoonslijst, en</li>
 * <li>er in de BRP geen persoonslijst voorkomt dat een a-nummer of historisch a-nummer heeft dat gelijk is aan a-nummer
 * van de aangeboden persoonslijst (*)</li>
 * </ol>
 */
@Component(value = "controleNieuwePersoonslijst")
public final class ControleNieuwePersoonslijst implements Controle {

    private final PlZoeker plZoekerObvActueelAnummer;
    private final PlZoeker plZoekerObvActueelBurgerServicenummer;
    private final LijstControle lijstControleGeen;
    private final LijstControle lijstControleGeenBsn;
    private final PlControle plControleBijhoudingsPartijGelijkVerzendendeGemeente;
    private final PlControle plControleAnummerHistorischGelijk;
    private final PlControle plControleNietOpgeschortMetCodeF;

    /**
     * Constructor.
     * @param plService persoonslijst service
     */
    @Inject
    public ControleNieuwePersoonslijst(final PlService plService) {
        this.plZoekerObvActueelAnummer = new PlZoekerOpActueelEnHistorischAnummerEnNietFoutiefOpgeschortObvActueelAnummer(plService);
        this.plZoekerObvActueelBurgerServicenummer =
                new PlZoekerOpActueelEnHistorischBurgerServicenummerEnNietFoutiefOpgeschortObvActueelBurgerServicenummer(plService);
        this.lijstControleGeen = new LijstControleGeenActueelAnummer();
        this.lijstControleGeenBsn = new LijstControleGeenBurgerServicenummer();
        this.plControleBijhoudingsPartijGelijkVerzendendeGemeente = new PlControleBijhoudingsPartijGelijkVerzendendeGemeente();
        this.plControleAnummerHistorischGelijk = new PlControleAnummerHistorischGelijk();
        this.plControleNietOpgeschortMetCodeF = new PlControleNietOpgeschortMetCodeF();
    }

    @Override
    public boolean controleer(final VerwerkingsContext context) {
        final ControleLogging logging = new ControleLogging(ControleMelding.CONTROLE_NIEUWE_PL);
        final List<BrpPersoonslijst> controlePersoonslijsten = plZoekerObvActueelAnummer.zoek(context);
        final List<BrpPersoonslijst> controlePersoonslijstenBsn = plZoekerObvActueelBurgerServicenummer.zoek(context);

        boolean result = lijstControleGeen.controleer(controlePersoonslijsten);
        result = result && lijstControleGeenBsn.controleer(controlePersoonslijstenBsn);
        result = result && plControleBijhoudingsPartijGelijkVerzendendeGemeente.controleer(context, null);
        result = result && plControleAnummerHistorischGelijk.controleer(context, null);
        result = result && plControleNietOpgeschortMetCodeF.controleer(context, null);

        logging.logResultaat(result, true);
        return result;
    }
}
