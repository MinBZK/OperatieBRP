package nl.bzk.brp.soapui.excel

import groovy.transform.CompileStatic

/**
 * Enumeratie voor de verschillende teststatussen.
 */
@CompileStatic
public enum TestStatus {

    Geslaagd('OK'),
    Gefaald('FAILED'),
    In_Quarantaine('QUARANTAINE'),
    Niet_Testbaar('NT'),
    Handmatig_Testen('HT'),
    Todo('ToDo'),
    Out_of_Scope('OOS'),
    Vervallen('VERVALLEN'),
    Niet_Schema_Valide('NOT_SCHEMA_COMPLIANT');

    String waarde;

    /**
     * Constructor.
     * @param w waarde zoals gebruikt
     */
    TestStatus(String w) {
        this.waarde = w
    }

    @Override
    String toString() { waarde }

    static List<String> geenXsdCheck() { [In_Quarantaine, Niet_Schema_Valide]*.waarde }

    static List<String> nooitUitvoeren() { [Niet_Testbaar, Handmatig_Testen, Out_of_Scope, Vervallen]*.waarde }
}
