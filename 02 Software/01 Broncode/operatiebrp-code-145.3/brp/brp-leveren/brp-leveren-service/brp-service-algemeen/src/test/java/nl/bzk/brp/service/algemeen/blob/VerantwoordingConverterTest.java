/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.blob;

import static nl.bzk.brp.domain.element.ElementHelper.getGroepElement;

import com.google.common.collect.Lists;
import java.time.ZonedDateTime;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.services.blobber.json.BlobRoot;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.Actiebron;
import nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling;
import nl.bzk.brp.domain.leveringmodel.Document;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import org.junit.Assert;
import org.junit.Test;

/**
 */
public class VerantwoordingConverterTest {

    @Test
    public void testConverteer() throws Exception {

        final ZonedDateTime tsReg = DatumUtil.nuAlsZonedDateTime();
        //@formatter:off
        final MetaObject metaObject = MetaObject.maakBuilder()
                .metObjectElement(Element.ADMINISTRATIEVEHANDELING.getId())
                .metId(1)
                .metGroep()
                .metGroepElement(Element.ADMINISTRATIEVEHANDELING_IDENTITEIT.getId())
                .metRecord()
                .metAttribuut(Element.ADMINISTRATIEVEHANDELING_SOORTNAAM.getId(), SoortAdministratieveHandeling.AANVANG_ONDERZOEK.getId())
                .metAttribuut(Element.ADMINISTRATIEVEHANDELING_PARTIJCODE.getId(), "000123")
                .metAttribuut(Element.ADMINISTRATIEVEHANDELING_TIJDSTIPREGISTRATIE.getId(), tsReg)
                .metAttribuut(Element.ADMINISTRATIEVEHANDELING_TOELICHTINGONTLENING.getId(), "toelichting")
                .eindeRecord()
                .eindeGroep()
                .metObject()
                .metObjectElement(Element.ACTIE.getId())
                .metId(2)
                .metGroep()
                .metGroepElement(getGroepElement(Element.ACTIE_IDENTITEIT))
                .metRecord()
                .metAttribuut(Element.ACTIE_SOORTNAAM.getId(), SoortActie.BEEINDIGING_BEHANDELD_ALS_NEDERLANDER.getId())
                .metAttribuut(Element.ACTIE_PARTIJCODE.getId(), "000123")
                .metAttribuut(Element.ACTIE_TIJDSTIPREGISTRATIE.getId(), tsReg)
                .metAttribuut(Element.ACTIE_DATUMONTLENING.getId(), 20010101)
                .eindeRecord()
                .eindeGroep()
                .metObject()
                .metObjectElement(Element.ACTIEBRON.getId())
                .metId(3)
                .metGroep()
                .metGroepElement(Element.ACTIEBRON_IDENTITEIT.getId())
                .metRecord()
                .metAttribuut(Element.ACTIEBRON_RECHTSGRONDOMSCHRIJVING.getId(), "rechtsgrondomschrijving")
                .eindeRecord()
                .eindeGroep()
                .metObject()
                .metId(70)
                .metObjectElement(Element.DOCUMENT.getId())
                .metGroep()
                .metGroepElement(Element.DOCUMENT_IDENTITEIT.getId())
                .metRecord()
                .metAttribuut(Element.DOCUMENT_SOORTNAAM.getId(), "soortnaam")
                .metAttribuut(Element.DOCUMENT_AKTENUMMER.getId(), "aktenummer")
                .metAttribuut(Element.DOCUMENT_OMSCHRIJVING.getId(), "omschrijving")
                .metAttribuut(Element.DOCUMENT_PARTIJCODE.getId(), "000123")
                .eindeRecord()
                .eindeGroep()
                .eindeObject()
                .eindeObject()
                .eindeObject()
                .build();
        //@formatter:on

        final BlobRoot root = BlobConverter.converteer(metaObject);

        // verify handeling
        final Set<AdministratieveHandeling> set = VerantwoordingConverter.map(Lists.newArrayList(root));
        Assert.assertEquals(1, set.size());
        final AdministratieveHandeling handeling = set.iterator().next();
        Assert.assertEquals(1L, handeling.getId().longValue());
        Assert.assertEquals(SoortAdministratieveHandeling.AANVANG_ONDERZOEK.getId(), handeling.getSoort().getId());
        Assert.assertTrue(tsReg.isEqual(handeling.getTijdstipRegistratie()));
        Assert.assertEquals("toelichting", handeling.getToelichtingOntlening());

        // verify actie
        Assert.assertEquals(1, handeling.getActies().size());
        final Actie actie = handeling.getActies().iterator().next();
        Assert.assertEquals(2L, actie.getId().longValue());
        Assert.assertEquals(handeling, actie.getAdministratieveHandeling());
        Assert.assertEquals(SoortActie.BEEINDIGING_BEHANDELD_ALS_NEDERLANDER.getId(), actie.getSoort().getId());
        Assert.assertEquals("000123", actie.getPartijCode());
        Assert.assertTrue(tsReg.isEqual(actie.getTijdstipRegistratie()));
        // Assert.assertEquals(123, actie.getDatumOntlening());

        // verify actiebron
        Assert.assertEquals(1, actie.getBronnen().size());
        final Actiebron actiebron = actie.getBronnen().iterator().next();
        Assert.assertEquals(actie, actiebron.getActie());
        Assert.assertEquals(3, actiebron.getId().intValue());
        Assert.assertEquals("rechtsgrondomschrijving", actiebron.getRechtsgrondomschrijving());

        // verify document
        final Document document = actiebron.getDocument();
        Assert.assertTrue(document != null);
        // Assert.assertEquals(70, document.getiD());
        Assert.assertEquals("aktenummer", document.getAktenummer());
        Assert.assertEquals("omschrijving", document.getOmschrijving());
        // Assert.assertEquals("1", document.getSoort());
        Assert.assertEquals("000123", document.getPartijCode());

    }
}
