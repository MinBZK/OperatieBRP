/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.gedeeld;

import nl.bzk.brp.model.logisch.AbstractIdentificerendeGroep;

/**
 * Een gemeenschappelijk object dat de titel, predikaat, voorvoegsel, scheidingsteken en naam bevat.
 *
 */
public abstract class AbstractSamengesteldeNaam extends AbstractIdentificerendeGroep {

    private String geslachtsnaam;
    private String voornamen;
    private String voorvoegsel;
    private String scheidingsTeken;
    private Predikaat predikaat;

    /**
     * @return het geslachtsnaam
     */
    public String getGeslachtsnaam() {
        return geslachtsnaam;
    }

    /**
     * @param geslachtsnaam het geslachtsnaam
     */
    public void setGeslachtsnaam(final String geslachtsnaam) {
        this.geslachtsnaam = geslachtsnaam;
    }

    public String getVoorvoegsel() {
        return voorvoegsel;
    }

    public void setVoorvoegsel(final String voorvoegsel) {
        this.voorvoegsel = voorvoegsel;
    }

    /**
     * @return het scheidingsTeken
     */
    public String getScheidingsTeken() {
        return scheidingsTeken;
    }

    /**
     * @param scheidingsTeken het scheidingsTeken
     */
    public void setScheidingsTeken(final String scheidingsTeken) {
        this.scheidingsTeken = scheidingsTeken;
    }

    /**
     * @return de predikaat
     */
    public Predikaat getPredikaat() {
        return predikaat;
    }

    /**
     * @param predikaat de predikaat
     */
    public void setPredikaat(final Predikaat predikaat) {
        this.predikaat = predikaat;
    }

    /**
     * @return the voornamen
     */
    public String getVoornamen() {
        return voornamen;
    }

    /**
     * @param voornamen the voornamen to set
     */
    public void setVoornamen(final String voornamen) {
        this.voornamen = voornamen;
    }

}
