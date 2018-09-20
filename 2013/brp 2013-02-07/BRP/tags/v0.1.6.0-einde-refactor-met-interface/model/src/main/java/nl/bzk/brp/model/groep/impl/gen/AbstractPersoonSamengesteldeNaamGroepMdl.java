/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.impl.gen;

import nl.bzk.brp.model.attribuuttype.GeslachtsnaamComponent;
import nl.bzk.brp.model.attribuuttype.JaNee;
import nl.bzk.brp.model.attribuuttype.ScheidingsTeken;
import nl.bzk.brp.model.attribuuttype.Voornaam;
import nl.bzk.brp.model.attribuuttype.Voorvoegsel;
import nl.bzk.brp.model.basis.AbstractGroep;
import nl.bzk.brp.model.groep.interfaces.gen.PersoonSamengesteldeNaamGroepBasis;
import nl.bzk.brp.model.objecttype.statisch.AdellijkeTitel;
import nl.bzk.brp.model.objecttype.statisch.Predikaat;
/**
 * .
 *
 */
public abstract class AbstractPersoonSamengesteldeNaamGroepMdl extends AbstractGroep
    implements PersoonSamengesteldeNaamGroepBasis
{
    private Predikaat predikaat;
    private AdellijkeTitel adellijkeTitel;
    private Voornaam voornamen;
    private Voorvoegsel voorvoegsel;
    private ScheidingsTeken scheidingsteken;
    private GeslachtsnaamComponent geslachtsnaam;
    private JaNee indNamenreeksAlsGeslachtsNaam;
    private JaNee indAlgorithmischAfgeleid;



    @Override
    public Predikaat getPredikaat() {
        return this.predikaat;
    }

    @Override
    public AdellijkeTitel getAdellijkeTitel() {
        return this.adellijkeTitel;
    }

    @Override
    public Voornaam getVoornamen() {
        return this.voornamen;
    }

    @Override
    public Voorvoegsel getVoorvoegsel() {
        return this.voorvoegsel;
    }

    @Override
    public ScheidingsTeken getScheidingsteken() {
        return this.scheidingsteken;
    }

    @Override
    public GeslachtsnaamComponent getGeslachtsnaam() {
        return this.geslachtsnaam;
    }

    @Override
    public JaNee getIndNamenreeksAlsGeslachtsNaam() {
        return this.indNamenreeksAlsGeslachtsNaam;
    }

    @Override
    public JaNee getIndAlgorithmischAfgeleid() {
        return this.indAlgorithmischAfgeleid;
    }

}
