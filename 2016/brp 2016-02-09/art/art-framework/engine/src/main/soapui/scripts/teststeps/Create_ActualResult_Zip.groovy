package scripts.teststeps

import nl.bzk.brp.soapui.handlers.FileHandler

import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

/**
 * Maakt een Zip-bestand van de ActialResults folder.
 */

FileHandler fileHandler = FileHandler.fromContext(context)

ZipOutputStream zipOutput = new ZipOutputStream(new FileOutputStream(fileHandler.geefOutputFile('../actualresults.zip')))

File input = fileHandler.outputDir
int inputDirLength = input.absolutePath.length() + 1

log.info "Ga zippen: $input.absolutePath"

input.eachFileRecurse { file ->
    def relative = file.absolutePath.substring(inputDirLength).replace('\\', '/')

    if (file.isDirectory() && !relative.endsWith('/')) { relative += '/' }

    log.debug "Toevoegen [$relative] aan zip"

    ZipEntry entry = new ZipEntry(relative)
    entry.time = file.lastModified()
    zipOutput.putNextEntry(entry)
    if (file.isFile()) {
        zipOutput << new FileInputStream(file)
    }
    zipOutput.closeEntry()
}

zipOutput.close()
log.info "klaar"

// beetje opruimen
context.put('statusList', null)
