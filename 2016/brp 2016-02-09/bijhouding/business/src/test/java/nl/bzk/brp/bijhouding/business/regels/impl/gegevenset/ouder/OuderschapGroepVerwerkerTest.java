/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.ouder;


import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Ja;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.bericht.kern.OuderBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.OuderHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.util.hisvolledig.kern.OuderHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * Unit test voor de functionaliteit zoals geboden door de ouderschapgroep verwerker, ook wel bekend als de
 * VR01002a verwerkingsregel.
 * <br/>
 * Merk op dat in deze test ook specifieke gevallen worden getest op basis van bepaalde administratieve handelingen
 * en dat de tests dus niet zo maar moeten worden aangepast op basis van wat er toevallig wordt geretourneerd, maar
 * dat er altijd goed gekeken moet worden of een wijziging dat specifieke geval dan wel terecht wijzigt.
 */
public class OuderschapGroepVerwerkerTest {

    private OuderHisVolledigImpl ouderMetOuderschap;
    private OuderHisVolledigImpl ouderZonderOuderschap;
    private OuderBericht         ouderBericht;
    private ActieModel           actieModelBestaand;
    private ActieModel           actieModelBericht;

    @Before
    public void setUp() {
        actieModelBestaand = new ActieModel(new SoortActieAttribuut(SoortActie.REGISTRATIE_GEBOORTE),
            null, null, new DatumEvtDeelsOnbekendAttribuut(20000101), null, DatumTijdAttribuut.bouwDatumTijd(2000, 1, 1), null);
        actieModelBericht = new ActieModel(new SoortActieAttribuut(SoortActie.REGISTRATIE_GEBOORTE),
            null, null, new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.vandaag()), null, DatumTijdAttribuut.nu(), null);

        ouderMetOuderschap =
            new OuderHisVolledigImplBuilder(null, null).nieuwOuderschapRecord(actieModelBestaand).indicatieOuder(Ja.J)
                                                       .eindeRecord().build();
        ouderZonderOuderschap = new OuderHisVolledigImplBuilder(null, null).build();
        ouderBericht = new OuderBericht();
    }

    @Test
    public void testGetRegel() {
        final OuderschapGroepVerwerker verwerker =
            new OuderschapGroepVerwerker(ouderBericht, ouderZonderOuderschap, actieModelBericht);
        Assert.assertEquals(Regel.VR01002a, verwerker.getRegel());
    }

    @Test
    public void testVerzamelAfleidingsregels() {
        final OuderschapGroepVerwerker verwerker =
            new OuderschapGroepVerwerker(ouderBericht, ouderZonderOuderschap, actieModelBericht);
        verwerker.verzamelAfleidingsregels();
        Assert.assertTrue(verwerker.getAfleidingsregels().isEmpty());
    }

    @Test
    public void testVerwerkingNogGeenOuderschap() {
        final OuderschapGroepVerwerker verwerker =
            new OuderschapGroepVerwerker(ouderBericht, ouderZonderOuderschap, actieModelBericht);
        verwerker.neemBerichtDataOverInModel();

        Assert.assertFalse(ouderZonderOuderschap.getOuderOuderschapHistorie().getHistorie().isEmpty());
        Assert.assertNotNull(ouderZonderOuderschap.getOuderOuderschapHistorie().getActueleRecord());
        Assert.assertEquals(1, ouderZonderOuderschap.getOuderOuderschapHistorie().getAantal());
    }

    @Test
    public void testVerwerkingReedsOuderschap() {
        final OuderschapGroepVerwerker verwerker =
            new OuderschapGroepVerwerker(ouderBericht, ouderMetOuderschap, actieModelBericht);
        verwerker.neemBerichtDataOverInModel();

        Assert.assertFalse(ouderMetOuderschap.getOuderOuderschapHistorie().getHistorie().isEmpty());
        Assert.assertNotNull(ouderMetOuderschap.getOuderOuderschapHistorie().getActueleRecord());
        Assert.assertEquals(3, ouderMetOuderschap.getOuderOuderschapHistorie().getAantal());
    }

}
