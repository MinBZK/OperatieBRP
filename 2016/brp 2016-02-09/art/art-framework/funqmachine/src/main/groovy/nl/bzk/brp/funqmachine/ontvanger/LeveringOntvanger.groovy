package nl.bzk.brp.funqmachine.ontvanger

/**
 * Ontvanger interface voor het ontvangen van asynchrone soap berichten.
 */
interface LeveringOntvanger {

    /**
     * Stop de ontvanger.
     * @param waitTime de wachttijd voor het stoppen
     */
    void stop(int waitTime)

    /**
     * Reset de ontvanger.
     */
    void reset()

    /**
     * Geeft de URL waarop de ontvanger beschikbaar is.
     * @return de URL
     */
    URL getUrl()

    /**
     * Geeft het aantal ontvangen berichten.
     * @return het aantal ontvangen berichten
     */
    int getReceivedMessages()

    /**
     * Geef het bericht op de gegeven index.
     *
     * @param number de index
     * @return het bericht op de gegeven index
     */
    String getMessage(int number)

    /**
     * Geef alle ontvangen berichten.
     * @return
     */
    List<String> getMessages()
}
