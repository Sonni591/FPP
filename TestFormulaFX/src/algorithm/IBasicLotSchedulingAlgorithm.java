package algorithm;

import model.LotSchedulingResult;

public interface IBasicLotSchedulingAlgorithm {

    public LotSchedulingResult calculateInTotal();

    public String getDescriptionToString();
}
