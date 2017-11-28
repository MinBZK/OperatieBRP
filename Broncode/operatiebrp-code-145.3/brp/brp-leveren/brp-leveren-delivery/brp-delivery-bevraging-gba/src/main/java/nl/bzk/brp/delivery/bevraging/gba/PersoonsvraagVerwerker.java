/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.bevraging.gba;

import java.util.List;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.gba.domain.bevraging.Persoonsantwoord;
import nl.bzk.brp.gba.domain.bevraging.Persoonsvraag;
import nl.bzk.brp.levering.lo3.bericht.BerichtFactory;
import nl.bzk.brp.levering.lo3.builder.Ha01Builder;
import nl.bzk.brp.levering.lo3.conversie.regels.GbaRegelcodeVertaler;
import nl.bzk.brp.levering.lo3.conversie.regels.RegelcodeVertaler;
import nl.bzk.brp.service.algemeen.blob.PersoonslijstService;
import nl.bzk.brp.service.bevraging.algemeen.BevragingVerzoekVerwerker;
import nl.bzk.brp.service.bevraging.gba.generiek.AdhocvraagVerwerker;
import nl.bzk.brp.service.bevraging.gba.generiek.BevragingVerzoekHandler;
import nl.bzk.brp.service.bevraging.gba.generiek.VerzoekMapper;
import nl.bzk.brp.service.bevraging.gba.persoon.PersoonsvraagVerzoek;
import nl.bzk.brp.service.gba.autorisatie.AutorisatieHandler;
import nl.bzk.brp.service.gba.autorisatie.GbaAutorisaties;
import org.springframework.stereotype.Component;

@Component
class PersoonsvraagVerwerker implements AdhocvraagVerwerker<Persoonsvraag, Persoonsantwoord> {

    private final BevragingVerzoekVerwerker<PersoonsvraagVerzoek> verzoekVerwerker;
    private final GbaAutorisaties gbaAutorisaties;
    private final PersoonslijstService persoonslijstService;
    private final Ha01Builder formatter;
    private final RegelcodeVertaler<Character> regelcodeVertaler = new GbaRegelcodeVertaler();

    /**
     * Constructor.
     * @param verzoekVerwerker service die verzoek verwerkt
     * @param gbaAutorisaties gba autorisaties
     * @param persoonslijstService service om alles van een persoonslijst op te vragen
     * @param berichtFactory bericht factory voor aanmaken van een ha01 bericht
     */
    @Inject
    PersoonsvraagVerwerker(final BevragingVerzoekVerwerker<PersoonsvraagVerzoek> verzoekVerwerker,
                           final GbaAutorisaties gbaAutorisaties,
                           final PersoonslijstService persoonslijstService,
                           final BerichtFactory berichtFactory) {
        this.verzoekVerwerker = verzoekVerwerker;
        this.gbaAutorisaties = gbaAutorisaties;
        this.persoonslijstService = persoonslijstService;
        this.formatter = new Ha01Builder(berichtFactory);
    }

    @Override
    public Persoonsantwoord verwerk(final Persoonsvraag vraag, final String berichtReferentie) {
        final AutorisatieHandler<Persoonsantwoord> autorisatieHandler = new AutorisatieHandler<>(gbaAutorisaties);
        return autorisatieHandler.verwerkMetAutorisatie(
                vraag.getPartijCode(), Rol.AFNEMER, vraag.getSoortDienst(),
                autorisatiebundel -> verwerkPersoonsvraag(vraag, berichtReferentie, autorisatiebundel),
                () -> maakAntwoord('X'));
    }

    private Persoonsantwoord verwerkPersoonsvraag(final Persoonsvraag vraag, final String berichtReferentie, final Autorisatiebundel autorisatiebundel) {
        final PersoonsvraagVerzoek verzoek =
                VerzoekMapper
                        .mapZoekPersoonVerzoek(PersoonsvraagVerzoek.class, vraag, autorisatiebundel, berichtReferentie);

        final BevragingVerzoekHandler<Persoonsantwoord, Character>
                handler =
                BevragingVerzoekHandler.<Persoonsantwoord, Character>builder(persoonslijstService, regelcodeVertaler)
                        .whenFoutcode(this::maakAntwoord)
                        .whenGeenResultaten(() -> maakAntwoord('G'))
                        .whenResultaat(persoonslijsten -> maakAntwoord(persoonslijsten, verzoek.getGevraagdeRubrieken()))
                        .build();

        verzoekVerwerker.verwerk(verzoek, handler);
        return handler.getAntwoord();
    }

    private Persoonsantwoord maakAntwoord(final List<Persoonslijst> persoonslijsten, final List<String> rubrieken) {
        final Persoonsantwoord antwoord = new Persoonsantwoord();
        antwoord.setInhoud(formatter.build(persoonslijsten, rubrieken));
        return antwoord;
    }

    private Persoonsantwoord maakAntwoord(final Character foutreden) {
        final Persoonsantwoord antwoord = new Persoonsantwoord();
        antwoord.setFoutreden(foutreden.toString());
        return antwoord;
    }
}
