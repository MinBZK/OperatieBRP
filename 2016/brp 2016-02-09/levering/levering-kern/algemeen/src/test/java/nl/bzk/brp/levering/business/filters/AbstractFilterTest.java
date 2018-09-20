/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.filters;

import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienstbundel;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.EffectAfnemerindicaties;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestDienstBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestDienstbundelBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestLeveringsautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestToegangLeveringautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangLeveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijRol;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestPartijBuilder;


public abstract class AbstractFilterTest {

    protected Leveringinformatie maakLeveringinformatie(final SoortDienst optie, final Integer leveringsautorisatieId) {
        return maakLeveringinformatieImpl(optie, leveringsautorisatieId, null, EffectAfnemerindicaties.DUMMY);
    }

    protected Leveringinformatie maakLeveringinformatie(final SoortDienst optie, final Integer leveringsautorisatieId,
                                                        final String attenderingsCriterium, final EffectAfnemerindicaties effectAfnemerindicaties)
    {
        return maakLeveringinformatieImpl(optie, leveringsautorisatieId, attenderingsCriterium, effectAfnemerindicaties);
    }

    private Leveringinformatie maakLeveringinformatieImpl(final SoortDienst soortDienst, final Integer leveringsautorisatieId,
            final String attenderingsCriterium, final EffectAfnemerindicaties metEffectPlaatsenAfnemerindicatie)
    {
        final Partij partij = TestPartijBuilder.maker().metNaam("PartijNaam").metCode(1238).maak();

        final Leveringsautorisatie la = TestLeveringsautorisatieBuilder.maker().
            metId(leveringsautorisatieId).metNaam("Naam").metPopulatiebeperking("WAAR").maak();


        final Dienst dienst = TestDienstBuilder.maker().metSoortDienst(soortDienst).metAttenderingscriterium(attenderingsCriterium).
            metEffectAfnemerindicaties(metEffectPlaatsenAfnemerindicatie).maak();
        final Dienstbundel dienstbundel = TestDienstbundelBuilder.maker().metDiensten(dienst).maak();
        la.setDienstbundels(dienstbundel);

        final PartijRol partijRol = new PartijRol(partij, null, null, null);
        final ToegangLeveringsautorisatie tla = TestToegangLeveringautorisatieBuilder.maker().metLeveringsautorisatie(la).metGeautoriseerde(partijRol).maak();
        return new Leveringinformatie(tla, dienst);
    }
}
