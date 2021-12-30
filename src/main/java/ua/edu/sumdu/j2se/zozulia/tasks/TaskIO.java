package ua.edu.sumdu.j2se.zozulia.tasks;

import com.google.gson.Gson;

import java.io.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * Class TaskIO - stores and reads Tasks
 *
 * @version 1.10 23 Dec 2021
 * @author Denis Zozulia
 */
public class TaskIO {

    public static void write(AbstractTaskList tasks, OutputStream out){

        try{ DataOutputStream outputStream = new DataOutputStream(out);

            outputStream.writeInt(tasks.size());

            for(Task task:tasks){

                String title = task.getTitle();
                outputStream.writeInt(title.length());
                outputStream.writeUTF(title);
                int active = task.isActive() ? 1:0;
                outputStream.writeInt(active);
                outputStream.writeBoolean(task.isRepeated());

                if(task.isRepeated()){
                    LocalDateTime startTime = task.getStartTime();
                    outputStream.writeLong(startTime.atZone(ZoneId.systemDefault()).toEpochSecond());
                    LocalDateTime endTime = task.getEndTime();
                    outputStream.writeLong(endTime.atZone(ZoneId.systemDefault()).toEpochSecond());
                    outputStream.writeInt(task.getRepeatInterval());
                }else {
                    LocalDateTime startTime = task.getStartTime();
                    outputStream.writeLong(startTime.atZone(ZoneId.systemDefault()).toEpochSecond());
                }
            }

        }catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void read(AbstractTaskList tasks, InputStream in){

        try{ DataInputStream inputStream = new DataInputStream(in);

            int tasksSize = inputStream.readInt();

            for (int i = 0; i < tasksSize; i++) {
                int titleLength = inputStream.readInt();
                String title = inputStream.readUTF();
                boolean active = (inputStream.readInt() == 1)? true:false;

                Task temp;
                if (inputStream.readBoolean()){
                    LocalDateTime startTime = LocalDateTime.ofInstant(
                            Instant.ofEpochSecond(inputStream.readLong()),ZoneId.systemDefault());
                    LocalDateTime endTime = LocalDateTime.ofInstant(
                            Instant.ofEpochSecond(inputStream.readLong()),ZoneId.systemDefault());
                    int repeatInterval = inputStream.readInt();
                    temp = new Task(title,startTime,endTime,repeatInterval);
                }else {
                    LocalDateTime startTime = LocalDateTime.ofInstant(
                            Instant.ofEpochSecond(inputStream.readLong()),ZoneId.systemDefault());
                    temp = new Task(title,startTime);
                }
                temp.setActive(active);
                tasks.add(temp);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeBinary(AbstractTaskList tasks, File file) {
        try { FileOutputStream fileWriter = new FileOutputStream(file);
            write(tasks,fileWriter);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void readBinary(AbstractTaskList tasks, File file){
        try { FileInputStream fileReader = new FileInputStream(file);
            read(tasks,fileReader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void write(AbstractTaskList tasks, Writer out) {
        try {
            BufferedWriter writer = new BufferedWriter(out);

            Gson gson = new Gson();

            AbstractTaskList temp;
            if (tasks instanceof LinkedTaskList) {
                temp = new ArrayTaskList();
                for (Task task : tasks) {
                    temp.add(task);
                }
            } else temp = tasks;

            String jStr = gson.toJson(temp);
            writer.write(jStr);
            writer.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void read(AbstractTaskList tasks, Reader in) {
        try {
            BufferedReader reader = new BufferedReader(in);
            Gson gson = new Gson();

            String jStr = reader.readLine();
            AbstractTaskList temp = gson.fromJson(jStr,AbstractTaskList.class);

            for(Task task:temp){
                tasks.add(task);
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void writeText(AbstractTaskList tasks, File file) {
        try { FileWriter fileWriter = new FileWriter(file);
            write(tasks,fileWriter);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readText(AbstractTaskList tasks, File file){
        try { FileReader fileReader = new FileReader(file);
            read(tasks,fileReader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    }
