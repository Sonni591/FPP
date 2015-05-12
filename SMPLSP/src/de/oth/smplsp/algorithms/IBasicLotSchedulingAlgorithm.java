package de.oth.smplsp.algorithms;

import de.oth.smplsp.error.MinimalProductionCycleError;
import de.oth.smplsp.model.LotSchedulingResult;

public interface IBasicLotSchedulingAlgorithm {

    public LotSchedulingResult calculateInTotal()
	    throws MinimalProductionCycleError;

    public String getDescriptionToString();

}
