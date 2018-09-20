/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.nationaliteit;


import nl.bzk.brp.bijhouding.business.regels.AbstractVerwerkingsregel;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.behandeldalsnederlander.BeeindigingBehandeldAlsNederlanderAfleiding;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.deelnameeuverkiezingen.DeelnameEUVerkiezingAfleidingDoorNederlanderschap;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.staatloos.BeeindigingStaatloosAfleiding;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.vastgesteldnietnederlander.BeeindigingVastgesteldNietNederlanderAfleiding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.bericht.kern.PersoonNationaliteitBericht;
import nl.bzk.brp.model.bericht.kern.PersoonNationaliteitStandaardGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonNationaliteitHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonNationaliteitModel;

/**
 * Verwerken Groep Nationaliteit.
 *
 * VR00004
 */
public class NationaliteitVerwerker extends AbstractVerwerkingsregel<PersoonNationaliteitBericht,
        PersoonNationaliteitHisVolledigImpl>
{

    private HisPersoonNationaliteitModel toegevoegdHisRecord;

    /**
     * Constructor.
     *
     * @param bericht het bericht object
     * @param model   het model object
     * @param actie   de actie
     */
    public NationaliteitVerwerker(final PersoonNationaliteitBericht bericht,
                                  final PersoonNationaliteitHisVolledigImpl model,
                                  final ActieModel actie)
    {
        super(bericht, model, actie);
    }

    @Override
    public final Regel getRegel() {
        return Regel.VR00004;
    }

    @Override
    public final void verrijkBericht() {
        //Let op: Indien er geen redenVerkrijgingNLCode aanwezig is, bijvoorbeeld bij een Griekse Nationaliteit
        //dan is er geen standaard groep aanwezig. Deze moeten we echter wel aanmaken, om de historie bij te houden.
        if (getBericht().getStandaard() == null) {
            getBericht().setStandaard(new PersoonNationaliteitStandaardGroepBericht());
            getBericht().getStandaard().setDatumAanvangGeldigheid(getActie().getDatumAanvangGeldigheid());
            getBericht().getStandaard().setDatumEindeGeldigheid(getActie().getDatumEindeGeldigheid());
        }
    }

    @Override
    public final void neemBerichtDataOverInModel() {
        this.toegevoegdHisRecord = new HisPersoonNationaliteitModel(getModel(), getBericht().getStandaard(),
                getBericht().getStandaard(), getActie());
        getModel().getPersoonNationaliteitHistorie().voegToe(this.toegevoegdHisRecord);
    }

    @Override
    public final void verzamelAfleidingsregels() {
        // Een registratie nationaliteit kan aanleiding zijn om enkele indicaties te beëindigen.
        this.voegAfleidingsregelToe(new BeeindigingStaatloosAfleiding(
                getModel().getPersoon(), getActie(), this.toegevoegdHisRecord));
        this.voegAfleidingsregelToe(new BeeindigingVastgesteldNietNederlanderAfleiding(
                getModel().getPersoon(), getActie(), this.toegevoegdHisRecord));
        this.voegAfleidingsregelToe(new BeeindigingBehandeldAlsNederlanderAfleiding(
                getModel().getPersoon(), getActie(), this.toegevoegdHisRecord));
        // Een registratie nationaliteit kan aanleiding zijn om enkele groepen te laten vervallen.
        this.voegAfleidingsregelToe(new DeelnameEUVerkiezingAfleidingDoorNederlanderschap(
                getModel().getPersoon(), getActie(), this.toegevoegdHisRecord));
    }
}
