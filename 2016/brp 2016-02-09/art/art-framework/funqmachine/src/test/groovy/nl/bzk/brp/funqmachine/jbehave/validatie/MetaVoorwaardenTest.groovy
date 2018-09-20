package nl.bzk.brp.funqmachine.jbehave.validatie

import spock.lang.Specification

class MetaVoorwaardenTest extends Specification {
    def metaRegels = MetaVoorwaarden.instance()

    def "er zijn valide statussen"() {
        expect:
        metaRegels.valideStatussen() == ['Onderhanden', 'Bug', 'Backlog', 'Review', 'Klaar', 'Uitgeschakeld']
    }

    def "er zijn x valide meta tags"() {
        expect:
        metaRegels.ondersteundeMetaTags().size() == 11
    }

    def "Meta 'component' valideert correct"() {
        def regel = metaRegels.getRegel('component')

        expect:
        (waarde ==~ regel.regex) == resultaat

        where:
        resultaat | waarde
        true      | 'Component'
        false     | ',,'
        true      | 'Component1, component2'
        false     | ',component1'
    }
}
