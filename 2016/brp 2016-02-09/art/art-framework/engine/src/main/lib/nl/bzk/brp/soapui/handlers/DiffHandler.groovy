package nl.bzk.brp.soapui.handlers

import difflib.Delta
import difflib.DiffUtils
import difflib.Patch
import difflib.myers.Equalizer
import groovy.transform.CompileStatic

/**
 * Handler, die verantwoordelijk is voor het maken van een Unified Diff, van twee bestanden.
 */
@CompileStatic
class DiffHandler {

    Equalizer equalizer = { String orig, String rev -> orig.replaceAll(/^\s+/, '').equals(rev.replaceAll(/^\s+/, ''))} as Equalizer<String>

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
    void schrijfDiff(final File verwacht, final File werkelijk, final File output, boolean filterWildcards = false) {
        List<String> aFile = (verwacht?.readLines() ?: new ArrayList<String>())
        List<String> bFile = (werkelijk?.readLines() ?: new ArrayList<String>())

        Patch<String> patch = DiffUtils.diff(aFile, bFile, equalizer)

        if (filterWildcards) {
            def filteredDeltas = patch.deltas.findAll() { Delta d ->
                d.type != Delta.TYPE.CHANGE || !(d.original.lines.every { it ==~ /.*[>"]\*[<"].*/ })
            }
            patch = new Patch<String>()
            filteredDeltas.each { Delta d -> patch.addDelta(d)}
        }

        if (!patch.deltas.isEmpty()) {
            String verw = lastPart(verwacht.absolutePath)
            String werk = lastPart(werkelijk.absolutePath)
            List<String> unifiedDiff = DiffUtils.generateUnifiedDiff(verw, werk, aFile, patch, 3)

            schrijfFile(output, unifiedDiff.join('\n'))
        }
    }

    /*
     * Geeft het laaste deel van een bestandspad, namelijk de laatste 2 folders en de bestandsnaam.
     * Indien het een windows pad betreft, worden de "\" karakters vervangen door "/".
     *
     * @param str het bestandspad
     * @return het laatste deel van het pad
     */
    private String lastPart(String str) {
        str.replace('\\', '/').split('/')[-3..-1].join('/')
    }

    /*
     * Schrijft een bestand.
     * @param file het bestand waarin wordt geschreven
     * @param content de te schrijven inhoud
     */
    private void schrijfFile(File file, String content) {
        file.write content ?: '', 'UTF-8'
    }
}
