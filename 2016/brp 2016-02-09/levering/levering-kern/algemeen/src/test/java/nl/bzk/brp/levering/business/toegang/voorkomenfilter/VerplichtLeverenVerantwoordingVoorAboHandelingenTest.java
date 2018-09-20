/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.toegang.voorkomenfilter;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Set;
import nl.bzk.brp.levering.algemeen.service.PartijService;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.Verwerkingssoort;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OntleningstoelichtingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijRol;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Rol;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestPartijBuilder;
import nl.bzk.brp.model.basis.FormeelVerantwoordbaar;
import nl.bzk.brp.model.basis.HistorieEntiteit;
import nl.bzk.brp.model.basis.MaterieelVerantwoordbaar;
import nl.bzk.brp.model.basis.VerantwoordingsEntiteit;
import nl.bzk.brp.model.hisvolledig.impl.kern.AdministratieveHandelingHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.AdministratieveHandelingHisVolledig;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.util.hisvolledig.kern.AdministratieveHandelingHisVolledigImplBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public class VerplichtLeverenVerantwoordingVoorAboHandelingenTest {

    private static final String ID = "iD";

    @Mock
    private PartijService partijService;

    @InjectMocks
    private final VerplichtLeverenVerantwoordingVoorAboHandelingenImpl regel = new VerplichtLeverenVerantwoordingVoorAboHandelingenImpl();

    @Test
    public void testLegeArgumenten() {
        regel.voerRegelUit(Collections.<HistorieEntiteit>emptyList(), Collections.<AdministratieveHandelingHisVolledig>emptyList());

    }

    @Test
    public void testMetHandeling() {
        final AdministratieveHandelingHisVolledig handelingHisVolledig = maakHandeling(1, Rol.DUMMY);
        when(partijService.vindPartijOpCode(anyInt())).thenReturn(handelingHisVolledig.getPartij().getWaarde());
        regel.voerRegelUit(Collections.<HistorieEntiteit>emptyList(), Collections.singletonList(handelingHisVolledig));

    }

    @Test
    public void testAboVerantwoordlingLeverenVoorMaterieelVoorkomen() {
        final AdministratieveHandelingHisVolledig handelingHisVolledig = maakHandeling(2, Rol.BIJHOUDINGSORGAAN_MINISTER);
        when(partijService.vindPartijOpCode(anyInt())).thenReturn(handelingHisVolledig.getPartij().getWaarde());

        final DummyMaterieelVerantwoordbaar materieelVerantwoordbaar = new DummyMaterieelVerantwoordbaar();
        materieelVerantwoordbaar.setVerantwoordingInhoud(maakActie(1));
        materieelVerantwoordbaar.setVerantwoordingVerval(maakActie(2));
        materieelVerantwoordbaar.setVerantwoordingAanpassingGeldigheid(maakActie(3));
        regel.voerRegelUit(Collections.<HistorieEntiteit>singletonList(materieelVerantwoordbaar), Collections.singletonList(handelingHisVolledig));

        assertFalse(materieelVerantwoordbaar.getVerantwoordingInhoud().isMagGeleverdWorden());
        assertTrue(materieelVerantwoordbaar.getVerantwoordingVerval().isMagGeleverdWorden());
        assertFalse(materieelVerantwoordbaar.getVerantwoordingAanpassingGeldigheid().isMagGeleverdWorden());

    }

    @Test
    public void testAboVerantwoordlingLeverenVoorFormeelVoorkomen() {
        final AdministratieveHandelingHisVolledig handelingHisVolledig = maakHandeling(2, Rol.BIJHOUDINGSORGAAN_MINISTER);
        when(partijService.vindPartijOpCode(anyInt())).thenReturn(handelingHisVolledig.getPartij().getWaarde());

        final DummyFormeelVerantwoordbaar formeelVerantwoordbaar = new DummyFormeelVerantwoordbaar();
        formeelVerantwoordbaar.setVerantwoordingInhoud(maakActie(1));
        formeelVerantwoordbaar.setVerantwoordingVerval(maakActie(2));
        regel.voerRegelUit(Collections.<HistorieEntiteit>singletonList(formeelVerantwoordbaar), Collections.singletonList(handelingHisVolledig));

        assertFalse(formeelVerantwoordbaar.getVerantwoordingInhoud().isMagGeleverdWorden());
        assertTrue(formeelVerantwoordbaar.getVerantwoordingVerval().isMagGeleverdWorden());

    }


    private AdministratieveHandelingHisVolledig maakHandeling(final long handelingId, final Rol rol) {
        final Partij partij = TestPartijBuilder.maker().metCode(123).maak();
        final PartijRol partijRol = new PartijRol(partij, rol, null, null);
        final Set<PartijRol> partijRollen = Collections.singleton(partijRol);
        ReflectionTestUtils.setField(partij, "partijrollen", partijRollen);
        final AdministratieveHandelingHisVolledigImpl dummy = new AdministratieveHandelingHisVolledigImplBuilder(
            SoortAdministratieveHandeling.DUMMY,
            partij, new OntleningstoelichtingAttribuut("dummy"), DatumTijdAttribuut.nu(), true).build();
        ReflectionTestUtils.setField(dummy, ID, handelingId);
        return dummy;
    }

    private static ActieModel maakActie(final long handelingId) {

        final AdministratieveHandelingModel administratieveHandelingModel = new AdministratieveHandelingModel(null, null, null, null);
        ReflectionTestUtils.setField(administratieveHandelingModel, ID, handelingId);
        return new ActieModel(new SoortActieAttribuut(SoortActie.CORRECTIE_ADRES), administratieveHandelingModel, null, null, null, null, null) {
            @Override
            public boolean heeftMinimaal1AttribuutDatGeleverdMagWorden() {
                return true;
            }
        };
    }

    private static class DummyMaterieelVerantwoordbaar implements MaterieelVerantwoordbaar, HistorieEntiteit {

        private VerantwoordingsEntiteit verantwoordingInhoud;
        private VerantwoordingsEntiteit verantwoordingVerval;
        private VerantwoordingsEntiteit verantwoordingAanpassingGeldigheid;

        @Override
        public ActieModel getVerantwoordingAanpassingGeldigheid() {
            return (ActieModel) verantwoordingAanpassingGeldigheid;
        }

        @Override
        public void setVerantwoordingAanpassingGeldigheid(final VerantwoordingsEntiteit verantwoodingAanpassingGeldigheid) {
            this.verantwoordingAanpassingGeldigheid = verantwoodingAanpassingGeldigheid;
        }

        @Override
        public ActieModel getVerantwoordingInhoud() {
            return (ActieModel) verantwoordingInhoud;
        }

        @Override
        public ActieModel getVerantwoordingVerval() {
            return (ActieModel) verantwoordingVerval;
        }

        @Override
        public void setVerantwoordingInhoud(final VerantwoordingsEntiteit verantwoodingInhoud) {
            this.verantwoordingInhoud = verantwoodingInhoud;
        }

        @Override
        public void setVerantwoordingVerval(final VerantwoordingsEntiteit verantwoodingVerval) {
            this.verantwoordingVerval = verantwoodingVerval;
        }

        @Override
        public Verwerkingssoort getVerwerkingssoort() {
            return null;
        }

        @Override
        public void setVerwerkingssoort(final Verwerkingssoort verwerkingsSoort) {

        }

        @Override
        public boolean isMagGeleverdWorden() {
            return false;
        }
    }

    private static class DummyFormeelVerantwoordbaar implements FormeelVerantwoordbaar, HistorieEntiteit {

        private VerantwoordingsEntiteit verantwoordingInhoud;
        private VerantwoordingsEntiteit verantwoordingVerval;
        private VerantwoordingsEntiteit verantwoordingAanpassingGeldigheid;

        @Override
        public ActieModel getVerantwoordingInhoud() {
            return (ActieModel) verantwoordingInhoud;
        }

        @Override
        public ActieModel getVerantwoordingVerval() {
            return (ActieModel) verantwoordingVerval;
        }

        @Override
        public void setVerantwoordingInhoud(final VerantwoordingsEntiteit verantwoodingInhoud) {
            this.verantwoordingInhoud = verantwoodingInhoud;
        }

        @Override
        public void setVerantwoordingVerval(final VerantwoordingsEntiteit verantwoodingVerval) {
            this.verantwoordingVerval = verantwoodingVerval;
        }

        @Override
        public Verwerkingssoort getVerwerkingssoort() {
            return null;
        }

        @Override
        public void setVerwerkingssoort(final Verwerkingssoort verwerkingsSoort) {

        }

        @Override
        public boolean isMagGeleverdWorden() {
            return false;
        }
    }
}
