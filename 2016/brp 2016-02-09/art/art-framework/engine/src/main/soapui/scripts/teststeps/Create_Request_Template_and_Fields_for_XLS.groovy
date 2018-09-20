package scripts.teststeps

import com.eviware.soapui.support.GroovyUtils
import groovy.xml.XmlUtil

/**
 * Deze routine maakt een request alle velden gevuld met een placeholder, verzendendId wordt ingevuld met de element naam en noValue wordt verwijderd.
 * Verder genereerd dit scriptje ook alle velden voor in de excel bestand.
 *
 * Hoe te gebruiken:
 *
 *  - Kopieer en plak een standaard request naar temp/input_for_request_template.xml
 *  - Pas xml aan waar nodig, bijvoorbeeld extra voornamen toevoegen
 *  - Draai dit scriptje
 *  - Kopieer het request_template.xml van temp naar de test script en kopieer de velden uit fields.txt naar de test script excel Data sheet
 *
 */
def groovyUtils = new GroovyUtils(context)

String PROJECT_DIR = groovyUtils.projectPath
String DIR = "${PROJECT_DIR}/temp"
String INPUT_FILE = "${DIR}/input_for_request_template.xml";

String REQUEST_TEMPLATE = "${DIR}/request_template.xml";
String FIELDS_XLS = "${DIR}/fields.txt";


File inputFile = new File(INPUT_FILE)
def requestNode = new XmlSlurper().parseText(inputFile.getText("UTF-8"));

// Lijst van beschikbare attributes
List replacedAttributes = new ArrayList();

// Maak template en lijst van velden
replaceValues(requestNode, "\\?", replacedAttributes);

// Schrijf template
new File(REQUEST_TEMPLATE).withWriter { out ->
    out.println XmlUtil.serialize(requestNode).replace(" ns:noValue=\"?\"", "").replace(" stuf:noValue=\"?\"", "");
}

// Schrijf velden
new File(FIELDS_XLS).withWriter { out ->
    replacedAttributes.each {
        out.println it
    }
}

return "Bestanden zijn weggeschreven naar: \n-${REQUEST_TEMPLATE}\n-${FIELDS_XLS}";

// -- Private methods -----------------------------
/**
 * Vervang waarde met de node naam.
 */
private def replaceValues(def node, String withText, def replacedAttributes) {
    def verzendendIdKeyList = [];
    def keyList = []; //counts the amount per kind of element

    node.depthFirst().each {
        // Unieke verzendend aanmaken
        String name = "${it.name()}"
        String id = "";
        if (name.length() > 29) {
            name = name.substring(0, 29);
        }
        if (it.@communicatieID.text() == "?") {
            id = "com_id.${name}";
            it.@communicatieID = "\${DataSource Values#" + id + getSequenceReplacedAttributes(id, keyList, replacedAttributes) + "}";
        }
        if (it.@referentieID.text() == "?") {
            id = "ref_id.${name}";
            it.@referentieID = "\${DataSource Values#" + id + getSequenceReplacedAttributes(id, keyList, replacedAttributes) + "}";
        }
        if (it.@technischeSleutel.text() == "?") {
            id = "tec_id.${name}";
            it.@technischeSleutel = "\${DataSource Values#" + id + getSequenceReplacedAttributes(id, keyList, replacedAttributes) + "}";
        }
        // groeps namen
        replacedAttributes.add("|${it.name()}|");

        // Unieke place holders aanmaken
        if (!(it.children().size() > 0)) {
            String id_suffix = it.parent().name()[0] + it.parent().parent().name()[0] + it.parent().parent().parent().name()[0];
            String key = "${it.name()}_${id_suffix}";

            def seq = keyList.count(key) + 1;//start vanaf 1
            String xmlId = "${key}${seq}";
            keyList.add(key);

            it.replaceBody("\${DataSource Values#${xmlId}}");
            replacedAttributes.add(xmlId);
        }
    }
}

private String getSequenceReplacedAttributes(String id, def keyList, def replacedAttributes) {
    def seq = keyList.count(id) + 1;//start vanaf 1
    keyList.add(id);
    replacedAttributes.add(id + seq);
    return "${seq}";
}
