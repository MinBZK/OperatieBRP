package nl.bzk.brp.soapui

import groovy.transform.CompileStatic
import nl.bzk.brp.soapui.steps.AssertionResults
import nl.bzk.brp.soapui.steps.ControlValues

import static nl.bzk.brp.soapui.excel.OutputKolommen.*

/**
 * Wrapper voor de resultaten van de ARTEngine.
 */
@CompileStatic
class Resultaten implements Iterable<Resultaat> {
    List<Properties> statusList = []

    /**
     * Constructor.
     * @param list
     */
    Resultaten(List<Properties> list) {
        statusList = list ?: new ArrayList<Properties>(1)
    }

    /**
     * Support voor de {@code <<} operator.
     * @param p
     * @return
     */
    Resultaten leftShift(Properties p) {
        voegToe(p)
        return this
    }

    /**
     * De grootte van de resultaten.
     * @return de grootte van de resultaten
     */
    int size() {
       statusList.size()
    }

    /**
     * Voeg een resultaat toe, in de vorm van een properties bestand.
     * @param resultaat een resultaat object
     * @return
     */
    boolean voegToe(Properties resultaat) { statusList << resultaat }

    /**
     * Voeg een resultaat toe, aan de hand van de waardes in de meegegeven parameters.
     * @param controlValues de control values
     * @param assertionResults de assertion results
     * @return
     */
    boolean voegToe(ControlValues controlValues, AssertionResults assertionResults) {

        if (controlValues.getTestGeval()) {
            Properties assertProperties = new Properties()

            assertProperties.put(TestGeval.naam, controlValues.getTestGeval() ?: 'onbekend')
            assertProperties.put(Volgnummer.naam, controlValues.getVolgNr() ?: '0')
            assertProperties.put(Beschrijving.naam, controlValues.getBeschrijving() ?: '')

            String debug_log = assertionResults.getDebugLog()
            String status = controlValues.getStatus() == 'QUARANTAINE' ? controlValues.getStatus() : assertionResults.getStatus()
            String assertion = assertionResults.getAssertion()
            String openstaandeIssues = assertionResults.getOpenstaandeIssues()
            String opmerkingen = assertionResults.getOpmerkingen()

            if (debug_log) { assertProperties.put(Debug_Log.naam, debug_log) }
            if (status) { assertProperties.put(Status.naam, status) }
            if (openstaandeIssues) { assertProperties.put(Openstaande_Issues.naam, openstaandeIssues) }
            if (opmerkingen) { assertProperties.put(Opmerkingen.naam, opmerkingen) }
            if (null != assertion) { assertProperties.put(Assertion.naam, assertion) }

            voegToe(assertProperties)
        }

        false
    }

    /**
     * Het aantal testcases dat is overgeslagen.
     * @return getal >= 0
     */
    int skipped() {
        int count = 0
        this.each { Resultaat r -> if (r.isSkipped()) {count++} }
        return count
    }

    /**
     * Het aantal testcases dat is gefaald.
     * @return getal >= 0
     */
    int failed() {
        int count = 0
        this.each { Resultaat r -> if (r.isFailed()) {count++} }
        return count
    }

    /**
     * Het aantal testcases dat is fout gegaan.
     * @return getal >= 0
     */
    int errors() {
        int count = 0
        this.each { Resultaat r -> if (r.isError()) {count++} }
        return count
    }

    /**
     * Zijn er geen fouten en gefaalde testcases.
     * @return  {@code true}, als er geen fouten zijn
     */
    boolean isSuccess() {
        errors() + failed() == 0
    }

    /**
     * Geeft de lijst met resultaten.
     * @return lijst van resultaten
     */
    List<Properties> getResultaat() { statusList }

    @Override
    Iterator<Resultaat> iterator() {
        def result = this.statusList.collect { Properties it -> new Resultaat(it) }
        result.iterator()
    }
}

/**
 * Wrapper om een {@link Properties} object met het resultaat van een testcase.
 */
class Resultaat {
    @Delegate Properties r

    Resultaat(Properties properties) {
        this.r = properties
    }

    /**
     * Is het resultaat een fout.
     * @return {@code true}, als het fout is
     */
    boolean isError() {
	r[Debug_Log.naam] && !(r[Status.naam] == 'FAILED' || r[Status.naam] == 'QUARANTAINE')
    }

    /**
     * Is het resultaat een falen.
     * @return {@code true}, als het gefaald is
     */
    boolean isFailed() {
        r[Status.naam] == 'FAILED'
    }

    /**
     * Is het resultaat overgeslagen.
     * @return {@code true}, als het overgeslagen is
     */
    boolean isSkipped() {
        r[Status.naam] == 'QUARANTAINE'
    }
}
