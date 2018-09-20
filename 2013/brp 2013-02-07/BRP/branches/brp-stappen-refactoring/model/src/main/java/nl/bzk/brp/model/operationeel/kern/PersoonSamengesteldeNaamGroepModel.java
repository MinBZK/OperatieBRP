/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.operationeel.kern;

import javax.persistence.Embeddable;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Geslachtsnaam;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNee;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Scheidingsteken;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Voornamen;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Voorvoegsel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AdellijkeTitel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Predikaat;
import nl.bzk.brp.model.logisch.kern.PersoonSamengesteldeNaamGroep;
import nl.bzk.brp.model.operationeel.kern.basis.AbstractPersoonSamengesteldeNaamGroepModel;


/**
 * De naam zoals die ontstaat door samenvoegen van alle exemplaren van Voornaam en van Geslachtsnaamcomponent van een
 * Persoon.
 *
 * De Samengestelde naam is vrijwel altijd via een algoritme af te leiden uit de exemplaren van Voornaam en
 * Geslachtsnaamcomponent van een Persoon. In uitzonderingssituaties is dat niet mogelijk.
 *
 * Verplicht aanwezig bij persoon
 *
 * Historie: beide vormen van historie, aangezien de samengestelde naam ook kan wijzigen ZONDER dat er sprake is van
 * terugwerkende kracht (met andere woorden: 'vanaf vandaag heet ik...' ipv 'en deze moet met terugwerkende kracht
 * gelden vanaf de geboorte').
 * RvdP 9 jan 2012
 *
 *
 *
 * Generator: nl.bzk.brp.generatoren.java.OperationeelModelGenerator.
 * Metaregister versie: 1.1.8.
 * Generator versie: 1.0-SNAPSHOT.
 * Generator gebouwd op: 2012-11-27 12:02:51.
 * Gegenereerd op: Tue Nov 27 14:55:36 CET 2012.
 */
@Embeddable
public class PersoonSamengesteldeNaamGroepModel extends AbstractPersoonSamengesteldeNaamGroepModel implements
        PersoonSamengesteldeNaamGroep
{

    /**
     * Standaard constructor (t.b.v. Hibernate/JPA).
     *
     */
    protected PersoonSamengesteldeNaamGroepModel() {
        super();
    }

    /**
     * Basis constructor die direct alle velden instantieert.
     *
     * @param indicatieAlgoritmischAfgeleid indicatieAlgoritmischAfgeleid van Samengestelde naam.
     * @param indicatieNamenreeks indicatieNamenreeks van Samengestelde naam.
     * @param predikaat predikaat van Samengestelde naam.
     * @param voornamen voornamen van Samengestelde naam.
     * @param adellijkeTitel adellijkeTitel van Samengestelde naam.
     * @param voorvoegsel voorvoegsel van Samengestelde naam.
     * @param scheidingsteken scheidingsteken van Samengestelde naam.
     * @param geslachtsnaam geslachtsnaam van Samengestelde naam.
     */
    public PersoonSamengesteldeNaamGroepModel(final JaNee indicatieAlgoritmischAfgeleid,
            final JaNee indicatieNamenreeks, final Predikaat predikaat, final Voornamen voornamen,
            final AdellijkeTitel adellijkeTitel, final Voorvoegsel voorvoegsel, final Scheidingsteken scheidingsteken,
            final Geslachtsnaam geslachtsnaam)
    {
        super(indicatieAlgoritmischAfgeleid, indicatieNamenreeks, predikaat, voornamen, adellijkeTitel, voorvoegsel,
                scheidingsteken, geslachtsnaam);
    }

    /**
     * Copy constructor om vanuit het bericht model een instantie van het operationeel model te maken.
     *
     * @param persoonSamengesteldeNaamGroep te kopieren groep.
     */
    public PersoonSamengesteldeNaamGroepModel(final PersoonSamengesteldeNaamGroep persoonSamengesteldeNaamGroep) {
        super(persoonSamengesteldeNaamGroep);
    }

}
