/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.serialisatie.persoon;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.hisvolledig.impl.kern.BetrokkenheidHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonOnderzoekHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.RelatieHisVolledigImpl;


/**
 * Implementatie van een Jackson {@link com.fasterxml.jackson.databind.Module}, om extra configuratie van de mapping te kunnen registreren.
 */
public final class PersoonHisVolledigMappingConfiguratieModule extends SimpleModule {

    /**
     * Default constructor, registreert deze module.
     */
    public PersoonHisVolledigMappingConfiguratieModule() {
        super("PersoonHisVolledigMappingConfiguratieModule", new Version(0, 1, 0, null, "nl.bzk.brp", "model"));
    }

    @Override
    public void setupModule(final SetupContext context) {
        context.setMixInAnnotations(RelatieHisVolledigImpl.class, RelatieHisVolledigMixin.class);

        context.setMixInAnnotations(PersoonHisVolledigImpl.class, PersoonHisVolledigMixin.class);
        context.setMixInAnnotations(BetrokkenheidHisVolledigImpl.class, BetrokkenheidHisVolledigMixin.class);
        context.setMixInAnnotations(PersoonOnderzoekHisVolledigImpl.class, PersoonOnderzoekHisVolledigMixin.class);

        // stamgegevens
        context.setMixInAnnotations(Partij.class, PartijMixin.class);

    }
}
