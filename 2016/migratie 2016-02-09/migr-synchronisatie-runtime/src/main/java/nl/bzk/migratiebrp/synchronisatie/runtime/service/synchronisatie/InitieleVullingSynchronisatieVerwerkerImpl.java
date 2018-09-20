/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie;

import javax.inject.Inject;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpVerzoekBericht;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3Bericht;
import nl.bzk.migratiebrp.synchronisatie.dal.service.BrpDalService;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieBeslissing;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieLogging;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.exception.SynchronisatieVerwerkerException;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.helper.SynchronisatieHelper;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.logging.PlVerwerkerLogging;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.logging.PlVerwerkerMelding;
import org.springframework.stereotype.Component;

/**
 * Implementatie van {@link nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.SynchronisatieVerwerker}
 * voor initiele vulling.
 */
@Component(value = "initieleVullingSynchronisatieVerwerker")
public final class InitieleVullingSynchronisatieVerwerkerImpl extends AbstractSynchronisatieVerwerkerImpl implements SynchronisatieVerwerker {

    @Inject
    private BrpDalService brpDalService;

    @Override
    public SynchroniseerNaarBrpAntwoordBericht verwerk(final SynchroniseerNaarBrpVerzoekBericht verzoek, final Lo3Bericht loggingBericht)
        throws SynchronisatieVerwerkerException
    {
        final PlVerwerkerLogging logging = new PlVerwerkerLogging(PlVerwerkerMelding.SYNCHRONISATIE_VERWERKER_INITIELE_VULLING);

        // Parse LO3 persoonslijst uit verzoek
        Lo3Persoonslijst lo3Persoonslijst = parsePersoonslijst(logging, verzoek);

        // Controleer groep 80
        if (lo3Persoonslijst.getInschrijvingStapel() != null && lo3Persoonslijst.isGroep80VanInschrijvingStapelLeeg()) {
            logging.addBeslissing(SynchronisatieBeslissing.INIT_VULLING_AANVULLEN_GROEP_80);
            lo3Persoonslijst = lo3Persoonslijst.maakKopieMetDefaultGroep80VoorInschrijvingStapel();
        }

        // Converteer LO3 persoonslijst naar BRP persoonslijst
        final BrpPersoonslijst brpPersoonslijst = converteerPersoonslijst(logging, lo3Persoonslijst);

        // Controleer dat actueel a-nummer niet in BRP database bestaat
        if (brpDalService.zoekPersoonOpAnummer(brpPersoonslijst.getActueelAdministratienummer()) != null) {
            logging.addBeslissing(SynchronisatieBeslissing.INIT_VULLING_ANUMMER_GEVONDEN);
            throw new SynchronisatieVerwerkerException(StatusType.FOUT);
        }

        // Maak antwoord (registreer resultaat in logging)
        logging.addBeslissing(SynchronisatieBeslissing.INIT_VULLING_TOEVOEGEN);
        // Opslaan
        brpDalService.persisteerPersoonslijst(brpPersoonslijst, loggingBericht);

        final SynchroniseerNaarBrpAntwoordBericht result = SynchronisatieHelper.maakAntwoord(verzoek, StatusType.TOEGEVOEGD);
        loggingBericht.setVerwerkingsmelding(SynchronisatieLogging.getMelding());

        // Done
        return result;

    }

}
