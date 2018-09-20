/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.parser;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Documentatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3GezagsverhoudingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3HuwelijkOfGpInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3InschrijvingInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3KiesrechtInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3KindInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3NationaliteitInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OverlijdenInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3PersoonInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3ReisdocumentInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3VerblijfplaatsInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3VerblijfstitelInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingBijzonderNederlandschap;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingEuropeesKiesrecht;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingHuisnummer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingInhoudingVermissingNederlandsReisdocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingNaamgebruikCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingUitgeslotenKiesrecht;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AanduidingVerblijfstitelCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AangifteAdreshouding;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AutoriteitVanAfgifteNederlandsReisdocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datumtijdstempel;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3FunctieAdres;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Geslachtsaanduiding;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Huisnummer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieCurateleregister;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieDocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieGeheimCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatieGezagMinderjarige;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3IndicatiePKVolledigGeconverteerdCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3NationaliteitCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenNederlandschapCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenOntbindingHuwelijkOfGpCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenOpschortingBijhoudingCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Signalering;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3SoortNederlandsReisdocument;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3SoortVerbintenis;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import org.junit.Test;

public class Lo3PersoonslijstParserTest extends AbstractParserTest {

    // Ouder 2
    private static final String WAARDE_ELEMENT_0110_2 = "8745316841";
    private static final String WAARDE_ELEMENT_0120_2 = "987153135";
    private static final String WAARDE_ELEMENT_0210_2 = "Gerarda";
    private static final String WAARDE_ELEMENT_0220_2 = "H";
    private static final String WAARDE_ELEMENT_0230_2 = "op het";
    private static final String WAARDE_ELEMENT_0240_2 = "Veld";
    private static final String WAARDE_ELEMENT_0310_2 = "19590312";
    private static final String WAARDE_ELEMENT_0410_2 = "V";

    private String waardeElement8310;
    private Lo3CategorieEnum categorie;

    @Test
    public void testPersoonslijstParser() {
        final List<Lo3CategorieWaarde> categorieen = new ArrayList<>();

        final Lo3Stapel<Lo3PersoonInhoud> persoonStapel = vulCategoriePersoon(categorieen);
        final Lo3Stapel<Lo3OuderInhoud> ouder1Stapel = vulCategorieOuder1(categorieen);
        final Lo3Stapel<Lo3OuderInhoud> ouder2Stapel = vulCategorieOuder2(categorieen);
        final Lo3Stapel<Lo3NationaliteitInhoud> nationaliteitStapel = vulCategorieNationaliteit(categorieen);
        final Lo3Stapel<Lo3HuwelijkOfGpInhoud> huwelijkOfGpStapel = vulCategorieHuwelijkOfGp(categorieen);
        final Lo3Stapel<Lo3OverlijdenInhoud> overlijdenStapel = vulCategorieOverlijden(categorieen);
        final Lo3Stapel<Lo3InschrijvingInhoud> inschrijvingStapel = vulCategorieInschrijving(categorieen);
        final Lo3Stapel<Lo3VerblijfplaatsInhoud> verblijfplaatsStapel = vulCategorieVerblijfplaats(categorieen);
        final Lo3Stapel<Lo3KindInhoud> kindStapel = vulCategorieKind(categorieen);
        final Lo3Stapel<Lo3VerblijfstitelInhoud> verblijfstitelStapel = vulCategorieVerblijfstitel(categorieen);
        final Lo3Stapel<Lo3GezagsverhoudingInhoud> gezagVerhoudingStapel = vulCategorieGezagsverhouding(categorieen);
        final Lo3Stapel<Lo3ReisdocumentInhoud> reisdocumentStapel = vulCategorieReisdocument(categorieen);
        final Lo3Stapel<Lo3KiesrechtInhoud> kiesrechtStapel = vulCategorieKiesrecht(categorieen);

        final Lo3PersoonslijstParser parser = new Lo3PersoonslijstParser();
        final Lo3Persoonslijst persoonslijst = parser.parse(categorieen);

        Assert.assertEquals(persoonStapel.getLaatsteElement().getInhoud(), persoonslijst.getPersoonStapel().getLaatsteElement().getInhoud());
        Assert.assertEquals(ouder1Stapel.getLaatsteElement().getInhoud(), persoonslijst.getOuder1Stapel().getLaatsteElement().getInhoud());
        Assert.assertEquals(ouder2Stapel.getLaatsteElement().getInhoud(), persoonslijst.getOuder2Stapel().getLaatsteElement().getInhoud());
        Assert.assertEquals(
            nationaliteitStapel.getLaatsteElement().getInhoud(),
            persoonslijst.getNationaliteitStapels().get(0).getLaatsteElement().getInhoud());
        Assert.assertEquals(
            huwelijkOfGpStapel.getLaatsteElement().getInhoud(),
            persoonslijst.getHuwelijkOfGpStapels().get(0).getLaatsteElement().getInhoud());
        Assert.assertEquals(overlijdenStapel.getLaatsteElement().getInhoud(), persoonslijst.getOverlijdenStapel().getLaatsteElement().getInhoud());
        Assert.assertEquals(inschrijvingStapel.getLaatsteElement().getInhoud(), persoonslijst.getInschrijvingStapel().getLaatsteElement().getInhoud());
        Assert.assertEquals(verblijfplaatsStapel.getLaatsteElement().getInhoud(), persoonslijst.getVerblijfplaatsStapel().getLaatsteElement().getInhoud());
        Assert.assertEquals(kindStapel.getLaatsteElement().getInhoud(), persoonslijst.getKindStapels().get(0).getLaatsteElement().getInhoud());
        Assert.assertEquals(verblijfstitelStapel.getLaatsteElement().getInhoud(), persoonslijst.getVerblijfstitelStapel().getLaatsteElement().getInhoud());
        Assert.assertEquals(
            gezagVerhoudingStapel.getLaatsteElement().getInhoud(),
            persoonslijst.getGezagsverhoudingStapel().getLaatsteElement().getInhoud());
        Assert.assertEquals(
            reisdocumentStapel.getLaatsteElement().getInhoud(),
            persoonslijst.getReisdocumentStapels().get(0).getLaatsteElement().getInhoud());
        Assert.assertEquals(kiesrechtStapel.getLaatsteElement().getInhoud(), persoonslijst.getKiesrechtStapel().getLaatsteElement().getInhoud());
    }

    private Lo3Stapel<Lo3PersoonInhoud> vulCategoriePersoon(final List<Lo3CategorieWaarde> categorieen) {
        this.categorie = Lo3CategorieEnum.CATEGORIE_01;
        this.waardeElement8310 = "010000";
        final Lo3Onderzoek onderzoek = maakOnderzoek();
        final Lo3Historie historie = maakHistorie();
        final Lo3Documentatie documentatie = maakDocumentatie();

        final Lo3CategorieWaarde categorieWaarde = new Lo3CategorieWaarde(categorie, 1, 1);

        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0110, WAARDE_ELEMENT_0110);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0120, WAARDE_ELEMENT_0120);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0210, WAARDE_ELEMENT_0210);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0220, WAARDE_ELEMENT_0220);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0230, WAARDE_ELEMENT_0230);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0240, WAARDE_ELEMENT_0240);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0310, WAARDE_ELEMENT_0310);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0320, WAARDE_ELEMENT_0320);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0330, WAARDE_ELEMENT_0330);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0410, WAARDE_ELEMENT_0410);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_2010, WAARDE_ELEMENT_2010);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_2020, WAARDE_ELEMENT_2020);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_6110, WAARDE_ELEMENT_6110);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8110, WAARDE_ELEMENT_8110);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8120, WAARDE_ELEMENT_8120);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8210, WAARDE_ELEMENT_8210);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8220, WAARDE_ELEMENT_8220);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8230, WAARDE_ELEMENT_8230);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8310, waardeElement8310);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8320, WAARDE_ELEMENT_8320);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8330, WAARDE_ELEMENT_8330);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8410, WAARDE_ELEMENT_8410);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8510, WAARDE_ELEMENT_8510);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8610, WAARDE_ELEMENT_8610);

        categorieen.add(categorieWaarde);

        final List<Lo3Categorie<Lo3PersoonInhoud>> categorieInhoudLijst = new ArrayList<>();

        final Lo3PersoonInhoud persoonInhoud =
                new Lo3PersoonInhoud(
                    maakLo3Long(WAARDE_ELEMENT_0110, null),
                    maakLo3Integer(WAARDE_ELEMENT_0120, onderzoek),
                    maakLo3String(WAARDE_ELEMENT_0210, onderzoek),
                    new Lo3AdellijkeTitelPredikaatCode(WAARDE_ELEMENT_0220, onderzoek),
                    maakLo3String(WAARDE_ELEMENT_0230, onderzoek),
                    maakLo3String(WAARDE_ELEMENT_0240, onderzoek),
                    maakDatum(WAARDE_ELEMENT_0310, onderzoek),
                    new Lo3GemeenteCode(WAARDE_ELEMENT_0320, onderzoek),
                    new Lo3LandCode(WAARDE_ELEMENT_0330, onderzoek),
                    new Lo3Geslachtsaanduiding(WAARDE_ELEMENT_0410, onderzoek),
                    maakLo3Long(WAARDE_ELEMENT_2010, null),
                    maakLo3Long(WAARDE_ELEMENT_2020, null),
                    new Lo3AanduidingNaamgebruikCode(WAARDE_ELEMENT_6110, onderzoek));

        final Lo3Categorie<Lo3PersoonInhoud> categorieInhoudCategorie =
                new Lo3Categorie<>(persoonInhoud, documentatie, onderzoek, historie, new Lo3Herkomst(categorie, 0, 0));

        categorieInhoudLijst.add(categorieInhoudCategorie);

        return new Lo3Stapel<>(categorieInhoudLijst);
    }

    private Lo3Stapel<Lo3OuderInhoud> vulCategorieOuder1(final List<Lo3CategorieWaarde> categorieen) {
        this.categorie = Lo3CategorieEnum.CATEGORIE_02;
        this.waardeElement8310 = "020000";
        final Lo3Onderzoek onderzoek = maakOnderzoek();
        final Lo3Historie historie = maakHistorie();
        final Lo3Documentatie documentatie = maakDocumentatie();

        final Lo3CategorieWaarde categorieWaarde = new Lo3CategorieWaarde(this.categorie, 1, 1);

        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0110, WAARDE_ELEMENT_0110);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0120, WAARDE_ELEMENT_0120);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0210, WAARDE_ELEMENT_0210);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0220, WAARDE_ELEMENT_0220);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0230, WAARDE_ELEMENT_0230);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0240, WAARDE_ELEMENT_0240);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0310, WAARDE_ELEMENT_0310);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0320, WAARDE_ELEMENT_0320);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0330, WAARDE_ELEMENT_0330);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0410, WAARDE_ELEMENT_0410);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_6210, WAARDE_ELEMENT_6210);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8110, WAARDE_ELEMENT_8110);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8120, WAARDE_ELEMENT_8120);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8210, WAARDE_ELEMENT_8210);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8220, WAARDE_ELEMENT_8220);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8230, WAARDE_ELEMENT_8230);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8310, waardeElement8310);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8320, WAARDE_ELEMENT_8320);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8330, WAARDE_ELEMENT_8330);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8410, WAARDE_ELEMENT_8410);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8510, WAARDE_ELEMENT_8510);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8610, WAARDE_ELEMENT_8610);

        categorieen.add(categorieWaarde);

        final List<Lo3Categorie<Lo3OuderInhoud>> categorieInhoudLijst = new ArrayList<>();

        final Lo3OuderInhoud ouderInhoud =
                new Lo3OuderInhoud(
                    maakLo3Long(WAARDE_ELEMENT_0110, onderzoek),
                    maakLo3Integer(WAARDE_ELEMENT_0120, onderzoek),
                    maakLo3String(WAARDE_ELEMENT_0210, onderzoek),
                    new Lo3AdellijkeTitelPredikaatCode(WAARDE_ELEMENT_0220, onderzoek),
                    maakLo3String(WAARDE_ELEMENT_0230, onderzoek),
                    maakLo3String(WAARDE_ELEMENT_0240, onderzoek),
                    maakDatum(WAARDE_ELEMENT_0310, onderzoek),
                    new Lo3GemeenteCode(WAARDE_ELEMENT_0320, onderzoek),
                    new Lo3LandCode(WAARDE_ELEMENT_0330, onderzoek),
                    new Lo3Geslachtsaanduiding(WAARDE_ELEMENT_0410, onderzoek),
                    maakDatum(WAARDE_ELEMENT_6210, onderzoek));

        final Lo3Categorie<Lo3OuderInhoud> categorieInhoudCategorie =
                new Lo3Categorie<>(ouderInhoud, documentatie, onderzoek, historie, new Lo3Herkomst(categorie, 0, 0));

        categorieInhoudLijst.add(categorieInhoudCategorie);
        return new Lo3Stapel<>(categorieInhoudLijst);
    }

    private Lo3Stapel<Lo3OuderInhoud> vulCategorieOuder2(final List<Lo3CategorieWaarde> categorieen) {
        this.categorie = Lo3CategorieEnum.CATEGORIE_03;
        this.waardeElement8310 = "030000";
        final Lo3Onderzoek onderzoek = maakOnderzoek();
        final Lo3Historie historie = maakHistorie();
        final Lo3Documentatie documentatie = maakDocumentatie();

        final Lo3CategorieWaarde categorieWaarde = new Lo3CategorieWaarde(this.categorie, 1, 1);

        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0110, WAARDE_ELEMENT_0110_2);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0120, WAARDE_ELEMENT_0120_2);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0210, WAARDE_ELEMENT_0210_2);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0220, WAARDE_ELEMENT_0220_2);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0230, WAARDE_ELEMENT_0230_2);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0240, WAARDE_ELEMENT_0240_2);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0310, WAARDE_ELEMENT_0310_2);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0320, WAARDE_ELEMENT_0320);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0330, WAARDE_ELEMENT_0330);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0410, WAARDE_ELEMENT_0410_2);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_6210, WAARDE_ELEMENT_6210);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8110, WAARDE_ELEMENT_8110);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8120, WAARDE_ELEMENT_8120);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8210, WAARDE_ELEMENT_8210);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8220, WAARDE_ELEMENT_8220);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8230, WAARDE_ELEMENT_8230);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8310, waardeElement8310);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8320, WAARDE_ELEMENT_8320);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8330, WAARDE_ELEMENT_8330);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8410, WAARDE_ELEMENT_8410);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8510, WAARDE_ELEMENT_8510);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8610, WAARDE_ELEMENT_8610);

        categorieen.add(categorieWaarde);

        final List<Lo3Categorie<Lo3OuderInhoud>> categorieInhoudLijst = new ArrayList<>();

        final Lo3OuderInhoud ouderInhoud =
                new Lo3OuderInhoud(
                    maakLo3Long(WAARDE_ELEMENT_0110_2, onderzoek),
                    maakLo3Integer(WAARDE_ELEMENT_0120_2, onderzoek),
                    maakLo3String(WAARDE_ELEMENT_0210_2, onderzoek),
                    new Lo3AdellijkeTitelPredikaatCode(WAARDE_ELEMENT_0220_2, onderzoek),
                    maakLo3String(WAARDE_ELEMENT_0230_2, onderzoek),
                    maakLo3String(WAARDE_ELEMENT_0240_2, onderzoek),
                    maakDatum(WAARDE_ELEMENT_0310_2, onderzoek),
                    new Lo3GemeenteCode(WAARDE_ELEMENT_0320, onderzoek),
                    new Lo3LandCode(WAARDE_ELEMENT_0330, onderzoek),
                    new Lo3Geslachtsaanduiding(WAARDE_ELEMENT_0410_2, onderzoek),
                    maakDatum(WAARDE_ELEMENT_6210, onderzoek));

        final Lo3Categorie<Lo3OuderInhoud> categorieInhoudCategorie =
                new Lo3Categorie<>(ouderInhoud, documentatie, onderzoek, historie, new Lo3Herkomst(categorie, 0, 0));

        categorieInhoudLijst.add(categorieInhoudCategorie);
        return new Lo3Stapel<>(categorieInhoudLijst);
    }

    private Lo3Stapel<Lo3NationaliteitInhoud> vulCategorieNationaliteit(final List<Lo3CategorieWaarde> categorieen) {
        this.categorie = Lo3CategorieEnum.CATEGORIE_04;
        this.waardeElement8310 = "040000";
        final Lo3Onderzoek onderzoek = maakOnderzoek();
        final Lo3Historie historie = maakHistorie();
        final Lo3Documentatie documentatie = maakDocumentatie();

        final Lo3CategorieWaarde categorieWaarde = new Lo3CategorieWaarde(categorie, 1, 1);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0510, WAARDE_ELEMENT_0510);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_6310, WAARDE_ELEMENT_6310);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_6410, WAARDE_ELEMENT_6410);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_6510, WAARDE_ELEMENT_6510);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8210, WAARDE_ELEMENT_8210);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8220, WAARDE_ELEMENT_8220);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8220, WAARDE_ELEMENT_8230);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8310, waardeElement8310);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8320, WAARDE_ELEMENT_8320);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8330, WAARDE_ELEMENT_8330);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8410, WAARDE_ELEMENT_8410);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8510, WAARDE_ELEMENT_8510);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8610, WAARDE_ELEMENT_8610);

        categorieen.add(categorieWaarde);

        final List<Lo3Categorie<Lo3NationaliteitInhoud>> categorieInhoudLijst = new ArrayList<>();

        final Lo3NationaliteitInhoud categorieInhoud =
                new Lo3NationaliteitInhoud(
                    new Lo3NationaliteitCode(WAARDE_ELEMENT_0510, onderzoek),
                    new Lo3RedenNederlandschapCode(WAARDE_ELEMENT_6310,onderzoek),
                    new Lo3RedenNederlandschapCode(WAARDE_ELEMENT_6410,onderzoek),
                    new Lo3AanduidingBijzonderNederlandschap(WAARDE_ELEMENT_6510, onderzoek));

        final Lo3Categorie<Lo3NationaliteitInhoud> categorieInhoudCategorie =
                new Lo3Categorie<>(categorieInhoud, documentatie, onderzoek, historie, new Lo3Herkomst(categorie, 0, 0));

        categorieInhoudLijst.add(categorieInhoudCategorie);
        return new Lo3Stapel<>(categorieInhoudLijst);
    }

    private Lo3Stapel<Lo3HuwelijkOfGpInhoud> vulCategorieHuwelijkOfGp(final List<Lo3CategorieWaarde> categorieen) {
        this.categorie = Lo3CategorieEnum.CATEGORIE_05;
        this.waardeElement8310 = "050000";
        final Lo3Onderzoek onderzoek = maakOnderzoek();
        final Lo3Historie historie = maakHistorie();
        final Lo3Documentatie documentatie = maakDocumentatie();

        final Lo3CategorieWaarde categorieWaarde = new Lo3CategorieWaarde(categorie, 1, 1);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0110, WAARDE_ELEMENT_0110);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0120, WAARDE_ELEMENT_0120);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0210, WAARDE_ELEMENT_0210);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0220, WAARDE_ELEMENT_0220);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0230, WAARDE_ELEMENT_0230);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0240, WAARDE_ELEMENT_0240);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0310, WAARDE_ELEMENT_0310);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0320, WAARDE_ELEMENT_0320);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0330, WAARDE_ELEMENT_0330);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0410, WAARDE_ELEMENT_0410);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0610, WAARDE_ELEMENT_0610);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0620, WAARDE_ELEMENT_0620);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0630, WAARDE_ELEMENT_0630);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0710, WAARDE_ELEMENT_0710);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0720, WAARDE_ELEMENT_0720);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0730, WAARDE_ELEMENT_0730);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0740, WAARDE_ELEMENT_0740);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_1510, WAARDE_ELEMENT_1510);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8110, WAARDE_ELEMENT_8110);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8120, WAARDE_ELEMENT_8120);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8210, WAARDE_ELEMENT_8210);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8220, WAARDE_ELEMENT_8220);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8230, WAARDE_ELEMENT_8230);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8310, waardeElement8310);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8320, WAARDE_ELEMENT_8320);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8330, WAARDE_ELEMENT_8330);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8410, WAARDE_ELEMENT_8410);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8510, WAARDE_ELEMENT_8510);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8610, WAARDE_ELEMENT_8610);

        categorieen.add(categorieWaarde);

        final List<Lo3Categorie<Lo3HuwelijkOfGpInhoud>> huwelijkOfGpInhoudLijst = new ArrayList<>();

        final Lo3HuwelijkOfGpInhoud huwelijkOfGpInhoud =
                new Lo3HuwelijkOfGpInhoud(
                    maakLo3Long(WAARDE_ELEMENT_0110, onderzoek),
                    maakLo3Integer(WAARDE_ELEMENT_0120, onderzoek),
                    maakLo3String(WAARDE_ELEMENT_0210, onderzoek),
                    new Lo3AdellijkeTitelPredikaatCode(WAARDE_ELEMENT_0220, onderzoek),
                    maakLo3String(WAARDE_ELEMENT_0230, onderzoek),
                    maakLo3String(WAARDE_ELEMENT_0240, onderzoek),
                    maakDatum(WAARDE_ELEMENT_0310, onderzoek),
                    new Lo3GemeenteCode(WAARDE_ELEMENT_0320, onderzoek),
                    new Lo3LandCode(WAARDE_ELEMENT_0330, onderzoek),
                    new Lo3Geslachtsaanduiding(WAARDE_ELEMENT_0410, onderzoek),
                    maakDatum(WAARDE_ELEMENT_0610, onderzoek),
                    new Lo3GemeenteCode(WAARDE_ELEMENT_0620, onderzoek),
                    new Lo3LandCode(WAARDE_ELEMENT_0630, onderzoek),
                    maakDatum(WAARDE_ELEMENT_0710, onderzoek),
                    new Lo3GemeenteCode(WAARDE_ELEMENT_0720, onderzoek),
                    new Lo3LandCode(WAARDE_ELEMENT_0730, onderzoek),
                    new Lo3RedenOntbindingHuwelijkOfGpCode(WAARDE_ELEMENT_0740, onderzoek),
                    new Lo3SoortVerbintenis(WAARDE_ELEMENT_1510, onderzoek));

        final Lo3Categorie<Lo3HuwelijkOfGpInhoud> huwelijkOfGpInhoudCategorie =
                new Lo3Categorie<>(huwelijkOfGpInhoud, documentatie, onderzoek, historie, new Lo3Herkomst(categorie, 0, 0));

        huwelijkOfGpInhoudLijst.add(huwelijkOfGpInhoudCategorie);
        return new Lo3Stapel<>(huwelijkOfGpInhoudLijst);
    }

    private Lo3Stapel<Lo3OverlijdenInhoud> vulCategorieOverlijden(final List<Lo3CategorieWaarde> categorieen) {
        this.categorie = Lo3CategorieEnum.CATEGORIE_06;
        this.waardeElement8310 = "060000";
        final Lo3Onderzoek onderzoek = maakOnderzoek();
        final Lo3Historie historie = maakHistorie();
        final Lo3Documentatie documentatie = maakDocumentatie();

        final Lo3CategorieWaarde categorieWaarde = new Lo3CategorieWaarde(categorie, 1, 1);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0810, WAARDE_ELEMENT_0810);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0820, WAARDE_ELEMENT_0820);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0830, WAARDE_ELEMENT_0830);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8110, WAARDE_ELEMENT_8110);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8120, WAARDE_ELEMENT_8120);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8210, WAARDE_ELEMENT_8210);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8220, WAARDE_ELEMENT_8220);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8230, WAARDE_ELEMENT_8230);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8310, waardeElement8310);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8320, WAARDE_ELEMENT_8320);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8330, WAARDE_ELEMENT_8330);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8410, WAARDE_ELEMENT_8410);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8510, WAARDE_ELEMENT_8510);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8610, WAARDE_ELEMENT_8610);

        categorieen.add(categorieWaarde);

        final List<Lo3Categorie<Lo3OverlijdenInhoud>> categorieInhoudLijst = new ArrayList<>();
        final Lo3OverlijdenInhoud overlijdenInhoud =
                new Lo3OverlijdenInhoud(
                    maakDatum(WAARDE_ELEMENT_0810, onderzoek),
                    new Lo3GemeenteCode(WAARDE_ELEMENT_0820, onderzoek),
                    new Lo3LandCode(WAARDE_ELEMENT_0830, onderzoek));

        final Lo3Categorie<Lo3OverlijdenInhoud> categorieInhoudCategorie =
                new Lo3Categorie<>(overlijdenInhoud, documentatie, onderzoek, historie, new Lo3Herkomst(categorie, 0, 0));

        categorieInhoudLijst.add(categorieInhoudCategorie);
        return new Lo3Stapel<>(categorieInhoudLijst);
    }

    private Lo3Stapel<Lo3InschrijvingInhoud> vulCategorieInschrijving(final List<Lo3CategorieWaarde> categorieen) {
        this.categorie = Lo3CategorieEnum.CATEGORIE_07;

        final Lo3CategorieWaarde categorieWaarde = new Lo3CategorieWaarde(categorie, 1, 1);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_6620, WAARDE_ELEMENT_6620);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_6710, WAARDE_ELEMENT_6710);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_6720, WAARDE_ELEMENT_6720);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_6810, WAARDE_ELEMENT_6810);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_6910, WAARDE_ELEMENT_6910);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_7010, WAARDE_ELEMENT_7010);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_7110, WAARDE_ELEMENT_7110);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_7120, WAARDE_ELEMENT_7120);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8010, WAARDE_ELEMENT_8010);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8020, WAARDE_ELEMENT_8020);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8710, WAARDE_ELEMENT_8710);

        categorieen.add(categorieWaarde);
        final Lo3Historie nullHistorie = Lo3Historie.NULL_HISTORIE;
        final List<Lo3Categorie<Lo3InschrijvingInhoud>> categorieInhoudLijst = new ArrayList<>();

        final Lo3InschrijvingInhoud inschrijvingInhoud =
                new Lo3InschrijvingInhoud(
                    maakDatum(WAARDE_ELEMENT_6620, null),
                    maakDatum(WAARDE_ELEMENT_6710, null),
                    new Lo3RedenOpschortingBijhoudingCode(WAARDE_ELEMENT_6720, null),
                    maakDatum(WAARDE_ELEMENT_6810, null),
                    new Lo3GemeenteCode(WAARDE_ELEMENT_6910, null),
                    new Lo3IndicatieGeheimCode(WAARDE_ELEMENT_7010, null),
                    maakDatum(WAARDE_ELEMENT_7110, null),
                    maakLo3String(WAARDE_ELEMENT_7120, null),
                    maakLo3Integer(WAARDE_ELEMENT_8010, null),
                    new Lo3Datumtijdstempel(WAARDE_ELEMENT_8020, null),
                    new Lo3IndicatiePKVolledigGeconverteerdCode(WAARDE_ELEMENT_8710, null));

        final Lo3Categorie<Lo3InschrijvingInhoud> categorieInhoudCategorie =
                new Lo3Categorie<>(inschrijvingInhoud, null, null, nullHistorie, new Lo3Herkomst(categorie, 0, 0));

        categorieInhoudLijst.add(categorieInhoudCategorie);
        return new Lo3Stapel<>(categorieInhoudLijst);
    }

    private Lo3Stapel<Lo3VerblijfplaatsInhoud> vulCategorieVerblijfplaats(final List<Lo3CategorieWaarde> categorieen) {

        this.categorie = Lo3CategorieEnum.CATEGORIE_08;
        this.waardeElement8310 = "080000";
        final Lo3Onderzoek onderzoek = maakOnderzoek();
        final Lo3Historie historie = maakHistorie();
        final Lo3Documentatie documentatie = maakDocumentatie();

        final Lo3CategorieWaarde categorieWaarde = new Lo3CategorieWaarde(categorie, 1, 1);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0910, WAARDE_ELEMENT_0910);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0920, WAARDE_ELEMENT_0920);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_1010, WAARDE_ELEMENT_1010);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_1020, WAARDE_ELEMENT_1020);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_1030, WAARDE_ELEMENT_1030);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_1110, WAARDE_ELEMENT_1110);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_1115, WAARDE_ELEMENT_1115);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_1120, WAARDE_ELEMENT_1120);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_1130, WAARDE_ELEMENT_1130);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_1140, WAARDE_ELEMENT_1140);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_1150, WAARDE_ELEMENT_1150);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_1160, WAARDE_ELEMENT_1160);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_1170, WAARDE_ELEMENT_1170);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_1180, WAARDE_ELEMENT_1180);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_1190, WAARDE_ELEMENT_1190);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_1210, WAARDE_ELEMENT_1210);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_1310, WAARDE_ELEMENT_1310);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_1320, WAARDE_ELEMENT_1320);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_1330, WAARDE_ELEMENT_1330);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_1340, WAARDE_ELEMENT_1340);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_1350, WAARDE_ELEMENT_1350);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_1410, WAARDE_ELEMENT_1410);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_1420, WAARDE_ELEMENT_1420);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_7210, WAARDE_ELEMENT_7210);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_7510, WAARDE_ELEMENT_7510);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8310, waardeElement8310);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8320, WAARDE_ELEMENT_8320);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8330, WAARDE_ELEMENT_8330);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8410, WAARDE_ELEMENT_8410);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8510, WAARDE_ELEMENT_8510);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8610, WAARDE_ELEMENT_8610);

        categorieen.add(categorieWaarde);

        final List<Lo3Categorie<Lo3VerblijfplaatsInhoud>> categorieInhoudLijst = new ArrayList<>();
        final Lo3VerblijfplaatsInhoud verblijfplaatsInhoud =
                new Lo3VerblijfplaatsInhoud(
                    new Lo3GemeenteCode(WAARDE_ELEMENT_0910, onderzoek),
                    maakDatum(WAARDE_ELEMENT_0920, onderzoek),
                    new Lo3FunctieAdres(WAARDE_ELEMENT_1010, onderzoek),
                    maakLo3String(WAARDE_ELEMENT_1020, onderzoek),
                    maakDatum(WAARDE_ELEMENT_1030, onderzoek),
                    maakLo3String(WAARDE_ELEMENT_1110, onderzoek),
                    maakLo3String(WAARDE_ELEMENT_1115, onderzoek),
                    new Lo3Huisnummer(WAARDE_ELEMENT_1120, onderzoek),
                    maakLo3Character(WAARDE_ELEMENT_1130, onderzoek),
                    maakLo3String(WAARDE_ELEMENT_1140, onderzoek),
                    new Lo3AanduidingHuisnummer(WAARDE_ELEMENT_1150, onderzoek),
                    maakLo3String(WAARDE_ELEMENT_1160, onderzoek),
                    maakLo3String(WAARDE_ELEMENT_1170, onderzoek),
                    maakLo3String(WAARDE_ELEMENT_1180, onderzoek),
                    maakLo3String(WAARDE_ELEMENT_1190, onderzoek),
                    maakLo3String(WAARDE_ELEMENT_1210, onderzoek),
                    new Lo3LandCode(WAARDE_ELEMENT_1310, onderzoek),
                    maakDatum(WAARDE_ELEMENT_1320, onderzoek),
                    maakLo3String(WAARDE_ELEMENT_1330, onderzoek),
                    maakLo3String(WAARDE_ELEMENT_1340, onderzoek),
                    maakLo3String(WAARDE_ELEMENT_1350, onderzoek),
                    new Lo3LandCode(WAARDE_ELEMENT_1410, onderzoek),
                    maakDatum(WAARDE_ELEMENT_1420, onderzoek),
                    new Lo3AangifteAdreshouding(WAARDE_ELEMENT_7210),
                    new Lo3IndicatieDocument(WAARDE_ELEMENT_7510, null));

        final Lo3Categorie<Lo3VerblijfplaatsInhoud> categorieInhoudCategorie =
                new Lo3Categorie<>(verblijfplaatsInhoud, documentatie, onderzoek, historie, new Lo3Herkomst(categorie, 0, 0));

        categorieInhoudLijst.add(categorieInhoudCategorie);
        return new Lo3Stapel<>(categorieInhoudLijst);
    }

    private Lo3Stapel<Lo3KindInhoud> vulCategorieKind(final List<Lo3CategorieWaarde> categorieen) {
        this.categorie = Lo3CategorieEnum.CATEGORIE_09;
        this.waardeElement8310 = "090000";
        final Lo3Onderzoek onderzoek = maakOnderzoek();
        final Lo3Historie historie = maakHistorie();
        final Lo3Documentatie documentatie = maakDocumentatie();

        final Lo3CategorieWaarde categorieWaarde = new Lo3CategorieWaarde(categorie, 1, 1);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0110, WAARDE_ELEMENT_0110);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0120, WAARDE_ELEMENT_0120);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0210, WAARDE_ELEMENT_0210);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0220, WAARDE_ELEMENT_0220);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0230, WAARDE_ELEMENT_0230);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0240, WAARDE_ELEMENT_0240);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0310, WAARDE_ELEMENT_0310);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0320, WAARDE_ELEMENT_0320);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_0330, WAARDE_ELEMENT_0330);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8110, WAARDE_ELEMENT_8110);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8120, WAARDE_ELEMENT_8120);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8210, WAARDE_ELEMENT_8210);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8220, WAARDE_ELEMENT_8220);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8230, WAARDE_ELEMENT_8230);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8310, waardeElement8310);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8320, WAARDE_ELEMENT_8320);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8330, WAARDE_ELEMENT_8330);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8410, WAARDE_ELEMENT_8410);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8510, WAARDE_ELEMENT_8510);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8610, WAARDE_ELEMENT_8610);

        categorieen.add(categorieWaarde);

        final List<Lo3Categorie<Lo3KindInhoud>> categorieInhoudLijst = new ArrayList<>();
        final Lo3KindInhoud kindInhoud =
                new Lo3KindInhoud(
                    maakLo3Long(WAARDE_ELEMENT_0110, onderzoek),
                    maakLo3Integer(WAARDE_ELEMENT_0120, onderzoek),
                    maakLo3String(WAARDE_ELEMENT_0210, onderzoek),
                    new Lo3AdellijkeTitelPredikaatCode(WAARDE_ELEMENT_0220, onderzoek),
                    maakLo3String(WAARDE_ELEMENT_0230, onderzoek),
                    maakLo3String(WAARDE_ELEMENT_0240, onderzoek),
                    maakDatum(WAARDE_ELEMENT_0310, onderzoek),
                    new Lo3GemeenteCode(WAARDE_ELEMENT_0320, onderzoek),
                    new Lo3LandCode(WAARDE_ELEMENT_0330, onderzoek));

        final Lo3Categorie<Lo3KindInhoud> categorieInhoudCategorie =
                new Lo3Categorie<>(kindInhoud, documentatie, onderzoek, historie, new Lo3Herkomst(categorie, 0, 0));

        categorieInhoudLijst.add(categorieInhoudCategorie);
        return new Lo3Stapel<>(categorieInhoudLijst);
    }

    private Lo3Stapel<Lo3VerblijfstitelInhoud> vulCategorieVerblijfstitel(final List<Lo3CategorieWaarde> categorieen) {
        this.categorie = Lo3CategorieEnum.CATEGORIE_10;
        this.waardeElement8310 = "100000";
        final Lo3Onderzoek onderzoek = maakOnderzoek();
        final Lo3Historie historie = maakHistorie();
        final Lo3Documentatie documentatie = maakDocumentatie();

        final Lo3CategorieWaarde categorieWaarde = new Lo3CategorieWaarde(categorie, 1, 1);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_3910, WAARDE_ELEMENT_3910);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_3920, WAARDE_ELEMENT_3920);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_3930, WAARDE_ELEMENT_3930);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8310, waardeElement8310);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8320, WAARDE_ELEMENT_8320);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8330, WAARDE_ELEMENT_8330);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8410, WAARDE_ELEMENT_8410);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8510, WAARDE_ELEMENT_8510);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8610, WAARDE_ELEMENT_8610);

        categorieen.add(categorieWaarde);

        final List<Lo3Categorie<Lo3VerblijfstitelInhoud>> categorieInhoudLijst = new ArrayList<>();

        final Lo3VerblijfstitelInhoud verblijfstitelInhoud =
                new Lo3VerblijfstitelInhoud(
                    new Lo3AanduidingVerblijfstitelCode(WAARDE_ELEMENT_3910, onderzoek),
                    maakDatum(WAARDE_ELEMENT_3920, onderzoek),
                    maakDatum(WAARDE_ELEMENT_3930, onderzoek));

        final Lo3Categorie<Lo3VerblijfstitelInhoud> categorieInhoudCategorie =
                new Lo3Categorie<>(verblijfstitelInhoud, documentatie, onderzoek, historie, new Lo3Herkomst(categorie, 0, 0));

        categorieInhoudLijst.add(categorieInhoudCategorie);
        return new Lo3Stapel<>(categorieInhoudLijst);
    }

    private Lo3Stapel<Lo3GezagsverhoudingInhoud> vulCategorieGezagsverhouding(final List<Lo3CategorieWaarde> categorieen) {
        this.categorie = Lo3CategorieEnum.CATEGORIE_11;
        this.waardeElement8310 = "110000";
        final Lo3Onderzoek onderzoek = maakOnderzoek();
        final Lo3Historie historie = maakHistorie();
        final Lo3Documentatie documentatie = maakDocumentatie();

        final Lo3CategorieWaarde categorieWaarde = new Lo3CategorieWaarde(categorie, 1, 1);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_3210, WAARDE_ELEMENT_3210);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_3310, WAARDE_ELEMENT_3310);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8210, WAARDE_ELEMENT_8210);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8220, WAARDE_ELEMENT_8220);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8230, WAARDE_ELEMENT_8230);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8310, waardeElement8310);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8320, WAARDE_ELEMENT_8320);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8330, WAARDE_ELEMENT_8330);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8410, WAARDE_ELEMENT_8410);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8510, WAARDE_ELEMENT_8510);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8610, WAARDE_ELEMENT_8610);

        categorieen.add(categorieWaarde);

        final List<Lo3Categorie<Lo3GezagsverhoudingInhoud>> categorieInhoudLijst = new ArrayList<>();

        final Lo3GezagsverhoudingInhoud gezagsverhoudingInhoud =
                new Lo3GezagsverhoudingInhoud(
                    new Lo3IndicatieGezagMinderjarige(WAARDE_ELEMENT_3210, onderzoek),
                    new Lo3IndicatieCurateleregister(WAARDE_ELEMENT_3310, onderzoek));

        final Lo3Categorie<Lo3GezagsverhoudingInhoud> categorieInhoudCategorie =
                new Lo3Categorie<>(gezagsverhoudingInhoud, documentatie, onderzoek, historie, new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_11, 0, 0));

        categorieInhoudLijst.add(categorieInhoudCategorie);
        return new Lo3Stapel<>(categorieInhoudLijst);
    }

    private Lo3Stapel<Lo3ReisdocumentInhoud> vulCategorieReisdocument(final List<Lo3CategorieWaarde> categorieen) {
        this.categorie = Lo3CategorieEnum.CATEGORIE_12;
        this.waardeElement8310 = "120000";
        final Lo3Onderzoek onderzoek = maakOnderzoek();
        final Lo3Historie historie = maakHistorie();
        final Lo3Documentatie documentatie = maakDocumentatie();

        final Lo3CategorieWaarde categorieWaarde = new Lo3CategorieWaarde(categorie, 1, 1);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_3510, WAARDE_ELEMENT_3510);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_3520, WAARDE_ELEMENT_3520);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_3530, WAARDE_ELEMENT_3530);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_3540, WAARDE_ELEMENT_3540);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_3550, WAARDE_ELEMENT_3550);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_3560, WAARDE_ELEMENT_3560);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_3570, WAARDE_ELEMENT_3570);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_3580, WAARDE_ELEMENT_3580);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_3610, WAARDE_ELEMENT_3610);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8210, WAARDE_ELEMENT_8210);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8220, WAARDE_ELEMENT_8220);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8230, WAARDE_ELEMENT_8230);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8310, waardeElement8310);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8320, WAARDE_ELEMENT_8320);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8330, WAARDE_ELEMENT_8330);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8510, WAARDE_ELEMENT_8510);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8610, WAARDE_ELEMENT_8610);

        categorieen.add(categorieWaarde);

        final List<Lo3Categorie<Lo3ReisdocumentInhoud>> categorieInhoudLijst = new ArrayList<>();

        final Lo3ReisdocumentInhoud reisdocumentInhoud =
                new Lo3ReisdocumentInhoud(
                    new Lo3SoortNederlandsReisdocument(WAARDE_ELEMENT_3510, onderzoek),
                    maakLo3String(WAARDE_ELEMENT_3520, onderzoek),
                    maakDatum(WAARDE_ELEMENT_3530, onderzoek),
                    new Lo3AutoriteitVanAfgifteNederlandsReisdocument(WAARDE_ELEMENT_3540, onderzoek),
                    maakDatum(WAARDE_ELEMENT_3550, onderzoek),
                    maakDatum(WAARDE_ELEMENT_3560, onderzoek),
                    new Lo3AanduidingInhoudingVermissingNederlandsReisdocument(WAARDE_ELEMENT_3570, onderzoek),
                    new Lo3Signalering(WAARDE_ELEMENT_3610, onderzoek));

        final Lo3Categorie<Lo3ReisdocumentInhoud> categorieInhoudCategorie =
                new Lo3Categorie<>(reisdocumentInhoud, documentatie, onderzoek, historie, new Lo3Herkomst(categorie, 0, 0));

        categorieInhoudLijst.add(categorieInhoudCategorie);
        return new Lo3Stapel<>(categorieInhoudLijst);
    }

    private Lo3Stapel<Lo3KiesrechtInhoud> vulCategorieKiesrecht(final List<Lo3CategorieWaarde> categorieen) {
        this.categorie = Lo3CategorieEnum.CATEGORIE_13;
        this.waardeElement8310 = "130000";
        final Lo3Documentatie documentatie = maakDocumentatie();

        final Lo3CategorieWaarde categorieWaarde = new Lo3CategorieWaarde(categorie, 1, 1);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_3110, WAARDE_ELEMENT_3110);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_3120, WAARDE_ELEMENT_3120);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_3130, WAARDE_ELEMENT_3130);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_3810, WAARDE_ELEMENT_3810);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_3820, WAARDE_ELEMENT_3820);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8210, WAARDE_ELEMENT_8210);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8220, WAARDE_ELEMENT_8220);
        categorieWaarde.addElement(Lo3ElementEnum.ELEMENT_8230, WAARDE_ELEMENT_8230);

        categorieen.add(categorieWaarde);

        final Lo3Historie nullHistorie = Lo3Historie.NULL_HISTORIE;
        final List<Lo3Categorie<Lo3KiesrechtInhoud>> categorieInhoudLijst = new ArrayList<>();

        final Lo3KiesrechtInhoud gezagsverhoudingInhoud =
                new Lo3KiesrechtInhoud(
                    new Lo3AanduidingEuropeesKiesrecht(WAARDE_ELEMENT_3110, null),
                    maakDatum(WAARDE_ELEMENT_3120, null),
                    maakDatum(WAARDE_ELEMENT_3130, null),
                    new Lo3AanduidingUitgeslotenKiesrecht(WAARDE_ELEMENT_3810),
                    maakDatum(WAARDE_ELEMENT_3820, null));

        final Lo3Categorie<Lo3KiesrechtInhoud> categorieInhoudCategorie =
                new Lo3Categorie<>(gezagsverhoudingInhoud, documentatie, null, nullHistorie, new Lo3Herkomst(categorie, 0, 0));

        categorieInhoudLijst.add(categorieInhoudCategorie);
        return new Lo3Stapel<>(categorieInhoudLijst);
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.bericht.model.lo3.parser.AbstractParserTest#getGegevensInOnderzoek()
     */
    @Override
    String getGegevensInOnderzoek() {
        return waardeElement8310;
    }

    /*
     * (non-Javadoc)
     * 
     * @see nl.bzk.migratiebrp.bericht.model.lo3.parser.AbstractParserTest#getCategorie()
     */
    @Override
    Lo3CategorieEnum getCategorie() {
        return categorie;
    }
}
