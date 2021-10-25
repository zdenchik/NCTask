package ua.edu.sumdu.j2se.zozulia.tasks;

/**
 * Class ArrayTaskList - main purpose is to store default or
 * a repeatable task`s
 *
 * @version 1.20 18 Oct 2021
 * @author Denis Zozulia
 */
public class LinkedTaskList {

    /* Set up element structure*/
    private class Node {
        Node next;
        Task data;
    }

    class List {
        private Node head;
        private Node tail;

        void add(Task task)
        {
            Node a = new Node();
            a.data = task;

            if(head == null) { head = tail = a;}
            else { a.next = head = a;}

        }

        void remove(Task task)
        {
            if(head == null)
                return;

            if (head == tail) {
                head = tail = null;
                return;
            }

            if (head.data == task) {
                head = head.next;
                return;
            }

            Node t = head;       //иначе начинаем искать
            while (t.next != null) {    //пока следующий элемент существует
                if (t.next.data == task) {  //проверяем следующий элемент
                    if(tail == t.next)      //если он последний
                    {
                        tail = t;           //то переключаем указатель на последний элемент на текущий
                    }
                    t.next = t.next.next;   //найденный элемент выкидываем
                    return;                 //и выходим
                }
                t = t.next;                //иначе ищем дальше
            }
        }
    }

    private Task[] linkedList = new Task[10];
    private int lastElement = 0;
    private double koef = 1.5;

    public double getKoef() {
        return koef;
    }

    public void setKoef(double koef) {
        this.koef = koef;
    }

    /* Add`s @param task to temp array and then clones into main array*/
    public void add(Task task) throws Exception {
        if(task == null){throw new Exception("Task can`t be null");}

        if (linkedList.length > lastElement){
            linkedList[lastElement++] = task;
            return;
        }
        Task[] tempArray = new Task[(int) (linkedList.length*koef)];
        System.arraycopy(linkedList,0,tempArray,0, linkedList.length);
        linkedList = tempArray.clone();
        linkedList[lastElement++] = task;
    }

    /* Remove`s @param task array and @return true if task existed or false if not*/
    public boolean remove(Task task){

        boolean answer = false;
        int k = 0;
        for(int i = 0; i < linkedList.length; i++){
            if(linkedList[i] == task){
                answer = true;
                k = i;
                break;
            }
        }

        if(answer){
            System.arraycopy(linkedList,k+1, linkedList,k,lastElement-k-1);
           linkedList[--lastElement] = null;
        }

        return answer;
    }

    public int size(){ return lastElement;}

    public Task getTask(int index) throws IndexOutOfBoundsException{
        if(index > lastElement){ throw new IndexOutOfBoundsException("Index can`t be bigger than" +
                " maximum amaunt of elements of array"); }
        return this.linkedList[index];
    }

    /*
     * Check when task`s from array will be repeated from @param current-to, or will it repeat at all
     * @return "-1" if task won`t be repeated or "time" of a next task
    */
    public LinkedTaskList incoming(int from, int to) throws Exception{

        LinkedTaskList tempArrayList = new LinkedTaskList();
        for (int i = 0; i < lastElement; i++) {
            int temp = linkedList[i].nextTimeAfter(from);
            if(temp != -1 && temp <= to){
                tempArrayList.add(linkedList[i]);
            }
        }
        return tempArrayList;
    }

}
