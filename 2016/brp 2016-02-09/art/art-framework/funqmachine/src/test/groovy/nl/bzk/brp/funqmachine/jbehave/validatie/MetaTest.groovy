package nl.bzk.brp.funqmachine.jbehave.validatie

import spock.lang.Specification

/**
 *
 */
class MetaTest extends Specification {

    def 'Kan meta met 1 waarde maken'() {
        def meta = new Meta('@meta waarde')

        expect:
        meta != null
        meta.values == ['waarde']
    }

    def 'Kan meta met meerdere waardes maken'() {
        def meta = new Meta('@meta waarde, foo, bar')

        expect:
        meta != null
        meta.values.size() == 3
        meta.values == ['waarde', 'foo', 'bar']
    }

    def 'Kan meta met zonder waarde maken'() {
        def meta = new Meta('@meta')
        expect:
        meta != null
        meta.values == []
    }

}
