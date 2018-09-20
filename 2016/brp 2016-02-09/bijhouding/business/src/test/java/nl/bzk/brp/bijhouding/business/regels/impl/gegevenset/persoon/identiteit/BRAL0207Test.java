/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.persoon.identiteit;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import nl.bzk.brp.bijhouding.business.stappen.context.BijhoudingBerichtContext;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoonAttribuut;
import nl.bzk.brp.model.basis.BerichtEntiteit;
import nl.bzk.brp.model.basis.BerichtIdentificeerbaar;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingBericht;
import nl.bzk.brp.model.bericht.kern.BetrokkenheidBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.RelatieBericht;
import nl.bzk.brp.model.bijhouding.RegistreerVerhuizingBericht;
import nl.bzk.brp.model.hisvolledig.impl.kern.OuderHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BRAL0207Test {

    private BRAL0207 bral0207;

    private RegistreerVerhuizingBericht bericht = new RegistreerVerhuizingBericht();
    @Mock
    private AdministratieveHandelingBericht administratieveHandeling;
    @Mock
    private ActieBericht                    actie1;
    @Mock
    private RelatieBericht                  relatie1;
    @Mock
    private BetrokkenheidBericht            betrokkenheid1;

    private PersoonBericht persoon1 = new PersoonBericht();
    @Mock
    private BetrokkenheidBericht betrokkenheid2;

    private PersoonBericht persoon2 = new PersoonBericht();
    @Mock
    private ActieBericht actie2;

    private PersoonBericht persoon3 = new PersoonBericht();
    private List<PersoonHisVolledigImpl> bijgehoudenPersonen;
    private List<BerichtEntiteit> nietIngeschrevenen;
    @Mock
    private BijhoudingBerichtContext     context;

    /**
     * Bericht heeft adm.hand., heeft 2 acties: actie1 en actie2. Actie1 heeft als root object relatie1, die als betrokken personen heeft persoon1 en
     * persoon2 Actie2 heeft als root object persoon3.
     */
    @Before
    public void init() {
        this.bral0207 = new BRAL0207();
        bericht.getStandaard().setAdministratieveHandeling(administratieveHandeling);
        when(this.administratieveHandeling.getActies()).thenReturn(Arrays.asList(this.actie1, this.actie2));
        when(this.actie1.getRootObject()).thenReturn(this.relatie1);
        when(this.relatie1.getBetrokkenheden())
            .thenReturn(Arrays.asList(this.betrokkenheid1, this.betrokkenheid2));
        when(this.betrokkenheid1.getPersoon()).thenReturn(this.persoon1);
        when(this.betrokkenheid2.getPersoon()).thenReturn(this.persoon2);
        when(this.actie2.getRootObject()).thenReturn(this.persoon3);
        this.bijgehoudenPersonen = new ArrayList<>();
        when(this.context.getBijgehoudenPersonen(Mockito.anyBoolean())).thenReturn(this.bijgehoudenPersonen);
        nietIngeschrevenen = new ArrayList<>();
    }

    @Test
    public void testGetRegel() {
        assertEquals(Regel.BRAL0207, this.bral0207.getRegel());
    }

    @Test
    public void testVoerRegelUit() {
        final PersoonBericht persoonBericht = new PersoonBericht();
        nietIngeschrevenen.add(persoonBericht);
        final List<BerichtIdentificeerbaar> overtredingen = bral0207.voerRegelUit(bericht, nietIngeschrevenen);
        assertEquals(1, overtredingen.size());
        assertEquals(persoonBericht, overtredingen.get(0));
    }

    @Test
    public void testVoerRegelUitMetNietGevondenPersoon() {
        nietIngeschrevenen.add(null);
        final List<BerichtIdentificeerbaar> overtredingen = bral0207.voerRegelUit(bericht, nietIngeschrevenen);
        assertEquals(1, overtredingen.size());
        assertEquals(bericht.getAdministratieveHandeling(), overtredingen.get(0));
    }

    @Test
    public void testGeenNietIngeschrevenen() {
        bijgehoudenPersonen.add(maakPersoon(SoortPersoon.INGESCHREVENE, 1));
        bijgehoudenPersonen.add(maakPersoon(SoortPersoon.INGESCHREVENE, 2));
        bijgehoudenPersonen.add(maakPersoon(SoortPersoon.INGESCHREVENE, 3));

        final List<BerichtIdentificeerbaar> resultaat = this.bral0207.voerRegelUit(this.bericht, nietIngeschrevenen);

        assertEquals(0, resultaat.size());
    }

    @Test
    public void testAlleenGoedeNietIngeschrevenen() {
        bijgehoudenPersonen.add(maakPersoon(SoortPersoon.INGESCHREVENE, 1));
        bijgehoudenPersonen.add(maakPersoon(SoortPersoon.INGESCHREVENE, 2));
        bijgehoudenPersonen.add(maakPersoon(SoortPersoon.INGESCHREVENE, 3));
        bijgehoudenPersonen.add(maakPersoon(SoortPersoon.NIET_INGESCHREVENE, 1));
        bijgehoudenPersonen.add(maakPersoon(SoortPersoon.NIET_INGESCHREVENE, 1));

        final List<BerichtIdentificeerbaar> resultaat = this.bral0207.voerRegelUit(this.bericht, nietIngeschrevenen);

        assertEquals(0, resultaat.size());
    }

    @Test
    @Ignore
    public void testFouteNietIngeschrevenen() {
        bijgehoudenPersonen.add(maakPersoon(SoortPersoon.INGESCHREVENE, 1));
        bijgehoudenPersonen.add(maakPersoon(SoortPersoon.INGESCHREVENE, 2));
        bijgehoudenPersonen.add(maakPersoon(SoortPersoon.INGESCHREVENE, 3));
        final PersoonHisVolledigImpl persoonHisVolledig1 = maakPersoon(SoortPersoon.NIET_INGESCHREVENE, 2);
        when(this.context.zoekHisVolledigRootObject(persoon1)).thenReturn(persoonHisVolledig1);
        bijgehoudenPersonen.add(persoonHisVolledig1);
        final PersoonHisVolledigImpl persoonHisVolledig2 = maakPersoon(SoortPersoon.NIET_INGESCHREVENE, 3);
        bijgehoudenPersonen.add(persoonHisVolledig2);
        when(this.context.zoekHisVolledigRootObject(persoon2)).thenReturn(null);
        final PersoonHisVolledigImpl persoonHisVolledig3 = maakPersoon(SoortPersoon.NIET_INGESCHREVENE, 9);
        bijgehoudenPersonen.add(persoonHisVolledig3);
        when(this.context.zoekHisVolledigRootObject(persoon3)).thenReturn(persoonHisVolledig3);

        final List<BerichtIdentificeerbaar> resultaat = this.bral0207.voerRegelUit(this.bericht, nietIngeschrevenen);

        assertEquals(3, resultaat.size());
        // Persoon 1 wordt wel gevonden op de context.
        assertEquals(resultaat.get(0), persoon1);
        // Persoon 2 wordt niet gevonden op de context, dus adm.hand. als fout object.
        assertEquals(resultaat.get(1), administratieveHandeling);
        // Persoon 3 wordt wel gevonden op de context.
        assertEquals(resultaat.get(2), persoon3);
    }

    private PersoonHisVolledigImpl maakPersoon(final SoortPersoon soortPersoon, final int aantalBetrokkenheden) {
        final PersoonHisVolledigImpl persoon = new PersoonHisVolledigImpl(new SoortPersoonAttribuut(soortPersoon));
        for (int i = 0; i < aantalBetrokkenheden; i++) {
            persoon.getBetrokkenheden().add(new OuderHisVolledigImpl(null, persoon));
        }
        return persoon;
    }

}
