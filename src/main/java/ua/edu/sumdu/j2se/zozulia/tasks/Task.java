package ua.edu.sumdu.j2se.zozulia.tasks;

/**
 * Class Task - main purpose is to create default or
 * a repeatable task and manage already created one`s
 *
 * @version 1.10 11 Oct 2021
 * @author Denis Zozulia
 */
public class Task {

    private String title;
    private boolean isActive;
    private boolean isRepeated;
    private int startTime;
    private int endTime;
    private int repeatInterval;

    /* Default constructor for a one time class */
    public Task(String title, int time) {
        this.isActive = false;
        this.isRepeated = false;
        timeSet(time, time, 0);
        this.title = title;
    }

    /* Default constructor for a repeatable task*/
    public Task(String title, int start, int end, int interval) {
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

    /* Get`s title of current task*/
    public String getTitle() { return title;}

    /* Set`s title of current task*/
    public void setTitle(String title){ this.title = title;}

    /* Check if current task is active */
    public boolean isActive() { return isActive;}

    public void setActive(boolean active) { this.isActive = active;}

    public int getTime() { return startTime;}


    public void setTime(int time) {
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
     *Check when current task will be repeated from @param current, or will it repeat at all
     * @return "-1" if task won`t be repeated or "time" of a next task
     */
    public int nextTimeAfter(int current) {
        if(!isActive){return -1;}

        if (!isRepeated) {
            return (current >= this.startTime) ? -1 : this.startTime;
        }

        if (current < this.startTime) {
            return this.startTime;
        }

        if (current >= this.endTime || (this.startTime + this.repeatInterval) >= this.endTime
                || this.endTime <= (current + this.repeatInterval)) {
            return -1;
        } else {
            int temp = this.startTime;
            while(temp <= current){
                temp = temp + this.repeatInterval;
            }
            return temp;

        }
    }
}