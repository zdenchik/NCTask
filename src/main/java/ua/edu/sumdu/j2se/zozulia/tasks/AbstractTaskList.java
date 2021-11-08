package ua.edu.sumdu.j2se.zozulia.tasks;

/**
 * Class AbstractTaskList - is a parent class for TaskList classes
 *
 * @version 1.50 08 Nov 2021
 * @author Denis Zozulia
 */
abstract class AbstractTaskList {

    private int lastElement = 0;
    private ListTypes.types type;

    /* Add`s @param tasks Object*/
    abstract void add(Task task) throws Exception;

    /* Remove`s @param task from Object and @return true if task existed or false if not*/
    abstract boolean remove(Task task);

    abstract int size();

    abstract Task getTask(int index) throws IndexOutOfBoundsException;

    /*
     * Check when task`s from Object will be repeated from @param current-to, or will it repeat at all
     * @return AbstractTaskList containing tasks that will be repeated in that time
     */
    public AbstractTaskList incoming(int from, int to) throws Exception{
        if (from < 0 || to < 0){throw new IndexOutOfBoundsException();}

        AbstractTaskList tempArrayList = TaskListFactory.createTaskList(type);
        for (int i = 0; i < lastElement; i++) {
            int temp = getTask(i).nextTimeAfter(from);
            if(temp != -1 && temp <= to){
                if (getTask(i) != null) {
                    tempArrayList.add(getTask(i));
                }
            }
        }
        return tempArrayList;
    }

    }

    