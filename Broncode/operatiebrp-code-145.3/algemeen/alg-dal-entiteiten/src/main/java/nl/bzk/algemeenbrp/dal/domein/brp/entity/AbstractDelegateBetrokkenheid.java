/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBetrokkenheid;

/**
 * Deze abstract class biedt delegeer functionaliteit voor de Betrokkenheid class.
 */
public abstract class AbstractDelegateBetrokkenheid extends Betrokkenheid implements DelegateEntiteit<Betrokkenheid> {

    private final Betrokkenheid delegate;

    /**
     * Initialiseert AbstractDelegateBetrokkenheid.
     *
     * @param delegate de delegate waar de getter en setter methodes naar moeten delegeren, mag niet
     *        null zijn
     */
    public AbstractDelegateBetrokkenheid(final Betrokkenheid delegate) {
        this.delegate = delegate;
    }

    @Override
    public Betrokkenheid getDelegate() {
        return delegate;
    }


    /******* Delegate methodes. *******/

    @Override
    public String toString() {
        return delegate.toString();
    }

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
    public Boolean getIndicatieOuderUitWieKindIsGeboren() {
        return delegate.getIndicatieOuderUitWieKindIsGeboren();
    }

    @Override
    public void setIndicatieOuderUitWieKindIsGeboren(final Boolean indicatieOuderUitWieKindIsGeboren) {
        delegate.setIndicatieOuderUitWieKindIsGeboren(indicatieOuderUitWieKindIsGeboren);
    }

    @Override
    public Boolean getIndicatieOuderHeeftGezag() {
        return delegate.getIndicatieOuderHeeftGezag();
    }

    @Override
    public void setIndicatieOuderHeeftGezag(final Boolean indicatieOuderHeeftGezag) {
        delegate.setIndicatieOuderHeeftGezag(indicatieOuderHeeftGezag);
    }

    @Override
    public Persoon getPersoon() {
        return delegate.getPersoon();
    }

    @Override
    public void setPersoon(final Persoon persoon) {
        delegate.setPersoon(persoon);
    }

    @Override
    public Relatie getRelatie() {
        return delegate.getRelatie();
    }

    @Override
    public void setRelatie(final Relatie relatie) {
        delegate.setRelatie(relatie);
    }

    @Override
    public SoortBetrokkenheid getSoortBetrokkenheid() {
        return delegate.getSoortBetrokkenheid();
    }

    @Override
    public void setSoortBetrokkenheid(final SoortBetrokkenheid soortBetrokkenheid) {
        delegate.setSoortBetrokkenheid(soortBetrokkenheid);
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
    public boolean isActueelEnGeldigVoorOuderschap() {
        return delegate.isActueelEnGeldigVoorOuderschap();
    }

    @Override
    public void setActueelEnGeldigVoorOuderschap(final boolean actueelEnGeldigVoorOuderschap) {
        delegate.setActueelEnGeldigVoorOuderschap(actueelEnGeldigVoorOuderschap);
    }

    @Override
    public boolean isActueelEnGeldigVoorOuderlijkGezag() {
        return delegate.isActueelEnGeldigVoorOuderlijkGezag();
    }

    @Override
    public void setActueelEnGeldigVoorOuderlijkGezag(final boolean actueelEnGeldigVoorOuderlijkGezag) {
        delegate.setActueelEnGeldigVoorOuderlijkGezag(actueelEnGeldigVoorOuderlijkGezag);
    }

    @Override
    public void addBetrokkenheidOuderHistorie(final BetrokkenheidOuderHistorie betrokkenheidOuderHistorie) {
        delegate.addBetrokkenheidOuderHistorie(betrokkenheidOuderHistorie);
    }

    @Override
    public boolean removeBetrokkenheidOuderHistorie(final BetrokkenheidOuderHistorie betrokkenheidOuderHistorie) {
        return delegate.removeBetrokkenheidOuderHistorie(betrokkenheidOuderHistorie);
    }

    @Override
    public Set<BetrokkenheidOuderHistorie> getBetrokkenheidOuderHistorieSet() {
        return delegate.getBetrokkenheidOuderHistorieSet();
    }

    @Override
    public Set<BetrokkenheidOuderlijkGezagHistorie> getBetrokkenheidOuderlijkGezagHistorieSet() {
        return delegate.getBetrokkenheidOuderlijkGezagHistorieSet();
    }

    @Override
    public void addBetrokkenheidOuderlijkGezagHistorie(final BetrokkenheidOuderlijkGezagHistorie betrokkenheidOuderlijkGezagHistorie) {
        delegate.addBetrokkenheidOuderlijkGezagHistorie(betrokkenheidOuderlijkGezagHistorie);
    }

    @Override
    public boolean removeBetrokkenheidOuderlijkGezagHistorie(final BetrokkenheidOuderlijkGezagHistorie betrokkenheidOuderlijkGezagHistorie) {
        return delegate.removeBetrokkenheidOuderlijkGezagHistorie(betrokkenheidOuderlijkGezagHistorie);
    }

    @Override
    public Set<BetrokkenheidHistorie> getBetrokkenheidHistorieSet() {
        return delegate.getBetrokkenheidHistorieSet();
    }

    @Override
    public void addBetrokkenheidHistorie(final BetrokkenheidHistorie betrokkenheidHistorie) {
        delegate.addBetrokkenheidHistorie(betrokkenheidHistorie);
    }

    @Override
    public Lo3AanduidingOuder getAanduidingOuder() {
        return delegate.getAanduidingOuder();
    }

    @Override
    public void setAanduidingOuder(final Lo3AanduidingOuder aanduidingOuder) {
        delegate.setAanduidingOuder(aanduidingOuder);
    }

    @Override
    public Map<String, Collection<FormeleHistorie>> verzamelHistorie() {
        return delegate.verzamelHistorie();
    }
}
