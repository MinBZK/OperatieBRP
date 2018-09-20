/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.web.groep.impl.gen;
import nl.bzk.brp.model.attribuuttype.GeslachtsnaamComponent;
import nl.bzk.brp.model.attribuuttype.JaNee;
import nl.bzk.brp.model.attribuuttype.ScheidingsTeken;
import nl.bzk.brp.model.attribuuttype.Voornaam;
import nl.bzk.brp.model.attribuuttype.Voorvoegsel;
import nl.bzk.brp.model.groep.interfaces.gen.PersoonSamengesteldeNaamGroepBasis;
import nl.bzk.brp.model.objecttype.statisch.AdellijkeTitel;
import nl.bzk.brp.model.objecttype.statisch.Predikaat;
import nl.bzk.brp.model.web.AbstractGroepWeb;

/**
 * .
 */
@SuppressWarnings("serial")
public abstract class AbstractPersoonSamengesteldeNaamGroepWeb extends AbstractGroepWeb
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
        return predikaat;
    }

    @Override
    public AdellijkeTitel getAdellijkeTitel() {
        return adellijkeTitel;
    }

    @Override
    public Voornaam getVoornamen() {
        return voornamen;
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
    public GeslachtsnaamComponent getGeslachtsnaam() {
        return geslachtsnaam;
    }

    @Override
    public JaNee getIndNamenreeksAlsGeslachtsNaam() {
        return indNamenreeksAlsGeslachtsNaam;
    }

    @Override
    public JaNee getIndAlgorithmischAfgeleid() {
        return indAlgorithmischAfgeleid;
    }

}
