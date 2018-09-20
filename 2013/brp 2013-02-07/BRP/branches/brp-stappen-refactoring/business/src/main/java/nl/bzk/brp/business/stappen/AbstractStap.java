/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.stappen;

import nl.bzk.brp.model.basis.ObjectType;

/**
 * Abstract class voor een stappen mechanisme te implementeren. Een process dat verschillende stappen
 * gebruikt, waarbij elke stap wordt geimplementeerd middels een implementatie van deze klasse.
 * De stappen voeren de methode voerStapUit uit, en kunnen optioneel ook een voerNabewerkingStapUit uitvoeren.
 *
 * @param <T> Type Objecttype waar deze stap voor wordt uitgevoerd.
 * @param <Y> Type Resultaat waar deze stap eventuele resultaten en meldingen in opslaat.
 */
public abstract class AbstractStap<T extends ObjectType, Y extends StappenResultaat> {

    /**
     * Resultaat voor de berichtverwerking die aangeeft dat het proces verder doorlopen kan worden; proces is dus nog
     * niet afgerond (geen fatale exceptie noch een eind situatie bereikt in deze stap).
     */
    public static final boolean DOORGAAN = Boolean.TRUE;

    /**
     * Resultaat voor de berichtverwerking die aangeeft dat het proces afgerond is na het doorlopen van deze stap; er
     * dient dus geen volgende stap in de keten doorlopen te worden daar er een fatale exceptie/fout is opgetreden in
     * de verwerking van deze stap, of er is een eind situatie opgetreden waardoor verdere stappen niet meer van
     * toepassing zijn.
     */
    public static final boolean STOPPEN = Boolean.FALSE;


}
