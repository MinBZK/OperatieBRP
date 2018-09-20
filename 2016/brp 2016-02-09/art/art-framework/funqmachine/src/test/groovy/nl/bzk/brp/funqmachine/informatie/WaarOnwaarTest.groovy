package nl.bzk.brp.funqmachine.informatie

import static groovy.test.GroovyAssert.shouldFail

import spock.lang.Specification

class WaarOnwaarTest extends Specification {

    def 'kan waar-achtigen naar true vertalen'() {
        expect:
            assert WaarOnwaar.isWaar(input)
            assert !WaarOnwaar.isOnwaar(input)

        where:
        _ | input
        _ | 'ja'
        _ | 'Ja'
        _ | 'JA'
        _ | 'jA'
        _ | 'wel'
        _ | 'j'
        _ | 'y'
        _ | 'waar'
        _ | 'true'
        _ | 'ok'
        _ | ' ok '
        _ | '  oK  '
    }

    def 'kan onwaar-achtigen naar false vertalen'() {
        expect:
        assert WaarOnwaar.isOnwaar(input)
        assert !WaarOnwaar.isWaar(input)

        where:
        _ | input
        _ | 'nee'
        _ | 'nEe'
        _ | ' NeE  '
        _ | 'niet'
        _ | 'n'
        _ | 'onwaar'
        _ | 'false'
        _ | 'nok'
        _ | 'nOK'
    }

    def 'overige waardes geven NULL'() {
        expect:
        shouldFail {WaarOnwaar.isWaar(input)}
        shouldFail {WaarOnwaar.isOnwaar(input)}

        where:
        _ | input
        _ | 'foo'
        _ | 'jazeker'
        _ | 'ja zeker'
        _ | 'not ok'
    }
}
