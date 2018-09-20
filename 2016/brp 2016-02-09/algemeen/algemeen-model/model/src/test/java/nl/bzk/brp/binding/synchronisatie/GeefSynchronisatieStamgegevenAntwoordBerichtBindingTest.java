/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.binding.synchronisatie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import nl.bzk.brp.binding.AbstractBindingUitIntegratieTest;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AangeverCodeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.OmschrijvingEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.RedenVerliesCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Bijhoudingsresultaat;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Verwerkingsresultaat;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Aangever;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RedenVerliesNLNationaliteit;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPartij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestPartijBuilder;
import nl.bzk.brp.model.basis.SynchroniseerbaarStamgegeven;
import nl.bzk.brp.model.bericht.ber.BerichtMeldingBericht;
import nl.bzk.brp.model.synchronisatie.GeefSynchronisatieStamgegevenAntwoordBericht;
import nl.bzk.brp.utils.TestDataBouwer;
import org.jibx.runtime.JiBXException;
import org.junit.Ignore;
import org.junit.Test;


/**
 *
 */
public class GeefSynchronisatieStamgegevenAntwoordBerichtBindingTest extends
        AbstractBindingUitIntegratieTest<GeefSynchronisatieStamgegevenAntwoordBericht>
{

    @Test
    public void testSynchronisatieAangeverTabel() throws JiBXException {
        List<SynchroniseerbaarStamgegeven> synchronisatieStamgegevensAangever = new ArrayList<>();
        synchronisatieStamgegevensAangever.add(nieuwAangever());
        synchronisatieStamgegevensAangever.add(nieuwAangever());

        GeefSynchronisatieStamgegevenAntwoordBericht bericht = new GeefSynchronisatieStamgegevenAntwoordBericht();
        bericht.setStuurgegevens(TestDataBouwer.getTestBerichtStuurgegevensGroepBericht());
        bericht.setResultaat(maakResultaatVoorAntwoordBericht(SoortMelding.GEEN, Verwerkingsresultaat.GESLAAGD,
                Bijhoudingsresultaat.VERWERKT));
        bericht.setMeldingen(Collections.<BerichtMeldingBericht>emptyList());

        bericht.setSynchronisatieStamgegevens(synchronisatieStamgegevensAangever);
        String xml = marshalObject(bericht);

        valideerTegenSchema(xml);
    }

    // TODO Koppelvlak BMR mismatch nog bespreken met met Gert-Jan
    @Ignore
    @Test
    public void testSynchronisatiePartijTabel() throws JiBXException {
        List<SynchroniseerbaarStamgegeven> synchronisatieStamgegevensPartij = new ArrayList<>();
        synchronisatieStamgegevensPartij.add(nieuwPartij());
        synchronisatieStamgegevensPartij.add(nieuwPartij());

        GeefSynchronisatieStamgegevenAntwoordBericht bericht = new GeefSynchronisatieStamgegevenAntwoordBericht();
        bericht.setStuurgegevens(TestDataBouwer.getTestBerichtStuurgegevensGroepBericht());
        bericht.setResultaat(maakResultaatVoorAntwoordBericht(SoortMelding.GEEN, Verwerkingsresultaat.GESLAAGD,
                Bijhoudingsresultaat.VERWERKT));
        bericht.setMeldingen(Collections.<BerichtMeldingBericht>emptyList());

        bericht.setSynchronisatieStamgegevens(synchronisatieStamgegevensPartij);
        String xml = marshalObject(bericht);

        valideerTegenSchema(xml);
    }

    @Test
    public void testSynchronisatieVerwerkingsResultaat() throws JiBXException {
        GeefSynchronisatieStamgegevenAntwoordBericht bericht = new GeefSynchronisatieStamgegevenAntwoordBericht();
        bericht.setStuurgegevens(TestDataBouwer.getTestBerichtStuurgegevensGroepBericht());
        bericht.setResultaat(maakResultaatVoorAntwoordBericht(SoortMelding.GEEN, Verwerkingsresultaat.GESLAAGD,
                Bijhoudingsresultaat.VERWERKT));
        bericht.setMeldingen(Collections.<BerichtMeldingBericht>emptyList());

        bericht.setSynchronisatieStamgegevens(new ArrayList<SynchroniseerbaarStamgegeven>());
        for (Verwerkingsresultaat verwerkingsresultaat : Verwerkingsresultaat.values()) {
            if (verwerkingsresultaat != Verwerkingsresultaat.DUMMY) {
                bericht.getSynchronisatieStamgegevens().add(verwerkingsresultaat);
            }
        }
        String xml = marshalObject(bericht);

        valideerTegenSchema(xml);
    }

    @Test
    public void testSynchronisatieRedenVerliesNationaliteit() throws JiBXException {
        GeefSynchronisatieStamgegevenAntwoordBericht bericht = new GeefSynchronisatieStamgegevenAntwoordBericht();
        List<SynchroniseerbaarStamgegeven> synchronisatieStamgegevensRdnVerliesNation = new ArrayList<>();
        synchronisatieStamgegevensRdnVerliesNation.add(nieuwRdnVerliesNation());
        synchronisatieStamgegevensRdnVerliesNation.add(nieuwRdnVerliesNation());

        bericht.setStuurgegevens(TestDataBouwer.getTestBerichtStuurgegevensGroepBericht());
        bericht.setResultaat(maakResultaatVoorAntwoordBericht(SoortMelding.GEEN, Verwerkingsresultaat.GESLAAGD,
                Bijhoudingsresultaat.VERWERKT));
        bericht.setMeldingen(Collections.<BerichtMeldingBericht>emptyList());

        bericht.setSynchronisatieStamgegevens(synchronisatieStamgegevensRdnVerliesNation);
        String xml = marshalObject(bericht);

        valideerTegenSchema(xml);
    }

    // TODO Koppelvlak BMR mismatch nog bespreken met met Gert-Jan
    @Ignore
    @Test
    public void testSynchronisatieSoortAdmnHnd() throws JiBXException {
        GeefSynchronisatieStamgegevenAntwoordBericht bericht = new GeefSynchronisatieStamgegevenAntwoordBericht();
        List<SynchroniseerbaarStamgegeven> synchronisatieStamgegevens = new ArrayList<>();

        for (SoortAdministratieveHandeling soortAdministratieveHandeling : SoortAdministratieveHandeling.values()) {
            if (soortAdministratieveHandeling.ordinal() > 0) {
                synchronisatieStamgegevens.add(soortAdministratieveHandeling);
            }
        }

        bericht.setStuurgegevens(TestDataBouwer.getTestBerichtStuurgegevensGroepBericht());
        bericht.setResultaat(maakResultaatVoorAntwoordBericht(SoortMelding.GEEN, Verwerkingsresultaat.GESLAAGD,
                Bijhoudingsresultaat.VERWERKT));
        bericht.setMeldingen(Collections.<BerichtMeldingBericht>emptyList());

        bericht.setSynchronisatieStamgegevens(synchronisatieStamgegevens);
        String xml = marshalObject(bericht);

        valideerTegenSchema(xml);
    }

    private SynchroniseerbaarStamgegeven nieuwRdnVerliesNation() {
        return new RedenVerliesNLNationaliteit(new RedenVerliesCodeAttribuut((short) 23),
                new OmschrijvingEnumeratiewaardeAttribuut("oms"), new DatumEvtDeelsOnbekendAttribuut(20100101), null);
    }

    private Aangever nieuwAangever() {
        return new Aangever(new AangeverCodeAttribuut("P"), new NaamEnumeratiewaardeAttribuut("boe"),
                new OmschrijvingEnumeratiewaardeAttribuut("oms"));
    }

    private SynchroniseerbaarStamgegeven nieuwPartij() {
        return TestPartijBuilder.maker().metNaam("piet").metSoort(SoortPartij.OVERHEIDSORGAAN).metCode(123456).metDatumAanvang(20010101).maak();
//        return new Partij(new NaamEnumeratiewaardeAttribuut("piet"), SoortPartij.OVERHEIDSORGAAN)
//                new PartijCodeAttribuut(123456), new DatumEvtDeelsOnbekendAttribuut(20010101), null, null, JaNeeAttribuut.JA, null, null);
    }

    @Override
    protected Class<GeefSynchronisatieStamgegevenAntwoordBericht> getBindingClass() {
        return GeefSynchronisatieStamgegevenAntwoordBericht.class;
    }

    @Override
    protected String getSchemaBestand() {
        return "brp0200_lvgSynchronisatie_Berichten.xsd";
    }
}
