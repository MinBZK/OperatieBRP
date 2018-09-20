/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.groep.operationeel.historisch.basis;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

import nl.bzk.brp.model.attribuuttype.Datum;
import nl.bzk.brp.model.attribuuttype.DatumTijd;
import nl.bzk.brp.model.basis.MaterieleHistorie;
import nl.bzk.brp.model.basis.MaterieleHistorieImpl;
import nl.bzk.brp.model.groep.operationeel.AbstractPersoonOpschortingGroep;
import nl.bzk.brp.model.objecttype.operationeel.ActieModel;
import nl.bzk.brp.model.objecttype.operationeel.PersoonModel;


/**
 * .
 *
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@SuppressWarnings("serial")
public abstract class AbstractPersoonOpschortingHisModel extends AbstractPersoonOpschortingGroep implements
        MaterieleHistorie
{

    @Id
    @SequenceGenerator(name = "hisPersOpschorting", sequenceName = "Kern.seq_his_persopschorting")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hisPersOpschorting")
    @Column(name = "id")
    private Long                  id;

    @ManyToOne
    @JoinColumn(name = "Pers")
    private PersoonModel persoon;

    @Embedded
    private MaterieleHistorieImpl historie;

    /**
     * Standaard (lege) constructor.
     */
    protected AbstractPersoonOpschortingHisModel() {
    }

    /**
     * Constructor die op basis van een (blauwdruk) groep een nieuwe instantie creeert en alle velden direct
     * initialiseert naar de waardes uit de opgegeven (blauwdruk) groep.
     *
     * @param persoonOpschortingGroep de persoon opschorting groep waaruit de initiele waardes worden gekopieerd.
     * @param persoonModel de persoon waarvoor de opschorting geldt.
     */
    protected AbstractPersoonOpschortingHisModel(final AbstractPersoonOpschortingGroep persoonOpschortingGroep,
                                                 final PersoonModel persoonModel)
    {
        super(persoonOpschortingGroep);
        this.persoon = persoonModel;
        if (persoonOpschortingGroep instanceof AbstractPersoonOpschortingHisModel) {
            historie = new MaterieleHistorieImpl(
                    ((AbstractPersoonOpschortingHisModel) persoonOpschortingGroep).getHistorie()
            );
        } else {
            historie = new MaterieleHistorieImpl();
        }
    }

    public Long getId() {
        return id;
    }

    public PersoonModel getPersoon() {
        return persoon;
    }

    public MaterieleHistorieImpl getHistorie() {
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

    @Override
    public Datum getDatumAanvangGeldigheid() {
        return historie.getDatumAanvangGeldigheid();
    }

    @Override
    public void setDatumAanvangGeldigheid(final Datum datumAanvangGeldigheid) {
        historie.setDatumAanvangGeldigheid(datumAanvangGeldigheid);
    }

    @Override
    public Datum getDatumEindeGeldigheid() {
        return historie.getDatumEindeGeldigheid();
    }

    @Override
    public void setDatumEindeGeldigheid(final Datum datumEindeGeldigheid) {
        historie.setDatumEindeGeldigheid(datumEindeGeldigheid);
    }

    @Override
    public ActieModel getActieAanpassingGeldigheid() {
        return historie.getActieAanpassingGeldigheid();
    }

    @Override
    public void setActieAanpassingGeldigheid(final ActieModel actieAanpassingGeldigheid) {
        historie.setActieAanpassingGeldigheid(actieAanpassingGeldigheid);
    }
}
