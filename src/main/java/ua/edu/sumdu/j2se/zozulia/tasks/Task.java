package ua.edu.sumdu.j2se.zozulia.tasks;

import java.time.Duration;
import java.time.LocalDateTime;
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
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer repeatInterval;

    /* Default constructor for a one start class */
    public Task(String title, LocalDateTime start) throws IllegalArgumentException {
        if(start == null || start.isBefore(LocalDateTime.MIN) || start.isAfter(LocalDateTime.MAX)){
            throw new IllegalArgumentException();
        }
        this.isActive = false;
        this.isRepeated = false;
        timeSet(start, start, 0);
        this.title = title;
    }

    /* Default constructor for a repeatable task*/
    public Task(String title, LocalDateTime start, LocalDateTime end, Duration interval) throws IllegalArgumentException {
        if(interval == null || start == null || end == null){
            throw new IllegalArgumentException();
        }

        if(start.isBefore(LocalDateTime.MIN) || end.isAfter(LocalDateTime.MAX) || start.isAfter(end)){
            throw new IllegalArgumentException();
        }

        this.isActive = false;
        this.isRepeated = true;
        timeSet(start, end, 0);
        this.title = title;
    }

   private void timeSet(LocalDateTime startTime, LocalDateTime endTime, Integer repeatInterval){
        this.startTime = startTime;
        this.endTime = endTime;
        this.repeatInterval = repeatInterval;
    }

    public String getTitle() { return title;}

    public void setTitle(String title){ this.title = title;}

    public boolean isActive() { return isActive;}

    public void setActive(boolean active) {this.isActive = active;}

    public LocalDateTime getTime() { return startTime;}

    public void setTime(LocalDateTime time) throws IllegalArgumentException {
        if(time.isBefore(LocalDateTime.MIN) || time.isAfter(LocalDateTime.MAX)){
            throw new IllegalArgumentException("Interval can`t be null");
        }
        if (isRepeated) {
            this.isRepeated = false;
            this.endTime = LocalDateTime.MIN;
            this.repeatInterval = 0;
        }
        this.startTime = time;
    }

    public void setTime(LocalDateTime start, LocalDateTime end, Integer interval) {
        this.isRepeated = true;
        this.startTime = start;
        this.endTime = end;
        this.repeatInterval = interval;
    }

    public LocalDateTime getStartTime() { return startTime;}

    public LocalDateTime getEndTime() { return isRepeated ? endTime : startTime;}

    public Integer getRepeatInterval() { return isRepeated ? repeatInterval : 0;}

    public boolean isRepeated() { return isRepeated;}

    /*
     * Check when current task will be repeated from @param current, or will it repeat at all
     * @return "-1" if task won`t be repeated or "time" of a next task
     */
    public LocalDateTime nextTimeAfter(LocalDateTime current) {
        if(current.isBefore(LocalDateTime.MIN) || current.isAfter(LocalDateTime.MAX)){
            throw new IllegalArgumentException();
        }

        if(!isActive){return null;}

        if (!isRepeated) {
            return (startTime.isAfter(current)) ? null : startTime;
        }

        if (startTime.isBefore(current)) {
            return startTime;
        }

        if (current.isAfter(endTime)
                || startTime.plusSeconds(repeatInterval).isAfter(endTime)
                || current.plusSeconds(repeatInterval).isAfter(endTime)) {
            return null;
        } else {
            LocalDateTime temp = startTime;
            while(temp.isBefore(current)){
                temp = temp.plusSeconds(repeatInterval);
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