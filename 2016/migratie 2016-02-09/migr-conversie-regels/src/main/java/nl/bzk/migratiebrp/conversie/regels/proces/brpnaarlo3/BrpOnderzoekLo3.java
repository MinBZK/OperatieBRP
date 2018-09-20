/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.brpnaarlo3;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3CategorieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.AbstractLo3Element;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Elementnummer;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

/**
 * Verzamel de onderzoek gegevens uit de LO3 elementen, en consolideer die tot 1 onderzoek gegeven op categorie niveau.
 */
@Component
public class BrpOnderzoekLo3 {

    private static final int TWEE_CIJFERS = 100;

    /**
     * Verzamel de onderzoek gegevens uit de LO3 elementen, en consolideer die tot 1 onderzoek gegeven op categorie
     * niveau.
     *
     * @param persoonslijst
     *            persoonslijst
     * @return persoonslijst (met ingevuld onderzoek per categorie)
     */
    public final Lo3Persoonslijst converteer(final Lo3Persoonslijst persoonslijst) {
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder(persoonslijst);

        builder.persoonStapel(verwerkOnderzoek(persoonslijst.getPersoonStapel(), Lo3CategorieEnum.CATEGORIE_01));
        builder.ouder1Stapel(verwerkOnderzoek(persoonslijst.getOuder1Stapel(), Lo3CategorieEnum.CATEGORIE_02));
        builder.ouder2Stapel(verwerkOnderzoek(persoonslijst.getOuder2Stapel(), Lo3CategorieEnum.CATEGORIE_03));
        builder.nationaliteitStapels(verwerkOnderzoek(persoonslijst.getNationaliteitStapels(), Lo3CategorieEnum.CATEGORIE_04));
        builder.huwelijkOfGpStapels(verwerkOnderzoek(persoonslijst.getHuwelijkOfGpStapels(), Lo3CategorieEnum.CATEGORIE_05));
        builder.overlijdenStapel(verwerkOnderzoek(persoonslijst.getOverlijdenStapel(), Lo3CategorieEnum.CATEGORIE_06));
        builder.inschrijvingStapel(verwerkOnderzoek(persoonslijst.getInschrijvingStapel(), Lo3CategorieEnum.CATEGORIE_07));
        builder.verblijfplaatsStapel(verwerkOnderzoek(persoonslijst.getVerblijfplaatsStapel(), Lo3CategorieEnum.CATEGORIE_08));
        builder.kindStapels(verwerkOnderzoek(persoonslijst.getKindStapels(), Lo3CategorieEnum.CATEGORIE_09));
        builder.verblijfstitelStapel(verwerkOnderzoek(persoonslijst.getVerblijfstitelStapel(), Lo3CategorieEnum.CATEGORIE_10));
        builder.gezagsverhoudingStapel(verwerkOnderzoek(persoonslijst.getGezagsverhoudingStapel(), Lo3CategorieEnum.CATEGORIE_11));
        builder.reisdocumentStapels(verwerkOnderzoek(persoonslijst.getReisdocumentStapels(), Lo3CategorieEnum.CATEGORIE_12));
        builder.kiesrechtStapel(verwerkOnderzoek(persoonslijst.getKiesrechtStapel(), Lo3CategorieEnum.CATEGORIE_13));

        return builder.build();
    }

    private <T extends Lo3CategorieInhoud> List<Lo3Stapel<T>> verwerkOnderzoek(final List<Lo3Stapel<T>> stapels, final Lo3CategorieEnum categorieEnum) {
        if (stapels == null) {
            return null;
        }

        final List<Lo3Stapel<T>> verwerkteStapels = new ArrayList<>();

        for (final Lo3Stapel<T> stapel : stapels) {
            verwerkteStapels.add(verwerkOnderzoek(stapel, categorieEnum));
        }

        return verwerkteStapels;
    }

    private <T extends Lo3CategorieInhoud> Lo3Stapel<T> verwerkOnderzoek(final Lo3Stapel<T> stapel, final Lo3CategorieEnum categorieEnum) {
        if (stapel == null) {
            return null;
        }

        final List<Lo3Categorie<T>> categorieen = new ArrayList<>();

        for (final Lo3Categorie<T> categorie : stapel.getCategorieen()) {
            categorieen.add(verwerkOnderzoek(categorie, categorieEnum));
        }

        return new Lo3Stapel<>(categorieen);
    }

    private <T extends Lo3CategorieInhoud> Lo3Categorie<T> verwerkOnderzoek(final Lo3Categorie<T> categorie, final Lo3CategorieEnum categorieEnum) {
        if (categorie.getOnderzoek() != null) {
            // Onderzoek is al ingevuld. Bijvoorbeeld voor IST geconverteerde stapels.
            // Voor dergelijke stapels wordt onderzoek niet opnieuw bepaald.
            return categorie;
        }

        final Lo3Onderzoek onderzoek = bepaalOnderzoekUitElementen(categorie, categorieEnum);

        return new Lo3Categorie<>(
            categorie.getInhoud(),
            categorie.getDocumentatie(),
            onderzoek,
            categorie.getHistorie(),
            categorie.getLo3Herkomst(),
            categorie.isAfsluitendVoorkomen());
    }

    /**
     * Bepaal het onderzoek voor een categorie.
     *
     * @param categorie
     *            categorie
     * @param categorieEnum
     *            categorie enum
     * @param <T>
     *            lo3 categorie inhoud type
     * @return onderzoek
     */
    public final <T extends Lo3CategorieInhoud> Lo3Onderzoek bepaalOnderzoekUitElementen(
        final Lo3Categorie<T> categorie,
        final Lo3CategorieEnum categorieEnum)
    {
        final List<Pair<AbstractLo3Element, Lo3ElementEnum>> elementen = new ArrayList<>();

        elementen.addAll(bepaalElementen(categorie.getInhoud()));
        elementen.addAll(bepaalElementen(categorie.getHistorie()));
        elementen.addAll(bepaalElementen(categorie.getDocumentatie()));

        final Set<Lo3Onderzoek> onderzoeken = new LinkedHashSet<>();

        for (final Pair<AbstractLo3Element, Lo3ElementEnum> element : elementen) {
            final Lo3Onderzoek onderzoek = element.getLeft().getOnderzoek();
            if (onderzoek != null) {
                if (onderzoek.getAanduidingGegevensInOnderzoek().getIntegerWaarde() != 0) {
                    onderzoeken.add(onderzoek);
                } else {
                    onderzoeken.add(new Lo3Onderzoek(
                        new Lo3Integer(bepaalOnderzoekCode(element.getRight(), categorieEnum), null),
                        onderzoek.getDatumIngangOnderzoek(),
                        onderzoek.getDatumEindeOnderzoek()));
                }
            }
        }

        final Lo3Onderzoek resultaat;

        if (onderzoeken.size() == 0) {
            resultaat = null;
        } else if (onderzoeken.size() == 1) {
            resultaat = onderzoeken.iterator().next();
        } else {
            resultaat = Lo3Onderzoek.consolideerOnderzoeken(onderzoeken, categorieEnum);
        }

        return resultaat;
    }

    private Collection<Pair<AbstractLo3Element, Lo3ElementEnum>> bepaalElementen(final Object elementVerzameling) {
        final List<Pair<AbstractLo3Element, Lo3ElementEnum>> resultaat = new ArrayList<>();

        if (elementVerzameling != null) {
            final Field[] velden = elementVerzameling.getClass().getDeclaredFields();
            for (final Field veld : velden) {
                final Lo3Elementnummer elementAnnotation = veld.getAnnotation(Lo3Elementnummer.class);
                if (elementAnnotation != null) {
                    veld.setAccessible(true);
                    try {
                        final Object veldWaarde = veld.get(elementVerzameling);

                        if (veldWaarde instanceof AbstractLo3Element) {
                            resultaat.add(Pair.of((AbstractLo3Element) veldWaarde, elementAnnotation.value()));
                        }
                    } catch (final IllegalAccessException iae) {
                        throw new IllegalStateException(iae);
                    }
                }
            }
        }

        return resultaat;
    }

    private String bepaalOnderzoekCode(final Lo3ElementEnum element, final Lo3CategorieEnum categorie) {
        return String.valueOf(categorie.getCategorieAsInt()
                              * TWEE_CIJFERS
                              * TWEE_CIJFERS
                              + element.getGroep().getGroepAsInt()
                              * TWEE_CIJFERS
                              + element.getRubriek().getRubriekAsInt());
    }
}
