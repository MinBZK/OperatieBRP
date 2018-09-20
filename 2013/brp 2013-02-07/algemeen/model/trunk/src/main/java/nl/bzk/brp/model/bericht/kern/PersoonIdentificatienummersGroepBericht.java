/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bericht.kern;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.ANummer;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.Burgerservicenummer;
import nl.bzk.brp.model.bericht.kern.basis.AbstractPersoonIdentificatienummersGroepBericht;
import nl.bzk.brp.model.logisch.kern.PersoonIdentificatienummersGroep;
import nl.bzk.brp.model.validatie.constraint.Bsn;


/**
 * Groep identificerende nummers
 *
 * De groep ""identificatienummers"" bevat het burgerservicenummer, en het Administratienummer, waarmee de persoon uniek
 * wordt aangeduid.
 *
 * Verplicht aanwezig bij persoon
 *
 * 1. Omdat in uitzonderingssituaties het nodig kan zijn om het burgerservicenummer en/of Administratienummer van een
 * persoon te wijzigen, is ervoor gekozen om burgerservicenummer en/of Administratienummer NIET als technische sleutel
 * te hanteren binnen de BRP.
 *
 * 2. Keuze van historie:
 * LO 3.x onderkend beide vormen van historie voor BSN en A nummer: ��k materi�le.
 * Er is een discussie geweest wat precies die materi�le historie zou zijn: is er niet simpelweg sprake van alleen een
 * formele tijdslijn voor het BSN en Anummer. Conclusie was: neen, er is ook een materi�le tijdslijn. En ja, die komt
 * meestal overeen met de formele tijdslijn.
 * NB: geldigheid gegeven wordt wel (mogelijk) geleverd aan afnemer.
 * RvdP vastgelegd 6-1-2012.
 *
 *
 *
 */
public class PersoonIdentificatienummersGroepBericht extends AbstractPersoonIdentificatienummersGroepBericht implements
        PersoonIdentificatienummersGroep
{

    @Bsn
    @Override
    public Burgerservicenummer getBurgerservicenummer() {
        return super.getBurgerservicenummer();
    }

    @nl.bzk.brp.model.validatie.constraint.ANummer
    @Override
    public ANummer getAdministratienummer() {
        return super.getAdministratienummer();
    }
}
