package nl.bzk.brp.soapui.handlers

/**
 * Exceptie die gegooid wordt als er een fout zit in een overschrijf-variabele.
 */
class OverschrijfVariabeleFout extends Exception {
    /**
     * Default constructor.
     * @param msg Het bericht.
     */
    OverschrijfVariabeleFout(String bericht) {
        super(bericht)
    }
}
