/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import nl.bzk.migratiebrp.bericht.model.sync.generated.StatusType;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpAntwoordBericht;
import nl.bzk.migratiebrp.bericht.model.sync.impl.SynchroniseerNaarBrpVerzoekBericht;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3Bericht;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieBeslissing;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieLogging;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.controle.Controle;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.exception.SynchronisatieVerwerkerException;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.helper.SynchronisatieHelper;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.pl.PlService;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.PlVerwerker;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.context.VerwerkingsContext;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.logging.PlVerwerkerLogging;
import nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.verwerker.logging.PlVerwerkerMelding;
import org.springframework.stereotype.Component;

/**
 * Implementatie van {@link nl.bzk.migratiebrp.synchronisatie.runtime.service.synchronisatie.SynchronisatieVerwerker}
 * voor gewone operatie.
 */
@Component(value = "synchronisatieVerwerker")
public final class SynchronisatieVerwerkerImpl extends AbstractSynchronisatieVerwerkerImpl implements SynchronisatieVerwerker {

    @Inject
    private PlService plService;

    @Inject
    @Named(value = "plVerwerkerBeheerdersKeuzeNieuw")
    private PlVerwerker plVerwerkerBeheerdersKeuzeNieuw;
    @Inject
    @Named(value = "plVerwerkerBeheerdersKeuzeVervang")
    private PlVerwerker plVerwerkerBeheerdersKeuzeVervang;
    @Inject
    @Named(value = "plVerwerkerSynchronisatie")
    private PlVerwerker plVerwerkerSynchronisatie;

    @Inject
    @Named(value = "controleGezaghebbend")
    private Controle controleGezaghebbend;

    @Override
    public SynchroniseerNaarBrpAntwoordBericht verwerk(final SynchroniseerNaarBrpVerzoekBericht verzoek, final Lo3Bericht loggingBericht)
        throws SynchronisatieVerwerkerException
    {
        final PlVerwerkerLogging logging = new PlVerwerkerLogging(PlVerwerkerMelding.SYNCHRONISATIE_VERWERKER_SYNCHRONISATIE);

        // Parse LO3 persoonslijst uit verzoek
        final Lo3Persoonslijst lo3Persoonslijst = parsePersoonslijst(logging, verzoek);

        // Converteer LO3 persoonslijst naar BRP persoonslijst
        final BrpPersoonslijst brpPersoonslijst = converteerPersoonslijst(logging, lo3Persoonslijst);

        final VerwerkingsContext verwerkingContext = new VerwerkingsContext(verzoek, loggingBericht, lo3Persoonslijst, brpPersoonslijst);

        // Splits in flows
        final SynchroniseerNaarBrpAntwoordBericht result;
        if (verzoek.isOpnemenAlsNieuwePl()) {
            // UC808 - Beheerderkeuze: opnemen als nieuwe pl
            logging.addBeslissing(SynchronisatieBeslissing.SYNCHRONISATIE_VERWERKER_NIEUWE_PL);
            result = plVerwerkerBeheerdersKeuzeNieuw.verwerk(verwerkingContext);
        } else if (verzoek.getANummerTeVervangenPl() != null) {
            // UC808 - Beheerderkeuze: vervang bestaand pl
            logging.addBeslissing(SynchronisatieBeslissing.SYNCHRONISATIE_VERWERKER_VERVANGEN_PL);
            result = plVerwerkerBeheerdersKeuzeVervang.verwerk(verwerkingContext);
        } else {
            if (verzoek.isGezaghebbendBericht() && controleGezaghebbend.controleer(verwerkingContext)) {
                // UC811 - La01 (gezaghebbend bericht)
                logging.addBeslissing(SynchronisatieBeslissing.SYNCHRONISATIE_VERWERKER_GEZAGHEBBEND_BERICHT);

                // Opslaan
                final List<Long> administratieveHandelingIds =
                        plService.persisteerPersoonslijst(brpPersoonslijst, brpPersoonslijst.getActueelAdministratienummer(), false, loggingBericht);

                // Maak antwoord (registreer resultaat in logging)
                result = SynchronisatieHelper.maakAntwoord(verzoek, StatusType.VERVANGEN);
                loggingBericht.setVerwerkingsmelding(SynchronisatieLogging.getMelding());
                result.setAdministratieveHandelingIds(administratieveHandelingIds);
            } else {
                // UC220 - Bepalen verwerking persoonslijst
                logging.addBeslissing(SynchronisatieBeslissing.SYNCHRONISATIE_VERWERKER_SYNCHRONISATIE);
                result = plVerwerkerSynchronisatie.verwerk(verwerkingContext);
            }
        }
        return result;
    }

}
