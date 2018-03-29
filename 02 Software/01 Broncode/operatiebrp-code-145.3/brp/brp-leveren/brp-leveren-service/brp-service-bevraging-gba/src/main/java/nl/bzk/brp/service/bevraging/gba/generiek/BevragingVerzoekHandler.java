/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.bevraging.gba.generiek;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import nl.bzk.algemeenbrp.util.common.serialisatie.JsonStringSerializer;
import nl.bzk.brp.domain.algemeen.Melding;
import nl.bzk.brp.domain.berichtmodel.BijgehoudenPersoon;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.levering.lo3.conversie.regels.RegelcodeVertaler;
import nl.bzk.brp.service.algemeen.blob.PersoonslijstService;
import nl.bzk.brp.service.bevraging.algemeen.BevragingCallback;
import nl.bzk.brp.service.bevraging.algemeen.BevragingResultaat;
import nl.bzk.brp.service.bevraging.algemeen.BevragingVerzoek;

/**
 * Handler voor het afhandelen van bevraging verzoeken.
 * @param <T> type van het antwoord dat wordt gerouterneerd
 * @param <U> type van de vertaling van een eventuele melding
 */
public final class BevragingVerzoekHandler<T, U> implements BevragingCallback<String> {

    private final RegelcodeVertaler<U> regelcodeVertaler;
    private final PersoonslijstService persoonslijstService;
    private final Supplier<T> leegResultaat;
    private final Function<List<Persoonslijst>, T> success;
    private final Function<U, T> failure;
    private T antwoord;

    private BevragingVerzoekHandler(final PersoonslijstService persoonslijstService,
                                    final Supplier<T> leegResultaat,
                                    final Function<List<Persoonslijst>, T> success,
                                    final Function<U, T> failure,
                                    final RegelcodeVertaler<U> regelcodeVertaler) {
        this.persoonslijstService = persoonslijstService;
        this.leegResultaat = leegResultaat;
        this.success = success;
        this.failure = failure;
        this.regelcodeVertaler = regelcodeVertaler;
    }

    /**
     * Geeft een builder terug voor het maken van een bevraging verzoek handler
     * @param persoonslijstService persoonslijst service
     * @param regelcodeVertaler de regelcode vertaler voor het vertal van regels naar foutcodes
     * @param <T> type van het antwoord dat de BevragingVerzoekHandler retourneert
     * @param <U> type van de vertaling van een eventuele melding
     * @return een builder terug voor het maken van een BevragingVerzoekHandler
     */
    public static <T, U> Builder<T, U> builder(final PersoonslijstService persoonslijstService, final RegelcodeVertaler<U> regelcodeVertaler) {
        return new Builder<>(persoonslijstService, regelcodeVertaler);
    }

    @Override
    public void verwerkResultaat(final BevragingVerzoek bevragingVerzoek, final BevragingResultaat resultaat) {
        if (resultaat.getMeldingen().isEmpty() && resultaat.getBericht().getBasisBerichtGegevens().getMeldingen().isEmpty()) {
            List<Persoonslijst> persoonslijsten = extract(resultaat);

            antwoord = persoonslijsten.isEmpty() ? leegResultaat.get() : success.apply(persoonslijsten);
        } else {
            List<Melding> meldingen =
                    resultaat.getMeldingen().isEmpty() ? resultaat.getBericht().getBasisBerichtGegevens().getMeldingen() : resultaat.getMeldingen();
            antwoord = regelcodeVertaler.bepaalFoutcode(meldingen)
                    .map(failure)
                    .orElseThrow(() -> new IllegalStateException("Regelcode kan niet vertaald worden."));
        }
    }

    @Override
    public String getResultaat() {
        return new JsonStringSerializer().serialiseerNaarString(antwoord);
    }

    public T getAntwoord() {
        return antwoord;
    }

    private List<Persoonslijst> extract(final BevragingResultaat resultaat) {
        return resultaat.getBericht().getBijgehoudenPersonen().stream()
                .map(BijgehoudenPersoon::getPersoonslijst)
                .map(Persoonslijst::getId)
                .map(persoonslijstService::getById)
                .collect(Collectors.toList());
    }

    /**
     * Builder class voor een BevragingVerzoekHandler.
     * @param <T> type van het antwoord dat de handler terug geeft
     * @param <U> type van de vertaling van een eventuele melding
     */
    public static final class Builder<T, U> {

        private final PersoonslijstService persoonslijstService;
        private final RegelcodeVertaler<U> regelcodeVertaler;
        private Supplier<T> leegResultaat;
        private Function<List<Persoonslijst>, T> success;
        private Function<U, T> failure;

        private Builder(final PersoonslijstService persoonslijstService, final RegelcodeVertaler<U> regelcodeVertaler) {
            this.persoonslijstService = persoonslijstService;
            this.regelcodeVertaler = regelcodeVertaler;
        }

        /**
         * Specificeert het gedrag dat optreedt bij een foutcode.
         * @param failure function die gegeven een foutcode een bericht van type T terug geeft
         * @return this builder
         */
        public Builder<T, U> whenFoutcode(final Function<U, T> failure) {
            this.failure = failure;
            return this;
        }

        /**
         * Specificeert het gedrag dat optreedt bij een succesvol resultaat.
         * @param success function die gegeven een lijst van persoonslijsten een bericht van type T terug geeft
         * @return this builder
         */
        public Builder<T, U> whenResultaat(final Function<List<Persoonslijst>, T> success) {
            this.success = success;
            return this;
        }

        /**
         * Specificeert het gedrag dat optreedt bij geen resultaat.
         * @param leegResultaat supplier die een bericht van type T terug geeft
         * @return this builder
         */
        public Builder<T, U> whenGeenResultaten(final Supplier<T> leegResultaat) {
            this.leegResultaat = leegResultaat;
            return this;
        }

        /**
         * Terminal operation voor deze builder.
         * @return geeft de BevragingVerzoekHandler terug
         */
        public BevragingVerzoekHandler<T, U> build() {
            return new BevragingVerzoekHandler<>(
                    Objects.requireNonNull(this.persoonslijstService),
                    leegResultaat == null ? () -> null : leegResultaat,
                    Objects.requireNonNull(success),
                    Objects.requireNonNull(failure),
                    regelcodeVertaler);
        }
    }
}
