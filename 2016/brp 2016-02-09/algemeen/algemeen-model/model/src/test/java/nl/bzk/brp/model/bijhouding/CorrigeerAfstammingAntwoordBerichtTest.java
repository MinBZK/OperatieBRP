/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bijhouding;

import nl.bzk.brp.model.bericht.ber.BerichtStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.HandelingCorrectieAfstammingBericht;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


public class CorrigeerAfstammingAntwoordBerichtTest {

    private CorrigeerAfstammingAntwoordBericht bericht = new CorrigeerAfstammingAntwoordBericht();

    @Test
    public void zouCorrectieAfstammingMoetenZijn() {
        bericht.setStandaard(new BerichtStandaardGroepBericht());
        bericht.getStandaard().setAdministratieveHandeling(new HandelingCorrectieAfstammingBericht());
        assertThat(bericht.isCorrectieAfstamming(), is(true));
    }

}
