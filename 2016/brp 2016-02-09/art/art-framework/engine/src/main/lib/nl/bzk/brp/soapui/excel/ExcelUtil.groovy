package nl.bzk.brp.soapui.excel

import groovy.transform.CompileStatic

/**
 * Utility methods for working with Excel.
 */
@CompileStatic
class ExcelUtil {
    /**
     * Geeft van een indexnummer de juiste lettercode voor een excel kolom.
     *
     * @param index de index van een kolom
     * @return de lettercode behorende bij de index
     */
    static String indexToColumnLetter(final int index) {
        int div = index + 1
        String colLetter = ''
        int mod = 0

        while (div > 0) {
            mod = div % 26
            colLetter += ((char) (64 + mod))
            div = (int) ((div - mod) / 26)
        }

        return colLetter.reverse().toUpperCase()
    }

    /**
     * Geeft van een lettercode de juiste index van een excel kolom.
     *
     * @param letters de lettercode van een kolom
     * @return de index behorende bij de lettercode
     */
    static int columnLetterToIndex(final String letters) {
        int index = 0
        letters.toUpperCase().chars.eachWithIndex{ char entry, int i ->
            def alphabetIndex = ((short)entry - 64)

            def k = letters.length() - (1 + i)
            if (k == 0) {
                index += alphabetIndex - 1
            } else {
                index += (alphabetIndex * (26.power(k)))
            }
        }

        return index
    }
}
