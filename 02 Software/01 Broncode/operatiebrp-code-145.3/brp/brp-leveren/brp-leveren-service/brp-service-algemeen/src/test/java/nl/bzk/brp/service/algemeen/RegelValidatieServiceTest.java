/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.brp.domain.algemeen.Melding;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * RegelValidatieServiceTest.
 */
public class RegelValidatieServiceTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testMetMelding() throws StapMeldingException {
        final RegelValidatieService regelValidatieService = new RegelValidatieServiceImpl();
        final List<RegelValidatie> regelValidaties = new ArrayList<>();
        regelValidaties.add(new RegelValidatie() {

            @Override
            public Regel getRegel() {
                return null;
            }

            @Override
            public Melding valideer() {
                return new Melding(Regel.R1261);
            }
        });

        expectedException.expect(StapMeldingException.class);
        expectedException.expect(new StapMeldingExceptionAantalMeldingMatches(1));

        regelValidatieService.valideer(regelValidaties);
    }

    @Test
    public void testValideerEnGeefMeldingen() throws StapMeldingException {
        final RegelValidatieService regelValidatieService = new RegelValidatieServiceImpl();
        final List<RegelValidatie> regelValidaties = new ArrayList<>();
        regelValidaties.add(new RegelValidatie() {

            @Override
            public Regel getRegel() {
                return null;
            }

            @Override
            public Melding valideer() {
                return new Melding(Regel.R1261);
            }
        });

        final List<Melding> meldingen = regelValidatieService.valideerEnGeefMeldingen(regelValidaties);

        Assert.assertEquals(1, meldingen.size());
        Assert.assertEquals(Regel.R1261, meldingen.get(0).getRegel());
    }

    @Test
    public void testFailFast() throws StapMeldingException {
        final RegelValidatieService regelValidatieService = new RegelValidatieServiceImpl();
        final List<RegelValidatie> regelValidaties = new ArrayList<>();
        regelValidaties.add(new RegelValidatie() {

            @Override
            public Regel getRegel() {
                return null;
            }

            @Override
            public Melding valideer() {
                return new Melding(Regel.R1261);
            }
        });

        expectedException.expect(StapMeldingException.class);
        expectedException.expect(new StapMeldingExceptionAantalMeldingMatches(1));

        regelValidatieService.valideerFailfast(regelValidaties);
    }


    @Test
    public void testZonderMelding() throws StapMeldingException {
        final RegelValidatieService regelValidatieService = new RegelValidatieServiceImpl();
        final List<RegelValidatie> regelValidaties = new ArrayList<>();
        regelValidaties.add(new RegelValidatie() {

            @Override
            public Regel getRegel() {
                return null;
            }

            @Override
            public Melding valideer() {
                return null;
            }
        });
        regelValidatieService.valideer(regelValidaties);
    }

    @Test
    public void testFailFastZonderMelding() throws StapMeldingException {
        final RegelValidatieService regelValidatieService = new RegelValidatieServiceImpl();
        final List<RegelValidatie> regelValidaties = new ArrayList<>();
        regelValidaties.add(new RegelValidatie() {

            @Override
            public Regel getRegel() {
                return null;
            }

            @Override
            public Melding valideer() {
                return null;
            }
        });
        regelValidatieService.valideer(regelValidaties);
    }


    private final class StapMeldingExceptionAantalMeldingMatches extends TypeSafeMatcher<StapMeldingException> {
        private final int aantalMeldingen;

        public StapMeldingExceptionAantalMeldingMatches(final int aantalMeldingen) {
            this.aantalMeldingen = aantalMeldingen;
        }

        @Override
        protected boolean matchesSafely(final StapMeldingException e) {
            return e.getMeldingen().size() == aantalMeldingen;
        }

        @Override
        public void describeTo(final Description description) {
            description.appendText("verwacht aantal meldingen  = ").appendValue(aantalMeldingen);
        }

        @Override
        protected void describeMismatchSafely(StapMeldingException e, Description mismatchDescription) {
            mismatchDescription.appendText("was ")
                    .appendValue(e.getMeldingen().size());
        }
    }
}
