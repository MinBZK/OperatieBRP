/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.bericht;

import java.util.List;
import nl.bzk.brp.levering.lo3.conversie.ConversieCache;
import nl.bzk.brp.levering.lo3.conversie.Converteerder;
import nl.bzk.brp.levering.lo3.filter.Filter;
import nl.bzk.brp.levering.lo3.format.Formatter;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortSynchronisatie;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.levering.AbstractSynchronisatieBericht;
import nl.bzk.brp.model.levering.AdministratieveHandelingSynchronisatie;
import nl.bzk.brp.model.logisch.ist.Stapel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import org.jibx.runtime.IMarshallingContext;

/**
 * Leveringsbericht implementatie.
 */
public final class BerichtImpl extends AbstractSynchronisatieBericht implements Bericht {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    // Config
    private final SoortBericht soortBericht;
    private final Converteerder converteerder;
    private final Filter filter;
    private final Formatter formatter;

    // Input
    private final PersoonHisVolledig persoon;
    private final List<Stapel> istStapels;
    private final AdministratieveHandelingModel administratieveHandeling;

    // Variabelen
    private List<Lo3CategorieWaarde> categorieen;
    private List<Lo3CategorieWaarde> categorieenGefilterd;

    /**
     * Constructor.
     *
     * @param soortBericht soort bericht
     * @param converteerder conversie
     * @param filter filter
     * @param formatter formatter
     * @param persoon persoon
     * @param istStapels ist stapels
     * @param administratieveHandeling administratieve handeling
     */
    protected BerichtImpl(
        final SoortBericht soortBericht,
        final Converteerder converteerder,
        final Filter filter,
        final Formatter formatter,
        final PersoonHisVolledig persoon,
        final List<Stapel> istStapels,
        final AdministratieveHandelingModel administratieveHandeling)
    {
        super(new AdministratieveHandelingSynchronisatie(administratieveHandeling));

        LOGGER.debug("Nieuw LO3 leveringsbericht: {} voor persoon {}", soortBericht, persoon.getID());

        this.soortBericht = soortBericht;
        this.converteerder = converteerder;
        this.filter = filter;
        this.formatter = formatter;

        this.persoon = persoon;
        this.istStapels = istStapels;
        this.administratieveHandeling = administratieveHandeling;

        addPersoon(new PersoonHisVolledigView(persoon, null));
    }

    /**
     * Soort bericht.
     *
     * @return soort bericht
     */
    public String geefSoortBericht() {
        return soortBericht.getSoortBericht();
    }

    @Override
    public SoortSynchronisatie geefSoortSynchronisatie() {
        return soortBericht.getSoortSynchronisatie();
    }

    @Override
    public void converteerNaarLo3(final ConversieCache conversieCache) {
        LOGGER.debug("Converteren voor {} voor persoon {}", soortBericht, persoon.getID());
        categorieen = converteerder.converteer(persoon, istStapels, administratieveHandeling, conversieCache);
    }

    @Override
    public boolean filterRubrieken(final List<String> rubrieken) {
        LOGGER.debug("Filteren voor {} voor persoon {}", soortBericht, persoon.getID());
        categorieenGefilterd = filter.filter(persoon, istStapels, administratieveHandeling, categorieen, rubrieken);
        final boolean resultaat = filter.leveringUitvoeren(persoon, categorieenGefilterd);
        LOGGER.debug("Filter resultaat voor {} voor persoon {} is {}", soortBericht, persoon.getID(), resultaat);
        return resultaat;
    }

    @Override
    public String maakUitgaandBericht() {
        LOGGER.debug("Maken bericht voor {} voor persoon {}", soortBericht, persoon.getID());
        return formatter.maakPlatteTekst(persoon, categorieen, categorieenGefilterd);
    }

    /**
     * (JBIX) Get name.
     *
     * @return name
     */
    public String JiBX_getName() {
        throw new UnsupportedOperationException();
    }

    /**
     * (JBIX) Marshal.
     *
     * @param arg0 context
     */
    public void marshal(final IMarshallingContext arg0) {
        throw new UnsupportedOperationException();
    }
}
