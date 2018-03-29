/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.blob;

import java.util.Map;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.NadereAanduidingVerval;
import nl.bzk.algemeenbrp.services.blobber.json.BlobRecord;
import nl.bzk.algemeenbrp.services.blobber.json.BlobRoot;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import org.junit.Assert;
import org.junit.Test;

/**
 */
public class BlobConverterTest {

    @Test
    public void test() {

        final Actie actie1 = TestVerantwoording.maakActie(1);

        //@formatter:off
        final MetaObject persoon = MetaObject.maakBuilder()
            .metObjectElement(Element.PERSOON.getId())
            .metId(999)
            .metObject()
                .metObjectElement(Element.PERSOON_ADRES.getId())
                .metId(20)
                .metGroep()
                    .metGroepElement(Element.PERSOON_ADRES_STANDAARD.getId())
                    .metRecord()
                        .metId(15)
                        .metActieInhoud(actie1)
                        .metActieVerval(actie1)
                        .metActieAanpassingGeldigheid(actie1)
                        .metActieVervalTbvLeveringMutaties(actie1)
                        .metNadereAanduidingVerval(NadereAanduidingVerval.O.getCode())
                        .metIndicatieTbvLeveringMutaties(true)
                        .metDatumAanvangGeldigheid(DatumUtil.gisteren())
                        .metDatumEindeGeldigheid(DatumUtil.morgen())
                        .metAttribuut(Element.PERSOON_ADRES_HUISNUMMER.getId(), "2")
                        .metAttribuut(Element.PERSOON_ADRES_AFGEKORTENAAMOPENBARERUIMTE.getId(), "honk")
                    .eindeRecord()
                .eindeGroep()
            .eindeObject()
        .build();
        //@formatter:on
        final BlobRoot root = BlobConverter.converteer(persoon);
        Assert.assertEquals(1, root.getRecordList().size());
        final BlobRecord blobRecord = root.getRecordList().get(0);
        final Map<Integer, Object> attributen = blobRecord.getAttributen();
        Assert.assertEquals(Element.PERSOON.getId(), blobRecord.getParentObjectElementId().intValue());
        Assert.assertEquals(999, blobRecord.getParentObjectSleutel().intValue());
        Assert.assertEquals(Element.PERSOON_ADRES.getId(), blobRecord.getObjectElementId().intValue());
        Assert.assertEquals(Element.PERSOON_ADRES_STANDAARD.getId(), blobRecord.getGroepElementId().intValue());
        Assert.assertEquals(actie1.getId(), blobRecord.getActieInhoud());
        Assert.assertEquals(actie1.getId(), blobRecord.getActieVerval());
        Assert.assertEquals(actie1.getId(), blobRecord.getActieAanpassingGeldigheid());
        Assert.assertEquals(actie1.getId(), blobRecord.getActieVervalTbvLeveringMutaties());
        Assert.assertEquals(NadereAanduidingVerval.O.name(), blobRecord.getNadereAanduidingVerval());
        Assert.assertTrue((blobRecord.isIndicatieTbvLeveringMutaties()));
        Assert.assertEquals(DatumUtil.gisteren(), blobRecord.getDatumAanvangGeldigheid());
        Assert.assertEquals(DatumUtil.morgen(), blobRecord.getDatumEindeGeldigheid());
        Assert.assertEquals(12, attributen.size());
        Assert.assertTrue(blobRecord.getAttributen().containsKey(Element.PERSOON_ADRES_HUISNUMMER.getId()));
        Assert.assertEquals("2", blobRecord.getAttributen().get(Element.PERSOON_ADRES_HUISNUMMER.getId()));
        Assert.assertTrue(blobRecord.getAttributen().containsKey(Element.PERSOON_ADRES_AFGEKORTENAAMOPENBARERUIMTE.getId()));
        Assert.assertEquals("honk", blobRecord.getAttributen().get(Element.PERSOON_ADRES_AFGEKORTENAAMOPENBARERUIMTE.getId()));
    }
}
