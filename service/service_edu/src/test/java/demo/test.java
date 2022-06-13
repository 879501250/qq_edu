package com.fine.tools.utils;

import java.io.*;
import java.net.URLEncoder;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;
import com.jcraft.jsch.ChannelSftp.LsEntry;

import javax.servlet.http.HttpServletResponse;

public class SFTPUtilNew {
    private String host = "127.0.0.1";//服务器连接ip
    private String username = "root";//用户名
    private String password = "123";//密码
    private int port = 22;//端口号
    private ChannelSftp sftp = null;
    private Session sshSession = null;

    public SFTPUtilNew(){}

    public SFTPUtilNew(String host, int port, String username, String password)
    {
        this.host = host;
        this.username = username;
        this.password = password;
        this.port = port;
    }

    public SFTPUtilNew(String host, String username, String password)
    {
        this.host = host;
        this.username = username;
        this.password = password;
    }

    /**
     * 通过SFTP连接服务器
     */
    public void connect()
    {
        try
        {
            JSch jsch = new JSch();
            jsch.getSession(username, host, port);
            sshSession = jsch.getSession(username, host, port);
            sshSession.setPassword(password);
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            sshSession.connect();
            Channel channel = sshSession.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 关闭连接
     */
    public void disconnect()
    {
        if (this.sftp != null)
        {
            if (this.sftp.isConnected())
            {
                this.sftp.disconnect();
            }
        }
        if (this.sshSession != null)
        {
            if (this.sshSession.isConnected())
            {
                this.sshSession.disconnect();
            }
        }
    }

    /**
     * 批量下载文件
     * @param remotePath：远程下载目录(以路径符号结束,可以为相对路径eg:/assess/sftp/jiesuan_2/2014/)
     * @param localPath：本地保存目录(以路径符号结束,D:\Duansha\sftp\)
     * @param fileFormat：下载文件格式(以特定字符开头,为空不做检验)
     * @param fileEndFormat：下载文件格式(文件格式)
     * @param del：下载后是否删除sftp文件
     * @return
     */
    public List<File> batchDownLoadFile(String remotePath, String localPath,
                                        String fileFormat, String fileEndFormat, boolean del)
    {
        List<File> fileList = new ArrayList<File>();
        File file = null;
        try
        {
            Vector v = listFiles(remotePath);
            if (v.size() > 0)
            {
                Iterator it = v.iterator();
                while (it.hasNext())
                {
                    LsEntry entry = (LsEntry) it.next();
                    String filename = entry.getFilename();
                    SftpATTRS attrs = entry.getAttrs();
                    if (!attrs.isDir())
                    {
                        fileFormat = fileFormat == null ? "" : fileFormat
                                .trim();
                        fileEndFormat = fileEndFormat == null ? ""
                                : fileEndFormat.trim();
                        // 三种情况
                        if (fileFormat.length() > 0 && fileEndFormat.length() > 0)
                        {
                            if (filename.startsWith(fileFormat) && filename.endsWith(fileEndFormat))
                            {
                                file = downloadFile(remotePath, filename,localPath, filename);
                                if (file!=null)
                                {
                                    fileList.add(file);
                                    if (file!=null && del)
                                    {
                                        deleteSFTP(remotePath, filename);
                                    }
                                }
                            }
                        }
                        else if (fileFormat.length() > 0 && "".equals(fileEndFormat))
                        {
                            if (filename.startsWith(fileFormat))
                            {
                                file = downloadFile(remotePath, filename, localPath, filename);
                                if (file!=null)
                                {
                                    fileList.add(file);
                                    if (file!=null && del)
                                    {
                                        deleteSFTP(remotePath, filename);
                                    }
                                }
                            }
                        }
                        else if (fileEndFormat.length() > 0 && "".equals(fileFormat))
                        {
                            if (filename.endsWith(fileEndFormat))
                            {
                                file = downloadFile(remotePath, filename,localPath, filename);
                                if (file!=null)
                                {
                                    fileList.add(file);
                                    if (file!=null && del)
                                    {
                                        deleteSFTP(remotePath, filename);
                                    }
                                }
                            }
                        }
                        else
                        {
                            file = downloadFile(remotePath, filename,localPath, filename);
                            if (file!=null)
                            {
                                fileList.add(file);
                                if (file!=null && del)
                                {
                                    deleteSFTP(remotePath, filename);
                                }
                            }
                        }
                    }
                }
            }
        }
        catch (SftpException e)
        {
            e.printStackTrace();
        }
        return fileList;
    }

    /**
     * 下载单个文件
     * @param remotePath：远程下载目录(以路径符号结束)
     * @param remoteFileName：下载文件名
     * @param localPath：本地保存目录(以路径符号结束)
     * @param localFileName：保存文件名
     * @return
     */
    public File downloadFile(String remotePath, String remoteFileName,String localPath, String localFileName)
    {
        FileOutputStream fieloutput = null;
        File file = null;
        try
        {
            file = new File(localPath + localFileName);
            fieloutput = new FileOutputStream(file);
            sftp.get(remotePath + remoteFileName, fieloutput);
            fieloutput.close();
            return file;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (SftpException e)
        {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally
        {
            if (null != fieloutput)
            {
                try
                {
                    fieloutput.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }

    public InputStream downloadStream(String directory, String downloadFile){
        try{
            if(directory != null && !"".equals(directory)){
                sftp.cd(directory);
            }
            return sftp.get(downloadFile);
        }catch (SftpException e){
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 上传单个文件
     * @param remotePath：远程保存目录
     * @param remoteFileName：保存文件名
     * @param localPath：本地上传目录(以路径符号结束)
     * @param localFileName：上传的文件名
     * @return
     */
    public boolean uploadFile(String remotePath, String remoteFileName,String localPath, String localFileName)
    {
        FileInputStream in = null;
        try
        {
            createDir(remotePath);
            File file = new File(localPath + localFileName);
            in = new FileInputStream(file);
            sftp.put(in, remoteFileName);
            return true;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (SftpException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (in != null)
            {
                try
                {
                    in.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * 批量上传文件
     * @param remotePath：远程保存目录
     * @param localPath：本地上传目录(以路径符号结束)
     * @param del：上传后是否删除本地文件
     * @return
     */
    public boolean bacthUploadFile(String remotePath, String localPath,
                                   boolean del)
    {
        try
        {
            connect();
            File file = new File(localPath);
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++)
            {
                if (files[i].isFile()
                        && files[i].getName().indexOf("bak") == -1)
                {
                    if (this.uploadFile(remotePath, files[i].getName(),
                            localPath, files[i].getName())
                            && del)
                    {
                        deleteFile(localPath + files[i].getName());
                    }
                }
            }
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            this.disconnect();
        }
        return false;

    }

    /**
     * 删除本地文件
     * @param filePath
     * @return
     */
    public boolean deleteFile(String filePath)
    {
        File file = new File(filePath);
        if (!file.exists())
        {
            return false;
        }

        if (!file.isFile())
        {
            return false;
        }
        boolean rs = file.delete();
        return rs;
    }

    /**
     * 创建目录
     * @param createpath
     * @return
     */
    public boolean createDir(String createpath)
    {
        try
        {
            if (isDirExist(createpath))
            {
                this.sftp.cd(createpath);
                return true;
            }
            String pathArry[] = createpath.split("/");
            StringBuffer filePath = new StringBuffer("/");
            for (String path : pathArry)
            {
                if (path.equals(""))
                {
                    continue;
                }
                filePath.append(path + "/");
                if (isDirExist(filePath.toString()))
                {
                    sftp.cd(filePath.toString());
                }
                else
                {
                    // 建立目录
                    sftp.mkdir(filePath.toString());
                    // 进入并设置为当前目录
                    sftp.cd(filePath.toString());
                }

            }
            this.sftp.cd(createpath);
            return true;
        }
        catch (SftpException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 判断目录是否存在
     * @param directory
     * @return
     */
    public boolean isDirExist(String directory)
    {
        boolean isDirExistFlag = false;
        try
        {
            SftpATTRS sftpATTRS = sftp.lstat(directory);
            isDirExistFlag = true;
            return sftpATTRS.isDir();
        }
        catch (Exception e)
        {
            if (e.getMessage().toLowerCase().equals("no such file"))
            {
                isDirExistFlag = false;
            }
        }
        return isDirExistFlag;
    }

    /**
     * 删除stfp文件
     * @param directory：要删除文件所在目录
     * @param deleteFile：要删除的文件
     */
    public void deleteSFTP(String directory, String deleteFile)
    {
        try
        {
            sftp.rm(directory + deleteFile);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 将文件打包
     * @param srcfile：文件集合
     * @param zipFileName：生成的文件名
     */
    public static void zipFiles(List<File> srcfile, String zipFileName, HttpServletResponse response) throws IOException {
        byte[] buf = new byte[1024];
        // 获取输出流
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileInputStream in = null;
        ZipOutputStream out = null;
        try {
            response.reset();
            // 不同类型的文件对应不同的MIME类型
            response.setContentType("application/x-msdownload");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(zipFileName + ".zip", "UTF-8"));

            // ZipOutputStream类：完成文件或文件夹的压缩
            out = new ZipOutputStream(bos);
            for (int i = 0; i < srcfile.size(); i++) {
                in = new FileInputStream(srcfile.get(i));
                // 给列表中的文件单独命名
                out.putNextEntry(new ZipEntry(srcfile.get(i).getName()));
                int len = -1;
                while ((len = in.read(buf)) != -1) {
                    out.write(buf, 0, len);
                }
            }
            out.close();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) in.close();
            if (out != null) out.close();
        }
    }

    /**
     * 列出目录下的文件
     *
     * @param directory：要列出的目录
     * @return
     * @throws SftpException
     */
    public Vector listFiles(String directory) throws SftpException
    {
        return sftp.ls(directory);
    }

    public String getHost()
    {
        return host;
    }

    public void setHost(String host)
    {
        this.host = host;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public int getPort()
    {
        return port;
    }

    public void setPort(int port)
    {
        this.port = port;
    }

    public ChannelSftp getSftp()
    {
        return sftp;
    }

    public void setSftp(ChannelSftp sftp)
    {
        this.sftp = sftp;
    }

}

