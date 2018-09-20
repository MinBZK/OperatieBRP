/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.kern;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.inject.Named;
import nl.bzk.brp.beheer.webapp.configuratie.ControllerConstants;
import nl.bzk.brp.beheer.webapp.controllers.AbstractHistorieVerwerker;
import nl.bzk.brp.beheer.webapp.controllers.AbstractReadWriteController;
import nl.bzk.brp.beheer.webapp.controllers.ErrorHandler;
import nl.bzk.brp.beheer.webapp.controllers.HistorieVerwerker;
import nl.bzk.brp.beheer.webapp.controllers.VergelijkingUtil;
import nl.bzk.brp.beheer.webapp.controllers.query.AttribuutValueAdapter;
import nl.bzk.brp.beheer.webapp.controllers.query.EqualPredicateBuilderFactory;
import nl.bzk.brp.beheer.webapp.controllers.query.Filter;
import nl.bzk.brp.beheer.webapp.controllers.query.IntegerValueAdapter;
import nl.bzk.brp.beheer.webapp.controllers.query.JaNeeAttribuutValueAdapter;
import nl.bzk.brp.beheer.webapp.controllers.query.LikePredicateBuilderFactory;
import nl.bzk.brp.beheer.webapp.controllers.query.ShortValueAdapter;
import nl.bzk.brp.beheer.webapp.controllers.query.StringValueAdapter;
import nl.bzk.brp.beheer.webapp.repository.ReadWriteRepository;
import nl.bzk.brp.beheer.webapp.repository.stamgegevens.kern.SoortPartijRepository;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.JaNeeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.NaamEnumeratiewaardeAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PartijCodeAttribuut;
import nl.bzk.brp.model.beheer.kern.HisPartij;
import nl.bzk.brp.model.beheer.kern.Partij;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Partij controller.
 */
@RestController
@RequestMapping(value = ControllerConstants.PARTIJ_URI)
public class PartijController extends AbstractReadWriteController<Partij, Short> {

    private static final String NAAM = "naam";
    private static final String CODE = "code";
    private static final String DATUM_INGANG = "datumIngang";
    private static final String DATUM_EINDE = "datumEinde";

    private static final Filter<?> FILTER_CODE = new Filter<>("filterCode", new AttribuutValueAdapter<>(
            new IntegerValueAdapter(),
            PartijCodeAttribuut.class), new EqualPredicateBuilderFactory(CODE));
    private static final Filter<?> FILTER_NAAM = new Filter<>("filterNaam", new AttribuutValueAdapter<>(
            new StringValueAdapter(),
            NaamEnumeratiewaardeAttribuut.class), new LikePredicateBuilderFactory(NAAM));
    private static final Filter<?> FILTER_SOORT = new Filter<>("filterSoort", new ShortValueAdapter(), new EqualPredicateBuilderFactory(
            "soort.iD"));
    private static final Filter<?> FILTER_DATUM_INGANG = new Filter<>("filterDatumIngang", new AttribuutValueAdapter<>(
            new IntegerValueAdapter(),
            DatumEvtDeelsOnbekendAttribuut.class), new EqualPredicateBuilderFactory(DATUM_INGANG));
    private static final Filter<?> FILTER_DATUM_EINDE = new Filter<>("filterDatumEinde", new AttribuutValueAdapter<>(
            new IntegerValueAdapter(),
            DatumEvtDeelsOnbekendAttribuut.class), new EqualPredicateBuilderFactory(DATUM_EINDE));
    private static final Filter<?> FILTER_INDICATIE = new Filter<>("filterIndicatie", new JaNeeAttribuutValueAdapter(), new EqualPredicateBuilderFactory(
            "indicatieVerstrekkingsbeperkingMogelijk"));

    private static final List<String> SORTERINGEN = Arrays.asList(CODE, NAAM, DATUM_INGANG, DATUM_EINDE);

    private final SoortPartijRepository soortPartijRepository;

    /**
     * Constructor.
     *
     * @param repository repository
     * @param soortPartijRepository repository voor soort partij
     */
    @Autowired
    protected PartijController(@Named("partijRepository") final ReadWriteRepository<Partij, Short> repository,
            final SoortPartijRepository soortPartijRepository)
    {
        super(repository,
                Arrays.asList(FILTER_CODE, FILTER_NAAM, FILTER_SOORT, FILTER_DATUM_INGANG, FILTER_DATUM_EINDE, FILTER_INDICATIE),
                Arrays.<HistorieVerwerker<Partij, ?>>asList(new HisPartijVerwerker()),
                SORTERINGEN);
        this.soortPartijRepository = soortPartijRepository;
    }

    @Override
    protected final void wijzigObjectVoorOpslag(final Partij partij) throws ErrorHandler.NotFoundException {
        if (partij.getSoort() != null && partij.getSoort().getID() != null) {
            partij.setSoort(soortPartijRepository.getOne(partij.getSoort().getID()));
        }

    }

    /**
     * Partij historie verwerker.
     */
    public static final class HisPartijVerwerker extends AbstractHistorieVerwerker<Partij, HisPartij> {

        @Override
        public HisPartij maakHistorie(final Partij item) {
            // Controle inhoudelijk leeg
            boolean inhoudelijkLeeg = item.getNaam() == null;
            inhoudelijkLeeg &= item.getSoort() == null;
            inhoudelijkLeeg &= item.getOIN() == null;
            inhoudelijkLeeg &= item.getDatumIngang() == null;
            inhoudelijkLeeg &= item.getDatumEinde() == null;
            inhoudelijkLeeg &= item.getIndicatieVerstrekkingsbeperkingMogelijk() == null;

            if (inhoudelijkLeeg) {
                return null;
            }

            final HisPartij hisPartij = new HisPartij();

            // A-laag
            hisPartij.setPartij(item);
            // Gegevens
            hisPartij.setNaam(item.getNaam());
            hisPartij.setOIN(item.getOIN());
            hisPartij.setSoort(item.getSoort());
            hisPartij.setDatumIngang(item.getDatumIngang());
            hisPartij.setDatumEinde(item.getDatumEinde());
            if (item.getIndicatieVerstrekkingsbeperkingMogelijk() != null) {
                hisPartij.setIndicatieVerstrekkingsbeperkingMogelijk(item.getIndicatieVerstrekkingsbeperkingMogelijk());
            } else {
                hisPartij.setIndicatieVerstrekkingsbeperkingMogelijk(JaNeeAttribuut.NEE);
            }
            // Historie
            hisPartij.setDatumTijdRegistratie(new DatumTijdAttribuut(new Date()));

            return hisPartij;
        }

        @Override
        public boolean isHistorieInhoudelijkGelijk(final HisPartij nieuweHistorie, final HisPartij actueleRecord) {
            boolean resultaat = VergelijkingUtil.isEqual(nieuweHistorie.getNaam(), actueleRecord.getNaam());
            resultaat &= VergelijkingUtil.isEqual(nieuweHistorie.getOIN(), actueleRecord.getOIN());
            resultaat &= VergelijkingUtil.isEqual(nieuweHistorie.getSoort(), actueleRecord.getSoort());
            resultaat &= VergelijkingUtil.isEqual(nieuweHistorie.getDatumIngang(), actueleRecord.getDatumIngang());
            resultaat &= VergelijkingUtil.isEqual(nieuweHistorie.getDatumEinde(), actueleRecord.getDatumEinde());
            if (actueleRecord.getIndicatieVerstrekkingsbeperkingMogelijk().getWaarde() != null) {
                resultaat &= nieuweHistorie.getIndicatieVerstrekkingsbeperkingMogelijk().getWaarde().equals(
                     actueleRecord.getIndicatieVerstrekkingsbeperkingMogelijk().getWaarde());
            } else {
                resultaat &= !nieuweHistorie.getIndicatieVerstrekkingsbeperkingMogelijk().getWaarde();
            }
            return resultaat;
        }

        @Override
        public Set<HisPartij> geefHistorie(final Partij item) {
            return item.getHisPartijLijst();
        }

        @Override
        public void kopieerHistorie(final Partij item, final Partij managedItem) {
            managedItem.setNaam(item.getNaam());
            managedItem.setOIN(item.getOIN());
            managedItem.setSoort(item.getSoort());
            managedItem.setDatumOvergangNaarBRP(item.getDatumOvergangNaarBRP());
            managedItem.setDatumIngang(item.getDatumIngang());
            managedItem.setDatumEinde(item.getDatumEinde());
            managedItem.setIndicatieVerstrekkingsbeperkingMogelijk(item.getIndicatieVerstrekkingsbeperkingMogelijk());
            managedItem.setIndicatieAutomatischFiatteren(item.getIndicatieAutomatischFiatteren());
        }

    }
}
