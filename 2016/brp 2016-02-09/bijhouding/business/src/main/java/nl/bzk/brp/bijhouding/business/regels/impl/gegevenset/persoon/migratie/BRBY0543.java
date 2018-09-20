/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.migratie;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.inject.Named;
import nl.bzk.brp.business.regels.VoorActieRegelMetMomentopname;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.AdresregelAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.LandGebiedCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortMigratie;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonMigratieGroepBericht;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.brp.model.logisch.kern.Actie;

/**
 * BRBY0543: Buitenlands adres mag alleen bij emigratie naar bekend land.
 * @brp.bedrijfsregel BRBY0543
 */
@Named("BRBY0543")
public class BRBY0543 implements VoorActieRegelMetMomentopname<PersoonView, PersoonBericht> {

    @Override
    public List<BerichtEntiteit> voerRegelUit(final PersoonView persoonView, final PersoonBericht persoonBericht,
                                              final Actie actie,
                                              final Map<String, PersoonView> stringPersoonViewMap)
    {
        final List<BerichtEntiteit> objectenDieDeRegelOvertreden = new ArrayList<>();
        if (SoortMigratie.EMIGRATIE == persoonBericht.getMigratie().getSoortMigratie().getWaarde()
                && minstensEenBuitenlandseAdresRegelIsGevuld(persoonBericht.getMigratie())
                && landGebiedMigratieIsOnbekend(persoonBericht.getMigratie()))
        {
            objectenDieDeRegelOvertreden.add(persoonBericht);
        }
        return objectenDieDeRegelOvertreden;
    }

    /**
     * Controleert of land gebied migratie onbekend is.
     * @param migratie de te controleren migratie
     * @return true indien land gebied onbekend is anders false
     */
    private boolean landGebiedMigratieIsOnbekend(final PersoonMigratieGroepBericht migratie) {
        return migratie.getLandGebiedMigratie() == null
                || migratie.getLandGebiedMigratie().getWaarde().getCode().equals(LandGebiedCodeAttribuut.ONBEKEND);
    }

    /**
     * Controleert of minstens een buitenlands adres regel is gevuld in de groep migratie.
     * @param migratie de migratie groep.
     * @return true indien minstens 1 regel gevuld is anders false
     */
    private boolean minstensEenBuitenlandseAdresRegelIsGevuld(final PersoonMigratieGroepBericht migratie) {
        final AdresregelAttribuut[] buitenlandsAdresRegelAttributen = new AdresregelAttribuut[]{
            migratie.getBuitenlandsAdresRegel1Migratie(),
            migratie.getBuitenlandsAdresRegel2Migratie(),
            migratie.getBuitenlandsAdresRegel3Migratie(),
            migratie.getBuitenlandsAdresRegel4Migratie(),
            migratie.getBuitenlandsAdresRegel5Migratie(),
            migratie.getBuitenlandsAdresRegel6Migratie(),
        };
        for (AdresregelAttribuut adresregelAttribuut : buitenlandsAdresRegelAttributen) {
            if (adresregelAttribuut != null) {
                return true;
            }
        }
        return false;
    }

    @Override
    public final Regel getRegel() {
        return Regel.BRBY0543;
    }
}
