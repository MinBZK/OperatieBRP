/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.dataaccess.bevraging;

import com.google.common.collect.Sets;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Zoekoptie;
import nl.bzk.brp.domain.algemeen.ZoekCriterium;
import nl.bzk.brp.domain.element.ElementHelper;
import nl.bzk.brp.domain.element.GroepElement;
import nl.bzk.brp.domain.element.ObjectElement;

/**
 * ZoekCriteria.
 */
final class ZoekCriteria {
    private final Set<ZoekCriterium> zoekCriteriaSet;
    private final Set<BestaatNietClauseActueel> bestaatNietClausesActueel;
    private final Set<BestaatNietClauseHistorisch> bestaatNietClausesHistorisch;
    private final boolean historischeZoekvraag;
    private Integer materieelPeilmoment;
    private boolean peilperiode;
    private int maxResults;

    ZoekCriteria(Set<ZoekCriterium> zoekCriteriaCompleet, boolean historischeZoekvraag) {
        final Set<ZoekCriterium> zoekCriteriaZonderLeeg = new HashSet<>(zoekCriteriaCompleet);
        if (historischeZoekvraag) {
            this.bestaatNietClausesActueel = Sets.newHashSet();
            this.bestaatNietClausesHistorisch = maakBestaatNietHistorischClauses(zoekCriteriaCompleet, zoekCriteriaZonderLeeg);

        } else {
            this.bestaatNietClausesHistorisch = Sets.newHashSet();
            this.bestaatNietClausesActueel = maakBestaatNietActueelClauses(zoekCriteriaCompleet, zoekCriteriaZonderLeeg);
        }
        this.zoekCriteriaSet = zoekCriteriaZonderLeeg;
        this.historischeZoekvraag = historischeZoekvraag;
    }

    private Set<BestaatNietClauseActueel> maakBestaatNietActueelClauses(Set<ZoekCriterium> zoekCriteriaCompleet, Set<ZoekCriterium> zoekCriteriaZonderLeeg) {
        final Set<BestaatNietClauseActueel> bestaatNietClausesTemp = new HashSet<>();
        //groepeer zoekcriteria per object om te bekijken of not exists vraag wordt gesteld
        final Map<ObjectElement, Set<ZoekCriterium>> objectElementZoekCriteria = new HashMap<>();
        for (ZoekCriterium zoekCriterion : zoekCriteriaCompleet) {
            objectElementZoekCriteria.computeIfAbsent(ElementHelper.getObjectElement(zoekCriterion.getElement().getObjectType()), e -> Sets.newHashSet())
                    .add(zoekCriterion);
        }
        for (Map.Entry<ObjectElement, Set<ZoekCriterium>> objectElementSetEntry : objectElementZoekCriteria.entrySet()) {
            final BestaatNietClauseActueel
                    bestaatNietClause =
                    new BestaatNietClauseActueel(objectElementSetEntry.getKey());

            final boolean voegToe = vulBestaatNietClause(objectElementSetEntry, bestaatNietClause);
            if (voegToe) {
                bestaatNietClausesTemp.add(bestaatNietClause);
                zoekCriteriaZonderLeeg.removeAll(bestaatNietClause.getZoekCriteria());
            }
        }
        return bestaatNietClausesTemp;
    }

    private Set<BestaatNietClauseHistorisch> maakBestaatNietHistorischClauses(Set<ZoekCriterium> zoekCriteriaCompleet,
                                                                              Set<ZoekCriterium> zoekCriteriaZonderLeeg) {
        final Set<BestaatNietClauseHistorisch> bestaatNietClausesTemp = new HashSet<>();
        //groepeer zoekcriteria per object om te bekijken of not exists vraag wordt gesteld
        final Map<GroepElement, Set<ZoekCriterium>> groepElementZoekCriteria = new HashMap<>();
        for (ZoekCriterium zoekCriterion : zoekCriteriaCompleet) {
            groepElementZoekCriteria.computeIfAbsent(zoekCriterion.getElement().getGroep(), e -> Sets.newHashSet())
                    .add(zoekCriterion);
        }
        for (Map.Entry<GroepElement, Set<ZoekCriterium>> groepElementSetEntry : groepElementZoekCriteria.entrySet()) {
            final BestaatNietClauseHistorisch
                    bestaatNietClause =
                    new BestaatNietClauseHistorisch(groepElementSetEntry.getKey());

            final boolean voegToe = vulBestaatNietClause(groepElementSetEntry, bestaatNietClause);
            if (voegToe) {
                bestaatNietClausesTemp.add(bestaatNietClause);
                zoekCriteriaZonderLeeg.removeAll(bestaatNietClause.getZoekCriteria());
            }
        }
        return bestaatNietClausesTemp;
    }

    private boolean vulBestaatNietClause(Map.Entry<ObjectElement, Set<ZoekCriterium>> objectElementSetEntry, BestaatNietClauseActueel bestaatNietClause) {
        final Set<ZoekCriterium> zoekCriteria = objectElementSetEntry.getValue();
        for (ZoekCriterium zoekCriterionVoorObject : zoekCriteria) {
            if (zoekCriterionVoorObject.getZoekOptie() == Zoekoptie.LEEG) {
                bestaatNietClause.getZoekCriteria().add(zoekCriterionVoorObject);
            } else {
                return false;
            }
        }
        return true;
    }

    private boolean vulBestaatNietClause(Map.Entry<GroepElement, Set<ZoekCriterium>> groepElementSetEntry, BestaatNietClauseHistorisch bestaatNietClause) {
        final Set<ZoekCriterium> zoekCriteria = groepElementSetEntry.getValue();
        for (ZoekCriterium zoekCriterionVoorGroep : zoekCriteria) {
            if (zoekCriterionVoorGroep.getZoekOptie() == Zoekoptie.LEEG) {
                bestaatNietClause.getZoekCriteria().add(zoekCriterionVoorGroep);
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * @return zoekcriteria
     */
    public Set<ZoekCriterium> getZoekCriteria() {
        return zoekCriteriaSet;
    }

    /**
     * @return bestaatNietClausesActueel
     */
    Set<BestaatNietClauseActueel> getBestaatNietClausesActueel() {
        return bestaatNietClausesActueel;
    }

    /**
     * @return maxResults
     */
    int getMaxResults() {
        return maxResults;
    }

    /**
     * @param maxResults maxResults
     */
    void setMaxResults(int maxResults) {
        this.maxResults = maxResults;
    }

    /**
     * @return bestaatNietClausesHistorisch
     */
    public Set<BestaatNietClauseHistorisch> getBestaatNietClausesHistorisch() {
        return bestaatNietClausesHistorisch;
    }

    /**
     * @return historischeZoekvraag
     */
    boolean isHistorischeZoekvraag() {
        return historischeZoekvraag;
    }

    /**
     * @return materieelPeilmoment
     */
    Integer getMaterieelPeilmoment() {
        return materieelPeilmoment;
    }

    /**
     * @param materieelPeilmoment materieelPeilmoment
     */
    void setMaterieelPeilmoment(Integer materieelPeilmoment) {
        this.materieelPeilmoment = materieelPeilmoment;
    }

    /**
     * @return peilperiode
     */
    boolean isPeilperiode() {
        return peilperiode;
    }

    /**
     * @param peilperiode peilperiode
     */
    void setPeilperiode(boolean peilperiode) {
        this.peilperiode = peilperiode;
    }
}
