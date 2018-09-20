/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.momentview;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Nationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortBetrokkenheid;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestNationaliteitBuilder;
import nl.bzk.brp.model.hisvolledig.impl.kern.BetrokkenheidHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.HuwelijkHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PartnerHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Betrokkenheid;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.logisch.kern.PersoonNationaliteit;
import nl.bzk.brp.util.hisvolledig.kern.HuwelijkHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonNationaliteitHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * Deze test is gemaakt naar aanleiding van ROMEO-695. Als een 1-op-N associatie
 * ergens in het verleden geldig was, maar nu niet meer, dan moet deze ook niet terug
 * te zien zijn in de 'slice'. Deze test maakt een dergelijke situatie na met behulp
 * van een testPersoon met nationaliteiten.
 * <p/>
 * Dit principe is tevens van toepassing op betrokkenheden en relaties.
 * Voor zover er groepen aanwezig zijn, worden deze gebruikt. Indien er geen groep is,
 * kan een eigen interpretatie gegeven worden in de user klasse.
 */
public class FilteringOpActueleGegevensTest {

    private static final short VENUSIAANSE_NATIONALITEIT_CODE                    = 1234;
    private static final short VENUSIAANSE_NATIONALITEIT_REDEN_VERKRIJGING_CODE  = 2345;
    private static final short MERCURIAANSE_NATIONALITEIT_CODE                   = 3456;
    private static final short MERCURIAANSE_NATIONALITEIT_REDEN_VERKRIJGING_CODE = 4567;

    private static final int HUWELIJKSPARTNER1_GEBOORTEDATUM = 19910101;
    private static final int HUWELIJKSPARTNER2_GEBOORTEDATUM = 19920101;

    private PersoonHisVolledigImpl testPersoon;
    private PersoonHisVolledigImpl huwelijkspartner1;
    private PersoonHisVolledigImpl huwelijkspartner2;

    /**
     * De persoon die hier gemaakt wordt:
     * <p/>
     * Heeft 2 geboorte records:
     * Op 2002-01-01 is de bekende geboortedatum 2002-01-01
     * Per 2009-01-01 is de bekende geboortedatum 2001-01-01
     * <p/>
     * Heeft 2 nationaliteiten in zijn geschiedenis:
     * Van 2001-01-01 tot 2009-01-01 had deze persoon de nationaliteit van Venus, registratie op 2001-01-01
     * Van 2009-01-01 tot heden heeft deze persoon de nationaliteit van Mercurius, registratie op 2009-01-01
     * <p/>
     * Heeft 2 huwelijken in zijn geschiedenis:
     * Van 2003-01-01 tot 2008-01-01 was deze persoon getrouwd met huwelijkspartner1, registratie op 2003-01-01
     * Van 2010-01-01 tot heden was deze persoon getrouwd met huwelijkspartner2, registratie op 2010-01-01
     * <p/>
     * TODO: Eigenlijk zou de huwelijkstest gebruik moeten maken van datum einde in de standaard groep en
     * niet van datum tijdstip verval.
     * <p/>
     * NB: Ja, het is een beetje raar dat deze persoon in 2001 al een nationaliteit had, terwijl
     * hij/zij/het nog niet geboren was en 2 jaar na geboorte trouwt, maar goed, dat is niet van belang voor deze test.
     */
    @Before
    public void init() {
        maakPersoonAan();
        bouwNationaliteitHistorie();
        bouwHuwelijkHistorie();
    }

    @Test
    public void testSliceInVerleden() {
        @SuppressWarnings("deprecation")
        PersoonView persoonIn2005MetKennisVan2005 =
                new PersoonView(testPersoon, new DatumTijdAttribuut(new Date(2005 - 1900, 0, 1)), new DatumAttribuut(
                        20050101));

        // De geboortedatum is in 2005 bekend als 2002.
        Assert.assertEquals(20020101, persoonIn2005MetKennisVan2005.getGeboorte().getDatumGeboorte().getWaarde()
                .intValue());

        // Er is maar 1 'actieve' nationaliteit bekend in 2005, namelijk die van Venus.
        assertEenNationaliteit(persoonIn2005MetKennisVan2005, VENUSIAANSE_NATIONALITEIT_CODE,
                VENUSIAANSE_NATIONALITEIT_REDEN_VERKRIJGING_CODE);

        // Er is maar 1 'actief' huwelijk bekend in 2013, namelijk die met huwelijkspartner2.
        assertEenHuwelijk(persoonIn2005MetKennisVan2005, HUWELIJKSPARTNER1_GEBOORTEDATUM);
    }

    @Test
    public void testSliceNu() {
        @SuppressWarnings("deprecation")
        PersoonView persoonIn2013MetKennisVan2013 =
                new PersoonView(testPersoon, new DatumTijdAttribuut(new Date(2013 - 1900, 0, 1)), new DatumAttribuut(
                        20130101));

        // De geboortedatum is in 2013 bekend als 2001.
        Assert.assertEquals(20010101, persoonIn2013MetKennisVan2013.getGeboorte().getDatumGeboorte().getWaarde()
                .intValue());

        // Er is maar 1 'actieve' nationaliteit bekend in 2013, namelijk die van Mercurius.
        assertEenNationaliteit(persoonIn2013MetKennisVan2013, MERCURIAANSE_NATIONALITEIT_CODE,
                MERCURIAANSE_NATIONALITEIT_REDEN_VERKRIJGING_CODE);

        // Er is maar 1 'actief' huwelijk bekend in 2013, namelijk die met huwelijkspartner2.
        assertEenHuwelijk(persoonIn2013MetKennisVan2013, HUWELIJKSPARTNER2_GEBOORTEDATUM);
    }

    /**
     * Check op 1 nationaliteit met de juiste code en tevens een gevulde standaard groep met
     * de juiste reden verkrijging code.
     */
    private void assertEenNationaliteit(final Persoon pers, final short nationaliteitCode,
            final short redenVerkrijgingCode)
    {
        Assert.assertEquals(1, pers.getNationaliteiten().size());
        PersoonNationaliteit persoonNationaliteit = pers.getNationaliteiten().iterator().next();
        Assert.assertEquals(nationaliteitCode, persoonNationaliteit.getNationaliteit().getWaarde().getCode()
                .getWaarde().shortValue());
        Assert.assertEquals(redenVerkrijgingCode, persoonNationaliteit.getStandaard().getRedenVerkrijging().getWaarde()
                .getCode().getWaarde().shortValue());
    }

    /**
     * Check dat de opgegeven persoon slechts 1 huwelijk heeft en hierin de partner de opgegeven geboorte datum heeft.
     *
     * @param pers                 de persoon waarvoor het huwelijk gecontroleerd dient te worden.
     * @param geboorteDatumPartner de datum waarop de partner geboren zou moeten zijn.
     */
    private void assertEenHuwelijk(final Persoon pers, final Integer geboorteDatumPartner) {
        Assert.assertEquals(1, pers.getBetrokkenheden().size());
        Assert.assertFalse(haalPartnersUitPersoon(pers).isEmpty());
        Assert.assertEquals(geboorteDatumPartner, haalPartnersUitPersoon(pers).iterator().next().getGeboorte()
                .getDatumGeboorte().getWaarde());
    }

    /**
     * Instantieert een nieuwe instantie voor de lokale <code>persoon</code> variabele en 'vult' deze met standaard
     * geboorte informatie.
     */
    private void maakPersoonAan() {
        testPersoon =
                new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE).nieuwGeboorteRecord(20020101)
                        .datumGeboorte(20020101).eindeRecord().nieuwGeboorteRecord(20090101).datumGeboorte(20010101)
                        .eindeRecord().build();
    }

    /**
     * Voegt enkele standaard nationaliteiten toe aan de lokale <code>persoon</code> variabele.
     */
    private void bouwNationaliteitHistorie() {
        Nationaliteit venusiaanseNationaliteit = TestNationaliteitBuilder.maker().metCode(VENUSIAANSE_NATIONALITEIT_CODE).maak();
        Nationaliteit mercuriaanseNationaliteit = TestNationaliteitBuilder.maker().metCode(MERCURIAANSE_NATIONALITEIT_CODE).maak();
        testPersoon.getNationaliteiten().add(
                new PersoonNationaliteitHisVolledigImplBuilder(venusiaanseNationaliteit)
                        .nieuwStandaardRecord(20010101, 20090101, 20010101)
                        .redenVerkrijging(VENUSIAANSE_NATIONALITEIT_REDEN_VERKRIJGING_CODE).eindeRecord().build());
        testPersoon.getNationaliteiten().add(
                new PersoonNationaliteitHisVolledigImplBuilder(mercuriaanseNationaliteit)
                        .nieuwStandaardRecord(20090101, null, 20090101)
                        .redenVerkrijging(MERCURIAANSE_NATIONALITEIT_REDEN_VERKRIJGING_CODE).eindeRecord().build());
    }

    /**
     * Voegt huwelijks informatie toe aan de lokale <code>persoon</code> variabele.
     */
    @SuppressWarnings("deprecation")
    private void bouwHuwelijkHistorie() {
        HuwelijkHisVolledigImpl huwelijk1 =
                new HuwelijkHisVolledigImplBuilder().nieuwStandaardRecord(20030101).eindeRecord().build();
        huwelijk1.getRelatieHistorie().verval(null, new DatumTijdAttribuut(new Date(2008 - 1900, 0, 1)));
        HuwelijkHisVolledigImpl huwelijk2 =
                new HuwelijkHisVolledigImplBuilder().nieuwStandaardRecord(20100101).eindeRecord().build();
        huwelijkspartner1 =
                new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                        .nieuwGeboorteRecord(HUWELIJKSPARTNER1_GEBOORTEDATUM)
                        .datumGeboorte(HUWELIJKSPARTNER1_GEBOORTEDATUM).eindeRecord().build();
        BetrokkenheidHisVolledigImpl huwelijk1partner1 = new PartnerHisVolledigImpl(huwelijk1, huwelijkspartner1);
        huwelijkspartner1.getBetrokkenheden().add(huwelijk1partner1);
        BetrokkenheidHisVolledigImpl huwelijk1partner2 = new PartnerHisVolledigImpl(huwelijk1, testPersoon);
        testPersoon.getBetrokkenheden().add(huwelijk1partner2);
        huwelijk1.getBetrokkenheden().add(huwelijk1partner1);
        huwelijk1.getBetrokkenheden().add(huwelijk1partner2);

        huwelijkspartner2 =
                new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                        .nieuwGeboorteRecord(HUWELIJKSPARTNER2_GEBOORTEDATUM)
                        .datumGeboorte(HUWELIJKSPARTNER2_GEBOORTEDATUM).eindeRecord().build();
        BetrokkenheidHisVolledigImpl huwelijk2partner1 = new PartnerHisVolledigImpl(huwelijk2, huwelijkspartner2);
        huwelijkspartner2.getBetrokkenheden().add(huwelijk2partner1);
        BetrokkenheidHisVolledigImpl huwelijk2partner2 = new PartnerHisVolledigImpl(huwelijk2, testPersoon);
        testPersoon.getBetrokkenheden().add(huwelijk2partner2);
        huwelijk2.getBetrokkenheden().add(huwelijk2partner1);
        huwelijk2.getBetrokkenheden().add(huwelijk2partner2);
    }

    /**
     * Haalt de partners op van de opgegeven persoon voor alle (partner) relaties waarin de persoon betrokken is.
     *
     * @param persoon de persoon waarvoor de partners opgehaald/geretourneerd dienen te worden.
     * @return een lijst van personen die partner zijn van de opgegeven persoon.
     */
    private List<Persoon> haalPartnersUitPersoon(final Persoon persoon) {
        List<Persoon> partners = new ArrayList<Persoon>();
        for (Betrokkenheid betrokkenheid : persoon.getBetrokkenheden()) {
            if (betrokkenheid.getRol().getWaarde() == SoortBetrokkenheid.PARTNER) {
                for (Betrokkenheid indirecteBetrokkenheid : betrokkenheid.getRelatie().getBetrokkenheden()) {
                    if (indirecteBetrokkenheid.getPersoon().getGeboorte().getDatumGeboorte().getWaarde()
                            != betrokkenheid
                            .getPersoon().getGeboorte().getDatumGeboorte().getWaarde())
                    {
                        partners.add(indirecteBetrokkenheid.getPersoon());
                    }
                }
            }
        }
        return partners;
    }

}
