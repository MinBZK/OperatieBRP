/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.bericht.basis;

import nl.bzk.brp.model.attribuuttype.AdellijkeTitelCode;
import nl.bzk.brp.model.attribuuttype.GeslachtsnaamComponent;
import nl.bzk.brp.model.attribuuttype.PredikaatCode;
import nl.bzk.brp.model.attribuuttype.ScheidingsTeken;
import nl.bzk.brp.model.attribuuttype.Voorvoegsel;
import nl.bzk.brp.model.basis.AbstractGroepBericht;
import nl.bzk.brp.model.groep.logisch.basis.PersoonGeslachtsnaamCompStandaardGroepBasis;
import nl.bzk.brp.model.objecttype.operationeel.statisch.AdellijkeTitel;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Predikaat;


/**
 * Implementatie standaard groep object type Persoon Geslachtsnaam component.
 */
@SuppressWarnings("serial")
public abstract class AbstractPersoonGeslachtsnaamCompStandaardGroepBericht extends AbstractGroepBericht implements
        PersoonGeslachtsnaamCompStandaardGroepBasis
{

    private Predikaat              predikaat;
    private PredikaatCode          predikaatCode;
    private AdellijkeTitel         adellijkeTitel;
    private AdellijkeTitelCode     adellijkeTitelCode;
    private Voorvoegsel            voorvoegsel;
    private ScheidingsTeken        scheidingsteken;
    private GeslachtsnaamComponent geslachtsnaamComponent;

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
    public ScheidingsTeken getScheidingsteken() {
        return scheidingsteken;
    }

    @Override
    public GeslachtsnaamComponent getNaam() {
        return geslachtsnaamComponent;
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

    public void setScheidingsteken(final ScheidingsTeken scheidingsteken) {
        this.scheidingsteken = scheidingsteken;
    }

    public void setGeslachtsnaamComponent(final GeslachtsnaamComponent geslachtsnaamComponent) {
        this.geslachtsnaamComponent = geslachtsnaamComponent;
    }

    public PredikaatCode getPredikaatCode() {
        return predikaatCode;
    }

    public AdellijkeTitelCode getAdellijkeTitelCode() {
        return adellijkeTitelCode;
    }

    public GeslachtsnaamComponent getGeslachtsnaamComponent() {
        return geslachtsnaamComponent;
    }
}
