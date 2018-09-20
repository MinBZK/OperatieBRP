/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.bijhouding;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Bijhoudingsaard;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.BijhoudingsaardAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NadereBijhoudingsaard;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NadereBijhoudingsaardAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBijhoudingGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonBijhoudingModel;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import org.junit.Assert;
import org.junit.Test;

public class BijhoudingGroepVerwerkerTest {

    private static final DatumEvtDeelsOnbekendAttribuut DATUM_AANVANG_GELDIGHEID = new DatumEvtDeelsOnbekendAttribuut(20120101);

    @Test
    public void testGetRegel() {
        BijhoudingGroepVerwerker verwerker =
                new BijhoudingGroepVerwerker(null, null, null);
        Assert.assertEquals(Regel.VR00015, verwerker.getRegel());
    }

    @Test
    public void testNeemBerichtDataOverInModel() {
        final PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setBijhouding(new PersoonBijhoudingGroepBericht());
        persoonBericht.getBijhouding().setDatumTijdRegistratie(DatumTijdAttribuut.bouwDatumTijd(2010, 1, 1));
        persoonBericht.getBijhouding().setDatumAanvangGeldigheid(DATUM_AANVANG_GELDIGHEID);
        persoonBericht.getBijhouding().setBijhoudingsaard(new BijhoudingsaardAttribuut(Bijhoudingsaard.NIET_INGEZETENE));
        persoonBericht.getBijhouding().setNadereBijhoudingsaard(new NadereBijhoudingsaardAttribuut(
                NadereBijhoudingsaard.EMIGRATIE));
        persoonBericht.getBijhouding().setBijhoudingspartij(StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_AMSTERDAM);
        persoonBericht.getBijhouding().setIndicatieOnverwerktDocumentAanwezig(new JaNeeAttribuut(true));

        final PersoonHisVolledigImpl persoon = new PersoonHisVolledigImpl(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        new BijhoudingGroepVerwerker(persoonBericht, persoon,
                                     new ActieModel(null, null, null, DATUM_AANVANG_GELDIGHEID, null,
                                                    DatumTijdAttribuut.bouwDatumTijd(2010, 1, 1), null)).neemBerichtDataOverInModel();

        Assert.assertNotNull(persoon.getPersoonBijhoudingHistorie().getActueleRecord());

        final HisPersoonBijhoudingModel actueleRecord = persoon.getPersoonBijhoudingHistorie().getActueleRecord();

        Assert.assertEquals(Bijhoudingsaard.NIET_INGEZETENE, actueleRecord.getBijhoudingsaard().getWaarde());
        Assert.assertEquals(NadereBijhoudingsaard.EMIGRATIE, actueleRecord.getNadereBijhoudingsaard().getWaarde());
        Assert.assertEquals(StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_AMSTERDAM, actueleRecord.getBijhoudingspartij());
        Assert.assertEquals(true, actueleRecord.getIndicatieOnverwerktDocumentAanwezig().getWaarde());
        Assert.assertEquals(DATUM_AANVANG_GELDIGHEID,
                            actueleRecord.getMaterieleHistorie().getDatumAanvangGeldigheid());
        Assert.assertNull(actueleRecord.getMaterieleHistorie().getDatumEindeGeldigheid());
    }

    @Test
    public void testVerzamelAfleidingsregels() {
        final BijhoudingGroepVerwerker bijhoudingGroepVerwerker = new BijhoudingGroepVerwerker(null, null, null);
        bijhoudingGroepVerwerker.verzamelAfleidingsregels();

        Assert.assertTrue(bijhoudingGroepVerwerker.getAfleidingsregels().isEmpty());
    }
}
