package nl.bzk.brp.soapui.utils

import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException

import static nl.bzk.brp.soapui.utils.DateSyntaxTranslatorUtil.parseString

class DateSyntaxTranslatorUtilTest {

    def timestamp = '1394047676684'

    @Rule public ExpectedException thrown = ExpectedException.none()

    @Test
    void formatVandaag() {
        assert '2014-03-05' == parseString(timestamp, '[vandaag]')
        assert '[vandaag()]' == parseString(timestamp, '[vandaag()]')

        assert '2013-03-05' == parseString(timestamp, '[vandaag(-1,0,0)]')
        assert '2014-02-05' == parseString(timestamp, '[vandaag(0,-1,0)]')
        assert '2014-02-28' == parseString(timestamp, '[vandaag(0,0,-5)]')
        assert '2012-02-29' == parseString(timestamp, '[vandaag(-2,0,-5)]')

        assert '2016-02-29' == parseString(timestamp, '[vandaag(+2,0,-5)]')
    }

    @Test
    void formatVandaagSql() {
        assert '20140305' == parseString(timestamp, '[vandaagsql]')
        assert '[vandaagsql()]' == parseString(timestamp, '[vandaagsql()]')

        assert '20130305' == parseString(timestamp, '[vandaagsql(-1,0,0)]')
        assert '20140205' == parseString(timestamp, '[vandaagsql(0,-1,0)]')
        assert '20140228' == parseString(timestamp, '[vandaagsql(0,0,-5)]')
        assert '20120229' == parseString(timestamp, '[vandaagsql(-2,0,-5)]')

        assert '20160229' == parseString(timestamp, '[vandaagsql(+2,0,-5)]')
    }

    @Test
    void formatMoment() {
        assert '2014-03-05T20:27:56.684Z' == parseString(timestamp, '[moment]')

        assert '2015-03-05T20:27:56.684Z' == parseString(timestamp, '[moment(+1,0,0)]')
        assert '2015-03-05T20:27:56.684Z' == parseString(timestamp, '[moment(1,0,0)]')
        
        assert '2015-03-05T19:26:56.684Z' == parseString(timestamp, '[moment(1,0,0,-1,-1)]')
        assert '2015-03-05T21:28:56.684Z' == parseString(timestamp, '[moment(1,0,0,1,1)]')
    }

    @Test
    void formatMomentVolgen() {
        assert '2014-03-05T20:27:56.684' == parseString(timestamp, '[moment_volgen]')

        assert '2015-03-05T20:27:56.684' == parseString(timestamp, '[moment_volgen(+1,0,0)]')
        assert '2015-03-05T20:27:56.684' == parseString(timestamp, '[moment_volgen(1,0,0)]')
    }

    @Test
    void teWeinigParameters() {
        assert '[vandaag(1)]' == parseString(timestamp, '[vandaag(1)]')
    }

    /**
     * geen replacement.
     */
    @Test
    void verkeerdeNaam() {
        assert '[foo]' == parseString(timestamp, '[foo]')
    }
}
