/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.bevraging.gba.ws;

import com.google.common.collect.ImmutableMap;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.brp.delivery.bevraging.gba.ws.model.Antwoorden;
import nl.bzk.brp.delivery.bevraging.gba.ws.vertaler.AntwoordBerichtResultaat;
import nl.bzk.brp.delivery.bevraging.gba.ws.vertaler.GbaWebserviceRegelcodeVertaler;
import nl.bzk.brp.delivery.bevraging.gba.ws.vraag.Antwoord;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.gba.domain.bevraging.Adresvraag;
import nl.bzk.brp.levering.lo3.bericht.BerichtFactory;
import nl.bzk.brp.levering.lo3.builder.AdhocWebserviceAntwoordBuilder;
import nl.bzk.brp.levering.lo3.conversie.regels.RegelcodeVertaler;
import nl.bzk.brp.service.algemeen.blob.PersoonslijstService;
import nl.bzk.brp.service.bevraging.algemeen.BevragingVerzoekVerwerker;
import nl.bzk.brp.service.bevraging.gba.adres.AdresvraagWebserviceVerzoek;
import nl.bzk.brp.service.bevraging.gba.generiek.AdhocvraagVerwerker;
import nl.bzk.brp.service.bevraging.gba.generiek.BevragingVerzoekHandler;
import nl.bzk.brp.service.bevraging.gba.generiek.VerzoekMapper;
import nl.bzk.brp.service.bevraging.gba.persoon.OngeprotocolleerdPersoonsvraagVerzoek;
import nl.bzk.brp.service.gba.autorisatie.AutorisatieHandler;
import nl.bzk.brp.service.gba.autorisatie.GbaAutorisaties;
import org.springframework.stereotype.Component;

@Component
class AdresvraagVerwerker implements AdhocvraagVerwerker<Adresvraag, Antwoord> {

    private final BevragingVerzoekVerwerker<OngeprotocolleerdPersoonsvraagVerzoek> persoonsvraagVerzoekVerwerker;
    private final BevragingVerzoekVerwerker<AdresvraagWebserviceVerzoek> adresvraagVerzoekVerwerker;
    private final GbaAutorisaties gbaAutorisaties;
    private final PersoonslijstService persoonslijstService;
    private final AdhocWebserviceAntwoordBuilder formatter;
    private final RegelcodeVertaler<AntwoordBerichtResultaat> regelcodeVertaler = new GbaWebserviceRegelcodeVertaler();

    private final ImmutableMap<Adresvraag.SoortIdentificatie, VerwerkAdresvraag> soortVerwerkers =
            ImmutableMap.of(
                    Adresvraag.SoortIdentificatie.ADRES, this::verwerkAdresIdentificatie,
                    Adresvraag.SoortIdentificatie.PERSOON, this::verwerkPersoonIdentificatie);

    @Inject
    public AdresvraagVerwerker(final BevragingVerzoekVerwerker<OngeprotocolleerdPersoonsvraagVerzoek> persoonsvraagVerzoekVerwerker,
                               final BevragingVerzoekVerwerker<AdresvraagWebserviceVerzoek> adresvraagVerzoekVerwerker,
                               final GbaAutorisaties gbaAutorisaties,
                               final PersoonslijstService persoonslijstService,
                               final BerichtFactory berichtFactory) {
        this.persoonsvraagVerzoekVerwerker = persoonsvraagVerzoekVerwerker;
        this.adresvraagVerzoekVerwerker = adresvraagVerzoekVerwerker;
        this.gbaAutorisaties = gbaAutorisaties;
        this.persoonslijstService = persoonslijstService;
        this.formatter = new AdhocWebserviceAntwoordBuilder(berichtFactory);
    }

    @Override
    public Antwoord verwerk(final Adresvraag vraag, final String berichtReferentie) {
        final AutorisatieHandler<Antwoord> autorisatieHandler = new AutorisatieHandler<>(gbaAutorisaties);
        return autorisatieHandler.verwerkMetAutorisatie(
                vraag.getPartijCode(), Rol.AFNEMER, vraag.getSoortDienst(),
                autorisatiebundel -> verwerkAdresvraag(vraag, berichtReferentie, autorisatiebundel),
                () -> Antwoorden.foutief(AntwoordBerichtResultaat.TECHNISCHE_FOUT_013));
    }

    private Antwoord verwerkAdresvraag(final Adresvraag vraag, final String berichtReferentie, final Autorisatiebundel autorisatiebundel) {
        return soortVerwerkers.get(vraag.getSoortIdentificatie()).apply(autorisatiebundel, vraag, berichtReferentie);
    }

    private Antwoord verwerkAdresIdentificatie(final Autorisatiebundel autorisatiebundel,
                                               final Adresvraag vraag,
                                               final String berichtReferentie) {
        AdresvraagWebserviceVerzoek verzoek = VerzoekMapper
                .mapZoekPersoonVerzoek(AdresvraagWebserviceVerzoek.class, vraag, autorisatiebundel, berichtReferentie);

        BevragingVerzoekHandler<Antwoord, AntwoordBerichtResultaat> handler =
                BevragingVerzoekHandler.<Antwoord, AntwoordBerichtResultaat>builder(persoonslijstService, regelcodeVertaler)
                        .whenFoutcode(Antwoorden::foutief)
                        .whenGeenResultaten(() -> Antwoorden.foutief(AntwoordBerichtResultaat.NIET_GEVONDEN))
                        .whenResultaat(persoonslijsten -> Antwoorden.ok(formatter, persoonslijsten, vraag.getGevraagdeRubrieken()))
                        .build();

        adresvraagVerzoekVerwerker.verwerk(verzoek, handler);
        return handler.getAntwoord();
    }

    private Antwoord verwerkPersoonIdentificatie(final Autorisatiebundel autorisatiebundel,
                                                 final Adresvraag persoonsAdresvraag,
                                                 final String berichtReferentie) {
        OngeprotocolleerdPersoonsvraagVerzoek verzoek =
                VerzoekMapper
                        .mapZoekPersoonVerzoek(OngeprotocolleerdPersoonsvraagVerzoek.class, persoonsAdresvraag, autorisatiebundel, berichtReferentie);

        BevragingVerzoekHandler<Antwoord, AntwoordBerichtResultaat> handler =
                BevragingVerzoekHandler.<Antwoord, AntwoordBerichtResultaat>builder(persoonslijstService, regelcodeVertaler)
                        .whenFoutcode(Antwoorden::foutief)
                        .whenGeenResultaten(() -> Antwoorden.foutief(AntwoordBerichtResultaat.NIET_GEVONDEN))
                        .whenResultaat(
                                persoonslijsten -> verwerkGeefMedebewoners(persoonslijsten.get(0), persoonsAdresvraag, autorisatiebundel, berichtReferentie))
                        .build();

        persoonsvraagVerzoekVerwerker.verwerk(verzoek, handler);
        return handler.getAntwoord();
    }

    private Antwoord verwerkGeefMedebewoners(final Persoonslijst persoonslijst, final Adresvraag persoonsAdresvraag,
                                             final Autorisatiebundel autorisatiebundel,
                                             final String berichtReferentie) {
        return persoonslijst.<String>getActueleAttribuutWaarde(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_IDENTIFICATIECODENUMMERAANDUIDING))
                .map(aanduiding -> geefWebserviceMedebewonersAntwoord(aanduiding, persoonsAdresvraag, autorisatiebundel, berichtReferentie))
                .orElse(Antwoorden.foutief(AntwoordBerichtResultaat.NIET_GEVONDEN));
    }

    private Antwoord geefWebserviceMedebewonersAntwoord(final String bagSleutel, final Adresvraag persoonsAdresvraag,
                                                        final Autorisatiebundel autorisatiebundel, final String berichtReferentie) {
        AdresvraagWebserviceVerzoek verzoek = VerzoekMapper
                .mapZoekPersoonVerzoek(
                        AdresvraagWebserviceVerzoek.class,
                        persoonsAdresvraag.toMedebewonersAdresvraag(bagSleutel),
                        autorisatiebundel,
                        berichtReferentie);

        BevragingVerzoekHandler<Antwoord, AntwoordBerichtResultaat> handler =
                BevragingVerzoekHandler.<Antwoord, AntwoordBerichtResultaat>builder(persoonslijstService, regelcodeVertaler)
                        .whenFoutcode(Antwoorden::foutief)
                        .whenGeenResultaten(() -> Antwoorden.foutief(AntwoordBerichtResultaat.NIET_GEVONDEN))
                        .whenResultaat(persoonslijsten -> Antwoorden.ok(formatter, persoonslijsten, verzoek.getGevraagdeRubrieken()))
                        .build();

        adresvraagVerzoekVerwerker.verwerk(verzoek, handler);
        return handler.getAntwoord();
    }

    @FunctionalInterface
    interface VerwerkAdresvraag {
        /**
         * Verwerk een adresvraag
         * @param autorisatiebundel autorisatiebundel
         * @param vraag adresvraag
         * @param berichtReferentie bericht referentie
         * @return adres antwoord
         */
        Antwoord apply(Autorisatiebundel autorisatiebundel, Adresvraag vraag, String berichtReferentie);
    }
}
