/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.logging.verschilanalyse.analyse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3VerschillenLog;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3CategorieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.init.logging.model.StapelMatch;
import nl.bzk.migratiebrp.init.logging.model.VerschilType;
import nl.bzk.migratiebrp.init.logging.model.VoorkomenMatch;
import nl.bzk.migratiebrp.init.logging.model.domein.entities.FingerPrint;
import nl.bzk.migratiebrp.init.logging.model.domein.entities.VerschilAnalyseRegel;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

/**
 * Bepaalt de verschillen tussen de LO3 PL en de teruggeconverteerde BRP PL en zet deze verschillen op de meegegeven
 * log.
 */
@Component
public final class VerschilBepaler {
    /**
     * Bepaalt de verschillen tussen twee LO3 persoonslijsten op basis van de herkomsten in deze persoonslijsten.
     * 
     * @param lo3Pl
     *            LO3 persoonslijst die gevalideerd is door de syntax controle en de precondities.
     * @param brpLo3Pl
     *            de LO3 persoonslijst die door de terugconversie is ontstaan
     * @return een lijst met daarin de combinatie van een lijst van {@link VerschilAnalyseRegel} met de daar
     *         bijbehorende gegenereerde {@link FingerPrint}
     */
    public List<Pair<List<VerschilAnalyseRegel>, FingerPrint>> bepaalVerschillen(final Lo3Persoonslijst lo3Pl, final Lo3Persoonslijst brpLo3Pl) {
        if (lo3Pl != null && brpLo3Pl != null) {
            // Bepaal de verschillen tussen beide PL-en
            final List<Pair<List<VerschilAnalyseRegel>, FingerPrint>> result = new ArrayList<>();

            // Cat 01
            result.addAll(vergelijkStapel(lo3Pl.getPersoonStapel(), brpLo3Pl.getPersoonStapel()));
            // Cat 02
            result.addAll(vergelijkStapel(lo3Pl.getOuder1Stapel(), brpLo3Pl.getOuder1Stapel()));
            // Cat 03
            result.addAll(vergelijkStapel(lo3Pl.getOuder2Stapel(), brpLo3Pl.getOuder2Stapel()));
            // Cat 04
            result.addAll(vergelijkStapels(lo3Pl.getNationaliteitStapels(), brpLo3Pl.getNationaliteitStapels()));
            // Cat 05
            result.addAll(vergelijkStapels(lo3Pl.getHuwelijkOfGpStapels(), brpLo3Pl.getHuwelijkOfGpStapels()));
            // Cat 06
            result.addAll(vergelijkStapel(lo3Pl.getOverlijdenStapel(), brpLo3Pl.getOverlijdenStapel()));
            // Cat 07
            result.addAll(vergelijkInschrijvingsStapel(lo3Pl.getInschrijvingStapel(), brpLo3Pl.getInschrijvingStapel()));
            // Cat 08
            result.addAll(vergelijkStapel(lo3Pl.getVerblijfplaatsStapel(), brpLo3Pl.getVerblijfplaatsStapel()));
            // Cat 09
            result.addAll(vergelijkStapels(lo3Pl.getKindStapels(), brpLo3Pl.getKindStapels()));
            // Cat 10
            result.addAll(vergelijkStapel(lo3Pl.getVerblijfstitelStapel(), brpLo3Pl.getVerblijfstitelStapel()));
            // Cat 11
            result.addAll(vergelijkStapel(lo3Pl.getGezagsverhoudingStapel(), brpLo3Pl.getGezagsverhoudingStapel()));
            // Cat 12
            result.addAll(vergelijkStapels(lo3Pl.getReisdocumentStapels(), brpLo3Pl.getReisdocumentStapels()));
            // Cat 13
            result.addAll(vergelijkStapel(lo3Pl.getKiesrechtStapel(), brpLo3Pl.getKiesrechtStapel()));

            return result;
        }
        return null;
    }

    /**
     * Inschrijvingsstapel heeft bij terugconversie geen herkomst. Maar dit is altijd 1 stapel en 1 voorkomen
     * 
     * @param lo3Stapel
     *            LO3 Stapel
     * @param brpLo3Stapel
     *            BRP Stapel
     * @return lijst met verschilanalyse regel en de bijbehorende fingerprints
     */
    private List<Pair<List<VerschilAnalyseRegel>, FingerPrint>> vergelijkInschrijvingsStapel(
        final Lo3Stapel<Lo3InschrijvingInhoud> lo3Stapel,
        final Lo3Stapel<Lo3InschrijvingInhoud> brpLo3Stapel)
    {
        final List<Pair<List<VerschilAnalyseRegel>, FingerPrint>> verschillen = new ArrayList<>();

        final Lo3Categorie<Lo3InschrijvingInhoud> lo3Voorkomen = lo3Stapel.getCategorieen().get(0);
        final Lo3Categorie<Lo3InschrijvingInhoud> brpLo3Voorkomen = brpLo3Stapel.getCategorieen().get(0);

        final Lo3Herkomst herkomst = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_07, 0, 0);

        final VoorkomenMatch<Lo3InschrijvingInhoud> voorkomenMatch = new VoorkomenMatch<>(herkomst);
        voorkomenMatch.addBrpLo3Voorkomen(brpLo3Voorkomen);
        voorkomenMatch.addLo3Voorkomen(lo3Voorkomen);

        final List<VerschilAnalyseRegel> regels = new ArrayList<>();
        vergelijkInhoud(voorkomenMatch, regels);

        if (!regels.isEmpty()) {
            verschillen.add(verwerkRegelsEnFingerPrint(regels));
        }
        return verschillen;
    }

    /**
     * Helper-methode om van een enkelvoudige stapel een meervoudige stapel te maken.
     */
    private <T extends Lo3CategorieInhoud> List<Pair<List<VerschilAnalyseRegel>, FingerPrint>> vergelijkStapel(
        final Lo3Stapel<T> lo3Stapel,
        final Lo3Stapel<T> brpLo3Stapel)
    {
        final List<Lo3Stapel<T>> lo3Stapels = lo3Stapel != null ? Collections.singletonList(lo3Stapel) : null;
        final List<Lo3Stapel<T>> brpLo3Stapels = brpLo3Stapel != null ? Collections.singletonList(brpLo3Stapel) : null;

        return vergelijkStapels(lo3Stapels, brpLo3Stapels);
    }

    /**
     * Vergelijken van meervoudige stapels. Als er een unieke match gevonden wordt, dan wordt er verder gekeken naar de
     * voorkomens binnen een stapel.
     */
    private <T extends Lo3CategorieInhoud> List<Pair<List<VerschilAnalyseRegel>, FingerPrint>> vergelijkStapels(
        final List<Lo3Stapel<T>> lo3Stapels,
        final List<Lo3Stapel<T>> brpLo3Stapels)
    {
        final List<Pair<List<VerschilAnalyseRegel>, FingerPrint>> verschillen = new ArrayList<>();
        final List<StapelMatch<T>> stapelMatches = StapelMatcher.matchStapels(lo3Stapels, brpLo3Stapels);

        for (final StapelMatch<T> stapelMatch : stapelMatches) {
            if (!stapelMatch.isUniqueMatched()) {
                verschillen.add(verwerkRegelEnFingerPrint(new VerschilAnalyseRegel(stapelMatch)));
            } else {
                verschillen.addAll(vergelijkVoorkomens(stapelMatch));
            }
        }

        return verschillen;
    }

    /**
     * Vergelijkt de voorkomens met elkaar. Als er een unieke match gevonden wordt, dan wordt er verder gekeken naar de
     * inhoud.
     */
    private <T extends Lo3CategorieInhoud> List<Pair<List<VerschilAnalyseRegel>, FingerPrint>> vergelijkVoorkomens(final StapelMatch<T> stapelMatch) {
        final List<Pair<List<VerschilAnalyseRegel>, FingerPrint>> verschillen = new ArrayList<>();
        final List<VoorkomenMatch<T>> voorkomenMatches = VoorkomenMatcher.matchVoorkomens(stapelMatch);

        for (final VoorkomenMatch<T> voorkomenMatch : voorkomenMatches) {
            final List<VerschilAnalyseRegel> regels = new ArrayList<>();

            if (!voorkomenMatch.isUniqueMatched()) {
                regels.add(new VerschilAnalyseRegel(voorkomenMatch));
            } else {
                vergelijkInhoud(voorkomenMatch, regels);
            }

            if (!regels.isEmpty()) {
                verschillen.add(verwerkRegelsEnFingerPrint(regels));
            }
        }

        return verschillen;
    }

    /**
     * Vergelijkt de inhoud van een voorkomen.
     */
    private <T extends Lo3CategorieInhoud> void vergelijkInhoud(final VoorkomenMatch<T> voorkomenMatch, final List<VerschilAnalyseRegel> regels) {
        final Lo3Categorie<T> lo3Voorkomen = voorkomenMatch.getLo3Voorkomens().get(0);
        final Lo3Categorie<T> brpLo3Voorkomen = voorkomenMatch.getBrpLo3Voorkomens().get(0);

        if (voorkomenMatch.isLo3Actueel() && !voorkomenMatch.isBrpLo3Actueel()) {
            regels.add(new VerschilAnalyseRegel(voorkomenMatch));
        }

        if (!lo3Voorkomen.equals(brpLo3Voorkomen)) {
            // Vraag aan de voorkomens de verschillen op.
            final Lo3Herkomst herkomst = lo3Voorkomen.getLo3Herkomst();
            final Lo3VerschillenLog verschillenLog = lo3Voorkomen.bepaalVerschillen(brpLo3Voorkomen);
            verwerkVerschillenLog(herkomst, verschillenLog.getNieuweElementen(), VerschilType.ADDED, regels);
            verwerkVerschillenLog(herkomst, verschillenLog.getVerwijderdeElementen(), VerschilType.REMOVED, regels);
            verwerkVerschillenLog(herkomst, verschillenLog.getGewijzigdeElementen(), VerschilType.MODIFIED, regels);
        }
    }

    private void verwerkVerschillenLog(
        final Lo3Herkomst herkomst,
        final List<String> elementen,
        final VerschilType verschilType,
        final List<VerschilAnalyseRegel> regels)
    {
        for (final String element : elementen) {
            regels.add(new VerschilAnalyseRegel(herkomst, verschilType, element));
        }
    }

    /**
     * Helper-methode om 1 verschil als een lijst van verschillen te verwerken.
     */
    private Pair<List<VerschilAnalyseRegel>, FingerPrint> verwerkRegelEnFingerPrint(final VerschilAnalyseRegel regel) {
        return verwerkRegelsEnFingerPrint(Collections.singletonList(regel));
    }

    /**
     * Genereert bij een lijst van verschillen de bijbehorende vingerafdruk.
     */
    private Pair<List<VerschilAnalyseRegel>, FingerPrint> verwerkRegelsEnFingerPrint(final List<VerschilAnalyseRegel> regels) {
        final String vingerafdruk = VingerafdrukGenerator.maakVingerafdruk(regels);
        final FingerPrint fingerPrint = new FingerPrint(vingerafdruk);
        return new ImmutablePair<>(regels, fingerPrint);
    }
}
