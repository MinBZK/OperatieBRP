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
import nl.bzk.algemeenbrp.dal.domein.brp.enums.StatusOnderzoek;
import nl.bzk.algemeenbrp.dal.domein.brp.util.ValidationUtils;

/**
 * Deze abstract class biedt delegeer functionaliteit voor de Onderzoek class.
 */
public abstract class AbstractDelegateOnderzoek extends Onderzoek implements DelegateEntiteit<Onderzoek> {

    private final Onderzoek delegate;

    /**
     * Initialiseert AbstractDelegateOnderzoek.
     * @param delegate de delegate waar de getter en setter methodes naar moeten delegeren, mag niet null zijn
     */
    public AbstractDelegateOnderzoek(final Onderzoek delegate) {
        ValidationUtils.controleerOpNullWaarden("%s mag niet null zijn", delegate);
        this.delegate = delegate;
    }

    @Override
    public Onderzoek getDelegate() {
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
    public Integer getId() {
        return delegate.getId();
    }

    @Override
    public void setId(final Integer id) {
        delegate.setId(id);
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
    public String getOmschrijving() {
        return delegate.getOmschrijving();
    }

    @Override
    public void setOmschrijving(final String omschrijving) {
        delegate.setOmschrijving(omschrijving);
    }

    @Override
    public StatusOnderzoek getStatusOnderzoek() {
        return delegate.getStatusOnderzoek();
    }

    @Override
    public void setStatusOnderzoek(final StatusOnderzoek statusOnderzoek) {
        delegate.setStatusOnderzoek(statusOnderzoek);
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
    public Set<OnderzoekHistorie> getOnderzoekHistorieSet() {
        return delegate.getOnderzoekHistorieSet();
    }

    @Override
    public void addOnderzoekHistorie(final OnderzoekHistorie onderzoekHistorie) {
        delegate.addOnderzoekHistorie(onderzoekHistorie);
    }

    @Override
    public Partij getPartij() {
        return delegate.getPartij();
    }

    @Override
    public void setPartij(final Partij partij) {
        delegate.setPartij(partij);
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
    public Set<GegevenInOnderzoek> getGegevenInOnderzoekSet() {
        return delegate.getGegevenInOnderzoekSet();
    }

    @Override
    public void addGegevenInOnderzoek(final GegevenInOnderzoek gegevenInOnderzoek) {
        delegate.addGegevenInOnderzoek(gegevenInOnderzoek);
    }

    @Override
    public void kopieerGegevenInOnderzoekVoorNieuwGegeven(final Entiteit oudGegevenInOnderzoek,
                                                          final Entiteit nieuwGegevenInOnderzoek, final BRPActie actie) {
        delegate.kopieerGegevenInOnderzoekVoorNieuwGegeven(oudGegevenInOnderzoek, nieuwGegevenInOnderzoek, actie);
    }

    @Override
    public Map<String, Collection<FormeleHistorie>> verzamelHistorie() {
        return delegate.verzamelHistorie();
    }
}
