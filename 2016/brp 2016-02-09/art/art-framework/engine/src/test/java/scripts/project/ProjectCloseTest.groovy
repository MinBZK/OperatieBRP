package scripts.project

import com.eviware.soapui.impl.wsdl.WsdlProject
import org.junit.Test
import scripts.AbstractScriptTester

/**
 * TODO: Add documentation
 */
class ProjectCloseTest extends AbstractScriptTester {

    @Test
    void clearsAllProperties() {
        def soapuiFile = new File(scriptPath, '../resources/ART-soapui-project.xml').absolutePath
        WsdlProject project = new WsdlProject(soapuiFile)

        binding.project = project
        shell.evaluate(new File(scriptPath, 'scripts/project/ARTEngine_save.groovy'))
    }
}
