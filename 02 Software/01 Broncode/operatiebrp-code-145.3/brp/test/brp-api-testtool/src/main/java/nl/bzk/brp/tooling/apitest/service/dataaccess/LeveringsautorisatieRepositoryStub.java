/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.dataaccess;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelGroep;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelGroepAttribuut;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelLo3Rubriek;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.brp.service.dalapi.LeveringsautorisatieRepository;
import nl.bzk.brp.test.common.DienstSleutel;
import nl.bzk.brp.tooling.apitest.autorisatie.AutorisatieData;
import org.springframework.util.Assert;

/**
 * Stub voor LeveringsautorisatieRepository.
 */
final class LeveringsautorisatieRepositoryStub implements LeveringsautorisatieRepository, AutorisatieStubService {

    private AutorisatieData autorisatieData;

    /**
     * Constructor.
     */
    public LeveringsautorisatieRepositoryStub() {
        reset();
    }

    /**
     * Reset de autorisatie data.
     */
    public void reset() {
        autorisatieData = new AutorisatieData();
    }

    /**
     * Geeft het id van de leveringsautorisatie met de gegeven naam.
     * @param leveringsautorisatieNaam naam van de leveringsautorisatie.
     * @return id van de leveringsautorisatie.
     */
    public Leveringsautorisatie getLeveringsautorisatie(final String leveringsautorisatieNaam) {
        Assert.notNull(leveringsautorisatieNaam);
        for (final Leveringsautorisatie leveringsautorisatie : autorisatieData.getLeveringsautorisatieEntities()) {
            if (leveringsautorisatieNaam.equals(leveringsautorisatie.getNaam())) {
                return leveringsautorisatie;
            }
        }
        throw new IllegalArgumentException("Leveringsautorisatie niet gevonden met naam: " + leveringsautorisatieNaam);
    }

    @Override
    public Dienst getDienstUitLeveringsautorisatie(final Leveringsautorisatie leveringsAutorisatie, final int dienstId) {
        final Set<Dienstbundel> dienstbundelSet = leveringsAutorisatie.getDienstbundelSet();
        for (Dienstbundel db : dienstbundelSet) {
            final Set<Dienst> dienstSet = db.getDienstSet();
            for (Dienst dienst : dienstSet) {
                if (dienst.getId().equals(dienstId)) {
                    return dienst;
                }
            }
        }
        return null;
    }

    @Override
    public Dienst getDienst(final DienstSleutel dienstSleutel) {
        final ToegangLeveringsAutorisatie toegangleveringsautorisatie = getToegangleveringsautorisatie(dienstSleutel);
        return autorisatieData.getDienstRefs().stream()
                .filter(dienstRef -> dienstRef.getRef().equals(dienstSleutel.getDienstRef()))
                .filter(dienstRef -> dienstRef.getValue().getDienstbundel().getLeveringsautorisatie() == toegangleveringsautorisatie.getLeveringsautorisatie())
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Dienst niet gevonden voor dienstSleutel: " + dienstSleutel))
                .getValue();
    }

    @Override
    public ToegangLeveringsAutorisatie getToegangleveringsautorisatie(final DienstSleutel dienstSleutel) {
        return autorisatieData.getToegangRefs().stream()
                .filter(toegangLeveringsAutorisatieRef -> toegangLeveringsAutorisatieRef.getRef().equals(dienstSleutel.getToegangRef()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Toegangleveringsautorisatie niet gevonden voor dienstSleutel: " + dienstSleutel))
                .getValue();
    }

    @Override
    public void setData(final AutorisatieData data) {
        this.autorisatieData = data;
    }

    @Override
    public List<ToegangLeveringsAutorisatie> haalAlleToegangLeveringsautorisatiesOpZonderAssociaties() {
        return autorisatieData.getToegangLeveringsautorisatieEntities();
    }

    @Override
    public List<Leveringsautorisatie> haalAlleLeveringsautorisatiesOpZonderAssocaties() {
        return autorisatieData.getLeveringsautorisatieEntities();
    }

    @Override
    public List<Dienstbundel> haalAlleDienstbundelsOpZonderAssocaties() {
        return autorisatieData.getDienstbundelEntities();
        //11.1_R2258_Nadere_popbep_volledig_geconverteerd
        //krijg nullpointer...
//        final List<Dienstbundel> list = Lists.newArrayList(autorisatieData.getDienstbundelEntities());
//        return list.stream().filter(dienstbundel -> dienstbundel.getIndicatieNaderePopulatiebeperkingVolledigGeconverteerd() == null).collect(
//            Collectors.toList());
    }

    @Override
    public List<Dienst> haalAlleDienstenOpZonderAssocaties() {
        return autorisatieData.getDienstEntities();
    }

    @Override
    public List<DienstbundelGroep> haalAlleDienstbundelGroepenOpZonderAssocaties() {
        return autorisatieData.getDienstbundelGroepEntities();
    }

    @Override
    public List<DienstbundelLo3Rubriek> haalAlleDienstbundelLo3Rubrieken() {
        return Collections.emptyList();
    }

    @Override
    public List<DienstbundelGroepAttribuut> haalAlleDienstbundelGroepAttributenOpZonderAssocaties() {
        return autorisatieData.getDienstbundelGroepAttrEntities();
    }
}
