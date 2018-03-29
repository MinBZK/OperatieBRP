/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.algemeen;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie;

/**
 * MaakBerichtPersoonInformatie. Wat extra informatie over de persoon in maak bericht voor een specifieke autorisatie.
 *
 * SoortSynchronisatie en datumaanvang materiele periode worden bepaald aan de hand van de autorisatie.
 * Historiefilter informatie wordt bepaald a.h.v. het bevragingsverzoek of de selectieautorisatie.
 */
public final class MaakBerichtPersoonInformatie {

    private SoortSynchronisatie soortSynchronisatie;
    /**
     * Het materiele punt. Het zetten van deze waarde limiteert de materiele historie, gegevens vòòr dit punt worden niet geleverd. Apart gemodelleerd want
     * bij plaatsen afnemerindicatie bevat de persoonsgegevens nog niet de afnemerindicatie.
     */
    private Integer datumAanvangmaterielePeriode;

    private MaakBerichtHistorieFilterInformatie historieFilterInformatie;

    /**
     * Constructor.
     * @param soortSynchronisatie soortSynchronisatie
     */
    public MaakBerichtPersoonInformatie(final SoortSynchronisatie soortSynchronisatie) {
        this.soortSynchronisatie = soortSynchronisatie;
    }

    public SoortSynchronisatie getSoortSynchronisatie() {
        return soortSynchronisatie;
    }

    public void setDatumAanvangmaterielePeriode(Integer datumAanvangmaterielePeriode) {
        this.datumAanvangmaterielePeriode = datumAanvangmaterielePeriode;
    }

    public Integer getDatumAanvangmaterielePeriode() {
        return datumAanvangmaterielePeriode;
    }

    public void setHistorieFilterInformatie(MaakBerichtHistorieFilterInformatie historieFilterInformatie) {
        this.historieFilterInformatie = historieFilterInformatie;
    }

    public MaakBerichtHistorieFilterInformatie getHistorieFilterInformatie() {
        return historieFilterInformatie;
    }
}
