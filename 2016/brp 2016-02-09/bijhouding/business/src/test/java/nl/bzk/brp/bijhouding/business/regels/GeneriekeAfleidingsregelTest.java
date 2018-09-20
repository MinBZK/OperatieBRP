/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels;

import nl.bzk.brp.bijhouding.business.regels.AbstractAfleidingsregel;
import nl.bzk.brp.bijhouding.business.regels.AfleidingResultaat;
import nl.bzk.brp.model.HisVolledigRootObject;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Unit test klasse die generieke zaken t.b.v. afleidingsregels test. Hierin worden dan ook met name de methodes in de
 * {@link AbstractAfleidingsregel} klasse getest.
 *
 * @see AbstractAfleidingsregel
 */
public class GeneriekeAfleidingsregelTest {

    @Test
    public void testConstructor() {
        final AbstractAfleidingsregel<HisVolledigRootObject> afleidingsregel = instantieerAfleidingsRegel();

        Assert.assertNotNull(afleidingsregel.getModel());
        Assert.assertNotNull(afleidingsregel.getActie());
        Assert.assertNotNull(afleidingsregel.getExtraBijgehoudenPersonen());
        Assert.assertNotNull(afleidingsregel.getExtraAangemaakteNietIngeschrevenen());
    }

    @Test
    public void testVoegExtraBijgehoudenPersoonToe() {
        final AbstractAfleidingsregel<HisVolledigRootObject> afleidingsregel = instantieerAfleidingsRegel();

        final PersoonHisVolledigImpl persoonHisVolledig = Mockito.mock(PersoonHisVolledigImpl.class);
        afleidingsregel.voegExtraBijgehoudenPersoonToe(persoonHisVolledig);

        Assert.assertFalse(afleidingsregel.getExtraBijgehoudenPersonen().isEmpty());
        Assert.assertEquals(persoonHisVolledig, afleidingsregel.getExtraBijgehoudenPersonen().iterator().next());
    }

    @Test
    public void testVoegExtraAangemaakteNietIngeschreveneToe() {
        final AbstractAfleidingsregel<HisVolledigRootObject> afleidingsregel = instantieerAfleidingsRegel();

        final PersoonHisVolledigImpl persoonHisVolledig = Mockito.mock(PersoonHisVolledigImpl.class);
        Mockito.when(persoonHisVolledig.getSoort())
               .thenReturn(new SoortPersoonAttribuut(SoortPersoon.NIET_INGESCHREVENE));
        afleidingsregel.voegExtraAangemaakteNietIngeschreveneToe(persoonHisVolledig);

        Assert.assertFalse(afleidingsregel.getExtraAangemaakteNietIngeschrevenen().isEmpty());
        Assert.assertEquals(persoonHisVolledig,
            afleidingsregel.getExtraAangemaakteNietIngeschrevenen().iterator().next());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testToevoegenIngeschreveneAlsExtraNietIngeschrevene() {
        final AbstractAfleidingsregel<HisVolledigRootObject> afleidingsregel = instantieerAfleidingsRegel();

        final PersoonHisVolledigImpl persoonHisVolledig = Mockito.mock(PersoonHisVolledigImpl.class);
        Mockito.when(persoonHisVolledig.getSoort())
               .thenReturn(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        afleidingsregel.voegExtraAangemaakteNietIngeschreveneToe(persoonHisVolledig);
    }

    /**
     * Instantieert een nieuwe {@link AbstractAfleidingsregel} instantie voor opgegeven object en actie.
     * @return een nieuwe afleidingsregel.
     */
    private AbstractAfleidingsregel<HisVolledigRootObject> instantieerAfleidingsRegel() {
        final HisVolledigRootObject rootObject = Mockito.mock(HisVolledigRootObject.class);
        final ActieModel actie = Mockito.mock(ActieModel.class);

        return new AbstractAfleidingsregel<HisVolledigRootObject>(rootObject, actie) {

            @Override
            public AfleidingResultaat leidAf() {
                return null;
            }

            @Override
            public Regel getRegel() {
                return Regel.DUMMY;
            }
        };

    }
}
