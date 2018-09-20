/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import nl.bzk.brp.bevraging.app.ContextParameterNames;
import nl.bzk.brp.bevraging.app.support.Abonnement;
import nl.bzk.brp.bevraging.app.support.Afnemer;
import org.apache.commons.chain.Context;
import org.springframework.stereotype.Service;

@Service
public class HaalAfnemersOpStap extends AbstractAsynchroonStap {

    private static final int DEFAULT_AANTAL_AFNEMERS = 100;

    private static final int MAX_AANTAL_ABONNEMENTEN = 1;

    @Override
    public boolean doExecute(final Context context) throws Exception {

        context.put(ContextParameterNames.AFNEMERLIJST, maakAfnemerLijst(context));

        return CONTINUE_PROCESSING;
    }

    /**
     * Geeft het aantal afnemers uit de context.
     * @param context de context om het aantal uit te halen
     * @return het aantal uit de context, anders het default aantal
     */
    private Integer getAantalAfnemers(final Context context) {
        if (context.get(ContextParameterNames.AANTAL_AFNEMERS) != null) {
            return (Integer) context.get(ContextParameterNames.AANTAL_AFNEMERS);
        }
        return DEFAULT_AANTAL_AFNEMERS;
    }

    /**
     * Maakt een lijst met afnemers.
     * @param context context om het aantal uit te halen
     * @return een lijst met afnemers
     */
    private List<Afnemer> maakAfnemerLijst(final Context context) {
        List<Abonnement> abonnementen = maakAbonnementen();

        int aantalAfnemers = getAantalAfnemers(context);

        List<Afnemer> afnemers = new ArrayList<Afnemer>();
        for (int i = 0; i < aantalAfnemers; i++) {
            List<Abonnement> afnemerAbonnementen = selecteerRandomAbonnementen(abonnementen);
            afnemers.add(new Afnemer("Afnemer " + i, afnemerAbonnementen));
        }

        return afnemers;
    }

    /**
     * Maakt een lijst {@link Abonnement}en.
     * @return een lijst abonnementen
     */
    private List<Abonnement> maakAbonnementen() {
        Abonnement abonnement1 = new Abonnement("Personen geboren na 1950.", "persoon.geboorte.datum >= #1950-01-01#");
        Abonnement abonnement2 = new Abonnement("Personen geboren voor 1950, met de eerste voornaam Karel.",
                                                "persoon.geboorte.datum < #1950-01-01# EN persoon.voornamen[1].naam = \"Karel\"");
        Abonnement abonnement3 =
                new Abonnement("Personen met de voornaam Pieter en achternaam Jansen.",
                               "persoon.voornamen[1].naam = \"Pieter\" EN persoon.geslachtsnaamcomponenten[1].naam = \"Jansen\"");
        Abonnement abonnement4 =
                new Abonnement("Personen geboren in het jaar 2000.", "JAAR(persoon.geboorte.datum) = 2000");
        Abonnement abonnement5 =
                new Abonnement("Personen geboren in de maand februari.", "MAAND(persoon.geboorte.datum) = 2");
        Abonnement abonnement6 = new Abonnement("Personen verhuisd na oktober 2010.",
                                                "persoon.adressen[1].datum_aanvang_adreshouding > #2010-OKT-01#");
        // Abonnement abonnement7 =
        //        new Abonnement("Personen met de Turkse nationaliteit.", "persoon.nationaliteiten[1].nationaliteit = \"Turkse\"");
        Abonnement abonnement8 = new Abonnement("Personen geboren op een van 3 specifieke data.",
                                                "persoon.geboorte.datum = #1950-01-01# OF persoon.geboorte.datum = #1960-07-05# OF persoon.geboorte.datum = #1970-11-07#");
        Abonnement abonnement9 = new Abonnement("Personen die de postcode 3521TK hebben.",
                                                "persoon.adressen[1].postcode = \"3521TK\"");
        Abonnement abonnement10 = new Abonnement("Personen die op huisnummer 12 wonen.",
                                                 "persoon.adressen[1].huisnummer = \"12\"");
        Abonnement abonnement11 =
                new Abonnement("Personen die op huisnummer 12 wonen en als eerste voornaam Henk of Anita hebben.",
                               "persoon.adressen[1].huisnummer = \"12\" EN persoon.voornamen[1].naam = \"Henk\" OF persoon.voornamen[1].naam = \"Anita\"");

        List<Abonnement> abonnementen = new ArrayList<Abonnement>();
        abonnementen.add(abonnement1);
        abonnementen.add(abonnement2);
        abonnementen.add(abonnement3);
        abonnementen.add(abonnement4);
        abonnementen.add(abonnement5);
        abonnementen.add(abonnement6);
        abonnementen.add(abonnement8);
        abonnementen.add(abonnement9);
        abonnementen.add(abonnement10);
        abonnementen.add(abonnement11);
        return abonnementen;
    }

    /**
     * Selecteert een random aantal abonnementen uit de gegeven lijst.
     * @param abonnementen de lijst van abonnementen om uit te selecteren
     * @return een lijst met abonnementen
     */
    private List<Abonnement> selecteerRandomAbonnementen(final List<Abonnement> abonnementen) {
        List<Abonnement> afnemerAbonnementen = new ArrayList<Abonnement>();
        afnemerAbonnementen.addAll(abonnementen);
        int randomAantal = getRandomGetalTussen1EnMax();
        Collections.shuffle(afnemerAbonnementen);
        afnemerAbonnementen = afnemerAbonnementen.subList(0, randomAantal);
        return afnemerAbonnementen;
    }

    /**
     * Geeft een random getal tussen 1 en max.
     * @return een getal
     */
    private int getRandomGetalTussen1EnMax() {
        if (MAX_AANTAL_ABONNEMENTEN == 1) {
            return 1;
        }
        return new Random().nextInt(MAX_AANTAL_ABONNEMENTEN - 1) + 1;
    }

}
