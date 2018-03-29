/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.util;

import java.util.HashSet;
import java.util.Set;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.ActieBron;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Document;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.GegevenInOnderzoek;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Onderzoek;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonReisdocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonReisdocumentHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortDocument;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SoortNederlandsReisdocument;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.AbstractAdresDeltaTest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OnderzoekUtilTest extends AbstractAdresDeltaTest {

    private static final String OMSCHRIJVING = "omschrijving";

    @Override
    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void testBevatAlleenActieOnderdelenAlleenActieOnderdelen() {
        final Partij partij = maakPartij();
        final Document document = new Document(new SoortDocument("naam", OMSCHRIJVING), partij);

        final BRPActie actie =
                new BRPActie(SoortActie.CONVERSIE_GBA, maakAdministratieveHandeling(DATUMTIJD_STEMPEL_NIEUW), partij, DATUMTIJD_STEMPEL_NIEUW);
        final ActieBron actieBron = new ActieBron(actie);
        actieBron.setDocument(document);

        final Onderzoek onderzoek = new Onderzoek(partij, maakPersoon(true));
        final GegevenInOnderzoek gegevenActie = new GegevenInOnderzoek(onderzoek, Element.ACTIE_PARTIJCODE);
        gegevenActie.setEntiteitOfVoorkomen(actie);
        final GegevenInOnderzoek gegevenActiebron = new GegevenInOnderzoek(onderzoek, Element.ACTIEBRON_RECHTSGRONDOMSCHRIJVING);
        gegevenActiebron.setEntiteitOfVoorkomen(actieBron);

        final GegevenInOnderzoek gegevenDocumentPartij = new GegevenInOnderzoek(onderzoek, Element.DOCUMENT_PARTIJCODE);
        gegevenDocumentPartij.setEntiteitOfVoorkomen(document);
        final GegevenInOnderzoek gegevenDocumentOmschrijving = new GegevenInOnderzoek(onderzoek, Element.DOCUMENT_OMSCHRIJVING);
        gegevenDocumentOmschrijving.setEntiteitOfVoorkomen(document);

        onderzoek.addGegevenInOnderzoek(gegevenActie);
        onderzoek.addGegevenInOnderzoek(gegevenActiebron);
        onderzoek.addGegevenInOnderzoek(gegevenDocumentPartij);
        onderzoek.addGegevenInOnderzoek(gegevenDocumentOmschrijving);

        final Set<Onderzoek> onderzoeken = new HashSet<>();
        onderzoeken.add(onderzoek);
        Assert.assertTrue(OnderzoekUtil.bevatAlleenActieOnderdelen(onderzoeken));
    }

    @Test
    public void testBevatAlleenActieOnderdelenGeenOnderzoeken() {
        final Set<Onderzoek> onderzoeken = new HashSet<>();
        Assert.assertFalse(OnderzoekUtil.bevatAlleenActieOnderdelen(onderzoeken));
    }

    @Test
    public void testBevatAlleenActieOnderdelenAlleenDocumentatie() {
        final Partij partij = maakPartij();
        final Document document = new Document(new SoortDocument("naam", OMSCHRIJVING), partij);

        final Onderzoek onderzoek = new Onderzoek(partij, maakPersoon(true));

        final GegevenInOnderzoek gegevenDocumentPartij = new GegevenInOnderzoek(onderzoek, Element.DOCUMENT_PARTIJCODE);
        gegevenDocumentPartij.setEntiteitOfVoorkomen(document);

        final GegevenInOnderzoek gegevenDocumentOmschrijving = new GegevenInOnderzoek(onderzoek, Element.DOCUMENT_OMSCHRIJVING);
        gegevenDocumentOmschrijving.setEntiteitOfVoorkomen(document);

        onderzoek.addGegevenInOnderzoek(gegevenDocumentPartij);
        onderzoek.addGegevenInOnderzoek(gegevenDocumentOmschrijving);

        final Set<Onderzoek> onderzoeken = new HashSet<>();
        onderzoeken.add(onderzoek);
        Assert.assertTrue(OnderzoekUtil.bevatAlleenActieOnderdelen(onderzoeken));
    }

    @Test
    public void testBevatAlleenActieOnderdelenMetReisdocument() {
        final BRPActie actie =
                new BRPActie(SoortActie.CONVERSIE_GBA, maakAdministratieveHandeling(DATUMTIJD_STEMPEL_NIEUW), maakPartij(), DATUMTIJD_STEMPEL_NIEUW);
        final PersoonReisdocument reisdoc = new PersoonReisdocument(getDbPersoon(), new SoortNederlandsReisdocument("code", OMSCHRIJVING));
        final PersoonReisdocumentHistorie reisdocHistorie =
                new PersoonReisdocumentHistorie(DATUM_OUD, DATUM_OUD, DATUM_NIEUW, "nummer", "autoriteit", reisdoc);
        final ActieBron actieBron = new ActieBron(actie);

        final Onderzoek onderzoek = new Onderzoek(maakPartij(), maakPersoon(true));

        final GegevenInOnderzoek gegevenActie = new GegevenInOnderzoek(onderzoek, Element.ACTIE_PARTIJCODE);
        gegevenActie.setEntiteitOfVoorkomen(actie);

        final GegevenInOnderzoek gegevenActiebron = new GegevenInOnderzoek(onderzoek, Element.ACTIEBRON_RECHTSGRONDOMSCHRIJVING);
        gegevenActiebron.setEntiteitOfVoorkomen(actieBron);

        final GegevenInOnderzoek gegevenReisdocument = new GegevenInOnderzoek(onderzoek, Element.PERSOON_REISDOCUMENT_PERSOONREISDOCUMENT);
        gegevenReisdocument.setEntiteitOfVoorkomen(reisdocHistorie);

        onderzoek.addGegevenInOnderzoek(gegevenActie);
        onderzoek.addGegevenInOnderzoek(gegevenActiebron);
        onderzoek.addGegevenInOnderzoek(gegevenReisdocument);

        final Set<Onderzoek> onderzoeken = new HashSet<>();
        onderzoeken.add(onderzoek);
        Assert.assertFalse(OnderzoekUtil.bevatAlleenActieOnderdelen(onderzoeken));
    }

    @Test
    public void testIsOnderzoekOpRelatie() {
        final Partij partij = maakPartij();
        final Persoon persoon = maakPersoon(true);

        final Onderzoek persoonOnderzoek = new Onderzoek(partij, persoon);
        persoonOnderzoek.setOmschrijving("010000");
        Assert.assertFalse(OnderzoekUtil.isOnderzoekOpRelatie(persoonOnderzoek));

        final Onderzoek ouder1Onderzoek = new Onderzoek(partij, persoon);
        ouder1Onderzoek.setOmschrijving("020000");
        Assert.assertTrue(OnderzoekUtil.isOnderzoekOpRelatie(ouder1Onderzoek));

        final Onderzoek ouder2Onderzoek = new Onderzoek(partij, persoon);
        ouder2Onderzoek.setOmschrijving("030000");
        Assert.assertTrue(OnderzoekUtil.isOnderzoekOpRelatie(ouder2Onderzoek));

        final Onderzoek huwelijkOfGpOnderzoek = new Onderzoek(partij, persoon);
        huwelijkOfGpOnderzoek.setOmschrijving("050000");
        Assert.assertTrue(OnderzoekUtil.isOnderzoekOpRelatie(huwelijkOfGpOnderzoek));

        final Onderzoek kindOnderzoek = new Onderzoek(partij, persoon);
        kindOnderzoek.setOmschrijving("090000");
        Assert.assertTrue(OnderzoekUtil.isOnderzoekOpRelatie(kindOnderzoek));

        final Onderzoek gezagsverhoudingOnderzoek = new Onderzoek(partij, persoon);
        gezagsverhoudingOnderzoek.setOmschrijving("110000");
        Assert.assertTrue(OnderzoekUtil.isOnderzoekOpRelatie(gezagsverhoudingOnderzoek));
    }
}
