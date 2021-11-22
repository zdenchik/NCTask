package ua.edu.sumdu.j2se.zozulia.tasks;

import java.util.*;
import java.util.function.Consumer;

/**
 * Class LinkedTaskList - main purpose is to store default or
 * a repeatable task`s
 *
 * @version 1.20 06 Nov 2021
 * @author Denis Zozulia
 */
public class LinkedTaskList extends AbstractTaskList{

    /* Set up element structure*/
    private static class Node {
        Node next;
        Node prev; //for a next time use
        Task data;
    }

        private Node head;
        private Node tail;
        private int lastElement = 0;

    /* Add`s @param tasks to Object*/
    public void add(Task task){
        if(task == null){throw new NullPointerException();}

        Node a = new Node();
            a.data = task;

            if(head == null) { head = tail = a;}
            else {
                a.prev = head;
                head.next = a;
                head = a;
            }
            lastElement++;
    }

    /* Remove`s @param task from Object and @return true if task existed or false if not*/
    public boolean remove(Task task) {
            if(head == null)
                return false;

            if (head.equals(tail)) {
                head = tail = null;
                lastElement--;
                return true;
            }

            if (tail.data.equals(task)) {
                tail = tail.next;
                tail.prev = null;
                lastElement--;
                return true;
            }

            Node temp = tail;
            while (temp.next != null) {
                if (temp.next.data.equals(task)) {
                    if(head == temp.next)
                    {
                        head = temp;
                        head.next = null;
                        lastElement--;
                        return true;
                    }
                    temp.next.next.prev = temp;
                    temp.next = temp.next.next;
                    lastElement--;
                    return true;
                }
                temp = temp.next;
            }
            return false;
    }


    public int size(){ return lastElement;}

    public Task getTask(int index) throws IndexOutOfBoundsException{
        if(index > lastElement){ throw new IndexOutOfBoundsException("Index can`temp be bigger than" +
                " maximum amount of elements in array"); }

        Node temp = tail;
        for (int i = 0; i < index; i++) {
            temp = temp.next;
        }
        return temp.data;
    }

    /*
     * Check when task`s from array will be repeated from @param current-to, or will it repeat at all
     * @return LinkedTaskList containing tasks that will be repeated in that time
     */
    public LinkedTaskList incoming(int from, int to){
        if (from < 0 || to < 0){throw new IndexOutOfBoundsException();}

        LinkedTaskList tempArrayList = new LinkedTaskList();
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
            private LinkedTaskList.Node lastReturnedElement;
            private LinkedTaskList.Node nextElement = tail;
            private int currentIterator = 0;

            @Override
            public boolean hasNext() { return currentIterator < lastElement;}

            @Override
            public Task next() {
                if (hasNext()){
                    lastReturnedElement = nextElement;
                    nextElement = nextElement.next;
                    currentIterator++;
                    return lastReturnedElement.data;

                }else throw new NoSuchElementException();
            }

            @Override
            public void remove() throws IllegalStateException{
                if(lastReturnedElement == null){ throw new IllegalStateException();}
                else {
                    LinkedTaskList.this.remove(lastReturnedElement.data);
                    lastReturnedElement = null;
                    currentIterator--;
                }
            }


        };

    }

    private LinkedTaskList superClone() {
        try {
            return (LinkedTaskList) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
    }

    @Override
    public LinkedTaskList clone() {

        LinkedTaskList result = superClone();
        result.tail = result.head = null;
        result.lastElement = 0;

        for (Node n = tail; n != null; n = n.next) result.add(n.data);

        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LinkedTaskList)) return false;
        if (o == null || getClass() != o.getClass() || size() != ((LinkedTaskList) o).size()) return false;

        LinkedTaskList tasks = (LinkedTaskList) o;

        for (int i = 0; i < size(); i++) {
            if(!tasks.getTask(i).equals(getTask(i))){
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int[] a = new int[lastElement];
        for (int i = 0; i < lastElement; i++) {
            a[i] = getTask(i).hashCode();
        }
        int result = Objects.hash(lastElement);
        result = 31 * result + Arrays.hashCode(a);

        return result;
    }

    @Override
    public void forEach(Consumer<? super Task> action) {
        for (Node i = tail; i.next != null; i = i.next) {
            action.accept(i.data);
        }
    }

    public String toString() {

        StringBuilder respond = new StringBuilder("LinkedTaskList(" + lastElement + " elements)" + " contains{" + '\n');
        for(Task i : this){
            respond.append(i.toString()).append('\n');
        }
        respond.append('}');

        return  respond.toString();
    }
}
