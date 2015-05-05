package losgroessenalgorithmen;

import model.LotSchedulingResult;

public interface IBasicLotSchedulingAlgorithm {

    public LotSchedulingResult calculateInTotal();

    public String getDescriptionToString();
}
