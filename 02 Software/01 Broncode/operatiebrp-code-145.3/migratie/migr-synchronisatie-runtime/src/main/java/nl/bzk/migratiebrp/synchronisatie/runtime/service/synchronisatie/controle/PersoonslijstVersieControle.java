/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle;

import java.util.List;

import javax.inject.Inject;

import nl.bzk.migratiebrp.bericht.model.sync.generated.SynchroniseerNaarBrpVerzoekType.BeheerderKeuze.Kandidaat;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.logging.ControleLogging;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.logging.ControleMelding;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.pl.PlService;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.context.VerwerkingsContext;

import org.springframework.stereotype.Component;

/**
 * Controle klasse voor het controleren van de versies van persoonslijsten.
 */
@Component
public class PersoonslijstVersieControle {

    private final PlService plService;

    /**
     * Constructor.
     * @param plService pl service
     */
    @Inject
    public PersoonslijstVersieControle(final PlService plService) {
        this.plService = plService;
    }

    /**
     * Controleer inhoudelijk de versies van persoonslijsten.
     * @param verwerkingsContext De verwerkings context
     * @return De uitkomst van de controle
     */
    public final boolean controle(final VerwerkingsContext verwerkingsContext) {

        final ControleLogging logging = new ControleLogging(ControleMelding.CONTROLE_VERSIE_PERSOONSLIJSTEN);

        boolean resultaatVersie = true;

        final List<Kandidaat> kandidaten = verwerkingsContext.getVerzoek().getBeheerderKeuze().getKandidaat();
        if (kandidaten != null) {
            for (final Kandidaat huidigeKandidaat : kandidaten) {
                final BrpPersoonslijst persoonslijstUitDatabase = plService.zoekPersoonslijstOpTechnischeSleutel(huidigeKandidaat.getPersoonId());

                if (persoonslijstUitDatabase == null || huidigeKandidaat.getVersie() != persoonslijstUitDatabase.getAdministratieveHandelingId()) {
                    resultaatVersie = false;
                    break;
                }
            }
        }

        logging.logResultaat(resultaatVersie);
        return resultaatVersie;
    }
}
