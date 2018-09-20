/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.adres;

import java.util.List;
import nl.bzk.brp.bijhouding.business.regels.Afleidingsregel;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonAdresHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonAdresModel;
import nl.bzk.brp.util.hisvolledig.kern.PersoonAdresHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

/**
 *
 */
public class AdresGroepAfleidingDoorEmigratieTest {

    @Test
    public void testLeidAfGeenActueleAdres() {
        //Let op migratie aanvang geldigheid moet verschillen van adres aanvang geldigheid!
        final PersoonHisVolledigImpl persoonHisVolledig =
                new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                        .nieuwMigratieRecord(20140101, null, 20120101).eindeRecord().build();

        final PersoonAdresHisVolledigImpl adres = new PersoonAdresHisVolledigImplBuilder(persoonHisVolledig)
                .nieuwStandaardRecord(20120101, 20130101, 20120101).eindeRecord().build();
        persoonHisVolledig.getAdressen().add(adres);

        new AdresGroepAfleidingDoorEmigratie(persoonHisVolledig, maakActie(20140101)).leidAf();

        Assert.assertEquals(1, adres.getPersoonAdresHistorie().getAantal());
        final HisPersoonAdresModel next = adres.getPersoonAdresHistorie().getNietVervallenHistorie().iterator().next();
        Assert.assertEquals(20120101, next.getDatumAanvangGeldigheid().getWaarde().intValue());
        Assert.assertEquals(20130101, next.getDatumEindeGeldigheid().getWaarde().intValue());
    }

    @Test
    public void testLeidAf() {
        final ActieModel actieInhoudMigratie = maakActie(20130101);
        final PersoonHisVolledigImpl persoonHisVolledig =
                new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                        .nieuwMigratieRecord(actieInhoudMigratie).eindeRecord().build();

        final PersoonAdresHisVolledigImpl adres = new PersoonAdresHisVolledigImplBuilder(persoonHisVolledig)
                .nieuwStandaardRecord(20120101, null, 20120101).eindeRecord().build();
        persoonHisVolledig.getAdressen().add(adres);

        final int oudeAantal = adres.getPersoonAdresHistorie().getAantal();

        final List<Afleidingsregel> afleidingsregels =
                new AdresGroepAfleidingDoorEmigratie(persoonHisVolledig, maakActie(20140101)).leidAf().getVervolgAfleidingen();

        Assert.assertNull(adres.getPersoonAdresHistorie().getActueleRecord());
        final HisPersoonAdresModel adresRecord = adres.getPersoonAdresHistorie().getHistorieRecord(
                new DatumAttribuut(20120101), DatumTijdAttribuut.nu());
        Assert.assertEquals(20130101, adresRecord.getDatumEindeGeldigheid().getWaarde().intValue());
        Assert.assertEquals(20120101, adresRecord.getDatumAanvangGeldigheid().getWaarde().intValue());
        Assert.assertEquals(actieInhoudMigratie, adresRecord.getVerantwoordingAanpassingGeldigheid());

        Assert.assertEquals(oudeAantal + 1, adres.getPersoonAdresHistorie().getAantal());
        Assert.assertTrue(afleidingsregels.isEmpty());
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.VR00013b, new AdresGroepAfleidingDoorEmigratie(null, null).getRegel());
    }

    private ActieModel maakActie(final int datumAanvang) {
        final ActieModel actieModel = new ActieModel(new SoortActieAttribuut(SoortActie.REGISTRATIE_MIGRATIE),
                                                     new AdministratieveHandelingModel(
                                                             new SoortAdministratieveHandelingAttribuut(
                                                                     SoortAdministratieveHandeling.VERHUIZING_NAAR_BUITENLAND),
                                                             null,
                                                             null, null), null, new DatumEvtDeelsOnbekendAttribuut(20130701), null,
                                                      DatumTijdAttribuut.nu(), null);
        ReflectionTestUtils.setField(actieModel, "datumAanvangGeldigheid", new DatumEvtDeelsOnbekendAttribuut(datumAanvang));
        return actieModel;
    }
}
