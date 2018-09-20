/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers.stamgegevens.autaut;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import nl.bzk.brp.beheer.webapp.configuratie.ControllerConstants;
import nl.bzk.brp.beheer.webapp.controllers.AbstractHistorieVerwerker;
import nl.bzk.brp.beheer.webapp.controllers.AbstractReadWriteController;
import nl.bzk.brp.beheer.webapp.controllers.ErrorHandler.NotFoundException;
import nl.bzk.brp.beheer.webapp.controllers.HistorieVerwerker;
import nl.bzk.brp.beheer.webapp.controllers.VergelijkingUtil;
import nl.bzk.brp.beheer.webapp.controllers.query.EqualPredicateBuilderFactory;
import nl.bzk.brp.beheer.webapp.controllers.query.Filter;
import nl.bzk.brp.beheer.webapp.controllers.query.IntegerValueAdapter;
import nl.bzk.brp.beheer.webapp.repository.ReadWriteRepository;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.beheer.autaut.Dienst;
import nl.bzk.brp.model.beheer.autaut.HisDienst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Dienst controller voor beheer diensten.
 */
@RestController
@RequestMapping(value = ControllerConstants.DIENST_URI)
public final class DienstController extends AbstractReadWriteController<Dienst, Integer> {

    /**
     * Parameter om te filteren op: abonnement.
     */
    public static final String PARAMETER_FILTER_ABONNEMENT = "abonnement";

    private static final String ABONNEMENT = PARAMETER_FILTER_ABONNEMENT;

    private static final List<String> SORTERINGEN = Arrays.asList(PARAMETER_FILTER_ABONNEMENT);

    //private final ReadonlyRepository<Leveringsautorisatie, Integer> abonnementRepository;

    /**
     * Constructor voor Dienstcontroller.
     *
     * @param repository           dienst repository
     */
    @Autowired
    public DienstController(
            final ReadWriteRepository<Dienst, Integer> repository)
    {
        super(repository,
                Arrays.<Filter<?>>asList(new Filter<>(ABONNEMENT, new IntegerValueAdapter(), new EqualPredicateBuilderFactory(ABONNEMENT))),
                Arrays.<HistorieVerwerker<Dienst, ?>>asList(new HisDienstVerwerker()),
                SORTERINGEN);
        //this.abonnementRepository = abonnementRepository;
    }

    @Override
    protected void wijzigObjectVoorOpslag(final Dienst item) throws NotFoundException {
        // Abonnement niet updaten via de dienst (laden via id)
        // if (item.getAbonnement() != null) {
        // item.setAbonnement(abonnementRepository.getOne(item.getAbonnement().getID()));
        // }
    }

    /**
     * Dienst historie verwerker.
     */
    public static final class HisDienstVerwerker extends AbstractHistorieVerwerker<Dienst, HisDienst> {

        @Override
        public HisDienst maakHistorie(final Dienst item) {
            // Controle inhoudelijk leeg
            if (VergelijkingUtil.and(
                    // item.getNaderePopulatiebeperking() == null,
                    item.getAttenderingscriterium() == null,
                    item.getDatumIngang() == null,
                    item.getDatumEinde() == null,
                    // item.getToestand() == null,
                    item.getEersteSelectiedatum() == null,
                    item.getSelectieperiodeInMaanden() == null))
            // item.getToelichting() == null))

            {
                return null;
            }

            final HisDienst hisDienst = new HisDienst();

            // A-laag
            hisDienst.setDienst(item);

            // Gegevens
            // hisDienst.setNaderePopulatiebeperking(item.getNaderePopulatiebeperking());
            // hisDienst.setAttenderingscriterium(item.getAttenderingscriterium());
            // hisDienst.setDatumIngang(item.getDatumIngang());
            // hisDienst.setDatumEinde(item.getDatumEinde());
            // hisDienst.setToestand(item.getToestand());
            // hisDienst.setEersteSelectiedatum(item.getEersteSelectiedatum());
            // hisDienst.setSelectieperiodeInMaanden(item.getSelectieperiodeInMaanden());
            // hisDienst.setIndicatieNaderePopulatiebeperkingVolledigGeconverteerd(item.getIndicatieNaderePopulatiebeperkingVolledigGeconverteerd());
            // hisDienst.setToelichting(item.getToelichting());

            // Historie
            hisDienst.setDatumTijdRegistratie(new DatumTijdAttribuut(new Date()));

            return hisDienst;
        }

        @Override
        public boolean isHistorieInhoudelijkGelijk(final HisDienst nieuweHistorie, final HisDienst actueleRecord) {
            return false;
            // return VergelijkingUtil.and(
            // VergelijkingUtil.isEqual(nieuweHistorie.getNaderePopulatiebeperking(),
            // actueleRecord.getNaderePopulatiebeperking()),
            // VergelijkingUtil.isEqual(nieuweHistorie.getAttenderingscriterium(),
            // actueleRecord.getAttenderingscriterium()),
            // VergelijkingUtil.isEqual(nieuweHistorie.getDatumIngang(), actueleRecord.getDatumIngang()),
            // VergelijkingUtil.isEqual(nieuweHistorie.getDatumEinde(), actueleRecord.getDatumEinde()),
            // VergelijkingUtil.isEqual(nieuweHistorie.getToestand(), actueleRecord.getToestand()),
            // VergelijkingUtil.isEqual(nieuweHistorie.getEersteSelectiedatum(),
            // actueleRecord.getEersteSelectiedatum()),
            // VergelijkingUtil.isEqual(nieuweHistorie.getSelectieperiodeInMaanden(),
            // actueleRecord.getSelectieperiodeInMaanden()),
            // VergelijkingUtil.isEqual(
            // nieuweHistorie.getIndicatieNaderePopulatiebeperkingVolledigGeconverteerd(),
            // actueleRecord.getIndicatieNaderePopulatiebeperkingVolledigGeconverteerd()),
            // VergelijkingUtil.isEqual(nieuweHistorie.getToelichting(), actueleRecord.getToelichting()));
        }

        @Override
        public Set<HisDienst> geefHistorie(final Dienst item) {
            return item.getHisDienstLijst();
        }

        @Override
        public void kopieerHistorie(final Dienst item, final Dienst managedItem) {
            // managedItem.setNaderePopulatiebeperking(item.getNaderePopulatiebeperking());
            // managedItem.setAttenderingscriterium(item.getAttenderingscriterium());
            // managedItem.setDatumIngang(item.getDatumIngang());
            // managedItem.setDatumEinde(item.getDatumEinde());
            // managedItem.setToestand(item.getToestand());
            // managedItem.setEersteSelectiedatum(item.getEersteSelectiedatum());
            // managedItem.setSelectieperiodeInMaanden(item.getSelectieperiodeInMaanden());
            // managedItem.setIndicatieNaderePopulatiebeperkingVolledigGeconverteerd(item.getIndicatieNaderePopulatiebeperkingVolledigGeconverteerd());
            // managedItem.setToelichting(item.getToelichting());

        }
    }
}
