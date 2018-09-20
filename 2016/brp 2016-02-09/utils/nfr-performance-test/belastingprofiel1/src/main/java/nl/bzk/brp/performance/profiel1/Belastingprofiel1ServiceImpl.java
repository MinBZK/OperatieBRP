/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.performance.profiel1;

import static nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling.GEBOORTE_IN_NEDERLAND;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import nl.bzk.brp.datataal.handlers.persoon.VerhuizingHandler;
import nl.bzk.brp.datataal.handlers.persoon.afstamming.GeboorteHandler;
import nl.bzk.brp.datataal.model.GebeurtenisAttributen;
import nl.bzk.brp.datataal.model.Persoon;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import org.springframework.stereotype.Component;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 */
@Component
public class Belastingprofiel1ServiceImpl implements Belastingprofiel1Service {

    private             Logger LOGGER     = LoggerFactory.getLogger();
    public static final int    BSN_MIN_ID = 1000;
    private static final Integer[] GEMEENTE_CODES;

    static {
        final URL url = Belastingprofiel1ServiceImpl.class.getResource("/gemeenten.txt");
        final List<Integer> codeLijst = new LinkedList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                codeLijst.add(Integer.parseInt(line.split("\\|")[2]));
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        Collections.shuffle(codeLijst);
        GEMEENTE_CODES = codeLijst.toArray(new Integer[codeLijst.size()]);
    }

    private static final ThreadLocal<List<PersoonHisVolledigImpl>> OUDERS = new ThreadLocal<List<PersoonHisVolledigImpl>>() {
        @Override
        protected List<PersoonHisVolledigImpl> initialValue() {
            return new LinkedList<>();
        }
    };


    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, transactionManager = "lezenSchrijvenTransactionManager")
    public final PersoonHisVolledigImpl genereerPersoon(final int i) {
        final PersoonHisVolledigImpl verhuis = verhuis(verhuis(geboorte(i)));
        if (i % 100 == 0) {
            LOGGER.info("Aantal personen: " + i);
        }
        return verhuis;
    }


    private PersoonHisVolledigImpl verhuis(final PersoonHisVolledigImpl opgeslagenPersoon) {
        final PersoonHisVolledigImplBuilder persBuilder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE);
        ReflectionTestUtils.setField(persBuilder, "hisVolledigImpl", opgeslagenPersoon);

        //VERHUIZEN
        final GebeurtenisAttributen verhuizingAttrs = new GebeurtenisAttributen();
        final VerhuizingHandler verhuizingHandler = new VerhuizingHandler(verhuizingAttrs, persBuilder);
        verhuizingHandler.startGebeurtenis();


        int huidigeGemeente = -1;
        if (!opgeslagenPersoon.getAdressen().isEmpty()) {
            huidigeGemeente = opgeslagenPersoon.getAdressen().iterator().next().getPersoonAdresHistorie().getActueleRecord().getGemeente()
                .getWaarde().getCode().getWaarde();
        }

        //garandeer dat gemeente verschilt
        int nieuweGemeente;
        while ((nieuweGemeente = GEMEENTE_CODES[new Random().nextInt(GEMEENTE_CODES.length)]) == huidigeGemeente) {
        }

        //LOGGER.info(String.format("verhuis persoon %d van %d naar %d", opgeslagenPersoon.getID(), huidigeGemeente, nieuweGemeente));

        final Map<String, Object> naarGemeenteProps = new HashMap<>();
        verhuizingHandler.naarGemeente(naarGemeenteProps, nieuweGemeente);
        verhuizingHandler.eindeGebeurtenis();
        final PersoonHisVolledigImpl persoon = persBuilder.build();
        return Persoon.slaOp(persoon);
    }

    private PersoonHisVolledigImpl geboorte(final int i) {
        final PersoonHisVolledigImplBuilder persBuilder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE);

        //GEBOORTE
        final GebeurtenisAttributen geboorteAttrs = new GebeurtenisAttributen();
        geboorteAttrs.setSoortHandeling(GEBOORTE_IN_NEDERLAND);
        final GeboorteHandler geboorteHandler = new GeboorteHandler(geboorteAttrs, persBuilder);
        geboorteHandler.startGebeurtenis();
        geboorteHandler.meisje();
        geboorteHandler.voornamen("testpersoon" + i);
        geboorteHandler.geslachtsnaam("geslnaam" + i);

        final List<PersoonHisVolledigImpl> mijnOuders = OUDERS.get();
        if (mijnOuders.size() == 2) {
            geboorteHandler.ouders(mijnOuders.get(0), mijnOuders.get(1));
            mijnOuders.clear();
        }

        final Map<String, Number> identificatieNummersMap = new HashMap<>();
        identificatieNummersMap.put("bsn", BSN_MIN_ID + i);
        identificatieNummersMap.put("anummer", BSN_MIN_ID + i);
        geboorteHandler.identificatienummers(identificatieNummersMap);

//            Map<String,String> opMap  = new HashMap<>();
//            opMap.put("op", "1980/01/01");
//            opMap.put("te", "Delft");
//            gh.op(opMap);
        geboorteHandler.eindeGebeurtenis();
        final PersoonHisVolledigImpl persoonHisVolledig = persBuilder.build();
        mijnOuders.add(persoonHisVolledig);
        return Persoon.slaOp(persoonHisVolledig);
    }
}
