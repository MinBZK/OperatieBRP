package scripts

import com.eviware.soapui.impl.wsdl.WsdlInterface
import com.eviware.soapui.impl.wsdl.WsdlProject
import com.eviware.soapui.model.iface.Interface

/**
 * GMaven script voor het runnen vanuit maven.
 *
 * Dit script neemt het SoapUI-project.xml (uit src/main/resources/) en werkt alle
 * <definitioncache/> elementen bij. Dit heeft hetzelfde effect als het openen van SoapUI
 * en handmatig de definities bijwerken in de User Interface.
 * Het bijwerken gaat tegen de geconfigureerde URL bij de definition, meestal is dit http://localhost:8080/ ...
 */
WsdlProject project = new WsdlProject("${soapui_project}")

project.getInterfaces().each { name, Interface iface ->
    if (iface instanceof WsdlInterface) {
        wsdlInterface = (WsdlInterface) iface

        String urlString = wsdlInterface.getDefinition()

        try {
            println "UPDATING: $urlString"
            wsdlInterface.updateDefinition(urlString, false)
        } catch (Exception e) {
            println "Kan interface definition niet bijwerken: $name , $e.message"
        }
    }
}

project.save()
