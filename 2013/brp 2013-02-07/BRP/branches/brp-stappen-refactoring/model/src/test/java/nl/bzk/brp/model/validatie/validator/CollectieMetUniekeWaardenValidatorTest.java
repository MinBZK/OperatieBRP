/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.validatie.validator;

import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Nationaliteitcode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Volgnummer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Voornaam;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Nationaliteit;
import nl.bzk.brp.model.bericht.kern.PersoonNationaliteitBericht;
import nl.bzk.brp.model.bericht.kern.PersoonVoornaamBericht;
import nl.bzk.brp.model.bericht.kern.PersoonVoornaamStandaardGroepBericht;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import org.junit.Ignore;
import org.junit.Test;

public class CollectieMetUniekeWaardenValidatorTest {

    private final CollectieMetUniekeWaardenValidator validator = new CollectieMetUniekeWaardenValidator();

    @Test
    public void test1Waarde() {
        Set<PersoonVoornaamBericht> persoonVoornamen = new HashSet<PersoonVoornaamBericht>();
        PersoonVoornaamBericht voornaam = new PersoonVoornaamBericht();
        voornaam.setVolgnummer(new Volgnummer(1));
        PersoonVoornaamStandaardGroepBericht standaardGroep = new PersoonVoornaamStandaardGroepBericht();
        standaardGroep.setNaam(new Voornaam("Piet"));
        voornaam.setStandaard(standaardGroep);
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
        standaardGroep.setNaam(new Voornaam("Piet"));
        voornaam.setStandaard(standaardGroep);
        persoonVoornamen.add(voornaam);

        PersoonVoornaamBericht voornaam2 = new PersoonVoornaamBericht();
        voornaam2.setVolgnummer(new Volgnummer(1));
        PersoonVoornaamStandaardGroepBericht standaardGroep2 = new PersoonVoornaamStandaardGroepBericht();
        standaardGroep2.setNaam(new Voornaam("Jan"));
        voornaam2.setStandaard(standaardGroep2);
        persoonVoornamen.add(voornaam2);

        Assert.assertFalse(validator.isValid(persoonVoornamen, null));
    }

    @Test
    public void testVerschillendeVolgnummers() {
        Set<PersoonVoornaamBericht> persoonVoornamen = new HashSet<PersoonVoornaamBericht>();

        PersoonVoornaamBericht voornaam = new PersoonVoornaamBericht();
        voornaam.setVolgnummer(new Volgnummer(1));
        PersoonVoornaamStandaardGroepBericht standaardGroep = new PersoonVoornaamStandaardGroepBericht();
        standaardGroep.setNaam(new Voornaam("Piet"));
        voornaam.setStandaard(standaardGroep);
        persoonVoornamen.add(voornaam);

        PersoonVoornaamBericht voornaam2 = new PersoonVoornaamBericht();
        voornaam2.setVolgnummer(new Volgnummer(2));
        PersoonVoornaamStandaardGroepBericht standaardGroep2 = new PersoonVoornaamStandaardGroepBericht();
        standaardGroep2.setNaam(new Voornaam("Jan"));
        voornaam2.setStandaard(standaardGroep2);
        persoonVoornamen.add(voornaam2);
        Assert.assertTrue(validator.isValid(persoonVoornamen, null));
    }

    @Test
    public void testVerschillendeVolgnummersMaarDezelfdeNaam() {
        Set<PersoonVoornaamBericht> persoonVoornamen = new HashSet<PersoonVoornaamBericht>();

        PersoonVoornaamBericht voornaam = new PersoonVoornaamBericht();
        voornaam.setVolgnummer(new Volgnummer(1));
        PersoonVoornaamStandaardGroepBericht standaardGroep = new PersoonVoornaamStandaardGroepBericht();
        standaardGroep.setNaam(new Voornaam("Piet"));
        voornaam.setStandaard(standaardGroep);
        persoonVoornamen.add(voornaam);

        PersoonVoornaamBericht voornaam2 = new PersoonVoornaamBericht();
        voornaam2.setVolgnummer(new Volgnummer(2));
        PersoonVoornaamStandaardGroepBericht standaardGroep2 = new PersoonVoornaamStandaardGroepBericht();
        standaardGroep2.setNaam(new Voornaam("Piet"));
        voornaam2.setStandaard(standaardGroep2);
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
        standaardGroep.setNaam(new Voornaam("Piet"));
        voornaam.setStandaard(standaardGroep);
        persoonVoornamen.add(voornaam);

        PersoonVoornaamBericht voornaam2 = new PersoonVoornaamBericht();
        voornaam2.setVolgnummer(new Volgnummer(1));
        PersoonVoornaamStandaardGroepBericht standaardGroep2 = new PersoonVoornaamStandaardGroepBericht();
        standaardGroep2.setNaam(new Voornaam("Jan"));
        voornaam2.setStandaard(standaardGroep2);
        persoonVoornamen.add(voornaam2);

        Assert.assertFalse(validator.isValid(persoonVoornamen, null));
    }

    @Test
    @Ignore
    public void testDezelfdeNationaliteiten() {
        Set<PersoonNationaliteitBericht> persoonNationalteiten = new HashSet<PersoonNationaliteitBericht>();

        PersoonNationaliteitBericht persNation1 = new PersoonNationaliteitBericht();
//        Nationaliteit nation1 = new Nationaliteit(new Nationaliteitcode("2"), null, null, null);
        persNation1.setNationaliteit(StatischeObjecttypeBuilder.NATIONALITEIT_NEDERLANDS);

        PersoonNationaliteitBericht persNation2 = new PersoonNationaliteitBericht();
//        Nationaliteit nation2 = new Nationaliteit(new Nationaliteitcode("2"), null, null, null);
        persNation2.setNationaliteit(StatischeObjecttypeBuilder.NATIONALITEIT_NEDERLANDS);

        //2x toevoegen!
        persoonNationalteiten.add(persNation1);
        persoonNationalteiten.add(persNation2);

        Assert.assertFalse(validator.isValid(persoonNationalteiten, null));
    }
}
