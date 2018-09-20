/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.adres;

import nl.bzk.brp.bijhouding.business.regels.AbstractVerwerkingsregel;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.bijhouding.BijhoudingAfleidingDoorVerhuizing;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.bericht.kern.PersoonAdresBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonAdresHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonAdresModel;

/**
 * Verwerkingsregel voor registratie adres.
 * VR00013: Verwerker voor de standaard groep van Adres
 */
public class AdresGroepVerwerker extends AbstractVerwerkingsregel<PersoonAdresBericht, PersoonAdresHisVolledigImpl> {

    /**
     * Constructor.
     *
     * @param bericht het bericht object
     * @param model   het model object
     * @param actie   de actie
     */
    public AdresGroepVerwerker(final PersoonAdresBericht bericht,
                               final PersoonAdresHisVolledigImpl model,
                               final ActieModel actie)
    {
        super(bericht, model, actie);
    }

    @Override
    public final Regel getRegel() {
        return Regel.VR00013;
    }

    @Override
    public final void verrijkBericht() {
        final HisPersoonAdresModel vorigAdres = getModel().getPersoonAdresHistorie().getActueleRecord();
        final SoortAdministratieveHandeling soortAdmHand = getActie().getAdministratieveHandeling().getSoort().getWaarde();
        // Verrijkt het adres in het bericht met gegevens die niet meekomen in het bericht. Dit zijn gemeente en land.
        switch (soortAdmHand) {
            case CORRECTIE_ADRES:
                // Geen verrijking meer nodig, land is verplicht in bericht.
                break;
            case VERHUIZING_BINNENGEMEENTELIJK:
                getBericht().getStandaard().setLandGebied(vorigAdres.getLandGebied());
                getBericht().getStandaard().setGemeente(vorigAdres.getGemeente());
                break;
            case VERHUIZING_INTERGEMEENTELIJK:
                getBericht().getStandaard().setLandGebied(vorigAdres.getLandGebied());
                break;
            default:
                throw new UnsupportedOperationException(
                        "Adres groep verwerker VR00013 ondersteunt soort administratieve handeling niet: "
                                + soortAdmHand);
        }
    }

    @Override
    public final void neemBerichtDataOverInModel() {
        final HisPersoonAdresModel nieuwAdres = new HisPersoonAdresModel(getModel(), getBericht().getStandaard(),
                                                                         getBericht().getStandaard(), getActie());
        getModel().getPersoonAdresHistorie().voegToe(nieuwAdres);
    }

    @Override
    public final void verzamelAfleidingsregels() {
        // Bij de registratie van een Adres, wordt de groep Bijhouding zo nodig afgeleid geregistreerd.
        // BR00015c
        voegAfleidingsregelToe(new BijhoudingAfleidingDoorVerhuizing(getModel().getPersoon(), getActie()));
    }

}
