package ua.edu.sumdu.j2se.zozulia.tasks;

import java.util.ArrayList;

/**
 * Class ArrayTaskList - main purpose is to store default or
 * a repeatable task`s
 *
 * @version 1.10 18 Oct 2021
 * @author Denis Zozulia
 */
public class ArrayTaskList {

    private ArrayList<Task> ArrayList = new ArrayList<>();

    public void add(Task task){ this.ArrayList.add(task);}

    public boolean remove(Task task){ return  this.ArrayList.remove(task);}

    public int size(){ return this.ArrayList.size();}

    public Task getTask(int index){ return this.ArrayList.get(index);}

    public ArrayTaskList incoming(int from, int to){

        ArrayTaskList TempArrayList = new ArrayTaskList();
        for(Task element : ArrayList){
            int temp = element.nextTimeAfter(from);
            if(temp != -1 && temp <= to){
                TempArrayList.add(element);
            }
        }
        return TempArrayList;
    }

}
