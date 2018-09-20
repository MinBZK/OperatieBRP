package nl.bzk.brp.funqmachine.schrijvers

import java.nio.file.Path
import java.nio.file.Paths
import nl.bzk.brp.funqmachine.jbehave.context.ScenarioResult
import nl.bzk.brp.funqmachine.jbehave.context.StepResult
import nl.bzk.brp.funqmachine.processors.xml.XmlUtils
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor
import org.apache.commons.io.monitor.FileAlterationMonitor
import org.apache.commons.io.monitor.FileAlterationObserver
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.DisposableBean
import org.springframework.stereotype.Component

/**
 *
 */
@Component
class FileWriter implements DisposableBean {
    private FileHandler handler
    private FileAlterationMonitor monitor

    FileWriter() {
        handler = new FileHandler()

        monitor = maakMonitor(handler)
        monitor.start()

        handler.prepareDirectories()
    }

    /**
     *
     * @param result
     * @param withDiff
     * @return
     */
    boolean write(ScenarioResult result, boolean withDiff = false) {

        result.eachWithIndex { r, idx ->
            if (r.response) {
                handler.schrijfFile("${result.path}/${r.soort}-${idx}-response.xml", XmlUtils.prettify(r.response))
            }

            if (r.expected) {
                handler.schrijfFile("${result.path}/${r.soort}-${idx}-expected.xml", XmlUtils.prettify(r.expected))

                if (withDiff) {
                    handler.schrijfDiff("${result.path}/${r.soort}-${idx}-expected.xml", "${result.path}/${r.soort}-${idx}-response.xml", "${result.path}/${r.soort}-${idx}-verschil.diff")
                }
            }

            if (r.request) {
                if (r.soort == StepResult.Soort.DATA) {
                    handler.schrijfFile("${result.path}/${r.soort}-${idx}-request.sql", r.request)
                } else {
                    handler.schrijfFile("${result.path}/${r.soort}-${idx}-request.xml", XmlUtils.prettify(r.request))
                }
            }
        }
    }

    /*
     * Maakt een fileMonitor.
     * @param handler input voor de monitor
     * @return een instantie van FileAlterationMonitor
     */
    private FileAlterationMonitor maakMonitor(FileHandler handler) {
        def observer = new FileAlterationObserver(handler.outputDir)
        observer.addListener(new FileCreationLogger(handler.baseDir))

        new FileAlterationMonitor(10, observer)
    }

    @Override
    void destroy() throws Exception {
        monitor?.stop()
    }

    /**
     * ListenerAdapter die logging toevoegd bij creatie en aanpassing van bestanden / directories.
     */
    private class FileCreationLogger extends FileAlterationListenerAdaptor {
        private final Logger logger = LoggerFactory.getLogger(FileCreationLogger)

        private final Path basePath

        /**
         * Constructor.
         * @param base basis van het schrijven van bestanden
         */
        FileCreationLogger(final File base) {
            basePath = Paths.get(base.canonicalPath)
        }

        /**
         * Maakt een relatief pad ten opzichte van {@link #basePath}.
         */
        def relative = { File f ->
            def path = Paths.get(f.canonicalPath)
            basePath.relativize(path).toString()
        }

        @Override
        void onDirectoryCreate(final File directory) {
            logger.debug('Created directory: {}', relative(directory))
        }

        @Override
        void onDirectoryDelete(final File directory) {
            logger.warn('DELETED directory: {}', directory)
        }

        @Override
        void onFileCreate(final File file) {
            logger.debug('Created file: {}', relative(file))
        }

        @Override
        void onFileChange(final File file) {
            logger.debug('Changed file: {}', relative(file))
        }
    }
}
