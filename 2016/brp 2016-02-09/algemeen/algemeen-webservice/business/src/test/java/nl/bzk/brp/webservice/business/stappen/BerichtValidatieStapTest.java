/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.webservice.business.stappen;

import java.util.Collections;
import javax.validation.constraints.NotNull;
import nl.bzk.brp.business.regels.BedrijfsregelManager;
import nl.bzk.brp.business.stappen.StappenContext;
import nl.bzk.brp.business.stappen.StappenResultaat;
import nl.bzk.brp.model.RegelParameters;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.MeldingtekstAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.BurgerservicenummerAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortMelding;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.DatabaseObjectKern;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Regel;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.bericht.ber.BerichtStandaardGroepBericht;
import nl.bzk.brp.model.bericht.kern.ActieBericht;
import nl.bzk.brp.model.bericht.kern.ActieRegistratieAfnemerindicatieBericht;
import nl.bzk.brp.model.bericht.kern.AdministratieveHandelingBericht;
import nl.bzk.brp.model.bericht.kern.HandelingPlaatsingAfnemerindicatieBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bericht.kern.PersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.synchronisatie.RegistreerAfnemerindicatieBericht;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public class BerichtValidatieStapTest {
    private AbstractBerichtValidatieStap abstractBerichtValidatieStap;
    private BerichtVerwerkingsResultaat verwerkingsResultaat;

    @Mock
    private BedrijfsregelManager bedrijfsregelManager;

    @Before
    public void setUp() {
        abstractBerichtValidatieStap = new AbstractBerichtValidatieStap() {
            @Override
            public boolean voerStapUit(final BrpObject onderwerp, final StappenContext context,
                                       final StappenResultaat resultaat)
            {
                verwerkingsResultaat = new BerichtVerwerkingsResultaatImpl();
                valideer(onderwerp, verwerkingsResultaat);

                return true;
            }
        };

        ReflectionTestUtils.setField(abstractBerichtValidatieStap, "bedrijfsregelManager", bedrijfsregelManager);
    }

    @Test
    public void kanStapUitvoeren() {
        // arrange
        final BrpObject object = new ValidatieModel("Foo");
        // act
        abstractBerichtValidatieStap.voerStapUit(object, null, null);
    }

    @Test
    public void kanStapUitvoerenMetInvalideObject() {
        // arrange
        final BrpObject object = new ValidatieModel(null);

        final RegelParameters parameters =
                new RegelParameters(new MeldingtekstAttribuut("Melding"), SoortMelding.INFORMATIE, Regel.ALG0001,
                                    DatabaseObjectKern.DATABASE_OBJECT);
        Mockito.when(bedrijfsregelManager.getRegelParametersVoorRegel(Mockito.any(Regel.class))).thenReturn(parameters);

        // act
        abstractBerichtValidatieStap.voerStapUit(object, null, null);
    }

    @Test
    public void valideerdInvalideBsnInAdministratieveHandeling() {
        // arrange
        final BurgerservicenummerAttribuut burgerservicenummerAttribuut = new BurgerservicenummerAttribuut(423342564);
        final PersoonIdentificatienummersGroepBericht persoonIdentificatienummersGroepBericht = new PersoonIdentificatienummersGroepBericht();
        persoonIdentificatienummersGroepBericht.setBurgerservicenummer(burgerservicenummerAttribuut);
        final PersoonBericht persoonBericht = new PersoonBericht();
        persoonBericht.setIdentificatienummers(persoonIdentificatienummersGroepBericht);
        final ActieBericht actieBericht = new ActieRegistratieAfnemerindicatieBericht();
        actieBericht.setRootObject(persoonBericht);
        final AdministratieveHandelingBericht administratieveHandelingBericht = new HandelingPlaatsingAfnemerindicatieBericht();
        administratieveHandelingBericht.setActies(Collections.singletonList(actieBericht));
        final BerichtStandaardGroepBericht berichtStandaardGroepBericht = new BerichtStandaardGroepBericht();
        berichtStandaardGroepBericht.setAdministratieveHandeling(administratieveHandelingBericht);
        final RegistreerAfnemerindicatieBericht registreerAfnemerindicatieBericht = new RegistreerAfnemerindicatieBericht();
        registreerAfnemerindicatieBericht.setStandaard(berichtStandaardGroepBericht);

        final RegelParameters parameters =
            new RegelParameters(new MeldingtekstAttribuut("Melding"), SoortMelding.INFORMATIE, Regel.ALG0001,
                DatabaseObjectKern.DATABASE_OBJECT);
        Mockito.when(bedrijfsregelManager.getRegelParametersVoorRegel(Mockito.any(Regel.class))).thenReturn(parameters);

        ReflectionTestUtils.setField(abstractBerichtValidatieStap, "bedrijfsregelManager", bedrijfsregelManager);
        // act
        abstractBerichtValidatieStap.voerStapUit(registreerAfnemerindicatieBericht, null, null);

        assert (verwerkingsResultaat.getMeldingen().size() == 1);
        assert (Regel.BRAL0012.equals(verwerkingsResultaat.getMeldingen().iterator().next().getRegel()));
    }

    class ValidatieModel implements BrpObject {
        @NotNull
        private String naam;

        public ValidatieModel(final String naam) {
            this.naam = naam;
        }

        public String getNaam() {
            return naam;
        }

        public void setNaam(final String naam) {
            this.naam = naam;
        }
    }
}
