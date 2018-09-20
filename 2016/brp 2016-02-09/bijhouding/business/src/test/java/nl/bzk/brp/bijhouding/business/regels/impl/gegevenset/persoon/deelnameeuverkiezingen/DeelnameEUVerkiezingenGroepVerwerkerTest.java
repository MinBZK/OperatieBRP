/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.deelnameeuverkiezingen;

import nl.bzk.brp.dataaccess.repository.ReferentieDataRepository;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonDeelnameEUVerkiezingenGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class DeelnameEUVerkiezingenGroepVerwerkerTest {

    private DeelnameEUVerkiezingenGroepVerwerker verwerker;
    private PersoonBericht persoonBericht;
    private PersoonHisVolledigImpl persoonHisVolledigImpl;

    @Mock
    private ReferentieDataRepository referentieDataRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        persoonBericht = new PersoonBericht();
        final PersoonDeelnameEUVerkiezingenGroepBericht euverkiezingen = new PersoonDeelnameEUVerkiezingenGroepBericht();
        euverkiezingen.setIndicatieDeelnameEUVerkiezingen(JaNeeAttribuut.JA);
        persoonBericht.setDeelnameEUVerkiezingen(euverkiezingen);

        persoonHisVolledigImpl = new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));

        ActieModel actie = creeerActie();

        verwerker = new DeelnameEUVerkiezingenGroepVerwerker(persoonBericht, persoonHisVolledigImpl, actie);
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.VR00016, verwerker.getRegel());
    }

    @Test
    public void testNeemBerichtDataOverInModel() {
        Assert.assertNull(persoonHisVolledigImpl.getPersoonDeelnameEUVerkiezingenHistorie().getActueleRecord());

        verwerker.verrijkBericht();
        verwerker.neemBerichtDataOverInModel();

        Assert.assertNotNull(persoonHisVolledigImpl.getPersoonDeelnameEUVerkiezingenHistorie().getActueleRecord());

        Assert.assertEquals(JaNeeAttribuut.JA,
                            persoonHisVolledigImpl.getPersoonDeelnameEUVerkiezingenHistorie().getActueleRecord()
                                    .getIndicatieDeelnameEUVerkiezingen());
    }

    @Test
    public void testVerzamelAfleidingsregels() {
        verwerker.verzamelAfleidingsregels();
        Assert.assertEquals(0, verwerker.getAfleidingsregels().size());
    }

    /**
     * Creeer een standaard actie.
     *
     * @return het actie model
     */
    private ActieModel creeerActie() {
        return new ActieModel(new SoortActieAttribuut(SoortActie.REGISTRATIE_OVERLIJDEN),
                              new AdministratieveHandelingModel(new SoortAdministratieveHandelingAttribuut(
                                      SoortAdministratieveHandeling.OVERLIJDEN_IN_NEDERLAND), null,
                                      null, null), null, new DatumEvtDeelsOnbekendAttribuut(20120101), null, DatumTijdAttribuut.nu(), null);
    }
}
