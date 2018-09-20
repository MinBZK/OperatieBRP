/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern.basis;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.Geslachtsnaam;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNee;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Scheidingsteken;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Voornamen;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Voorvoegsel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AdellijkeTitel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Predikaat;
import nl.bzk.brp.model.basis.Groep;


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
 * Generator: nl.bzk.brp.generatoren.java.LogischModelGenerator.
 * Generator versie: 1.0-SNAPSHOT.
 * Metaregister versie: 1.6.0.
 * Gegenereerd op: Tue Jan 15 12:53:52 CET 2013.
 */
public interface PersoonSamengesteldeNaamGroepBasis extends Groep {

    /**
     * Retourneert Algoritmisch afgeleid? van Samengestelde naam.
     *
     * @return Algoritmisch afgeleid?.
     */
    JaNee getIndicatieAlgoritmischAfgeleid();

    /**
     * Retourneert Namenreeks? van Samengestelde naam.
     *
     * @return Namenreeks?.
     */
    JaNee getIndicatieNamenreeks();

    /**
     * Retourneert Predikaat van Samengestelde naam.
     *
     * @return Predikaat.
     */
    Predikaat getPredikaat();

    /**
     * Retourneert Voornamen van Samengestelde naam.
     *
     * @return Voornamen.
     */
    Voornamen getVoornamen();

    /**
     * Retourneert Adellijke titel van Samengestelde naam.
     *
     * @return Adellijke titel.
     */
    AdellijkeTitel getAdellijkeTitel();

    /**
     * Retourneert Voorvoegsel van Samengestelde naam.
     *
     * @return Voorvoegsel.
     */
    Voorvoegsel getVoorvoegsel();

    /**
     * Retourneert Scheidingsteken van Samengestelde naam.
     *
     * @return Scheidingsteken.
     */
    Scheidingsteken getScheidingsteken();

    /**
     * Retourneert Geslachtsnaam van Samengestelde naam.
     *
     * @return Geslachtsnaam.
     */
    Geslachtsnaam getGeslachtsnaam();

}
