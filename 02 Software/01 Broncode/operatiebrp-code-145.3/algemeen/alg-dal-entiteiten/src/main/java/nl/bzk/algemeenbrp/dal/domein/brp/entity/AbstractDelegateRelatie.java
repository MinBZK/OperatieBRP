/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;

/**
 * Deze abstract class biedt delegeer functionaliteit voor de Relatie class.
 */
public abstract class AbstractDelegateRelatie extends Relatie implements DelegateEntiteit<Relatie> {

    private final Relatie delegate;

    /**
     * Initialiseert AbstractDelegateRelatie.
     * @param delegate de delegate waar de getter en setter methodes naar moeten delegeren, mag niet null zijn
     */
    public AbstractDelegateRelatie(final Relatie delegate) {
        ValidationUtils.controleerOpNullWaarden("%s mag niet null zijn", delegate);
        this.delegate = delegate;
    }

    @Override
    public Relatie getDelegate() {
        return delegate;
    }

    /******* Delegate methodes. *******/

    @Override
    public void setGegevenInOnderzoek(final GegevenInOnderzoek gegevenInOnderzoek) {
        delegate.setGegevenInOnderzoek(gegevenInOnderzoek);
    }

    @Override
    public Collection<Element> getElementenInOnderzoek() {
        return delegate.getElementenInOnderzoek();
    }

    @Override
    public Map<Element, GegevenInOnderzoek> getGegevenInOnderzoekPerElementMap() {
        return delegate.getGegevenInOnderzoekPerElementMap();
    }

    @Override
    public void verwijderGegevenInOnderzoek(final Element gegevenInOnderzoek) {
        delegate.verwijderGegevenInOnderzoek(gegevenInOnderzoek);
    }

    @Override
    public Long getId() {
        return delegate.getId();
    }

    @Override
    public void setId(final Long id) {
        delegate.setId(id);
    }

    @Override
    public String getBuitenlandsePlaatsAanvang() {
        return delegate.getBuitenlandsePlaatsAanvang();
    }

    @Override
    public void setBuitenlandsePlaatsAanvang(final String buitenlandsePlaatsAanvang) {
        delegate.setBuitenlandsePlaatsAanvang(buitenlandsePlaatsAanvang);
    }

    @Override
    public String getBuitenlandsePlaatsEinde() {
        return delegate.getBuitenlandsePlaatsEinde();
    }

    @Override
    public void setBuitenlandsePlaatsEinde(final String buitenlandsePlaatsEinde) {
        delegate.setBuitenlandsePlaatsEinde(buitenlandsePlaatsEinde);
    }

    @Override
    public String getBuitenlandseRegioAanvang() {
        return delegate.getBuitenlandseRegioAanvang();
    }

    @Override
    public void setBuitenlandseRegioAanvang(final String buitenlandseRegioAanvang) {
        delegate.setBuitenlandseRegioAanvang(buitenlandseRegioAanvang);
    }

    @Override
    public String getBuitenlandseRegioEinde() {
        return delegate.getBuitenlandseRegioEinde();
    }

    @Override
    public void setBuitenlandseRegioEinde(final String buitenlandseRegioEinde) {
        delegate.setBuitenlandseRegioEinde(buitenlandseRegioEinde);
    }

    @Override
    public Integer getDatumAanvang() {
        return delegate.getDatumAanvang();
    }

    @Override
    public void setDatumAanvang(final Integer datumAanvang) {
        delegate.setDatumAanvang(datumAanvang);
    }

    @Override
    public Integer getDatumEinde() {
        return delegate.getDatumEinde();
    }

    @Override
    public void setDatumEinde(final Integer datumEinde) {
        delegate.setDatumEinde(datumEinde);
    }

    @Override
    public String getOmschrijvingLocatieAanvang() {
        return delegate.getOmschrijvingLocatieAanvang();
    }

    @Override
    public void setOmschrijvingLocatieAanvang(final String omschrijvingLocatieAanvang) {
        delegate.setOmschrijvingLocatieAanvang(omschrijvingLocatieAanvang);
    }

    @Override
    public String getOmschrijvingLocatieEinde() {
        return delegate.getOmschrijvingLocatieEinde();
    }

    @Override
    public void setOmschrijvingLocatieEinde(final String omschrijvingLocatieEinde) {
        delegate.setOmschrijvingLocatieEinde(omschrijvingLocatieEinde);
    }

    @Override
    public Set<Betrokkenheid> getBetrokkenheidSet() {
        return delegate.getBetrokkenheidSet();
    }

    @Override
    public Set<Betrokkenheid> getActueleBetrokkenheidSet() {
        return delegate.getActueleBetrokkenheidSet();
    }

    @Override
    public Set<Betrokkenheid> getActueleBetrokkenheidSet(final SoortBetrokkenheid soortBetrokkenheid) {
        return delegate.getActueleBetrokkenheidSet(soortBetrokkenheid);
    }

    @Override
    public Set<Betrokkenheid> getBetrokkenheidSet(final SoortBetrokkenheid soortBetrokkenheid) {
        return delegate.getBetrokkenheidSet(soortBetrokkenheid);
    }

    @Override
    public void addBetrokkenheid(final Betrokkenheid betrokkenheid) {
        delegate.addBetrokkenheid(betrokkenheid);
    }

    @Override
    public boolean removeBetrokkenheid(final Betrokkenheid betrokkenheid) {
        return delegate.removeBetrokkenheid(betrokkenheid);
    }

    @Override
    public Set<RelatieHistorie> getRelatieHistorieSet() {
        return delegate.getRelatieHistorieSet();
    }

    @Override
    public RelatieHistorie getActueleRelatieHistorie() {
        return delegate.getActueleRelatieHistorie();
    }

    @Override
    public void addRelatieHistorie(final RelatieHistorie relatieHistorie) {
        delegate.addRelatieHistorie(relatieHistorie);
    }

    @Override
    public LandOfGebied getLandOfGebiedEinde() {
        return delegate.getLandOfGebiedEinde();
    }

    @Override
    public void setLandOfGebiedEinde(final LandOfGebied landOfGebiedEinde) {
        delegate.setLandOfGebiedEinde(landOfGebiedEinde);
    }

    @Override
    public LandOfGebied getLandOfGebiedAanvang() {
        return delegate.getLandOfGebiedAanvang();
    }

    @Override
    public void setLandOfGebiedAanvang(final LandOfGebied landOfGebiedAanvang) {
        delegate.setLandOfGebiedAanvang(landOfGebiedAanvang);
    }

    @Override
    public Gemeente getGemeenteEinde() {
        return delegate.getGemeenteEinde();
    }

    @Override
    public void setGemeenteEinde(final Gemeente gemeenteEinde) {
        delegate.setGemeenteEinde(gemeenteEinde);
    }

    @Override
    public Gemeente getGemeenteAanvang() {
        return delegate.getGemeenteAanvang();
    }

    @Override
    public void setGemeenteAanvang(final Gemeente gemeenteAanvang) {
        delegate.setGemeenteAanvang(gemeenteAanvang);
    }

    @Override
    public String getWoonplaatsnaamAanvang() {
        return delegate.getWoonplaatsnaamAanvang();
    }

    @Override
    public void setWoonplaatsnaamAanvang(final String woonplaatsnaamAanvang) {
        delegate.setWoonplaatsnaamAanvang(woonplaatsnaamAanvang);
    }

    @Override
    public String getWoonplaatsnaamEinde() {
        return delegate.getWoonplaatsnaamEinde();
    }

    @Override
    public void setWoonplaatsnaamEinde(final String woonplaatsnaamEinde) {
        delegate.setWoonplaatsnaamEinde(woonplaatsnaamEinde);
    }

    @Override
    public RedenBeeindigingRelatie getRedenBeeindigingRelatie() {
        return delegate.getRedenBeeindigingRelatie();
    }

    @Override
    public void setRedenBeeindigingRelatie(final RedenBeeindigingRelatie redenBeeindigingRelatie) {
        delegate.setRedenBeeindigingRelatie(redenBeeindigingRelatie);
    }

    @Override
    public SoortRelatie getSoortRelatie() {
        return delegate.getSoortRelatie();
    }

    @Override
    public void setSoortRelatie(final SoortRelatie soortRelatie) {
        delegate.setSoortRelatie(soortRelatie);
    }

    @Override
    public boolean isActueelEnGeldig() {
        return delegate.isActueelEnGeldig();
    }

    @Override
    public void setActueelEnGeldig(final boolean actueelEnGeldig) {
        delegate.setActueelEnGeldig(actueelEnGeldig);
    }

    @Override
    public Set<Stapel> getStapels() {
        return delegate.getStapels();
    }

    @Override
    public boolean bevatStapel(final Stapel stapel) {
        return delegate.bevatStapel(stapel);
    }

    @Override
    public void addStapel(final Stapel stapel) {
        delegate.addStapel(stapel);
    }

    @Override
    public void removeStapel(final Stapel stapel) {
        delegate.removeStapel(stapel);
    }

    @Override
    public Map<String, Collection<FormeleHistorie>> verzamelHistorie() {
        return delegate.verzamelHistorie();
    }

    @Override
    public Betrokkenheid getAndereBetrokkenheid(final Persoon persoon) {
        return delegate.getAndereBetrokkenheid(persoon);
    }

    @Override
    public List<Persoon> getAndereBetrokkenheden(final Persoon persoon, final SoortBetrokkenheid soortBetrokkenheid) {
        return delegate.getAndereBetrokkenheden(persoon, soortBetrokkenheid);
    }

    @Override
    public String toString() {
        return delegate.toString();
    }
}
