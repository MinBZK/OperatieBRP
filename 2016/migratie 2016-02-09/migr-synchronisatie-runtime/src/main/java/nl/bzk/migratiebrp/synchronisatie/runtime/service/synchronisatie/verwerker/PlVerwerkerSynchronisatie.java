/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpAntwoordBericht;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieBeslissing;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieLogging;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.Controle;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.exception.SynchronisatieVerwerkerException;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.helper.SynchronisatieHelper;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.pl.PlService;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.context.VerwerkingsContext;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.logging.PlVerwerkerLogging;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.logging.PlVerwerkerMelding;

import org.springframework.stereotype.Component;

/**
 * Verwerk een synchronisatiebericht (UC220).
 */
@Component(value = "plVerwerkerSynchronisatie")
public final class PlVerwerkerSynchronisatie implements PlVerwerker {

    @Inject
    @Named("controleReguliereWijziging")
    private Controle controleReguliereWijziging;
    @Inject
    @Named("controleReguliereVerhuizing")
    private Controle controleReguliereVerhuizing;
    @Inject
    @Named("controleEmigratie")
    private Controle controleEmigratie;
    @Inject
    @Named("controleAnummerWijziging")
    private Controle controleAnummerWijziging;
    @Inject
    @Named("controleNieuwePersoonslijst")
    private Controle controleNieuwePersoonslijst;
    @Inject
    @Named("controlePersoonslijstIsOuder")
    private Controle controlePersoonslijstIsOuder;
    @Inject
    @Named("controleBevatBlokkeringsinformatie")
    private Controle controleBevatBlokkeringsinformatie;
    @Inject
    @Named("controlePersoonslijstIsGelijk")
    private Controle controlePersoonslijstIsGelijk;

    @Inject
    private PlService plService;

    @Override
    public SynchroniseerNaarBrpAntwoordBericht verwerk(final VerwerkingsContext context) throws SynchronisatieVerwerkerException {
        final PlVerwerkerLogging logging = new PlVerwerkerLogging(PlVerwerkerMelding.PL_VERWERKER_SYNCHRONISATIE);

        final SynchroniseerNaarBrpAntwoordBericht result;
        if (controleBevatBlokkeringsinformatie.controleer(context)) {
            // Negeren
            logging.addBeslissing(SynchronisatieBeslissing.SYNCHRONISATIE_PERSOONSLIJST_GEBLOKKEERD);
            throw new SynchronisatieVerwerkerException(StatusType.GENEGEERD);

        } else if (controleReguliereWijziging.controleer(context)) {
            // Vervangen
            logging.addBeslissing(SynchronisatieBeslissing.SYNCHRONISATIE_REGULIER_WIJZIGEN);
            result = verwerkVervangen(context, context.getAnummer(), false);

        } else if (controleReguliereVerhuizing.controleer(context)) {
            // Vervangen
            logging.addBeslissing(SynchronisatieBeslissing.SYNCHRONISATIE_REGULIER_VERHUIZEN);
            result = verwerkVervangen(context, context.getAnummer(), false);

        } else if (controleEmigratie.controleer(context)) {
            // Vervangen
            logging.addBeslissing(SynchronisatieBeslissing.SYNCHRONISATIE_EMIGRATIE);
            result = verwerkVervangen(context, context.getAnummer(), false);

        } else if (controleAnummerWijziging.controleer(context)) {
            // Vervangen
            logging.addBeslissing(SynchronisatieBeslissing.SYNCHRONISATIE_ANUMMER_WIJZIGING);
            result = verwerkVervangen(context, context.getVorigAnummer(), true);

        } else if (controleNieuwePersoonslijst.controleer(context)) {
            // Toevoegen
            logging.addBeslissing(SynchronisatieBeslissing.SYNCHRONISATIE_TOEVOEGEN);
            result = verwerkToevoegen(context);

        } else if (controlePersoonslijstIsOuder.controleer(context)) {
            // Negeren
            logging.addBeslissing(SynchronisatieBeslissing.SYNCHRONISATIE_PERSOONSLIJST_OUDER);
            throw new SynchronisatieVerwerkerException(StatusType.GENEGEERD);

        } else if (controlePersoonslijstIsGelijk.controleer(context)) {
            // Negeren
            logging.addBeslissing(SynchronisatieBeslissing.SYNCHRONISATIE_PERSOONSLIJST_GELIJK);
            throw new SynchronisatieVerwerkerException(StatusType.GENEGEERD);

        } else {
            // Onduidelijk
            logging.addBeslissing(SynchronisatieBeslissing.SYNCHRONISATIE_ONDUIDELIJK);
            throw new SynchronisatieVerwerkerException(StatusType.ONDUIDELIJK, context.getKandidaten());
        }

        return result;
    }

    private SynchroniseerNaarBrpAntwoordBericht verwerkToevoegen(final VerwerkingsContext context) {
        // Opslaan
        final List<Long> administratieveHandelingIds = plService.persisteerPersoonslijst(context.getBrpPersoonslijst(), context.getLoggingBericht());

        // Maak antwoord
        final SynchroniseerNaarBrpAntwoordBericht result = SynchronisatieHelper.maakAntwoord(context.getVerzoek(), StatusType.TOEGEVOEGD);
        context.getLoggingBericht().setVerwerkingsmelding(SynchronisatieLogging.getMelding());
        result.setAdministratieveHandelingIds(administratieveHandelingIds);

        // Done
        return result;
    }

    private SynchroniseerNaarBrpAntwoordBericht verwerkVervangen(
        final VerwerkingsContext context,
        final long teVervangenAnummer,
        final boolean isAnummerWijziging)
    {
        // Opslaan
        final List<Long> administratieveHandelingIds =
                plService.persisteerPersoonslijst(context.getBrpPersoonslijst(), teVervangenAnummer, isAnummerWijziging, context.getLoggingBericht());

        // Maak antwoord
        final SynchroniseerNaarBrpAntwoordBericht result = SynchronisatieHelper.maakAntwoord(context.getVerzoek(), StatusType.VERVANGEN);
        context.getLoggingBericht().setVerwerkingsmelding(SynchronisatieLogging.getMelding());
        result.setAdministratieveHandelingIds(administratieveHandelingIds);

        // Done
        return result;
    }
}
