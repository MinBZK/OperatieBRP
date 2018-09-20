/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.autaut;

import nl.bzk.brp.beheer.webapp.controllers.stamgegevens.autaut.DienstbundelLO3RubriekController.HisDienstbundelLO3RubriekVerwerker;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaAttribuut;
import nl.bzk.brp.model.beheer.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.beheer.autaut.DienstbundelLO3Rubriek;
import nl.bzk.brp.model.beheer.autaut.HisDienstbundelLO3Rubriek;
import nl.bzk.brp.model.beheer.conv.ConversieLO3Rubriek;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class DienstbundelLO3RubriekControllerHistorieVerwerkerTest {

    private final HisDienstbundelLO3RubriekVerwerker subject = new HisDienstbundelLO3RubriekVerwerker();

    @Test
    public void testMaakHis() {
        final DienstbundelLO3Rubriek item = new DienstbundelLO3Rubriek();
        final Leveringsautorisatie abonnement = new Leveringsautorisatie();
        abonnement.setID(Integer.valueOf(2));
        final ConversieLO3Rubriek rubriek = new ConversieLO3Rubriek();
        rubriek.setID(Integer.valueOf(3));
        item.setRubriek(rubriek);

        final HisDienstbundelLO3Rubriek historie = subject.maakHistorie(item);
        Assert.assertEquals(item, historie.getDienstbundelLO3Rubriek());
        Assert.assertNotNull(historie.getTijdstipRegistratie());
    }

    @Test
    public void testMaakHisLeeg() {
        Assert.assertNull(subject.maakHistorie(new DienstbundelLO3Rubriek()));
    }

    @Test
    public void testIsHistorieInhoudelijkGelijk() {
        final HisDienstbundelLO3Rubriek nieuweHistorie = maakHisDienstbundelLO3Rubriek(new JaAttribuut(Ja.J));
        final HisDienstbundelLO3Rubriek actueleRecord = maakHisDienstbundelLO3Rubriek(new JaAttribuut(Ja.J));

        Assert.assertTrue(subject.isHistorieInhoudelijkGelijk(nieuweHistorie, actueleRecord));
        Assert.assertFalse(subject.isHistorieInhoudelijkGelijk(null, actueleRecord));
        Assert.assertTrue(subject.isHistorieInhoudelijkGelijk(null, actueleRecord));
    }

    private HisDienstbundelLO3Rubriek maakHisDienstbundelLO3Rubriek(final JaAttribuut actief) {
        final HisDienstbundelLO3Rubriek result = new HisDienstbundelLO3Rubriek();

        return result;
    }
}
