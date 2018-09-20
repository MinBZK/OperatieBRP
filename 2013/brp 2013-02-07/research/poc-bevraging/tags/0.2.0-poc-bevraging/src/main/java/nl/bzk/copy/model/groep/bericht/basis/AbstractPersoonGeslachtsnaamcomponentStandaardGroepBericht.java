/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.groep.bericht.basis;

import nl.bzk.copy.model.attribuuttype.*;
import nl.bzk.copy.model.basis.AbstractGroepBericht;
import nl.bzk.copy.model.groep.logisch.basis.PersoonGeslachtsnaamcomponentStandaardGroepBasis;
import nl.bzk.copy.model.objecttype.operationeel.statisch.AdellijkeTitel;
import nl.bzk.copy.model.objecttype.operationeel.statisch.Predikaat;


/**
 * Implementatie standaard groep object type Persoon Geslachtsnaam component.
 */
@SuppressWarnings("serial")
public abstract class AbstractPersoonGeslachtsnaamcomponentStandaardGroepBericht extends AbstractGroepBericht implements
        PersoonGeslachtsnaamcomponentStandaardGroepBasis
{

    private Predikaat predikaat;
    private PredikaatCode predikaatCode;
    private AdellijkeTitel adellijkeTitel;
    private AdellijkeTitelCode adellijkeTitelCode;
    private Voorvoegsel voorvoegsel;
    private Scheidingsteken scheidingsteken;
    private Geslachtsnaamcomponent geslachtsnaamcomponent;

    @Override
    public Predikaat getPredikaat() {
        return predikaat;
    }

    @Override
    public AdellijkeTitel getAdellijkeTitel() {
        return adellijkeTitel;
    }

    @Override
    public Voorvoegsel getVoorvoegsel() {
        return voorvoegsel;
    }

    @Override
    public Scheidingsteken getScheidingsteken() {
        return scheidingsteken;
    }

    @Override
    public Geslachtsnaamcomponent getNaam() {
        return geslachtsnaamcomponent;
    }

    public void setPredikaat(final Predikaat predikaat) {
        this.predikaat = predikaat;
    }

    public void setAdellijkeTitel(final AdellijkeTitel adellijkeTitel) {
        this.adellijkeTitel = adellijkeTitel;
    }

    public void setVoorvoegsel(final Voorvoegsel voorvoegsel) {
        this.voorvoegsel = voorvoegsel;
    }

    public void setScheidingsteken(final Scheidingsteken scheidingsteken) {
        this.scheidingsteken = scheidingsteken;
    }

    public void setGeslachtsnaamcomponent(final Geslachtsnaamcomponent geslachtsnaamcomponent) {
        this.geslachtsnaamcomponent = geslachtsnaamcomponent;
    }

    public PredikaatCode getPredikaatCode() {
        return predikaatCode;
    }

    public AdellijkeTitelCode getAdellijkeTitelCode() {
        return adellijkeTitelCode;
    }

    public Geslachtsnaamcomponent getGeslachtsnaamcomponent() {
        return geslachtsnaamcomponent;
    }
}
