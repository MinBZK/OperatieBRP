/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijstBuilder;
import nl.bzk.migratiebrp.conversie.model.brp.BrpStapel;

/**
 * De class helpt bij het maken van een Builder op basis van een generieke lijst met stapels.
 */
class BrpPersoonslijstBuilderVisitor {

    private final BrpPersoonslijstBuilder builder;

    /**
     * Maakt een BrpPersoonslijstBuilderVisitor object.
     * @param builder de builder
     */
    BrpPersoonslijstBuilderVisitor(final BrpPersoonslijstBuilder builder) {
        this.builder = builder;
    }

    BrpPersoonslijstBuilderVisitor addStapels(final List<BrpStapel<?>> stapels) {
        for (final BrpStapel<?> stapel : stapels) {
            voegStapelToeAanBuilder(stapel);
        }
        return this;
    }

    BrpPersoonslijst build() {
        return builder.build();
    }

    private void voegStapelToeAanBuilder(final BrpStapel<?> stapel) {
        final Object inhoud = stapel.getLaatsteElement().getInhoud();
        final Method builderMethod = getBuilderMethode(inhoud);
        try {
            builderMethod.invoke(builder, stapel);
        } catch (final IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (final InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private Method getBuilderMethode(final Object groepInhoud) {
        final String methodeNaam = bepaaldMethodeNaam(groepInhoud);
        try {
            return builder.getClass().getMethod(methodeNaam, BrpStapel.class);
        } catch (final NoSuchMethodException e) {
            throw new IllegalArgumentException(String.format(
                    "Er kon geen methode gevonden worden in de BrpPersoonslijstBuilder met de signature: %s(%s)",
                    methodeNaam,
                    BrpStapel.class.getSimpleName()), e);
        }
    }

    private static String bepaaldMethodeNaam(final Object groepInhoud) {
        final String classNaam = groepInhoud.getClass().getSimpleName();
        // verwijder brp prefix
        String naam = classNaam.substring("Brp".length());
        // verwijder Inhoud suffix
        naam = naam.substring(0, naam.indexOf("Inhoud"));
        // lowercase eerste letter
        final String eersteLetter = naam.substring(0, 1);
        naam = eersteLetter.toLowerCase() + naam.substring(1);
        // Stapel suffix toevoegen
        naam = naam + "Stapel";
        return naam;
    }
}
