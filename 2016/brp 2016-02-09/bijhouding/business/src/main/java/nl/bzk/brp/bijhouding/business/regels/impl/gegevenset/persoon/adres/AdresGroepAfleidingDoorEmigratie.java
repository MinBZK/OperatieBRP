/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.adres;

import nl.bzk.brp.bijhouding.business.regels.AbstractAfleidingsregel;
import nl.bzk.brp.bijhouding.business.regels.AfleidingResultaat;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonAdresHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonMigratieModel;

/**
 * VR00013b: Afgeleide beeindiging Adres bij Emigratie.
 *
 * Als voor een Persoon een actuele groep Migratie wordt geregistreerd van de Soort "Emigratie" terwijl er een actueel
 * Adres staat geregistreerd , dan wordt dat Adres als volgt afgeleid beeindigd:
 * DatumEindeGeldigheid := Migratie.DatumAanvangGeldigheid
 */
public class AdresGroepAfleidingDoorEmigratie extends AbstractAfleidingsregel<PersoonHisVolledigImpl> {

    /**
     * Constructor.
     *
     * @param model het model
     * @param actie de actie
     */
    public AdresGroepAfleidingDoorEmigratie(final PersoonHisVolledigImpl model, final ActieModel actie) {
        super(model, actie);
    }

    @Override
    public final AfleidingResultaat leidAf() {
        final HisPersoonMigratieModel actueleMigratie = getModel().getPersoonMigratieHistorie().getActueleRecord();
        final PersoonAdresHisVolledigImpl adres = getModel().getAdressen().iterator().next();
        adres.getPersoonAdresHistorie().beeindig(actueleMigratie, actueleMigratie.getVerantwoordingInhoud());
        return GEEN_VERDERE_AFLEIDINGEN;
    }

    @Override
    public final Regel getRegel() {
        return Regel.VR00013b;
    }
}
