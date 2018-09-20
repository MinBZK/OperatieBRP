/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.common.rapportage;

import java.util.Date;

/**
 * Rapportage DAO interface voor rapportages over JBPM processen.
 */
public interface RapportageDao {

    /**
     * Voegt een nieuwe rij toe aan de extractie tabel gebruikt voor rapportages over JBPM processen.
     *
     * @param procesnaam
     *            De procesnaam van het lopende proces.
     * @param berichtType
     *            Het berichttype van het startende bericht.
     * @param kanaal
     *            Het kanaal van het startende bericht.
     * @param procesInstantieId
     *            Het id van het lopende proces.
     * @param startdatum
     *            De startdatum van de foutafhandeling.
     *
     */
    void voegStartProcesInstantieToe(String procesnaam, String berichtType, String kanaal, Long procesInstantieId, Date startdatum);

    /**
     * Update de extractie regel in de database waarbij de wachtstartdatum en foutreden worden gevuld.
     *
     * @param procesInstantieId
     *            De proces instantie waarover we loggen.
     * @param wachtStartdatum
     *            De wachtStartDatum van het foutafhandelingsproces.
     * @param foutreden
     *            De foutreden.
     */
    void updateFoutreden(Long procesInstantieId, Date wachtStartdatum, String foutreden);

    /**
     * Update de extractie regel in de database waarbij de wachteinddatum wordt gevuld.
     *
     * @param procesInstantieId
     *            De proces instantie waarover we loggen.
     * @param wachtEinddatum
     *            De wachtEindDatum van het foutafhandelingsproces.
     */
    void updateWachtBeheerderEinde(Long procesInstantieId, Date wachtEinddatum);

    /**
     * Update de extractie regel in de database waarbij de einddatum wordt gevuld.
     *
     * @param procesInstantieId
     *            De proces instantie waarover we loggen.
     * @param einddatum
     *            De eindDatum van het proces.
     */
    void updateEindeProcesInstantie(Long procesInstantieId, Date einddatum);
}
