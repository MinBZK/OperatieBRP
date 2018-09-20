package documentatie

import groovyjarjarantlr.collections.AST
import java.nio.file.Paths
import org.apache.commons.io.FileUtils
import org.apache.html.dom.HTMLDocumentImpl
import org.apache.xpath.XPathAPI
import org.codehaus.groovy.antlr.AntlrASTProcessor
import org.codehaus.groovy.antlr.SourceBuffer
import org.codehaus.groovy.antlr.UnicodeEscapingReader
import org.codehaus.groovy.antlr.parser.GroovyLexer
import org.codehaus.groovy.antlr.parser.GroovyRecognizer
import org.codehaus.groovy.antlr.treewalker.SourceCodeTraversal
import org.codehaus.groovy.groovydoc.GroovyMethodDoc
import org.codehaus.groovy.tools.groovydoc.SimpleGroovyClassDoc
import org.codehaus.groovy.tools.groovydoc.SimpleGroovyClassDocAssembler
import org.cyberneko.html.parsers.DOMFragmentParser
import org.xml.sax.InputSource
import spock.lang.Specification
import spock.lang.Unroll

/**
 * Unit test die uitvoer wegschrijft ten behoeve van de documentatie van de FunqMachine.
 */
final class StepsDocumenterTest extends Specification {

    private static final String NEW_LINE = ''
    @Unroll
    def "Maak asciidoc voor klasse #input"() {

        expect:
        maakAsciidoc(input as String)

        where:
        _ | input
        _ | 'HousekeepingSteps.groovy'
        _ | 'HousekeepingLegacySteps.groovy'
        _ | 'SynchroonBerichtenSteps.groovy'
        _ | 'AsynchroonBerichtenSteps.groovy'
        _ | 'DatabaseSteps.groovy'
    }

    private void maakAsciidoc(String klasse) {
        def doc = parseGroovyFile(klasse)

        ['Given', 'When', 'Then'].each { step ->
            def methods = doc.methods().findAll {
                it.public && it.annotations().any { ann -> ann.name().endsWith(step)}
            }

            schrijfMethods(step, methods, "bdd-steps-${step.toLowerCase()}.adoc")
        }
    }

    private void schrijfMethods(String type, List<GroovyMethodDoc> methods, String output) {
        def steps = []

        methods.each {
            steps << getOmschrijving(it)
            steps << getBddStep(it, type)
        }

        schrijfRegels(output, steps.flatten().collect { it?.trim() })
    }

    private def getBddStep(GroovyMethodDoc method, String type) {
        def result = []
        method.annotations().each { ann ->
            if (ann.name().endsWith('Alias') || ann.name().endsWith(type)) {
                result << "$type " + ann.description().split('\"')[-2].replaceAll('\\\\', '')
            }
        }

        ['[source,gherkin]\n----', result, '----']
    }

    private def getOmschrijving(GroovyMethodDoc method) {
        if (method.commentText() == '' ) {
            throw new AssertionError("De method '${method.name()}' heeft geen documentatie")
        }

        def fragment = new HTMLDocumentImpl().createDocumentFragment()
        new DOMFragmentParser().parse(new InputSource(new StringReader(method.commentText())), fragment)

        def attrs = XPathAPI.selectNodeList(fragment, '//DD')

        if (method.parameters().size() != attrs.length) {
            throw new AssertionError("Niet alle parameters van de method '${method.name()}' zijn gedocumenteerd")
        }

        def omschrijving = [NEW_LINE, fragment.firstChild?.textContent?.split('\n'), NEW_LINE, attrs.collect { "- ${it.textContent}" }, NEW_LINE]

        def deprecated = method.annotations().any { it.name() == 'Deprecated' }
        if (deprecated) {
            omschrijving <<
'''

====
IMPORTANT: Deze step bij voorkeur niet gebruiken
====

'''
        }

        omschrijving
    }

    private def parseGroovyFile(String s) {
        def root = Paths.get(getClass().getResource('/').toURI())
        def path = Paths.get(root.toString(), "../../src/main/groovy/nl/bzk/brp/funqmachine/jbehave/steps/$s").normalize()

        File file = path.toFile()

        return parseGroovyFile( file, s )
    }

    private SimpleGroovyClassDoc parseGroovyFile(final File f, final String name) {
        def reader = f.newReader()
        SourceBuffer sourceBuffer = new SourceBuffer()
        UnicodeEscapingReader unicodeReader = new UnicodeEscapingReader(reader, sourceBuffer)
        GroovyLexer lexer = new GroovyLexer(unicodeReader)
        unicodeReader.setLexer(lexer)
        GroovyRecognizer parser = GroovyRecognizer.make(lexer)
        parser.setSourceBuffer(sourceBuffer)
        parser.compilationUnit()

        AST ast = parser.getAST()

        def visitor = new SimpleGroovyClassDocAssembler("/nl/bzk/brp/funqmachine/jbehave/steps", f.name, sourceBuffer, [], new Properties(), true)
        AntlrASTProcessor traverser = new SourceCodeTraversal(visitor)
        traverser.process(ast)
        SimpleGroovyClassDoc doc = (SimpleGroovyClassDoc)visitor.getGroovyClassDocs().values().find { name.startsWith(it.name()) }

        doc
    }

    private void schrijfRegels(String bestand, List steps) {
        def folder = FileUtils.toFile(getClass().getResource('/'))
        FileUtils.writeLines(new File(folder, bestand), 'UTF-8', steps, true)
    }
}
