/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen.blob;

/**
 * Parameters specifiek voor het verwerken van afnemerindicatie mutaties.
 */
public class AfnemerindicatieParameters {
    private final Long persoonId;
    private final Long persoonLockVersie;
    private final Long afnemerindicatieLockVersie;

    /**
     * @param persoonId de persoon id
     * @param persoonLockVersie de lock versie van de persoon
     * @param afnemerindicatieLockVersie de lock versie van de afnemerindicatie
     */
    public AfnemerindicatieParameters(final Long persoonId, final Long persoonLockVersie, final Long afnemerindicatieLockVersie) {
        this.persoonId = persoonId;
        this.persoonLockVersie = persoonLockVersie;
        this.afnemerindicatieLockVersie = afnemerindicatieLockVersie;
    }

    public Long getPersoonId() {
        return persoonId;
    }

    public Long getPersoonLockVersie() {
        return persoonLockVersie;
    }

    public Long getAfnemerindicatieLockVersie() {
        return afnemerindicatieLockVersie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AfnemerindicatieParameters that = (AfnemerindicatieParameters) o;
        boolean equals = true;
        if (!persoonId.equals(that.persoonId)) {
            equals =  false;
        }
        if (equals && persoonLockVersie != null ? !persoonLockVersie.equals(that.persoonLockVersie) : that.persoonLockVersie != null) {
            equals = false;
        }
        return equals && afnemerindicatieLockVersie != null ? afnemerindicatieLockVersie.equals(that.afnemerindicatieLockVersie)
                : that.afnemerindicatieLockVersie == null;
    }

    @Override
    public int hashCode() {
        int result = persoonId.hashCode();
        result = 31 * result + (persoonLockVersie != null ? persoonLockVersie.hashCode() : 0);
        result = 31 * result + (afnemerindicatieLockVersie != null ? afnemerindicatieLockVersie.hashCode() : 0);
        return result;
    }
}
