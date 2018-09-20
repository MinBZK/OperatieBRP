/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.selectie;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Geslachtsaanduiding;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortBetrokkenheid;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortPersoon;
import nl.bzk.brp.model.objecttype.operationeel.statisch.SoortRelatie;

/**
 * RelatieSelectieFilter is een selectie criteria voor het opzoeken van relaties tov een persoon.
 * Door soortRelaties in te vullen kan men selecteren op specifieke relatie type(s).
 * Door soortBetrokkenheden in te vullen kan men selecteren op rol(len) van de hoofdpersoon binnen de relatie(s).
 * De soortRollen is een filter op de rollen gevonden personen (bv. Kind, Ouder)
 * De peilDatum wordt gebruik om te kijken of dit binnen de aanvangDatum en eindatum (als ze gedefinieerd zijn).
 * De uitGeslachtsaanduiding is om de uitkomst ook te selecteren op geslacht.
 */
public class RelatieSelectieFilter implements Serializable {

    private static final long serialVersionUID = -7352110311311584565L;
    private List<SoortRelatie> soortRelaties;
    private List<SoortBetrokkenheid> soortBetrokkenheden;
    private List<SoortBetrokkenheid> soortRollen;
    private Datum peilDatum;
    private List<Geslachtsaanduiding> uitGeslachtsaanduidingen;
    private List<SoortPersoon> uitPersoonTypen;


    public List<SoortRelatie> getSoortRelaties() {
        return soortRelaties;
    }

    public void setSoortRelaties(final List<SoortRelatie> soortRelaties) {
        this.soortRelaties = soortRelaties;
    }

    public void setSoortRelaties(final SoortRelatie... soortRelatieLijst) {
        soortRelaties = Arrays.asList(soortRelatieLijst);
    }

    public List<SoortBetrokkenheid> getSoortBetrokkenheden() {
        return soortBetrokkenheden;
    }

    public void setSoortBetrokkenheden(final List<SoortBetrokkenheid> soortBetrokkenheden) {
        this.soortBetrokkenheden = soortBetrokkenheden;
    }

    public void setSoortBetrokkenheden(final SoortBetrokkenheid... soortBetrokkenhedenLijst) {
        soortBetrokkenheden = Arrays.asList(soortBetrokkenhedenLijst);
    }

    public Datum getPeilDatum() {
        return peilDatum;
    }

    public void setPeilDatum(final Datum peilDatum) {
        this.peilDatum = peilDatum;
    }

    public List<SoortBetrokkenheid> getSoortRollen() {
        return soortRollen;
    }

    public void setSoortRollen(final List<SoortBetrokkenheid> soortRollen) {
        this.soortRollen = soortRollen;
    }

    public void setSoortRollen(final SoortBetrokkenheid... soortRollenLijst) {
        soortRollen = Arrays.asList(soortRollenLijst);
    }

    public List<Geslachtsaanduiding> getUitGeslachtsaanduidingen() {
        return uitGeslachtsaanduidingen;
    }

    public void setUitGeslachtsaanduidingen(final List<Geslachtsaanduiding> uitGeslachtsaanduidingen) {
        this.uitGeslachtsaanduidingen = uitGeslachtsaanduidingen;
    }

    public void setUitGeslachtsaanduidingen(final Geslachtsaanduiding... uitgeslachtAand) {
        uitGeslachtsaanduidingen = Arrays.asList(uitgeslachtAand);
    }

    public List<SoortPersoon> getUitPersoonTypen() {
        return uitPersoonTypen;
    }

    public void setUitPersoonTypen(final List<SoortPersoon> uitPersoonTypen) {
        this.uitPersoonTypen = uitPersoonTypen;
    }

    public void setUitPersoonTypen(final SoortPersoon... persoonTypen) {
        uitPersoonTypen = Arrays.asList(persoonTypen);
    }
}
