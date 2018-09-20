/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.migratie;

import nl.bzk.brp.bijhouding.business.regels.AbstractVerwerkingsregel;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.adres.AdresGroepAfleidingDoorEmigratie;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.bijhouding.BijhoudingAfleidingDoorEmigratie;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.deelnameeuverkiezingen.DeelnameEUVerkiezingAfleidingDoorEmigratie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortMigratie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortMigratieAttribuut;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonMigratieGroepBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.HisPersoonMigratieModel;

/**
 * VR00028: Verwerken Groep Migratie.
 */
public class MigratieGroepVerwerker extends AbstractVerwerkingsregel<PersoonBericht, PersoonHisVolledigImpl>
{

    /**
     * Constructor.
     *
     * @param persoonBericht het bericht object
     * @param persoonHisVolledig het model object
     * @param actieModel de actie
     */
    public MigratieGroepVerwerker(final PersoonBericht persoonBericht, final PersoonHisVolledigImpl persoonHisVolledig,
                                  final ActieModel actieModel)
    {
        super(persoonBericht, persoonHisVolledig, actieModel);
    }

    @Override
    public final void verrijkBericht() {
        // De soort migratie wordt automatisch gezet nav de soort handeling waar we mee bezig zijn.
        final SoortAdministratieveHandeling soortHandeling = getActie().getAdministratieveHandeling().getSoort().getWaarde();
        if (soortHandeling == SoortAdministratieveHandeling.VERHUIZING_NAAR_BUITENLAND) {
            getBericht().getMigratie().setSoortMigratie(new SoortMigratieAttribuut(SoortMigratie.EMIGRATIE));
        } else if (soortHandeling == SoortAdministratieveHandeling.VESTIGING_NIET_INGESCHREVENE) {
            getBericht().getMigratie().setSoortMigratie(new SoortMigratieAttribuut(SoortMigratie.IMMIGRATIE));
        } else {
            throw new IllegalStateException("Onverwachte soort administratieve handeling bij "
                    + "verwerken migratie groep: " + soortHandeling);
        }
    }

    @Override
    public final void neemBerichtDataOverInModel() {
        final PersoonMigratieGroepBericht migratieBericht = getBericht().getMigratie();
        final SoortMigratie srtMigratie;
        // In een VerhuizingNaarBuitenland en WijzigingBijzondereVerblijfsrechtelijkePositie wordt Soort gevuld met
        // de waarde "Emigratie", anders met "Immigratie"
        final SoortAdministratieveHandeling srtAdmHnd = getActie().getAdministratieveHandeling().getSoort().getWaarde();
        if (SoortAdministratieveHandeling.VERHUIZING_NAAR_BUITENLAND == srtAdmHnd) {
            srtMigratie = SoortMigratie.EMIGRATIE;
        } else {
            throw new UnsupportedOperationException("Migratie wordt voorlopig alleen nog ondersteund met"
                                                            + " de handeling \"Verhuizing naar buitenland\".");
        }

        final HisPersoonMigratieModel nieuweMigratieGroep = new HisPersoonMigratieModel(
                getModel(), new SoortMigratieAttribuut(srtMigratie),
                migratieBericht.getRedenWijzigingMigratie(),
                migratieBericht.getAangeverMigratie(),
                migratieBericht.getLandGebiedMigratie(),
                migratieBericht.getBuitenlandsAdresRegel1Migratie(),
                migratieBericht.getBuitenlandsAdresRegel2Migratie(),
                migratieBericht.getBuitenlandsAdresRegel3Migratie(),
                migratieBericht.getBuitenlandsAdresRegel4Migratie(),
                migratieBericht.getBuitenlandsAdresRegel5Migratie(),
                migratieBericht.getBuitenlandsAdresRegel6Migratie(),
                getActie(),
                migratieBericht
        );

        getModel().getPersoonMigratieHistorie().voegToe(nieuweMigratieGroep);
    }

    @Override
    public final void verzamelAfleidingsregels() {
        final HisPersoonMigratieModel actueleMigratie = getModel().getPersoonMigratieHistorie().getActueleRecord();
        //Is het zojuist toegevoegde record een EMIGRATIE?
        if (SoortMigratie.EMIGRATIE == actueleMigratie.getSoortMigratie().getWaarde()) {
            // VR00015b: Afgeleide registratie Bijhouding door Emigratie.
            voegAfleidingsregelToe(new BijhoudingAfleidingDoorEmigratie(getModel(), getActie()));
            //VR00013b: Afgeleide beeindiging Adres bij Emigratie.
            voegAfleidingsregelToe(new AdresGroepAfleidingDoorEmigratie(getModel(), getActie()));
            //VR00016a: Afgeleide beeindiging Deelname EU verkiezingen door Emigratie.
            voegAfleidingsregelToe(new DeelnameEUVerkiezingAfleidingDoorEmigratie(getModel(), getActie()));
        }
    }

    @Override
    public final Regel getRegel() {
        return Regel.VR00028;
    }
}
