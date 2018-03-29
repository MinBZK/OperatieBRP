/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.bericht.model.lo3.impl;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import nl.bzk.migratiebrp.bericht.model.BerichtInhoudException;
import nl.bzk.migratiebrp.bericht.model.lo3.AbstractParsedLo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Header;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3HeaderVeld;
import nl.bzk.migratiebrp.bericht.model.lo3.format.Lo3Format;
import nl.bzk.migratiebrp.bericht.model.lo3.parser.Parser;
import nl.bzk.migratiebrp.bericht.model.lo3.parser.SimpleParser;
import nl.bzk.migratiebrp.bericht.model.lo3.syntax.Lo3SyntaxControle;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaardeUtil;

/**
 * Wa01 bericht.
 */
public final class Wa01Bericht extends AbstractParsedLo3Bericht implements Lo3Bericht, Serializable {
    private static final long serialVersionUID = 1L;

    private static final Lo3Header HEADER =
            new Lo3Header(Lo3HeaderVeld.RANDOM_KEY, Lo3HeaderVeld.BERICHTNUMMER, Lo3HeaderVeld.HERHALING, Lo3HeaderVeld.A_NUMMER, Lo3HeaderVeld.DATUM);

    private String aNummer;
    private String voornamen;
    private Lo3AdellijkeTitelPredikaatCode adellijkeTitelPredikaatCode;
    private String voorvoegselGeslachtsnaam;
    private String geslachtsnaam;
    private Lo3Datum geboortedatum;
    private Lo3GemeenteCode geboorteGemeenteCode;
    private Lo3LandCode geboorteLandCode;

    /**
     * Constructor.
     */
    public Wa01Bericht() {
        super(HEADER, Lo3SyntaxControle.STANDAARD, "Wa01", "uc312");
    }

    /*
     * (non-Javadoc)
     *
     * @see nl.bzk.migratiebrp.bericht.model.lo3.AbstractLo3Bericht#getGerelateerdeAnummers()
     */
    @Override
    protected List<String> getGerelateerdeAnummers() {
        return Arrays.asList(aNummer, getHeaderWaarde(Lo3HeaderVeld.A_NUMMER));
    }

    /*
     * *********************************************************************************************
     * ****************
     */

    @Override
    protected void parseCategorieen(final List<Lo3CategorieWaarde> categorieen) throws BerichtInhoudException {
        // Categorie 01
        final Lo3CategorieWaarde categorie01 = Lo3CategorieWaardeUtil.getCategorieVoorkomen(categorieen, Lo3CategorieEnum.CATEGORIE_01, 0, 0);
        if (categorie01 != null) {
            aNummer = categorie01.getElement(Lo3ElementEnum.ELEMENT_0110);
            voornamen = categorie01.getElement(Lo3ElementEnum.ELEMENT_0210);
            adellijkeTitelPredikaatCode =
                    Parser.parseLo3AdellijkeTitelPredikaatCode(categorie01.getElementen(), Lo3ElementEnum.ELEMENT_0220, Lo3CategorieEnum.CATEGORIE_01, null);
            voorvoegselGeslachtsnaam = categorie01.getElement(Lo3ElementEnum.ELEMENT_0230);
            geslachtsnaam = categorie01.getElement(Lo3ElementEnum.ELEMENT_0240);
            geboortedatum = Parser.parseLo3Datum(categorie01.getElementen(), Lo3ElementEnum.ELEMENT_0310, Lo3CategorieEnum.CATEGORIE_01, null);
            geboorteGemeenteCode = Parser.parseLo3GemeenteCode(categorie01.getElementen(), Lo3ElementEnum.ELEMENT_0320, Lo3CategorieEnum.CATEGORIE_01, null);
            geboorteLandCode = Parser.parseLo3LandCode(categorie01.getElementen(), Lo3ElementEnum.ELEMENT_0330, Lo3CategorieEnum.CATEGORIE_01, null);
        }
    }

    @Override
    protected List<Lo3CategorieWaarde> formatInhoud() {
        // Categorie 01
        final Lo3CategorieWaarde categorie01 = new Lo3CategorieWaarde(Lo3CategorieEnum.CATEGORIE_01, 0, 0);
        categorie01.addElement(Lo3ElementEnum.ELEMENT_0110, Lo3Format.format(aNummer));
        categorie01.addElement(Lo3ElementEnum.ELEMENT_0210, voornamen);
        categorie01.addElement(Lo3ElementEnum.ELEMENT_0220, Lo3Format.format(adellijkeTitelPredikaatCode));
        categorie01.addElement(Lo3ElementEnum.ELEMENT_0230, voorvoegselGeslachtsnaam);
        categorie01.addElement(Lo3ElementEnum.ELEMENT_0240, geslachtsnaam);
        categorie01.addElement(Lo3ElementEnum.ELEMENT_0310, Lo3Format.format(geboortedatum));
        categorie01.addElement(Lo3ElementEnum.ELEMENT_0320, Lo3Format.format(geboorteGemeenteCode));
        categorie01.addElement(Lo3ElementEnum.ELEMENT_0330, Lo3Format.format(geboorteLandCode));

        return categorie01.isEmpty() ? Collections.emptyList() : Collections.singletonList(categorie01);
    }

    /*
     * *********************************************************************************************
     * ****************
     */

    /**
     * Geef de waarde van nieuw a nummer.
     * @return nieuw a-nummer
     */
    public String getNieuwANummer() {
        return getHeaderWaarde(Lo3HeaderVeld.A_NUMMER);
    }

    /**
     * zet nieuw a-nummer.
     * @param nieuwANummer a-nummer
     */
    public void setNieuwANummer(final String nieuwANummer) {
        setHeader(Lo3HeaderVeld.A_NUMMER, nieuwANummer);
    }

    /**
     * Geef de waarde van datum geldigheid.
     * @return datum geldigheid
     */
    public Lo3Datum getDatumGeldigheid() {
        return SimpleParser.parseLo3Datum(getHeaderWaarde(Lo3HeaderVeld.DATUM));
    }

    /**
     * zet datum geldigheid.
     * @param datum datum geldigheid
     */
    public void setDatumGeldigheid(final Lo3Datum datum) {
        setHeader(Lo3HeaderVeld.DATUM, Lo3Format.format(datum));
    }

    /**
     * Geef de waarde van oud a nummer.
     * @return oud a-nummer
     */
    public String getOudANummer() {
        return aNummer;
    }

    /**
     * zet oud a-nummer.
     * @param oudANummer a-nummer
     */
    public void setOudANummer(final String oudANummer) {
        aNummer = oudANummer;
    }

    /**
     * Geef de waarde van voornamen.
     * @return voornamen
     */
    public String getVoornamen() {
        return voornamen;
    }

    /**
     * Zet de waarde van voornamen.
     * @param voornamen voornamen
     */
    public void setVoornamen(final String voornamen) {
        this.voornamen = voornamen;
    }

    /**
     * Geef de waarde van adellijke titel predikaat code.
     * @return adellijke titel predikaat code
     */
    public Lo3AdellijkeTitelPredikaatCode getAdellijkeTitelPredikaatCode() {
        return adellijkeTitelPredikaatCode;
    }

    /**
     * Zet de waarde van adellijke titel predikaat code.
     * @param adellijkeTitelPredikaatCode adellijke titel predikaat code
     */
    public void setAdellijkeTitelPredikaatCode(final Lo3AdellijkeTitelPredikaatCode adellijkeTitelPredikaatCode) {
        this.adellijkeTitelPredikaatCode = adellijkeTitelPredikaatCode;
    }

    /**
     * Geef de waarde van voorvoegsel geslachtsnaam.
     * @return voorvoegsel geslachtsnaam
     */
    public String getVoorvoegselGeslachtsnaam() {
        return voorvoegselGeslachtsnaam;
    }

    /**
     * Zet de waarde van voorvoegsel geslachtsnaam.
     * @param voorvoegselGeslachtsnaam voorvoegsel geslachtsnaam
     */
    public void setVoorvoegselGeslachtsnaam(final String voorvoegselGeslachtsnaam) {
        this.voorvoegselGeslachtsnaam = voorvoegselGeslachtsnaam;
    }

    /**
     * Geef de waarde van geslachtsnaam.
     * @return geslachtsnaam
     */
    public String getGeslachtsnaam() {
        return geslachtsnaam;
    }

    /**
     * Zet de waarde van geslachtsnaam.
     * @param geslachtsnaam geslachtsnaam
     */
    public void setGeslachtsnaam(final String geslachtsnaam) {
        this.geslachtsnaam = geslachtsnaam;
    }

    /**
     * Geef de waarde van geboortedatum.
     * @return geboortedatum
     */
    public Lo3Datum getGeboortedatum() {
        return geboortedatum;
    }

    /**
     * Zet de waarde van geboortedatum.
     * @param geboortedatum geboortedatum
     */
    public void setGeboortedatum(final Lo3Datum geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    /**
     * Geef de waarde van geboorte gemeente code.
     * @return geboorte gemeente code
     */
    public Lo3GemeenteCode getGeboorteGemeenteCode() {
        return geboorteGemeenteCode;
    }

    /**
     * Zet de waarde van geboorte gemeente code.
     * @param geboorteGemeenteCode geboorte gemeente code
     */
    public void setGeboorteGemeenteCode(final Lo3GemeenteCode geboorteGemeenteCode) {
        this.geboorteGemeenteCode = geboorteGemeenteCode;
    }

    /**
     * Geef de waarde van geboorte land code.
     * @return geboorte land code
     */
    public Lo3LandCode getGeboorteLandCode() {
        return geboorteLandCode;
    }

    /**
     * Zet de waarde van geboorte land code.
     * @param geboorteLandCode geboorte land code
     */
    public void setGeboorteLandCode(final Lo3LandCode geboorteLandCode) {
        this.geboorteLandCode = geboorteLandCode;
    }
}
