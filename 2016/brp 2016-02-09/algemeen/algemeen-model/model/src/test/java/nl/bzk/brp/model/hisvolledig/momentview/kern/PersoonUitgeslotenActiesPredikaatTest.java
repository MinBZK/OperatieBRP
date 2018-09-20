/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.hisvolledig.momentview.kern;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.basis.AbstractMaterieelHistorischMetActieVerantwoording;
import nl.bzk.brp.model.basis.FormeelVerantwoordbaar;
import nl.bzk.brp.model.basis.MaterieelVerantwoordbaar;
import nl.bzk.brp.model.basis.MaterieleHistorie;
import nl.bzk.brp.model.logisch.kern.PersoonGeboorteGroep;
import nl.bzk.brp.model.logisch.kern.PersoonIdentificatienummersGroep;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonGeboorteModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonIdentificatienummersModel;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class PersoonUitgeslotenActiesPredikaatTest {

    private final long actieInhoudId = 1L;

    @Test
    public void testNietVervallenGeenAapassingGeldigheidActieInhoudGeldigMaterieel() {
        final PersoonUitgeslotenActiesPredikaat persoonViewOpActiesPredikaat = new PersoonUitgeslotenActiesPredikaat();
        final MaterieelVerantwoordbaar<ActieModel> materieelVerantwoordbaar = maakMaterieelVerantwoordbaar();

        assertTrue(persoonViewOpActiesPredikaat.evaluate(materieelVerantwoordbaar));
    }

    @Test
    public void testNietVervallenActieInhoudGeldigFormeel() {
        final PersoonUitgeslotenActiesPredikaat persoonViewOpActiesPredikaat = new PersoonUitgeslotenActiesPredikaat();
        final FormeelVerantwoordbaar<ActieModel> formeelVerantwoordbaar = maakFormeelVerantwoordbaar();

        assertTrue(persoonViewOpActiesPredikaat.evaluate(formeelVerantwoordbaar));
    }

    @Test
    public void testNietVervallenWelAanpassingGeldigheidActieInhoudGeldigMaterieel() {
        final PersoonUitgeslotenActiesPredikaat persoonViewOpActiesPredikaat = new PersoonUitgeslotenActiesPredikaat();
        final AbstractMaterieelHistorischMetActieVerantwoording materieelVerantwoordbaar = maakMaterieelVerantwoordbaar();

        materieelVerantwoordbaar.getMaterieleHistorie().setDatumEindeGeldigheid(new DatumEvtDeelsOnbekendAttribuut(20000000));

        assertFalse(persoonViewOpActiesPredikaat.evaluate(materieelVerantwoordbaar));
    }

    @Test
    public void testVervallenEnActieGeldigGeenAanpassingGeldigheidActieInhoudGeldigMaterieel() {
        final PersoonUitgeslotenActiesPredikaat persoonViewOpActiesPredikaat = new PersoonUitgeslotenActiesPredikaat();
        final MaterieelVerantwoordbaar<ActieModel> materieelVerantwoordbaar = maakMaterieelVerantwoordbaar();

        materieelVerantwoordbaar.setVerantwoordingVerval(mock(ActieModel.class));

        assertFalse(persoonViewOpActiesPredikaat.evaluate(materieelVerantwoordbaar));
    }

    @Test
    public void testVervallenEnActieGeldigActieInhoudGeldigFormeel() {
        final PersoonUitgeslotenActiesPredikaat persoonViewOpActiesPredikaat = new PersoonUitgeslotenActiesPredikaat();
        final FormeelVerantwoordbaar<ActieModel> formeelVerantwoordbaar = maakFormeelVerantwoordbaar();

        formeelVerantwoordbaar.setVerantwoordingVerval(mock(ActieModel.class));

        assertFalse(persoonViewOpActiesPredikaat.evaluate(formeelVerantwoordbaar));
    }

    @Test
    public void testVervallenEnActieGeldigWelAapassingGeldigheidActieInhoudGeldigMaterieel() {
        final PersoonUitgeslotenActiesPredikaat persoonViewOpActiesPredikaat = new PersoonUitgeslotenActiesPredikaat();
        final AbstractMaterieelHistorischMetActieVerantwoording materieelVerantwoordbaar = maakMaterieelVerantwoordbaar();

        materieelVerantwoordbaar.setVerantwoordingVerval(mock(ActieModel.class));
        materieelVerantwoordbaar.getMaterieleHistorie().setDatumEindeGeldigheid(new DatumEvtDeelsOnbekendAttribuut(20000000));

        assertFalse(persoonViewOpActiesPredikaat.evaluate(materieelVerantwoordbaar));
    }

    @Test
    public void testVervallenMaarNietGeldigGeenAapassingGeldigheidActieInhoudGeldigMaterieel() {
        final long actieIdNietGeldig = 5L;
        final Set<Long> actieIdsNietGeldig = new HashSet<>(Arrays.asList(new Long[]{ 3L, 4L, actieIdNietGeldig }));

        final PersoonUitgeslotenActiesPredikaat persoonViewOpActiesPredikaat = new PersoonUitgeslotenActiesPredikaat(actieIdsNietGeldig);
        final MaterieelVerantwoordbaar<ActieModel> materieelVerantwoordbaar = maakMaterieelVerantwoordbaar();

        materieelVerantwoordbaar.setVerantwoordingVerval(maakActieMetId(actieIdNietGeldig));

        assertTrue(persoonViewOpActiesPredikaat.evaluate(materieelVerantwoordbaar));
    }


    @Test
    public void testVervallenMaarNietGeldigGeenAapassingGeldigheidActieInhoudGeldigFormeel() {
        final long actieIdNietGeldig = 5L;
        final Set<Long> actieIdsNietGeldig = new HashSet<>(Arrays.asList(new Long[]{ 3L, 4L, actieIdNietGeldig }));

        final PersoonUitgeslotenActiesPredikaat persoonViewOpActiesPredikaat = new PersoonUitgeslotenActiesPredikaat(actieIdsNietGeldig);
        final FormeelVerantwoordbaar<ActieModel> formeelVerantwoordbaar = maakFormeelVerantwoordbaar();

        formeelVerantwoordbaar.setVerantwoordingVerval(maakActieMetId(actieIdNietGeldig));

        assertTrue(persoonViewOpActiesPredikaat.evaluate(formeelVerantwoordbaar));
    }

    @Test
    public void testNietVervallenGeenAanpassingGeldigheidActieInhoudNietGeldigMaterieel() {
        final Set<Long> actieIdsNietGeldig = new HashSet<>(Arrays.asList(new Long[]{ actieInhoudId, 2L, 3L }));

        final PersoonUitgeslotenActiesPredikaat persoonViewOpActiesPredikaat = new PersoonUitgeslotenActiesPredikaat(actieIdsNietGeldig);
        final MaterieelVerantwoordbaar<ActieModel> materieelVerantwoordbaar = maakMaterieelVerantwoordbaar();

        assertFalse(persoonViewOpActiesPredikaat.evaluate(materieelVerantwoordbaar));
    }


    @Test
    public void testNietVervallenActieInhoudNietGeldigFormeel() {
        final Set<Long> actieIdsNietGeldig = new HashSet<>(Arrays.asList(new Long[]{ actieInhoudId, 2L, 3L }));

        final PersoonUitgeslotenActiesPredikaat persoonViewOpActiesPredikaat = new PersoonUitgeslotenActiesPredikaat(actieIdsNietGeldig);
        final FormeelVerantwoordbaar<ActieModel> formeelVerantwoordbaar = maakFormeelVerantwoordbaar();

        assertFalse(persoonViewOpActiesPredikaat.evaluate(formeelVerantwoordbaar));
    }

    @Test
    public void testVervallenMaarNietGeldigGeenAapassingGeldigheidActieInhoudNietGeldigMaterieel() {
        final long nietGeldigeActieVervalId = 4L;
        final Set<Long> actieIdsNietGeldig = new HashSet<>(Arrays.asList(new Long[]{ actieInhoudId, 2L, 3L, nietGeldigeActieVervalId }));

        final PersoonUitgeslotenActiesPredikaat persoonViewOpActiesPredikaat = new PersoonUitgeslotenActiesPredikaat(actieIdsNietGeldig);
        final MaterieelVerantwoordbaar<ActieModel> materieelVerantwoordbaar = maakMaterieelVerantwoordbaar();

        materieelVerantwoordbaar.setVerantwoordingVerval(maakActieMetId(nietGeldigeActieVervalId));

        assertFalse(persoonViewOpActiesPredikaat.evaluate(materieelVerantwoordbaar));
    }

    @Test
    public void testVervallenMaarNietGeldigActieInhoudNietGeldigFormeel() {
        final long nietGeldigeActieVervalId = 4L;
        final Set<Long> actieIdsNietGeldig = new HashSet<>(Arrays.asList(new Long[]{ actieInhoudId, 2L, 3L, nietGeldigeActieVervalId }));

        final PersoonUitgeslotenActiesPredikaat persoonViewOpActiesPredikaat = new PersoonUitgeslotenActiesPredikaat(actieIdsNietGeldig);
        final FormeelVerantwoordbaar<ActieModel> formeelVerantwoordbaar = maakFormeelVerantwoordbaar();

        formeelVerantwoordbaar.setVerantwoordingVerval(maakActieMetId(nietGeldigeActieVervalId));

        assertFalse(persoonViewOpActiesPredikaat.evaluate(formeelVerantwoordbaar));
    }

    private FormeelVerantwoordbaar<ActieModel> maakFormeelVerantwoordbaar() {
        final PersoonGeboorteGroep groep = mock(PersoonGeboorteGroep.class);
        final ActieModel verantwoodingInhoud = maakActieMetId(actieInhoudId);
        return new HisPersoonGeboorteModel(null, groep,
            verantwoodingInhoud);
    }

    private AbstractMaterieelHistorischMetActieVerantwoording maakMaterieelVerantwoordbaar() {
        final PersoonIdentificatienummersGroep groep = mock(PersoonIdentificatienummersGroep.class);
        final MaterieleHistorie historie = mock(MaterieleHistorie.class);
        final ActieModel verantwoodingInhoud = maakActieMetId(actieInhoudId);
        return new HisPersoonIdentificatienummersModel(null, groep, historie,
            verantwoodingInhoud);
    }

    private ActieModel maakActieMetId(final long actieId) {
        final ActieModel actieModel = new ActieModel(null, null, null, null, null, null, null);
        ReflectionTestUtils.setField(actieModel, "iD", actieId);
        return actieModel;
    }

}