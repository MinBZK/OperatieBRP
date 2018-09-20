/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc306;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;
import nl.moderniseringgba.isc.esb.message.BerichtInhoudException;
import nl.moderniseringgba.isc.esb.message.BerichtSyntaxException;
import nl.moderniseringgba.isc.esb.message.brp.generated.StatusType;
import nl.moderniseringgba.isc.esb.message.brp.impl.GeboorteAntwoordBericht;
import nl.moderniseringgba.isc.esb.message.lo3.format.Lo3Format;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tb01Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tf11Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tv01Bericht;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3IndicatieGeheimCodeEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3LandCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.syntax.Lo3CategorieWaarde;

import org.junit.Test;

public class ControleerTv01DecisionTest {

	@Test
	public void testControleerTv01DecisionVerwijsgegevensIncorrect() throws Exception {
        final Tb01Bericht tb01Bericht = new Tb01Bericht();

        final Tv01Bericht tv01Bericht = buildTv01Bericht(buildCat21ElementenLijst());

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("tb01Bericht", tb01Bericht);
        parameters.put("tv01Bericht", tv01Bericht);

        final ControleerTv01Decision tv01Decision = new ControleerTv01Decision();
        String decision = tv01Decision.execute(parameters);

        Assert.assertEquals("Geen positieve reactie verwacht", "7b. Verwijsgegevens incorrect", decision);
	}
	
	
	@Test
    public void testControleerTv01DecisionAntwoordGoed() throws IOException, BerichtSyntaxException,
            BerichtInhoudException {
        // initieel een onvolledig Tb01Bericht
        final Tb01Bericht tb01Bericht = new Tb01Bericht();
        final Lo3Persoonslijst lo3Persoonslijst = maakLo3Persoonslijst();
        tb01Bericht.setLo3Persoonslijst(lo3Persoonslijst);

        final Tv01Bericht tv01Bericht = buildTv01Bericht(buildCat21ElementenLijst());

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("tb01Bericht", tb01Bericht);
        parameters.put("tv01Bericht", tv01Bericht);

        final ControleerTv01Decision tv01Decision = new ControleerTv01Decision();
        String decision = tv01Decision.execute(parameters);

        Assert.assertEquals("Positieve reactie verwacht", null, decision);
    }

    private Lo3Persoonslijst maakLo3Persoonslijst() {
        return Lo3PersoonslijstTestUtils.maakGeboorte(Lo3PersoonslijstTestUtils.maakLo3PersoonInhoud());
    }

    @Test
    public void testControleerTv01DecisionAntwoordMinderGoed() throws IOException, BerichtSyntaxException,
            BerichtInhoudException {

        final Tv01Bericht tv01Bericht = buildTv01Bericht(buildCat21ElementenLijst());
        tv01Bericht.setCorrelationId("tv01test");

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("tv01Bericht", tv01Bericht);

        // Test dat Tf11 bericht goed wordt aangemaakt
        final MaakTf11VerkeerdeVerwijsgegevensAction maakTf11 = new MaakTf11VerkeerdeVerwijsgegevensAction();

        final Map<String, Object> parametersMetTf11 = maakTf11.execute(parameters);

        final Tf11Bericht expectedTf11 = new Tf11Bericht();
        Long aNummer = 2349326344L;
        expectedTf11.setANummer(aNummer.toString());
        expectedTf11.setCorrelationId(tv01Bericht.getCorrelationId());

        Assert.assertEquals("tf11 bericht verwacht", expectedTf11.format(),
                ((Tf11Bericht) parametersMetTf11.get("tf11Bericht")).format());

        // Test dat BrpGeboorteAntwoord bericht met Waarschuwing wordt aangemaakt
        final MaakBrpGeboortAntwoordWaarschuwingAction maakBrpAntwoord =
                new MaakBrpGeboortAntwoordWaarschuwingAction();

        final Map<String, Object> parametersMetBrpAntwoord = maakBrpAntwoord.execute(parameters);

        final GeboorteAntwoordBericht expectedBrpAntwoord = new GeboorteAntwoordBericht();
        expectedBrpAntwoord.setStatus(StatusType.WAARSCHUWING);
        expectedBrpAntwoord.setCorrelationId(tv01Bericht.getCorrelationId());
        expectedBrpAntwoord.setToelichting("De verwijsgegevens waren niet correct");

        Assert.assertEquals("brp GeboorteAntwoord met waarschuwing verwacht", expectedBrpAntwoord.format(),
                ((GeboorteAntwoordBericht) parametersMetBrpAntwoord.get("brpGeboorteAntwoordWaarschuwingBericht"))
                        .format());
    }

    @Test
    public void testControleerTv01DecisionZonderVerwijsgegevens() {
        final Tb01Bericht tb01Bericht = new Tb01Bericht();
        final Tv01Bericht tv01Bericht = new Tv01Bericht();

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("tb01Bericht", tb01Bericht);
        parameters.put("verzendenTb01Antwoord", tv01Bericht);

        assertEquals("7b. Verwijsgegevens incorrect", new ControleerTv01Decision().execute(parameters));
    }

    @Test
    public void testControleerTv01DecisionVerschilInVerwijsgegevens() {
        final Tb01Bericht tb01Bericht = new Tb01Bericht();
        tb01Bericht.setLo3Persoonslijst(Lo3PersoonslijstTestUtils.maakGeboorte(Lo3PersoonslijstTestUtils
                .maakLo3PersoonInhoud2()));

        final Tv01Bericht tv01Bericht = buildTv01Bericht(buildCat21ElementenLijst());

        final Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("tb01Bericht", tb01Bericht);
        parameters.put("verzendenTb01Antwoord", tv01Bericht);

        assertEquals("7b. Verwijsgegevens incorrect", new ControleerTv01Decision().execute(parameters));
    }

    
	private List<Lo3CategorieWaarde> buildCat21ElementenLijst() {
		
		List<Lo3CategorieWaarde> categorieen = new ArrayList<Lo3CategorieWaarde>();
		Lo3CategorieWaarde cat21 = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_21, 0, 0);

		cat21.addElement(Lo3ElementEnum.ELEMENT_0110, Lo3Format.format(2349326344L));
		cat21.addElement(Lo3ElementEnum.ELEMENT_0120, Lo3Format.format(123456789L));

		cat21.addElement(Lo3ElementEnum.ELEMENT_0210, Lo3Format.format("Billy"));
		cat21.addElement(Lo3ElementEnum.ELEMENT_0220, null);
		cat21.addElement(Lo3ElementEnum.ELEMENT_0230, null);
		cat21.addElement(Lo3ElementEnum.ELEMENT_0240, Lo3Format.format("Barendsen"));

		cat21.addElement(Lo3ElementEnum.ELEMENT_0310, Lo3Format.format(new Lo3Datum(20121024)));
		cat21.addElement(Lo3ElementEnum.ELEMENT_0320, Lo3Format.format(new Lo3GemeenteCode("0518")));
		cat21.addElement(Lo3ElementEnum.ELEMENT_0330, Lo3Format.format(new Lo3LandCode(Lo3LandCode.CODE_NEDERLAND)));

		cat21.addElement(Lo3ElementEnum.ELEMENT_0910, Lo3Format.format(new Lo3GemeenteCode("0518")));
		cat21.addElement(Lo3ElementEnum.ELEMENT_0920, Lo3Format.format(new Lo3Datum(20121025)));

		cat21.addElement(Lo3ElementEnum.ELEMENT_7010, Lo3Format.format(Lo3IndicatieGeheimCodeEnum.GEEN_BEPERKING.asElement()));

		cat21.addElement(Lo3ElementEnum.ELEMENT_8310, null); // TODO: uitzoeken waar dit vandaan moet komen
		cat21.addElement(Lo3ElementEnum.ELEMENT_8320, null);
		cat21.addElement(Lo3ElementEnum.ELEMENT_8330, null);

		cat21.addElement(Lo3ElementEnum.ELEMENT_8510, Lo3Format.format(new Lo3Datum(20000101)));

		categorieen.add(cat21);
		return categorieen;
	}
	
    private Tv01Bericht buildTv01Bericht(final List<Lo3CategorieWaarde> categorien) {
        final Tv01Bericht tv01Bericht = new Tv01Bericht(categorien);
        return tv01Bericht;
    }    
}
