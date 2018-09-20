/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.verstrekkingsbeperkingspecifiek;

import nl.bzk.brp.bijhouding.business.regels.AbstractAfleidingsregel;
import nl.bzk.brp.bijhouding.business.regels.AfleidingResultaat;
import nl.bzk.brp.bijhouding.business.stappen.resultaat.ResultaatMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.kern.PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledig;
import nl.bzk.brp.model.operationeel.kern.ActieModel;


/**
 * Beeindig een eventueel aanwezige volledige verstrekkingsbeperkingen van een persoon, nav
 * het registreren van een specifieke verstrekkingsbeperking.
 */
public class BeeindigingVolledigeVerstrekkingsbeperkingAfleiding extends AbstractAfleidingsregel<PersoonHisVolledig> {

    /**
     * Forwarding constructor.
     *
     * @param rootObject het root object
     * @param actie de actie
     */
    public BeeindigingVolledigeVerstrekkingsbeperkingAfleiding(final PersoonHisVolledig rootObject,
            final ActieModel actie)
    {
        super(rootObject, actie);
    }

    @Override
    public final Regel getRegel() {
        return Regel.VR00021b;
    }

    @Override
    public AfleidingResultaat leidAf() {
        final PersoonIndicatieVolledigeVerstrekkingsbeperkingHisVolledig volledigeVerstrekkingsbeperking =
            getModel().getIndicatieVolledigeVerstrekkingsbeperking();
        boolean afgeleidVervallen = false;
        // Als er een volledige verstrekkingsbeperking bestaat en die niet al vervallen is,
        // laat deze dan vervallen met als actie en tijdstip de gegevens uit de 'veroorzakende' actie.
        if (volledigeVerstrekkingsbeperking != null
            && !volledigeVerstrekkingsbeperking.getPersoonIndicatieHistorie().isVervallen())
        {
            volledigeVerstrekkingsbeperking.getPersoonIndicatieHistorie().verval(getActie(),
                    getActie().getTijdstipRegistratie());
            afgeleidVervallen = true;
        }

        final AfleidingResultaat afleidingResultaat = new AfleidingResultaat();
        if (afgeleidVervallen) {
            final ResultaatMelding resultaatMelding = ResultaatMelding.builder()
                .withSoort(SoortMelding.WAARSCHUWING)
                .withRegel(this.getRegel())
                .withMeldingTekst(this.getRegel().getOmschrijving())
                .build();
            afleidingResultaat.voegMeldingToe(resultaatMelding);
        }
        return afleidingResultaat;
    }

}
