/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.geslachtsnaamcomponent;

import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.samengesteldenaam.SamengesteldeNaamAfleiding;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GeslachtsnaamstamAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VolgnummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.bericht.kern.PersoonGeslachtsnaamcomponentBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeslachtsnaamcomponentStandaardGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonGeslachtsnaamcomponentHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GeslachtsnaamcomponentVerwerkerTest {

    private static final Integer DATUM_AANVANG_GELDIGHEID = 20120101;
    private static final Integer DATUM_GEBOORTE           = 20120101;
    private static final String  NAAM                     = "Testnaam";
    private PersoonGeslachtsnaamcomponentBericht         geslachtsnaamcomponentBericht;
    private PersoonGeslachtsnaamcomponentHisVolledigImpl geslachtsnaamcomponentHisVolledigImpl;
    private PersoonHisVolledigImpl                       persoonHisVolledigImpl;

    @Before
    public void init() {
        persoonHisVolledigImpl = new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));

        geslachtsnaamcomponentBericht = new PersoonGeslachtsnaamcomponentBericht();
        geslachtsnaamcomponentHisVolledigImpl = new PersoonGeslachtsnaamcomponentHisVolledigImpl(persoonHisVolledigImpl,
                new VolgnummerAttribuut(1));
    }

    @Test
    public void testGetRegel() {
        ActieModel actie = creeerActie();

        GeslachtsnaamcomponentVerwerker verwerker =
                new GeslachtsnaamcomponentVerwerker(geslachtsnaamcomponentBericht,
                        geslachtsnaamcomponentHisVolledigImpl,
                        actie);
        Assert.assertEquals(Regel.VR00002, verwerker.getRegel());
    }

    @Test
    public void testNeemBerichtDataOverInModel() {
        PersoonGeslachtsnaamcomponentStandaardGroepBericht geslachtsnaamcomponentStandaardGroepBericht
            = creeerGeslachtsnaamcomponent();
        geslachtsnaamcomponentBericht.setStandaard(geslachtsnaamcomponentStandaardGroepBericht);

        ActieModel actie = creeerActie();

        Assert.assertNull(
                geslachtsnaamcomponentHisVolledigImpl.getPersoonGeslachtsnaamcomponentHistorie().getActueleRecord());

        GeslachtsnaamcomponentVerwerker verwerker =
                new GeslachtsnaamcomponentVerwerker(geslachtsnaamcomponentBericht,
                        geslachtsnaamcomponentHisVolledigImpl, actie);
        verwerker.verrijkBericht();
        verwerker.neemBerichtDataOverInModel();

        Assert.assertNotNull(geslachtsnaamcomponentHisVolledigImpl.getPersoonGeslachtsnaamcomponentHistorie()
                .getActueleRecord());
        Assert.assertEquals(NAAM,
                geslachtsnaamcomponentHisVolledigImpl.getPersoonGeslachtsnaamcomponentHistorie()
                        .getActueleRecord().getStam().getWaarde());
    }

    @Test
    public void testVerzamelAfleidingsregels() {
        PersoonGeslachtsnaamcomponentStandaardGroepBericht geslachtsnaamcomponentStandaardGroepBericht
            = creeerGeslachtsnaamcomponent();
        geslachtsnaamcomponentBericht.setStandaard(geslachtsnaamcomponentStandaardGroepBericht);

        ActieModel actie = creeerActie();

        GeslachtsnaamcomponentVerwerker verwerker =
                new GeslachtsnaamcomponentVerwerker(geslachtsnaamcomponentBericht,
                        geslachtsnaamcomponentHisVolledigImpl, actie);
        verwerker.verrijkBericht();
        verwerker.neemBerichtDataOverInModel();

        verwerker.verzamelAfleidingsregels();

        Assert.assertEquals(1, verwerker.getAfleidingsregels().size());
        Assert.assertTrue(verwerker.getAfleidingsregels().get(0) instanceof SamengesteldeNaamAfleiding);
    }

    /**
     * Creeer een standaard persoon geslachtsnaamcomponent groep bericht.
     *
     * @return het persoon geslachtsnaamcomponent groep bericht
     */
    private PersoonGeslachtsnaamcomponentStandaardGroepBericht creeerGeslachtsnaamcomponent() {
        PersoonGeslachtsnaamcomponentStandaardGroepBericht geslachtsnaamcomponent =
                new PersoonGeslachtsnaamcomponentStandaardGroepBericht();
        geslachtsnaamcomponent.setStam(new GeslachtsnaamstamAttribuut(NAAM));
        geslachtsnaamcomponent.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(DATUM_AANVANG_GELDIGHEID));
        return geslachtsnaamcomponent;
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
