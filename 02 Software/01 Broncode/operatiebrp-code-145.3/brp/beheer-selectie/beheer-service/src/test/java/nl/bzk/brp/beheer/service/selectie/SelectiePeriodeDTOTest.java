/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.service.selectie;

import static org.junit.Assert.*;

import java.time.LocalDate;
import org.junit.Test;

public class SelectiePeriodeDTOTest {

    @Test
    public void maak() {
        final LocalDate beginDatum = LocalDate.of(2010, 1,1);
        final LocalDate eindDatum = LocalDate.of(2015, 1,1);
        final SelectiePeriodeDTO selectiePeriodeDTO = new SelectiePeriodeDTO(beginDatum, eindDatum);

        assertEquals(beginDatum, selectiePeriodeDTO.getBeginDatum());
        assertEquals(eindDatum, selectiePeriodeDTO.getEindDatum());
    }

    @Test
    public void isValid() {
        final LocalDate beginDatum = LocalDate.of(2010, 1,1);
        final LocalDate eindDatum = LocalDate.of(2015, 1,1);
        final SelectiePeriodeDTO selectiePeriodeDTO = new SelectiePeriodeDTO(beginDatum, eindDatum);

        assertTrue(selectiePeriodeDTO.isValid());
    }


    @Test
    public void isNietValid_begindatumNull() {
        final SelectiePeriodeDTO selectiePeriodeDTO = new SelectiePeriodeDTO(null, LocalDate.now());

        assertFalse(selectiePeriodeDTO.isValid());
    }


    @Test
    public void isNietValid_einddatumNull() {
        final SelectiePeriodeDTO selectiePeriodeDTO = new SelectiePeriodeDTO(LocalDate.now(), null);

        assertFalse(selectiePeriodeDTO.isValid());
    }



}