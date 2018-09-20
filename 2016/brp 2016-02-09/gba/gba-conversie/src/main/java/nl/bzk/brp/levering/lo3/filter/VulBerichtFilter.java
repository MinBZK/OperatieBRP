/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import nl.bzk.brp.levering.lo3.conversie.OnderzoekUtil;
import nl.bzk.brp.levering.lo3.util.PersoonUtil;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.logisch.ist.Stapel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3GroepEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import org.springframework.stereotype.Component;

/**
 * Filter voor vulberichten.
 */
@Component("lo3_vulBerichtFilter")
public final class VulBerichtFilter implements Filter {

    /** Logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Override
    public List<Lo3CategorieWaarde> filter(
        final PersoonHisVolledig persoon,
        final List<Stapel> istStapels,
        final AdministratieveHandelingModel administratieveHandeling,
        final List<Lo3CategorieWaarde> categorieen,
        final List<String> lo3Filterrubrieken)
    {
        LOGGER.debug("Filter bericht op basis van abonnement filter rubrieken: {}", lo3Filterrubrieken);
        return filter(categorieen, lo3Filterrubrieken, true);
    }

    /**
     * Filter de gegeven categorieen op basis van de lijst met rubrieken (XX.XX.XX).
     *
     * @param categorieen
     *            lijst van LO3 categorieen
     * @param lo3Filterrubrieken
     *            lijst met rubrieken (format XX.XX.XX)
     * @param filterOnjuist
     *            indicatie of onjuiste categorieen eruit gefilterd moeten worden
     * @return gefiltered categorieen
     */
    public List<Lo3CategorieWaarde> filter(
        final List<Lo3CategorieWaarde> categorieen,
        final List<String> lo3Filterrubrieken,
        final boolean filterOnjuist)
    {
        return filter(categorieen, lo3Filterrubrieken, true, true);
    }

    /**
     * Filter de gegeven categorieen op basis van de lijst met rubrieken (XX.XX.XX).
     *
     * @param categorieen
     *            lijst van LO3 categorieen
     * @param lo3Filterrubrieken
     *            lijst met rubrieken (format XX.XX.XX)
     * @param filterOnjuist
     *            indicatie of onjuiste categorieen eruit gefilterd moeten worden
     * @param inclusiefStandaardElementen
     *            indicatie of standaard elementen moeten worden toegevoegd
     * @return gefiltered categorieen
     */
    public List<Lo3CategorieWaarde> filter(
        final List<Lo3CategorieWaarde> categorieen,
        final List<String> lo3Filterrubrieken,
        final boolean filterOnjuist,
        final boolean inclusiefStandaardElementen)
    {
        final List<Lo3CategorieWaarde> categorieenGefilterd = new ArrayList<>();

        if (lo3Filterrubrieken != null && !lo3Filterrubrieken.isEmpty()) {
            // Loop door categorieen heen en bouw nieuwe categorieen op met alleen de waardes van de filterrubrieken.
            for (final Lo3CategorieWaarde categorie : categorieen) {
                if (!filterOnjuist || !isOnjuist(categorie)) {
                    LOGGER.debug("Start: {}", categorie);
                    final Lo3CategorieWaarde categorieGefilterd =
                            new Lo3CategorieWaarde(categorie.getCategorie(), categorie.getStapel(), categorie.getVoorkomen());

                    filterElementen(lo3Filterrubrieken, categorie, categorieGefilterd, inclusiefStandaardElementen);
                    LOGGER.debug("Filter: {}", categorieGefilterd);

                    // Groep 83 Onderzoek verwijderen uit de categorie, als in de autorisatie geen gegevens zitten
                    // die onderzocht worden.
                    if (categorie.getElement(Lo3ElementEnum.ELEMENT_8310) != null && !"".equals(categorie.getElement(Lo3ElementEnum.ELEMENT_8310))) {
                        LOGGER.debug("Categorie bevat onderzoek");
                        if (!OnderzoekUtil.magOnderzoekWordenGeleverd(
                            categorie.getCategorie().getCategorie(),
                            categorie.getElement(Lo3ElementEnum.ELEMENT_8310),
                            lo3Filterrubrieken))
                        {
                            LOGGER.debug("Onderzoek mag niet geleverd worden obv filter rubrieken. Verwijderen...");
                            categorieGefilterd.addElement(Lo3ElementEnum.ELEMENT_8310, null);
                            categorieGefilterd.addElement(Lo3ElementEnum.ELEMENT_8320, null);
                            categorieGefilterd.addElement(Lo3ElementEnum.ELEMENT_8330, null);
                        }
                    }

                    // Controleren of de categorie alleen RNI en/of ONJUIST bevat.
                    if (bevatAlleenStandaardGeleverdeElementen(categorieGefilterd, lo3Filterrubrieken)) {
                        categorieGefilterd.getElementen().clear();
                    }
                    LOGGER.debug("Einde: {}", categorieGefilterd);

                    if (!categorieGefilterd.isEmpty()) {
                        categorieenGefilterd.add(categorieGefilterd);
                    }
                }
            }
        }

        // retourneer de gefilterde categorieen.
        return categorieenGefilterd;
    }

    private boolean bevatAlleenStandaardGeleverdeElementen(final Lo3CategorieWaarde categorie, final List<String> lo3Filterrubrieken) {
        for (final Entry<Lo3ElementEnum, String> entry : categorie.getElementen().entrySet()) {
            final Lo3ElementEnum element = entry.getKey();
            final Lo3GroepEnum groep = element.getGroep();
            final boolean isGroep71 = Lo3GroepEnum.GROEP71.equals(groep);
            final boolean isGroep83 = Lo3GroepEnum.GROEP83.equals(groep);
            final boolean isGroep84 = Lo3GroepEnum.GROEP84.equals(groep);
            final boolean isGroep88 = Lo3GroepEnum.GROEP88.equals(groep);
            if (!(isGroep71 || isGroep83 || isGroep84 || isGroep88)) {
                return false;
            }

            if (Lo3ElementEnum.ELEMENT_8310.equals(element)) {
                if (OnderzoekUtil.onderzoekSlaatOpDirectGeautoriseerdeRubriek(categorie.getCategorie(), entry.getValue(), lo3Filterrubrieken)) {
                    return false;
                }
            }
        }

        return true;
    }

    private void filterElementen(
        final List<String> lo3Filterrubrieken,
        final Lo3CategorieWaarde categorie,
        final Lo3CategorieWaarde categorieGefilterd,
        final boolean inclusiefStandaardElementen)
    {
        for (final Lo3ElementEnum element : categorie.getElementen().keySet()) {
            final String rubriek = categorie.getCategorie().getCategorie() + "." + element.getElementNummer(true);
            final boolean isGroep83 = Lo3GroepEnum.GROEP83.equals(element.getGroep());
            final boolean isGroep84 = Lo3GroepEnum.GROEP84.equals(element.getGroep());
            final boolean isRni = Lo3GroepEnum.GROEP71.equals(element.getGroep()) || Lo3GroepEnum.GROEP88.equals(element.getGroep());
            if (lo3Filterrubrieken.contains(rubriek) || (isGroep83 || isGroep84 || isRni) && inclusiefStandaardElementen) {
                categorieGefilterd.addElement(element, categorie.getElement(element));
            }
        }
    }

    /**
     * Bepaalt of de gegeven categorie onjuist is.
     *
     * @param categorie
     *            categorie
     * @return true, als de categorie element 84.10 bevat met een waarde
     */
    private boolean isOnjuist(final Lo3CategorieWaarde categorie) {
        final String onjuistString = categorie.getElement(Lo3ElementEnum.ELEMENT_8410);
        return onjuistString != null && !"".equals(onjuistString);
    }

    @Override
    public boolean leveringUitvoeren(final PersoonHisVolledig persoon, final List<Lo3CategorieWaarde> gefilterd) {
        return gefilterd != null && !gefilterd.isEmpty() && !PersoonUtil.isAfgevoerd(persoon);
    }
}
