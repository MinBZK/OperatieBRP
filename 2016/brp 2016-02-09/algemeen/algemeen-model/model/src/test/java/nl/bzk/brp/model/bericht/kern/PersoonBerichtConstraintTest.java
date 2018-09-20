/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdresregelAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AfgekorteNaamOpenbareRuimteAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GemeentedeelAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.HuisletterAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.HuisnummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.HuisnummertoevoegingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.IdentificatiecodeAdresseerbaarObjectAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.IdentificatiecodeNummeraanduidingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LocatieTenOpzichteVanAdres;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LocatieTenOpzichteVanAdresAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LocatieomschrijvingAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamOpenbareRuimteAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PostcodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ScheidingstekenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VolgnummerAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoornaamAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoorvoegselAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.FunctieAdres;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.FunctieAdresAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.basis.AbstractAttribuut;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import org.junit.Test;


public class PersoonBerichtConstraintTest {

    @Test
    public void testBRAL0212NietGeldig() {
        PersoonBericht persoon = new PersoonBericht();
        persoon.setSoort(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        persoon.setGeslachtsnaamcomponenten(new ArrayList<PersoonGeslachtsnaamcomponentBericht>());

        PersoonGeslachtsnaamcomponentBericht geslComp = new PersoonGeslachtsnaamcomponentBericht();

        PersoonGeslachtsnaamcomponentStandaardGroepBericht groep =
                new PersoonGeslachtsnaamcomponentStandaardGroepBericht();
        groep.setScheidingsteken(null);
        groep.setVoorvoegsel(new VoorvoegselAttribuut("abc"));

        geslComp.setVolgnummer(new VolgnummerAttribuut(1));
        geslComp.setStandaard(groep);

        persoon.getGeslachtsnaamcomponenten().add(geslComp);

        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<PersoonBericht>> overtredingen = validator.validate(persoon, Default.class);
        assertEquals(1, overtredingen.size());
        @SuppressWarnings("rawtypes")
        ConstraintViolation cv = overtredingen.iterator().next();
        assertEquals(Regel.BRAL0212, haalRegelCodeOp(cv));
    }

    @Test
    public void testBRAL0211VoornamenTeLang() {
        PersoonBericht persoon = new PersoonBericht();
        persoon.setSoort(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        persoon.setVoornamen(new ArrayList<PersoonVoornaamBericht>());

        persoon.getVoornamen().add(maakPersoonVoornaamBericht(1, maakVoornaam(99)));
        persoon.getVoornamen().add(maakPersoonVoornaamBericht(2, maakVoornaam(101)));

        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<PersoonBericht>> overtredingen = validator.validate(persoon, Default.class);
        assertEquals(1, overtredingen.size());
        @SuppressWarnings("rawtypes")
        ConstraintViolation cv = overtredingen.iterator().next();
        assertEquals(Regel.BRAL0211, haalRegelCodeOp(cv));
    }

    @Test
    public void testBRAL0503VolgnummersNietGoed() {
        PersoonBericht persoon = new PersoonBericht();
        persoon.setSoort(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        persoon.setVoornamen(new ArrayList<PersoonVoornaamBericht>());

        persoon.getVoornamen().add(maakPersoonVoornaamBericht(2, "voornaam1"));

        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<PersoonBericht>> overtredingen = validator.validate(persoon, Default.class);
        assertEquals(1, overtredingen.size());
        @SuppressWarnings("rawtypes")
        ConstraintViolation cv = overtredingen.iterator().next();
        assertEquals(Regel.BRAL0503, haalRegelCodeOp(cv));
    }

    @Test
    public void testBRAL0504VolgnummersNietGoed() {
        PersoonBericht persoon = new PersoonBericht();
        persoon.setSoort(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        persoon.setGeslachtsnaamcomponenten(new ArrayList<PersoonGeslachtsnaamcomponentBericht>());

        PersoonGeslachtsnaamcomponentBericht geslcomp = new PersoonGeslachtsnaamcomponentBericht();
        geslcomp.setVolgnummer(new VolgnummerAttribuut(2));
        persoon.getGeslachtsnaamcomponenten().add(geslcomp);

        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<PersoonBericht>> overtredingen = validator.validate(persoon, Default.class);
        assertEquals(1, overtredingen.size());
        @SuppressWarnings("rawtypes")
        ConstraintViolation cv = overtredingen.iterator().next();
        assertEquals(Regel.BRAL0504, haalRegelCodeOp(cv));
    }

    @Test
    public void testBRAL0505VoorvoegselNietGoedVanwegeIndicatieNamenreek() {
        PersoonBericht persoon = new PersoonBericht();
        persoon.setSoort(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        persoon.setSamengesteldeNaam(new PersoonSamengesteldeNaamGroepBericht());
        persoon.getSamengesteldeNaam().setIndicatieNamenreeks(JaNeeAttribuut.JA);
        persoon.getSamengesteldeNaam().setVoorvoegsel(new VoorvoegselAttribuut("de"));
        persoon.getSamengesteldeNaam().setScheidingsteken(new ScheidingstekenAttribuut("-"));

        persoon.setGeboorte(new PersoonGeboorteGroepBericht());
        persoon.setGeslachtsaanduiding(new PersoonGeslachtsaanduidingGroepBericht());

        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<PersoonBericht>> overtredingen = validator.validate(persoon, Default.class);
        assertEquals(1, overtredingen.size());
        @SuppressWarnings("rawtypes")
        ConstraintViolation cv = overtredingen.iterator().next();
        assertEquals("BRAL0505", cv.getMessage());
        assertEquals(Regel.BRAL0505, haalRegelCodeOp(cv));
    }

    @Test
    public void testGeenSpatieInVoornaam() {
        PersoonBericht persoon = new PersoonBericht();
        persoon.setSoort(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        persoon.setVoornamen(new ArrayList<PersoonVoornaamBericht>());

        persoon.getVoornamen().add(maakPersoonVoornaamBericht(1, "voor naam"));

        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<PersoonBericht>> overtredingen = validator.validate(persoon, Default.class);
        assertEquals(1, overtredingen.size());
        @SuppressWarnings("rawtypes")
        ConstraintViolation cv = overtredingen.iterator().next();
        assertEquals("{BRAL0501}", cv.getMessage());
        assertEquals(Regel.BRAL0501, haalRegelCodeOp(cv));
        assertEquals("voor naam", ((AbstractAttribuut<?>) cv.getInvalidValue()).getWaarde());
    }

    @Test
    public void testGeenSpatieInMeerdereVoornaam() {
        PersoonBericht persoon = new PersoonBericht();
        persoon.setSoort(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        persoon.setVoornamen(new ArrayList<PersoonVoornaamBericht>());

        persoon.getVoornamen().add(maakPersoonVoornaamBericht(2, "voor naam"));
        persoon.getVoornamen().add(maakPersoonVoornaamBericht(1, "Met slagroom"));

        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<PersoonBericht>> overtredingen = validator.validate(persoon, Default.class);
        assertEquals(2, overtredingen.size());
        @SuppressWarnings("rawtypes")
        ConstraintViolation cv = overtredingen.iterator().next();
        assertEquals("{BRAL0501}", cv.getMessage());
        assertEquals(Regel.BRAL0501, haalRegelCodeOp(cv));
    }

    @Test
    public void testGeenSpatieInVoornaamEnVolgnummerNietGoed() {
        PersoonBericht persoon = new PersoonBericht();
        persoon.setSoort(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        persoon.setVoornamen(new ArrayList<PersoonVoornaamBericht>());

        persoon.getVoornamen().add(maakPersoonVoornaamBericht(2, "voor naam"));

        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<PersoonBericht>> overtredingen = validator.validate(persoon, Default.class);
        assertEquals(2, overtredingen.size());
    }

    @Test
    public void testBRAL0209() {
        PersoonAdresBericht persoonAdresBericht = new PersoonAdresBericht();
        persoonAdresBericht.setStandaard(new PersoonAdresStandaardGroepBericht());
        persoonAdresBericht.getStandaard().setWoonplaatsnaam(StatischeObjecttypeBuilder.WOONPLAATS_AMSTERDAM);
        persoonAdresBericht.getStandaard().setIdentificatiecodeNummeraanduiding(
                new IdentificatiecodeNummeraanduidingAttribuut("a"));
        persoonAdresBericht.getStandaard().setNaamOpenbareRuimte(new NaamOpenbareRuimteAttribuut("b"));
        persoonAdresBericht.getStandaard().setHuisnummer(new HuisnummerAttribuut(1));
        persoonAdresBericht.getStandaard().setIdentificatiecodeAdresseerbaarObject(
                new IdentificatiecodeAdresseerbaarObjectAttribuut("c"));
        PersoonBericht persoon = new PersoonBericht();
        persoon.setSoort(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        persoon.setAdressen(new ArrayList<PersoonAdresBericht>());
        persoon.getAdressen().add(persoonAdresBericht);

        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<PersoonBericht>> overtredingen = validator.validate(persoon, Default.class);
        assertEquals(1, overtredingen.size());
        @SuppressWarnings("rawtypes")
        ConstraintViolation cv = overtredingen.iterator().next();
        assertEquals("BRAL0209", cv.getMessage());
        assertEquals(Regel.BRAL0209, haalRegelCodeOp(cv));

        persoonAdresBericht.getStandaard().setGemeente(StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM);
        overtredingen = validator.validate(persoon, Default.class);
        assertEquals(0, overtredingen.size());
    }

    @Test
    public void testBRAL9025() {
        PersoonAdresBericht persoonAdresBericht = new PersoonAdresBericht();
        persoonAdresBericht.setStandaard(new PersoonAdresStandaardGroepBericht());
        persoonAdresBericht.getStandaard().setLocatieomschrijving(
                new LocatieomschrijvingAttribuut("123456789012345678901234567890123456"));

        PersoonBericht persoon = new PersoonBericht();
        persoon.setSoort(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        persoon.setAdressen(new ArrayList<PersoonAdresBericht>());
        persoon.getAdressen().add(persoonAdresBericht);

        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<PersoonBericht>> overtredingen = validator.validate(persoon, Default.class);
        assertEquals(1, overtredingen.size());

        boolean isBRAL9025Geraakt = false;
        for (ConstraintViolation<PersoonBericht> cv : overtredingen) {
            final Regel regel = haalRegelCodeOp(cv);
            if (regel == Regel.BRAL9025) {
                isBRAL9025Geraakt = true;
            }
        }

        assertTrue(isBRAL9025Geraakt);

        persoonAdresBericht.getStandaard().setLocatieomschrijving(
                new LocatieomschrijvingAttribuut("12345678901234567890123456789012345"));

        overtredingen = validator.validate(persoon, Default.class);
        assertEquals(0, overtredingen.size());
    }

    @Test
    // BRAL9027: Buitenlandse adresregel mag niet uit meer dan 35 karakters bestaan. */
    public void testBRAL9027()
    {
        PersoonMigratieGroepBericht persoonMigratieGroepBericht = new PersoonMigratieGroepBericht();
        persoonMigratieGroepBericht.setBuitenlandsAdresRegel1Migratie(new AdresregelAttribuut(
                "123456789012345678901234567890123456"));
        persoonMigratieGroepBericht.setBuitenlandsAdresRegel2Migratie(new AdresregelAttribuut(
                "123456789012345678901234567890123456"));
        persoonMigratieGroepBericht.setBuitenlandsAdresRegel3Migratie(new AdresregelAttribuut(
                "123456789012345678901234567890123456"));
        persoonMigratieGroepBericht.setBuitenlandsAdresRegel4Migratie(new AdresregelAttribuut(
                "123456789012345678901234567890123456"));
        persoonMigratieGroepBericht.setBuitenlandsAdresRegel5Migratie(new AdresregelAttribuut(
                "123456789012345678901234567890123456"));
        persoonMigratieGroepBericht.setBuitenlandsAdresRegel6Migratie(new AdresregelAttribuut(
                "123456789012345678901234567890123456"));

        PersoonBericht persoon = new PersoonBericht();
        persoon.setSoort(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        persoon.setMigratie(persoonMigratieGroepBericht);

        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<PersoonBericht>> overtredingen = validator.validate(persoon, Default.class);
        assertEquals(6, overtredingen.size());

        boolean isBRAL9027Geraakt = false;
        for (ConstraintViolation<PersoonBericht> cv : overtredingen) {
            final Regel regel = haalRegelCodeOp(cv);
            if (regel == Regel.BRAL9027) {
                isBRAL9027Geraakt = true;
            }
        }

        assertTrue(isBRAL9027Geraakt);

        persoonMigratieGroepBericht.setBuitenlandsAdresRegel1Migratie(new AdresregelAttribuut(
                "12345678901234567890123456789012345"));
        persoonMigratieGroepBericht.setBuitenlandsAdresRegel2Migratie(new AdresregelAttribuut(
                "12345678901234567890123456789012345"));
        persoonMigratieGroepBericht.setBuitenlandsAdresRegel3Migratie(new AdresregelAttribuut(
                "12345678901234567890123456789012345"));
        persoonMigratieGroepBericht.setBuitenlandsAdresRegel4Migratie(new AdresregelAttribuut(
                "12345678901234567890123456789012345"));
        persoonMigratieGroepBericht.setBuitenlandsAdresRegel5Migratie(new AdresregelAttribuut(
                "12345678901234567890123456789012345"));
        persoonMigratieGroepBericht.setBuitenlandsAdresRegel6Migratie(new AdresregelAttribuut(
                "12345678901234567890123456789012345"));

        overtredingen = validator.validate(persoon, Default.class);
        assertEquals(0, overtredingen.size());
    }

    @Test
    public void testBRAL2085() {
        PersoonAdresBericht persoonAdresBericht = new PersoonAdresBericht();
        persoonAdresBericht.setStandaard(new PersoonAdresStandaardGroepBericht());
        persoonAdresBericht.getStandaard().setHuisnummertoevoeging(new HuisnummertoevoegingAttribuut("Zwart"));

        PersoonBericht persoon = new PersoonBericht();
        persoon.setSoort(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        persoon.setAdressen(new ArrayList<PersoonAdresBericht>());
        persoon.getAdressen().add(persoonAdresBericht);

        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<PersoonBericht>> overtredingen = validator.validate(persoon, Default.class);
        assertEquals(1, overtredingen.size());
        @SuppressWarnings("rawtypes")
        ConstraintViolation cv = overtredingen.iterator().next();
        assertEquals("BRAL2085", cv.getMessage());
        assertEquals(Regel.BRAL2085, haalRegelCodeOp(cv));

        persoonAdresBericht.getStandaard().setHuisnummer(new HuisnummerAttribuut(1));
        overtredingen = validator.validate(persoon, Default.class);
        assertEquals(0, overtredingen.size());
    }

    @Test
    public void testBRAL2086() {
        PersoonAdresBericht persoonAdresBericht = new PersoonAdresBericht();
        persoonAdresBericht.setStandaard(new PersoonAdresStandaardGroepBericht());
        persoonAdresBericht.getStandaard().setHuisnummer(new HuisnummerAttribuut(1));
        persoonAdresBericht.getStandaard().setHuisnummertoevoeging(new HuisnummertoevoegingAttribuut("Zwart"));
        persoonAdresBericht.getStandaard().setLocatieTenOpzichteVanAdres(
                new LocatieTenOpzichteVanAdresAttribuut(LocatieTenOpzichteVanAdres.TO));

        PersoonBericht persoon = new PersoonBericht();
        persoon.setSoort(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        persoon.setAdressen(new ArrayList<PersoonAdresBericht>());
        persoon.getAdressen().add(persoonAdresBericht);

        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<PersoonBericht>> overtredingen = validator.validate(persoon, Default.class);
        assertEquals(1, overtredingen.size());
        @SuppressWarnings("rawtypes")
        ConstraintViolation cv = overtredingen.iterator().next();
        assertEquals("BRAL2086", cv.getMessage());
        assertEquals(Regel.BRAL2086, haalRegelCodeOp(cv));

        persoonAdresBericht.getStandaard().setHuisnummertoevoeging(null);

        overtredingen = validator.validate(persoon, Default.class);
        assertEquals(0, overtredingen.size());
    }

    @Test
    public void testBRAL2084() {
        PersoonAdresBericht persoonAdresBericht = new PersoonAdresBericht();
        persoonAdresBericht.setStandaard(new PersoonAdresStandaardGroepBericht());
        persoonAdresBericht.getStandaard().setHuisletter(new HuisletterAttribuut("a"));

        PersoonBericht persoon = new PersoonBericht();
        persoon.setSoort(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        persoon.setAdressen(new ArrayList<PersoonAdresBericht>());
        persoon.getAdressen().add(persoonAdresBericht);

        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<PersoonBericht>> overtredingen = validator.validate(persoon, Default.class);
        assertEquals(1, overtredingen.size());
        @SuppressWarnings("rawtypes")
        ConstraintViolation cv = overtredingen.iterator().next();
        assertEquals("BRAL2084", cv.getMessage());
        assertEquals(Regel.BRAL2084, haalRegelCodeOp(cv));

        persoonAdresBericht.getStandaard().setHuisnummer(new HuisnummerAttribuut(1));
        overtredingen = validator.validate(persoon, Default.class);
        assertEquals(0, overtredingen.size());
    }

    @Test
    public void testBRAL2035() {
        PersoonAdresBericht persoonAdresBericht = new PersoonAdresBericht();
        persoonAdresBericht.setStandaard(new PersoonAdresStandaardGroepBericht());
        persoonAdresBericht.getStandaard().setGemeentedeel(new GemeentedeelAttribuut("abc"));

        PersoonBericht persoon = new PersoonBericht();
        persoon.setSoort(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        persoon.setAdressen(new ArrayList<PersoonAdresBericht>());
        persoon.getAdressen().add(persoonAdresBericht);

        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<PersoonBericht>> overtredingen = validator.validate(persoon, Default.class);
        assertEquals(1, overtredingen.size());
        @SuppressWarnings("rawtypes")
        ConstraintViolation cv = overtredingen.iterator().next();
        assertEquals("BRAL2035", cv.getMessage());
        assertEquals(Regel.BRAL2035, haalRegelCodeOp(cv));

        persoonAdresBericht.getStandaard().setGemeente(StatischeObjecttypeBuilder.bouwGemeente(1, "abc", null));
        overtredingen = validator.validate(persoon, Default.class);
        assertEquals(0, overtredingen.size());
    }

    @Test
    public void testBRAL2094() {
        PersoonAdresBericht persoonAdresBericht = new PersoonAdresBericht();
        persoonAdresBericht.setStandaard(new PersoonAdresStandaardGroepBericht());
        persoonAdresBericht.getStandaard().setPostcode(new PostcodeAttribuut("1000AB"));

        PersoonBericht persoon = new PersoonBericht();
        persoon.setSoort(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        persoon.setAdressen(new ArrayList<PersoonAdresBericht>());
        persoon.getAdressen().add(persoonAdresBericht);

        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<PersoonBericht>> overtredingen = validator.validate(persoon, Default.class);
        assertEquals(1, overtredingen.size());
        @SuppressWarnings("rawtypes")
        ConstraintViolation cv = overtredingen.iterator().next();
        assertEquals("BRAL2094", cv.getMessage());
        assertEquals(Regel.BRAL2094, haalRegelCodeOp(cv));

        persoonAdresBericht.getStandaard().setHuisnummer(new HuisnummerAttribuut(1));

        overtredingen = validator.validate(persoon, Default.class);
        cv = overtredingen.iterator().next();
        assertEquals(1, overtredingen.size());
        assertEquals("BRAL2094", cv.getMessage());
        assertEquals(Regel.BRAL2094, haalRegelCodeOp(cv));

        persoonAdresBericht.getStandaard().setHuisnummer(null);
        persoonAdresBericht.getStandaard().setNaamOpenbareRuimte(new NaamOpenbareRuimteAttribuut("abc"));
        persoonAdresBericht.getStandaard().setAfgekorteNaamOpenbareRuimte(
                new AfgekorteNaamOpenbareRuimteAttribuut("abc"));
        persoonAdresBericht.getStandaard().setIdentificatiecodeAdresseerbaarObject(
                new IdentificatiecodeAdresseerbaarObjectAttribuut("a"));
        persoonAdresBericht.getStandaard().setWoonplaatsnaam(StatischeObjecttypeBuilder.WOONPLAATS_AMSTERDAM);
        persoonAdresBericht.getStandaard().setGemeente(StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM);
        persoonAdresBericht.getStandaard().setIdentificatiecodeNummeraanduiding(
                new IdentificatiecodeNummeraanduidingAttribuut("1"));
        overtredingen = validator.validate(persoon, Default.class);
        assertEquals(2, overtredingen.size());
        bevatOvertredingen(overtredingen, Regel.BRAL2094, Regel.BRAL2083);

        persoonAdresBericht.getStandaard().setHuisnummer(new HuisnummerAttribuut(1));
        persoonAdresBericht.getStandaard().setNaamOpenbareRuimte(new NaamOpenbareRuimteAttribuut("abc"));

        overtredingen = validator.validate(persoon, Default.class);
        assertEquals(0, overtredingen.size());
    }

    private void
    bevatOvertredingen(final Set<ConstraintViolation<PersoonBericht>> overtredingen, final Regel... regels) {
        int gevondenOvertredingen = 0;
        for (@SuppressWarnings("rawtypes")
        ConstraintViolation overtreding : overtredingen)
        {
            if (Arrays.asList(regels).contains(haalRegelCodeOp(overtreding))) {
                gevondenOvertredingen++;
            } else {
                fail("Onverwachte overtreding " + overtreding.getMessage() + " gevonden");
            }
        }
        assertEquals(regels.length, gevondenOvertredingen);

    }

    @Test
    public void testBRAL2032() {
        PersoonAdresBericht persoonAdresBericht = new PersoonAdresBericht();
        persoonAdresBericht.setStandaard(new PersoonAdresStandaardGroepBericht());
        persoonAdresBericht.getStandaard().setSoort(new FunctieAdresAttribuut(FunctieAdres.WOONADRES));
        persoonAdresBericht.getStandaard().setDatumAanvangAdreshouding(new DatumEvtDeelsOnbekendAttribuut(20130524));
        persoonAdresBericht.getStandaard().setGemeente(StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM);
        persoonAdresBericht.getStandaard().setIdentificatiecodeAdresseerbaarObject(
                new IdentificatiecodeAdresseerbaarObjectAttribuut("adres"));
        persoonAdresBericht.getStandaard().setNaamOpenbareRuimte(new NaamOpenbareRuimteAttribuut("a"));
        persoonAdresBericht.getStandaard().setHuisnummer(new HuisnummerAttribuut(1));
        persoonAdresBericht.getStandaard().setIdentificatiecodeNummeraanduiding(
                new IdentificatiecodeNummeraanduidingAttribuut("b"));
        persoonAdresBericht.getStandaard().setWoonplaatsnaam(StatischeObjecttypeBuilder.WOONPLAATS_AMSTERDAM);
        persoonAdresBericht.getStandaard().setRedenWijziging(StatischeObjecttypeBuilder.RDN_WIJZ_ADRES_AMBT);

        PersoonBericht persoon = new PersoonBericht();
        persoon.setSoort(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        persoon.setAdressen(new ArrayList<PersoonAdresBericht>());
        persoon.getAdressen().add(persoonAdresBericht);

        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        // Als er geen land is opgegeven dan don't care of adresseerbaarObject is opgegeven
        persoonAdresBericht.getStandaard().setIdentificatiecodeAdresseerbaarObject(
                new IdentificatiecodeAdresseerbaarObjectAttribuut("adres"));
        Set<ConstraintViolation<PersoonBericht>> overtredingen = validator.validate(persoon, Default.class);
        assertEquals(0, overtredingen.size());

        // Als land NL is dan moet adresseerbaarObject ingevuld zijn, dus dit lever een fout op
        persoonAdresBericht.getStandaard().setIdentificatiecodeAdresseerbaarObject(null);
        persoonAdresBericht.getStandaard().setLandGebied(StatischeObjecttypeBuilder.LAND_NEDERLAND);
        overtredingen = validator.validate(persoon, Default.class);
        assertEquals(2, overtredingen.size());
        bevatOvertredingen(overtredingen, Regel.BRAL2032, Regel.BRAL2095);

        // Land NL met adresseerbaarObject dus dit gaat goed
        persoonAdresBericht.getStandaard().setIdentificatiecodeAdresseerbaarObject(
                new IdentificatiecodeAdresseerbaarObjectAttribuut("adres"));
        persoonAdresBericht.getStandaard().setLandGebied(StatischeObjecttypeBuilder.LAND_NEDERLAND);
        overtredingen = validator.validate(persoon, Default.class);
        assertEquals(0, overtredingen.size());

        // Bij niet in NL mag adresseerbaarObject niet opgegeven zijn
        persoonAdresBericht.getStandaard().setIdentificatiecodeAdresseerbaarObject(null);
        persoonAdresBericht.getStandaard().setNaamOpenbareRuimte(null);
        persoonAdresBericht.getStandaard().setHuisnummer(null);
        persoonAdresBericht.getStandaard().setIdentificatiecodeNummeraanduiding(null);
        persoonAdresBericht.getStandaard().setWoonplaatsnaam(null);
        persoonAdresBericht.getStandaard().setLandGebied(StatischeObjecttypeBuilder.LAND_BELGIE);
        overtredingen = validator.validate(persoon, Default.class);
        assertEquals(0, overtredingen.size());
    }

    @Test
    public void testBRAL2031() {
        PersoonAdresBericht persoonAdresBericht = new PersoonAdresBericht();
        persoonAdresBericht.setStandaard(new PersoonAdresStandaardGroepBericht());

        PersoonBericht persoon = new PersoonBericht();
        persoon.setSoort(new SoortPersoonAttribuut(SoortPersoon.INGESCHREVENE));
        persoon.setAdressen(new ArrayList<PersoonAdresBericht>());
        persoon.getAdressen().add(persoonAdresBericht);

        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        // Buiten NL en adresseerbaarObject ingevuld dus NOK
        persoonAdresBericht.getStandaard().setIdentificatiecodeAdresseerbaarObject(
                new IdentificatiecodeAdresseerbaarObjectAttribuut("adres"));
        persoonAdresBericht.getStandaard().setNaamOpenbareRuimte(new NaamOpenbareRuimteAttribuut("a"));
        persoonAdresBericht.getStandaard().setHuisnummer(new HuisnummerAttribuut(1));
        persoonAdresBericht.getStandaard().setIdentificatiecodeNummeraanduiding(
                new IdentificatiecodeNummeraanduidingAttribuut("b"));
        persoonAdresBericht.getStandaard().setWoonplaatsnaam(StatischeObjecttypeBuilder.WOONPLAATS_AMSTERDAM);
        persoonAdresBericht.getStandaard().setGemeente(StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM);
        persoonAdresBericht.getStandaard().setLandGebied(StatischeObjecttypeBuilder.LAND_BELGIE);
        Set<ConstraintViolation<PersoonBericht>> overtredingen = validator.validate(persoon, Default.class);
        assertEquals(1, overtredingen.size());
        @SuppressWarnings("rawtypes")
        ConstraintViolation cv = overtredingen.iterator().next();
        assertEquals("BRAL2031", cv.getMessage());
        assertEquals(Regel.BRAL2031, haalRegelCodeOp(cv));

        // Buiten NL en adresseerbaarObject niet ingevuld dus OK
        persoonAdresBericht.getStandaard().setIdentificatiecodeAdresseerbaarObject(null);
        persoonAdresBericht.getStandaard().setNaamOpenbareRuimte(null);
        persoonAdresBericht.getStandaard().setHuisnummer(null);
        persoonAdresBericht.getStandaard().setIdentificatiecodeNummeraanduiding(null);
        persoonAdresBericht.getStandaard().setWoonplaatsnaam(null);
        persoonAdresBericht.getStandaard().setGemeente(null);
        overtredingen = validator.validate(persoon, Default.class);
        assertEquals(0, overtredingen.size());

        // NL met adresseerbaarObject dus OK
        persoonAdresBericht.getStandaard().setIdentificatiecodeAdresseerbaarObject(
                new IdentificatiecodeAdresseerbaarObjectAttribuut("adres"));
        persoonAdresBericht.getStandaard().setNaamOpenbareRuimte(new NaamOpenbareRuimteAttribuut("a"));
        persoonAdresBericht.getStandaard().setHuisnummer(new HuisnummerAttribuut(1));
        persoonAdresBericht.getStandaard().setIdentificatiecodeNummeraanduiding(
                new IdentificatiecodeNummeraanduidingAttribuut("b"));
        persoonAdresBericht.getStandaard().setWoonplaatsnaam(StatischeObjecttypeBuilder.WOONPLAATS_AMSTERDAM);
        persoonAdresBericht.getStandaard().setLandGebied(StatischeObjecttypeBuilder.LAND_NEDERLAND);
        persoonAdresBericht.getStandaard().setSoort(new FunctieAdresAttribuut(FunctieAdres.WOONADRES));
        persoonAdresBericht.getStandaard().setDatumAanvangAdreshouding(new DatumEvtDeelsOnbekendAttribuut(20130524));
        persoonAdresBericht.getStandaard().setGemeente(StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM);
        persoonAdresBericht.getStandaard().setRedenWijziging(StatischeObjecttypeBuilder.RDN_WIJZ_ADRES_AMBT);

        overtredingen = validator.validate(persoon, Default.class);
        assertEquals(0, overtredingen.size());
    }

    private String maakVoornaam(final int lengte) {
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

        return naam;
    }

    private PersoonVoornaamBericht maakPersoonVoornaamBericht(final Integer volgnummer, final String naam) {
        PersoonVoornaamBericht voornaam = new PersoonVoornaamBericht();
        if (volgnummer != null) {
            voornaam.setVolgnummer(new VolgnummerAttribuut(volgnummer));
        }
        voornaam.setStandaard(new PersoonVoornaamStandaardGroepBericht());
        if (null != naam) {
            voornaam.getStandaard().setNaam(new VoornaamAttribuut(naam));
        }
        return voornaam;
    }

    private Regel haalRegelCodeOp(@SuppressWarnings("rawtypes") final ConstraintViolation cv) {
        return (Regel) cv.getConstraintDescriptor().getAttributes().get("code");
    }
}
