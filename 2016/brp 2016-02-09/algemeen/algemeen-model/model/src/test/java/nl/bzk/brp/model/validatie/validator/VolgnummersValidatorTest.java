/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.validatie.validator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.GeslachtsnaamstamAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VolgnummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoornaamAttribuut;
import nl.bzk.brp.model.bericht.kern.OuderBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeslachtsnaamcomponentBericht;
import nl.bzk.brp.model.bericht.kern.PersoonGeslachtsnaamcomponentStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.PersoonVoornaamBericht;
import nl.bzk.brp.model.bericht.kern.PersoonVoornaamStandaardGroepBericht;
import nl.bzk.brp.model.logisch.kern.Betrokkenheid;
import nl.bzk.brp.model.logisch.kern.PersoonVoornaam;

import org.junit.Assert;
import org.junit.Test;


/**
 * Unit test voor de {@link VolgnummersValidator} class.
 */
public class VolgnummersValidatorTest {

    private final VolgnummersValidator validator = new VolgnummersValidator();

    @Test
    public void testAllesVolgnummersGoed() {
        List<PersoonVoornaamBericht> namen =
                Arrays.asList(maakVoornaam(3, "Jan"), maakVoornaam(1, "Keesje"), maakVoornaam(2, "Klaasje"));

        // initialize doet niks
        this.validator.initialize(null);

        Assert.assertTrue("Er zal aan alle regels voldoen moeten zijn.", this.validator.isValid(namen, null));
    }

    @Test
    public void testGeenVolgnummer1() {
        List<PersoonVoornaamBericht> namen = Arrays.asList(maakVoornaam(2, "pietje"), maakVoornaam(3, "parkietje"));
        Assert.assertFalse("Er moet op zijn minst een volgnummer 1 aanwezig zijn.",
                this.validator.isValid(namen, null));
    }

    @Test
    public void testGeenUniekeVolgnummers() {
        List<PersoonVoornaamBericht> namen = Arrays.asList(maakVoornaam(1, "jantje"), maakVoornaam(1, "beton"));
        Assert.assertFalse("Volgnummers moeten uniek zijn.", this.validator.isValid(namen, null));
    }

    @Test
    public void testGeenOpeenVolgendenummers() {
        List<PersoonVoornaamBericht> namen = Arrays.asList(maakVoornaam(1, "Henk"), maakVoornaam(3, "Blom"));
        Assert.assertFalse("Volgnummers moeten opvolgend zijn.", this.validator.isValid(namen, null));
    }

    @Test
    public void testGeenVoornamen() {
        List<PersoonVoornaam> namen = null;
        Assert.assertTrue("Zou niet moeten valideren.", this.validator.isValid(namen, null));

        namen = new ArrayList<PersoonVoornaam>();
        Assert.assertTrue("Zou niet moeten valideren.", this.validator.isValid(namen, null));
    }

    @Test
    public void testAllesVolgnummersInGeslachtsnaamCompGoed() {
        List<PersoonGeslachtsnaamcomponentBericht> namen =
                Arrays.asList(maakGeslachtsnaam(3, "naam3"), maakGeslachtsnaam(2, "naam2"),
                        maakGeslachtsnaam(1, "naam1"));
        Assert.assertTrue("Er zal aan alle regels voldoen moeten zijn.", this.validator.isValid(namen, null));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testNietOndersteundeObjectType() {
        List<Betrokkenheid> betr = new ArrayList<Betrokkenheid>();

        OuderBericht ouder = new OuderBericht();

        betr.add(ouder);

        this.validator.isValid(betr, null);
    }

    private PersoonVoornaamBericht maakVoornaam(final Integer volgNummer, final String voornaam) {
        PersoonVoornaamBericht naam = new PersoonVoornaamBericht();
        naam.setVolgnummer(new VolgnummerAttribuut(volgNummer));
        naam.setStandaard(new PersoonVoornaamStandaardGroepBericht());
        naam.getStandaard().setNaam(new VoornaamAttribuut(voornaam));
        return naam;
    }

    private PersoonGeslachtsnaamcomponentBericht maakGeslachtsnaam(final Integer volgNummer, final String geslnaam) {
        PersoonGeslachtsnaamcomponentBericht naam = new PersoonGeslachtsnaamcomponentBericht();
        naam.setVolgnummer(new VolgnummerAttribuut(volgNummer));
        naam.setStandaard(new PersoonGeslachtsnaamcomponentStandaardGroepBericht());
        naam.getStandaard().setStam(new GeslachtsnaamstamAttribuut(geslnaam));
        return naam;
    }
}
