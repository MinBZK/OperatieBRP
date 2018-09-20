package nl.bzk.brp.artconversie.conversie

import org.junit.Test

/**
 *
 */
class TemplateConverterTest {
    def converter = new TemplateConverter()

    @Test
    void testConverteer() {
        def file = new File(getClass().getResource('in.txt').toURI())

        converter.convert(file)

        def lines = file.readLines()
        assert lines[0] == '${data}, ${_data_},'
        assert lines[1] == '<xml attr="${data}" prop="${_data_}"/>'
        assert lines[2] == '<xml attr="id.${data}" prop="id.${_data_}"/>'
        assert lines[3] == '<xml attr="id.${data$waarde}" prop="id.${_data$waarde_}"/>'
    }

    @Test
    void converteerObjectSleutel() {
        def file = new File(getClass().getResource('objectsleutel.txt').toURI())

        converter.convert(file)

        def lines = file.readLines()
        assert lines[0] == '<brp:persoon brp:objectSleutel="[@objectsleutel var=_objectid$burgerservicenummer_ipo_B01_ partij=TODO /]" brp:communicatieID="${comid$persoon_B01}" brp:objecttype="Persoon" ></brp:persoon>'
    }
}
