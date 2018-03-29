/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.repository.stamgegevens.kern;

import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.brp.beheer.webapp.configuratie.annotations.Master;
import nl.bzk.brp.beheer.webapp.repository.ReadWriteRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Partij repository.
 */
@Master
public interface PartijRepository extends ReadWriteRepository<Partij, Short> {

    /**
     * Zoek de partij op partij code.
     * @param code De code waarop de partij wordt gezocht
     * @return De partij indien deze op basis van de code wordt gevonden, null indien geen partij wordt gevonden
     */
    Partij findByCode(String code);

    /**
     * Geeft de partijen terug die geldig zijn voor vrij bericht.
     * @param peildatum peildatum
     * @return de gevonden partijen
     */
    @Query("select p from Partij p where p.datumIngang <= :peildatum and (p.datumEinde > :peildatum or p.datumEinde is null) "
            + "and p.datumIngangVrijBericht <= :peildatum and (p.datumEindeVrijBericht > :peildatum or p.datumEindeVrijBericht is null) "
            + "and p.datumOvergangNaarBrp <= :peildatum "
            + "and p.isActueelEnGeldig = true and p.isActueelEnGeldigVoorVrijBericht = true "
            + "and (p.isVrijBerichtGeblokkeerd = false or p.isVrijBerichtGeblokkeerd is null)"
            + "and p.afleverpuntVrijBericht is not null")
    List<Partij> geldigeVrijBerichtPartijen(@Param("peildatum") Integer peildatum);
}
