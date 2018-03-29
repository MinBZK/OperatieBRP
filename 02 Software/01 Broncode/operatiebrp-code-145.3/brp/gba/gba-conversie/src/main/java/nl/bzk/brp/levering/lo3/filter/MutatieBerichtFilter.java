/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.filter;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Stapel;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.levering.lo3.conversie.IdentificatienummerMutatie;
import nl.bzk.brp.levering.lo3.util.PersoonUtil;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3GroepEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import org.springframework.stereotype.Component;

/**
 * Filter voor mutatieberichten.
 */
@Component("lo3_mutatieBerichtFilter")
public final class MutatieBerichtFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static final String PREFIX_1 = "1";
    private static final String PREFIX_0 = "0";
    public static final int CATEGORIEEN_OUD_NIEUW = 2;

    /**
     * Let op: aanname dat de categorieen 'kloppen' voor een mutatie. Dus setjes van twee categorieen waarbij de eerste altijd een actuele is en de tweede
     * altijd de bijbehorende historische.
     * <p>
     * {@inheritDoc}
     * @throws IllegalArgumentException wanneer de categorieen niet 'kloppen'
     */
    @Override
    public List<Lo3CategorieWaarde> filter(
            // Input
            final Persoonslijst persoon,
            final List<Stapel> istStapels,
            final AdministratieveHandeling administratieveHandeling,
            final IdentificatienummerMutatie identificatienummerMutatieResultaat,
            // Variabele
            final List<Lo3CategorieWaarde> categorieen,
            // Config
            final List<String> lo3Filterrubrieken) {
        LOGGER.debug("Filter rubrieken: {}", lo3Filterrubrieken);
        final List<Lo3CategorieWaarde> gefilteredCategorieen = new ArrayList<>();

        if (categorieen != null) {
            controleerCorrectAantalCategorieen(categorieen);

            final List<String> lo3MutatieFilterrubrieken = converteerVoorMutatieFilter(lo3Filterrubrieken);
            LOGGER.debug("Mutatie filter rubrieken: {}", lo3MutatieFilterrubrieken);

            for (int index = 0; index < categorieen.size(); index = index + CATEGORIEEN_OUD_NIEUW) {
                final Lo3CategorieWaarde actueleCategorie = categorieen.get(index);
                final Lo3CategorieWaarde historischeCategorie = categorieen.get(index + 1);
                LOGGER.debug("Before:\n{}\n{}", actueleCategorie, historischeCategorie);

                if (!actueleCategorie.getCategorie().isActueel()
                        || historischeCategorie.getCategorie().isActueel()
                        || historischeCategorie.getCategorie() != Lo3CategorieEnum.bepaalHistorischeCategorie(actueleCategorie.getCategorie(), true)) {
                    throw new IllegalArgumentException(
                            "Ongeldige categorieen bij mutatie levering (actuele categorie="
                                    + actueleCategorie.getCategorie()
                                    + ", historische categorie="
                                    + historischeCategorie.getCategorie()
                                    + ").");
                }

                final Lo3CategorieWaarde gefilterdeActueleCategorie = filterCategorie(lo3MutatieFilterrubrieken, actueleCategorie);
                final Lo3CategorieWaarde gefilterdeHistorischeCategorie = filterCategorie(lo3MutatieFilterrubrieken, historischeCategorie);
                LOGGER.debug("Na basis filter:\n{}\n{}", gefilterdeActueleCategorie, gefilterdeHistorischeCategorie);

                evalueerOnderzoek(lo3MutatieFilterrubrieken, gefilterdeActueleCategorie, gefilterdeHistorischeCategorie);
                LOGGER.debug("Na evalueer onderzoek:\n{}\n{}", gefilterdeActueleCategorie, gefilterdeHistorischeCategorie);

                if (!gefilterdeActueleCategorie.isEmpty() || !gefilterdeHistorischeCategorie.isEmpty()) {
                    gefilteredCategorieen.add(gefilterdeActueleCategorie);
                    gefilteredCategorieen.add(gefilterdeHistorischeCategorie);
                }
            }
        }

        return gefilteredCategorieen;
    }

    private void controleerCorrectAantalCategorieen(final List<Lo3CategorieWaarde> categorieen) {
        if (categorieen.size() % CATEGORIEEN_OUD_NIEUW != 0) {
            throw new IllegalArgumentException("Ongeldig (oneven) aantal categorieen bij mutatie levering.");
        }
    }

    private Lo3CategorieWaarde filterCategorie(final List<String> lo3Filterrubrieken, final Lo3CategorieWaarde categorie) {
        final Lo3CategorieWaarde gefilterdeCategorie = new Lo3CategorieWaarde(categorie.getCategorie(), categorie.getStapel(), categorie.getVoorkomen());

        for (final Lo3ElementEnum element : categorie.getElementen().keySet()) {
            final String rubriek = categorie.getCategorie().getCategorie() + "." + element.getElementNummer(true);
            final boolean isGroep83 = Lo3GroepEnum.GROEP83.equals(element.getGroep());
            final boolean isGroep84 = Lo3GroepEnum.GROEP84.equals(element.getGroep());
            final boolean isRni = Lo3GroepEnum.GROEP71.equals(element.getGroep()) || Lo3GroepEnum.GROEP88.equals(element.getGroep());
            if (lo3Filterrubrieken.contains(rubriek) || isGroep83 || isGroep84 || isRni) {
                gefilterdeCategorie.addElement(element, categorie.getElement(element));
            }
        }

        return gefilterdeCategorie;
    }

    private void evalueerOnderzoek(
            final List<String> lo3Filterrubrieken,
            final Lo3CategorieWaarde gefilterdeActueleCategorie,
            final Lo3CategorieWaarde gefilterdeHistorischeCategorie) {
        final String actueleOnderzoek = gefilterdeActueleCategorie.getElement(Lo3ElementEnum.ELEMENT_8310);
        final String historischeOnderzoek = gefilterdeHistorischeCategorie.getElement(Lo3ElementEnum.ELEMENT_8310);

        if (actueleOnderzoek == null && historischeOnderzoek == null) {
            // Geen onderzoeksgegevens
            return;
        }

        final String gefilterdeActueleOnderzoek = filterOnderzoek(lo3Filterrubrieken, gefilterdeActueleCategorie.getCategorie(), actueleOnderzoek);
        filterGeautoriseerdeGegevens(gefilterdeActueleCategorie, gefilterdeActueleOnderzoek);
        final String gefilterdeHistorischeOnderzoek =
                filterOnderzoek(lo3Filterrubrieken, gefilterdeHistorischeCategorie.getCategorie(), historischeOnderzoek);
        filterGeautoriseerdeGegevens(gefilterdeHistorischeCategorie, gefilterdeHistorischeOnderzoek);

        if ((gefilterdeActueleOnderzoek == null || "".equals(gefilterdeActueleOnderzoek))
                && (gefilterdeHistorischeOnderzoek == null || "".equals(gefilterdeHistorischeOnderzoek))) {
            // Beide actueel en historisch zijn nu 'leeg' (kan doordat het onderzoek zich niet strekt tot
            // de geautoriseerde gegevens, of omdat er geen onderzoek was/is).
            // Dan mogen de onderzoeksgegevens worden verwijderd.
            gefilterdeActueleCategorie.addElement(Lo3ElementEnum.ELEMENT_8310, null);
            gefilterdeActueleCategorie.addElement(Lo3ElementEnum.ELEMENT_8320, null);
            gefilterdeActueleCategorie.addElement(Lo3ElementEnum.ELEMENT_8330, null);
            gefilterdeHistorischeCategorie.addElement(Lo3ElementEnum.ELEMENT_8310, null);
            gefilterdeHistorischeCategorie.addElement(Lo3ElementEnum.ELEMENT_8320, null);
            gefilterdeHistorischeCategorie.addElement(Lo3ElementEnum.ELEMENT_8330, null);
        }
    }

    private void filterGeautoriseerdeGegevens(final Lo3CategorieWaarde gefilterdeActueleCategorie, final String gefilterdeActueleOnderzoek) {
        if (gefilterdeActueleOnderzoek == null) {
            // Actuele onderzoek strekt zich niet uit tot geautoriseerde gegevens
            gefilterdeActueleCategorie.addElement(Lo3ElementEnum.ELEMENT_8310, "");
            gefilterdeActueleCategorie.addElement(Lo3ElementEnum.ELEMENT_8320, "");
            if (gefilterdeActueleCategorie.getElement(Lo3ElementEnum.ELEMENT_8330) != null) {
                gefilterdeActueleCategorie.addElement(Lo3ElementEnum.ELEMENT_8330, "");
            }
        }
    }

    private String filterOnderzoek(final List<String> lo3Filterrubrieken, final Lo3CategorieEnum categorie, final String onderzoek) {
        final String result;
        if (onderzoek == null || "".equals(onderzoek)) {
            result = null;
        } else {
            if (OnderzoekUtil.onderzoekSlaatOpDirectGeautoriseerdeRubriek(categorie, onderzoek, lo3Filterrubrieken)) {
                result = onderzoek;
            } else {
                result = null;
            }
        }

        return result;
    }

    /**
     * Converteer een set aan filter rubrieken zodat deze bruikbaar is voor een mutatie bericht.
     * @param rubrieken rubrieken zoals opgeslagen als filter bij het abonnement
     * @return rubrieken voor vulberichten filter maar aangepast voor mutatiebericht
     */
    private List<String> converteerVoorMutatieFilter(final List<String> rubrieken) {
        final List<String> result = new ArrayList<>();

        if (rubrieken != null) {
            for (final String rubriek : rubrieken) {
                if (isActueel(rubriek)) {
                    result.add(rubriek);
                    result.add(maakHistorisch(rubriek));
                }
            }
        }

        return result;
    }

    /**
     * Bepaalt of een rubriek actueel is.
     * @param rubriek rubriek
     * @return true als de rubriek actueel is
     */
    private boolean isActueel(final String rubriek) {
        return rubriek.startsWith(PREFIX_0) || rubriek.startsWith(PREFIX_1);

    }

    /**
     * Maakt een rubriek historisch.
     * @param rubriek rubriek
     * @return historische rubriek
     */
    private String maakHistorisch(final String rubriek) {
        if (rubriek.startsWith(PREFIX_0)) {
            return "5" + rubriek.substring(1);
        } else if (rubriek.startsWith(PREFIX_1)) {
            return "6" + rubriek.substring(1);
        } else {
            throw new IllegalArgumentException("Kan rubriek " + rubriek + " niet historisch maken.");
        }
    }

    @Override
    public boolean leveringUitvoeren(final Persoonslijst persoon, final List<Lo3CategorieWaarde> gefilterd) {
        return gefilterd != null && !gefilterd.isEmpty() && !PersoonUtil.isAfgevoerd(persoon);
    }

}
