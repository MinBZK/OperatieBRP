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
import nl.bzk.brp.model.algemeen.stamgegeven.kern.NaamgebruikAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PredicaatAttribuut;
import nl.bzk.brp.model.basis.Groep;

/**
 * 1. De gegevens over naamgebruik (voornamen , voorvoegsel etc etc) worden - gecontroleerd - redundant opgeslagen. De
 * reden hiervoor is dat er situaties zijn waarin een afnemer NIET geautoriseerd wordt voor de relaties die een persoon
 * heeft (en dus bijvoorbeeld NIET mag weten wie de partner is), terwijl de afnemer WEL geautoriseerd is voor de
 * gegevens over het naamgebruik, aangezien zij de persoon wel 'juist' moet kunnen aanschrijven. Tot en met het
 * operationele model zijn de gegevens (dus) redundant opgenomen; het is aan een DBA om te beslissen of ook in het
 * uiteindelijke technische model (dan wel de technische modellen) ook sprake is van redundantie, of dat één-en-ander
 * alleen wordt afgeleid.
 *
 * 2. De gegevens in de groep Naamgebruik lijken heel erg op de gegevens in de groep Samengestelde naam. De optie om de
 * groep Samengestelde naam uit te breiden met de gegevens uit de groep Naamgebruik (en deze laatste groep dus eigenlijk
 * te laten vervallen) is vervallen op grond van het volgende argument: het wijzigen van de Samengestelde (formele!)
 * naam is een formeel proces (o.a. de Koning dient de wijziging goed te keuren). De wijziging van Naamgebruik is
 * (veel!) minder formeel.
 *
 * 3. De motivatie voor de groep aanschrijving is als volgt: - Er is (vanuit afnemers) behoefte aan een éénduidige
 * vastlegging van de (wijze) van aanschrijving. Door stuurgroep is daarom besloten om hiertoe een gegevensgroep op te
 * nemen. - Er zijn een aantal situaties die de aanschrijving moet aankunnen, zoals: -- het (casuïstiek!) door de
 * rechter bekrachtigde verzoek voor de weduwe om de naam van haar VOORlaatste partner te mogen voeren. -- de door de
 * Hoge Raad van Adel erkende 'maatschappelijk gebruik' om de echtgenoot van de Baron met Barones aan te duiden, ondanks
 * het feit dat de echtgenote in kwestie zelf geen adellijke titel heeft. Er is voor gekozen om het RESULTAAT hiervan
 * vast te leggen (dus: 'adellijke titel te gebruiken bij aanschrijving'), in plaats van situatie-specifieke indicaties
 * ('indicatie gebruik titel partner' en 'indicatie gebruik naamgegevens voorlaatste ex-partner').
 *
 * 4. In geval van correcties e.d. is het bijhouden van de materiële historie relatief complex, doordat meerdere
 * factoren de (algoritmische) afleiding kunnen beïnvloeden. Tegelijkertijd is er geen business case voor het willen
 * weten "wat had de goede aanschrijving geweest voor x dagen geleden met de kennis van vandaag"; of te wel FORMELE
 * HISTORIE volstaat. Om die reden is de historie aangepast tot alleen formele historie.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.LogischModelGenerator")
public interface PersoonNaamgebruikGroepBasis extends Groep {

    /**
     * Retourneert Naamgebruik van Naamgebruik.
     *
     * @return Naamgebruik.
     */
    NaamgebruikAttribuut getNaamgebruik();

    /**
     * Retourneert Naamgebruik afgeleid? van Naamgebruik.
     *
     * @return Naamgebruik afgeleid?.
     */
    JaNeeAttribuut getIndicatieNaamgebruikAfgeleid();

    /**
     * Retourneert Predicaat naamgebruik van Naamgebruik.
     *
     * @return Predicaat naamgebruik.
     */
    PredicaatAttribuut getPredicaatNaamgebruik();

    /**
     * Retourneert Voornamen naamgebruik van Naamgebruik.
     *
     * @return Voornamen naamgebruik.
     */
    VoornamenAttribuut getVoornamenNaamgebruik();

    /**
     * Retourneert Adellijke titel naamgebruik van Naamgebruik.
     *
     * @return Adellijke titel naamgebruik.
     */
    AdellijkeTitelAttribuut getAdellijkeTitelNaamgebruik();

    /**
     * Retourneert Voorvoegsel naamgebruik van Naamgebruik.
     *
     * @return Voorvoegsel naamgebruik.
     */
    VoorvoegselAttribuut getVoorvoegselNaamgebruik();

    /**
     * Retourneert Scheidingsteken naamgebruik van Naamgebruik.
     *
     * @return Scheidingsteken naamgebruik.
     */
    ScheidingstekenAttribuut getScheidingstekenNaamgebruik();

    /**
     * Retourneert Geslachtsnaamstam naamgebruik van Naamgebruik.
     *
     * @return Geslachtsnaamstam naamgebruik.
     */
    GeslachtsnaamstamAttribuut getGeslachtsnaamstamNaamgebruik();

}
