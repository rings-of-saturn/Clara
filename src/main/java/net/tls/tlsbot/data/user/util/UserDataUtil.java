package net.tls.tlsbot.data.user.util;

import java.io.*;

public class UserDataUtil{
    public static File getProxyData(File userData, String proxyName) {
        File dir = new File(userData + "/proxyDatas/" + proxyName);
        if(!dir.exists())
            dir.mkdirs();
        return dir;
    }

    public static File getUserStorage(String username){
        File dir = new File("E:/Projects/TLSBot/src/main/resources/tlsbot/stored/" + username);
        if(!dir.exists())
            dir.mkdirs();
        return dir;
    }


    public static void setProxyName(File userDir, String proxyName) {
        new File(userDir + "/name.txt").delete();
        new File(userDir.getParent() + "/" + proxyName).mkdirs();
    }

    public static String getProxyDisplayName(File pDir, String proxyName){
        FileReader fw;
        File dir = new File(pDir + "/" + proxyName + "/dn.txt");
        if(!dir.exists())
            return proxyName;
        try {
            fw = new FileReader(dir);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            return String.valueOf(fw.read());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
