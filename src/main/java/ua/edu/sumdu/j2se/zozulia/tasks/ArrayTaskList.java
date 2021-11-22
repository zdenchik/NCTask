package ua.edu.sumdu.j2se.zozulia.tasks;

import java.util.*;
import java.util.function.Consumer;

/**
 * Class ArrayTaskList - main purpose is to store default or
 * a repeatable task`s
 *
 * @version 1.20 18 Oct 2021
 * @author Denis Zozulia
 */
public class ArrayTaskList extends AbstractTaskList {

    private Task[] arrayList = new Task[10];
    private int lastElement = 0;
    private double koef = 1.5;

    public double getKoef() {
        return koef;
    }

    public void setKoef(double koef) {
        this.koef = koef;
    }

    /* Add`s @param tasks to temp array and then clones into main array*/
    public void add(Task task) {
        if(task == null){throw new NullPointerException();}

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
            if(arrayList[i].equals(task)){
                answer = true;
                k = i;
                break;
            }
        }

        if(answer){
            System.arraycopy(arrayList,k+1,arrayList,k, lastElement -k-1);
           arrayList[--lastElement] = null;
        }

        return answer;
    }

    public int size(){ return lastElement;}

    public Task getTask(int index) throws IndexOutOfBoundsException{
        if(index > lastElement){ throw new IndexOutOfBoundsException("Index can`t be bigger than" +
                " maximum amount of elements of array"); }
        return this.arrayList[index];
    }

    /*
     * Check when task`s from array will be repeated from @param current-to, or will it repeat at all
     * @return ArrayTaskList containing tasks that will be repeated in that time
     */
    public ArrayTaskList incoming(int from, int to){
        if (from < 0 || to < 0){throw new IndexOutOfBoundsException();}

        ArrayTaskList tempArrayList = new ArrayTaskList();
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

    @Override
    public Iterator<Task> iterator() {
        return new Iterator<Task>() {
                private int lastReturnedElement = -1;
                private int currentIterator = 0;

                @Override
                public boolean hasNext() { return currentIterator < lastElement;}

                @Override
                public Task next() {
                    if(hasNext()){
                        lastReturnedElement = currentIterator;
                        return arrayList[currentIterator++];
                    }else throw new NoSuchElementException();
                }

                @Override
                public void remove() throws IllegalStateException{
                   if(lastReturnedElement < 0){ throw new IllegalStateException();}
                   else {
                       ArrayTaskList.this.remove(arrayList[lastReturnedElement]);
                       currentIterator = lastReturnedElement;
                       lastReturnedElement = -1;
                   }
                }
        };
    }

    private ArrayTaskList superClone() {
        try {
            return (ArrayTaskList) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
    }

    @Override
    public ArrayTaskList clone(){
        ArrayTaskList clone = superClone();

        clone.lastElement = 0;
        clone.arrayList = arrayList.clone();

        for (int n = 0; n < lastElement; n++) clone.add(arrayList[n]);

        return clone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArrayTaskList)) return false;
        if (o == null || getClass() != o.getClass()) return false;
        ArrayTaskList tasks = (ArrayTaskList) o;

        return lastElement == tasks.lastElement &&
                Arrays.equals(arrayList, tasks.arrayList);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(lastElement);
        result = 31 * result + Arrays.hashCode(arrayList);
        return result;
    }

    @Override
    public void forEach(Consumer<? super Task> action) {

    }

    @Override
    public Spliterator<Task> spliterator() {
        return null;
        }

    public String toString() {
        String respond = "ArrayTaskList{ size = "  + lastElement + '\n' + "contains[";
        for(Task i : this){
            respond =  respond + i.toString() + '\n';
        }
        return  respond + "]}";
    }
}


