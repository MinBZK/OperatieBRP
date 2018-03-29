/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.leveringmodel.persoon;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import nl.bzk.algemeenbrp.dal.domein.brp.annotatie.Bedrijfsregel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling;
import nl.bzk.brp.domain.leveringmodel.MetaObject;

/**
 * Een factory implementatie voor het maken van nieuwe persoonsbeelden.
 */
public final class PersoonslijstBeeldFactory {

    private static final int INDEX_VOOR_LAATSTE = 2;
    private final Persoonslijst persoonslijst;

    private Persoonslijst vorigeHandeling;

    /**
     * Constructor.
     * @param persoonslijst een persoonslijst
     */
    PersoonslijstBeeldFactory(final Persoonslijst persoonslijst) {
        this.persoonslijst = persoonslijst;
    }

    /**
     * Geeft de {@link Persoonslijst} op het gegeven materiele peilmoment.
     * Het formele peilmoment verandert niet, dat blijft het tijdstipRegistratie van de op dat moment
     * actuele administratievehandeling {@link Persoonslijst#getAdministratieveHandeling()}
     * <p>
     * Let dit is geen puntmoment. Formele en materiele historie wordt behouden.
     * @param materieelPeilMoment materieel peilmoment
     * @return het beeld op gegeven materieel peilmoment
     */
    public Persoonslijst materieelPeilmoment(final int materieelPeilMoment) {
        final Persoonslijst nuNuPersoonslijst = persoonslijst.getNuNuBeeld();
        final MetaObjectFilter metaObjectFilter = new MetaObjectFilter(nuNuPersoonslijst.getMetaObject());
        metaObjectFilter.addRecordFilter(new WisToekomstigMaterieelRecordPredicate(materieelPeilMoment));
        metaObjectFilter.addAttribuutFilter(new WisToekomstigMaterieelAttribuutPredicate(materieelPeilMoment));
        final MetaObject gefilterdObject = metaObjectFilter.filter();
        return new Persoonslijst(gefilterdObject, nuNuPersoonslijst, persoonslijst.getAfnemerindicatieLockVersie());
    }

    /**
     * Geeft de {@link Persoonslijst} op het gegeven formele peilmoment.
     * @param formeelTijdstip formeelTijdstip
     * @return het beeld op het gegeven formeel peilmoment
     * @throws IllegalStateException als er geen handeling bestaat op het gegeven formele peilmoment
     */
    public Persoonslijst formeelPeilmoment(final ZonedDateTime formeelTijdstip) {
        final Persoonslijst nuNuPersoonslijst = persoonslijst.getNuNuBeeld();
        final List<AdministratieveHandeling> administratieveHandelings =
                Lists.newArrayList(nuNuPersoonslijst.getAdministratieveHandelingen());
        Collections.reverse(administratieveHandelings);

        for (final AdministratieveHandeling administratieveHandeling : administratieveHandelings) {
            if (!formeelTijdstip.isBefore(administratieveHandeling.getTijdstipRegistratie())) {
                return administratievehandeling(administratieveHandeling.getId());
            }
        }
        throw new IllegalStateException("Er bestaat geen administratievehandeling op het gegeven formele peilmoment");
    }

    /**
     * Maakt een {@link Persoonslijst} zoals dat eruit zag op het moment (direct na) de
     * gegeven administratievehandeling.
     * @param administratieveHandelingId het id van de administratievehandeling
     * @return het actuele persoonsbeeld.
     */
    @Bedrijfsregel(Regel.R1556)
    public Persoonslijst administratievehandeling(final long administratieveHandelingId) {
        final Persoonslijst nuNuPersoonslijst = persoonslijst.getNuNuBeeld();
        AdministratieveHandeling match = null;
        for (AdministratieveHandeling administratieveHandeling : nuNuPersoonslijst.getAdministratieveHandelingen()) {
            if (administratieveHandeling.getId() == administratieveHandelingId) {
                match = administratieveHandeling;
                break;
            }
        }
        if (match == null) {
            throw new IllegalArgumentException("Administratievehandeling bestaat niet: " + administratieveHandelingId);
        }
        return administratievehandeling(match);
    }

    /**
     * Geeft de {@link Persoonslijst} van de handeling vòòr de huidige handeling.
     * Geeft null als er geen vorige handeling is.
     * @return een {@link Persoonslijst}
     */
    public Persoonslijst vorigeHandeling() {
        if (vorigeHandeling != null) {
            return vorigeHandeling;
        }
        if (persoonslijst.getAdministratieveHandelingen().size() == 1) {
            //er is geen vorige handeling
            return null;
        }
        final List<AdministratieveHandeling> handelingen = persoonslijst.getAdministratieveHandelingen();
        this.vorigeHandeling = administratievehandeling(handelingen.get(handelingen.size() - INDEX_VOOR_LAATSTE));
        return this.vorigeHandeling;
    }

    /**
     * Maakt een nieuw {@link Persoonslijst} obv het gegeven filter.
     * @param filter het filter.
     * @return de persoonslijst
     */
    public Persoonslijst filter(final Consumer<MetaObjectFilter> filter) {
        final MetaObjectFilter metaObjectFilter = new MetaObjectFilter(persoonslijst.getMetaObject());
        filter.accept(metaObjectFilter);
        final MetaObject gefilterdObject = metaObjectFilter.filter();
        return new Persoonslijst(gefilterdObject, persoonslijst.getNuNuBeeld(), persoonslijst.getAfnemerindicatieLockVersie());
    }

    private Set<AdministratieveHandeling> getToekomstigeHandelingen(final AdministratieveHandeling administratieveHandeling) {
        final Persoonslijst nuNuPersoonslijst = persoonslijst.getNuNuBeeld();
        final List<AdministratieveHandeling> administratieveHandelingen = Lists.newArrayList(nuNuPersoonslijst.getAdministratieveHandelingen());
        final int index = administratieveHandelingen.indexOf(administratieveHandeling);
        return Sets.newHashSet(administratieveHandelingen.subList(index + 1, administratieveHandelingen.size()));
    }

    private Persoonslijst administratievehandeling(final AdministratieveHandeling administratieveHandeling) {
        final Persoonslijst nuNuPersoonslijst = persoonslijst.getNuNuBeeld();
        if (Iterables.getLast(nuNuPersoonslijst.getAdministratieveHandelingen()) == administratieveHandeling) {
            return nuNuPersoonslijst;
        } else {
            final Set<AdministratieveHandeling> toekomstigeHandelingen = getToekomstigeHandelingen(administratieveHandeling);
            final MetaObjectFilter metaObjectFilter = new MetaObjectFilter(nuNuPersoonslijst.getMetaObject());
            metaObjectFilter.addRecordFilter(new WisToekomstigFormeelRecordPredicate(toekomstigeHandelingen));
            metaObjectFilter.addRecordFilter(new WisOverigeMutLevRecordPredicate(toekomstigeHandelingen));
            metaObjectFilter.addAttribuutFilter(new WisToekomstigFormeelAttribuutPredicate(toekomstigeHandelingen));
            final MetaObject gefilterdObject = metaObjectFilter.filter();
            return new Persoonslijst(gefilterdObject, nuNuPersoonslijst, persoonslijst.getAfnemerindicatieLockVersie());
        }
    }
}
