/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import java.util.HashSet;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LandGebiedCodeAttribuut;
import org.junit.Assert;
import org.junit.Test;


/**
 * Testen voor constraints die op dit groep is opgelegd.
 */
public class RelatieStandaardGroepBerichtConstraintTest {

    @Test
    public void testDatumAanvangNietGeldige() {
        // NL aanvang
        Set<ConstraintViolation> violations =
                validate(maakGroep(LandGebiedCodeAttribuut.NL_LAND_CODE_STRING, "02002", null, null,
                        new DatumEvtDeelsOnbekendAttribuut(20100229), null));
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("BRAL2102", violations.iterator().next().getMessage());

        violations =
                validate(maakGroep(LandGebiedCodeAttribuut.NL_LAND_CODE_STRING, "02002", null, null,
                        new DatumEvtDeelsOnbekendAttribuut(20100200), null));
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("BRAL2102", violations.iterator().next().getMessage());

        // Geen NL aanvang
        violations = validate(maakGroep("1", null, null, null, new DatumEvtDeelsOnbekendAttribuut(20110001), null));
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("{BRAL0102}", violations.iterator().next().getMessage());
    }

    @Test
    public void testDatumAanvangGeldig() {
        // NL aanvang
        Set<ConstraintViolation> violations =
                validate(maakGroep(LandGebiedCodeAttribuut.NL_LAND_CODE_STRING, "02002", null, null,
                        new DatumEvtDeelsOnbekendAttribuut(20100228), null));
        Assert.assertEquals(0, violations.size());

        // Geen NL aanvang
        violations = validate(maakGroep("1", null, null, null, new DatumEvtDeelsOnbekendAttribuut(20110100), null));
        Assert.assertEquals(0, violations.size());
    }

    @Test
    public void testDatumEindeNietGeldig() {
        // NL aanvang
        Set<ConstraintViolation> violations =
                validate(maakGroep(null, null, LandGebiedCodeAttribuut.NL_LAND_CODE_STRING, "02002", null,
                        new DatumEvtDeelsOnbekendAttribuut(20100229)));
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("BRAL2103", violations.iterator().next().getMessage());

        violations =
                validate(maakGroep(null, null, LandGebiedCodeAttribuut.NL_LAND_CODE_STRING, "02002", null,
                        new DatumEvtDeelsOnbekendAttribuut(20100200)));
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("BRAL2103", violations.iterator().next().getMessage());

        // Geen NL aanvang
        violations = validate(maakGroep(null, null, "1", null, null, new DatumEvtDeelsOnbekendAttribuut(20110001)));
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("{BRAL0102}", violations.iterator().next().getMessage());
    }

    @Test
    public void testDatumEindeGeldig() {
        // NL aanvang
        Set<ConstraintViolation> violations =
                validate(maakGroep(null, null, LandGebiedCodeAttribuut.NL_LAND_CODE_STRING, "02002", null,
                        new DatumEvtDeelsOnbekendAttribuut(20100228)));
        Assert.assertEquals(0, violations.size());

        // Geen NL aanvang
        violations = validate(maakGroep(null, null, "1", null, null, new DatumEvtDeelsOnbekendAttribuut(20110100)));
        Assert.assertEquals(0, violations.size());
    }

    private RelatieStandaardGroepBericht maakGroep(final String landGebiedAanvangCode,
            final String gemeenteAanvangCode, final String landGebiedEindeCode, final String gemeenteEindeCode,
            final DatumEvtDeelsOnbekendAttribuut datumAanvang, final DatumEvtDeelsOnbekendAttribuut datumEinde)
    {
        final RelatieStandaardGroepBericht groep =
                new RelatieStandaardGroepBericht();
        if (null != landGebiedAanvangCode) {
            groep.setLandGebiedAanvangCode(landGebiedAanvangCode);
        }
        if (null != gemeenteAanvangCode) {
            groep.setGemeenteAanvangCode(gemeenteAanvangCode);
        }
        if (null != landGebiedEindeCode) {
            groep.setLandGebiedEindeCode(landGebiedEindeCode);
        }
        if (null != gemeenteEindeCode) {
            groep.setGemeenteEindeCode(gemeenteEindeCode);
        }
        if (null != datumAanvang) {
            groep.setDatumAanvang(datumAanvang);
        }
        if (null != datumEinde) {
            groep.setDatumEinde(datumEinde);
        }

        return groep;
    }

    @SuppressWarnings("rawtypes")
    private Set<ConstraintViolation> validate(final Object groep) {
        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        final Set<ConstraintViolation> overtredingen = new HashSet<>();
        overtredingen.addAll(validator.validate(groep, Default.class));
        return overtredingen;
    }
}
