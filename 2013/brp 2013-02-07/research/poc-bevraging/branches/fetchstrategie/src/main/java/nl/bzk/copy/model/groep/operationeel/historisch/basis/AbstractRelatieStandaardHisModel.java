/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.copy.model.groep.operationeel.historisch.basis;

import javax.persistence.*;

import nl.bzk.copy.model.attribuuttype.DatumTijd;
import nl.bzk.copy.model.basis.FormeleHistorie;
import nl.bzk.copy.model.basis.FormeleHistorieImpl;
import nl.bzk.copy.model.groep.operationeel.AbstractRelatieStandaardGroep;
import nl.bzk.copy.model.objecttype.operationeel.ActieModel;
import nl.bzk.copy.model.objecttype.operationeel.RelatieModel;


/**
 * .
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractRelatieStandaardHisModel extends AbstractRelatieStandaardGroep
        implements FormeleHistorie
{

    @Id
    @SequenceGenerator(name = "hisRelatie", sequenceName = "Kern.seq_His_Relatie")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hisRelatie")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "Relatie")
    private RelatieModel relatie;

    @Embedded
    private FormeleHistorieImpl historie;

    /**
     * Default constructor tbv hibernate.
     */
    protected AbstractRelatieStandaardHisModel() {
        super();
    }

    /**
     * Constructor die op basis van een (blauwdruk) groep een nieuwe instantie creeert en alle velden direct
     * initialiseert naar de waardes uit de opgegeven (blauwdruk) groep.
     *
     * @param groep        de relatie groep waaruit de initiele waardes worden gekopieerd.
     * @param relatieModel de relatie waarvoor het geldt.
     */
    protected AbstractRelatieStandaardHisModel(final AbstractRelatieStandaardGroep groep,
                                               final RelatieModel relatieModel)
    {
        super(groep);
        relatie = relatieModel;
        if (groep instanceof AbstractRelatieStandaardHisModel) {
            historie = new FormeleHistorieImpl(((AbstractRelatieStandaardHisModel) groep).getHistorie());
        } else {
            historie = new FormeleHistorieImpl();
        }
    }

    public Long getId() {
        return id;
    }

    public RelatieModel getRelatie() {
        return relatie;
    }

    public FormeleHistorieImpl getHistorie() {
        return historie;
    }

    @Override
    public DatumTijd getDatumTijdRegistratie() {
        return historie.getDatumTijdRegistratie();
    }

    @Override
    public void setDatumTijdRegistratie(final DatumTijd datumTijdRegistratie) {
        historie.setDatumTijdRegistratie(datumTijdRegistratie);
    }

    @Override
    public DatumTijd getDatumTijdVerval() {
        return historie.getDatumTijdVerval();
    }

    @Override
    public void setDatumTijdVerval(final DatumTijd datumTijdVerval) {
        historie.setDatumTijdVerval(datumTijdVerval);
    }

    @Override
    public ActieModel getActieInhoud() {
        return historie.getActieInhoud();
    }

    @Override
    public void setActieInhoud(final ActieModel actieInhoud) {
        historie.setActieInhoud(actieInhoud);
    }

    @Override
    public ActieModel getActieVerval() {
        return historie.getActieVerval();
    }

    @Override
    public void setActieVerval(final ActieModel actieVerval) {
        historie.setActieVerval(actieVerval);
    }
}
