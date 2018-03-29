/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.logging.verschilanalyse.analyse;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3CategorieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Element;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Validatie;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Elementnummer;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.init.logging.model.StapelMatch;
import nl.bzk.migratiebrp.init.logging.model.VerschilType;
import nl.bzk.migratiebrp.init.logging.model.VoorkomenMatch;
import nl.bzk.migratiebrp.init.logging.model.domein.entities.FingerPrint;
import nl.bzk.migratiebrp.init.logging.model.domein.entities.VerschilAnalyseRegel;

/**
 * Bepaalt de verschillen tussen de LO3 PL en de teruggeconverteerde BRP PL en zet deze verschillen
 * op de meegegeven log.
 */
public final class VerschilBepaler {
    /**
     * Bepaalt de verschillen tussen twee LO3 persoonslijsten op basis van de herkomsten in deze
     * persoonslijsten.
     * @param lo3Pl LO3 persoonslijst die gevalideerd is door de syntax controle en de precondities.
     * @param brpLo3Pl de LO3 persoonslijst die door de terugconversie is ontstaan
     * @return een lijst met daarin de combinatie van een lijst van {@link VerschilAnalyseRegel} met de daar bijbehorende gegenereerde {@link FingerPrint}
     */
    public List<VergelijkResultaat> bepaalVerschillen(final Lo3Persoonslijst lo3Pl, final Lo3Persoonslijst brpLo3Pl) {
        if (lo3Pl != null && brpLo3Pl != null) {
            // Bepaal de verschillen tussen beide PL-en
            final List<VergelijkResultaat> result = new ArrayList<>();

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
        return Collections.emptyList();
    }

    /**
     * Controleert of de alle actuele voorkomens in de persoonlijst juist (84.10 is leeg) is.
     * @param brpLo3Pl de LO3 Persoonslijst komende vanuit BRP.
     * @return een lijst met {@link VergelijkResultaat} als er actuele voorkomens onjuist zijn, anders wordt er een lege lijst terug gegeven.
     */
    public List<VergelijkResultaat> controleerActueelJuist(final Lo3Persoonslijst brpLo3Pl) {
        final List<VergelijkResultaat> result = new ArrayList<>();
        if (brpLo3Pl != null) {
            result.addAll(bepaalActueelJuist(Collections.singletonList(brpLo3Pl.getPersoonStapel())));
            result.addAll(bepaalActueelJuist(Collections.singletonList(brpLo3Pl.getOuder1Stapel())));
            result.addAll(bepaalActueelJuist(Collections.singletonList(brpLo3Pl.getOuder2Stapel())));
            result.addAll(bepaalActueelJuist(brpLo3Pl.getNationaliteitStapels()));
            result.addAll(bepaalActueelJuist(brpLo3Pl.getHuwelijkOfGpStapels()));
            result.addAll(bepaalActueelJuist(Collections.singletonList(brpLo3Pl.getOverlijdenStapel())));
            result.addAll(bepaalActueelJuist(Collections.singletonList(brpLo3Pl.getInschrijvingStapel())));
            result.addAll(bepaalActueelJuist(Collections.singletonList(brpLo3Pl.getVerblijfplaatsStapel())));
            result.addAll(bepaalActueelJuist(brpLo3Pl.getKindStapels()));
            result.addAll(bepaalActueelJuist(Collections.singletonList(brpLo3Pl.getVerblijfstitelStapel())));
            result.addAll(bepaalActueelJuist(Collections.singletonList(brpLo3Pl.getGezagsverhoudingStapel())));
            result.addAll(bepaalActueelJuist(brpLo3Pl.getReisdocumentStapels()));
            result.addAll(bepaalActueelJuist(Collections.singletonList(brpLo3Pl.getKiesrechtStapel())));
        }
        return result;
    }

    private <T extends Lo3CategorieInhoud> List<VergelijkResultaat> bepaalActueelJuist(final List<Lo3Stapel<T>> stapels) {
        final List<VergelijkResultaat> resultaat = new ArrayList<>();
        stapels.stream().filter(Objects::nonNull).forEach(stapel -> {
            final Lo3Categorie<T> lo3ActueelVoorkomen = stapel.getLo3ActueelVoorkomen();
            if (lo3ActueelVoorkomen.getHistorie().isOnjuist()) {
                resultaat.add(verwerkRegelEnFingerPrint(new VerschilAnalyseRegel(lo3ActueelVoorkomen.getLo3Herkomst(), VerschilType.ACTUAL_ONJUIST, null)));
            }
        });

        return resultaat;
    }


    /**
     * Inschrijvingsstapel heeft bij terugconversie geen herkomst. Maar dit is altijd 1 stapel en 1
     * voorkomen
     * @param lo3Stapel LO3 Stapel
     * @param brpLo3Stapel BRP Stapel
     * @return lijst met verschilanalyse regel en de bijbehorende fingerprints
     */
    private List<VergelijkResultaat> vergelijkInschrijvingsStapel(final Lo3Stapel<Lo3InschrijvingInhoud> lo3Stapel,
                                                                  final Lo3Stapel<Lo3InschrijvingInhoud> brpLo3Stapel) {
        final List<VergelijkResultaat> verschillen = new ArrayList<>();

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
    private <T extends Lo3CategorieInhoud> List<VergelijkResultaat> vergelijkStapel(final Lo3Stapel<T> lo3Stapel, final Lo3Stapel<T> brpLo3Stapel) {
        final List<Lo3Stapel<T>> lo3Stapels = lo3Stapel == null ? null : Collections.singletonList(lo3Stapel);
        final List<Lo3Stapel<T>> brpLo3Stapels = brpLo3Stapel == null ? null : Collections.singletonList(brpLo3Stapel);

        return vergelijkStapels(lo3Stapels, brpLo3Stapels);
    }

    /**
     * Vergelijken van meervoudige stapels. Als er een unieke match gevonden wordt, dan wordt er
     * verder gekeken naar de voorkomens binnen een stapel.
     */
    private <T extends Lo3CategorieInhoud> List<VergelijkResultaat> vergelijkStapels(final List<Lo3Stapel<T>> lo3Stapels,
                                                                                     final List<Lo3Stapel<T>> brpLo3Stapels) {
        final List<VergelijkResultaat> verschillen = new ArrayList<>();
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
     * Vergelijkt de voorkomens met elkaar. Als er een unieke match gevonden wordt, dan wordt er
     * verder gekeken naar de inhoud.
     */
    private <T extends Lo3CategorieInhoud> List<VergelijkResultaat> vergelijkVoorkomens(final StapelMatch<T> stapelMatch) {
        final List<VergelijkResultaat> verschillen = new ArrayList<>();
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
            final Lo3VerschillenLog verschillenLog = bepaalVerschillen(lo3Voorkomen, brpLo3Voorkomen);

            final Lo3Herkomst herkomst = lo3Voorkomen.getLo3Herkomst();
            verschillenLog.getNieuweElementen().forEach(element -> regels.add(new VerschilAnalyseRegel(herkomst, VerschilType.ADDED, element)));
            verschillenLog.getVerwijderdeElementen().forEach(element -> regels.add(new VerschilAnalyseRegel(herkomst, VerschilType.REMOVED, element)));
            verschillenLog.getGewijzigdeElementen().forEach(element -> regels.add(new VerschilAnalyseRegel(herkomst, VerschilType.MODIFIED, element)));
        }
    }

    /**
     * Helper-methode om 1 verschil als een lijst van verschillen te verwerken.
     */
    private VergelijkResultaat verwerkRegelEnFingerPrint(final VerschilAnalyseRegel regel) {
        return verwerkRegelsEnFingerPrint(Collections.singletonList(regel));
    }

    /**
     * Genereert bij een lijst van verschillen de bijbehorende vingerafdruk.
     */
    private VergelijkResultaat verwerkRegelsEnFingerPrint(final List<VerschilAnalyseRegel> regels) {
        final String vingerafdruk = VingerafdrukGenerator.maakVingerafdruk(regels);
        final FingerPrint fingerPrint = new FingerPrint(vingerafdruk);
        return new VergelijkResultaat(regels, fingerPrint);
    }

    private <T extends Lo3CategorieInhoud> Lo3VerschillenLog bepaalVerschillen(final Lo3Categorie<T> lo3Voorkomen, final Lo3Categorie<T> brpVoorkomen) {
        final Lo3VerschillenLog verschillenLog = new Lo3VerschillenLog();
        bepaalVerschillen(verschillenLog, lo3Voorkomen.getInhoud(), brpVoorkomen.getInhoud());
        bepaalVerschillen(verschillenLog, lo3Voorkomen.getHistorie(), brpVoorkomen.getHistorie());
        bepaalVerschillen(verschillenLog, lo3Voorkomen.getDocumentatie(), brpVoorkomen.getDocumentatie());
        bepaalVerschillenOnderzoek(verschillenLog, lo3Voorkomen.getOnderzoek(), brpVoorkomen.getOnderzoek());

        return verschillenLog;
    }

    private void bepaalVerschillenOnderzoek(final Lo3VerschillenLog verschillenLog, final Lo3Onderzoek lo3Onderzoek, final Lo3Onderzoek brpOnderzoek) {
        if (lo3Onderzoek == null && brpOnderzoek == null) {
            return;
        }
        if (lo3Onderzoek == null) {
            verschillenLog.addNieuwElement(Lo3ElementEnum.ELEMENT_8310);
            verschillenLog.addNieuwElement(Lo3ElementEnum.ELEMENT_8320);
            verschillenLog.addNieuwElement(Lo3ElementEnum.ELEMENT_8330);
        } else if (brpOnderzoek == null) {
            verschillenLog.addVerwijderdElement(Lo3ElementEnum.ELEMENT_8310);
            verschillenLog.addVerwijderdElement(Lo3ElementEnum.ELEMENT_8320);
            verschillenLog.addVerwijderdElement(Lo3ElementEnum.ELEMENT_8330);
        } else {
            bepaalVerschillen(verschillenLog, lo3Onderzoek, brpOnderzoek);
        }
    }

    private void bepaalVerschillen(final Lo3VerschillenLog verschillenLog, final Object lo3Object, final Object brpObject) {
        if (lo3Object == null || brpObject == null) {
            return;
        }

        final Field[] fields = lo3Object.getClass().getDeclaredFields();
        for (final Field field : fields) {
            final Lo3Elementnummer elementAnnotation = field.getAnnotation(Lo3Elementnummer.class);
            if (elementAnnotation != null) {
                bepaalVerschilLo3Element(verschillenLog, lo3Object, brpObject, field, elementAnnotation);
            }
        }
    }

    private void bepaalVerschilLo3Element(final Lo3VerschillenLog verschillenLog, final Object lo3Object, final Object brpObject, final Field field,
                                          final Lo3Elementnummer elementAnnotation) {
        final Lo3ElementEnum elementNummer = elementAnnotation.value();
        field.setAccessible(true);
        try {
            final Lo3Element lo3Value = (Lo3Element) field.get(lo3Object);
            final Lo3Element brpValue = (Lo3Element) field.get(brpObject);

            final boolean isBrpValueGevuld = Lo3Validatie.isElementGevuld(brpValue);
            final boolean isLo3ValueGevuld = Lo3Validatie.isElementGevuld(lo3Value);

            if (isLo3ValueGevuld || isBrpValueGevuld) {
                if (!isLo3ValueGevuld) {
                    verschillenLog.addNieuwElement(elementNummer);
                } else if (!isBrpValueGevuld) {
                    verschillenLog.addVerwijderdElement(elementNummer);
                } else if (!isElementVerschillend(lo3Value, brpValue)) {
                    verschillenLog.addGewijzigdElement(elementNummer);
                }
            }
        } catch (final IllegalAccessException iae) {
            throw new IllegalStateException(iae);
        }
    }

    private boolean isElementVerschillend(final Lo3Element lo3Element, final Lo3Element brpElement) {
        final boolean bevatLo3ElementWaarde = Lo3Validatie.isElementGevuld(lo3Element);
        final boolean bevatBrpElementWaarde = Lo3Validatie.isElementGevuld(brpElement);

        return !bevatLo3ElementWaarde ? !bevatBrpElementWaarde : brpElement.getWaarde().equals(lo3Element.getWaarde());
    }
}
