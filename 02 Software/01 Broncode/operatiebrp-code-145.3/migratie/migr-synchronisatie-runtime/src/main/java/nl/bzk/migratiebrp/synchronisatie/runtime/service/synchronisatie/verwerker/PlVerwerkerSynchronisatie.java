/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker;

import javax.inject.Inject;
import javax.inject.Named;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieBeslissing;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.Controle;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.logging.ControleUitkomst;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.zoeker.PlZoeker;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.zoeker
        .PlZoekerOpActueelEnHistorischAnummerEnNietFoutiefOpgeschortObvActueelAnummer;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.zoeker
        .PlZoekerOpActueelEnHistorischVolgendeAnummerEnNietFoutiefOpgeschortObvActueelAnummer;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.zoeker
        .PlZoekerOpActueelEnHistorischVorigeAnummerEnNietFoutiefOpgeschortObvActueelAnummer;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.pl.PlService;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.context.VerwerkingsContext;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.logging.PlVerwerkerLogging;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.logging.PlVerwerkerMelding;
import org.springframework.stereotype.Component;

/**
 * Verwerk een synchronisatiebericht (UC220). Beter bekend als de Beslisboom.
 */
@Component
public final class PlVerwerkerSynchronisatie implements PlVerwerker {

    private final Controle controleReguliereWijziging;
    private final Controle controleReguliereVerhuizing;
    private final Controle controleEmigratie;
    private final Controle controleAnummerWijziging;
    private final Controle controleWijzigingOverledenPersoon;
    private final Controle controleNieuwePersoonslijst;
    private final Controle controlePersoonslijstIsOuder;
    private final Controle controleBevatBlokkeringsinformatie;
    private final Controle controlePersoonslijstIsGelijk;
    private final PlZoeker plZoekerOpAnummer;
    private final PlZoeker plZoekerOpVorigeAnummer;
    private final PlZoeker plZoekerOpVolgendeAnummer;

    /**
     * Constructor.
     * @param controleReguliereWijziging controle reguliere wijziging
     * @param controleReguliereVerhuizing controle reguliere verhuizing
     * @param controleEmigratie controle emigratie
     * @param controleAnummerWijziging controle anummer wijziging
     * @param controleWijzigingOverledenPersoon controle wijziging overleden persoon
     * @param controleNieuwePersoonslijst controle nieuwe persoonslijst
     * @param controlePersoonslijstIsOuder controle persoonslijst is ouder
     * @param controleBevatBlokkeringsinformatie controle bevat blokkeringsinformatie
     * @param controlePersoonslijstIsGelijk controle persoonslijst is gelijk
     * @param plService persoonslijst service
     */
    @Inject
    public PlVerwerkerSynchronisatie(@Named("controleReguliereWijziging") final Controle controleReguliereWijziging,
                                     @Named("controleReguliereVerhuizing") final Controle controleReguliereVerhuizing,
                                     @Named("controleEmigratie") final Controle controleEmigratie,
                                     @Named("controleAnummerWijziging") final Controle controleAnummerWijziging,
                                     @Named("controleWijzigingOverledenPersoon") final Controle controleWijzigingOverledenPersoon,
                                     @Named("controleNieuwePersoonslijst") final Controle controleNieuwePersoonslijst,
                                     @Named("controlePersoonslijstIsOuder") final Controle controlePersoonslijstIsOuder,
                                     @Named("controleBevatBlokkeringsinformatie") final Controle controleBevatBlokkeringsinformatie,
                                     @Named("controlePersoonslijstIsGelijk") final Controle controlePersoonslijstIsGelijk,
                                     PlService plService) {
        this.controleReguliereWijziging = controleReguliereWijziging;
        this.controleReguliereVerhuizing = controleReguliereVerhuizing;
        this.controleEmigratie = controleEmigratie;
        this.controleAnummerWijziging = controleAnummerWijziging;
        this.controleWijzigingOverledenPersoon = controleWijzigingOverledenPersoon;
        this.controleNieuwePersoonslijst = controleNieuwePersoonslijst;
        this.controlePersoonslijstIsOuder = controlePersoonslijstIsOuder;
        this.controleBevatBlokkeringsinformatie = controleBevatBlokkeringsinformatie;
        this.controlePersoonslijstIsGelijk = controlePersoonslijstIsGelijk;

        plZoekerOpAnummer = new PlZoekerOpActueelEnHistorischAnummerEnNietFoutiefOpgeschortObvActueelAnummer(plService);
        plZoekerOpVorigeAnummer = new PlZoekerOpActueelEnHistorischVorigeAnummerEnNietFoutiefOpgeschortObvActueelAnummer(plService);
        plZoekerOpVolgendeAnummer = new PlZoekerOpActueelEnHistorischVolgendeAnummerEnNietFoutiefOpgeschortObvActueelAnummer(plService);
    }

    /**
     * Controleer bericht en bepaal de te nemen actie.
     * @param context verwerkingscontext
     * @return de uitkomst
     */
    @Override
    public ControleUitkomst  controle(final VerwerkingsContext context) {
        final PlVerwerkerLogging logging = new PlVerwerkerLogging(PlVerwerkerMelding.PL_VERWERKER_SYNCHRONISATIE);

        // Deze controles komen overeen met de controles zoals beschreven in UC220.
        final ControleUitkomst result;
        if (controleReguliereWijziging.controleer(context)) {
            // 1. Vervangen: reguliere wijziging door de gemeente van bijhouding
            logging.addBeslissing(SynchronisatieBeslissing.SYNCHRONISATIE_REGULIER_WIJZIGEN);
            result = ControleUitkomst.VERVANGEN;

        } else if (controleReguliereVerhuizing.controleer(context)) {
            // 2. Vervangen: reguliere wijziging door verhuizing of gemeentelijke herindeling
            logging.addBeslissing(SynchronisatieBeslissing.SYNCHRONISATIE_REGULIER_VERHUIZEN);
            result = ControleUitkomst.VERVANGEN;

        } else if (controleEmigratie.controleer(context)) {
            // 3. Vervangen: emigratie (RNI)
            logging.addBeslissing(SynchronisatieBeslissing.SYNCHRONISATIE_EMIGRATIE);
            result = ControleUitkomst.VERVANGEN;

        } else if (controleAnummerWijziging.controleer(context)) {
            // 4. Vervangen: a-nummer wijziging door de gemeente van bijhouding
            logging.addBeslissing(SynchronisatieBeslissing.SYNCHRONISATIE_ANUMMER_WIJZIGING);
            result = ControleUitkomst.VERVANGEN;

        } else if (controleWijzigingOverledenPersoon.controleer(context)) {
            // 5. Vervangen: wijziging van opgeschorte persoonslijst met reden 'O' door een andere gemeente
            logging.addBeslissing(SynchronisatieBeslissing.SYNCHRONISATIE_WIJZIGEN_OVERLEDEN_PERSOON);
            result = ControleUitkomst.VERVANGEN;

        } else if (controleNieuwePersoonslijst.controleer(context)) {
            // 6. Toevoegen
            logging.addBeslissing(SynchronisatieBeslissing.SYNCHRONISATIE_TOEVOEGEN);
            result = ControleUitkomst.TOEVOEGEN;

        } else if (controlePersoonslijstIsOuder.controleer(context)) {
            // 7. Negeren: de aangeboden persoonslijst is ouder
            logging.addBeslissing(SynchronisatieBeslissing.SYNCHRONISATIE_PERSOONSLIJST_OUDER);
            result = ControleUitkomst.NEGEREN;

        } else if (controleBevatBlokkeringsinformatie.controleer(context)) {
            // 8. Negeren: de aangeboden persoonslijst bevat blokkeringsinformatie
            logging.addBeslissing(SynchronisatieBeslissing.SYNCHRONISATIE_PERSOONSLIJST_GEBLOKKEERD);
            result = ControleUitkomst.NEGEREN;

        } else if (controlePersoonslijstIsGelijk.controleer(context)) {
            // 9. Negeren: de persoonslijsten zijn gelijk
            logging.addBeslissing(SynchronisatieBeslissing.SYNCHRONISATIE_PERSOONSLIJST_GELIJK);
            result = ControleUitkomst.NEGEREN;

        } else {
            // Onduidelijk: indien het systeem geen van bovenstaande resultaten kan bepalen.
            logging.addBeslissing(SynchronisatieBeslissing.SYNCHRONISATIE_ONDUIDELIJK);
            result = ControleUitkomst.ONDUIDELIJK;

            // Als een onduidelijke situatie wordt vastgesteld dienen eventuele kandidaat persoonslijsten
            // voorgelegd te worden aan de beheerder. Een persoonslijst in de BRP is kandidaat als het
            // a-nummer van de aangeboden persoonlijst voorkomt in:

            // de historie van a-nummers, of
            plZoekerOpAnummer.zoek(context);
            // de historie van vorige a-nummers, of
            plZoekerOpVorigeAnummer.zoek(context);
            // de historie van volgende a-nummers, of
            plZoekerOpVolgendeAnummer.zoek(context);
        }

        return result;
    }
}
