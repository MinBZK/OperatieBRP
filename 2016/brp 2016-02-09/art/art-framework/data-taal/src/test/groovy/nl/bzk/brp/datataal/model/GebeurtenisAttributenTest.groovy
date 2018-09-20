package nl.bzk.brp.datataal.model
import static nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling.G_B_A_INITIELE_VULLING

import nl.bzk.brp.datataal.dataaccess.SpringBeanProvider
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.springframework.context.ApplicationContext

class GebeurtenisAttributenTest {

    @Before
    public void voordat() {
        SpringBeanProvider.setContext(Mockito.mock(ApplicationContext.class))
    }

    @Test
    void defaultConstructorGeeftRandomWaardes() {
        def attr = new GebeurtenisAttributen()
        def datum = DatumAttribuut.vandaag()
        datum.voegDagToe(-10)

        assert attr.toelichting.length() == 15
        assert attr.soortHandeling == G_B_A_INITIELE_VULLING
        assert attr.aanvang == datum.waarde
    }

    @Test
    void constructorMetNamedParameters() {
        def waardes = [aanvang: 20150101, partij: 36101]
        def attr = new GebeurtenisAttributen(waardes)

        assert attr.aanvang == 20150101
        assert attr.toelichting.length() == 15
        assert attr.partij == 36101
    }

}
