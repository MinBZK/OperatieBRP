/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.service.impl.mapper;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import junit.framework.Assert;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpActie;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpGroep;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpHistorie;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpStapel;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatumTijd;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpPartijCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpSoortActieCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpVerdragCode;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpBelemmeringVerstrekkingReisdocumentIndicatieInhoud;
import nl.moderniseringgba.migratie.conversie.model.brp.groep.BrpDocumentInhoud;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.BRPActie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Document;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.DocumentHistorie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Partij;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.PersoonIndicatieHistorie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.SoortActie;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.SoortDocument;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Verdrag;

import org.junit.Test;

/**
 * Deze test is niet voor de inhoudelijk conversie van de mapper, maar voor het generieke gedeelte waarbij actie en
 * documenten worden gemapt.
 * 
 */
public class BrpMapperTest extends BrpAbstractTest {

    @Inject
    private BrpBelemmeringVerstrekkingReisdocumentIndicatieMapper mapper;

    @Test
    public void testActie() {
        final PersoonIndicatieHistorie historie = new PersoonIndicatieHistorie();
        historie.setWaarde(Boolean.TRUE);
        historie.setActieInhoud(buildBRPActieInhoud());
        historie.setActieVerval(buildBRPActieVerval());
        historie.setActieAanpassingGeldigheid(null);
        historie.setDatumAanvangGeldigheid(new BigDecimal("19940102"));
        historie.setDatumEindeGeldigheid(new BigDecimal("20120403"));
        historie.setDatumTijdRegistratie(timestamp("20110101123403"));
        historie.setDatumTijdVerval(timestamp("20120304183306"));

        final Set<PersoonIndicatieHistorie> historieSet = new HashSet<PersoonIndicatieHistorie>();
        historieSet.add(historie);

        final BrpStapel<BrpBelemmeringVerstrekkingReisdocumentIndicatieInhoud> result = mapper.map(historieSet);

        Assert.assertNotNull(result);
        Assert.assertEquals(1, result.size());
        final BrpGroep<BrpBelemmeringVerstrekkingReisdocumentIndicatieInhoud> groep = result.get(0);

        Assert.assertNotNull(groep);
        assertHistorie(groep.getHistorie());
        assertActieInhoud(groep.getActieInhoud());
        assertActieVerval(groep.getActieVerval());
        Assert.assertNull(groep.getActieGeldigheid());
    }

    private void assertHistorie(final BrpHistorie historie) {
        Assert.assertNotNull(historie);
        Assert.assertEquals(new BrpDatum(19940102), historie.getDatumAanvangGeldigheid());
        Assert.assertEquals(new BrpDatum(20120403), historie.getDatumEindeGeldigheid());
        Assert.assertEquals(BrpDatumTijd.fromDatumTijd(20110101123403L), historie.getDatumTijdRegistratie());
        Assert.assertEquals(BrpDatumTijd.fromDatumTijd(20120304183306L), historie.getDatumTijdVerval());
    }

    private BRPActie buildBRPActieInhoud() {
        // Actie zonder documenten
        final BRPActie actie = new BRPActie();
        actie.setDatumTijdOntlening(timestamp("20090101154510"));
        actie.setDatumTijdRegistratie(timestamp("20050303120000"));
        actie.setId(44885744L);
        actie.setPartij(new Partij());
        actie.getPartij().setGemeentecode(new BigDecimal("0123"));
        actie.getPartij().setNaam("Partij van Alles");
        actie.setSoortActie(SoortActie.CONVERSIE_GBA);
        actie.setVerdrag(new Verdrag());
        actie.getVerdrag().setOmschrijving("Verdrag van Geneve");

        return actie;

    }

    private void assertActieInhoud(final BrpActie actie) {
        Assert.assertNotNull(actie);
        Assert.assertEquals(BrpDatumTijd.fromDatumTijd(20090101154510L), actie.getDatumTijdOntlening());
        Assert.assertEquals(BrpDatumTijd.fromDatumTijd(20050303120000L), actie.getDatumTijdRegistratie());
        Assert.assertEquals(Long.valueOf(44885744L), actie.getId());
        Assert.assertEquals(new BrpPartijCode("Partij van Alles", Integer.valueOf("0123")), actie.getPartijCode());
        Assert.assertEquals(BrpSoortActieCode.CONVERSIE_GBA, actie.getSoortActieCode());
        Assert.assertEquals(new BrpVerdragCode("Verdrag van Geneve"), actie.getVerdragCode());
        Assert.assertNull(actie.getDocumentStapels());
    }

    private BRPActie buildBRPActieVerval() {
        // Actie met documenten
        final BRPActie actie = new BRPActie();
        actie.setDatumTijdOntlening(timestamp("20110603112315"));
        actie.setDatumTijdRegistratie(timestamp("20110603123313"));
        actie.setId(453874L);
        actie.setPartij(new Partij());
        actie.getPartij().setGemeentecode(new BigDecimal("6534"));
        actie.getPartij().setNaam("Partij XYZ");
        actie.setSoortActie(SoortActie.CONVERSIE_GBA);
        actie.setVerdrag(new Verdrag());
        actie.getVerdrag().setOmschrijving("Verdrag van Maastricht");

        actie.addDocument(buildDocument());
        actie.addDocument(buildDocumentExtraInfo());

        return actie;
    }

    private Document buildDocument() {
        final DocumentHistorie historie = new DocumentHistorie();
        historie.setAktenummer("Akte-01");
        historie.setPartij(new Partij());
        historie.getPartij().setGemeentecode(new BigDecimal("0518"));
        historie.getPartij().setNaam("'s-Gravenhage");
        historie.setDatumTijdRegistratie(timestamp("20111212141500"));

        final Document document = new Document();
        document.setId(1L);
        document.setSoortDocument(new SoortDocument());
        document.getSoortDocument().setOmschrijving("Akte");
        document.addDocumentHistorie(historie);

        return document;
    }

    private Document buildDocumentExtraInfo() {
        final DocumentHistorie historie = new DocumentHistorie();
        historie.setOmschrijving("Extra info doc");
        historie.setPartij(new Partij());
        historie.getPartij().setNaam("Migratievoorziening");
        historie.setDatumTijdRegistratie(timestamp("20040303040415"));

        final Document document = new Document();
        document.setId(2L);
        document.setSoortDocument(new SoortDocument());
        document.getSoortDocument().setOmschrijving("Migratievoorziening");
        document.addDocumentHistorie(historie);

        return document;
    }

    private void assertActieVerval(final BrpActie actie) {
        Assert.assertNotNull(actie);
        Assert.assertEquals(BrpDatumTijd.fromDatumTijd(20110603112315L), actie.getDatumTijdOntlening());
        Assert.assertEquals(BrpDatumTijd.fromDatumTijd(20110603123313L), actie.getDatumTijdRegistratie());
        Assert.assertEquals(Long.valueOf(453874L), actie.getId());
        Assert.assertEquals(new BrpPartijCode("Partij XYZ", Integer.valueOf("6534")), actie.getPartijCode());
        Assert.assertEquals(BrpSoortActieCode.CONVERSIE_GBA, actie.getSoortActieCode());
        Assert.assertEquals(new BrpVerdragCode("Verdrag van Maastricht"), actie.getVerdragCode());
        Assert.assertNotNull(actie.getDocumentStapels());
        Assert.assertEquals(2, actie.getDocumentStapels().size());

        assertDocument(actie.getDocumentStapels().get(0));
        assertDocument(actie.getDocumentStapels().get(1));
    }

    private void assertDocument(final BrpStapel<BrpDocumentInhoud> stapel) {
        Assert.assertNotNull(stapel);
        Assert.assertEquals(1, stapel.size());

        final BrpDocumentInhoud inhoud = stapel.get(0).getInhoud();

        if ("Akte-01".equals(inhoud.getAktenummer())) {
            Assert.assertEquals(new Integer("0518"), inhoud.getPartijCode().getGemeenteCode());
            Assert.assertEquals("'s-Gravenhage", inhoud.getPartijCode().getNaam());
            Assert.assertEquals("Akte", inhoud.getSoortDocumentCode().getCode());
        } else if ("Extra info doc".equals(inhoud.getOmschrijving())) {
            Assert.assertNull(inhoud.getPartijCode().getGemeenteCode());
            Assert.assertEquals("Migratievoorziening", inhoud.getPartijCode().getNaam());
            Assert.assertEquals("Migratievoorziening", inhoud.getSoortDocumentCode().getCode());
        } else {
            Assert.fail("Onverwacht document");
        }

    }
}
