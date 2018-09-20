/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.regels.context;

import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;


/**
 * De interface voor de context voor bedrijfsregels die betrekking hebben op afnemerindicaties.
 */
public final class AfnemerindicatieRegelContext extends AbstractRegelContext implements RegelContext {

    private final Partij                         afnemer;
    private final Leveringsautorisatie           leveringsautorisatie;
    private final PersoonView                    huidigeSituatie;
    private final SoortAdministratieveHandeling  soortAdministratieveHandeling;
    private final DatumEvtDeelsOnbekendAttribuut datumAanvangMaterielePeriode;
    private final DatumAttribuut                 datumEindeVolgen;

    /**
     * Instantieert een nieuwe Afnemerindicatie regel context.
     *
     * @param afnemer                       de afnemer
     * @param huidigeSituatie               de huidige situatie
     * @param soortAdministratieveHandeling de soort administratieve handeling
     * @param leveringsautorisatie          de leveringsautorisatie
     * @param datumAanvangMaterielePeriode  de datum aanvang materiele periode
     * @param datumEindeVolgen              de datum einde volgen
     */
    public AfnemerindicatieRegelContext(
        final Partij afnemer,
        final PersoonView huidigeSituatie,
        final SoortAdministratieveHandeling soortAdministratieveHandeling,
        final Leveringsautorisatie leveringsautorisatie,
        final DatumEvtDeelsOnbekendAttribuut datumAanvangMaterielePeriode,
        final DatumAttribuut datumEindeVolgen)
    {
        this.afnemer = afnemer;
        this.huidigeSituatie = huidigeSituatie;
        this.soortAdministratieveHandeling = soortAdministratieveHandeling;
        this.leveringsautorisatie = leveringsautorisatie;
        this.datumAanvangMaterielePeriode = datumAanvangMaterielePeriode;
        this.datumEindeVolgen = datumEindeVolgen;
    }

    public Partij getAfnemer() {
        return afnemer;
    }

    public Leveringsautorisatie getLeveringsautorisatie() {
        return leveringsautorisatie;
    }

    public SoortAdministratieveHandeling getSoortAdministratieveHandeling() {
        return soortAdministratieveHandeling;
    }

    public PersoonView getHuidigeSituatie() {
        return huidigeSituatie;
    }

    public DatumEvtDeelsOnbekendAttribuut getDatumAanvangMaterielePeriode() {
        return datumAanvangMaterielePeriode;
    }

    public DatumAttribuut getDatumEindeVolgen() {
        return datumEindeVolgen;
    }

    @Override
    public BrpObject getSituatie() {
        return huidigeSituatie;
    }
}
