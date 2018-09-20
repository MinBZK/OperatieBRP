/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import java.util.List;

import javax.validation.Valid;

import nl.bzk.brp.model.bericht.kern.basis.AbstractPersoonBericht;
import nl.bzk.brp.model.logisch.kern.Persoon;
import nl.bzk.brp.model.validatie.MeldingCode;
import nl.bzk.brp.model.validatie.constraint.CollectieMetUniekeWaarden;


/**
 * Een persoon is een drager van rechten en plichten die een mens is.
 *
 * Buiten de BRP wordt ook wel de term 'Natuurlijk persoon' gebruikt.
 * In de BRP worden zowel personen ingeschreven die onder een College van Burgemeester en Wethouders vallen
 * ('ingezetenen'), als personen waarvoor de Minister verantwoordelijkheid geldt.
 *
 * 1. Binnen BRP gebruiken we het begrip Persoon, waar elders gebruikelijk is voor dit objecttype de naam
 * "Natuurlijk persoon" te gebruiken. Binnen de context van BRP hebben we het bij het hanteren van de term Persoon
 * echter nooit over niet-natuurlijke personen zoals rechtspersonen. Het gebruik van de term Persoon is verder dermate
 * gebruikelijk binnen de context van BRP, dat ervoor gekozen is deze naam te blijven hanteren. We spreken dus over
 * Persoon en niet over "Natuurlijk persoon".
 * 2. Voor "alternatieve realiteit" personen, en voor niet-ingeschrevenen, gebruiken we in het logisch & operationeel
 * model (maar dus NIET in de gegevensset) het construct 'persoon'. Om die reden zijn veel groepen niet verplicht, die
 * wellicht wel verplicht zouden zijn in geval van (alleen) ingeschrevenen.
 * RvdP 27 juni 2011
 *
 *
 *
 *
 */
public class PersoonBericht extends AbstractPersoonBericht implements Persoon {

    @Valid
    @Override
    public PersoonIdentificatienummersGroepBericht getIdentificatienummers() {
        return super.getIdentificatienummers();
    }

    @Valid
    @Override
    public PersoonGeboorteGroepBericht getGeboorte() {
        return super.getGeboorte();
    }

    @Valid
    @Override
    public PersoonAfgeleidAdministratiefGroepBericht getAfgeleidAdministratief() {
        return super.getAfgeleidAdministratief();
    }

    @Valid
    @Override
    public List<PersoonAdresBericht> getAdressen() {
        return super.getAdressen();
    }

    @CollectieMetUniekeWaarden(code = MeldingCode.INC001)
    @Override
    public List<PersoonVoornaamBericht> getVoornamen() {
        return super.getVoornamen();
    }

    @CollectieMetUniekeWaarden(code = MeldingCode.INC002)
    @Override
    public List<PersoonGeslachtsnaamcomponentBericht> getGeslachtsnaamcomponenten() {
        return super.getGeslachtsnaamcomponenten();
    }

    @CollectieMetUniekeWaarden(code = MeldingCode.INC003)
    @Override
    public List<PersoonNationaliteitBericht> getNationaliteiten() {
        return super.getNationaliteiten();
    }

}
