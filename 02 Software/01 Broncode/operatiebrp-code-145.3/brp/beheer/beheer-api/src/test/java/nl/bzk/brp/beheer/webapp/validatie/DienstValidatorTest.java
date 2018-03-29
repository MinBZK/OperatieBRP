/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.validatie;

import java.util.HashMap;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAdres;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.validation.Errors;
import org.springframework.validation.MapBindingResult;

public class DienstValidatorTest {

    private final DienstValidator subject = new DienstValidator();

    @Test
    public void supports() {
        Assert.assertTrue(subject.supports(Dienst.class));
        Assert.assertFalse(subject.supports(PersoonAdres.class));
    }

    @Test
    public void geenDatumIngang() {
        final Dienst dienst =
                new Dienst(new Dienstbundel(new Leveringsautorisatie(Stelsel.BRP, false)), SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE);

        Assert.assertEquals(1, errorCount(dienst));
    }

    @Test
    public void datumIngangVoorVandaag() {
        final Dienst dienst =
                new Dienst(new Dienstbundel(new Leveringsautorisatie(Stelsel.BRP, false)), SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE);
        dienst.setDatumIngang(20160606);
        Assert.assertEquals(0, errorCount(dienst));
    }

    @Test
    public void datumIngangNaDatumEinde() {
        final Dienst dienst =
                new Dienst(new Dienstbundel(new Leveringsautorisatie(Stelsel.BRP, false)), SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE);
        dienst.setDatumIngang(20160606);
        dienst.setDatumEinde(20160605);

        Assert.assertEquals(1, errorCount(dienst));
    }

    @Test
    public void datumIngangVoorDatumEinde() {
        final Dienst dienst =
                new Dienst(new Dienstbundel(new Leveringsautorisatie(Stelsel.BRP, false)), SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE);
        dienst.setDatumIngang(20160605);
        dienst.setDatumEinde(20160606);

        Assert.assertEquals(0, errorCount(dienst));
    }

    @Test
    public void datumIngangDeelsOnbekend() {
        final Dienst dienst =
                new Dienst(new Dienstbundel(new Leveringsautorisatie(Stelsel.BRP, false)), SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE);
        dienst.setDatumIngang(20160005);

        Assert.assertEquals(1, errorCount(dienst));
    }

    @Test
    public void datumEindeDeelsOnbekend() {
        final Dienst dienst =
                new Dienst(new Dienstbundel(new Leveringsautorisatie(Stelsel.BRP, false)), SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE);
        dienst.setDatumIngang(20160005);

        Assert.assertEquals(1, errorCount(dienst));
    }

    private int errorCount(final Dienst dienst) {
        final Map<?, ?> result = new HashMap<>();
        final Errors errors = new MapBindingResult(result, "dienst");
        subject.validate(dienst, errors);
        return errors.getErrorCount();
    }
}
