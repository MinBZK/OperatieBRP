/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorieZonderVerantwoording;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonSamengesteldeNaamHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.AdellijkeTitel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Predicaat;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortPersoon;
import org.junit.Test;

/**
 * Unittest voor {@link SamengesteldeNaamElement}.
 */
public class SamengesteldeNaamElementTest extends AbstractNaamTest {

    @Test
    public void valideerScheidingstekenBijVoorvoegselGeldig() throws Exception {
        test(VOORVOEGSEL_ELEMENT, SCHEIDINGSTEKEN_ELEMENT, null, null, null, BooleanElement.NEE);
    }

    @Test
    public void valideerScheidingstekenBijVoorvoegselBeideLeegGeldig() throws Exception {
        test(null, null, null, null, null, BooleanElement.NEE);
    }

    @Test
    public void valideerScheidingstekenBijVoorvoegselVvLeegOngeldig() throws Exception {
        test(VOORVOEGSEL_ELEMENT, null, null, null, null, BooleanElement.NEE, Regel.R1672);
    }

    @Test
    public void valideerScheidingstekenBijVoorvoegselStLeegOngeldig() throws Exception {
        test(null, SCHEIDINGSTEKEN_ELEMENT, null, null, null, BooleanElement.NEE, Regel.R1672);
    }

    @Test
    public void testOngeldigVoorvoegselScheidingstekenPaar() {
        test(new StringElement("bla"), SCHEIDINGSTEKEN_ELEMENT, null, null, null, BooleanElement.NEE, Regel.R1676);
    }

    @Test
    public void valideerAdelijkeTitelGeldig() throws Exception {
        test(null, null, ADELIJKE_TITEL_ELEMENT, null, null, BooleanElement.NEE);
    }

    @Test
    public void valideerPredicaatGeldig() throws Exception {
        test(null, null, null, PREDICAAT_ELEMENT, null, BooleanElement.NEE);
    }

    @Test
    public void valideerPredicaatEnAdelijkeTitelBeideGevuldOngeldig() throws ExecutionException {
        test(null, null, ADELIJKE_TITEL_ELEMENT, PREDICAAT_ELEMENT, null, BooleanElement.NEE, Regel.R1673);
    }

    @Test
    public void valideerIndicatieNamenreeksNeeEnVoornamenGeldig() throws ExecutionException {
        test(null, null, null, null, VOORNAMEN_ELEMENT, BooleanElement.NEE);
    }

    @Test
    public void valideerIndicatieNamenreeksJaEnNullWaardesGeldig() throws ExecutionException {
        test(null, null, null, null, null, BooleanElement.JA);
    }

    @Test
    public void valideerIndicatieNamenreeksNeeEnVoorvoegselGeldig() throws ExecutionException {
        test(VOORVOEGSEL_ELEMENT, SCHEIDINGSTEKEN_ELEMENT, null, null, null, BooleanElement.NEE);
    }

    @Test
    public void valideerIndicatieNamenreeksEnVoorvoegselOnGeldig() throws ExecutionException {
        test(VOORVOEGSEL_ELEMENT, SCHEIDINGSTEKEN_ELEMENT, null, null, null, BooleanElement.JA, Regel.R1810);
    }

    @Test
    public void testMaakPersoonSamengesteldeNaamHistorieEntiteit() {
        final Predicaat predicaat = Predicaat.K;
        final String voornamen = "Jan Pieter";
        final String geslachtsnaamstam = "geslachtsnaamstam";
        final SamengesteldeNaamElement element =
                new SamengesteldeNaamElement(
                    new HashMap<>(0),
                    BooleanElement.NEE,
                    new StringElement(predicaat.getCode()),
                    new StringElement(voornamen),
                    null,
                    VOORVOEGSEL_ELEMENT,
                    SCHEIDINGSTEKEN_ELEMENT,
                    new StringElement(geslachtsnaamstam));
        final List<MeldingElement> meldingen = element.valideer();
        assertEquals(0, meldingen.size());
        final int datumAanvangGeldigheid = 20010101;
        final BijhoudingPersoon persoon = BijhoudingPersoon.decorate(new Persoon(SoortPersoon.INGESCHREVENE));
        persoon.voegPersoonSamengesteldeNaamHistorieToe(element, getActie(), datumAanvangGeldigheid);
        final PersoonSamengesteldeNaamHistorie entiteit =
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(persoon.getPersoonSamengesteldeNaamHistorieSet());
        assertEquals(persoon.isPersoonIngeschrevene(), entiteit.getIndicatieAfgeleid());
        assertEquals(false, entiteit.getIndicatieNamenreeks());
        assertEquals(predicaat, entiteit.getPredicaat());
        assertNull(entiteit.getAdellijkeTitel());
        assertEquals(voornamen, entiteit.getVoornamen());
        assertEquals(VOORVOEGSEL_ELEMENT.getWaarde(), entiteit.getVoorvoegsel());
        assertEquals(SCHEIDINGSTEKEN_ELEMENT.getWaarde(), entiteit.getScheidingsteken());
        assertEquals(geslachtsnaamstam, entiteit.getGeslachtsnaamstam());
        assertEquals(1, persoon.getPersoonSamengesteldeNaamHistorieSet().size());
        assertEquals(entiteit, persoon.getPersoonSamengesteldeNaamHistorieSet().iterator().next());
        assertEntiteitMetMaterieleHistorie(entiteit, datumAanvangGeldigheid);
    }

    @Test
    public void testGetInstance() {
        //setup
        final PersoonSamengesteldeNaamHistorie samengesteldeNaamHistorie = new PersoonSamengesteldeNaamHistorie(new Persoon(SoortPersoon.INGESCHREVENE), "stam", false, true);
        final BijhoudingVerzoekBericht verzoekBericht = mock(BijhoudingVerzoekBericht.class);
        //execute
        SamengesteldeNaamElement samengesteldeNaamElement = SamengesteldeNaamElement.getInstance(samengesteldeNaamHistorie, verzoekBericht);
        //verify
        assertEquals(samengesteldeNaamHistorie.getGeslachtsnaamstam(), BmrAttribuut.getWaardeOfNull(samengesteldeNaamElement.getGeslachtsnaamstam()));
        assertEquals(samengesteldeNaamHistorie.getIndicatieNamenreeks(), BmrAttribuut.getWaardeOfNull(samengesteldeNaamElement.getIndicatieNamenreeks()));
        assertNull(samengesteldeNaamElement.getVoornamen());
        assertNull(samengesteldeNaamElement.getPredicaatCode());
        assertNull(samengesteldeNaamElement.getAdellijkeTitelCode());
        assertNull(samengesteldeNaamElement.getVoorvoegsel());
        assertNull(samengesteldeNaamElement.getScheidingsteken());
        //setup optionele velden
        samengesteldeNaamHistorie.setVoornamen("voornaam");
        samengesteldeNaamHistorie.setPredicaat(Predicaat.H);
        samengesteldeNaamHistorie.setAdellijkeTitel(AdellijkeTitel.B);
        samengesteldeNaamHistorie.setVoorvoegsel("voorvoegsel");
        samengesteldeNaamHistorie.setScheidingsteken(' ');
        //execute
        samengesteldeNaamElement = SamengesteldeNaamElement.getInstance(samengesteldeNaamHistorie, verzoekBericht);
        //verify
        assertEquals(samengesteldeNaamHistorie.getVoornamen(), BmrAttribuut.getWaardeOfNull(samengesteldeNaamElement.getVoornamen()));
        assertEquals(samengesteldeNaamHistorie.getPredicaat().getCode(), BmrAttribuut.getWaardeOfNull(samengesteldeNaamElement.getPredicaatCode()));
        assertEquals(samengesteldeNaamHistorie.getAdellijkeTitel().getCode(), BmrAttribuut.getWaardeOfNull(samengesteldeNaamElement.getAdellijkeTitelCode()));
        assertEquals(samengesteldeNaamHistorie.getVoorvoegsel(), BmrAttribuut.getWaardeOfNull(samengesteldeNaamElement.getVoorvoegsel()));
        assertEquals(samengesteldeNaamHistorie.getScheidingsteken(), BmrAttribuut.getWaardeOfNull(samengesteldeNaamElement.getScheidingsteken()));
        //verify null voorkomen
        assertNull(SamengesteldeNaamElement.getInstance(null, verzoekBericht));
    }

    private void test(
        final StringElement voorvoegsel,
        final CharacterElement scheidingsteken,
        final StringElement adelijkeTitel,
        final StringElement predicaat,
        final StringElement voornamen,
        final BooleanElement indicatieNamenreeks,
        final Regel... regels)
    {
        final SamengesteldeNaamElement element =
                new SamengesteldeNaamElement(
                    new AbstractBmrGroep.AttributenBuilder().communicatieId("ci_test").build(),
                    indicatieNamenreeks,
                    predicaat,
                    voornamen,
                    adelijkeTitel,
                    voorvoegsel,
                    scheidingsteken,
                    new StringElement("geslachtsnaamstam"));
        controleerRegels(element.valideer(), regels);
    }
}
