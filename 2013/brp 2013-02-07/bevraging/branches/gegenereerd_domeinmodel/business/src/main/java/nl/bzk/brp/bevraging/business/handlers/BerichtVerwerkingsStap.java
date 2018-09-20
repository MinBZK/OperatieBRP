/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.handlers;

import nl.bzk.brp.bevraging.business.dto.BerichtContext;
import nl.bzk.brp.bevraging.business.dto.antwoord.BerichtAntwoord;
import nl.bzk.brp.bevraging.business.dto.verzoek.BerichtVerzoek;


/**
 * Interface voor de verschillende bericht verwerkings stappen. Elk bericht wordt in verschillende stappen
 * verwerkt, waarbij elke stap wordt geimplementeerd middels een implementatie van deze interface. De verwerking
 * van een bericht gebeurd door achtereenvolgens op de voor het bericht benodigde BerichtVerwerkingsStap instanties
 * de centrale {@link #voerVerwerkingsStapUitVoorBericht(BerichtVerzoek, BerichtContext, BerichtAntwoord)} methode aan
 * te roepen.
 */
public interface BerichtVerwerkingsStap {

    /**
     * Resultaat voor de berichtverwerking die aangeeft dat het proces verder doorlopen kan worden; proces is dus nog
     * niet afgerond (geen fatale exceptie noch een eind situatie bereikt in deze stap).
     */
    boolean DOORGAAN_MET_VERWERKING = Boolean.TRUE;

    /**
     * Resultaat voor de berichtverwerking die aangeeft dat het proces afgerond is na het doorlopen van deze stap; er
     * dient dus geen volgende stap in de keten doorlopen te worden daar er een fatale exceptie/fout is opgetreden in
     * de verwerking van deze stap, of er is een eind situatie opgetreden waardoor verdere stappen niet meer van
     * toepassing zijn.
     */
    boolean STOP_VERWERKING         = Boolean.FALSE;

    /**
     * Methode voor het uitvoeren van de verwerkingsstap waarvoor deze class dient. Het resultaat geeft aan of
     * de verwerking succesvol was en dus de volgende stap kan worden genomen, of dat de stap faalde en dus
     * de verdere verwerking gestopt dient te worden.
     *
     * @param verzoek het berichtverzoek waarvoor de verwerkingsstap dient te worden uitgevoerd.
     * @param context de context waarbinnen het berichtverzoek dient te worden uitgevoerd.
     * @param antwoord het antwoordbericht dat in de stap kan worden (aan)gevuld.
     * @param <T> Type van het antwoordbericht behorende bij het verzoek.
     * @return een boolean die aangeeft of de stap succesvol is uitgevoerd (true) of niet (false).
     */
    <T extends BerichtAntwoord> boolean voerVerwerkingsStapUitVoorBericht(BerichtVerzoek<T> verzoek,
            BerichtContext context, T antwoord);

    /**
     * Methode die na uitvoering van de stap (en alle volgende stappen in het proces) wordt uitgevoerd. In deze
     * methode kunnen eventuele zaken worden opgeruimd en/of vrijgegeven die in deze stap (en de volgende stappen)
     * benodigd waren. Hierbij kan het bijvoorbeeld gaan om zaken als transacties, connecties, resources etc.
     *
     * @param verzoek het berichtverzoek waarvoor de verwerkingsstap dient te worden uitgevoerd.
     * @param context de context waarbinnen het berichtverzoek dient te worden uitgevoerd.
     */
    void naVerwerkingsStapVoorBericht(BerichtVerzoek<? extends BerichtAntwoord> verzoek, BerichtContext context);

}
