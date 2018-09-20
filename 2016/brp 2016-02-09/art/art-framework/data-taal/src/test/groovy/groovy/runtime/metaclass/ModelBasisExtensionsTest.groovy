package groovy.runtime.metaclass
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BurgerservicenummerAttribuut
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut
import nl.bzk.brp.model.algemeen.attribuuttype.kern.HuisnummerAttribuut
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut
import nl.bzk.brp.model.operationeel.kern.ActieModel
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel
import org.codehaus.groovy.runtime.typehandling.GroovyCastException
import org.junit.Test
/**
 *
 */
class ModelBasisExtensionsTest {
    ActieModel model = new ActieModel(new SoortActieAttribuut(SoortActie.DUMMY), (AdministratieveHandelingModel) null, (PartijAttribuut) null,
        new DatumEvtDeelsOnbekendAttribuut(20140101), new DatumEvtDeelsOnbekendAttribuut(20141231), DatumTijdAttribuut.datumTijd(2014, 1, 1),
        new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.vandaag()))

    @Test
    void kanAanvangOphalen() {
        assert model.aanvangGeldigheid == 20140101
    }

    @Test
    void kanEindeOphalen() {
        assert model.eindeGeldigheid == 20141231
    }

    @Test
    void kanAttribuutConverteren() {
        def bsn = new BurgerservicenummerAttribuut(123)

        assert bsn as Integer == 123
    }

    @Test(expected = GroovyCastException)
    void invalideConversieGeeftExceptie() {
        def huisnummer = new HuisnummerAttribuut(32)

        huisnummer as String
    }
}
