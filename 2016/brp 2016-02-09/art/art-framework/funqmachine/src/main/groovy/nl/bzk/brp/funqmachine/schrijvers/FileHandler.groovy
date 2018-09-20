package nl.bzk.brp.funqmachine.schrijvers

import groovy.transform.CompileStatic

/**
 * Doet alle bestandsafhandeling voor de ARTs.
 */
@CompileStatic
class FileHandler {
    File baseDir
    File outputDir

    /**
     * Constructor.
     */
    FileHandler() {
        def dir = new File(getClass().getResource('/').toURI()) as String

        baseDir = new File(dir, '/../')
        outputDir = new File(baseDir, 'funqmachine')
    }

    /**
     * Zorgt ervoor dat directories waarvan de aanwezigheid wordt aangenomen
     * ook daadwerkelijk zijn gecreeerd.
     */
    public void prepareDirectories() {
        outputDir.mkdirs()
        new File(baseDir, 'surefire-reports').mkdirs()
    }

    /**
     * Geeft een file relatief t.o.v. outputdir.
     * Bijvoorbeeld, {@code report.xls} of {@code ./data/request/een-request.xml}.
     *
     * @param filename een bestandsnaam, evt met relatief pad
     * @return
     */
    File geefOutputFile(String filename) {
        new File("${outputDir.absolutePath}/${filename}")
    }

    /**
     * Schrijf de content naar een bestand. De file is relatief t.o.v. outputdir.
     * Bijvoorbeeld, {@code report.sql} of {@code ./data/request/een-request.xml}.
     *
     * @param filename
     * @param content
     */
    void schrijfFile(String filename, String content) {
        File file = new File("${outputDir.absolutePath}/${filename}")

        if (!file.parentFile.exists()) {
            file.parentFile.mkdirs()
        }
        schrijfFileContent(file, content)
    }

    /*
     * Schrijft naar een file.
     *
     * @param file de file om in te schrijven
     * @param content de content
     */
    private void schrijfFileContent(File file, String content) {
        file.write content ?: '', 'UTF-8'
    }

    /**
     * Probeert een ongebruikt/onbestaand bestand te vinden. Indien het bestand bestaat,
     * zal een volgende worden gemaakt, volgens naam-{tijdstempel}.extensie
     *
     * @param path de folder waarin gezocht moet worden
     * @param filename het bestand
     * @return een File
     */
    static File vindVolgendeBestand(String path, String filename) {
        String[] nameParts = filename.tokenize('.')
        File file = new File(path, filename)

        if (file.exists()) {
            def newfile = "${nameParts[0]}-${new Date().format('yyyyMMdd_HHmmss')}.${nameParts[-1]}"

            file = new File(path, newfile)
        }

        return file
    }

    /**
     * Maakt een diff van twee bestanden. Bij het bepalen van het verschil wordt rekening gehouden met de volgende
     * voorwaardes:
     *
     * 1. Leading whitespace wordt genegeerd bij het bepalen van het verschil op een regel
     * 2. Indien de originele regel (expected) de combinatie >*< of "*" bevat, wordt dit resultaat weggelaten.
     *    Het algoritme neemt hier aan dat het om een wildcard gaat.
     *
     * Indien er verschillen worden geconstateerd, wordt de diff opgeslagen.
     *
     * @param verwacht het verwachte bestand
     * @param werkelijk het actuele resultaat
     * @param filename de locatie van de diff-file, t.o.v. van outputDir
     */
    void schrijfDiff(final String verwacht, final String werkelijk, final String filename, boolean filterWildcards = false) {
        DiffHandler diffHandler = new DiffHandler()

        diffHandler.schrijfDiff(geefOutputFile(verwacht), geefOutputFile(werkelijk), geefOutputFile(filename), filterWildcards)
    }
}
