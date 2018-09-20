/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.groep.impl.gen;

import nl.bzk.brp.model.attribuuttype.GeslachtsnaamComponent;
import nl.bzk.brp.model.attribuuttype.ScheidingsTeken;
import nl.bzk.brp.model.attribuuttype.Voorvoegsel;
import nl.bzk.brp.model.groep.interfaces.gen.PersoonGeslachtsnaamCompStandaardGroepBasis;
import nl.bzk.brp.model.objecttype.statisch.AdellijkeTitel;
import nl.bzk.brp.model.objecttype.statisch.Predikaat;
import nl.bzk.brp.web.AbstractGroepWeb;

/**
 * Implementatie standaard groep object type Persoon Geslachtsnaam component.
 */
public abstract class AbstractPersoonGeslachtsnaamCompStandaardGroepWeb extends AbstractGroepWeb
        implements PersoonGeslachtsnaamCompStandaardGroepBasis
{

    private Predikaat predikaat;
    private AdellijkeTitel adellijkeTitel;
    private Voorvoegsel voorvoegsel;
    private ScheidingsTeken scheidingsteken;
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
}
