/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.builders;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Verwerkingssoort;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.MetaGroep;
import nl.bzk.brp.domain.leveringmodel.MetaModel;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.helper.TestDatumUtil;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.maakbericht.algemeen.Berichtgegevens;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtParameters;
import nl.bzk.brp.service.maakbericht.algemeen.MaakBerichtPersoonInformatie;
import nl.bzk.brp.service.maakbericht.bepaling.StatischePersoongegevens;
import org.junit.Assert;
import org.junit.Test;

/**
 * MaterieelMetaRecordComparatorTest.
 */
public class MaterieelMetaRecordComparatorTest {

    @Test
    public void testVergelijkTijdstipregistratie() {
        final MaakBerichtParameters maakBerichtParameters = new MaakBerichtParameters();
        final MaakBerichtPersoonInformatie maakBerichtPersoon = new MaakBerichtPersoonInformatie(SoortSynchronisatie.VOLLEDIG_BERICHT);
        final Berichtgegevens berichtgegevens = new Berichtgegevens(maakBerichtParameters, new Persoonslijst(TestBuilders.LEEG_PERSOON, 0L),
                maakBerichtPersoon, null, new StatischePersoongegevens());

        MetaGroep.Builder groepBuilder = new MetaGroep.Builder(null);
        groepBuilder.metGroepElement(Element.PERSOON_ADRES_STANDAARD.getId());
        final MetaGroep metaGroep = groepBuilder.build(null);

        Actie actieInhoud1 = TestVerantwoording.maakActie(1, TestDatumUtil.gisteren());
        MetaRecord.Builder recordBuilder1 = new MetaRecord.Builder(groepBuilder);
        recordBuilder1.metActieInhoud(actieInhoud1);
        final MetaRecord metaRecord1 = recordBuilder1.build(metaGroep);

        Actie actieInhoud2 = TestVerantwoording.maakActie(2, TestDatumUtil.morgen());
        MetaRecord.Builder recordBuilder2 = new MetaRecord.Builder(groepBuilder);
        recordBuilder2.metActieInhoud(actieInhoud2);
        final MetaRecord metaRecord2 = recordBuilder2.build(metaGroep);

        final Comparator<MetaRecord> metaRecordComparator = BerichtRecordComparatorFactory.maakComparator(berichtgegevens);

        final int compare = metaRecordComparator.compare(metaRecord1, metaRecord2);

        //morgen voor vandaag
        Assert.assertEquals(1, compare);

    }

    @Test
    public void testVergelijkTijdstipVerval() {
        final MaakBerichtParameters maakBerichtParameters = new MaakBerichtParameters();
        final MaakBerichtPersoonInformatie maakBerichtPersoon = new MaakBerichtPersoonInformatie(SoortSynchronisatie.VOLLEDIG_BERICHT);
        final Berichtgegevens berichtgegevens = new Berichtgegevens(maakBerichtParameters, new Persoonslijst(TestBuilders.LEEG_PERSOON, 0L),
                maakBerichtPersoon, null, new StatischePersoongegevens());

        MetaGroep.Builder groepBuilder = new MetaGroep.Builder(null);
        groepBuilder.metGroepElement(Element.PERSOON_ADRES_STANDAARD.getId());
        final MetaGroep metaGroep = groepBuilder.build(null);

        Actie actieInhoud1 = TestVerantwoording.maakActie(1, TestDatumUtil.gisteren());
        Actie actieVerval1 = TestVerantwoording.maakActie(1, TestDatumUtil.morgen());
        MetaRecord.Builder recordBuilder1 = new MetaRecord.Builder(groepBuilder);
        recordBuilder1.metActieInhoud(actieInhoud1);
        recordBuilder1.metActieVerval(actieVerval1);
        final MetaRecord metaRecord1 = recordBuilder1.build(metaGroep);

        Actie actieInhoud2 = TestVerantwoording.maakActie(2, TestDatumUtil.morgen());
        Actie actieVerval2 = TestVerantwoording.maakActie(1, TestDatumUtil.gisteren());
        MetaRecord.Builder recordBuilder2 = new MetaRecord.Builder(groepBuilder);
        recordBuilder2.metActieInhoud(actieInhoud2);
        recordBuilder2.metActieVerval(actieVerval2);
        final MetaRecord metaRecord2 = recordBuilder2.build(metaGroep);

        final Comparator<MetaRecord> metaRecordComparator = BerichtRecordComparatorFactory.maakComparator(berichtgegevens);

        final int compare = metaRecordComparator.compare(metaRecord1, metaRecord2);

        //morgen voor vandaag (eerst verval vergelijken)
        Assert.assertEquals(-1, compare);

    }

    @Test
    public void testVergelijkTijdstipVerval1Gevuld() {
        final MaakBerichtParameters maakBerichtParameters = new MaakBerichtParameters();
        final MaakBerichtPersoonInformatie maakBerichtPersoon = new MaakBerichtPersoonInformatie(SoortSynchronisatie.VOLLEDIG_BERICHT);
        final Berichtgegevens berichtgegevens = new Berichtgegevens(maakBerichtParameters, new Persoonslijst(TestBuilders.LEEG_PERSOON, 0L),
                maakBerichtPersoon, null, new StatischePersoongegevens());

        MetaGroep.Builder groepBuilder = new MetaGroep.Builder(null);
        groepBuilder.metGroepElement(Element.PERSOON_ADRES_STANDAARD.getId());
        final MetaGroep metaGroep = groepBuilder.build(null);

        Actie actieInhoud1 = TestVerantwoording.maakActie(1, TestDatumUtil.gisteren());
        Actie actieVerval1 = TestVerantwoording.maakActie(1, TestDatumUtil.morgen());
        MetaRecord.Builder recordBuilder1 = new MetaRecord.Builder(groepBuilder);
        recordBuilder1.metActieInhoud(actieInhoud1);
        recordBuilder1.metActieVerval(actieVerval1);
        final MetaRecord metaRecord1 = recordBuilder1.build(metaGroep);

        Actie actieInhoud2 = TestVerantwoording.maakActie(2, TestDatumUtil.morgen());
        MetaRecord.Builder recordBuilder2 = new MetaRecord.Builder(groepBuilder);
        recordBuilder2.metActieInhoud(actieInhoud2);
        final MetaRecord metaRecord2 = recordBuilder2.build(metaGroep);

        final Comparator<MetaRecord> metaRecordComparator = BerichtRecordComparatorFactory.maakComparator(berichtgegevens);

        final int compare = metaRecordComparator.compare(metaRecord1, metaRecord2);

        //2 geen verval dus eeuwig dus eerst
        Assert.assertEquals(1, compare);

    }

    @Test
    public void testVergelijkDatumAanvangGeldigheid() {
        final MaakBerichtParameters maakBerichtParameters = new MaakBerichtParameters();
        final MaakBerichtPersoonInformatie maakBerichtPersoon = new MaakBerichtPersoonInformatie(SoortSynchronisatie.VOLLEDIG_BERICHT);
        final Berichtgegevens berichtgegevens = new Berichtgegevens(maakBerichtParameters, new Persoonslijst(TestBuilders.LEEG_PERSOON, 0L),
                maakBerichtPersoon, null, new StatischePersoongegevens());

        MetaGroep.Builder groepBuilder = new MetaGroep.Builder(null);
        groepBuilder.metGroepElement(Element.PERSOON_ADRES_STANDAARD.getId());
        final MetaGroep metaGroep = groepBuilder.build(null);

        Actie actieInhoud1 = TestVerantwoording.maakActie(1, TestDatumUtil.gisteren());
        Actie actieVerval1 = TestVerantwoording.maakActie(1, TestDatumUtil.morgen());
        MetaRecord.Builder recordBuilder1 = new MetaRecord.Builder(groepBuilder);
        recordBuilder1.metActieInhoud(actieInhoud1);
        recordBuilder1.metActieVerval(actieVerval1);
        recordBuilder1.metDatumAanvangGeldigheid(20101010);
        final MetaRecord metaRecord1 = recordBuilder1.build(metaGroep);

        MetaRecord.Builder recordBuilder2 = new MetaRecord.Builder(groepBuilder);
        recordBuilder2.metActieInhoud(actieInhoud1);
        recordBuilder2.metActieVerval(actieVerval1);
        recordBuilder2.metDatumAanvangGeldigheid(20111010);
        final MetaRecord metaRecord2 = recordBuilder2.build(metaGroep);

        final Comparator<MetaRecord> metaRecordComparator = BerichtRecordComparatorFactory.maakComparator(berichtgegevens);

        final int compare = metaRecordComparator.compare(metaRecord1, metaRecord2);

        //dag, record 2 voor record 1
        Assert.assertEquals(1, compare);

    }

    @Test
    public void testVergelijkDatumEindeGeldigheidBeideGevuld() {
        final MaakBerichtParameters maakBerichtParameters = new MaakBerichtParameters();
        final MaakBerichtPersoonInformatie maakBerichtPersoon = new MaakBerichtPersoonInformatie(SoortSynchronisatie.VOLLEDIG_BERICHT);
        final Berichtgegevens berichtgegevens = new Berichtgegevens(maakBerichtParameters, new Persoonslijst(TestBuilders.LEEG_PERSOON, 0L),
                maakBerichtPersoon, null, new StatischePersoongegevens());

        MetaGroep.Builder groepBuilder = new MetaGroep.Builder(null);
        groepBuilder.metGroepElement(Element.PERSOON_ADRES_STANDAARD.getId());
        final MetaGroep metaGroep = groepBuilder.build(null);

        Actie actieInhoud1 = TestVerantwoording.maakActie(1, TestDatumUtil.gisteren());
        Actie actieVerval1 = TestVerantwoording.maakActie(1, TestDatumUtil.morgen());
        MetaRecord.Builder recordBuilder1 = new MetaRecord.Builder(groepBuilder);
        recordBuilder1.metActieInhoud(actieInhoud1);
        recordBuilder1.metActieVerval(actieVerval1);
        recordBuilder1.metDatumAanvangGeldigheid(20101010);
        recordBuilder1.metDatumEindeGeldigheid(20101010);
        final MetaRecord metaRecord1 = recordBuilder1.build(metaGroep);

        MetaRecord.Builder recordBuilder2 = new MetaRecord.Builder(groepBuilder);
        recordBuilder2.metActieInhoud(actieInhoud1);
        recordBuilder2.metActieVerval(actieVerval1);
        recordBuilder2.metDatumAanvangGeldigheid(20101010);
        recordBuilder2.metDatumEindeGeldigheid(20111010);
        final MetaRecord metaRecord2 = recordBuilder2.build(metaGroep);

        final Comparator<MetaRecord> metaRecordComparator = BerichtRecordComparatorFactory.maakComparator(berichtgegevens);

        final int compare = metaRecordComparator.compare(metaRecord1, metaRecord2);

        //dag, record 2 voor record 1
        Assert.assertEquals(1, compare);

    }

    @Test
    public void testVergelijkDatumEindeGeldigheid1Leeg() {
        final MaakBerichtParameters maakBerichtParameters = new MaakBerichtParameters();
        final MaakBerichtPersoonInformatie maakBerichtPersoon = new MaakBerichtPersoonInformatie(SoortSynchronisatie.VOLLEDIG_BERICHT);
        final Berichtgegevens berichtgegevens = new Berichtgegevens(maakBerichtParameters, new Persoonslijst(TestBuilders.LEEG_PERSOON, 0L),
                maakBerichtPersoon, null, new StatischePersoongegevens());

        MetaGroep.Builder groepBuilder = new MetaGroep.Builder(null);
        groepBuilder.metGroepElement(Element.PERSOON_ADRES_STANDAARD.getId());
        final MetaGroep metaGroep = groepBuilder.build(null);

        Actie actieInhoud1 = TestVerantwoording.maakActie(1, TestDatumUtil.gisteren());
        Actie actieVerval1 = TestVerantwoording.maakActie(1, TestDatumUtil.morgen());
        MetaRecord.Builder recordBuilder1 = new MetaRecord.Builder(groepBuilder);
        recordBuilder1.metActieInhoud(actieInhoud1);
        recordBuilder1.metActieVerval(actieVerval1);
        recordBuilder1.metDatumAanvangGeldigheid(20101010);
        final MetaRecord metaRecord1 = recordBuilder1.build(metaGroep);

        MetaRecord.Builder recordBuilder2 = new MetaRecord.Builder(groepBuilder);
        recordBuilder2.metActieInhoud(actieInhoud1);
        recordBuilder2.metActieVerval(actieVerval1);
        recordBuilder2.metDatumAanvangGeldigheid(20101010);
        recordBuilder2.metDatumEindeGeldigheid(20111010);
        final MetaRecord metaRecord2 = recordBuilder2.build(metaGroep);

        final Comparator<MetaRecord> metaRecordComparator = BerichtRecordComparatorFactory.maakComparator(berichtgegevens);

        final int compare = metaRecordComparator.compare(metaRecord1, metaRecord2);

        //dag, record 1 voor record 2, 1 eeuwig
        Assert.assertEquals(-1, compare);

    }

    @Test
    public void testVergelijkVerwerkingsoort() {
        final MaakBerichtParameters maakBerichtParameters = new MaakBerichtParameters();
        final MaakBerichtPersoonInformatie maakBerichtPersoon = new MaakBerichtPersoonInformatie(SoortSynchronisatie.MUTATIE_BERICHT);
        final Berichtgegevens berichtgegevens = new Berichtgegevens(maakBerichtParameters, new Persoonslijst(TestBuilders.LEEG_PERSOON, 0L),
                maakBerichtPersoon, null, new StatischePersoongegevens());

        MetaGroep.Builder groepBuilder = new MetaGroep.Builder(null);
        groepBuilder.metGroepElement(Element.PERSOON_ADRES_STANDAARD.getId());
        final MetaGroep metaGroep = groepBuilder.build(null);

        Actie actieInhoud1 = TestVerantwoording.maakActie(1, TestDatumUtil.gisteren());
        MetaRecord.Builder recordBuilder1 = new MetaRecord.Builder(groepBuilder);
        recordBuilder1.metActieInhoud(actieInhoud1);
        final MetaRecord metaRecord1 = recordBuilder1.build(metaGroep);

        Actie actieInhoud2 = TestVerantwoording.maakActie(1, TestDatumUtil.morgen());
        MetaRecord.Builder recordBuilder2 = new MetaRecord.Builder(groepBuilder);
        recordBuilder2.metActieInhoud(actieInhoud2);
        final MetaRecord metaRecord2 = recordBuilder2.build(metaGroep);

        final Map<MetaModel, Verwerkingssoort> verwerkingssoortMap = new HashMap<>();
        verwerkingssoortMap.put(metaRecord2, Verwerkingssoort.VERWIJDERING);
        verwerkingssoortMap.put(metaRecord1, Verwerkingssoort.TOEVOEGING);

        berichtgegevens.getStatischePersoongegevens().setVerwerkingssoortMap(verwerkingssoortMap);

        final Comparator<MetaRecord> metaRecordComparator = BerichtRecordComparatorFactory.maakComparator(berichtgegevens);

        final int compare = metaRecordComparator.compare(metaRecord1, metaRecord2);
        //verwijdering na toevoeging
        Assert.assertTrue(compare < 0);

        //zet verwerkingsoort gelijk
        verwerkingssoortMap.put(metaRecord2, Verwerkingssoort.TOEVOEGING);
        verwerkingssoortMap.put(metaRecord1, Verwerkingssoort.TOEVOEGING);

        final int compare1 = metaRecordComparator.compare(metaRecord1, metaRecord2);
        //morgen voor vandaag, fallback
        Assert.assertEquals(1, compare1);

        //zet verwerkingsoort op null voor 1
        verwerkingssoortMap.put(metaRecord2, null);
        verwerkingssoortMap.put(metaRecord1, Verwerkingssoort.TOEVOEGING);

        final int compare2 = metaRecordComparator.compare(metaRecord1, metaRecord2);
        //morgen voor vandaag, fallback
        Assert.assertEquals(1, compare2);

    }

}
