/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.berichtmodel;

import com.google.common.collect.Lists;
import java.util.List;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import org.junit.Assert;
import org.junit.Test;

/**
 * VerwerkPersoonBericht.
 */
public class VerwerkPersoonBerichtTest {

    @Test
    public void testVerwerkPersoonBericht() {
        final BasisBerichtGegevens basisBerichtGegevens = BasisBerichtGegevens.builder().build();

        final Persoonslijst persoonsLijst = TestBuilders.maakPersoonMetHandelingen(1);
        final BerichtElement berichtElement = BerichtElement.builder().build();
        final BijgehoudenPersoon.Builder builder = new BijgehoudenPersoon.Builder(persoonsLijst, berichtElement);
        final BijgehoudenPersoon bijgehoudenPersoon = builder.build();
        final List<BijgehoudenPersoon> bijgehoudenPersonen = Lists.newArrayList(bijgehoudenPersoon);
        final VerwerkPersoonBericht verwerkPersoonBericht = new VerwerkPersoonBericht(basisBerichtGegevens, null, bijgehoudenPersonen);
        Assert.assertEquals(bijgehoudenPersonen, verwerkPersoonBericht.getBijgehoudenPersonen());
        Assert.assertEquals(basisBerichtGegevens, verwerkPersoonBericht.getBasisBerichtGegevens());
        Assert.assertFalse(verwerkPersoonBericht.isLeeg());
    }

    @Test
    public void testVerwerkPersoonBericht_geenBijgehoudenPersonen() {
        final BasisBerichtGegevens basisBerichtGegevens = BasisBerichtGegevens.builder().build();
        final VerwerkPersoonBericht verwerkPersoonBericht = new VerwerkPersoonBericht(basisBerichtGegevens, null, null);
        Assert.assertTrue(verwerkPersoonBericht.getBijgehoudenPersonen().isEmpty());
        Assert.assertEquals(basisBerichtGegevens, verwerkPersoonBericht.getBasisBerichtGegevens());
    }
}
