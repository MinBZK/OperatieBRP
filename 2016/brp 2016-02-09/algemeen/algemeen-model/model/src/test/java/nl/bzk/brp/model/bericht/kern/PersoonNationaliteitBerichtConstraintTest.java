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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NationaliteitcodeAttribuut;
import org.junit.Assert;
import org.junit.Test;


/**
 * Testen voor constraints die op dit groep is opgelegd.
 */
public class PersoonNationaliteitBerichtConstraintTest {

    @Test
    public void testLanCodeVerkrijgenCode() {
        // alles leeg => alles is goed (land null => gemeente null)
        Assert.assertEquals(0, validate(maakGroep(null, null)).size());
    }

    @Test
    public void testLanCodeNLEnRedenCodeGevuld() {
        // land NL , code gevuld
        Assert.assertEquals(0, validate(maakGroep(NationaliteitcodeAttribuut.NL_NATIONALITEIT_CODE_STRING, "002")).size());
    }

    @Test
    public void testLanCodeNietNLEnCodeGevuld() {
        // niet NL ==> code moet null zijn
        Assert.assertEquals(1, validate(maakGroep("9999", "202")).size());
    }

    @Test
    public void testLanCodeNullEnCodeGevuld() {
        // niet NL (null) ==> moet null zijn
        Assert.assertEquals(1, validate(maakGroep(null, "002")).size());
    }

    private PersoonNationaliteitBericht maakGroep(final String natCode, final String code) {
        PersoonNationaliteitBericht object = new PersoonNationaliteitBericht();
        object.setStandaard(new PersoonNationaliteitStandaardGroepBericht());
        if (null != natCode) {
            object.setNationaliteitCode(natCode);
        }
        if (null != code) {
            object.getStandaard().setRedenVerkrijgingCode(code);
        }
        return object;
    }

    @SuppressWarnings("rawtypes")
    private Set<ConstraintViolation> validate(final Object groep) {
        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation> overtredingen = new HashSet<ConstraintViolation>();
        overtredingen.addAll(validator.validate(groep, Default.class));
        return overtredingen;
    }
}
