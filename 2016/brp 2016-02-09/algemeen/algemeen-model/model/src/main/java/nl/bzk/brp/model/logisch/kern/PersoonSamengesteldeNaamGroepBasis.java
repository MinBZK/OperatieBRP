/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.kern;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.GeslachtsnaamstamAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.ScheidingstekenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoornamenAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.VoorvoegselAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.AdellijkeTitelAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PredicaatAttribuut;
import nl.bzk.brp.model.basis.Groep;

/**
 * De naam zoals die ontstaat door samenvoegen van alle exemplaren van Voornaam en van Geslachtsnaamcomponent van een
 * Persoon.
 *
 * De Samengestelde naam is vrijwel altijd via een algoritme af te leiden uit de exemplaren van Voornaam en
 * Geslachtsnaamcomponent van een Persoon. In uitzonderingssituaties is dat niet mogelijk.
 *
 * De groep Samengestelde naam bevat de naam zoals die is opgebouwd uit de naamgegevens uit de groepen voornaam en
 * geslachtsnaamcomponent. Deze samengestelde gegevens hoeven bij het bijhouden van de groepen voornaam en
 * geslachtsnaamcomponent niet door de voor de bijhouding verantwoordelijke partij te worden ingevoerd. De centrale
 * voorzieningen stellen de gegevens uit de groep samengestelde naam op dat moment samen op basis van de groepen
 * voornaam en geslachtsnaamcomponent volgens het onderstaande voorschrift:
 *
 * Voornamen - de naam zoals opgenomen in de voornaam met volgnummer één, gevolgd de naam zoals opgenomen in de actuele
 * groep voornaam met volgnummer twee, enzovoort. De voornamen worden gescheiden door een spatie. Merk op dat de BRP is
 * voorbereid op het opnemen van voornamen als 'Jan Peter' of 'Aberto di Maria' of 'Wonder op aarde' als één enkele
 * voornaam; in de BRP is het namelijk niet nodig (om conform LO 3.x) de verschillende worden aan elkaar te plakken met
 * een koppelteken. Predicaat - het predicaat dat door de persoon gevoerd wordt voor diens voornaam. Dit komt overeen
 * met het predicaat van de eerste geslachtsnaamcomponent. Indien voor een persoon meerdere predikaten van toepassing
 * is, het predikaar dat voor de voornamen geplaatst mag worden. Adelijke titel - de adelijke titel zoals opgenomen in
 * geslachtsnaamcomponent met volgnummer gelijk aan '1'; Voorvoegsel - het voorvoegsel zoals opgenomen in de
 * geslachtsnaamcomponent met volgnummer gelijk aan '1'; Scheidingsteken - het scheidingsteken zoals opgenomen in de
 * geslachtsnaamcomponent met volgnummer '1'; Geslachtsnaam - bestaande uit de samenvoeging van alle
 * geslachtsnaamcomponenten, inclusief predikaten die niet voor de voornamen worden geplaatst. Ook eventuele adellijke
 * titels die niet voor de gehele geslachtsnaam wordt geplaatst, worden hierin opgenomen.
 *
 * Verplicht aanwezig bij persoon
 *
 * Historie: beide vormen van historie, aangezien de samengestelde naam ook kan wijzigen ZONDER dat er sprake is van
 * terugwerkende kracht (met andere woorden: 'vanaf vandaag heet ik...' ipv 'en deze moet met terugwerkende kracht
 * gelden vanaf de geboorte').
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.LogischModelGenerator")
public interface PersoonSamengesteldeNaamGroepBasis extends Groep {

    /**
     * Retourneert Afgeleid? van Samengestelde naam.
     *
     * @return Afgeleid?.
     */
    JaNeeAttribuut getIndicatieAfgeleid();

    /**
     * Retourneert Namenreeks? van Samengestelde naam.
     *
     * @return Namenreeks?.
     */
    JaNeeAttribuut getIndicatieNamenreeks();

    /**
     * Retourneert Predicaat van Samengestelde naam.
     *
     * @return Predicaat.
     */
    PredicaatAttribuut getPredicaat();

    /**
     * Retourneert Voornamen van Samengestelde naam.
     *
     * @return Voornamen.
     */
    VoornamenAttribuut getVoornamen();

    /**
     * Retourneert Adellijke titel van Samengestelde naam.
     *
     * @return Adellijke titel.
     */
    AdellijkeTitelAttribuut getAdellijkeTitel();

    /**
     * Retourneert Voorvoegsel van Samengestelde naam.
     *
     * @return Voorvoegsel.
     */
    VoorvoegselAttribuut getVoorvoegsel();

    /**
     * Retourneert Scheidingsteken van Samengestelde naam.
     *
     * @return Scheidingsteken.
     */
    ScheidingstekenAttribuut getScheidingsteken();

    /**
     * Retourneert Geslachtsnaamstam van Samengestelde naam.
     *
     * @return Geslachtsnaamstam.
     */
    GeslachtsnaamstamAttribuut getGeslachtsnaamstam();

}
