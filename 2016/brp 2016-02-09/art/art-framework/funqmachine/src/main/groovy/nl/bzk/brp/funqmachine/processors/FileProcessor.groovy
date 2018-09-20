package nl.bzk.brp.funqmachine.processors

import nl.bzk.brp.funqmachine.jbehave.converters.FileConverter
import nl.bzk.brp.funqmachine.jbehave.steps.ResourceResolver
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 *
 */
class FileProcessor {
    private final Logger logger = LoggerFactory.getLogger('FileProcessor')

    /**
     *
     * @param fileNaam
     * @return
     */
    File geefFile(String fileNaam) {
        if (fileNaam.startsWith('/')) {
            return new FileConverter().convertFile(fileNaam)
        } else {
            return new ResourceResolver(contextView).resolve(fileNaam)
        }
    }

    /**
     * 
     * @param fileNaam
     * @return
     */
    String geefFileOfRedirectedFile(String fileNaam) {
        def lines = geefFile(fileNaam).readLines()

        if (lines[0].startsWith('[data/') || lines[0].startsWith('[/data/')) {
            def newFile = lines[0][1..-2].split('/').last()

            def path = fileNaam.split('/')
            path[-1] = newFile

            logger.info 'Redirect expected [{}] naar: {}', fileNaam, path.join('/')

            return path.join('/')
        } else {
            return fileNaam
        }
    }
}
