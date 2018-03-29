/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import nl.bzk.brp.bijhouding.bericht.annotation.XmlElementRegister;

/**
 * Deze class bevat een lijst met alle implementaties van ActieElement. Deze wordt gebruikt door
 * {@link nl.bzk.brp.bijhouding.bericht.annotation.XmlElementInterface}.
 */
public final class ActieRegister implements XmlElementRegister {

    /**
     * De lijst met implementaties van {@link ActieElement}.
     */
    private static final List<Class<?>> ACTIE_ELEMENTEN =
            Arrays.asList(
                    new Class<?>[]{RegistratieAanvangHuwelijkActieElement.class,
                            RegistratieAanvangGeregistreerdPartnerschapActieElement.class,
                            RegistratieEindeGeregistreerdPartnerschapActieElement.class,
                            RegistratieEindeHuwelijkActieElement.class,
                            RegistratieGeslachtsnaamActieElement.class,
                            RegistratieNaamgebruikActieElement.class,
                            RegistratieAdresActieElement.class,
                            RegistratieMigratieActieElement.class,
                            RegistratieVerstrekkingsbeperkingActieElement.class,
                            RegistratieBijzondereVerblijfsrechtelijkePositieActieElement.class,
                            BeeindigingBijzondereVerblijfsrechtelijkePositieActieElement.class,
                            RegistratieVerblijfsrechtActieElement.class,
                            RegistratieGeboreneActieElement.class,
                            RegistratieIdentificatienummersActieElement.class,
                            RegistratieNationaliteitActieElement.class,
                            RegistratieStaatloosActieElement.class,
                            CorrectieVervalRelatieActieElement.class,
                            CorrectieRegistratieRelatieActieElement.class,
                            VervalHuwelijkActieElement.class,
                            VervalGeregistreerdPartnerschapActieElement.class,
                            RegistratieOuderActieElement.class,
                            RegistratieGeslachtsnaamVoornaamActieElement.class,
                            BeeindigingNationaliteitActieElement.class,
                            RegistratieIdentificatienummersGerelateerdeActieElement.class,
                            RegistratieSamengesteldeNaamGerelateerdeActieElement.class,
                            RegistratieGeboorteGerelateerdeActieElement.class,
                            RegistratieGeslachtsaanduidingGerelateerdeActieElement.class,
                            RegistratieAanvangOnderzoekActieElement.class,
                            RegistratieWijzigingOnderzoekActieElement.class,
                            CorrectieVervalIdentificatienummersGerelateerde.class,
                            CorrectieRegistratieIdentificatienummersRegistratieGegevensGerelateerde.class,
                            CorrectieVervalSamengesteldeNaamGerelateerde.class,
                            CorrectieRegistratieSamengesteldeNaamRegistratieGegevensGerelateerde.class,
                            CorrectieVervalGeboorteGerelateerde.class,
                            CorrectieRegistratieGeboorteRegistratieGegevensGerelateerde.class,
                            CorrectieVervalGeslachtsaanduidingGerelateerde.class,
                            CorrectieRegistratieGeslachtsaanduidingRegistratieGegevensGerelateerde.class});

    @Override
    public List<Class<?>> getImplementaties() {
        return Collections.unmodifiableList(ACTIE_ELEMENTEN);
    }
}
