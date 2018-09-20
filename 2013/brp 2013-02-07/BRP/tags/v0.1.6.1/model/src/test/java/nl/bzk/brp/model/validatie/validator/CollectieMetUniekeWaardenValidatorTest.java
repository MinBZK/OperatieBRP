/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.validatie.validator;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import nl.bzk.brp.model.gedeeld.Nationaliteit;
import nl.bzk.brp.model.logisch.PersoonVoornaam;
import nl.bzk.brp.model.logisch.groep.PersoonNationaliteit;
import org.junit.Test;

public class CollectieMetUniekeWaardenValidatorTest {

    private final CollectieMetUniekeWaardenValidator validator = new CollectieMetUniekeWaardenValidator();

    @Test
    public void test1Waarde() {
        List<Comparable> persoonVoornamen = new ArrayList<Comparable>();
        PersoonVoornaam voornaam = new PersoonVoornaam();
        voornaam.setVolgnummer(1);
        voornaam.setNaam("Piet");
        persoonVoornamen.add(voornaam);
        Assert.assertTrue(validator.isValid(persoonVoornamen, null));
    }

    @Test
    public void testDezelfdeVolgnummers() {
        List<Comparable> persoonVoornamen = new ArrayList<Comparable>();
        PersoonVoornaam voornaam = new PersoonVoornaam();
        voornaam.setVolgnummer(1);
        voornaam.setNaam("Piet");
        persoonVoornamen.add(voornaam);
        PersoonVoornaam voornaam2 = new PersoonVoornaam();
        voornaam2.setVolgnummer(1);
        voornaam2.setNaam("Jan");
        persoonVoornamen.add(voornaam2);
        Assert.assertFalse(validator.isValid(persoonVoornamen, null));
    }

    @Test
    public void testVerschillendeVolgnummers() {
        List<Comparable> persoonVoornamen = new ArrayList<Comparable>();
        PersoonVoornaam voornaam = new PersoonVoornaam();
        voornaam.setVolgnummer(1);
        voornaam.setNaam("Piet");
        persoonVoornamen.add(voornaam);
        PersoonVoornaam voornaam2 = new PersoonVoornaam();
        voornaam2.setVolgnummer(2);
        voornaam2.setNaam("Jan");
        persoonVoornamen.add(voornaam2);
        Assert.assertTrue(validator.isValid(persoonVoornamen, null));
    }

    @Test
    public void testVerschillendeVolgnummersMaarDezelfdeNaam() {
        List<Comparable> persoonVoornamen = new ArrayList<Comparable>();
        PersoonVoornaam voornaam = new PersoonVoornaam();
        voornaam.setVolgnummer(1);
        voornaam.setNaam("Piet");
        persoonVoornamen.add(voornaam);
        PersoonVoornaam voornaam2 = new PersoonVoornaam();
        voornaam2.setVolgnummer(2);
        voornaam2.setNaam("Piet");
        persoonVoornamen.add(voornaam2);
        Assert.assertTrue(validator.isValid(persoonVoornamen, null));
    }

    @Test
    public void testDezelfdeVolgnummersMaarVerschillendeNamen() {
        List<Comparable> persoonVoornamen = new ArrayList<Comparable>();
        PersoonVoornaam voornaam = new PersoonVoornaam();
        voornaam.setVolgnummer(1);
        voornaam.setNaam("Piet");
        persoonVoornamen.add(voornaam);
        PersoonVoornaam voornaam2 = new PersoonVoornaam();
        voornaam2.setVolgnummer(1);
        voornaam2.setNaam("Jan");
        persoonVoornamen.add(voornaam2);
        Assert.assertFalse(validator.isValid(persoonVoornamen, null));
    }

    @Test
    public void testDezelfdeNationaliteiten() {
        List<Comparable> persoonNationalteiten = new ArrayList<Comparable>();

        PersoonNationaliteit persNation1 = new PersoonNationaliteit();
        Nationaliteit nation1 = new Nationaliteit();
        nation1.setCode("NL");
        persNation1.setNationaliteit(nation1);
        persoonNationalteiten.add(persNation1);
        persoonNationalteiten.add(persNation1);

        Assert.assertFalse(validator.isValid(persoonNationalteiten, null));
    }
}
