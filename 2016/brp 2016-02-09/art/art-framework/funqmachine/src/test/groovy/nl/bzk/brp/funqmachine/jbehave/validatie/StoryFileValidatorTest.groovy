package nl.bzk.brp.funqmachine.jbehave.validatie

import spock.lang.Specification

class StoryFileValidatorTest extends Specification {

    def "kan story files valideren"() {
        def validator = new StoryFileValidator()

        expect:
        File f = new File(getClass().getResource(bestand).toURI())
        resultaat == validator.valideer(f).orNull()

        where:
        bestand                  | resultaat
        'valide.story'           | true
        'legacy.story'           | null
        'te_lang.story'          | false
        'geen_narrative.story'   | false
        'onbekende_status.story' | false
        'onbekende_meta.story'   | false
    }
}
