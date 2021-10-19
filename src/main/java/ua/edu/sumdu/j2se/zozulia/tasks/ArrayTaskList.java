package ua.edu.sumdu.j2se.zozulia.tasks;

/**
 * Class ArrayTaskList - main purpose is to store default or
 * a repeatable task`s
 *
 * @version 1.20 18 Oct 2021
 * @author Denis Zozulia
 */
public class ArrayTaskList {

    private Task[] arrayList = new Task[0];

    /* Add`s @param task to temp array and then clones into main array*/
    public void add(Task task) {
        Task[] tempArray = new Task[arrayList.length + 1];
        for (int i = 0; i < arrayList.length; i++) {

            tempArray[i] = arrayList[i];

        }
        tempArray[tempArray.length - 1] = task;
        this.arrayList = tempArray.clone();
    }

    /* Remove`s @param task array and @return true if task existed or false if not*/
    public boolean remove(Task task){

        boolean answer = false;
        int k = 0;
        for(int i = 0; i < arrayList.length; i++){
            if(arrayList[i] == task){
                answer = true;
                k = i;
                break;
            }
        }

        if(answer){
            Task[] tempArray = new Task[arrayList.length-1];
            for(int i = 0, j = 0; i < arrayList.length; i++){
                if(i != k){

                    tempArray[j++] = arrayList[i];

                }
            }
            
            this.arrayList = tempArray.clone();
        }

        return answer;
    }

    public int size(){ return this.arrayList.length;}

    public Task getTask(int index){ return this.arrayList[index];}

    /*
     * Check when task`s from array will be repeated from @param current-to, or will it repeat at all
     * @return "-1" if task won`t be repeated or "time" of a next task
    */
    public ArrayTaskList incoming(int from, int to){

        ArrayTaskList tempArrayList = new ArrayTaskList();
        for(Task element : arrayList){
            int temp = element.nextTimeAfter(from);
            if(temp != -1 && temp <= to){
                tempArrayList.add(element);
            }
        }
        return tempArrayList;
    }

}
