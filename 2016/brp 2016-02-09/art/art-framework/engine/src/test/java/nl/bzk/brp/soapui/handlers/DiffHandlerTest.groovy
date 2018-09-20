package nl.bzk.brp.soapui.handlers

import org.junit.Test

/**
 *
 */
class DiffHandlerTest {

    DiffHandler diffHandler = new DiffHandler()

    @Test
    void schrijfDiffZonderFilter() {
        File xml1 = geefInput '1-expected.xml'
        File xml2 = geefInput '1-actual.xml'

        File out = geefOutput 'diff.diff'

        diffHandler.schrijfDiff(xml1, xml2, out)

        assert out.readLines().collect({ it.trim() }).join(' ').startsWith('--- test-classes/xml/1-expected.xml +++ test-classes/xml/1-actual.xml @@ -2,9 +2,9 @@')
    }

    @Test
    void schrijfDiffFiltertWildcardMatch() {
        File xml1 = geefInput '1-expected.xml'
        File xml2 = geefInput '1-actual.xml'

        File out = geefOutput 'diff2.diff'

        diffHandler.schrijfDiff(xml1, xml2, out, true)

        assert !out.exists()
    }

    @Test
    void schrijfDiffFiltertWildcardMatchOpTweeRegels() {
        File xml1 = geefInput '1-expected-2.xml'
        File xml2 = geefInput '1-actual.xml'

        File out = geefOutput 'diff5.diff'

        diffHandler.schrijfDiff(xml1, xml2, out, true)

        assert !out.exists()
    }

    @Test
    void schrijfDiffFiltertVerschilNiet() {
        File xml1 = geefInput '1-actual.xml'
        File xml2 = geefInput '2-actual.xml'

        File out = geefOutput 'diff3.diff'

        diffHandler.schrijfDiff(xml1, xml2, out, true)

        assert out.readLines().collect({ it.trim() }).join(' ').startsWith('--- test-classes/xml/1-actual.xml +++ test-classes/xml/2-actual.xml @@ -2,7 +2,9 @@')
    }

    @Test
    void schrijfDiffFiltertOntbrekendeNodes() {
        File xml1 = geefInput '1-expected.xml'
        File xml2 = geefInput '2-actual.xml'

        File out = geefOutput 'diff4.diff'

        diffHandler.schrijfDiff(xml1, xml2, out, true)

        assert !out.exists()
    }

    @Test
    void diffMetWerkelijkeVoorbeelden() {
        File xml1 = geefInput 'BRLV0018-TC00-3-expected.xml'
        File xml2 = geefInput 'BRLV0018-TC00-3-actual.xml'

        File out = geefOutput 'BRLV0018-TC00-2.diff'

        diffHandler.schrijfDiff(xml1, xml2, out, true)

        assert out.exists()
    }

    private File geefInput(String name) {
        new File(getClass().getResource("/xml/$name").toURI())
    }

    private File geefOutput(String name) {
        File path = new File(getClass().getResource("/").toURI())
        new File(path, name)
    }
}
