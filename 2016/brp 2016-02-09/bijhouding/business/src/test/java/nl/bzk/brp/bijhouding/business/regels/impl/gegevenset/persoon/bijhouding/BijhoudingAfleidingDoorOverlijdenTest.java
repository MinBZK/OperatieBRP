/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.bijhouding;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Bijhoudingsaard;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NadereBijhoudingsaard;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPartij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestPartijBuilder;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Test;

public class BijhoudingAfleidingDoorOverlijdenTest {

    private BijhoudingAfleidingDoorOverlijden bijhoudingAfleidingDoorOverlijden;

    @Test
    public void testGetRegel() {
        bijhoudingAfleidingDoorOverlijden =
                new BijhoudingAfleidingDoorOverlijden(null, null);
        Assert.assertEquals(Regel.VR00015a, bijhoudingAfleidingDoorOverlijden.getRegel());
    }

    @Test
    public void testLeidAfHappyFlow() {
        PersoonHisVolledigImplBuilder builder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE);
        PersoonHisVolledigImpl persoonHisVolledigImpl = builder
                .nieuwBijhoudingRecord(20130626, null, 20130626)
                .nadereBijhoudingsaard(NadereBijhoudingsaard.ACTUEEL)
                .bijhoudingspartij(1234)
                .bijhoudingsaard(Bijhoudingsaard.DUMMY)
                .eindeRecord()
                .build();

        Assert.assertEquals(1, persoonHisVolledigImpl.getPersoonBijhoudingHistorie().getAantal());

        bijhoudingAfleidingDoorOverlijden =
                new BijhoudingAfleidingDoorOverlijden(persoonHisVolledigImpl, maakActie());

        bijhoudingAfleidingDoorOverlijden.leidAf();

        Assert.assertEquals(3, persoonHisVolledigImpl.getPersoonBijhoudingHistorie().getAantal());

        Assert.assertNotNull(persoonHisVolledigImpl.getPersoonBijhoudingHistorie().getActueleRecord());
        Assert.assertEquals(NadereBijhoudingsaard.OVERLEDEN,
                persoonHisVolledigImpl.getPersoonBijhoudingHistorie().getActueleRecord().getNadereBijhoudingsaard().getWaarde());
        Assert.assertEquals(new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.vandaag()),
                persoonHisVolledigImpl.getPersoonBijhoudingHistorie().getActueleRecord().getDatumAanvangGeldigheid());
        Assert.assertEquals(1234,
          persoonHisVolledigImpl.getPersoonBijhoudingHistorie().getActueleRecord().getBijhoudingspartij().getWaarde().getCode().getWaarde().longValue());
        Assert.assertEquals(Bijhoudingsaard.DUMMY, persoonHisVolledigImpl.getPersoonBijhoudingHistorie().getActueleRecord().getBijhoudingsaard().getWaarde());
    }

    private ActieModel maakActie() {
        Partij partij  = TestPartijBuilder.maker()
            .metSoort(SoortPartij.GEMEENTE)
            .metCode(7894).maak();
        return new ActieModel(new SoortActieAttribuut(SoortActie.REGISTRATIE_BIJHOUDING), null,
                              new PartijAttribuut(partij),
                              new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.vandaag()), null, DatumTijdAttribuut.nu(), null);
    }
}
