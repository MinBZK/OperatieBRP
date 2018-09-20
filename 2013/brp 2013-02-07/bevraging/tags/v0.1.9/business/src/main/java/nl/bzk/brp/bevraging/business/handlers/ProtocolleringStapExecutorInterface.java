/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.handlers;

import java.util.Calendar;
import java.util.Collection;

import nl.bzk.brp.bevraging.business.dto.BerichtContext;
import nl.bzk.brp.bevraging.business.dto.antwoord.BerichtAntwoord;
import nl.bzk.brp.bevraging.domein.kern.Persoon;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * Inner class voor de uitvoering van de protocollering. Dit gebeurd in deze class en niet in de
 * {@link ProtocolleringStap} zelf, daar er anders transactie problemen optreden waardoor mogelijk optredende
 * excepties niet in de {@link ProtocolleringStap} worden gevangen, maar pas in de aanroeper van deze stap,
 * waarbij dat in dit geval dus niet zou moeten zijn.
 */
public interface ProtocolleringStapExecutorInterface {

    /**
     * Voert de protocollering uit over de opgegeven personen  en vult dus de juiste gegevens in de tabellen
     * Levering en Levering/Persoon.
     *
     * @param personen de personen waarover geprotocolleerd dient te worden.
     * @param context de context waarbinnen het berichtverzoek is uitgevoerd.
     * @param beschouwingsMoment het beschouwingmoment.
     * @param <T> Type van het antwoordbericht behorende bij het verzoek.
     * @return een boolean die aangeeft of de stap succesvol is uitgevoerd (true) of niet (false).
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    <T extends BerichtAntwoord> Long protocolleer(final Collection<Persoon> personen,
            final BerichtContext context, final Calendar beschouwingsMoment);

}
