package ua.edu.sumdu.j2se.zozulia.tasks;

import java.util.Arrays;

/**
 * Class ArrayTaskList - main purpose is to store default or
 * a repeatable task`s
 *
 * @version 1.20 18 Oct 2021
 * @author Denis Zozulia
 */
public class ArrayTaskList {

    private Task[] arrayList = new Task[10];
    private int lastElement = 0;
    private double koef = 1.5;

    public double getKoef() {
        return koef;
    }

    public void setKoef(double koef) {
        this.koef = koef;
    }

    /* Add`s @param task to temp array and then clones into main array*/
    public void add(Task task) {
        if (arrayList.length > lastElement){
            arrayList[lastElement++] = task;
            return;
        }
        Task[] tempArray = new Task[(int) (arrayList.length*koef)];
        System.arraycopy(arrayList,0,tempArray,0,arrayList.length);
        arrayList = tempArray.clone();
        arrayList[lastElement++] = task;
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
            System.arraycopy(arrayList,k+1,arrayList,k,lastElement-k-1);
           arrayList[--lastElement] = null;
        }

        return answer;
    }

    public int size(){ return lastElement;}

    public Task getTask(int index){ return this.arrayList[index];}

    /*
     * Check when task`s from array will be repeated from @param current-to, or will it repeat at all
     * @return "-1" if task won`t be repeated or "time" of a next task
    */
    public ArrayTaskList incoming(int from, int to){

        ArrayTaskList tempArrayList = new ArrayTaskList();
        for (int i = 0; i < lastElement; i++) {
            int temp = arrayList[i].nextTimeAfter(from);
            if(temp != -1 && temp <= to){
                tempArrayList.add(arrayList[i]);
            }
        }
        return tempArrayList;
    }

}
