package ua.edu.sumdu.j2se.zozulia.tasks;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedMap;

/**
 * Class Tasks - ...
 *
 * @version 1.10 11 Dec 2021
 * @author Denis Zozulia
 */
public class Tasks {

    public static Iterable<Task> incoming(Iterable<Task> tasks, LocalDateTime start, LocalDateTime end){
        if (start == null || end == null){throw new IndexOutOfBoundsException();}

        for(Iterator<Task> i = tasks.iterator(); i.hasNext();i.next()){
            Task tempTask = i.next();

            if(tempTask != null && tempTask.nextTimeAfter(start).compareTo(end) <= 0){
            i.next();
            }
            else i.remove();
        }

        return tasks;
    }

    public static SortedMap<Date, Set<Task>> calendar(Iterable<Task> tasks, LocalDateTime start, LocalDateTime end){
        return null;
    }






}
