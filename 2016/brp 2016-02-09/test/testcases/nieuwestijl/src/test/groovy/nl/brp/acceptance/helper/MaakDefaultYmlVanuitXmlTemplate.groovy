package nl.brp.acceptance.helper

import nl.brp.acceptance.internal.YamlVoorbeeldBuilder
import org.junit.Ignore
import org.junit.Test

class MaakDefaultYmlVanuitXmlTemplate {

    @Test
    @Ignore
    void genereerYmlEnXmlBestanden() {
        new YamlVoorbeeldBuilder('template_input.xml')
            .metXmlOutput('template_output.xml')
            .metYamlOutput('template_output.yml')
            .schrijf()
    }
}
