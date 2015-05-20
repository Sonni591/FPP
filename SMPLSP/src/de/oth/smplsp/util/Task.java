package de.oth.smplsp.util;

import java.io.Serializable;
import java.util.List;

import org.jfree.util.ObjectUtilities;
import org.jfree.util.PublicCloneable;

public class Task implements Cloneable, PublicCloneable, Serializable {

    /** For serialization. */
    private static final long serialVersionUID = 1094303785346988894L;

    private String description;

    private double start; // start Time

    private double end; // end Time

    public double getStart() {
	return start;
    }

    public void setStart(double start) {
	this.start = start;
    }

    public double getEnd() {
	return end;
    }

    public void setEnd(double end) {
	this.end = end;
    }

    /** The percent complete (<code>null</code> is permitted). */
    private Double percentComplete;

    /** Storage for the sub-tasks (if any). */
    private List subtasks;

    public Task(String description, double start, double end) {
	if (description == null) {
	    throw new IllegalArgumentException("Null 'description' argument.");
	}
	this.description = description;
	this.start = start;
	this.end = end;
	this.percentComplete = null;
	this.subtasks = new java.util.ArrayList();
    }

    public String getDescription() {
	return this.description;
    }

    public void setDescription(String description) {
	if (description == null) {
	    throw new IllegalArgumentException("Null 'description' argument.");
	}
	this.description = description;
    }

    public Double getPercentComplete() {
	return this.percentComplete;
    }

    public void setPercentComplete(Double percent) {
	this.percentComplete = percent;
    }

    public void setPercentComplete(double percent) {
	setPercentComplete(new Double(percent));
    }

    public void addSubtask(Task subtask) {
	if (subtask == null) {
	    throw new IllegalArgumentException("Null 'subtask' argument.");
	}
	this.subtasks.add(subtask);
    }

    public void removeSubtask(Task subtask) {
	this.subtasks.remove(subtask);
    }

    public int getSubtaskCount() {
	return this.subtasks.size();
    }

    public Task getSubtask(int index) {
	return (Task) this.subtasks.get(index);
    }

    public boolean equals(Object object) {
	if (object == this) {
	    return true;
	}
	if (!(object instanceof Task)) {
	    return false;
	}
	Task that = (Task) object;
	if (!ObjectUtilities.equal(this.description, that.description)) {
	    return false;
	}
	if (!ObjectUtilities.equal(this.start, that.start)) {
	    return false;
	}
	if (!ObjectUtilities.equal(this.end, that.end)) {
	    return false;
	}
	if (!ObjectUtilities.equal(this.percentComplete, that.percentComplete)) {
	    return false;
	}
	if (!ObjectUtilities.equal(this.subtasks, that.subtasks)) {
	    return false;
	}
	return true;
    }

    public Object clone() throws CloneNotSupportedException {
	Task clone = (Task) super.clone();
	return clone;
    }

}