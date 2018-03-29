/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.leveringmodel.persoon;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.leveringmodel.MetaGroep;
import nl.bzk.brp.domain.leveringmodel.MetaRecord;
import org.junit.Test;

/**
 * Unit test voor {@link HistorieVanafPredikaat}.
 */
public class HistorieVanafPredikaatTest {

    @Test(expected = GlazenbolException.class)
    public void gooitGlazenBolAlsVanafNaVandaag() throws Exception {
        HistorieVanafPredikaat.geldigOpEnNa(DatumUtil.morgen());
    }

    @Test
    public void geldigOpEnNa() throws Exception {
        final int vandaag = DatumUtil.vandaag();
        final HistorieVanafPredikaat historieVanafPredikaat = HistorieVanafPredikaat.geldigOpEnNa(vandaag);

        assertThat(historieVanafPredikaat.getLeverenVanafMoment(), is(vandaag));
    }

    @Test
    public void applyMaterieelVanafGeldigTrue() throws Exception {
        MetaGroep.Builder groepBuilder = new MetaGroep.Builder(null);
        groepBuilder.metGroepElement(Element.PERSOON_ADRES_STANDAARD.getId());
        final MetaGroep metaGroep = groepBuilder.build(null);
        MetaRecord.Builder recordBuilder = new MetaRecord.Builder(groepBuilder);
        recordBuilder.metDatumEindeGeldigheid(20150101);
        final MetaRecord metaRecord = recordBuilder.build(metaGroep);

        final boolean apply = HistorieVanafPredikaat.geldigOpEnNa(20140101).apply(metaRecord);

        assertThat(apply, is(true));
    }

    @Test
    public void applyMaterieelVanafGeldigFalse() throws Exception {
        MetaGroep.Builder groepBuilder = new MetaGroep.Builder(null);
        groepBuilder.metGroepElement(Element.PERSOON_ADRES_STANDAARD.getId());
        final MetaGroep metaGroep = groepBuilder.build(null);
        MetaRecord.Builder recordBuilder = new MetaRecord.Builder(groepBuilder);
        recordBuilder.metDatumEindeGeldigheid(20130101);
        final MetaRecord metaRecord = recordBuilder.build(metaGroep);

        final boolean apply = HistorieVanafPredikaat.geldigOpEnNa(20140101).apply(metaRecord);

        assertThat(apply, is(false));
    }

    @Test
    public void applyAlsNietMaterieelTrue() throws Exception {
        MetaGroep.Builder groepBuilder = new MetaGroep.Builder(null);
        groepBuilder.metGroepElement(Element.ONDERZOEK_STANDAARD.getId());
        final MetaGroep metaGroep = groepBuilder.build(null);
        MetaRecord.Builder recordBuilder = new MetaRecord.Builder(groepBuilder);
        final MetaRecord metaRecord = recordBuilder.build(metaGroep);

        final boolean apply = HistorieVanafPredikaat.geldigOpEnNa(20140101).apply(metaRecord);

        assertThat(apply, is(true));
    }

}
