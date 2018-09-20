/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.nationaliteit;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NationaliteitcodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OmschrijvingEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenVerkrijgingCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Nationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NationaliteitAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenVerkrijgingNLNationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenVerkrijgingNLNationaliteitAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.bericht.kern.PersoonNationaliteitBericht;
import nl.bzk.brp.model.bericht.kern.PersoonNationaliteitStandaardGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonNationaliteitHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class NationaliteitVerwerkerTest {

    private static final Integer DATUM_AANVANG_GELDIGHEID = 20120101;
    private static final Integer DATUM_GEBOORTE = 20120101;
    private PersoonNationaliteitBericht persoonNationaliteitBericht;
    private PersoonNationaliteitHisVolledigImpl nationaliteitHisVolledigImpl;
    private PersoonHisVolledigImpl persoonHisVolledigImpl;

    @Before
    public void init() {
        persoonHisVolledigImpl = new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));

        persoonNationaliteitBericht = new PersoonNationaliteitBericht();
        nationaliteitHisVolledigImpl = new PersoonNationaliteitHisVolledigImpl(persoonHisVolledigImpl, new NationaliteitAttribuut(
                new Nationaliteit(NationaliteitcodeAttribuut.NL_NATIONALITEIT_CODE, new NaamEnumeratiewaardeAttribuut("NL"), null, null)));
    }

    @Test
    public void testGetRegel() {
        ActieModel actie = creeerActie();

        NationaliteitVerwerker verwerker =
                new NationaliteitVerwerker(persoonNationaliteitBericht, nationaliteitHisVolledigImpl, actie);
        Assert.assertEquals(Regel.VR00004, verwerker.getRegel());
    }

    @Test
    public void testNeemBerichtDataOverInModel() {
        PersoonNationaliteitStandaardGroepBericht persoonVoornaamStandaardGroepBericht = creeerNationaliteitGroep();
        persoonNationaliteitBericht.setStandaard(persoonVoornaamStandaardGroepBericht);

        ActieModel actie = creeerActie();

        Assert.assertNull(nationaliteitHisVolledigImpl.getPersoonNationaliteitHistorie().getActueleRecord());

        NationaliteitVerwerker verwerker =
                new NationaliteitVerwerker(persoonNationaliteitBericht, nationaliteitHisVolledigImpl, actie);
        verwerker.neemBerichtDataOverInModel();

        Assert.assertNotNull(nationaliteitHisVolledigImpl.getPersoonNationaliteitHistorie().getActueleRecord());
        Assert.assertEquals(1,
                nationaliteitHisVolledigImpl.getPersoonNationaliteitHistorie()
                        .getActueleRecord().getRedenVerkrijging().getWaarde().getCode()
                        .getWaarde().intValue());
    }

    @Test
    public void testVerrijkBericht() {
        final ActieModel actieModel = creeerActie();
        Assert.assertNull(persoonNationaliteitBericht.getStandaard());
        new NationaliteitVerwerker(
                persoonNationaliteitBericht, nationaliteitHisVolledigImpl, actieModel).verrijkBericht();
        Assert.assertNotNull(persoonNationaliteitBericht.getStandaard());
        Assert.assertEquals(actieModel.getDatumAanvangGeldigheid(),
                            persoonNationaliteitBericht.getStandaard().getDatumAanvangGeldigheid());
        Assert.assertEquals(actieModel.getDatumEindeGeldigheid(),
                            persoonNationaliteitBericht.getStandaard().getDatumEindeGeldigheid());
    }

    @Test
    public void testVerrijkBerichtMetReedsStandaardGroep() {
        final ActieModel actieModel = creeerActie();
        persoonNationaliteitBericht.setStandaard(new PersoonNationaliteitStandaardGroepBericht());
        persoonNationaliteitBericht.getStandaard().setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(20111111));
        persoonNationaliteitBericht.getStandaard().setDatumEindeGeldigheid(new DatumEvtDeelsOnbekendAttribuut(20111212));
        Assert.assertNotNull(persoonNationaliteitBericht.getStandaard());

        new NationaliteitVerwerker(
            persoonNationaliteitBericht, nationaliteitHisVolledigImpl, actieModel).verrijkBericht();
        Assert.assertNotNull(persoonNationaliteitBericht.getStandaard());
        Assert.assertNotEquals(actieModel.getDatumAanvangGeldigheid(),
            persoonNationaliteitBericht.getStandaard().getDatumAanvangGeldigheid());
        Assert.assertNotEquals(actieModel.getDatumEindeGeldigheid(),
            persoonNationaliteitBericht.getStandaard().getDatumEindeGeldigheid());
    }

    @Test
    public void testVerzamelAfleidingsregels() {
        NationaliteitVerwerker verwerker =
                new NationaliteitVerwerker(persoonNationaliteitBericht, nationaliteitHisVolledigImpl, creeerActie());
        verwerker.verzamelAfleidingsregels();
        Assert.assertEquals(4, verwerker.getAfleidingsregels().size());
    }

    /**
     * Creeer een standaard persoon nationaliteit groep bericht.
     *
     * @return het persoon nationaliteit groep bericht
     */
    private PersoonNationaliteitStandaardGroepBericht creeerNationaliteitGroep() {
        PersoonNationaliteitStandaardGroepBericht nationaliteit = new PersoonNationaliteitStandaardGroepBericht();
        nationaliteit.setRedenVerkrijging(new RedenVerkrijgingNLNationaliteitAttribuut(
                new RedenVerkrijgingNLNationaliteit(new RedenVerkrijgingCodeAttribuut((short) 1),
                new OmschrijvingEnumeratiewaardeAttribuut("abc"), null, null)));
        nationaliteit.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(DATUM_AANVANG_GELDIGHEID));

        return nationaliteit;
    }

    /**
     * Creeer een standaard actie.
     *
     * @return het actie model
     */
    private ActieModel creeerActie() {
        return new ActieModel(new SoortActieAttribuut(SoortActie.REGISTRATIE_NATIONALITEIT),
                new AdministratieveHandelingModel(new SoortAdministratieveHandelingAttribuut(
                        SoortAdministratieveHandeling.GEBOORTE_IN_NEDERLAND), null,
                        null, null), null, new DatumEvtDeelsOnbekendAttribuut(DATUM_GEBOORTE), null, DatumTijdAttribuut.nu(), null);
    }
}
