/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.kern;

import java.util.Arrays;
import java.util.Set;
import javax.inject.Named;
import nl.bzk.brp.beheer.webapp.configuratie.ControllerConstants;
import nl.bzk.brp.beheer.webapp.controllers.AbstractHistorieVerwerker;
import nl.bzk.brp.beheer.webapp.controllers.AbstractReadWriteController;
import nl.bzk.brp.beheer.webapp.controllers.HistorieVerwerker;
import nl.bzk.brp.beheer.webapp.controllers.VergelijkingUtil;
import nl.bzk.brp.beheer.webapp.controllers.query.EqualPredicateBuilderFactory;
import nl.bzk.brp.beheer.webapp.controllers.query.Filter;
import nl.bzk.brp.beheer.webapp.controllers.query.ShortValueAdapter;
import nl.bzk.brp.beheer.webapp.repository.stamgegevens.kern.PartijRolRepository;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.beheer.kern.HisPartijRol;
import nl.bzk.brp.model.beheer.kern.PartijRol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller voor PartijRol.
 */
@RestController
@RequestMapping(value = ControllerConstants.PARTIJROL_URI)
public class PartijRolController extends AbstractReadWriteController<PartijRol, Integer> {

    private static final String PARAMETER_PARTIJ = "partij";
    private static final String PARAMETER_PARTIJ_FIELD = "partij.iD";
    private static final Filter<?> FILTER_PARTIJ = new Filter<>(PARAMETER_PARTIJ, new ShortValueAdapter(), new EqualPredicateBuilderFactory(
            PARAMETER_PARTIJ_FIELD));

    /**
     * Constructor.
     *
     * @param repository Partijrol repository
     */
    @Autowired
    public PartijRolController(@Named("partijRolRepository") final PartijRolRepository repository) {
        super(repository,
                Arrays.<Filter<?>>asList(FILTER_PARTIJ),
                Arrays.<HistorieVerwerker<PartijRol, ?>>asList(new HisPartijRolVerwerker()));
    }

    /**
     * Partij historie verwerker.
     */
    public static final class HisPartijRolVerwerker extends AbstractHistorieVerwerker<PartijRol, HisPartijRol> {

        @Override
        public Set<HisPartijRol> geefHistorie(final PartijRol item) {
            return item.getHisPartijRolLijst();
        }

        @Override
        public boolean isHistorieInhoudelijkGelijk(final HisPartijRol nieuweHistorie, final HisPartijRol actueleRecord) {
            return VergelijkingUtil.isEqual(nieuweHistorie.getDatumIngang(), actueleRecord.getDatumIngang())
                    && VergelijkingUtil.isEqual(nieuweHistorie.getDatumEinde(), actueleRecord.getDatumEinde());
        }

        @Override
        public HisPartijRol maakHistorie(final PartijRol item) {
            if (item.getPartij() == null
                    && item.getRol() == null
                    && item.getDatumIngang() == null
                    && item.getDatumEinde() == null)
            {
                return null;
            }
            final HisPartijRol hisPartijRol = new HisPartijRol();
            // A laag
            hisPartijRol.setPartijRol(item);
            // Gegegevens
            hisPartijRol.setDatumTijdRegistratie(DatumTijdAttribuut.nu());
            hisPartijRol.setDatumIngang(item.getDatumIngang());
            hisPartijRol.setDatumEinde(item.getDatumEinde());
            return hisPartijRol;
        }

        @Override
        public void kopieerHistorie(final PartijRol item, final PartijRol managedItem) {
            managedItem.setDatumIngang(item.getDatumIngang());
            managedItem.setDatumEinde(item.getDatumEinde());
        }
    }
}
