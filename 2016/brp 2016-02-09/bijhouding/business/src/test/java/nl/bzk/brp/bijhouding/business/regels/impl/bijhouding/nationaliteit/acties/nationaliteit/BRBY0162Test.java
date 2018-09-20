/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.bijhouding.nationaliteit.acties.nationaliteit;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NationaliteitcodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NationaliteitAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortDocumentAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestNationaliteitBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestSoortDocumentBuilder;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieBronBericht;
import nl.bzk.brp.model.bericht.kern.ActieConversieGBABericht;
import nl.bzk.brp.model.bericht.kern.DocumentBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonNationaliteitBericht;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Unit test voor de {@link BRBY0162} bedrijfsregel.
 */
public class BRBY0162Test {

    private final DatumEvtDeelsOnbekendAttribuut opTijd    = new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.vandaag());
    private final DatumEvtDeelsOnbekendAttribuut netOpTijd = new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.vandaag());
    private final DatumEvtDeelsOnbekendAttribuut teLaat    = new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.vandaag());

    private final BRBY0162 brby0162 = new BRBY0162();

    @Before
    public void init() {
        netOpTijd.voegJaarToe(-1);
        teLaat.voegJaarToe(-2);
    }

    @Test
    public void testGetRegel() {
        Assert.assertEquals(Regel.BRBY0162, brby0162.getRegel());
    }

    @Test
    public void testNationaliteitenNull() {
        final List<BerichtEntiteit> overtreders = brby0162.voerRegelUit(null, bouwNieuweSituatie(null), null, null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testNationaliteitenLeeg() {
        final PersoonBericht persoon = bouwNieuweSituatie(null);
        persoon.setNationaliteiten(new ArrayList<PersoonNationaliteitBericht>());
        final List<BerichtEntiteit> overtreders = brby0162.voerRegelUit(null, persoon, null, null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testVreemdeNationaliteit() {
        final List<BerichtEntiteit> overtreders = brby0162
            .voerRegelUit(null, bouwNieuweSituatie(new NationaliteitcodeAttribuut((short) 1234)), null, null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testNederlandseNationaliteitBronnenNull() {
        final ActieBericht actie = bouwActie(null);
        actie.setBronnen(null);
        final List<BerichtEntiteit> overtreders = brby0162
            .voerRegelUit(null, bouwNieuweSituatie(NationaliteitcodeAttribuut.NL_NATIONALITEIT_CODE), actie, null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testNederlandseNationaliteitBronnenLeeg() {
        final ActieBericht actie = bouwActie(null);
        actie.setBronnen(new ArrayList<ActieBronBericht>());
        final List<BerichtEntiteit> overtreders = brby0162.voerRegelUit(null, bouwNieuweSituatie(NationaliteitcodeAttribuut.NL_NATIONALITEIT_CODE), actie, null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testNederlandseNationaliteitDocumentNull() {
        final ActieBericht actie = bouwActie(teLaat);
        ReflectionTestUtils.setField(actie.getBronnen().iterator().next(), "document", null);
        final List<BerichtEntiteit> overtreders = brby0162.voerRegelUit(null, bouwNieuweSituatie(NationaliteitcodeAttribuut.NL_NATIONALITEIT_CODE), actie, null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testNederlandseNationaliteitBronAnders() {
        final ActieBericht actie = bouwActie(teLaat);
        ReflectionTestUtils.setField(actie.getBronnen().iterator().next().getDocument().getSoort().getWaarde(), "naam", new NaamEnumeratiewaardeAttribuut("Ander document"));
        final List<BerichtEntiteit> overtreders = brby0162.voerRegelUit(null, bouwNieuweSituatie(NationaliteitcodeAttribuut.NL_NATIONALITEIT_CODE), actie, null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testNederlandseNationaliteitBronKoninklijkBesluitOpTijd() {
        final List<BerichtEntiteit> overtreders = brby0162.voerRegelUit(null, bouwNieuweSituatie(NationaliteitcodeAttribuut.NL_NATIONALITEIT_CODE), bouwActie(opTijd), null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testNederlandseNationaliteitBronKoninklijkBesluitNetNogOpTijd() {
        final List<BerichtEntiteit> overtreders = brby0162.voerRegelUit(null, bouwNieuweSituatie(NationaliteitcodeAttribuut.NL_NATIONALITEIT_CODE), bouwActie(netOpTijd), null);
        Assert.assertEquals(0, overtreders.size());
    }

    @Test
    public void testNederlandseNationaliteitBronKoninklijkBesluitTeLaat() {
        final List<BerichtEntiteit> overtreders = brby0162.voerRegelUit(null, bouwNieuweSituatie(NationaliteitcodeAttribuut.NL_NATIONALITEIT_CODE), bouwActie(teLaat), null);
        Assert.assertEquals(1, overtreders.size());
    }

    private PersoonBericht bouwNieuweSituatie(final NationaliteitcodeAttribuut nationaliteit) {
        final PersoonBericht persoon = new PersoonBericht();
        if (nationaliteit != null) {
            persoon.setNationaliteiten(new ArrayList<PersoonNationaliteitBericht>());
            final PersoonNationaliteitBericht persoonNationaliteit = new PersoonNationaliteitBericht();
            persoonNationaliteit.setNationaliteit(new NationaliteitAttribuut(
                TestNationaliteitBuilder.maker().metCode(nationaliteit).maak()));
            persoon.getNationaliteiten().add(persoonNationaliteit);
        }
        return persoon;
    }

    private ActieBericht bouwActie(final DatumEvtDeelsOnbekendAttribuut datumAanvangGeldigheidNaturalisatie) {
        final ActieBericht actie = new ActieConversieGBABericht();
        actie.setTijdstipRegistratie(DatumTijdAttribuut.nu());
        actie.setDatumAanvangGeldigheid(datumAanvangGeldigheidNaturalisatie);
        actie.setBronnen(new ArrayList<ActieBronBericht>());
        final DocumentBericht document = new DocumentBericht();
        document.setSoort(new SoortDocumentAttribuut(TestSoortDocumentBuilder.maker()
            .metNaam(NaamEnumeratiewaardeAttribuut.DOCUMENT_NAAM_KONINKLIJK_BESLUIT).maak()));
        final ActieBronBericht bron = new ActieBronBericht();
        bron.setDocument(document);
        actie.getBronnen().add(bron);
        return actie;
    }
}
