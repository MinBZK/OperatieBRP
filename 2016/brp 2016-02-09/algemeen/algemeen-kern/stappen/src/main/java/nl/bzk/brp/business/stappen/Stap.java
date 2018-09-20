/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.stappen;

import nl.bzk.brp.model.basis.BrpObject;


/**
 * Interface voor een stap in een stappenproces.
 *
 * @param <O> het type onderwerp dat wordt verwerkt
 * @param <C> het type context
 * @param <R> het type resultaat
 */
public interface Stap<O extends BrpObject, C extends StappenContext, R extends StappenResultaat> {

    /**
     * Resultaat voor de verwerking dat aangeeft dat het proces verder doorlopen kan worden;
     * proces is dus nog niet afgerond (geen fatale exceptie noch een eindsituatie bereikt in deze stap).
     */
    boolean DOORGAAN = Boolean.TRUE;

    /**
     * Resultaat voor de verwerking dat aangeeft dat het proces afgerond is na het doorlopen van deze stap;
     * er dient dus geen volgende stap in de keten doorlopen te worden daar er een fatale exceptie/fout is opgetreden in de verwerking van deze stap,
     * of er is een eindsituatie ontstaan waardoor verdere stappen niet meer van toepassing zijn.
     */
    boolean STOPPEN = Boolean.FALSE;

    /**
     * Voert de stap uit.
     *
     * De returnwaarde geeft aan of de stap succesvol was of dat de stap faalde.
     *
     * @param onderwerp Het onderwerp van deze stap, dit kan een bericht zijn of een Persoon of elk ander ObjectType.
     * @param context   De context waarbinnen de stap wordt uitgevoerd (bevat informatie en kan informatie meekrijgen).
     * @param resultaat Resultaat met lijst om eventuele meldingen die door de stap worden gemaakt terug te geven
     *                  aan de client.
     * @return een boolean die aangeeft of de stap succesvol is uitgevoerd (true) of niet (false).
     */
    boolean voerStapUit(final O onderwerp, final C context, final R resultaat);

    /**
     * Voert de nabewerking van een stap uit.
     *
     * Moet worden aangeroepen na uitvoering van de stap (en alle volgende stappen in het proces).
     * Hierin kunnen eventuele zaken worden opgeruimd en/of vrijgegeven die in deze stap (en de volgende stappen)
     * benodigd waren. Hierbij kan het bijvoorbeeld gaan om zaken als transacties, connecties, resources etc.
     *
     * @param onderwerp Het binnenkomende bericht.
     * @param context   De context waarbinnen het bericht wordt uitgevoerd.
     * @param resultaat Resultaat met lijst van eventuele meldingen die door de stap worden gemaakt terug te
     *                  geven aan de client.
     */
    void voerNabewerkingStapUit(O onderwerp, C context, R resultaat);
}
