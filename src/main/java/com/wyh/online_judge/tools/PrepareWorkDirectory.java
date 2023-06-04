package com.wyh.online_judge.tools;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

public class PrepareWorkDirectory {
    private String srcDirectory = "D:\\test_docker";

    private String id;

    public PrepareWorkDirectory(String id) {
        this.id = id;
    }

    //创建工作目录
    public boolean create_workDirectory(){
        //创建文件对象
        File file = new File(srcDirectory, id);

        if(file.mkdirs()) {
            return true;
        } else {
            return false;
        }

    }
    //创建code.c文件
    public boolean create_code_c(String code) {
        try {
            // 创建文件对象
            File file = new File(srcDirectory+"\\"+id, "code.c");

            // 如果文件已经存在，则删除文件
            if (file.exists()) {
                file.delete();
            }

            // 创建新文件
            file.createNewFile();

            // 写入文件内容
            FileWriter writer = new FileWriter(file);
            writer.write(code);
            writer.close();

            // 返回成功
            return true;
        } catch (IOException e) {
            // 处理异常并返回失败
            e.printStackTrace();
            return false;
        }
    }
    //创建code.c文件
    public boolean create_code_cpp(String code) {
        try {
            // 创建文件对象
            File file = new File(srcDirectory+"\\"+id, "code.c");

            // 如果文件已经存在，则删除文件
            if (file.exists()) {
                file.delete();
            }

            // 创建新文件
            file.createNewFile();

            // 写入文件内容
            FileWriter writer = new FileWriter(file);
            writer.write(code);
            writer.close();

            // 返回成功
            return true;
        } catch (IOException e) {
            // 处理异常并返回失败
            e.printStackTrace();
            return false;
        }
    }
    //创建Solution.java
    public boolean create_Solution_java(String code) {
        try {
            // 创建文件对象
            File file = new File(srcDirectory+"\\"+id, "Solution.java");

            // 如果文件已经存在，则删除文件
            if (file.exists()) {
                file.delete();
            }

            // 创建新文件
            file.createNewFile();

            // 写入文件内容
            FileWriter writer = new FileWriter(file);
            writer.write(code);
            writer.close();

            // 返回成功
            return true;
        } catch (IOException e) {
            // 处理异常并返回失败
            e.printStackTrace();
            return false;
        }
    }
    //创建main.c文件
    public boolean create_main_c(String code) {
        try {
            // 创建文件对象
            File file = new File(srcDirectory+"\\"+id, "main.c");

            // 如果文件已经存在，则删除文件
            if (file.exists()) {
                file.delete();
            }

            // 创建新文件
            file.createNewFile();

            // 写入文件内容
            FileWriter writer = new FileWriter(file);
            writer.write(code);
            writer.close();

            // 返回成功
            return true;
        } catch (IOException e) {
            // 处理异常并返回失败
            e.printStackTrace();
            return false;
        }
    }
    //创建main.c文件
    public boolean create_main_cpp(String code) {
        try {
            // 创建文件对象
            File file = new File(srcDirectory+"\\"+id, "main.c");

            // 如果文件已经存在，则删除文件
            if (file.exists()) {
                file.delete();
            }

            // 创建新文件
            file.createNewFile();

            // 写入文件内容
            FileWriter writer = new FileWriter(file);
            writer.write(code);
            writer.close();

            // 返回成功
            return true;
        } catch (IOException e) {
            // 处理异常并返回失败
            e.printStackTrace();
            return false;
        }
    }
    //创建Main.java
    public boolean create_Main_java(String code) {
        try {
            // 创建文件对象
            File file = new File(srcDirectory+"\\"+id, "Main.java");

            // 如果文件已经存在，则删除文件
            if (file.exists()) {
                file.delete();
            }

            // 创建新文件
            file.createNewFile();

            // 写入文件内容
            FileWriter writer = new FileWriter(file);
            writer.write(code);
            writer.close();

            // 返回成功
            return true;
        } catch (IOException e) {
            // 处理异常并返回失败
            e.printStackTrace();
            return false;
        }
    }
    //创建test_set文件
    public boolean create_test(String code) {
        try {
            // 创建文件对象
            File file = new File(srcDirectory+"\\"+id, "test.txt");

            // 如果文件已经存在，则删除文件
            if (file.exists()) {
                file.delete();
            }

            // 创建新文件
            file.createNewFile();

            // 写入文件内容
            FileWriter writer = new FileWriter(file);
            writer.write(code);
            writer.close();

            // 返回成功
            return true;
        } catch (IOException e) {
            // 处理异常并返回失败
            e.printStackTrace();
            return false;
        }
    }
    //完整流程C
    public boolean prepareWorkDirectory_c(String code, String main, String test) {
        if (create_workDirectory() && create_code_c(code) && create_main_c(main) && create_test(test))
            return true;
        else
            return false;
    }
    //完整流程cpp
    public boolean prepareWorkDirectory_cpp(String code, String main, String test) {
        if (create_workDirectory() && create_code_c(code) && create_main_c(main) && create_test(test))
            return true;
        else
            return false;
    }
    //完整流程Java
    public boolean prepareWorkDirectory_java(String code, String main, String test) {
        if (create_workDirectory() && create_Solution_java(code) && create_Main_java(main) && create_test(test))
            return true;
        else
            return false;
    }
}
