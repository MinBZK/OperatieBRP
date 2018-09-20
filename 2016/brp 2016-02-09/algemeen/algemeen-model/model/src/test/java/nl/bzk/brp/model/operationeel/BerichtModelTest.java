/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel;

import nl.bzk.brp.model.algemeen.attribuuttype.ber.BerichtdataAttribuut;
import nl.bzk.brp.model.operationeel.ber.BerichtModel;
import nl.bzk.brp.model.operationeel.ber.BerichtStandaardGroepModel;
import org.junit.Assert;
import org.junit.Test;


public class BerichtModelTest {

    @Test
    public void testPublicConstructorBerichtModel() {
        // BerichtModel moet public constructor hebben, dit is een handmatige wijziging.
        final BerichtModel berichtModel = new BerichtModel();
        Assert.assertNotNull(berichtModel);
    }

    @Test
    public void testSettersBerichtModelStandaardGroep() {
        // BerichtModel moet setters hebben op standaardgroep, dit is een handmatige wijziging.
        final BerichtModel berichtModel = new BerichtModel();
        final BerichtStandaardGroepModel standaardGroep =
                new BerichtStandaardGroepModel(null, new BerichtdataAttribuut("test"), null);
        standaardGroep.setAdministratieveHandelingId(2L);
        standaardGroep.setAdministratieveHandelingId(1L);
        standaardGroep.setAntwoordOp(new BerichtModel());
        standaardGroep.setData(new BerichtdataAttribuut("test"));
        berichtModel.setStandaard(standaardGroep);

        Assert.assertNotNull(berichtModel);
    }
}
