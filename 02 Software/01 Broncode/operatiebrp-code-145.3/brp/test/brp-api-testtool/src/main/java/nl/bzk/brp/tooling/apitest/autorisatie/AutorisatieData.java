/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.autorisatie;

import com.google.common.collect.Lists;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelGroep;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.DienstbundelGroepAttribuut;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.brp.test.common.dsl.Ref;

/**
 */
public final class AutorisatieData {

    private final List<ToegangLeveringsAutorisatie> toegangLeveringsautorisatieEntities = Lists.newLinkedList();
    private final List<Leveringsautorisatie>        leveringsautorisatieEntities        = Lists.newLinkedList();
    private final List<Dienstbundel>                dienstbundelEntities                = Lists.newLinkedList();
    private final List<Dienst>                      dienstEntities                      = Lists.newLinkedList();
    private final List<DienstbundelGroep>           dienstbundelGroepEntities           = Lists.newLinkedList();
    private final List<DienstbundelGroepAttribuut>  dienstbundelGroepAttrEntities       = Lists.newLinkedList();

    private final List<Ref<ToegangLeveringsAutorisatie>> toegangRefs = Lists.newLinkedList();
    private final List<Ref<Dienst>> dienstRefs = Lists.newLinkedList();

    public List<ToegangLeveringsAutorisatie> getToegangLeveringsautorisatieEntities() {
        return toegangLeveringsautorisatieEntities;
    }

    public List<Leveringsautorisatie> getLeveringsautorisatieEntities() {
        return leveringsautorisatieEntities;
    }

    public List<Dienstbundel> getDienstbundelEntities() {
        return dienstbundelEntities;
    }

    public List<Dienst> getDienstEntities() {
        return dienstEntities;
    }

    public List<DienstbundelGroep> getDienstbundelGroepEntities() {
        return dienstbundelGroepEntities;
    }

    public List<DienstbundelGroepAttribuut> getDienstbundelGroepAttrEntities() {
        return dienstbundelGroepAttrEntities;
    }

    public List<Ref<ToegangLeveringsAutorisatie>> getToegangRefs() {
        return toegangRefs;
    }

    public List<Ref<Dienst>> getDienstRefs() {
        return dienstRefs;
    }
}
