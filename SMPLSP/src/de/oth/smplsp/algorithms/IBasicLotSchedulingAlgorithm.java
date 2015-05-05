package de.oth.smplsp.algorithms;

import de.oth.smplsp.model.LotSchedulingResult;

public interface IBasicLotSchedulingAlgorithm {

    public LotSchedulingResult calculateInTotal();

    public String getDescriptionToString();
}
