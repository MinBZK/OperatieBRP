/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.SorteervolgordeAttribuut;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonAfgeleidAdministratiefModel;
import org.apache.commons.lang.NotImplementedException;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.Date;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;


/**
 * Tests die user klasse aanpassingen valideren.
 */
public class UserKlasseAanpassingenGeneratieTest {

    private static final long MOMENT_IN_DE_TIJD = 1380116282881L;

//    @Test
//    public void abonnementKlassesHebbenIsGeldigMethode() {
//        Method methode = ReflectionUtils.findMethod(Abonnement.class, "isGeldigOp", DatumAttribuut.class);
//        assertThat(methode, notNullValue());
//
//        methode = ReflectionUtils.findMethod(ToegangAbonnementModel.class, "isGeldigOp", DatumAttribuut.class);
//        assertThat(methode, notNullValue());
//
//        methode = ReflectionUtils.findMethod(DienstModel.class, "isGeldigOp", DatumAttribuut.class);
//        assertThat(methode, notNullValue());
//    }

    @Test
    public void aanpassingDatumTijdAttribuutGeeftNooitTimestampInstantie() {
        final Timestamp timestamp = new Timestamp(MOMENT_IN_DE_TIJD);

        final DatumTijdAttribuut datumTijdAttribuut = new DatumTijdAttribuut(timestamp);

        assertThat(datumTijdAttribuut.getWaarde(), not(instanceOf(Timestamp.class)));
    }

    @Test
    public void datumTijdAttribuutEqualsGebruiktGetwaardeMethode() {
        final Timestamp timestamp = new Timestamp(MOMENT_IN_DE_TIJD);
        final Date date = new Date(MOMENT_IN_DE_TIJD);

        final DatumTijdAttribuut timestampAttribuut = new DatumTijdAttribuut(timestamp);
        final DatumTijdAttribuut dateAttribuut = new DatumTijdAttribuut(date);

        assertThat(dateAttribuut.equals(timestampAttribuut), is(true));
        assertThat(timestampAttribuut.equals(dateAttribuut), is(true));
    }

    @Test
    public void testPersoonHisVolledigViewHeeftCommunicatieId() {
        final PersoonHisVolledigView view = new PersoonHisVolledigView(null, null);
        final String commId = "TestString";
        view.setCommunicatieID(commId);

        assertThat(view.getCommunicatieID(), equalTo(commId));
    }

    @Test(expected = NotImplementedException.class)
    public void aanpassingInHisPersoonAfgeleidAdministratiefModel() {
        final ActieModel actieModel = new ActieModel(null, null, null, null, null, DatumTijdAttribuut.nu(), null);

        final HisPersoonAfgeleidAdministratiefModel model =
                new HisPersoonAfgeleidAdministratiefModel(null, null,
                    DatumTijdAttribuut.nu(), new SorteervolgordeAttribuut((byte) 1),
                        JaNeeAttribuut.NEE,
                        DatumTijdAttribuut.nu(), actieModel);

        assertNotNull(model);

        // expect exception
        model.getIndicatieGegevensInOnderzoek();
    }
}
