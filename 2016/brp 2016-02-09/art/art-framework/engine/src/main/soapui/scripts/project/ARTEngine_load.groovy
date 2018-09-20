package scripts.project

import com.eviware.soapui.SoapUI
import com.eviware.soapui.model.TestPropertyHolder
import com.eviware.soapui.support.ClasspathHacker

/**
 * Laadt de ART engine.
 */

// -- Variabelen -------------------------------------------------------------
def projectDir = context.expand('${projectDir}')
def dburl = SoapUI.globalProperties.getPropertyValue("dburl")

project.setPropertyValue('projectDir', projectDir)
expandClassPath(projectDir)

loadCommandlineParameters(projectDir)
setProjectPropertiesFromFile("${projectDir}/ENV/${project.getPropertyValue('ENV')}", 'env.properties')


// Overschrijf de DB url als deze meegegeven wordt via command line
if (dburl) {
    project.setPropertyValue('ds_url', dburl);
}

project.propertyList.each { prop ->
    log.info "------ ${prop.name} = ${prop.value}"
}

correctScriptPath(projectDir);

// -- Private helpers --------------------------------------------------------
/*
 * Laadt extra "libraries" en Groovy scripts die aan het het
 * classpath worden toegevoegd.
 */
private void expandClassPath(String projectDir) {
    ['lib', ''].each {
	def extraLibPathFile = new File(projectDir, it).absolutePath

	log.info "Toevoegen aan het classpath: ${extraLibPathFile}"
	ClasspathHacker.addFile(extraLibPathFile)
    }
}

/*
 * Laad nu de project settings, deze wordt alleen uitgevoerd als SOAPUI wordt gestart vanaf de command line .
 * bij de GUI, dient men deze met expliciet de hand te laden
 * Als geen parameter wordt meegeleverd, haal de ENV definitie uit de ENV/omgeving.properties; deze verwijst welk omgeving geladen moet worden.
 * In de env.properties staat de settings voor endpointURL(s), en datasource informatie.
 */
private void loadCommandlineParameters(String projectDir) {
    def projectEnv = SoapUI.globalProperties.getPropertyValue('projectEnv')
    def testScript = SoapUI.globalProperties.getPropertyValue('testScript')

    def props = null
    if (projectEnv && testScript) {
        // parameters meegekregen, waarschijnlijk gestart vanuit maven of cmdline.
        props = ['RUNTIME': 'CMD'] as Properties
        setProjectProperties(projectEnv, testScript)
    } else {
        // parameters niet gekregen, waarschijnlijk GUI, lees settings uit 'default.properties' bestand.
        props = ['RUNTIME': 'GUI'] as Properties
        File defaultPropertiesFile = new File(projectDir, 'default.properties')
        if (defaultPropertiesFile.exists()) {
            log.info "------ run from GUI, read settings from 'default.properties'"
            setProjectPropertiesFromFile("${projectDir}", 'default.properties')
        }
    }
    setProperties(props, project)
}

/*
 *
 */
private void setProjectProperties(String projectEnv, String testScript) {
    log.info "------ run from commandline, settings must be correct: projectEnv=${projectEnv}, testScript=${testScript}"

    def props = ['ENV':projectEnv, 'test_script_location': testScript] as Properties
    setProperties(props, project)

    if (!projectEnv)
        log.warn "[Project Load Script] projectEnv is niet opgegeven via command line, gebruikt de waarde opgeslagen in ./default.properties - ENV: ${project.getPropertyValue('ENV')}"

    if (!testScript)
        log.warn "[Project Load Script] testScript is niet opgegeven via command line, gebruikt de waarde opgeslagen in ./default.properties - test_script_location: ${project.getPropertyValue('test_script_location')}"
}

/*
 * corrigeer nu als de test_script_location een 'relatief' pad is.
 */
private void correctScriptPath(String projectDir) {
    File scriptPathFile = new File(projectDir, project.getPropertyValue('test_script_location'))

    if (!scriptPathFile.exists()) {
        scriptPathFile = new File(project.getPropertyValue('test_script_location'))
        if (!scriptPathFile.exists()) {
            def errMsg = "Kan het scriptpath ${scriptPathFile.absolutePath} niet vinden"
            log.error errMsg
            throw new IOException(errMsg)
        }
    }

    def props = ['test_script_location': scriptPathFile.getAbsolutePath()] as Properties
    setProperties(props, project)

    log.info "--- ScriptPath=[${scriptPathFile.absolutePath}]"
}

/*
 * Leest een '.properties' bestand en zet de waardes als property
 * op het project.
 */
private void setProjectPropertiesFromFile(String dir, String filename) {
    File file = new File(dir, filename)
    Properties properties = new Properties()
    file.withInputStream { stream -> properties.load(stream) }

    setProperties(properties, project)

    log.info("[Project Load Script] Goed geladen bestand: ${dir}/${filename}");
}

/*
 * Zet properties op target.
 */
private void setProperties(Properties props, TestPropertyHolder target) {
    props.each { key, val ->
        target.setPropertyValue((String)key, (String)val)
    }
}
