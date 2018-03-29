/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie.mutatie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Stapel;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.levering.lo3.conversie.ConversieCache;
import nl.bzk.brp.levering.lo3.conversie.Converteerder;
import nl.bzk.brp.levering.lo3.conversie.IdentificatienummerMutatie;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Mutatiebepaling.
 */
@Component("lo3_mutatieConverteerder")
public final class MutatieConverteerder implements Converteerder {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    public static final int NIEUWE_CATEGORIEEN = 2;

    private final List<Lo3ElementEnum> anummerAdministratieveElementen =
            Arrays.asList(
                    Lo3ElementEnum.ELEMENT_8110,
                    Lo3ElementEnum.ELEMENT_8120,
                    Lo3ElementEnum.ELEMENT_8210,
                    Lo3ElementEnum.ELEMENT_8220,
                    Lo3ElementEnum.ELEMENT_8230,
                    Lo3ElementEnum.ELEMENT_8410,
                    Lo3ElementEnum.ELEMENT_8510,
                    Lo3ElementEnum.ELEMENT_8610);
    private final List<Lo3ElementEnum> anummerAdministratieveOngewijzigdeElementen =
            Arrays.asList(
                    Lo3ElementEnum.ELEMENT_8310,
                    Lo3ElementEnum.ELEMENT_8320,
                    Lo3ElementEnum.ELEMENT_8330,
                    Lo3ElementEnum.ELEMENT_8810,
                    Lo3ElementEnum.ELEMENT_8820);
    private final List<Lo3ElementEnum> anummerInschrijvingElementen = Arrays.asList(Lo3ElementEnum.ELEMENT_8010, Lo3ElementEnum.ELEMENT_8020);

    @Autowired
    private MutatieVisitor mutatieVisitor;

    @Override
    public List<Lo3CategorieWaarde> converteer(
            final Persoonslijst persoon,
            final List<Stapel> istStapels,
            final AdministratieveHandeling administratieveHandeling,
            final IdentificatienummerMutatie identificatienummerMutatie,
            final ConversieCache conversieCache) {
        List<Lo3CategorieWaarde> resultaat = conversieCache.getMutatieCategorien();

        if (resultaat == null) {
            LOGGER.debug("converteer(persoon={}, administratieveHandeling={}, cache={})", persoon, administratieveHandeling, conversieCache);

            // Bepaal mutaties
            resultaat = mutatieVisitor.visit(persoon, istStapels, administratieveHandeling);

            // Verwijder wijzigingen van een a-nummer wijziging
            if (identificatienummerMutatie != null && identificatienummerMutatie.isAnummerWijziging()) {
                verwijderAnummerWijzigingUitResultaat(resultaat);
            }

            LOGGER.debug("registreer resultaat in cache");
            conversieCache.setMutatieCategorien(resultaat);
        }

        return resultaat;
    }

    private void verwijderAnummerWijzigingUitResultaat(final List<Lo3CategorieWaarde> categorieen) {
        verwijderAnummerWijzigingUitCategorie01(categorieen);
        if (alleenInschrijvingAanwezig(categorieen)) {
            categorieen.clear();
        }
    }

    private void verwijderAnummerWijzigingUitCategorie01(final List<Lo3CategorieWaarde> categorieen) {
        final List<Integer> teVerwijderenIndices = new ArrayList<>();
        for (int index = 0; index < categorieen.size(); index = index + NIEUWE_CATEGORIEEN) {
            final Lo3CategorieWaarde actueleCategorie = categorieen.get(index);
            final Lo3CategorieWaarde historischeCategorie = categorieen.get(index + 1);

            if (Lo3CategorieEnum.CATEGORIE_01 == actueleCategorie.getCategorie()) {
                actueleCategorie.addElement(Lo3ElementEnum.ELEMENT_0110, null);
                actueleCategorie.addElement(Lo3ElementEnum.ELEMENT_2010, null);
                historischeCategorie.addElement(Lo3ElementEnum.ELEMENT_0110, null);
                historischeCategorie.addElement(Lo3ElementEnum.ELEMENT_2010, null);

                if (categorieBevatAlleenAdministratieveGegevens(actueleCategorie, historischeCategorie)) {
                    actueleCategorie.getElementen().clear();
                    historischeCategorie.getElementen().clear();
                }
            }

            // Haal categorieen uit lijst indien leeg
            if (actueleCategorie.isEmpty() && historischeCategorie.isEmpty()) {
                teVerwijderenIndices.add(index);
                teVerwijderenIndices.add(index + 1);
            }
        }
        Collections.sort(teVerwijderenIndices, Collections.reverseOrder());
        teVerwijderenIndices.forEach(i -> categorieen.remove(i.intValue()));

    }

    private boolean categorieBevatAlleenAdministratieveGegevens(final Lo3CategorieWaarde actueleCategorie, final Lo3CategorieWaarde historischeCategorie) {
        boolean resultaat = true;
        for (final Lo3ElementEnum element : actueleCategorie.getElementen().keySet()) {
            if (!anummerAdministratieveElementen.contains(element)) {
                resultaat = checkAdministratieveOngewijzigdeElementen(actueleCategorie, historischeCategorie, element);
                if (!resultaat) {
                    break;
                }
            }
        }
        return resultaat;
    }

    private boolean checkAdministratieveOngewijzigdeElementen(final Lo3CategorieWaarde actueleCategorie, final Lo3CategorieWaarde historischeCategorie,
                                                              final Lo3ElementEnum element) {
        boolean resultaat = true;
        if (anummerAdministratieveOngewijzigdeElementen.contains(element)) {
            // Het betreft een administratief element wat altijd geleverd wordt, maar niet gewijzigd mag worden bij
            // een a-nummer wijziging.
            if (!actueleCategorie.getElement(element).equals(historischeCategorie.getElement(element))) {
                resultaat = false;
            }
        } else {
            resultaat = false;
        }
        return resultaat;
    }

    private boolean alleenInschrijvingAanwezig(final List<Lo3CategorieWaarde> categorieen) {
        return IntStream.range(0, categorieen.size())
                 .filter(this::isActueleCategorie)
                 .mapToObj(categorieen::get)
                 .allMatch(this::isCategorie07AnummerInschrijvingElement);
    }

    private boolean isActueleCategorie(final int index) {
        return index % NIEUWE_CATEGORIEEN == 0;
    }

    private boolean isCategorie07AnummerInschrijvingElement(final Lo3CategorieWaarde categorie) {
        boolean result = false;
        if (Lo3CategorieEnum.CATEGORIE_07 == categorie.getCategorie()) {
            result = categorie.getElementen().keySet().stream().allMatch(anummerInschrijvingElementen::contains);
        }
        return result;
    }

}
