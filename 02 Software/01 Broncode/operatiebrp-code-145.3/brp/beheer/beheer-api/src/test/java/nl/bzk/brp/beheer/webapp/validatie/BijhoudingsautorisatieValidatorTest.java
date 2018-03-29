/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.validatie;

import java.util.HashMap;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Bijhoudingsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAdres;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.validation.Errors;
import org.springframework.validation.MapBindingResult;

public class BijhoudingsautorisatieValidatorTest {

    private final BijhoudingsautorisatieValidator subject = new BijhoudingsautorisatieValidator();

    @Test
    public void supports() {
        Assert.assertTrue(subject.supports(Bijhoudingsautorisatie.class));
        Assert.assertFalse(subject.supports(PersoonAdres.class));
    }

    @Test
    public void geenDatumIngang() {
        final Bijhoudingsautorisatie bijhoudingsautorisatie = new Bijhoudingsautorisatie(false);
        Assert.assertEquals(1, errorCount(bijhoudingsautorisatie));
    }

    @Test
    public void datumIngangVoorVandaag() {
        final Bijhoudingsautorisatie bijhoudingsautorisatie = new Bijhoudingsautorisatie(false);
        bijhoudingsautorisatie.setDatumIngang(20160606);
        Assert.assertEquals(0, errorCount(bijhoudingsautorisatie));
    }

    @Test
    public void datumIngangNaDatumEinde() {
        final Bijhoudingsautorisatie bijhoudingsautorisatie = new Bijhoudingsautorisatie(false);
        bijhoudingsautorisatie.setDatumIngang(20160606);
        bijhoudingsautorisatie.setDatumEinde(20160605);

        Assert.assertEquals(1, errorCount(bijhoudingsautorisatie));
    }

    @Test
    public void datumIngangVoorDatumEinde() {
        final Bijhoudingsautorisatie bijhoudingsautorisatie = new Bijhoudingsautorisatie(false);
        bijhoudingsautorisatie.setDatumIngang(20160605);
        bijhoudingsautorisatie.setDatumEinde(20160606);

        Assert.assertEquals(0, errorCount(bijhoudingsautorisatie));
    }

    @Test
    public void datumIngangDeelsOnbekend() {
        final Bijhoudingsautorisatie bijhoudingsautorisatie = new Bijhoudingsautorisatie(false);
        bijhoudingsautorisatie.setDatumIngang(20160005);

        Assert.assertEquals(1, errorCount(bijhoudingsautorisatie));
    }

    @Test
    public void datumEindeDeelsOnbekend() {
        final Bijhoudingsautorisatie bijhoudingsautorisatie =
                new Bijhoudingsautorisatie(false);
        bijhoudingsautorisatie.setDatumIngang(20160005);

        Assert.assertEquals(1, errorCount(bijhoudingsautorisatie));
    }

    private int errorCount(final Bijhoudingsautorisatie bijhoudingsautorisatie) {
        final Map<?, ?> result = new HashMap<>();
        final Errors errors = new MapBindingResult(result, "bijhoudingsautorisatie");
        subject.validate(bijhoudingsautorisatie, errors);
        return errors.getErrorCount();
    }
}
