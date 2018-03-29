/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.bevraging.gba.ws.validators;

import static nl.bzk.brp.delivery.bevraging.gba.ws.Vragen.param;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import nl.bzk.brp.delivery.bevraging.gba.ws.Vragen;
import nl.bzk.brp.delivery.bevraging.gba.ws.model.AdhocWebserviceVraagBericht;
import nl.bzk.brp.delivery.bevraging.gba.ws.model.Antwoorden;
import nl.bzk.brp.delivery.bevraging.gba.ws.model.WebserviceBericht;
import nl.bzk.brp.delivery.bevraging.gba.ws.vertaler.AntwoordBerichtResultaat;
import nl.bzk.brp.delivery.bevraging.gba.ws.vraag.Antwoord;
import org.junit.Before;
import org.junit.Test;

public class VraagValidatorTest {

    private AdhocWebserviceVraagBericht vraagBericht = new AdhocWebserviceVraagBericht(
            Vragen.persoonsvraag(Arrays.asList(10110, 10120), param(10110, "123456789")));
    private AtomicInteger counter = new AtomicInteger();
    private VraagValidator<AdhocWebserviceVraagBericht> subject;

    @Before
    public void setup() {
        subject = new VraagValidator<>(vraagBericht);
        counter = new AtomicInteger();
    }

    @Test
    public void noValidationErrors() {
        subject.setValidators(Arrays.asList(this::noValidationError, this::noValidationError));
        assertEquals("A", subject.ifValid(this::ok).getResultaat().getLetter());
        assertEquals(2, counter.get());
    }

    @Test
    public void validateShouldStopAfterFirstValidationError() {
        subject.setValidators(Arrays.asList(this::validationError, this::assertNotCalled));
        assertEquals("X", subject.ifValid(this::ok).getResultaat().getLetter());
        assertEquals(1, counter.get());
    }

    @Test
    public void noValidators() {
        assertEquals("A", subject.ifValid(this::ok).getResultaat().getLetter());
        assertEquals(0, counter.get());
    }

    private Antwoord ok(final WebserviceBericht bericht) {
        return Antwoorden.foutief(AntwoordBerichtResultaat.OK);
    }

    private Optional<Antwoord> validationError(final AdhocWebserviceVraagBericht bericht) {
        counter.incrementAndGet();
        return Optional.of(Antwoorden.foutief(AntwoordBerichtResultaat.TECHNISCHE_FOUT_013));
    }

    private Optional<Antwoord> noValidationError(final AdhocWebserviceVraagBericht bericht) {
        counter.incrementAndGet();
        return Optional.empty();
    }

    private Optional<Antwoord> assertNotCalled(final AdhocWebserviceVraagBericht bericht) {
        counter.incrementAndGet();
        return Optional.of(Antwoorden.foutief(AntwoordBerichtResultaat.TECHNISCHE_FOUT_032));
    }
}
