/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.leveringmodel;

import static nl.bzk.brp.domain.element.ElementHelper.getGroepElement;

import java.time.ZonedDateTime;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 */
public class AdministratieveHandelingTest {


    @Test
    public void testEquals() {
        final AdministratieveHandeling ah1 = AdministratieveHandeling.converter().converteer(
                TestVerantwoording.maakAdministratieveHandeling(1, "000001", DatumUtil.nuAlsZonedDateTime(),
                        SoortAdministratieveHandeling.AANGAAN_GEREGISTREERD_PARTNERSCHAP_IN_BUITENLAND).build());
        final AdministratieveHandeling ah2 = AdministratieveHandeling.converter().converteer(
                TestVerantwoording.maakAdministratieveHandeling(1, "000001", DatumUtil.nuAlsZonedDateTime(),
                        SoortAdministratieveHandeling.AANGAAN_GEREGISTREERD_PARTNERSCHAP_IN_BUITENLAND).build());
        final AdministratieveHandeling ah3 = AdministratieveHandeling.converter().converteer(
                TestVerantwoording.maakAdministratieveHandeling(3, "000001", DatumUtil.nuAlsZonedDateTime(),
                        SoortAdministratieveHandeling.AANGAAN_GEREGISTREERD_PARTNERSCHAP_IN_BUITENLAND).build());

        Assert.assertTrue(ah1.equals(ah2));
        Assert.assertTrue(ah2.equals(ah1));
        Assert.assertTrue(ah1.equals(ah1));
        Assert.assertFalse(ah1.equals(ah3));
        Assert.assertFalse(ah1.equals(new Object()));
        Assert.assertFalse(ah1.equals(null));
    }

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
                    .metAttribuut(Element.ADMINISTRATIEVEHANDELING_SOORTNAAM.getId(), SoortAdministratieveHandeling.AANGAAN_GEREGISTREERD_PARTNERSCHAP_IN_BUITENLAND.getId())
                    .metAttribuut(Element.ADMINISTRATIEVEHANDELING_PARTIJCODE.getId(), "000123")
                    .metAttribuut(Element.ADMINISTRATIEVEHANDELING_TIJDSTIPREGISTRATIE.getId(),  tsReg)
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
                            .metAttribuut(Element.ACTIEBRON_RECHTSGRONDCODE.getId(), "21")
                            .metAttribuut(Element.ACTIEBRON_RECHTSGRONDOMSCHRIJVING.getId(), "rechtsgrondomschrijving")
                        .eindeRecord()
                    .eindeGroep()
                    .metObject()
                        .metId(70)
                        .metObjectElement(Element.DOCUMENT.getId())
                        .metGroep()
                            .metGroepElement(Element.DOCUMENT_IDENTITEIT.getId())
                            .metRecord()
                                .metId(34324324)
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

        final AdministratieveHandeling handeling = AdministratieveHandeling.converter().converteer(metaObject);

        //verify handeling
        Assert.assertNotNull(handeling);
        Assert.assertEquals(1L, handeling.getId().longValue());
        Assert.assertEquals(SoortAdministratieveHandeling.AANGAAN_GEREGISTREERD_PARTNERSCHAP_IN_BUITENLAND.getId(), handeling.getSoort().getId());
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
        Assert.assertTrue(20010101 == actie.getDatumOntlening());

        // verify actiebron
        Assert.assertEquals(1, actie.getBronnen().size());
        final Actiebron actiebron = actie.getBronnen().iterator().next();
        Assert.assertEquals(actie, actiebron.getActie());
        Assert.assertEquals(3, actiebron.getId().intValue());
        Assert.assertEquals("21", actiebron.getRechtsgrond());
        Assert.assertEquals("rechtsgrondomschrijving", actiebron.getRechtsgrondomschrijving());

        // verify document
        final Document document = actiebron.getDocument();
        Assert.assertTrue(document != null);
        Assert.assertEquals(34324324, document.getiD());
        Assert.assertEquals("aktenummer", document.getAktenummer());
        Assert.assertEquals("omschrijving", document.getOmschrijving());
        Assert.assertEquals(0, document.getSoort());
        Assert.assertEquals("soortnaam", document.getSoortNaam());
        Assert.assertEquals("000123", document.getPartijCode());
    }

    @Test
    public void testComparator() {
        final ZonedDateTime zdtNu = ZonedDateTime.now();
        final AdministratieveHandeling ahGisteren = AdministratieveHandeling.converter().converteer(
                TestVerantwoording.maakAdministratieveHandeling(1, "000001", zdtNu.minusDays(1L),
                        SoortAdministratieveHandeling.AANGAAN_GEREGISTREERD_PARTNERSCHAP_IN_BUITENLAND).build());
        final AdministratieveHandeling ahVandaag = AdministratieveHandeling.converter().converteer(
                TestVerantwoording.maakAdministratieveHandeling(2, "000001", zdtNu,
                        SoortAdministratieveHandeling.AANGAAN_GEREGISTREERD_PARTNERSCHAP_IN_BUITENLAND).build());
        final AdministratieveHandeling ahMorgen = AdministratieveHandeling.converter().converteer(
                TestVerantwoording.maakAdministratieveHandeling(3, "000001", zdtNu.plusDays(1L),
                        SoortAdministratieveHandeling.AANGAAN_GEREGISTREERD_PARTNERSCHAP_IN_BUITENLAND).build());
        final AdministratieveHandeling ahVandaag2 = AdministratieveHandeling.converter().converteer(
                TestVerantwoording.maakAdministratieveHandeling(4, "000001", zdtNu,
                        SoortAdministratieveHandeling.AANGAAN_GEREGISTREERD_PARTNERSCHAP_IN_BUITENLAND).build());

        Assert.assertEquals(-1, AdministratieveHandeling.comparator().compare(ahGisteren, ahVandaag));
        Assert.assertEquals(1, AdministratieveHandeling.comparator().compare(ahMorgen, ahVandaag));
        Assert.assertEquals(0, AdministratieveHandeling.comparator().compare(ahVandaag, ahVandaag));
        Assert.assertEquals(1, AdministratieveHandeling.comparator().compare(ahVandaag2, ahVandaag));
    }
}
