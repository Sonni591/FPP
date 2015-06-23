package de.oth.smplsp.error;

/**
 * Throws a custom exception; used when the calculated product cycle is less
 * then the minimal required cycle
 * 
 * @exception MinimalProductionCycleError
 */
public class MinimalProductionCycleError extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * Throws a custom exception; used when the calculated product cycle is less
     * then the minimal required cycle
     * 
     * @exception MinimalProductionCycleError
     */
    public MinimalProductionCycleError() {
	super(
		"Fehler: Optimaler gemeinsamer Produktionszyklus ist kleiner als minimaler zulässiger Produktionszyklus!");
    }

}
