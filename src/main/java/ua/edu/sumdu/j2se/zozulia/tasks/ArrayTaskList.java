package ua.edu.sumdu.j2se.zozulia.tasks;

/**
 * Class ArrayTaskList - main purpose is to store default or
 * a repeatable task`s
 *
 * @version 1.20 18 Oct 2021
 * @author Denis Zozulia
 */
public class ArrayTaskList {

    private Task[] ArrayList = new Task[0];

    /* Add`s @param task to temp array and then clones into main array*/
    public void add(Task task) {
        Task[] TempArray = new Task[ArrayList.length + 1];
        for (int i = 0; i < ArrayList.length; i++) {

            TempArray[i] = ArrayList[i];

        }
        TempArray[TempArray.length - 1] = task;
        this.ArrayList = TempArray.clone();
    }

    /* Remove`s @param task array and @return true if task existed or false if not*/
    public boolean remove(Task task){

        boolean Answer = false;
        int k = 0;
        for(int i = 0; i < ArrayList.length;i++){
            if(ArrayList[i] == task){
                Answer = true;
                k = i;
                break;
            }
        }

        if(Answer){
            Task[] TempArray = new Task[ArrayList.length-1];
            for(int i = 0, j = 0; i < ArrayList.length;i++){
                if(i != k){

                    TempArray[j++] = ArrayList[i];

                }
            }
            
            this.ArrayList = TempArray.clone();
        }

        return Answer;
    }

    public int size(){ return this.ArrayList.length;}

    public Task getTask(int index){ return this.ArrayList[index];}

    /*
     * Check when task`s from array will be repeated from @param current-to, or will it repeat at all
     * @return "-1" if task won`t be repeated or "time" of a next task
    */
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
