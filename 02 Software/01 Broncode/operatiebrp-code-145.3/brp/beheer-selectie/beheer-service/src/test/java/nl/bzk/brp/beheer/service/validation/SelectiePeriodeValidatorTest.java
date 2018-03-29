/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.service.validation;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import nl.bzk.brp.beheer.service.selectie.SelectiePeriodeDTO;
import org.junit.Test;

/**
 * Unit test voor {@link SelectiePeriodeValidator}.
 */
public class SelectiePeriodeValidatorTest {

    @Test
    public void valideAlsPeriodeKleinerDanMaximum() {
        SelectiePeriodeDTO dto = new SelectiePeriodeDTO(LocalDate.now(), LocalDate.now().plus(6, ChronoUnit.MONTHS));
        SelectiePeriodeValidator validator = new SelectiePeriodeValidator();

        boolean valid = validator.isValid(dto, null);

        assertThat(valid, is(true));
    }

    @Test
    public void valideAlsPeriodeGroterDanMaximum() {
        SelectiePeriodeDTO dto = new SelectiePeriodeDTO(LocalDate.now(), LocalDate.now().plus(2, ChronoUnit.YEARS));
        SelectiePeriodeValidator validator = new SelectiePeriodeValidator();

        boolean valid = validator.isValid(dto, null);

        assertThat(valid, is(false));
    }
}
