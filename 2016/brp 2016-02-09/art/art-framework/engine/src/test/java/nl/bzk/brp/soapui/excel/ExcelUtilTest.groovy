package nl.bzk.brp.soapui.excel

import spock.lang.Specification

class ExcelUtilTest extends Specification {

    def "kan kolomletter vertalen naar index en vice versa"() {
        expect:
        ExcelUtil.indexToColumnLetter(idx) == letter
        ExcelUtil.columnLetterToIndex(letter) == idx

        where:
        letter | idx
           'A' | 0
           'B' | 1
          'AA' | 26
          'AB' | 27
          'FB' | 157
          'IV' | 255    // max in xls
         'XFD' | 16383  // max in xlsx
    }
}
