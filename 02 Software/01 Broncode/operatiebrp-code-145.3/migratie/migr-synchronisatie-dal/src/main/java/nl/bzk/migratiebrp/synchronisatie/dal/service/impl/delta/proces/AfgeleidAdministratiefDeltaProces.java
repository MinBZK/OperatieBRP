/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.proces;

import java.sql.Timestamp;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BRPActie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.FormeleHistorieZonderVerantwoording;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAfgeleidAdministratiefHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.migratiebrp.synchronisatie.dal.service.impl.delta.DeltaBepalingContext;

/**
 * Implementatie voor het bepalen en verwerken van de delta op afgeleid administratief.
 */
public final class AfgeleidAdministratiefDeltaProces implements DeltaProces {

    @Override
    public void bepaalVerschillen(final DeltaBepalingContext context) {
        // niet relevant voor deze groep
    }

    @Override
    public void verwerkVerschillen(final DeltaBepalingContext context) {
        for (final AdministratieveHandeling administratieveHandeling : context.getAdministratieveHandelingen()) {
            voegAfgeleidAdministratiefToe(
                    context.getBestaandePersoon(),
                    context.getNieuwePersoon().getPersoonAfgeleidAdministratiefHistorieSet(),
                    administratieveHandeling);
        }
    }

    private void voegAfgeleidAdministratiefToe(
            final Persoon bestaandePersoon,
            final Set<PersoonAfgeleidAdministratiefHistorie> afgeleidAdministratiefHistorieSet,
            final AdministratieveHandeling administratieveHandeling) {
        final BRPActie cat07Actie =
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(bestaandePersoon.getPersoonInschrijvingHistorieSet()).getActieInhoud();
        final AdministratieveHandeling cat07AdministratieveHandeling = cat07Actie.getAdministratieveHandeling();
        final PersoonAfgeleidAdministratiefHistorie vorigActueleVoorkomen =
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(bestaandePersoon.getPersoonAfgeleidAdministratiefHistorieSet());

        // Afgeleid administratief historie wordt altijd aangemaakt tijdens conversie
        final PersoonAfgeleidAdministratiefHistorie basisAfgeleidAdministratiefHistorie =
                FormeleHistorieZonderVerantwoording.getActueelHistorieVoorkomen(afgeleidAdministratiefHistorieSet);

        final Timestamp tsReg = administratieveHandeling.getDatumTijdRegistratie();
        final PersoonAfgeleidAdministratiefHistorie historie =
                getPersoonAfgeleidAdministratiefHistorie(
                        bestaandePersoon,
                        cat07AdministratieveHandeling,
                        basisAfgeleidAdministratiefHistorie,
                        administratieveHandeling,
                        tsReg);
        historie.setDatumTijdRegistratie(tsReg);
        historie.setDatumTijdLaatsteWijzigingGba(tsReg);
        historie.setDatumTijdLaatsteWijziging(tsReg);
        bestaandePersoon.addPersoonAfgeleidAdministratiefHistorie(historie);

        vorigActueleVoorkomen.setActieVerval(historie.getActieInhoud());
        vorigActueleVoorkomen.setDatumTijdVerval(historie.getDatumTijdRegistratie());
    }

    private PersoonAfgeleidAdministratiefHistorie getPersoonAfgeleidAdministratiefHistorie(
            final Persoon bestaandePersoon,
            final AdministratieveHandeling cat07AdministratieveHandeling,
            final PersoonAfgeleidAdministratiefHistorie basisAfgeleidAdministratiefHistorie,
            final AdministratieveHandeling administratieveHandeling,
            final Timestamp tsReg) {
        final PersoonAfgeleidAdministratiefHistorie historie;
        if (administratieveHandeling.equals(cat07AdministratieveHandeling)) {
            // Deze administratieve handeling is gekoppeld aan de gegevens uit de cat07 van de LO3
            // persoonslijst.
            historie = basisAfgeleidAdministratiefHistorie;
            historie.setAdministratieveHandeling(administratieveHandeling);
            // De Actie Inhoud heeft een andere AH dan de rest van de PL. Deze nu gelijk trekken met die aan bv
            // Inschrijving groep hangt
            final BRPActie actieInhoud = historie.getActieInhoud();
            if (actieInhoud != null) {
                actieInhoud.setAdministratieveHandeling(administratieveHandeling);
            }
        } else {
            final BRPActie actieInhoud = new BRPActie(SoortActie.CONVERSIE_GBA, administratieveHandeling, administratieveHandeling.getPartij(), tsReg);
            historie = new PersoonAfgeleidAdministratiefHistorie((short) 1, bestaandePersoon, administratieveHandeling, tsReg);
            historie.setActieInhoud(actieInhoud);
        }
        return historie;
    }
}
