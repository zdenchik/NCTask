package ua.edu.sumdu.j2se.zozulia.tasks;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Class ArrayTaskList - main purpose is to store default or
 * a repeatable task`s
 *
 * @version 1.21 22 Nov 2021
 * @author Denis Zozulia
 */
public class ArrayTaskList extends AbstractTaskList {

    private Task[] arrayList = new Task[10];
    private int lastElement = 0;
    private double multiplier = 1.5;

    public double getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(double multiplier) {
        this.multiplier = multiplier;
    }

    /* Add`s @param tasks to temp array and then clones into main array*/
    public void add(Task task) {
        if(task == null){throw new NullPointerException();}

        if (arrayList.length > lastElement){
            arrayList[lastElement++] = task;
            return;
        }
        Task[] tempArray = new Task[(int) (arrayList.length*multiplier)];
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

    @Override
    public Stream<Task> getStream() {
        return Arrays.stream(arrayList);
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

    public String toString() {

        StringBuilder respond = new StringBuilder("ArrayTaskList(" + lastElement
                + " elements)" + " contains{" + '\n');
        for(Task i : this){
            respond.append(i.toString()).append('\n');
        }
        respond.append('}');

        return  respond.toString();
    }

}


