/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.validatie;

import java.util.HashMap;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BijhouderFiatteringsuitzondering;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAdres;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.validation.Errors;
import org.springframework.validation.MapBindingResult;

public class BijhouderFiatteringsuitzonderingValidatorTest {

    private final BijhouderFiatteringsuitzonderingValidator subject = new BijhouderFiatteringsuitzonderingValidator();

    @Test
    public void supports() {
        Assert.assertTrue(subject.supports(BijhouderFiatteringsuitzondering.class));
        Assert.assertFalse(subject.supports(PersoonAdres.class));
    }

    @Test
    public void geenDatumIngang() {
        final BijhouderFiatteringsuitzondering
                bijhouderFiatteringsuitzondering =
                new BijhouderFiatteringsuitzondering(new PartijRol(new Partij("partij1", "000001"), Rol.AFNEMER));
        Assert.assertEquals(1, errorCount(bijhouderFiatteringsuitzondering));
    }

    @Test
    public void datumIngangVoorVandaag() {
        final BijhouderFiatteringsuitzondering
                bijhouderFiatteringsuitzondering =
                new BijhouderFiatteringsuitzondering(new PartijRol(new Partij("partij1", "000001"), Rol.AFNEMER));
        bijhouderFiatteringsuitzondering.setDatumIngang(20160606);
        Assert.assertEquals(0, errorCount(bijhouderFiatteringsuitzondering));
    }

    @Test
    public void datumIngangNaDatumEinde() {
        final BijhouderFiatteringsuitzondering
                bijhouderFiatteringsuitzondering =
                new BijhouderFiatteringsuitzondering(new PartijRol(new Partij("partij1", "000001"), Rol.AFNEMER));
        bijhouderFiatteringsuitzondering.setDatumIngang(20160606);
        bijhouderFiatteringsuitzondering.setDatumEinde(20160605);

        Assert.assertEquals(1, errorCount(bijhouderFiatteringsuitzondering));
    }

    @Test
    public void datumIngangVoorDatumEinde() {
        final BijhouderFiatteringsuitzondering
                bijhouderFiatteringsuitzondering =
                new BijhouderFiatteringsuitzondering(new PartijRol(new Partij("partij1", "000001"), Rol.AFNEMER));
        bijhouderFiatteringsuitzondering.setDatumIngang(20160605);
        bijhouderFiatteringsuitzondering.setDatumEinde(20160606);

        Assert.assertEquals(0, errorCount(bijhouderFiatteringsuitzondering));
    }

    @Test
    public void datumIngangDeelsOnbekend() {
        final BijhouderFiatteringsuitzondering
                bijhouderFiatteringsuitzondering =
                new BijhouderFiatteringsuitzondering(new PartijRol(new Partij("partij1", "000001"), Rol.AFNEMER));
        bijhouderFiatteringsuitzondering.setDatumIngang(20160005);

        Assert.assertEquals(1, errorCount(bijhouderFiatteringsuitzondering));
    }

    @Test
    public void datumEindeDeelsOnbekend() {
        final BijhouderFiatteringsuitzondering bijhouderFiatteringsuitzondering =
                new BijhouderFiatteringsuitzondering(new PartijRol(new Partij("partij1", "000001"), Rol.AFNEMER));
        bijhouderFiatteringsuitzondering.setDatumIngang(20160005);

        Assert.assertEquals(1, errorCount(bijhouderFiatteringsuitzondering));
    }

    private int errorCount(final BijhouderFiatteringsuitzondering bijhouderFiatteringsuitzondering) {
        final Map<?, ?> result = new HashMap<>();
        final Errors errors = new MapBindingResult(result, "bijhouderFiatteringsuitzondering");
        subject.validate(bijhouderFiatteringsuitzondering, errors);
        return errors.getErrorCount();
    }
}
