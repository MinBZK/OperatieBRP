/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.validatie;

import java.util.HashMap;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAdres;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Protocolleringsniveau;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.validation.Errors;
import org.springframework.validation.MapBindingResult;

public class LeveringsautorisatieValidatorTest {

    private final LeveringsautorisatieValidator subject = new LeveringsautorisatieValidator();

    @Test
    public void supports() {
        Assert.assertTrue(subject.supports(Leveringsautorisatie.class));
        Assert.assertFalse(subject.supports(PersoonAdres.class));
    }

    @Test
    public void geenDatumIngang() {
        final Leveringsautorisatie leveringsautorisatie = new Leveringsautorisatie(Stelsel.GBA, false);
        leveringsautorisatie.setNaam("Testautorisatie");
        leveringsautorisatie.setProtocolleringsniveau(Protocolleringsniveau.GEEN_BEPERKINGEN);
        Assert.assertEquals(1, errorCount(leveringsautorisatie));
    }

    @Test
    public void datumIngangVoorVandaag() {
        final Leveringsautorisatie leveringsautorisatie = new Leveringsautorisatie(Stelsel.GBA, false);
        leveringsautorisatie.setNaam("Testautorisatie");
        leveringsautorisatie.setProtocolleringsniveau(Protocolleringsniveau.GEEN_BEPERKINGEN);
        leveringsautorisatie.setDatumIngang(20160606);
        Assert.assertEquals(0, errorCount(leveringsautorisatie));
    }

    @Test
    public void datumIngangNaDatumEinde() {
        final Leveringsautorisatie leveringsautorisatie = new Leveringsautorisatie(Stelsel.BRP, false);
        leveringsautorisatie.setNaam("Testautorisatie");
        leveringsautorisatie.setProtocolleringsniveau(Protocolleringsniveau.GEEN_BEPERKINGEN);
        leveringsautorisatie.setDatumIngang(20160606);
        leveringsautorisatie.setDatumEinde(20160605);

        Assert.assertEquals(1, errorCount(leveringsautorisatie));
    }

    @Test
    public void datumIngangVoorDatumEinde() {
        final Leveringsautorisatie leveringsautorisatie = new Leveringsautorisatie(Stelsel.BRP, false);
        leveringsautorisatie.setNaam("Testautorisatie");
        leveringsautorisatie.setProtocolleringsniveau(Protocolleringsniveau.GEEN_BEPERKINGEN);
        leveringsautorisatie.setDatumIngang(20160605);
        leveringsautorisatie.setDatumEinde(20160606);

        Assert.assertEquals(0, errorCount(leveringsautorisatie));
    }

    @Test
    public void datumIngangDeelsOnbekend() {
        final Leveringsautorisatie leveringsautorisatie = new Leveringsautorisatie(Stelsel.BRP, false);
        leveringsautorisatie.setNaam("Testautorisatie");
        leveringsautorisatie.setProtocolleringsniveau(Protocolleringsniveau.GEEN_BEPERKINGEN);
        leveringsautorisatie.setDatumIngang(20160005);

        Assert.assertEquals(1, errorCount(leveringsautorisatie));
    }

    @Test
    public void datumEindeDeelsOnbekend() {
        final Leveringsautorisatie leveringsautorisatie =
                new Leveringsautorisatie(Stelsel.BRP, false);
        leveringsautorisatie.setNaam("Testautorisatie");
        leveringsautorisatie.setProtocolleringsniveau(Protocolleringsniveau.GEEN_BEPERKINGEN);
        leveringsautorisatie.setDatumIngang(20160005);

        Assert.assertEquals(1, errorCount(leveringsautorisatie));
    }

    @Test
    public void kaleAutorisatie() {
        final Leveringsautorisatie leveringsautorisatie =
                new Leveringsautorisatie(Stelsel.BRP, false);

        Assert.assertEquals(3, errorCount(leveringsautorisatie));
    }

    private int errorCount(final Leveringsautorisatie leveringsautorisatie) {
        final Map<?, ?> result = new HashMap<>();
        final Errors errors = new MapBindingResult(result, "leveringsautorisatie");
        subject.validate(leveringsautorisatie, errors);
        return errors.getErrorCount();
    }
}
