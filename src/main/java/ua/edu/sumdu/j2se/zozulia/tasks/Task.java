package ua.edu.sumdu.j2se.zozulia.tasks;

import java.util.Objects;

/**
 * Class Task - main purpose is to create default or
 * a repeatable task and manage already created one`s
 *
 * @version 1.10 11 Oct 2021
 * @author Denis Zozulia
 */
public class Task implements Cloneable{

    private String title;
    private boolean isActive;
    private boolean isRepeated;
    private int startTime;
    private int endTime;
    private int repeatInterval;

    /* Default constructor for a one time class */
    public Task(String title, int time) throws IllegalArgumentException {
        if(time < 0){
            throw new IllegalArgumentException("Time can`t be below 0");
        }
        this.isActive = false;
        this.isRepeated = false;
        timeSet(time, time, 0);
        this.title = title;
    }

    /* Default constructor for a repeatable task*/
    public Task(String title, int start, int end, int interval) throws IllegalArgumentException {
        if(start < 0 || end < 0){
            throw new IllegalArgumentException("Time can`t be below 0");
        }
        if(interval <= 0){
            throw new IllegalArgumentException("Interval can`t be below or equal 0");
        }
        this.isActive = false;
        this.isRepeated = true;
        timeSet(start, end, interval);
        this.title = title;
    }

   private void timeSet(int startTime, int endTime, int repeatInterval){
        this.startTime = startTime;
        this.endTime = endTime;
        this.repeatInterval = repeatInterval;
    }

    public String getTitle() { return title;}

    public void setTitle(String title){ this.title = title;}

    public boolean isActive() { return isActive;}

    public void setActive(boolean active) { this.isActive = active;}

    public int getTime() { return startTime;}

    public void setTime(int time) throws IllegalArgumentException {
        if(time < 0){
            throw new IllegalArgumentException("Time can`t be below 0");
        }
        if (isRepeated) {
            this.isRepeated = false;
            this.endTime = 0;
            this.repeatInterval = 0;
        }
        this.startTime = time;
    }

    public void setTime(int start, int end, int interval) {
        this.isRepeated = true;
        this.startTime = start;
        this.endTime = end;
        this.repeatInterval = interval;
    }

    public int getStartTime() { return startTime;}

    public int getEndTime() { return isRepeated ? endTime : startTime;}

    public int getRepeatInterval() { return isRepeated ? repeatInterval : 0;}

    public boolean isRepeated() { return isRepeated;}

    /*
     * Check when current task will be repeated from @param current, or will it repeat at all
     * @return "-1" if task won`t be repeated or "time" of a next task
     */
    public int nextTimeAfter(int current) {
        if(!isActive){return -1;}

        if (!isRepeated) {
            return (current >= startTime) ? -1 : startTime;
        }

        if (current < startTime) {
            return startTime;
        }

        if (current >= endTime || (startTime + repeatInterval) >= endTime
                || endTime <= (current + repeatInterval)) {
            return -1;
        } else {
            int temp = startTime;
            while(temp <= current){
                temp = temp + repeatInterval;
            }
            return temp;

        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task task = (Task) o;
        return isActive() == task.isActive() &&
                isRepeated() == task.isRepeated() &&
                getStartTime() == task.getStartTime() &&
                getEndTime() == task.getEndTime() &&
                getRepeatInterval() == task.getRepeatInterval() &&
                getTitle().equals(task.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), isActive(), isRepeated(), getStartTime(), getEndTime(), getRepeatInterval());
    }

    private Task superClone() {
        try {
            return (Task) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
    }

    public Task clone(){
        Task clone = superClone();

        clone.isActive = this.isActive();
        clone.isRepeated = this.isRepeated();
        clone.title = getTitle();
        clone.timeSet(getStartTime(), getEndTime(), getRepeatInterval());

        return clone;
    }

    public String toString() {
        if(isRepeated){
            return  "Repeatable Task{'" + title + "', Active = " + isActive
                    + ", starts = " + startTime + ", ends = " + endTime +
                    ", repeats = " + repeatInterval + '}';
        }
            return  "NonRepeatable Task{'" + title + "', Active = " + isActive
                    + ", starts = " + startTime +'}';

    }
}