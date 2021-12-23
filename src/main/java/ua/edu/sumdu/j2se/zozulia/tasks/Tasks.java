package ua.edu.sumdu.j2se.zozulia.tasks;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Class Tasks - manages Tasks that will be done in future
 *
 * @version 1.10 11 Dec 2021
 * @author Denis Zozulia
 */
public class Tasks {

    public static Iterable<Task> incoming(Iterable<Task> tasks, LocalDateTime start, LocalDateTime end){
        if (start.isBefore(LocalDateTime.MIN) || end.isAfter(LocalDateTime.MAX) || start.isAfter(end)){
            throw new IndexOutOfBoundsException();
        }

        AbstractTaskList tempList = new ArrayTaskList();

        tasks.forEach(tempList::add);

        Stream<Task> stream = tempList.getStream();

        Set<Task> returnTask = new LinkedHashSet<>();

        stream.filter(a -> a != null && a.nextTimeAfter(start) != null && a.isActive()
                && a.nextTimeAfter(start).isAfter(start.minusSeconds(1))
                && a.nextTimeAfter(start).isBefore(end.plusSeconds(1))).forEach(returnTask::add);

        return returnTask;
    }

    public static SortedMap<LocalDateTime, Set<Task>> calendar(Iterable<Task> tasks, LocalDateTime start, LocalDateTime end){
        if (start.isBefore(LocalDateTime.MIN) || end.isAfter(LocalDateTime.MAX) || start.isAfter(end)){
            throw new IndexOutOfBoundsException();
        }

        LinkedHashSet<Task> incoming = (LinkedHashSet<Task>) incoming(tasks, start, end);

        SortedMap<LocalDateTime, Set<Task>> returnTask = new TreeMap<>();
        LocalDateTime time;
        for(Task temp : incoming){
            time = start;
            while (time != null && (time.isBefore(end) | time.isEqual(end))){

                Set<Task> tempTask;
                time = temp.nextTimeAfter(time);
                Stream<Task> stream = incoming.stream();
                LocalDateTime endOfTask = time;

                tempTask = stream.filter(t -> endOfTask != null
                       && endOfTask.equals(t.nextTimeAfter(endOfTask.minusSeconds(1)))).collect(Collectors.toSet());

                if  (time != null && (time.isBefore(end) | time.isEqual(end))) {
                    returnTask.put(time, tempTask);
                }

            }

            return returnTask;
        }

        return null;
    }






}
