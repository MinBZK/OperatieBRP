/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa;

import java.util.Date;

import nl.bzk.brp.model.operationeel.kern.PersistentPersoonNationaliteit;

/**
 * Custom uitbreiding van de JPA PersoonNationaliteitRepository.
 */
public interface PersoonNationaliteitRepositoryCustom {

    /**
     * Persisteer de historie voor het argument persoonNationaliteit. Hierbij wordt ook rekening gehouden met
     * overlappende materiele historie etc.
     *
     * @param persoonNationaliteit De (nieuwe) persoon nationaliteit die zojuist is toegevoegd.
     * @param datumAanvangGeldigheid Datum wanneer persoonNationaliteit ingaat.
     * @param datumEindeGeldigheid Eventuele datum wanneer persoonNationaliteit niet meer geldt.
     * @param registratieTijd Tijd dat persoonNationaliteit is geregistreerd.
     */
    void persisteerHistorie(final PersistentPersoonNationaliteit persoonNationaliteit,
                                   final Integer datumAanvangGeldigheid,
                                   final Integer datumEindeGeldigheid,
                                   final Date registratieTijd);
}
