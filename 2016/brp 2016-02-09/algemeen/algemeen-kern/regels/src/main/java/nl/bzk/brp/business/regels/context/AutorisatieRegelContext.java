/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.regels.context;

import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangLeveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.basis.BrpObject;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;


/**
 * De interface voor de context voor bedrijfsregels die betrekking hebben op autorisatie.
 */
public final class AutorisatieRegelContext extends AbstractRegelContext implements RegelContext {
    private static final String PREFIX_LOGMELDING_FOUT   = "Authenticatie/Autorisatie mislukt - ";
    private static final String PREFIX_LOGMELDING_SUCCES = "Authenticatie/Autorisatie geslaagd - ";

    private final ToegangLeveringsautorisatie   toegangLeveringsautorisatie;
    private       PersoonView                   huidigeSituatie;
    private       SoortAdministratieveHandeling soortAdministratieveHandeling;
    private       Dienst                        dienst;

    /**
     * Instantieert een nieuwe autorisatie regel context.
     *
     * @param toegangLeveringsautorisatie   de toegangLeveringsautorisatie
     * @param dienst                        de diensten.
     * @param huidigeSituatie               de huidige situatie als PersoonView.
     * @param soortAdministratieveHandeling de soort administratieve handeling.
     */
    public AutorisatieRegelContext(final ToegangLeveringsautorisatie toegangLeveringsautorisatie, final Dienst dienst, final PersoonView huidigeSituatie,
        final SoortAdministratieveHandeling soortAdministratieveHandeling)
    {
        this.toegangLeveringsautorisatie = toegangLeveringsautorisatie;
        this.dienst = dienst;
        this.huidigeSituatie = huidigeSituatie;
        this.soortAdministratieveHandeling = soortAdministratieveHandeling;
    }

    /**
     * Instantieert een nieuwe autorisatie regel context.
     *
     * @param toegangLeveringsautorisatie de toegangLeveringsautorisatie.
     * @param dienst                      de diensten.
     * @param huidigeSituatie             de huidige situatie als PersoonView.
     */
    public AutorisatieRegelContext(final ToegangLeveringsautorisatie toegangLeveringsautorisatie, final Dienst dienst, final PersoonView huidigeSituatie) {
        this.toegangLeveringsautorisatie = toegangLeveringsautorisatie;
        this.dienst = dienst;
        this.huidigeSituatie = huidigeSituatie;
    }

    /**
     * Instantieert een nieuwe autorisatie regel context.
     *
     * @param toegangLeveringsautorisatie de toegangLeveringsautorisatie.
     * @param dienst                      de dienst.
     */
    public AutorisatieRegelContext(final ToegangLeveringsautorisatie toegangLeveringsautorisatie, final Dienst dienst) {
        this.toegangLeveringsautorisatie = toegangLeveringsautorisatie;
        this.dienst = dienst;
    }

    public ToegangLeveringsautorisatie getToegangLeveringsautorisatie() {
        return toegangLeveringsautorisatie;
    }

    public Dienst getDienst() {
        return dienst;
    }

    public PersoonView getHuidigeSituatie() {
        return huidigeSituatie;
    }

    public SoortAdministratieveHandeling getSoortAdministratieveHandeling() {
        return soortAdministratieveHandeling;
    }

    @Override
    public BrpObject getSituatie() {
        return huidigeSituatie;
    }

    @Override
    protected String getPrefixLogmeldingFout() {
        return PREFIX_LOGMELDING_FOUT;
    }

    @Override
    protected String getPrefixLogmeldingSucces() {
        return PREFIX_LOGMELDING_SUCCES;
    }
}
