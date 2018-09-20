/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.lo3.impl;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import nl.moderniseringgba.isc.esb.message.BerichtInhoudException;
import nl.moderniseringgba.isc.esb.message.lo3.AbstractLo3Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3Bericht;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3Header;
import nl.moderniseringgba.isc.esb.message.lo3.Lo3HeaderVeld;
import nl.moderniseringgba.isc.esb.message.lo3.format.Lo3Format;
import nl.moderniseringgba.isc.esb.message.lo3.parser.Parser;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3CategorieEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3ElementEnum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3AdellijkeTitelPredikaatCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3Datum;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3LandCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.moderniseringgba.migratie.conversie.model.lo3.syntax.Lo3CategorieWaardeUtil;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Wa01 bericht.
 */
public final class Wa01Bericht extends AbstractLo3Bericht implements Lo3Bericht, Serializable {
    private static final long serialVersionUID = 1L;

    private static final Lo3Header HEADER = new Lo3Header(Lo3HeaderVeld.RANDOM_KEY, Lo3HeaderVeld.BERICHTNUMMER,
            Lo3HeaderVeld.HERHALING, Lo3HeaderVeld.A_NUMMER, Lo3HeaderVeld.DATUM);

    private Long aNummer;
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
        super(HEADER);
    }

    @Override
    public String getBerichtType() {
        return "Wa01";
    }

    @Override
    public String getStartCyclus() {
        return "uc312";
    }

    /* ************************************************************************************************************* */

    @Override
    protected void parseInhoud(final List<Lo3CategorieWaarde> categorieen) throws BerichtInhoudException {
        // Categorie 01
        final Lo3CategorieWaarde categorie01 =
                Lo3CategorieWaardeUtil.getCategorieVoorkomen(categorieen, Lo3CategorieEnum.CATEGORIE_01, 0, 0);
        if (categorie01 != null) {
            aNummer = Parser.parseLong(categorie01.getElement(Lo3ElementEnum.ELEMENT_0110));
            voornamen = categorie01.getElement(Lo3ElementEnum.ELEMENT_0210);
            adellijkeTitelPredikaatCode =
                    Parser.parseLo3AdellijkeTitelPredikaatCode(categorie01.getElement(Lo3ElementEnum.ELEMENT_0220));
            voorvoegselGeslachtsnaam = categorie01.getElement(Lo3ElementEnum.ELEMENT_0230);
            geslachtsnaam = categorie01.getElement(Lo3ElementEnum.ELEMENT_0240);
            geboortedatum = Parser.parseLo3Datum(categorie01.getElement(Lo3ElementEnum.ELEMENT_0310));
            geboorteGemeenteCode = Parser.parseLo3GemeenteCode(categorie01.getElement(Lo3ElementEnum.ELEMENT_0320));
            geboorteLandCode = Parser.parseLo3LandCode(categorie01.getElement(Lo3ElementEnum.ELEMENT_0330));
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

        return categorie01.isEmpty() ? Collections.<Lo3CategorieWaarde>emptyList() : Arrays.asList(categorie01);
    }

    /* ************************************************************************************************************* */

    /**
     * @return nieuw a-nummer
     */
    public Long getNieuwANummer() {
        return Parser.parseLong(getHeader(Lo3HeaderVeld.A_NUMMER));
    }

    /**
     * zet nieuw a-nummer.
     * 
     * @param aNummer
     *            a-nummer
     */
    public void setNieuwANummer(final Long aNummer) {
        setHeader(Lo3HeaderVeld.A_NUMMER, Lo3Format.format(aNummer));
    }

    /**
     * @return datum geldigheid
     */
    public Lo3Datum getDatumGeldigheid() {
        return Parser.parseLo3Datum(getHeader(Lo3HeaderVeld.DATUM));
    }

    /**
     * zet datum geldigheid.
     * 
     * @param datum
     *            datum geldigheid
     */
    public void setDatumGeldigheid(final Lo3Datum datum) {
        setHeader(Lo3HeaderVeld.DATUM, Lo3Format.format(datum));
    }

    /**
     * @return oud a-nummer
     */
    public Long getOudANummer() {
        return aNummer;
    }

    /**
     * zet oud a-nummer.
     * 
     * @param aNummer
     *            a-nummer
     */
    public void setOudANummer(final Long aNummer) {
        this.aNummer = aNummer;
    }

    public String getVoornamen() {
        return voornamen;
    }

    public void setVoornamen(final String voornamen) {
        this.voornamen = voornamen;
    }

    public Lo3AdellijkeTitelPredikaatCode getAdellijkeTitelPredikaatCode() {
        return adellijkeTitelPredikaatCode;
    }

    public void setAdellijkeTitelPredikaatCode(final Lo3AdellijkeTitelPredikaatCode adellijkeTitelPredikaatCode) {
        this.adellijkeTitelPredikaatCode = adellijkeTitelPredikaatCode;
    }

    public String getVoorvoegselGeslachtsnaam() {
        return voorvoegselGeslachtsnaam;
    }

    public void setVoorvoegselGeslachtsnaam(final String voorvoegselGeslachtsnaam) {
        this.voorvoegselGeslachtsnaam = voorvoegselGeslachtsnaam;
    }

    public String getGeslachtsnaam() {
        return geslachtsnaam;
    }

    public void setGeslachtsnaam(final String geslachtsnaam) {
        this.geslachtsnaam = geslachtsnaam;
    }

    public Lo3Datum getGeboortedatum() {
        return geboortedatum;
    }

    public void setGeboortedatum(final Lo3Datum geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    public Lo3GemeenteCode getGeboorteGemeenteCode() {
        return geboorteGemeenteCode;
    }

    public void setGeboorteGemeenteCode(final Lo3GemeenteCode geboorteGemeenteCode) {
        this.geboorteGemeenteCode = geboorteGemeenteCode;
    }

    public Lo3LandCode getGeboorteLandCode() {
        return geboorteLandCode;
    }

    public void setGeboorteLandCode(final Lo3LandCode geboorteLandCode) {
        this.geboorteLandCode = geboorteLandCode;
    }

    /* ************************************************************************************************************* */

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Wa01Bericht)) {
            return false;
        }
        final Wa01Bericht castOther = (Wa01Bericht) other;
        return new EqualsBuilder().appendSuper(super.equals(other)).append(aNummer, castOther.aNummer)
                .append(voornamen, castOther.voornamen)
                .append(adellijkeTitelPredikaatCode, castOther.adellijkeTitelPredikaatCode)
                .append(voorvoegselGeslachtsnaam, castOther.voorvoegselGeslachtsnaam)
                .append(geslachtsnaam, castOther.geslachtsnaam).append(geboortedatum, castOther.geboortedatum)
                .append(geboorteGemeenteCode, castOther.geboorteGemeenteCode)
                .append(geboorteLandCode, castOther.geboorteLandCode).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().appendSuper(super.hashCode()).append(aNummer).append(voornamen)
                .append(adellijkeTitelPredikaatCode).append(voorvoegselGeslachtsnaam).append(geslachtsnaam)
                .append(geboortedatum).append(geboorteGemeenteCode).append(geboorteLandCode).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).appendSuper(super.toString())
                .append("aNummer", aNummer).append("voornamen", voornamen)
                .append("adellijkeTitelPredikaatCode", adellijkeTitelPredikaatCode)
                .append("voorvoegselGeslachtsnaam", voorvoegselGeslachtsnaam).append("geslachtsnaam", geslachtsnaam)
                .append("geboortedatum", geboortedatum).append("geboorteGemeenteCode", geboorteGemeenteCode)
                .append("geboorteLandCode", geboorteLandCode).toString();
    }

}
