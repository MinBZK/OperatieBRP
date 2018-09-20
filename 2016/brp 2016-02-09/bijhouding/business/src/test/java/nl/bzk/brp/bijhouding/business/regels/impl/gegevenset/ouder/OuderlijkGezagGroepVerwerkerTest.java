/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.ouder;


import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.bericht.kern.OuderBericht;
import nl.bzk.brp.model.bericht.kern.OuderOuderlijkGezagGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.OuderHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.util.hisvolledig.kern.OuderHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OuderlijkGezagGroepVerwerkerTest {

    private OuderHisVolledigImpl ouderZonderOuderlijkGezag;
    private OuderBericht         ouderBericht;
    private ActieModel           actieModelBestaand;
    private ActieModel           actieModelBericht;

    @Before
    public void setUp() {
        actieModelBestaand = new ActieModel(new SoortActieAttribuut(SoortActie.REGISTRATIE_GEBOORTE),
            null, null, new DatumEvtDeelsOnbekendAttribuut(20000101), null, DatumTijdAttribuut.bouwDatumTijd(2000, 1, 1), null);
        actieModelBericht = new ActieModel(new SoortActieAttribuut(SoortActie.REGISTRATIE_GEBOORTE),
            null, null, new DatumEvtDeelsOnbekendAttribuut(20100101), null, DatumTijdAttribuut.bouwDatumTijd(2010, 1, 1), null);

        ouderZonderOuderlijkGezag = new OuderHisVolledigImplBuilder(null, null)
                .nieuwOuderlijkGezagRecord(actieModelBestaand).indicatieOuderHeeftGezag(JaNeeAttribuut.NEE)
                .eindeRecord().build();
        ouderBericht = new OuderBericht();
        final OuderOuderlijkGezagGroepBericht gezagBericht = new OuderOuderlijkGezagGroepBericht();
        gezagBericht.setIndicatieOuderHeeftGezag(JaNeeAttribuut.JA);
        ouderBericht.setOuderlijkGezag(gezagBericht);
    }

    @Test
    public void testGetRegel() {
        final OuderlijkGezagGroepVerwerker verwerker =
            new OuderlijkGezagGroepVerwerker(ouderBericht, ouderZonderOuderlijkGezag, actieModelBericht);
        Assert.assertEquals(Regel.VR01003, verwerker.getRegel());
    }

    @Test
    public void testVerzamelAfleidingsregels() {
        final OuderlijkGezagGroepVerwerker verwerker =
            new OuderlijkGezagGroepVerwerker(ouderBericht, ouderZonderOuderlijkGezag, actieModelBericht);
        verwerker.verzamelAfleidingsregels();
        Assert.assertTrue(verwerker.getAfleidingsregels().isEmpty());
    }

    @Test
    public void testVerwerking() {
        final OuderHisVolledigImpl ouderUnderTest = ouderZonderOuderlijkGezag;
        final OuderlijkGezagGroepVerwerker verwerker =
            new OuderlijkGezagGroepVerwerker(ouderBericht, ouderUnderTest, actieModelBericht);
        verwerker.neemBerichtDataOverInModel();

        Assert.assertEquals(3, ouderUnderTest.getOuderOuderlijkGezagHistorie().getHistorie().size());
        Assert.assertNotNull(ouderUnderTest.getOuderOuderlijkGezagHistorie().getActueleRecord());
        Assert.assertEquals(JaNeeAttribuut.JA, ouderUnderTest.getOuderOuderlijkGezagHistorie().getActueleRecord().getIndicatieOuderHeeftGezag());
    }

}
