/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.maakbericht.algemeen;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;

/**
 * Parameter object voor maakbericht service voor alle maak bericht services met 1 persoon. 
 */
public final class MaakBerichtParameters {
    //autorisatie en personen
    private List<Persoonslijst> bijgehoudenPersonen;
    private List<Autorisatiebundel> autorisatiebundels;
    private Map<Autorisatiebundel, Map<Persoonslijst, MaakBerichtPersoonInformatie>> maakBerichtPersoonMap;
    //alleen gevuld voor mutatielevering
    private Long administratieveHandelingId;
    //stuurparameters
    private boolean verantwoordingLeveren = true;
    private Set<AttribuutElement> gevraagdeElementen = Collections.emptySet();
    private boolean migratieGroepEnkelOpnemenBijEmigratie;


    //dienst informatie
    private SoortDienst soortDienst;

    private BijgehoudenPersoonBerichtDecorator bijgehoudenPersoonBerichtDecorator;

    /**
     * Geeft een {@link MaakBerichtParameters} object.
     * @param autorisatiebundel autorisatiebundel
     * @param bijgehoudenPersoonBerichtDecorator bijgehouden persoon berichtdecorator
     * @param datumAanvangMaterielePeriode datum aanvang materiele periode
     * @param persoonslijst persoonslijst
     * @param soortSynchronisatie soort synchronisatie
     * @param maakBerichtHistorieFilterInformatie maakbericht historie filter informatie
     * @return maakbericht parameters
     */
    public static MaakBerichtParameters getInstance(final Autorisatiebundel autorisatiebundel,
                                                    final BijgehoudenPersoonBerichtDecorator bijgehoudenPersoonBerichtDecorator,
                                                    final Integer datumAanvangMaterielePeriode,
                                                    final Persoonslijst persoonslijst,
                                                    final SoortSynchronisatie soortSynchronisatie,
                                                    final MaakBerichtHistorieFilterInformatie maakBerichtHistorieFilterInformatie) {
        final MaakBerichtPersoonInformatie maakBerichtPersoon = new MaakBerichtPersoonInformatie(soortSynchronisatie);
        maakBerichtPersoon.setDatumAanvangmaterielePeriode(datumAanvangMaterielePeriode);
        maakBerichtPersoon.setHistorieFilterInformatie(maakBerichtHistorieFilterInformatie);
        final Map<Autorisatiebundel, Map<Persoonslijst, MaakBerichtPersoonInformatie>> maakBerichtPersoonMap = new HashMap<>();
        final Map<Persoonslijst, MaakBerichtPersoonInformatie> map = Maps.newHashMap();
        map.put(persoonslijst, maakBerichtPersoon);
        maakBerichtPersoonMap.put(autorisatiebundel, map);

        final MaakBerichtParameters maakBerichtParameters = new MaakBerichtParameters();
        maakBerichtParameters.setAutorisatiebundels(Lists.newArrayList(autorisatiebundel));
        maakBerichtParameters.setBijgehoudenPersoonBerichtDecorator(bijgehoudenPersoonBerichtDecorator);
        maakBerichtParameters.setMaakBerichtPersoonMap(maakBerichtPersoonMap);
        maakBerichtParameters.setBijgehoudenPersonen(Lists.newArrayList(persoonslijst));
        return maakBerichtParameters;
    }

    public BijgehoudenPersoonBerichtDecorator getBijgehoudenPersoonBerichtDecorator() {
        return bijgehoudenPersoonBerichtDecorator;
    }

    public void setBijgehoudenPersoonBerichtDecorator(final BijgehoudenPersoonBerichtDecorator bijgehoudenPersoonBerichtDecorator) {
        this.bijgehoudenPersoonBerichtDecorator = bijgehoudenPersoonBerichtDecorator;
    }

    /**
     * @return indicatie of verantwoording geleverd moet worden, default true.
     */
    public boolean isVerantwoordingLeveren() {
        return verantwoordingLeveren;
    }

    /**
     * Zet de indicatie of verantwoording geleverd moet worden.
     * @param verantwoordingLeveren boolean indicatie
     */
    public void setVerantwoordingLeveren(final boolean verantwoordingLeveren) {
        this.verantwoordingLeveren = verantwoordingLeveren;
    }

    /**
     * @return Set met elementen welke in het bericht verwacht worden (dit is een subset van de normaal te autoriseren elementen)
     */
    public Set<AttribuutElement> getGevraagdeElementen() {
        return gevraagdeElementen;
    }

    /**
     * Geeft aan welke elementen in het bericht verwacht worden. (dit is een subset van de normaal te autoriseren elementen)
     * @param gevraagdeElementen set met elementen.
     */
    public void setGevraagdeElementen(final Set<AttribuutElement> gevraagdeElementen) {
        this.gevraagdeElementen = gevraagdeElementen;
    }

    /**
     * @return de autorisatie
     */
    public List<Autorisatiebundel> getAutorisatiebundels() {
        return autorisatiebundels;
    }

    /**
     * Zet de autorisatie.
     * @param autorisatiebundels de autorisaties
     */
    public void setAutorisatiebundels(final List<Autorisatiebundel> autorisatiebundels) {
        this.autorisatiebundels = autorisatiebundels;
    }

    /**
     * @return de administratieve handeling waarvoor geleverd wordt, null indien er niet obv een handeling geleverd wordt.
     */
    public Long getAdministratieveHandeling() {
        return administratieveHandelingId;
    }

    /**
     * Zet de administratiehandeling waarvoor geleverd wordt. Dit is optioneel.
     * @param administratieveHandelingId een admistratieve handeling
     */
    public void setAdministratieveHandelingId(final long administratieveHandelingId) {
        this.administratieveHandelingId = administratieveHandelingId;
    }

    /**
     * @return indicatie of de migratie groep enkel geleverd wordt bij emigratie.
     */
    public boolean migratieGroepEnkelOpnemenBijEmigratie() {
        return migratieGroepEnkelOpnemenBijEmigratie;
    }

    /**
     * Zet de indicatie of de migratie groep enkel geleverd wordt bij emigratie.
     * @param migratieGroepEnkelOpnemenBijEmigratie boolean indicatie.
     */
    public void setMigratieGroepEnkelOpnemenBijEmigratie(final boolean migratieGroepEnkelOpnemenBijEmigratie) {
        this.migratieGroepEnkelOpnemenBijEmigratie = migratieGroepEnkelOpnemenBijEmigratie;
    }

    /**
     * Geeft de {@link SoortDienst} waarbinnen MaakBericht wordt aangeroepen.
     * @return soort dienst
     */
    public SoortDienst getSoortDienst() {
        return soortDienst;
    }

    /**
     * Zet de {@link SoortDienst} waarbinnen MaakBericht wordt aangeroepen.
     * @param soortDienst soort dienst
     */
    public void setSoortDienst(final SoortDienst soortDienst) {
        this.soortDienst = soortDienst;
    }

    public List<Persoonslijst> getBijgehoudenPersonen() {
        return bijgehoudenPersonen;
    }

    public void setBijgehoudenPersonen(final List<Persoonslijst> personen) {
        this.bijgehoudenPersonen = personen;
    }

    public Map<Autorisatiebundel, Map<Persoonslijst, MaakBerichtPersoonInformatie>> getMaakBerichtPersoonMap() {
        return maakBerichtPersoonMap;
    }

    public void setMaakBerichtPersoonMap(
            final Map<Autorisatiebundel, Map<Persoonslijst, MaakBerichtPersoonInformatie>> maakBerichtPersoonMap) {
        this.maakBerichtPersoonMap = maakBerichtPersoonMap;
    }
}
