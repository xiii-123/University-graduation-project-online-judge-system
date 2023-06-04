package com.wyh.online_judge.docker_controller;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.WaitContainerResultCallback;
import com.github.dockerjava.api.model.*;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.command.LogContainerResultCallback;

import java.io.File;

public class Docker_cpp {
    //一些控制或者输出信息
    int running_time;
    int running_memory;
    String compile_error;
    String console_output;
    String error_stream;
    //所用到的组件
    DefaultDockerClientConfig config;
    DockerClient dockerClient;
    CreateContainerResponse container;
    //设置要挂载的源文件夹
    String srcFolder;

    public int getRunning_time() {
        return running_time;
    }

    public int getRunning_memory() {
        return running_memory;
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

    public DefaultDockerClientConfig getConfig() {
        return config;
    }

    public DockerClient getDockerClient() {
        return dockerClient;
    }

    public CreateContainerResponse getContainer() {
        return container;
    }

    public String getSrcFolder() {
        return srcFolder;
    }

    //构造函数
    public Docker_cpp(String srcFolder) {
        this.srcFolder = srcFolder;
        // 创建Docker客户端配置
        config = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost("tcp://localhost:2375") // Set the Docker host URL here
                .build();
        // 使用配置创建Docker客户端实例
        dockerClient = DockerClientBuilder.getInstance(config).build();
        // 创建并配置容器
        container = dockerClient.createContainerCmd("my-gcc-environment")
                .withBinds(new Bind(srcFolder, new Volume("/code"), AccessMode.rw))
                .exec();
    }

    //启动容器
    public boolean start_container(){
        try {
            // 启动容器
            dockerClient.startContainerCmd(container.getId()).exec();
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false; // 返回失败
        }
    }

    //关闭并删除容器
    public boolean close_container(){
        try {
            // 停止并删除容器
            dockerClient.stopContainerCmd(container.getId()).exec();
            dockerClient.waitContainerCmd(container.getId()).exec(new WaitContainerResultCallback()).awaitStatusCode();
            dockerClient.removeContainerCmd(container.getId()).exec();

            dockerClient.close();
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false; // 返回失败
        }
    }

    //编译
    public boolean compile() {
        try {
            //执行容器中的命令并捕获输出
            String execId = dockerClient.execCreateCmd(container.getId())
                    .withAttachStdout(true)
                    .withAttachStderr(true) // 添加捕获标准错误输出的设置
                    .withCmd("/bin/sh", "-c", "gcc -o /code/code.c /code/main.c")
                    .exec()
                    .getId();

            // 使用LogContainerResultCallback回调处理容器的日志信息
            LogContainerResultCallback logCallback = new LogContainerResultCallback() {
                @Override
                public void onNext(Frame item) {
                    // 处理标准输出和标准错误输出
                    if (item.getStreamType().equals(StreamType.STDOUT)) {
//                        System.out.println("compile_STDOUT"+item.toString());
                    } else if (item.getStreamType().equals(StreamType.STDERR)) {
//                        System.err.println("compile_STDERR"+item.toString());
                        compile_error = item.toString();
                    }
                    super.onNext(item);
                }
            };

            // 执行容器的命令并等待执行结果
            dockerClient.execStartCmd(execId)
                    .exec(logCallback)
                    .awaitCompletion();
            return true; // 返回成功
        } catch (Exception e) {
            e.printStackTrace();
            return false; // 返回失败
        }

    }

    //运行
    public boolean run(){
        try {
            //执行容器中的命令并捕获输出
            String execId = dockerClient.execCreateCmd(container.getId())
                    .withAttachStdout(true)
                    .withAttachStderr(true) // 添加捕获标准错误输出的设置
                    .withCmd("/bin/sh", "-c", "cd /code && ./code.c")
                    .exec()
                    .getId();

            // 使用LogContainerResultCallback回调处理容器的日志信息
            LogContainerResultCallback logCallback = new LogContainerResultCallback() {
                @Override
                public void onNext(Frame item) {
                    // 处理标准输出和标准错误输出
                    if (item.getStreamType().equals(StreamType.STDOUT)) {
                        console_output = item.toString();
//                        System.out.println("run_STDOUT"+item.toString());
                    } else if (item.getStreamType().equals(StreamType.STDERR)) {
//                        System.err.println("run_STDERR"+item.toString());
                        error_stream = item.toString();
                    }
                    super.onNext(item);
                }
            };

            // 执行容器的命令并等待执行结果
            dockerClient.execStartCmd(execId)
                    .exec(logCallback)
                    .awaitCompletion();
            return true; // 返回成功
        } catch (Exception e) {
            e.printStackTrace();
            return false; // 返回失败
        }
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

    //完整判题流程
    public int judgement(){
        start_container();
        compile();
        run();
        close_container();
        clean();
        if (getCompile_error()!=null)
            return 0;
        else if (getError_stream()!=null)
            return 1;
        else
            return 2;
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
}
