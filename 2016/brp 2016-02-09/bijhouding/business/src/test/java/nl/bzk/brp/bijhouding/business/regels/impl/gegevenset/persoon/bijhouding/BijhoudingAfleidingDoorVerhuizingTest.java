/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.bijhouding;

import java.util.List;
import nl.bzk.brp.bijhouding.business.regels.Afleidingsregel;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LandGebiedCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Bijhoudingsaard;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.BijhoudingsaardAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.GemeenteAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebied;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.LandGebiedAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NadereBijhoudingsaard;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NadereBijhoudingsaardAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonAdresHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonAdresModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonBijhoudingModel;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 */
public class BijhoudingAfleidingDoorVerhuizingTest {

    @Test
    public void testGeenVervolgAfleidingen() {
        final PersoonHisVolledigImpl persoon = maakPersoon();
        voegAdresToe(persoon, null, new LandGebiedCodeAttribuut((short) 125));
        final BijhoudingAfleidingDoorVerhuizing afleiding = new BijhoudingAfleidingDoorVerhuizing(persoon, null);
        final List<Afleidingsregel> afleidingsregels = afleiding.leidAf().getVervolgAfleidingen();
        Assert.assertTrue(afleidingsregels.isEmpty());
    }

    @Test
    public void zouCodeTerugMoetenKrijgen() {
        final BijhoudingAfleidingDoorVerhuizing afleiding = new BijhoudingAfleidingDoorVerhuizing(null, null);

        Assert.assertEquals(Regel.VR00015c, afleiding.getRegel());
    }

    @Test
    public void testActueelAdresIsBuitenlandsAdres() {
        PersoonHisVolledigImpl persoon = maakPersoon();
        voegAdresToe(persoon, null, new LandGebiedCodeAttribuut((short) 125));
        new BijhoudingAfleidingDoorVerhuizing(persoon, maakActie(20130101)).leidAf();
        Assert.assertNull(persoon.getPersoonBijhoudingHistorie().getActueleRecord());
    }

    @Test
    public void testActueelAdresIsNederlandsAdresPersoonZonderBijhoudingGroep() {
        PersoonHisVolledigImpl persoon = maakPersoon();
        voegAdresToe(persoon, StatischeObjecttypeBuilder.GEMEENTE_BREDA, LandGebiedCodeAttribuut.NEDERLAND);

        new BijhoudingAfleidingDoorVerhuizing(persoon, maakActie(20130101)).leidAf();

        final HisPersoonAdresModel actueleAdres =
                persoon.getAdressen().iterator().next().getPersoonAdresHistorie().getActueleRecord();
        Assert.assertNotNull(persoon.getPersoonBijhoudingHistorie().getActueleRecord());

        final HisPersoonBijhoudingModel actueleRecord = persoon.getPersoonBijhoudingHistorie().getActueleRecord();
        Assert.assertEquals(Bijhoudingsaard.INGEZETENE, actueleRecord.getBijhoudingsaard().getWaarde());
        Assert.assertEquals(NadereBijhoudingsaard.ACTUEEL, actueleRecord.getNadereBijhoudingsaard().getWaarde());
        Assert.assertEquals(StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA, actueleRecord.getBijhoudingspartij());
        Assert.assertEquals(false, actueleRecord.getIndicatieOnverwerktDocumentAanwezig().getWaarde());
        Assert.assertEquals(actueleAdres.getMaterieleHistorie().getDatumAanvangGeldigheid(),
                            actueleRecord.getMaterieleHistorie().getDatumAanvangGeldigheid());
        Assert.assertEquals(actueleAdres.getMaterieleHistorie().getDatumEindeGeldigheid(),
                            actueleRecord.getMaterieleHistorie().getDatumEindeGeldigheid());
        Assert.assertEquals(actueleAdres.getVerantwoordingInhoud(),
                            actueleRecord.getVerantwoordingInhoud());
    }

    @Test
    public void testActueelAdresIsNederlandsAdresPersoonMetBijhoudingGroepZonderBijhoudingPartij() {
        PersoonHisVolledigImpl persoon = maakPersoon();
        persoon.getPersoonBijhoudingHistorie().voegToe(
                new HisPersoonBijhoudingModel(persoon, null,
                                              new BijhoudingsaardAttribuut(Bijhoudingsaard.DUMMY),
                                              new NadereBijhoudingsaardAttribuut(NadereBijhoudingsaard.DUMMY),
                                              new JaNeeAttribuut(true),
                                              maakActie(20100101),
                                              maakActie(20100101)));
        voegAdresToe(persoon, StatischeObjecttypeBuilder.GEMEENTE_BREDA, LandGebiedCodeAttribuut.NEDERLAND);

        new BijhoudingAfleidingDoorVerhuizing(persoon, maakActie(20130101)).leidAf();

        final HisPersoonAdresModel actueleAdres =
                persoon.getAdressen().iterator().next().getPersoonAdresHistorie().getActueleRecord();
        Assert.assertNotNull(persoon.getPersoonBijhoudingHistorie().getActueleRecord());

        final HisPersoonBijhoudingModel actueleRecord = persoon.getPersoonBijhoudingHistorie().getActueleRecord();

        Assert.assertEquals(Bijhoudingsaard.INGEZETENE, actueleRecord.getBijhoudingsaard().getWaarde());
        Assert.assertEquals(NadereBijhoudingsaard.ACTUEEL, actueleRecord.getNadereBijhoudingsaard().getWaarde());
        Assert.assertEquals(StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA, actueleRecord.getBijhoudingspartij());
        Assert.assertEquals(false, actueleRecord.getIndicatieOnverwerktDocumentAanwezig().getWaarde());
        Assert.assertEquals(actueleAdres.getMaterieleHistorie().getDatumAanvangGeldigheid(),
                            actueleRecord.getMaterieleHistorie().getDatumAanvangGeldigheid());
        Assert.assertEquals(actueleAdres.getMaterieleHistorie().getDatumEindeGeldigheid(),
                            actueleRecord.getMaterieleHistorie().getDatumEindeGeldigheid());
        Assert.assertEquals(actueleAdres.getVerantwoordingInhoud(),
                            actueleRecord.getVerantwoordingInhoud());
    }

    @Test
    public void testActueelAdresIsNederlandsAdresMetGemeenteGelijkAanBijhoudingPartij() {
        PersoonHisVolledigImpl persoon = maakPersoon();
        persoon.getPersoonBijhoudingHistorie().voegToe(
                new HisPersoonBijhoudingModel(persoon,
                                              StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA,
                                              new BijhoudingsaardAttribuut(Bijhoudingsaard.DUMMY),
                                              new NadereBijhoudingsaardAttribuut(NadereBijhoudingsaard.DUMMY),
                                              new JaNeeAttribuut(true),
                                              maakActie(20100101),
                                              maakActie(20100101)));
        voegAdresToe(persoon, StatischeObjecttypeBuilder.GEMEENTE_BREDA, LandGebiedCodeAttribuut.NEDERLAND);

        new BijhoudingAfleidingDoorVerhuizing(persoon, maakActie(20130101)).leidAf();

        Assert.assertEquals(1, persoon.getPersoonBijhoudingHistorie().getAantal());
        Assert.assertNotNull(persoon.getPersoonBijhoudingHistorie().getActueleRecord());

        final HisPersoonBijhoudingModel actueleRecord = persoon.getPersoonBijhoudingHistorie().getActueleRecord();

        Assert.assertEquals(Bijhoudingsaard.DUMMY, actueleRecord.getBijhoudingsaard().getWaarde());
        Assert.assertEquals(NadereBijhoudingsaard.DUMMY, actueleRecord.getNadereBijhoudingsaard().getWaarde());
        Assert.assertEquals(StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA, actueleRecord.getBijhoudingspartij());
        Assert.assertEquals(true, actueleRecord.getIndicatieOnverwerktDocumentAanwezig().getWaarde());
        Assert.assertEquals(actueleRecord.getMaterieleHistorie().getDatumAanvangGeldigheid(),
                            actueleRecord.getMaterieleHistorie().getDatumAanvangGeldigheid());
        Assert.assertEquals(actueleRecord.getMaterieleHistorie().getDatumEindeGeldigheid(),
                            actueleRecord.getMaterieleHistorie().getDatumEindeGeldigheid());
        Assert.assertEquals(actueleRecord.getVerantwoordingInhoud(),
                            actueleRecord.getVerantwoordingInhoud());
    }

    @Test
    public void testActueelAdresIsNederlandsAdresMetGemeenteNietGelijkAanBijhoudingPartij() {
        PersoonHisVolledigImpl persoon = maakPersoon();
        persoon.getPersoonBijhoudingHistorie().voegToe(
                new HisPersoonBijhoudingModel(persoon, StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_AMSTERDAM,
                                              new BijhoudingsaardAttribuut(Bijhoudingsaard.DUMMY),
                                              new NadereBijhoudingsaardAttribuut(NadereBijhoudingsaard.DUMMY),
                                              new JaNeeAttribuut(true),
                                              maakActie(20100101),
                                              maakActie(20100101)));
        voegAdresToe(persoon, StatischeObjecttypeBuilder.GEMEENTE_BREDA, LandGebiedCodeAttribuut.NEDERLAND);

        new BijhoudingAfleidingDoorVerhuizing(persoon, maakActie(20130101)).leidAf();

        final HisPersoonAdresModel actueleAdres =
                persoon.getAdressen().iterator().next().getPersoonAdresHistorie().getActueleRecord();
        Assert.assertNotNull(persoon.getPersoonBijhoudingHistorie().getActueleRecord());

        final HisPersoonBijhoudingModel actueleRecord = persoon.getPersoonBijhoudingHistorie().getActueleRecord();

        Assert.assertEquals(Bijhoudingsaard.INGEZETENE, actueleRecord.getBijhoudingsaard().getWaarde());
        Assert.assertEquals(NadereBijhoudingsaard.ACTUEEL, actueleRecord.getNadereBijhoudingsaard().getWaarde());
        Assert.assertEquals(StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_BREDA, actueleRecord.getBijhoudingspartij());
        Assert.assertEquals(false, actueleRecord.getIndicatieOnverwerktDocumentAanwezig().getWaarde());
        Assert.assertEquals(actueleAdres.getMaterieleHistorie().getDatumAanvangGeldigheid(),
                            actueleRecord.getMaterieleHistorie().getDatumAanvangGeldigheid());
        Assert.assertEquals(actueleAdres.getMaterieleHistorie().getDatumEindeGeldigheid(),
                            actueleRecord.getMaterieleHistorie().getDatumEindeGeldigheid());
        Assert.assertEquals(actueleAdres.getVerantwoordingInhoud(),
                            actueleRecord.getVerantwoordingInhoud());
    }

    private void voegAdresToe(final PersoonHisVolledigImpl persoon, final GemeenteAttribuut gemeente,
                              final LandGebiedCodeAttribuut landGebiedCode)
    {
        final ActieModel actie = maakActie(20100101);
        PersoonAdresHisVolledigImpl adres = new PersoonAdresHisVolledigImpl(persoon);
        final HisPersoonAdresModel actueelRecord =
                new HisPersoonAdresModel(
                        adres,
                        null, null, null, null, null, null,
                        gemeente,
                        null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
                        new LandGebiedAttribuut(new LandGebied(landGebiedCode, null, null, null, null)),
                        null, actie, actie);
        adres.getPersoonAdresHistorie().voegToe(actueelRecord);

        persoon.getAdressen().add(adres);
    }

    private ActieModel maakActie(final int datumAanvGel) {
        return new ActieModel(null, null, null, new DatumEvtDeelsOnbekendAttribuut(datumAanvGel), null,
                              DatumTijdAttribuut.terug24Uur(), null);
    }

    private PersoonHisVolledigImpl maakPersoon() {
        final PersoonHisVolledigImpl persoon = new PersoonHisVolledigImpl(
                new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        return persoon;
    }
}
