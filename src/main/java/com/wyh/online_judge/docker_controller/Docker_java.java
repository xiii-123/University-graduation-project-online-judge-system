package com.wyh.online_judge.docker_controller;

import com.github.dockerjava.api.command.WaitContainerResultCallback;
import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.api.model.StreamType;
import com.github.dockerjava.core.command.LogContainerResultCallback;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Docker_java {
    String compile_error;
    String console_output;
    String error_stream;
    String srcFolder;

    public Docker_java(String srcFolder) {
        this.srcFolder = srcFolder;
    }

    public int run_java() throws IOException, InterruptedException {
        // 编译code.java和main.java文件
        ProcessBuilder compilerProcessBuilder = new ProcessBuilder("javac", "Main.java", "Solution.java");
        compilerProcessBuilder.directory(new File(srcFolder));
        compilerProcessBuilder.redirectErrorStream(true);
        Process compilerProcess = compilerProcessBuilder.start();
        BufferedReader compilerOutput = new BufferedReader(new InputStreamReader(compilerProcess.getInputStream(),"gbk"));
        String line;
        StringBuilder compile_err_in = new StringBuilder();
        while ((line = compilerOutput.readLine()) != null) {
            compile_err_in.append(line).append("\n");
        }
        compilerProcess.waitFor();

        // 运行main.java程序
        ProcessBuilder javaProcessBuilder = new ProcessBuilder("java", "Main");
        javaProcessBuilder.directory(new File(srcFolder));
        javaProcessBuilder.redirectErrorStream(true);
        Process javaProcess = javaProcessBuilder.start();
        BufferedReader javaOutput = new BufferedReader(new InputStreamReader(javaProcess.getInputStream(),"UTF-8"));
        BufferedReader javaError = new BufferedReader(new InputStreamReader(javaProcess.getErrorStream(),"UTF-8"));
        StringBuilder stderr_in = new StringBuilder();
        StringBuilder stdout_in = new StringBuilder();
        while ((line = javaOutput.readLine()) != null) {
            stdout_in.append(line).append("\n");
        }
        while ((line = javaError.readLine()) != null) {
            stderr_in.append(line).append("\n");
        }
        javaProcess.waitFor();
        Thread.sleep(2000);
        // 输出结果
        clean();
        compile_error=compile_err_in.toString();
        error_stream=stdout_in.toString();
        if (!compile_error.equals("") && !compile_error.equals("null"))
            return 0;
        else if (!error_stream.equals("") && !error_stream.equals("null"))
            return 1;
        else
            return 2;
    }

    //清理工作目录
    public boolean clean() {
        // 创建要删除的目录对象
        File directory = new File(srcFolder);

        // 调用递归删除方法
        deleteDirectory(directory);

        // 删除工作目录
        return directory.delete();
    }

    private void deleteDirectory(File directory) {
        // 如果是文件，则直接删除
        if(directory.isFile()) {
            directory.delete();
            return;
        }

        // 获取文件夹中的所有文件和文件夹
        File[] files = directory.listFiles();

        // 递归删除所有文件和文件夹
        for (File file : files) {
            if (file.isDirectory()) {
                deleteDirectory(file);
            } else {
                file.delete();
            }
        }

        // 删除空文件夹
        directory.delete();
    }

    public String getCompile_error() {
        return compile_error;
    }

    public String getConsole_output() {
        return console_output;
    }

    public String getError_stream() {
        return error_stream;
    }

    public String getSrcFolder() {
        return srcFolder;
    }


}
