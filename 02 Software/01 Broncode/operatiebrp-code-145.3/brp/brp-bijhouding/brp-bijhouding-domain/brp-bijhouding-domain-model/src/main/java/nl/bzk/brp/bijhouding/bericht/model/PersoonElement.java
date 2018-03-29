/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;

/**
 * Interface voor de verschillende implementaties van de XML representaties van een persoon.
 */
public interface PersoonElement extends BmrEntiteit<BijhoudingPersoon>, BmrGroepReferentie<PersoonElement> {

    /**
     * Controleert of dit element een verwijzing heeft een een {@link nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon}. Als dit element een objectsleutel
     * heeft, of een nieuw persoonis , dan is het antwoord true. Als dit element een referentie heeft naar een ander {@link PersoonElement}, dan wordt de
     * vraag doorgegeven aan dat element.
     * @return true als dit element een verwijzing heeft naar een bestaand of nieuw persoon.
     */
    boolean heeftPersoonEntiteit();

    /**
     * Geef de {@link Persoon} terug die hoort bij dit element.
     * @return de persoon entiteit
     */
    BijhoudingPersoon getPersoonEntiteit();

    /**
     * Geeft aan of er gerelateerde gegevens aanwezig zijn.
     * - identificatie nummers
     * - geslachtsaanduiding
     * - geboorte
     * -samengestelde naam
     * @return true indien 1 van de velden gevuld is
     */
    boolean bevatGerelateerdeGegevens();

    /**
     * Geef de waarde van identificatienummers.
     * @return identificatienummers
     */
    IdentificatienummersElement getIdentificatienummers();

    /**
     * Geef de waarde van samengesteldeNaam.
     * @return samengesteldeNaam
     */
    SamengesteldeNaamElement getSamengesteldeNaam();

    /**
     * Geef de waarde van geboorte.
     * @return geboorte
     */
    GeboorteElement getGeboorte();

    /**
     * Geef de waarde van geslachtsaanduiding.
     * @return geslachtsaanduiding
     */
    GeslachtsaanduidingElement getGeslachtsaanduiding();

    /**
     * Geef de waarde van voornamen.
     * @return voornamen
     */
    List<VoornaamElement> getVoornamen();

    /**
     * Geef de waarde van geslachtsnaamcomponent.
     * @return geslachtsnaamcomponent
     */
    List<GeslachtsnaamcomponentElement> getGeslachtsnaamcomponenten();

    /**
     * Geeft lijst met Adressen terug.
     * @return lijst met adressen
     */
    List<AdresElement> getAdressen();

    /**
     * Geeft het eerste adres in de lijst met adressen terug.
     * vanuit de xsd wordt bij een verhuising afgedwongen dat maar 1 adres kan worden meegegeven.
     * @return AdresElement
     */
    AdresElement getAdres();

    /**
     * Geeft het migratieterug.
     * @return MigratieElement
     */
    MigratieElement getMigratie();

    /**
     * Geeft lijst met indicaties terug.
     * @return lijst met indicaties
     */
    List<IndicatieElement> getIndicaties();

    /**
     * Geeft lijst met verstrekkingsbeperkingen terug.
     * @return lijst met verstrekkingsbeperkingen
     */
    List<VerstrekkingsbeperkingElement> getVerstrekkingsbeperkingen();

    /**
     * Geef de waarde van naamgebruik.
     * @return naamgebruik
     */
    NaamgebruikElement getNaamgebruik();

    /**
     * Geeft de afgeleid administratief terug.
     * @return afgeleid administratief
     */
    AfgeleidAdministratiefElement getAfgeleidAdministratief();

    /**
     * Geeft de nationaliteiten terug.
     * @return de nationaliteiten
     */
    List<NationaliteitElement> getNationaliteiten();

    /**
     * Geeft aan of dit element 1 of meerdere {@link NationaliteitElement} bevat.
     * @return true als dit element 1 of meerdere {@link NationaliteitElement} bevat.
     */
    boolean heeftNationaliteiten();
    /**
     * Geeft de eerste nationaliteit terug uit de lijst van nationaliteiten. Als de {@link PersoonElement} geen nationaliteiten bevat en deze methode wordt
     * aangeroepen, dan levert dit een {@link IllegalStateException} op
     * @return de eerste nationaliteit uit de lijst of een {@link IllegalStateException} als er geen nationaliteiten zijn
     */
    NationaliteitElement getNationaliteit();

    /**
     * Geeft de bijhouding terug.
     * @return bijhouding
     */
    BijhoudingElement getBijhouding();

    /**
     * Geeft de verblijfsrecht terug.
     * @return verblijfsrecht
     */
    VerblijfsrechtElement getVerblijfsrecht();

}
