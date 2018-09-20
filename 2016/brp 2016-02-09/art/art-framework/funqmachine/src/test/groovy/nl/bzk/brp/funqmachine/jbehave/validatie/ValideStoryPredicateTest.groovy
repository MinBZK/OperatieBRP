package nl.bzk.brp.funqmachine.jbehave.validatie

import spock.lang.Specification

/**
 *
 */
class ValideStoryPredicateTest extends Specification {

    def "kan story files valideren"() {
        def predicate = new ValideStoryPredicate()

        expect:
        resultaat == predicate.apply("nl/bzk/brp/funqmachine/jbehave/validatie/${input}")

        where:
        input                    | resultaat
        'valide.story'           | true
        'te_lang.story'          | false
        'geen_narrative.story'   | false
        'legacy.story'           | true
        'legacy/some.story'      | true
    }

}
