/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.samengesteldenaam;

import java.util.SortedSet;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GeslachtsnaamstamAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VolgnummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonSamengesteldeNaamGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonVoornaamHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonSamengesteldeNaamModel;
import nl.bzk.brp.util.hisvolledig.kern.PersoonVoornaamHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SamengesteldeNaamGroepVerwerkerTest {

    private static final Integer DATUM_AANVANG_GELDIGHEID = 20120101;
    private static final Integer DATUM_GEBOORTE = 20120101;
    private static final String GESLACHTSNAAM = "Testersma";

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

        SamengesteldeNaamGroepVerwerker verwerker =
                new SamengesteldeNaamGroepVerwerker(persoonBericht, persoonHisVolledigImpl, actie);
        Assert.assertEquals(Regel.VR00003, verwerker.getRegel());
    }

    @Test
    public void testNeemBerichtDataOverInModel() {
        PersoonSamengesteldeNaamGroepBericht samengesteldeNaam = creeerSamengesteldeNaam(
                new GeslachtsnaamstamAttribuut(GESLACHTSNAAM));
        persoonBericht.setSamengesteldeNaam(samengesteldeNaam);

        ActieModel actie = creeerActie();

        Assert.assertNull(persoonHisVolledigImpl.getPersoonSamengesteldeNaamHistorie().getActueleRecord());

        SamengesteldeNaamGroepVerwerker verwerker =
                new SamengesteldeNaamGroepVerwerker(persoonBericht, persoonHisVolledigImpl, actie);
        verwerker.verrijkBericht();
        verwerker.neemBerichtDataOverInModel();

        Assert.assertNotNull(persoonHisVolledigImpl.getPersoonSamengesteldeNaamHistorie().getActueleRecord());
        Assert.assertEquals(GESLACHTSNAAM,
                            persoonHisVolledigImpl.getPersoonSamengesteldeNaamHistorie().getActueleRecord()
                                    .getGeslachtsnaamstam().getWaarde());
    }

    @Test
    public void testNeemBerichtDataOverInModelIndNamenReeksWasNeeEnWordNee() {
        PersoonSamengesteldeNaamGroepBericht samengesteldeNaam = creeerSamengesteldeNaam(null);
        samengesteldeNaam.setIndicatieNamenreeks(JaNeeAttribuut.NEE);
        samengesteldeNaam.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(20120601));
        persoonBericht.setSamengesteldeNaam(samengesteldeNaam);

        ActieModel actie = creeerActie();

        persoonHisVolledigImpl.getPersoonSamengesteldeNaamHistorie().voegToe(
                new HisPersoonSamengesteldeNaamModel(persoonHisVolledigImpl,
                                                     JaNeeAttribuut.JA,
                                                     JaNeeAttribuut.NEE,
                                                     null, null, null, null, null, null, actie, actie)
        );

        Assert.assertEquals(1, persoonHisVolledigImpl.getPersoonSamengesteldeNaamHistorie().getAantal());

        SamengesteldeNaamGroepVerwerker verwerker =
                new SamengesteldeNaamGroepVerwerker(persoonBericht, persoonHisVolledigImpl, actie);
        verwerker.verrijkBericht();
        verwerker.neemBerichtDataOverInModel();

        //Er moet geen nieuw record zijn toegevoegd.
        Assert.assertEquals(1, persoonHisVolledigImpl.getPersoonSamengesteldeNaamHistorie().getAantal());
    }

    @Test
    public void testVerzamelAfleidingsregels() {
        PersoonSamengesteldeNaamGroepBericht samengesteldeNaam = creeerSamengesteldeNaam(
                new GeslachtsnaamstamAttribuut(GESLACHTSNAAM));
        persoonBericht.setSamengesteldeNaam(samengesteldeNaam);

        ActieModel actie = creeerActie();

        SamengesteldeNaamGroepVerwerker verwerker =
                new SamengesteldeNaamGroepVerwerker(persoonBericht, persoonHisVolledigImpl, actie);
        verwerker.verrijkBericht();
        verwerker.neemBerichtDataOverInModel();

        verwerker.verzamelAfleidingsregels();

        Assert.assertEquals(1, verwerker.getAfleidingsregels().size());
    }

    @Test
    public void testBeeindigVoornamenBijIndicatieNamenreeksJaVoorIngeschrevene() {
        ActieModel actie = creeerActie();
        persoonBericht = bouwPersoonBerichtMetSamengesteldeNaam(JaNeeAttribuut.JA);
        persoonHisVolledigImpl =  bouwPersoonHisVolledigMetEenVoornaam(SoortPersoon.INGESCHREVENE);

        SamengesteldeNaamGroepVerwerker verwerker =
                new SamengesteldeNaamGroepVerwerker(persoonBericht, persoonHisVolledigImpl, actie);
        verwerker.neemBerichtDataOverInModel();

        SortedSet<PersoonVoornaamHisVolledigImpl> voornamen = persoonHisVolledigImpl.getVoornamen();
        for (PersoonVoornaamHisVolledigImpl voornaam : voornamen) {
            Assert.assertNull(voornaam.getPersoonVoornaamHistorie().getActueleRecord());
            Assert.assertEquals(1, voornaam.getPersoonVoornaamHistorie().getVervallenHistorie().size());
        }
    }

    @Test
    public void testBeeindigVoornamenBijIndicatieNamenreeksNeeVoorIngeschrevene() {
        ActieModel actie = creeerActie();
        persoonBericht = bouwPersoonBerichtMetSamengesteldeNaam(JaNeeAttribuut.NEE);
        persoonHisVolledigImpl =  bouwPersoonHisVolledigMetEenVoornaam(SoortPersoon.INGESCHREVENE);

        SamengesteldeNaamGroepVerwerker verwerker =
                new SamengesteldeNaamGroepVerwerker(persoonBericht, persoonHisVolledigImpl, actie);
        verwerker.neemBerichtDataOverInModel();

        SortedSet<PersoonVoornaamHisVolledigImpl> voornamen = persoonHisVolledigImpl.getVoornamen();
        for (PersoonVoornaamHisVolledigImpl voornaam : voornamen) {
            Assert.assertNotNull(voornaam.getPersoonVoornaamHistorie().getActueleRecord());
            Assert.assertEquals(0, voornaam.getPersoonVoornaamHistorie().getVervallenHistorie().size());
        }
    }

    @Test
    public void testBeeindigVoornamenBijIndicatieNamenreeksNeeVoorNietIngeschrevene() {
        ActieModel actie = creeerActie();
        persoonBericht = bouwPersoonBerichtMetSamengesteldeNaam(JaNeeAttribuut.NEE);
        persoonHisVolledigImpl =  bouwPersoonHisVolledigMetEenVoornaam(SoortPersoon.NIET_INGESCHREVENE);

        SamengesteldeNaamGroepVerwerker verwerker =
                new SamengesteldeNaamGroepVerwerker(persoonBericht, persoonHisVolledigImpl, actie);
        verwerker.neemBerichtDataOverInModel();

        SortedSet<PersoonVoornaamHisVolledigImpl> voornamen = persoonHisVolledigImpl.getVoornamen();
        for (PersoonVoornaamHisVolledigImpl voornaam : voornamen) {
            Assert.assertNotNull(voornaam.getPersoonVoornaamHistorie().getActueleRecord());
            Assert.assertEquals(0, voornaam.getPersoonVoornaamHistorie().getVervallenHistorie().size());
        }
    }

    @Test
    public void testBeeindigVoornamenBijIndicatieNamenreeksJaVoorNietIngeschrevene() {
        ActieModel actie = creeerActie();
        persoonBericht = bouwPersoonBerichtMetSamengesteldeNaam(JaNeeAttribuut.JA);
        persoonHisVolledigImpl =  bouwPersoonHisVolledigMetEenVoornaam(SoortPersoon.NIET_INGESCHREVENE);

        SamengesteldeNaamGroepVerwerker verwerker =
                new SamengesteldeNaamGroepVerwerker(persoonBericht, persoonHisVolledigImpl, actie);
        verwerker.neemBerichtDataOverInModel();

        SortedSet<PersoonVoornaamHisVolledigImpl> voornamen = persoonHisVolledigImpl.getVoornamen();
        for (PersoonVoornaamHisVolledigImpl voornaam : voornamen) {
            Assert.assertNotNull(voornaam.getPersoonVoornaamHistorie().getActueleRecord());
            Assert.assertEquals(0, voornaam.getPersoonVoornaamHistorie().getVervallenHistorie().size());
        }
    }


    /**
     * Creeer een standaard persoon samengestelde naam groep bericht.
     *
     * @return het persoon samengestelde namen groep bericht
     */
    private PersoonSamengesteldeNaamGroepBericht creeerSamengesteldeNaam(final GeslachtsnaamstamAttribuut geslachtsnaam) {
        PersoonSamengesteldeNaamGroepBericht samengesteldeNaam = new PersoonSamengesteldeNaamGroepBericht();
        samengesteldeNaam.setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(DATUM_AANVANG_GELDIGHEID));
        samengesteldeNaam.setGeslachtsnaamstam(geslachtsnaam);
        return samengesteldeNaam;
    }

    /**
     * Creeer een standaard persoon samengestelde naam groep bericht met indicatie namenreeks.
     *
     * @return het persoon samengestelde namen groep bericht
     */
    private PersoonSamengesteldeNaamGroepBericht creeerSamengesteldeNaamMetIndicatieNamenreeks(
            final GeslachtsnaamstamAttribuut geslachtsnaam, final JaNeeAttribuut indicatieNamenreeks)
    {
        PersoonSamengesteldeNaamGroepBericht samengesteldeNaam = creeerSamengesteldeNaam(geslachtsnaam);
        samengesteldeNaam.setIndicatieNamenreeks(indicatieNamenreeks);
        return samengesteldeNaam;
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

    private PersoonHisVolledigImpl bouwPersoonHisVolledigMetEenVoornaam(final SoortPersoon soortPersoon) {
        PersoonHisVolledigImpl persoon = new PersoonHisVolledigImpl(new SoortPersoonAttribuut(soortPersoon));
        persoon.getVoornamen().add(new PersoonVoornaamHisVolledigImplBuilder(persoonHisVolledigImpl, new VolgnummerAttribuut(1))
                                                          .nieuwStandaardRecord(DATUM_AANVANG_GELDIGHEID, null,
                                                                                DATUM_AANVANG_GELDIGHEID)
                                                          .naam("Jan")
                                                          .eindeRecord().build());

        return persoon;
    }

    private PersoonBericht bouwPersoonBerichtMetSamengesteldeNaam(final JaNeeAttribuut indicatieNamenreeks) {
        PersoonSamengesteldeNaamGroepBericht samengesteldeNaam = creeerSamengesteldeNaamMetIndicatieNamenreeks(
                new GeslachtsnaamstamAttribuut(GESLACHTSNAAM), indicatieNamenreeks);

        PersoonBericht persoon = new PersoonBericht();
        persoon.setSamengesteldeNaam(samengesteldeNaam);

        return persoon;
    }
}
