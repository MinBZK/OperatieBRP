/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.stappen;

import nl.bzk.brp.model.bericht.ber.BerichtBericht;

/**
 * Abstract class voor de verschillende bericht verwerkings stappen. Elk bericht wordt in verschillende stappen
 * verwerkt, waarbij elke stap wordt geimplementeerd middels een implementatie van deze klasse. De verwerking
 * van een bericht gebeurd door achtereenvolgens op de voor het bericht benodigde BerichtVerwerkingsStap instanties
 * de centrale {#voerVerwerkingsStapUitVoorBericht(nl.bzk.brp.binding.bijhouding.BijhoudingsBericht , List<Melding>)}
 * methode aan te roepen.
 *
 * @param <T> Type Bericht waar deze stap voor wordt uitgevoerd.
 * @param <Y> Type BerichtResultaat waar deze stap eventuele resultaten en meldingen in opslaat.
 */
public abstract class AbstractBerichtVerwerkingsStap<T extends BerichtBericht, Y extends BerichtVerwerkingsResultaat>
        extends AbstractStap<T, BerichtContext, Y>
{

    /**
     * Resultaat voor de berichtverwerking die aangeeft dat het proces verder doorlopen kan worden; proces is dus nog
     * niet afgerond (geen fatale exceptie noch een eind situatie bereikt in deze stap).
     */
    public static final boolean DOORGAAN_MET_VERWERKING = Boolean.TRUE;

    /**
     * Resultaat voor de berichtverwerking die aangeeft dat het proces afgerond is na het doorlopen van deze stap; er
     * dient dus geen volgende stap in de keten doorlopen te worden daar er een fatale exceptie/fout is opgetreden in
     * de verwerking van deze stap, of er is een eind situatie opgetreden waardoor verdere stappen niet meer van
     * toepassing zijn.
     */
    public static final boolean STOP_VERWERKING = Boolean.FALSE;

    /**
     * Methode voor het uitvoeren van de verwerkingsstap waarvoor deze class dient. Het resultaat geeft aan of
     * de verwerking succesvol was en dus de volgende stap kan worden genomen, of dat de stap faalde en dus
     * de verdere verwerking gestopt dient te worden.
     *
     * @param bericht   Het binnenkomende bericht.
     * @param context   De context waarbinnen het bericht wordt uitgevoerd.
     * @param resultaat Resultaat met lijst om eventuele meldingen die door de stap worden gemaakt terug te geven
     *                  aan de client.
     * @return een boolean die aangeeft of de stap succesvol is uitgevoerd (true) of niet (false).
     */
    @Deprecated
    public abstract boolean voerVerwerkingsStapUitVoorBericht(final T bericht, final BerichtContext context,
                                                              final Y resultaat);

    /**
     * Methode die na uitvoering van de stap (en alle volgende stappen in het proces) wordt uitgevoerd. In deze
     * methode kunnen eventuele zaken worden opgeruimd en/of vrijgegeven die in deze stap (en de volgende stappen)
     * benodigd waren. Hierbij kan het bijvoorbeeld gaan om zaken als transacties, connecties, resources etc.
     *
     * @param bericht   Het binnenkomende bericht.
     * @param context   De context waarbinnen het bericht wordt uitgevoerd.
     * @param resultaat Resultaat met lijst om eventuele meldingen die door de stap worden gemaakt terug te
     *                  geven aan de client.
     */
    @Deprecated
    public void naVerwerkingsStapVoorBericht(final T bericht, final BerichtContext context, final Y resultaat) {
        voerNabewerkingStapUit(bericht, context, resultaat);
    }

    @Override
    public void voerNabewerkingStapUit(final T berichtBericht, final BerichtContext stappenContext,
                                       final Y berichtVerwerkingsResultaat)
    {
        //do nothing, niet elke stap heeft een naverwerking.
    }
}
