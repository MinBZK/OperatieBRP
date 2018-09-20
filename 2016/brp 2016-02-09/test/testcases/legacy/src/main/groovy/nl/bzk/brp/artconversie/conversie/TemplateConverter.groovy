package nl.bzk.brp.artconversie.conversie

/**
 * Converteer bestaande SoapUI templates naar Freemarker compatible formaat:
 * gebruik freemarker functies, vervang placeholders naar compatible opmaak.
 */
class TemplateConverter {

    private def placeholder_regex = /\$\{[\s\w]+#([|\-\w\s\.]+)\}/
    private def functies_regex = /\[(\w+(\(.*\))?)\]/
    private def objectsleutel_regex = /objectSleutel="\$\{(.+?)\}"/
    private def persoon_id_db_regex = /persoon_id_voor_bsn\(\$\{(.+?)\}\)/

    TemplateConverter() {}

    def conversieClosure = { String text ->
        text = text.readLines().findAll { !(it.contains('soapenv') || it.contains('<?xml') || it.isAllWhitespace()) }.join('\n')

        def lines = text.readLines()
        def firstLine = lines.isEmpty() ? '' : lines.first()
        if (!firstLine.contains('xmlns:brp=') && firstLine.trim().startsWith('<brp:')) {
            text = text.replaceFirst('>', ' xmlns:brp="http://www.bzk.nl/brp/brp0200">')
        }

        text = text.replaceAll(placeholder_regex) { all, String key ->
            "\${${KeyNamerStrategy.rename(key)}}"
        }

        text = text.replaceAll(functies_regex) { def match ->
            String functie = match[1]
            return functie.endsWith(')') ? "\${${functie}}" : "\${${functie}()}"
        }

        text = text.replaceAll(objectsleutel_regex) { all, String key ->
            "objectSleutel=\"[@objectsleutel var=${key} partij=TODO /]\""
        }

        text = text.replaceAll(persoon_id_db_regex) { all, String key ->
            "[@persoon_id_voor bsn=${key} /]"
        }

        return text
    }

    void convert(File input) {
        processFileInplace(input, conversieClosure)
    }

    private def processFileInplace(File file, Closure processText) {
        def text = file.text
        file.write(processText(text))
    }
}
