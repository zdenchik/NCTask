package ua.edu.sumdu.j2se.zozulia.tasks;

import java.time.LocalDateTime;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Class AbstractTaskList - is a parent class for TaskList classes
 *
 * @version 1.51 21 Nov 2021
 * @author Denis Zozulia
 */
abstract class
AbstractTaskList implements Iterable<Task>, Cloneable {

    private int lastElement = 0;
    protected ListTypes.types type;

    public static AbstractTaskList create(ListTypes.types type){
        switch (type){
            case LINKED: return new LinkedTaskList();
            case ARRAY: return new ArrayTaskList();
        }
        return new ArrayTaskList();
    }

    public void setType(ListTypes.types type) {
        this.type = type;
    }

    /* Add`s @param tasks Object*/
    abstract void add(Task task);

    /* Remove`s @param task from Object and @return true if task existed or false if not*/
    abstract boolean remove(Task task);

    abstract int size();

    abstract Task getTask(int index) throws IndexOutOfBoundsException;

    /*
     * Check when task`s from Object will be repeated from @param current-to, or will it repeat at all
     * @return AbstractTaskList containing tasks that will be repeated in that time
     */
    final public AbstractTaskList incoming(LocalDateTime from, LocalDateTime to){
        if (from == null || to == null){throw new IndexOutOfBoundsException();}

        AbstractTaskList tempArrayList = TaskListFactory.createTaskList(type);

        getStream().filter(a -> a != null && a.nextTimeAfter(from).compareTo(to) <= 0
                && a.isActive()).forEach(tempArrayList::add);

        return tempArrayList;
    }

    public Stream<Task> getStream(){
       return StreamSupport.stream(spliterator(),false);
    }
}

    