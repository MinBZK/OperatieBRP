/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.identificatienummers;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.BurgerservicenummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class IdentificatienummersGroepVerwerkerTest {

    private static final Integer BSN = 111222333;
    private static final Integer DATUM_AANVANG_GELDIGHEID = 20120101;
    private static final Integer DATUM_GEBOORTE = 20120101;

    private PersoonBericht persoonBericht;
    private PersoonHisVolledigImpl persoonHisVolledigImpl;

    @Before
    public void init() {
        persoonBericht = new PersoonBericht();
        persoonHisVolledigImpl = new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
    }

    @Test
    public void testGetRegel() {
        ActieModel actie = creeerActie();

        IdentificatienummersGroepVerwerker verwerker =
                new IdentificatienummersGroepVerwerker(persoonBericht, persoonHisVolledigImpl, actie);
        Assert.assertEquals(Regel.VR00025, verwerker.getRegel());
    }

    @Test
    public void testNeemBerichtDataOverInModel() {
        PersoonIdentificatienummersGroepBericht identificatienummers = creeerIdentificatienummers();
        persoonBericht.setIdentificatienummers(identificatienummers);

        ActieModel actie = creeerActie();

        Assert.assertNull(persoonHisVolledigImpl.getPersoonIdentificatienummersHistorie().getActueleRecord());

        IdentificatienummersGroepVerwerker verwerker =
                new IdentificatienummersGroepVerwerker(persoonBericht, persoonHisVolledigImpl, actie);
        verwerker.verrijkBericht();
        verwerker.neemBerichtDataOverInModel();

        Assert.assertNotNull(persoonHisVolledigImpl.getPersoonIdentificatienummersHistorie().getActueleRecord());
        Assert.assertEquals(BSN,
                            persoonHisVolledigImpl.getPersoonIdentificatienummersHistorie().getActueleRecord()
                                    .getBurgerservicenummer().getWaarde());
    }

    @Test
    public void testVerzamelAfleidingsregels() {
        PersoonIdentificatienummersGroepBericht identificatienummers = creeerIdentificatienummers();
        persoonBericht.setIdentificatienummers(identificatienummers);

        ActieModel actie = creeerActie();

        IdentificatienummersGroepVerwerker verwerker =
                new IdentificatienummersGroepVerwerker(persoonBericht, persoonHisVolledigImpl, actie);
        verwerker.verrijkBericht();
        verwerker.neemBerichtDataOverInModel();

        verwerker.verzamelAfleidingsregels();

        Assert.assertEquals(0, verwerker.getAfleidingsregels().size());
    }


    /**
     * Creeer een standaard persoon identificatienummers groep bericht.
     *
     * @return het persoon geboorte groep bericht
     */
    private PersoonIdentificatienummersGroepBericht creeerIdentificatienummers() {
        PersoonIdentificatienummersGroepBericht identificatienummers = new PersoonIdentificatienummersGroepBericht();
        identificatienummers.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(DATUM_AANVANG_GELDIGHEID));
        identificatienummers.setBurgerservicenummer(new BurgerservicenummerAttribuut(BSN));
        return identificatienummers;
    }

    /**
     * Creeer een standaard actie.
     *
     * @return het actie model
     */
    private ActieModel creeerActie() {
        return new ActieModel(new SoortActieAttribuut(SoortActie.REGISTRATIE_GEBOORTE),
                              new AdministratieveHandelingModel(new SoortAdministratieveHandelingAttribuut(
                                      SoortAdministratieveHandeling.GEBOORTE_IN_NEDERLAND), null,
                                      null, null), null, new DatumEvtDeelsOnbekendAttribuut(DATUM_GEBOORTE), null, DatumTijdAttribuut.nu(), null);
    }
}
