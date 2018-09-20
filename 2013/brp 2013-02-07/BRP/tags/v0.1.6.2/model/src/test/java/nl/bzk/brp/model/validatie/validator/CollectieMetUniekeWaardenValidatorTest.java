/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.validatie.validator;

import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;
import nl.bzk.brp.model.attribuuttype.NationaliteitCode;
import nl.bzk.brp.model.attribuuttype.Volgnummer;
import nl.bzk.brp.model.attribuuttype.Voornaam;
import nl.bzk.brp.model.groep.bericht.PersoonVoornaamStandaardGroepBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonNationaliteitBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonVoornaamBericht;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Nationaliteit;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class CollectieMetUniekeWaardenValidatorTest {

    private final CollectieMetUniekeWaardenValidator validator = new CollectieMetUniekeWaardenValidator();

    @Test
    public void test1Waarde() {
        Set<PersoonVoornaamBericht> persoonVoornamen = new HashSet<PersoonVoornaamBericht>();
        PersoonVoornaamBericht voornaam = new PersoonVoornaamBericht();
        voornaam.setVolgnummer(new Volgnummer(1));
        PersoonVoornaamStandaardGroepBericht standaardGroep = new PersoonVoornaamStandaardGroepBericht();
        standaardGroep.setVoornaam(new Voornaam("Piet"));
        voornaam.setGegevens(standaardGroep);
        persoonVoornamen.add(voornaam);
        Assert.assertTrue(validator.isValid(persoonVoornamen, null));
    }

    @Test
    @Ignore
    public void testDezelfdeVolgnummers() {
        Set<PersoonVoornaamBericht> persoonVoornamen = new HashSet<PersoonVoornaamBericht>();

        PersoonVoornaamBericht voornaam = new PersoonVoornaamBericht();
        voornaam.setVolgnummer(new Volgnummer(1));
        PersoonVoornaamStandaardGroepBericht standaardGroep = new PersoonVoornaamStandaardGroepBericht();
        standaardGroep.setVoornaam(new Voornaam("Piet"));
        voornaam.setGegevens(standaardGroep);
        persoonVoornamen.add(voornaam);

        PersoonVoornaamBericht voornaam2 = new PersoonVoornaamBericht();
        voornaam2.setVolgnummer(new Volgnummer(1));
        PersoonVoornaamStandaardGroepBericht standaardGroep2 = new PersoonVoornaamStandaardGroepBericht();
        standaardGroep2.setVoornaam(new Voornaam("Jan"));
        voornaam2.setGegevens(standaardGroep2);
        persoonVoornamen.add(voornaam2);

        Assert.assertFalse(validator.isValid(persoonVoornamen, null));
    }

    @Test
    public void testVerschillendeVolgnummers() {
        Set<PersoonVoornaamBericht> persoonVoornamen = new HashSet<PersoonVoornaamBericht>();

        PersoonVoornaamBericht voornaam = new PersoonVoornaamBericht();
        voornaam.setVolgnummer(new Volgnummer(1));
        PersoonVoornaamStandaardGroepBericht standaardGroep = new PersoonVoornaamStandaardGroepBericht();
        standaardGroep.setVoornaam(new Voornaam("Piet"));
        voornaam.setGegevens(standaardGroep);
        persoonVoornamen.add(voornaam);

        PersoonVoornaamBericht voornaam2 = new PersoonVoornaamBericht();
        voornaam2.setVolgnummer(new Volgnummer(2));
        PersoonVoornaamStandaardGroepBericht standaardGroep2 = new PersoonVoornaamStandaardGroepBericht();
        standaardGroep2.setVoornaam(new Voornaam("Jan"));
        voornaam2.setGegevens(standaardGroep2);
        persoonVoornamen.add(voornaam2);
        Assert.assertTrue(validator.isValid(persoonVoornamen, null));
    }

    @Test
    public void testVerschillendeVolgnummersMaarDezelfdeNaam() {
        Set<PersoonVoornaamBericht> persoonVoornamen = new HashSet<PersoonVoornaamBericht>();

        PersoonVoornaamBericht voornaam = new PersoonVoornaamBericht();
        voornaam.setVolgnummer(new Volgnummer(1));
        PersoonVoornaamStandaardGroepBericht standaardGroep = new PersoonVoornaamStandaardGroepBericht();
        standaardGroep.setVoornaam(new Voornaam("Piet"));
        voornaam.setGegevens(standaardGroep);
        persoonVoornamen.add(voornaam);

        PersoonVoornaamBericht voornaam2 = new PersoonVoornaamBericht();
        voornaam2.setVolgnummer(new Volgnummer(2));
        PersoonVoornaamStandaardGroepBericht standaardGroep2 = new PersoonVoornaamStandaardGroepBericht();
        standaardGroep2.setVoornaam(new Voornaam("Piet"));
        voornaam2.setGegevens(standaardGroep2);
        persoonVoornamen.add(voornaam2);
        Assert.assertTrue(validator.isValid(persoonVoornamen, null));
    }

    @Test
    @Ignore
    public void testDezelfdeVolgnummersMaarVerschillendeNamen() {
        Set<PersoonVoornaamBericht> persoonVoornamen = new HashSet<PersoonVoornaamBericht>();

        PersoonVoornaamBericht voornaam = new PersoonVoornaamBericht();
        voornaam.setVolgnummer(new Volgnummer(1));
        PersoonVoornaamStandaardGroepBericht standaardGroep = new PersoonVoornaamStandaardGroepBericht();
        standaardGroep.setVoornaam(new Voornaam("Piet"));
        voornaam.setGegevens(standaardGroep);
        persoonVoornamen.add(voornaam);

        PersoonVoornaamBericht voornaam2 = new PersoonVoornaamBericht();
        voornaam2.setVolgnummer(new Volgnummer(1));
        PersoonVoornaamStandaardGroepBericht standaardGroep2 = new PersoonVoornaamStandaardGroepBericht();
        standaardGroep2.setVoornaam(new Voornaam("Jan"));
        voornaam2.setGegevens(standaardGroep2);
        persoonVoornamen.add(voornaam2);

        Assert.assertFalse(validator.isValid(persoonVoornamen, null));
    }

    @Test
    @Ignore
    public void testDezelfdeNationaliteiten() {
        Set<PersoonNationaliteitBericht> persoonNationalteiten = new HashSet<PersoonNationaliteitBericht>();

        PersoonNationaliteitBericht persNation1 = new PersoonNationaliteitBericht();
        Nationaliteit nation1 = new Nationaliteit();
        ReflectionTestUtils.setField(nation1, "nationaliteitCode", new NationaliteitCode("NL"));
        persNation1.setNationaliteit(nation1);

        PersoonNationaliteitBericht persNation2 = new PersoonNationaliteitBericht();
        Nationaliteit nation2 = new Nationaliteit();
        ReflectionTestUtils.setField(nation2, "nationaliteitCode", new NationaliteitCode("NL"));
        persNation2.setNationaliteit(nation2);

        //2x toevoegen!
        persoonNationalteiten.add(persNation1);
        persoonNationalteiten.add(persNation2);

        Assert.assertFalse(validator.isValid(persoonNationalteiten, null));
    }
}
