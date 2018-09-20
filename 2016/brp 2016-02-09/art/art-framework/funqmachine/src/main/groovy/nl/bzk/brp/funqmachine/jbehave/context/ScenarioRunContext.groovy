package nl.bzk.brp.funqmachine.jbehave.context

/**
 * Context voor uitwisseling van informatie tussen verschillende {@link nl.bzk.brp.funqmachine.jbehave.steps.Steps} klasses
 * tijdens het uitvoeren van scenario's. Op deze wijze kan bv. de data
 * die wordt ingelezen in meerdere klasses beschikbaar zijn en resultaten uit
 * meerdere klasses worden verzameld.
 */
interface ScenarioRunContext {

    /**
     * Start een context.
     */
    void start();

    /**
     * Schrijf de resultaten uit de context weg.
     * @param contextView extra informatie benodigd voor het wegschrijven
     */
    void schrijfResultaat(BevraagbaarContextView contextView);

    /**
     * Verwijder de tot nu toe verzamelde resultaten.
     */
    void discardResultaten();

    /**
     * Stop de huidige context.
     */
    void stop();

    /**
     * Geef de data uit deze context.
     * @return de data in de context
     */
    Map<String, Object> getData();

    /**
     * Zet data in de context.
     * @param data de data om in de context te bewaren
     */
    void setData(Map<String, Object> data);

    /**
     * Geeft terug of de cache gerefreshed dient te worden tbv activatie van abonnementen.
     */
    boolean isCacheHeeftActivatieRefreshNodig()

    /**
     * Geef aan context mee of cache gerefreshed dient te worden tbv activatie.
     * @param cacheHeeftRefreshNodig boolean die zegt of cache herladen dient te worden
     */
    void setCacheHeeftActivatieRefreshNodig(boolean cacheHeeftActivatieRefreshNodig)

    /**
     * Geeft terug of de cache compleet herladen dient te worden.
     */
    boolean isCacheHeeftRefreshNodig()

    /**
     * Geef aan context mee of cache compleet herladen dient te worden.
     * @param cacheHeeftRefreshNodig boolean die zegt of cache herladen dient te worden
     */
    void setCacheHeeftRefreshNodig(boolean cacheHeeftRefreshNodig)

    /**
     * @return XML request van synchrone verzoek
     */
    StepResult geefLaatsteVerzoek()

}
