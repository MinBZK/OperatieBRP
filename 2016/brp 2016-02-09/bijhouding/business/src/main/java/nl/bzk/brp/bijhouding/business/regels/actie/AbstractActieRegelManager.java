/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.actie;

import java.util.Arrays;
import java.util.List;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.bericht.request.BRBY9910;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.betrokkenheid.identiteitbetrokkenheid.BRAL2010;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.object.materielehistorie.BRAL2203;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.geslachtsnaamcomponent.BRBY0183;
import nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.samengesteldenaam.BRAL0502;
import nl.bzk.brp.business.regels.NaActieRegel;
import nl.bzk.brp.business.regels.VoorActieRegel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;

/**
 * ActieRegelManager met default implementaties van de methoden.
 */
public abstract class AbstractActieRegelManager implements ActieRegelManager {

    /**
     * De VoorActieRegels die voor elke RegelManager gelden.
     */
    protected static final List<Class<? extends VoorActieRegel>> ALGEMENE_VOOR_ACTIE_REGELS = Arrays
        .<Class<? extends VoorActieRegel>>asList(BRAL2010.class, BRAL2203.class, BRBY0183.class, BRBY9910.class);

    /**
     * De NaActieRegels die voor elke RegelManager gelden.
     */
    protected static final List<Class<? extends NaActieRegel>> ALGEMENE_NA_ACTIE_REGELS = Arrays
        .<Class<? extends NaActieRegel>>asList(BRAL0502.class);

    /**
     * Geeft de default regels die voor een actie moeten worden uitgevoerd.
     * @param soortAdministratieveHandeling wordt niet gebruikt in deze default implementatie
     * @return de lijst van algemene VoorActieRegel types
     */
    @Override
    public List<Class<? extends VoorActieRegel>> getVoorActieRegels(final SoortAdministratieveHandeling soortAdministratieveHandeling) {
        return ALGEMENE_VOOR_ACTIE_REGELS;
    }

    /**
     * Geeft de default regels die na een actie moeten worden uitgevoerd.
     * @param soortAdministratieveHandeling wordt niet gebruikt in deze default implementatie
     * @return de lijst van algemene NaActieRegel types
     */
    @Override
    public List<Class<? extends NaActieRegel>> getNaActieRegels(final SoortAdministratieveHandeling soortAdministratieveHandeling) {
        return ALGEMENE_NA_ACTIE_REGELS;
    }
}
