/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.migratie;

import java.util.List;
import nl.bzk.brp.bijhouding.business.regels.Afleidingsregel;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.adres.AdresGroepAfleidingDoorEmigratie;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.bijhouding.BijhoudingAfleidingDoorEmigratie;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.deelnameeuverkiezingen.DeelnameEUVerkiezingAfleidingDoorEmigratie;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdresregelAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortMigratie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonMigratieGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonMigratieModel;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 */
public class MigratieGroepVerwerkerTest {

    private static final String ADRESREGEL_1 = "adresregel1";
    private static final String ADRESREGEL_2 = "adresregel2";
    private static final String ADRESREGEL_3 = "adresregel3";
    private static final String ADRESREGEL_4 = "adresregel4";
    private static final String ADRESREGEL_5 = "adresregel5";
    private static final String ADRESREGEL_6 = "adresregel6";

    @Test(expected = UnsupportedOperationException.class)
    public void testNeemBerichtDataOverInModelSrtAdmHndIsNietVerhuizingNaarBuitenland() {
        final PersoonBericht persoonBericht = new PersoonBericht();
        final ActieModel actieModel = maakActie(SoortAdministratieveHandeling.ADOPTIE_INGEZETENE);

        new MigratieGroepVerwerker(persoonBericht, null, actieModel).neemBerichtDataOverInModel();
    }

    @Test
    public void testVerrijkBerichtHandelingVerhuizingNaarBuitenland() {
        final PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setMigratie(new PersoonMigratieGroepBericht());
        final ActieModel actieModel = maakActie(SoortAdministratieveHandeling.VERHUIZING_NAAR_BUITENLAND);
        new MigratieGroepVerwerker(persoonBericht, null, actieModel).verrijkBericht();

        Assert.assertEquals(SoortMigratie.EMIGRATIE, persoonBericht.getMigratie().getSoortMigratie().getWaarde());
    }

    @Test
    public void testVerrijkBerichtHandelingVestigingImmigrant() {
        final PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setMigratie(new PersoonMigratieGroepBericht());
        final ActieModel actieModel = maakActie(SoortAdministratieveHandeling.VESTIGING_NIET_INGESCHREVENE);
        new MigratieGroepVerwerker(persoonBericht, null, actieModel).verrijkBericht();

        Assert.assertEquals(SoortMigratie.IMMIGRATIE, persoonBericht.getMigratie().getSoortMigratie().getWaarde());
    }

    @Test(expected = IllegalStateException.class)
    public void testVerrijkBerichtHandelingOnbekend() {
        final ActieModel actieModel = maakActie(SoortAdministratieveHandeling.G_B_A_INITIELE_VULLING);
        new MigratieGroepVerwerker(null, null, actieModel).verrijkBericht();
    }

    @Test
    public void testNeemBerichtDataOverInModel() {
        final PersoonHisVolledigImpl persoonHisVolledig =
                new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                        .nieuwMigratieRecord(20120101, null, 20120101).eindeRecord().build();
        final PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setMigratie(new PersoonMigratieGroepBericht());
        persoonBericht.getMigratie().setDatumAanvangGeldigheid(new DatumEvtDeelsOnbekendAttribuut(20130101));
        persoonBericht.getMigratie().setBuitenlandsAdresRegel1Migratie(new AdresregelAttribuut(ADRESREGEL_1));
        persoonBericht.getMigratie().setBuitenlandsAdresRegel2Migratie(new AdresregelAttribuut(ADRESREGEL_2));
        persoonBericht.getMigratie().setBuitenlandsAdresRegel3Migratie(new AdresregelAttribuut(ADRESREGEL_3));
        persoonBericht.getMigratie().setBuitenlandsAdresRegel4Migratie(new AdresregelAttribuut(ADRESREGEL_4));
        persoonBericht.getMigratie().setBuitenlandsAdresRegel5Migratie(new AdresregelAttribuut(ADRESREGEL_5));
        persoonBericht.getMigratie().setBuitenlandsAdresRegel6Migratie(new AdresregelAttribuut(ADRESREGEL_6));
        persoonBericht.getMigratie().setLandGebiedMigratie(StatischeObjecttypeBuilder.LAND_AFGANISTAN);
        final ActieModel actieModel = maakActie(SoortAdministratieveHandeling.VERHUIZING_NAAR_BUITENLAND);

        new MigratieGroepVerwerker(persoonBericht, persoonHisVolledig, actieModel).neemBerichtDataOverInModel();

        Assert.assertNotNull(persoonHisVolledig.getPersoonMigratieHistorie().getActueleRecord());
        Assert.assertEquals(3, persoonHisVolledig.getPersoonMigratieHistorie().getAantal());

        final HisPersoonMigratieModel nieuwRecord =
                persoonHisVolledig.getPersoonMigratieHistorie().getActueleRecord();
        Assert.assertEquals(SoortMigratie.EMIGRATIE, nieuwRecord.getSoortMigratie().getWaarde());
        Assert.assertEquals(Integer.valueOf(20130101), nieuwRecord.getDatumAanvangGeldigheid().getWaarde());
        Assert.assertEquals(ADRESREGEL_1, nieuwRecord.getBuitenlandsAdresRegel1Migratie().getWaarde());
        Assert.assertEquals(ADRESREGEL_2, nieuwRecord.getBuitenlandsAdresRegel2Migratie().getWaarde());
        Assert.assertEquals(ADRESREGEL_3, nieuwRecord.getBuitenlandsAdresRegel3Migratie().getWaarde());
        Assert.assertEquals(ADRESREGEL_4, nieuwRecord.getBuitenlandsAdresRegel4Migratie().getWaarde());
        Assert.assertEquals(ADRESREGEL_5, nieuwRecord.getBuitenlandsAdresRegel5Migratie().getWaarde());
        Assert.assertEquals(ADRESREGEL_6, nieuwRecord.getBuitenlandsAdresRegel6Migratie().getWaarde());
        Assert.assertEquals(StatischeObjecttypeBuilder.LAND_AFGANISTAN, nieuwRecord.getLandGebiedMigratie());
    }

    @Test
    public void testVerzamelAfleidingsregelsSoortMigratieIsImmigratie() {
        final PersoonHisVolledigImpl persoonHisVolledig =
                new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                        .nieuwMigratieRecord(20120101, null, 20120101)
                        .soortMigratie(SoortMigratie.IMMIGRATIE).eindeRecord().build();
        final PersoonBericht persoonBericht = new PersoonBericht();
        final ActieModel actieModel = maakActie(SoortAdministratieveHandeling.VERHUIZING_NAAR_BUITENLAND);

        final MigratieGroepVerwerker migratieGroepVerwerker =
                new MigratieGroepVerwerker(persoonBericht, persoonHisVolledig, actieModel);
        migratieGroepVerwerker.verzamelAfleidingsregels();

        final List<Afleidingsregel> afleidingsregels = migratieGroepVerwerker.getAfleidingsregels();

        Assert.assertTrue(afleidingsregels.isEmpty());
    }

    @Test
    public void testVerzamelAfleidingsregelsSoortMigratieIsEmmigratie() {
        final PersoonHisVolledigImpl persoonHisVolledig =
                new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                        .nieuwMigratieRecord(20120101, null, 20120101)
                        .soortMigratie(SoortMigratie.EMIGRATIE).eindeRecord().build();
        final PersoonBericht persoonBericht = new PersoonBericht();
        final ActieModel actieModel = maakActie(SoortAdministratieveHandeling.VERHUIZING_NAAR_BUITENLAND);

        final MigratieGroepVerwerker migratieGroepVerwerker =
                new MigratieGroepVerwerker(persoonBericht, persoonHisVolledig, actieModel);
        migratieGroepVerwerker.verzamelAfleidingsregels();

        final List<Afleidingsregel> afleidingsregels = migratieGroepVerwerker.getAfleidingsregels();

        Assert.assertEquals(3, afleidingsregels.size());
        Assert.assertTrue(afleidingsregels.get(0) instanceof BijhoudingAfleidingDoorEmigratie);
        Assert.assertTrue(afleidingsregels.get(1) instanceof AdresGroepAfleidingDoorEmigratie);
        Assert.assertTrue(afleidingsregels.get(2) instanceof DeelnameEUVerkiezingAfleidingDoorEmigratie);
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.VR00028, new MigratieGroepVerwerker(null, null, null).getRegel());
    }

    private ActieModel maakActie(final SoortAdministratieveHandeling srtAdmHnd) {
        return new ActieModel(new SoortActieAttribuut(SoortActie.REGISTRATIE_MIGRATIE),
            new AdministratieveHandelingModel(new SoortAdministratieveHandelingAttribuut(
                srtAdmHnd), null,
                null, null), null, new DatumEvtDeelsOnbekendAttribuut(20130701), null, DatumTijdAttribuut.nu(), null);
    }
}
