/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.viewer.service.impl;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.conversie.model.brp.BrpBetrokkenheid;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpRelatie;
import nl.moderniseringgba.migratie.conversie.viewer.service.DbService;
import nl.moderniseringgba.migratie.conversie.viewer.service.PermissionService;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Component;

/**
 * Geeft aan of de huidige gebruiker leesrechten heeft op de gevraagde pl. *
 */
@Component
public class PermissionServiceImpl implements PermissionService {

    @Inject
    private DbService dbService;

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean hasPermissionOnPl(final BrpPersoonslijst brpPersoonslijst) {
        boolean permission = false;
        final String gemeenteCode = retrieveGemeenteCode(brpPersoonslijst);
        final Subject currentUser = SecurityUtils.getSubject();

        if (currentUser.hasRole("ROLE_ADMIN")) {
            // ROLE_ADMIN gebruikers mogen alles inzien.
            permission = true;
        } else if (currentUser.hasRole(gemeenteCode)) {
            permission = true;
        } else {
            // Check of het anummer van een gerelateerde is, waar de gemeente wel recht op heeft
            for (final BrpRelatie relatie : brpPersoonslijst.getRelaties()) {
                for (final BrpBetrokkenheid betrokkenheid : relatie.getBetrokkenheden()) {
                    final Long gerelateerdeAnummer = retrieveAnrBetrokkenheid(betrokkenheid);
                    if (gerelateerdeAnummer != null) {
                        final BrpPersoonslijst gerelateerdeBrpPersoonslijst =
                                dbService.zoekBrpPersoonsLijst(gerelateerdeAnummer);
                        final String gerelateerdeGemeenteCode = retrieveGemeenteCode(gerelateerdeBrpPersoonslijst);
                        permission = currentUser.hasRole(gerelateerdeGemeenteCode);
                        if (permission) {
                            return permission;
                        }
                    }
                }
            }
        }
        return permission;
    }

    private Long retrieveAnrBetrokkenheid(final BrpBetrokkenheid betrokkenheid) {
        Long anr = null;
        if (betrokkenheid != null && betrokkenheid.getIdentificatienummersStapel() != null
                && betrokkenheid.getIdentificatienummersStapel().getMeestRecenteElement() != null
                && betrokkenheid.getIdentificatienummersStapel().getMeestRecenteElement().getInhoud() != null) {
            anr =
                    betrokkenheid.getIdentificatienummersStapel().getMeestRecenteElement().getInhoud()
                            .getAdministratienummer();
        }
        return anr;
    }

    private String retrieveGemeenteCode(final BrpPersoonslijst brpPersoonslijst) {
        String gemeenteCode = null;
        if (brpPersoonslijst != null) {
            if (brpPersoonslijst.getBijhoudingsgemeenteStapel() != null
                    && brpPersoonslijst.getBijhoudingsgemeenteStapel().getMeestRecenteElement() != null
                    && brpPersoonslijst.getBijhoudingsgemeenteStapel().getMeestRecenteElement().getInhoud() != null
                    && brpPersoonslijst.getBijhoudingsgemeenteStapel().getMeestRecenteElement().getInhoud()
                            .getBijhoudingsgemeenteCode() != null) {
                gemeenteCode =
                        brpPersoonslijst.getBijhoudingsgemeenteStapel().getMeestRecenteElement().getInhoud()
                                .getBijhoudingsgemeenteCode().getFormattedStringCode();
            }

        }
        return gemeenteCode;
    }

}
