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
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VolgnummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Bijhoudingsaard;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Geslachtsaanduiding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.util.DatumTijdUtil;
import nl.bzk.brp.util.DatumUtil;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonAdresHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonGeslachtsnaamcomponentHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonNationaliteitHisVolledigImplBuilder;
import nl.bzk.brp.util.hisvolledig.kern.PersoonVoornaamHisVolledigImplBuilder;
import org.apache.commons.chain.Context;
import org.apache.commons.chain.impl.ContextBase;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

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
        Persoon testPersoon1 = maakPersoonMetPlaats(19500101, "Pieter", "Jansen", 19990101, "Turkse", "3521TK", 37);
        Persoon testPersoon2 = maakPersoonMetPlaats(19491231, "Karel", "Jansen", 19990101, "Turkse", "3521TK", 37);
        Persoon testPersoon3 =
                maakPersoonMetPlaats(20000201, "Karel", "Jansen", 19990101, "Nederlandse", "5555ZZ", 12);
        Persoon testPersoon4 =
                maakPersoonMetPlaats(19600605, "Karel", "Jansen", 19990101, "Nederlandse", "5555ZZ", 12);
        Persoon testPersoon5 =
                maakPersoonMetPlaats(19701107, "Karel", "Jansen", 20110101, "Nederlandse", "5555ZZ", 12);
        Persoon testPersoon6 =
                maakPersoonMetPlaats(19440101, "Anita", "Jansen", 20100101, "Nederlandse", "5555ZZ", 12);

        testPersonen.add(testPersoon1);
        testPersonen.add(testPersoon2);
        testPersonen.add(testPersoon3);
        testPersonen.add(testPersoon4);
        testPersonen.add(testPersoon5);
        testPersonen.add(testPersoon6);

        return testPersonen;
    }

    private List<Afnemer> getTestAfnemers() {
        Abonnement abonnement1 =
                new Abonnement("Personen geboren na 1950.", "geboorte.datum > 1950/01/01");    //4
        Abonnement abonnement2 =
                new Abonnement("Personen geboren voor 1950, met de eerste voornaam Karel.",              //1
                               "geboorte.datum < 1950/01/01 EN ER_IS(voornamen, v, v.naam = \"Karel\")");
        Abonnement abonnement3 =
                //1
                new Abonnement("Personen met de voornaam Pieter en achternaam Jansen.",
                               "ER_IS(voornamen, v, v.naam = \"Pieter\") EN ER_IS(geslachtsnaamcomponenten, g, g.naam = \"Jansen\")");
        Abonnement abonnement4 =
                //1
                new Abonnement("Personen geboren in het jaar 2000.", "JAAR(geboorte.datum) = 2000");
        Abonnement abonnement5 =
                //1
                new Abonnement("Personen geboren in de maand februari.", "MAAND(geboorte.datum) = 2");
        Abonnement abonnement6 =
                new Abonnement("Personen verhuisd na oktober 2010.",                                     //1
                               "ER_IS(adressen, a, a.datum_aanvang_adreshouding > 2010/OKT/01)");
        Abonnement abonnement8 = new Abonnement("Personen geboren op een van 3 specifieke data.",
                                                "geboorte.datum = 1950/01/01 OF geboorte.datum = 1960/07/05 OF geboorte.datum = 1970/11/07");       //3
        Abonnement abonnement9 =
                new Abonnement("Personen die de postcode 3521TK hebben.",                                         //2
                               "ER_IS(adressen, a, a.postcode = \"3521TK\")");
        Abonnement abonnement10 =
                new Abonnement("Personen die op huisnummer 12 wonen.",                                  //4
                               "ER_IS(adressen, a, a.huisnummer = 12)");
        Abonnement abonnement11 =
                new Abonnement("Personen die op huisnummer 12 wonen en als eerste voornaam Henk of Anita hebben.",//1
                               "ER_IS(adressen, a, a.huisnummer = 12) EN ER_IS(voornamen, v, v.naam = \"Henk\") OF ER_IS(voornamen, v, v.naam = \"Anita\")");

        List<Afnemer> testAfnemers = new ArrayList<Afnemer>();
        testAfnemers.add(new Afnemer("Afnemer 1", Arrays
                .asList(abonnement1, abonnement2, abonnement3, abonnement4, abonnement5)));
        testAfnemers.add(new Afnemer("Afnemer 2",
                                     Arrays.asList(abonnement6, abonnement8, abonnement9, abonnement10, abonnement11)));
        return testAfnemers;
    }

    private Persoon maakPersoonMetPlaats(final Integer geboorteDatum, final String voornaam, final String achternaam,
                                         final Integer verhuisDatum, final String nationaliteit, final String postcode,
                                         final Integer huisnummer)
    {
        DatumTijdAttribuut nu = DatumTijdUtil.nu();

        ActieModel actie = new ActieModel(new SoortActieAttribuut(SoortActie.CORRECTIE_ADRES), null, null, DatumUtil.vandaag(), null, nu, null);


        PersoonHisVolledigImplBuilder builder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE)
                .nieuwIdentificatienummersRecord(actie)
                    .burgerservicenummer(1234567890)
                    .eindeRecord()
                .nieuwGeslachtsaanduidingRecord(actie)
                    .geslachtsaanduiding(Geslachtsaanduiding.MAN)
                    .eindeRecord()
                .nieuwGeboorteRecord(actie)
                    .datumGeboorte(geboorteDatum)
                    .gemeenteGeboorte(StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM.getWaarde())
                    .landGeboorte(StatischeObjecttypeBuilder.LAND_NEDERLAND.getWaarde())
                    .eindeRecord()
                .nieuwBijhoudingsgemeenteRecord(actie)
                    .bijhoudingsgemeente(StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_AMSTERDAM.getWaarde())
                    .datumInschrijvingInGemeente(geboorteDatum)
                    .eindeRecord()
                .nieuwBijhoudingsaardRecord(actie)
                    .bijhoudingsaard(Bijhoudingsaard.INGEZETENE)
                    .eindeRecord()
                .nieuwSamengesteldeNaamRecord(actie)
                    .geslachtsnaam(achternaam)
                    .voornamen(voornaam)
                    .eindeRecord()
                .voegPersoonVoornaamToe(
                        new PersoonVoornaamHisVolledigImplBuilder(new VolgnummerAttribuut(1))
                                .nieuwStandaardRecord(actie)
                                .naam(voornaam)
                                .eindeRecord().build()
                        )
                .voegPersoonGeslachtsnaamcomponentToe(
                        new PersoonGeslachtsnaamcomponentHisVolledigImplBuilder(new VolgnummerAttribuut(1))
                                .nieuwStandaardRecord(actie)
                                .naam(achternaam)
                                .eindeRecord().build()
                                                     )
                .voegPersoonNationaliteitToe(
                        new PersoonNationaliteitHisVolledigImplBuilder(
                                StatischeObjecttypeBuilder.bouwNationaliteit("1111", nationaliteit).getWaarde())
                                .nieuwStandaardRecord(actie)
                                .eindeRecord().build()
                        )
                .voegPersoonAdresToe(
                        new PersoonAdresHisVolledigImplBuilder()
                                .nieuwStandaardRecord(actie)
                                .naamOpenbareRuimte("NOR")
                                .huisnummer(huisnummer)
                                .postcode(postcode)
                                .woonplaats(StatischeObjecttypeBuilder.WOONPLAATS_AMSTERDAM.getWaarde())
                                .gemeente(StatischeObjecttypeBuilder.GEMEENTE_UTRECHT.getWaarde())
                                .datumAanvangAdreshouding(verhuisDatum)
                                .eindeRecord().build()
                        );

        return new PersoonView(builder.build(), nu);
    }
}
