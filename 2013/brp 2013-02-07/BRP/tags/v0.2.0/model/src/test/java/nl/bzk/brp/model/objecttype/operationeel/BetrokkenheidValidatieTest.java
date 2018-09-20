/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.operationeel;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;

import junit.framework.Assert;
import nl.bzk.brp.model.attribuuttype.Ja;
import nl.bzk.brp.model.groep.bericht.BetrokkenheidOuderschapGroepBericht;
import nl.bzk.brp.model.groep.bericht.RelatieStandaardGroepBericht;
import nl.bzk.brp.model.objecttype.bericht.BetrokkenheidBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.bericht.RelatieBericht;
import nl.bzk.brp.model.objecttype.logisch.Persoon;
import nl.bzk.brp.util.DatumUtil;
import nl.bzk.brp.util.PersoonBuilder;
import nl.bzk.brp.util.RelatieBuilder;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test klasse voor de {@link Melding} klasse.
 */
public class BetrokkenheidValidatieTest {

    private Persoon man = null;
    private Persoon vrouw = null;
    private Persoon kind = null;

    @Before
    public void init() {
        man = PersoonBuilder.bouwRefererendPersoon("103055666");
        vrouw = PersoonBuilder.bouwRefererendPersoon("030946438");
        kind = PersoonBuilder.bouwRefererendPersoon("110029562");
    }


    @Test
    public void testValidatieMinIngevuldVoorRolKind() {
        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        RelatieBericht bericht = bouwFamRechBetrRelatie(man, vrouw, kind);
//        vulMaxAan(bericht);

        RelatieModel relatieModel = new RelatieModel(bericht);
        PersoonModel persoonModel = new PersoonModel(bericht.getKindBetrokkenheid().getBetrokkene());
        BetrokkenheidModel betrModel = new BetrokkenheidModel(bericht.getKindBetrokkenheid(), persoonModel, relatieModel);
        Set<ConstraintViolation<BetrokkenheidModel>> overtredingenModel =
                    validator.validate(betrModel, Default.class);
        // er zijn geen fouten gevonden
        Assert.assertEquals(0, overtredingenModel.size());

    }


    @Test
    public void testValidatieMaxIngevuldVoorRolKind() {
        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        RelatieBericht bericht = bouwFamRechBetrRelatie(man, vrouw, kind);
        vulMaxAan(bericht.getKindBetrokkenheid());


        RelatieModel relatieModel = new RelatieModel(bericht);
        PersoonModel persoonModel = new PersoonModel(bericht.getKindBetrokkenheid().getBetrokkene());
        BetrokkenheidModel betrModel = new BetrokkenheidModel(bericht.getKindBetrokkenheid(), persoonModel, relatieModel);
        Set<ConstraintViolation<BetrokkenheidModel>> overtredingenModel =
                    validator.validate(betrModel, Default.class);
        // er zijn maximaal 3 fouten te genereren, we hebben ze nu allemaal
        Assert.assertEquals(3, overtredingenModel.size());

    }


    private void vulMaxAan(final BetrokkenheidBericht betrokkenheid) {
        betrokkenheid.setBetrokkenheidOuderschap(new BetrokkenheidOuderschapGroepBericht());
        betrokkenheid.getBetrokkenheidOuderschap().setDatumAanvang(DatumUtil.vandaag());
        betrokkenheid.getBetrokkenheidOuderschap().setIndAdresGevend(Ja.Ja);
        betrokkenheid.getBetrokkenheidOuderschap().setIndOuder(Ja.Ja);
    }

    /**
     * Bouw een fam.rech.betr relatie met 3 personen.
     * @param ouder1 de man.
     * @param ouder2 de vrouw.
     * @param kindje het kind.
     * @return de relatie.
     */
    private RelatieBericht bouwFamRechBetrRelatie(final Persoon ouder1, final Persoon ouder2, final Persoon kindje) {
        RelatieBericht relatie = new RelatieBuilder()
            .bouwFamilieRechtelijkeBetrekkingRelatie()
            .voegKindToe((PersoonBericht) kindje)
            .voegOuderToe((PersoonBericht) ouder1)
            .voegOuderToe((PersoonBericht) ouder2)
            .getRelatie();
        // dit wordt standaard niet aangemaakt voor fam.recht.betr.
        relatie.setGegevens(new RelatieStandaardGroepBericht());
        return relatie;
    }

    private RelatieBericht bouwHuwelijkRelatie(final Persoon partner1, final Persoon partner2) {
        RelatieBericht relatie = new RelatieBuilder()
            .bouwHuwlijkRelatie()
            .voegPartnerToe((PersoonBericht) partner1)
            .voegPartnerToe((PersoonBericht) partner2)
            .getRelatie();
        return relatie;
    }

    private RelatieBericht bouwGeregPartner(final Persoon partner1, final Persoon partner2) {
        RelatieBericht relatie = new RelatieBuilder()
            .bouwGeregistreerdPartnerschap()
            .voegPartnerToe((PersoonBericht) partner1)
            .voegPartnerToe((PersoonBericht) partner2)
            .getRelatie();
        return relatie;
    }
}
