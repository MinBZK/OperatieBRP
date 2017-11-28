/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.util.List;
import java.util.Map;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlChildList;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlElement;

/**
 * Het element dat een relatie vanuit het perspectief van een Persoon representeerd.
 */
@XmlElement("persoon")
public final class PersoonRelatieElement extends AbstractPersoonElement {

    @XmlChildList(listElementType = BetrokkenheidElement.class)
    private final List<BetrokkenheidElement> betrokkenheden;

    /**
     * Maakt een PersoonRelatie object.
     * @param attributen de lijst met attributen
     * @param afgeleidAdministratief afgeleid administratief
     * @param identificatienummers identificatienummers
     * @param samengesteldeNaam samengesteldeNaam
     * @param geboorte geboorte
     * @param geslachtsaanduiding geslachtsaanduiding
     * @param bijhouding bijhouding
     * @param verblijfsrecht verblijfsrecht
     * @param voornamen voornamen
     * @param geslachtsnaamcomponenten geslachtsnaamcomponenten
     * @param naamgebruik naamgebruik
     * @param adressen adres elementen
     * @param indicaties indicaties
     * @param verstrekkingsbeperkingen verstrekkingsbeperkingen
     * @param migratie MigratieElement
     * @param nationaliteiten nationaliteiten
     * @param onderzoeken onderzoeken
     * @param betrokkenheden betrokkenheden van deze relatie.
     */
    public PersoonRelatieElement(final Map<String, String> attributen,
                                 final AfgeleidAdministratiefElement afgeleidAdministratief,
                                 final IdentificatienummersElement identificatienummers,
                                 final SamengesteldeNaamElement samengesteldeNaam,
                                 final GeboorteElement geboorte,
                                 final GeslachtsaanduidingElement geslachtsaanduiding,
                                 final BijhoudingElement bijhouding,
                                 final VerblijfsrechtElement verblijfsrecht,
                                 final List<VoornaamElement> voornamen,
                                 final List<GeslachtsnaamcomponentElement> geslachtsnaamcomponenten,
                                 final NaamgebruikElement naamgebruik,
                                 final List<AdresElement> adressen,
                                 final List<IndicatieElement> indicaties,
                                 final List<VerstrekkingsbeperkingElement> verstrekkingsbeperkingen,
                                 final MigratieElement migratie,
                                 final List<NationaliteitElement> nationaliteiten,
                                 final List<OnderzoekElement> onderzoeken,
                                 final List<BetrokkenheidElement> betrokkenheden) {
        super(attributen, afgeleidAdministratief, identificatienummers, samengesteldeNaam, geboorte, geslachtsaanduiding, bijhouding, verblijfsrecht, voornamen,
                geslachtsnaamcomponenten, naamgebruik, adressen, indicaties, verstrekkingsbeperkingen, migratie, nationaliteiten, onderzoeken);
        this.betrokkenheden = betrokkenheden;
    }

    @Override
    protected List<MeldingElement> valideerInhoud() {
        return VALIDATIE_OK;
    }

    public List<BetrokkenheidElement> getBetrokkenheden() {
        return betrokkenheden;
    }

    @Override
    public boolean bevatGerelateerdeGegevens() {
        return false;
    }
}
