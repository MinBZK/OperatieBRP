/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.bevraging.gba.ws;

import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.brp.delivery.bevraging.gba.ws.model.Antwoorden;
import nl.bzk.brp.delivery.bevraging.gba.ws.vertaler.AntwoordBerichtResultaat;
import nl.bzk.brp.delivery.bevraging.gba.ws.vertaler.GbaWebserviceRegelcodeVertaler;
import nl.bzk.brp.delivery.bevraging.gba.ws.vraag.Antwoord;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.gba.domain.bevraging.Persoonsvraag;
import nl.bzk.brp.levering.lo3.bericht.BerichtFactory;
import nl.bzk.brp.levering.lo3.builder.OpvragenPLWebserviceAntwoordBuilder;
import nl.bzk.brp.levering.lo3.conversie.regels.RegelcodeVertaler;
import nl.bzk.brp.service.algemeen.blob.PersoonslijstService;
import nl.bzk.brp.service.bevraging.algemeen.BevragingVerzoekVerwerker;
import nl.bzk.brp.service.bevraging.gba.generiek.AdhocvraagVerwerker;
import nl.bzk.brp.service.bevraging.gba.generiek.BevragingVerzoekHandler;
import nl.bzk.brp.service.bevraging.gba.generiek.VerzoekMapper;
import nl.bzk.brp.service.bevraging.gba.persoon.OpvragenPLWebserviceVerzoek;
import nl.bzk.brp.service.gba.autorisatie.AutorisatieHandler;
import nl.bzk.brp.service.gba.autorisatie.GbaAutorisaties;
import org.springframework.stereotype.Component;

@Component
class OpvragenPLVerwerker implements AdhocvraagVerwerker<Persoonsvraag, Antwoord> {

    private final BevragingVerzoekVerwerker<OpvragenPLWebserviceVerzoek> verzoekVerwerker;
    private final GbaAutorisaties gbaAutorisaties;
    private final PersoonslijstService persoonslijstService;
    private final OpvragenPLWebserviceAntwoordBuilder formatter;
    private final RegelcodeVertaler<AntwoordBerichtResultaat> regelcodeVertaler = new GbaWebserviceRegelcodeVertaler();

    @Inject
    public OpvragenPLVerwerker(final BevragingVerzoekVerwerker<OpvragenPLWebserviceVerzoek> verzoekVerwerker,
                               final GbaAutorisaties gbaAutorisaties,
                               final PersoonslijstService persoonslijstService,
                               final BerichtFactory berichtFactory) {
        this.verzoekVerwerker = verzoekVerwerker;
        this.gbaAutorisaties = gbaAutorisaties;
        this.persoonslijstService = persoonslijstService;
        this.formatter = new OpvragenPLWebserviceAntwoordBuilder(berichtFactory);
    }

    @Override
    public Antwoord verwerk(final Persoonsvraag vraag, final String berichtReferentie) {
        return verwerk(vraag, Rol.AFNEMER, berichtReferentie);
    }

    /**
     * Verwerk de ad hoc vraag.
     * @param vraag vraag
     * @param rol de rol waaraan de autorisatie gekoppeld dient te zijn
     * @param berichtReferentie bericht referentie
     * @return ad hoc antwoord
     */
    Antwoord verwerk(final Persoonsvraag vraag, final Rol rol, final String berichtReferentie) {
        final AutorisatieHandler<Antwoord> autorisatieHandler = new AutorisatieHandler<>(gbaAutorisaties);
        return autorisatieHandler.verwerkMetAutorisatie(
                vraag.getPartijCode(), rol, vraag.getSoortDienst(),
                autorisatiebundel -> verwerkPersoonsvraag(vraag, berichtReferentie, autorisatiebundel),
                () -> Antwoorden.foutief(AntwoordBerichtResultaat.TECHNISCHE_FOUT_013));
    }

    private Antwoord verwerkPersoonsvraag(final Persoonsvraag vraag, final String berichtReferentie, final Autorisatiebundel autorisatiebundel) {
        final OpvragenPLWebserviceVerzoek verzoek = VerzoekMapper
                .mapZoekPersoonVerzoek(OpvragenPLWebserviceVerzoek.class, vraag, autorisatiebundel, berichtReferentie);

        final BevragingVerzoekHandler<Antwoord, AntwoordBerichtResultaat> handler =
                BevragingVerzoekHandler.<Antwoord, AntwoordBerichtResultaat>builder(persoonslijstService, regelcodeVertaler)
                        .whenFoutcode(Antwoorden::foutief)
                        .whenGeenResultaten(() -> Antwoorden.foutief(AntwoordBerichtResultaat.NIET_GEVONDEN))
                        .whenResultaat(persoonslijsten -> Antwoorden.ok(formatter, persoonslijsten, vraag.getGevraagdeRubrieken())).build();

        verzoekVerwerker.verwerk(verzoek, handler);
        return handler.getAntwoord();
    }
}
