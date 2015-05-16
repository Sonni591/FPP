package de.oth.smplsp.error;

public class MinimalProductionCycleError extends Exception {

    /**
     * 
     */
    public MinimalProductionCycleError() {
	super(
		"Fehler: Optimaler gemeinsamer Produktionszyklus ist kleiner als minimaler zulässiger Produktionszyklus!");
    }

}
