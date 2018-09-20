package scripts

import com.eviware.soapui.impl.wsdl.WsdlInterface
import com.eviware.soapui.impl.wsdl.WsdlProject
import com.eviware.soapui.impl.wsdl.support.wsdl.WsdlContext
import com.eviware.soapui.model.iface.Interface

/**
 * GMaven script voor het runnen vanuit maven.
 *
 * Dit script neemt het SoapUI-project.xml (uit src/main/resources/) en verwijdert alle
 * <definitioncache/> elementen zodat het een minimale xml wordt.
 */
WsdlProject project = new WsdlProject("${soapui_project}")

project.getInterfaces().each { name, Interface iface ->
    if (iface instanceof WsdlInterface) {
        wsdlInterface = (WsdlInterface) iface

        String urlString = wsdlInterface.getDefinition()

        try {
            println "CLEARING: $urlString"
            WsdlContext.uncache(urlString)
            wsdlInterface.wsdlContext.release()
            wsdlInterface.wsdlContext.definitionCache.clear()
        } catch (Exception e) {
            println "Kan interface definition niet legen: $name , $e.message"
        }
    }
}

project.save()
