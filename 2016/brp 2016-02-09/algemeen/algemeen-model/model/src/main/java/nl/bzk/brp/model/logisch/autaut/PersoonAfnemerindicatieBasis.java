/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.autaut;

import javax.annotation.Generated;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.LeveringsautorisatieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.logisch.kern.Persoon;

/**
 * De indicatie dat een persoon wordt gevolgd door een afnemer in kader van een abonnement.
 *
 * Partijen (lees: afnemers) kunnen zich 'abonneren' op een persoon. Dit is normaliter in kader van een specifiek
 * leveringsautorisatie; voorzien is echter dat niet in alle gevallen het is te relateren aan precies één leveringsautorisatie.
 *
 * Aangaande de verantwoording is het volgende besluit genomen (mei 2014):
 *
 * Voor bijhoudingen/mutaties in de BRP kan er onderscheid gemaakt worden tussen mutaties die de Persoonslijst raken,
 * mutaties die overige gegevens van of gerelateerd aan een persoon raken niet behorende tot de Persoonslijst (denk aan
 * afnemerindicaties) en mutaties die niet persoonsgerelateerde gegevens raken (denk aan stamgegevens, authenticatie en
 * autorisatie gegevens etc.). Voor deze drie categorieën kun je onderscheid maken in hoe wijzigingen hiervan moeten
 * worden verantwoord/gelogd (audit trail).
 *
 * Voor de eerste categorie, wijzigingen op de Persoonslijst, is het standaard mechanisme van acties en handelingen (en
 * bijbehorende bronnen, documenten, rechtsgronden en eventueel gedeblokkeerde meldingen) van toepassing. Voor de tweede
 * categorie, gegevens gerelateerd aan een persoon maar die niet tot de Persoonslijst behoren (afnemerindicaties) hoeft
 * er geen gebruik gemaakt te worden van het standaard verantwoordingsmechanisme (dus geen acties en handelingen
 * benodigd). Deze kunnen eventueel functioneel gelogd worden, maar eventueel ook middels opname extra attributen op
 * betreffende groep. Voor de derde categorie, gegevens die niet gerelateerd zijn aan een persoon (bijv. stamgegevens en
 * autorisaties door beheerders) dient gebruik gemaakt te worden van een functionele logging (audit trail) waarbij het
 * initieel niet vereist is dat dit in de database dient te worden opgeslagen en/of eenvoudig bevraagd kan worden
 * (afhankelijk van eisen vanuit beheer).
 *
 * Ten aanzien van het middels berichtenverkeer muteren van gegevens waarvoor niet gebruik gemaakt hoeft te worden van
 * het standaard verantwoordingsmechanisme (dus o.a. afnemerindicaties) kan/mag in het bericht wel het mechanisme
 * administratieve handeling en actie worden ingezet. De handeling en actie uit het bericht dienen dan alleen niet
 * opgeslagen te worden en fungeren dus puur als herkenbaar en gebruikelijk vehikel voor mutaties in berichten —> Alleen
 * handelingen die daadwerkelijk betrekking hebben op gegevens op de PL worden gepersisteerd. Dus handelingen over
 * indicaties niet. Er is wel behoefte aan enige verantwoordingsinfo over indicaties. Het gebruik van het standaard
 * verantwoordingsmechnisme is hiervoor niet noodzakelijk en zelfs niet gewenst (zie opmerking t.a.v. tweede categorie
 * bij eerste punt). Dit zou prima kunnen met het standaard tijdstip registratie en verval (formele historie) in
 * combinatie met een enum die aangeeft 'vanuit welk proces geplaatst' en een andere die aangeeft 'vanuit welk proces
 * verwijderd'. Mogelijke waarden: direct door afnemer, selectie, attendering (oid?) Voor wat betreft overige informatie
 * die gemuteerd wordt, denk aan autorisaties door beheerders etc, hiervoor lijkt ons functionele logging het aangewezen
 * middel (Zie ook opmerking t.a.v. derde categorie bij eerste punt).
 *
 * Dus we gaan bij het plaatsen van afnemerindicaties geen handeling en/of actie opslaan, maar wel nog een of twee
 * attributen toevoegen waarin we de reden/dienst opslaan die er toe heeft geleid dat de indicatie is opgenomen of
 * verwijderd.
 *
 * Voor de implementatie is hier een nieuwe categorie verantwoording gemaakt. Immers, het toevoegen van de twee velden
 * zorgt er voor dat de velden niet uitsluitend in de C/D laag terecht komen, maar ook in de A laag en dat is niet
 * gewenst.
 *
 *
 *
 */
@Generated(value = "nl.bzk.brp.generatoren.java.LogischModelGenerator")
public interface PersoonAfnemerindicatieBasis extends BrpObject {

    /**
     * Retourneert Persoon van Persoon \ Afnemerindicatie.
     *
     * @return Persoon.
     */
    Persoon getPersoon();

    /**
     * Retourneert Afnemer van Persoon \ Afnemerindicatie.
     *
     * @return Afnemer.
     */
    PartijAttribuut getAfnemer();

    /**
     * Retourneert Leveringsautorisatie van Persoon \ Afnemerindicatie.
     *
     * @return Leveringsautorisatie.
     */
    LeveringsautorisatieAttribuut getLeveringsautorisatie();

    /**
     * Retourneert Standaard van Persoon \ Afnemerindicatie.
     *
     * @return Standaard.
     */
    PersoonAfnemerindicatieStandaardGroep getStandaard();

}
