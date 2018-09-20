package scripts

import org.junit.After
import org.junit.Before

/**
 * Abstract class for testing Groovy scripts, that sets up an environment.
 * @see <a href="http://kousenit.wordpress.com/2011/02/24/testing-groovy-scripts/">Ken Kousen</a>
 */
abstract class AbstractScriptTester {
    // maven env
    String scriptPath

    // runtime environment
    GroovyShell shell
    Binding binding
    PrintStream orig
    ByteArrayOutputStream out

    @Before
    void setUp() {
        scriptPath = new File(getClass().getResource('/').toURI()).absolutePath + '/../../src/main/soapui/'

        orig = System.out
        out = new ByteArrayOutputStream()
        System.setOut(new PrintStream(out))
        binding = new Binding()
        shell = new GroovyShell(binding)
    }

    @After
    void tearDown() {
        System.setOut(orig)
    }
}
