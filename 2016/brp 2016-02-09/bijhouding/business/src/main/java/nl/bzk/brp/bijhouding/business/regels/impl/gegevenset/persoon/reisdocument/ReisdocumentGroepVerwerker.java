/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.reisdocument;


import nl.bzk.brp.bijhouding.business.regels.AbstractVerwerkingsregel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.bericht.kern.PersoonReisdocumentBericht;
import nl.bzk.brp.model.bericht.kern.PersoonReisdocumentStandaardGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonReisdocumentHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonReisdocumentModel;

/**
 * Verwerken Groep Reisdocument.
 *
 * VR00020
 */
public class ReisdocumentGroepVerwerker extends AbstractVerwerkingsregel<PersoonReisdocumentBericht,
        PersoonReisdocumentHisVolledigImpl>
{

    /**
     * Constructor.
     *
     * @param bericht het bericht object
     * @param model   het model object
     * @param actie   de actie
     */
    public ReisdocumentGroepVerwerker(final PersoonReisdocumentBericht bericht,
                                      final PersoonReisdocumentHisVolledigImpl model,
                                      final ActieModel actie)
    {
        super(bericht, model, actie);
    }

    @Override
    public final Regel getRegel() {
        return Regel.VR00020;
    }

    @Override
    public final void neemBerichtDataOverInModel() {
        final HisPersoonReisdocumentModel reisdocumentGroep;

        if (this.getActie().getAdministratieveHandeling().getSoort().getWaarde()
                == SoortAdministratieveHandeling.ONTTREKKING_REISDOCUMENT)
        {
            reisdocumentGroep = maakReisdocumentModelVoorOnttrekking();
        } else {
            // Neem de groep over zoals in het bericht aanwezig.
            reisdocumentGroep = new HisPersoonReisdocumentModel(getModel(), getBericht().getStandaard(), getActie());
        }

        getModel().getPersoonReisdocumentHistorie().voegToe(reisdocumentGroep);
    }

    /**
     * Maakt een His Persoon Reisdocument Model voor onttrekking reisdocument.
     *
     * @return His Persoon Reisdocument Model
     */
    private HisPersoonReisdocumentModel maakReisdocumentModelVoorOnttrekking() {
        final HisPersoonReisdocumentModel actueleReisdocumentGroep = getModel().getPersoonReisdocumentHistorie().getActueleRecord();
        final PersoonReisdocumentStandaardGroepBericht berichtReisdocumentGroep = getBericht().getStandaard();

        return new HisPersoonReisdocumentModel(getModel(),
                // Bestaande info uit meest recente record overnemen.
                actueleReisdocumentGroep.getNummer(),
                actueleReisdocumentGroep.getAutoriteitVanAfgifte(),
                actueleReisdocumentGroep.getDatumIngangDocument(),
                actueleReisdocumentGroep.getDatumEindeDocument(),
                actueleReisdocumentGroep.getDatumUitgifte(),
                // Onttrokken info uit bericht overnemen.
                berichtReisdocumentGroep.getDatumInhoudingVermissing(),
                berichtReisdocumentGroep.getAanduidingInhoudingVermissing(),
                // De actie
                getActie());
    }

}
