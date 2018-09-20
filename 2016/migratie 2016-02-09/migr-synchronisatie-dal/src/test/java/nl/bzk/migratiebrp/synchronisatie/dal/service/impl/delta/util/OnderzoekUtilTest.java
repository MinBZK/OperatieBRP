/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.util;

import java.util.HashSet;
import java.util.Set;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.ActieBron;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.BRPActie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Document;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.DocumentHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Element;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.GegevenInOnderzoek;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Onderzoek;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Partij;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonOnderzoek;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonReisdocument;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PersoonReisdocumentHistorie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortActie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortDocument;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortNederlandsReisdocument;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.AbstractAdresDeltaTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OnderzoekUtilTest extends AbstractAdresDeltaTest {

    public static final String OMSCHRIJVING = "omschrijving";

    @Override @Before public void setUp() {
        super.setUp();
    }

    @Test public void testBevatAlleenActieOnderdelenAlleenActieOnderdelen() {
        final Partij partij = maakPartij();
        final Document document = new Document(new SoortDocument("naam", OMSCHRIJVING));
        final DocumentHistorie documentHistorie = new DocumentHistorie(document, partij);

        final BRPActie actie =
                new BRPActie(SoortActie.CONVERSIE_GBA, maakAdministratieveHandeling(DATUMTIJD_STEMPEL_NIEUW), partij, DATUMTIJD_STEMPEL_NIEUW);
        final ActieBron actieBron = new ActieBron(actie);
        actieBron.setDocument(document);

        final Onderzoek onderzoek = new Onderzoek();
        final GegevenInOnderzoek gegevenActie = new GegevenInOnderzoek(onderzoek, Element.ACTIE_PARTIJCODE);
        gegevenActie.setObject(actie);
        final GegevenInOnderzoek gegevenActiebron = new GegevenInOnderzoek(onderzoek, Element.ACTIEBRON_RECHTSGRONDOMSCHRIJVING);
        gegevenActiebron.setObject(actieBron);

        final GegevenInOnderzoek gegevenDocumentPartij = new GegevenInOnderzoek(onderzoek, Element.DOCUMENT_PARTIJCODE);
        gegevenDocumentPartij.setVoorkomen(documentHistorie);
        final GegevenInOnderzoek gegevenDocumentOmschrijving = new GegevenInOnderzoek(onderzoek, Element.DOCUMENT_OMSCHRIJVING);
        gegevenDocumentOmschrijving.setVoorkomen(documentHistorie);

        onderzoek.addGegevenInOnderzoek(gegevenActie);
        onderzoek.addGegevenInOnderzoek(gegevenActiebron);
        onderzoek.addGegevenInOnderzoek(gegevenDocumentPartij);
        onderzoek.addGegevenInOnderzoek(gegevenDocumentOmschrijving);

        final Set<Onderzoek> onderzoeken = new HashSet<>();
        onderzoeken.add(onderzoek);
        Assert.assertTrue(OnderzoekUtil.bevatAlleenActieOnderdelen(onderzoeken));
    }

    @Test public void testBevatAlleenActieOnderdelenGeenOnderzoeken() {
        final Set<Onderzoek> onderzoeken = new HashSet<>();
        Assert.assertFalse(OnderzoekUtil.bevatAlleenActieOnderdelen(onderzoeken));
    }

    @Test public void testBevatAlleenActieOnderdelenAlleenDocumentatie() {
        final Partij partij = maakPartij();
        final Document document = new Document(new SoortDocument("naam", OMSCHRIJVING));
        final DocumentHistorie documentHistorie = new DocumentHistorie(document, partij);

        final Onderzoek onderzoek = new Onderzoek();

        final GegevenInOnderzoek gegevenDocumentPartij = new GegevenInOnderzoek(onderzoek, Element.DOCUMENT_PARTIJCODE);
        gegevenDocumentPartij.setVoorkomen(documentHistorie);

        final GegevenInOnderzoek gegevenDocumentOmschrijving = new GegevenInOnderzoek(onderzoek, Element.DOCUMENT_OMSCHRIJVING);
        gegevenDocumentOmschrijving.setVoorkomen(documentHistorie);

        onderzoek.addGegevenInOnderzoek(gegevenDocumentPartij);
        onderzoek.addGegevenInOnderzoek(gegevenDocumentOmschrijving);

        final Set<Onderzoek> onderzoeken = new HashSet<>();
        onderzoeken.add(onderzoek);
        Assert.assertTrue(OnderzoekUtil.bevatAlleenActieOnderdelen(onderzoeken));
    }

    @Test public void testBevatAlleenActieOnderdelenMetReisdocument() {
        final BRPActie actie =
                new BRPActie(SoortActie.CONVERSIE_GBA, maakAdministratieveHandeling(DATUMTIJD_STEMPEL_NIEUW), maakPartij(), DATUMTIJD_STEMPEL_NIEUW);
        final PersoonReisdocument reisdoc = new PersoonReisdocument(getDbPersoon(), new SoortNederlandsReisdocument("code", OMSCHRIJVING));
        final PersoonReisdocumentHistorie reisdocHistorie =
                new PersoonReisdocumentHistorie(DATUM_OUD, DATUM_OUD, DATUM_NIEUW, "nummer", "autoriteit", reisdoc);
        final ActieBron actieBron = new ActieBron(actie);

        final Onderzoek onderzoek = new Onderzoek();

        final GegevenInOnderzoek gegevenActie = new GegevenInOnderzoek(onderzoek, Element.ACTIE_PARTIJCODE);
        gegevenActie.setObject(actie);

        final GegevenInOnderzoek gegevenActiebron = new GegevenInOnderzoek(onderzoek, Element.ACTIEBRON_RECHTSGRONDOMSCHRIJVING);
        gegevenActiebron.setObject(actieBron);

        final GegevenInOnderzoek gegevenReisdocument = new GegevenInOnderzoek(onderzoek, Element.PERSOON_REISDOCUMENT_PERSOONREISDOCUMENT);
        gegevenReisdocument.setVoorkomen(reisdocHistorie);

        onderzoek.addGegevenInOnderzoek(gegevenActie);
        onderzoek.addGegevenInOnderzoek(gegevenActiebron);
        onderzoek.addGegevenInOnderzoek(gegevenReisdocument);

        final Set<Onderzoek> onderzoeken = new HashSet<>();
        onderzoeken.add(onderzoek);
        Assert.assertFalse(OnderzoekUtil.bevatAlleenActieOnderdelen(onderzoeken));
    }

    @Test public void testFilterOnderzoek() {
        final Persoon dbPersoon = getDbPersoon();
        final Onderzoek onderzoek1 = new Onderzoek();
        onderzoek1.setOmschrijving("010200");

        final Onderzoek onderzoek2 = new Onderzoek();
        onderzoek2.setOmschrijving("040000");

        final PersoonOnderzoek persoonOnderzoek1 = new PersoonOnderzoek(dbPersoon, onderzoek1);
        final PersoonOnderzoek persoonOnderzoek2 = new PersoonOnderzoek(dbPersoon, onderzoek2);

        final Set<PersoonOnderzoek> persoonOnderzoekSet = new HashSet<>();
        persoonOnderzoekSet.add(persoonOnderzoek1);
        persoonOnderzoekSet.add(persoonOnderzoek2);

        final Set<Onderzoek> onderzoekSet = OnderzoekUtil.transformeerPersoonOnderzoekenNaarOnderzoeken(persoonOnderzoekSet);
        Assert.assertEquals(2, onderzoekSet.size());
        Assert.assertTrue(onderzoekSet.contains(onderzoek1));
        Assert.assertTrue(onderzoekSet.contains(onderzoek2));
    }

    @Test public void testFilterOnderzoekNull() {
        final Set<Onderzoek> onderzoekSet = OnderzoekUtil.transformeerPersoonOnderzoekenNaarOnderzoeken(null);
        Assert.assertEquals(0, onderzoekSet.size());
    }

    @Test public void testIsOnderzoekOpRelatie() {
        final Onderzoek persoonOnderzoek = new Onderzoek();
        persoonOnderzoek.setOmschrijving("010000");
        Assert.assertFalse(OnderzoekUtil.isOnderzoekOpRelatie(persoonOnderzoek));

        final Onderzoek ouder1Onderzoek = new Onderzoek();
        ouder1Onderzoek.setOmschrijving("020000");
        Assert.assertTrue(OnderzoekUtil.isOnderzoekOpRelatie(ouder1Onderzoek));

        final Onderzoek ouder2Onderzoek = new Onderzoek();
        ouder2Onderzoek.setOmschrijving("030000");
        Assert.assertTrue(OnderzoekUtil.isOnderzoekOpRelatie(ouder2Onderzoek));

        final Onderzoek huwelijkOfGpOnderzoek = new Onderzoek();
        huwelijkOfGpOnderzoek.setOmschrijving("050000");
        Assert.assertTrue(OnderzoekUtil.isOnderzoekOpRelatie(huwelijkOfGpOnderzoek));

        final Onderzoek kindOnderzoek = new Onderzoek();
        kindOnderzoek.setOmschrijving("090000");
        Assert.assertTrue(OnderzoekUtil.isOnderzoekOpRelatie(kindOnderzoek));

        final Onderzoek gezagsverhoudingOnderzoek = new Onderzoek();
        gezagsverhoudingOnderzoek.setOmschrijving("110000");
        Assert.assertTrue(OnderzoekUtil.isOnderzoekOpRelatie(gezagsverhoudingOnderzoek));
    }
}
