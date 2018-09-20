/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.validatie.validator;

import java.util.ArrayList;
import java.util.List;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoornaamAttribuut;
import nl.bzk.brp.model.bericht.kern.PersoonVoornaamBericht;
import nl.bzk.brp.model.bericht.kern.PersoonVoornaamStandaardGroepBericht;
import nl.bzk.brp.model.logisch.kern.PersoonVoornaam;

import org.junit.Assert;
import org.junit.Test;


/**
 * Unit test voor de {@link BRAL0211Validator} class.
 */
public class BRAL0211ValidatorTest {

    private final BRAL0211Validator validator = new BRAL0211Validator();

    @Test
    public void testGeconcateneerdeNamen200Tekens() {
        List<PersoonVoornaam> namen = new ArrayList<PersoonVoornaam>();

        PersoonVoornaamBericht voornaam1 = new PersoonVoornaamBericht();
        voornaam1.setStandaard(new PersoonVoornaamStandaardGroepBericht());
        voornaam1.getStandaard().setNaam(maakVoornaam(100));

        PersoonVoornaamBericht voornaam2 = new PersoonVoornaamBericht();
        voornaam2.setStandaard(new PersoonVoornaamStandaardGroepBericht());
        voornaam2.getStandaard().setNaam(maakVoornaam(99));

        namen.add(voornaam1);
        namen.add(voornaam2);

        // initialize doet niks
        this.validator.initialize(null);

        Assert.assertTrue("Inclusief spaties zou 200 tekens een geldige voornaam moeten zijn",
                this.validator.isValid(namen, null));
    }

    @Test
    public void testGeconcateneerdeNamen199Tekens() {
        List<PersoonVoornaam> namen = new ArrayList<PersoonVoornaam>();

        PersoonVoornaamBericht voornaam1 = new PersoonVoornaamBericht();
        voornaam1.setStandaard(new PersoonVoornaamStandaardGroepBericht());
        voornaam1.getStandaard().setNaam(maakVoornaam(100));

        PersoonVoornaamBericht voornaam2 = new PersoonVoornaamBericht();
        voornaam2.setStandaard(new PersoonVoornaamStandaardGroepBericht());
        voornaam2.getStandaard().setNaam(maakVoornaam(98));

        namen.add(voornaam1);
        namen.add(voornaam2);

        Assert.assertTrue("Inclusief spaties zou 199 tekens een geldige voornaam moeten zijn",
                this.validator.isValid(namen, null));
    }

    @Test
    public void testGeconcateneerdeNamen201Tekens() {
        List<PersoonVoornaam> namen = new ArrayList<PersoonVoornaam>();

        PersoonVoornaamBericht voornaam1 = new PersoonVoornaamBericht();
        voornaam1.setStandaard(new PersoonVoornaamStandaardGroepBericht());
        voornaam1.getStandaard().setNaam(maakVoornaam(100));

        PersoonVoornaamBericht voornaam2 = new PersoonVoornaamBericht();
        voornaam2.setStandaard(new PersoonVoornaamStandaardGroepBericht());
        voornaam2.getStandaard().setNaam(maakVoornaam(100));

        namen.add(voornaam1);
        namen.add(voornaam2);

        Assert.assertFalse("Inclusief spaties zou 201 tekens een ongeldige voornaam moeten zijn",
                this.validator.isValid(namen, null));
    }

    @Test
    public void testMeerdereVoornamen200Tekens() {
        List<PersoonVoornaam> namen = new ArrayList<PersoonVoornaam>();

        PersoonVoornaamBericht voornaam1 = new PersoonVoornaamBericht();
        voornaam1.setStandaard(new PersoonVoornaamStandaardGroepBericht());
        voornaam1.getStandaard().setNaam(maakVoornaam(100));

        PersoonVoornaamBericht voornaam2 = new PersoonVoornaamBericht();
        voornaam2.setStandaard(new PersoonVoornaamStandaardGroepBericht());
        voornaam2.getStandaard().setNaam(maakVoornaam(66));

        PersoonVoornaamBericht voornaam3 = new PersoonVoornaamBericht();
        voornaam3.setStandaard(new PersoonVoornaamStandaardGroepBericht());
        voornaam3.getStandaard().setNaam(maakVoornaam(32));

        namen.add(voornaam1);
        namen.add(voornaam2);
        namen.add(voornaam3);

        Assert.assertTrue("Inclusief spaties zou 200 tekens een geldige voornaam moeten zijn",
                this.validator.isValid(namen, null));
    }

    @Test
    public void testMeerdereVoornamen201Tekens() {
        List<PersoonVoornaam> namen = new ArrayList<PersoonVoornaam>();

        PersoonVoornaamBericht voornaam1 = new PersoonVoornaamBericht();
        voornaam1.setStandaard(new PersoonVoornaamStandaardGroepBericht());
        voornaam1.getStandaard().setNaam(maakVoornaam(100));

        PersoonVoornaamBericht voornaam2 = new PersoonVoornaamBericht();
        voornaam2.setStandaard(new PersoonVoornaamStandaardGroepBericht());
        voornaam2.getStandaard().setNaam(maakVoornaam(66));

        PersoonVoornaamBericht voornaam3 = new PersoonVoornaamBericht();
        voornaam3.setStandaard(new PersoonVoornaamStandaardGroepBericht());
        voornaam3.getStandaard().setNaam(maakVoornaam(33));

        namen.add(voornaam1);
        namen.add(voornaam2);
        namen.add(voornaam3);

        Assert.assertFalse("Inclusief spaties zou 201 tekens een ongeldige voornaam moeten zijn",
                this.validator.isValid(namen, null));
    }

    @Test
    public void testMeerdereVoornamenNietGevuld() {
        List<PersoonVoornaam> namen = new ArrayList<PersoonVoornaam>();

        PersoonVoornaamBericht voornaam1 = null;

        PersoonVoornaamBericht voornaam2 = new PersoonVoornaamBericht();

        PersoonVoornaamBericht voornaam3 = new PersoonVoornaamBericht();
        voornaam3.setStandaard(new PersoonVoornaamStandaardGroepBericht());

        PersoonVoornaamBericht voornaam4 = new PersoonVoornaamBericht();
        voornaam4.setStandaard(new PersoonVoornaamStandaardGroepBericht());
        voornaam4.getStandaard().setNaam(new VoornaamAttribuut(null));

        namen.add(voornaam1);
        namen.add(voornaam2);
        namen.add(voornaam3);
        namen.add(voornaam4);

        Assert.assertTrue("Inclusief spaties zou 200 tekens een geldige voornaam moeten zijn",
                this.validator.isValid(namen, null));
    }

    private VoornaamAttribuut maakVoornaam(final int lengte) {
        String naam = "";

        int getal = 0;
        for (int i = 0; i < lengte; i++) {
            naam = naam + getal;

            if (getal == 9) {
                getal = 0;
            } else {
                getal++;
            }
        }

        return new VoornaamAttribuut(naam);
    }
}
