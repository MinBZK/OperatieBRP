package nl.bzk.brp.artconversie.model.art

import static nl.bzk.brp.artconversie.model.art.Kolommen.*

/**
 *
 */
class ExcelRegel implements Comparable<ExcelRegel> {

    @Delegate Map<Kolommen, String> data = [:]
    ArtSheet parent
    int regel

    ExcelRegel(ArtSheet sheet) {
        this.parent = sheet
    }

    boolean isVerzenden() {
        return data[Send_Request] == '1'
    }

    String getRequestTemplate() {
        return this[Request_Template] ?: parent.defaultRequestTemplate
    }

    @Override
    int compareTo(ExcelRegel other) {
        return this[Volgnummer] as Integer <=> other[Volgnummer] as Integer
    }

    boolean alternatiefSoap() {
        return (this[SOAP_Endpoint] && this[SOAP_Interface] && this[SOAP_Operation] && this[Request_Template]) || this[Alternatieve_Properties]
    }
}
