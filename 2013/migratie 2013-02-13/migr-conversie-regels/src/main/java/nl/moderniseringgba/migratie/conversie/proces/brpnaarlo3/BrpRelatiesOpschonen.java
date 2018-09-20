/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Categorie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Historie;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3PersoonslijstBuilder;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Stapel;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3HuwelijkOfGpInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3KindInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.springframework.stereotype.Component;

/**
 * Schoon geconverteerde relaties op.
 *
 * Uiteindelijk zorgt dit ervoor dat alle persoonsgegevens en wijzigingen die waren geregistreerd voor het begin van de
 * relatie (ouder of huwelijk) worden verwijderd.
 */
@Component
public class BrpRelatiesOpschonen {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final Comparator<Lo3Categorie<?>> CATEGORIEEN_COMPARATOR = new Comparator<Lo3Categorie<?>>() {
        @Override
        public int compare(final Lo3Categorie<?> o1, final Lo3Categorie<?> o2) {
            final Lo3Historie h1 = o1.getHistorie();
            final Lo3Historie h2 = o2.getHistorie();

            int result = h1.getDatumVanOpneming().compareTo(h2.getDatumVanOpneming());

            if (result == 0) {
                result = h1.getIngangsdatumGeldigheid().compareTo(h2.getIngangsdatumGeldigheid());
            }

            return result;
        }

    };

    /**
     * Schoon de relatie stapels op.
     *
     * @param persoonslijst
     *            persoonslijst
     * @return persoonslijst
     */
    public final Lo3Persoonslijst converteer(final Lo3Persoonslijst persoonslijst) {
        LOG.debug("converteerLo3Persoonslijst(lo3Persoonslijst.anummer={})",
                persoonslijst.getActueelAdministratienummer());
        final Lo3PersoonslijstBuilder builder = new Lo3PersoonslijstBuilder(persoonslijst);

        builder.ouder1Stapels(opschonenOuderStapels(persoonslijst.getOuder1Stapels()));
        builder.ouder2Stapels(opschonenOuderStapels(persoonslijst.getOuder2Stapels()));
        builder.huwelijkOfGpStapels(opschonenHuwelijkStapels(persoonslijst.getHuwelijkOfGpStapels()));
        builder.kindStapels(opschonenKindStapels(persoonslijst.getKindStapels()));

        return builder.build();
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private List<Lo3Stapel<Lo3OuderInhoud>> opschonenOuderStapels(final List<Lo3Stapel<Lo3OuderInhoud>> stapels) {
        if (stapels == null || stapels.isEmpty()) {
            return stapels;
        }

        final List<Lo3Stapel<Lo3OuderInhoud>> result = new ArrayList<Lo3Stapel<Lo3OuderInhoud>>();

        for (final Lo3Stapel<Lo3OuderInhoud> stapel : stapels) {
            result.add(opschonenOuderStapel(stapel));
        }

        return result;
    }

    private Lo3Stapel<Lo3OuderInhoud> opschonenOuderStapel(final Lo3Stapel<Lo3OuderInhoud> stapel) {
        final List<Lo3Categorie<Lo3OuderInhoud>> categorieen = stapel.getCategorieen();
        Collections.sort(categorieen, CATEGORIEEN_COMPARATOR);

        final List<Lo3Categorie<Lo3OuderInhoud>> result = new ArrayList<Lo3Categorie<Lo3OuderInhoud>>();

        boolean cleaning = true;
        for (final Lo3Categorie<Lo3OuderInhoud> categorie : categorieen) {
            cleaning = cleaning && categorie.getInhoud().getFamilierechtelijkeBetrekking() == null;
            if (!cleaning) {
                result.add(opschonenOuderPersoonsgegevens(categorie));
            }
        }

        return new Lo3Stapel<Lo3OuderInhoud>(result);
    }

    private Lo3Categorie<Lo3OuderInhoud> opschonenOuderPersoonsgegevens(final Lo3Categorie<Lo3OuderInhoud> categorie) {
        final Lo3OuderInhoud inhoud = categorie.getInhoud();

        if (inhoud.getFamilierechtelijkeBetrekking() == null) {
            // Leegmaken persoonsgegevens
            final Lo3OuderInhoud.Builder builder = new Lo3OuderInhoud.Builder(inhoud);

            builder.setaNummer(null);
            builder.setBurgerservicenummer(null);
            builder.setGeslachtsaanduiding(null);
            builder.setGeboortedatum(null);
            builder.setGeboorteGemeenteCode(null);
            builder.setGeboorteLandCode(null);
            builder.setAdellijkeTitelPredikaatCode(null);
            builder.setVoornamen(null);
            builder.setVoorvoegselGeslachtsnaam(null);
            builder.setGeslachtsnaam(null);

            return new Lo3Categorie<Lo3OuderInhoud>(builder.build(), categorie.getDocumentatie(),
                    categorie.getHistorie(), categorie.getLo3Herkomst());

        } else {
            return categorie;
        }
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private List<Lo3Stapel<Lo3HuwelijkOfGpInhoud>> opschonenHuwelijkStapels(
            final List<Lo3Stapel<Lo3HuwelijkOfGpInhoud>> stapels) {
        if (stapels == null || stapels.isEmpty()) {
            return stapels;
        }

        final List<Lo3Stapel<Lo3HuwelijkOfGpInhoud>> result = new ArrayList<Lo3Stapel<Lo3HuwelijkOfGpInhoud>>();

        for (final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel : stapels) {
            result.add(opschonenHuwelijkStapel(stapel));
        }

        return result;
    }

    private Lo3Stapel<Lo3HuwelijkOfGpInhoud> opschonenHuwelijkStapel(final Lo3Stapel<Lo3HuwelijkOfGpInhoud> stapel) {
        final List<Lo3Categorie<Lo3HuwelijkOfGpInhoud>> categorieen = stapel.getCategorieen();
        Collections.sort(categorieen, CATEGORIEEN_COMPARATOR);

        final List<Lo3Categorie<Lo3HuwelijkOfGpInhoud>> result = new ArrayList<Lo3Categorie<Lo3HuwelijkOfGpInhoud>>();

        Lo3Datum correctie = new Lo3Datum(0);
        for (final Lo3Categorie<Lo3HuwelijkOfGpInhoud> categorie : categorieen) {
            if (categorie.getInhoud().getSoortVerbintenis() != null) {
                correctie = null;
            } else {
                if (correctie == null) {
                    result.add(opschonenHuwelijkPersoonsgegevens(categorie));
                    correctie = categorie.getHistorie().getDatumVanOpneming();
                } else if (correctie.equals(categorie.getHistorie().getDatumVanOpneming())) {
                    result.add(opschonenHuwelijkPersoonsgegevens(categorie));
                }
            }

            if (correctie == null) {
                result.add(opschonenHuwelijkPersoonsgegevens(categorie));
            }
        }

        return new Lo3Stapel<Lo3HuwelijkOfGpInhoud>(opschonenDubbeleCorrecties(result));
    }

    private List<Lo3Categorie<Lo3HuwelijkOfGpInhoud>> opschonenDubbeleCorrecties(
            final List<Lo3Categorie<Lo3HuwelijkOfGpInhoud>> categorieen) {
        final List<Lo3Categorie<Lo3HuwelijkOfGpInhoud>> result = new ArrayList<Lo3Categorie<Lo3HuwelijkOfGpInhoud>>();

        final List<Long> correctieIds = new ArrayList<Long>();

        for (final Lo3Categorie<Lo3HuwelijkOfGpInhoud> categorie : categorieen) {
            if (categorie.getInhoud().isLeeg()) {
                if (correctieIds.contains(categorie.getDocumentatie().getId())) {
                    continue;
                }

                correctieIds.add(categorie.getDocumentatie().getId());
            }

            result.add(categorie);
        }

        return result;
    }

    private Lo3Categorie<Lo3HuwelijkOfGpInhoud> opschonenHuwelijkPersoonsgegevens(
            final Lo3Categorie<Lo3HuwelijkOfGpInhoud> categorie) {
        final Lo3HuwelijkOfGpInhoud inhoud = categorie.getInhoud();

        if (inhoud.getSoortVerbintenis() == null) {
            // Leegmaken persoonsgegevens
            final Lo3HuwelijkOfGpInhoud.Builder builder = new Lo3HuwelijkOfGpInhoud.Builder(inhoud);

            builder.setaNummer(null);
            builder.setBurgerservicenummer(null);
            builder.setGeslachtsaanduiding(null);
            builder.setAdellijkeTitelPredikaatCode(null);
            builder.setVoornamen(null);
            builder.setVoorvoegselGeslachtsnaam(null);
            builder.setGeslachtsnaam(null);
            builder.setGeboortedatum(null);
            builder.setGeboorteGemeenteCode(null);
            builder.setGeboorteLandCode(null);

            return new Lo3Categorie<Lo3HuwelijkOfGpInhoud>(builder.build(), categorie.getDocumentatie(),
                    categorie.getHistorie(), categorie.getLo3Herkomst());

        } else {
            return categorie;
        }
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private List<Lo3Stapel<Lo3KindInhoud>> opschonenKindStapels(final List<Lo3Stapel<Lo3KindInhoud>> stapels) {
        if (stapels == null || stapels.isEmpty()) {
            return stapels;
        }

        final List<Lo3Stapel<Lo3KindInhoud>> result = new ArrayList<Lo3Stapel<Lo3KindInhoud>>();

        for (final Lo3Stapel<Lo3KindInhoud> stapel : stapels) {
            final Lo3Stapel<Lo3KindInhoud> opgeschoondeStapel = opschonenKindStapel(stapel);

            if (opgeschoondeStapel != null) {
                result.add(opgeschoondeStapel);
            }
        }

        return result;
    }

    private Lo3Stapel<Lo3KindInhoud> opschonenKindStapel(final Lo3Stapel<Lo3KindInhoud> stapel) {
        final List<Lo3Categorie<Lo3KindInhoud>> result = new ArrayList<Lo3Categorie<Lo3KindInhoud>>();

        boolean cleaning = true;
        for (final Lo3Categorie<Lo3KindInhoud> kind : stapel) {
            if (kind.getInhoud().getIndicatieKind()) {
                cleaning = false;
            } else if (cleaning) {
                continue;
            }

            result.add(opschonenKindCategorie(kind));

        }

        return result.isEmpty() ? null : new Lo3Stapel<Lo3KindInhoud>(result);

    }

    private Lo3Categorie<Lo3KindInhoud> opschonenKindCategorie(final Lo3Categorie<Lo3KindInhoud> kind) {
        if (kind.getInhoud().getIndicatieKind()) {
            return kind;
        }

        final Lo3KindInhoud inhoud = kind.getInhoud();

        // Leegmaken persoonsgegevens
        final Lo3KindInhoud.Builder builder = new Lo3KindInhoud.Builder(inhoud);

        builder.setaNummer(null);
        builder.setBurgerservicenummer(null);
        builder.setGeboortedatum(null);
        builder.setGeboorteGemeenteCode(null);
        builder.setGeboorteLandCode(null);
        builder.setAdellijkeTitelPredikaatCode(null);
        builder.setVoornamen(null);
        builder.setVoorvoegselGeslachtsnaam(null);
        builder.setGeslachtsnaam(null);

        return new Lo3Categorie<Lo3KindInhoud>(builder.build(), kind.getDocumentatie(), kind.getHistorie(),
                kind.getLo3Herkomst());

    }
}
