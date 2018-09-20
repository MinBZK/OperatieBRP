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
import nl.bzk.brp.model.attribuuttype.BuitenlandsePlaats;
import nl.bzk.brp.model.attribuuttype.BuitenlandseRegio;
import nl.bzk.brp.model.attribuuttype.Omschrijving;
import nl.bzk.brp.model.attribuuttype.RedenBeeindigingRelatieCode;
import nl.bzk.brp.model.groep.bericht.RelatieStandaardGroepBericht;
import nl.bzk.brp.model.objecttype.bericht.PersoonBericht;
import nl.bzk.brp.model.objecttype.bericht.RelatieBericht;
import nl.bzk.brp.model.objecttype.logisch.Persoon;
import nl.bzk.brp.model.objecttype.operationeel.statisch.RedenBeeindigingRelatie;
import nl.bzk.brp.util.DatumUtil;
import nl.bzk.brp.util.PersoonBuilder;
import nl.bzk.brp.util.RelatieBuilder;
import nl.bzk.brp.util.StatischeObjecttypeBuilder;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * Unit test klasse voor de {@link Melding} klasse.
 */
public class RelatieValidatieTest {

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
    public void testValidatieMinIngevuldVoorFamRechBetrekking() {
        // bewijzen dat alles zonder fouten gevalideerd kan worden. In volgende test gaan we ALLE fouten genereren
        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        RelatieBericht bericht = bouwFamRechBetrRelatie(man, vrouw, kind);

        RelatieModel relatieModel = new RelatieModel(bericht);
        Set<ConstraintViolation<RelatieModel>> overtredingenModel = validator.validate(relatieModel, Default.class);
        // er zijn geen fouten opgetreden.
        Assert.assertEquals(0, overtredingenModel.size());
    }

    @Test
    public void testValidatieMaxIngevuldVoorFamRechBetrekking() {
        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        RelatieBericht bericht = bouwFamRechBetrRelatie(man, vrouw, kind);
        vulMaxAan(bericht);

        RelatieModel relatieModel = new RelatieModel(bericht);
        Set<ConstraintViolation<RelatieModel>> overtredingenModel = validator.validate(relatieModel, Default.class);
        // er zijn maximaal 15 fouten te genereren.
        Assert.assertEquals(15, overtredingenModel.size());

    }

    @Test
    public void testValidatieMaxIngevuldHuwelijk() {
        // bewijzen dat voor huwelijk en gereg partner. alles nog steeds werkt.
        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        RelatieBericht bericht = bouwHuwelijkRelatie(man, vrouw);
        vulMaxAan(bericht);

        RelatieModel relatieModel = new RelatieModel(bericht);
        Set<ConstraintViolation<RelatieModel>> overtredingenModel = validator.validate(relatieModel, Default.class);
        // er zijn geen fouten opgetreden.
        Assert.assertEquals(0, overtredingenModel.size());
    }

    @Test
    public void testValidatieMaxIngevuldGeregPart() {
        // bewijzen dat voor huwelijk en gereg partner. alles nog steeds werkt.
        final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        RelatieBericht bericht = bouwGeregPartner(man, vrouw);
        vulMaxAan(bericht);

        RelatieModel relatieModel = new RelatieModel(bericht);
        Set<ConstraintViolation<RelatieModel>> overtredingenModel = validator.validate(relatieModel, Default.class);
        // er zijn geen fouten opgetreden.
        Assert.assertEquals(0, overtredingenModel.size());
    }

    private void vulMaxAan(final RelatieBericht bericht) {
        bericht.getGegevens().setBuitenlandsePlaatsAanvang(new BuitenlandsePlaats("---"));
        bericht.getGegevens().setBuitenlandsePlaatsEinde(new BuitenlandsePlaats("---"));

        bericht.getGegevens().setBuitenlandseRegioAanvang(new BuitenlandseRegio("---"));
        bericht.getGegevens().setBuitenlandseRegioEinde(new BuitenlandseRegio("---"));

        bericht.getGegevens().setDatumAanvang(DatumUtil.vandaag());
        bericht.getGegevens().setDatumEinde(DatumUtil.vandaag());

        bericht.getGegevens().setGemeenteAanvang(StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM);
        bericht.getGegevens().setGemeenteEinde(StatischeObjecttypeBuilder.GEMEENTE_AMSTERDAM);

        bericht.getGegevens().setLandAanvang(StatischeObjecttypeBuilder.LAND_NEDERLAND);
        bericht.getGegevens().setLandEinde(StatischeObjecttypeBuilder.LAND_NEDERLAND);

        bericht.getGegevens().setOmschrijvingLocatieAanvang(new Omschrijving("----"));
        bericht.getGegevens().setOmschrijvingLocatieEinde(new Omschrijving("----"));

        bericht.getGegevens().setWoonPlaatsAanvang(StatischeObjecttypeBuilder.WOONPLAATS_BREDA);
        bericht.getGegevens().setWoonPlaatsEinde(StatischeObjecttypeBuilder.WOONPLAATS_BREDA);

        RedenBeeindigingRelatie rdn = new RedenBeeindigingRelatie();
        ReflectionTestUtils.setField(rdn, "code", new RedenBeeindigingRelatieCode("2"));
        bericht.getGegevens().setRedenBeeindigingRelatie(rdn);

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
