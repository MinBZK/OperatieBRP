/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker;

import java.util.List;
import javax.inject.Inject;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpAntwoordBericht;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieBeslissing;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieLogging;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.exception.SynchronisatieVerwerkerException;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.helper.SynchronisatieHelper;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.pl.PlService;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.context.VerwerkingsContext;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.logging.PlVerwerkerLogging;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.logging.PlVerwerkerMelding;
import org.springframework.stereotype.Component;

/**
 * Beheerder keuze: nieuw.
 */
@Component(value = "plVerwerkerBeheerdersKeuzeNieuw")
public final class PlVerwerkerBeheerdersKeuzeNieuw implements PlVerwerker {

    @Inject
    private PlService plService;

    @Override
    public SynchroniseerNaarBrpAntwoordBericht verwerk(final VerwerkingsContext context) throws SynchronisatieVerwerkerException {
        final PlVerwerkerLogging logging = new PlVerwerkerLogging(PlVerwerkerMelding.PL_VERWERKER_KEUZE_NIEUW);
        final long anummer = context.getAnummer();
        logging.addMelding("Actueel a-nummer: " + anummer);

        // Controleer dat actueel a-nummer niet in BRP database bestaat
        final BrpPersoonslijst dbPersoonslijst = plService.zoekNietFoutievePersoonslijstOpActueelAnummer(anummer);
        if (dbPersoonslijst != null) {
            logging.addBeslissing(SynchronisatieBeslissing.BEHEERDERS_KEUZE_NIEUW_GEVONDEN);
            throw new SynchronisatieVerwerkerException(StatusType.FOUT);
        }

        // Maak antwoord (registreer resultaat in logging)
        logging.addBeslissing(SynchronisatieBeslissing.BEHEERDERS_KEUZE_NIEUW_TOEVOEGEN);

        // Opslaan
        final List<Long> administratieveHandelingIds = plService.persisteerPersoonslijst(context.getBrpPersoonslijst(), context.getLoggingBericht());

        final SynchroniseerNaarBrpAntwoordBericht result = SynchronisatieHelper.maakAntwoord(context.getVerzoek(), StatusType.TOEGEVOEGD);
        context.getLoggingBericht().setVerwerkingsmelding(SynchronisatieLogging.getMelding());
        result.setAdministratieveHandelingIds(administratieveHandelingIds);

        // Done
        return result;
    }
}
