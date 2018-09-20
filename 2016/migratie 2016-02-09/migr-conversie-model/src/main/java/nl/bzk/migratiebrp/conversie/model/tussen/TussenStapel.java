/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.tussen;

import java.util.List;
import nl.bzk.migratiebrp.conversie.model.Stapel;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;
import org.simpleframework.xml.ElementList;

/**
 * Deze class representeert een Migratie stapel. Dit is een lijst met Migratie groepen die samen de actuele en
 * historische gegevens van deze groep representeren.
 * 
 * De stapel staat het niet toe dat de lijst van elementen wordt gewijzigd. Indien dus de elementen waaruit de lijst
 * bestaat immutable zijn is deze class immutable en dus thread safe. *
 * 
 * @param <I>
 *            een groep inhoud type
 */
public class TussenStapel<I extends BrpGroepInhoud> extends Stapel<TussenGroep<I>> {

    /**
     * Maakt een Migratie-groep stapel aan.
     * 
     * @param groepen
     *            de lijst met Migratie groepen. Deze mag niet leeg zijn.
     * @throws IllegalArgumentException
     *             als groepen een lege lijst is
     * @throws NullPointerException
     *             als groepen null is
     */
    public TussenStapel(@ElementList(name = "groepen", inline = true, type = TussenGroep.class) final List<TussenGroep<I>> groepen) {
        super(groepen);
    }

    /**
     * Geef de waarde van groepen.
     *
     * @return BRP groepen
     */
    @ElementList(name = "groepen", inline = true, type = TussenGroep.class)
    public final List<TussenGroep<I>> getGroepen() {
        return super.getElementen();
    }

    /**
     * Bepaal of er alleen lege elementen in deze stapel zitten.
     * 
     * @return true als er ten minste 1 element niet leeg is in deze stapel
     */
    public final boolean isStapelMetAlleenLegeInhoud() {
        for (final TussenGroep<I> groep : getGroepen()) {
            if (!groep.isInhoudelijkLeeg()) {
                return false;
            }
        }
        return true;
    }
}
