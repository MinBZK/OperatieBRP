/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.bedrijfsregels;

import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

import junit.framework.Assert;
import nl.bzk.brp.pocmotor.model.RootObject;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Huisletter;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Huisnummer;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Huisnummertoevoeging;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.NaamOpenbareRuimte;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Postcode;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.FunctieAdres;
import nl.bzk.brp.pocmotor.model.logisch.usr.groep.PersoonAdresStandaard;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.Persoon;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.PersoonAdres;
import org.junit.Test;

public class VerhuizingNaarHetzelfdeAdresTest {

    @Test
    public void test2IdentiekeAdressen() {
        final PersoonAdres huidig = maakStandaardAdres();
        final PersoonAdres nieuw = maakStandaardAdres();

        final Persoon persoon1 = new Persoon();
        persoon1.setAdressen(new TreeSet<PersoonAdres>());
        persoon1.getAdressen().add(huidig);
        
        final Persoon persoon2 = new Persoon();
        persoon2.setAdressen(new TreeSet<PersoonAdres>());
        persoon2.getAdressen().add(nieuw);

        BedrijfsRegelFout fout = new VerhuizingNaarHetzelfdeAdres().executeer(persoon1, rootOjectenInLijst(persoon2));

        Assert.assertNotNull(fout);
    }

    @Test
    public void testVerschillendePostcodeAdressen() {
        final PersoonAdres huidig = maakStandaardAdres();
        final PersoonAdres nieuw = maakStandaardAdres();

        nieuw.getStandaard().getPostcode().setWaarde("2222B");

        final Persoon persoon1 = new Persoon();
        persoon1.setAdressen(new TreeSet<PersoonAdres>());
        persoon1.getAdressen().add(huidig);

        final Persoon persoon2 = new Persoon();
        persoon2.setAdressen(new TreeSet<PersoonAdres>());
        persoon2.getAdressen().add(nieuw);

        BedrijfsRegelFout fout = new VerhuizingNaarHetzelfdeAdres().executeer(persoon1, rootOjectenInLijst(persoon2));

        Assert.assertNull(fout);
    }

    @Test
    public void testVerschillendeHuisnummerAdressen() {
        final PersoonAdres huidig = maakStandaardAdres();
        final PersoonAdres nieuw = maakStandaardAdres();

        nieuw.getStandaard().getHuisnummer().setWaarde("89");

        final Persoon persoon1 = new Persoon();
        persoon1.setAdressen(new TreeSet<PersoonAdres>());
        persoon1.getAdressen().add(huidig);

        final Persoon persoon2 = new Persoon();
        persoon2.setAdressen(new TreeSet<PersoonAdres>());
        persoon2.getAdressen().add(nieuw);

        BedrijfsRegelFout fout = new VerhuizingNaarHetzelfdeAdres().executeer(persoon1, rootOjectenInLijst(persoon2));

        Assert.assertNull(fout);
    }

    @Test
    public void testVerschillendeHuisletterAdressen() {
        final PersoonAdres huidig = maakStandaardAdres();
        final PersoonAdres nieuw = maakStandaardAdres();

        nieuw.getStandaard().getHuisletter().setWaarde("Z");

        final Persoon persoon1 = new Persoon();
        persoon1.setAdressen(new TreeSet<PersoonAdres>());
        persoon1.getAdressen().add(huidig);

        final Persoon persoon2 = new Persoon();
        persoon2.setAdressen(new TreeSet<PersoonAdres>());
        persoon2.getAdressen().add(nieuw);

        BedrijfsRegelFout fout = new VerhuizingNaarHetzelfdeAdres().executeer(persoon1, rootOjectenInLijst(persoon2));

        Assert.assertNull(fout);
    }

    @Test
    public void testVerschillendeHuisnummerToevoegingAdressen() {
        final PersoonAdres huidig = maakStandaardAdres();
        final PersoonAdres nieuw = maakStandaardAdres();

        nieuw.getStandaard().getHuisnummertoevoeging().setWaarde("1");

        final Persoon persoon1 = new Persoon();
        persoon1.setAdressen(new TreeSet<PersoonAdres>());
        persoon1.getAdressen().add(huidig);

        final Persoon persoon2 = new Persoon();
        persoon2.setAdressen(new TreeSet<PersoonAdres>());
        persoon2.getAdressen().add(nieuw);

        BedrijfsRegelFout fout = new VerhuizingNaarHetzelfdeAdres().executeer(persoon1, rootOjectenInLijst(persoon2));

        Assert.assertNull(fout);
    }

    @Test
    public void testVanGeenHuisLetterNaarHuisLetter() {
        final PersoonAdres huidig = maakStandaardAdres();
        final PersoonAdres nieuw = maakStandaardAdres();

        huidig.getStandaard().getHuisletter().setWaarde(null);
        nieuw.getStandaard().getHuisletter().setWaarde("Z");

        final Persoon persoon1 = new Persoon();
        persoon1.setAdressen(new TreeSet<PersoonAdres>());
        persoon1.getAdressen().add(huidig);

        final Persoon persoon2 = new Persoon();
        persoon2.setAdressen(new TreeSet<PersoonAdres>());
        persoon2.getAdressen().add(nieuw);

        BedrijfsRegelFout fout = new VerhuizingNaarHetzelfdeAdres().executeer(persoon1, rootOjectenInLijst(persoon2));

        Assert.assertNull(fout);
    }

    @Test
    public void testVanHuisNummerNaarHuisLetter() {
        final PersoonAdres huidig = maakStandaardAdres();
        final PersoonAdres nieuw = maakStandaardAdres();

        huidig.getStandaard().getHuisletter().setWaarde(null);
        huidig.getStandaard().getHuisnummer().setWaarde("1");

        nieuw.getStandaard().getHuisletter().setWaarde("Z");
        nieuw.getStandaard().getHuisnummer().setWaarde(null);

        final Persoon persoon1 = new Persoon();
        persoon1.setAdressen(new TreeSet<PersoonAdres>());
        persoon1.getAdressen().add(huidig);

        final Persoon persoon2 = new Persoon();
        persoon2.setAdressen(new TreeSet<PersoonAdres>());
        persoon2.getAdressen().add(nieuw);

        BedrijfsRegelFout fout = new VerhuizingNaarHetzelfdeAdres().executeer(persoon1, rootOjectenInLijst(persoon2));

        Assert.assertNull(fout);
    }

    @Test
    public void testStraatNaamGewijzigd() {
        final PersoonAdres huidig = maakStandaardAdres();
        final PersoonAdres nieuw = maakStandaardAdres();

        huidig.getStandaard().setNaamOpenbareRuimte(new NaamOpenbareRuimte());
        nieuw.getStandaard().setNaamOpenbareRuimte(new NaamOpenbareRuimte());
        huidig.getStandaard().getNaamOpenbareRuimte().setWaarde("PietStraat");
        nieuw.getStandaard().getNaamOpenbareRuimte().setWaarde("JanStraat");

        final Persoon persoon1 = new Persoon();
        persoon1.setAdressen(new TreeSet<PersoonAdres>());
        persoon1.getAdressen().add(huidig);

        final Persoon persoon2 = new Persoon();
        persoon2.setAdressen(new TreeSet<PersoonAdres>());
        persoon2.getAdressen().add(nieuw);

        BedrijfsRegelFout fout = new VerhuizingNaarHetzelfdeAdres().executeer(persoon1, rootOjectenInLijst(persoon2));

        Assert.assertNotNull(fout);
    }

    private PersoonAdres maakStandaardAdres() {
        final PersoonAdres adres = new PersoonAdres();
        final PersoonAdresStandaard persoonAdresStandaard = new PersoonAdresStandaard();
        adres.setStandaard(persoonAdresStandaard);

        Postcode postcode = new Postcode();
        postcode.setWaarde("1111AA");
        persoonAdresStandaard.setPostcode(postcode);

        Huisnummer huisnummer = new Huisnummer();
        huisnummer.setWaarde("25");
        persoonAdresStandaard.setHuisnummer(huisnummer);

        Huisletter huisletter = new Huisletter();
        huisletter.setWaarde("A");
        persoonAdresStandaard.setHuisletter(huisletter);

        Huisnummertoevoeging huisnummertoevoeging = new Huisnummertoevoeging();
        huisnummertoevoeging.setWaarde("3");
        persoonAdresStandaard.setHuisnummertoevoeging(huisnummertoevoeging);

        persoonAdresStandaard.setSoort(FunctieAdres.WOONADRES);

        return adres;
    }

    private List<RootObject> rootOjectenInLijst(RootObject... objecten) {
        return Arrays.asList(objecten);
    }
}
