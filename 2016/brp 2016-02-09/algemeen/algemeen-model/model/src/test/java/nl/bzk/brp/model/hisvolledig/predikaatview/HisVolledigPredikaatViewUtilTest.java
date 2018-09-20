/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.predikaatview;

import nl.bzk.brp.model.logisch.kern.HisPersoonAfgeleidAdministratiefGroep;
import nl.bzk.brp.model.logisch.kern.HisPersoonDeelnameEUVerkiezingenGroep;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeboorteModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeslachtsaanduidingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIdentificatienummersModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonSamengesteldeNaamModel;
import org.junit.Assert;
import org.junit.Test;

public class HisVolledigPredikaatViewUtilTest {

    @Test
    public void testIsAltijdTonenGroep() {
        final boolean resultaat1 = HisVolledigPredikaatViewUtil.isAltijdTonenGroep(HisPersoonGeslachtsaanduidingModel.class);
        final boolean resultaat2 = HisVolledigPredikaatViewUtil.isAltijdTonenGroep(HisPersoonGeboorteModel.class);
        final boolean resultaat3 = HisVolledigPredikaatViewUtil.isAltijdTonenGroep(HisPersoonIdentificatienummersModel.class);
        final boolean resultaat4 = HisVolledigPredikaatViewUtil.isAltijdTonenGroep(HisPersoonSamengesteldeNaamModel.class);

        final boolean resultaat5 = HisVolledigPredikaatViewUtil.isAltijdTonenGroep(HisPersoonAfgeleidAdministratiefGroep.class);
        final boolean resultaat6 = HisVolledigPredikaatViewUtil.isAltijdTonenGroep(HisPersoonDeelnameEUVerkiezingenGroep.class);

        Assert.assertTrue(resultaat1);
        Assert.assertTrue(resultaat2);
        Assert.assertTrue(resultaat3);
        Assert.assertTrue(resultaat4);
        Assert.assertFalse(resultaat5);
        Assert.assertFalse(resultaat6);
    }
}
