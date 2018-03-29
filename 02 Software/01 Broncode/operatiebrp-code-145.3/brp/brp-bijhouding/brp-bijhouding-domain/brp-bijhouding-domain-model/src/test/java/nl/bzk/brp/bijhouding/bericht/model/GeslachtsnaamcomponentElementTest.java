/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.concurrent.ExecutionException;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeslachtsnaamcomponent;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonGeslachtsnaamcomponentHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.AdellijkeTitel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Predicaat;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import org.junit.Test;

/**
 * Unittest voor {@link GeslachtsnaamcomponentElement}.
 */
public class GeslachtsnaamcomponentElementTest extends AbstractNaamTest {

    private static final String GESLACHTSNAAMSTAM = "geslachtsnaamstam";

    @Test
    public void testOngeldigVoorvoegselScheidingstekenPaar() {
        test(null, null, SCHEIDINGSTEKEN_ELEMENT, ONGELDIG_STRING_ELEMENT, Regel.R2161);
    }

    @Test
    public void testGeldigVoorvoegselScheidingstekenPaar() {
        test(null, null, SCHEIDINGSTEKEN_ELEMENT, VOORVOEGSEL_ELEMENT);
    }

    @Test
    public void valideerAdelijkeTitelBestaat() throws Exception {
        test(ADELIJKE_TITEL_ELEMENT, null, null, null);
    }

    @Test
    public void valideerPredicaatBestaat() throws Exception {
        test(null, PREDICAAT_ELEMENT, null, null);
    }

    @Test
    public void valideerPredicaatEnAdelijkeTitelBeideGevuldOngeldig() throws ExecutionException {
        test(ADELIJKE_TITEL_ELEMENT, PREDICAAT_ELEMENT, null, null, Regel.R2163);
    }

    @Test
    public void testWijzigPersoonGeslachtsnaamcomponentEntiteit() {
        //setup
        final BijhoudingPersoon persoon = BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE));
        final GeslachtsnaamcomponentElement
                element =
                maakGeslachtsnaamcomponentElement(ADELIJKE_TITEL_ELEMENT, null, SCHEIDINGSTEKEN_ELEMENT, VOORVOEGSEL_ELEMENT,
                        new StringElement(GESLACHTSNAAMSTAM));
        final AdministratieveHandeling administratieveHandeling = new AdministratieveHandeling(new Partij("test partij", "000000"),
                SoortAdministratieveHandeling.VOLTREKKING_HUWELIJK_IN_NEDERLAND, new Timestamp(System.currentTimeMillis()));
        final BRPActie actie = new BRPActie(SoortActie.REGISTRATIE_AANVANG_HUWELIJK, administratieveHandeling,
                administratieveHandeling.getPartij(), administratieveHandeling.getDatumTijdRegistratie());
        final int datumAanvangGeldigheid = 20010101;
        assertEquals(0, persoon.getPersoonGeslachtsnaamcomponentSet().size());
        //execute
        persoon.wijzigPersoonGeslachtsnaamcomponentEntiteit(element, actie, datumAanvangGeldigheid);
        //verify
        assertEquals(1, persoon.getPersoonGeslachtsnaamcomponentSet().size());
        final PersoonGeslachtsnaamcomponentHistorie geslachtsnaamcomponent = persoon.getPersoonGeslachtsnaamcomponentSet().iterator().next()
                .getPersoonGeslachtsnaamcomponentHistorieSet().iterator().next();
        assertEquals(GESLACHTSNAAMSTAM, geslachtsnaamcomponent.getStam());
        assertEquals(AdellijkeTitel.parseCode(ADELIJKE_TITEL_ELEMENT.getWaarde()), geslachtsnaamcomponent.getAdellijkeTitel());
        assertNull(geslachtsnaamcomponent.getPredicaat());
        assertEquals(VOORVOEGSEL_ELEMENT.getWaarde(), geslachtsnaamcomponent.getVoorvoegsel());
        assertEquals(SCHEIDINGSTEKEN_ELEMENT.getWaarde(), geslachtsnaamcomponent.getScheidingsteken());
    }

    @Test
    public void testWijzigPersoonGeslachtsnaamcomponentEntiteitBestaandGeslachtsnaamcomponent() {
        //setup
        final Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        final PersoonGeslachtsnaamcomponent geslachtsnaamcomponent = new PersoonGeslachtsnaamcomponent(persoon, 1);
        final PersoonGeslachtsnaamcomponentHistorie historie = new PersoonGeslachtsnaamcomponentHistorie(geslachtsnaamcomponent, "oud");
        geslachtsnaamcomponent.addPersoonGeslachtsnaamcomponentHistorie(historie);
        persoon.addPersoonGeslachtsnaamcomponent(geslachtsnaamcomponent);

        final GeslachtsnaamcomponentElement
                element =
                maakGeslachtsnaamcomponentElement(null, PREDICAAT_ELEMENT, SCHEIDINGSTEKEN_ELEMENT, VOORVOEGSEL_ELEMENT, new StringElement(GESLACHTSNAAMSTAM));
        final AdministratieveHandeling administratieveHandeling = new AdministratieveHandeling(new Partij("test partij", "000000"),
                SoortAdministratieveHandeling.VOLTREKKING_HUWELIJK_IN_NEDERLAND, new Timestamp(System.currentTimeMillis()));
        final BRPActie actie = new BRPActie(SoortActie.REGISTRATIE_AANVANG_HUWELIJK, administratieveHandeling,
                administratieveHandeling.getPartij(), administratieveHandeling.getDatumTijdRegistratie());
        final int datumAanvangGeldigheid = 20010101;
        assertEquals(1, persoon.getPersoonGeslachtsnaamcomponentSet().size());
        //execute
        BijhoudingPersoon.decorate(persoon).wijzigPersoonGeslachtsnaamcomponentEntiteit(element, actie, datumAanvangGeldigheid);
        //verify
        assertEquals(1, persoon.getPersoonGeslachtsnaamcomponentSet().size());
        final PersoonGeslachtsnaamcomponent geslachtsnaamcomponentResult = persoon.getPersoonGeslachtsnaamcomponentSet().iterator().next();
        assertEquals(3, geslachtsnaamcomponentResult.getPersoonGeslachtsnaamcomponentHistorieSet().size());
    }

    @Test
    public void valideerVoorvoegselOfScheidingstekenLeeg() throws Exception {
        test(null, null, SCHEIDINGSTEKEN_ELEMENT, null, Regel.R2228);
        test(null, null, null, VOORVOEGSEL_ELEMENT, Regel.R2228);
    }

    private void test(
            final StringElement adelijkeTitel,
            final StringElement predicaat,
            final CharacterElement scheidingsteken,
            final StringElement voorvoegsel,
            final Regel... regels) {
        final GeslachtsnaamcomponentElement element = maakGeslachtsnaamcomponentElement(adelijkeTitel, predicaat, scheidingsteken, voorvoegsel,
                new StringElement(GESLACHTSNAAMSTAM));
        controleerRegels(element.valideer(), regels);
    }

    @Test
    public void vergelijkElementenGelijk() {
        final GeslachtsnaamcomponentElement element1 = maakGeslachtsnaamcomponentElement(null, null, null, null, new StringElement(GESLACHTSNAAMSTAM));
        final GeslachtsnaamcomponentElement element2 = maakGeslachtsnaamcomponentElement(null, null, null, null, new StringElement(GESLACHTSNAAMSTAM));
        assertTrue(element1.vergelijkMetAndereGeslachtsNaamComponent(element2));
    }

    @Test
    public void vergelijkElementenScheidingsTekenNietGelijk() {
        final GeslachtsnaamcomponentElement element1 = maakGeslachtsnaamcomponentElement(null, null, null, null, new StringElement(GESLACHTSNAAMSTAM));
        final GeslachtsnaamcomponentElement
                element2 =
                maakGeslachtsnaamcomponentElement(null, null, new CharacterElement('-'), null, new StringElement(GESLACHTSNAAMSTAM));
        assertFalse(element1.vergelijkMetAndereGeslachtsNaamComponent(element2));
        assertFalse(element2.vergelijkMetAndereGeslachtsNaamComponent(element1));
    }

    @Test
    public void vergelijkElementenVoorvoegselNietGelijk() {
        final GeslachtsnaamcomponentElement element1 = maakGeslachtsnaamcomponentElement(null, null, null, null, new StringElement(GESLACHTSNAAMSTAM));
        final GeslachtsnaamcomponentElement
                element2 =
                maakGeslachtsnaamcomponentElement(null, null, null, new StringElement("de"), new StringElement(GESLACHTSNAAMSTAM));
        assertFalse(element1.vergelijkMetAndereGeslachtsNaamComponent(element2));
        assertFalse(element2.vergelijkMetAndereGeslachtsNaamComponent(element1));
    }

    @Test
    public void vergelijkElementenStamNietGelijk() {
        final GeslachtsnaamcomponentElement element1 = maakGeslachtsnaamcomponentElement(null, null, null, null, new StringElement(GESLACHTSNAAMSTAM));
        final GeslachtsnaamcomponentElement element2 = maakGeslachtsnaamcomponentElement(null, null, null, null, new StringElement("stammie"));
        assertFalse(element1.vergelijkMetAndereGeslachtsNaamComponent(element2));
        assertFalse(element2.vergelijkMetAndereGeslachtsNaamComponent(element1));
    }

    @Test
    public void vergelijkElementMetHistorieStamGelijk() {
        final GeslachtsnaamcomponentElement element1 = maakGeslachtsnaamcomponentElement(null, null, null, null, new StringElement(GESLACHTSNAAMSTAM));
        final PersoonGeslachtsnaamcomponentHistorie historie = maakEntiteit(null,null,null,null,GESLACHTSNAAMSTAM);
        assertTrue(element1.vergelijkMetPersoonsGeslachtsNaamComponent(historie));
    }

    @Test
    public void vergelijkElementMetHistorieStamGelijkVolledig() {
        final GeslachtsnaamcomponentElement element1 = maakGeslachtsnaamcomponentElement(new StringElement("B"),new StringElement( "J"),new CharacterElement('-'), new StringElement("de"), new StringElement(GESLACHTSNAAMSTAM));
        final PersoonGeslachtsnaamcomponentHistorie historie = maakEntiteit(AdellijkeTitel.B,Predicaat.J,'-',"de",GESLACHTSNAAMSTAM);
        assertTrue(element1.vergelijkMetPersoonsGeslachtsNaamComponent(historie));
    }

    @Test
    public void vergelijkElementMetHistorieScheidingsTekenVerschillend() {
        final GeslachtsnaamcomponentElement element1 = maakGeslachtsnaamcomponentElement(new StringElement("B"),new StringElement( "J"),new CharacterElement('-'), new StringElement("de"), new StringElement(GESLACHTSNAAMSTAM));
        final PersoonGeslachtsnaamcomponentHistorie historie = maakEntiteit(AdellijkeTitel.B,Predicaat.J,'/',"de",GESLACHTSNAAMSTAM);
        assertFalse(element1.vergelijkMetPersoonsGeslachtsNaamComponent(historie));
    }

    @Test
    public void vergelijkElementMetHistorieSVoorvoegselVerschillend() {
        final GeslachtsnaamcomponentElement element1 = maakGeslachtsnaamcomponentElement(new StringElement("B"),new StringElement( "J"),new CharacterElement('-'), new StringElement("de"), new StringElement(GESLACHTSNAAMSTAM));
        final PersoonGeslachtsnaamcomponentHistorie historie = maakEntiteit(AdellijkeTitel.B,Predicaat.J,'-',"van",GESLACHTSNAAMSTAM);
        assertFalse(element1.vergelijkMetPersoonsGeslachtsNaamComponent(historie));
    }

    @Test
    public void vergelijkElementMetHistorieStamVerschillend() {
        final GeslachtsnaamcomponentElement element1 = maakGeslachtsnaamcomponentElement(new StringElement("B"),new StringElement( "J"),new CharacterElement('-'), new StringElement("de"), new StringElement(GESLACHTSNAAMSTAM));
        final PersoonGeslachtsnaamcomponentHistorie historie = maakEntiteit(AdellijkeTitel.B,Predicaat.J,'-',"de","niet");
        assertFalse(element1.vergelijkMetPersoonsGeslachtsNaamComponent(historie));
    }


    private GeslachtsnaamcomponentElement maakGeslachtsnaamcomponentElement(final StringElement adelijkeTitel, final StringElement predicaat,
                                                                            final CharacterElement scheidingsteken, final StringElement voorvoegsel,
                                                                            final StringElement stam) {
        return new GeslachtsnaamcomponentElement(
                new AbstractBmrGroep.AttributenBuilder().communicatieId("ci_test").build(),
                predicaat,
                adelijkeTitel,
                voorvoegsel,
                scheidingsteken,
                stam);
    }


    private PersoonGeslachtsnaamcomponentHistorie maakEntiteit(final AdellijkeTitel aTitel, final Predicaat predicaat,
                                                               final Character scheidingsteken, final String voorvoegsel, final String stam) {
        Persoon persoon = new Persoon(SoortPersoon.INGESCHREVENE);
        PersoonGeslachtsnaamcomponent comp = new PersoonGeslachtsnaamcomponent(persoon, 1);
        final PersoonGeslachtsnaamcomponentHistorie historie = new PersoonGeslachtsnaamcomponentHistorie(comp, stam);
        historie.setPredicaat(predicaat);
        historie.setAdellijkeTitel(aTitel);
        historie.setScheidingsteken(scheidingsteken);
        historie.setVoorvoegsel(voorvoegsel);
        return historie;
    }
}
