/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.converter;

import nl.bzk.brp.model.logisch.PersoonAdres;
import nl.bzk.brp.model.operationeel.kern.PersistentPersoonAdres;

/**
 * Converteert objecten van operationeel model naar logisch model. in dit geval gaat het om persoon adres.
 *
 */


public final class PersoonAdresConverter {
    /**
     * default constructor.
     */
    private PersoonAdresConverter() {
    }

    /**
     * Converteert een Operationeel persoon model instantie ({@link PersistentPersoonAdres}) naar een logisch persoon
     * model instantie ({@link PersoonAdres}).
     *
     * @param persistentPersoonAdres de operationeel model instantie van een persoonAdres.
     * @return de logisch model instantie van de opgegeven persoonAdres (of null als persistentPersoonAdresis null).
     */
    public static PersoonAdres converteerOperationeelNaarLogisch(final PersistentPersoonAdres persistentPersoonAdres) {
        PersoonAdres adres = null;
        if (persistentPersoonAdres != null) {
            adres = new PersoonAdres();
            adres.setSoort(persistentPersoonAdres.getSoort());
            adres.setRedenWijziging(persistentPersoonAdres.getRedenWijziging());
            adres.setAangeverAdreshouding(persistentPersoonAdres.getAangeverAdreshouding());
            adres.setDatumAanvangAdreshouding(persistentPersoonAdres.getDatumAanvangAdreshouding());
            adres.setAdresseerbaarObject(persistentPersoonAdres.getAdresseerbaarObject());
            adres.setIdentificatiecodeNummeraanduiding(persistentPersoonAdres.getIdentificatiecodeNummeraanduiding());
            adres.setGemeente(persistentPersoonAdres.getGemeente());
            adres.setNaamOpenbareRuimte(persistentPersoonAdres.getNaamOpenbareRuimte());
            adres.setAfgekorteNaamOpenbareRuimte(persistentPersoonAdres.getAfgekorteNaamOpenbareRuimte());
            adres.setGemeentedeel(persistentPersoonAdres.getGemeentedeel());
            adres.setHuisnummer(persistentPersoonAdres.getHuisnummer());
            adres.setHuisletter(persistentPersoonAdres.getHuisletter());
            adres.setHuisnummertoevoeging(persistentPersoonAdres.getHuisnummertoevoeging());
            adres.setPostcode(persistentPersoonAdres.getPostcode());
            adres.setWoonplaats(persistentPersoonAdres.getWoonplaats());
            adres.setLocatieTovAdres(persistentPersoonAdres.getLocatietovAdres());
            adres.setLocatieOmschrijving(persistentPersoonAdres.getLocatieOmschrijving());

            adres.setBuitenlandsAdresRegel1(persistentPersoonAdres.getBuitenlandsAdresRegel1());
            adres.setBuitenlandsAdresRegel2(persistentPersoonAdres.getBuitenlandsAdresRegel2());
            adres.setBuitenlandsAdresRegel3(persistentPersoonAdres.getBuitenlandsAdresRegel3());
            adres.setBuitenlandsAdresRegel4(persistentPersoonAdres.getBuitenlandsAdresRegel4());
            adres.setBuitenlandsAdresRegel5(persistentPersoonAdres.getBuitenlandsAdresRegel5());
            adres.setBuitenlandsAdresRegel6(persistentPersoonAdres.getBuitenlandsAdresRegel6());
            adres.setLand(persistentPersoonAdres.getLand());
            adres.setDatumVertrekUitNederland(persistentPersoonAdres.getDatumVertrekUitNederland());
        }
        return adres;
    }

}
