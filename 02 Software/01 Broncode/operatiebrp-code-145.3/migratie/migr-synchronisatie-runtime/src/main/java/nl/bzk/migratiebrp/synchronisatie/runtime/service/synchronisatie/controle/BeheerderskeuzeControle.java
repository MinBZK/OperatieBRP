/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle;

import javax.inject.Inject;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieBeslissing;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.logging.ControleLogging;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.logging.ControleMelding;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.logging.ControleUitkomst;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.exception.SynchronisatieVerwerkerException;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.context.VerwerkingsContext;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.logging.PlVerwerkerLogging;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.logging.PlVerwerkerMelding;
import org.springframework.stereotype.Component;

/**
 * Controle klasse voor het controleren van de beheerderskeuze.
 */
@Component
public class BeheerderskeuzeControle {

    private final PersoonslijstVersieControle versieControle;

    /**
     * Constructor.
     * @param versieControle versie controle
     */
    @Inject
    public BeheerderskeuzeControle(final PersoonslijstVersieControle versieControle) {
        this.versieControle = versieControle;
    }

    /**
     * Controleer inhoudelijk de beheerderskeuze.
     * @param verwerkingsContext De verwerkings context
     * @return De uitkomst van de controle
     * @throws SynchronisatieVerwerkerException In het geval van een ongeldige beheerderskeuze
     */
    public final ControleUitkomst controle(final VerwerkingsContext verwerkingsContext) throws SynchronisatieVerwerkerException {

        final ControleLogging logging = new ControleLogging(ControleMelding.CONTROLE_BEHEERDERSKEUZE);
        final PlVerwerkerLogging beslissingLogger = new PlVerwerkerLogging(PlVerwerkerMelding.SYNCHRONISATIE_VERWERKER_BEHEERDERS_KEUZE);

        final ControleUitkomst resultaatBeheerdersKeuze;

        if (verwerkingsContext.getVerzoek().getBeheerderKeuze() != null) {
            if (!versieControle.controle(verwerkingsContext)) {
                resultaatBeheerdersKeuze = ControleUitkomst.ONDUIDELIJK;
                beslissingLogger.addBeslissing(SynchronisatieBeslissing.BEHEERDERS_KEUZE_ONDUIDELIJK);
            } else {
                switch (verwerkingsContext.getVerzoek().getBeheerderKeuze().getKeuze()) {
                    case AFKEUREN:
                        resultaatBeheerdersKeuze = ControleUitkomst.AFKEUREN;
                        beslissingLogger.addBeslissing(SynchronisatieBeslissing.BEHEERDERS_KEUZE_AFKEUREN);
                        break;
                    case NEGEREN:
                        resultaatBeheerdersKeuze = ControleUitkomst.NEGEREN;
                        beslissingLogger.addBeslissing(SynchronisatieBeslissing.BEHEERDERS_KEUZE_NEGEREN);
                        break;
                    case TOEVOEGEN:
                        resultaatBeheerdersKeuze = ControleUitkomst.TOEVOEGEN;
                        beslissingLogger.addBeslissing(SynchronisatieBeslissing.BEHEERDERS_KEUZE_NIEUW_TOEVOEGEN);
                        break;
                    case VERVANGEN:
                        resultaatBeheerdersKeuze = ControleUitkomst.VERVANGEN;
                        beslissingLogger.addBeslissing(SynchronisatieBeslissing.BEHEERDERS_KEUZE_VERVANGEN);
                        break;
                    default:
                        throw new SynchronisatieVerwerkerException(StatusType.FOUT, new IllegalArgumentException("Ongeldige beheerderskeuze."));

                }
            }
        } else {
            resultaatBeheerdersKeuze = ControleUitkomst.ONDUIDELIJK;
            beslissingLogger.addBeslissing(SynchronisatieBeslissing.BEHEERDERS_KEUZE_ONDUIDELIJK);
        }

        logging.logResultaat(resultaatBeheerdersKeuze);
        return resultaatBeheerdersKeuze;
    }

}
