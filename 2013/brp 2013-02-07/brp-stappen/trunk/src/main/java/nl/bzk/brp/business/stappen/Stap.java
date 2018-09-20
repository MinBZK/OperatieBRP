/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.stappen;

import nl.bzk.brp.model.basis.ObjectType;

/**
 *
 */
public interface Stap<O extends ObjectType, C extends StappenContext, R extends StappenResultaat> {

      /**
     * Methode voor het uitvoeren van stap waarvoor deze class dient. Het resultaat geeft aan of
     * de stap succesvol was en dus de volgende stap kan worden genomen, of dat de stap faalde en dus
     * dat er gestopt dient te worden.
     *
     * @param onderwerp Het onderwerp van deze stap, dit kan een bericht zijn of een Persoon of elk ander ObjectType.
     * @param context De context waarbinnen de stap wordt uitgevoerd.
     * @param resultaat Resultaat met lijst om eventuele meldingen die door de stap worden gemaakt terug te geven
     * aan de client.
     * @return een boolean die aangeeft of de stap succesvol is uitgevoerd (true) of niet (false).
     */
    public boolean voerStapUit(final O onderwerp, final C context, final R resultaat);

    /**
     * Methode die na uitvoering van de stap (en alle volgende stappen in het proces) wordt uitgevoerd. In deze
     * methode kunnen eventuele zaken worden opgeruimd en/of vrijgegeven die in deze stap (en de volgende stappen)
     * benodigd waren. Hierbij kan het bijvoorbeeld gaan om zaken als transacties, connecties, resources etc.
     *
     * @param onderwerp Het binnenkomende bericht.
     * @param context De context waarbinnen het bericht wordt uitgevoerd.
     * @param resultaat Resultaat met lijst om eventuele meldingen die door de stap worden gemaakt terug te
     * geven aan de client.
     */
    public void voerNabewerkingStapUit(O onderwerp, C context, R resultaat);
}
