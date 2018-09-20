/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels;

import nl.bzk.brp.bijhouding.business.regels.AbstractVerwerkingsregel;
import nl.bzk.brp.bijhouding.business.regels.Afleidingsregel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.basis.HisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;


/**
 * Unit test voor de generieke methodes van alle verwerkingsregels, dus voor de methodes in de
 * {@link AbstractVerwerkingsregel} klasse.
 */
public class GeneriekeVerwerkingsregelTest {

    @Test
    public void testConstructor() {
        final BerichtEntiteit berichtEntiteit = Mockito.mock(BerichtEntiteit.class);
        final HisVolledigImpl hisVolledig = Mockito.mock(HisVolledigImpl.class);
        final ActieModel actie = Mockito.mock(ActieModel.class);

        final AbstractVerwerkingsregel<BerichtEntiteit, HisVolledigImpl> abstractVerwerkingsregel =
            new AbstractVerwerkingsregel<BerichtEntiteit, HisVolledigImpl>(berichtEntiteit, hisVolledig, actie)
            {
                @Override
                public void neemBerichtDataOverInModel() {
                }

                @Override
                public Regel getRegel() {
                    return Regel.DUMMY;
                }
            };

        Assert.assertEquals(berichtEntiteit, abstractVerwerkingsregel.getBericht());
        Assert.assertEquals(hisVolledig, abstractVerwerkingsregel.getModel());
        Assert.assertEquals(actie, abstractVerwerkingsregel.getActie());
    }

    @Test
    public void testVoegAfleidingsregelToe() {
        final BerichtEntiteit berichtEntiteit = Mockito.mock(BerichtEntiteit.class);
        final HisVolledigImpl hisVolledig = Mockito.mock(HisVolledigImpl.class);
        final ActieModel actie = Mockito.mock(ActieModel.class);

        final AbstractVerwerkingsregel<BerichtEntiteit, HisVolledigImpl> abstractVerwerkingsregel =
            new AbstractVerwerkingsregel<BerichtEntiteit, HisVolledigImpl>(berichtEntiteit, hisVolledig, actie)
            {
                @Override
                public void neemBerichtDataOverInModel() {
                }

                @Override
                public Regel getRegel() {
                    return Regel.DUMMY;
                }
            };

        final Afleidingsregel afleidingsregel = Mockito.mock(Afleidingsregel.class);
        abstractVerwerkingsregel.voegAfleidingsregelToe(afleidingsregel);

        Assert.assertFalse(abstractVerwerkingsregel.getAfleidingsregels().isEmpty());
        Assert.assertEquals(afleidingsregel, abstractVerwerkingsregel.getAfleidingsregels().get(0));
    }
}
