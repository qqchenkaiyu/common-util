package com.cky.jsch;

import com.cky.io.IoUtil;
import com.cky.strUtil.StringUtil;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.jcraft.jsch.*;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.Vector;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class JschUtil {
    private static Cache<String, Object> cache;
    static {
        CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder();
        cacheBuilder.expireAfterWrite(5, TimeUnit.MINUTES);
        cache = cacheBuilder.build();
    }
    public static void recursiveDelete(ChannelSftp sftp, String path)
            throws SftpException {
        Vector<?> entries = sftp.ls(path);
        for (Object object : entries) {
            ChannelSftp.LsEntry entry = (ChannelSftp.LsEntry) object;
            if (entry.getFilename().equals(".")
                    || entry.getFilename().equals("..")) {
                continue;
            }
            if (entry.getAttrs().isDir()) {
                recursiveDelete(sftp, path + entry.getFilename() + "/");
            } else {
                sftp.rm(path + entry.getFilename());
            }
        }
        sftp.rmdir(path);
    }
    public  static boolean isFileExist(ChannelSftp sftpChannel, String path) {
        try {
            SftpATTRS lstat = sftpChannel.lstat(path);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    @SneakyThrows
    public static Session getRootSession(ServerConfig selectedItem) {
        if (selectedItem.getRootUsername() == null) {
            throw new Exception("必须提供root用户名密码");
        }
        return (Session) cache.get(selectedItem.getIp() + "Session", () -> createNewSession(selectedItem));
    }

    private static Session createNewSession(ServerConfig selectedItem) throws JSchException {
        JSch jsch = new JSch();
        Session session = jsch.getSession(selectedItem.getRootUsername(), selectedItem.getIp(), selectedItem.getPort());
        session.setConfig("StrictHostKeyChecking", "no");
        session.setPassword(selectedItem.getRootPassword());
        session.connect();
        return session;
    }

    @SneakyThrows
    public static String getAsyncExecResult(ServerConfig selectedItem, String cmd) {
        Session session = getRootSession(selectedItem);
        ChannelShell channel = (ChannelShell) session.openChannel("shell");
        channel.connect();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        StringBuffer result = new StringBuffer();
        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(200);
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(channel.getInputStream(),
                                StandardCharsets.UTF_8));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    if (line.contains(selectedItem.getRootUsername())) break;
                    result.append(line + System.lineSeparator());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            countDownLatch.countDown();
        });
        PrintWriter printWriter = new PrintWriter(channel.getOutputStream());
        printWriter.println(cmd);
        printWriter.flush();
        printWriter.close();
        countDownLatch.await(5, TimeUnit.SECONDS);
        return result.toString();
    }

    @SneakyThrows
    public static String getExecResult(ServerConfig selectedItem, String cmd) {
        String result =
                ExecShell(selectedItem, cmd);
        if (StringUtil.isBlank(result)) {
            System.out.println("同步无法获得结果 改为异步");
            result = getAsyncExecResult(selectedItem, cmd);
        }
        return result;
    }

    @SneakyThrows
    public static String ExecShell(ServerConfig selectedItem, String cmd) {
        Session session = getRootSession(selectedItem);
        ChannelExec channel = (ChannelExec) session.openChannel("exec");
        channel.setCommand(cmd);
        channel.connect();
        String result = IoUtil.readStringFromStream(channel.getInputStream());
        return result;
    }

    @SneakyThrows
    public static ChannelSftp getSftpChannel(ServerConfig selectedItem) {
        return (ChannelSftp) cache.get(selectedItem.getIp() + "SftpChannel", () -> createNewChannel(selectedItem));
    }

    private static ChannelSftp createNewChannel(ServerConfig selectedItem) throws JSchException {
        Session session = getRootSession(selectedItem);
        ChannelSftp channel = (ChannelSftp) session.openChannel("sftp");
        channel.connect();
        return channel;
    }
}
