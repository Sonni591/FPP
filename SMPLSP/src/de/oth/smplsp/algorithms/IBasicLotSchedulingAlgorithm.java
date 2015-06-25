package de.oth.smplsp.algorithms;

import de.oth.smplsp.error.MinimalProductionCycleError;
import de.oth.smplsp.model.LotSchedulingResult;

public interface IBasicLotSchedulingAlgorithm {

    /**
     * calculates the result with the algorithm
     * 
     * @return the result computed from the algorithm
     * @throws MinimalProductionCycleError
     */
    public LotSchedulingResult calculateInTotal()
	    throws MinimalProductionCycleError;

    /**
     * @return the description of the algorithm
     */
    public String getDescriptionToString();

    /**
     * @return the result computed from the algorithm
     */
    public LotSchedulingResult getResult();

}
