/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.validatie;

import java.util.HashMap;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAdres;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.validation.Errors;
import org.springframework.validation.MapBindingResult;

public class ToegangLeveringsAutorisatieValidatorTest {

    private final ToegangLeveringsAutorisatieValidator subject = new ToegangLeveringsAutorisatieValidator();

    @Test
    public void supports() {
        Assert.assertTrue(subject.supports(ToegangLeveringsAutorisatie.class));
        Assert.assertFalse(subject.supports(PersoonAdres.class));
    }

    @Test
    public void geenDatumIngang() {
        final ToegangLeveringsAutorisatie toegang =
                new ToegangLeveringsAutorisatie(new PartijRol(new Partij("partij1", "000001"), Rol.AFNEMER), new Leveringsautorisatie(Stelsel.BRP, false));
        Assert.assertEquals(1, errorCount(toegang));
    }

    @Test
    public void datumIngangVoorVandaag() {
        final ToegangLeveringsAutorisatie toegang =
                new ToegangLeveringsAutorisatie(new PartijRol(new Partij("partij1", "000001"), Rol.AFNEMER), new Leveringsautorisatie(Stelsel.BRP, false));
        toegang.setDatumIngang(20160606);
        Assert.assertEquals(0, errorCount(toegang));
    }

    @Test
    public void datumIngangNaDatumEinde() {
        final ToegangLeveringsAutorisatie toegang =
                new ToegangLeveringsAutorisatie(new PartijRol(new Partij("partij1", "000001"), Rol.AFNEMER), new Leveringsautorisatie(Stelsel.BRP, false));
        toegang.setDatumIngang(20160606);
        toegang.setDatumEinde(20160605);

        Assert.assertEquals(1, errorCount(toegang));
    }

    @Test
    public void datumIngangVoorDatumEinde() {
        final ToegangLeveringsAutorisatie toegang =
                new ToegangLeveringsAutorisatie(new PartijRol(new Partij("partij1", "000001"), Rol.AFNEMER), new Leveringsautorisatie(Stelsel.BRP, false));
        toegang.setDatumIngang(20160605);
        toegang.setDatumEinde(20160606);

        Assert.assertEquals(0, errorCount(toegang));
    }

    @Test
    public void datumIngangDeelsOnbekend() {
        final ToegangLeveringsAutorisatie toegang =
                new ToegangLeveringsAutorisatie(new PartijRol(new Partij("partij1", "000001"), Rol.AFNEMER), new Leveringsautorisatie(Stelsel.BRP, false));
        toegang.setDatumIngang(20160005);

        Assert.assertEquals(1, errorCount(toegang));
    }

    @Test
    public void datumEindeDeelsOnbekend() {
        final ToegangLeveringsAutorisatie toegang =
                new ToegangLeveringsAutorisatie(new PartijRol(new Partij("partij1", "000001"), Rol.AFNEMER), new Leveringsautorisatie(Stelsel.BRP, false));
        toegang.setDatumIngang(20160005);

        Assert.assertEquals(1, errorCount(toegang));
    }

    private int errorCount(final ToegangLeveringsAutorisatie toegang) {
        final Map<?, ?> result = new HashMap<>();
        final Errors errors = new MapBindingResult(result, "toegang");
        subject.validate(toegang, errors);
        return errors.getErrorCount();
    }
}
