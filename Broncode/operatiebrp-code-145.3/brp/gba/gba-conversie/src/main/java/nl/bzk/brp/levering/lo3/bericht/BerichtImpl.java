/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.bericht;

import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Stapel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSynchronisatie;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.levering.lo3.conversie.ConversieCache;
import nl.bzk.brp.levering.lo3.conversie.Converteerder;
import nl.bzk.brp.levering.lo3.conversie.IdentificatienummerMutatie;
import nl.bzk.brp.levering.lo3.filter.Filter;
import nl.bzk.brp.levering.lo3.format.Formatter;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;

/**
 * Leveringsbericht implementatie.
 */
public final class BerichtImpl implements Bericht {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    // Config
    private final SoortBericht soortBericht;
    private final Converteerder converteerder;
    private final Filter filter;
    private final Formatter formatter;

    // Input
    private final Persoonslijst persoon;
    private final List<Stapel> istStapels;
    private final AdministratieveHandeling administratieveHandeling;
    private final IdentificatienummerMutatie identificatienummerMutatie;

    // Variabelen
    private List<Lo3CategorieWaarde> categorieen;
    private List<Lo3CategorieWaarde> categorieenGefilterd;

    /**
     * Constructor.
     * @param soortBericht soort bericht
     * @param converteerder conversie
     * @param filter filter
     * @param formatter formatter
     * @param persoon persoon
     * @param istStapels ist stapels
     * @param administratieveHandeling administratieve handeling
     * @param identificatienummerMutatie identificatienummer mutatie resultaat
     */
    protected BerichtImpl(
            final SoortBericht soortBericht,
            final Converteerder converteerder,
            final Filter filter,
            final Formatter formatter,
            final Persoonslijst persoon,
            final List<Stapel> istStapels,
            final AdministratieveHandeling administratieveHandeling,
            final IdentificatienummerMutatie identificatienummerMutatie) {
        LOGGER.debug("Nieuw LO3 leveringsbericht: {} voor persoon {}", soortBericht, persoon != null ? persoon.getMetaObject().getObjectsleutel() : null);

        this.soortBericht = soortBericht;
        this.converteerder = converteerder;
        this.filter = filter;
        this.formatter = formatter;

        this.persoon = persoon;
        this.istStapels = istStapels;
        this.administratieveHandeling = administratieveHandeling;
        this.identificatienummerMutatie = identificatienummerMutatie;
    }

    /**
     * Soort bericht.
     * @return soort bericht
     */
    public String geefSoortBericht() {
        return soortBericht.getBerichtNummer();
    }

    @Override
    public Persoonslijst getPersoonsgegevens() {
        return persoon;
    }

    @Override
    public void converteerNaarLo3(final ConversieCache conversieCache) {
        LOGGER.debug("Converteren voor {} voor persoon {}", soortBericht, persoon.getMetaObject().getObjectsleutel());
        categorieen = converteerder.converteer(persoon, istStapels, administratieveHandeling, identificatienummerMutatie, conversieCache);
    }

    @Override
    public boolean filterRubrieken(final List<String> rubrieken) {
        LOGGER.debug("Filteren voor {} voor persoon {}", soortBericht, persoon.getMetaObject().getObjectsleutel());
        categorieenGefilterd = filter.filter(persoon, istStapels, administratieveHandeling, identificatienummerMutatie, categorieen, rubrieken);
        final boolean resultaat = filter.leveringUitvoeren(persoon, categorieenGefilterd);
        LOGGER.debug("Filter resultaat voor {} voor persoon {} is {}", soortBericht, persoon.getMetaObject().getObjectsleutel(), resultaat);
        return resultaat;
    }

    @Override
    public String maakUitgaandBericht() {
        LOGGER.debug("Maken bericht voor {} voor persoon {}", soortBericht, persoon != null ? persoon.getMetaObject().getObjectsleutel() : null);
        return formatter.maakPlatteTekst(persoon, identificatienummerMutatie, categorieen, categorieenGefilterd);
    }

    @Override
    public SoortSynchronisatie getSoortSynchronisatie() {
        return soortBericht.getSoortSynchronisatie();
    }
}
