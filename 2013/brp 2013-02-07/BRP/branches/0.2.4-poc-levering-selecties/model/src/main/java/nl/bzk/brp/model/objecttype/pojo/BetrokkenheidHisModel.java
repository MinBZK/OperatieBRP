/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.objecttype.pojo;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import nl.bzk.brp.model.groep.operationeel.historisch.BetrokkenheidOuderlijkGezagHisModel;
import nl.bzk.brp.model.groep.operationeel.historisch.BetrokkenheidOuderschapHisModel;
import nl.bzk.brp.model.objecttype.operationeel.BetrokkenheidModel;

/**
 * TODO: Add documentation
 */
public class BetrokkenheidHisModel {

    private final Integer id;

    private PersoonHisModel persoonHisModel;

    @JsonProperty
    private BetrokkenheidModel betrokkenheid;

    @JsonProperty
    private List<BetrokkenheidOuderschapHisModel> ouderschapHisModels;

    @JsonProperty
    private List<BetrokkenheidOuderlijkGezagHisModel> ouderlijkGezagHisModels;

    /**
     * Default constructor.
     */
    public BetrokkenheidHisModel() {
        this(-1);
    }

    /**
     * Constructor.
     * @param i de identifier
     */
    public BetrokkenheidHisModel(final Integer i) {
        this.id = i;

        ouderlijkGezagHisModels = new ArrayList<BetrokkenheidOuderlijkGezagHisModel>();
        ouderschapHisModels = new ArrayList<BetrokkenheidOuderschapHisModel>();
    }

    public Integer getId() {
        return id;
    }

    public PersoonHisModel getPersoonHisModel() {
        return persoonHisModel;
    }

    public void setPersoonHisModel(final PersoonHisModel persoonHisModel) {
        this.persoonHisModel = persoonHisModel;
    }

    public BetrokkenheidModel getBetrokkenheid() {
        return betrokkenheid;
    }

    public void setBetrokkenheid(final BetrokkenheidModel betrokkenheid) {
        this.betrokkenheid = betrokkenheid;
    }

    public List<BetrokkenheidOuderschapHisModel> getOuderschapHisModels() {
        return ouderschapHisModels;
    }

    public boolean addOuderschapHisModel(final BetrokkenheidOuderschapHisModel ouderschapHisModel) {
        return this.ouderschapHisModels.add(ouderschapHisModel);
    }
    public boolean addAllOuderschapHisModel(final List<BetrokkenheidOuderschapHisModel> ouderschapHisModels) {
        return this.ouderschapHisModels.addAll(ouderschapHisModels);
    }

    public List<BetrokkenheidOuderlijkGezagHisModel> getOuderlijkGezagHisModels() {
        return ouderlijkGezagHisModels;
    }

    public boolean addOuderlijkGezagHisModel(final BetrokkenheidOuderlijkGezagHisModel hisModel) {
        return this.ouderlijkGezagHisModels.add(hisModel);
    }
    public boolean addAllOuderlijkGezagHisModel(final List<BetrokkenheidOuderlijkGezagHisModel> hisModels) {
        return this.ouderlijkGezagHisModels.addAll(hisModels);
    }

}
