/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import java.util.List;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.operationeel.kern.HisOnderzoekModel;
import nl.bzk.brp.model.operationeel.kern.PersoonOnderzoekModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Spring data jpa repository voor persoon / onderzoek.
 */
public interface PersoonOnderzoekSpringDataRepository extends JpaRepository<PersoonOnderzoekModel, Integer> {

    /**
     * Haalt een lijst op van onderzoeken voor een persoon, rekening houdend met historie
     * en de einddatum van een onderzoek. (zou in de toekomst kunnen liggen)
     * Datum aanvang wordt niet meegenomen, want is niet relevant. Het gaat om de twijfel die
     * is ontstaan over het gegeven, niet om wanneer het onderzoek is begonnen.
     * (oftewel: zou nooit in de toekomst kunnen / mogen beginnen)
     *
     * @param persoonId id van persoon.
     * @param vandaag de datum van vandaag, gebruik hiervoor DatumAttribuut.vandaag()
     * @return lijst met onderzoeken.
     */
    @Query("SELECT hisOnderzoek "
         + "FROM HisOnderzoekModel hisOnderzoek, PersoonOnderzoekModel persoonOnderzoek "
         + "WHERE hisOnderzoek.formeleHistorie.datumTijdVerval IS NULL "
         + "AND (hisOnderzoek.datumEinde IS NULL OR hisOnderzoek.datumEinde > ?2) "
         + "AND persoonOnderzoek.persoon.id = ?1 "
         + "AND persoonOnderzoek.onderzoek.id = hisOnderzoek.onderzoek.id")
    List<HisOnderzoekModel> vindOnderzoekenVoorPersoon(final Integer persoonId,
                                                       final DatumEvtDeelsOnbekendAttribuut vandaag);

}
