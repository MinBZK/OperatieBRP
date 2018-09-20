package documentatie

import java.lang.annotation.Annotation
import java.lang.reflect.Method
import org.apache.commons.io.FileUtils
import org.jbehave.core.annotations.Alias
import org.jbehave.core.annotations.Given
import org.jbehave.core.annotations.Pending
import org.jbehave.core.annotations.Then
import org.jbehave.core.annotations.When
import org.junit.Test
import org.reflections.Reflections
import org.reflections.scanners.MethodAnnotationsScanner

/**
 * Unit test die uitvoer wegschrijft ten behoeve van de documentatie van de FunqMachine.
 */
final class AppendixDocumenterTest {
    Reflections reflections = new Reflections('nl.bzk.brp.funqmachine.jbehave', new MethodAnnotationsScanner())

    def vervallen = {Annotation it -> it.annotationType() == Pending || it.annotationType() == Deprecated}

    @Test
    void schrijfGivenSteps() {
        def steps = []

        reflections.getMethodsAnnotatedWith(Given).sort { it.declaringClass } .each {Method m ->
            def isOud = m.declaredAnnotations.any vervallen
            m.declaredAnnotations.findAll {a -> a instanceof Given || a instanceof Alias}.each {Annotation a ->
                steps << "Given ${m.getAnnotation(a.annotationType()).value()} ${isOud ? ' <1>' : ''}\n"
            }
        }

        schrijfRegels('bdd-steps-appendix-given.adoc', steps)
    }

    @Test
    void schrijfWhenSteps() {
        def steps = []
        reflections.getMethodsAnnotatedWith(When).sort { it.declaringClass } .each {Method m ->
            def isOud = m.declaredAnnotations.any vervallen
            m.declaredAnnotations.findAll {a -> a instanceof When || a instanceof Alias}.each {Annotation a ->
                steps << "When ${m.getAnnotation(a.annotationType()).value()} ${isOud ? ' <1>' : ''}\n"
            }
        }

        schrijfRegels('bdd-steps-appendix-when.adoc', steps)
    }

    @Test
    void schrijfThenSteps() {
        def steps = []
        reflections.getMethodsAnnotatedWith(Then).sort { it.declaringClass } .each {Method m ->
            def isOud = m.declaredAnnotations.any vervallen
            m.declaredAnnotations.findAll {a -> a instanceof Then || a instanceof Alias}.each {Annotation a ->
                steps << "Then ${m.getAnnotation(a.annotationType()).value()} ${isOud ? ' <1>' : ''}\n"
            }
        }

        schrijfRegels('bdd-steps-appendix-then.adoc', steps)
    }

    private void schrijfRegels(String bestand, List steps) {
        def folder = FileUtils.toFile(getClass().getResource('/'))
        FileUtils.writeLines(new File(folder, bestand), steps)
    }
}
