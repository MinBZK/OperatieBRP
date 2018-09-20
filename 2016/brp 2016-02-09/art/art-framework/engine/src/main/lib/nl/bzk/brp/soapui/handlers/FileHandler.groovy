package nl.bzk.brp.soapui.handlers

import groovy.transform.CompileStatic
import nl.bzk.brp.soapui.ARTVariabelen
import nl.bzk.brp.soapui.excel.DataSheets
import nl.bzk.brp.soapui.excel.InputExcel
import nl.bzk.brp.soapui.utils.DateSyntaxTranslatorUtil
import nl.bzk.brp.soapui.utils.FileImportUtil
import org.apache.log4j.Logger

/**
 * Doet alle bestandsafhandeling voor de ARTs.
 */
@CompileStatic
class FileHandler {
    static final Logger log = Logger.getLogger(FileHandler.class)

    File baseDir
    File outputDir
    File projectDir

    def context

    /**
     * Factory method.
     * @param context
     * @return
     */
    static FileHandler fromContext(def context) {
        new FileHandler(context)
    }

    void destroy() {
        baseDir = null
        outputDir = null
        projectDir = null
        context = null
    }

    /**
     * Protected constructor, gebruik via {@link #fromContext(java.lang.Object)}
     * @param context
     */
    protected FileHandler(def context) {
        this.context = context
        def dir = ARTVariabelen.getScriptLocation(context).replace('\\', '/')
        baseDir = new File(dir)

        outputDir = new File(dir, ARTVariabelen.getActualDir(context))
        if (!outputDir.exists()) {
            outputDir.mkdirs()
        }

        projectDir = new File(ARTVariabelen.getProjectDir(context))
    }

    /**
     * Geeft een file relatief t.o.v. basedir.
     *
     * @param filename een bestandsnaam, evt met relatief pad
     * @return
     */
    File geefInputFile(String filename) {
        def file = new File("${baseDir.absolutePath}/${filename}")

        if (!file.exists()) {
            throw new FileNotFoundException("Het opgegeven 'input' bestand [$filename] kan niet worden gevonden op ($file.absolutePath)")
        }

        return file
    }

    /**
     * Geeft een abstractie van de ART-excel bestand.
     *
     * @param checkDataSheets if {@code true} check of de opgegeven datasheets aanwezig zijn in het Excel bestand
     * @return instantie van de ART excel
     */
    InputExcel geefArtExcel(boolean checkDataSheets = false) {
        String excelFile = ARTVariabelen.getInputFile(context)
        String artSheet = ARTVariabelen.getInputSheetNaam(context)
        List<String> dataSheets = ARTVariabelen.getInputDataSheetNamen(context)

        try {
            File f = new File(baseDir, excelFile)
            return new InputExcel(f, artSheet, (checkDataSheets ? dataSheets : new ArrayList<String>(0)))
        } catch (Exception e) {
            log.error "Kan de input excel [${excelFile}] niet laden uit [${baseDir.absolutePath}].\nFout: ${e.message}"
            throw e
        }
    }

    /**
     * Geeft een abstractie van de ART-Datasheets.
     *
     * @return instantie van DataSheets
     */
    DataSheets geefDataSheets() {
        List<String> dataSheets = ARTVariabelen.getInputDataSheetNamen(context)
        String excelFile = ARTVariabelen.getInputFile(context)

        String altExcelPath = ARTVariabelen.getAlternativeScriptPath(context)
        String altExcelFileName = ARTVariabelen.getAlternativeInputFile(context)

        boolean cache = (ARTVariabelen.getRuntime(context) == 'CMD')

        File f = null
        try {
            if (altExcelFileName) {
                f = new File(altExcelPath, altExcelFileName)
            } else {
                f = new File(baseDir, excelFile)
            }
            log.info "Excel file gekozen: ${f.absolutePath} : $f.name"
            return new DataSheets(f, dataSheets, cache)
        } catch (Exception e) {
            log.error "Kan de datasheets excel [${excelFile}] niet laden uit [${f?.absolutePath}].\nFout: ${e.message}"
            throw e
        }
    }

    /**
     * Geeft een bestand uit de afhankelijkheden.
     *
     * @param filename het pad naar het bestand, relatief tov de afhankelijkheden folder
     * @return een File
     */
    File geefAfhankelijkheid(String filename) {
        def path = "${baseDir.absolutePath}/../afhankelijkheden/${filename}"
        def file = new File(path)

        if (!file.exists()) {
            throw new FileNotFoundException("Het opgegeven 'afhankelijkheden' bestand [$filename] kan niet worden gevonden op ($file.absolutePath)")
        }

        return file
    }

    /**
     * Geeft een bestand uit de expected results.
     * Als er een alt_test_script_path is opgegeven, wordt deze in acht genomen.
     *
     * @param filename het pad naar het bestand, relatief tov de expected-results folder
     * @return een File
     */
    File geefVerwachtBestand(String filename) {
        def altPath = ARTVariabelen.getAlternativeScriptPath(context)
        File file = new File("${baseDir.absolutePath}/ExpectedResults/${filename}")

        if (!file.exists() && altPath) {
            File path = new File(altPath)
            if (path.exists()) {
               file = new File("${path.absolutePath}/ExpectedResults/${filename}")
            }
        }

        if (!file.exists()) {
            throw new FileNotFoundException("Het opgegeven 'expected' bestand [$filename] kan niet worden gevonden op ($file.absolutePath)")
        }

        return file
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
     * Geeft een file uit de Project dir, de folder van het SoapUI project file.
     *
     * @param filename het pad naar het bestand, relatief tov de projectDir folder
     * @return een File
     */
    File geefProjectFile(String filename) {
        new File("${projectDir.absolutePath}/${filename}")
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

        if (!file.parentFile.exists()) { file.parentFile.mkdirs() }
        schrijfFile(file, content)
    }

    /**
     * Schrijft naar een file.
     *
     * @param file de file om in te schrijven
     * @param content de content
     */
    void schrijfFile(File file, String content) {
        log.info "Schrijf file: ${file.absoluteFile}"
        file.write content ?: '', 'UTF-8'
    }

    /**
     * Leest een bestand en probeert de import placeholders in te vullen en daarna
     * de datum placeholder.
     *
     * @param file
     * @param timestamp
     * @return
     */
    String processExpectedFile(File file, Long timestamp) {
        if (!file.exists()) {
            throw new FileNotFoundException("Het opgegeven expected bestand kan niet worden gevonden op ($file.absolutePath)")
        }

        def txt = file.getText('UTF-8')
	    txt = FileImportUtil.importFiles(this, txt)
        txt = DateSyntaxTranslatorUtil.formatTijd(timestamp, txt)

        return txt
    }

    /**
     * Maakt de output directories in de meegegeven directory.
     */
    void createOutputDirectories() {
        ['/data/request',
            '/data/response',
            '/data/expected_response',
            '/soap/request',
            '/soap/response',
            '/soap/expected_response',
            '/report',
            '../../../surefire-reports'
        ].each { String dirname ->
            def dir = new File(outputDir, dirname)
            if (!dir.exists()) {
                dir.mkdirs()
            }
        }
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
