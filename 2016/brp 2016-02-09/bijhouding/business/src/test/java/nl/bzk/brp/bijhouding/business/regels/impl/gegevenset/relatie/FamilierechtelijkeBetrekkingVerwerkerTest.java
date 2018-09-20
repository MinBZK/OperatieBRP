/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.relatie;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.bericht.kern.FamilierechtelijkeBetrekkingBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.FamilierechtelijkeBetrekkingHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.util.hisvolledig.kern.FamilierechtelijkeBetrekkingHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Test;

public class FamilierechtelijkeBetrekkingVerwerkerTest {

    @Test
    public void testGetRegel() {
        final FamilierechtelijkeBetrekkingVerwerker verwerker = new FamilierechtelijkeBetrekkingVerwerker(null, null, null);

        Assert.assertEquals(Regel.VR02002, verwerker.getRegel());
    }

    @Test
    public void testNeemBerichtDataOverInModel() {
        final FamilierechtelijkeBetrekkingBericht familierechtelijkeBetrekkingBericht = new FamilierechtelijkeBetrekkingBericht();
        final FamilierechtelijkeBetrekkingHisVolledigImpl familierechtelijkeBetrekkingHisVolledig =
            new FamilierechtelijkeBetrekkingHisVolledigImplBuilder().build();

        Assert.assertTrue(familierechtelijkeBetrekkingHisVolledig.getRelatieHistorie().getHistorie().size() == 0);

        final FamilierechtelijkeBetrekkingVerwerker verwerker =
            new FamilierechtelijkeBetrekkingVerwerker(familierechtelijkeBetrekkingBericht, familierechtelijkeBetrekkingHisVolledig,
                                                      creeerActie(SoortAdministratieveHandeling.GEBOORTE_IN_NEDERLAND, SoortActie.REGISTRATIE_GEBOORTE));

        verwerker.neemBerichtDataOverInModel();

        Assert.assertTrue(familierechtelijkeBetrekkingHisVolledig.getRelatieHistorie().getHistorie().size() > 0);
        Assert.assertNotNull(familierechtelijkeBetrekkingHisVolledig.getRelatieHistorie().getActueleRecord());
    }

    /**
     * Creeert een standaard actie.
     *
     * @param soortAdministratieveHandeling soort administratieve handeling
     * @param soortActie soort actie
     * @return actie model
     */
    private ActieModel creeerActie(final SoortAdministratieveHandeling soortAdministratieveHandeling,
                                   final SoortActie soortActie)
    {
        return new ActieModel(new SoortActieAttribuut(soortActie),
                              new AdministratieveHandelingModel(new SoortAdministratieveHandelingAttribuut(
                                  soortAdministratieveHandeling), null,
                                                                null, null), null, new DatumEvtDeelsOnbekendAttribuut(20130710), null,
                              DatumTijdAttribuut.bouwDatumTijd(2013, 7, 9), null);
    }
}
