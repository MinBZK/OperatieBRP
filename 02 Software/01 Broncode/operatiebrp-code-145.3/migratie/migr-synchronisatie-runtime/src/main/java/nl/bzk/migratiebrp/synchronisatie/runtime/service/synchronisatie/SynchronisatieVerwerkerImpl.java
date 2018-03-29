/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Bericht;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpVerzoekBericht;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpPartijCode;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.regels.proces.ConverteerLo3NaarBrpService;
import nl.bzk.migratiebrp.conversie.regels.proces.preconditie.Lo3SyntaxControle;
import nl.bzk.migratiebrp.conversie.regels.proces.preconditie.PreconditiesService;
import nl.bzk.migratiebrp.synchronisatie.dal.service.TeLeverenAdministratieveHandelingenAanwezigException;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.BeheerderskeuzeControle;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.logging.ControleUitkomst;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.exception.SynchronisatieVerwerkerException;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.helper.SynchronisatieHelper;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.pl.PlService;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.PlVerwerkerSynchronisatie;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.context.VerwerkingsContext;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.logging.PlVerwerkerLogging;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.logging.PlVerwerkerMelding;
import org.springframework.stereotype.Component;

/**
 * Implementatie van
 * {@link nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.SynchronisatieVerwerker}
 * voor gewone operatie.
 */
@Component(value = "synchronisatieVerwerker")
public final class SynchronisatieVerwerkerImpl extends AbstractSynchronisatieVerwerkerImpl implements SynchronisatieVerwerker {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private final PlService plService;
    private final PlVerwerkerSynchronisatie plVerwerkerSynchronisatie;
    private final BeheerderskeuzeControle beheerderskeuzeControle;


    /**
     * Constructor.
     * @param syntaxControle syntax controle
     * @param preconditieService preconditie service
     * @param converteerLo3NaarBrpService converteer lo3 naar brp service
     * @param plService pl service
     * @param plVerwerkerSynchronisatie pl verwerker
     * @param beheerderskeuzeControle beheerderskeuze controle
     */
    @Inject
    public SynchronisatieVerwerkerImpl(final Lo3SyntaxControle syntaxControle,
                                       final PreconditiesService preconditieService,
                                       final ConverteerLo3NaarBrpService converteerLo3NaarBrpService,
                                       final PlService plService,
                                       final PlVerwerkerSynchronisatie plVerwerkerSynchronisatie,
                                       final BeheerderskeuzeControle beheerderskeuzeControle) {
        super(syntaxControle, preconditieService, converteerLo3NaarBrpService);
        this.plService = plService;
        this.plVerwerkerSynchronisatie = plVerwerkerSynchronisatie;
        this.beheerderskeuzeControle = beheerderskeuzeControle;
    }

    @Override
    public SynchroniseerNaarBrpAntwoordBericht verwerk(final SynchroniseerNaarBrpVerzoekBericht verzoek, final Lo3Bericht loggingBericht)
            throws SynchronisatieVerwerkerException {
        final PlVerwerkerLogging logging = new PlVerwerkerLogging(PlVerwerkerMelding.SYNCHRONISATIE_VERWERKER_SYNCHRONISATIE);

        // Parse LO3 persoonslijst uit verzoek
        final Lo3Persoonslijst lo3Persoonslijst = parsePersoonslijst(logging, verzoek);

        // Converteer LO3 persoonslijst naar BRP persoonslijst
        final BrpPersoonslijst brpPersoonslijst = converteerPersoonslijst(logging, lo3Persoonslijst);

        // Converteer verzendende gemeente naar BRPPartijcode
        final BrpPartijCode brpVerzendendePartij = new BrpPartijCode(verzoek.getVerzendendeGemeente());

        final VerwerkingsContext verwerkingsContext = new VerwerkingsContext(verzoek, loggingBericht, lo3Persoonslijst, brpPersoonslijst, brpVerzendendePartij);

        // Verwerking als volgt:
        // Stap 1: Eerste beslissing door beslisboom, status 'Onduidelijk' gaat naar stap 2,
        // statussen 'Toegevoegd' en 'Vervangen' gaan naar stap 3 en de statussen 'Afgekeurd'
        // en 'Genegeerd' gaan naar stap 4.
        // Stap 2: Voor status 'Onduidelijk' is verdere verwerking nodig van de beheerderskeuze.
        // Stap 3: Als status 'Toegevoegd' of 'Vervangen' is, dient de aangeleverde persoonslijst
        // gepersisteerd te worden.
        // Stap 4: Einde, retourneren antwoordbericht aan service.

        // Stap 1
        ControleUitkomst resultaat = plVerwerkerSynchronisatie.controle(verwerkingsContext);

        // Stap 2
        if (ControleUitkomst.ONDUIDELIJK.equals(resultaat)) {
            resultaat = beheerderskeuzeControle.controle(verwerkingsContext);
        }

        // Lijst voor de administratievehandelingen.
        List<Long> administratieveHandelingIds = new ArrayList<>();

        // Stap 3
        StatusType synchronisatieResultaat;

        switch (resultaat) {
            case TOEVOEGEN:
                // Toevoegen persoonslijst in de database.
                administratieveHandelingIds =
                        plService.persisteerPersoonslijst(verwerkingsContext.getBrpPersoonslijst(), verwerkingsContext.getLoggingBericht());
                synchronisatieResultaat = StatusType.TOEGEVOEGD;
                break;
            case VERVANGEN:
                // Vervangen aangegeven persoonslijst door de nieuwe.
                final Long persoonslijstId = verwerkingsContext.getKandidaten().get(0).getPersoonId();
                synchronisatieResultaat = verwerkVervangActie(persoonslijstId, administratieveHandelingIds, verwerkingsContext);
                break;
            case AFKEUREN:
                synchronisatieResultaat = StatusType.AFGEKEURD;
                break;
            case NEGEREN:
                synchronisatieResultaat = StatusType.GENEGEERD;
                break;
            case ONDUIDELIJK:
                synchronisatieResultaat = StatusType.ONDUIDELIJK;
                break;
            default:
                throw new IllegalStateException("Uitkomst uit de beslisboom heeft een ongeldige waarde.");
        }

        // Stap 4
        final SynchroniseerNaarBrpAntwoordBericht antwoord = SynchronisatieHelper.maakAntwoord(verzoek, synchronisatieResultaat);
        if (ControleUitkomst.ONDUIDELIJK.equals(resultaat)) {
            antwoord.setKandidaten(Arrays.asList(plService.converteerKandidaten(verwerkingsContext.getKandidaten())));
        }
        antwoord.setAdministratieveHandelingIds(administratieveHandelingIds);

        return antwoord;
    }

    private StatusType verwerkVervangActie(final Long persoonslijstId, final List<Long> administratieveHandelingIds,
                                           final VerwerkingsContext verwerkingsContext) {
        try {
            administratieveHandelingIds.addAll(plService.persisteerPersoonslijst(verwerkingsContext.getBrpPersoonslijst(), persoonslijstId,
                    verwerkingsContext.getLoggingBericht()));
            return StatusType.VERVANGEN;
        } catch (final TeLeverenAdministratieveHandelingenAanwezigException e) {
            LOGGER.debug("Te leveren administratieve handelingen aanwezig", e);
            return StatusType.VORIGE_HANDELINGEN_NIET_GELEVERD;
        }
    }
}
