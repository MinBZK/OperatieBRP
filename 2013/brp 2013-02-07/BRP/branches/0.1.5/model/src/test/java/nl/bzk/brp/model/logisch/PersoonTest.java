/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch;

import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;

import nl.bzk.brp.model.gedeeld.SoortIndicatie;
import nl.bzk.brp.model.logisch.groep.PersoonNationaliteit;
import org.junit.Assert;
import org.junit.Test;


/**
 * Unit test class voor de {@link Persoon} class, waarbij met name tests op de niet getter en setter methodes binnen
 * de {@link Persoon} class worden uitgevoerd.
 */
public class PersoonTest {

    // Identiteit

    @Test
    public void testGetterEnSetterId() {
        Persoon persoon = new Persoon();
        persoon.setIdentiteit(null);
        Assert.assertNull(persoon.getId());
        Assert.assertNull(persoon.getIdentiteit());

        persoon.setId(Long.valueOf(2));
        Assert.assertEquals(Long.valueOf(2), persoon.getId());
        Assert.assertNotNull(persoon.getIdentiteit());

        persoon.setId(Long.valueOf(3));
        Assert.assertEquals(Long.valueOf(3), persoon.getId());
    }


    // Indicaties

    @Test
    public void testToevoegenEnOphalenIndicaties() {
        Persoon persoon = new Persoon();
        Assert.assertNotNull(persoon.getIndicaties());
        Assert.assertEquals(0, persoon.getIndicaties().size());
        Assert.assertNull(persoon.getIndicatieWaarde(SoortIndicatie.VERSTREKKINGSBEPERKING));

        persoon.voegPersoonIndicatieToe(bouwPersoonIndicatie(SoortIndicatie.VERSTREKKINGSBEPERKING, Boolean.TRUE));
        Assert.assertEquals(1, persoon.getIndicaties().size());
        Assert.assertTrue(persoon.getIndicatieWaarde(SoortIndicatie.VERSTREKKINGSBEPERKING));

        persoon.voegPersoonIndicatieToe(bouwPersoonIndicatie(SoortIndicatie.ONDER_CURATELE, Boolean.FALSE));
        Assert.assertEquals(2, persoon.getIndicaties().size());
        Assert.assertFalse(persoon.getIndicatieWaarde(SoortIndicatie.ONDER_CURATELE));
    }

    @Test
    public void testIndicatiesAanwezig() {
        Persoon persoon = new Persoon();
        Assert.assertFalse(persoon.indicatiesAanwezig());

        persoon.voegPersoonIndicatieToe(bouwPersoonIndicatie(SoortIndicatie.ONDER_CURATELE, false));
        Assert.assertTrue(persoon.indicatiesAanwezig());
    }

    @Test
    public void testZettenAlleIndicatiesInEens() {
        Persoon persoon = new Persoon();
        Assert.assertNotNull(persoon.getIndicaties());
        Assert.assertEquals(0, persoon.getIndicaties().size());

        SortedSet<PersoonIndicatie> indicaties = new TreeSet<PersoonIndicatie>();
        indicaties.addAll(Arrays.asList(bouwPersoonIndicatie(SoortIndicatie.ONDER_CURATELE, true),
            bouwPersoonIndicatie(SoortIndicatie.GEPRIVILEGIEERDE, false)));
        persoon.voegPersoonIndicatiesToe(indicaties);

        Assert.assertNull(persoon.getIndicatieWaarde(SoortIndicatie.VERSTREKKINGSBEPERKING));
        Assert.assertTrue(persoon.getIndicatieWaarde(SoortIndicatie.ONDER_CURATELE));
        Assert.assertFalse(persoon.getIndicatieWaarde(SoortIndicatie.GEPRIVILEGIEERDE));
    }

    @Test
    public void testVerstrekkingsBeperking() {
        Persoon persoon = new Persoon();
        Assert.assertFalse(persoon.indicatiesVerstrekkingsbeperkingAanwezig());

        PersoonIndicatie verstrekkingsBeperking = new PersoonIndicatie();
        verstrekkingsBeperking.setSoort(SoortIndicatie.VERSTREKKINGSBEPERKING);
        verstrekkingsBeperking.setWaarde(false);

        persoon.voegPersoonIndicatiesToe(verstrekkingsBeperking);

        Assert.assertTrue(persoon.indicatiesVerstrekkingsbeperkingAanwezig());
        Assert.assertFalse(persoon.getIndicatiesVerstrekkingsbeperking().getWaarde());
    }

    @Test
    public void testMissendeVerstrekkingsBeperking() {
        Persoon persoon = new Persoon();
        Assert.assertFalse(persoon.indicatiesVerstrekkingsbeperkingAanwezig());
        Assert.assertNull(persoon.getIndicatiesVerstrekkingsbeperking());
    }

    /**
     * Instantieert en retourneert een {@link PersoonIndicatie} waarbij de opgegeven soort en waarde worden gebruikt
     * voor het zetten van de velden van de persoon indicatie.
     *
     * @param soort de soort van de indicatie.
     * @param waarde de waarde van de indicatie.
     * @return een persoon indicatie instantie.
     */
    private PersoonIndicatie bouwPersoonIndicatie(final SoortIndicatie soort, final Boolean waarde) {
        PersoonIndicatie indicatie = new PersoonIndicatie();
        indicatie.setSoort(soort);
        indicatie.setWaarde(waarde);
        return indicatie;
    }


    // Geslachtsnaam componenten

    @Test
    public void testToevoegenGeslachtsnaamComponenten() {
        Persoon persoon = new Persoon();
        Assert.assertNotNull(persoon.getGeslachtsnaamcomponenten());
        Assert.assertTrue(persoon.getGeslachtsnaamcomponenten().isEmpty());

        persoon.voegGeslachtsnaamcomponentToe(bouwPersoonGeslachtsnaamcomponent(1, "Test"));
        persoon.voegGeslachtsnaamcomponentToe(bouwPersoonGeslachtsnaamcomponent(2, "Test"));
        Assert.assertEquals(2, persoon.getGeslachtsnaamcomponenten().size());
    }

    @Test
    public void testToevoegenIdentiekeGeslachtsnaamComponenten() {
        Persoon persoon = new Persoon();
        Assert.assertNotNull(persoon.getGeslachtsnaamcomponenten());
        Assert.assertTrue(persoon.getGeslachtsnaamcomponenten().isEmpty());

        persoon.voegGeslachtsnaamcomponentToe(bouwPersoonGeslachtsnaamcomponent(1, "Test"));
        persoon.voegGeslachtsnaamcomponentToe(bouwPersoonGeslachtsnaamcomponent(1, "Test"));
        Assert.assertEquals(1, persoon.getGeslachtsnaamcomponenten().size());
    }

    @Test
    public void testToevoegenNullVoorGeslachtsnaamComponent() {
        Persoon persoon = new Persoon();
        Assert.assertNotNull(persoon.getGeslachtsnaamcomponenten());
        Assert.assertTrue(persoon.getGeslachtsnaamcomponenten().isEmpty());

        persoon.voegGeslachtsnaamcomponentToe(null);
        Assert.assertTrue(persoon.getGeslachtsnaamcomponenten().isEmpty());
    }

    /**
     * Instantieert en retourneert een {@link PersoonGeslachtsnaamcomponent} waarbij het opgegeven volgnummer en
     * naam worden gebruikt voor het zetten van de velden van het persoon geslachtsnaam component.
     *
     * @param volgNummer het volgnummer van het geslachtsnaam component
     * @param naam de naam van het geslachtsnaam component
     * @return een persoon geslachtsnaam component instantie.
     */
    private PersoonGeslachtsnaamcomponent bouwPersoonGeslachtsnaamcomponent(final int volgNummer, final String naam) {
        PersoonGeslachtsnaamcomponent persoonGeslachtsnaamcomponent = new PersoonGeslachtsnaamcomponent();
        persoonGeslachtsnaamcomponent.setNaam(naam);
        persoonGeslachtsnaamcomponent.setVolgnummer(volgNummer);
        return persoonGeslachtsnaamcomponent;
    }


    // Voornamen

    @Test
    public void testToevoegenVoornamen() {
        Persoon persoon = new Persoon();
        Assert.assertNotNull(persoon.getPersoonVoornamen());
        Assert.assertTrue(persoon.getPersoonVoornamen().isEmpty());

        persoon.voegPersoonVoornaamToe(bouwVoornaam(1, "Test"));
        persoon.voegPersoonVoornaamToe(bouwVoornaam(2, "Test"));
        Assert.assertEquals(2, persoon.getPersoonVoornamen().size());
    }

    @Test
    public void testToevoegenIdentiekeVoornamen() {
        Persoon persoon = new Persoon();
        Assert.assertNotNull(persoon.getPersoonVoornamen());
        Assert.assertTrue(persoon.getPersoonVoornamen().isEmpty());

        persoon.voegPersoonVoornaamToe(bouwVoornaam(1, "Test"));
        persoon.voegPersoonVoornaamToe(bouwVoornaam(1, "Test"));
        Assert.assertEquals(1, persoon.getPersoonVoornamen().size());
    }

    @Test
    public void testToevoegenNullVoorVoornaam() {
        Persoon persoon = new Persoon();
        Assert.assertNotNull(persoon.getPersoonVoornamen());
        Assert.assertTrue(persoon.getPersoonVoornamen().isEmpty());

        persoon.voegPersoonVoornaamToe(null);
        Assert.assertTrue(persoon.getPersoonVoornamen().isEmpty());
    }

    @Test
    public void testTestMethodes() {
        Persoon persoon = new Persoon();

        Assert.assertFalse(persoon.heeftGeslachtsnaamComponenten());
        Assert.assertFalse(persoon.heeftNationaliteiten());
        Assert.assertFalse(persoon.heeftVoornamen());
        Assert.assertFalse(persoon.heeftBetrokkenheden());

        persoon.voegGeslachtsnaamcomponentToe(new PersoonGeslachtsnaamcomponent());
        persoon.getNationaliteiten().add(new PersoonNationaliteit());
        persoon.voegPersoonVoornaamToe(new PersoonVoornaam());
        persoon.voegKindBetrokkenHeidToe(new Betrokkenheid());

        Assert.assertTrue(persoon.heeftGeslachtsnaamComponenten());
        Assert.assertTrue(persoon.heeftNationaliteiten());
        Assert.assertTrue(persoon.heeftVoornamen());
        Assert.assertTrue(persoon.heeftBetrokkenheden());
    }

    /**
     * Instantieert en retourneert een {@link PersoonVoornaam} waarbij het opgegeven volgnummer en
     * naam worden gebruikt voor het zetten van de velden van de voornaam
     *
     * @param volgNummer het volgnummer van de voornaam
     * @param naam de naam van de voornaam
     * @return een persoon voornaam instantie.
     */
    private PersoonVoornaam bouwVoornaam(final int volgNummer, final String naam) {
        PersoonVoornaam persoonVoornaam = new PersoonVoornaam();
        persoonVoornaam.setNaam(naam);
        persoonVoornaam.setVolgnummer(volgNummer);
        return persoonVoornaam;
    }

}
