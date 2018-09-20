package nl.bzk.brp.soapui.excel

import groovy.transform.CompileStatic

/**
 * Enumeratie van de kolommen in de resultaat excel-file.
 */
@CompileStatic
public enum OutputKolommen {
    TestGeval('Testgeval'),
    Volgnummer('VolgNr'),
    Beschrijving('Beschrijving'),
    Status('Status'),

    Openstaande_Issues('Openstaande Issues'),
    Opmerkingen('Opmerkingen'),
    Assertion('Assertion'),
    Debug_Log('debug_log');

    String naam

    OutputKolommen(String naam) {
        this.naam = naam
    }

    static List<String> kolomnamen() {
        values()*.naam
    }

    @Override
    String toString() {
        return naam
    }
}
