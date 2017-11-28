/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.dataaccess.bevraging;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.HistoriePatroon;

/**
 * Tabel.
 */
class Tabel {
    private String naam;
    private String alias;
    private HistoriePatroon historiePatroon;

    /**
     * @param naam naam
     * @param alias alias
     * @param historiePatroon historiePatroon
     */
    Tabel(final String naam, final String alias, final HistoriePatroon historiePatroon) {
        this.naam = naam;
        this.alias = alias;
        this.historiePatroon = historiePatroon;
    }

    /**
     * @return naam
     */
    public String getNaam() {
        return naam;
    }

    /**
     * @return alias
     */
    public String getAlias() {
        return alias;
    }

    /**
     * @return is materieel
     */
    public boolean isMaterieel() {
        return historiePatroon != null && (historiePatroon == HistoriePatroon.F_M || historiePatroon == HistoriePatroon.F_M1);
    }

    /**
     * @return heeft formele historie
     */
    boolean heeftFormeleHistorie() {
        return historiePatroon != null && historiePatroon != HistoriePatroon.G && historiePatroon != HistoriePatroon.M1;
    }


    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Tabel tabel = (Tabel) o;

        return !(naam != null ? !naam.equals(tabel.naam) : tabel.naam != null);

    }

    @Override
    public int hashCode() {
        return naam != null ? naam.hashCode() : 0;
    }
}
