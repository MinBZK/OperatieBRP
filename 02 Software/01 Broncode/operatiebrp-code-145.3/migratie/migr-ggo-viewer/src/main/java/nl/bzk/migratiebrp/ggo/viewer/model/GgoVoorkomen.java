/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.Serializable;
import java.util.Map;

/**
 * Key welke gebruikt wordt om een GgoCategorie te identificeren.
 */
public class GgoVoorkomen implements Serializable {

    private static final long serialVersionUID = 1L;

    private String aNummer;
    /**
     * CategorieNr voor weergave. We maken een onderscheid tussen het categorieNr dat gebruikt wordt in de weergave, en
     * de categorie die gebruikt wordt voor de herkomst, omdat laatstgenoemde in de terugconversie anders kan zijn
     * (historisch / niet historisch). N.B. dit gegeven wordt alleen gevuld / gebruikt aan de Lo3 zijde. Zou daarom ook
     * in eigen subclass kunnen.
     */
    private int categorieLabelNr;
    private int categorieNr;
    private int stapelNr;
    private int voorkomenNr;
    private String label;
    private String datumAanvangGeldigheid;
    private boolean vervallen;
    private Map<String, String> inhoud;

    /**
     * Constructor.
     */
    public GgoVoorkomen() {
        super();
        vervallen = false;
    }

    /**
     * @return the aNummer
     */
    public final String getaNummer() {
        return aNummer;
    }

    /**
     * @param aNummer the aNummer to set
     */
    public final void setaNummer(final String aNummer) {
        this.aNummer = aNummer;
    }

    /**
     * Gets the categorieNr voor weergave.
     * @return the categorieLabelNr
     */
    public final int getCategorieLabelNr() {
        return categorieLabelNr;
    }

    /**
     * Sets the categorieNr voor weergave.
     * @param categorieLabelNr the categorieLabelNr to set
     */
    public final void setCategorieLabelNr(final int categorieLabelNr) {
        this.categorieLabelNr = categorieLabelNr;
    }

    /**
     * Geef de waarde van categorie nr.
     * @return the categorieNr
     */
    public final int getCategorieNr() {
        return categorieNr;
    }

    /**
     * Zet de waarde van categorie nr.
     * @param categorieNr the categorieNr to set
     */
    public final void setCategorieNr(final int categorieNr) {
        this.categorieNr = categorieNr;
    }

    /**
     * Geef de waarde van stapel nr.
     * @return the stapelNr
     */
    public final int getStapelNr() {
        return stapelNr;
    }

    /**
     * Zet de waarde van stapel nr.
     * @param stapelNr the stapelNr to set
     */
    public final void setStapelNr(final int stapelNr) {
        this.stapelNr = stapelNr;
    }

    /**
     * Geef de waarde van voorkomen nr.
     * @return the voorkomenNr
     */
    public final int getVoorkomenNr() {
        return voorkomenNr;
    }

    /**
     * Zet de waarde van voorkomen nr.
     * @param voorkomenNr the voorkomenNr to set
     */
    public final void setVoorkomenNr(final int voorkomenNr) {
        this.voorkomenNr = voorkomenNr;
    }

    /**
     * Geef de waarde van label.
     * @return the label
     */
    public final String getLabel() {
        return label;
    }

    /**
     * Zet de waarde van label.
     * @param label the label to set
     */
    public final void setLabel(final String label) {
        this.label = label;
    }

    /**
     * Zet de waarde van datum aanvang geldigheid.
     * @param datumAanvangGeldigheid datum aanvang geldigheid.
     */
    public final void setDatumAanvangGeldigheid(final String datumAanvangGeldigheid) {
        this.datumAanvangGeldigheid = datumAanvangGeldigheid;
    }

    /**
     * Geef de waarde van datum aanvang geldigheid.
     * @return datum aanvang geldigheid.
     */
    public final String getDatumAanvangGeldigheid() {
        return datumAanvangGeldigheid;
    }

    /**
     * Geef de vervallen.
     * @return De vervallen indicatie tbv de weergave.
     */
    public final boolean isVervallen() {
        return vervallen;
    }

    /**
     * Zet de waarde van vervallen.
     * @param vervallen Is dit voorkomen vervallen? (Indicatie onjuist in Lo3, vervaldatum in BRP)
     */
    public final void setVervallen(final boolean vervallen) {
        this.vervallen = vervallen;
    }

    /**
     * Geef de waarde van inhoud.
     * @return De inhoud van deze categorie / dit voorkomen, in de vorm van een key / value map.
     */
    public final Map<String, String> getInhoud() {
        return inhoud;
    }

    /**
     * Sets the inhoud.
     * @param inhoud De complete inhoud van dit voorkomen ineens.
     */
    public final void setInhoud(final Map<String, String> inhoud) {
        this.inhoud = inhoud;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public final String toString() {
        final ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (final JsonProcessingException e) {
            return super.toString();
        }
    }
}
