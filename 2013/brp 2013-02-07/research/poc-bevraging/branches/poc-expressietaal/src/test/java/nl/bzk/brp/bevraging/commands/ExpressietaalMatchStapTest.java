/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nl.bzk.brp.bevraging.app.ContextParameterNames;
import nl.bzk.brp.bevraging.app.support.Abonnement;
import nl.bzk.brp.bevraging.app.support.Afnemer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Datum;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GemeenteCode;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaarde;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Nationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPartij;
import nl.bzk.brp.model.bericht.kern.PersoonAdresBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonNationaliteitBericht;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import nl.bzk.brp.util.PersoonAdresBuilder;
import nl.bzk.brp.util.PersoonBuilder;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import org.apache.commons.chain.Context;
import org.apache.commons.chain.impl.ContextBase;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(MockitoJUnitRunner.class)
public class ExpressietaalMatchStapTest {

    @InjectMocks
    private ExpressietaalMatchStap expressietaalMatchStap = new ExpressietaalMatchStap();

    @Test
    public void shouldReturnPersoonsLijst() throws Exception {
        // given
        Context context = new ContextBase();
        context.put(ContextParameterNames.AANTAL_THREADS, 2);
        context.put(ContextParameterNames.AANTAL_PERSOONSLIJSTEN, 10);

        List<Afnemer> testAfnemers = getTestAfnemers();
        context.put(ContextParameterNames.AFNEMERLIJST, testAfnemers);

        List<Persoon> testPersonen = getTestPersonen();
        context.put(ContextParameterNames.PERSONENLIJST, testPersonen);

        // when
        expressietaalMatchStap.execute(context);

        // then
        List<BevraagInfo> average = (List) context.get(ContextParameterNames.TASK_INFO_LIJST);
        Assert.assertThat(average.size(), Matchers.is(2));
    }

    private List<Persoon> getTestPersonen() {
        List<Persoon> testPersonen = new ArrayList<Persoon>();
        Persoon testPersoon1 = maakPersoonMetPlaats(19500101, "Pieter", "Jansen", 19990101, "Turkse", "3521TK", "37");
        Persoon testPersoon2 = maakPersoonMetPlaats(19491231, "Karel", "Jansen", 19990101, "Turkse", "3521TK", "37");
        Persoon testPersoon3 = maakPersoonMetPlaats(20000201, "Karel", "Jansen", 19990101, "Nederlandse", "5555ZZ", "12");
        Persoon testPersoon4 = maakPersoonMetPlaats(19600605, "Karel", "Jansen", 19990101, "Nederlandse", "5555ZZ", "12");
        Persoon testPersoon5 = maakPersoonMetPlaats(19701107, "Karel", "Jansen", 20110101, "Nederlandse", "5555ZZ", "12");
        Persoon testPersoon6 = maakPersoonMetPlaats(19440101, "Anita", "Jansen", 20100101, "Nederlandse", "5555ZZ", "12");

        testPersonen.add(testPersoon1);
        testPersonen.add(testPersoon2);
        testPersonen.add(testPersoon3);
        testPersonen.add(testPersoon4);
        testPersonen.add(testPersoon5);
        testPersonen.add(testPersoon6);

        return testPersonen;
    }

    private List<Afnemer> getTestAfnemers() {
        Abonnement abonnement1 = new Abonnement("Personen geboren na 1950.", "persoon.geboorte.datum >= #1950-01-01#");    //4
        Abonnement abonnement2 = new Abonnement("Personen geboren voor 1950, met de eerste voornaam Karel.",              //1
                "persoon.geboorte.datum < #1950-01-01# EN persoon.voornamen[1].naam = \"Karel\"");
        Abonnement abonnement3 =                                                                                          //1
                new Abonnement("Personen met de voornaam Pieter en achternaam Jansen.",
                        "persoon.voornamen[1].naam = \"Pieter\" EN persoon.geslachtsnaamcomponenten[1].naam = \"Jansen\"");
        Abonnement abonnement4 =                                                                                          //1
                new Abonnement("Personen geboren in het jaar 2000.", "JAAR(persoon.geboorte.datum) = 2000");
        Abonnement abonnement5 =                                                                                          //1
                new Abonnement("Personen geboren in de maand februari.", "MAAND(persoon.geboorte.datum) = 2");
        Abonnement abonnement6 = new Abonnement("Personen verhuisd na oktober 2010.",                                     //1
                "persoon.adressen[1].datum_aanvang_adreshouding > #2010-OKT-01#");
        //Abonnement abonnement7 =                                                                                          //2
        //        new Abonnement("Personen met de Turkse nationaliteit.", "persoon.nationaliteiten[1].nationaliteit = \"Turkse\"");
        Abonnement abonnement8 = new Abonnement("Personen geboren op een van 3 specifieke data.",
                "persoon.geboorte.datum = #1950-01-01# OF persoon.geboorte.datum = #1960-07-05# OF persoon.geboorte.datum = #1970-11-07#");       //3
        Abonnement abonnement9 = new Abonnement("Personen die de postcode 3521TK hebben.",                                         //2
                "persoon.adressen[1].postcode = \"3521TK\"");
        Abonnement abonnement10 = new Abonnement("Personen die op huisnummer 12 wonen.",                                  //4
                "persoon.adressen[1].huisnummer = \"12\"");
        Abonnement abonnement11 = new Abonnement("Personen die op huisnummer 12 wonen en als eerste voornaam Henk of Anita hebben.",    //1
                "persoon.adressen[1].huisnummer = \"12\" EN persoon.voornamen[1].naam = \"Henk\" OF persoon.voornamen[1].naam = \"Anita\"");

        List<Afnemer> testAfnemers = new ArrayList<Afnemer>();
        testAfnemers.add(new Afnemer("Afnemer 1", Arrays
                .asList(abonnement1, abonnement2, abonnement3, abonnement4, abonnement5)));
        testAfnemers.add(new Afnemer("Afnemer 2", Arrays.asList(abonnement6, abonnement8, abonnement9, abonnement10, abonnement11)));
        return testAfnemers;
    }

    private Persoon maakPersoonMetPlaats(final Integer geboorteDatum, final String voornaam, final String achternaam,
                                         final Integer verhuisDatum, final String nationaliteit, final String postcode,
                                         final String huisnummer) {
        Partij gemeente = maakGemeente("24", "Utrecht", 19920101, 200020101);

        PersoonBericht persoon =
                PersoonBuilder.bouwPersoon("1234567890", Geslachtsaanduiding.MAN, geboorteDatum, null, voornaam, null, achternaam);

        PersoonAdresBericht paBericht =
                PersoonAdresBuilder.bouwWoonadres("NOR", huisnummer, postcode, StatischeObjecttypeBuilder.WOONPLAATS_AMSTERDAM,
                        gemeente, verhuisDatum);
        paBericht.setVerzendendId("adres1");
        persoon.setAdressen(new ArrayList<PersoonAdresBericht>());
        persoon.getAdressen().add(paBericht);

        PersoonModel persoonModel = new PersoonModel(persoon);

        Nationaliteit nation = StatischeObjecttypeBuilder.bouwNationaliteit("1111", nationaliteit);
        PersoonNationaliteitBericht persoonNationaliteit = PersoonBuilder.bouwPersoonNationaliteit(nation);
        PersoonBuilder.voegNationaliteitenToe(persoonModel, persoonNationaliteit);

        return persoon;
    }

    private Partij maakGemeente(final String code, final String naam, final Integer begin, final Integer eind) {
        Datum datumAanvang = null;
        if (begin != null) {
            datumAanvang = new Datum(begin);
        }
        Datum datumEind = null;
        if (eind != null) {
            datumEind = new Datum(eind);
        }

        Partij gemeente = new Partij(new NaamEnumeratiewaarde(naam), SoortPartij.GEMEENTE, new GemeenteCode(code), datumEind, datumAanvang, null, null, null, null, null);

        return gemeente;
    }
}
