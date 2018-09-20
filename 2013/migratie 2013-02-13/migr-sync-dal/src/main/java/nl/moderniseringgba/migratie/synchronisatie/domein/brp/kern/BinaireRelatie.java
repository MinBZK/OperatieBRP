/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern;

import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Betrokkenheid;
import nl.moderniseringgba.migratie.synchronisatie.domein.brp.kern.entity.Relatie;

/**
 * Deze class representeert een binaire relatie. Dit is een wrapper over twee betrokkenheden waarmee de richting van een
 * relatie tussen twee personen wordt uitgedrukt. Een huwelijk of geregistreerd partnerschap (gp) is al een binaire
 * relatie maar een familierechtelijke betrekking is dat niet. Deze class maakt het mogelijk om beide gevallen onder een
 * gemeenschappelijke noemer te plaatsen en ondersteund het relateren van een persoon.
 * 
 */
public final class BinaireRelatie {

    private final Betrokkenheid ikBetrokkenheid;
    private final Betrokkenheid andereBetrokkenheid;

    /**
     * Maakt een BinaireRelatie object.
     * 
     * @param ikBetrokkenheid
     *            de ik betrokkenheid, oftewel de start van de relatie
     * @param andereBetrokkenheid
     *            den andere betrokkenheid, oftewel het einde van de relatie
     */
    public BinaireRelatie(final Betrokkenheid ikBetrokkenheid, final Betrokkenheid andereBetrokkenheid) {
        this.ikBetrokkenheid = ikBetrokkenheid;
        this.andereBetrokkenheid = andereBetrokkenheid;
    }

    public Betrokkenheid getIkBetrokkenheid() {
        return ikBetrokkenheid;
    }

    public Betrokkenheid getAndereBetrokkenheid() {
        return andereBetrokkenheid;
    }

    /**
     * @return de relatie
     */
    public Relatie getRelatie() {
        return ikBetrokkenheid.getRelatie();
    }

    /**
     * @return een nieuwe BinaireRelatie object waarbij de richting is omgedraaid.
     */
    public BinaireRelatie inverse() {
        return new BinaireRelatie(andereBetrokkenheid, ikBetrokkenheid);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (andereBetrokkenheid == null ? 0 : andereBetrokkenheid.hashCode());
        result = prime * result + (ikBetrokkenheid == null ? 0 : ikBetrokkenheid.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof BinaireRelatie)) {
            return false;
        }
        final BinaireRelatie other = (BinaireRelatie) obj;
        if (andereBetrokkenheid == null) {
            if (other.andereBetrokkenheid != null) {
                return false;
            }
        } else if (!andereBetrokkenheid.equals(other.andereBetrokkenheid)) {
            return false;
        }
        if (ikBetrokkenheid == null) {
            if (other.ikBetrokkenheid != null) {
                return false;
            }
        } else if (!ikBetrokkenheid.equals(other.ikBetrokkenheid)) {
            return false;
        }
        return true;
    }
}
