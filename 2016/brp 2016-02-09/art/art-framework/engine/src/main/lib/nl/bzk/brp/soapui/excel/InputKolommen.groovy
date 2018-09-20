package nl.bzk.brp.soapui.excel

import groovy.transform.CompileStatic

/**
 * Enumeratie van de kolommen in de ART Excelsheet.
 */
@CompileStatic
enum InputKolommen {
    TestGeval('Testgeval'),
    Volgnummer('VolgNr'),
    Beschrijving('Beschrijving'),
    Status('Status'),
    Openstaande_Issues('Openstaande Issues'),
    Opmerkingen('Opmerkingen'),
    Prepare_Data('PrepareData'),
    Pre_Query('Pre Query'),
    DB_Query_Delay('DB Query delay'),
    Send_Request('SendRequest'),
    Soap_Response_Query('Soap Response Query'),
    DB_Query('DB Query'),
    Post_Query('Post Query'),
    DB_Response_Query('DB Response Query'),
    Alternatieve_Properties('Alternative test properties'),
    SOAP_Interface('Soap Interface'),
    SOAP_Operation('Soap Operation'),
    SOAP_Endpoint('Soap Endpoint'),
    Request_Template('Request Template'),
    Overschrijf_Variabelen('Overschrijf variabelen');

    String naam

    InputKolommen(String naam) {
        this.naam = naam
    }

    static List<String> kolomnamen() {
        values()*.naam
    }

    static List<String> nietVerplichteKolomnamen() {
        [Openstaande_Issues, Opmerkingen, DB_Query_Delay, DB_Response_Query, Alternatieve_Properties, SOAP_Interface, SOAP_Endpoint, SOAP_Operation, Request_Template, Overschrijf_Variabelen]*.naam
    }

    @Override
    String toString() {
        return naam
    }
}
