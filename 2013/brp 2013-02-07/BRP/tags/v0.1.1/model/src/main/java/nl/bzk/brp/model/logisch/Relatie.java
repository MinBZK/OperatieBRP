/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nl.bzk.brp.model.RootObject;
import nl.bzk.brp.model.gedeeld.Land;
import nl.bzk.brp.model.gedeeld.Partij;
import nl.bzk.brp.model.gedeeld.Plaats;
import nl.bzk.brp.model.gedeeld.RedenBeeindigingRelatie;
import nl.bzk.brp.model.gedeeld.SoortBetrokkenheid;
import nl.bzk.brp.model.gedeeld.SoortRelatie;

/**
 * Logisch gegevens model object voor Relatie.
 */
public class Relatie implements RootObject {

    private SoortRelatie soortRelatie;
    private Integer datumAanvang;
    private Partij gemeenteAanvang;
    private Plaats plaatsAanvang;
    private String buitenlandsePlaatsAanvang;
    private String buitenlandseRegioAanvang;
    private Land landAanvang;
    private String omschrijvingLocatieAanvang;
    private RedenBeeindigingRelatie redenBeeindigingRelatie;
    private Integer datumEinde;
    private Partij gemeenteEinde;
    private Plaats plaatsEinde;
    private String buitenlandsePlaatsEinde;
    private String buitenlandseRegioEinde;
    private Land landEinde;
    private String omschrijvingLocatieEinde;
    private Set<Betrokkenheid> betrokkenheden;

    public SoortRelatie getSoortRelatie() {
        return soortRelatie;
    }

    public void setSoortRelatie(final SoortRelatie soortRelatie) {
        this.soortRelatie = soortRelatie;
    }

    public Partij getGemeenteAanvang() {
        return gemeenteAanvang;
    }

    public void setGemeenteAanvang(final Partij gemeenteAanvang) {
        this.gemeenteAanvang = gemeenteAanvang;
    }

    public Plaats getPlaatsAanvang() {
        return plaatsAanvang;
    }

    public void setPlaatsAanvang(final Plaats plaatsAanvang) {
        this.plaatsAanvang = plaatsAanvang;
    }

    public String getBuitenlandsePlaatsAanvang() {
        return buitenlandsePlaatsAanvang;
    }

    public void setBuitenlandsePlaatsAanvang(final String buitenlandsePlaatsAanvang) {
        this.buitenlandsePlaatsAanvang = buitenlandsePlaatsAanvang;
    }

    public String getBuitenlandseRegioAanvang() {
        return buitenlandseRegioAanvang;
    }

    public void setBuitenlandseRegioAanvang(final String buitenlandseRegioAanvang) {
        this.buitenlandseRegioAanvang = buitenlandseRegioAanvang;
    }

    public Land getLandAanvang() {
        return landAanvang;
    }

    public void setLandAanvang(final Land landAanvang) {
        this.landAanvang = landAanvang;
    }

    public String getOmschrijvingLocatieAanvang() {
        return omschrijvingLocatieAanvang;
    }

    public void setOmschrijvingLocatieAanvang(final String omschrijvingLocatieAanvang) {
        this.omschrijvingLocatieAanvang = omschrijvingLocatieAanvang;
    }

    public RedenBeeindigingRelatie getRedenBeeindigingRelatie() {
        return redenBeeindigingRelatie;
    }

    public void setRedenBeeindigingRelatie(final RedenBeeindigingRelatie redenBeeindigingRelatie) {
        this.redenBeeindigingRelatie = redenBeeindigingRelatie;
    }

    public Integer getDatumEinde() {
        return datumEinde;
    }

    public void setDatumEinde(final Integer datumEinde) {
        this.datumEinde = datumEinde;
    }

    public Partij getGemeenteEinde() {
        return gemeenteEinde;
    }

    public void setGemeenteEinde(final Partij gemeenteEinde) {
        this.gemeenteEinde = gemeenteEinde;
    }

    public Plaats getPlaatsEinde() {
        return plaatsEinde;
    }

    public void setPlaatsEinde(final Plaats plaatsEinde) {
        this.plaatsEinde = plaatsEinde;
    }

    public String getBuitenlandsePlaatsEinde() {
        return buitenlandsePlaatsEinde;
    }

    public void setBuitenlandsePlaatsEinde(final String buitenlandsePlaatsEinde) {
        this.buitenlandsePlaatsEinde = buitenlandsePlaatsEinde;
    }

    public String getBuitenlandseRegioEinde() {
        return buitenlandseRegioEinde;
    }

    public void setBuitenlandseRegioEinde(final String buitenlandseRegioEinde) {
        this.buitenlandseRegioEinde = buitenlandseRegioEinde;
    }

    public Land getLandEinde() {
        return landEinde;
    }

    public void setLandEinde(final Land landEinde) {
        this.landEinde = landEinde;
    }

    public String getOmschrijvingLocatieEinde() {
        return omschrijvingLocatieEinde;
    }

    public void setOmschrijvingLocatieEinde(final String omschrijvingLocatieEinde) {
        this.omschrijvingLocatieEinde = omschrijvingLocatieEinde;
    }

    public Set<Betrokkenheid> getBetrokkenheden() {
        return betrokkenheden;
    }

    public void setBetrokkenheden(final Set<Betrokkenheid> betrokkenheden) {
        this.betrokkenheden = betrokkenheden;
    }

    public Integer getDatumAanvang() {
        return datumAanvang;
    }

    public void setDatumAanvang(final Integer datumAanvang) {
        this.datumAanvang = datumAanvang;
    }

    public boolean isHuwelijk() {
        return SoortRelatie.HUWELIJK == soortRelatie;
    }

    public boolean isGeregistreerdPartnerschap() {
        return SoortRelatie.GEREGISTREERD_PARTNERSCHAP == soortRelatie;
    }

    /**
     * Retourneert de partner betrokkenheid uit de lijst van betrokkenheden.
     * Functie wordt vereist door de Jibx databinding.
     * @return De partner betrokkenheid.
     */
    public Betrokkenheid getPartnerBetrokkenheid() {
        if (betrokkenheden != null) {
            for (Betrokkenheid betrokkenheid : betrokkenheden) {
                if (betrokkenheid.getSoortBetrokkenheid() == SoortBetrokkenheid.PARTNER) {
                    return betrokkenheid;
                }
            }
        }
        return null;
    }

    /**
     * Voegt partner betrokkenhedid toe aan de lijst van betrokkenheden.
     * Functie wordt vereist door de Jibx databinding.
     * @param betr De toe te voegen betrokkenheid.
     */
    public void voegPartnerBetrokkenheidToe(final Betrokkenheid betr) {
        if (betrokkenheden == null) {
            betrokkenheden = new HashSet<Betrokkenheid>();
        }
        betrokkenheden.add(betr);
    }

    /**
     * Retourneert de kind betrokkenheid uit de lijst van betrokkenheden.
     * Functie wordt vereist door de Jibx databinding.
     * @return De partner betrokkenheid.
     */
    public Betrokkenheid getKindBetrokkenheid() {
        if (betrokkenheden != null) {
            for (Betrokkenheid betrokkenheid : betrokkenheden) {
                if (betrokkenheid.getSoortBetrokkenheid() == SoortBetrokkenheid.KIND) {
                    return betrokkenheid;
                }
            }
        }
        return null;
    }

    /**
     * Voegt kind betrokkenhedid toe aan de lijst van betrokkenheden.
     * Functie wordt vereist door de Jibx databinding.
     * @param betr De toe te voegen betrokkenheid.
     */
    public void voegKindBetrokkenheidToe(final Betrokkenheid betr) {
        if (betrokkenheden == null) {
            betrokkenheden = new HashSet<Betrokkenheid>();
        }
        betrokkenheden.add(betr);
    }

    /**
     * Retourneert de ouder betrokkenheden uit de lijst van betrokkenheden.
     * Functie wordt vereist door de Jibx databinding.
     * @return De ouder betrokkenheden.
     */
    public List<Betrokkenheid> getOuderBetrokkenheden() {
        if (betrokkenheden != null) {
            final List<Betrokkenheid> ouderBetrokkenheden = new ArrayList<Betrokkenheid>();
            for (Betrokkenheid betrokkenheid : betrokkenheden) {
                if (betrokkenheid.getSoortBetrokkenheid() == SoortBetrokkenheid.OUDER) {
                    ouderBetrokkenheden.add(betrokkenheid);
                }
            }
            return ouderBetrokkenheden;
        }
        return null;
    }

    /**
     * Voegt ouder betrokkenheden toe aan de lijst van betrokkenheden.
     * Functie wordt vereist door de Jibx databinding.
     * @param ouderBetrokkenheden De toe te voegen betrokkenheden.
     */
    public void voegOuderBetrokkenhedenToe(final List<Betrokkenheid> ouderBetrokkenheden) {
        if (betrokkenheden == null) {
            betrokkenheden = new HashSet<Betrokkenheid>();
        }
        betrokkenheden.addAll(ouderBetrokkenheden);
    }
}
