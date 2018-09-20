/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.regels.impl.gegevenset.definitieregels;

import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.logisch.kern.PersoonGeboorteGroep;
import nl.bzk.brp.model.logisch.kern.PersoonGeslachtsaanduidingGroep;
import nl.bzk.brp.model.logisch.kern.PersoonSamengesteldeNaamGroep;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * Vergelijken Niet-Ingeschreven Personen
 *
 * De gegevens van een tweetal NietIngeschrevenen komen exact overeen indien de (ontbrekende) waarde van alle
 * algemene attributen van de groepen Geboorte, Geslachtsaanduiding en Samengestelde naam exact overeenkomen.
 *
 * @brp.bedrijfsregel BRBY0007
 */
public class BRBY0007 {

    /**
     * Controleert of de twee personen gelijk zijn volgende de BRBY0007 regel.
     *
     * @param persoon1 persoon
     * @param persoon2 persoon
     * @return true als gelijk
     */
    public final boolean isGelijk(final Persoon persoon1, final Persoon persoon2) {
        return isSamengesteldeNaamIdentiek(persoon1.getSamengesteldeNaam(), persoon2.getSamengesteldeNaam())
                && isGeboorteIdentiek(persoon1.getGeboorte(), persoon2.getGeboorte())
                && isGeslachtsaanduidingIdentiek(persoon1.getGeslachtsaanduiding(), persoon2.getGeslachtsaanduiding());
    }


    /**
     * Controleert of twee samengestelde naam groepen identiek zijn.
     *
     * @param samengesteldeNaamGroep1 de eerste samengestelde naam
     * @param samengesteldeNaamGroep2 de tweede samengestelde naam
     * @return true indien gelijk, anders false
     */
    private boolean isSamengesteldeNaamIdentiek(final PersoonSamengesteldeNaamGroep samengesteldeNaamGroep1,
                                                final PersoonSamengesteldeNaamGroep samengesteldeNaamGroep2)
    {
        return samengesteldeNaamGroep1 != null && samengesteldeNaamGroep2 != null && new EqualsBuilder()
                .append(samengesteldeNaamGroep1.getVoornamen(), samengesteldeNaamGroep2.getVoornamen())
                .append(samengesteldeNaamGroep1.getVoorvoegsel(), samengesteldeNaamGroep2.getVoorvoegsel())
                .append(samengesteldeNaamGroep1.getScheidingsteken(), samengesteldeNaamGroep2.getScheidingsteken())
                .append(samengesteldeNaamGroep1.getGeslachtsnaamstam(), samengesteldeNaamGroep2.getGeslachtsnaamstam())
                .isEquals();
    }

    /**
     * Controleert of de twee geboorte groep identiek zijn.
     * @param geboorteGroep1 de eerste geboorte groep
     * @param geboorteGroep2 de tweede geboorte groep
     * @return true indien gelijk, anders false
     */
    private boolean isGeboorteIdentiek(final PersoonGeboorteGroep geboorteGroep1,
                                       final PersoonGeboorteGroep geboorteGroep2)
    {
        if (geboorteGroep1 == null || geboorteGroep2 == null) {
            return false;
        }
        return gemeentesGelijk(geboorteGroep1, geboorteGroep2)
            && plaatsGeboorteGelijk(geboorteGroep1, geboorteGroep2)
            && landGeboorteGelijk(geboorteGroep1, geboorteGroep2)
            && groepenGelijk(geboorteGroep1, geboorteGroep2);
    }

    private boolean groepenGelijk(final PersoonGeboorteGroep geboorteGroep1, final PersoonGeboorteGroep geboorteGroep2) {
        return new EqualsBuilder()
        .append(geboorteGroep1.getDatumGeboorte(), geboorteGroep2.getDatumGeboorte())
        .append(geboorteGroep1.getBuitenlandsePlaatsGeboorte(),
            geboorteGroep2.getBuitenlandsePlaatsGeboorte())
        .append(geboorteGroep1.getBuitenlandseRegioGeboorte(),
            geboorteGroep2.getBuitenlandseRegioGeboorte())
        .append(geboorteGroep1.getOmschrijvingLocatieGeboorte(),
            geboorteGroep2.getOmschrijvingLocatieGeboorte()).isEquals();
    }

    private boolean landGeboorteGelijk(final PersoonGeboorteGroep geboorteGroep1, final PersoonGeboorteGroep geboorteGroep2) {
        boolean result;
        if (geboorteGroep1.getLandGebiedGeboorte() != null && geboorteGroep2.getLandGebiedGeboorte() != null) {
            result = geboorteGroep1.getLandGebiedGeboorte().getWaarde().getCode().equals(
                    geboorteGroep2.getLandGebiedGeboorte().getWaarde().getCode());
        } else {
            result = geboorteGroep1.getLandGebiedGeboorte() == null
                    && geboorteGroep2.getLandGebiedGeboorte() == null;
        }
        return result;
    }

    private boolean plaatsGeboorteGelijk(final PersoonGeboorteGroep geboorteGroep1, final PersoonGeboorteGroep geboorteGroep2) {
        boolean result;
        if (geboorteGroep1.getWoonplaatsnaamGeboorte() != null && geboorteGroep2.getWoonplaatsnaamGeboorte() != null) {
            result = geboorteGroep1.getWoonplaatsnaamGeboorte().getWaarde().equals(
                    geboorteGroep2.getWoonplaatsnaamGeboorte().getWaarde());
        } else {
            result = geboorteGroep1.getWoonplaatsnaamGeboorte() == null
                    && geboorteGroep2.getWoonplaatsnaamGeboorte() == null;
        }
        return result;
    }

    private boolean gemeentesGelijk(final PersoonGeboorteGroep geboorteGroep1, final PersoonGeboorteGroep geboorteGroep2) {
        boolean result;
        if (geboorteGroep1.getGemeenteGeboorte() != null && geboorteGroep2.getGemeenteGeboorte() != null) {
            result = geboorteGroep1.getGemeenteGeboorte().getWaarde().getCode().equals(
                    geboorteGroep2.getGemeenteGeboorte().getWaarde().getCode());
        } else {
            result = geboorteGroep1.getGemeenteGeboorte() == null
                    && geboorteGroep2.getGemeenteGeboorte() == null;
        }
        return result;
    }

    /**
     * Controleert of de twee geslachtsaanduiding groep identiek zijn.
     * @param geslachtsaanduidingGroep1 de eerste geslachtsaanduiding groep
     * @param geslachtsaanduidingGroep2 de tweede geslachtsaanduiding groep
     * @return true indien gelijk, anders false
     */
    private boolean isGeslachtsaanduidingIdentiek(final PersoonGeslachtsaanduidingGroep geslachtsaanduidingGroep1,
                                                  final PersoonGeslachtsaanduidingGroep geslachtsaanduidingGroep2)
    {
        return geslachtsaanduidingGroep1 != null && geslachtsaanduidingGroep2 != null && new EqualsBuilder()
                .append(geslachtsaanduidingGroep1.getGeslachtsaanduiding(),
                        geslachtsaanduidingGroep2.getGeslachtsaanduiding()).isEquals();
    }
}
