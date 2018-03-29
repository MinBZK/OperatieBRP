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

/**
 * Deze abstract class biedt delegeer functionaliteit voor de {@link PersoonNationaliteit} class.
 */
public abstract class AbstractDelegatePersoonNationaliteit extends PersoonNationaliteit implements DelegateEntiteit<PersoonNationaliteit> {

    private final PersoonNationaliteit delegate;

    /**
     * Initialiseert AbstractDelegateNationaliteit.
     * @param delegate de delegate waar de getter en setter methodes naar moeten delegeren, mag niet null zijn
     */
    public AbstractDelegatePersoonNationaliteit(final PersoonNationaliteit delegate) {
        this.delegate = delegate;
    }

    @Override
    public PersoonNationaliteit getDelegate() {
        return delegate;
    }

    /******* Delegate methodes. *******/

    @Override
    public Long getId() {
        return delegate.getId();
    }

    @Override
    public void setId(final Long id) {
        delegate.setId(id);
    }

    @Override
    public Set<PersoonNationaliteitHistorie> getPersoonNationaliteitHistorieSet() {
        return delegate.getPersoonNationaliteitHistorieSet();
    }

    @Override
    public void addPersoonNationaliteitHistorie(final PersoonNationaliteitHistorie persoonNationaliteitHistorie) {
        delegate.addPersoonNationaliteitHistorie(persoonNationaliteitHistorie);
    }

    @Override
    public Nationaliteit getNationaliteit() {
        return delegate.getNationaliteit();
    }

    @Override
    public void setNationaliteit(final Nationaliteit nationaliteit) {
        delegate.setNationaliteit(nationaliteit);
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
    public RedenVerkrijgingNLNationaliteit getRedenVerkrijgingNLNationaliteit() {
        return delegate.getRedenVerkrijgingNLNationaliteit();
    }

    @Override
    public void setRedenVerkrijgingNLNationaliteit(final RedenVerkrijgingNLNationaliteit redenVerkrijgingNLNationaliteit) {
        delegate.setRedenVerkrijgingNLNationaliteit(redenVerkrijgingNLNationaliteit);
    }

    @Override
    public RedenVerliesNLNationaliteit getRedenVerliesNLNationaliteit() {
        return delegate.getRedenVerliesNLNationaliteit();
    }

    @Override
    public void setRedenVerliesNLNationaliteit(final RedenVerliesNLNationaliteit redenVerliesNLNationaliteit) {
        delegate.setRedenVerliesNLNationaliteit(redenVerliesNLNationaliteit);
    }

    @Override
    public Boolean getIndicatieBijhoudingBeeindigd() {
        return delegate.getIndicatieBijhoudingBeeindigd();
    }

    @Override
    public void setIndicatieBijhoudingBeeindigd(final Boolean indicatieBijhoudingBeeindigd) {
        delegate.setIndicatieBijhoudingBeeindigd(indicatieBijhoudingBeeindigd);
    }

    @Override
    public Integer getMigratieDatumEindeBijhouding() {
        return delegate.getMigratieDatumEindeBijhouding();
    }

    @Override
    public void setMigratieDatumEindeBijhouding(final Integer migratieDatumEindeBijhouding) {
        delegate.setMigratieDatumEindeBijhouding(migratieDatumEindeBijhouding);
    }

    @Override
    public String getMigratieRedenOpnameNationaliteit() {
        return delegate.getMigratieRedenOpnameNationaliteit();
    }

    @Override
    public void setMigratieRedenOpnameNationaliteit(final String migratieRedenOpnameNationaliteit) {
        delegate.setMigratieRedenOpnameNationaliteit(migratieRedenOpnameNationaliteit);
    }

    @Override
    public String getMigratieRedenBeeindigenNationaliteit() {
        return delegate.getMigratieRedenBeeindigenNationaliteit();
    }

    @Override
    public void setMigratieRedenBeeindigenNationaliteit(final String migratieRedenBeeindigenNationaliteit) {
        delegate.setMigratieRedenBeeindigenNationaliteit(migratieRedenBeeindigenNationaliteit);
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
    public Map<String, Collection<FormeleHistorie>> verzamelHistorie() {
        return delegate.verzamelHistorie();
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
    public String toString() {
        return delegate.toString();
    }
}
