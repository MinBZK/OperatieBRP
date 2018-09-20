/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.geslachtsaanduiding;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.GeslachtsaanduidingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeslachtsaanduidingGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GeslachtsaanduidingGroepVerwerkerTest {

    private static final Integer DATUM_AANVANG_GELDIGHEID = 20120101;
    private static final Integer DATUM_GEBOORTE = 20120101;
    private static final Geslachtsaanduiding GESLACHTSAANDUIDING = Geslachtsaanduiding.MAN;

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

        GeslachtsaanduidingGroepVerwerker verwerker =
                new GeslachtsaanduidingGroepVerwerker(persoonBericht, persoonHisVolledigImpl, actie);
        Assert.assertEquals(Regel.VR00010, verwerker.getRegel());
    }

    @Test
    public void testNeemBerichtDataOverInModel() {
        PersoonGeslachtsaanduidingGroepBericht geslachtsaanduiding = creeerGeslachtsaanduiding();
        persoonBericht.setGeslachtsaanduiding(geslachtsaanduiding);

        ActieModel actie = creeerActie();

        GeslachtsaanduidingGroepVerwerker verwerker =
                new GeslachtsaanduidingGroepVerwerker(persoonBericht, persoonHisVolledigImpl, actie);
        verwerker.verrijkBericht();
        verwerker.neemBerichtDataOverInModel();

        Assert.assertNotNull(persoonHisVolledigImpl.getPersoonGeslachtsaanduidingHistorie().getActueleRecord());
        Assert.assertEquals(DATUM_AANVANG_GELDIGHEID,
                            persoonHisVolledigImpl.getPersoonGeslachtsaanduidingHistorie().getActueleRecord()
                                    .getDatumAanvangGeldigheid().getWaarde());
        Assert.assertEquals(GESLACHTSAANDUIDING,
                            persoonHisVolledigImpl.getPersoonGeslachtsaanduidingHistorie().getActueleRecord()
                                    .getGeslachtsaanduiding().getWaarde());
    }

    @Test
    public void testVerzamelAfleidingsregels() {
        PersoonGeslachtsaanduidingGroepBericht geslachtsaanduiding = creeerGeslachtsaanduiding();
        persoonBericht.setGeslachtsaanduiding(geslachtsaanduiding);

        ActieModel actie = creeerActie();

        GeslachtsaanduidingGroepVerwerker verwerker =
                new GeslachtsaanduidingGroepVerwerker(persoonBericht, persoonHisVolledigImpl, actie);
        verwerker.verrijkBericht();
        verwerker.neemBerichtDataOverInModel();

        verwerker.verzamelAfleidingsregels();

        Assert.assertEquals(0, verwerker.getAfleidingsregels().size());
    }


    /**
     * Creeer een standaard persoon geboorte groep bericht.
     *
     * @return het persoon geboorte groep bericht
     */
    private PersoonGeslachtsaanduidingGroepBericht creeerGeslachtsaanduiding() {
        PersoonGeslachtsaanduidingGroepBericht geslachtsaanduiding = new PersoonGeslachtsaanduidingGroepBericht();
        geslachtsaanduiding.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(DATUM_AANVANG_GELDIGHEID));
        geslachtsaanduiding.setGeslachtsaanduiding(new GeslachtsaanduidingAttribuut(GESLACHTSAANDUIDING));
        return geslachtsaanduiding;
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
                                      null, null), null, new DatumEvtDeelsOnbekendAttribuut(DATUM_GEBOORTE),
                              null, DatumTijdAttribuut.nu(), null);
    }
}
