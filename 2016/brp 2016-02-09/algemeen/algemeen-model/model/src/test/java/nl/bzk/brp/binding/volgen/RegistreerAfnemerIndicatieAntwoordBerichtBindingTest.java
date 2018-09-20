/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.binding.volgen;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.binding.AbstractBindingUitIntegratieTest;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.MeldingtekstAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMeldingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.Verwerkingsresultaat;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.VerwerkingsresultaatAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.RegelAttribuut;
import nl.bzk.brp.model.bericht.ber.BerichtMeldingBericht;
import nl.bzk.brp.model.bericht.ber.BerichtResultaatGroepBericht;
import nl.bzk.brp.model.bericht.ber.BerichtStandaardGroepBericht;
import nl.bzk.brp.model.bericht.ber.MeldingBericht;
import nl.bzk.brp.model.bericht.kern.HandelingPlaatsingAfnemerindicatieBericht;
import nl.bzk.brp.model.synchronisatie.RegistreerAfnemerindicatieAntwoordBericht;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import nl.bzk.brp.utils.TestDataBouwer;
import org.jibx.runtime.JiBXException;
import org.junit.Test;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;


public class RegistreerAfnemerIndicatieAntwoordBerichtBindingTest extends
        AbstractBindingUitIntegratieTest<RegistreerAfnemerindicatieAntwoordBericht>
{

    private static final Logger LOGGER = LoggerFactory
            .getLogger();

    @Test
    public void testBinding() throws JiBXException {
        final RegistreerAfnemerindicatieAntwoordBericht bericht = new RegistreerAfnemerindicatieAntwoordBericht();

        bericht.setStuurgegevens(TestDataBouwer.getTestBerichtStuurgegevensGroepBericht());
        bericht.setResultaat(getTestResultaat());
        bericht.setMeldingen(getTestMeldingen());
        bericht.setStandaard(new BerichtStandaardGroepBericht());
        bericht.getStandaard().setAdministratieveHandeling(getAdministratieveHandelingAfnemerindicatie());

        final String xml = marshalObject(bericht);

        assertNotNull(xml);
        valideerTegenSchema(xml);

        LOGGER.info(xml);
    }

    private HandelingPlaatsingAfnemerindicatieBericht getAdministratieveHandelingAfnemerindicatie() {
        final HandelingPlaatsingAfnemerindicatieBericht handelingPlaatsingAfnemerindicatieBericht =
                new HandelingPlaatsingAfnemerindicatieBericht();
        handelingPlaatsingAfnemerindicatieBericht.setCommunicatieID("een administratieveHandeling");
        handelingPlaatsingAfnemerindicatieBericht.setObjecttype("een sleutel");
        handelingPlaatsingAfnemerindicatieBericht.setObjectSleutel("123");
        final Partij partij = StatischeObjecttypeBuilder.PARTIJ_GEMEENTE_AMSTERDAM.getWaarde();
        handelingPlaatsingAfnemerindicatieBericht.setPartij(new PartijAttribuut(partij));
        handelingPlaatsingAfnemerindicatieBericht.setPartijCode(partij.getCode().toString());
        handelingPlaatsingAfnemerindicatieBericht.setTijdstipRegistratie(DatumTijdAttribuut.bouwDatumTijd(2013, 1, 12));
        return handelingPlaatsingAfnemerindicatieBericht;
    }

    private List<BerichtMeldingBericht> getTestMeldingen() {

        final List<BerichtMeldingBericht> meldingen = new ArrayList<>();

        final BerichtMeldingBericht berichtMeldingBericht = new BerichtMeldingBericht();
        final MeldingBericht meldingBericht = new MeldingBericht();
        meldingBericht.setSoort(new SoortMeldingAttribuut(SoortMelding.INFORMATIE));
        meldingBericht.setRegel(new RegelAttribuut(Regel.ACT0001));
        meldingBericht.setReferentieID("ref1");
        final MeldingtekstAttribuut meldingTekst = new MeldingtekstAttribuut("een tekst");
        meldingBericht.setMelding(meldingTekst);

        berichtMeldingBericht.setMelding(meldingBericht);
        meldingen.add(berichtMeldingBericht);

        return meldingen;
    }

    private BerichtResultaatGroepBericht getTestResultaat() {

        final BerichtResultaatGroepBericht berichtResultaatGroepBericht = new BerichtResultaatGroepBericht();
        berichtResultaatGroepBericht.setHoogsteMeldingsniveau(new SoortMeldingAttribuut(SoortMelding.INFORMATIE));
        berichtResultaatGroepBericht.setVerwerking(new VerwerkingsresultaatAttribuut(Verwerkingsresultaat.GESLAAGD));

        return berichtResultaatGroepBericht;
    }

    @Override
    protected Class<RegistreerAfnemerindicatieAntwoordBericht> getBindingClass() {
        return RegistreerAfnemerindicatieAntwoordBericht.class;
    }

    @Override
    protected String getSchemaBestand() {
        return "brp0200_lvgAfnemerindicatie_Berichten.xsd";
    }
}
