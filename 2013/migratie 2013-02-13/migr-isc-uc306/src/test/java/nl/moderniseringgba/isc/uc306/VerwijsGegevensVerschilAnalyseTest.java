/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.uc306;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;
import nl.moderniseringgba.isc.esb.message.lo3.format.Lo3Format;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tb01Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.impl.Tv01Bericht;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.codes.Lo3IndicatieGeheimCodeEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3LandCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.syntax.Lo3CategorieWaarde;

import org.junit.Test;

public class VerwijsGegevensVerschilAnalyseTest {

	@Test
	public void testGeenPersoonsgegevensInTb01Bericht() {
		final Tb01Bericht tb01Bericht = new Tb01Bericht();
		final Tv01Bericht tv01Bericht = buildTv01Bericht(buildCat21ElementenLijst());

		final Set<String> actual = VerwijsGegevensVerschilAnalyse
				.bepaalVerschilVerwijsGegevens(tb01Bericht, tv01Bericht);

		final Set<String> expected = new HashSet<String>();
		expected.add("Persoonslijst van Tb01 bericht is leeg, terwijl de verwijsgegevens wel in het Tv01 bericht aanwezig zijn.");

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testGeenVerschillen() {

		final Tb01Bericht tb01Bericht = maakTb01BerichtMetPersoonInhoud();
		final Tv01Bericht tv01Bericht = buildTv01Bericht(buildCat21ElementenLijst());

		final Set<String> actual = VerwijsGegevensVerschilAnalyse
				.bepaalVerschilVerwijsGegevens(tb01Bericht, tv01Bericht);

		final Set<String> expected = new HashSet<String>();

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testVerschillenGroep02() {

		final Tb01Bericht tb01Bericht = maakTb01BerichtMetAfwijkendeNaamInPersoonInhoud();
		final Tv01Bericht tv01Bericht = buildTv01Bericht(buildCat21ElementenLijst());

		final Set<String> actual = VerwijsGegevensVerschilAnalyse
				.bepaalVerschilVerwijsGegevens(tb01Bericht, tv01Bericht);

		final Set<String> expected = new HashSet<String>();
		expected.add("Verschil 02.10 voornamen - Persoonsgegevens verstuurd (Tb01): Henk Verwijsgegevens ontvangen (Tv01): Billy");

		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testVerschillenGroep02en03() {

		final Tb01Bericht tb01Bericht = maakTb01BerichtMetAfwijkendeNaamEnAfwijkendeGeboorteDatumInPersoonInhoud();
		final Tv01Bericht tv01Bericht = buildTv01Bericht(buildCat21ElementenLijst());

		final Set<String> actual = VerwijsGegevensVerschilAnalyse
				.bepaalVerschilVerwijsGegevens(tb01Bericht, tv01Bericht);

		final Set<String> expected = new HashSet<String>();
		expected.add("Verschil 02.10 voornamen - Persoonsgegevens verstuurd (Tb01): Henk Verwijsgegevens ontvangen (Tv01): Billy");
		expected.add("Verschil 03.10 Geboortedatum - Persoonsgegevens verstuurd (Tb01): Lo3Datum[datum=20121001] Verwijsgegevens ontvangen (Tv01): Lo3Datum[datum=20121024]");

		Assert.assertEquals(expected, actual);
	}

	// @Test
	// public void testVerschillenGroep03() {
	//
	// final Tb01Bericht tb01Bericht =
	// maakTb01BerichtMetAfwijkendeGeboorteDatumInPersoonInhoud();
	// final Tv01Bericht tv01Bericht =
	// maakTv01Bericht(Lo3PersoonslijstTestUtils.maakLo3VerwijzingInhoud());
	//
	// final Set<String> actual =
	// VerwijsGegevensVerschilAnalyse.bepaalVerschilVerwijsGegevens(tb01Bericht.getLo3Persoonslijst(),
	// tv01Bericht.getVerwijzing().getInhoud());
	//
	// final Set<String> expected = new HashSet<String>();
	// expected.add("Verschil 03.10 Geboortedatum - Persoonsgegevens verstuurd (Tb01): Lo3Datum[datum=20121001] Verwijsgegevens ontvangen (Tv01): Lo3Datum[datum=20121024]");
	//
	// Assert.assertEquals(expected, actual);
	// }
	//
	// @Test
	// public void testGeenVerwijsgegevensInTv01Bericht() {
	// final Tb01Bericht tb01Bericht = maakTb01BerichtMetPersoonInhoud();
	//
	// final Set<String> actual =
	// VerwijsGegevensVerschilAnalyse.bepaalVerschilVerwijsGegevens(tb01Bericht.getLo3Persoonslijst(),
	// null);
	//
	// final Set<String> expected = new HashSet<String>();
	// expected.add("Verwijsgegevens van Tv01 bericht zijn leeg, terwijl er wel persoonsgegevens in het Tb01 bericht aanwezig waren.");
	//
	// Assert.assertEquals(expected, actual);
	// }
	//
	private Tb01Bericht maakTb01BerichtMetAfwijkendeNaamInPersoonInhoud() {
		final Tb01Bericht tb01Bericht = new Tb01Bericht();

		tb01Bericht
				.setLo3Persoonslijst(Lo3PersoonslijstTestUtils
						.maakGeboorte(Lo3PersoonslijstTestUtils
								.maakLo3PersoonInhoud2()));

		return tb01Bericht;
	}

	private Tb01Bericht maakTb01BerichtMetAfwijkendeNaamEnAfwijkendeGeboorteDatumInPersoonInhoud() {
		final Tb01Bericht tb01Bericht = new Tb01Bericht();

		tb01Bericht
				.setLo3Persoonslijst(Lo3PersoonslijstTestUtils
						.maakGeboorte(Lo3PersoonslijstTestUtils
								.maakLo3PersoonInhoud3()));

		return tb01Bericht;
	}

	//
	// private Tb01Bericht
	// maakTb01BerichtMetAfwijkendeGeboorteDatumInPersoonInhoud() {
	// final Tb01Bericht tb01Bericht = new Tb01Bericht();
	//
	// tb01Bericht.setLo3Persoonslijst(Lo3PersoonslijstTestUtils.maakGeboorte(Lo3PersoonslijstTestUtils
	// .maakLo3PersoonInhoud4()));
	//
	// return tb01Bericht;
	// }
	//
	private Tb01Bericht maakTb01BerichtMetPersoonInhoud() {
		final Tb01Bericht tb01Bericht = new Tb01Bericht();

		tb01Bericht
				.setLo3Persoonslijst(Lo3PersoonslijstTestUtils
						.maakGeboorte(Lo3PersoonslijstTestUtils
								.maakLo3PersoonInhoud()));

		return tb01Bericht;
	}

	// private Tv01Bericht maakTv01Bericht(final Lo3VerwijzingInhoud inhoud) {
	// final Tv01Bericht tv01Bericht = new Tv01Bericht();
	//
	// final Lo3Historie historie = new Lo3Historie(null, new
	// Lo3Datum(20000101), new Lo3Datum(20000102));
	//
	// final Lo3Categorie<Lo3VerwijzingInhoud> categorie =
	// new Lo3Categorie<Lo3VerwijzingInhoud>(inhoud, null, historie, new
	// Lo3Herkomst(
	// Lo3CategorieEnum.CATEGORIE_21, 1, 1));
	//
	// tv01Bericht.setVerwijzing(categorie);
	// return tv01Bericht;
	// }

	private List<Lo3CategorieWaarde> buildCat21ElementenLijst() {

		List<Lo3CategorieWaarde> categorieen = new ArrayList<Lo3CategorieWaarde>();
		Lo3CategorieWaarde cat21 = new Lo3CategorieWaarde(
				Lo3CategorieEnum.CATEGORIE_21, 0, 0);

		cat21.addElement(Lo3ElementEnum.ELEMENT_0110,
				Lo3Format.format(2349326344L));
		cat21.addElement(Lo3ElementEnum.ELEMENT_0120,
				Lo3Format.format(123456789L));

		cat21.addElement(Lo3ElementEnum.ELEMENT_0210, Lo3Format.format("Billy"));
		cat21.addElement(Lo3ElementEnum.ELEMENT_0220, null);
		cat21.addElement(Lo3ElementEnum.ELEMENT_0230, null);
		cat21.addElement(Lo3ElementEnum.ELEMENT_0240,
				Lo3Format.format("Barendsen"));

		cat21.addElement(Lo3ElementEnum.ELEMENT_0310,
				Lo3Format.format(new Lo3Datum(20121024)));
		cat21.addElement(Lo3ElementEnum.ELEMENT_0320,
				Lo3Format.format(new Lo3GemeenteCode("0518")));
		cat21.addElement(Lo3ElementEnum.ELEMENT_0330,
				Lo3Format.format(new Lo3LandCode(Lo3LandCode.CODE_NEDERLAND)));

		cat21.addElement(Lo3ElementEnum.ELEMENT_0910,
				Lo3Format.format(new Lo3GemeenteCode("0518")));
		cat21.addElement(Lo3ElementEnum.ELEMENT_0920,
				Lo3Format.format(new Lo3Datum(20121025)));

		cat21.addElement(Lo3ElementEnum.ELEMENT_7010, Lo3Format
				.format(Lo3IndicatieGeheimCodeEnum.GEEN_BEPERKING.asElement()));

		cat21.addElement(Lo3ElementEnum.ELEMENT_8310, null); // TODO: uitzoeken
																// waar dit
																// vandaan moet
																// komen
		cat21.addElement(Lo3ElementEnum.ELEMENT_8320, null);
		cat21.addElement(Lo3ElementEnum.ELEMENT_8330, null);

		cat21.addElement(Lo3ElementEnum.ELEMENT_8510,
				Lo3Format.format(new Lo3Datum(20000101)));

		categorieen.add(cat21);
		return categorieen;
	}

	private Tv01Bericht buildTv01Bericht(
			final List<Lo3CategorieWaarde> categorien) {
		final Tv01Bericht tv01Bericht = new Tv01Bericht(categorien);
		return tv01Bericht;
	}

}
