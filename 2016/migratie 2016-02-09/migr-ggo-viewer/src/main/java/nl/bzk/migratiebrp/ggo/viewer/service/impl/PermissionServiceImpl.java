/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.service.impl;

import java.util.List;
import javax.inject.Inject;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3HuwelijkOfGpInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3KindInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.categorie.Lo3OuderInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Long;
import nl.bzk.migratiebrp.ggo.viewer.service.DbService;
import nl.bzk.migratiebrp.ggo.viewer.service.PermissionService;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3Bericht;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Component;

/**
 * Geeft aan of de huidige gebruiker leesrechten heeft op de gevraagde pl.
 */
@Component
public class PermissionServiceImpl implements PermissionService {

    @Inject
    private DbService dbService;

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean hasPermissionOnPl(final Lo3Persoonslijst lo3Persoonslijst) {
        boolean permission;
        final String gemeenteCode = retrieveGemeenteCode(lo3Persoonslijst);
        final Subject currentUser = SecurityUtils.getSubject();

        if (currentUser.hasRole("ROLE_ADMIN")) {
            // ROLE_ADMIN gebruikers mogen alles inzien.
            permission = true;
        } else if (currentUser.hasRole(gemeenteCode)) {
            permission = true;
        } else {
            // Check of het anummer van een gerelateerde een is waar de gemeente wel recht op heeft
            permission = hasPermissionOnRelaties(lo3Persoonslijst, currentUser);
        }

        return permission;
    }

    /**
     * Kijkt of de huidige gebruiker voor een gerelateerde Persoonslijst geautoriseerd is (02-Ouder1, 03-Ouder2,
     * 05-Huwelijk en 09-Kind).
     * 
     * @param lo3Persoonslijst
     *            Lo3Persoosnlijst
     * @param currentUser
     *            Subject shiro
     * @return boolean permission
     */
    private boolean hasPermissionOnRelaties(final Lo3Persoonslijst lo3Persoonslijst, final Subject currentUser) {
        boolean permission;

        permission = hasPermissionOnOuder(lo3Persoonslijst.getOuder1Stapel(), currentUser);

        if (!permission) {
            permission = hasPermissionOnOuder(lo3Persoonslijst.getOuder2Stapel(), currentUser);
        }
        if (!permission) {
            permission = hasPermissionOnHuwelijk(lo3Persoonslijst.getHuwelijkOfGpStapels(), currentUser);
        }
        if (!permission) {
            permission = hasPermissionOnKind(lo3Persoonslijst.getKindStapels(), currentUser);
        }

        return permission;
    }

    private boolean hasPermissionOnOuder(final Lo3Stapel<Lo3OuderInhoud> ouderStapel, final Subject currentUser) {
        boolean permission = false;
        if (ouderStapel != null && ouderStapel.getLaatsteElement().getInhoud() != null) {
            final Long gerelateerdeAnummer = Lo3Long.unwrap(ouderStapel.getLaatsteElement().getInhoud().getaNummer());
            permission = hasPermissionOnRelatie(gerelateerdeAnummer, currentUser);
        }
        return permission;
    }

    private boolean hasPermissionOnHuwelijk(final List<Lo3Stapel<Lo3HuwelijkOfGpInhoud>> huwelijkStapels, final Subject currentUser) {
        boolean permission = false;
        for (final Lo3Stapel<Lo3HuwelijkOfGpInhoud> huwelijkStapel : huwelijkStapels) {
            if (!permission && huwelijkStapel != null && huwelijkStapel.getLaatsteElement().getInhoud() != null) {
                final Long gerelateerdeAnummer = Lo3Long.unwrap(huwelijkStapel.getLaatsteElement().getInhoud().getaNummer());
                permission = hasPermissionOnRelatie(gerelateerdeAnummer, currentUser);
            }
        }
        return permission;
    }

    private boolean hasPermissionOnKind(final List<Lo3Stapel<Lo3KindInhoud>> kindStapels, final Subject currentUser) {
        boolean permission = false;
        for (final Lo3Stapel<Lo3KindInhoud> kindStapel : kindStapels) {
            if (!permission && kindStapel != null && kindStapel.getLaatsteElement().getInhoud() != null) {
                final Long gerelateerdeAnummer = Lo3Long.unwrap(kindStapel.getLaatsteElement().getInhoud().getaNummer());
                permission = hasPermissionOnRelatie(gerelateerdeAnummer, currentUser);
            }
        }
        return permission;
    }

    /**
     * Zoekt de gerelateerde Lo3 Persoonslijst op en controleert of de gebruiker geautoriseerd is deze Persoonslijst te
     * zien.
     * 
     * @param gerelateerdeAnummer
     *            Long
     * @param currentUser
     *            Subject shiro
     * @return boolean permission
     */
    private boolean hasPermissionOnRelatie(final Long gerelateerdeAnummer, final Subject currentUser) {
        boolean permission = false;
        if (gerelateerdeAnummer != null) {
            final Lo3Bericht lo3Bericht = dbService.zoekLo3Bericht(gerelateerdeAnummer);
            if (lo3Bericht != null) {
                final Lo3Persoonslijst relatieLo3Pl = dbService.haalLo3PersoonslijstUitLo3Bericht(lo3Bericht);
                final String gerelateerdeGemeenteCode = retrieveGemeenteCode(relatieLo3Pl);
                permission = currentUser.hasRole(gerelateerdeGemeenteCode);
            }
        }
        return permission;
    }

    /**
     * Haalt de gemeenteCode (08.09.10 gemeente van inschrijving) uit de Lo3Persoonslijst.
     * 
     * @param lo3Persoonslijst
     *            Lo3Persoonslijst
     * @return gemeenteCode String
     */
    private String retrieveGemeenteCode(final Lo3Persoonslijst lo3Persoonslijst) {
        String gemeenteCode = null;

        // Altijd true wegens de invariant op stapel (die mag niet leeg zijn):
        // lo3Persoonslijst.getVerblijfplaatsStapel().getLaatsteElement() != null
        if (lo3Persoonslijst != null
            && lo3Persoonslijst.getVerblijfplaatsStapel() != null
            && lo3Persoonslijst.getVerblijfplaatsStapel().getLaatsteElement().getInhoud() != null
            && lo3Persoonslijst.getVerblijfplaatsStapel().getLaatsteElement().getInhoud().getGemeenteInschrijving() != null)
        {
            gemeenteCode = lo3Persoonslijst.getVerblijfplaatsStapel().getLaatsteElement().getInhoud().getGemeenteInschrijving().getWaarde();

        }
        return gemeenteCode;
    }
}
