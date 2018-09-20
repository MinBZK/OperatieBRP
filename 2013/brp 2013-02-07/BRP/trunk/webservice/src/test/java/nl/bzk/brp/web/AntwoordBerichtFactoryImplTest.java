/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web;

import java.util.ArrayList;

import nl.bzk.brp.business.dto.bijhouding.BijhoudingResultaat;
import nl.bzk.brp.business.dto.bijhouding.InschrijvingGeboorteBericht;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.Verwerkingswijze;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.basis.Identificeerbaar;
import nl.bzk.brp.model.bericht.ber.BerichtBericht;
import nl.bzk.brp.model.bericht.ber.BerichtParametersGroepBericht;
import nl.bzk.brp.model.bericht.kern.HandelingInschrijvingDoorGeboorteBericht;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.model.validatie.MeldingCode;
import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


public class AntwoordBerichtFactoryImplTest {

    AntwoordBerichtFactoryImpl antwoordBerichtFactory = new AntwoordBerichtFactoryImpl();

    @Test
    public void zouHoogsteMeldingNiveauGMoetenGevenAlsErGeenMeldingenZijn() {
        BerichtBericht ingaandBericht = new InschrijvingGeboorteBericht();
        BijhoudingResultaat resultaat = new BijhoudingResultaat(new ArrayList<Melding>());

        final BerichtParametersGroepBericht parameters = new BerichtParametersGroepBericht();
        parameters.setVerwerkingswijze(Verwerkingswijze.B);
        ingaandBericht.setParameters(parameters);
        ingaandBericht.setAdministratieveHandeling(new HandelingInschrijvingDoorGeboorteBericht());

        AbstractAntwoordBericht antwoordBericht = antwoordBerichtFactory.bouwAntwoordBericht(ingaandBericht, resultaat);
        assertThat(antwoordBericht.getResultaat().getHoogsteMeldingsniveau(), is(SoortMelding.GEEN));

    }

    @Test
    public void zouHoogsteMeldingNiveauFMoetenGevenAlsErEenFoutmeldingIs() {
        BerichtBericht ingaandBericht = new InschrijvingGeboorteBericht();
        final ArrayList<Melding> meldingen = new ArrayList<Melding>();
        meldingen.add(new Melding(SoortMelding.FOUT, MeldingCode.ACT0001, null, null));
        BijhoudingResultaat resultaat = new BijhoudingResultaat(meldingen);

        final BerichtParametersGroepBericht parameters = new BerichtParametersGroepBericht();
        parameters.setVerwerkingswijze(Verwerkingswijze.B);
        ingaandBericht.setParameters(parameters);
        ingaandBericht.setAdministratieveHandeling(new HandelingInschrijvingDoorGeboorteBericht());

        AbstractAntwoordBericht antwoordBericht = antwoordBerichtFactory.bouwAntwoordBericht(ingaandBericht, resultaat);
        assertThat(antwoordBericht.getResultaat().getHoogsteMeldingsniveau(), is(SoortMelding.FOUT));


    }
}
