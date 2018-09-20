/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.deelnameeuverkiezingen;

import nl.bzk.brp.bijhouding.business.regels.AbstractAfleidingsregel;
import nl.bzk.brp.bijhouding.business.regels.AfleidingResultaat;
import nl.bzk.brp.bijhouding.business.stappen.resultaat.ResultaatMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonDeelnameEUVerkiezingenModel;

/**
 * VR00016a: Afgeleide beeindiging Deelname EU verkiezingen door Emigratie.
 *
 * Als een actuele groep Migratie van de Soort "Emigratie" wordt geregistreerd terwijl DeelnameEUverkiezingen gewenst is
 * (Deelname="Ja"), dan komt die deelname als volgt afgeleid te vervallen o.v.v. melding [1]:
 * TijdstipVerval := Migratie.TijdstipRegistratie
 * ActieVerval = Migratie.ActieInhoud.
 */
public class DeelnameEUVerkiezingAfleidingDoorEmigratie extends AbstractAfleidingsregel<PersoonHisVolledig> {

    /**
     * Constructor.
     *
     * @param model het model
     * @param actie de actie
     */
    public DeelnameEUVerkiezingAfleidingDoorEmigratie(final PersoonHisVolledig model,
                                                      final ActieModel actie)
    {
        super(model, actie);
    }

    @Override
    public AfleidingResultaat leidAf() {
        final HisPersoonDeelnameEUVerkiezingenModel actueleDeelnameEuVerkiezingen =
                getModel().getPersoonDeelnameEUVerkiezingenHistorie().getActueleRecord();

        boolean afgeleidVervallen = false;
        if (actueleDeelnameEuVerkiezingen != null
                && actueleDeelnameEuVerkiezingen.getIndicatieDeelnameEUVerkiezingen().getWaarde())
        {
            // Laat het record vervallen nav de huidige 'veroorzakende' actie.
            getModel().getPersoonDeelnameEUVerkiezingenHistorie().verval(
                    getActie(), getActie().getTijdstipRegistratie());
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

    @Override
    public final Regel getRegel() {
        return Regel.VR00016a;
    }
}
