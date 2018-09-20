/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.validatie.validator;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.VolgnummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoornaamAttribuut;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonNationaliteitBericht;
import nl.bzk.brp.model.bericht.kern.PersoonVoornaamBericht;
import nl.bzk.brp.model.bericht.kern.PersoonVoornaamStandaardGroepBericht;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;

import org.junit.Assert;
import org.junit.Test;


public class CollectieMetUniekeWaardenValidatorTest {

    private final CollectieMetUniekeWaardenValidator validator = new CollectieMetUniekeWaardenValidator();

    @Test
    public void test1Waarde() {
        List<PersoonVoornaamBericht> persoonVoornamen = new ArrayList<PersoonVoornaamBericht>();
        PersoonVoornaamBericht voornaam = new PersoonVoornaamBericht();
        voornaam.setVolgnummer(new VolgnummerAttribuut(1));
        PersoonVoornaamStandaardGroepBericht standaardGroep = new PersoonVoornaamStandaardGroepBericht();
        standaardGroep.setNaam(new VoornaamAttribuut("Klaas"));
        voornaam.setStandaard(standaardGroep);
        persoonVoornamen.add(voornaam);
        Assert.assertTrue(validator.isValid(persoonVoornamen, null));
    }

    @Test
    public void testDezelfdeVolgnummers() {
        List<PersoonVoornaamBericht> persoonVoornamen = new ArrayList<PersoonVoornaamBericht>();

        PersoonVoornaamBericht voornaam = new PersoonVoornaamBericht();
        voornaam.setVolgnummer(new VolgnummerAttribuut(1));
        PersoonVoornaamStandaardGroepBericht standaardGroep = new PersoonVoornaamStandaardGroepBericht();
        standaardGroep.setNaam(new VoornaamAttribuut("Piet"));
        voornaam.setStandaard(standaardGroep);
        persoonVoornamen.add(voornaam);

        PersoonVoornaamBericht voornaam2 = new PersoonVoornaamBericht();
        voornaam2.setVolgnummer(new VolgnummerAttribuut(1));
        PersoonVoornaamStandaardGroepBericht standaardGroep2 = new PersoonVoornaamStandaardGroepBericht();
        standaardGroep2.setNaam(new VoornaamAttribuut("Jan"));
        voornaam2.setStandaard(standaardGroep2);
        persoonVoornamen.add(voornaam2);

        Assert.assertFalse(validator.isValid(persoonVoornamen, null));
    }

    @Test
    public void testVerschillendeVolgnummers() {
        List<PersoonVoornaamBericht> persoonVoornamen = new ArrayList<PersoonVoornaamBericht>();

        PersoonVoornaamBericht voornaam = new PersoonVoornaamBericht();
        voornaam.setVolgnummer(new VolgnummerAttribuut(1));
        PersoonVoornaamStandaardGroepBericht standaardGroep = new PersoonVoornaamStandaardGroepBericht();
        standaardGroep.setNaam(new VoornaamAttribuut("Dirk"));
        voornaam.setStandaard(standaardGroep);
        persoonVoornamen.add(voornaam);

        PersoonVoornaamBericht voornaam2 = new PersoonVoornaamBericht();
        voornaam2.setVolgnummer(new VolgnummerAttribuut(2));
        PersoonVoornaamStandaardGroepBericht standaardGroep2 = new PersoonVoornaamStandaardGroepBericht();
        standaardGroep2.setNaam(new VoornaamAttribuut("Klaassen"));
        voornaam2.setStandaard(standaardGroep2);
        persoonVoornamen.add(voornaam2);
        Assert.assertTrue(validator.isValid(persoonVoornamen, null));
    }

    @Test
    public void testVerschillendeVolgnummersMaarDezelfdeNaam() {
        List<PersoonVoornaamBericht> persoonVoornamen = new ArrayList<PersoonVoornaamBericht>();

        PersoonVoornaamBericht voornaam = new PersoonVoornaamBericht();
        voornaam.setVolgnummer(new VolgnummerAttribuut(1));
        PersoonVoornaamStandaardGroepBericht standaardGroep = new PersoonVoornaamStandaardGroepBericht();
        standaardGroep.setNaam(new VoornaamAttribuut("Henk"));
        voornaam.setStandaard(standaardGroep);
        persoonVoornamen.add(voornaam);

        PersoonVoornaamBericht voornaam2 = new PersoonVoornaamBericht();
        voornaam2.setVolgnummer(new VolgnummerAttribuut(2));
        PersoonVoornaamStandaardGroepBericht standaardGroep2 = new PersoonVoornaamStandaardGroepBericht();
        standaardGroep2.setNaam(new VoornaamAttribuut("Blom"));
        voornaam2.setStandaard(standaardGroep2);
        persoonVoornamen.add(voornaam2);
        Assert.assertTrue(validator.isValid(persoonVoornamen, null));
    }

    @Test
    public void testDezelfdeVolgnummersMaarVerschillendeNamen() {
        List<PersoonVoornaamBericht> persoonVoornamen = new ArrayList<PersoonVoornaamBericht>();

        PersoonVoornaamBericht voornaam = new PersoonVoornaamBericht();
        voornaam.setVolgnummer(new VolgnummerAttribuut(1));
        PersoonVoornaamStandaardGroepBericht standaardGroep = new PersoonVoornaamStandaardGroepBericht();
        standaardGroep.setNaam(new VoornaamAttribuut("Oussama"));
        voornaam.setStandaard(standaardGroep);
        persoonVoornamen.add(voornaam);

        PersoonVoornaamBericht voornaam2 = new PersoonVoornaamBericht();
        voornaam2.setVolgnummer(new VolgnummerAttribuut(1));
        PersoonVoornaamStandaardGroepBericht standaardGroep2 = new PersoonVoornaamStandaardGroepBericht();
        standaardGroep2.setNaam(new VoornaamAttribuut("Chougna"));
        voornaam2.setStandaard(standaardGroep2);
        persoonVoornamen.add(voornaam2);

        Assert.assertFalse(validator.isValid(persoonVoornamen, null));
    }

    @Test
    public void testDezelfdeNationaliteiten() {
        PersoonBericht persoon = new PersoonBericht();

        List<PersoonNationaliteitBericht> persoonNationalteiten = new ArrayList<PersoonNationaliteitBericht>();

        PersoonNationaliteitBericht persNation1 = new PersoonNationaliteitBericht();
        persNation1.setPersoon(persoon);
        // Nationaliteit nation1 = new Nationaliteit(new Nationaliteitcode("2"), null, null, null);
        persNation1.setNationaliteit(StatischeObjecttypeBuilder.NATIONALITEIT_NEDERLANDS);

        PersoonNationaliteitBericht persNation2 = new PersoonNationaliteitBericht();
        persNation2.setPersoon(persoon);
        // Nationaliteit nation2 = new Nationaliteit(new Nationaliteitcode("2"), null, null, null);
        persNation2.setNationaliteit(StatischeObjecttypeBuilder.NATIONALITEIT_NEDERLANDS);

        // 2x toevoegen!
        persoonNationalteiten.add(persNation1);
        persoonNationalteiten.add(persNation2);

        Assert.assertFalse(validator.isValid(persoonNationalteiten, null));
    }
}
