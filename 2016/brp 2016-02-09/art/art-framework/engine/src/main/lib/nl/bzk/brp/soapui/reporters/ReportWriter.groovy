package nl.bzk.brp.soapui.reporters

/**
 * Interface voor het wegschrijven van rapportages.
 */
interface ReportWriter {
    /**
     * Schrijft de rapportage.
     *
     * @param results de resultaten die in het rapport moeten komen
     */
    void writeReport(List<Properties> results)
}
