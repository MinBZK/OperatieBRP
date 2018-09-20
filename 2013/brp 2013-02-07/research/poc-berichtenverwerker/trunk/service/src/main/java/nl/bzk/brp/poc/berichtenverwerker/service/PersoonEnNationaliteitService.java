/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.poc.berichtenverwerker.service;

import java.math.BigDecimal;
import java.util.Set;

import nl.bzk.brp.poc.berichtenverwerker.model.Actie;
import nl.bzk.brp.poc.berichtenverwerker.model.Bron;


/**
 * Service voor het toevoegen, verwijderen en aanbrengen van wijzigingen op de nationaliteit(en) van een persoon.
 */
public interface PersoonEnNationaliteitService {

    /**
     * Voegt een nationaliteit toe aan een persoon.
     *
     * @param actie beschrijving inclusief metadata van de uit te voeren bewerking.
     * @param bronnen de brondocument(en) die ten grondslag liggen aan de uit te voeren bewerking.
     * @param bsn het BSN van de persoon aan wie de nationaliteit moet worden toegevoegd.
     * @param nationId de id van de nationaliteit die dient te worden toegevoegd.
     * @param datumAanvangGeldigheid de datum vanaf wanneer de toegevoegde nationaliteit geldig is.
     */
    void toevoegenNationaliteit(Actie actie, Set<Bron> bronnen, BigDecimal bsn, int nationId,
            BigDecimal datumAanvangGeldigheid);

    /**
     * Heft een (bestaande) nationaliteit van een persoon op.
     *
     * @param actie beschrijving inclusief metadata van de uit te voeren bewerking.
     * @param bronnen de brondocument(en) die ten grondslag liggen aan de uit te voeren bewerking.
     * @param persoonsNationaliteitId de id van de persoon/nationaliteit combinatie die dient te worden opgeheven.
     */
    void opheffenNationaliteit(Actie actie, Set<Bron> bronnen, long persoonsNationaliteitId);

    /**
     * Wijzigt eigenschappen van een (bestaande) nationaliteit van een persoon.
     *
     * @param actie beschrijving inclusief metadata van de uit te voeren bewerking.
     * @param bronnen de brondocument(en) die ten grondslag liggen aan de uit te voeren bewerking.
     * @param persoonsNationaliteitId de id van de persoon/nationaliteit combinatie die dient te worden gewijzigd.
     */
    void wijzigPersoonNationaliteit(Actie actie, Set<Bron> bronnen, long persoonsNationaliteitId);

}
