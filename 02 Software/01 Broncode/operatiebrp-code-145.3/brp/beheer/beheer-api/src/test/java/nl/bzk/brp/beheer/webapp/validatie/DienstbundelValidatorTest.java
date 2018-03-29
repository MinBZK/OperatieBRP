/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.validatie;

import java.util.HashMap;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAdres;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.validation.Errors;
import org.springframework.validation.MapBindingResult;

public class DienstbundelValidatorTest {

    private final DienstbundelValidator subject = new DienstbundelValidator();

    @Test
    public void supports() {
        Assert.assertTrue(subject.supports(Dienstbundel.class));
        Assert.assertFalse(subject.supports(PersoonAdres.class));
    }

    @Test
    public void geenDatumIngang() {
        final Dienstbundel bundel = new Dienstbundel(new Leveringsautorisatie(Stelsel.BRP, false));
        Assert.assertEquals(1, errorCount(bundel));
    }

    @Test
    public void datumIngangVoorVandaag() {
        final Dienstbundel bundel = new Dienstbundel(new Leveringsautorisatie(Stelsel.BRP, false));
        bundel.setDatumIngang(20160606);
        Assert.assertEquals(0, errorCount(bundel));
    }

    @Test
    public void datumIngangNaDatumEinde() {
        final Dienstbundel bundel = new Dienstbundel(new Leveringsautorisatie(Stelsel.BRP, false));
        bundel.setDatumIngang(20160606);
        bundel.setDatumEinde(20160605);

        Assert.assertEquals(1, errorCount(bundel));
    }

    @Test
    public void datumIngangVoorDatumEinde() {
        final Dienstbundel bundel = new Dienstbundel(new Leveringsautorisatie(Stelsel.BRP, false));
        bundel.setDatumIngang(20160605);
        bundel.setDatumEinde(20160606);

        Assert.assertEquals(0, errorCount(bundel));
    }

    @Test
    public void datumIngangDeelsOnbekend() {
        final Dienstbundel bundel = new Dienstbundel(new Leveringsautorisatie(Stelsel.BRP, false));
        bundel.setDatumIngang(20160005);

        Assert.assertEquals(1, errorCount(bundel));
    }

    @Test
    public void datumEindeDeelsOnbekend() {
        final Dienstbundel bundel = new Dienstbundel(new Leveringsautorisatie(Stelsel.BRP, false));
        bundel.setDatumIngang(20160005);

        Assert.assertEquals(1, errorCount(bundel));
    }

    private int errorCount(final Dienstbundel bundel) {
        final Map<?, ?> result = new HashMap<>();
        final Errors errors = new MapBindingResult(result, "bundel");
        subject.validate(bundel, errors);
        return errors.getErrorCount();
    }
}
