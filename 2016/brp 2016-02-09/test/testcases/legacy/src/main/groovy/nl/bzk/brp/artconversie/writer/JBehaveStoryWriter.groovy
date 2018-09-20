package nl.bzk.brp.artconversie.writer

import nl.bzk.brp.artconversie.model.jbehave.Scenario
import nl.bzk.brp.artconversie.model.jbehave.Step
import nl.bzk.brp.artconversie.model.jbehave.Story

/**
 *
 */
class JBehaveStoryWriter {
    File outPath
    JBehaveStoryWriter(File file) {
        outPath = file

        if (!outPath.exists()) {
            outPath.mkdirs()
        }
    }

    boolean write(final Story story) {
        def out = new File(outPath, "${story.name}.story")

        out.newWriter().with {
            if (!story.meta.isEmpty()) {
                writeLine 'Meta:'
                story.meta.each {
                    writeLine "$it"
                }
                newLine()
            }

            story.scenarios.each { Scenario scenario ->
                writeLine "Scenario: $scenario.name"
                if (!scenario.meta.isEmpty()) {
                    writeLine 'Meta:'
                    scenario.meta.asMap().each { String k, v ->
                        writeLine("${k} ${v.join(', ')}")
                    }
                    newLine()
                }

                scenario.each { Step step ->
                    writeLine "${step.type} ${step.text}".trim()
                }
                newLine()
            }

            flush()
            close()
        }
    }
}
