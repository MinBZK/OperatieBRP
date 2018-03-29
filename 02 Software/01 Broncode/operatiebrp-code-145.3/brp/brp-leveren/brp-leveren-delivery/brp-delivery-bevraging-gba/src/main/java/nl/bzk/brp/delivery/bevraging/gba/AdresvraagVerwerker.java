/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.bevraging.gba;

import com.google.common.collect.ImmutableMap;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.gba.domain.bevraging.Adresantwoord;
import nl.bzk.brp.gba.domain.bevraging.Adresvraag;
import nl.bzk.brp.gba.domain.bevraging.ZoekCriterium;
import nl.bzk.brp.levering.lo3.bericht.BerichtFactory;
import nl.bzk.brp.levering.lo3.builder.Xa01Builder;
import nl.bzk.brp.levering.lo3.conversie.regels.GbaRegelcodeVertaler;
import nl.bzk.brp.levering.lo3.conversie.regels.RegelcodeVertaler;
import nl.bzk.brp.service.algemeen.blob.PersoonslijstService;
import nl.bzk.brp.service.bevraging.algemeen.BevragingVerzoekVerwerker;
import nl.bzk.brp.service.bevraging.gba.adres.AdresvraagVerzoek;
import nl.bzk.brp.service.bevraging.gba.generiek.AdhocvraagVerwerker;
import nl.bzk.brp.service.bevraging.gba.generiek.BevragingVerzoekHandler;
import nl.bzk.brp.service.bevraging.gba.generiek.VerzoekMapper;
import nl.bzk.brp.service.bevraging.gba.persoon.OngeprotocolleerdPersoonsvraagVerzoek;
import nl.bzk.brp.service.gba.autorisatie.AutorisatieHandler;
import nl.bzk.brp.service.gba.autorisatie.GbaAutorisaties;
import org.springframework.stereotype.Component;

@Component
class AdresvraagVerwerker implements AdhocvraagVerwerker<Adresvraag, Adresantwoord> {

    private final BevragingVerzoekVerwerker<OngeprotocolleerdPersoonsvraagVerzoek> persoonsvraagVerzoekVerwerker;
    private final BevragingVerzoekVerwerker<AdresvraagVerzoek> adresvraagVerzoekVerwerker;
    private final GbaAutorisaties gbaAutorisaties;
    private final PersoonslijstService persoonslijstService;
    private final Xa01Builder formatter;
    private final RegelcodeVertaler<Character> regelcodeVertaler = new GbaRegelcodeVertaler();

    private final ImmutableMap<Adresvraag.SoortIdentificatie, VerwerkAdresvraag> soortVerwerkers =
            ImmutableMap.of(
                    Adresvraag.SoortIdentificatie.ADRES, this::verwerkAdresIdentificatie,
                    Adresvraag.SoortIdentificatie.PERSOON, this::verwerkPersoonIdentificatie);

    /**
     * Constructor.
     * @param persoonsvraagVerzoekVerwerker verwerker voor een persoonsvraag verzoek
     * @param adresvraagVerzoekVerwerker verwerker voor een adresvraag verzoek
     * @param gbaAutorisaties gba autorisaties
     * @param persoonslijstService service om alles van een persoonslijst op te vragen
     * @param berichtFactory bericht factory voor aanmaken van een ha01 bericht
     */
    @Inject
    AdresvraagVerwerker(final BevragingVerzoekVerwerker<OngeprotocolleerdPersoonsvraagVerzoek> persoonsvraagVerzoekVerwerker,
                        final BevragingVerzoekVerwerker<AdresvraagVerzoek> adresvraagVerzoekVerwerker,
                        final GbaAutorisaties gbaAutorisaties,
                        final PersoonslijstService persoonslijstService,
                        final BerichtFactory berichtFactory) {
        this.persoonsvraagVerzoekVerwerker = persoonsvraagVerzoekVerwerker;
        this.adresvraagVerzoekVerwerker = adresvraagVerzoekVerwerker;
        this.gbaAutorisaties = gbaAutorisaties;
        this.persoonslijstService = persoonslijstService;
        this.formatter = new Xa01Builder(berichtFactory);
    }

    @Override
    public Adresantwoord verwerk(final Adresvraag vraag, final String berichtReferentie) {
        final AutorisatieHandler<Adresantwoord> autorisatieHandler = new AutorisatieHandler<>(gbaAutorisaties);
        return autorisatieHandler.verwerkMetAutorisatie(
                vraag.getPartijCode(), Rol.AFNEMER, vraag.getSoortDienst(),
                autorisatiebundel -> verwerkAdresvraag(vraag, berichtReferentie, autorisatiebundel),
                () -> maakAntwoord('X'));
    }

    private Adresantwoord verwerkAdresvraag(final Adresvraag vraag, final String berichtReferentie, final Autorisatiebundel autorisatiebundel) {
        return soortVerwerkers.get(vraag.getSoortIdentificatie()).apply(autorisatiebundel, vraag, berichtReferentie);
    }

    private Adresantwoord verwerkAdresIdentificatie(final Autorisatiebundel autorisatiebundel,
                                                    final Adresvraag vraag,
                                                    final String berichtReferentie) {
        AdresvraagVerzoek verzoek = VerzoekMapper
                .mapZoekPersoonVerzoek(AdresvraagVerzoek.class, vraag, autorisatiebundel, berichtReferentie);

        BevragingVerzoekHandler<Adresantwoord, Character> handler =
                BevragingVerzoekHandler.<Adresantwoord, Character>builder(persoonslijstService, regelcodeVertaler)
                        .whenFoutcode(this::maakAntwoord)
                        .whenGeenResultaten(() -> maakAntwoord('G'))
                        .whenResultaat(persoonslijsten -> maakAntwoord(persoonslijsten, verzoek.getGevraagdeRubrieken()))
                        .build();

        adresvraagVerzoekVerwerker.verwerk(verzoek, handler);
        return handler.getAntwoord();
    }

    private Adresantwoord verwerkPersoonIdentificatie(final Autorisatiebundel autorisatiebundel,
                                                      final Adresvraag persoonsAdresvraag,
                                                      final String berichtReferentie) {
        OngeprotocolleerdPersoonsvraagVerzoek verzoek =
                VerzoekMapper
                        .mapZoekPersoonVerzoek(OngeprotocolleerdPersoonsvraagVerzoek.class, persoonsAdresvraag, autorisatiebundel, berichtReferentie);

        BevragingVerzoekHandler<Adresantwoord, Character>
                handler =
                BevragingVerzoekHandler.<Adresantwoord, Character>builder(persoonslijstService, regelcodeVertaler)
                        .whenFoutcode(this::maakAntwoord)
                        .whenGeenResultaten(() -> maakAntwoord('G'))
                        .whenResultaat(
                                persoonslijsten -> verwerkGeefMedebewoners(persoonslijsten.get(0), persoonsAdresvraag, autorisatiebundel, berichtReferentie))
                        .build();

        persoonsvraagVerzoekVerwerker.verwerk(verzoek, handler);
        return handler.getAntwoord();
    }

    private Adresantwoord verwerkGeefMedebewoners(final Persoonslijst persoonslijst, final Adresvraag persoonsAdresvraag,
                                                  final Autorisatiebundel autorisatiebundel,
                                                  final String berichtReferentie) {
        return persoonslijst.<String>getActueleAttribuutWaarde(ElementHelper.getAttribuutElement(Element.PERSOON_ADRES_IDENTIFICATIECODENUMMERAANDUIDING))
                .map(aanduiding -> geefMedebewonersAntwoord(aanduiding, persoonsAdresvraag, autorisatiebundel, berichtReferentie))
                .orElse(maakAntwoord('G'));
    }

    private Adresantwoord geefMedebewonersAntwoord(final String bagSleutel, final Adresvraag persoonsAdresvraag,
                                                   final Autorisatiebundel autorisatiebundel, final String berichtReferentie) {
        AdresvraagVerzoek verzoek = VerzoekMapper
                .mapZoekPersoonVerzoek(
                        AdresvraagVerzoek.class,
                        maakMedebewonersVraag(persoonsAdresvraag, bagSleutel),
                        autorisatiebundel,
                        berichtReferentie);

        BevragingVerzoekHandler<Adresantwoord, Character>
                handler =
                BevragingVerzoekHandler.<Adresantwoord, Character>builder(persoonslijstService, regelcodeVertaler)
                        .whenFoutcode(this::maakAntwoord)
                        .whenGeenResultaten(() -> maakAntwoord('Z'))
                        .whenResultaat(persoonslijsten -> maakAntwoord(persoonslijsten, verzoek.getGevraagdeRubrieken()))
                        .build();

        adresvraagVerzoekVerwerker.verwerk(verzoek, handler);
        return handler.getAntwoord();
    }

    private Adresantwoord maakAntwoord(final List<Persoonslijst> persoonslijsten, final List<String> rubrieken) {
        Adresantwoord antwoord = new Adresantwoord();
        antwoord.setInhoud(formatter.build(persoonslijsten, rubrieken));
        return antwoord;
    }

    private Adresantwoord maakAntwoord(final Character foutreden) {
        Adresantwoord antwoord = new Adresantwoord();
        antwoord.setFoutreden(foutreden.toString());
        return antwoord;
    }

    private Adresvraag maakMedebewonersVraag(final Adresvraag persoonsAdresvraag, final String bagSleutel) {
        Adresvraag medebewonersVraag = persoonsAdresvraag.toMedebewonersAdresvraag(bagSleutel);
        List<ZoekCriterium> criteriums = persoonsAdresvraag.getZoekCriteria().stream()
                .filter(c -> Element.PERSOON_ADRES_SOORTCODE.getNaam().equals(c.getNaam()))
                .findFirst()
                .map(c -> {
                    List<ZoekCriterium> xs = new ArrayList<>(medebewonersVraag.getZoekCriteria());
                    xs.add(c);
                    return xs;
                })
                .orElse(medebewonersVraag.getZoekCriteria());
        medebewonersVraag.setZoekCriteria(criteriums);
        return medebewonersVraag;
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
        Adresantwoord apply(Autorisatiebundel autorisatiebundel, Adresvraag vraag, String berichtReferentie);
    }
}
