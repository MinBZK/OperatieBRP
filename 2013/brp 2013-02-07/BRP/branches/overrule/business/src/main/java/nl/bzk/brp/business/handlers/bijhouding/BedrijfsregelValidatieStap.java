/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.handlers.bijhouding;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import nl.bzk.brp.business.bedrijfsregels.BedrijfsRegel;
import nl.bzk.brp.business.bedrijfsregels.BedrijfsRegelManager;
import nl.bzk.brp.business.dto.BerichtContext;
import nl.bzk.brp.business.dto.BerichtResultaat;
import nl.bzk.brp.business.dto.bijhouding.AbstractBijhoudingsBericht;
//import nl.bzk.brp.business.dto.bijhouding.OverruleMelding;
import nl.bzk.brp.business.handlers.AbstractBerichtVerwerkingsStap;
import nl.bzk.brp.dataaccess.repository.PersoonRepository;
import nl.bzk.brp.model.PersistentRootObject;
import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.logisch.BRPActie;
import nl.bzk.brp.model.logisch.Persoon;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.OverruleMelding;
import nl.bzk.brp.model.validatie.SoortMelding;
import org.apache.commons.lang.StringUtils;


/**
 * De stap in de uitvoering van een bericht waarin wordt gecontroleerd of het binnenkomende bericht valide is volgens
 * een bedrijfsregel. Geconstateerde fouten waardes worden, inclusief bericht melding en zwaarte, toegevoegd aan de
 * lijst van fouten binnen het antwoord.
 */
public class BedrijfsregelValidatieStap extends AbstractBerichtVerwerkingsStap<AbstractBijhoudingsBericht,
        BerichtResultaat>
{

    @Inject
    private BedrijfsRegelManager bedrijfsRegelManager;

    @Inject
    private PersoonRepository    persoonRepository;

    /** {@inheritDoc} */
    @Override
    public boolean voerVerwerkingsStapUitVoorBericht(final AbstractBijhoudingsBericht bericht,
                                                     final BerichtContext context,
                                                     final BerichtResultaat resultaat)
    {
        for (BRPActie actie : bericht.getBrpActies()) {
            PersistentRootObject huidigeSituatie = haalHuidigeSituatieOp(actie);
            RootObject nieuweSituatie = actie.getRootObjecten().get(0);

            if (null != bedrijfsRegelManager.getUitTeVoerenBedrijfsRegels(actie.getSoort())) {
                for (BedrijfsRegel<PersistentRootObject, RootObject> bedrijfsRegel : bedrijfsRegelManager
                        .getUitTeVoerenBedrijfsRegels(actie.getSoort()))
                {
                    final Melding melding =
                        bedrijfsRegel.executeer(huidigeSituatie, nieuweSituatie, actie.getDatumAanvangGeldigheid());
                    if (melding != null) {
                        resultaat.voegMeldingToe(melding);
                    }
                }
            }
        }

        // we moeten hier corrigeren, omdat als we deze methode verlaten en er is een Fout geconstateerd,
        // de trein wordt gestopt.
        corrigeerVoorOverrulebareFouten(bericht, context, resultaat);
        return DOORGAAN_MET_VERWERKING;
    }

    /**
     * Omdat bedrijfsregels gebruik maken van de huidige situatie in de BRP database, moet de huidige situatie ook
     * via de DAL laag opgehaald worden. Dat gebeurt in deze functie.
     *
     * @param actie De actie die uitegevoerd dient te worden uit het inkomende bericht.
     * @return Een PersistentRootObject wat in feite een instantie is van een Persoon of Relatie sinds deze 2 objecten
     *         als RootObject worden gezien.
     */
    private PersistentRootObject haalHuidigeSituatieOp(final BRPActie actie) {
        final PersistentRootObject huidigeSituatie;
        switch (actie.getSoort()) {
            case VERHUIZING:
                huidigeSituatie =
                    persoonRepository.findByBurgerservicenummer(((Persoon) actie.getRootObjecten().get(0))
                            .getIdentificatienummers().getBurgerservicenummer());
                break;
            case REGISTRATIE_NATIONALITEIT:
            case AANGIFTE_GEBOORTE:
                huidigeSituatie = null;
                break;
            default:
                throw new IllegalArgumentException(actie.getSoort()
                    + " wordt nog niet ondersteund door de berichtverwerker");
        }
        return huidigeSituatie;
    }

    // Op dit ogenblik wordt nog geen rekening gehouden met het verzendenID, en
    // worden de regels alleen gematched op
    // regelCode en niet regelCode + verzendendId.

    /**
     * Corrigeer alle fouten van het type OVERRULEBAAR, met deze voorwaarden:
     * - alle overrulebare fouten zijn gespecificeerd in de lijst met overrulemeldingen.
     * - het aantal is identiek (maw. alle fouten moeten ook daadwerkelijk geconstateerd zijn).
     * - er mogen geen andere overrulebare fouten overblijven. (maw. alle fouten zijn oevrruled).
     * Alleen dan worden alle overrulebare gedowngrade naar WAARSCHUWING en de trein kan doorgaan.
     * @param bericht het volledig bericht
     * @param context de context
     * @param resultaat de lijst met meldingen.
     */
    public void corrigeerVoorOverrulebareFouten(final AbstractBijhoudingsBericht bericht,
            final BerichtContext context, final BerichtResultaat resultaat)
    {
        if (null != bericht.getOverruledMeldingen() && !bericht.getOverruledMeldingen().isEmpty()) {
            List<OverruleMelding> lokOverrMeldingen = new ArrayList<OverruleMelding>(bericht.getOverruledMeldingen());
            System.out.println("---- List van overrulbare meldingen " + lokOverrMeldingen);

            System.out.println("---- resultaat.bevatVerwerkingStoppendeFouten() " + resultaat.bevatVerwerkingStoppendeFouten());
            if (resultaat.bevatVerwerkingStoppendeFouten()) {
                List<Melding> resultaatMeldingen = resultaat.getMeldingen();
                List<Melding> copy = new ArrayList<Melding>(resultaatMeldingen);

                for (Melding melding : resultaatMeldingen) {
                    System.out.println("---- cheking: melding " + melding.getSoort() + " " + melding.getCode());
                    if (melding.getSoort() == SoortMelding.FOUT_OVERRULEBAAR) {
                        // gevondenMelding is het object in de overrulebareMeldingen lijst.
                        OverruleMelding gevondenMelding = isMeldingOverruled(bericht.getOverruledMeldingen(), melding);
                        System.out.println("---- gevonden " + gevondenMelding);
                        if (gevondenMelding != null) {
                            copy.remove(melding);
                            System.out.println("0----- Removing from copy " + melding.getCode());
                            removeOverrulebareMelding(bericht.getOverruledMeldingen(), lokOverrMeldingen,
                                    gevondenMelding);
                            System.out.println("----- Removing from gevondenMelding " + gevondenMelding.getCode());
                        }
                    }
                }

                // OK, test nu of overrulebare fouten in beide lijsten leeg zijn. zo ja, dan doen we in het echt
                System.out.println("----- lokOverrMeldingen " + lokOverrMeldingen.size() + " "
                        + (!lijstHeeftOverRulebareMeldingen(copy)));
                if (lokOverrMeldingen.size() == 0 && (!lijstHeeftOverRulebareMeldingen(copy))) {
                    System.out.println("----- cleaning");
                    resultaat.overruleAlleOverrulebareFouten();
                    System.out.println("----- result");
                    resultaat.bevatVerwerkingStoppendeFouten();
                    resultaat.setOverruleMeldingen(bericht.getOverruledMeldingen());
                }
            }

            // test of nog in de lijst meldingen staan die we niet gevonden hebben.
            if (lokOverrMeldingen.size() != 0) {
                // oeps, probeert te overrulen wat er niet was. foei !!
                List<String> codes = new ArrayList<String>();
                for (OverruleMelding m : lokOverrMeldingen) {
                    codes.add(m.getCode());
                }
                resultaat.voegMeldingToe(new Melding(SoortMelding.FOUT_ONOVERRULEBAAR, MeldingCode.ALG0001,
                    "De lijst van overrulebare meldingen bevat meer dan daadwerkelijk gevonden is: [" + codes + "]."));
            }
        }
    }

    /**
     * Test of een lijst Fout-meldingen bevat.
     * @param meldingen de lijst
     * @return true of false
     */
    private boolean lijstHeeftOverRulebareMeldingen(final List<Melding> meldingen) {
        for (Melding m : meldingen) {
            if (m.getSoort() == SoortMelding.FOUT_OVERRULEBAAR || m.getSoort() == SoortMelding.FOUT_ONOVERRULEBAAR) {
                return true;
            }
        }
        return false;
    }

//    /**
//     * vergelijk 2 trings met elkaar.
//     * @param s1 string een
//     * @param s2 string 2
//     * @return of ze gelijk zijn of niet
//     */
//    private boolean stringEquals(final String s1, final String s2) {
//        boolean retval = false;
//        if (StringUtils.isBlank(s1) && StringUtils.isBlank(s2)) {
//            // beide leeg, of nulll
//            retval = true;
//        } else if (s1 == null || s2 == null) {
//            //de ene is null, de ander niet
//            retval = false;
//        } else {
//            // beide niet null (en niet allebei zijn leeg)
//            retval = s1.equals(s2);
//        }
//        return retval;
//    }
    /**
     * .
     * @param overrulebareMeldingen .
     * @param melding .
     * @return .
     */
    private OverruleMelding isMeldingOverruled(final List<OverruleMelding> overrulebareMeldingen, final Melding melding) {
        for (OverruleMelding m : overrulebareMeldingen) {
            System.out.println(" vergeleken: " +m.getCode() + " == " + melding.getCode().getNaam());
            if (m.getCode().equals(melding.getCode().getNaam())) {
                // op dit ogenblik is business rule de enige waar we op kunnen matchen.
                // later zou dit evt. de attribuutNaam, maar zeker de cIDverzendend moeten matchen.
                return m;
            }
        }
        return null;
    }

    /**
     * Verwijder overrubare melding uit de lijst. Omdat weniet met verzendId's werken, moeten we ALLE van het zelfde
     * mtype verwijderen, maar dit kan ook meerdere keren voorkomen; dus hebben we de originele lijst ook nodig.
     * @param origin de originele lijst
     * @param copy de bewerkte lijst
     * @param melding de te verwijderen melding.
     */
    private void removeOverrulebareMelding(final List<OverruleMelding> origin, final List<OverruleMelding> copy,
            final OverruleMelding melding)
    {
        // normaal gesproken, verwijderen we hier 1 melding, maar omdat we nog geen gebruik van Id's ect, moeten
        // we alles van dezelfde regelCode verwijderen.
        // daarom hebben we de origin nodig, anders hadden we met de copy kunnen voldoen.
        for (OverruleMelding m : origin) {
            if (m.getCode() == melding.getCode()) {
                if (copy.contains(m)) {
                    copy.remove(m);
                }
            }
        }
    }
}
