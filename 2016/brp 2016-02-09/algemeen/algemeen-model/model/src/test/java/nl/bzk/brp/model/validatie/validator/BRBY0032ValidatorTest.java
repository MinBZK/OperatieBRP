/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.validatie.validator;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.bericht.kern.ActieCorrectieAdresBericht;

import org.junit.Assert;
import org.junit.Test;


/**
 * Unit test voor de {@link ConditioneelVerplichtVeldValidator} class.
 */
public class BRBY0032ValidatorTest {

    private final BRBY0032Validator validator = new BRBY0032Validator();

    @Test
    public void testDatumDAGnaDEG() {
        ActieCorrectieAdresBericht actie = new ActieCorrectieAdresBericht();
        actie.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(20130101));
        actie.setDatumEindeGeldigheid(new DatumEvtDeelsOnbekendAttribuut(20120101));

        Assert.assertFalse(validator.isValid(actie, null));
    }

    @Test
    public void testDatumDAGzelfdeAlsDEG() {
        ActieCorrectieAdresBericht actie = new ActieCorrectieAdresBericht();
        actie.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(20130101));
        actie.setDatumEindeGeldigheid(new DatumEvtDeelsOnbekendAttribuut(20130101));

        Assert.assertFalse(validator.isValid(actie, null));
    }

    @Test
    public void testDatumDAGvoorDEG() {
        ActieCorrectieAdresBericht actie = new ActieCorrectieAdresBericht();
        actie.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(20110101));
        actie.setDatumEindeGeldigheid(new DatumEvtDeelsOnbekendAttribuut(20120101));

        Assert.assertTrue(validator.isValid(actie, null));
    }

    @Test
    public void testGeenDAGofDEG() {
        ActieCorrectieAdresBericht actie = new ActieCorrectieAdresBericht();
        actie.setDatumAanvangGeldigheid(null);
        actie.setDatumEindeGeldigheid(new DatumEvtDeelsOnbekendAttribuut(20120101));

        Assert.assertTrue(validator.isValid(actie, null));

        actie.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(20120101));
        actie.setDatumEindeGeldigheid(null);

        Assert.assertTrue(validator.isValid(actie, null));

        actie.setDatumAanvangGeldigheid(null);
        actie.setDatumEindeGeldigheid(null);

        Assert.assertTrue(validator.isValid(actie, null));
    }
}
